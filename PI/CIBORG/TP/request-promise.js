const request = require('request')
const Errors = require('./internal-errors')
module.exports = config => {
    return {
        buildRequestPromise: function (options) {
            return new Promise((resolve, reject) => {
                request(options, function (error, response, body) {
                    if (error || !response.statusCode) reject(Errors.InternalError)
                    else resolve({ response, body })
                })
            })
        },
        buildOptions: function (path = '/', method = 'GET', headers, body) {
            return new Options(path, method, headers, body)
        }
    }

    function Options(path, method, headers, body) {
        let url = "http://"
        if (config.hostname && config.port) url = url + config.hostname + ":" + config.port
        this.url = url + path
        this.headers = headers
        this.body = JSON.stringify(body)
        this.method = method
        return this
    }
}



