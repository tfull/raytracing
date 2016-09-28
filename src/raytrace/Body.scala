package raytrace

object Material extends Enumeration{
    val GLASS = Value("Glass")
    val MIRROR = Value("Mirror")
    val LAMBERTIAN = Value("Lambertian")
}

class Body(val color: Color, val material: Material.Value){
    def intersect(r: Ray) : Option[Double] = None
    def normal(p: Vector) : Vector = new Vector(0.0, 0.0, 0.0)
}

object Body{
    val EPS: Double = 1e-6
}
