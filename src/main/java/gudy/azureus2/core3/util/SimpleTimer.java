package gudy.azureus2.core3.util;




public class SimpleTimer {
	
	protected static final Timer timer;
	
	static {
		timer = new Timer("Simple Timer", 32);
		//timer.setLogCPU();
		//timer.setLogging(true);
		addPeriodicEvent(
			"SimpleTimer:ticker",
			1000,
			new TimerEventPerformer() {
				private int tickCount;
				@Override
				public void perform(TimerEvent event) {
					tickCount++;
				}
			}
		);
	}
	
	public static TimerEventPeriodic addPeriodicEvent(
			String				name,
			long				frequency,
			TimerEventPerformer	performer) {
		TimerEventPeriodic	res = timer.addPeriodicEvent(name, frequency, performer);
		return (res);
	}
}
