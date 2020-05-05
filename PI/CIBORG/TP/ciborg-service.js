const responseMapper = require('./internal-errors')

Array.prototype.remove = function (elem) {
  let idx = this.findIndex(e => e == elem)
  if (idx != -1) {
    this.splice(idx, 1)
    return true;
  }
  return false
}

module.exports = function (_boardGamesData, _ciborgBD) {
  const boardGamesData = _boardGamesData
  const ciborgDB = _ciborgBD

  return {
    getPopularGames,
    getSpecificGameWithName,
    postGroups,
    editGroup,
    getAllGroups,
    getGroupDetail,
    putGameInGroup,
    deleteGameInGroup,
    getGamesInGroupWithinTime,
    deleteGroup,
    createUser,
    deleteUser,
    validateLogin,
    getUser
  }

  function getUser(username){
    return ciborgDB.getUser(username)
  }

  function validateLogin(username, password) {
    return ciborgDB.validateLogin(username, password)
  }

  function checkIfGroupBelongsToUser(username, groupId) {
    return ciborgDB.getUser(username)
      .then(user => {
        if (user.groups.find((id) => id == groupId)) {
          return true
        }
        throw responseMapper.NoSuchResource("Group")
      })
  }

  function createUser(username, password) {
    return responseMapper
      .validateParameter(username, (name) => name, "username")
      .then(() => responseMapper.validateParameter(password, (password) => password, "password"))
      .then(() => ciborgDB.userExists(username))
      .then(exists => {
        if (!exists) {
          return ciborgDB.createUser(username, password)
        } else {
          throw responseMapper.UserAlreadyExists(username)
        }
      })
  }

  function deleteUser(username, password) {
    return responseMapper
      .validateParameter(username, (name) => name, "username")
      .then(() => responseMapper.validateParameter(password, (password) => password, "password"))
      .then(() => ciborgDB.getUser(username))
      .then((user) => {
          if(user.username == username && user.password == password){
              return ciborgDB.deleteUser(username,password)
          }
          else throw responseMapper.Unauthorized

      })
  }

  function getPopularGames() {
    return boardGamesData.getPopularGames()
  }

  function getSpecificGameWithName(gameName) {
    return responseMapper
      .validateParameter(gameName, (gname) => gameName, "gameName")
      .then(() => boardGamesData.getSpecificGameWithName(gameName))
  }


  function postGroups(name, description, username) {
    return responseMapper
      .validateParameter(name, n => n, "name")
      .then(() => responseMapper.validateParameter(description, d => d, "description"))
      .then(() => ciborgDB.postGroups(name, description))
      .then((result) => ciborgDB.getUser(username)
        .then((user) => {
          user.groups.push(result.id)
          return ciborgDB.editUser(user.username, user.password, user.groups)
        }))
  }


  function editGroup(groupId, name, description, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => responseMapper.validateParameter(name, n => n, "name"))
      .then(() => responseMapper.validateParameter(description, d => d, "description"))
      .then(() => checkIfGroupBelongsToUser(username, groupId)
        .then(() => ciborgDB.getGroupDetail(groupId)
          .then(group => ciborgDB.editGroup(groupId, name, description, group.games))
        )
      )
  }

  function getAllGroups(username) {
    return ciborgDB.getUser(username)
      .then(user => ciborgDB.getAllGroups(user.groups))
  }

  function getGroupDetail(groupId, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => checkIfGroupBelongsToUser(username, groupId))
      .then(() => ciborgDB.getGroupDetail(groupId))
      .then(group => {
        if (!group.games.length) return group
        return boardGamesData.getSpecificGames(group.games)
          .then(games => {
            group.games = games;
            return group
          }
          )
      })
  }


  function putGameInGroup(groupId, gameId, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => responseMapper.validateParameter(gameId, (gId) => gId, "gameId"))
      .then(() => checkIfGroupBelongsToUser(username, groupId))
      .then(() => ciborgDB.getGroupDetail(groupId))
      .then(group => {
        const gamePresent = group.games.find(presentGameId => presentGameId == gameId)
        if (gamePresent) return { status: "Game Added", uri: `/ciborg/api/groups/${groupId}` }
        return boardGamesData.getSpecificGame(gameId)
          .then(game => {
            group.games.push(game.game.id)
            return ciborgDB.putGameInGroup(group.id, group.name, group.description, group.games)
          })
      })
  }

  function deleteGameInGroup(groupId, gameId, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => responseMapper.validateParameter(gameId, (gId) => gId, "gameId"))
      .then(() => checkIfGroupBelongsToUser(username, groupId))
      .then(() => ciborgDB.getGroupDetail(groupId))
      .then(group => {
        const game = group.games.find(element => element == gameId)
        if (!game) {
          throw responseMapper.NoSuchResource("Game")
        }
        group.games.remove(game)
        return ciborgDB.deleteGameInGroup(group.id, group.name, group.description, group.games)
      })
  }

  function getGamesInGroupWithinTime(groupId, minTime, maxTime, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => responseMapper.validateParameter(minTime, (min) => min, "minTime"))
      .then(() => responseMapper.validateParameter(maxTime, (max) => max, "maxTime"))
      .then(() => checkIfGroupBelongsToUser(username, groupId))
      .then(() => ciborgDB.getGroupDetail(groupId))
      .then(group => {
        if (!group.games.length) return group
        return boardGamesData.getSpecificGames(group.games)
          .then(games => {
            group.games = games
            const gamesWithinTime = games.filter(elem => elem['max_playtime'] >= minTime && elem['max_playtime'] <= maxTime).sort((time1, time2) => time1 - time2)
            return { games: gamesWithinTime }
          }
          )
      })
  }

  function deleteGroup(groupId, username) {
    return responseMapper
      .validateParameter(groupId, (gId) => gId, "groupId")
      .then(() => checkIfGroupBelongsToUser(username, groupId))
      .then(() => ciborgDB.deleteGroup(groupId))
  }
}