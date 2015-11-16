package br.ufscar.sor.dcomp.sd.mytube.util;

/**
 *
 * @author USER
 */
public class HashUtil {
	
	private static HexString hexString;
	private static String hashString;
	
	public static String getHash() {
		return hashString;
	}
	
	public static String getHashFrom(String s) throws Exception {
		hexString = new HexString(s);
		
		hashString = getString();
		
		return hashString;
	}
	
	private static String getString() {
		StringBuilder builder = new StringBuilder();
		
		int i = 0;
		while (i < hexString.size())
			builder.append(hexString.get(i++));
		
		return builder.substring(0);
	}
	
}
