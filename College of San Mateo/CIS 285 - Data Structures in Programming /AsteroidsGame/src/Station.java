import java.awt.Color;
import java.awt.Graphics;
import java.util.LinkedList;

/**
 * @author      Ryan Patterson 
 * @version     Asteroid Assignment
 * @since       2/19/2015
 */


/* Class Station
 * 
 * -----------------------------
 *  - angle:double
 *	- hits: int
 *	- x: double
 *	- y: double
 * -----------------------------
 * 
 * + Station (double, double) 
 * + moveLeft():void
 * + moveRight( ):void
 * + fire (LinkedList<Rocket>):void
 * + checkHit (Asteroid rock):void
 * + paint (Graphics g):void
 */


/**
 * Station Class will hold all of the data for the "station" on the bottom of the screen. 
 * <p>
 * The station will be at the bottom of the screen and will move its "cannon" upon the user
 * pressing the J or K keys on the keyboard. Upon the press of the space bar the station
 * will fire a rocket.
 * <p>
 * The station class stores all of the data for the station at the bottom of the screen. Its
 * "cannon" can only rotate around a circle in its center. When ever the user hits one of the 
 * three action buttons the station will respond. If one of the move buttons is it the station will
 * move in the desired direction.  
 * 
 * @param  null 
 * @return null
 */

public class Station 
{
	private double angle = Math.PI / 2.0; // public static final double PI 3.141592653589793d 
	private int hits = 0;
	private final double x;
	private final double y;
	
	public Station (double ix, double iy) 
	{
		x = ix; y = iy;
	}
	
	public void moveLeft( )
	{
		angle = angle + 0.1; 	
	} 
	
	public void moveRight( ) 
	{ 
		angle = angle - 0.1; 
	}
	
	public void fire (LinkedList<Rocket> rockets) 
	{ 
		double cosAngle = Math.cos(angle);
		double sinAngle = Math.sin(angle);
		
		Rocket r = new Rocket(x + 15 * cosAngle, y - 15 * sinAngle, 5 * cosAngle, - 5 * sinAngle);
		rockets.add(r); 
	}
	
	public void checkHit (Asteroid rock) 
	{
		if (rock.nearTo((double) x, (double) y))
		{
			hits += rock.size;
		}
	}
	
	public void paint (Graphics g) 
	{
		g.setColor (Color.black);
		double lv = 20 * Math.sin(angle);
		double lh = 20 * Math.cos(angle);
		g.drawLine((int) x, (int) y, (int) (x + lh), (int) (y - lv)); 				
		g.drawString("hits: " + hits, (int) (x + 10), (int) (y - 5));
	}
}
