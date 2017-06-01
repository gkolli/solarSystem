import java.util.*;
import java.awt.*;
import java.awt.event.*;
import javax.swing.*;
import java.awt.image.*;
import javax.imageio.ImageIO;
import java.io.File;

public class SolarSystemMain extends JPanel
{
    Model model;
    CelestialBody[] celestialBodies = new CelestialBody[9];
    boolean[] descriptionSeen = new boolean[9];
    
    final static int DELAY = 50; 
    double size = 1;
    BufferedImage[] bimgs = new BufferedImage[9];
    String[][] description;
    boolean stop = false;
    int clicked = -1;
        
    public SolarSystemMain()
    {
          model = new Model();
          model.setPreferredSize(new Dimension(1200, 1200));
          add(model);
          
            celestialBodies[0] = new CelestialBody(600, 450, -4.7, 0, 9, 8, Color.GRAY, 1000); //Mercury
            celestialBodies[1] = new CelestialBody(752, 400, 0, 2.5, 900, 12, new Color(207,153,52), 1000); //Venus
            celestialBodies[2] = new CelestialBody(600, 150, 1.8, 0, 900, 11, Color.BLUE, 2000); //Earth
            celestialBodies[3] = new CelestialBody(650, -50, 1.2, 0, 900, 7, Color.RED, 2000); //Mars
            celestialBodies[4] = new CelestialBody(600, -100, 1.2, 0, 900, 20, new Color(255,140,0), 2000); //Jupiter
            celestialBodies[5] = new CelestialBody(600, -150, 1.2, 0, 900, 15, new Color(112,128,144), 2000); //Saturn
            celestialBodies[6] = new CelestialBody(600, -175, 1.2, 0, 900, 15, new Color(196,233,238), 2000); //Uranus
            celestialBodies[7] = new CelestialBody(0, 400, 0, -1.2, 900, 13, new Color(66, 98, 243), 2000);//Neptune
            
            celestialBodies[8] = new CelestialBody(600, 400, .1, 0, 1000, 30, Color.ORANGE, 0);//Sun
            
                      
                    
          setBackground(Color.BLACK);
         
          description = new String[][]{ 
              {"Mercury","Diameter: " + celestialBodies[0].getDiameter()*1058+ " kilometers",
                  "Mass: 0.330 x 10^(24) kg",
                  "Atmosphere Type: Thin",
                  "Average Temperature: 167 degrees C",
                  "Average Day Length: 3.1 Earth Days",
                  "Inner Planet - Closest to the Sun"},
              {"Venus","Diameter: " + celestialBodies[1].getDiameter()*1058+ " kilometers",
                  	 "Mass: 4.87 x 10^(24) kg",
                       "Atmosphere Type: Medium Thin",
                       "Average Temperature: 464 degrees C",                             
                       "Average Day Length: .9 Earth Days",
                       "Inner Planet - Known as Earth's Twin"
                  },
              { "Earth","Diameter: " + celestialBodies[2].getDiameter()*1058+ " kilometers",
                      "Mass: 5.97 x 10^(24) kg",
                      "Atmosphere Type: Thin",
                      "Average Temperature: 15 degrees C",
                      "Average Day Length: 1 Earth Day",
                      "Inner Planet - Our Home, the Blue Marble"	
                 },
              { "Mars","Diameter: " + celestialBodies[3].getDiameter()*1058+ " kilometers",
                 	"Mass: 0.642 x 10^(24) kg",
                  "Atmosphere Type: Medium Thick",
                  "Average Temperature: -65 degrees C",
                  "Average Day Length: .8 Earth Days",
                  " Inner Planet - Known as the Red Planet"
                  },
              {"Jupiter","Diameter: " + celestialBodies[4].getDiameter()*1058+ " kilometers",
                      "Mass: 1898 x 10^(24) kg",
                      "Atmosphere Type: Thick",
                      "Average Temperature: -110 degrees C",
                      "Average Day Length: .6 Earth Days",
                      "Outer Planet - Largest planet in the Solar System"},
                  {"Saturn","Diameter: " + celestialBodies[5].getDiameter()*3058+ " kilometers",
                      "Mass: 568 x 10^(24) kg",
                      "Average Temperature: -140 degrees C",
                      "Atmosphere Type: Thick",
                      "Outer Planet - Known for its Rings"}, 
                  {"Uranus","Diameter: " + celestialBodies[6].getDiameter()*3058+ " kilometers",
                          "Mass: 86.8 x 10^(24) kg",
                          "Atmosphere Type: Thick",
                          "Average Temperature: -195 degrees C",
                          "Outer Planet - Interior Composed of Ices and Rock"},
              {"Neptune","Diameter: " + celestialBodies[7].getDiameter()*1058+ " kilometers",
                  "Mass: 102 x 10^(24) kg",
                  "Atmosphere Type: Thin",
                  "Atmosphere Type: Thick",
                  "Average Temperature: -200 degrees C",
                  "Average Day Length: .6 Earth Days",
                  "Outer Planet - Only planet found by mathematical prediction, not empirical observation"},
              {"Sun","Diameter: " + celestialBodies[8].getDiameter()*3058+ " kilometers",
                  "Mass: 1.989 × 10^30 kg",
                  "Atmosphere Type: Thick",
                  "Average Temperature: 5505 degrees C",
                  "Largest Body in the Solar System"},
           };

           bimgs[0] = getImage("mercury.jpg"); //stackOverflow
           bimgs[1] = getImage("Venus.jpg");
           bimgs[2] = getImage("bluemarble.jpg");
           bimgs[3] = getImage("mars.jpg");
           bimgs[4] = getImage("jupiterNasa.jpg");
           bimgs[5] = getImage("saturn.jpg");
           bimgs[6] = getImage("uranus.jpg");
           bimgs[7] = getImage("neptune.jpg");
           bimgs[8] = getImage("sun.jpg");
           

          Thread thread =  new Thread() {
     
            @Override
             public void run() {
                gameLoop();
             }
          }; 
          
          thread.start();
    }
   
    
    public static BufferedImage getImage(String ref) {  //loading the image
        BufferedImage bimg = null;  
        try {  
  
            bimg = ImageIO.read(new File(ref));  
        } catch (Exception e) {  
            e.printStackTrace();  
        }  
        return bimg;  
    }  
    private void gameLoop() {
    	
      while (true) {
            if (!stop)
            {
                for(int i = 0; i < celestialBodies.length-1; i++)
                {
                	celestialBodies[i].update(celestialBodies[8].getXPosition(),celestialBodies[8].getYPosition(),celestialBodies[8].getMass());
                }
            }
         repaint();

         try {
            Thread.sleep(DELAY);
         } catch (InterruptedException ex) { }
      }
   }
    

