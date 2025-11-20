/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.util.profiler;

import java.util.function.Supplier;
import net.minecraft.util.profiler.EmptyProfileResult;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.ReadableProfiler;

public class DummyProfiler
implements ReadableProfiler {
    public static final DummyProfiler INSTANCE = new DummyProfiler();

    private DummyProfiler() {
    }

    @Override
    public void startTick() {
    }

    @Override
    public void endTick() {
    }

    @Override
    public void push(String location) {
    }

    @Override
    public void push(Supplier<String> locationGetter) {
    }

    @Override
    public void pop() {
    }

    @Override
    public void swap(String location) {
    }

    @Override
    public void swap(Supplier<String> locationGetter) {
    }

    @Override
    public void visit(String marker) {
    }

    @Override
    public void visit(Supplier<String> markerGetter) {
    }

    @Override
    public ProfileResult getResult() {
        return EmptyProfileResult.INSTANCE;
    }
}

