package com.example.android.persistence.util;

public class ThreadUtil {
    public static String getThreadInfo() {
        Thread thread = Thread.currentThread();
        return "tid=" + thread.getId() + ", tname:"+thread.getName() +
                ", group name:"+thread.getThreadGroup().getName();
    }
}
