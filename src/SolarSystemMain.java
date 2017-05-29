import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.util.Random;
import java.io.File;

public class SolarSystemMain extends JPanel
{
    GameCanvas canvas;
    Planet[] planets = new Planet[9];
    boolean[] planetDescVisible = new boolean[9];
    //x, y, velX, velY, mass, diameter, color, orbit-speed
    
    double scale = 1;
    boolean paused = false;
    BufferedImage[] bimgs = new BufferedImage[9];
    String[][] planetDesc;
    int selected = -1;
    
    final static int DELAY = 10; //in milliseconds
    /**
     * Constructor for objects of class SolarSystemMain
     */
    public SolarSystemMain()
    {
          canvas = new GameCanvas();
          canvas.setPreferredSize(new Dimension(1200, 800));
          add(canvas);
            planets[0] = new Planet(600, 450, -4.7, 0, 999, 8, Color.GRAY, 1000); //Mercury
            planets[1] = new Planet(752, 400, 0, 2.5, 999, 12, Color.PINK, 1000); //Venus
            planets[2] = new Planet(600, 150, 1.8, 0, 999, 11, Color.BLUE, 2000); //Earth
            planets[3] = new Planet(650, -50, 1.2, 0, 999, 7, Color.RED, 2000); //Mars
            planets[4] = new Planet(600, -100, 1.2, 0, 999, 20, new Color(255,140,0), 2000); //Jupiter
            planets[5] = new Planet(600, -150, 1.2, 0, 999, 15, new Color(112,128,144), 2000); //Saturn
            planets[6] = new Planet(600, -175, 1.2, 0, 999, 15, new Color(196,233,238), 2000); //Uranus
            planets[7] = new Planet(0, 400, 0, -1.2, 999, 13, Color.CYAN, 2000);//Neptune
            planets[8] = new Planet(600, 400, .1, 0, 1000, 30, Color.ORANGE, 0);//Sun
            
            planetDesc = new String[][]{ 
                            {"Mercury","Diameter: " + planets[0].diameter*1058+ " kilometers",
                                "Mass: .0773 Earth Mass",
                                "Atmosphere Type: Thin",
                                "Average Temperature: 180°C",
                                "Seasonal Range: -130°C to 380°C",
                                "Average Day Length: 3.1 Earth Days",
                                "Terrestrial planet with metallic core"},
                            {"Venus","Diameter: " + planets[1].diameter*1058+ " kilometers",
                                	 "Mass: 1.82 Earth Mass",
                                     "Atmosphere Type: Medium Thin",
                                     "Average Temperature: -70°C",
                                     "Seasonal Range: -150°C to 20°C",
                                     "Average Day Length: .9 Earth Days",
                                     "Terrestrial planet with metallic core"
                                },
                            { "Earth","Diameter: " + planets[2].diameter*1058+ " kilometers",
                                    "Mass: 1 Earth Mass",
                                    "Atmosphere Type: Thin",
                                    "Average Temperature: -90°C",
                                    "Seasonal Range: -190°C to °15C",
                                    "Average Day Length: 1 Earth Day",
                                    "Wandering Dwarf Planet with metallic core"	
                               },
                            { "Mars","Diameter: " + planets[3].diameter*1058+ " kilometers",
                               	"Mass: 1.39 Earth Mass",
                                "Atmosphere Type: Medium Thick",
                                "Average Temperature: 20°C",
                                "Seasonal Range: -90°C to 60°C",
                                "Average Day Length: .8 Earth Days",
                                "Terrestrial planet with metallic core",
                                "Supports life"},
                            {"Jupiter","Diameter: " + planets[4].diameter*1058+ " kilometers",
                                    "Mass: 14.9 Earth Mass",
                                    "Atmosphere Type: Thin",
                                    "Average Temperature: -210°C",
                                    "Seasonal Range: -225°C to -195°C",
                                    "Average Day Length: .6 Earth Days",
                                    "Gas Giant with dense gassy core"},
                                {"Saturn","Diameter: " + planets[5].diameter*3058+ " kilometers",
                                    "Mass: 300000 Earth Mass",
                                    "Average Temperature: 5083°C",
                                    "Yellow Dwarf, Main-sequence Star"}, 
                                {"Uranus","Diameter: " + planets[6].diameter*3058+ " kilometers",
                                        "Mass: 300000 Earth Mass",
                                        "Average Temperature: 5083°C",
                                        "Yellow Dwarf, Main-sequence Star"},
                            {"Neptune","Diameter: " + planets[7].diameter*1058+ " kilometers",
                                "Mass: 14.9 Earth Mass",
                                "Atmosphere Type: Thin",
                                "Average Temperature: -210°C",
                                "Seasonal Range: -225°C to -195°C",
                                "Average Day Length: .6 Earth Days",
                                "Gas Giant with dense gassy core"},
                            {"Sun","Diameter: " + planets[8].diameter*3058+ " kilometers",
                                "Mass: 300000 Earth Mass",
                                "Average Temperature: 5083°C",
                                "Yellow Dwarf, Main-sequence Star"},
                         };
          
          bimgs[0] = loadImage("mercury.jpg");
          bimgs[1] = loadImage("Venus.jpg");
          bimgs[2] = loadImage("bluemarble.jpg");
          bimgs[3] = loadImage("mars.jpg");
          bimgs[4] = loadImage("jupiterNasa.jpg");
          bimgs[5] = loadImage("saturn.jpg");
          bimgs[6] = loadImage("uranus.jpg");
          bimgs[7] = loadImage("neptune.jpg");
          bimgs[8] = loadImage("sun.jpg");
          
          
          setBackground(Color.BLACK);
                    // Create a new thread
          Thread gameThread =  new Thread() {
             // Override run() to provide the running behavior of this thread.
             /* (non-Javadoc)
             * @see java.lang.Thread#run()
             */
            /* (non-Javadoc)
             * @see java.lang.Thread#run()
             */
            @Override
             public void run() {
                gameLoop();
             }
          };
          // Start the thread. start() calls run(), which in turn calls gameLoop().
          gameThread.start();
    }
   
    
    public static BufferedImage loadImage(String ref) {  
        BufferedImage bimg = null;  
        try {  
  
            bimg = ImageIO.read(new File(ref));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bimg;  
    }  
    private void gameLoop() {
      // Regenerate the game objects for a new game
   
      // Game loop
      while (true) {
            // Update the state and position of all the game objects,
            // detect collisions and provide responses.
            if (!paused)
            {
                //s.move();
                for(int i = 0; i < planets.length-1; i++)
                {
                    planets[i].update(planets[8].getX(),planets[8].getY(),planets[8].getMass());
                }
            }
         // Refresh the display
         repaint();
         // Delay timer to provide the necessary delay to meet the target rate

         try {
            // Provides the necessary delay and also yields control so that other thread can do work.
            Thread.sleep(DELAY);
         } catch (InterruptedException ex) { }
      }
   }
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JFrame frame = new JFrame("Solar System Model - Naren Kolli");
            // Set the content-pane of the JFrame to an instance of main JPanel
            frame.setContentPane(new SolarSystemMain());  // main JPanel as content pane
            //frame.setJMenuBar(menuBar);          // menu-bar (if defined)
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); // center the application window
            frame.setVisible(true);            // show it
         }
      });
    }
    class GameCanvas extends JPanel implements KeyListener, MouseListener {
      // Constructor
      public GameCanvas() {

         setFocusable(true);  // so that this can receive key-events
         requestFocus();
         addKeyListener(this);
         addMouseListener(this);
      }

      // Override paintComponent to do custom drawing.
      // Called back by repaint().
      @Override
      public void paintComponent(Graphics g) {
         for(Planet p : planets)
            p.draw(g,scale);
         
         //following code is for creating stars - will need to work on them pausing
         Random r = new Random();
         for(int count=0;count<=100;count++) {
        	 g.setColor(Color.WHITE); 
        	 g.drawOval(r.nextInt(1500),r.nextInt(1500),1,1);
        	 }
         //
         for (int i = 0; i < planets.length; i++)
         {
             if (planets[i].getDescVisible())
                planets[i].dispDesc(g,scale);
         }
         if (selected > -1)
         {
             g.drawImage(bimgs[selected],0,0,200,200,Color.WHITE,null);
             g.setFont(new Font("Dialog", Font.PLAIN, 25));
             g.setColor(Color.WHITE);
             for(int i = 0; i < planetDesc[selected].length; i++)
             {
                 g.drawString(planetDesc[selected][i], 0, 210+i*30);
             }
         }
      }
      // KeyEvent handlers
      @Override
      public void keyPressed(KeyEvent e) { }
      
      @Override
      public void keyReleased(KeyEvent e) { 
          if(e.getKeyCode() == KeyEvent.VK_SPACE)
          {
              paused = !paused;
          }
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          {
              System.exit(0);
          }
          if(e.getKeyCode() == KeyEvent.VK_MINUS && scale > 0)
            scale -= .1;
          if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS)
            scale += .1;
      }
   
      @Override
      public void keyTyped(KeyEvent e) { }
      @Override
      public void mousePressed(MouseEvent e) { }
      public void mouseReleased(MouseEvent e) {
          for(int i = 0; i < planets.length; i++)
              if (planets[i].containsPt(e.getX(), e.getY(), scale))
              {
                  
                  planets[i].setDescVisible(!planets[i].getDescVisible());
                  if(planets[i].getDescVisible())
                    selected = i;
                  else 
                    selected = -1;
              }
      }
      @Override
      public void mouseEntered(MouseEvent e) { }
      @Override
      public void mouseExited(MouseEvent e) { }
      @Override
      public void mouseClicked(MouseEvent e) { }
   }
}




