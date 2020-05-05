const templates = require('./templates')


module.exports = {
  homeView,
  gamesView,
  groupsView,
  aboutView,
  specificGroupView,
  createGroupView,
  addGameToGroupView,
  putGameIntoGroupView,
  editGroupView,
  deleteGroupView,
  deleteGroupGameView,
  groupGamesBetweenTimeView,
  createUserView,
  signUpView,
  userLoginView,
  loginView,
  logoutView,
  errorView
}
const showNavigationBar = {
  true: function(routeManager){
    routeManager.setNavigationBarContent(templates.authenticatedNavbar())
  },
  false : function(routeManager){
    routeManager.setNavigationBarContent(templates.unauthenticatedNavbar())
  } 
}

function displayView(html,routeManager){
  showNavigationBar[routeManager.context.login](routeManager)
  routeManager.setMainContent(html)
}

function homeView(data, routeManager) {
  displayView(templates.homeTemplate(),routeManager)
}

function gamesView(data, routeManager) {
  displayView(templates.listGames(data),routeManager)
  document.getElementById("searchGameButton").addEventListener("click", searchGame)
  if(routeManager.context.login)
    document.querySelectorAll("button.addToGroup").forEach(b => b.addEventListener("click", addToGroup))
  else{
    document.querySelectorAll("button.addToGroup").forEach(b => b.hidden = "hidden")
  }
  
  function searchGame() {
    const gameName = document.getElementById("gameName").value
    if (gameName)
      routeManager.changeRoute(`searchGamesWithName/${gameName}`)
    else
      routeManager.showAlert(templates.alert({ type: "danger", message: "Missing Information" }))
  }

  function addToGroup() {
    const gameId = this.id;
    routeManager.changeRoute(`addGameToGroup/${gameId}`)
  }
}

function groupsView(data, routeManager) {
  displayView(templates.groupsTemplate(data),routeManager)
  document.getElementById("createGroupButton").addEventListener("click", postGroup)
  document.querySelectorAll("button.deleteGroup").forEach(b => b.addEventListener("click", deleteGroup))
  function postGroup() {
    const name = document.getElementById("groupName").value
    const description = document.getElementById("groupDescription").value
    const groupInfo = {
      name,
      description
    }
    if (name)
      routeManager.changeRoute(`createGroup`, groupInfo)
    else
      routeManager.showAlert(templates.alert({ type: "danger", message: "Missing Information" }))
  }

  function deleteGroup() {
    routeManager.changeRoute(`deleteGroup/${this.id}`)
  }
}

function createGroupView(data, routeManager) {
  routeManager.showAlert(templates.alert({ type: "success", message: "Group Created" }))
  routeManager.changeRoute(`groups`)
}

function deleteGroupView(data, routeManager) {
  routeManager.showAlert(templates.alert({ type: "success", message: "Group Deleted" }))
  routeManager.changeRoute(`groups`)
}


function aboutView(data, routeManager) {
  displayView(templates.aboutTemplate(),routeManager)
}

function specificGroupView(data, routeManager) {
  console.log("DATA " + JSON.stringify(data))
  displayView(templates.specificGroup(data.data),routeManager)
  document.getElementById("editGroupButton").addEventListener("click", () => editGroup(data.groupId, routeManager))
  document.getElementById("searchGroupGamesBetweenMinAndMaxTime").addEventListener("click", () => searchGamesBetweenTime(data.groupId, routeManager))
  document.querySelectorAll("button.deleteGameOfGroup").forEach(b => b.addEventListener("click", deleteGameOfGroup))
  function deleteGameOfGroup() {
    routeManager.changeRoute("deleteGroupGame", { gameId: this.id, groupId: data.groupId })
  }
}

function groupGamesBetweenTimeView(data, routeManager) {
  console.log("DATA " + JSON.stringify(data))
  displayView(templates.specificGroup(data.data),routeManager)
  document.getElementById("editGroupButton").addEventListener("click", () => editGroup(data.groupId, routeManager))
  document.getElementById("searchGroupGamesBetweenMinAndMaxTime").addEventListener("click", () => {console.log("clicked"); return searchGamesBetweenTime(data.groupId, routeManager)})
  document.querySelectorAll("button.deleteGameOfGroup").forEach(b => b.addEventListener("click", deleteGameOfGroup))

  function deleteGameOfGroup() {
    routeManager.changeRoute("deleteGroupGame", { gameId: this.id, groupId: data.groupId })
  }
}

