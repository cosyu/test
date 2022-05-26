package com.example.lock;

import org.checkerframework.checker.units.qual.A;

import java.util.ArrayList;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.StampedLock;
import java.util.concurrent.atomic.AtomicInteger;

public class StampedLockTest {

    private final StampedLock stampedLock = new StampedLock();

    private double x;
    private double y;


    public void move(double deltaX, double deltaY){

        long stamp = stampedLock.writeLock();//get write lock
        try{
            x += deltaX;
            y += deltaY;
        }finally {
            stampedLock.unlock(stamp);//release write lock
        }

    }

    public double distanceFromOrigin(){

        //get optimistic lock
        long stamp = stampedLock.tryOptimisticRead();
        //x,y may have been changed by other threads as it use optimistic lock
        double currentX = x;
        double currentY = y;
        //通过validate()去验证版本号，如果在读取过程中没有写入，版本号不变，验证成功，我们就可以放心地继续后续操作。
        // 如果在读取过程中有写入，版本号会发生变化，验证将失败。在失败的时候，我们再通过获取悲观读锁再次读取
        if(!stampedLock.validate(stamp)){
            stamp = stampedLock.readLock();// 获取一个悲观读锁
            try{
                currentX = x;
                currentY = y;
            }finally {
                stampedLock.unlockRead(stamp);
            }
        }

        return Math.sqrt(currentX*currentX+currentY*currentY);
    }
}
