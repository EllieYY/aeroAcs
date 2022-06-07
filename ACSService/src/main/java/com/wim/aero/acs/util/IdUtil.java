package com.wim.aero.acs.util;

import com.google.common.collect.Lists;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicLong;
import java.util.stream.Collectors;

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
