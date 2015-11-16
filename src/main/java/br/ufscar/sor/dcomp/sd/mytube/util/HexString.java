package br.ufscar.sor.dcomp.sd.mytube.util;

/**
 *
 * @author USER
 */
public class HexString {
	
	private HexCharacter[] s;

	public HexString(String s) throws Exception {
		int l = s.length();
		int g = s.hashCode();
		
		this.s = new HexCharacter[l];
		
		for (int i = 0; i < l; i++) {
			int h = (s.charAt(i) * g^(l - i + 1)) % 16;
			
			this.s[i] = new HexCharacter(h);
		}
	}

	public int size() {
		return s.length;
	}

	public char get(int i) {
		return s[i].getChar();
	}
	
}
