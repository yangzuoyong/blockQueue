package com.gp12713.blockqueue;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;
@Slf4j
public class BlockQueue {
    private List list = new ArrayList();
    private ReentrantLock lock = new ReentrantLock();
    private Condition putCondition = lock.newCondition();
    private Condition takeCondition = lock.newCondition();
    private static final int QUEQUE_SIZE = 10;

    public void put(Object object){
        try {
            lock.lock();
            while (list.size()>=QUEQUE_SIZE){
                log.info(String.format("线程【%s】等待加入队列，当前队列大小：%d",Thread.currentThread().getName(),list.size()));
                putCondition.await();
            }
            list.add(object);
            log.info(String.format("线程【%s】成功放入对象【%s】,当前队列大小：%d",Thread.currentThread().getName(),object,list.size()));
            takeCondition.signalAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
            lock.unlock();
        }

    }
    public Object take(){

        Object object = null;

        try {
            lock.lock();
            while (0==list.size()){
                log.info(String.format("线程【%s】等待其他线程放入对象，当前队列大小：%s",Thread.currentThread().getName(),list.size()));
                takeCondition.await();
            }
            object = list.remove(0);
            log.info(String.format("线程【%s】成功取到对象【%s】,当前队列大小：%d",Thread.currentThread().getName(),object,list.size()));
            putCondition.signalAll();
        } catch (Exception e) {
            log.error(e.getMessage());
        } finally {
        }
        return object;
    }
}
