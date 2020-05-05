const routes = require('./routes')


window.addEventListener("load", function () {
  window.addEventListener('hashchange', processHashChange)
  let results = document.querySelector("#results")
  let alert = document.querySelector("#ciborg-alert")

  let routeData = null;

  const routeManager = {
    context: {
      login: false,
      username: undefined 
    },
    setMainContent: function (html) {
      results.innerHTML = html
    },
    setNavigationBarContent : function(html){
      navBarResults.innerHTML = html
    },
    showAlert: function (html) {
      alert.innerHTML = html
      setTimeout(() => alert.innerHTML = "", 2000)
    },

    changeRoute: function (hash, data) {
      routeData = data
      window.location.hash = hash
    }
  }

  function addRouteData(args) {
    args.push(routeData)
    resetRouteData()
  }


  function resetRouteData(args) {
    routeData = null;
  }

  processHashChange()

  function processHashChange() {
    const DEFAULT_STATE = "home"
    const hash = window.location.hash.substring(1)
    let [state, ...args] = hash.split('/')
    let route = routes[state];

    if (!route) {
      window.location.hash = DEFAULT_STATE;
      return;
    }

    addRouteData(args)
    route
      .controller.apply(null, [routeManager, ...args])
      .then(data => route.view(data, routeManager))
      .catch(err => route.errorView(err, routeManager))
  }
});





