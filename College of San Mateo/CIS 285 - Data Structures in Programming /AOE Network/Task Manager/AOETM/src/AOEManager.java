// Ryan Patterson
// In conjunction with: Tiffanie Linkin and Kris Solomon
// CIS 256
// Final Project
// 5/19/2015

// Import statements. 
import java.util.*;
import java.awt.*;

public class AOEManager 
{
	private Vector vVertex; // Set of Event
	private Vector vEdge;   // Set of Edge   
	
	private WeightedDirectedGraph graph;
	
	private VectorStack stack = new VectorStack();
	
	// Turn Testing On and Off
	private boolean isTesting = false;
	private Vertex[] vertices;
	
	// Variables that store the start and end of the vertex.
	// Used in ForwardStage() and BackwardStage(). 
	private Vertex start;
	private Vertex end;

	// Default Class Constructor
	// Initializes variables to empty Vectors.
	public AOEManager() 
	{
		vVertex = new Vector();
		vEdge = new Vector();
	}

	// Draw the activity network when ever the draw method is called. 
	public void draw(Graphics g)
	{
		// Cycles through each of the vVertexs and draws them onto the window. 
		for (int x =  0; x < vVertex.size(); x++)
		{
			((Vertex)vVertex.elementAt(x)).draw(g);
		}
		
		// Cycles through each of the vEdges and draws them onto the window. 
		for (int x =  0; x < vEdge.size(); x++)
		{
			((Edge)vEdge.elementAt(x)).draw(g);
		}
	}
	
	// Function adds a vertex to the vVertex Vector with the location (x,y) with id
	public void addVertex(int x, int y, int id) 
	{
		vVertex.add(new Vertex(x, y, id));
	}

	// Function adds a edge to  the vEdge Vector with the location (v2,v2) with the amount of
	// time it takes to get to get from vertex to vertex. 
	public void addEdge(Vertex v1, Vertex v2, int timeActivity, int iDedge) 
	{
		vEdge.add(new Edge(v1,v2, timeActivity,iDedge));
	}

	// Return a specific Vertex if x and y are within the vVertez vector. 
	public Vertex findVertex (int x , int y) 
	{
		// For loop cycles through each of the of the variables in the class. 
		for (int z = 0; x < vVertex.size(); z++) 
		{
			// If the coordinates are found return the vertex element. 
			if (((Vertex)vVertex.elementAt(z)).isInclude( x , y )) 
			{
				return (Vertex)vVertex.elementAt(z);
			}
		}
		// If the for loop completes, it is not found, returns null.
		return null;
	}
	 
	// Return a specific Vertex if the point are along the Edge
	public Edge findEdge(Point pt) 
	{
		// For loop cycles through each of the of the variables in the class.
		for (int x = 0; x < vEdge.size(); x++) 
		{
			// If the coordinates are found return the edge element.
			if (((Edge)vEdge.elementAt(x)).isInclude( pt))
			{
				return (Edge)vEdge.elementAt(x);
			}
		}
		// If the for loop completes, it is not found, returns null.
		return null;
	}

	// Function that returns the critical path of the custom AOE Network. 
	public void criticalPath() 
	{
		//Checks to see if the vVertex isEmpty, if it is the function end and returns. 
		if (vVertex.isEmpty())
		{
			return;
		}
		
		// Creates a new Vertex with size equal to the amount of variables within the vVertex. 
		vertices = new Vertex[vVertex.size()];
    
		// For loop that cycles once for each element within the vVertex. 
    	for (int x = 0; x < vVertex.size(); x++) 
    	{
    		// Creates Vertex and sets it equal to the element at x.
    		// Sets the variable to the location equal to tempVertex. 
    		Vertex tempVertex = (Vertex) vVertex.elementAt(x);
    		vertices[tempVertex.getId()] = tempVertex;
    	}
    
    	// Creates a new Weighted Directed Graph with size equal to the amount of variables within vVertex
    	graph = new WeightedDirectedGraph(vVertex.size());
    
    	// For loop that cycles once for each element within the vVertex. 
    	for (int x = 0; x < vEdge.size(); x++) 
    	{
    		// Creates a tempEdge and sets it equal to variable at element x of vEdge. 
    		Edge tempEdge = (Edge) vEdge.elementAt(x);
    		
    		// If statement checks to see if tempEdge == Null, if true then returns error. 
    		if (tempEdge == null) 
    		{
    			System.out.println("Weighted Graph == Null. Error");
    			return;
    		}
    		
    		vertices[tempEdge.getDestination()].incPredecessorsCount();
    		vertices[tempEdge.getSource()].incSuccessorsCount();
    		graph.addEdge(tempEdge.getSource(), tempEdge.getDestination(), tempEdge.getTime());
    	}
    	
    	// For loop that cycles once for each element within the vVertex. 
    	for (int x = 0; x < vVertex.size(); x++) 
    	{
    		// Creates a temporary Vertex variable and sets it equal to vVertex element at x. 
    		Vertex tempVertex = (Vertex) vVertex.elementAt(x);
    		
    		if (tempVertex.getPredecessorsCount() == 0) 
    		{
    			// Sets start variable equal to the temp variable. 
    			start = tempVertex;
    		} 
    		
    		else if (tempVertex.getSuccessorsCount() == 0) 
    		{
    			// Sets end variable equal to the temp variable. 
    			end = tempVertex;
    		}
    	}
    	
    	// Calls forwardStage, backwardStage. 
    	forwardStage ();
    	backwardStage ();
		}

