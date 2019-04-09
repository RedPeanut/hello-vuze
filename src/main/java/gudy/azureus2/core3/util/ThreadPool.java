package gudy.azureus2.core3.util;

import hello.util.Log;

import java.util.ArrayList;
import java.util.List;


public class ThreadPool {
	
	private static String TAG = ThreadPool.class.getSimpleName();
	
	final String		name;
	private final int	maxSize;
	
	List<Runnable>	taskQueue	= new ArrayList<>();
	AESemaphore		threadSemaphore;
	
	public static final int MODE_BLOCKING = 0;
	public static final int MODE_NONBLOCKING = 1;
	int mode;
	
	public ThreadPool(String _name, int _maxSize) {
		this(_name, _maxSize, MODE_BLOCKING);
	}
	
	public ThreadPool(String _name, int _maxSize, int _mode) {
		name = _name;
		maxSize = _maxSize;
		threadSemaphore = new AESemaphore(_name, _maxSize);
		mode = _mode;
	}
	
	public ThreadPoolWorker run(Runnable runnable) {
		if (mode == MODE_BLOCKING) {
			threadSemaphore.reserve();
			taskQueue.add(runnable);
			ThreadPoolWorker allocatedWorker = new BlockingThreadPoolWorker();;
			return allocatedWorker;
		} else { // mode == MODE_NONBLOCKING
			ThreadPoolWorker allocatedWorker;
			synchronized(this) {
				taskQueue.add(runnable);
				if (!threadSemaphore.reserveIfAvailable())
					allocatedWorker = null;
				else
					allocatedWorker = new NonBlockingThreadPoolWorker();
			}
			return allocatedWorker;
		}
	}
	
	abstract class ThreadPoolWorker extends Thread {
		ThreadPoolWorker() {
			super.start();
		}
		void runIt(Runnable runnable) {
			runnable.run();
		}
		abstract public void run(); // interface Runnable in Thread
	}
	
	class BlockingThreadPoolWorker extends ThreadPoolWorker {
		@Override
		public void run() {
			try {
				if (taskQueue.size() > 0) {
					Runnable runnable = taskQueue.remove(0);
					runIt(runnable);
				}
			} finally {
				threadSemaphore.release();
			}
		}
	}
	
	class NonBlockingThreadPoolWorker extends ThreadPoolWorker {
		@Override
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
	}
}
