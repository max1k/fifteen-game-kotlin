package ru.mxk.game.fifteenkotlin.configuration.annotation

import org.springframework.stereotype.Component

@Target(AnnotationTarget.CLASS)
@Retention(AnnotationRetention.RUNTIME)
@Component
annotation class FxController(val value: String = "")
