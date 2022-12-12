package ru.mxk.game.fifteenkotlin.configuration.component

import javafx.stage.Stage
import org.springframework.context.ApplicationListener
import org.springframework.context.support.GenericApplicationContext
import org.springframework.stereotype.Component
import ru.mxk.game.fifteenkotlin.configuration.StageReadyEvent

@Component
data class StageInitializer(private val context: GenericApplicationContext) : ApplicationListener<StageReadyEvent> {
    override fun onApplicationEvent(event: StageReadyEvent) {
        context.registerBean(Stage::class.java, event.stage)
    }
}