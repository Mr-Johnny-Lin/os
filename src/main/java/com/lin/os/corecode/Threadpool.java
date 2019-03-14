package com.lin.os.corecode;

import com.lin.os.controller.UploadController;
import com.lin.os.data.TestData;
import com.lin.os.data.TestResult;
import org.springframework.context.ApplicationContext;
import org.springframework.web.context.support.WebApplicationContextUtils;

import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Threadpool {

	private List results;
	private long pool_starttime;
	private ExecutorService pool;
	private CriticalSection2 lock;

	public Threadpool(int nThreads) {
		pool = Executors.newFixedThreadPool(nThreads);
		ApplicationContext context = WebApplicationContextUtils.getRequiredWebApplicationContext(UploadController.getServletContext());
		lock = (CriticalSection2) context.getBean("lock");
        results = new ArrayList();
	}

	public List excute(List work) {

		pool_starttime = new Date().getTime();
		Iterator<TestData> i = work.iterator();
		while (i.hasNext()) {
			TestData data = i.next();
			pool.execute(new Runnable()  {
				@Override
				public void run() {
					List result = new ArrayList();
					try {
						result.add(new TestResult(data.getId(), data.getRole() + "creat", gettime(new Date())));
						Thread.sleep(data.getStarttime());
						result.add(new TestResult(data.getId(), data.getRole() + "apply", gettime(new Date())));
						if (data.getRole().equals("R")) {
						    lock.readLock();
							result.add(new TestResult(data.getId(), data.getRole() + "start", gettime(new Date())));
							Thread.sleep(data.getRunningtime());
							result.add(new TestResult(data.getId(), data.getRole() + "finish", gettime(new Date())));

						} else {
                            lock.writeLock();
                            result.add(new TestResult(data.getId(), data.getRole() + "start", gettime(new Date())));
                            Thread.sleep(data.getRunningtime());
							result.add(new TestResult(data.getId(), data.getRole() + "finish", gettime(new Date())));
						}
						addResult(result);
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
					finally {
					    if (data.getRole().equals("R")){
					        lock.readUnlock();
                        }
                        else {
                            lock.writeUnlock();
                        }
                    }
				}
			});
		}

		pool.shutdown();
		try {
			if (pool.awaitTermination(100, TimeUnit.SECONDS)){
				return getResult();
			}

		} catch (InterruptedException e) {
			e.printStackTrace();
		}
			return null;
	}
	/*private class Mywork implements Callable<List>{
		Mywork
		@Override
		public List call() throws Exception {
			List result = new ArrayList();
			try {
				result.add(new TestResult(data.getId(), data.getRole() + "creat", gettime(new Date())));
				Thread.sleep(data.getStarttime()*1000);
				result.add(new TestResult(data.getId(), data.getRole() + "apply", gettime(new Date())));
				if (data.getRole().equals("R")) {
					lock.readLock();;
					result.add(new TestResult(data.getId(), data.getRole() + "start", gettime(new Date())));
					Thread.sleep(data.getRunningtime()*1000);
					result.add(new TestResult(data.getId(), data.getRole() + "finish", gettime(new Date())));

				} else {
					lock.writeLock();;
					result.add(new TestResult(data.getId(), data.getRole() + "start", gettime(new Date())));
					Thread.sleep(data.getRunningtime()*1000);
					result.add(new TestResult(data.getId(), data.getRole() + "finish", gettime(new Date())));
				}
				addResult(result);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
			finally {
				if (data.getRole().equals("R")){
					lock.readUnlock();
				}
				else {
					lock.writeUnlock();
				}
			}
		}

	}*/
 	private synchronized  void addResult(List result){
		results.addAll(result);
	}
	private static  class  ComoareResult implements Comparator{

		@Override
		public int compare(Object o1, Object o2) {
			TestResult result1 = (TestResult)o1;
			TestResult result2 = (TestResult)o2;
			if (new Long(result1.getTime()).compareTo(result2.getTime())==0){
				return result1.getId().compareTo(result2.getId());
			}

			return new Long(result1.getTime()).compareTo(result2.getTime());
		}
	}
    private List getResult() {


			Collections.sort(results, new ComoareResult());


        return results;
    }

    private int gettime(Date date) {
		return (int) (date.getTime() - pool_starttime);
	}

}