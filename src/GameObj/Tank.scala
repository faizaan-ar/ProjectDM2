package GameObj

import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint._


class Tank(var health: Double, var attack: Double, var defense: Double, var position: List[Double] = List(0,0)) {

  var numOfKills: Int = 0
  var numOfDeaths: Int = 0
}
