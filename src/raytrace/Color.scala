package raytrace

import scala.math

class Color(val r: Double = 0.0, val g: Double = 0.0, val b: Double = 0.0){
    def +(c: Color) = new Color(r + c.r, g + c.g, b + c.b)
    def *(k: Double) = new Color(r * k, g * k, b * k)
    def /(k: Double) = new Color(r / k, g / k, b / k)
    def *(c: Color) = new Color(r * c.r, g * c.g, b * c.b)

    def clamp(x: Double) : Double = {
        if(x < 0.0){
            0.0
        }else{
            math.min(1.0, x)
        }
    }

    def gamma(x: Double) : Int = {
        (math.pow(clamp(x), 1.0 / 2.2) * 255.0 + 0.5).toInt
    }

    override def toString() : String = {
        gamma(r) + " " + gamma(g) + " " + gamma(b)
    }
}
