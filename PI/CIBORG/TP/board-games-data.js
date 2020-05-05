const responseMapper = require('./internal-errors')

function mapGame(game) {
  return {
    id: game.id,
    name: game.name,
    image_url: game['image_url'],
    max_playtime: game['max_playtime'],
    description: game.description
  }
}

function mapGames(games) {
  return games.map(mapGame)
}


module.exports = function (config) {
  const requestPromise = require('./request-promise')(config)
  const BGA_CLIENT_ID = config.CLIENT_ID
  const BGA_HOST = config.HOST
  const BGA_SEARCH = `${BGA_HOST}search?client_id=${BGA_CLIENT_ID}`
  const BGA_SEARCH_GAME_ID = `${BGA_SEARCH}&ids=%s`
  const BGA_SEARCH_GAME_NAME = `${BGA_SEARCH}&name=%s`
  const BGA_POPULAR_GAMES = `${BGA_SEARCH}&order_by=popularity`

  return {
    getPopularGames,
    getSpecificGameWithName,
    getSpecificGame,
    getSpecificGames
  }

  function getSpecificGame(id) {
    const options = requestPromise.buildOptions(BGA_SEARCH_GAME_ID.replace("%s", id), 'GET')
    return requestPromise.buildRequestPromise(options)
      .then(result => {
        const parsedBody = JSON.parse(result.body)
        if (!parsedBody.games) result.response.statusCode = 404
        return responseMapper.mapResponse(result.response.statusCode,
          () => {
            return { game: mapGame(parsedBody.games[0]) }
          }, 200, "Game")
      }
      )
  }

  function getPopularGames() {
    const options = requestPromise.buildOptions(BGA_POPULAR_GAMES, 'GET')
    return requestPromise.buildRequestPromise(options).then(
      result => responseMapper.mapResponse(result.response.statusCode,
        () => {
          const parsedBody = JSON.parse(result.body)
          const games = parsedBody.games.map(mapGame)
          return { games }
        })
    )
  }

  function getSpecificGameWithName(name) {
    const options = requestPromise.buildOptions(BGA_SEARCH_GAME_NAME.replace("%s", name), 'GET')
    return requestPromise.buildRequestPromise(options)
      .then(result => {
        const parsedBody = JSON.parse(result.body)
        if (!parsedBody.games) result.response.statusCode = 404
        return responseMapper.mapResponse(result.response.statusCode,
          () => {
            return { games: mapGames(parsedBody.games) }
          }, 200, "Game")
      })
  }

  function getSpecificGames(ids) {
    ids = ids.reduce((accumulator, id2) => accumulator + id2 + ",", "")
    const options = requestPromise.buildOptions(BGA_SEARCH_GAME_ID.replace("%s", ids), 'GET')
    return requestPromise.buildRequestPromise(options).then(
      result => {
        const parsedBody = JSON.parse(result.body)
        return responseMapper.mapResponse(result.response.statusCode, () => {
          const games = parsedBody.games.map(mapGame)
          return games
        })
      }
    )
  }
}