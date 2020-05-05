const Handlebars = require('../../node_modules/handlebars/dist/handlebars')

module.exports = {
  homeTemplate:
    Handlebars.compile(require('./templates/home.hbs').default),
  listGames:
    Handlebars.compile(require('./templates/listGames.hbs').default),
  groupsTemplate:
    Handlebars.compile(require('./templates/groups.hbs').default),
  aboutTemplate:
    Handlebars.compile(require('./templates/about.hbs').default),
  specificGroup:
    Handlebars.compile(require('./templates/specificGroup.hbs').default),
  alert:
    Handlebars.compile(require('./templates/alert.hbs').default),
  addGameToGroup:
    Handlebars.compile(require('./templates/addGameToGroup.hbs').default),
  signUpTemplate:
    Handlebars.compile(require('./templates/signUp.hbs').default),
  userLogin:
    Handlebars.compile(require('./templates/userLogin.hbs').default),
  error404:
    Handlebars.compile(require('./templates/error404.hbs').default),
  error500:
    Handlebars.compile(require('./templates/error500.hbs').default),
  error400:
    Handlebars.compile(require('./templates/error400.hbs').default),
  error401:
    Handlebars.compile(require('./templates/error401.hbs').default),
  error403:
    Handlebars.compile(require('./templates/error403.hbs').default),
  error503:
    Handlebars.compile(require('./templates/error503.hbs').default),
  authenticatedNavbar:
    Handlebars.compile(require('./templates/authenticatedNavbar.hbs').default),
  unauthenticatedNavbar:
    Handlebars.compile(require('./templates/unauthenticatedNavbar.hbs').default)
}
