package gudy.azureus2.core3.util;

public abstract class ThreadPoolTask extends AERunnable {
	
	protected ThreadPool.ThreadPoolWorker worker;
	
	public void	taskStarted() {}
	public void taskCompleted() {}
	
}