    class Model extends JPanel implements KeyListener, MouseListener {
      public Model() {

         setFocusable(true); //wasn't working, went to stackOverflow for query
         requestFocus();
         addKeyListener(this);
         addMouseListener(this);
      }

      
      public void paintComponent(Graphics g) {


         for(CelestialBody body : celestialBodies)
            body.draw(g,size);
         
         //following code is for creating stars 
         //any more stars, and it got distracting
         for(int count=0;count<=1000;count++) {
        	 g.setColor(Color.WHITE); 
        	 
        	 g.drawOval(50*count,100*count,1,1);
        	g.drawOval(75*count,100*count,1,1);
        	
        	 g.drawOval(100*count,200*count,1,1);
        	 g.drawOval(150*count,200*count,1,1);
        	 g.drawOval(200*count,200*count,1,1);
        	 g.drawOval(250*count,200*count,1,1);
        	 g.drawOval(300*count,200*count,1,1);
        	 g.drawOval(350*count,200*count,1,1);
        	 g.drawOval(400*count,100*count,1,1);
        	 g.drawOval(450*count,100*count,1,1);
        	 g.drawOval(500*count,100*count,1,1);
        	 g.drawOval(550*count,300*count,1,1);
        	 g.drawOval(600*count,300*count,1,1);
        	 g.drawOval(700*count,300*count,1,1);
        	 g.drawOval(800*count,300*count,1,1);
        	 g.drawOval(900*count,300-count,1,1);
        	 g.drawOval(1000*count,300-count,1,1);



        	 }
         //
         
         for (int i = 0; i < celestialBodies.length; i++)
         {
             if (celestialBodies[i].getDescVisible())
            	 celestialBodies[i].dispDesc(g,size);
         }
         if (clicked > -1)
         {
             g.drawImage(bimgs[clicked],0,0,200,200,Color.WHITE,null);
             g.setFont(new Font("Arial", Font.PLAIN, 20));
             g.setColor(Color.WHITE);
             for(int i = 0; i < description[clicked].length; i++)
             {
                 g.drawString(description[clicked][i], 0, 210+i*30);
             }
         }
         
          
          
          String legend = "Solar System Model Legend";
        	String zoomIn = "+ KEY  =  Zoom In";
        	String zoomOut = "- KEY  =  Zoom Out";
        	 String spaceBar = "SPACEBAR = Pause/Play";
        	 String mouseClick =  "MOUSECLICK = More Info on Planet";
        	 String planetNumberInfo = "~The Number beside planet is its";
        	 String planetNumberInfoSecLine = "~distance from the Sun";
        	String escape = "ESC = Quit Model";
      
        g.setFont(new Font("Arial", Font.PLAIN, 10));
        g.setColor(Color.WHITE);
         g.drawString(legend,950,60);
         g.drawString(zoomIn, 950, 100);
         g.drawString(zoomOut, 950, 120);
         g.drawString(spaceBar, 950, 140);
         g.drawString(mouseClick, 950, 160);
         g.drawString(planetNumberInfo, 950, 180);
         g.drawString(planetNumberInfoSecLine,950,200);
         g.drawString(escape, 950, 220);
         
         
         celestialBodies[0].dispDesc(g,size);
         celestialBodies[1].dispDesc(g,size);
         celestialBodies[2].dispDesc(g,size);
         celestialBodies[3].dispDesc(g,size);
         celestialBodies[4].dispDesc(g,size);
         celestialBodies[5].dispDesc(g,size);
         celestialBodies[6].dispDesc(g,size);
         celestialBodies[7].dispDesc(g,size);
         celestialBodies[8].dispDesc(g,size);

      }
      
