const data = require('../model/ciborg-data')

function updateState(routeManager){
  return data.isSessionActive()
      .then(session => { 
        routeManager.context.login = session
  })
}


module.exports = {
  home: async function (routeManager) {
    return updateState(routeManager).then(()=>{})
  },
  games: async function (routeManager) {
    return updateState(routeManager).then(()=>data.getPopularGames().then( data => {
        data.games.map( game => {
          game.description = game.description.slice(0,100) + "..." // Description too long
          return game
        })
        return data;
    }))
  },
  searchGamesWithName: async function (routeManager, name, ) {
    return updateState(routeManager).then(()=>data.getSpecificGameWithName(name)
      .then(data => {
        data.games.map( game => {
          game.description = game.description.slice(0,100) + "..." // Description too long
          return game
        })
        return data;
      }))
  },
  groups: async function (routeManager) {
    return updateState(routeManager).then(()=> data.getGroups()
            .then( data => {
                data.groups = data.groups.sort((g1,g2) => g1.name>g2.name ? 1 : g1.name<g2.name ? -1 : 0 )
                return data
             })
      )
  },
  about: async function (routeManager) {
    return updateState(routeManager).then(()=>{})
  },
  specificGroup: async function (routeManager, id) {
    return updateState(routeManager).then(()=>data.getSpecificGroupWithId(id).then(
      data=>{
        data.data.games.map( game => {
          game.description = game.description.slice(0,100) + "..." // Description too long
          return game
        })
        return data
      }
    ))
  },
  createGroup: async function (routeManager, groupInfo) {
    return updateState(routeManager).then(()=>data.postGroup(groupInfo))
  },
  addGameToGroup: async function (routeManager, gameId) {
    return updateState(routeManager).then(()=>data.getGroups()
      .then((groups) => {
        groups.groups = groups.groups.filter( group => !group.games.includes(gameId))
        groups.groups = groups.groups.sort((g1,g2) => g1.name>g2.name ? 1 : g1.name<g2.name ? -1 : 0 )
        return { groups, gameId }
      }))
  },
  putGameIntoGroup: async function (routeManager, gameGroupInfo) {
    return updateState(routeManager).then(()=>data.putGameIntoGroup(gameGroupInfo))
  },
  editGroup: async function (routeManager, groupInfo) {
    return updateState(routeManager).then(()=>data.editGroup(groupInfo))
  },
  deleteGroup: async function (routeManager, groupId) {
    return updateState(routeManager).then(()=>data.deleteGroup(groupId))
  },
  deleteGroupGame: async function (routeManager, gameGroupInfo) {
    return updateState(routeManager).then(()=>data.deleteGroupGame(gameGroupInfo))
  },
  groupGamesBetweenTime: async function (routeManager, groupId, queryString) {
    return updateState(routeManager).then(()=>data.groupGamesBetweenTime(queryString,groupId))
  },
  signUp: async function (routeManager) {
    return updateState(routeManager).then(()=>{})
  },
  createUser: async function (routeManager, userInfo) {
    return updateState(routeManager).then(()=>data.createUser(userInfo))
  },
  userLogin: async function (routeManager) {
    return updateState(routeManager).then(()=>{})
  },
  login: async function (routeManager, loginInfo) {
    const username = loginInfo.username
    const password = loginInfo.password
    if(!routeManager.context.login){
      return data.login(username, password)
      .then( (data) => {
        routeManager.context.login = true
        routeManager.context.username = username
        return data
      })
    }
  }, 
  logout: async function (routeManager) {
    return updateState(routeManager)
    .then(() => data.logout())
    .then( (data) => {
      routeManager.context.login = false
      routeManager.context.username = undefined
      return data
    })
  }
}