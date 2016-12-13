import java.awt.Point;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 * This solution is kinda elegant in that it avoids the actual instanciation of the 2d array at heart
 * Wanna be code Too cute...expecting ints and square borders
 * Doesn't solve the generic case as part 2 problem illustrates so well
 * @author tpreston
 *
 */
public class AdventOfCode2016Day2 {
	boolean debug = true;
	static final int MIN_IDX = 0;
	static final int MAX_IDX = 2;
	Point pTracker = new Point(1, 1);
	int iDir = 0;

	static final int UP = 0;
	static final int RIGHT = 1;
	static final int DOWN = 2;
	static final int LEFT = 3;

	static final ArrayList<Point> directionStep = new ArrayList<Point>();

	public AdventOfCode2016Day2() {
		Point moveUp = new Point(-1, 0);
		Point moveRight = new Point(0, 1);
		Point moveDown = new Point(1, 0);
		Point moveLeft = new Point(0, -1);

		directionStep.add(moveUp);
		directionStep.add(moveRight);
		directionStep.add(moveDown);
		directionStep.add(moveLeft);
	}

	public static void main(String[] argv) {
		AdventOfCode2016Day2 g = new AdventOfCode2016Day2();
		g.go();
	}

	private void go() {
		List<String> lines = getFileLines();
		ArrayList<Integer> solution1 = new ArrayList<Integer>();
		for (int lineNum = 0; lineNum < lines.size(); lineNum++) {
			String s = lines.get(lineNum);
			for (int i = 0; i < s.length(); i++) {
				char c = s.charAt(i);
				move(c);
			}
			solution1.add(calcPadVal());
		}
		printSolution(solution1);
	}

	private void printSolution(ArrayList<Integer> solution1) {
		System.out.println(solution1);
	}

	private void move(char c) {
		if(debug) System.out.print(calcPadVal() + ": " + pTracker + ": " + c + ": ");
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
		pTracker.translate(directionStep.get(iDir).x, directionStep.get(iDir).y);
		// fix out of bounds by just moving back into the boundary that they
		// moved out of (not rotating)
		if (pTracker.x < MIN_IDX) pTracker.x = MIN_IDX;
		if (pTracker.y < MIN_IDX) pTracker.y = MIN_IDX;
		if (pTracker.x > MAX_IDX) pTracker.x = MAX_IDX;
		if (pTracker.y > MAX_IDX) pTracker.y = MAX_IDX;
		if(debug) System.out.print(calcPadVal()+"\n");
	}

	private Integer calcPadVal() {
		int rowBaseNum = pTracker.x * 3; // 0, 3, 6
		int rowValue = pTracker.y + 1; // values are one based
		int retval = rowBaseNum + rowValue;
		return new Integer(retval);
	}

	private List<String> getFileLines() {
		String fileName = "test.txt";
		List<String> retval = new ArrayList<String>();
		try {
			retval = Files.readAllLines(Paths.get(fileName));
		} catch (IOException e) {
			e.printStackTrace();
		}
		return retval;
	}

}
