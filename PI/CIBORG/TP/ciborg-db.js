const responseMapper = require('./internal-errors')
const REFRESH_TIME = 1000


module.exports = function (config) {
	const groupsRefreshState = {
		changed: false,
		timeConst: 0,
		index: "/groups"
	}

	const usersRefreshState = {
		changed: false,
		timeConst: 0,
		index: "/users",
	}

	function updateIndex(state) {
		if (!state.changed) {
			return Promise.resolve()
		}
		const time = Date.now()
		const timeDifference = time - state.timeConst
		if (timeDifference > 0 && timeDifference <= REFRESH_TIME) {
			const options = requestPromise.buildOptions(state.index + "/_refresh", "POST")
			return requestPromise.buildRequestPromise(options)

		} else {
			state.changed = false;
			state.timeConst = 0;
			return Promise.resolve()
		}
	}

	function setChanged(state) {
		state.changed = true
		state.timeConst = Date.now()
		return Promise.resolve()
	}

	function refresh(state, method) {
		return mapToRefresh[method](state)
	}

	const mapToRefresh = {
		"GET": updateIndex,
		"POST": setChanged,
		"PUT": setChanged,
		"DELETE": setChanged
	}

	const requestPromise = require('./request-promise')(config)
	const getSize = config['get_size']
	const groupIndex = config.groupsIndex
	const usersIndex = config.usersIndex

	init()

	function init() {
		createIndex(groupIndex)
			.then(() => createIndex(usersIndex))

		function createIndex(index) {
			const IndexOptions = requestPromise.buildOptions(index)
			//check if groups index exists
			return requestPromise.buildRequestPromise(IndexOptions)
				.then(result => {
					// create groups index if it doesn´t exists
					if (result.response.statusCode == 404) {
						const createIndexOptions = requestPromise.buildOptions(index, 'PUT')
						requestPromise.buildRequestPromise(createIndexOptions)
							.then(r => console.log(`INIT - Created ${index} Index ` + JSON.parse(r.body).index))
					}
				})
				.catch(() => {
					//responseMapper.mapResponse(500)
					console.error("Server Cannot Start")
					process.exit()
				})
		}
	}


	return {
		postGroups,
		getAllGroups,
		getGroupDetail,
		editGroup,
		putGameInGroup,
		deleteGameInGroup,
		deleteGroup,
		createUser,
		getUser,
		userExists,
		validateLogin,
		editUser,
		deleteUser
	}

	function validateLogin(username, password) {
		return refresh(usersRefreshState, "GET").then(() => getUser(username)
			.then(user => {
				return passwordsMatch(password, user.password)
			})
			.catch(err => false)
		)
	}

	function passwordsMatch(inputPassword, password) {
		return inputPassword == password
	}

	function userExists(username) {
		return refresh(usersRefreshState, "GET").then(() => getUser(username)
			.then(() => true)
			.catch((err) => {
				if (err.name == "NoSuchResource") {
					return false;
				}
				throw err;
			}))
	}

	function getUser(username) {
		const body = { username }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(usersRefreshState, "GET").then(() => get("/users/_doc/" + username, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					const parsedBody = JSON.parse(result.body)
					const user = {}
					const values = parsedBody["_source"]
					Object.keys(values).map(key => user[key] = values[key])
					return user
				}, 200, "User")
		)
		)
	}



	function createUser(username, password) {
		const body = { username, password, groups: [] }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(usersRefreshState, "POST").then(() => post("/users/_create/" + username, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "User Created"
					}
				}, 201)
		))
	}

	function editUser(username, password, groups) {
		const body = { username, password, groups }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(usersRefreshState, "PUT").then(() => put("/users/_doc/" + username, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "User Edited"
					}
				}, 200, "User")
		))
	}

	function deleteUser(username) {
		const body = {}
		const headers = { 'Content-Type': 'application/json' }
		return refresh(usersRefreshState, "DELETE").then(() => del("/users/_doc/" + username, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "User Deleted"
					}
				}, 200, "User")
		))
	}

	function postGroups(name, description) {
		const body = { name, description, games: [] }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(groupsRefreshState, "POST").then(() => post("/groups/_doc/", headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "group Created",
						id: JSON.parse(result.body)["_id"]
					}
				}, 201)
		)
		)
	}

	function editGroup(groupId, name, description, games) {
		const body = { name, description, games }
		const headers = { 'Content-Type': 'application/json' }

		return refresh(groupsRefreshState, "PUT").then(() => put("/groups/_doc/" + groupId, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "group edited"
					}
				}, 200, "Group")
		))
	}

	function getAllGroups(groups) {
		const body = {
			query: {
				ids: {
					values: groups
				}
			}
		}
		const headers = {
			'Content-Type': 'application/json'
		};

		return refresh(groupsRefreshState, "GET").then(() => get(`/_search`, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					const parsedBody = JSON.parse(result.body)
					const groups = parsedBody.hits.hits.map(dto => {
						const toReturn = {
							id: dto["_id"]
						}
						const values = dto["_source"]
						Object.keys(values).map(key => toReturn[key] = values[key])
						return toReturn
					})
					return { groups }
				},200,"Groups"
			))
		)
	}

	function getGroupDetail(groupId) {
		const body = { name: groupId }
		const headers = {
			'Content-Type': 'application/json'
		};
		return refresh(groupsRefreshState, "GET").then(() => get("/groups/_doc/" + groupId, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					const parsedBody = JSON.parse(result.body)
					const group = {
						id: parsedBody["_id"]
					}
					const values = parsedBody["_source"]
					Object.keys(values).map(key => group[key] = values[key])
					return group
				}, 200, "Group")

		))

	}

	function putGameInGroup(groupId, name, description, games) {
		const body = { name, description, games }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(groupsRefreshState, "PUT").then(() => put("/groups/_doc/" + groupId, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "Game Added"
					}
				}, 200, "Group")
		)
		)

	}

	function deleteGameInGroup(groupId, name, description, games) {
		const body = { name, description, games }
		const headers = { 'Content-Type': 'application/json' }
		return refresh(groupsRefreshState, "PUT").then(() => put("/groups/_doc/" + groupId, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "Game Deleted"
					}
				}, 200, "Group")
		)
		)
	}

	function deleteGroup(groupId) {
		const body = {}
		const headers = { 'Content-Type': 'application/json' }
		return refresh(groupsRefreshState, "DELETE").then(() => del("/groups/_doc/" + groupId, headers, body).then(
			result => responseMapper.mapResponse(result.response.statusCode,
				() => {
					return {
						status: "Group Deleted"
					}
				}, 200, "Group")
		))
	}
	function get(URL, headers, body) {
		const options = requestPromise.buildOptions(URL, 'GET', headers, body)
		return requestPromise.buildRequestPromise(options)
	}
	function post(URL, headers, body) {
		const options = requestPromise.buildOptions(URL, 'POST', headers, body)
		return requestPromise.buildRequestPromise(options)
	}
	function put(URL, headers, body) {
		const options = requestPromise.buildOptions(URL, 'PUT', headers, body)
		return requestPromise.buildRequestPromise(options)
	}
	function del(URL, headers, body) {
		const options = requestPromise.buildOptions(URL, 'DELETE', headers, body)
		return requestPromise.buildRequestPromise(options)
	}
}




