
module.exports = function(){

    return {
        getSpecificGameWithName,
    }

    function getSpecificGameWithName(id){
        return Promise.reject({
            error : `Resource not found`,
            uri : `/ciborg/api/resourcenotfound`,
            statusCode : 404
        })
    }
}