package Game

class Tank(var health: Double, var attack: Double, var defense: Double, var position: List[Int] = List(0,0)) {
  var numOfKills: Int = 0
  var numOfDeaths: Int = 0
}
