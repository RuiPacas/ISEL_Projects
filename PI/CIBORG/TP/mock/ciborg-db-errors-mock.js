
module.exports = function () {

    return{
		getGroupDetail,
		editGroup,
		putGameInGroup,
		deleteGameInGroup,
		deleteGroup,
		createUser,
		getUser,
		userExists,
		validateLogin
    }
    function validateLogin(username, password) {
		return false
	}

	function passwordsMatch(inputPassword, password) {
		return inputPassword == password
	}

	function userExists(username) {
		return false;
	}

	function getUser(username) {
		return notFoundError()
	}

	function createUser(username, password) {
		return notFoundError()
    }
    
    function editGroup(groupId, name, description, games){
        return notFoundError()
    }

    function getGroupDetail(groupId){
        return notFoundError()
    }

    function putGameInGroup(groupId, name, description, games){
        return notFoundError()
    }

    function deleteGameInGroup(groupId, name, description, games) {
        return notFoundError()		
    }
    
	function deleteGroup(groupId){
		return notFoundError()
    }
    
    function notFoundError(){
        return Promise.reject({
            error : `Resource not found`,
            uri : `/ciborg/api/resourcenotfound`,
            statusCode : 404
        })
    }

}