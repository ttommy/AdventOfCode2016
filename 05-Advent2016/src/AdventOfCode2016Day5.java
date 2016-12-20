
public class AdventOfCode2016Day5 {
	String input = "reyedfim";  // answer is 863DDE27
//	String input = "abc";
	char[] password = new char[8];
	
	public static void main(String[] argv) {
		AdventOfCode2016Day5 g = new AdventOfCode2016Day5();
		g.go();
	}

	private void go() {
		boolean solved = false;
		int i = 0;
		while(!solved) {
			StringBuffer in = new StringBuffer();
			in.append(input);
			in.append(i);
			String s = md5(in.toString());
			if(s.startsWith("00000")) {
				System.out.println(in + ": " + s);
				
				//get the position within password
				int pos = -1;
				try {
					pos = Integer.parseInt(Character.toString(s.charAt(5))) ;
				}
				catch(NumberFormatException e) {}
				
				//add to password if valid position
				if(pos >= 0 && pos < 8 && password[pos] == '\0') {
					password[pos] = s.charAt(6);
				}
				
				//check if solved
				solved = true;
				for(int j = 0; j < password.length; j++) {
					if(password[j] == '\0') {
						solved = false;
						break;
					}
				}
			}
			i++;
		}
		System.out.println(password);
	}
	
	public String md5(String md5) {
		String retval = null;
		try {
			java.security.MessageDigest md = java.security.MessageDigest.getInstance("MD5");
		    byte[] array = md.digest(md5.getBytes());
		    retval = javax.xml.bind.DatatypeConverter.printHexBinary(array);
		} 
		catch (java.security.NoSuchAlgorithmException e) {
		    e.printStackTrace();
		}
		return retval;
	}
}
