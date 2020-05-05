
const config = require("./config.json")
const boardGamesData = require("./mock/board-games-data-mock")(config.api)
const ciborgDb = require("./mock/ciborg-db-mock")(config.db)

console.log("Tests Server")

const server = require("./ciborg-server")(boardGamesData, ciborgDb, config)
server.run()

