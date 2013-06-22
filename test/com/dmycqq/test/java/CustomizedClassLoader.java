package com.dmycqq.test.java;

import java.io.ByteArrayOutputStream;
import java.io.FileInputStream;
import java.io.InputStream;

public class CustomizedClassLoader extends ClassLoader {
	@Override
	protected Class<?> findClass(String name) throws ClassNotFoundException {
		byte[] classData = getData();
		return defineClass("Test", classData, 0, classData.length);
	}

	private byte[] getData() {
		try {
			InputStream is = new FileInputStream("C:\\Test.class");
			ByteArrayOutputStream stream = new ByteArrayOutputStream();
			byte[] buffer = new byte[2048];
			int num = 0;
			while ((num = is.read(buffer)) != -1) {
				stream.write(buffer, 0, num);
			}
			is.close();
			return stream.toByteArray();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;
	}
}
