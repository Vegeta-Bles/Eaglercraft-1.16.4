/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.resource;

import java.util.concurrent.CompletableFuture;
import net.minecraft.util.Unit;

public interface ResourceReloadMonitor {
    public CompletableFuture<Unit> whenComplete();

    public float getProgress();

    public boolean isPrepareStageComplete();

    public boolean isApplyStageComplete();

    public void throwExceptions();
}

