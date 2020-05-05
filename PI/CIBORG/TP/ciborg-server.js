module.exports = function (boardGamesData, ciborgDb, config) {
    const http = require("http")
    const express = require("express")
    const cookieParser = require("cookie-parser")
    const passport = require("passport")
    const expressSession = require('express-session')
    const router = express.Router()

    const PORT = config.server.port
    const app = express()

    const ciborgService = require("./ciborg-service")(boardGamesData, ciborgDb)
    const ciborgWebApi = require("./ciborg-web-api")(router, ciborgService)

    passport.serializeUser(ciborgWebApi.serializeUser);
    passport.deserializeUser(ciborgWebApi.deserializeUser);

    app.use(cookieParser())
    app.use(express.json())


    app.use(expressSession(
        {
            resave: false,
            saveUninitialized: true,
            //     store: new FileStore(),
            secret: "..."
        }
    ))
    app.use(passport.initialize())
    app.use(passport.session())

    app.use(config.server["base_address"], router)
    app.use('/', express.static(__dirname + '/public'))


    return {
        run: function () {
            app.listen(PORT, function (err) {
                if (err) {
                    return console.error(err)
                }
                console.log(`Server listening on port ${PORT}`)
            })
        }
    }
}