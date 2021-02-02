package org.example.scores

import org.example.scores.db.UserStats
import org.springframework.data.repository.CrudRepository
import org.springframework.stereotype.Repository
import org.springframework.stereotype.Service
import java.lang.IllegalArgumentException
import javax.persistence.EntityManager
import javax.persistence.TypedQuery
import javax.transaction.Transactional


//@Transactional @Service, needs to have a @Repository top level declaration
@Repository
interface UserStatsRepository : CrudRepository<UserStats, String>

@Service
@Transactional
class UserStatsService(
        val repository: UserStatsRepository,
        val em: EntityManager
) {

    fun registerNewUser(userId: String): Boolean {

        if (repository.existsById(userId)) {
            return false
        }

        val stats = UserStats(userId, 0, 0, 0, 0)
        repository.save(stats)
        return true
    }

    fun getNextPage(size: Int, keysetId: String? = null, keysetScore: Int? = null): List<UserStats> {

        if (size < 1 || size > 1000) {
            throw IllegalArgumentException("Invalid size value: $size")
        }

        if ((keysetId == null && keysetScore != null) || (keysetId != null && keysetScore == null)) {
            throw IllegalArgumentException("keysetid and keyscore should beb oth missing or both present")
        }

        val query: TypedQuery<UserStats>
        if (keysetId == null) {
            query = em.createQuery(
                    "select s from UserStats s order by s.score DESC, s.userId DESC", UserStats::class.java)

        } else {
            query = em.createQuery(
                    "select s from UserStats s where s.score<?2 or (s.score=?2 and s.userId<?1) order by s.score DESC, s.userId DESC",
                    UserStats::class.java)

        }
        query.maxResults = size
        return query.resultList
    }
}