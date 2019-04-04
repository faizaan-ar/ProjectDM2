package Physics

object Physics {
  def detectCollision(vec1: PhysicsVector, vec2: PhysicsVector, bound: Boundary): Boolean ={
    val sX: Double = vec1.x
    val sY: Double = vec1.y
    val fX: Double = vec2.x
    val fY: Double = vec2.y
    var crossX: Boolean = false
    var crossY: Boolean = false

    if(math.min(sY, fY) < math.max(bound.start.y, bound.end.y) && math.max(sY, fY) > math.min(bound.start.y, bound.end.y)){
        crossY = true
    }
    if(math.min(sX, fX) < math.max(bound.start.x, bound.end.x) && math.max(sX, fX) > math.min(bound.start.x, bound.end.x)){
      crossX = true
    }

    if(crossX && crossY) return false
    else return true
  }

}
