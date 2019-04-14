package lbms.plugins.mldht.kad;

public class DHT {
	
	Node node;
	
	public void start() {
		node = new Node(this);
	}
}
