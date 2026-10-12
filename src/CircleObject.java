import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Point2D;

public class CircleObject extends CollisionObject{
    private double radius;

    public CircleObject(){
        super();
        radius = 10.;

    }

    /**
     *
     * @param x- x coordinate of the upper left corner of the circle to be drawn
     * @param y- the y coordinate of the upper left corner of the circle to be drawn.
     * @param radius - the radius of the circle to be drawn
     * @param c-color of the circle to be drawn
     */
    public CircleObject(double x, double y, double radius, Color c){
        super(x, y, c);
        this.radius = radius;
    }

    public double getRadius(){
        return radius;
    }


    public double computeDistance(double cameraX, double cameraY){

        Point2D.Double cameraLoc = new Point2D.Double(cameraX, cameraY);
        Point2D.Double circleLoc = new Point2D.Double(getX(), getY());
        double distance =  (Math.sqrt((Math.pow((cameraLoc.x-circleLoc.x),2.))+(Math.pow((cameraLoc.y-circleLoc.y), 2))))-getRadius();
        if (distance < 0){
           return 0;
        }
        return distance;
    }
    @Override
    public void drawObject(Graphics2D g2d) {
        Ellipse2D ellipse = new Ellipse2D.Double(getX()-radius, getY()-radius, radius*2, radius*2);
        g2d.setColor(getColor());
        g2d.fill(ellipse);
    }
    public boolean contains(double x, double y) {
        // Check if the point is inside the circle
        return Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) <= Math.pow(radius, 2);
    }
}
