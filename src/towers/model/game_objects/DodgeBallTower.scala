package towers.model.game_objects

import play.api.libs.json.{JsObject, JsValue, Json}
import towers.model.genetics.genes.Gene
import towers.model.physics.{Physics, PhysicsVector}

class DodgeBallTower(val x: Int, val y: Int) extends GameObject {

  // The height at which projectiles are fired
  val height = 3.0

  // Towers can only fire at players closer than this distance from the tower
  val sightRange = 5.0

  // The magnitude of the velocity at which projectiles are fired
  val projectileVelocity = 5.0


  def fire(jsonGameState: String): List[Projectile] = {
    // TODO: Objective 2
    var bull = List()
    val parsed: JsValue = Json.parse(jsonGameState)
    // unused values, but this is how we would extract message and timestamp
    //    val players: String = (parsed \ "players").as[String]
    val timestamp: Int = (parsed \ "baseHealth").as[Int]
    //    val players: Array[Map[String, JsObject]] = (parsed \ "players").as[Array[Map[String, JsObject]]]
    val players: Array[JsObject] = (parsed \ "players").as[Array[JsObject]]
    val towers: Array[JsObject] = (parsed \ "towers").as[Array[JsObject]]

    //    var p = 0
    //    for (i <- players){
    //      p = p + 1
    //      if (p == 1){
    //        //          var t = i._1
    //        //          print(players.values)
    //        //PhysicalObject((0.5, 4.5, 0.0)
    //        //(0.5, 4.5, 0.0)
    //        //(24,4)
    //        var aa = players(1_)
    //        var bb = i._2.location.y
    //    for (i <- players){
    //      if(i("x") != null){
    //        print(i("x"))
    //      }
    //    }
    var jk = List()
    var jkkk: List[Projectile] = List()
    var sight = sightRange
    var we = 0
    var yes = 0

    //      for (j <- towers){
    var you: Projectile = new Projectile(new PhysicsVector(0,0,0), new PhysicsVector(0,0,0))
    for (i <- players) {
      if (i == null){
        return List()
      }
      var xq = i("x").toString().toDouble
      var yq = i("y").toString().toDouble
      var hh = 0.0
      var hhh = 0.0
      var tx: Double = x + .5
      var ty: Double = y + .5
      //          if((hh != rh) && (hhh != rhh)){
      //            sight = 5.0
      //          }
      if ((xq - tx) < sight){
        if((tx - xq) < sight){
          if((yq - ty) < sight){
            if((ty - yq) < sight){
              var f = new PhysicsVector(tx,ty,0)
              var f1 = new PhysicsVector(xq,yq,0)
              //                  var f2 = new PhysicsVector(projectileVelocity,projectileVelocity, projectileVelocity)
              //                  var t = new PhysicalObject(f, f2)

              //                var e = f.normal2d(f1)
              //                var d = Physics.computePotentialLocation(t,e)
              if ((f.distance2d(f1) < sight) && (hh != x) && (hhh != y)){
                var ad = Math.pow((xq - tx), 2) + Math.pow((yq - ty), 2)
                ad = Math.sqrt(ad)
                //                    print(ad)
                we = 1
                yes = 1
                sight = f.distance2d(f1)
                you = new Projectile(new PhysicsVector(tx,ty,height),new PhysicsVector((xq - tx) / ad * projectileVelocity , (yq - ty) / ad * projectileVelocity, 0))
                var a1 = 0.0
                var a2 = 0.0
                var a3 = 0.0
                var a4 = 0.0
                if (((xq - tx) >= 4) || ((xq - tx) <= -4)){
                  if ((xq - tx) > 0){
                    a1 = 3.9
                  }
                  else{
                    a1 = -3.9
                  }
                }
                if (((yq - ty) >= 4) || ((yq - ty) <= -4)){
                  if ((yq - ty) > 0){
                    a3 = 3.9
                  }
                  else{
                    a3 = -3.9
                  }
                }
                if((a1 != 0) && (a3 != 0)){
                  var ad = Math.pow((a1), 2) + Math.pow((a3), 2)
                  ad = Math.sqrt(ad)
                  you = new Projectile(new PhysicsVector(tx,ty,height),new PhysicsVector(a1 / ad * projectileVelocity, a3 / ad * projectileVelocity,0))
                }
                if((a1 != 0) && (a3 == 0)){
                  var ad = Math.pow((a1), 2) + Math.pow((yq - ty), 2)
                  ad = Math.sqrt(ad)
                  //                      print(ad)
                  you = new Projectile(new PhysicsVector(tx,ty,height),new PhysicsVector(a1 / ad * projectileVelocity, (yq - ty) / ad * projectileVelocity, 0))
                  //                    print(you)
                }
                if((a1 == 0) && (a3 != 0)){
                  var ad = Math.pow((xq - tx), 2) + Math.pow((a3), 2)
                  ad = Math.sqrt(ad)
                  you = new Projectile(new PhysicsVector(tx,ty,height),new PhysicsVector((xq - tx) / ad * projectileVelocity, a3 / ad * projectileVelocity, 0))
                }


                //                    print(you)

              }
            }
          }
        }
      }
    }

    if (we == 1){
      sight = 5.0
      jkkk = List(you)
      we = 0
    }
    //    var kk: List[Projectile] = List()
    //    if(jkkk != kk){
    //      return jkkk
    //    }
    //    else {
    //      return jk
    //    print(jkkk)
    jkkk
    //    print(players)
    //{"baseHealth":20,"players":[{"x":5.721951952488226,"v_y":0,"y":4.441539729612537,"id":"cfe6067516ba4794abaa664d21831ff4","v_x":0}],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}
    //{"baseHealth":20,"players":[],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}{"baseHealth":20,"players":[],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}{"baseHealth":20,"players":[],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}{"baseHealth":20,"players":[],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}{"baseHealth":20,"players":[],"projectiles":[],"base":{"x":24,"y":4},"towers":[{"x":20,"y":6},{"x":18,"y":3},{"x":15,"y":3},{"x":15,"y":4},{"x":15,"y":5}],"walls":[{"x":12,"y":8},{"x":12,"y":7},{"x":12,"y":6},{"x":12,"y":5},{"x":12,"y":3},{"x":12,"y":2},{"x":12,"y":1},{"x":12,"y":0}],"start":{"x":0,"y":4},"gridSize":{"x":25,"y":9},"maxBaseHealth":20}Listening on port: 8000
  }


