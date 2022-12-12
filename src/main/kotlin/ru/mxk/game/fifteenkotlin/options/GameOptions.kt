package ru.mxk.game.fifteenkotlin.options

import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.boot.context.properties.ConstructorBinding
import org.springframework.validation.annotation.Validated
import javax.validation.constraints.Max
import javax.validation.constraints.Min

@Validated
@ConstructorBinding
@ConfigurationProperties(prefix = "game")
data class GameOptions(
    @Min(2)
    @Max(30)
    val size: Int
)