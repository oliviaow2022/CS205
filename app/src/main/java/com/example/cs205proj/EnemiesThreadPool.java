package com.example.cs205proj;

import java.util.Set;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

public class EnemiesThreadPool extends ThreadPoolExecutor {

    private final ConcurrentHashMap<Enemy, Boolean> activeTasks = new ConcurrentHashMap<>();

    public EnemiesThreadPool(int corePoolSize, int maximumPoolSize, long keepAliveTime, TimeUnit unit, BlockingQueue<Runnable> workQueue) {
        super(corePoolSize, maximumPoolSize, keepAliveTime, unit, workQueue);
    }

    @Override
    protected void beforeExecute(Thread t, Runnable r) {
        if (r instanceof Enemy){
            Enemy e = (Enemy)r;
            activeTasks.put(e, Boolean.TRUE);
            super.beforeExecute(t, e);
        }
    }

    @Override
    protected void afterExecute(Runnable r, Throwable t) {
        if (r instanceof Enemy){
            Enemy e = (Enemy)r;
            super.afterExecute(e, t);
            activeTasks.remove(e);
        }


    }

    public Set<Enemy> getActiveTasks() {
        // the returned set will not throw a ConcurrentModificationException.
        return activeTasks.keySet();
    }

    public boolean hasAvailableSpot() {
        // Get the task queue
        int queueSize = this.getQueue().size();
        // Get the maximum queue size
        int maxQueueSize = this.getQueue().remainingCapacity();
        // Check if there's space available in the queue
        return queueSize < maxQueueSize;
    }

    public void executeEnemy(Enemy enemy) {
        if (hasAvailableSpot()) {
            execute(enemy);
        }
    }

}
