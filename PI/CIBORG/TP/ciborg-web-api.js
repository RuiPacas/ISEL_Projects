let ciborgService
const httpErrorMapper = require('./internal-to-http')
const responseMapper = require('./internal-errors')

function json(rsp, data) {
  rsp.setHeader('Content-type', 'application/json')
  rsp.end(JSON.stringify(data))
}

module.exports = function (router, _ciborgService) {
  ciborgService = _ciborgService


  router.use('/auth', verifyAuthenticated)
  router.get("/games", getPopularGames)
  router.get("/games/:name", getSpecificGameWithName)
  router.post('/login', validateLogin)
  router.put('/auth/logout', logout)
  router.post("/auth/groups", postGroups)
  router.put("/auth/groups/:id", editGroup)
  router.get("/auth/groups", getAllGroups)
  router.get("/auth/groups/:id", getGroupDetail)
  router.put("/auth/groups/:id/games", putGameInGroup)
  router.delete("/auth/groups/:id/games/:idGame", deleteGameInGroup)
  router.get("/auth/groups/:id/games", getGamesInGroupWithinTime)
  router.delete("/auth/groups/:id", deleteGroup)
  router.post("/users", createUser)
  router.delete("/users", deleteUser)
  router.get("/session",getSessionState)




  function verifyAuthenticated(req, rsp, next) {
    if (req.isAuthenticated())
      return next()
    return rsp.status(401).send(responseMapper.Unauthorized)
  }


  function validateLogin(req, rsp) {
    if (!req.user || !req.user.username) {
      return ciborgService.validateLogin(req.body.username, req.body.password)
        .then((isValid) => {
          if (isValid) {
            req.login({ username: req.body.username }, (err => {
              if (err) {
                throw responseMapper.Unauthorized
              }
              return processResponse(null, { status: "Authenticated" }, rsp)
            }
            ))
          } else {
            throw responseMapper.Unauthorized
          }
        })
        .catch(error => processResponse(error, null, rsp))
    }
    return processResponse(responseMapper.Forbidden, null, rsp)
  }

  function getSessionState(req,rsp){
    processResponse(null,{status : req.isAuthenticated()},rsp,200)
  }

  function logout(req, rsp) {
    req.logout()
    return processResponse(null, { status: "Logout" }, rsp)
  }

  function createUser(req, rsp) {
    ciborgService.createUser(req.body.username, req.body.password)
      .then(value => processResponse(null, value, rsp, 201))
      .catch(error => processResponse(error, null, rsp))
  }

  function deleteUser(req, rsp) {
    ciborgService.deleteUser(req.body.username, req.body.password)
      .then(value => {
        if (req.user && req.user.username == req.body.username) req.logout()
        return processResponse(null, value, rsp)
      })
      .catch(error => processResponse(error, null, rsp))
  }

  function getPopularGames(req, rsp) {
    ciborgService.getPopularGames()
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  function getSpecificGameWithName(req, rsp) {
    ciborgService.getSpecificGameWithName(req.params.name)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  function postGroups(req, rsp) {
    ciborgService.postGroups(req.body.name, req.body.description, req.user.username)
      .then(value => processResponse(null, value, rsp, 201))
      .catch(error => processResponse(error, null, rsp))
  }

  function editGroup(req, rsp) {
    ciborgService.editGroup(req.params.id, req.body.name, req.body.description, req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }


  function getAllGroups(req, rsp) {
    ciborgService.getAllGroups(req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }


  function getGroupDetail(req, rsp) {
    ciborgService.getGroupDetail(req.params.id, req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  function putGameInGroup(req, rsp) {
    ciborgService.putGameInGroup(req.params.id, req.body.idGame, req.user.username)
      .then(value => processResponse(null, value, rsp, 201))
      .catch(error => processResponse(error, null, rsp))
  }

  function deleteGameInGroup(req, rsp) {
    ciborgService.deleteGameInGroup(req.params.id, req.params.idGame, req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  function getGamesInGroupWithinTime(req, rsp) {
    const minimum_time = req.query["minimum_time"]
    const maximum_time = req.query["maximum_time"]
    ciborgService.getGamesInGroupWithinTime(req.params.id, minimum_time, maximum_time, req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  function deleteGroup(req, rsp) {
    ciborgService.deleteGroup(req.params.id, req.user.username)
      .then(value => processResponse(null, value, rsp))
      .catch(error => processResponse(error, null, rsp))
  }

  return {

    serializeUser: function (user, done) {
      done(null, user.username)
    },

    deserializeUser: function (username, done) {
      done(null,{username})
    }
  }
}

function processResponse(err, data, rsp, sucessStatusCode = 200) {
  if (err != null) {
    err = httpErrorMapper(err)
    rsp.statusCode = err.statusCode

    json(rsp,
      {
        error: err.error
      }
    )
  }
  else {
    rsp.statusCode = sucessStatusCode
    json(rsp, data)
  }
}


