// Arrange
const config = require('../config.json')
const assert = require('assert')

const successBoardGamesDataMocks = "../mock/board-games-data-mock"
const successCiborgDbMocks = "../mock/ciborg-db-mock"


const boardGamesData = require(successBoardGamesDataMocks)()
const ciborgDb = require(successCiborgDbMocks)()
const ciborgService = require("../ciborg-service")(boardGamesData, ciborgDb)

const username = "testUser"

//Act && Assert

function processError(err) {
    assert.fail("Should not have thrown error : " + err.name)
}

describe('Ciborg-Service Tests with Sucessfull returns', function () {

    describe('#getPopularGames()', function () {
        it('Should get only 2 games, Spirit Island and Gloomhaven ', function () {
            ciborgService.getPopularGames()
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                const games = result.games
                assert.equal(games.length, 2)
                const spiritIsland = games[0]
                const gloomhaven = games[1]
                assert.equal(spiritIsland.id, 'kPDxpJZ8PD')
                assert.equal(gloomhaven.id, 'RLlDWHh7hR')
            }

        })
    })

    describe('#getSpecificGameWithName(name)', function () {
        it('Should get only 1 game, Spirit Island', function () {
            const name = "Spirit Island" // could be any ID
            ciborgService.getSpecificGameWithName(name)
                .then(processSucess)
                .catch(processError)

            function processSucess(game) {
                assert.equal(game.game.id, 'kPDxpJZ8PD')
                assert.equal(game.game.name, 'Spirit Island')
            }
        })
    })

    describe('#postGroups(name,description)', function () {
        it('Should return object with group created status', function () {
            const name = "name" // could be any name
            const description = "description" // could be any description
            ciborgService.postGroups(name, description,username)
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                assert.equal(result.status, "user edited")
                assert.equal(result.uri, `/ciborg/api/users/${username}`)
            }
        })
    })

    describe('#editGroup(groupId, name, description)', function () {
        it('Should return object with group edited status', function () {
            const groupId = 1
            const name = "name"
            const description = "description"

            ciborgService.editGroup(groupId, name, description,username)
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                assert.equal(result.status, "group edited")
                assert.equal(result.uri, "/ciborg/api/groups/FzxfXG4BjRxSEc-UWXCP")
            }
        })
    })

    describe('#getAllGroups()', function () {
        it('Should return object with all groups', function () {
            
            ciborgService.getAllGroups(username)
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                const groups = result.groups
                assert.equal(groups.length, 1)
                const group = groups[0]
                assert.equal(group.id, 'FzxfXG4BjRxSEc-UWXCP')
                assert.equal(group.description, 'description of group 1')
            }
        })
    })

    describe('#getGroupDetail(groupId)', function () {
        it('Should return object with a group, and it`s details, like it`s games', function () {
            const groupId = 1

            ciborgService.getGroupDetail(groupId,username)
                .then(processSucess)
                .catch(processError)

            function processSucess(group) {
                const groupGames = group.games
                assert.equal(groupGames.length, 1)
                assert.equal(group.id, groupId)
                assert.equal(group.description, 'description of group 1')
            }
        })
    })

    describe('#putGameInGroup(groupId, gameId)', function () {
        it('Should return object with game added status in a group', function () {
            const groupId = 1
            const gameId = "kPDxpJZ8PD"

            ciborgService.putGameInGroup(groupId, gameId,username)
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                assert.equal(result.status, "Game Added")
                assert.equal(result.uri, `/ciborg/api/groups/${groupId}`)
            }
        })
    })

    describe('#deleteGameInGroup(groupId, gameId)', function () {
        it('Should return object with game removed status in a group', function () {
            const groupId = 1
            const gameId = "kPDxpJZ8PD"

            ciborgService.deleteGameInGroup(groupId, gameId,username)
                .then(processSucess)
                .catch(processError)

            function processSucess(result) {
                assert.equal(result.status, "Game Deleted")
                assert.equal(result.uri, "/ciborg/api/groups/FzxfXG4BjRxSEc-UWXCP")
            }
        })
    })
})