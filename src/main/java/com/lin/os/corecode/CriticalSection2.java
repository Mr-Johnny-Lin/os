package com.lin.os.corecode;

		import org.springframework.stereotype.Component;
@Component("lock")
public class CriticalSection2 {

//	private static CriticalSection2 lock = new CriticalSection2();

	private int currentReaders = 0;
	private boolean writing = false;

	public synchronized void readLock() {
		while (writing) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}
		currentReaders++;
	}

	public synchronized void readUnlock() {
		currentReaders--;
		notifyAll();
	}

	public synchronized void writeLock() {

		while (writing || currentReaders != 0) {
			try {
				wait();
			} catch (InterruptedException e) {
				e.printStackTrace();

			}
		}

		writing = true;
	}

	public synchronized void writeUnlock() {
		writing = false;
		notifyAll();
	}
}