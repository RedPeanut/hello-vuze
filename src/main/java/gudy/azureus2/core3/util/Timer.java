package gudy.azureus2.core3.util;

import java.util.Iterator;
import java.util.Set;
import java.util.TreeSet;



public class Timer implements Runnable {

	private ThreadPool	threadPool;
	private Set<TimerEvent>	events = new TreeSet<TimerEvent>();
	
	private long	uniqueIdNext	= 0;
	
	public Timer(String	name, int threadPoolSize) {
		
		threadPool = new ThreadPool(name, threadPoolSize);
		
		Thread t = new Thread(this, "Timer:" + name);
		t.setDaemon(true);
		t.setPriority(Thread.NORM_PRIORITY);
		t.start();
	}

	public synchronized TimerEvent addEvent(
			String				name,
			long				creationTime,
			long				when,
			TimerEventPerformer	performer) {
			
		TimerEvent event = new TimerEvent(this, uniqueIdNext++, creationTime, when, performer);
		events.add(event);

		return (event);
	}
	
	public TimerEventPeriodic addPeriodicEvent(
			String name,
			long frequency,
			TimerEventPerformer performer) {
		return new TimerEventPeriodic(
				this,
				frequency,
				performer);
	}

	@Override
	public void run() {
		while (true) {
			TimerEvent	eventToRun = null;
			synchronized(this) {
				long	now = SystemTime.getCurrentTime();
				Iterator<TimerEvent> it = events.iterator();
				TimerEvent	nextEvent = it.next();
				long rem = nextEvent.getWhen() - now;
				if (rem <= SystemTime.TIME_GRANULARITY_MILLIS) {
					eventToRun = nextEvent;
					it.remove();
				}
			}
			
			if (eventToRun != null) {
				threadPool.run(eventToRun.getRunnable());
			}
		}
	}

}
