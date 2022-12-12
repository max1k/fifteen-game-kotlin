package ru.mxk.game.fifteenkotlin

import javafx.application.Application
import javafx.application.Platform
import javafx.scene.Scene
import javafx.stage.Stage
import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.builder.SpringApplicationBuilder
import org.springframework.boot.context.properties.ConfigurationPropertiesScan
import org.springframework.context.ConfigurableApplicationContext
import ru.mxk.game.fifteenkotlin.configuration.StageReadyEvent
import ru.mxk.game.fifteenkotlin.configuration.component.FxControllerLoader
import ru.mxk.game.fifteenkotlin.fx.controller.PlayFieldController

@SpringBootApplication
@ConfigurationPropertiesScan
class FxApplication : Application() {
    private lateinit var applicationContext : ConfigurableApplicationContext

    override fun init() {
        applicationContext = SpringApplicationBuilder(FxApplication::class.java).run()
    }

    override fun stop() {
        applicationContext.close()
        Platform.exit()
    }

    override fun start(stage: Stage) {
        applicationContext.publishEvent(StageReadyEvent(stage))

        val fxControllerLoader = applicationContext.getBean(FxControllerLoader::class.java)
        val playFieldController = fxControllerLoader.loadController(PlayFieldController::class.java)

        stage.title = "Fifteen game"
        stage.scene = Scene(playFieldController.content)
        stage.show()
    }
}

fun main(args: Array<String>) {
    Application.launch(FxApplication::class.java, *args)
}
