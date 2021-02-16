package org.example.usercollections.db

import javax.persistence.*
import javax.validation.constraints.Min
import javax.validation.constraints.NotBlank
import javax.validation.constraints.NotNull

//also in these entities we use () instead of {},  because getter and setters is not needed in kotlin
@Entity
class CardCopy(

        @get:Id
        @get:GeneratedValue
        var id: Long? = null,

        //many card copies for one user?.. or many users for one card copy????
        @get:ManyToOne
        @get:NotNull
        var user: User? = null,

        @get:NotBlank
        var cardId: String? = null,

        @get:Min(0)
        var numberOfCopies: Int = 0
)