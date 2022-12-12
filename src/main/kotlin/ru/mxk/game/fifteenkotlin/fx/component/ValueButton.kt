package ru.mxk.game.fifteenkotlin.fx.component

import javafx.scene.control.Button

data class ValueButton(val value: Int): Button(value.toString())