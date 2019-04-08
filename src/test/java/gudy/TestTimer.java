package gudy;

import gudy.azureus2.core3.util.SimpleTimer;
import gudy.azureus2.core3.util.TimerEvent;
import gudy.azureus2.core3.util.TimerEventPerformer;

public class TestTimer {
	
	public static void main(String[] args) {
		
		SimpleTimer.addPeriodicEvent(null, 1000, new TimerEventPerformer() {
			@Override
			public void perform(TimerEvent event) {
				System.out.println("perform is called...");
			}
		});
		
		while (true) {
			try {
				Thread.sleep(10);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
}
