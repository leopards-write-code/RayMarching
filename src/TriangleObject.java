import java.awt.*;
import java.awt.geom.Line2D;

public class TriangleObject extends CollisionObject {
    private double[] x;
    private double[] y;

    /**
     *
     * @param x-an arr of x coordinates
     * @param y- an arr of y coordinates
     * @param c- color of triangle
     */
    public TriangleObject(double[] x, double[] y, Color c){
        super(x[0], y[0],c);
        this.x = x;
        this.y = y;
    }

    public double getX1() {
        return x[0];
    }
    public double getY1(){
        return y[0];
    }
    public double getX2() {
        return x[1];
    }
    public double getY2(){
        return y[1];
    }
    public double getX3() {
        return x[2];
    }
    public double getY3(){
        return y[2];
    }

    public double[] getXVal(){
        return x;
    }
    public double[] getYVal(){
        return y;
    }

    @Override
    public void drawObject(Graphics2D g2d) {
        Polygon p = new Polygon(new int[]{(int)getX1(), (int)getX2(), (int)getX3()},new int[]{(int)getY1(), (int)getY2(), (int)getY3()},3 );
        g2d.setColor(getColor());
        g2d.fillPolygon(p);
    }
    public double computeDistance(double cameraX, double cameraY){
        /*         /\
                  /  \
                 /    \
                /______\

         */
        Line2D.Double line1 = new Line2D.Double(getX1(), getY1(), getX2(), getY2());
        Line2D.Double line2 = new Line2D.Double(getX2(), getY2(), getX3(), getY3());
        Line2D.Double line3  = new Line2D.Double(getX3(), getY3(), getX1(), getY1());
        return Math.min(line1.ptSegDist(cameraX, cameraY), Math.min(line2.ptSegDist(cameraX, cameraY), line3.ptSegDist(cameraX, cameraY)));
    }
    /**
     *
     * @return points in the format [x0, y0, x1, y1, ... ].
     */
    @Override
    public boolean contains(double x, double y) {
        // https://www.geeksforgeeks.org/check-whether-a-given-point-lies-inside-a-triangle-or-not/
        double triangleArea = getArea();
        double tolerance = 1e-6;
        double aOne = getArea(x, y, getX2(), getY2(), getX3(), getY3());
        double aTwo = getArea(getX1(), getY1(), x, y, getX3(), getY3());
        double aThree = getArea(getX1(), getY1(), getX2(), getY2(), x, y);
        return  Math.abs(triangleArea - (aOne + aTwo + aThree)) < tolerance;
    }
    public double getArea(){
        /*
             https://www.mathopenref.com/coordtrianglearea.html
              Given the coordinates of the three vertices of any triangle, the area of the triangle is given by:
              (Ax(	 By 	−	 Cy ) 	+	 Bx 	 (	 Cy 	−	 A	y 	) 	+	 Cx(Ay 	−	 By ))/2
         */
        // a = 1/2BH
        return Math.abs(getX1()*(getY2()-getY3())+getX2()*(getY3()-getY1())+getX3()*(getY1()-getY2()))/2.;
    }
    public double getArea(double x1, double y1, double x2, double y2, double x3, double y3){

        return Math.abs(x1*(y2-y3)+x2*(y3-y1)+x3*(y1-y2))/2.;
    }
}
