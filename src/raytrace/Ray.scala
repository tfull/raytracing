package raytrace

class Ray(val origin: Vector, d: Vector){
    val direction = d.normalized()
}
