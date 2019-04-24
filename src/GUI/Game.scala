package GUI
//HI
import javafx.scene.input.{KeyCode, KeyEvent, MouseEvent}
import scalafx.animation.AnimationTimer
import scalafx.application.JFXApp
import scalafx.application.JFXApp.PrimaryStage
import scalafx.scene.paint.Color
import scalafx.scene.shape.{Circle, Rectangle, Shape}
import scalafx.scene.{Group, Scene}
import scalafx.scene.paint._
import Physics.Boundary
import GameObj._

object Game extends JFXApp {

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

  //val enemies = new Circle {
    //centerX = 100
    //centerY = 100
    //radius = playerCircleRadius
    //fill = Color.Black
  //}

  val play = new Circle {
    centerX = 400
    centerY = 300
    radius = 50
    fill = Color.Green
  }
  //sceneGraphics.children.add(enemies)
  sceneGraphics.children.add(play)


  val player = new Rectangle() {
    width = rectangleWidth
    height = rectangleHeight
    translateX = rectangleWidth / 2.0
    translateY = rectangleHeight / 2.0
    fill = Color.Blue
  }
  sceneGraphics.children.add(player)

  val player2 = new Rectangle() {
    width = rectangleWidth
    height = rectangleHeight
    translateX = 60 / 2.0
    translateY = 1000 / 2.0
    fill = Color.Blue
  }
  sceneGraphics.children.add(player2)

  def shoot(centerXX: Double, centerYY: Double, v1: Double, v2: Double): Unit = {
    val en = new Circle {
      centerX = v1
      centerY = v2
      radius = playerCircleRadius
      fill = Color.Black
    }
    sceneGraphics.children.add(en)
    var lastTime: Long = 0
    var l: Int = 0
    var delay: Double = 5.0
    val timer: AnimationTimer = AnimationTimer(t => {
      if (lastTime > 0) {
        val delta = (t - lastTime) / 100
        val dx = centerXX - en.centerX.value
        val dy = centerYY - en.centerY.value
        val dis = math.sqrt(dx * dx + dy * dy)
        en.centerX = en.centerX.value + dx / dis * speed20
        en.centerY = en.centerY.value + dy / dis * speed20
        if (dis < 0.5){
          sceneGraphics.children.remove(en)
        }
        //delay += delta
        //if (delay < 0){
          //delay = 5.0
        //}
      }
      lastTime = t

    })
    timer.start()
  }
  def BB(): Unit = {
    val en = new Circle {
      centerX = player.getLayoutX
      centerY = player.getLayoutY
      radius = playerCircleRadius
      fill = Color.Black
    }
    sceneGraphics.children.add(en)
  }

  def drawRectangle(centerXX: Double, centerYY: Double): Unit = {
    val newRectangle = new Circle {
      centerX = centerXX
      centerY = centerYY
      radius = playerCircleRadius
      fill = Color.Green
    }
    sceneGraphics.children.add(newRectangle)
    allRectangles = newRectangle :: allRectangles
  }


  def keyPressed(keyCode: KeyCode): Unit = {
    keyCode.getName match {
      case "W" => player.translateY.value -= playerSpeed
      case "A" => player.translateX.value -= playerSpeed
      case "S" => player.translateY.value += playerSpeed
      case "D" => player.translateX.value += playerSpeed
      case "U" => player2.translateY.value -= playerSpeed
      case "H" => player2.translateX.value -= playerSpeed
      case "J" => player2.translateY.value += playerSpeed
      case "K" => player2.translateX.value += playerSpeed
      
      case _ => println(keyCode.getName + " pressed with no action")
    }
  }


  this.stage = new PrimaryStage {
    this.title = "2D Graphics"
    scene = new Scene(windowWidth, windowHeight) {
      content = List(sceneGraphics)

      //var lastTime: Long = 0
      //val speed1 = 0
      //val speed2 = 1
      //val timer = AnimationTimer(t => {
        //if (lastTime>0){
          //val delta = (t - lastTime)/100
          //val dx = play.centerX.value - enemies.centerX.value
          //val dy = play.centerY.value - enemies.centerY.value
          //val dis = math.sqrt(dx * dx + dy * dy)
          //enemies.centerX = enemies.centerX.value + dx / dis * speed2
          //enemies.centerY = enemies.centerY.value + dy / dis * speed2
        //}
        //lastTime = t

      //})
      //timer.start()



      // add an EventHandler[KeyEvent] to control player movement
      addEventHandler(KeyEvent.KEY_PRESSED, (event: KeyEvent) => keyPressed(event.getCode))

      // add an EventHandler[MouseEvent] to draw a rectangle when the player clicks the screen
      addEventHandler(MouseEvent.MOUSE_CLICKED, (event: MouseEvent) => shoot(event.getX, event.getY, player.translateX.value, player.translateY.value))
    }

    // define a function for the action timer (Could also use a method)
    // Rotate all rectangles (relies on frame rate. lag will slow rotation)
    val update: Long => Unit = (time: Long) => {
      for (shape <- allRectangles) {
        shape.rotate.value += 0.5
      }
    }



    // Start Animations. Calls update 60 times per second (takes update as an argument)
    AnimationTimer(update).start()
  }

}
// hello world 123456789