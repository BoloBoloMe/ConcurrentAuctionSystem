package com.bolo.auction.test;

/**
 * 死循环测试
 */
public class EndlessLoopTest {
    public static void main(String[] args) {
        // 倒计时30秒
        long count = 30;
        // 间隔1秒
        long pause = 1000;
        System.out.println(String.format("倒计时 %d 秒", count));
        long lastTime = System.currentTimeMillis();
        while (true) {
            long currentTime = System.currentTimeMillis();
            if (currentTime - lastTime >= pause) {
                System.out.println(String.format("倒计时：%d 秒", --count));
                if (count == 0) {
                    break;
                }
                lastTime = currentTime;
            }
        }
        System.out.println("倒计时结束");
    }
}
