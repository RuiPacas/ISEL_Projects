module.exports = function (err) {
  return map[`${err.name}`](err)
}


const map = {
  BadRequest: function (err) {
    return {
      error: `Bad Request : ${err.err}`,
      statusCode: 400
    }
  },
  Forbidden: function (err) {
    return {
      error: `Forbidden`,
      statusCode: 403
    }
  },
  Unauthorized: function (err) {
    return {
      error: `Unauthorized`,
      statusCode: 401
    }
  },
  InvalidParameter: function (err) {
    return {
      error: `Invalid Parameter : ${err.parameterName}`,
      statusCode: 400
    }
  },
  NoSuchResource: function (err) {
    return {
      error: `Resource ${err.resourceName} not found`,
      statusCode: 404
    }
  }
  , InternalError: function (err) {
    return {
      error: `Internal Error`,
      statusCode: 500
    }
  },
  ServiceUnavailable: function (err) {
    return {
      error: `Service Unavailable`,
      statusCode: 503
    }
  },
  UserAlreadyExists: function (err) {
    return {
      error: `User Already Exists`,
      statusCode: 400
    }
  },
  InvalidCredentials : function() {
    return {
      error : `Invalid Credentials`,
      statusCode: 400
    }
  }
}