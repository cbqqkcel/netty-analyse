package com.dmycqq.test.java.concurrent;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Semaphore;

/**
 * 信号量
 * @author DMY
 *
 */
public class SemaphoreTest {
	public static void main(String[] args) throws Exception  {
		// 线程池
		ExecutorService exec = Executors.newCachedThreadPool();
		// 只能个线程同时访问
		final Semaphore semp = new Semaphore(1);
		// 模拟个客户端访问
		for (int index = 1; index < 21; index++) {
			final int NO = index;
			Runnable run = new Runnable() {
				public void run() {
					try {
						// 获取许可
						semp.acquire();
						System.out.println("Accessing: " + NO);
						Thread.sleep((long) (Math.random() * 1000));
						// 访问完后，释放
						System.out.println("Release: " + NO);
						semp.release();
					} catch (InterruptedException e) {
					}
				}
			};
			exec.execute(run);
		}
		// 退出线程池
		exec.shutdown();
	}
}
