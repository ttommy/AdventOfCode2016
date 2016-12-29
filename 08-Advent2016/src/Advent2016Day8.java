import java.io.IOException;
import java.lang.reflect.Array;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

public class Advent2016Day8 {
	//default
	int mCol = 50;
	int mRow = 6; 
	char[][] screen, tScreen;
	
	static final String COLUMN = "column";
	static final String ROW = "row";
	private static final String RECT = "rect";
	private static final String ROTATE = "rotate";
	
	public static void main(String[] args) throws IOException {
		Advent2016Day8 g = new Advent2016Day8();
//		g.goTest();
//		g.goTestInput();
		g.go();
	}

	private void go() throws IOException {
		initScreen();
		Files.lines(Paths.get("input.txt")).forEach(s -> processLine(s));
		int solution1 = countLitPixels();
		System.out.println(solution1);
	}
	private void goTestInput() throws IOException {
		initScreen(3,7);
		Files.lines(Paths.get("test-input.txt")).forEach(s -> processLine(s));
	}

	private void processLine(String s) {
		String[] arr = s.split(" ");
		if(arr[0].equals(RECT)) {
			String[] dims = arr[1].split("x");
			rect(Integer.parseInt(dims[0]), Integer.parseInt(dims[1]));
		}
		else if(arr[0].equals(ROTATE)) {
			int index = Integer.parseInt(arr[2].split("=")[1]);
			int distance = Integer.parseInt(arr[4]);
			if(arr[1].equals(COLUMN)) {
				rotateColumn(index, distance);
			}
			else {
				rotateRow(index, distance);
			}
			
		}
		printScreen(screen);
	}
	
	private int countLitPixels() {
		int retval = 0;
		for(int i = 0; i < mRow; i++) {
			for(int j = 0; j < mCol; j++) {
				if(screen[i][j] == 'X') retval++;
			}
		}
		return retval;
	}
	
	private void goTest() {
		initScreen();
		printScreen(screen);
		rect(10,2);
		printScreen(screen);
		nums(0);
		printScreen(screen);
		rotate(screen, 0, 1);
		printScreen(screen);
		tScreen = transpose(screen);
		printScreen(tScreen);
		rotate(tScreen, 0, 1);
		printScreen(tScreen);
		screen = transpose(tScreen);
		printScreen(screen);
	}

	private void nums(int rowNum) {
		final int RADIX = 10;
		for(int i = 0; i < mCol; i++) {
			char ch = Character.forDigit(i%RADIX, RADIX);
			screen[0][i] = ch;
		}
	}
	
	static char[] joinArray(char[]... arrays) {
        int length = 0;
        for (char[] array : arrays) {
            length += array.length;
        }

        final char[] result = new char[length];

        int offset = 0;
        for (char[] array : arrays) {
            System.arraycopy(array, 0, result, offset, array.length);
            offset += array.length;
        }

        return result;
    }
	
	private void rotateRow(int index, int distance) {
		rotate(screen, index, distance);
	}
	
	private void rotateColumn(int index, int distance) {
		tScreen = transpose(screen);
		rotate(tScreen, index, distance);
		screen = transpose(tScreen);
	}
	
	private void rotate(char[][] s, int index, int distance) {
		char[] arr = s[index];   
		distance %= arr.length;
		char[] beg = Arrays.copyOfRange(arr, arr.length-distance, arr.length);
		char[] end = Arrays.copyOfRange(arr, 0, arr.length-distance);
		
		char[] result = joinArray(beg, end);
		//System.out.println(Arrays.toString(result));
		s[index] = result;
	}
	
	private char[][] transpose(char[][] a) {
		char[][] retval = new char[a[0].length][a.length];
		for(int i = 0; i < a.length; i++) {
			for(int j = 0; j < a[0].length; j++) {
				retval[j][i] = a[i][j];
			}
		}
		return retval;
	}

	private void rect(int x, int y) {
		for(int i = 0; i < y; i++) {
			for(int j = 0; j < x; j++) {
				screen[i][j] = 'X';
			}
		}		
	}
	
	private void initScreen() {
		screen = new char[mRow][mCol];
		tScreen = new char[mCol][mRow];
		for(int i = 0; i < mRow; i++) {
			for(int j = 0; j < mCol; j++) {
				screen[i][j] = '.';
			}
		}
	}
	private void initScreen(int row, int col) {
		mRow = row;
		mCol = col;
		initScreen();
	}
	
	private void printScreen(char[][] s) {
		int rows = s.length;
		int cols = s[0].length;
		for(int i = 0; i < rows; i++) {
			for(int j = 0; j < cols; j++) {
				System.out.print(s[i][j]);
			}
			System.out.print("\n");
		}
		System.out.println("0000000000000000000000000000000000000000000000000000000000000000000000000000000000000000");
	}
}
