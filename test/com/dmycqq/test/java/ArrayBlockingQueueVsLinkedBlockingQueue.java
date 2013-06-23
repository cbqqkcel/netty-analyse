package com.dmycqq.test.java;
import java.util.concurrent.ArrayBlockingQueue;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

public class ArrayBlockingQueueVsLinkedBlockingQueue {
	public static final int Q_SIZE = 1024000;
	public static final int THREAD_NUM = 4;

	class Product {
		String name;

		Product(String name) {
			this.name = name;
		}
	}

	public void test(final BlockingQueue<Product> q)
			throws InterruptedException {

		class Producer implements Runnable {
			@Override
			public void run() {
				for (int i = 0; i < Q_SIZE * 10; i++) {
					try {
						q.put(new Product("Lee"));
					} catch (InterruptedException e) {
						e.printStackTrace();
					}
				}
			}

		}
		;
		class Consumer implements Runnable {
			@Override
			public void run() {
				for (int i = 0; i < Q_SIZE * 10; i++) {
					try {
						q.take();
					} catch (InterruptedException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
				}
			}
		}
		;
		Thread[] arrProducerThread = new Thread[THREAD_NUM];
		for (int i = 0; i < THREAD_NUM; i++) {
			arrProducerThread[i] = new Thread(new Producer());
		}
		Thread[] arrConsumerThread = new Thread[THREAD_NUM];
		for (int i = 0; i < THREAD_NUM; i++) {
			arrConsumerThread[i] = new Thread(new Consumer());
		}
		long t1 = System.currentTimeMillis();
		for (int i = 0; i < THREAD_NUM; i++) {
			arrProducerThread[i].start();
			arrConsumerThread[i].start();
		}
		for (int i = 0; i < THREAD_NUM; i++) {
			arrProducerThread[i].join();
			arrConsumerThread[i].join();
		}
		long t2 = System.currentTimeMillis();
		System.out.println(q.getClass().getSimpleName() + " cost : "
				+ (t2 - t1));
	}

	public static void main(String[] args) throws InterruptedException {
		final BlockingQueue<Product> q1 = new LinkedBlockingQueue<Product>(
				Q_SIZE);
		final BlockingQueue<Product> q2 = new ArrayBlockingQueue<Product>(
				Q_SIZE);
		new ArrayBlockingQueueVsLinkedBlockingQueue().test(q1);
		new ArrayBlockingQueueVsLinkedBlockingQueue().test(q2);
	}
}
