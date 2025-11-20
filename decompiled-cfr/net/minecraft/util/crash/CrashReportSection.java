/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.util.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.crash.CrashCallable;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.math.BlockPos;

public class CrashReportSection {
    private final CrashReport report;
    private final String title;
    private final List<Element> elements = Lists.newArrayList();
    private StackTraceElement[] stackTrace = new StackTraceElement[0];

    public CrashReportSection(CrashReport report, String title) {
        this.report = report;
        this.title = title;
    }

    public static String createPositionString(double x, double y, double z) {
        return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", x, y, z, CrashReportSection.createPositionString(new BlockPos(x, y, z)));
    }

    public static String createPositionString(BlockPos pos) {
        return CrashReportSection.createPositionString(pos.getX(), pos.getY(), pos.getZ());
    }

    public static String createPositionString(int x, int y, int z) {
        StringBuilder stringBuilder = new StringBuilder();
        try {
            stringBuilder.append(String.format("World: (%d,%d,%d)", x, y, z));
        }
        catch (Throwable _snowman2) {
            stringBuilder.append("(Error finding world loc)");
        }
        stringBuilder.append(", ");
        try {
            int n = x >> 4;
            _snowman = z >> 4;
            _snowman = x & 0xF;
            _snowman = y >> 4;
            _snowman = z & 0xF;
            _snowman = n << 4;
            _snowman = _snowman << 4;
            _snowman = (n + 1 << 4) - 1;
            _snowman = (_snowman + 1 << 4) - 1;
            stringBuilder.append(String.format("Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", _snowman, _snowman, _snowman, n, _snowman, _snowman, _snowman, _snowman, _snowman));
        }
        catch (Throwable throwable) {
            stringBuilder.append("(Error finding chunk loc)");
        }
        stringBuilder.append(", ");
        try {
            int n = x >> 9;
            _snowman = z >> 9;
            _snowman = n << 5;
            _snowman = _snowman << 5;
            _snowman = (n + 1 << 5) - 1;
            _snowman = (_snowman + 1 << 5) - 1;
            _snowman = n << 9;
            _snowman = _snowman << 9;
            _snowman = (n + 1 << 9) - 1;
            _snowman = (_snowman + 1 << 9) - 1;
            stringBuilder.append(String.format("Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)", n, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman));
        }
        catch (Throwable throwable) {
            stringBuilder.append("(Error finding world loc)");
        }
        return stringBuilder.toString();
    }

    public CrashReportSection add(String string, CrashCallable<String> crashCallable) {
        try {
            this.add(string, crashCallable.call());
        }
        catch (Throwable throwable) {
            this.add(string, throwable);
        }
        return this;
    }

    public CrashReportSection add(String name, Object object) {
        this.elements.add(new Element(name, object));
        return this;
    }

    public void add(String name, Throwable throwable) {
        this.add(name, (Object)throwable);
    }

    public int initStackTrace(int ignoredCallCount) {
        StackTraceElement[] stackTraceElementArray = Thread.currentThread().getStackTrace();
        if (stackTraceElementArray.length <= 0) {
            return 0;
        }
        this.stackTrace = new StackTraceElement[stackTraceElementArray.length - 3 - ignoredCallCount];
        System.arraycopy(stackTraceElementArray, 3 + ignoredCallCount, this.stackTrace, 0, this.stackTrace.length);
        return this.stackTrace.length;
    }

    public boolean method_584(StackTraceElement stackTraceElement, StackTraceElement stackTraceElement2) {
        if (this.stackTrace.length == 0 || stackTraceElement == null) {
            return false;
        }
        _snowman = this.stackTrace[0];
        if (!(_snowman.isNativeMethod() == stackTraceElement.isNativeMethod() && _snowman.getClassName().equals(stackTraceElement.getClassName()) && _snowman.getFileName().equals(stackTraceElement.getFileName()) && _snowman.getMethodName().equals(stackTraceElement.getMethodName()))) {
            return false;
        }
        if (stackTraceElement2 != null != this.stackTrace.length > 1) {
            return false;
        }
        if (stackTraceElement2 != null && !this.stackTrace[1].equals(stackTraceElement2)) {
            return false;
        }
        this.stackTrace[0] = stackTraceElement;
        return true;
    }

    public void trimStackTraceEnd(int callCount) {
        StackTraceElement[] stackTraceElementArray = new StackTraceElement[this.stackTrace.length - callCount];
        System.arraycopy(this.stackTrace, 0, stackTraceElementArray, 0, stackTraceElementArray.length);
        this.stackTrace = stackTraceElementArray;
    }

    public void addStackTrace(StringBuilder stringBuilder2) {
        stringBuilder2.append("-- ").append(this.title).append(" --\n");
        stringBuilder2.append("Details:");
        for (Element element : this.elements) {
            stringBuilder2.append("\n\t");
            stringBuilder2.append(element.getName());
            stringBuilder2.append(": ");
            stringBuilder2.append(element.getDetail());
        }
        if (this.stackTrace != null && this.stackTrace.length > 0) {
            StringBuilder stringBuilder2;
            stringBuilder2.append("\nStacktrace:");
            for (StackTraceElement stackTraceElement : this.stackTrace) {
                stringBuilder2.append("\n\tat ");
                stringBuilder2.append(stackTraceElement);
            }
        }
    }

    public StackTraceElement[] getStackTrace() {
        return this.stackTrace;
    }

    public static void addBlockInfo(CrashReportSection element, BlockPos pos, @Nullable BlockState state) {
        if (state != null) {
            element.add("Block", state::toString);
        }
        element.add("Block location", () -> CrashReportSection.createPositionString(pos));
    }

    static class Element {
        private final String name;
        private final String detail;

        public Element(String name, @Nullable Object detail) {
            this.name = name;
            if (detail == null) {
                this.detail = "~~NULL~~";
            } else if (detail instanceof Throwable) {
                Throwable throwable = (Throwable)detail;
                this.detail = "~~ERROR~~ " + throwable.getClass().getSimpleName() + ": " + throwable.getMessage();
            } else {
                this.detail = detail.toString();
            }
        }

        public String getName() {
            return this.name;
        }

        public String getDetail() {
            return this.detail;
        }
    }
}

