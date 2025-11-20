/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.ImmutableList
 */
package com.mojang.blaze3d.systems;

import com.google.common.collect.ImmutableList;
import com.mojang.blaze3d.systems.RenderCall;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class RenderCallStorage {
    private final List<ConcurrentLinkedQueue<RenderCall>> recordingQueues = ImmutableList.of(new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue());
    private volatile int field_20454 = this.field_20455 = this.field_20456 + 1;
    private volatile int field_20455;
    private volatile int field_20456;
}

