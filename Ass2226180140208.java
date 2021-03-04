import java.io.*;
import java.util.*;



public class Ass2226180140208 {

	double [] [] adj;
	int pairWithHighestShortestPathsTotal;
	int firstPairOfHighestShortestHopsA, firstPairOfHighestShortestHopsB;
	int N;
	int plus1VerticiesForHops;
	HashMap <Integer, LonLat> lonsAndLats;


	Ass2226180140208 (){
	}


	Ass2226180140208 (double [] [] a) {
		adj= new double [a.length][a.length];
		for (int i=0;i<a.length;i++)
			for (int j=0;j<a.length;j++)
				adj[i][j]=a[i][j];
	}


	public HashSet <Integer> neighbours(int v) {
		HashSet <Integer> h = new HashSet <Integer> ();
		int i;
		for (i=0;i<adj.length;i++) {
			if (adj[v][i]!=0) {
				h.add(i);
//				System.out.println("verticies: " + v + " neighbour:  " + i);
			}
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
		for (ArrayList <Integer> p: sofar) {
			for (Integer z: neighbours(p.get(p.size()-1))) {
				if (p.size() == 1 && z == end ) { 
					visited.add(z);					
				}
				if (!visited.contains(z)) {
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




//----------------------------------------- My Code ---------------------------------------------//


	int getNforGraph( String playgroundsfile ) throws Exception {

		BufferedReader input = new BufferedReader(new FileReader(playgroundsfile));
		String last = "", line;

		while ((line = input.readLine()) != null) {
			last = line;
		}

		String [] lastLine = last.split(",");

//		for (int i=0; i<lastLine.length; i++) System.out.println(lastLine[i]);

		return Integer.parseInt(lastLine[0]);

	}


        void makeGraph (double [] [] a) {

                adj= new double [a.length][a.length];

//		System.out.println("a.length = " + a.length);

                for (int i=0;i<a.length;i++)
                        for (int j=0;j<a.length;j++) {
                                adj[i][j]=a[i][j];

//				System.out.println("adj["+i+"]["+j+"] = a["+a[i][j]+"]"); 
			}

	}


	double [][] getGraphEdges (String edgeValuesFile, int N) throws Exception {

		int n = N+1;
		double edges [][] = new double [n][n];

//		System.out.println("n+1 = " + n);
//		System.out.println(edges.length);

		for ( int i = 0; i < edges.length; i++ )
		       for ( int j = 0; j < edges.length; j++ )
		       		edges[i][j] = 0.0;	       

		Scanner s = new Scanner (new FileReader(edgeValuesFile));
		String z;
		while (s.hasNext()) {
			z = s.nextLine();
			String [] results = z.split(",");
			try {
				edges [Integer.parseInt(results[0])] [Integer.parseInt(results[1])] = Double.parseDouble(results[2]);

//				int a = Integer.parseInt(results[0]);
//				int b = Integer.parseInt(results[1]);
//				System.out.println("edge: " + (a) + " " + (b) +  " is results: " + results[0] + " " + results[1] + " which = " + results[2]);

			} catch (NumberFormatException nfe) {
				// skip non number lines
			}
		}
		return edges;
	}


	int howManyShortestPathsBetweenPlaygroundAandB( int a, int b ) {
		HashSet <ArrayList <Integer>> resultFromShortestPath = shortestPaths(a, b); 

//		System.out.println("RESULTS FROM :: " + resultFromShortestPath);
//		System.out.println("Pair " + a + ", " + b + " has " + resultFromShortestPath.size() + " shortest paths.");

		return resultFromShortestPath.size();
	}


	String pairsWithHighestNumberOfShortestPaths(int N) {

//		System.out.println(N);

		List<Pair> pairs = getAllPairs(N);
		List<Pair> highestPairs = new ArrayList<Pair>();
		int highestShortestPaths = 0;

		for (int i=0; i < pairs.size(); i++) {

			int currentPairShortestPathsTotal = howManyShortestPathsBetweenPlaygroundAandB(pairs.get(i).x, pairs.get(i).y);

//			System.out.println("pair " + pairs.get(i).x + ", " + pairs.get(i).y + " " + howManyShortestPathsBetweenPlaygroundAandB(pairs.get(i).x, pairs.get(i).y) );

			if (currentPairShortestPathsTotal > highestShortestPaths) {
//				System.out.println( currentPairShortestPathsTotal );
				highestPairs = new ArrayList<Pair>();
				highestPairs.add( pairs.get(i) );	// inputs x and y id's of pair
				highestShortestPaths = currentPairShortestPathsTotal;
			}

			else if (currentPairShortestPathsTotal == highestShortestPaths) {
				highestPairs.add( pairs.get(i) );
//				System.out.println(getAnswerAsAString(highestPairs));
			}	
		}

		this.pairWithHighestShortestPathsTotal = highestShortestPaths;
		return getAnswerAsAString(highestPairs);

	}

	
	String getAnswerAsAString( List <Pair> highestPairs ) {

		String Answer = "";

		for (int i = 0; i < highestPairs.size(); i++){

			Answer += "[" + highestPairs.get(i).x + ", " + highestPairs.get(i).y + "]";

			if ( i == 0 ) {
				this.firstPairOfHighestShortestHopsA = highestPairs.get(0).x; 
				this.firstPairOfHighestShortestHopsB = highestPairs.get(0).y;
			}

			if (i != highestPairs.size()-1) Answer += ",";
		}
		return Answer;
	}


	// Modified from https://stackoverflow.com/questions/9453074/generating-all-unique-pairs-from-a-list-of-numbers-n-choose-2
	class Pair {
		int x,y;
		Pair(int x, int y){
			this.x = x;
			this.y = y;
		}
	}

	// Modified from https://stackoverflow.com/questions/9453074/generating-all-unique-pairs-from-a-list-of-numbers-n-choose-2
	List<Pair> getAllPairs(int N) {
		List<Pair> pairs = new ArrayList<Pair>();
		int total = N;
		for(int i=1; i <= total; i++) {
			int num1 = i;
			for(int j=i+1; j <= total; j++) {
				int num2 = j;
				pairs.add(new Pair(num1,num2));
			}
		}

//		System.out.println(pairs.size());
		
//		for (int i=0; i<pairs.size();i++){
//			System.out.println(pairs.get(i).x + ", " + pairs.get(i).y);
//		}
		return pairs;
	}


	int getPairsWithHighestNumberOfShortestPathsTotal() {
		return this.pairWithHighestShortestPathsTotal;		
	}


	int getHighestPairsHopCount() {
		int Answer = highestPairShortestPathsHopCount( this.firstPairOfHighestShortestHopsA, this.firstPairOfHighestShortestHopsB);
		this.plus1VerticiesForHops = Answer +1;
		return Answer;
	}


///////////// can save time here//////// re-write an above method to get the hashset not the .size();////////////
	int highestPairShortestPathsHopCount( int a, int b ) {

		HashSet <ArrayList <Integer>> resultFromShortestPath = shortestPaths(a, b);   
		ArrayList<Integer> firstPathFromResults = new ArrayList<Integer>();

//		System.out.println("RESULTS FROM SHORTEST PATHS FOR PAIRS " + a + ", " + b + ".");
//	       	System.out.println();
//		System.out.println( resultFromShortestPath );

		try {
			firstPathFromResults = (ArrayList<Integer>) resultFromShortestPath.toArray()[0];
//			System.out.println("First path from results .size (hop count) " + firstPathFromResults.size());
			return firstPathFromResults.size() - 1;

		} catch (Exception e) {
			return 0;
		}	
	}	
/////////////////////////////////////////////////////////////////////////////////////////////////////////////////
	

	List<Pair> getAllDestinationsForAPlayground(int playgroundID) {
		List<Pair> pairs = new ArrayList<Pair>();
		for (int i=1; i<=this.N; i++) { 
			if (i != playgroundID) pairs.add(new Pair(playgroundID, i));
		}
		return pairs;
	}


	ArrayList <Integer> getFurthestPlaygroundsFrom( int playgroundID ) {
		return getShortestHopCountToAllPlaygroundsFromA( playgroundID );
	}


/////////////can save time here/////////////////////////////////////////////////////////////////////////////////
	ArrayList <Integer> getShortestHopCountToAllPlaygroundsFromA( int playgroundID ) {

                HashMap <Integer, Integer> playgroundIDToPlaygroundHopCount = new HashMap <Integer, Integer>();
		int highestHops = 0; 	//furthest playground hop count

		for(int i=1; i <= this.N; i++) {

//			System.out.println("Playground " + playgroundID + " to " + i + " = " + highestPairShortestPathsHopCount(playgroundID, i)); 
//			System.out.println("Playground " + playgroundID + " to " + i + " = " + shortestPaths(playgroundID, i)); 
//			System.out.println(highestHops);

			if (i != playgroundID) {
				playgroundIDToPlaygroundHopCount.put(i, highestPairShortestPathsHopCount( playgroundID, i ) ) ; //returns the first shortest path's hop count. 
				
				if ( playgroundIDToPlaygroundHopCount.get(i) > highestHops ) highestHops = playgroundIDToPlaygroundHopCount.get(i) ; 
			}
		}

//		System.out.println(playgroundID + " to " + playgroundIDToPlaygroundHopCount);

		return furthestPlaygroundsFromHopsToAllPlaygrounds( playgroundIDToPlaygroundHopCount, highestHops, playgroundID );

	}
////////////////////////////////////////////////////////////////////////////////////////////////////////////////////////


	ArrayList <Integer> furthestPlaygroundsFromHopsToAllPlaygrounds( HashMap <Integer,Integer> playgroundToAllOthersHopCounts, int highestHopsInSet, int playgroundID ) {

		int highestHops = highestHopsInSet;	//furthest playground hop count

//		System.out.println(highestHops);

		ArrayList <Integer> idsOfFurthestPlaygrounds = new ArrayList <Integer>();

		for(int i=1; i <= this.N; i++) {

			if (i != playgroundID) {
				if ( playgroundToAllOthersHopCounts.get(i) == highestHopsInSet )  idsOfFurthestPlaygrounds.add(i);   

			}
		}
		return idsOfFurthestPlaygrounds;
	}



	double distanceOfShortestPathBetween( int a, int b ) {

//		System.out.println();
//		System.out.println("SHORTEST PATHS     " + shortestPaths(a,b));
//		System.out.println();
//		System.out.println("DIJIKSTRA          " + dijkstra(a,b));

		ArrayList <Integer> dijikstraResult = dijkstra(a, b);
		double sumOfPathValues = 0;

		for (int i=0; i < dijikstraResult.size()-1; i++) {

//			System.out.println("ADJ["+dijikstraResult.get(i)+"]["+dijikstraResult.get(i+1)+"]" +  adj [dijikstraResult.get(i)] [dijikstraResult.get(i+1)] );
			sumOfPathValues += adj [dijikstraResult.get(i)] [dijikstraResult.get(i+1)] ;
		}	

//		System.out.println();
//		System.out.println("SUM OF PATHS =  " + sumOfPathValues);
//		System.out.println();

//		return sumOfPathValues;

		return sumOfPaths(shortestPaths(a,b));
	
	}


	class LonLat {
		double lon, lat;
		LonLat ( double lon, double lat) {
			this.lon = lon;
			this.lat = lat;
		}
	}


	void loadLonLatFile( String file ) throws Exception {

		HashMap <Integer, LonLat> idsAndGPSCoords = new HashMap();

		Scanner s = new Scanner ( new FileReader ( file ));
		String z;
		while (s.hasNext()) {
			z = s.nextLine();
			String [] results = z.split(",");
			try {
				idsAndGPSCoords.put(Integer.parseInt(results[0]), (new LonLat (Double.parseDouble(results[1]), Double.parseDouble(results[2])) ) );	

 //				System.out.println( Integer.parseInt(results[0]) +  " " +  idsAndGPSCoords.get(Integer.parseInt(results[0])).lon + ", " + idsAndGPSCoords.get(Integer.parseInt(results[0])).lat ) ;

			} catch (NumberFormatException e) {
				// skip non number lines.
			}
		}

		setLonLat( idsAndGPSCoords );
	}


	void setLonLat( HashMap <Integer, LonLat> idsAndGPSs ) {
		this.lonsAndLats = idsAndGPSs;
	}


	double lengthInKMofShortestDistanceBetween( int a, int b ) {

//		System.out.println("DIJIKSTRA          " + dijkstra(a,b));
//		System.out.println(shortestPaths(a ,b));

		ArrayList <Integer> dijikstraResult = dijkstra(a, b);
		double sumOfGPSPathValues = 0;

		for (int i=0; i < dijikstraResult.size()-1; i++) {

//			System.out.println(	"ID: " + dijikstraResult.get(i)  + ", with CO-ORDINATES:  " + this.lonsAndLats.get( dijikstraResult.get(i)).lon  + ", " + this.lonsAndLats.get(dijikstraResult.get(i)).lat +
//				    "    -TO-    ID: " + dijikstraResult.get(i+1) + ", with CO-ORDINATES:  " + this.lonsAndLats.get( dijikstraResult.get(i+1)).lon + ", " + this.lonsAndLats.get( dijikstraResult.get(i+1)).lat );    
				
			sumOfGPSPathValues += realDistance(	this.lonsAndLats.get(dijikstraResult.get(i)).lat, this.lonsAndLats.get(dijikstraResult.get(i)).lon,   
								this.lonsAndLats.get(dijikstraResult.get(i+1)).lat, this.lonsAndLats.get(dijikstraResult.get(i+1)).lon );

//			System.out.println();
//			System.out.println(sumOfGPSPathValues);
//			System.out.println();
			
		}	


//		System.out.println();
//		System.out.println("SUM OF PATHS =  " + sumOfGPSPathValues);
//		System.out.println();		
//		return sumOfGPSPathValues;

		return sumOfGPSPaths(shortestPaths(a,b));

	}


	Double sumOfGPSPaths( HashSet <ArrayList <Integer>> shortestPaths ) {

		double lowestPathCost = Double.POSITIVE_INFINITY;

		for (int i=0; i<shortestPaths.size();i++) {
     			ArrayList<Integer> a = new ArrayList<Integer>();
              		a = (ArrayList<Integer>) shortestPaths.toArray()[i];

			double pathCost = sumOfGPSPath(a);

			if (pathCost < lowestPathCost) lowestPathCost = pathCost;
		}

		return lowestPathCost;
	}


	double sumOfGPSPath(ArrayList<Integer> path) {

		double totalPathCost = 0.0;
//		System.out.println("path = " +  path);
//		System.out.println();

		for (int i=0;i<path.size()-1;i++){


			totalPathCost	 += realDistance(	this.lonsAndLats.get(path.get(i)).lat, this.lonsAndLats.get(path.get(i)).lon,   
								this.lonsAndLats.get(path.get(i+1)).lat, this.lonsAndLats.get(path.get(i+1)).lon );
						
//			System.out.println(	"ID: " + path.get(i)  + ", with CO-ORDINATES:  " + this.lonsAndLats.get( path.get(i)).lon  + ", " + this.lonsAndLats.get(path.get(i)).lat +
//				    "    -TO-    ID: " + path.get(i+1) + ", with CO-ORDINATES:  " + this.lonsAndLats.get( path.get(i+1)).lon + ", " + this.lonsAndLats.get( path.get(i+1)).lat );    
	

		}

//		System.out.println("path cost " + totalPathCost);
		return totalPathCost;

	}


	Double sumOfPaths( HashSet <ArrayList <Integer>> shortestPaths ) {

		double lowestPathCost = Double.POSITIVE_INFINITY;

		for (int i=0; i<shortestPaths.size();i++) {
     			ArrayList<Integer> a = new ArrayList<Integer>();
              		a = (ArrayList<Integer>) shortestPaths.toArray()[i];

//			System.out.println(a);

			double pathCost = sumOfPath(a);

			if (pathCost < lowestPathCost) lowestPathCost = pathCost;

		}

		return lowestPathCost;
	}


	double sumOfPath(ArrayList<Integer> path) {

		double totalPathCost = 0.0;
//		System.out.println("path = " +  path);

		for (int i=0;i<path.size()-1;i++){

			totalPathCost += adj[path.get(i)][path.get(i+1)];

		}

//		System.out.println("path cost " + totalPathCost);
//		System.out.println();
		return totalPathCost;

	}



	static double realDistance(double lat1, double lon1, double lat2, double lon2 ) {
		
		int R = 6371; // km (change this constant to get miles)
		
		double dLat = (lat2-lat1) * Math.PI / 180;
		double dLon = (lon2-lon1) * Math.PI / 180;
		
		double a = Math.sin(dLat/2) * Math.sin(dLat/2) + Math.cos(lat1 * Math.PI / 180 ) * Math.cos(lat2 * Math.PI / 180 )* Math.sin(dLon/2) * Math.sin(dLon/2);
		double c = 2 * Math.atan2(Math.sqrt(a), Math.sqrt(1-a)); 
		
		double d = R * c;
		
		return d;
	
	}



	void setN (int N) {
		this.N = N;
	}


	int getN() {
		return this.N;
	}


	void printAdjMatrix(){
		
		for (int i=0;i<adj.length; i++){
			for (int j=0;j<adj.length;j++){
				System.out.print(adj[i][j]+ "  :");
			}
			System.out.println("LINE " + i);
		}
	}

	int getPlus1VerticiesForHops() {
		return plus1VerticiesForHops;
	}


	public static void main ( String [] args ) throws Exception {

		long startTime = System.nanoTime();

		Ass2226180140208 G = new Ass2226180140208 ();
		int N = G.getNforGraph(args[0]);
//		System.out.println("N for graph: " + N);
		G.setN(N);							// Set N here for paths beyond the ID's found in the last line of the first file.
		double edges [][] = G.getGraphEdges(args[2], G.getN());
		G.makeGraph(edges); 
										// graph is id indexed. not 0 indexed.
		G.loadLonLatFile(args[1]);


/////////////////////////////////////////////////



/////////////////////////////////////////////////


		//"In the case of a direct link, please ignore this option look for a route that includes one extra link; this constraint applies to all questions for this coursework assignemnt" - cw2. 

		System.out.println();
		System.out.println("Name: Mark Anthony Start");
		System.out.println("Student ID: 180140208");
		System.out.println();
		System.out.println("Question 1:   " + G.howManyShortestPathsBetweenPlaygroundAandB(14, 36));
		System.out.println("Question 2:   " + G.pairsWithHighestNumberOfShortestPaths(G.getN()));
		System.out.println("Question 3:   " + G.getPairsWithHighestNumberOfShortestPathsTotal() );
		System.out.println("Question 4:   " + G.getHighestPairsHopCount() + " edges between " + G.getPlus1VerticiesForHops()  + " vertices.");
		System.out.println("Question 5:   " + G.getFurthestPlaygroundsFrom(9));
		System.out.println("Question 6:   " + G.distanceOfShortestPathBetween(75,67));
		System.out.println("Question 7:   " + G.lengthInKMofShortestDistanceBetween(50,34) );
		System.out.println();
		System.out.println("Execution Time: " + ((System.nanoTime() - startTime)/1000000) + " milliseconds" );
		System.out.println();
		
//		G.printAdjMatrix();
	}


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

		int totalNodes = this.N+1; 

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
?? You must override hashCode() in every class that overrides equals(). Failure to do so will result in a violation of the general contract for Object.hashCode(), which will prevent your class from functioning properly in conjunction with all hash-based collections, including HashMap, HashSet, and Hashtable.
?? */



}
