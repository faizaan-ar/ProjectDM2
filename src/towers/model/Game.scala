package towers.model

import play.api.libs.json.{JsValue, Json}
import towers.model.game_objects._
import towers.model.physics.{Physics, PhysicsVector, World}


class Game {

  val world: World = new World(10)

  var towers: List[DodgeBallTower] = List()
  var walls: List[Wall] = List()
  var projectiles: List[PhysicalObject] = List()

  var baseHealth = 10

  var level: Level = new Level()

  var players: Map[String, Player] = Map()
  val playerSize: Double = 0.3

  var lastUpdateTime: Long = System.nanoTime()


  def loadLevel(newLevel: Level): Unit = {
    world.boundaries = List()
    level = newLevel

    projectiles.foreach(po => po.destroy())
    towers = List()
    walls = List()
    projectiles = List()

    blockTile(0, 0, level.gridWidth, level.gridHeight)

    level.towerLocations.foreach(tower => placeTower(tower.x, tower.y))
    level.wallLocations.foreach(wall => placeWall(wall.x, wall.y))
    players.values.foreach(player => player.location = startingVector())

    baseHealth = level.maxBaseHealth
  }


  def addPlayer(id: String): Unit = {
    val player = new Player(startingVector(), new PhysicsVector(0, 0))
    players += (id -> player)
    world.objects = player :: world.objects
  }


  def removePlayer(id: String): Unit = {
    players(id).destroy()
    players -= id
  }

  def blockTile(x: Int, y: Int, width: Int = 1, height: Int = 1): Unit = {
    val ul = new PhysicsVector(x, y)
    val ur = new PhysicsVector(x + width, y)
    val lr = new PhysicsVector(x + width, y + height)
    val ll = new PhysicsVector(x, y + height)

    world.boundaries ::= new Boundary(ul, ur)
    world.boundaries ::= new Boundary(ur, lr)
    world.boundaries ::= new Boundary(lr, ll)
    world.boundaries ::= new Boundary(ll, ul)
  }


  def placeWall(x: Int, y: Int): Unit = {
    blockTile(x, y)
    walls = new Wall(x, y) :: walls
  }

  def placeTower(x: Int, y: Int): Unit = {
    towers = new DodgeBallTower(x, y) :: towers
  }


  def addProjectile(projectile: PhysicalObject): Unit = {
    projectiles = projectile :: projectiles
    world.objects = projectile :: world.objects
  }


  def startingVector(): PhysicsVector = {
    new PhysicsVector(level.startingLocation.x + 0.5, level.startingLocation.y + 0.5)
  }



  def update(): Unit = {
    val time: Long = System.nanoTime()
    val dt = (time - this.lastUpdateTime) / 1000000000.0
    Physics.updateWorld(this.world, dt)
    checkForPlayerHits()
    checkForBaseDamage()
    projectiles = projectiles.filter(po => !po.destroyed)
    this.lastUpdateTime = time
  }

  def gameState(): String = {
    val gameState: Map[String, JsValue] = Map(
      "gridSize" -> Json.toJson(Map("x" -> level.gridWidth, "y" -> level.gridHeight)),
      "start" -> Json.toJson(Map("x" -> level.startingLocation.x, "y" -> level.startingLocation.y)),
      "base" -> Json.toJson(Map("x" -> level.base.x, "y" -> level.base.y)),
      "baseHealth" -> Json.toJson(baseHealth),
      "maxBaseHealth" -> Json.toJson(level.maxBaseHealth),
      "walls" -> Json.toJson(this.walls.map({ w => Json.toJson(Map("x" -> w.x, "y" -> w.y)) })),
      "towers" -> Json.toJson(this.towers.map({ t => Json.toJson(Map("x" -> t.x, "y" -> t.y)) })),
      "players" -> Json.toJson(this.players.map({ case (k, v) => Json.toJson(Map(
        "x" -> Json.toJson(v.location.x),
        "y" -> Json.toJson(v.location.y),
        "v_x" -> Json.toJson(v.velocity.x),
        "v_y" -> Json.toJson(v.velocity.y),
        "id" -> Json.toJson(k))) })),
      "projectiles" -> Json.toJson(this.projectiles.map({ po => Json.toJson(Map("x" -> po.location.x, "y" -> po.location.y)) }))
    )

    Json.stringify(Json.toJson(gameState))
  }




