
module.exports = function () {

  return {
    getPopularGames,
    getSpecificGameWithName,
    getSpecificGames
  }


  function getPopularGames() {
    return Promise.resolve(JSON.parse(`{
      "games": [ 
        {
          "id": "kPDxpJZ8PD",
          "name": "Spirit Island",
          "image_url": "https://s3-us-west-1.amazonaws.com/5cc.images/games/uploaded/1559254941010-61PJxjjnbfL.jpg",
          "max_playtime": 120,
          "description": "Powerful Spirits have existed on this isolated island for time immemorial. They are both part of the natural world and - at the same time - something beyond nature. Native Islanders, known as the Dahan, have learned how to co-exist with the spirits, but with a healthy dose of fear and reverence. However, now, the island has been &quot;discovered&quot; by invaders from a far-off land. These would-be colonists are taking over the land and upsetting the natural balance, destroying the presence of Spirits as they go. As Spirits, you must grow in power and work together to drive the invaders from your island... before it's too late!"
        },
        {
          "id": "RLlDWHh7hR",
          "name": "Gloomhaven",
          "image_url": "https://s3-us-west-1.amazonaws.com/5cc.images/games/uploaded/1559254920151-51ulRXlJ7LL.jpg",
          "max_playtime": 150,
          "description": "Gloomhaven is a game of Euro-inspired tactical combat in a persistent world of shifting motives. Players will take on the role of a wandering adventurer with their own special set of skills and their own reasons for traveling to this dark corner of the world."
        }
      ]
  }`))
  }


  function getSpecificGameWithName(name) {
    return Promise.resolve(JSON.parse(`{
     "game" : {
      "id": "kPDxpJZ8PD",
      "name": "Spirit Island",
      "image_url": "https://s3-us-west-1.amazonaws.com/5cc.images/games/uploaded/1559254941010-61PJxjjnbfL.jpg",
    "max_playtime": 120,
    "description": "Powerful Spirits have existed on this isolated island for time immemorial. They are both part of the natural world and - at the same time - something beyond nature. Native Islanders, known as the Dahan, have learned how to co-exist with the spirits, but with a healthy dose of fear and reverence. However, now, the island has been &quot;discovered&quot; by invaders from a far-off land. These would-be colonists are taking over the land and upsetting the natural balance, destroying the presence of Spirits as they go. As Spirits, you must grow in power and work together to drive the invaders from your island... before it's too late!"
  }}`))
  }

  function getSpecificGames(ids) {
    return Promise.resolve(JSON.parse(
      `[
        {
            "id": "kPDxpJZ8PD",
            "name": "Spirit Island",
            "image_url": "https://s3-us-west-1.amazonaws.com/5cc.images/games/uploaded/1559254941010-61PJxjjnbfL.jpg",
            "max_playtime": 120,
            "description": "Powerful Spirits have existed on this isolated island for time immemorial. They are both part of the natural world and - at the same time - something beyond nature. Native Islanders, known as the Dahan, have learned how to co-exist with the spirits, but with a healthy dose of fear and reverence. However, now, the island has been &quot;discovered&quot; by invaders from a far-off land. These would-be colonists are taking over the land and upsetting the natural balance, destroying the presence of Spirits as they go. As Spirits, you must grow in power and work together to drive the invaders from your island... before it's too late!"
        }
      ]`
    ))
  }

}