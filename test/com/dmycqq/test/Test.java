package com.dmycqq.test;

import io.netty.channel.Channel;
import io.netty.channel.socket.nio.NioSocketChannel;
import io.netty.util.internal.PlatformDependent;

import java.util.Random;
import java.util.concurrent.ConcurrentMap;

public class Test implements Runnable {
	static final ConcurrentMap<Integer, Channel> allChannels = PlatformDependent.newConcurrentHashMap();

    private static final Random random = new Random();

    /**
     * Generates a negative unique integer ID.  This method generates only
     * negative integers to avoid conflicts with user-specified IDs where only
     * non-negative integers are allowed.
     */
    private static Integer allocateId(Channel channel) {
        int idVal = random.nextInt();
        if (idVal > 0) {
            idVal = -idVal;
        } else if (idVal == 0) {
            idVal = -1;
        }

        Integer id;
        for (;;) {
            id = Integer.valueOf(idVal);
            // Loop until a unique ID is acquired.
            // It should be found in one loop practically.
            if (allChannels.putIfAbsent(id, channel) == null) {
                // Successfully acquired.
                return id;
            } else {
                // Taken by other channel at almost the same moment.
                idVal --;
                if (idVal >= 0) {
                    idVal = -1;
                }
            }
        }
    }
    
	public static void main(String[] args) {
		System.out.println(Test.allocateId(new NioSocketChannel()));
	}

	@Override
	public void run() {
		System.out.println("启动");
	}
}
