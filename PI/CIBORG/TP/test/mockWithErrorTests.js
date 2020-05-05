// Arrange
const config = require('../config.json')
const assert = require('assert')

const errorBoardGamesDataMocks = "../mock/board-games-data-errors-mock"
const errorCiborgDbMocks = "../mock/ciborg-db-errors-mock"


const boardGamesData = require(errorBoardGamesDataMocks)()
const ciborgDb = require(errorCiborgDbMocks)()
const ciborgService = require("../ciborg-service")(boardGamesData, ciborgDb)



//Act && Assert

function processSucess(err) {
    assert.fail("Should not have thrown error : " + err.name)
}

describe('CiborgService Tests with Unsucessful returns', function () {

    describe('#getSpecificGameWithName(name)', function () {
        it('Should throw not found error ', function () {
            ciborgService.getSpecificGameWithName("xpto")
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })

    describe('#editGroupgroup(Id, name, description, game)', function () {
        it('Should throw not found error ', function () {
            ciborgService.editGroup("1", "Group 1", "Group 1 description", null)
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })

    describe('#getGroupDetail(groupId)', function () {
        it('Should throw not found error ', function () {
            ciborgService.getGroupDetail("1")
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })

    describe('#putGameInGroup(groupId, name, description, games)', function () {
        it('Should throw not found error ', function () {
            ciborgService.putGameInGroup("1", "Group 1", "Group 1 description", null)
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })

    describe('#deleteGameInGroup(groupId, name, description, games)', function () {
        it('Should throw not found error ', function () {
            ciborgService.deleteGameInGroup("1", "Group 1", "Group 1 description", null)
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })

    describe('#deleteGroup(groupId)', function () {
        it('Should throw not found error ', function () {
            ciborgService.deleteGroup("1")
                .then(processSucess)
                .catch(processError)

            function processError(result) {
                assert.equal(result.error, 'Resource not found')
                assert.equal(result.statusCode, 404)
            }

        })
    })


})