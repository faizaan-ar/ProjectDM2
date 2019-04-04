package Test

import GUI.Game2
import Physics._
import javafx.scene.shape.Rectangle
import org.scalatest._

class TestGame2 extends FunSuite {
  test("ComputeLocation") {
    Game2
    val r: Rectangle = Game2.createRectangle()
    assert(r.getHeight == 40.0)

  }
}
