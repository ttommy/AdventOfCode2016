import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

public class AdventOfCode2016Day6 {
	private static final String input = "test-input.txt";
	
	public static void main(String[] argv) {
		AdventOfCode2016Day6 g = new AdventOfCode2016Day6();
		g.solve();
	}
		

	private void solve() {
		//try-with-resources statement will auto-close Stream
		try (Stream<String> lines = Files.lines(Paths.get(input))) {   // intermediate operation; lines of a file to a string steam (Stream<String>)
			Stream<IntStream> streamOfIntStreams = lines.map(s -> s.chars());  // intermediate operation; map each string to a char stream (Stream<IntStream>)
			List<IntStream> intStreamList = streamOfIntStreams.collect(Collectors.toList());       // terminal operation; map int stream to int array
			
			ArrayList<HashMap<Integer,Integer>> retval = new ArrayList<HashMap<Integer,Integer>>();
			for(int i = 0; i < 8; i++) {
				retval.add(new HashMap<Integer,Integer>());
			}
			for(IntStream istream : intStreamList) {
				List<Integer> ilist = istream.boxed().collect(Collectors.toList());  // make each line into a list of integers (representing chars)
				for(int j = 0; j < ilist.size(); j++) {  // now j is the character index into the string of the line (the column)
					HashMap<Integer, Integer> columnMap = retval.get(j);  
					Integer c = ilist.get(j);  // this is the char in Integer format. We will use the char as the key
					if(columnMap.containsKey(c)) {
						Integer count = columnMap.get(c);
						int iCount = count.intValue();
						columnMap.put(c, ++iCount);
						System.out.println("c: "+ c + " is now set to: " + iCount);
					}
					else {
						columnMap.put(c, 1);
						System.out.println("c: "+ c + " is now set to: 1");

					}
					
				}
			}
			printRetval(retval);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	private void printRetval(ArrayList<HashMap<Integer, Integer>> retval) {
		//print 
		StringBuffer solution = new StringBuffer();
		try {
		for(int ii = 0; ii < retval.size(); ii++) {
			HashMap<Integer, Integer> toSortByVal = retval.get(ii);
			Optional<Map.Entry<Integer,Integer>> top = toSortByVal
			.entrySet().stream()  // stream the entry set
			.sorted(Map.Entry.<Integer,Integer>comparingByValue().reversed()) // sort by value reversed for answer 1, remove reversed for answer 2
			.findFirst();  //find the highest value entry
			
			Integer key = top.get().getKey();  //get the key from the highest value entry
			Integer val = top.get().getValue(); //get the value from the highest value entry
			char ccc = (char) key.intValue();  //get the char value of the key
			solution.append(ccc);
		}
		} catch (NoSuchElementException e) {}
		System.out.println("The solution is: " + solution);
		
	}
	
}
