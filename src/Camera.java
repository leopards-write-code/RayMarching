import java.awt.*;
import java.awt.geom.Ellipse2D;

public class Camera extends CollisionObject implements Drawable {
    private double angle;
    private double radius;
    public Camera(double x, double y){
        super(x, y);
        angle = 0.01;
        radius = 10.;
    }
    public void setCameraRadius(double camRad){
        radius = camRad;
    }

    @Override
    public void drawObject(Graphics2D g2d) {
        Ellipse2D camera = new Ellipse2D.Double(getX()-radius, getY()-radius, radius*2, radius*2);
        g2d.setColor(new Color(93, 63, 211));
        g2d.draw(camera);
    }
    // The camera center is cameraX and cameraY
    @Override
    public double computeDistance(double cameraX, double cameraY) {
        return 0;
    }
    public void rotateLeft(){
        angle-= Math.PI/12;
    }
    public void rotateRight(){
        angle+= Math.PI/12;
    }
    public double getCameraAngle(){
        return angle;
    }

    @Override
    public boolean contains(double x, double y) {
        return Math.pow(x - getX(), 2) + Math.pow(y - getY(), 2) <= Math.pow(radius, 2);
    }

}
