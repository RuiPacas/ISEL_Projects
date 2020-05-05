const request = require('request')
module.exports = function () {
	


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
		editUser
	}

	function validateLogin(username, password) {
		return Promise.resolve(true)
	}


	function userExists(username) {
		return Promise.resolve(
			{
				username,
				groups : [1] 
			});
	}

	function getUser(username) {
		return Promise.resolve({
			username,
			groups : [1] 
		})
	}

	function createUser(username, password) {
		return {
			username,
			groups : [1] 
		}
    }

	function postGroups(name, description) {
		return Promise.resolve( {
				status: "group Created",
				uri: "/ciborg/api/groups/FzxfXG4BjRxSEc-UWXCP",
				id:  "FzxfXG4BjRxSEc-UWXCP"
			})		
	}
	function editUser(username, password, groups) {
		return Promise.resolve({
						status: "user edited",
						uri: `/ciborg/api/users/${username}`
			}
		)
	}
	function editGroup(groupId, name, description, games) {
		return Promise.resolve(JSON.parse(
			`{
				"status": "group edited",
				"uri": "/ciborg/api/groups/FzxfXG4BjRxSEc-UWXCP"
			}`
		))
	}

	function getAllGroups(groups) {
		return Promise.resolve(JSON.parse(
			`{
				"groups": [
					{
						"id": "FzxfXG4BjRxSEc-UWXCP",
						"name": "group1",
						"description": "description of group 1",
						"games": [
							"kPDxpJZ8PD"
						]
					}
				]
			}`
		))
	}

	function getGroupDetail(groupId) {
		return Promise.resolve(JSON.parse(
			`{
				"id": "${groupId}",
				"name": "group1",
				"description": "description of group 1",
				"games": [
					"kPDxpJZ8PD"
				]
			}`
		))
	}

	function putGameInGroup(groupId, name, description, games) {
		return Promise.resolve(
				{
					status: "Game Added",
					uri: "/ciborg/api/groups/kPDxpJZ8PD"
				}
		)
				
	}

	function deleteGameInGroup(groupId, name, description, games) {
		return Promise.resolve(
			{
				"status": "Game Deleted",
				"uri": "/ciborg/api/groups/FzxfXG4BjRxSEc-UWXCP"
			}
		)
				
	}

	function deleteGroup(groupId){
		return Promise.resolve(JSON.parse(
			`{
				"status": "Group Deleted",
				"uri": "/ciborg/api/groups/${groupId}"
			}`
		))
	}
	
}




