
function fetchResponse(uri, options, sucessStatusCode) {
  return fetch(uri, options
  ).then(response => {
    return response.json()
      .then(parsedBody => {
        if (response.status == sucessStatusCode) return parsedBody
        parsedBody.statusCode = response.status;  
        throw parsedBody
      })
  })
}

function buildOptions(method, body) {
  const toReturn = {}
  toReturn.method = method
  toReturn.body = JSON.stringify(body)
  toReturn.headers = { 'Accept': 'application/json', 'Content-Type': 'application/json' }
  return toReturn
}

function CiborgApiUris() {
  const baseUri = "http://localhost:8080/ciborg/api/"
  const baseUriAuth = `${baseUri}auth/`

  this.getPopularGamesUri = () => `${baseUri}games`
  this.getSpecificGameWithNameUri = (name) => `${baseUri}games/${name}`
  this.getGroupsUri = () => `${baseUriAuth}groups`
  this.getSpecificGroupWithIdUri = (id) => `${baseUriAuth}groups/${id}`
  this.putGameIntoGroupUri = (groupId) => `${baseUriAuth}groups/${groupId}/games`
  this.deleteGroupGameUri = (groupId, gameId) => `${baseUriAuth}groups/${groupId}/games/${gameId}`
  this.groupGamesBetweenTimeUri = (queryString, groupId) => `${baseUriAuth}groups/${groupId}/games${queryString}`
  this.createUser = () => `${baseUri}users`
  this.login = () => `${baseUri}login`
  this.logout = () => `${baseUriAuth}logout`
  this.session = () => `${baseUri}session`
}

const apiUris = new CiborgApiUris();

module.exports = {
  getPopularGames: function () {
    return fetchResponse(apiUris.getPopularGamesUri(), {}, 200)
  },
  getSpecificGameWithName: function (name) {
    return fetchResponse(apiUris.getSpecificGameWithNameUri(name), {}, 200)
  },
  getGroups: function () {
    return fetchResponse(apiUris.getGroupsUri(), {}, 200)
  },
  getSpecificGroupWithId: function (id) {
    return fetchResponse(apiUris.getSpecificGroupWithIdUri(id), {}, 200)
      .then(data => { return { data, groupId: id } })
  },
  postGroup: function (groupsInfo) {
    const options = buildOptions("POST", { name: groupsInfo.name, description: groupsInfo.description })
    return fetchResponse(apiUris.getGroupsUri(), options, 201);
  },
  putGameIntoGroup: function (gameGroupInfo) {
    const options = buildOptions("PUT", { idGame: gameGroupInfo.gameId })
    return fetchResponse(apiUris.putGameIntoGroupUri(gameGroupInfo.groupId),
      options, 201).then(data => gameGroupInfo)

  },
  editGroup: function (groupInfo) {
    const options = buildOptions("PUT", { name: groupInfo.name, description: groupInfo.description })
    return fetchResponse(apiUris.getSpecificGroupWithIdUri(groupInfo.groupId),
      options, 200)
      .then(data => groupInfo)
  },
  deleteGroup: function (groupId) {
    const options = buildOptions("DELETE", {})
    return fetchResponse(apiUris.getSpecificGroupWithIdUri(groupId), options, 200)
  },
  deleteGroupGame: function (gameGroupInfo) {
    const options = buildOptions("DELETE", {})
    return fetchResponse(apiUris.deleteGroupGameUri(gameGroupInfo.groupId, gameGroupInfo.gameId),
      options, 200
    ).then(data => gameGroupInfo)
  },
  groupGamesBetweenTime: function (queryString,groupId) {
    return fetchResponse(apiUris.groupGamesBetweenTimeUri(queryString, groupId),
      buildOptions("GET"), 200)
      .then(games => fetchResponse(apiUris.getSpecificGroupWithIdUri(groupId), buildOptions("GET"), 200)
        .then(r => {
          r.games = games.games
          return r
        })
      ).then(data => { return { data, groupId } })
  },
  createUser: function (userInfo) {
    const options = buildOptions("POST", { username: userInfo.username, password: userInfo.password })
    return fetchResponse(apiUris.createUser(), options, 201)
  },
  login: function (username, password) {
    const options = buildOptions("POST", { username, password })
    return fetchResponse(apiUris.login(), options, 200)
    
  },
  logout: function () {
    const options = buildOptions("PUT")
    return fetchResponse(apiUris.logout(), options, 200)
      .then( data => data )
  },
  isSessionActive : function () {
    return fetchResponse(apiUris.session(),{},200).then(data => data.status )
  }
}
