/*
    NAME: Victor Li
    DATE: 3/25/2025-3/28/2024
    PROJECT: Ray Marching Project
    Description: In this lab, we're
going to explore the topic of sphere-tracing: a type of ray marching algorithm.

 */

import java.awt.*;
import java.awt.event.*;
import java.awt.geom.Point2D;
import java.io.File;
import java.util.ArrayList;
import javax.sound.sampled.*;
import javax.swing.*;
// java.awt.geom


public class myFrame extends JFrame implements ActionListener {
    private Timer timer;
    private JPanel southPanel = new JPanel();
    private JPanel drawingPanel = new JPanel();
    private JButton reset = new JButton("reset");
    private JButton spin = new JButton("spin");
    private JButton help = new JButton("help");
    private JButton music = new JButton("music");
    private JButton quit = new JButton("quit");


    private ArrayList<JButton>buttons;
    private ArrayList<CollisionObject>shapes;

    private int cpHeight;
    private int cpWidth;
    private Camera c;
    private Clip clip;
    private boolean musicOn;
    private boolean isSpinning;

    public myFrame() {
        setTitle("Designed by \uD83C\uDD86\uD83C\uDD7E\uD83C\uDD7D\uD83C\uDD73\uD83C\uDD74\uD83C\uDD81 \uD83C\uDD70\uD83C\uDD7F\uD83C\uDD7F\uD83C\uDD7B\uD83C\uDD78\uD83C\uDD72\uD83C\uDD70\uD83C\uDD83\uD83C\uDD78\uD83C\uDD7E\uD83C\uDD7D\uD83C\uDD82"+" 2025©");
        setVisible(true);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);