	public void forwardStage() 
	{
		stack.push(start);

		// For loop that cycles once for each element within the vertices. 
		for (int loop = 0; loop < vertices.length; loop++)
		{
			// Creates a temporary vertex equal to the item on top of the stack, removes item from stack. 
			Vertex tempVertex = (Vertex) stack.pop();
      
			if (tempVertex == null) 
			{
				System.out.println("Path already has been created");
				return;
			} else {
				java.util.List<Integer> l = graph.getSuccessors(tempVertex.getId());
				
				for (Integer x : l) 
				{
					vertices[x].decPredecessorsCount();
					
					// If statement checks to see if the earliest event is equal to the temp eariels event + the current weight. 
					if (vertices[x].getEarliestEvent() < (tempVertex.getEarliestEvent() + graph.getWeight(tempVertex.getId(), x))) 
					{
						vertices[x].setEarliestEvent(tempVertex.getEarliestEvent() + graph.getWeight(tempVertex.getId(), x));
					}
          
					//Checks to see if predecessor count is == 0. 
					if (vertices[x].getPredecessorsCount() == 0) 
					{
						stack.push(vertices[x]);
					} 
					// Outputs error if a problem occurs. 
					else if (vertices[x].getPredecessorsCount() < 0) 
					{
						System.out.println("PredecessorCount <0. Error");
					}
				}
			}
		}
  }
	

	public void backwardStage() 
	{
		// If statement triggers if end is equal to null. 
		if (end == null) 
		{
			// End has no variables, function returns. 
			return;
		}
    
		// 
		end.setLatestEvent(end.getEarliestEvent());
		
		// For loop that cycles once for each element within the vVertex. 
		for (int x = 0; x < vVertex.size(); x++) 
		{
			Vertex v = (Vertex) vVertex.elementAt(x);
			v.setLatestEvent(end.getLatestEvent());
		}
		
		stack.push(end); 
		
		// For loop that cycles once for each element within the vertices. 
		for (int loop = 0; loop < vertices.length; loop++) 
		{
			// Creates a temporary vertex equal to the item on top of the stack, removes item from stack. 
			Vertex tempVertex = (Vertex) stack.pop();
     
			if (tempVertex == null) 
			{
				System.out.println(" tempVertex == Null. Error");
				return;
			} else {
				java.util.List<Integer> l = graph.getPredecessors(tempVertex.getId());
        
				for (Integer x : l)
				{
					vertices[x].decSuccessorsCount();
          
					// If statement checks to see if the earliest event is greater to the temp earliest event - the current weight.
					if (vertices[x].getLatestEvent() > (tempVertex.getLatestEvent() - graph.getWeight(x, tempVertex.getId()))) 
					{
						vertices[x].setLatestEvent(tempVertex.getLatestEvent() - graph.getWeight(x, tempVertex.getId()));
					}
					
					if (vertices[x].getSuccessorsCount() == 0) 
					{
						stack.push(vertices[x]);
					} 
					// Else if statement triggers if count <0. Prints Error
					else if (vertices[x].getSuccessorsCount() < 0) 
					{
						System.out.println("PredecessorCount <0. Error");
					}
				}
			}
		}
	  // Checks to see if the program is testing. 
      testing ( isTesting );
  }

	public void resetEarlyLateTime ()
	{
		// For loop that cycles once for each element within the vertices. 
		for ( int x = 0; x < vVertex.size(); x ++ )
		{
			Vertex current = (Vertex)vVertex.elementAt(x);
			current.reset();
		}
	}

	public void testing (boolean testing)
	{
		if (testing)
		{
			System.out.println("Test: Forwardstage()");

			for ( int x = 0; x < vVertex.size(); x ++ )
			{
				Vertex vtry = ((Vertex)vVertex.elementAt(x)) ;
				System.out.println(vtry.getEarliestEvent());
			}

			System.out.println("Testing Backwardstage()");

			for ( int x = 0; x < vVertex.size(); x ++ )
			{
				Vertex vtry = ((Vertex)vVertex.elementAt(x)) ;
				System.out.println(vtry.getLatestEvent());
			}
		}
	}
}
