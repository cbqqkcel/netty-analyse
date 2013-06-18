package com.dmycqq.test.java;

import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

public class ConcurrentMapTest {

	public static void main(String[] args) {
		ConcurrentMap<String, Boolean> map = new ConcurrentHashMap<String, Boolean>();
		/*
		 * if (!map.containsKey(key)) 
		 * return map.put(key, value); 
		 * else 
		 * return map.get(key);
		 */
		map.putIfAbsent("a", true);
		map.putIfAbsent("a", false);
		System.out.println(map.get("a"));
	}

}
