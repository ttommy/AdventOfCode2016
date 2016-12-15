import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.stream.Stream;

public class AdventOfCode2016Day3 {
	int j = 0;
	boolean debug = true;
	boolean byColumn = true;
	int[][] buffer = new int[3][3];
	int rowNum = 0;
	int validCount = 0;
	
	//constructor
	public AdventOfCode2016Day3() {
		initialize2DArray();
	}
	
	public static void main(String[] argv) {
		AdventOfCode2016Day3 g = new AdventOfCode2016Day3();
		g.go();
	}
	
    private void go() {
    	//try-with-resources statement will auto-close Stream
        try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
        	long answer = 0;
        	if(byColumn) { // by column
        		lines.forEach( s -> countValidColumnInput(s) );
        		process2DArray();  // process it one last time
        		answer = validCount;
        	}
        	else { // by row
        		answer = lines.filter(s -> validInput(s)).count();
        	}
        	System.out.println("\nAnswer:\nvalid triangles: " + answer);
        } catch (IOException e) {
        	e.printStackTrace();
        }
    }
    
    private void countValidColumnInput(String s) {
    	System.out.println("row " + rowNum + " %3 == " + rowNum % 3);
    	if(rowNum == 1634) {
    		System.out.println("1634 here");
    	}
    	int i = rowNum % 3;
    	if(i == 0) process2DArray(); // process every 3 rows
    	//put current row into 2D array
		String[] ss = s.trim().split("\\s+");
		int a = Integer.parseInt(ss[0]);
		int b = Integer.parseInt(ss[1]);
		int c = Integer.parseInt(ss[2]);
		buffer[i][0] = a;
		buffer[i][1] = b;
		buffer[i][2] = c;
    	rowNum++;
    }

	private void process2DArray() {
		if(rowNum == 0) return; //don't have anything to process yet
		
		for(int j = 0; j < 3; j++) {
			int a = buffer[0][j];
			int b = buffer[1][j];
			int c = buffer[2][j];
			if(isValid(a, b, c)) validCount++;
		}
		initialize2DArray();
	}

	private boolean validInput(String s) {
		boolean retval = true;
		String[] ss = s.trim().split("\\s+");
		int a = Integer.parseInt(ss[0]);
		int b = Integer.parseInt(ss[1]);
		int c = Integer.parseInt(ss[2]);
		retval = isValid(a, b, c);
		if(debug) if(retval == false) System.out.println(++j + ": "+ a+" "+b+" "+c);
		return retval;
	}

	private boolean isValid(int a, int b, int c) {
		boolean retval = true;
		if(a >= (b + c)) retval = false;
		else if(b >= (a + c)) retval = false;
		else if(c >= (a + b)) retval = false;
		return retval;
	}
	
	private void initialize2DArray() {
		buffer = new int[][] {
			{0,0,0}, // [0][0-2]
			{0,0,0}, // [1][0-2]
			{0,0,0}  // [2][0-2]
		};	
	}

}
