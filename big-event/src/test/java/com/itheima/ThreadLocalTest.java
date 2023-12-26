package com.itheima;

import org.junit.jupiter.api.Test;

public class ThreadLocalTest {
    @Test
    public void testThreadLocalSetAndGet(){
        //1.提供一个ThreadLocal对象
        ThreadLocal tl=new ThreadLocal();

        //2.开启两个线程
        new Thread(()->{tl.set("消炎");
                        System.out.println(Thread.currentThread().getName()+" "+tl.get());
                        System.out.println(Thread.currentThread().getName()+" "+tl.get());
                        System.out.println(Thread.currentThread().getName()+" "+tl.get());
            },"蓝色").start();


        new Thread(()->{tl.set("姚程");
            System.out.println(Thread.currentThread().getName()+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+" "+tl.get());
            System.out.println(Thread.currentThread().getName()+" "+tl.get());
        },"绿色").start();

    }
}
