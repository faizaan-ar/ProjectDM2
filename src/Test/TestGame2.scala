package Test

import GUI.Game2
import Physics._
import javafx.scene.shape.Rectangle
import org.scalatest._

class TestGame2 extends FunSuite {
  test("ComputeLocation") {
    val v1 = new PhysicsVector(0,0,0)
    val v2 = new PhysicsVector(10,10,0)
    val v3 = new PhysicsVector(0,0,0)
    val v4 = new PhysicsVector(0,10,0)
    val a: Boundary = new Boundary(new PhysicsVector(0,5,10), new PhysicsVector(0,15,10))
    val b: Boundary = new Boundary(new PhysicsVector(-1,0,10), new PhysicsVector(-1,5,10))
    val r: Rectangle = Game2.createRectangle()

    assert(Physics.detectCollision(v1, v2, a) == true, v1)

    assert(Physics.detectCollision(v3, v4, b) == true, v1)

    assert(r.getHeight == 40.0)

  }
}
