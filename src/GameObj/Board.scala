package GameObj

import scala.collection.mutable.ListBuffer

class Board {
  var Tanks: ListBuffer[Tank] = ListBuffer.empty
  var Bullets: ListBuffer[Bullet] = ListBuffer.empty
}
