package org.example.usercollections.dto

import io.swagger.annotations.ApiModelProperty

enum class Command {

    OPEN_PACK,
    MILL_CARD,
    BUY_CARD
}

data class PatchUserDto(

        @get:ApiModelProperty("Command to execute on a users collection")
        var command: Command? = null,

        @get:ApiModelProperty("Optional card id, if the command required one")
        var cardId: String? = null
)