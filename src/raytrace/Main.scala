package raytrace

import java.io.BufferedReader
import java.util.Scanner
import scala.io.StdIn

object Main{
    def main(args: Array[String]){
        var line: String = null

        var lights: List[Light] = List()
        var bodies: List[Body] = List()
        var viewpoint: Vector = new Vector()

        var depth = 5

        var width: Int = 640
        var height: Int = 480
        var density: Int = 3

        var grido: Vector = new Vector()
        var gridh: Vector = new Vector()
        var gridv: Vector = new Vector()

        line = StdIn.readLine

        while(line != null){
            val scan: Scanner = new Scanner(line)

            if(scan.hasNext()){
                scan.next() match{
                    case "light" => {
                        val p = new Array[Double](6)
                        for(i <- 0 until 6){
                            p(i) = scan.nextDouble()
                        }
                        val position = new Vector(p(0), p(1), p(2))
                        val color = new Color(p(3), p(4), p(5))

                        lights ::= new Light(position, color)
                    }
                    case "viewpoint" => {
                        val x = scan.nextDouble()
                        val y = scan.nextDouble()
                        val z = scan.nextDouble()
                        viewpoint = new Vector(x, y, z)
                    }
                    case "width" => {
                        width = scan.nextInt()
                    }
                    case "height" => {
                        height = scan.nextInt()
                    }
                    case "density" => {
                        density = scan.nextInt()
                    }
                    case "grid" => {
                        val s = scan.next()
                        val x = scan.nextDouble()
                        val y = scan.nextDouble()
                        val z = scan.nextDouble()
                        val v = new Vector(x, y, z)

                        s match{
                            case "origin" => {
                                grido = v
                            }
                            case "horizontal" => {
                                gridh = v
                            }
                            case "vertical" => {
                                gridv = v
                            }
                        }
                    }
                    case "body" => {
                        scan.next() match{
                            case "sphere" => {
                                var a = new Array[Double](7)
                                for(i <- 0 until 7){
                                    a(i) = scan.nextDouble()
                                }
                                var m = Material.LAMBERTIAN
                                scan.next() match{
                                    case "lambertian" => {
                                        m = Material.LAMBERTIAN
                                    }
                                }
                                bodies ::= new Sphere(new Vector(a(0),a(1),a(2)), a(3), new Color(a(4),a(5),a(6)), m)
                            }
                        }
                    }
                    case "depth" => {
                        depth = scan.nextInt()
                    }
                    case "#" => {
                    }
                }
            }

            line = StdIn.readLine
        }

        val grid = new Grid(width, height, density, grido, gridh, gridv)
        val tracer = new Tracer(depth, bodies.reverse.toArray, lights.reverse.toArray)
        val render = new Render(tracer, viewpoint, grid)

        render.run()
    }
}
