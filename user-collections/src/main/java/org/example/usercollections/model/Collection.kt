package org.example.usercollections.model

import org.example.cards.dto.CollectionDto
import org.example.cards.dto.Rarity
import java.lang.IllegalArgumentException
import java.lang.Math.abs

data class Collection(

        val cards: List<Card>,

        val prices: Map<Rarity, Int>,

        val millValues: Map<Rarity, Int>,

        val rarityProbabilities: Map<Rarity, Double>

) {

    constructor(dto: CollectionDto) : this(
            dto.cards.map { Card(it) }, //.map for MutableList, and toMap for MutableMap
            dto.prices.toMap(),
            dto.millValues.toMap(),
            dto.rarityProbabilities.toMap()

    )

    val cardsByRarity: Map<Rarity, List<Card>> = cards.groupBy { it.rarity }

    init {
        if (cards.isEmpty()) {
            throw IllegalArgumentException("No cards")
        }
        Rarity.values().forEach {
            requireNotNull(prices[it])
            requireNotNull(millValues[it])
            requireNotNull(rarityProbabilities[it])
        }

        val p = rarityProbabilities.values.sum()
        if (abs(1 - p) > 0.00001) {
            throw IllegalArgumentException("Invalid prob sum: $p")
        }
    }
}

