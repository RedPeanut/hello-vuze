package aelitis;

import java.io.UnsupportedEncodingException;

import org.junit.Test;

public class TestFindCharset {
	
	@Test
	public void testFindCharset() {
		String originalStr = "ë¶ì° ë°ì´í°ë² ì´ì¤"; // 
		String [] charSet = {"utf-8","euc-kr","ksc5601","iso-8859-1","x-windows-949"};
			
		for (int i=0; i<charSet.length; i++) {
			for (int j=0; j<charSet.length; j++) {
				try {
					System.out.println("[" + charSet[i] +"," + charSet[j] +"] = " + new String(originalStr.getBytes(charSet[i]), charSet[j]));
				} catch (UnsupportedEncodingException e) {
					e.printStackTrace();
				}
			}
		}
	}
}
