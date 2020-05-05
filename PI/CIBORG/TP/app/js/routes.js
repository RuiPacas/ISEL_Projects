const controller = require('./controller/ciborg-controller')
const views = require('./view/views')



module.exports = {
  home: {
    controller: controller.home,
    view: views.homeView
  },
  games: {
    controller: controller.games,
    view: views.gamesView,
    errorView: views.errorView
  },
  searchGamesWithName: {
    controller: controller.searchGamesWithName,
    view: views.gamesView,
    errorView: views.errorView
  },
  groups: {
    controller: controller.groups,
    view: views.groupsView,
    errorView: views.errorView
  },
  about: {
    controller: controller.about,
    view: views.aboutView,
    errorView: views.errorView
  },
  specificGroup: {
    controller: controller.specificGroup,
    view: views.specificGroupView,
    errorView: views.errorView
  },
  createGroup: {
    controller: controller.createGroup,
    view: views.createGroupView,
    errorView: views.errorView
  },
  addGameToGroup: {
    controller: controller.addGameToGroup,
    view: views.addGameToGroupView,
    errorView: views.errorView
  },
  putGameIntoGroup: {
    controller: controller.putGameIntoGroup,
    view: views.putGameIntoGroupView,
    errorView: views.errorView
  },
  editGroup: {
    controller: controller.editGroup,
    view: views.editGroupView,
    errorView: views.errorView
  },
  deleteGroup: {
    controller: controller.deleteGroup,
    view: views.deleteGroupView,
    errorView: views.errorView
  },
  deleteGroupGame: {
    controller: controller.deleteGroupGame,
    view: views.deleteGroupGameView,
    errorView: views.errorView
  },
  groupGamesBetweenTime: {
    controller: controller.groupGamesBetweenTime,
    view: views.groupGamesBetweenTimeView,
    errorView: views.errorView
  },
  signUp: {
    controller: controller.signUp,
    view: views.signUpView,
    errorView: views.errorView
  },
  createUser: {
    controller: controller.createUser,
    view: views.createUserView,
    errorView: views.errorView
  },
  userLogin: {
    controller: controller.userLogin,
    view: views.userLoginView,
    errorView: views.errorView
  },
  login: {
    controller: controller.login,
    view: views.loginView,
    errorView: views.errorView
  },
  logout: {
    controller: controller.logout,
    view: views.logoutView,
    errorView: views.errorView
  }
}

