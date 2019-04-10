package hello.util;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URL;

public class NetworkUtil {
	public static String getExternalAddress() {
		String ip = null;
		BufferedReader in = null;
		try {
	        URL url = new URL("http://checkip.amazonaws.com");
            in = new BufferedReader(new InputStreamReader(url.openStream()));
            ip = in.readLine();
            
        } catch (IOException e) {
			e.printStackTrace();
		} finally {
            if (in != null) {
                try { in.close(); } catch (IOException e) {}
            }
        }
		return ip;
    }
}
