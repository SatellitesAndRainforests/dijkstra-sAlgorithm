import java.io.*;
import java.util.Scanner;
import java.util.*;


public class graph {

	double [] [] adj;
	
	graph (double [] [] a) {
		adj= new double [a.length][a.length];
		for (int i=0;i<a.length;i++)
			for (int j=0;j<a.length;j++)
				adj[i][j]=a[i][j];
	}

	public HashSet <Integer> neighbours(int v) {

		HashSet <Integer> h = new HashSet <Integer> ();

		int i;

		for (i=0;i<adj.length;i++) {
			if (adj[v][i]!=0)
				h.add(i);
		}

		return h;
	
	}

	public HashSet <Integer> vertices(){
		HashSet <Integer> h = new HashSet <Integer>();
		for (int i=0;i<adj.length;i++)
			h.add(i);
		return h;
	}

	ArrayList <Integer> addToEnd (int i, ArrayList <Integer> path) {
		// returns a new path with i at the end of path
		ArrayList <Integer> k;
		k=(ArrayList<Integer>)path.clone();
		k.add(i);
		return k;
	}


	public HashSet <ArrayList <Integer>> shortestPaths1 (HashSet <ArrayList <Integer>> sofar, HashSet <Integer> visited, int end) {

		HashSet <ArrayList <Integer>> more = new HashSet <ArrayList <Integer>>();
		HashSet <ArrayList <Integer>> result = new HashSet <ArrayList <Integer>>();
		HashSet <Integer> newVisited = (HashSet <Integer>) visited.clone();
		
		boolean done = false;
		boolean carryon = false;
		for (ArrayList <Integer> p: sofar)
		{
			for (Integer z: neighbours(p.get(p.size()-1)))
			{
				if (!visited.contains(z))
				{
					carryon=true;
					newVisited.add(z);
					if (z==end) {
						done=true;
						result.add(addToEnd(z,p));
					}
					else
						more.add(addToEnd(z,p));
				}
			}
		}
		if (done) return result; else
			if (carryon)
				return
					shortestPaths1(more,newVisited,end);
			else
				return new HashSet <ArrayList <Integer>>();
	}


	public HashSet <ArrayList <Integer>> shortestPaths (int first, int end) {
		
		HashSet <ArrayList <Integer>> sofar = new HashSet <ArrayList<Integer>>();
		HashSet <Integer> visited = new HashSet<Integer>();
		ArrayList <Integer> starting = new ArrayList<Integer>();

		starting.add(first);
		sofar.add(starting);
		if (first==end)
			return sofar;
		visited.add(first);
		return shortestPaths1(sofar,visited,end);
	}

/*	
	public static void main(String [] args) {
		double [ ] [] a = {
			{0.0, 1.0, 1.0, 0.0},
			{0.0, 0.0, 1.0, 1.0},
			{0.0, 1.0, 0.0, 1.0},
			{0.0, 1.0, 1.0, 0.0}
		};
		graph g = new graph(a);
		for (int i=0;i<a.length;i++)
		{for (int j=0;j<a.length;j++)
			if (i!=j) System.out.println(i + " to " + j +": "+
					g.shortestPaths(i,j));
		}
	}
*/



	public static void main ( String [] args ) throws Exception {

		int N=1000;
		
		double edges [][] = new double [N][N];
		
		for ( int i = 0; i < N; i++ )
		       for ( int j = 0; j < N; j++ )
		       		edges[i][j] = 0.0;	       
		
		Scanner s = new Scanner (new FileReader("randomGraphTube.csv"));
		String z;
		z = s.nextLine();

		while (s.hasNext()) {
			z = s.nextLine();
			String [] results = z.split(",");
			edges [Integer.parseInt(results[0])] [Integer.parseInt(results[1])] = Double.parseDouble(results[2]);
		}

		graph G = new graph (edges); 

		System.out.println(G.dijkstra(Integer.parseInt(args[0]), Integer.parseInt(args[1])));
	}



// me

	int findSmallest( HashMap <Integer, Double> nodeTDValue ) {

		Object [] things = nodeTDValue.keySet().toArray();

		int lowestKey = (int) things[0];
		double lowestValue = nodeTDValue.get(things[0]);

		Set <Integer> keySet = nodeTDValue.keySet();
		

		for ( Integer key : keySet ) {
			if ( nodeTDValue.get(key) < lowestValue ) {
				lowestKey = key;
				lowestValue = nodeTDValue.get(key);
			}
		}

		return lowestKey;
	
	}




	public ArrayList <Integer> dijkstra( int start, int end  ) {

		int totalNodes = 1000; 

		HashMap <Integer,Double> nodeTDValues = new HashMap <Integer, Double>();
		ArrayList <Integer> [] paths = new ArrayList [totalNodes];	
		

		for (int i=0; i < totalNodes; i++) {
			nodeTDValues.put(i, Double.POSITIVE_INFINITY );   
			paths[i] = new ArrayList <Integer>();
			paths[i].add(start);
		}


		HashSet <Integer> visitedSet = new HashSet();
		visitedSet.add(start);
		nodeTDValues.put(start, 0.0 );
		
		while (!nodeTDValues.isEmpty()) { 

			int smallestNodeValueKey = findSmallest(nodeTDValues);

			if (smallestNodeValueKey == end && (nodeTDValues.get(smallestNodeValueKey) != Double.POSITIVE_INFINITY )  ) {
				return paths[end];

			}
			
			double w = nodeTDValues.get( smallestNodeValueKey );
			visitedSet.add( smallestNodeValueKey );

			for ( int u : neighbours(smallestNodeValueKey))

				if (!visitedSet.contains(u)) {
					
					double w1 = (adj[smallestNodeValueKey][u]) + w ;

					if (w1 < nodeTDValues.get(u)) {
						nodeTDValues.put(u,w1);
						paths[u] = addToEnd(u, paths[smallestNodeValueKey]);

					}
				}
				nodeTDValues.remove(smallestNodeValueKey);
		}
		return new ArrayList <Integer> ();
	}







/*

	Set S = {start};
	// S is the set of vertices for which the shortest paths from start have already been found
	HashMap <Integer,Double> Q = Map each Vertex to Infinity
	(Double.POSITIVE_INFINITY), except map start -> 0;
	// Q.get(i) represents the shortest distance found from start to i found so far
	ArrayList <Integer> [] paths;
	For each i set path[i] to be the path just containing start.
		
		while (Q is not empty) {
			let v be the key of Q with the smallest value;
			//I've given you a method int findSmallest(HashMap <Integer,Double> t) for this
				if (v is end and Q does not map v to infinity)
					return paths[end]; let w be the value of v in Q;
			add v to S;
			for (each neighbour u of v) do
			{
				if (u not in S)
				{
					let w1 be the the weight of the (v,u) edge + w;
					if w1 < the value of u in Q, then do the following:
					{
						update Q so now the value of u is w1
							6update paths(u) to be paths(v) with u stuck on the
							end
					}
				}
				remove v from Q;
			}
		}






*/
























}

