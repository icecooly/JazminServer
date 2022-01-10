package jazmin.core;

import java.util.concurrent.ThreadFactory;
import java.util.concurrent.atomic.AtomicInteger;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author yama
 * @date Jun 21, 2014
 */
public class JazminThreadFactory implements ThreadFactory{
	private static Logger logger=LoggerFactory.getLogger(JazminThreadFactory.class);
	private AtomicInteger threadCounter=new AtomicInteger();
	private String threadName;
	public JazminThreadFactory(String name) {
		threadName=name;
	}
	//
	@Override
	public Thread newThread(Runnable r) {
		Thread t=new Thread(r);
		t.setContextClassLoader(Jazmin.getAppClassLoader());
		t.setName(threadName+"-"+threadCounter.incrementAndGet());
		Thread.UncaughtExceptionHandler logHander=new Thread.UncaughtExceptionHandler() {
			@Override
			public void uncaughtException(Thread t, Throwable e) {
				logger.error(e.getMessage(),e);
			}
		};
		t.setUncaughtExceptionHandler(logHander);
		return t;
	}
}
