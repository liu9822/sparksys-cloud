package com.sparksys.commons.redis.config;

import org.redisson.api.RLock;
import org.redisson.api.RMapCache;
import org.redisson.api.RedissonClient;

import java.util.concurrent.TimeUnit;

/*
 * description：
 *
 * @author zhouxinlei
 * @date  2020/6/10 0010
 */
public class RedissLockUtil {

    private static RedissonClient redissonClient;

    public void setRedissonClient(RedissonClient locker) {
        redissonClient = locker;
    }

    /**
     * 加锁
     *
     * @param lockKey 加锁的key值
     * @return RLock
     */
    public static RLock lock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock();
        return lock;
    }

    /**
     * 释放锁
     *
     * @param lockKey 加锁的key值
     */
    public static void unlock(String lockKey) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.unlock();
    }

    /**
     * 释放锁
     *
     * @param lock 锁对象
     */
    public static void unlock(RLock lock) {
        lock.unlock();
    }

    /**
     * 带超时的锁
     *
     * @param lockKey 加锁的key值
     * @param timeout 超时时间   单位：秒
     */
    public static RLock lock(String lockKey, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, TimeUnit.SECONDS);
        return lock;
    }

    /**
     * 带超时的锁
     *
     * @param lockKey 加锁的key值
     * @param unit    时间单位
     * @param timeout 超时时间
     */
    public static RLock lock(String lockKey, TimeUnit unit, int timeout) {
        RLock lock = redissonClient.getLock(lockKey);
        lock.lock(timeout, unit);
        return lock;
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey   加锁的key值
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public static boolean tryLock(String lockKey, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, TimeUnit.SECONDS);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 尝试获取锁
     *
     * @param lockKey   加锁的key值
     * @param unit      时间单位
     * @param waitTime  最多等待时间
     * @param leaseTime 上锁后自动释放锁时间
     * @return boolean
     */
    public static boolean tryLock(String lockKey, TimeUnit unit, int waitTime, int leaseTime) {
        RLock lock = redissonClient.getLock(lockKey);
        try {
            return lock.tryLock(waitTime, leaseTime, unit);
        } catch (InterruptedException e) {
            return false;
        }
    }

    /**
     * 初始红包数量
     *
     * @param key
     * @param count
     */
    public void initCount(String key, int count) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        mapCache.putIfAbsent(key, count, 3, TimeUnit.DAYS);
    }

    /**
     * 递增
     *
     * @param key
     * @param delta 要增加几(大于0)
     * @return int
     */
    public int incr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递增因子必须大于0");
        }
        //加1并获取计算后的值
        return mapCache.addAndGet(key, 1);
    }

    /**
     * 递减
     *
     * @param key   键
     * @param delta 要减少几(小于0)
     * @return int
     */
    public int decr(String key, int delta) {
        RMapCache<String, Integer> mapCache = redissonClient.getMapCache("skill");
        if (delta < 0) {
            throw new RuntimeException("递减因子必须大于0");
        }
        //加1并获取计算后的值
        return mapCache.addAndGet(key, -delta);
    }
}
