package org.example.usercollections

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import io.swagger.models.Response
import org.example.usercollections.DtoConverter
import org.example.usercollections.db.UserService
import org.example.usercollections.dto.Command
import org.example.usercollections.dto.PatchResultDto
import org.example.usercollections.dto.PatchUserDto
import org.example.usercollections.dto.UserDto
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.*
import java.awt.PageAttributes
import java.lang.IllegalArgumentException


@Api(value = "/api/user-collections", description = "operations on card-collections owned by users")
@RequestMapping(
path =["api/user-collections"],
        produces = [(MediaType.APPLICATION_JSON_VALUE)]
)

@RestController
class RestAPI(
        private val userService: UserService
) {

    //describe the endpoint
    @ApiOperation("Retrieve card collection information for a specific user")
    //method + mapping
    @GetMapping(path=["/{userId}"])
    fun getUserInfo(
            @PathVariable("userId") userId: String
    ) : ResponseEntity<UserDto> {

        val user = userService.findByIdEager(userId)
        if(user == null) {
            return ResponseEntity.notFound().build()
        }

        return ResponseEntity.status(200).body(DtoConverter.DtoConverter.transform(user))
    }

    @ApiOperation("Create a new user, with the given id")
    @PutMapping(path=["/{userId"])
    fun createUser(
            @PathVariable("userId") userId: String
    ) : ResponseEntity<Void>{

        val ok = userService.registerNewUser(userId)
        return if(!ok) ResponseEntity.status(400).build()
        else ResponseEntity.status(201).build()
    }


    //produces and consumes after path...??, only produces in toplevel declaration after @Api
    @ApiOperation("Execute a command on a users collection")
    @PatchMapping(
            path=["/{userId"],
            consumes = [(MediaType.APPLICATION_JSON_VALUE)]
    )
    fun patchUser(
            @PathVariable("userId") userId: String,
            @RequestBody dto: PatchUserDto
    ) : ResponseEntity<PatchResultDto>/*what the entity shall return*/{

        if(dto.command == null) {
            return ResponseEntity.status(400).build()
        }

        if(dto.command == Command.OPEN_PACK) {
            val ids = try {
                userService.openPack(userId)
            } catch(e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto().apply { cardIdsInOpenedPack.addAll(ids) })
        }

        val cardId = dto.cardId
                ?: return ResponseEntity.status(400).build()

        if(dto.command == Command.BUY_CARD) {
            try{
                userService.buyCard(userId,cardId)
            } catch (e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto())
        }

        if(dto.command == Command.MILL_CARD) {
            try {
                userService.millCard(userId, cardId)
            } catch(e: IllegalArgumentException) {
                return ResponseEntity.status(400).build()
            }
            return ResponseEntity.status(200).body(PatchResultDto())
        }

        return ResponseEntity.status(400).build()

    }

}