package aelitis.azureus.core.dht.control.impl;

import gudy.azureus2.core3.util.ThreadPoolTask;

public class DHTControlImpl implements DHTControl {
	
	protected DhtTask lookup() {
		
		DhtTask	task = new DhtTask() {

			@Override
			public void runSupport() {
			}
			
		};
		return task;
	}
	
	protected abstract class DhtTask extends ThreadPoolTask {
		//protected DhtTask(ThreadPool threadPool) {}
	}
	
}
