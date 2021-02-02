package org.example.cards.dto
//DTO's with () and not {}
class CardDto (

    var cardId : String? = null,
    var name : String? = null,
    var description : String? = null,
    var rarity : Rarity? = null,
    var imageId : String? = null
)