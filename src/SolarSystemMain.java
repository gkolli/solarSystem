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
    // instance variables - replace the example below with your own
    GameCanvas canvas;
    Planet[] planets = new Planet[6];
    boolean[] planetDescVisible = new boolean[6];
    //x, y, velX, velY, mass, diameter, color, orbitspeed
    //Planet m1 = new Moon(780, 400, 0, 2.6, 99, 6, new Color(255,0,25), 200);
    Planet s = new Planet(600, 400, .1, 0, 1000, 25, Color.ORANGE, 0);
    double scale = 1;
    boolean paused = false;
    BufferedImage[] bimgs = new BufferedImage[6];
    String[][] planetDesc;
    int selected = -1;
    
    final static int DELAY = 10; //in milliseconds
    /**
     * Constructor for objects of class SolarSystemMain
     */
    public SolarSystemMain()
    {
          //img1 = new ImageIcon(Toolkit.getDefaultToolkit().getImage("Pluto.gif")).getImage();
          canvas = new GameCanvas();
          canvas.setPreferredSize(new Dimension(1200, 800));
          add(canvas);
            planets[0] = new Planet(600, 450, -4.7, -3, 999, 8, Color.GRAY, 1000);
            planets[1] = new Planet(752, 400, 0, 2.5, 999, 12, Color.PINK, 1000);
            planets[2] = new Planet(600, 150, 1.8, 0, 999, 11, Color.BLUE, 2000);
            planets[3] = new Planet(600, -100, 1.2, 0, 999, 7, Color.RED, 2000); 
            planets[4] = new Planet(0, 400, 0, -1.3, 999, 13, Color.CYAN, 2000);
            planets[5] = s;
            
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
                            {"Neptune","Diameter: " + planets[4].diameter*1058+ " kilometers",
                                "Mass: 14.9 Earth Mass",
                                "Atmosphere Type: Thin",
                                "Average Temperature: -210°C",
                                "Seasonal Range: -225°C to -195°C",
                                "Average Day Length: .6 Earth Days",
                                "Gas Giant with dense gassy core"},
                            {"Sun","Diameter: " + planets[5].diameter*3058+ " kilometers",
                                "Mass: 300000 Earth Mass",
                                "Average Temperature: 5083°C",
                                "Yellow Dwarf, Main-sequence Star"},
                         };
          
          bimgs[0] = loadImage("src/mercury.jpg");
          bimgs[1] = loadImage("src/Venus.jpg");
          bimgs[2] = loadImage("src/bluemarble.jpg");
          bimgs[3] = loadImage("src/mars.jpg");
          bimgs[4] = loadImage("src/neptune.jpg");
          bimgs[5] = loadImage("src/sun.jpg");
          
          
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
                    planets[i].update(s.getX(),s.getY(),s.getMass());
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
        	 g.setColor(Color.LIGHT_GRAY); 
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




