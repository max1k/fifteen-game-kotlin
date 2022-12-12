package ru.mxk.game.fifteenkotlin.util

import org.junit.jupiter.api.Assertions
import org.junit.jupiter.api.Test

private const val CAMEL_CASE_STRING = "SomeCamelCaseText"
private const val SNAKE_CASE_STRING = "some_snake_case_text"

internal class StringUtilKtTest {

    @Test
    fun camelToDashedCase() {
        val dashedCaseString = CAMEL_CASE_STRING.camelToDashedCase()

        Assertions.assertEquals("some-camel-case-text", dashedCaseString)
    }

    @Test
    fun camelToSnakeCase() {
        val dashedCaseString = CAMEL_CASE_STRING.camelToSnakeCase()

        Assertions.assertEquals("some_camel_case_text", dashedCaseString)
    }

    @Test
    fun snakeToUpperCamelCase() {
        val dashedCaseString = SNAKE_CASE_STRING.snakeToUpperCamelCase()

        Assertions.assertEquals("SomeSnakeCaseText", dashedCaseString)
    }

    @Test
    fun snakeToLowerCamelCase() {
        val dashedCaseString = SNAKE_CASE_STRING.snakeToLowerCamelCase()

        Assertions.assertEquals("someSnakeCaseText", dashedCaseString)
    }
}