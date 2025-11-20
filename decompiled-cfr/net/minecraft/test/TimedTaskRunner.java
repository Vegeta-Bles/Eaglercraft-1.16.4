/*
 * Decompiled with CFR 0.152.
 */
package net.minecraft.test;

import java.util.Iterator;
import java.util.List;
import net.minecraft.test.GameTest;
import net.minecraft.test.TimeMismatchException;
import net.minecraft.test.TimedTask;

public class TimedTaskRunner {
    private final GameTest test;
    private final List<TimedTask> tasks;
    private long tick;

    public void runSilently(long tick) {
        try {
            this.runTasks(tick);
        }
        catch (Exception exception) {
            // empty catch block
        }
    }

    public void runReported(long tick) {
        try {
            this.runTasks(tick);
        }
        catch (Exception exception) {
            this.test.fail(exception);
        }
    }

    private void runTasks(long tick) {
        Iterator<TimedTask> iterator = this.tasks.iterator();
        while (iterator.hasNext()) {
            TimedTask timedTask = iterator.next();
            timedTask.task.run();
            iterator.remove();
            long _snowman2 = tick - this.tick;
            long _snowman3 = this.tick;
            this.tick = tick;
            if (timedTask.duration == null || timedTask.duration == _snowman2) continue;
            this.test.fail(new TimeMismatchException("Succeeded in invalid tick: expected " + (_snowman3 + timedTask.duration) + ", but current tick is " + tick));
            break;
        }
    }
}

