package com.dmycqq.test.future;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

public class ApiFutureTest  {
	public static String init;
	public static void main(String[] args) throws Exception {
		ExecutorService executor = Executors.newCachedThreadPool();
		Future<String> future = executor.submit(new Callable<String>() {
			public String call() throws Exception {
				Thread.sleep(5000);
				return "初始化完毕！";
			}
		});
		System.out.println("初始化其他系统");
		System.out.println(future.get());
		executor.shutdown();
	}
}