  def checkForBaseDamage(): Unit = {
    // TODO: Objective 1
    val a = level.base.x + .5
    val b = level.base.y + .5
    var p = 0
    //    print(a, b)
    for (i <- players){
      p = p + 1
      if (p == 1){
        //          var t = i._1
        //          print(players.values)
        //PhysicalObject((0.5, 4.5, 0.0)
        //(0.5, 4.5, 0.0)
        //(24,4)
        var aa = i._2.location.x
        var bb = i._2.location.y
        var aaa = aa - a
        var aaaa = a - aa
        var bbb = bb - b
        var bbbb = b - bb
        var last = i._2.location.distance2d(new PhysicsVector(a,b))
        if(aaa <= playerSize){
          if (aaaa <= playerSize){
            print("aaaa")
            if(bbb <= playerSize){
              print("bbb")
              if (bbbb <= playerSize){
                print("bbbb")
                print(i._2.location.x, a)
                baseHealth = baseHealth - 1
                //                  removePlayer(i._1)
                //                  addPlayer(i._1)
                i._2.location = startingVector()

              }
            }
          }
        }
        //          if (last <= playerSize){
        //            print(i._2.location.x, a)
        //            baseHealth = baseHealth - 1
        //            removePlayer(i._1)
        //            addPlayer(i._1)
        //          }

      }
      else {
        p = 0
      }
    }
  }


  def checkForPlayerHits(): Unit = {
    // TODO: Objective 3
    for (i <- players){
      //      checkForBaseDamage()
      //      var a: List[PhysicalObject] = List()
      var b = -1
      for (k <- projectiles){
        //        checkForBaseDamage()

        b = b + 1
        var xx = i._2.location.x
        var xy = i._2.location.y
        var yx = k.location.x
        var yy = k.location.y
        var xd = Math.abs(xx - yx)
        var yd = Math.abs(xy - yy)
        var f1 = i._2.location
        var f2 = k.location
        var lastTry = f1.distance2d(f2)

        //(10.578285599999996,4.5,11.660658000000002,4.5)
        //(1.7445812999999966,0.0)
        if (lastTry < playerSize){
          //          print(xd,yd)
          i._2.location = startingVector()
          k.destroy()

          //          i._2.velocity = new PhysicsVector(0,0)
          //          removePlayer(i._1)
          //          addPlayer(i._1)
          //          print(projectiles)
          //List(PhysicalObject((11.83279310504388, 4.5596905397548415, 9.234522364001685E-4), (-4.843319330220225, -1.2418767513385192, -7.571681000000002)), PhysicalObject((11.714159500000004, 4.5, 9.234522364001685E-4), (-5.0, 0.0, -7.571681000000002)), PhysicalObject((11.83279310504388, 4.44030946024516, 9.234522364001685E-4), (-4.843319330220225, 1.2418767513385192, -7.571681000000002)))


          //          print(projectiles)
          //List(PhysicalObject((11.965117499999998, 4.5, 0.37493365792620414), (-5.0, 0.0, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.377977679092678, 0.37493365792620414), (-4.843319330220225, 1.2418767513385192, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.622022320907322, 0.37493365792620414), (-4.843319330220225, -1.2418767513385192, -7.069765000000002)))List(PhysicalObject((11.965117499999998, 4.5, 0.37493365792620414), (-5.0, 0.0, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.377977679092678, 0.37493365792620414), (-4.843319330220225, 1.2418767513385192, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.622022320907322, 0.37493365792620414), (-4.843319330220225, -1.2418767513385192, -7.069765000000002)))List(PhysicalObject((11.965117499999998, 4.5, 0.37493365792620414), (-5.0, 0.0, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.377977679092678, 0.37493365792620414), (-4.843319330220225, 1.2418767513385192, -7.069765000000002)), PhysicalObject((12.075887051538562, 4.622022320907322, 0.37493365792620414), (-4.843319330220225, -1.2418767513385192, -7.069765000000002)))
          //List(PhysicalObject((11.961451508612877, 4.397620667225802, 0.20924604469960065), (-4.846499476099184, 1.2294075110272986, -7.301245999999999)), PhysicalObject((11.961451508612877, 4.6023793327741975, 0.20924604469960065), (-4.846499476099184, -1.2294075110272986, -7.301245999999999)), PhysicalObject((11.849376999999999, 4.5, 0.20924604469960065), (-5.0, 0.0, -7.301245999999999)))
          //          print(k)
          //          a = a :+ k
        }
      }
    }
    //    print(projectiles)
  }


}
