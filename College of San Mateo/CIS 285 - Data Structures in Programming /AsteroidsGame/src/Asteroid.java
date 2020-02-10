import java.awt.Color;
import java.awt.Graphics;

/**
 * @author      Ryan Patterson 
 * @version     Asteroid Assignment
 * @since       2/19/2015
 */

/* Class Rocket
 * 
 * -----------------------------
 *	- x: double
 *	- y: double
 *	- dx: double
 *	- dy: double
 *  + size: int
 * -----------------------------
 *   + Asteroid (double, double, double, double)
 *   + move (LinkedList<Asteroid>): void
 *   + paint (Graphics): void
 *   + hit ( ): void
 *   + nearTo (double, double): boolean
 */


/**
 * Asteroid Class will store all the data of an asteroid. 
 * <p>
 * This class will be used inside a linked list. Each of the variables within the linked 
 * list will be an Asteroid class. 
 * <p>
 * An "asteroid" hold the three main variables, its current size, its current location on
 * the x and y coordinates and the current direction that the asteroid will move. This has a 
 * class constructor that takes the the two sets of coordinates, the size of the asteroid is preset to 20. 
 * 
 * @param  double ix, double iy, double idx, double idy    
 * @return null
 */
public class Asteroid 
{
	// Class Variables
	private double x, y; 
	public int size = 20; 
	private double dx, dy;
	
	// Class Constructor
	public Asteroid (double ix, double iy, double idx, double idy) 
	{
		x = ix; y = iy; dx = idx; dy = idy; 
	}
	
	// Class Methods
	
	// Moves the asteroid by adding the dx and dy coordinates to the x and y. 
	// This will be done every time that that the move method is called. 
	public void move ( ) 
	{
		x += dx; y += dy;
	}
	
	// Changes the shape of the asteroid upon a successful hit. it will need to be
	// hit five times in order to be deleted. 
	public void paint (Graphics g) 
	{
		g.setColor(Color.black);
		g.drawOval((int) x, (int) y, size, size);
	}
	
	// If a "rocket" makes contact with the asteroid it will reduce its size by 4. 
	public void hit ( ) 
	{ 
		size = size - 4; 
	}
	
	// Checks to see if a rocket is near enough to the asteroid. if the distance is less then 10
	// it returns true, if it is greater it returns false. 
	public boolean nearTo (double tx, double ty) 
	{
		double distance = Math.sqrt( (x-tx)*(x-tx) + (y-ty)*(y-ty) );
		return distance < 10;
	}
}
