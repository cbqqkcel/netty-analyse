package com.dmycqq.test;

public class Test implements Runnable {
	public static void main(String[] args) {
		Test t = new Test();
		new Thread(t).start();
		System.out.println("111");
	}

	@Override
	public void run() {
		System.out.println("启动");
	}
}
