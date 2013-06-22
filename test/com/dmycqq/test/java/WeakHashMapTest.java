package com.dmycqq.test.java;

import java.util.HashMap;
import java.util.Map;
import java.util.WeakHashMap;

/**
 * WeakHashMap，此种Map的特点是，当除了自身有对key的引用外，此key没有其他引用那么此map会自动丢弃此值，
 * 见实例：此例子中声明了两个Map对象，一个是HashMap，一个是WeakHashMap，同时向两个map中放入a、b两个对象，当HashMap
 * remove掉a 并且将a、b都指向null时，WeakHashMap中的a将自动被回收掉。出现这个状况的原因是，对于a对象而言，当HashMap
 * remove掉并且将a指向null后
 * ，除了WeakHashMap中还保存a外已经没有指向a的指针了，所以WeakHashMap会自动舍弃掉a，而对于b对象虽然指向了null
 * ，但HashMap中还有指向b的指针，所以 WeakHashMap将会保留
 * 
 * @author Computer
 * 
 */
public class WeakHashMapTest {
	public static void main(String[] args) throws Exception {
		String a = new String("a");
		String b = new String("b");
		Map<String, String> map = new HashMap<String, String>();
		map.put(a, "aaa");
		map.put(b, "bbb");
		map.remove(a);

		Map<String, String> weakmap = new WeakHashMap<String, String>();
		weakmap.put(a, "aaa");
		weakmap.put(b, "bbb");

		a = null;
		b = null;

		System.gc();
		
		for (String key : map.keySet()) {
			System.out.println("map:" + key + ":" + map.get(key));
		}

		for (String key : weakmap.keySet()) {
			System.out.println("weakmap:" + key + ":" + weakmap.get(key));
		}
		
		
		/*
		 * 测试Class对象被收回的场景
		 * 自定义一个ClassLoader从C盘中加载一个空的Test.class，目前写死的。  
		 * 然后会看到当classLoader为空时weak会被收回;
		 * 使用系统的ClassLoader目前还不知道什么时候会被回收。
		 */
		WeakHashMap<Object, Object> weak = new WeakHashMap<Object, Object>();
		CustomizedClassLoader classLoader = new CustomizedClassLoader();
		Class<?> findClass = classLoader.findClass(null);
		weak.put(findClass, "Test");
		
		Object newInstance = findClass.newInstance();
		ClassLoader classLoader2 = newInstance.getClass().getClassLoader();
		System.out.println(classLoader == classLoader2);

		classLoader = null;
		findClass = null;
		newInstance = null;
		classLoader2 = null;
		System.gc();

		for (String key : map.keySet()) {
			System.out.println("map:" + key + ":" + weak.get(key));
		}
	}

}
