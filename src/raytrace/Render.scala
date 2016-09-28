package raytrace

import java.awt.Dimension
import java.io.PrintWriter

class Render(val tracer: Tracer, val viewpoint: Vector, val grid: Grid, val writer: PrintWriter = new PrintWriter(System.out)){

    def run(){
        val width: Int = grid.width
        val height: Int = grid.height
        val density: Int = grid.density

        val wd: Int = width * density
        val hd: Int = height * density

        writer.println("P3")
        writer.println(width + " " + height)
        writer.println(255)

        for(i <- 0 until height){
            for(j <- 0 until width){
                var c: Color = new Color()

                for(di <- 0 until density){
                    for(dj <- 0 until density){
                        val view: Vector = grid.origin + grid.horizontal * ((j * density + dj + 0.5) / wd) + grid.vertical * ((i * density + di + 0.5) / hd)
                        val ray: Ray = new Ray(viewpoint, view - viewpoint)

                        c += tracer.trace(ray)
                    }
                }
                writer.println(c)
            }
        }
        writer.flush
    }
}
