import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;
import java.util.ListIterator;

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
 * -----------------------------
 *   + Rocket (double, double, double, double)
 *   + move (LinkedList<Asteroid>): void
 *   + paint (Graphics): void
 */

/**
 * Rocket Class will store all the data of an rocket. 
 * <p>
 * This class will be used inside a linked list. Each of the variables within the linked 
 * list will be an Rocket class. 
 * <p>
 * The rocket class is similar to a asteroid class, it holds the data of a rocket 
 * object. The rocket will have variables to store the current x and y coordinates  
 * on the plane. The other variable is the path of the rocke.t 
 * 
 * @param  double ix, double iy, double idx, double idy    
 * @return null
 */
public class Rocket
{
	// Class Variables
	private double x, y; 
	private double dx, dy;
	
	
	// Class Constructor 
	public Rocket (double ix, double iy, double idx, double idy)
	{ 
		x = ix; y = iy; dx = idx; dy = idy;
	}
	
	// Class Methods 
	
	// Moves the rocket one space forward. 
	// The linked list of asteroids is passed by reference into the function.
	// Once the Rocket is moved one space forward it is referenced against all of the asteroids currently
	// in the asteroids linked list. This is done by first creating a Linked List iterator, in this
	// method it is called "listIterator". The while loop within the statement checks to see if there is
	// another link in the list. If there is it will check it to see if the rocket is near the current 
	// asteroid in the list. If it is near enouch it tirggers the hit command. 
	public void move (LinkedList<Asteroid> asteroids)
	{
		x += dx; y += dy;
		ListIterator<Asteroid> listIterator = asteroids.listIterator();
		while (listIterator.hasNext())
		{
			Asteroid asteroid = listIterator.next();
			if (asteroid.nearTo(x,y))
			{
				asteroid.hit();
			}
		}
	}
	
	// Creates and paints the rocket upon every move. 
	public void paint (Graphics g)
	{
		g.setColor((Color.black));
		g.fillOval((int) x, (int) y, 5, 5);
	}
}
