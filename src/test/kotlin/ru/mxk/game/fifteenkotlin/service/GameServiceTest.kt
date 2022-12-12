package ru.mxk.game.fifteenkotlin.service

import org.junit.jupiter.api.Assertions.*
import org.junit.jupiter.api.BeforeEach
import org.junit.jupiter.api.Test
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.boot.test.context.SpringBootTest
import java.util.stream.Collectors
import java.util.stream.IntStream

@SpringBootTest
internal class GameServiceTest {
    @Autowired
    private lateinit var gameplayService: GameService

    private val maxValue: Int
        get() {
            return gameplayService.size * gameplayService.size
        }

    private val lastIndex: Int
        get() {
            return maxValue - 1
        }

    @BeforeEach
    fun reset() {
        gameplayService.reset()
    }

    @Test
    fun testGetIndex() {
        val indexByLastCellValue: Int = gameplayService.getIndexByValue(maxValue) + 1

        assertEquals(maxValue, indexByLastCellValue)
        assertEquals(0, gameplayService.getIndexByValue(1))
    }


    @Test
    fun testIsEmpty() {
        assertTrue(gameplayService.isEmptyCell(lastIndex))
        assertFalse(gameplayService.isEmptyCell(0))
    }

    @Test
    fun testTrySwap() {
        gameplayService.trySwap(maxValue - gameplayService.size)

        assertTrue(gameplayService.isEmptyCell(lastIndex - gameplayService.size))
        assertFalse(gameplayService.isEmptyCell(lastIndex))
    }

    @Test
    fun testCheckWin() {
        assertTrue(gameplayService.isWon)

        gameplayService.trySwap(maxValue - gameplayService.size)
        assertFalse(gameplayService.isWon)
    }

    @Test
    fun testMapCells() {
        val expectedValues = (1..maxValue).toList()
        val actualValues: List<Int> = gameplayService.mapCells { it }

        assertEquals(expectedValues, actualValues)
    }
}