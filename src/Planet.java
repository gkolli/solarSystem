import java.awt.*;
import java.util.*;
/**
 * This class 
 * @author Naren Kolli
 */
public class Planet
{

	public int mass, diameter;
    private double x, y, velX, velY, orbitSpeed;
    Color color;
    double accel, dirX, dirY;
    double dist, closest=999, furthest=0;
    int period, pcounter;
    boolean descVisible;
    int pathPts[][] = new int[500][2];
    int pathCounter = 0;
    /**
     * Constructor for objects of class Planet
     */
    public Planet(double x, double y, double velX, double velY, int mass, int diameter, Color color, double orbitSpeed)
    {
        // initialise instance variables
        this.x = x;
        this.y = y;
        this.velX = velX;
        this.velY = velY;
        this.mass = mass;
        this.diameter = diameter;
        this.color = color;
        this.orbitSpeed = orbitSpeed;
    }
    public double getX(){return x;}
    public double getY(){return y;}
    public int getMass(){return mass;}
    public int getDiameter(){return diameter;}
    public boolean getDescVisible() {return descVisible;}
    public void setDescVisible(boolean b) {descVisible = b;}
    public boolean containsPt(int x, int y, double scale)
    {
        return (x>600+(getX()-diameter-600)*scale && x<600+(getX()+diameter-600)*scale && 
                y>400+(getY()-diameter-400)*scale && y<400+(getY()+diameter-400)*scale);
    }
    /**
     * An example of a method - replace this comment with your own
     * 
     * @param  y   a sample parameter for a method
     * @return     the sum of x and y 
     */
    
    public void move()
    {
        x += velX;
        y += velY;
    }
    
    public void update(double StarX, double StarY, int StarMass)
    {
        if (descVisible){
            pathPts[pathCounter][0]=(int)(x+.5);
            pathPts[pathCounter][1]=(int)(y+.5);
            pathCounter = (pathCounter+1)%500;
        }
        else{
            pathPts = new int[500][2];
            pathCounter = 0;
        }
        dist = Math.sqrt((StarX - x)*(StarX - x) + (StarY - y)*(StarY - y));
        closest = Math.min(dist,closest);
        furthest = Math.max(dist,furthest);
        
        accel = StarMass/dist/dist;
        //accel = Math.sqrt((dirX*dirX + dirY*dirY));
        
        dirX = (StarX-x)/dist;
        dirY = (StarY-y)/dist;
        
        velX += dirX * accel;
        velY += dirY * accel;
        move();
        pcounter += 1;
        if(x>600 && velX > 0 && x-velX<600)
        {
            period = pcounter;
            pcounter = 0;
        }
    }
    public void draw(Graphics g, double scale)
    {
        g.setColor(color);
        g.fillOval((int)(600+(x-diameter/2-600)*scale), (int)(400+(y-diameter/2-400)*scale),
                    (int)(diameter*scale), (int)(diameter*scale));
    }
    public void dispDesc(Graphics g, double scale)
    {
        g.setColor(color);
        for (int[] a : pathPts)
            g.drawLine(a[0],a[1],a[0],a[1]);
        g.setFont(new Font("Dialog", Font.PLAIN, 16));
        g.setColor(Color.MAGENTA);
        
        g.drawString((Math.round(dist*100.0)/100.0) * 1000000 + " km", 
                    diameter+(int)(600+(x-diameter/2-600)*scale), 16+(int)(400+(y-diameter/2-400)*scale)+diameter);
        
    }
}


