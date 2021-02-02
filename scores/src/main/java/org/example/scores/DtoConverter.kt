package org.example.scores

import org.example.scores.db.UserStats
import org.example.scores.dto.UserStatsDto


object DtoConverter {

    fun transform(stats: UserStats) : UserStatsDto =
            stats.run { UserStatsDto(userId, victories, defeats, draws, score)}

    fun transform(scores: Iterable<UserStats>) : List<UserStatsDto> = scores.map { transform(it) }
}