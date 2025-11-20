/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Streams
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server.dedicated;

import com.google.common.collect.Streams;
import java.io.File;
import java.lang.management.ManagementFactory;
import java.lang.management.ThreadInfo;
import java.lang.management.ThreadMXBean;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Timer;
import java.util.TimerTask;
import java.util.stream.Collectors;
import net.minecraft.Bootstrap;
import net.minecraft.server.dedicated.MinecraftDedicatedServer;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.world.GameRules;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class DedicatedServerWatchdog
implements Runnable {
    private static final Logger LOGGER = LogManager.getLogger();
    private final MinecraftDedicatedServer server;
    private final long maxTickTime;

    public DedicatedServerWatchdog(MinecraftDedicatedServer server) {
        this.server = server;
        this.maxTickTime = server.getMaxTickTime();
    }

    @Override
    public void run() {
        while (this.server.isRunning()) {
            long l = this.server.getServerStartTime();
            long l2 = Util.getMeasuringTimeMs();
            _snowman = l2 - l;
            if (_snowman > this.maxTickTime) {
                LOGGER.fatal("A single server tick took {} seconds (should be max {})", (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf((float)_snowman / 1000.0f)), (Object)String.format(Locale.ROOT, "%.2f", Float.valueOf(0.05f)));
                LOGGER.fatal("Considering it to be crashed, server will forcibly shutdown.");
                ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
                ThreadInfo[] _snowman2 = threadMXBean.dumpAllThreads(true, true);
                StringBuilder _snowman3 = new StringBuilder();
                Error _snowman4 = new Error("Watchdog");
                for (ThreadInfo threadInfo : _snowman2) {
                    if (threadInfo.getThreadId() == this.server.getThread().getId()) {
                        _snowman4.setStackTrace(threadInfo.getStackTrace());
                    }
                    _snowman3.append(threadInfo);
                    _snowman3.append("\n");
                }
                CrashReport crashReport = new CrashReport("Watching Server", _snowman4);
                this.server.populateCrashReport(crashReport);
                CrashReportSection _snowman5 = crashReport.addElement("Thread Dump");
                _snowman5.add("Threads", _snowman3);
                CrashReportSection _snowman6 = crashReport.addElement("Performance stats");
                _snowman6.add("Random tick rate", () -> this.server.getSaveProperties().getGameRules().get(GameRules.RANDOM_TICK_SPEED).toString());
                _snowman6.add("Level stats", () -> Streams.stream(this.server.getWorlds()).map(serverWorld -> serverWorld.getRegistryKey() + ": " + serverWorld.method_31268()).collect(Collectors.joining(",\n")));
                Bootstrap.println("Crash report:\n" + crashReport.asString());
                File file = new File(new File(this.server.getRunDirectory(), "crash-reports"), "crash-" + new SimpleDateFormat("yyyy-MM-dd_HH.mm.ss").format(new Date()) + "-server.txt");
                if (crashReport.writeToFile(file)) {
                    LOGGER.error("This crash report has been saved to: {}", (Object)file.getAbsolutePath());
                } else {
                    LOGGER.error("We were unable to save this crash report to disk.");
                }
                this.shutdown();
            }
            try {
                Thread.sleep(l + this.maxTickTime - l2);
            }
            catch (InterruptedException interruptedException) {}
        }
    }

    private void shutdown() {
        try {
            Timer timer = new Timer();
            timer.schedule(new TimerTask(this){
                final /* synthetic */ DedicatedServerWatchdog field_13826;
                {
                    this.field_13826 = dedicatedServerWatchdog;
                }

                public void run() {
                    Runtime.getRuntime().halt(1);
                }
            }, 10000L);
            System.exit(1);
        }
        catch (Throwable throwable) {
            Runtime.getRuntime().halt(1);
        }
    }
}

