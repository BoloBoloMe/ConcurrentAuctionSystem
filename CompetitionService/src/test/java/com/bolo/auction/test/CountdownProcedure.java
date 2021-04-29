package com.bolo.auction.test;

/**
 * 倒计时程序
 */
public class CountdownProcedure {
    public static void main(String[] args) {
        long count = 30; // 倒计时30秒
        long pause = 1000; // 间隔1秒

        long lastTime = 0;
        do {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= pause) {
                System.out.println(String.format("倒计时：%d 秒", count--));
                lastTime = currentTime;
            }
        } while (-1 < count);
        System.out.println("倒计时结束");
    }
}
