package GameObj

object TankGame {
  def dealDamage(attacking: Tank, defending: Tank, b: Board): Unit ={
    val damage = attacking.attack - defending.defense
    if(damage > 0) defending.health -= damage
    if(defending.health <= 0) death(defending, attacking, b)
  }

  def death(dead: Tank, killer: Tank, b: Board): Unit ={
    killer.numOfKills += 1
    dead.numOfDeaths += 1
    b.Tanks -= dead
    dead.health = 100
    spawn(dead, b)
  }

  def shoot(t1: Tank, b: Board, direction: List[Int]): Unit ={
    val newX: Int = t1.position.head + direction.head
    val newY: Int = t1.position(1) + direction(1)

    b.Bullets += new Bullet(List(newX, newY), direction, t1)
  }

  def detectCollision(b: Board, t: Tank, bullet: Bullet): Boolean = {
    if(t.position == bullet.position){
      dealDamage(bullet.tank, t, b)
      b.Bullets -= bullet
      return true
    }
    else return false
  }

  def spawn(t1: Tank, b: Board): Unit ={
    val start = 0
    val end   = 100
    val r = new scala.util.Random
    val r1 = start + r.nextInt(( end - start) + 1)
    val r2 = start + r.nextInt(( end - start) + 1)
    t1.position = List(r1,r2)
    b.Tanks += t1
  }
  var timeInState: Double = 0.0

  def update(dt: Double): Unit = {
    timeInState += dt
  }
}
