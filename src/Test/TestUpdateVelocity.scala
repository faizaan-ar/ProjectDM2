package Test

import Physics._
import org.scalatest._

class TestUpdateVelocity extends FunSuite {
  test("ComputeLocation") {
    val a: PhysicalObject = new PhysicalObject(new PhysicsVector(0, 0, 0), new PhysicsVector(1, 1, 1))
    val b: PhysicalObject = new PhysicalObject(new PhysicsVector(0, 0, 0), new PhysicsVector(1, 1, -1))
    val c: PhysicalObject = new PhysicalObject(new PhysicsVector(0, 0, 10), new PhysicsVector(1, 1, -10))
    val d: PhysicalObject = new PhysicalObject(new PhysicsVector(0, 0, 10), new PhysicsVector(1, 1, 10))
    val e: PhysicalObject = new PhysicalObject(new PhysicsVector(0, 0, 0), new PhysicsVector(1, 1, 20))

    Physics.updateVelocity(a, new World(1.0), 1.0)
    Physics.updateVelocity(b, new World(2.0), 1.0)
    Physics.updateVelocity(c, new World(5.0), 1.0)
    Physics.updateVelocity(d, new World(15.0), 1.0)
    Physics.updateVelocity(e, new World(15.0), 1.0)

    assert(a.velocity.z == 0)
    assert(b.velocity.z == 0)
    assert(c.velocity.z == -15.0)
    assert(d.velocity.z == -5.0)
    assert(e.velocity.z == 5.0)

  }
}