function editGroup(groupId, routeManager) {
  const name = document.getElementById("groupName").value
  const description = document.getElementById("groupDescription").value
  const groupInfo = {
    name,
    description,
    groupId
  }
  routeManager.changeRoute(`editGroup`, groupInfo)
}

function searchGamesBetweenTime(groupId, routeManager) {
  const minimumTime = document.getElementById("minTime").value
  const maximum_time = document.getElementById("maxTime").value
  routeManager.changeRoute(`groupGamesBetweenTime/${groupId}/?minimum_time=${minimumTime}&maximum_time=${maximum_time}`)
}

function editGroupView(data, routeManager) {

  routeManager.showAlert(templates.alert({ type: "success", message: "Group Edited" }))
  routeManager.changeRoute(`specificGroup/${data.groupId}`)

}

function deleteGroupGameView(data, routeManager) {
  routeManager.showAlert(templates.alert({ type: "success", message: "Game Deleted" }))
  routeManager.changeRoute(`specificGroup/${data.groupId}`)

}

function addGameToGroupView(data, routeManager) {
  displayView(templates.addGameToGroup(data.groups),routeManager)
  document.querySelectorAll("button.addToGroup").forEach(b => b.addEventListener("click", addToGroup))


  function addToGroup() {
    const groupId = this.id
    const gameId = data.gameId
    routeManager.changeRoute(`putGameIntoGroup`, { groupId, gameId })
  }

}

function putGameIntoGroupView(groupInfo, routeManager) {
  const gameId = groupInfo.gameId
  routeManager.showAlert(templates.alert({ type: "success", message: "Game Added to Group" }))
  routeManager.changeRoute(`addGameToGroup/${gameId}`)

}

function signUpView(data, routeManager) {
  if(routeManager.context.login) routeManager.changeRoute(`home`)
  displayView(templates.signUpTemplate(data),routeManager)
  document.getElementById("createUserButton").addEventListener("click", createUser)

  function createUser() {
    const username = document.getElementById("username").value
    const password = document.getElementById("password").value
    if (username && password)
      routeManager.changeRoute(`createUser`, { username, password })
    else
      routeManager.showAlert(templates.alert({ type: "danger", message: "Missing Information" }))

  }
}

function createUserView(data, routeManager) {
  routeManager.showAlert(templates.alert({ type: "success", message: "User Created" }))
  routeManager.changeRoute(`home`)
}

function userLoginView(data, routeManager) {
  if(routeManager.context.login) routeManager.changeRoute(`home`)
  displayView(templates.userLogin(data),routeManager)
  document.getElementById("loginButton").addEventListener("click", login)

  function login() {
    const username = document.getElementById("username").value
    const password = document.getElementById("password").value
    if (username && password) {
      routeManager.changeRoute(`login`, { username, password })
    }
    else
      routeManager.showAlert(templates.alert({ type: "danger", message: "Missing Information" }))
  }
}

function loginView(data, routeManager) {
  if (data) {
    routeManager.showAlert(templates.alert({ type: "success", message: "User login" }))
    routeManager.changeRoute(`home`)
  } else {
    routeManager.showAlert(templates.alert({ type: "danger", message: "Error logging in" }))
    routeManager.changeRoute(`userlogin`)
  }
}

function logoutView(data, routeManager) {
  routeManager.showAlert(templates.alert({ type: "success", message: "User logout" }))
  routeManager.changeRoute(`home`)
}

function errorView(err, routeManager) {
  const map = {

    '400': function () { displayView(templates.error400(err),routeManager) },
    '401': function () { displayView(templates.error401(err),routeManager) },
    '403': function () { displayView(templates.error403(err),routeManager) },
    '404': function () { displayView(templates.error404(err),routeManager) },
    '500': function () { displayView(templates.error500(err),routeManager) },
    '503': function () { displayView(templates.error503(err),routeManager) }
  }
  if(!err || !err.statusCode || !map[err.statusCode]){
    err = {error :"Unexpected Error", statusCode : 500}
  }
  map[err.statusCode]()
}
