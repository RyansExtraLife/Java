import java.awt.*;
import java.awt.event.*;
import java.util.Iterator;
import java.util.Random;

public class CaterpillarGame extends Frame 
{
	// Game Board Variables
	final static public int BoardWidth = 60;
	final static public int BoardHeight = 40;
	final static public int SegmentSize = 10;
	
	// Random Game Points variables 
	private Point numberSquare = new Point(20, 10);
	private int numberSquarePoints = 0;
	
	// Player Scores
	private int playerOnePoints = 0;
	private int playerTwoPoints = 0; 
	
	// Caterpillars
	private Caterpillar playerOne = new Caterpillar(Color.blue, new Point (20, 10));
	private Caterpillar playerTwo = new Caterpillar (Color.red, new Point(20, 30));
	
	// Main Function
	public static void main (String [ ] args) 
	{
		CaterpillarGame world = new CaterpillarGame();
		world.setVisible(true);
		world.newNumberSquare();
		world.run();
		
	}
	
	// Class Constructor 
	public CaterpillarGame ( ) 
	{
		setSize ((BoardWidth+1)*SegmentSize, BoardHeight*SegmentSize + 30);
		setTitle ("Caterpillar Game");
		addKeyListener (new KeyReader( ));
	}
	
	// Class Functions
	public void run ( ) 
	{
		boolean GameOver = true; 
		while (GameOver) 
		{
			
			// Checks to see if player one is on a points square, if true adds points and creates a new
			// number square.
			if (playerOne.inPosition(numberSquare))
			{
					playerOnePoints = playerOnePoints + numberSquarePoints; 
					playerOne.addToCatapillerLength();
					newNumberSquare(); 
			}
			
			// Checks to see if player two is on a points square, if true adds points and creates a new
			// number square. 
			
			if (playerTwo.inPosition(numberSquare)) 
			{
			playerTwoPoints = playerTwoPoints + numberSquarePoints; 
			playerTwo.addToCatapillerLength();
			newNumberSquare(); 
			}
			movePieces( );
			repaint( );
			
			
			
			// If player ones points is greater then 50 the game ends and Player one is the winner. 
			if(playerOnePoints >= 50)
			{
				GameOver = false; 
			}
			
			// If player twos poins is greater then 50 the game ends and Player two is the winner. 
			if(playerTwoPoints >= 50)
			{
				GameOver = false;
			}
			
			try 
			{
				Thread.sleep(100);
			} catch (Exception e) { }
		}
	}
	
	public void paint (Graphics g) 
	{
		playerOne.paint(g);
		playerTwo.paint(g);
		// Paints the score to the board
		g.drawString("Player One Score: " + playerOnePoints, (int) (30), (int) (38));
		g.drawString("Player Two Score: " + playerTwoPoints, (int) (450), (int) (38));
		g.drawString("" + numberSquarePoints, (int) numberSquare.x, (int) numberSquare.y);
	}

	
	// Added Class Functions 
	
	
	// Generates a random point value and a random locaion for the points square to be. 
	private void newNumberSquare ()
	{
		Random randomGenerator = new Random();
		numberSquarePoints = randomGenerator.nextInt(9) + 1; 
		numberSquare = new Point(randomGenerator.nextInt(400) +1, randomGenerator.nextInt(400) + 1); 
	}
	
	public void movePieces () 
	{
		playerOne.move(this);
		playerTwo.move(this);
	}
		
	public boolean canMove (Point np) 
	{
			int x = np.x;
			int y = np.y;

			if ((x <= 0) || (y <= 0)) 
			{
				return false;
			}
			
			if ((x >= BoardWidth) || (y >= BoardHeight))
			{
				return false;
			}
			
			if (playerOne.inPosition(np)) 
			{
				return false;
			}
			
			if (playerTwo.inPosition(np))
			{
				return false;
			}

			return true;
	}
		
	private class KeyReader extends KeyAdapter 
		{
			public void keyPressed (KeyEvent e) 
			{
				char c = e.getKeyChar( );
				switch (c) 
				{
					case 'q': playerOne.setDirection('Z'); break;
					case 'a': playerOne.setDirection('W'); break;
					case 'd': playerOne.setDirection('E'); break;
					case 'w': playerOne.setDirection('N'); break;
					case 's': playerOne.setDirection('S'); break;
					case 'p': playerTwo.setDirection('Z'); break;
					case 'j': playerTwo.setDirection('W'); break;
					case 'l': playerTwo.setDirection('E'); break;
					case 'i': playerTwo.setDirection('N'); break;
					case 'k': playerTwo.setDirection('S'); break;
				}
			}
		}



}