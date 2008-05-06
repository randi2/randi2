package de.randi2.utility;

import java.util.Random;

import org.springframework.beans.factory.annotation.Autowired;

public final class TestStringUtil {

	@Autowired
	private Random random;
	
	public final char[] LETTERS  = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
		'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	
	public String getWithLength(int length){
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i< length; i++){
			buffer.append(this.getLetter());
		}
		return buffer.toString();
	}

	private char getLetter(){ 
		return LETTERS[random.nextInt(LETTERS.length)];
	}
	
}
