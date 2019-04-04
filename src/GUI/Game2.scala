package GUI

import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle}
import scalafx.scene.Scene
import Physics._
import GameObj._


object Game2 extends JFXApp {

  val windowWidth: Double = 800
  val windowHeight: Double = 600
  val playerSpeed: Double = 10
  var board = new Board()
  
  board.Tanks += createRectangle
  board.Tanks += createRectangle

  def createRectangle(): Rectangle = {
    val rect = new Rectangle() {
      width = 40
      height = 40
      translateX = Math.random * windowWidth
      translateY = Math.random * windowHeight
      fill = Color.rgb((Math.random * 255).toInt,(Math.random * 255).toInt, (Math.random * 255).toInt)
    }
    return rect
  }
  def shoot(mouseX: Double, mouseY: Double, v1: Double, v2: Double): Unit = {
    val dx: Double = (mouseX - v1) / Math.sqrt(Math.pow(mouseX - v1, 2) + Math.pow(mouseY - v2, 2))
    val dy: Double = (mouseY - v2) / Math.sqrt(Math.pow(mouseX - v1, 2) + Math.pow(mouseY - v2, 2))
    val en = new Circle {
      centerX = v1
      centerY = v2
      radius = 10
      fill = Color.Black
    }

    board.Vectors += new PhysicsVector(dx * 2, dy * 2, 0)
    board.Bullets += en
  }

  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => board.Tanks(0).translateY.value -= playerSpeed
      case "A" => board.Tanks(0).translateX.value -= playerSpeed
      case "S" => board.Tanks(0).translateY.value += playerSpeed
      case "D" => board.Tanks(0).translateX.value += playerSpeed

      case _ => println(keyCode.getName + "No Action")
    }
  }


  //  // Anything in here will occur 60 times per second. Use for hit detection
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
          if(board.Bullets(i).centerX.value > windowWidth || board.Bullets(i).centerY.value > windowHeight || board.Bullets(i).centerX.value  < 0 || board.Bullets(i).centerY.value < 0){
            board.Bullets -= board.Bullets(i)
            board.Vectors -= board.Vectors(i)
          }
          var bulletV1: PhysicsVector = new PhysicsVector(board.Bullets(i).centerX.value - board.Bullets(i).radius.value, board.Bullets(i).centerY.value, 0)
          var bulletV2: PhysicsVector = new PhysicsVector(board.Bullets(i).centerX.value + board.Bullets(i).radius.value, board.Bullets(i).centerY.value, 0)

          var bulletV3: PhysicsVector = new PhysicsVector(board.Bullets(i).centerX.value, board.Bullets(i).centerY.value - board.Bullets(i).radius.value, 0)
          var bulletV4: PhysicsVector = new PhysicsVector(board.Bullets(i).centerX.value, board.Bullets(i).centerY.value + board.Bullets(i).radius.value, 0)

          for(j <- board.Tanks.indices){
            if(j != 0){
              val lBound: PhysicsVector = new PhysicsVector(board.Tanks(j).translateX.value, board.Tanks(j).translateY.value + (board.Tanks(j).height.value), 0)
              val rBound: PhysicsVector = new PhysicsVector(board.Tanks(j).translateX.value, board.Tanks(j).translateY.value - (board.Tanks(j).height.value), 0)
              val bound: Boundary = new Boundary(lBound, rBound)
              val lBound2: PhysicsVector = new PhysicsVector(board.Tanks(j).translateX.value + (board.Tanks(j).width.value), board.Tanks(j).translateY.value, 0)
              val rBound2: PhysicsVector = new PhysicsVector(board.Tanks(j).translateX.value - (board.Tanks(j).width.value), board.Tanks(j).translateY.value, 0)
              val bound2: Boundary = new Boundary(lBound2, rBound2)
              if(!Physics.detectCollision(bulletV1, bulletV2, bound) || !Physics.detectCollision(bulletV3, bulletV4, bound2) ){
                board.Bullets -= board.Bullets(i)
                board.Vectors -= board.Vectors(i)
                board.Tanks -= board.Tanks(j)
                board.Tanks += createRectangle()
              }

            }
          }
        }
      }

    }
    addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))
    // add an EventHandler[MouseEvent] to draw a circle when the board.Tanks(0) clicks the screen
    addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => shoot(event.getX, event.getY, board.Tanks(0).translateX.value, board.Tanks(0).translateY.value))
  }
}
