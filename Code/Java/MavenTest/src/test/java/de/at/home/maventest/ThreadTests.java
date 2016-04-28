package de.at.home.maventest;

import org.junit.Test;

import java.io.IOException;

/**
 * Created by juuri on 27.04.16.
 */
public class ThreadTests
{
    @Test
    public void threadTest() throws IOException
    {
        SomeObject someObject = new SomeObject();
        (new Thread(new WorkerThread(true,  someObject))).start();
        (new Thread(new WorkerThread(false, someObject))).start();
        //(new Thread(new WorkerThread(false, new SomeObject()))).start();

        System.out.println("Main-Thread finished");
        System.in.read();
    }

    private class WorkerThread implements Runnable
    {
        private boolean do1 = true;
        private SomeObject someObject;

        public WorkerThread(boolean do1, SomeObject someObject)
        {
            this.do1 = do1;
            this.someObject = someObject;
        }

        @Override
        public void run()
        {
            if (this.do1)
            {
                synchronized (this.someObject)
                {
                    this.someObject.do1();
                }
            }
            else
            {
                synchronized (this.someObject)
                {
                    this.someObject.do2();
                }
            }
        }
    }

    private class SomeObject
    {
        public void do1()
        {
            System.out.println(Thread.currentThread().getName() + ", do1()");
            try{Thread.sleep(30000);}catch(Exception e){}
        }

        public void do2()
        {
            System.out.println(Thread.currentThread().getName() + ", do2()");
            try{Thread.sleep(30000);}catch(Exception e){}
        }
    }
}