  def aimFire(jsonGameState: String): List[Projectile] = {
    // TODO: Bonus Objective
    List()
  }


  // Suggested Genetic Algorithm setup
  def getFitnessFunction(targetPlayer: Player): PhysicsVector => Double = {
    null
  }

  def vectorIncubator(genes: List[Gene]): PhysicsVector = {
    null
  }

}



//if (((x - tx) > 4) && ((y - ty) > 4)){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx - 1, y - ty - 1, projectileVelocity))
//                      print(you)
//                    }
//                    else if (((x - tx) > 4) && ((y - ty) < -4)){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx - 1, y - ty + 1, projectileVelocity))
//                      print(you)
//                    }
//                    else if (((x - tx) < -4) && ((y - ty) > 4)){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx + 1, y - ty - 1, projectileVelocity))
//                      print(you)
//                    }
//                    else if (((x - tx) < -4) && ((y - ty) < -4)){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx + 1, y - ty + 1, projectileVelocity))
//                      print(you)
//                    }
//                    else if (x - tx > 4){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx - 1, y - ty, projectileVelocity))
//                      print(you)
//                    }
//                    else if (y - ty < -4){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx, y - ty + 1, projectileVelocity))
//                      print(you)
//                    }
//                    else if (x - tx < -4){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx + 1, y - ty, projectileVelocity))
//                      print(you)
//                    }
//                    else if (y - ty > 4){
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx + 1, y - ty + 1, projectileVelocity))
//                      print(you)
//                    }
//                    else {
//                      sight = f.distance2d(f1)
//                      we = 1
//                      you = new Projectile(new PhysicsVector(tx, ty, 0), new PhysicsVector(x - tx, y - ty, projectileVelocity))
//                      print(you)
//                    }