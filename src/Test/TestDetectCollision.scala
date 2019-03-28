package Test

import Game._
import org.scalatest.FunSuite

class TestDetectCollision extends FunSuite {
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
