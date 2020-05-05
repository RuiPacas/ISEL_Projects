const config = require("./config.json")
const boardGamesData = require("./board-games-data")(config.api)
const ciborgDb = require("./ciborg-db")(config.db)

console.log("Production Server")

const server = require("./ciborg-server")(boardGamesData, ciborgDb, config)
server.run()



