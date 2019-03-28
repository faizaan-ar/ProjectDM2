package Test

import Game.{Board, Bullet, Tank, TankGame}
import Physics._
import org.scalatest._

class TestDetectCollision extends FunSuite {
  test("ComputeLocation") {
    val a: PhysicalObject = new PhysicalObject(new PhysicsVector(0,0,0), new PhysicsVector(1,1,1))
    val b: PhysicalObject = new PhysicalObject(new PhysicsVector(5,10,0), new PhysicsVector(1,1,-1))

    val c: PhysicsVector = new PhysicsVector(10,10,10)
    val d: PhysicsVector = new PhysicsVector(-5,10,0)

    val e: Boundary = new Boundary(new PhysicsVector(0,5,10), new PhysicsVector(0,15,10))

    assert(Physics.detectCollision(a, c, e) == true, a)
    assert(Physics.detectCollision(b, d, e) == false, b)
  }
  test("ComputeLocation") {
    val b: Board = new Board
    val t1: Tank = new Tank(100, 10, 5)
    val t2: Tank = new Tank(50, 5, 8)

    TankGame.spawn(t1, b)
    TankGame.spawn(t2, b)

    TankGame.shoot(b.Tanks.head, b, List(0, 1))
    TankGame.shoot(b.Tanks(1), b, List(1, 0))

    b.Bullets += new Bullet(t1.position, List(0,1), t2)

    assert(!TankGame.detectCollision(b, b.Tanks(0), b.Bullets(1)), b)
    assert(!TankGame.detectCollision(b, b.Tanks(1), b.Bullets(0)), b)
    assert(TankGame.detectCollision(b, b.Tanks(0), b.Bullets(2)), b)
  }
}