package net.minecraft.util.profiler;

import com.google.common.base.Splitter;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import it.unimi.dsi.fastutil.objects.Object2LongMap;
import it.unimi.dsi.fastutil.objects.Object2LongMaps;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStreamWriter;
import java.io.Writer;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Map.Entry;
import net.minecraft.SharedConstants;
import net.minecraft.util.Util;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ProfileResultImpl implements ProfileResult {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final ProfileLocationInfo EMPTY_INFO = new ProfileLocationInfo() {
      @Override
      public long getTotalTime() {
         return 0L;
      }

      @Override
      public long getVisitCount() {
         return 0L;
      }

      @Override
      public Object2LongMap<String> getCounts() {
         return Object2LongMaps.emptyMap();
      }
   };
   private static final Splitter SPLITTER = Splitter.on('\u001e');
   private static final Comparator<Entry<String, ProfileResultImpl.CounterInfo>> COMPARATOR = Entry.<String, ProfileResultImpl.CounterInfo>comparingByValue(
         Comparator.comparingLong(_snowman -> _snowman.totalTime)
      )
      .reversed();
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
      ProfileLocationInfo _snowman = this.locationInfos.get(path);
      return _snowman != null ? _snowman : EMPTY_INFO;
   }

   @Override
   public List<ProfilerTiming> getTimings(String parentPath) {
      String _snowman = parentPath;
      ProfileLocationInfo _snowmanx = this.getInfo("root");
      long _snowmanxx = _snowmanx.getTotalTime();
      ProfileLocationInfo _snowmanxxx = this.getInfo(parentPath);
      long _snowmanxxxx = _snowmanxxx.getTotalTime();
      long _snowmanxxxxx = _snowmanxxx.getVisitCount();
      List<ProfilerTiming> _snowmanxxxxxx = Lists.newArrayList();
      if (!parentPath.isEmpty()) {
         parentPath = parentPath + '\u001e';
      }

      long _snowmanxxxxxxx = 0L;

      for (String _snowmanxxxxxxxx : this.locationInfos.keySet()) {
         if (isSubpath(parentPath, _snowmanxxxxxxxx)) {
            _snowmanxxxxxxx += this.getInfo(_snowmanxxxxxxxx).getTotalTime();
         }
      }

      float _snowmanxxxxxxxxx = (float)_snowmanxxxxxxx;
      if (_snowmanxxxxxxx < _snowmanxxxx) {
         _snowmanxxxxxxx = _snowmanxxxx;
      }

      if (_snowmanxx < _snowmanxxxxxxx) {
         _snowmanxx = _snowmanxxxxxxx;
      }

      for (String _snowmanxxxxxxxxxx : this.locationInfos.keySet()) {
         if (isSubpath(parentPath, _snowmanxxxxxxxxxx)) {
            ProfileLocationInfo _snowmanxxxxxxxxxxx = this.getInfo(_snowmanxxxxxxxxxx);
            long _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getTotalTime();
            double _snowmanxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxx * 100.0 / (double)_snowmanxxxxxxx;
            double _snowmanxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxx * 100.0 / (double)_snowmanxx;
            String _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.substring(parentPath.length());
            _snowmanxxxxxx.add(new ProfilerTiming(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx.getVisitCount()));
         }
      }

      if ((float)_snowmanxxxxxxx > _snowmanxxxxxxxxx) {
         _snowmanxxxxxx.add(
            new ProfilerTiming(
               "unspecified",
               (double)((float)_snowmanxxxxxxx - _snowmanxxxxxxxxx) * 100.0 / (double)_snowmanxxxxxxx,
               (double)((float)_snowmanxxxxxxx - _snowmanxxxxxxxxx) * 100.0 / (double)_snowmanxx,
               _snowmanxxxxx
            )
         );
      }

      Collections.sort(_snowmanxxxxxx);
      _snowmanxxxxxx.add(0, new ProfilerTiming(_snowman, 100.0, (double)_snowmanxxxxxxx * 100.0 / (double)_snowmanxx, _snowmanxxxxx));
      return _snowmanxxxxxx;
   }

   private static boolean isSubpath(String parent, String path) {
      return path.length() > parent.length() && path.startsWith(parent) && path.indexOf(30, parent.length() + 1) < 0;
   }

   private Map<String, ProfileResultImpl.CounterInfo> setupCounters() {
      Map<String, ProfileResultImpl.CounterInfo> _snowman = Maps.newTreeMap();
      this.locationInfos.forEach((_snowmanx, _snowmanxx) -> {
         Object2LongMap<String> _snowmanx = _snowmanxx.getCounts();
         if (!_snowmanx.isEmpty()) {
            List<String> _snowmanxxx = SPLITTER.splitToList(_snowmanx);
            _snowmanx.forEach((_snowmanxxxxx, _snowmanxxxx) -> _snowman.computeIfAbsent(_snowmanxxxxx, _snowmanxxxxxxx -> new ProfileResultImpl.CounterInfo()).add(_snowman.iterator(), _snowmanxxxx));
         }
      });
      return _snowman;
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

   @Override
   public boolean save(File file) {
      file.getParentFile().mkdirs();
      Writer _snowman = null;

      boolean var4;
      try {
         _snowman = new OutputStreamWriter(new FileOutputStream(file), StandardCharsets.UTF_8);
         _snowman.write(this.asString(this.getTimeSpan(), this.getTickSpan()));
         return true;
      } catch (Throwable var8) {
         LOGGER.error("Could not save profiler results to {}", file, var8);
         var4 = false;
      } finally {
         IOUtils.closeQuietly(_snowman);
      }

      return var4;
   }

   protected String asString(long timeSpan, int tickSpan) {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("---- Minecraft Profiler Results ----\n");
      _snowman.append("// ");
      _snowman.append(generateWittyComment());
      _snowman.append("\n\n");
      _snowman.append("Version: ").append(SharedConstants.getGameVersion().getId()).append('\n');
      _snowman.append("Time span: ").append(timeSpan / 1000000L).append(" ms\n");
      _snowman.append("Tick span: ").append(tickSpan).append(" ticks\n");
      _snowman.append("// This is approximately ")
         .append(String.format(Locale.ROOT, "%.2f", (float)tickSpan / ((float)timeSpan / 1.0E9F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      _snowman.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.appendTiming(0, "root", _snowman);
      _snowman.append("--- END PROFILE DUMP ---\n\n");
      Map<String, ProfileResultImpl.CounterInfo> _snowmanx = this.setupCounters();
      if (!_snowmanx.isEmpty()) {
         _snowman.append("--- BEGIN COUNTER DUMP ---\n\n");
         this.appendCounterDump(_snowmanx, _snowman, tickSpan);
         _snowman.append("--- END COUNTER DUMP ---\n\n");
      }

      return _snowman.toString();
   }

   private static StringBuilder indent(StringBuilder sb, int size) {
      sb.append(String.format("[%02d] ", size));

      for (int _snowman = 0; _snowman < size; _snowman++) {
         sb.append("|   ");
      }

      return sb;
   }

   private void appendTiming(int level, String name, StringBuilder sb) {
      List<ProfilerTiming> _snowman = this.getTimings(name);
      Object2LongMap<String> _snowmanx = ((ProfileLocationInfo)ObjectUtils.firstNonNull(new ProfileLocationInfo[]{this.locationInfos.get(name), EMPTY_INFO}))
         .getCounts();
      _snowmanx.forEach(
         (_snowmanxx, _snowmanxxx) -> indent(sb, level).append('#').append(_snowmanxx).append(' ').append(_snowmanxxx).append('/').append(_snowmanxxx / (long)this.tickDuration).append('\n')
      );
      if (_snowman.size() >= 3) {
         for (int _snowmanxx = 1; _snowmanxx < _snowman.size(); _snowmanxx++) {
            ProfilerTiming _snowmanxxx = _snowman.get(_snowmanxx);
            indent(sb, level)
               .append(_snowmanxxx.name)
               .append('(')
               .append(_snowmanxxx.visitCount)
               .append('/')
               .append(String.format(Locale.ROOT, "%.0f", (float)_snowmanxxx.visitCount / (float)this.tickDuration))
               .append(')')
               .append(" - ")
               .append(String.format(Locale.ROOT, "%.2f", _snowmanxxx.parentSectionUsagePercentage))
               .append("%/")
               .append(String.format(Locale.ROOT, "%.2f", _snowmanxxx.totalUsagePercentage))
               .append("%\n");
            if (!"unspecified".equals(_snowmanxxx.name)) {
               try {
                  this.appendTiming(level + 1, name + '\u001e' + _snowmanxxx.name, sb);
               } catch (Exception var9) {
                  sb.append("[[ EXCEPTION ").append(var9).append(" ]]");
               }
            }
         }
      }
   }

   private void appendCounter(int depth, String name, ProfileResultImpl.CounterInfo info, int tickSpan, StringBuilder sb) {
      indent(sb, depth)
         .append(name)
         .append(" total:")
         .append(info.selfTime)
         .append('/')
         .append(info.totalTime)
         .append(" average: ")
         .append(info.selfTime / (long)tickSpan)
         .append('/')
         .append(info.totalTime / (long)tickSpan)
         .append('\n');
      info.subCounters.entrySet().stream().sorted(COMPARATOR).forEach(_snowmanxxx -> this.appendCounter(depth + 1, _snowmanxxx.getKey(), _snowmanxxx.getValue(), tickSpan, sb));
   }

   private void appendCounterDump(Map<String, ProfileResultImpl.CounterInfo> counters, StringBuilder sb, int tickSpan) {
      counters.forEach((_snowmanxx, _snowmanxxx) -> {
         sb.append("-- Counter: ").append(_snowmanxx).append(" --\n");
         this.appendCounter(0, "root", _snowmanxxx.subCounters.get("root"), tickSpan, sb);
         sb.append("\n\n");
      });
   }

   private static String generateWittyComment() {
      String[] _snowman = new String[]{
         "Shiny numbers!",
         "Am I not running fast enough? :(",
         "I'm working as hard as I can!",
         "Will I ever be good enough for you? :(",
         "Speedy. Zoooooom!",
         "Hello world",
         "40% better than a crash report.",
         "Now with extra numbers",
         "Now with less numbers",
         "Now with the same numbers",
         "You should add flames to things, it makes them go faster!",
         "Do you feel the need for... optimization?",
         "*cracks redstone whip*",
         "Maybe if you treated it better then it'll have more motivation to work faster! Poor server."
      };

      try {
         return _snowman[(int)(Util.getMeasuringTimeNano() % (long)_snowman.length)];
      } catch (Throwable var2) {
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
      private final Map<String, ProfileResultImpl.CounterInfo> subCounters = Maps.newHashMap();

      private CounterInfo() {
      }

      public void add(Iterator<String> pathIterator, long time) {
         this.totalTime += time;
         if (!pathIterator.hasNext()) {
            this.selfTime += time;
         } else {
            this.subCounters.computeIfAbsent(pathIterator.next(), _snowman -> new ProfileResultImpl.CounterInfo()).add(pathIterator, time);
         }
      }
   }
}
