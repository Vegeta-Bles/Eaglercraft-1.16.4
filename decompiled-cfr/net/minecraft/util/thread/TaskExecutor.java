/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  it.unimi.dsi.fastutil.ints.Int2BooleanFunction
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util.thread;

import it.unimi.dsi.fastutil.ints.Int2BooleanFunction;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.concurrent.Executor;
import java.util.concurrent.RejectedExecutionException;
import java.util.concurrent.atomic.AtomicInteger;
import net.minecraft.SharedConstants;
import net.minecraft.util.thread.MessageListener;
import net.minecraft.util.thread.TaskQueue;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskExecutor<T>
implements MessageListener<T>,
AutoCloseable,
Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final AtomicInteger stateFlags = new AtomicInteger(0);
    public final TaskQueue<? super T, ? extends Runnable> queue;
    private final Executor executor;
    private final String name;

    public static TaskExecutor<Runnable> create(Executor executor, String name) {
        return new TaskExecutor<Runnable>(new TaskQueue.Simple(new ConcurrentLinkedQueue()), executor, name);
    }

    public TaskExecutor(TaskQueue<? super T, ? extends Runnable> queue, Executor executor, String name) {
        this.executor = executor;
        this.queue = queue;
        this.name = name;
    }

    private boolean unpause() {
        int n;
        do {
            if (((n = this.stateFlags.get()) & 3) == 0) continue;
            return false;
        } while (!this.stateFlags.compareAndSet(n, n | 2));
        return true;
    }

    private void pause() {
        int n;
        while (!this.stateFlags.compareAndSet(n = this.stateFlags.get(), n & 0xFFFFFFFD)) {
        }
    }

    private boolean hasMessages() {
        if ((this.stateFlags.get() & 1) != 0) {
            return false;
        }
        return !this.queue.isEmpty();
    }

    @Override
    public void close() {
        int n;
        while (!this.stateFlags.compareAndSet(n = this.stateFlags.get(), n | 1)) {
        }
    }

    private boolean isUnpaused() {
        return (this.stateFlags.get() & 2) != 0;
    }

    private boolean runNext() {
        String _snowman2;
        if (!this.isUnpaused()) {
            return false;
        }
        Runnable runnable = this.queue.poll();
        if (runnable == null) {
            return false;
        }
        if (SharedConstants.isDevelopment) {
            Thread thread = Thread.currentThread();
            _snowman2 = thread.getName();
            thread.setName(this.name);
        } else {
            thread = null;
            _snowman2 = null;
        }
        runnable.run();
        if (thread != null) {
            thread.setName(_snowman2);
        }
        return true;
    }

    @Override
    public void run() {
        try {
            this.runWhile(n -> n == 0);
        }
        finally {
            this.pause();
            this.execute();
        }
    }

    @Override
    public void send(T message) {
        this.queue.add(message);
        this.execute();
    }

    private void execute() {
        if (this.hasMessages() && this.unpause()) {
            try {
                this.executor.execute(this);
            }
            catch (RejectedExecutionException rejectedExecutionException) {
                try {
                    this.executor.execute(this);
                }
                catch (RejectedExecutionException rejectedExecutionException2) {
                    LOGGER.error("Cound not schedule mailbox", (Throwable)rejectedExecutionException2);
                }
            }
        }
    }

    private int runWhile(Int2BooleanFunction condition) {
        int n = 0;
        while (condition.get(n) && this.runNext()) {
            ++n;
        }
        return n;
    }

    public String toString() {
        return this.name + " " + this.stateFlags.get() + " " + this.queue.isEmpty();
    }

    @Override
    public String getName() {
        return this.name;
    }
}

