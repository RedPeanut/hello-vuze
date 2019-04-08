package gudy.azureus2.core3.util;






public class TimerEventPeriodic implements TimerEventPerformer {

	private final Timer					timer;
	private final long					frequency;
	//private final boolean				absolute;
	private final TimerEventPerformer	performer;
	
	private String				name;
	private TimerEvent			currentEvent;
	private boolean				cancelled;
	
	protected TimerEventPeriodic(
			Timer				_timer,
			long				_frequency,
			TimerEventPerformer	_performer) {
		
		timer		= _timer;
		frequency	= _frequency;
		//absolute	= _absolute;
		performer	= _performer;

		long now = SystemTime.getCurrentTime();
		currentEvent = timer.addEvent(null, now, now + frequency, this);
	}

	@Override
	public void perform(TimerEvent event) {
		performer.perform(event);
		long now = SystemTime.getCurrentTime();
		currentEvent = timer.addEvent(name, now, now + frequency, this);
	}
	
}
