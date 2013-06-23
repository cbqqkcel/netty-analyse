package com.dmycqq.test;

import io.netty.buffer.ByteBuf;
import io.netty.buffer.CompositeByteBuf;
import io.netty.buffer.Unpooled;

import java.util.Iterator;

public class ByteBufTypeTest {
	 public static void main(String[] args){
         byte[] byteArrayA = {1, 2};
         byte[] byteArrayB = {3, 4};
        
         ByteBuf heapBuffer = Unpooled.buffer();
         System.out.println("/***********Heap ByteBuf***************/");
         System.out.println("Default Byte Order: " + heapBuffer.order());
         System.out.println("Default Heap Buffer Capacity: " + heapBuffer.capacity());
         System.out.println();
         System.out.println();
        
         ByteBuf wrappedBufferA = Unpooled.wrappedBuffer(byteArrayA);
         System.out.println("/***********Wrapped ByteBuf***************/");
         for(int i = 0; i < wrappedBufferA.writerIndex(); i++){
               System.out.println(wrappedBufferA.getByte(i));
         }
         System.out.println();
         System.out.println();
        
         ByteBuf wrappedBufferB = Unpooled.wrappedBuffer(byteArrayB);
         ByteBuf compositeByteBuf = Unpooled.compositeBuffer().addComponent(wrappedBufferA).addComponent(wrappedBufferB);
         Iterator<ByteBuf> compositeIterator = ((CompositeByteBuf)compositeByteBuf).iterator();
         System.out.println("/***********Composite ByteBuf***************/");
         while(compositeIterator.hasNext()){
               ByteBuf tempBuf = compositeIterator.next();
               for(int i = 0; i < 2;i++){
                    System.out.println(tempBuf.getByte(i));
               }
         }
         System.out.println();
         System.out.println();

         System.out.println("/***********Direct ByteBuf***************/");
         ByteBuf directBuffer = Unpooled.directBuffer();
         System.out.println("Has NIO Buffer? " + directBuffer);
         System.out.println();
         System.out.println();
         System.out.println("/*****************End*********************/");
    }
}
