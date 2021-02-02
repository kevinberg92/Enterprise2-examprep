package org.example.usercollections.dto

import io.swagger.annotations.ApiModelProperty
import javax.persistence.GeneratedValue
import javax.persistence.Id
import javax.persistence.ManyToOne
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

data class CardCopyDto(

        @get:ApiModelProperty("Id of the card")
        var cardId: String? = null,

        @get:ApiModelProperty("Number of copies of the card that the user owns")
        var numberOfCopies: Int? = null
)
