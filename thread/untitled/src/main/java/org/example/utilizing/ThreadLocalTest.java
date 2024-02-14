package org.example.utilizing;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class ThreadLocalTest {

    public static void main(String[] args) throws InterruptedException {

        ThreadLocalTest threadLocalTest = new ThreadLocalTest();
        threadLocalTest.printInheritableThreadLocalValue();
    }

    public void printThreadLocalValue(){
        Thread thread = new Thread(new ThreadLocalValuePrinter());
        thread.start();
    }

    public static class ThreadLocalValuePrinter implements Runnable{

        private ThreadLocal<String> threadLocal = ThreadLocal.withInitial(() -> "Hello World");

        @Override
        public void run() {
            System.out.println("threadLocal default value = " + threadLocal.get());
            threadLocal.set("set value");
            System.out.println("threadLocal set value = " + threadLocal.get());
            threadLocal.remove();
            System.out.println("threadLocal remove value = " + threadLocal.get());
        }
    }

    /**
     * 각 스레드 별로 다른 값을 가지게 된다.
     */
    public void printThreadLocalLog() throws InterruptedException {
        Logger logger = new ThreadLocalLogger();
        Thread thread1 = new Thread(new LoggerWorker(logger));
        Thread thread2 = new Thread(new LoggerWorker(logger));
        Thread thread3 = new Thread(new LoggerWorker(logger));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    /**
     * 각 스레드 별로 다른 값을 가지지 않고 데이터를 공유하게 된다.
     */
    public void printLog() throws InterruptedException {
        Logger logger = new Logger();
        Thread thread1 = new Thread(new LoggerWorker(logger));
        Thread thread2 = new Thread(new LoggerWorker(logger));
        Thread thread3 = new Thread(new LoggerWorker(logger));

        thread1.start();
        thread2.start();
        thread3.start();

        thread1.join();
        thread2.join();
        thread3.join();
    }

    public void printThreadLocalValueWhenNotRemoveInThreadPool(){
        ThreadPoolThreadLocal threadPoolThreadLocal = new ThreadPoolThreadLocal();
        threadPoolThreadLocal.printThreadLocalValueWhenNotRemove();
    }

    public void printThreadLocalValueWhenRemoveInThreadPool(){
        ThreadPoolThreadLocal threadPoolThreadLocal = new ThreadPoolThreadLocal();
        threadPoolThreadLocal.printThreadLocalValueWhenRemove();
    }

    public void printInheritableThreadLocalValue() throws InterruptedException {
        InheritableThreadLocalPrinter inheritableThreadLocalPrinter = new InheritableThreadLocalPrinter();
        inheritableThreadLocalPrinter.printInheritableThreadLocalValue();
    }

    public static class Logger {
        private final List<String> LOGS = new ArrayList<>();

        public void addLog(String log){
            LOGS.add(log);
        }

        public void printLog(){

            System.out.println("[" + Thread.currentThread().getName() + "]" + String.join("->", LOGS));
        }

        public void clearLog(){
            LOGS.clear();
        }

        public class ServiceA {
            public void process(){
                addLog("ServiceA 로직 수행");
            }
        }

        public class ServiceB {
            public void process(){
                addLog("ServiceB 로직 수행");
            }
        }

        public class ServiceC {
            public void process(){
                addLog("ServiceC 로직 수행");
            }
        }
    }


    public static class ThreadLocalLogger extends Logger {
        private final ThreadLocal<List<String>> THREAD_LOCAL_LOG = ThreadLocal.withInitial(ArrayList::new);

        public void addLog(String log){
            THREAD_LOCAL_LOG.get().add(log);
        }

        public void printLog(){
            List<String> logs = THREAD_LOCAL_LOG.get();
            System.out.println("[" + Thread.currentThread().getName() + "]" + String.join("->", logs));
        }

        public void clearLog(){
            THREAD_LOCAL_LOG.remove();
        }
    }

    public static class LoggerWorker implements Runnable {
        private final Logger logger;
        public LoggerWorker(Logger logger) {
            this.logger = logger;
        }

        @Override
        public void run() {

            Logger.ServiceA serviceA = logger.new ServiceA();
            Logger.ServiceB serviceB = logger.new ServiceB();
            Logger.ServiceC serviceC = logger.new ServiceC();

            if(Thread.currentThread().getName().equals("Thread-1")){
                serviceA.process();
                serviceB.process();
                serviceC.process();
            } else if(Thread.currentThread().getName().equals("Thread-2")){
                serviceB.process();
                serviceA.process();
                serviceC.process();
            } else {
                serviceC.process();
                serviceA.process();
                serviceB.process();
            }

            logger.printLog();
            logger.clearLog();
        }
    }


    public static class ThreadPoolThreadLocal {
        private ThreadLocal<String> threadLocal = new ThreadLocal<>();

        /**
         * 스레드 로컬의 값을 제거하지 않으면, 스레드 로컬 값을 담은 스레드가 값을 재사용하게 된다.
         * 스레드 로컬의 값을 제거하지 않으면, 다른 클라이언트들이 동일한 값을 사용하게 되는 문제점이 발생할 수 있다.
         */
        public void printThreadLocalValueWhenNotRemove(){

            ExecutorService executor = Executors.newFixedThreadPool(2);

            executor.submit(() -> {
                threadLocal.set("작업 1의 값");
                System.out.println(Thread.currentThread().getName() + "의 ThreadLocal Value : " + threadLocal.get());
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 5; i++) {
                executor.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "의 ThreadLocal Value : " + threadLocal.get());;
                });
            }

            executor.shutdown();
        }

        public void printThreadLocalValueWhenRemove(){

            ExecutorService executor = Executors.newFixedThreadPool(2);

            executor.submit(() -> {
                threadLocal.set("작업 1의 값");
                System.out.println(Thread.currentThread().getName() + "의 ThreadLocal Value : " + threadLocal.get());
                threadLocal.remove();
            });

            try {
                Thread.sleep(500);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }

            for (int i = 0; i < 5; i++) {
                executor.submit(() -> {
                    System.out.println(Thread.currentThread().getName() + "의 ThreadLocal Value : " + threadLocal.get());;
                });
            }

            executor.shutdown();
        }
    }

    public static class InheritableThreadLocalPrinter{
        private InheritableThreadLocal<String> inheritableThreadLocal = new InheritableThreadLocal<>();

        public void printInheritableThreadLocalValue() throws InterruptedException {
            inheritableThreadLocal.set("부모 스레드의 값");

            Thread childThread = new Thread(() -> {
                System.out.println("자식 스레드에서 값 가져오기, inheritableThreadLocal Value: " + inheritableThreadLocal.get());

                inheritableThreadLocal.set("자식 스레드 새로운 값");
                System.out.println("자식 스레드에서 값 셋팅, inheritableThreadLocal Value: " + inheritableThreadLocal.get());
            });
            childThread.start();
            childThread.join();

            System.out.println("부모 스레드에서 값 가져오기, inheritableThreadLocal Value: " + inheritableThreadLocal.get());
        }


    }


}
