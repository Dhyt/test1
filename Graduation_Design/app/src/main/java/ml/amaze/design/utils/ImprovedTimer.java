package ml.amaze.design.utils;

import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.ScheduledFuture;
import java.util.concurrent.ScheduledThreadPoolExecutor;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;

/**
 *
 * @author hxj
 * @date 2017/12/23 0023
 */

public class ImprovedTimer {
    /**
     * 线程池不允许使用Executors去创建，而是通过ThreadPoolExecutor的方式，这样的处理方式让写的同学更加明确线程池的运行规则，规避资源耗尽的风险。 说明：Executors各个方法的弊端：
     *  1）newFixedThreadPool和newSingleThreadExecutor:
     *   主要问题是堆积的请求处理队列可能会耗费非常大的内存，甚至OOM。
     * 2）newCachedThreadPool和newScheduledThreadPool:
     *   主要问题是线程数最大数是Integer.MAX_VALUE，可能会创建数量非常多的线程，甚至OOM。
     *
     *  线程池能按时间计划来执行任务，允许用户设定计划执行任务的时间，int类型的参数是设定
     *  线程池中线程的最小数目。当任务较多时，线程池可能会自动创建更多的工作线程来执行任务
     */
    private final ScheduledExecutorService executorService = new ScheduledThreadPoolExecutor(1, new ImprovedTimer.DaemonThreadFactory());
    private ScheduledFuture<?> improvedTimerFuture = null;

    public ImprovedTimer() {
    }

    /**
     * 周期性重复执行定时任务
     * @param command 执行 Runnable
     * @param initialDelay 单位 MILLISECONDS
     * @param period 单位 MILLISECONDS
     */
    public void schedule(Runnable command, long initialDelay, long period){
        // initialDelay 毫秒后开始执行任务，以后每隔 period 毫秒执行一次

        // schedule方法被用来延迟指定时间来执行某个指定任务。
        // 如果你需要周期性重复执行定时任务可以使用scheduleAtFixedRate或者scheduleWithFixedDelay方法，它们不同的是前者以固定频率执行，后者以相对固定频率执行。
        // 不管任务执行耗时是否大于间隔时间，scheduleAtFixedRate和scheduleWithFixedDelay都不会导致同一个任务并发地被执行。
        // 唯一不同的是scheduleWithFixedDelay是当前一个任务结束的时刻，开始结算间隔时间，如0秒开始执行第一次任务，任务耗时5秒，任务间隔时间3秒，那么第二次任务执行的时间是在第8秒开始。

        improvedTimerFuture = executorService.scheduleAtFixedRate(command, initialDelay, period, TimeUnit.MILLISECONDS);
    }

    /**
     * 周期性重复执行定时任务
     * @param command 执行 Runnable
     * @param initialDelay 单位 MILLISECONDS
     */
    public void schedule(Runnable command, long initialDelay){
        // initialDelay 毫秒后开始执行任务

        improvedTimerFuture = executorService.schedule(command, initialDelay, TimeUnit.MILLISECONDS);
    }


    private void cancel() {
        if (improvedTimerFuture != null) {
            improvedTimerFuture.cancel(true);
            improvedTimerFuture = null;
        }
    }

    public void shutdown() {
        cancel();
        executorService.shutdown();
    }


    /**
     * 守护线程工厂类，用于生产后台运行线程
     */
    private static final class DaemonThreadFactory implements ThreadFactory {
        private AtomicInteger atoInteger = new AtomicInteger(0);

        @Override
        public Thread newThread(Runnable runnable) {
            Thread thread = new Thread(runnable);
            thread.setName("schedule-pool-Thread-" + atoInteger.getAndIncrement());
            thread.setDaemon(true);
            return thread;
        }
    }


}
