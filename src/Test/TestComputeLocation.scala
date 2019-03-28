package Test

import Physics._
import org.scalatest._

class TestComputeLocation extends FunSuite{
  test("ComputeLocation") {
    val a: PhysicalObject = new PhysicalObject(new PhysicsVector(0,0,0), new PhysicsVector(1,1,1))
    val b: PhysicalObject = new PhysicalObject(new PhysicsVector(0,0,0), new PhysicsVector(1,1,-1))

    assert(Physics.computePotentialLocation(a,1.0).x == 1, a)
    assert(Physics.computePotentialLocation(a,1.0).y == 1, a)
    assert(Physics.computePotentialLocation(a,1.0).z == 1, a)

    assert(Physics.computePotentialLocation(b,1.0).x == 1, a)
    assert(Physics.computePotentialLocation(b,1.0).y == 1, a)
    assert(Physics.computePotentialLocation(b,1.0).z == 0, a)
  }
}
