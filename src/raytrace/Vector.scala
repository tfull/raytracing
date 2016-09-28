package raytrace

import scala.math

class Vector(val x: Double = 0.0, val y: Double = 0.0, val z: Double = 0.0){
    def +(v: Vector) = new Vector(x + v.x, y + v.y, z + v.z)
    def -(v: Vector) = new Vector(x - v.x, y - v.y, z - v.z)
    def *(k: Double) = new Vector(x * k, y * k, z * k)
    def /(k: Double) = new Vector(x / k, y / k, z / k)
    def dot(v: Vector) = x * v.x + y * v.y + z * v.z
    def cross(v: Vector) = {
        val cx = y * v.z - v.y * z
        val cy = z * v.x - v.z * x
        val cz = x * v.y - v.x * y
        new Vector(cx, cy, cz)
    }
    def norm() = math.sqrt(this.square())
    def square() = x * x + y * y + z * z
    def normalized() = this / this.norm()
}
