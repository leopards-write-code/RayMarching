import java.awt.*;
import java.awt.geom.Rectangle2D;

public abstract class CollisionObject implements Drawable {
    private Color c;
    private double x;
    private double y;
    public CollisionObject(){
        x = 0.;
        y = 0.;
        c = Color.BLUE;
    }
    public CollisionObject(double x, double y){
        this.x=x;
        this.y=y;
        c = Color.BLUE;
    }
    public CollisionObject(double x, double y, Color c){
        this.x=x;
        this.y=y;
        this.c = c;
    }

    public void setVals(double x, double y ){
        this.x = x;
        this.y = y;
    }
    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }
    public Color getColor(){
        return c;
    }


    public abstract double computeDistance(double cameraX, double cameraY);
    public abstract boolean contains(double x, double y);
}
