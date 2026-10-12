import java.awt.*;
import java.util.ArrayList;
import java.util.List;

public class Ray implements Drawable{
    private ArrayList<March> marches;
     public Ray(){
         marches = new ArrayList<>();
     }
    public Ray(ArrayList<March>marches){
        this.marches = marches;
    }
    public void addMarch(March m){
         marches.add(m);
    }
    public void drawObject(Graphics2D g2d){
        for(March m: marches){
            m.drawObject(g2d);
        }
    }
    public void drawObject(Graphics2D g2d, Camera c){
        for(March m: marches){
            m.drawObject(g2d, c);
        }
    }
}
