package org.example.usercollections.model

import org.example.cards.dto.CardDto
import org.example.cards.dto.Rarity

data class Card(
    val cardId: String,
    val rarity: Rarity
){

    constructor(dto: CardDto): this(
            dto.cardId ?: throw IllegalArgumentException("Null cardId"),
            dto.rarity ?: throw IllegalArgumentException("Null rarity"))

}
