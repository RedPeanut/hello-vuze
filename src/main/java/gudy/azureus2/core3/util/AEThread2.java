package gudy.azureus2.core3.util;



public abstract class AEThread2 {

	private ThreadWrapper	wrapper;

	private String			name;
	private final boolean	daemon;
	
	public AEThread2(
			String		_name,
			boolean		_daemon) {
		name		= _name;
		daemon		= _daemon;
	}

	public void start() {
		wrapper = new ThreadWrapper(name, false);
		wrapper.start(this, name);
	}
	
	public abstract void run();
	
	protected static class ThreadWrapper extends Thread {
		
		private AEThread2	target;
		
		protected ThreadWrapper(
			String		name,
			boolean		daemon) {
			super(name);
			setDaemon(daemon);
		}
		
		protected void start(
			AEThread2	_target,
			String		_name) {
			target	= _target;
			setName(_name);
			super.start();
		}
		
		public void run() {
			while(true) {
				
				try {
					target.run();
				} catch(Throwable e) {
				} finally {
					target = null;
				}
				 
				if (isInterrupted() || !Thread.currentThread().isDaemon()) {
					break;
				}
			}
		}
	}
}
