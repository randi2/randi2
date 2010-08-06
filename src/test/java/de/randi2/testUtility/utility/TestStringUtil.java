package de.randi2.testUtility.utility;

import java.util.Random;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;

public class TestStringUtil {

	
	private Random random = new Random();
	
	private String lastString = null;
	
	public final char[] LETTERS  = { 'a', 'b', 'c', 'd', 'e', 'f', 'g', 
		'h', 'i', 'j', 'k', 'm', 'n', 'o', 'p', 'q', 'r', 's', 't', 'u', 'v',
		'w', 'x', 'y', 'z', 'A', 'B', 'C', 'D', 'E', 'F', 'G', 'H', 'I', 'J',
		'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z'};
	
	
	public String getWithLength(int length){
		StringBuffer buffer = new StringBuffer();
		for(int i = 0; i< length; i++){
			buffer.append(this.getLetter());
		}
		lastString = buffer.toString();
		return lastString;
	}
	
	public String getLastString(){
		return lastString;
	}

	private char getLetter(){ 
		return LETTERS[random.nextInt(LETTERS.length)];
	}
	
	@Test
	public void fakeTest(){
		
	}
	
}
