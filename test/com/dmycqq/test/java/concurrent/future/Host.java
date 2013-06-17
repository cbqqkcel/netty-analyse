package com.dmycqq.test.future;

public class Host {  
    public Data request(final int count, final char c) {  
        System.out.println("    request(" + count + ", " + c + ") BEGIN");  
  
        // (1)建立FutureData的实例  
        final FutureData future = new FutureData();  
  
        // (2)为了建立RealData的实例，启用新的线程  
        new Thread() {                                        
            public void run() {                               
                RealData realdata = new RealData(count, c);  
                future.setRealData(realdata);  
            }                                                 
        }.start();                                            
  
        System.out.println("    request(" + count + ", " + c + ") END");  
  
        // (3)取回FutureData实例，作为返回值  
        return future;  
    }  
} 