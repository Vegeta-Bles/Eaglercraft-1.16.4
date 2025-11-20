/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.base.Splitter
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  it.unimi.dsi.fastutil.objects.Object2LongMap
 *  it.unimi.dsi.fastutil.objects.Object2LongMaps
 *  org.apache.commons.io.IOUtils
 *  org.apache.commons.lang3.ObjectUtils
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.util.profiler;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.TreeMap;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import net.minecraft.util.profiler.ProfileLocationInfo;
import net.minecraft.util.profiler.ProfileResult;
import net.minecraft.util.profiler.ProfilerTiming;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfileResultImpl
implements ProfileResult {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final ProfileLocationInfo EMPTY_INFO = new ProfileLocationInfo(){

        public long getTotalTime() {
            return 0L;
        }

        public long getVisitCount() {
            return 0L;
        }

        public Object2LongMap<String> getCounts() {
            return Object2LongMaps.emptyMap();
        }
    };
    private static final Splitter SPLITTER = Splitter.on((char)'\u001e');
    private static final Comparator<Map.Entry<String, CounterInfo>> COMPARATOR = Map.Entry.comparingByValue(Comparator.comparingLong(counterInfo -> CounterInfo.method_24265(counterInfo))).reversed();
    private final Map<String, ? extends ProfileLocationInfo> locationInfos;
    private final long startTime;
    private final int startTick;
    private final long endTime;
    private final int endTick;
    private final int tickDuration;

    public ProfileResultImpl(Map<String, ? extends ProfileLocationInfo> locationInfos, long startTime, int startTick, long endTime, int endTick) {
        this.locationInfos = locationInfos;
        this.startTime = startTime;
        this.startTick = startTick;
        this.endTime = endTime;
        this.endTick = endTick;
        this.tickDuration = endTick - startTick;
    }

    private ProfileLocationInfo getInfo(String path) {
        ProfileLocationInfo profileLocationInfo = this.locationInfos.get(path);
        return profileLocationInfo != null ? profileLocationInfo : EMPTY_INFO;
    }

    @Override
    public List<ProfilerTiming> getTimings(String parentPath) {
        String string = parentPath;
        ProfileLocationInfo _snowman2 = this.getInfo("root");
        long _snowman3 = _snowman2.getTotalTime();
        ProfileLocationInfo _snowman4 = this.getInfo(parentPath);
        long _snowman5 = _snowman4.getTotalTime();
        long _snowman6 = _snowman4.getVisitCount();
        ArrayList _snowman7 = Lists.newArrayList();
        if (!parentPath.isEmpty()) {
            parentPath = parentPath + '\u001e';
        }
        long _snowman8 = 0L;
        for (String string2 : this.locationInfos.keySet()) {
            if (!ProfileResultImpl.isSubpath(parentPath, string2)) continue;
            _snowman8 += this.getInfo(string2).getTotalTime();
        }
        float _snowman9 = _snowman8;
        if (_snowman8 < _snowman5) {
            _snowman8 = _snowman5;
        }
        if (_snowman3 < _snowman8) {
            _snowman3 = _snowman8;
        }
        for (String string3 : this.locationInfos.keySet()) {
            if (!ProfileResultImpl.isSubpath(parentPath, string3)) continue;
            ProfileLocationInfo profileLocationInfo = this.getInfo(string3);
            long _snowman10 = profileLocationInfo.getTotalTime();
            double _snowman11 = (double)_snowman10 * 100.0 / (double)_snowman8;
            double _snowman12 = (double)_snowman10 * 100.0 / (double)_snowman3;
            String _snowman13 = string3.substring(parentPath.length());
            _snowman7.add(new ProfilerTiming(_snowman13, _snowman11, _snowman12, profileLocationInfo.getVisitCount()));
        }
        if ((float)_snowman8 > _snowman9) {
            _snowman7.add(new ProfilerTiming("unspecified", (double)((float)_snowman8 - _snowman9) * 100.0 / (double)_snowman8, (double)((float)_snowman8 - _snowman9) * 100.0 / (double)_snowman3, _snowman6));
        }
        Collections.sort(_snowman7);
        _snowman7.add(0, new ProfilerTiming(string, 100.0, (double)_snowman8 * 100.0 / (double)_snowman3, _snowman6));
        return _snowman7;
    }

    private static boolean isSubpath(String parent, String path) {
        return path.length() > parent.length() && path.startsWith(parent) && path.indexOf(30, parent.length() + 1) < 0;
    }

    private Map<String, CounterInfo> setupCounters() {
        TreeMap treeMap = Maps.newTreeMap();
        this.locationInfos.forEach((string, profileLocationInfo) -> {
            Object2LongMap<String> object2LongMap = profileLocationInfo.getCounts();
            if (!object2LongMap.isEmpty()) {
                List list = SPLITTER.splitToList((CharSequence)string);
                object2LongMap.forEach((string2, l) -> treeMap.computeIfAbsent(string2, string -> new CounterInfo()).add(list.iterator(), (long)l));
            }
        });
        return treeMap;
    }

    @Override
    public long getStartTime() {
        return this.startTime;
    }

    @Override
    public int getStartTick() {
        return this.startTick;
    }

    @Override
    public long getEndTime() {
        return this.endTime;
    }

    @Override
    public int getEndTick() {
        return this.endTick;
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public boolean save(File file) {
        boolean bl;
        file.getParentFile().mkdirs();
        OutputStreamWriter outputStreamWriter = null;
        try {
            outputStreamWriter = new OutputStreamWriter((OutputStream)new FileOutputStream(file), StandardCharsets.UTF_8);
            outputStreamWriter.write(this.asString(this.getTimeSpan(), this.getTickSpan()));
            bl = true;
        }
        catch (Throwable _snowman2) {
            boolean bl2;
            try {
                LOGGER.error("Could not save profiler results to {}", (Object)file, (Object)_snowman2);
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

    protected String asString(long timeSpan, int tickSpan) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("---- Minecraft Profiler Results ----\n");
        stringBuilder.append("// ");
        stringBuilder.append(ProfileResultImpl.generateWittyComment());
        stringBuilder.append("\n\n");
        stringBuilder.append("Version: ").append(SharedConstants.getGameVersion().getId()).append('\n');
        stringBuilder.append("Time span: ").append(timeSpan / 1000000L).append(" ms\n");
        stringBuilder.append("Tick span: ").append(tickSpan).append(" ticks\n");
        stringBuilder.append("// This is approximately ").append(String.format(Locale.ROOT, "%.2f", Float.valueOf((float)tickSpan / ((float)timeSpan / 1.0E9f)))).append(" ticks per second. It should be ").append(20).append(" ticks per second\n\n");
        stringBuilder.append("--- BEGIN PROFILE DUMP ---\n\n");
        this.appendTiming(0, "root", stringBuilder);
        stringBuilder.append("--- END PROFILE DUMP ---\n\n");
        Map<String, CounterInfo> _snowman2 = this.setupCounters();
        if (!_snowman2.isEmpty()) {
            stringBuilder.append("--- BEGIN COUNTER DUMP ---\n\n");
            this.appendCounterDump(_snowman2, stringBuilder, tickSpan);
            stringBuilder.append("--- END COUNTER DUMP ---\n\n");
        }
        return stringBuilder.toString();
    }

    private static StringBuilder indent(StringBuilder sb, int size) {
        sb.append(String.format("[%02d] ", size));
        for (int i = 0; i < size; ++i) {
            sb.append("|   ");
        }
        return sb;
    }

    private void appendTiming(int level, String name, StringBuilder sb) {
        List<ProfilerTiming> list = this.getTimings(name);
        Object2LongMap<String> _snowman2 = ((ProfileLocationInfo)ObjectUtils.firstNonNull((Object[])new ProfileLocationInfo[]{this.locationInfos.get(name), EMPTY_INFO})).getCounts();
        _snowman2.forEach((string, l) -> ProfileResultImpl.indent(sb, level).append('#').append((String)string).append(' ').append(l).append('/').append(l / (long)this.tickDuration).append('\n'));
        if (list.size() < 3) {
            return;
        }
        for (int i = 1; i < list.size(); ++i) {
            ProfilerTiming profilerTiming = list.get(i);
            ProfileResultImpl.indent(sb, level).append(profilerTiming.name).append('(').append(profilerTiming.visitCount).append('/').append(String.format(Locale.ROOT, "%.0f", Float.valueOf((float)profilerTiming.visitCount / (float)this.tickDuration))).append(')').append(" - ").append(String.format(Locale.ROOT, "%.2f", profilerTiming.parentSectionUsagePercentage)).append("%/").append(String.format(Locale.ROOT, "%.2f", profilerTiming.totalUsagePercentage)).append("%\n");
            if ("unspecified".equals(profilerTiming.name)) continue;
            try {
                this.appendTiming(level + 1, name + '\u001e' + profilerTiming.name, sb);
                continue;
            }
            catch (Exception _snowman3) {
                sb.append("[[ EXCEPTION ").append(_snowman3).append(" ]]");
            }
        }
    }

    private void appendCounter(int depth, String name, CounterInfo info, int tickSpan, StringBuilder sb) {
        ProfileResultImpl.indent(sb, depth).append(name).append(" total:").append(info.selfTime).append('/').append(info.totalTime).append(" average: ").append(info.selfTime / (long)tickSpan).append('/').append(info.totalTime / (long)tickSpan).append('\n');
        info.subCounters.entrySet().stream().sorted(COMPARATOR).forEach(entry -> this.appendCounter(depth + 1, (String)entry.getKey(), (CounterInfo)entry.getValue(), tickSpan, sb));
    }

    private void appendCounterDump(Map<String, CounterInfo> counters, StringBuilder sb, int tickSpan) {
        counters.forEach((string, counterInfo) -> {
            sb.append("-- Counter: ").append((String)string).append(" --\n");
            this.appendCounter(0, "root", (CounterInfo)((CounterInfo)counterInfo).subCounters.get("root"), tickSpan, sb);
            sb.append("\n\n");
        });
    }

    private static String generateWittyComment() {
        String[] stringArray = new String[]{"Shiny numbers!", "Am I not running fast enough? :(", "I'm working as hard as I can!", "Will I ever be good enough for you? :(", "Speedy. Zoooooom!", "Hello world", "40% better than a crash report.", "Now with extra numbers", "Now with less numbers", "Now with the same numbers", "You should add flames to things, it makes them go faster!", "Do you feel the need for... optimization?", "*cracks redstone whip*", "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."};
        try {
            return stringArray[(int)(Util.getMeasuringTimeNano() % (long)stringArray.length)];
        }
        catch (Throwable _snowman2) {
            return "Witty comment unavailable :(";
        }
    }

    @Override
    public int getTickSpan() {
        return this.tickDuration;
    }

    static class CounterInfo {
        private long selfTime;
        private long totalTime;
        private final Map<String, CounterInfo> subCounters = Maps.newHashMap();

        private CounterInfo() {
        }

        public void add(Iterator<String> pathIterator, long time) {
            this.totalTime += time;
            if (!pathIterator.hasNext()) {
                this.selfTime += time;
            } else {
                this.subCounters.computeIfAbsent(pathIterator.next(), string -> new CounterInfo()).add(pathIterator, time);
            }
        }
    }
}

