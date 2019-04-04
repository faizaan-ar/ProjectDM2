package Physics

object Physics {
  def computePotentialLocation(obj: PhysicalObject, time: Double): PhysicsVector = {
    var x: Double = obj.location.x
    var y: Double = obj.location.y
    var z: Double = obj.location.z

    x += obj.velocity.x * time
    y += obj.velocity.y * time
    z += obj.velocity.z * time
    if(z < 0) z = 0

    return new PhysicsVector(x, y, z)
  }
  def updateVelocity(obj: PhysicalObject, w: World, time: Double): Unit = {
    var v: Double = obj.velocity.z
    val g: Double = w.gravity

    v -= g * time

    if(v < 0 && obj.location.z <= 0) v = 0

    obj.velocity.z  = v

  }
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