        //centers the frame
        Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
        setBounds((int) (.5*(screensize.width-getWidth())), (int)(.5*(screensize.height-getHeight())), getWidth(), getHeight() );
        setResizable(false);
        initGUI();
        pack();
    }
    private void initGUI(){

        addPanels();
        addButtons();
        addButtonListeners();
        prepareCollisionObjects();
        prepareCamera();
        addMouseListener();

        // add anything else here plus other methods below

    }
    private void addPanels(){
        drawingPanel = new DrawingPanel();
      //  drawingPanel.removeAll();

        drawingPanel.setBackground(Color.BLACK);
        drawingPanel.setPreferredSize(new Dimension(800, 600));
        shapes = new ArrayList<>();

       southPanel.setBackground(new Color(127, 0, 255));
       getContentPane().add(drawingPanel, BorderLayout.CENTER);
       getContentPane().add(southPanel, BorderLayout.SOUTH);

        cpHeight = 600;
        cpWidth = 800;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                drawingPanel.requestFocusInWindow();

            }
        });


    }
    private void addButtons(){
        buttons = new ArrayList<>();
        buttons.add(reset);
        buttons.add(spin);
        buttons.add(help);
        buttons.add(music);
        buttons.add(quit);
        musicOn = false;
        isSpinning = false;
        reset.setBackground(Color.RED);
        spin.setBackground(Color.PINK);
        quit.setBackground(Color.CYAN);
        help.setBackground(Color.YELLOW);
        music.setBackground(Color.ORANGE);
        for(JButton b: buttons){
            southPanel.add(b);
        }
    }
    private void addButtonListeners(){
        for(JButton b: buttons){
            for (ActionListener listener : b.getActionListeners()) {
                b.removeActionListener(listener);
            }
        }
        for (JButton b : buttons) {
            b.addActionListener(this);
        }
    }
    private void prepareCollisionObjects(){
        int lowerBound = drawingPanel.getHeight()/30;
        int upperBound= drawingPanel.getHeight()/12;
        int numRect = (int)(Math.random()*7)+3;
        int numCircles = (int)(Math.random()*7)+3;
        int numTriangles = (int)(Math.random()*7)+3;
        prepareRect(lowerBound, upperBound, numRect);
        prepareCircle(lowerBound, upperBound, numCircles);
        prepareTriangle(lowerBound, upperBound, numTriangles);
        drawingPanel.repaint();


    }
    private void prepareRect(int min, int max, int n){
        int r = (max-min)+1;
        for(int i = 0; i<n; i++){
            double h = (randomVal(r)+min)*100;
            double w = (randomVal(r)+min)*100;
            double x = Math.max(0, randomVal((cpWidth-w)));
            double y = Math.max(0, randomVal((cpHeight-h)));
            Color c = getRandomColor();
            if(c.equals(Color.BLACK))c = Color.PINK;
            if(h<5.0) h = 5.0;
            if(w<5.0) w = 5.0;


            shapes.add(new RectangleObject(x, y, w, h, c));
        }


    }
    private void prepareCircle(int min, int max,int n){
        int rand = (max-min)+1;
        for(int i = 0; i<n; i++){
            double r = (randomVal(rand)+min)*100;
            double x = Math.max(0, (randomVal(cpWidth- (r*2))));
            double y = Math.max(0, (randomVal(cpHeight-(r*2))));
            if(r<10){
                r= 10.0; //Ensures that the shape isnt way too small
            }
            shapes.add(new CircleObject(x, y, r, getRandomColor()));

        }
    }
    private void prepareTriangle(int min, int max, int n){
        for(int numTri = 0; numTri<n; numTri++){
            double[] x = new double[3];
            double[] y = new double[3];
            double x1 = randomVal(cpWidth);
            double y1 = randomVal(cpHeight);
            double proximity = 75.;
            x[0] = x1;
            y[0]=y1;
            for(int i = 2; i<n; i++){
                for(int j = 1; j<3; j++){
                    x[j]=Math.max(0., Math.min(cpWidth, x1+randomVal(proximity*2)-proximity));
                }
                for(int k = 1; k<3; k++){
                    y[k]=Math.max(0., Math.min(cpHeight, y1+randomVal(proximity*2)-proximity));
                }
                shapes.add(new TriangleObject(x, y, getRandomColor()));

            }
        }

    }
    private void prepareCamera(){
        double x = (cpWidth/2.0)-10;
        double y = (cpHeight/2.0)-10;
        c = new Camera(x, y);
    }

    /**
     * @return - a random Color
     */
    private Color getRandomColor(){
        return new Color(randomColorVal(), randomColorVal(), randomColorVal());
    }

    /**
     * @return - an integer between 0 and 255, inclusive
     */
    private int randomColorVal(){
        return (int)(Math.random()*256);
    }
    public static void main(String[] args) {
        new myFrame();
    }

    /*
    Math.random() returns a random number between 0.0-0.99.
    (int)(Math.random()*range) + min moves the random number into a range starting from a minimum number.
   The range is the (max number - min number + 1).


     */
    private double randomVal(double range){

        return (Math.random()*range);
    }

    @Override
    public void actionPerformed(ActionEvent ae) {
        Object source = ae.getSource();
        if(source.equals(reset)){
            int r  = JOptionPane.showConfirmDialog(null, "Are you sure you want to reset?", "RESET", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(r==JOptionPane.YES_OPTION){

                drawingPanel.removeAll();
                initGUI();

                timer.stop();
                drawingPanel.revalidate();

                drawingPanel.setBackground(Color.BLACK);
                JOptionPane.showMessageDialog(null, "The game has been reset! シ㋛☺");
            }
        }

        else if(source.equals(help)){
            JOptionPane.showMessageDialog(null, "Put your cursor anywhere on the canvas to see the ray"+"\n"+"Except of couse, if you put it over a shape"+"\n"+"Hit the left arrow key to make it rotate to the left \n and right to make it go right\n To make it spin continously, hold down the arrow key of your choice");
            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    drawingPanel.requestFocusInWindow();

                }
            });
        }
        else if(source.equals(quit)){
            int r = JOptionPane.showConfirmDialog(null, "Are you sure you want to quit?", "QUIT",JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
            if(JOptionPane.YES_OPTION==r)System.exit(0);
            else{
                drawingPanel.requestFocusInWindow();
            }
        }
        else if(source.equals(spin)){
            if(!isSpinning) {
                int r = JOptionPane.showConfirmDialog(null, "Are you you want it to spin continuously? hitting an arrow key or pressing spin again will stop this action ", "Spin", JOptionPane.YES_NO_OPTION, JOptionPane.QUESTION_MESSAGE);
                if (JOptionPane.YES_OPTION == r) {
                    isSpinning = true;
                    timer = new Timer(40, x -> {
                        c.rotateRight();
                        repaint();
                        });
                    timer.start();
                    SwingUtilities.invokeLater(new Runnable() {
                        @Override
                        public void run() {
                            drawingPanel.requestFocusInWindow();

                        }
                    });
                }
            }
            else{
                isSpinning = false;
                timer.stop();
                JOptionPane.showMessageDialog(null, "Spinning has been stopped");
                drawingPanel.requestFocusInWindow();
            }
        }
        else{
            musicOn = !musicOn;
            if(musicOn){
                File soundFile = new File("Nessun_Dorma.wav");
                playMusic(soundFile);
            }
            else stopMusic();

            SwingUtilities.invokeLater(new Runnable() {
                @Override
                public void run() {
                    drawingPanel.requestFocusInWindow();

                }
            });
        }
    }



    private void playMusic(File f){
        try{
            AudioInputStream audioInputStream = AudioSystem.getAudioInputStream(f);
            clip = AudioSystem.getClip();
            clip.open(audioInputStream);
            long startPos = 5000000;

            clip.setMicrosecondPosition(startPos);
            clip.setLoopPoints((int) startPos, -1);
            clip.loop(Clip.LOOP_CONTINUOUSLY);
        }catch(UnsupportedAudioFileException e){
            System.out.println("The audio file is not supported");

        }catch(LineUnavailableException e){
            System.out.println("There are issues with this system's audio resources");

        }catch(SecurityException e){
            System.out.println("Make sure the file is accessible and you have proper permissions");

        }catch (Exception e){
            e.printStackTrace();
        }

    }
    private void stopMusic() {
        if (clip != null && clip.isRunning()) {
            clip.stop();  // Stop the music
            clip.close();
        }
    }

    public void addMouseListener(){
        drawingPanel.setFocusable(true);
//        drawingPanel.requestFocusInWindow();

        drawingPanel.addFocusListener(new FocusAdapter() {
            @Override
            public void focusGained(FocusEvent e) {

                drawingPanel.requestFocusInWindow();
            }
        });
        drawingPanel.addKeyListener(new KeyAdapter() {
            @Override
            public void keyPressed(KeyEvent e) {
                if(isSpinning){
                    isSpinning = false;
                    timer.stop();
                }
                int val = e.getKeyCode(); // Log key press
                if(val == KeyEvent.VK_LEFT){
                    c.rotateLeft();
                    repaint();
                }
                else if(val == KeyEvent.VK_RIGHT){
                    c.rotateRight();
                    repaint();
                }
            }
        });
        drawingPanel.addMouseMotionListener(new MouseAdapter() {
            @Override
            public void mouseMoved(MouseEvent e) {
                double x = e.getX();
                double y = e.getY();

                 c.setVals(x, y);

                 if(shapes!=null&&c!=null)c.setCameraRadius(determineClosestShapeDistance()); //line 5
                 drawingPanel.repaint();
                 }
            });




    }
    private double determineClosestShapeDistance(){
        double distance = Double.MAX_VALUE;

        for(CollisionObject obj: shapes){

            if(obj instanceof CircleObject){
                CircleObject c = (CircleObject) obj;
                double current = c.computeDistance(this.c.getX(), this.c.getY());
                if(current<distance){
                    distance = current;
                }
            }
            else if(obj instanceof RectangleObject){
                RectangleObject r = (RectangleObject) obj;
                double current = r.computeDistance(c.getX(), c.getY());
                if(current<distance){
                    distance =  current;
                }
            }
            else{
                TriangleObject tri = (TriangleObject) obj;
                double current = tri.computeDistance(c.getX(), c.getY());
                if(current<distance){
                    distance = current;
                }
            }

        }
        return distance;
    }
    private double determineClosestShapeDistance(double x, double y){

        double distance = Double.MAX_VALUE;
        for(CollisionObject obj: shapes){

            if(obj instanceof CircleObject){
                CircleObject c = (CircleObject) obj;
                double current = c.computeDistance(x, y);
                if(current<distance){
                    distance = current;

                }
            }
            else if(obj instanceof RectangleObject){
                RectangleObject r = (RectangleObject) obj;
                double current = r.computeDistance(x, y);
                if(current<distance){
                    distance =  current;

                }
            }
            else{
                TriangleObject tri = (TriangleObject) obj;
                double current = tri.computeDistance(x, y);
                if(current<distance){
                    distance = current;

                }
            }

        }
        if(distance<=0)return 0.1;
        return distance;
    }

    public  int getCpWidth(){
        return cpWidth;
    }
    public int getCpHeight(){
        return cpHeight;
    }


    private boolean inBounds(double x, double y){
        return x>=0&&x<getCpWidth()&&y>=0&&y<getCpHeight();
    }

    private class DrawingPanel extends JPanel{
        public void paintComponent(Graphics g){
            super.paintComponent(g);

            for(CollisionObject s: shapes){
                s.drawObject((Graphics2D) g);
            }



            Ray r = new Ray();
            double x = c.getX();
            double y = c.getY();

            for (CollisionObject s : shapes) {
                if (s.contains( x,  y)) {


                    return;
                }
            }
            c.drawObject((Graphics2D) g);
            double distance;
            do{
                double angle = c.getCameraAngle();
                distance = determineClosestShapeDistance(x, y);


                March m = new March(x, y, distance);
                r.addMarch(m);

                x+=distance*Math.cos(angle);
                y+=distance*Math.sin(angle);


            }while(distance>=.01&&inBounds(x, y));
            r.drawObject((Graphics2D) g, c);
        }
    }
}
