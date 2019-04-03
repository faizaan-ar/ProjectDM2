package Test

import GameObj._
import org.scalatest.FunSuite

class TestDealDamage extends FunSuite{
  test("ComputeLocation") {
    val b: Board = new Board
    val t1: Tank = new Tank(100, 10, 5)
    val t2: Tank = new Tank(50,5,8)

    TankGame.spawn(t1, b)
    TankGame.spawn(t2, b)

    TankGame.dealDamage(t1, t2, b)
    TankGame.dealDamage(t2, t1, b)

    assert(b.Tanks(0).health == 100)
    assert(b.Tanks(1).health == 48)
  }
}
