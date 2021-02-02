package org.example.cards

import io.swagger.annotations.Api
import io.swagger.annotations.ApiOperation
import org.example.cards.dto.CollectionDto
import org.example.wrappedresponses.`rest-dto`.WrappedResponse
import org.springframework.http.CacheControl
import org.springframework.http.MediaType
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import java.net.URI
import java.util.concurrent.TimeUnit


@Api(value = "/api/cards", description = "Operation on the cards existing in the game")
@RequestMapping(path = ["/api/cards"])
@RestController
class RestApi {

    //this is kotlin's solution to STATIC from java
    companion object {
        const val LATEST = "v1_000"
    }


    @ApiOperation("Return info on all cards in the game")
    @GetMapping(
            path = ["/collection_$LATEST"],
            produces = [MediaType.APPLICATION_JSON_VALUE]
    )
    fun getLatest(): ResponseEntity<WrappedResponse<CollectionDto>> {

        val collection = CardCollection.get()

        return ResponseEntity
                .status(200)
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic())
                .body(WrappedResponse(200, collection).validated())

    }

    @ApiOperation("Return info on all cards in the game")
    @GetMapping(
            path = [
                "/collection_v0_001",
                "/collection_v0_002",
                "/collection_v0_003"
            ])

    fun getOld(): ResponseEntity<WrappedResponse<Void>>{

        return ResponseEntity.status(301)
                .location(URI.create("/api/cards/collection_$LATEST"))
                .build()
    }

    @ApiOperation("Return the image for the specified card")
    @GetMapping(
            path= ["/imgs/{imgId}"],
            produces= ["image/svg+xml"]
    )
    fun getImage(@PathVariable("imgId") imgId: String) : ResponseEntity<String>{

        val folder = when{
            imgId.run{ endsWith("-monster.svg") || endsWith("-cyclops.svg")
                    || endsWith("-dragon.svg") || endsWith("-snake.svg")}
            -> "/1236106-monsters"
            else -> return ResponseEntity.status(400).build()
        }

        val svg = javaClass.getResource("$folder/svg/$imgId")?.readText()
                ?: return ResponseEntity.notFound().build()

        return ResponseEntity
                .status(200)
                .cacheControl(CacheControl.maxAge(365, TimeUnit.DAYS).cachePublic())
                .body(svg)
    }
}