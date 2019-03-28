package Test.physics

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
  def detectCollision(obj: PhysicalObject, vec: PhysicsVector, bound: Boundary): Boolean ={
    val sX: Double = obj.location.x
    val sY: Double = obj.location.y
    val fX: Double = vec.x
    val fY: Double = vec.y
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
  def updateWorld(w: World, time: Double): Unit ={
    var updated: PhysicsVector = new PhysicsVector(0,0,0)
    var miss: Boolean = true

    for(obj: PhysicalObject <- w.objects){
      miss = true
      updateVelocity(obj, w, time)
      updated = computePotentialLocation(obj, time)

      for(bound: Boundary <- w.boundaries){
        if(miss) miss = detectCollision(obj, updated, bound)
      }

      if(miss){
        obj.location.x = updated.x
        obj.location.y = updated.y
      }

      obj.location.z = updated.z
    }
  }
}
