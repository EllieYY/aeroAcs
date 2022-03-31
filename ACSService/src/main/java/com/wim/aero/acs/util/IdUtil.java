package com.wim.aero.acs.util;

import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;

public final class IdUtil {

    private static final AtomicLong IDX = new AtomicLong();

    private IdUtil() {
        //no instance
    }

    public static String nextId() {
//        return IDX.incrementAndGet();
        return UUID.randomUUID().toString();
    }

}
