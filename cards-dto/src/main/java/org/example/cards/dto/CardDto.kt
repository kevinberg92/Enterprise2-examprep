package org.example.cards.dto

import io.swagger.annotations.ApiModelProperty

//DTO's with () and not {}

class CardDto(

        @get:ApiModelProperty("The id of the card")
        var cardId : String? = null,

        @get:ApiModelProperty("The name of this card")
        var name : String? = null,

        @get:ApiModelProperty("A description of the card effects")
        var description: String? = null,

        @get:ApiModelProperty("The rarity of the card")
        var rarity: Rarity? = null,

        @get:ApiModelProperty("The id of the image associated with this card")
        var imageId: String? = null
)