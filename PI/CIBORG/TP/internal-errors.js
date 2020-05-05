const InvalidParameter = function (parameterName) {
  return {
    name: "InvalidParameter",
    parameterName
  }
}

const NoSuchResource = function (resourceName) {
  return {
    name: "NoSuchResource",
    resourceName
  }
}
const InternalError = {
  name: "InternalError"
}
const ServiceUnavailable = {
  name: "ServiceUnavailable"
}
const UserAlreadyExists = function (userName) {
  return {
    name: "UserAlreadyExists",
    userName
  }
}

const BadRequest = {
  name: "Bad Request"
}

const Forbidden = {
  name: "Forbidden"
}

const Unauthorized = {
  name: "Unauthorized"
}

module.exports = {
  InvalidParameter,
  NoSuchResource,
  InternalError,
  ServiceUnavailable,
  UserAlreadyExists,
  BadRequest,
  Forbidden,
  Unauthorized,

  validateParameter: function (parameter, predicate, parameterName) {
    return (predicate(parameter)) ? Promise.resolve() : Promise.reject(InvalidParameter(parameterName))
  },
  mapResponse: function (statusCode, sucessProcessor, successStatusCode = 200, missingResourceName = null) {
    if (statusCode == successStatusCode) return Promise.resolve(sucessProcessor())
    const map = {
      '404': function () {
        return Promise.reject(NoSuchResource(missingResourceName))
      },
      '500': function () {
        return Promise.reject(ServiceUnavailable)
      },
      '400': function () {
        return Promise.reject(BadRequest)
      },
      '401': function () {
        return Promise.reject(Unauthorized)
      },
      '403': function () {
        return Promise.reject(Forbidden)
      }

    }
    return map[statusCode] ? map[statusCode]() : Promise.reject(InternalError)
  },



}



