package physics

class World(val gravity: Double) {
  var objects: List[PhysicalObject] = List(new PhysicalObject(new PhysicsVector(0,0,0), new PhysicsVector(1,1,1)), new PhysicalObject(new PhysicsVector(5,10,0), new PhysicsVector(1,1,-1)))
  var boundaries: List[Boundary] = List(new Boundary(new PhysicsVector(0,5,10), new PhysicsVector(0,15,10)))
}
