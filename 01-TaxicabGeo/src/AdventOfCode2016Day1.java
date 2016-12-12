import java.awt.Point;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Scanner;

/**
 * http://adventofcode.com/2016/day/1
 * 
 * @author tpreston
 *
 */
public class AdventOfCode2016Day1 {
	//debug flag
	boolean debug = false;
	
	//point being tracked through execution. Value at end solves first part of problem
	Point pTrack = new Point(0,0);
	
	//point where the lines intersect for the first time (solves second part of problem)
	Point pSolution = null;
	HashMap<Point, Point> points = new HashMap<Point, Point>();

	int iDir = 0;
	static final int NORTH = 0;
	static final int EAST = 1;
	static final int SOUTH = 2;
	static final int WEST = 3;
	
	static final ArrayList<Point> directionStep = new ArrayList<Point>();
	
	
	public AdventOfCode2016Day1() {
		Point moveNorth = new Point(0, 1);
		Point moveEast = new Point(1, 0);
		Point moveSouth = new Point(0, -1);
		Point moveWest = new Point(-1, 0);

		directionStep.add(moveNorth);
		directionStep.add(moveEast);
		directionStep.add(moveSouth);
		directionStep.add(moveWest);
	}
	
	public static void main(String[] argv) throws FileNotFoundException {
		AdventOfCode2016Day1 g = new AdventOfCode2016Day1();
		g.go();
	}

	private void go() throws FileNotFoundException {
		List<String> inputList = readInputFile("input.txt");
		for(String s : inputList) {
			processListItem(s);
		}
		System.out.println("Part 1 Solution: " + solutionString(pTrack));
	}

	private void processListItem(String s) {
		String inputDirection = s.substring(0, 1);
		String distStr = s.substring(1);
		int inputDistance = Integer.parseInt(distStr);
		
		if(inputDirection.equals("R")) {
			moveDirRight();
		}
		else {
			moveDirLeft();
		}
		
		Point moveCoord = directionStep.get(iDir);
		for(int i = 0; i < inputDistance; i++) {
			pTrack.translate(moveCoord.x, moveCoord.y);
			updateMap(new Point(pTrack));
		}
	}

	private void updateMap(Point p2) {
		//return if already solved
		if(pSolution != null) return;
		
		//assert: not yet solved
		if(points.containsKey(p2)) {
			pSolution = new Point(p2);
			System.out.println("Part 2 Solution:" + solutionString(p2));
		}
		else {
			if(debug) System.out.println("Adding: "+p2 + " NOW SIZE: "+points.size());
			points.put(p2, p2);
		}
	}
	
	
	//utilities
	
	private List<String> readInputFile(String inFile) throws FileNotFoundException {
		File f = new File(inFile);
		Scanner s = new Scanner(f);
		String fileStr = s.nextLine();
		String[] splitStr = fileStr.split(", ");
		List<String> retval = Arrays.asList(splitStr);
		s.close();
		return retval;
	}
	
	private void moveDirLeft() {
		iDir -= 1;
		if(iDir < 0) iDir = 3;
	}

	private void moveDirRight() {
		iDir += 1;
		if(iDir > 3) iDir = 0;
	}
	
	private int calculateDist(Point p) {
		int x = Math.abs((int) p.getX());
		int y = Math.abs((int) p.getY());
		return x+y;
	}

	private String solutionString(Point p) {
		String retval =  p + " Distance: " + calculateDist(p);
		return retval;
	}

}
