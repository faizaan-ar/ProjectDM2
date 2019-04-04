package GameObj

import Physics.PhysicsVector
import scalafx.scene.shape._

import scala.collection.mutable.ListBuffer

class Board {
  var Tanks: ListBuffer[Rectangle] = ListBuffer.empty
  var Bullets: ListBuffer[Circle] = ListBuffer.empty
  var Vectors: ListBuffer[PhysicsVector] = ListBuffer.empty
}
