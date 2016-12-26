import java.awt.Point;

import edu.princeton.cs.algs4.BreadthFirstPaths;
import edu.princeton.cs.algs4.Graph;

public class Advent2016Day13 {

	public static void main(String[] args) {
		Advent2016Day13 g = new Advent2016Day13();
		g.go();
	}

	private int input = 1362;  //10
	private int xCount = 40;   //10
	private int yCount = 40;   //6
	Point startP = new Point(1,1);
	Point endP = new Point(31,39);
	//each point gIndex is x + (y*xCount)
	//0,0 = 0; 1,0 = 1; 1,1 = 11; 9,1 = 19
	
	private void go() {
		if(!isOpen(startP)) System.out.println("The starting point is a WALL! Something is wrong!");
		// print(xCount,yCount);  // call this only on the small 10x6 test graph
		Graph g = initGraph();
		int shortestDist = calcShortestDistance(g, startP, endP);
		int locsReached = calcLocsWithin(g, startP, 50);
		System.out.println("Shortest distance from "+startP+" to "+endP+" is: "+shortestDist);
		System.out.println("Number of locs within 50 steps of "+startP+" is: "+locsReached);
	}

	private boolean isOpen(Point p) {
		int x = (int)p.getX();
		int y = (int)p.getY();
		int calc = x*x + 3*x + 2*x*y + y + y*y;
		calc += input;
		//others got the 1 bit count using Long.bitCount()
		String binaryS = Integer.toBinaryString(calc);
		long onesCount = binaryS.chars() //IntStream
						.filter(i -> i != '0') //IntStream with only '1' chars remaining
						.count();  // count of the '1' chars
		
		if(onesCount % 2 == 0) { // it's even which means that it is an open space
			return true;
		}
		return false;
	}

	private Graph initGraph() {
		Graph g = new Graph(xCount*yCount);
		//add edges between vertices
		//for every v, add an edge to each next v when the next v is open and not out of bounds
		for(int x = 0; x < xCount; x++) {
			for(int y = 0; y < yCount; y++) {
				Point currentP = new Point(x,y);
				if(isOpen(currentP)) {
					for(Point adjP : new Point[]{new Point(x, y+1), new Point(x+1, y)}) {
						if(isValidEntry(adjP) && isOpen(adjP)) {
							g.addEdge(getPointIndex(currentP), getPointIndex(adjP));
						}
					}
				}
			}
		}
		return g;
	}

	private int calcShortestDistance(Graph g, Point startP, Point endP) {
		BreadthFirstPaths bfs = new BreadthFirstPaths(g, getPointIndex(startP));
		int retval = bfs.distTo(getPointIndex(endP));
		return retval;
	}

	private int calcLocsWithin(Graph g, Point startP, int withinSteps) {
		BreadthFirstPaths bfs = new BreadthFirstPaths(g, getPointIndex(startP));
		int retval = 0;
		for(int i = 0; i < xCount*yCount; i++) {
			if(bfs.hasPathTo(i) && bfs.distTo(i) <= 50) {
				retval++;
			}
		}
		return retval;
	}

	private int getPointIndex(Point p) {
		int retval = p.x + (p.y * xCount);
		return retval;
	}
	
	private boolean isValidEntry(Point p) {
		if(p.x >= 0 && p.x < xCount && p.y >= 0 && p.y < yCount) return true;
		return false;
	}

	// print to show if we have correct calcs
	private void print(int x, int y) {
		printHeader(x);
		for(int i = 0; i < y; i++) {
			printSide(i);
			for(int j = 0; j < x; j++) {
				printPoint(new Point(j, i));
			}
			printNewLine();
		}
	}

	private void printPoint(Point p) {
		boolean open = isOpen(p);
		if(open) {
			System.out.print(".");
		}
		else {
			System.out.print("X");
		}
	}

	private void printNewLine() {
		System.out.println();
	}

	private void printSide(int i) {
		i %= 10;
		System.out.print(i);
	}

	private void printHeader(int x) {
		System.out.print(" ");
		for(int i = 0; i < x; i++) {
			i %= 10;
			System.out.print(i);
		}
		System.out.println();
	}   
}
