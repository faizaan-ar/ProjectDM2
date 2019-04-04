package GUI

import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint._
import Physics._
import GameObj._
import scalafx.application

import scala.collection.mutable.ListBuffer

object Game2 extends JFXApp {

  val speed20 = 1

  val windowWidth: Double = 800
  val windowHeight: Double = 600

  val playerCircleRadius: Double = 20
  val playerSpeed: Double = 10

  val rectangleWidth: Double = 60
  val rectangleHeight: Double = 40

  var allRectangles: List[Shape] = List()
  var sceneGraphics: Group = new Group {}

  var board = new Board()

  val player = new Rectangle() {
    width = rectangleWidth
    height = rectangleHeight
    translateX = rectangleWidth / 2.0
    translateY = rectangleHeight / 2.0
    fill = Color.Blue
  }
  board.Tanks += player
  sceneGraphics.children.add(player)

  def shoot(mouseX: Double, mouseY: Double, v1: Double, v2: Double): Unit = {
    val en = new Circle {
      centerX = v1
      centerY = v2
      radius = playerCircleRadius
      fill = Color.Black
    }
    var dx: Double = (mouseX - v1) / Math.sqrt(Math.pow(mouseX - v1, 2) + Math.pow(mouseY - v2, 2))
    var dy: Double = (mouseY - v2) / Math.sqrt(Math.pow(mouseX - v1, 2) + Math.pow(mouseY - v2, 2))
    board.Vectors += new PhysicsVector(dx, dy, 0)
    sceneGraphics.children.add(en)
    board.Bullets += en
  }

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => player.translateY.value -= playerSpeed
      case "A" => player.translateX.value -= playerSpeed
      case "S" => player.translateY.value += playerSpeed
      case "D" => player.translateX.value += playerSpeed

      case _ => println(keyCode.getName + " pressed with no action")
    }
  }


  //  // Anything in here will occur 60 timess per second. Use for hit detection
  stage = new JFXApp.PrimaryStage {
    title.value = "2D Graphics"
    scene = new Scene(windowWidth, windowHeight) {
      AnimationTimer(update).start()
      def update: Long => Unit = (time: Long) => {
        content = board.Bullets.toList ::: board.Tanks.toList
        println(board.Bullets)
        for(i <- board.Bullets.indices){
          board.Bullets(i).centerX = board.Bullets(i).centerX.value + board.Vectors(i).x
          board.Bullets(i).centerY = board.Bullets(i).centerY.value + board.Vectors(i).y
          if(board.Bullets(i).centerX.value > 800 || board.Bullets(i).centerY.value > 600 || board.Bullets(i).centerX.value  < 0 || board.Bullets(i).centerY.value < 0){
            board.Bullets -= board.Bullets(i)
            board.Vectors -= board.Vectors(i)
          }
        }

      }

    }
    addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))
    // add an EventHandler[MouseEvent] to draw a circle when the player clicks the screen
    addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => shoot(event.getX, event.getY, player.translateX.value, player.translateY.value))



  }
}
