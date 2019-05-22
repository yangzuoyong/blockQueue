package com.gp12713.blockqueue;

import org.junit.Test;

public class BlockQueueTest {
    private static BlockQueue blockQueue =new BlockQueue();
    @Test
    public  void test(){
        for (int i=0;i< 10;i++) {
            new PutClass(i).start();
        }

        for (int i = 0; i < 10; i++) {
             new TakeClass(i).start();
        }
    }

    private class PutClass extends Thread{
        public PutClass(int i) {
            setName(String.format("PutClass[%d]",i));
        }

        @Override
        public void run() {
            blockQueue.put(new Object());
        }
    }
    private class TakeClass extends Thread{
        public TakeClass(int i) {
            setName(String.format("TaskeClass[%d]",i));
        }

        @Override
        public void run() {
            blockQueue.take();
        }
    }

}
