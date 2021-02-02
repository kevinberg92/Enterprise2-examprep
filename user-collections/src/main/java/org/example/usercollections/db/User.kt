package org.example.usercollections.db

import javax.persistence.*
import javax.validation.constraints.Min


@Entity
@Table(name="user_data")
class User (

    @get:Id
    @get:GeneratedValue
    var userId: String? = null,

    @get:Min(0)
    var coins: Int = 0,

    @get:Min(0)
    var cardPacks: Int = 0,

    @get:OneToMany(mappedBy = "user", cascade = [(CascadeType.ALL)])
    var ownedCards: MutableList<CardCopy> = mutableListOf()
)