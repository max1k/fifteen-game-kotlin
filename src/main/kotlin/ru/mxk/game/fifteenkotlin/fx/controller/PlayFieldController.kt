package ru.mxk.game.fifteenkotlin.fx.controller

import javafx.event.ActionEvent
import javafx.fxml.FXML
import javafx.scene.control.Alert
import javafx.scene.layout.Pane
import org.springframework.beans.factory.annotation.Autowired
import ru.mxk.game.fifteenkotlin.configuration.annotation.FxController
import ru.mxk.game.fifteenkotlin.fx.component.ValueButton
import ru.mxk.game.fifteenkotlin.fx.controller.base.BaseFxController
import ru.mxk.game.fifteenkotlin.service.GameService
import kotlin.math.floor


@FxController
class PlayFieldController: BaseFxController() {
    @Autowired
    lateinit var gameService: GameService

    lateinit var playField: Pane

    @FXML
    fun initialize() {
        playField.heightProperty().addListener { _ -> refreshField() }
        playField.widthProperty().addListener { _ -> refreshField() }
    }

    private fun refreshField() {
        playField.children.clear()

        gameService.mapCells(this::createButton)
            .forEach(playField.children::add)
    }

    private fun createButton(value: Int): ValueButton {
        val button = ValueButton(value)
        button.setOnAction(this::handleButtonClick)
        button.prefWidth = floor(playField.width / gameService.size) - 1
        button.prefHeight = floor(playField.height / gameService.size) - 1

        val cellIsEmpty = gameService.isEmptyCell(gameService.getIndexByValue(value))
        button.isVisible = !cellIsEmpty

        return button
    }

    private fun handleButtonClick(actionEvent: ActionEvent) {
        require(actionEvent.source is ValueButton) { "Unexpected object provided: " + actionEvent.source }

        val button = actionEvent.source as ValueButton
        val stateIsChanged = gameService.trySwap(button.value)

        if (stateIsChanged) {
            refreshField()

            if (gameService.isWon) {
                showWinMessage()
            }
        }
    }

    private fun showWinMessage() {
        val alert = Alert(Alert.AlertType.INFORMATION)
        alert.title = "Game results"
        alert.headerText = "Congratulations!"
        alert.contentText = "You won!"
        alert.showAndWait()
    }

}