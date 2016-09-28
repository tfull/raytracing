package raytrace

import scala.math

class Tracer(val deepest: Int, val bodies: Array[Body], val lights: Array[Light]){
    val background: Color = new Color()

    def nearest(ray: Ray) : Option[(Double, Body)] = {
        var distance: Double = Double.MaxValue
        var mbody: Option[Body] = None

        for(body <- this.bodies){
            body.intersect(ray) match{
                case Some(d) =>
                    if(d < distance){
                        distance = d
                        mbody = Some(body)
                    }
                case None => ;
            }
        }

        mbody match{
            case Some(b) => Some((distance, b))
            case None => None
        }
    }

    def trace(ray: Ray, depth: Int = 1) : Color = {

        def traceNearest(near: Option[(Double, Body)]) : Color = {
            near match{
                case Some((d, body)) => traceBody(d, body)
                case None => this.background
            }
        }

        def traceBody(t: Double, body: Body) : Color = {
            val hit: Vector = ray.origin + ray.direction * t
            val normal: Vector = body.normal(hit)
            val orient: Vector = if((normal dot ray.direction) >= 0.0){ normal * (-1.0) }else{ normal }

            body.material match{
                case Material.LAMBERTIAN => {
                    var color: Color = new Color(0.0, 0.0, 0.0)

                    for(light <- this.lights){
                        val vl: Vector = light.position - hit
                        val vl_length: Double = vl.norm()

                        this.nearest(new Ray(hit, vl)) match{
                            case Some((dist, _)) if dist < vl_length => ;
                            case _ => color += (light.color * body.color) * math.max(orient dot vl.normalized(), 0.0) / (vl_length * vl_length)
                        }
                    }
                    color
                }
                case Material.GLASS => {
                    var color: Color = new Color(0.0, 0.0, 0.0)

                    for(light <- this.lights){
                        val vL: Vector = ray.direction
                        val vN: Vector = body.normal()
                        val c: Double = - vL dot vN
                        val vR: Vector = vL + vN * (2.0 * c)
                    }
                }
                case _ => new Color(0.0, 0.0, 0.0)
            }
        }

        if(depth > this.deepest){
            this.background
        }else{
            traceNearest(this.nearest(ray))
        }
    }
}
