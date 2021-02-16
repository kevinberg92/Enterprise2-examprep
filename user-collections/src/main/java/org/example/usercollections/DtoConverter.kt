package org.example.usercollections

import org.example.usercollections.db.CardCopy
import org.example.usercollections.db.User
import org.example.usercollections.dto.CardCopyDto
import org.example.usercollections.dto.UserDto

//in every module we create @Entity we need to convert them from JPA to DTO.
//need to transform every field from the DTO class(es).
class DtoConverter {
    object DtoConverter {


        fun transform(user: User): UserDto {

            return UserDto().apply {
                userId = user.userId
                coins = user.coins
                cardPacks = user.cardPacks
                ownedCards = user.ownedCards.map { transform(it) }.toMutableList()
            }
        }

        fun transform(cardCopy: CardCopy): CardCopyDto {
            return CardCopyDto().apply {
                cardId = cardCopy.cardId
                numberOfCopies = cardCopy.numberOfCopies
            }
        }
    }
}