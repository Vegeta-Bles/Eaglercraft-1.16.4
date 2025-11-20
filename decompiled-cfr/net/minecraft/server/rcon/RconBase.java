/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.rcon;

import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import net.minecraft.util.logging.UncaughtExceptionHandler;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class RconBase
implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final AtomicInteger THREAD_COUNTER = new AtomicInteger(0);
    protected volatile boolean running;
    protected final String description;
    @Nullable
    protected Thread thread;

    protected RconBase(String description) {
        this.description = description;
    }

    public synchronized boolean start() {
        if (this.running) {
            return true;
        }
        this.running = true;
        this.thread = new Thread(this, this.description + " #" + THREAD_COUNTER.incrementAndGet());
        this.thread.setUncaughtExceptionHandler(new UncaughtExceptionHandler(LOGGER));
        this.thread.start();
        LOGGER.info("Thread {} started", (Object)this.description);
        return true;
    }

    public synchronized void stop() {
        this.running = false;
        if (null == this.thread) {
            return;
        }
        int n = 0;
        while (this.thread.isAlive()) {
            try {
                this.thread.join(1000L);
                if (++n >= 5) {
                    LOGGER.warn("Waited {} seconds attempting force stop!", (Object)n);
                    continue;
                }
                if (!this.thread.isAlive()) continue;
                LOGGER.warn("Thread {} ({}) failed to exit after {} second(s)", (Object)this, (Object)this.thread.getState(), (Object)n, (Object)new Exception("Stack:"));
                this.thread.interrupt();
            }
            catch (InterruptedException interruptedException) {}
        }
        LOGGER.info("Thread {} stopped", (Object)this.description);
        this.thread = null;
    }

    public boolean isRunning() {
        return this.running;
    }
}

