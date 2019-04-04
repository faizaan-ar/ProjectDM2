package GameObj


import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint._
import GameObj._

class Bullet(var position: List[Double], var vector: List[Double], var tank: Tank) {
}
