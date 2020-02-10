import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.Queue;

public class Caterpillar 
{
	// Private class variables, holds the x and y coords, the static direction and the length of hte catapiller
	private Color color;
	private Point position;
	private char direction = 'E';
	private int Catapillerlength = 10; 
	
	private Queue<Point> body = new LinkedList<Point>();
	private Queue<Character> commands = new LinkedList<Character>();

	public Caterpillar (Color c, Point sp) 
	{
		color = c;
		
		// increases the length of the catapiller if you so choose. 
		/*
		for (int i = 0; i < Catapillerlength; i++) 
		{
			position = new Point(sp.x + i, sp.y);
			body.add(position);
		}
		*/
	}
	
	// returns the current position of the catapiller
	public Point getPosition()
	{
		return position; 
	}
	
	// Changes the direction of the caterpiller
	public void setDirection (char d) 
	{
		commands.add(new Character(d));
	}
	
	public void move (CaterpillarGame game)
	{
		if (commands.size( ) > 0)
		{
			Character c = (Character) commands.peek(); 
			commands.remove( );
			direction = c.charValue( ); 
			
			if (direction == 'Z') 
			{
				return;
			}
		}
		
		Point np = newPosition( ); 
		
		if (game.canMove(np)) 
		{
		
		body.remove( );
		body.add(np);
		position = np;
		}
	}
	
	
	private Point newPosition ( ) 
	{
		int x = position.x;
		int y = position.y;
		if (direction == 'E') x++;
		else if (direction == 'W') x--;
		else if (direction == 'N') y--;
		else if (direction == 'S') y++;
		return new Point(x, y);
	}
	
	// uses an iterator to search the queue of the caterpiller
	public boolean inPosition (Point np) 
	{
		Iterator<Point> e = body.iterator();
		while (e.hasNext()) 
		{
			Point location = (Point) e.next();
			if (np.equals(location)) 
			{
				return true;
			}
		}
		return false;
		}
	
	public void paint (Graphics g) 
	{
		g.setColor(color);
		Iterator<Point> e = body.iterator();

		
		while (e.hasNext( )) 
		{
			Point p = (Point) e.next();
			g.fillOval(5 + CaterpillarGame.SegmentSize * p.x,
					15 + CaterpillarGame.SegmentSize * p.y,
					CaterpillarGame.SegmentSize,
					CaterpillarGame.SegmentSize);
		}
	}
	
	
	// 
	//Increases the size of the caterpiller
	public void addToCatapillerLength()
	{
		Catapillerlength = Catapillerlength +1; 
	}
}
