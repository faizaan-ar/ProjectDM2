package Test

import Game.{Board, Tank, TankGame}
import org.scalatest.FunSuite

class TestShoot extends FunSuite{
  test("ComputeLocation") {
    val b: Board = new Board
    val t1: Tank = new Tank(100, 10, 5)
    val t2: Tank = new Tank(50, 5, 8)

    TankGame.spawn(t1, b)
    TankGame.spawn(t2, b)

    TankGame.shoot(b.Tanks.head, b, List(0,1))
    TankGame.shoot(b.Tanks(1), b, List(1, 0))

    val location1: List[Int] = b.Tanks.head.position
    val location2: List[Int] = b.Tanks(1).position

    assert(b.Bullets.head.position == List(location1.head, location1(1) + 1))
    assert(b.Bullets(1).position == List(location2.head + 1, location2(1)))
    assert(b.Bullets.head.tank == b.Tanks.head)
    assert(b.Bullets(1).tank == b.Tanks(1))

  }
}
