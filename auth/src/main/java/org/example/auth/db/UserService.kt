package org.example.auth.db

import org.springframework.data.repository.CrudRepository
import org.springframework.security.crypto.password.PasswordEncoder
import org.springframework.stereotype.Service
import org.springframework.transaction.annotation.Transactional


@Service
@Transactional
class UserService(
        //NB!! This is not the userrep from usercollections, but from the first import statement
        private val userCrud: UserRepository,
        private val passwordEncoder: PasswordEncoder
) {


    fun createUser(username: String, password: String, roles: Set<String> = setOf()): Boolean {

        try {
            val hash = passwordEncoder.encode(password)

            if (userCrud.existsById(username)) {
                return false
            }

            val user = User(username, hash, roles.map { "ROLE_$it" }.toSet())

            userCrud.save(user)

            return true
        } catch (e: Exception) {
            return false
        }
    }

}

interface UserRepository : CrudRepository<User, String>