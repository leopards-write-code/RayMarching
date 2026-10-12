import java.awt.*;
import java.awt.geom.Ellipse2D;
import java.awt.geom.Line2D;
import java.awt.geom.Point2D;

public class March implements Drawable{
    private Point2D.Double startLoc;

    private double radius;
    private Line2D.Double line;

    public March(Point2D.Double start, double radius){
        startLoc = start;
        this.radius = radius;
        this.line = new Line2D.Double(startLoc, new Point2D.Double(startLoc.x+radius,start.y ));
    }

    public March(double x, double y, double radius) {
        startLoc = new Point2D.Double(x, y);
        this.radius = radius;
        this.line = new Line2D.Double(startLoc, new Point2D.Double(x+radius,y+radius));
    }
    public void drawObject(Graphics2D g2d){
        Ellipse2D circle = new Ellipse2D.Double(startLoc.x-radius, startLoc.y-radius, radius*2, radius*2);
        g2d.setColor(new Color(93, 63, 211));
        g2d.draw(circle);

        Point2D.Double p2 = new Point2D.Double(startLoc.x+radius, startLoc.y);
        Line2D.Double lineSeg = new Line2D.Double(startLoc, p2);
        g2d.draw(lineSeg);
    }
    public void drawObject(Graphics2D g2d, Camera c){
        Ellipse2D circle = new Ellipse2D.Double(startLoc.x-radius, startLoc.y-radius, radius*2, radius*2);
        g2d.setColor(new Color(93, 63, 211));
        g2d.draw(circle);
        double angle = c.getCameraAngle();
        double endX = startLoc.x +radius*Math.cos(angle);
        double endY = startLoc.y+radius*Math.sin(angle);
        Point2D.Double p2 = new Point2D.Double(endX, endY);
        Line2D.Double lineSeg = new Line2D.Double(startLoc, p2);
        g2d.draw(lineSeg);
    }

    private boolean inBounds(Point2D.Double p){
        return false;
    }
}
