import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Stream;

/**
 * need order that you see distinct lowercase lettters
 * sectorID is at the end of the dash separated parse
 * checksum is within square brackets at the very end
 * checksum should in order of most frequent letters, when tie, use alpha order
 * 
 * @author tpreston
 *
 */
public class Advent2016Day4 {
	//char, how many and first instance
	//HashMap<Character, SimpleEntry<Integer, Integer>> map = new HashMap<Character, SimpleEntry<Integer, Integer>>();
	LinkedHashMap<Character, Integer> map = new LinkedHashMap<Character, Integer>();
	private boolean debug = true;
	private boolean debugh = false;
	int goodChecksums = 0;
	int sectorTotal = 0;
	int northPoleSectorId = 0;
	int lineNum = 0;
	
	public Advent2016Day4() {}
	
	public static void main(String[] argv) {
		Advent2016Day4 g = new Advent2016Day4();
		g.go();
	}

	private void go() {		
		try (Stream<String> lines = Files.lines(Paths.get("input.txt"))) {
			//instance method reference
			lines.forEach(this::processLine);
		}
		catch(IOException ioe) {
			ioe.printStackTrace();
		}
		System.out.println("Total Good Checksums: " + goodChecksums + " Sector Total: " + sectorTotal + " Northpole sectorID: "+northPoleSectorId);

	}

	/**
	 * Each line is in the form of: key [checksum]
	 * for example: some-key-delimited-by-dash [achecksum]
	 * the key can be validated against the checksum by running: boolean isValidChecksum(key, checksum)
	 * checksum is valid if isValidChecksum(key, 
	 * @param s
	 */
	private void processLine(String s) {
		if(debugh ) System.out.println("s: "+s);
		// get the key and the checksum for this line
		String checksum = s.substring(s.indexOf('[')+1, s.length()-1);
		String key = s.substring(0, s.indexOf('['));
		String[] splitKey = key.split("-");
		int sectorId = Integer.parseInt(splitKey[splitKey.length - 1]);
		key = key.substring(0, key.lastIndexOf('-'));
		
		//check valid checksum		
		if(isValidChecksum(key, checksum)) {
			goodChecksums++;
			sectorTotal += sectorId;
		}
		//figure out how far to shift the char to the right
		int rightShift = sectorId % 26;
		String decrypt = decrypt(key, rightShift);
		System.out.println(++lineNum + " Decypt: " + decrypt);
		if(decrypt.contains("north") && decrypt.contains("pole")) {
			northPoleSectorId = sectorId;
		}
	}
	
	private String decrypt(String key, int rightShift) {
		//letters 0 - 25
		//dashes replace with space
		StringBuffer buf = new StringBuffer();
		for(int i = 0; i < key.length(); i++) {
			char c = key.charAt(i);
			if(c == '-') {
				buf.append(' ');
			}
			else {
				int ci = c - 'a'; // 0 - 25
				ci += rightShift; 
				ci %= 26;
				ci += 'a';
				buf.append((char)ci);
			}
		}
		return buf.toString();
	}

	private boolean isValidChecksum(String key, String checksum) {
		boolean retval = false;
		//first get a count by character  
		LinkedHashMap<Character, Integer> mapCharToCount = mapCharToCount(key);
		//now order first by value, then by char
		Map<Character, Integer> sortedList = new LinkedHashMap<>();
		
		//using Java 8 stream sorting with a primary and secondary sort
		mapCharToCount.entrySet().stream()  //gives stream of entry: Stream<Entry<Character,Integer>>
		.sorted(Map.Entry.<Character,Integer>comparingByValue()
		.reversed()
		.thenComparing(Map.Entry.<Character,Integer>comparingByKey()))
		.forEachOrdered(x -> sortedList.put(x.getKey(), x.getValue()));
		
		
		if(debugh) System.out.println("Sorted: " + sortedList);
		StringBuffer realChecksum = new StringBuffer();
		Iterator<Entry<Character,Integer>> it = sortedList.entrySet().iterator();
		for(int i = 0; i < 5; i++) {
			realChecksum.append(it.next().getKey());
		}
		if(checksum.contentEquals(realChecksum)) {
			retval = true;
		}
		if(debug) System.out.println("Key: "+ key + " realChecksum: "+realChecksum + " vs claimedChecksum: "+checksum + " REAL: " + retval);

		return retval;
	}

	public LinkedHashMap<Character, Integer> mapCharToCount(String key) {
		// create a map of key string character to the count of that character occurring in the key string
		LinkedHashMap<Character, Integer> mapCharToCount = new LinkedHashMap<Character, Integer>();
		// impl char-count
		for(int i = 0; i < key.length(); i++) {
			Character nextC = key.charAt(i);
			if(!(nextC >= 'a' && nextC <= 'z') ) continue; // ignore everything not between a - z
			if(mapCharToCount.containsKey(nextC)) {
				Integer count = mapCharToCount.get(nextC);
				mapCharToCount.put(nextC, ++count);
			}
			else { // map doesn't yet contain this key
				mapCharToCount.put(nextC, 1);
			}
		}
		if(debugh) mapCharToCount.forEach((c, j) -> System.out.println(c + " :: " + j) );
		return mapCharToCount;
	}	
}
