package lbms.plugins.mldht.kad;

import java.util.Arrays;

public class Key {
	
	public static final Key MIN_KEY;
	public static final Key MAX_KEY;
	
	static {
		MIN_KEY = new Key();
		MAX_KEY = new Key();
		Arrays.fill(MAX_KEY.hash, (byte)0xFF); 
	}
	
	public static final int		SHA1_HASH_LENGTH	= 20;
	public static final int		KEY_BITS			= SHA1_HASH_LENGTH * 8;
	protected byte[]			hash				= new byte[SHA1_HASH_LENGTH];
	
}
