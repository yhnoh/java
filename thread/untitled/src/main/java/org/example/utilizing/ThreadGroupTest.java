package org.example.utilizing;

import static java.lang.Thread.sleep;

/**
 * 스레드 그룹 특징
 * 1. 스레드 그룹이 interrupt 되면 해당 그룹에 포함된 모든 스레드가 interrupt 된다.
 * 2. 상위 스레드 그룹이 interrupt 되면 상위 그룹에 포함된 하위 그룹도 interrupt 된다.
 */
public class ThreadGroupTest {

    public static void main(String[] args) throws InterruptedException {
        ThreadGroupTest threadGroupTest = new ThreadGroupTest();
        threadGroupTest.apiTest();
    }

    public void threadGroup(){
        ThreadGroup mainThreadGroup = Thread.currentThread().getThreadGroup();
        ThreadGroup customThreadGroup = new ThreadGroup( "Custom Thread Group");

        Thread defaultGroupThread = new Thread(new MyRunnable(), "DefaultGroupThread");
        Thread mainGroupThread = new Thread(mainThreadGroup, new MyRunnable(), "MainGroupThread");
        Thread customGroupThread = new Thread(customThreadGroup, new MyRunnable(), "CustomGroupThread");

        defaultGroupThread.start();
        mainGroupThread.start();
        customGroupThread.start();

    }

    static class MyRunnable implements Runnable {

        @Override
        public void run() {
            Thread thread = Thread.currentThread();
            System.out.println(Thread.currentThread().getName() + "는 " + Thread.currentThread().getThreadGroup().getName() + "에 속한다.");
        }
    }

    public void nestedThreadGroup() throws InterruptedException {

        ThreadGroup parentThreadGroup = new ThreadGroup("ParentThreadGroup");
        ThreadGroup childThreadGroup = new ThreadGroup(parentThreadGroup, "ChildThreadGroup");

        Thread parentGroupThread = new Thread(parentThreadGroup, new MyRunnable(), "ParentGroupThread");
        Thread childGroupThread = new Thread(childThreadGroup, new MyRunnable(), "ChildGroupThread");

        parentGroupThread.start();
        childGroupThread.start();

        Thread.sleep(1000);

        parentThreadGroup.list();
    }

    public void priorityThreadGroup() throws InterruptedException {

        ThreadGroup parentThreadGroup = new ThreadGroup("ParentThreadGroup");
        ThreadGroup childThreadGroup = new ThreadGroup(parentThreadGroup, "ChildThreadGroup");

        Thread parentGroupThread = new Thread(parentThreadGroup, () -> {
            System.out.println("상위 그룹 스레드에서 하위 그룹의 우선 순위 변경 전: " + parentThreadGroup.getMaxPriority());
            parentThreadGroup.setMaxPriority(3);
            System.out.println("상위 그룹 스레드에서 하위 그룹의 우선 순위 변경 후: " + parentThreadGroup.getMaxPriority());
        }, "ParentGroupThread") ;

        Thread childGroupThread = new Thread(childThreadGroup, () -> {
            System.out.println("하위 그룹 스레드에서 하위 그룹의 우선 순위 변경 전: " + childThreadGroup.getMaxPriority());
            childThreadGroup.setMaxPriority(4);
            System.out.println("하위 그룹 스레드에서 하위 그룹의 우선 순위 변경 후: " + childThreadGroup.getMaxPriority());
        }, "ChildGroupThread") ;

        parentGroupThread.start();
        childGroupThread.start();

        parentGroupThread.join();
        childGroupThread.join();
        //스레드 그룹에 우선 순위를 셋팅하기 전에 스레드를 생성하면 적용되지 않는다.
        System.out.println(parentGroupThread.getName() + " : " + parentGroupThread.getPriority());
        System.out.println(childGroupThread.getName() + " : " + childGroupThread.getPriority());

        Thread userThread1 = new Thread(parentThreadGroup, () -> {}, "userThread1");
        Thread userThread2 = new Thread(childThreadGroup, () -> {}, "userThread2");

        userThread1.start();
        userThread2.start();

        userThread1.join();
        userThread2.join();
        //스레드 그룹에 우선 순위를 셋팅하고 난 뒤에 스레드를 생성하면 적용된다.
        //뿐만 아니라 최상위 스레드 그룹에 설정한 우선순위가 하위 스레드 그룹에 설정한 우선순위 보다 높다.
        System.out.println(userThread1.getName() + " : " + userThread1.getPriority());
        System.out.println(userThread2.getName() + " : " + userThread2.getPriority());

    }

    public void interruptThreadGroup() throws InterruptedException {
        ThreadGroup parentThreadGroup = new ThreadGroup("ParentThreadGroup");
        ThreadGroup childThreadGroup = new ThreadGroup(parentThreadGroup, "ChildThreadGroup");

        Thread parentGroupThread = new Thread(parentThreadGroup, () -> {
            while (true) {
                System.out.println("상위 그룹 스레드에서 실행중...");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("상위 그룹 스레드에서 인터럽트 발생");
                    break;
                }
            }

            System.out.println("상위 그룹 스레드에서 작업 종료");
        }, "parentGroupThread");

        Thread childGroupThread = new Thread(childThreadGroup, () -> {
            while (true) {
                System.out.println("하위 그룹 스레드에서 실행중...");
                try {
                    sleep(500);
                } catch (InterruptedException e) {
                    System.out.println("하위 그룹 스레드에서 인터럽트 발생");
                    break;
                }
            }
            System.out.println("하위 그룹 스레드에서 작업 종료");
        }, "childGroupThread");

        parentGroupThread.start();
        childGroupThread.start();

        Thread.sleep(1000);

//        childThreadGroup.interrupt();
        parentThreadGroup.interrupt();
    }


    public void apiTest(){
        ThreadGroup parentThreadGroup = new ThreadGroup("ParentThreadGroup");
        ThreadGroup childThreadGroup = new ThreadGroup(parentThreadGroup, "ChildThreadGroup");

        Thread[] parentThreads = new Thread[5];
        for (int i = 0; i < parentThreads.length; i++) {
            parentThreads[i] = new Thread(parentThreadGroup, () -> {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "ParentGroupThread" + i);
            parentThreads[i].start();
        }


        Thread[] childThreads = new Thread[5];
        for (int i = 0; i < childThreads.length; i++) {
            childThreads[i] = new Thread(childThreadGroup, () -> {
                try {
                    sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
            }, "ChildGroupThread" + i);
            childThreads[i].start();
        }


        System.out.println("상위그룹 활성 스레드 수 : " + parentThreadGroup.activeCount());
        System.out.println("상위그룹 활성 스레드 그룹 수 : " + parentThreadGroup.activeGroupCount());

        System.out.println("하위그룹 활성 스레드 수 : " + childThreadGroup.activeCount());
        System.out.println("하위그룹 활성 스레드 그룹 수 : " + childThreadGroup.activeGroupCount());

        System.out.println("상위 그룹은 하위 그룹의 상위 그룹인가? " + parentThreadGroup.parentOf(childThreadGroup));
    }

}
