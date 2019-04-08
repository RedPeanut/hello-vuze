package gudy.azureus2.core3.util;




public class TimerEvent extends ThreadPoolTask {
	
	private final Timer					timer;
	private final long					created;
	private long						when;
	private final TimerEventPerformer	performer;
	
	//private final boolean	absolute;
	
	private long			uniqueId	= 1;
	
	protected TimerEvent(
			Timer					_timer,
			long					_uniqueId,
			long					_created,
			long					_when,
			TimerEventPerformer		_performer) {
			
			timer		= _timer;
			uniqueId	= _uniqueId;
			when		= _when;
			//absolute	= _absolute;
			performer	= _performer;
			created 	= _created;
	}
	
	@Override
	public void runSupport() {
		performer.perform(this);
	}
	
	public long getWhen() {
		return when;
	}

	public Runnable getRunnable() {
		return this;
	}
	

}
