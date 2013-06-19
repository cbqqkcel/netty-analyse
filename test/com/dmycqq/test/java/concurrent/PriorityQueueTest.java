package com.dmycqq.test.java.concurrent;

import java.util.PriorityQueue;

/**
 * 非线程安全的优先级队列
 * 线程安全 @see PriorityBlockingQueue
 * @author DMY
 *
 */
public class PriorityQueueTest {
	public static void main(String[] args) {
		PriorityQueue<Integer> pq = new PriorityQueue<Integer>();
		pq.add(5);
		pq.add(2);
		pq.add(4);
		pq.add(3);
		pq.add(1);

		for (Integer i : pq) {
			System.out.print(i + " ");
		}
		
		System.out.println();
		
		for (int i = pq.size(); i != 0; i--) {
			System.out.print(pq.poll() + " ");
		}
	}
}
