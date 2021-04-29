package com.bolo.auction.test.bunchmark;

import lombok.SneakyThrows;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.net.URLConnection;
import java.util.Scanner;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.atomic.AtomicInteger;

/**
 * 性能测试程序
 * 建立指定数量的连接，创建指定数量的线程，向目标服务器发起应价请求
 *
 * @author : LuoJingYan
 * Date    : 2021/4/29
 * Time    : 15:44
 */
public class BunchmarkRunner {

    public static void main(String[] args) {
        int connectNum = 0, threadNum = 0;
        String target = null;
        if (null != args && args.length == 3) {
            connectNum = Integer.parseInt(args[0]);
            threadNum = Integer.parseInt(args[1]);
            target = args[2];
        }
        Scanner scanner = new Scanner(System.in);
        while (true) {
            if (connectNum <= 0) {
                System.out.println("请输入有效的连接数（大于0）：");
                connectNum = scanner.nextInt();
            }
            if (threadNum <= 0) {
                System.out.println("请输入有效的线程数（大于0）：");
                threadNum = scanner.nextInt();
            }
            if (null == target || "".equals(target)) {
                System.out.println("请输入有效的目标服务器地址：");
                target = scanner.nextLine();
            }
            if (connectNum * threadNum > 0 && null != target && !"".equals(target)) {
                break;
            }
        }

        System.out.println("创建连接数：" + connectNum);
        System.out.println("创建线程数：" + threadNum);
        System.out.println("请求地址：" + target);
        System.out.println("输入Y开始执行测试（输入N退出）：");
        while (true) {
            String falg = scanner.nextLine();
            if (falg.toLowerCase().startsWith("y")) {
                break;
            }
            if (falg.toLowerCase().startsWith("n")) {
                return;
            }
        }
        System.out.println("开始执行测试...");
        bunchark(connectNum, threadNum, target);
        System.out.println("执行完毕！");
        long succeed = queue.stream().filter(q -> q == 200).count();
        System.out.println("成功率：" + (succeed / queue.size()));
    }

    @SneakyThrows
    public static void bunchark(int connectNum, int threadNum, String target) {
        ExecutorService threadPool = Executors.newFixedThreadPool(threadNum);
        CountDownLatch latch = new CountDownLatch(threadNum + 1);
        CountDownLatch finishCouter = new CountDownLatch(threadNum);
        for (int t = 1; t <= threadNum; t++) {
            threadPool.submit(new TaskRunner(target, latch, finishCouter));
        }
        Thread.sleep(threadNum > 1000 ? 5000 : 2000);
        if (count.get() < threadNum) {
            System.err.println("任务分配失败！接受任务的线程未达到目标线程数！");
            threadPool.shutdownNow();
            return;
        }
        System.out.println("所有线程均已准备就绪,开始！！");
        long startTime, endTime;
        startTime = System.currentTimeMillis();
        latch.countDown();
        finishCouter.await();
        endTime = System.currentTimeMillis();
        System.out.println("测试结束，耗时：" + (endTime - startTime) + " mm");
        threadPool.shutdownNow();
    }


    private static final AtomicInteger count = new AtomicInteger(0);
    private static final ConcurrentLinkedQueue<Integer> queue = new ConcurrentLinkedQueue<>();

    /**
     * 执行任务的实现类
     */
    public static class TaskRunner implements Runnable {
        private String target;
        private CountDownLatch latch;
        private CountDownLatch finishCouter;
        private String runnerThreadName;

        public TaskRunner(String target, CountDownLatch latch, CountDownLatch finishCouter) {
            this.target = target;
            this.latch = latch;
            this.finishCouter = finishCouter;
        }

        @SneakyThrows
        @Override
        public void run() {
            if (null == runnerThreadName) {
                runnerThreadName = Thread.currentThread().getName();
            }
            System.out.println("线程" + runnerThreadName + "已就位");
            latch.countDown();
            count.incrementAndGet();
            HttpURLConnection connection = (HttpURLConnection) new URL(target).openConnection();
            connection.setRequestMethod("GET");
            //write header
            connection.setRequestProperty("Content-Type", "application/html");
            //set timeout
            connection.setConnectTimeout(1000 * 5);
            connection.setReadTimeout(1000 * 10);
            latch.await();
            // send request
            request(connection);
            finishCouter.countDown();
        }


        @SneakyThrows
        private void request(HttpURLConnection connection) {
            connection.connect();
            int responseCode = connection.getResponseCode();
            queue.add(responseCode);
        }
    }

}
