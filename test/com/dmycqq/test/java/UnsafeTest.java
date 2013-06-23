package com.dmycqq.test.java;

import io.netty.util.internal.SystemPropertyUtil;

import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.nio.ByteBuffer;

import sun.misc.Cleaner;
import sun.misc.Unsafe;

public class UnsafeTest {
	private static final Unsafe UNSAFE;
	static {
        Unsafe unsafe;
        try {
            Field unsafeField = Unsafe.class.getDeclaredField("theUnsafe");
            unsafeField.setAccessible(true);
            unsafe = (Unsafe) unsafeField.get(null);

            // Ensure the unsafe supports all necessary methods to work around the mistake in the latest OpenJDK.
            // https://github.com/netty/netty/issues/1061
            // http://www.mail-archive.com/jdk6-dev@openjdk.java.net/msg00698.html
            try {
                unsafe.getClass().getDeclaredMethod("copyMemory", new Class[] { Object.class, long.class, Object.class, long.class, long.class });
            } catch (NoSuchMethodError t) {
                throw t;
            }
        } catch (Throwable cause) {
            unsafe = null;
        }
        UNSAFE = unsafe;

        try {
            Class<?> bitsClass = Class.forName("java.nio.Bits", false, ClassLoader.getSystemClassLoader());
            Method unalignedMethod = bitsClass.getDeclaredMethod("unaligned");
            unalignedMethod.setAccessible(true);
        } catch (Throwable t) {
            // We at least know x86 and x64 support unaligned access.
            String arch = SystemPropertyUtil.get("os.arch", "");
            //noinspection DynamicRegexReplaceableByCompiledPattern
        }
    }
	
	public int name = 1;
	/**
	 * @param args
	 * @throws SecurityException 
	 * @throws NoSuchFieldException 
	 * @throws ClassNotFoundException 
	 */
	public static void main(String[] args) throws Exception {
		ByteBuffer buffer = ByteBuffer.allocateDirect(1);
		UnsafeTest t = new UnsafeTest();
		System.out.println(t.getClass().getClassLoader());
		System.out.println(UnsafeTest.class.getName());
		for (Field f : t.getClass().getFields()) {
			long objectFieldOffset = UNSAFE.objectFieldOffset(f);
			System.out.println(UNSAFE.getInt(t.name, objectFieldOffset));
		}
		Cleaner cleaner = (Cleaner) UNSAFE.getObject(buffer, 56l);
		cleaner.clean();
	}
}
