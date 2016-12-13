import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class AdventOfCode2016Day2Part2 {
	static final int MIN_IDX = 0;
	
	// change MAX_IDX and startedPosition and keypad
	static final int MAX_IDX = 4;
	//static final int MAX_IDX1 = 2;
	//static final int MAX_IDX2 = 4;
	
	// set the starting position to 5
	Point startingPosition = new Point(2, 0);
	//Point startingPosition1 = new Point(1, 1);
	//Point startingPosition2 = new Point(2, 0);
	 
	//define what the keypad looks like, go UP from D gets you to B, go R from B gets you to C, etc
	private char[][] keypad = new char[][] {
		"00100".toCharArray(), // [0][0-6]
		"02340".toCharArray(), // [1][0-6]
	    "56789".toCharArray(), // [2][0-6]
	    "0ABC0".toCharArray(), // [3][0-6]
	    "00D00".toCharArray()  // [4][0-6]
	};
	private char[][] keypad1 = new char[][] {
		"123".toCharArray(), // [0][0-2]
		"456".toCharArray(), // [1][0-2]
		"789".toCharArray()  // [2][0-2]
	};
	  
	//debug flag
	boolean debug = true;
	
	//start at 5
	Point pTracker = startingPosition;
	int iDir = 0;
	private final static int UP = 0;
	private final static int RIGHT = 1;
	private final static int DOWN = 2;
	private final static int LEFT = 3;

	//0 is moveUp, 1 is moveRight, 2 is moveDown, 3 is moveLeft. See initializeDirectionStep()
	static final ArrayList<Point> directionStep = new ArrayList<Point>();

	public AdventOfCode2016Day2Part2() {
		initializeDirectionStep();
	}

	private void initializeDirectionStep() {
		Point moveUp 	= new Point(-1, 0);
		Point moveRight = new Point(0, 1);
		Point moveDown 	= new Point(1, 0);
		Point moveLeft 	= new Point(0, -1);

		directionStep.add(moveUp);
		directionStep.add(moveRight);
		directionStep.add(moveDown);
		directionStep.add(moveLeft);		
	}

	public static void main(String[] argv) throws IOException {
		AdventOfCode2016Day2Part2 g = new AdventOfCode2016Day2Part2();
		g.go();
	}

	private void go() throws IOException {
		//read all of the lines from the files and store as a List of Strings
		List<String> lines = Files.readAllLines(Paths.get("test.txt"));
		//a place to store the solution. The solution is the keypad element at the end of each line.
		ArrayList<Character> solution2 = new ArrayList<Character>();
		//iterate each line and at the end of each line, store the keypad element as part of the solution
		for (int lineNum = 0; lineNum < lines.size(); lineNum++) {
			String s = lines.get(lineNum);
			//iterate the chars of the line and make the specified moves
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				move(c);
			}
			solution2.add(calcPadVal());
		}
		System.out.println("Solution: " + solution2);

	}

	private char calcPadVal() {
		char c = keypad[pTracker.x][pTracker.y]; 
		return c;		
	}

	private void move(char c) {
		if(debug) System.out.println(calcPadVal() + ": " + pTracker + ": " + c + ": ");
		switch (c) {
		case 'U':
			iDir = UP;
			move();
			break;
		case 'D':
			iDir = DOWN;
			move();
			break;
		case 'R':
			iDir = RIGHT;
			move();
			break;
		case 'L':
			iDir = LEFT;
			move();
			break;
		}
	}

	private void move() {
		// move the tracker (potentiall out of bounds)
		int moveX = directionStep.get(iDir).x;
		int moveY = directionStep.get(iDir).y;
		int checkX = pTracker.x + moveX;
		int checkY = pTracker.y + moveY;
		if(checkX < MIN_IDX || checkY < MIN_IDX || checkX > MAX_IDX || checkY > MAX_IDX) return; //don't move out of bounds
		//assert, this step move will not be out of bounds from keypad[][] perspective
		pTracker.translate(moveX, moveY);
		// check and fix condition where we have moved off of an active key 
		char c = keypad[pTracker.x][pTracker.y];
		if(c == '0') { 
			if(iDir == UP) move('D');
			else if (iDir == DOWN) move('U');
			else if (iDir == RIGHT) move('L');
			else if (iDir == LEFT) move('R');
		}
		
		if(debug) System.out.print(calcPadVal()+"\n");
	}


}
