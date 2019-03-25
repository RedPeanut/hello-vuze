package gudy.azureus2.core3.util;

import java.util.ArrayList;
import java.util.List;

public class ThreadPool {
	
	List<Runnable>	taskQueue	= new ArrayList<>();
	AESemaphore		threadSemaphore;
	
	public ThreadPool(String name, int maxSize) {
		threadSemaphore = new AESemaphore(name, maxSize);
	}
	
	public ThreadPoolWorker run(Runnable runnable) {
		synchronized(this) {
			taskQueue.add(runnable);
			//threadSemaphore.reserveIfAvailable();
			return new ThreadPoolWorker();
		}
	}
	
	class ThreadPoolWorker extends Thread {
		
		private /*volatile */Runnable runnable;
		
		public ThreadPoolWorker() {
			super.start();
		}
		
		public void run() {
			do {
				synchronized (ThreadPool.this) {
					if (taskQueue.size() > 0)
						runnable = taskQueue.remove(0);
					else
						break;
				}
				
				synchronized (ThreadPool.this) {
					
				}
				
				runIt(runnable);
				
			} while (runnable != null);
		}
		
		protected void runIt(Runnable runnable) {
			runnable.run();
		}
	}
}
