import java.awt.*;
import java.awt.event.*;
import java.util.LinkedList;
import java.util.ListIterator;

/**
 * @author      Ryan Patterson 
 * @version     Asteroid Assignment
 * @since       2/19/2015
 */


/* Class Asteroid Game extends Frame
 * 
 * -----------------------------
 *  - FrameWidth : int
 *	- FrameHeight: int
 *	- LinkedList<Asteroid>: Asteroid
 *	- LinkedList<Rocket>: Rocket
 *  - Station: Station
 * -----------------------------
 * - main ():void
 * + AsteroidGame()
 * - run ():void
 * - paint (Graphics):void
 * - movePieces ():void
 * - class keyDown extends KeyAdapter
 * 
 */

/**
 * Game class stores all of the data to link together the Asteroid, Rocket and Station Class
 * <p>
 * This class contains the main function that starts the game. It also holds all of the data for the 
 * key commands and window information. 
 * <p>
 * 
 * 
 * @param  null 
 * @return null
 */
public class AsteroidGame extends Frame{

	
	private static final long serialVersionUID = 4516462215585206354L;
			
	// Main funciton for game
	static public void main (String [ ] args)
	{ 
		AsteroidGame world = new AsteroidGame( ); 
		world.setVisible(true); 
		world.run( ); 
	}
	
	// Constructor fo the asteroids game. 
	public AsteroidGame()
	{
		setTitle("Asteroid Game");
		setSize(FrameWidth, FrameHeight);
		addKeyListener (new keyDown());
		//addWindowListener (new CloseQuit());
	}
	
	// Class method that moves the pieces and repaints all with each pass through the while
	// loop It will then put the program to sleep for a short peroid. If you wanted to increase the 
	// frame rate lower the number. if you wanted to slow the game down, increase the number. 
	public void run ()
	{
		while (true)
		{
			movePieces();
			repaint();
			try
			{
				Thread.sleep(100);
			}catch (Exception e) { }	
		}
	}
	
	// Window size: width and height. 
	private int FrameWidth = 500;
	private int FrameHeight = 400;
	
	// The two linked lists that will store each of the rockets and rockets that are currently on the
	// Graphics plane. each linked list will store an item of its respective class. For example the asteroid 
	// linked list stores objects of type asteroid. 
	private LinkedList<Asteroid> asteroids = new LinkedList(); 
	private LinkedList<Rocket> rockets = new LinkedList();
	
	// creates object station. puts it dead center and raised 20 pixels. 
	private Station station = new Station (FrameWidth/2, FrameHeight-20);
	
	public void paint (Graphics g)
	{
		station.paint(g);
		// creates iterator for type asteroid that will look at all of the objects stored 
		// within the linked list.
		ListIterator<Asteroid> asteroidListIterator = asteroids.listIterator();
		// upon each visit it will repaint the next asteroid and move to the next. 
		while (asteroidListIterator.hasNext())
		{
			Asteroid asteroid = asteroidListIterator.next();
			asteroid.paint(g);
		}
		// this process is repeated for rockets. 
		ListIterator<Rocket>  rocketListIterator = rockets.listIterator();
		while (rocketListIterator.hasNext())
		{
			Rocket rock = rocketListIterator.next();
			rock.paint(g);
		}
	}
	
	private void movePieces ()
	{
		// create a random new asteroid – 30% of the time
		if (Math.random( ) < 0.1) 
		{
			Asteroid newRock = new Asteroid(FrameWidth * Math.random( ), 20, 10 * Math.random( ) - 5, 3 + 3 * Math.random( ));
			asteroids.add(newRock);
		} 
		// list iterator that will pass by each object within the linked lit and calls the move command onto it
		
		ListIterator<Asteroid> asteroidListIterator = asteroids.listIterator();
		while (asteroidListIterator.hasNext())
		{
			Asteroid asteroid = asteroidListIterator.next();
			asteroid.move();
			station.checkHit(asteroid);
		}
		
		// list iterator that will pass by each object within the linked lit and calls the move command onto it
		ListIterator<Rocket>  rocketListIterator = rockets.listIterator();
		while (rocketListIterator.hasNext())
		{
			Rocket rock = rocketListIterator.next();
			rock.move(asteroids);
		}
	}
	
	private class keyDown extends KeyAdapter
	{
		public void keyPressed (KeyEvent e)
		{
			char key = e.getKeyChar();
			switch(key)
			{
			case 'j': station.moveLeft(); break;
			case 'k': station.moveRight(); break;
			case ' ': station.fire(rockets); break;
			case 'q': System.exit(0);
			}
		}
	}
}