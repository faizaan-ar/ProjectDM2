package Test

import GameObj._
import org.scalatest.FunSuite

class TestSpawn extends FunSuite{
  test("ComputeLocation") {
    val b: Board = new Board
    val t1: Tank = new Tank(100, 10, 5)
    val t2: Tank = new Tank(50,5,8)

    TankGame.spawn(t1, b)
    TankGame.spawn(t2, b)

    print(b.Tanks(0).position)
    print(b.Tanks(1).position)

    assert(b.Tanks(0).attack == 10, b)
    assert(b.Tanks(1).defense == 8, b)
    assert(b.Tanks(0).position(0) >= 0, b)
    assert(b.Tanks(1).position(1) <= 100, b)
  }
}
