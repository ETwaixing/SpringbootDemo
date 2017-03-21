package com.waixing.utils.concurrent;

import com.google.common.util.concurrent.ThreadFactoryBuilder;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.concurrent.CancellationException;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.Future;
import java.util.concurrent.SynchronousQueue;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * 线程池工具类
 *
 * @author eric
 * @version v1.0.0
 * @since v1.0.0
 */
public class ThreadPoolUtil {
    private static final Logger logger = LogManager.getLogger(ThreadPoolUtil.class.getName());
    private static ThreadPoolExecutor executor = null;

    /**
     * 获取线程池(默认初始大小20个)
     *
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolExecutor() {
        return getThreadPoolExecutor(20);
    }

    /**
     * 获取线程池
     *
     * @param corePoolSize 线程池初始大小
     * @return
     */
    public static ThreadPoolExecutor getThreadPoolExecutor(int corePoolSize) {
        if (null == executor) {
            ThreadFactory FACTORY = new ThreadFactoryBuilder().setDaemon(false).build();

            executor = new ThreadPoolExecutor(corePoolSize, Integer.MAX_VALUE, 1, TimeUnit.MINUTES, new SynchronousQueue<>(), FACTORY) {

                protected void afterExecute(Runnable r, Throwable t) {
                    super.afterExecute(r, t);
                    printException(r, t, logger);
                }
            };

            executor.prestartAllCoreThreads();

            if (logger.isWarnEnabled()) {
                executor.submit(() -> {
                    while (true) {
                        Thread.sleep(5 * 60 * 1000);

                        int activeCount = executor.getActiveCount();
                        long taskCount = executor.getTaskCount();
                        long completedTaskCount = executor.getCompletedTaskCount();
                        logger.info("【当前线程池线程数量】taskCount:" + taskCount + ",activeCount:" + activeCount + ",completedTaskCount:" + completedTaskCount);
                    }
                });
            }
        }

        return executor;
    }

    /**
     * 打印线程池中的异常
     *
     * @param r
     * @param t
     * @param logger
     */
    private static void printException(Runnable r, Throwable t, Logger logger) {
        if (t == null && r instanceof Future<?>) {
            try {
                Future<?> future = (Future<?>) r;
                if (future.isDone())
                    future.get();
            } catch (CancellationException ce) {
                t = ce;
            } catch (ExecutionException ee) {
                t = ee.getCause();
            } catch (InterruptedException ie) {
                Thread.currentThread().interrupt(); // ignore/reset
            }
        }
        if (t != null)
            logger.error(t.getMessage(), t);
    }
}
