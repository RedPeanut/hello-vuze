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
		ThreadPoolWorker allocatedWorker;
		synchronized(this) {
			taskQueue.add(runnable);
			if (!threadSemaphore.reserveIfAvailable())
				allocatedWorker = null;
			else
				allocatedWorker = new ThreadPoolWorker();
		}
		return allocatedWorker;
	}
	
	class ThreadPoolWorker extends Thread {
		
		//private volatile Runnable runnable;
		
		public ThreadPoolWorker() {
			super.start();
		}
		
		public void run() {
			try {
				Runnable runnable;
				do {
					runnable = null;
					synchronized (ThreadPool.this) {
						if (taskQueue.size() > 0)
							runnable = taskQueue.remove(0);
						else
							break;
					}
					runIt(runnable);
				} while (runnable != null);
			} finally {
				threadSemaphore.release();
			}
		}
		
		protected void runIt(Runnable runnable) {
			runnable.run();
		}
	}
}