      public void keyTyped(KeyEvent e) {
    	  
      }
      public void mousePressed(MouseEvent e) {
    	  
      }
      public void mouseReleased(MouseEvent e) {
          for(int i = 0; i < celestialBodies.length; i++)
              if (celestialBodies[i].hitPlanet(e.getX(), e.getY(), size))
              {
                  
            	  celestialBodies[i].setDescVisible(!celestialBodies[i].getDescVisible());
                  if(celestialBodies[i].getDescVisible()) {
                	  clicked = i;
                  }
                  else  {
                	  clicked = -1;
                  }
              }
      }
      public void mouseEntered(MouseEvent e) { 
    	  
      }
      public void mouseExited(MouseEvent e) { 
    	  
      }
      public void mouseClicked(MouseEvent e) { 
    	  
      }
      
      public void keyPressed(KeyEvent e) {
    	  
      }
      
      @Override
      public void keyReleased(KeyEvent e) { 

          if(e.getKeyCode() == KeyEvent.VK_PLUS || e.getKeyCode() == KeyEvent.VK_EQUALS)
        	  size += .1;
          
    	  if(e.getKeyCode() == KeyEvent.VK_MINUS && size > 0)
        	  size -= .1;
    	  
          if(e.getKeyCode() == KeyEvent.VK_SPACE)
          {
              stop = !stop;
          }
          if(e.getKeyCode() == KeyEvent.VK_ESCAPE)
          {
              System.exit(0);
          }
         
      }
   

   }
    
    
    public static void main(String[] args)
    {
        SwingUtilities.invokeLater(new Runnable() {
         @Override
         public void run() {
            JFrame frame = new JFrame("Solar System Model - Naren Kolli");
            frame.setContentPane(new SolarSystemMain());  
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.pack();
            frame.setLocationRelativeTo(null); 
            frame.setVisible(true);            
         }
      });
    }
}




