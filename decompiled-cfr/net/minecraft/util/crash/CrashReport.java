/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ArrayUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util.crash;

import com.google.common.collect.Lists;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.PrintWriter;
import java.io.StringWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.concurrent.CompletionException;
import java.util.stream.Collectors;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReportSection;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ArrayUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CrashReport {
    private static final Logger LOGGER = LogManager.getLogger();
    private final String message;
    private final Throwable cause;
    private final CrashReportSection systemDetailsSection = new CrashReportSection(this, "System Details");
    private final List<CrashReportSection> otherSections = Lists.newArrayList();
    private File file;
    private boolean hasStackTrace = true;
    private StackTraceElement[] stackTrace = new StackTraceElement[0];

    public CrashReport(String message, Throwable cause) {
        this.message = message;
        this.cause = cause;
        this.fillSystemDetails();
    }

    private void fillSystemDetails() {
        this.systemDetailsSection.add("Minecraft Version", () -> SharedConstants.getGameVersion().getName());
        this.systemDetailsSection.add("Minecraft Version ID", () -> SharedConstants.getGameVersion().getId());
        this.systemDetailsSection.add("Operating System", () -> System.getProperty("os.name") + " (" + System.getProperty("os.arch") + ") version " + System.getProperty("os.version"));
        this.systemDetailsSection.add("Java Version", () -> System.getProperty("java.version") + ", " + System.getProperty("java.vendor"));
        this.systemDetailsSection.add("Java VM Version", () -> System.getProperty("java.vm.name") + " (" + System.getProperty("java.vm.info") + "), " + System.getProperty("java.vm.vendor"));
        this.systemDetailsSection.add("Memory", () -> {
            Runtime runtime = Runtime.getRuntime();
            long _snowman2 = runtime.maxMemory();
            long _snowman3 = runtime.totalMemory();
            long _snowman4 = runtime.freeMemory();
            long _snowman5 = _snowman2 / 1024L / 1024L;
            long _snowman6 = _snowman3 / 1024L / 1024L;
            long _snowman7 = _snowman4 / 1024L / 1024L;
            return _snowman4 + " bytes (" + _snowman7 + " MB) / " + _snowman3 + " bytes (" + _snowman6 + " MB) up to " + _snowman2 + " bytes (" + _snowman5 + " MB)";
        });
        this.systemDetailsSection.add("CPUs", Runtime.getRuntime().availableProcessors());
        this.systemDetailsSection.add("JVM Flags", () -> {
            List list = Util.getJVMFlags().collect(Collectors.toList());
            return String.format("%d total; %s", list.size(), list.stream().collect(Collectors.joining(" ")));
        });
    }

    public String getMessage() {
        return this.message;
    }

    public Throwable getCause() {
        return this.cause;
    }

    public void addStackTrace(StringBuilder stringBuilder2) {
        StringBuilder stringBuilder2;
        if (!(this.stackTrace != null && this.stackTrace.length > 0 || this.otherSections.isEmpty())) {
            this.stackTrace = (StackTraceElement[])ArrayUtils.subarray((Object[])this.otherSections.get(0).getStackTrace(), (int)0, (int)1);
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            stringBuilder2.append("-- Head --\n");
            stringBuilder2.append("Thread: ").append(Thread.currentThread().getName()).append("\n");
            stringBuilder2.append("Stacktrace:\n");
            for (StackTraceElement stackTraceElement : this.stackTrace) {
                stringBuilder2.append("\t").append("at ").append(stackTraceElement);
                stringBuilder2.append("\n");
            }
            stringBuilder2.append("\n");
        }
        for (CrashReportSection crashReportSection : this.otherSections) {
            crashReportSection.addStackTrace(stringBuilder2);
            stringBuilder2.append("\n\n");
        }
        this.systemDetailsSection.addStackTrace(stringBuilder2);
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public String getCauseAsString() {
        String string;
        StringWriter stringWriter = null;
        PrintWriter _snowman2 = null;
        Throwable _snowman3 = this.cause;
        if (_snowman3.getMessage() == null) {
            if (_snowman3 instanceof NullPointerException) {
                _snowman3 = new NullPointerException(this.message);
            } else if (_snowman3 instanceof StackOverflowError) {
                _snowman3 = new StackOverflowError(this.message);
            } else if (_snowman3 instanceof OutOfMemoryError) {
                _snowman3 = new OutOfMemoryError(this.message);
            }
            _snowman3.setStackTrace(this.cause.getStackTrace());
        }
        try {
            stringWriter = new StringWriter();
            _snowman2 = new PrintWriter(stringWriter);
            _snowman3.printStackTrace(_snowman2);
            string = stringWriter.toString();
        }
        catch (Throwable throwable) {
            IOUtils.closeQuietly((Writer)stringWriter);
            IOUtils.closeQuietly(_snowman2);
            throw throwable;
        }
        IOUtils.closeQuietly((Writer)stringWriter);
        IOUtils.closeQuietly((Writer)_snowman2);
        return string;
    }

    public String asString() {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---- Minecraft Crash Report ----\n");
        stringBuilder.append("// ");
        stringBuilder.append(CrashReport.generateWittyComment());
        stringBuilder.append("\n\n");
        stringBuilder.append("Time: ");
        stringBuilder.append(new SimpleDateFormat().format(new Date()));
        stringBuilder.append("\n");
        stringBuilder.append("Description: ");
        stringBuilder.append(this.message);
        stringBuilder.append("\n\n");
        stringBuilder.append(this.getCauseAsString());
        stringBuilder.append("\n\nA detailed walkthrough of the error, its code path and all known details is as follows:\n");
        for (int i = 0; i < 87; ++i) {
            stringBuilder.append("-");
        }
        stringBuilder.append("\n\n");
        this.addStackTrace(stringBuilder);
        return stringBuilder.toString();
    }

    public File getFile() {
        return this.file;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    public boolean writeToFile(File file) {
        boolean bl;
        if (this.file != null) {
            return false;
        }
        if (file.getParentFile() != null) {
            file.getParentFile().mkdirs();
        }
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStreamWriter.write(this.asString());
            this.file = file;
            bl = true;
        }
        catch (Throwable _snowman2) {
            boolean bl2;
            try {
                LOGGER.error("Could not save crash report to {}", (Object)file, (Object)_snowman2);
                bl2 = false;
            }
            catch (Throwable throwable) {
                IOUtils.closeQuietly(outputStreamWriter);
                throw throwable;
            }
            IOUtils.closeQuietly((Writer)outputStreamWriter);
            return bl2;
        }
        IOUtils.closeQuietly((Writer)outputStreamWriter);
        return bl;
    }

    public CrashReportSection getSystemDetailsSection() {
        return this.systemDetailsSection;
    }

    public CrashReportSection addElement(String name) {
        return this.addElement(name, 1);
    }

    public CrashReportSection addElement(String name, int ignoredStackTraceCallCount) {
        CrashReportSection crashReportSection = new CrashReportSection(this, name);
        if (this.hasStackTrace) {
            int n = crashReportSection.initStackTrace(ignoredStackTraceCallCount);
            StackTraceElement[] _snowman2 = this.cause.getStackTrace();
            StackTraceElement _snowman3 = null;
            StackTraceElement _snowman4 = null;
            _snowman = _snowman2.length - n;
            if (_snowman < 0) {
                System.out.println("Negative index in crash report handler (" + _snowman2.length + "/" + n + ")");
            }
            if (_snowman2 != null && 0 <= _snowman && _snowman < _snowman2.length) {
                _snowman3 = _snowman2[_snowman];
                if (_snowman2.length + 1 - n < _snowman2.length) {
                    _snowman4 = _snowman2[_snowman2.length + 1 - n];
                }
            }
            this.hasStackTrace = crashReportSection.method_584(_snowman3, _snowman4);
            if (n > 0 && !this.otherSections.isEmpty()) {
                CrashReportSection crashReportSection2 = this.otherSections.get(this.otherSections.size() - 1);
                crashReportSection2.trimStackTraceEnd(n);
            } else if (_snowman2 != null && _snowman2.length >= n && 0 <= _snowman && _snowman < _snowman2.length) {
                this.stackTrace = new StackTraceElement[_snowman];
                System.arraycopy(_snowman2, 0, this.stackTrace, 0, this.stackTrace.length);
            } else {
                this.hasStackTrace = false;
            }
        }
        this.otherSections.add(crashReportSection);
        return crashReportSection;
    }

    private static String generateWittyComment() {
        String[] stringArray = new String[]{"Who set us up the TNT?", "Everything's going to plan. No, really, that was supposed to happen.", "Uh... Did I do that?", "Oops.", "Why did you do that?", "I feel sad now :(", "My bad.", "I'm sorry, Dave.", "I let you down. Sorry :(", "On the bright side, I bought you a teddy bear!", "Daisy, daisy...", "Oh - I know what I did wrong!", "Hey, that tickles! Hehehe!", "I blame Dinnerbone.", "You should try our sister game, Minceraft!", "Don't be sad. I'll do better next time, I promise!", "Don't be sad, have a hug! <3", "I just don't know what went wrong :(", "Shall we play a game?", "Quite honestly, I wouldn't worry myself about that.", "I bet Cylons wouldn't have this problem.", "Sorry :(", "Surprise! Haha. Well, this is awkward.", "Would you like a cupcake?", "Hi. I'm Minecraft, and I'm a crashaholic.", "Ooh. Shiny.", "This doesn't make any sense!", "Why is it breaking :(", "Don't do that.", "Ouch. That hurt :(", "You're mean.", "This is a token for 1 free hug. Redeem at your nearest Mojangsta: [~~HUG~~]", "There are four lights!", "But it works on my machine."};
        try {
            return stringArray[(int)(Util.getMeasuringTimeNano() % (long)stringArray.length)];
        }
        catch (Throwable _snowman2) {
            return "Witty comment unavailable :(";
        }
    }

    public static CrashReport create(Throwable cause, String title) {
        while (cause instanceof CompletionException && cause.getCause() != null) {
            cause = cause.getCause();
        }
        CrashReport crashReport = cause instanceof CrashException ? ((CrashException)cause).getReport() : new CrashReport(title, cause);
        return crashReport;
    }

    public static void initCrashReport() {
        new CrashReport("Don't panic!", new Throwable()).asString();
    }
}

