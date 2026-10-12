import java.awt.*;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;
import java.awt.geom.Rectangle2D;
import java.util.Arrays;

public class RectangleObject extends CollisionObject{
    private double width;
    private double height;
    private Point2D.Double[] points;
    public RectangleObject(){
        super();
        width = 0.;
        height = 0.;
        points = setRectPoints();
    }
    public RectangleObject(double width, double height){
        super();
        this.width = width;
        this.height = height;
        points = setRectPoints();
    }

    /**
     *
     * @param x-  X coordinate of the upper-left corner of the Rectangle
     * @param y-  Y coordinate of the upper-left corner of the Rectangle
     * @param width- width of Rectangle
     * @param height-height of Rectangle
     */
    public RectangleObject(double x, double y, double width, double height){
        super(x, y);

        this.width = width;
        this.height = height;
        points = setRectPoints();
    }

    /**
     *
     * @param x- X coordinate of the upper-left corner of the Rectangle
     * @param y-  Y coordinate of the upper-left corner of the Rectangle
     * @param width- width of Rectangle
     * @param height-height of Rectangle
     * @param c-color of Rectangle
     */
    public RectangleObject(double x, double y, double width, double height, Color c){
        super(x, y, c);
        this.width = width;
        this.height = height;
        points = setRectPoints();
    }
    public double getWidth(){return width;}
    public double getHeight(){return height;}
    private Point2D.Double[] setRectPoints(){
        /*
             P1 ____________P3
                |          |
              P2|__________|P4
         */
        Point2D.Double[] rectPoints = new Point2D.Double[4];
        rectPoints[0] = new Point2D.Double(getX(), getY());
        rectPoints[1] = new Point2D.Double(getX(), getY() + getHeight());
        rectPoints[2] = new Point2D.Double(getX()+getWidth(), getY());
        rectPoints[3]= new Point2D.Double(getX()+getWidth(), getY()+getHeight());
        return rectPoints;
    }
    public Point2D.Double[] getRectPoints(){
        return points;
    }
    @Override
    public void drawObject(Graphics2D g2d) {
        Rectangle2D.Double rect = new Rectangle2D.Double(getX(), getY(), width, height);
        g2d.setColor(getColor());
        g2d.fill(rect);
    }
    public double computeDistance(double cameraX, double cameraY){

        Point2D.Double[] points = new Point2D.Double[getRectPoints().length];
                points = getRectPoints();
        // FOr debuggingSystem.out.println("Points: " + Arrays.toString(points)); // Check points
        //For debuggingSystem.out.println("X: " + getX() + ", Y: " + getY());   // Check x and y values
        Point2D.Double camLoc = new Point2D.Double(cameraX, cameraY);
        if (points == null || points.length != 4) {
            throw new IllegalStateException("Rectangle points are not properly initialized.");
        }
        /*
             P1   _____L4_____
               L1 |          |L3
                  |____L2____|
         */
        Line2D.Double line1 = new Line2D.Double(points[0], points[1]);
        Line2D.Double line2 = new Line2D.Double(points[1], points[3]);
        Line2D.Double line3 = new Line2D.Double(points[3], points[2]);
        Line2D.Double line4 = new Line2D.Double(points[0], points[2]);
        return Math.min(Math.min(line1.ptSegDist(camLoc),line2.ptSegDist(camLoc)), Math.min(line3.ptSegDist(camLoc), line4.ptSegDist(camLoc)));
    }

    @Override
    public boolean contains(double x, double y) {

          /*
             P1 ____________P3
                |          |
              P2|__________|P4
         */
        Point2D.Double p1 = points[0];
        Point2D.Double p2 = points[1];
        Point2D.Double p3 = points[2];

        return (x>=p1.x&&x<=p3.x)&&(y>=p1.y&&y<=p2.y);
    }

}
