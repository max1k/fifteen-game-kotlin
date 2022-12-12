package ru.mxk.game.fifteenkotlin.service

import org.springframework.stereotype.Service
import ru.mxk.game.fifteenkotlin.options.GameOptions

@Service
class GameService(gameOptions: GameOptions) {
    val size = gameOptions.size

    private val cellsCount: Int
        get() = size * size

    private val cells = Array(cellsCount) { index -> index + 1 }

    private val valueIndexMap: MutableMap<Int, Int> = mutableMapOf()

    val isWon: Boolean
        get() {
            for ((index, value) in cells.withIndex()) {
                if (value != index + 1) {
                    return false
                }
            }
            return true
        }

    init {
        reset()
        shuffle()
    }

    final fun reset() {
        for (index in 0 until cellsCount) {
            val cellValue = index + 1
            cells[index] = cellValue
            valueIndexMap[cellValue] = index
        }
    }

    private fun shuffle() {
        repeat(size * size * size) {
            val emptyCellIndex = getIndexByValue(cellsCount)
            val neighborCellsIndex = getNeighborCellsIndexes(emptyCellIndex)

            trySwap(cells[neighborCellsIndex.random()])
        }
    }

    fun getIndexByValue(value: Int) =
        valueIndexMap[value] ?: throw IllegalStateException("Can't find cell index by value $value")

    private fun getNeighborCellsIndexes(cellIndex: Int) =
        listOf(cellIndex - 1, cellIndex + 1, cellIndex - size, cellIndex + size)
            .filter { it >= 0 }
            .filter { it < cellsCount }

    fun isEmptyCell(it: Int) = cells[it] == cellsCount

    fun trySwap(cellValue: Int): Boolean {
        val cellIndex = getIndexByValue(cellValue)
        val emptyCellIndex = getNeighborCellsIndexes(cellIndex)
            .find { isEmptyCell(it) }
            ?: return false

        swap(cellIndex, emptyCellIndex)
        return true
    }

    private fun swap(firstIndex: Int, secondIndex: Int) {
        val firstValue = cells[firstIndex]
        cells[firstIndex] = cells[secondIndex]
        cells[secondIndex] = firstValue

        valueIndexMap[cells[firstIndex]] = firstIndex
        valueIndexMap[cells[secondIndex]] = secondIndex
    }

    fun <T> mapCells(mapper: (Int) -> T): List<T> = cells.map(mapper)

}