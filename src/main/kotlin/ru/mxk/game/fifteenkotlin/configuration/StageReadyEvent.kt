package ru.mxk.game.fifteenkotlin.configuration

import javafx.stage.Stage
import org.springframework.context.ApplicationEvent

data class StageReadyEvent(val stage: Stage) : ApplicationEvent(stage)