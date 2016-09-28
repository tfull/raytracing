package raytrace

import scala.math

class Sphere(val center: Vector, val radius: Double, c: Color, m: Material.Value) extends Body(c, m){

    override def intersect(ray: Ray) : Option[Double] = {
        val so: Vector = ray.origin - this.center
        val b: Double = ray.direction dot so
        val c: Double = so.square() - this.radius * this.radius
        val d: Double = b * b - c

        if(d >= 0.0){
            val root_d: Double = math.sqrt(d)
            val t1: Double = - b + root_d
            val t2: Double = - b - root_d

            if(t2 >= Body.EPS){
                Some(t2)
            }else if(t1 >= Body.EPS){
                Some(t1)
            }else{
                None
            }
        }else{
            None
        }
    }

    override def normal(p: Vector) : Vector = (p - this.center).normalized()
}
