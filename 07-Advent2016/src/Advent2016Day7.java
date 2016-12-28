import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class Advent2016Day7 {
	String input = "input.txt";
	private int tlsLines;
	private int sslLines;
	ArrayList<String> outsideParen = new ArrayList<String>();
	ArrayList<String> insideParen = new ArrayList<String>();

	public static void main(String[] argv) {
		Advent2016Day7 g = new Advent2016Day7();
		g.go();
	}

	private void go() {
		try {
			List<String> lines = Files.lines(Paths.get(input)).collect(Collectors.toList()); 
			for(String line : lines) {
				if(supportsTLS(line)) tlsLines++;
				if(supportsSSL(line)) sslLines++;
			}
		} catch (IOException e) {
			e.printStackTrace();
		}
		System.out.println("Total TLS count: " + tlsLines);
		System.out.println("Total SSL count: " + sslLines);
	}

	private boolean supportsSSL(String s) {
		parseInOut(s);
		for(String outsideS : outsideParen) {
			ArrayList<String> matches = containsABAReflection(outsideS);
			for(String r : matches) {
				for(String insideS : insideParen) {
					StringBuffer search = new StringBuffer();
					search.append(r.charAt(1));
					search.append(r.charAt(0));
					search.append(r.charAt(1));					
					if(insideS.contains(search.toString())) return true;
				}
			}
		}
		return false;
	}

	private void parseInOut(String s) {
		outsideParen = new ArrayList<String>();
		insideParen = new ArrayList<String>();
		String[] sarray = s.split("\\[");
		
		for(String ss : sarray) {
			if(ss.contains("]")) {
				String[] abc = ss.split("]");
				outsideParen.add(abc[1]);
				insideParen.add(abc[0]);
			}
			else {
				outsideParen.add(ss);
			}
		}
		
	}
	
	private boolean supportsTLS(String s) {
		parseInOut(s);
		for(String insideS : insideParen) {
			if(containsReflection(insideS)) {
				printer("returning false...inside contains", s, insideParen, outsideParen);
				return false;
			}
		}
		
		for(String outsideS : outsideParen) {
			if(containsReflection(outsideS)) { 
				printer("returning true...outside contains", s, insideParen, outsideParen);
				return true;
			}
		}
		printer("nothing anywhere", s, insideParen, outsideParen);
		return false;
	}
	
	private void printer(String msg, String s, ArrayList<String> insideParen, ArrayList<String> outsideParen) {
		System.out.println("========= " +msg+ " =========");
		System.out.println("=========S=========");
		System.out.println(s);
		System.out.println("========A==========");
		outsideParen.stream().forEach(System.out::println);
		System.out.println("========B==========");
		insideParen.stream().forEach(System.out::println);		
	}

	private ArrayList<String> containsABAReflection(String s) {
		ArrayList<String> retval = new ArrayList<String>();
		int startIndex = 0;
		while(s.length()-startIndex >= 3) {
			if(isABAReflection(s, startIndex)) retval.add(s.substring(startIndex, startIndex+3));
			startIndex++;
		}
		return retval;
	}
	
	private boolean containsReflection(String s) {
		int startIndex = 0;
		while(s.length()-startIndex >= 4) {
			if(isReflection(s, startIndex)) return true;
			startIndex++;
		}
		return false;
	}

	private boolean isABAReflection(String s, int startIndex) {
		if(s.charAt(startIndex+0) == s.charAt(startIndex+1)) return false;
		if((s.charAt(startIndex+0) == s.charAt(startIndex+2))) return true;
		return false;
	}
	
	private boolean isReflection(String s, int startIndex) {
		if(s.charAt(startIndex+0) == s.charAt(startIndex+1)) return false;
		if((s.charAt(startIndex+0) == s.charAt(startIndex+3)) && (s.charAt(startIndex+1) == s.charAt(startIndex+2))) return true;
		return false;
	}

}
