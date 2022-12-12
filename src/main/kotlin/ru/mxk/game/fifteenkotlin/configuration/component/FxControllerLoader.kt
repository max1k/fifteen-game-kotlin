package ru.mxk.game.fifteenkotlin.configuration.component

import javafx.fxml.FXMLLoader
import javafx.scene.Parent
import org.springframework.context.ApplicationContext
import org.springframework.stereotype.Component
import ru.mxk.game.fifteenkotlin.configuration.annotation.FxController
import ru.mxk.game.fifteenkotlin.fx.controller.base.BaseFxController
import ru.mxk.game.fifteenkotlin.util.camelToDashedCase
import java.net.URL

@Component
class FxControllerLoader(private val applicationContext: ApplicationContext) {

    fun <T: BaseFxController> loadController(controllerClass: Class<T>): T {
        val fxmlLoader = FXMLLoader(getFxmlResource(controllerClass))
        val content: Parent = fxmlLoader.load()
        val controller: T = fxmlLoader.getController()
        applicationContext.autowireCapableBeanFactory.autowireBean(controller)

        controller.content = content
        return controller
    }

    private fun getFxmlResource(controllerClass: Class<out Any>): URL {
        val controllerAnnotation = controllerClass.getAnnotation(FxController::class.java)
            ?: throw IllegalArgumentException(
                "${controllerClass.name} should be annotated with @${FxController::class.java.simpleName}"
            )

        val fxmlFileName = controllerAnnotation.value.ifBlank { this.classToFxmlFileName(controllerClass) }

        return controllerClass.getResource(fxmlFileName) ?: throw IllegalArgumentException()
    }

    private fun classToFxmlFileName(controllerClass: Class<out Any>): String {
        return controllerClass.simpleName
            .replace("Controller", "")
            .camelToDashedCase()
            .plus("-view.fxml")
    }
}