package com.dmycqq.test.concurrent;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.TimeUnit;

/**
 * 任务调度
 * @author DMY
 *
 */
public class ScheduledExecutorServiceTest {
	private final TimeUnit SECONDS = TimeUnit.SECONDS;//时间已秒为单位
	private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(1);
	private final Runnable run = new Runnable() {
		public void run() {
			System.out.println("test");
		}
	};

	public static void main(String[] args) {
		ScheduledExecutorServiceTest test = new ScheduledExecutorServiceTest();
		test.test1();
//		test.scheduler.shutdown();
	}

	
	public void test1() {
		//5秒之后执行deeper,然后就是每隔2秒执行一次
		final ScheduledFuture<?> scheduledFuture = scheduler.scheduleAtFixedRate(run, 5, 2, SECONDS);
	}
	
	public void test2() {
		//5秒之后执行
		ScheduledFuture<?> schedule = scheduler.schedule(run, 5, SECONDS);
	}

}
