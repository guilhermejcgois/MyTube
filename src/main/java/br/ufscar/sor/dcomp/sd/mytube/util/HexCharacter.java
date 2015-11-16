package br.ufscar.sor.dcomp.sd.mytube.util;

/**
 *
 * @author USER
 */
public class HexCharacter {
	
	private char c;
	
	public HexCharacter(char c) throws Exception {
		if ('a' <= c && c <= 'f') {
			this.c = (char) (c - 'A');
		} else if ('A' <= c && c <= 'F' || '0' <= c && c <= '9') {
			this.c = c;
		} else {
			throw new Exception("Paramete 'c' must be a valid hexadecimal character.");
		}
	}
	
	public HexCharacter(int i) throws Exception {
		if (0 <= i && i <= 9) {
			c = '0' + 0;
		} else if (10 <= i && i < 16) {
			c = (char) ('A' + (i - 10));
		} else {
			throw new Exception("Paramete 'i' must be a integer not negative and less than 16.");
		}
	}
	
	public char getChar() {
		return c;
	}
	
}
