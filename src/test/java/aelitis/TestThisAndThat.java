package aelitis;

import org.junit.Test;

import hello.util.Util;

public class TestThisAndThat {
	
	@Test
	public void testThisAndThat() {
		byte[] bytes = {0x12,0x34,0x56};
		System.out.println("bytes = " + Util.toHexString(bytes));
		System.out.println("" + '1' + '2');
	}
	
}
