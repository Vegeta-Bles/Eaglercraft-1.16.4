package net.minecraft.util.crash;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.util.math.BlockPos;

public class CrashReportSection {
   private final CrashReport report;
   private final String title;
   private final List<CrashReportSection.Element> elements = Lists.newArrayList();
   private StackTraceElement[] stackTrace = new StackTraceElement[0];

   public CrashReportSection(CrashReport report, String title) {
      this.report = report;
      this.title = title;
   }

   public static String createPositionString(double x, double y, double z) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", x, y, z, createPositionString(new BlockPos(x, y, z)));
   }

   public static String createPositionString(BlockPos pos) {
      return createPositionString(pos.getX(), pos.getY(), pos.getZ());
   }

   public static String createPositionString(int x, int y, int z) {
      StringBuilder _snowman = new StringBuilder();

      try {
         _snowman.append(String.format("World: (%d,%d,%d)", x, y, z));
      } catch (Throwable var16) {
         _snowman.append("(Error finding world loc)");
      }

      _snowman.append(", ");

      try {
         int _snowmanx = x >> 4;
         int _snowmanxx = z >> 4;
         int _snowmanxxx = x & 15;
         int _snowmanxxxx = y >> 4;
         int _snowmanxxxxx = z & 15;
         int _snowmanxxxxxx = _snowmanx << 4;
         int _snowmanxxxxxxx = _snowmanxx << 4;
         int _snowmanxxxxxxxx = (_snowmanx + 1 << 4) - 1;
         int _snowmanxxxxxxxxx = (_snowmanxx + 1 << 4) - 1;
         _snowman.append(
            String.format(
               "Chunk: (at %d,%d,%d in %d,%d; contains blocks %d,0,%d to %d,255,%d)", _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanx, _snowmanxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx
            )
         );
      } catch (Throwable var15) {
         _snowman.append("(Error finding chunk loc)");
      }

      _snowman.append(", ");

      try {
         int _snowmanx = x >> 9;
         int _snowmanxx = z >> 9;
         int _snowmanxxx = _snowmanx << 5;
         int _snowmanxxxx = _snowmanxx << 5;
         int _snowmanxxxxx = (_snowmanx + 1 << 5) - 1;
         int _snowmanxxxxxx = (_snowmanxx + 1 << 5) - 1;
         int _snowmanxxxxxxx = _snowmanx << 9;
         int _snowmanxxxxxxxx = _snowmanxx << 9;
         int _snowmanxxxxxxxxx = (_snowmanx + 1 << 9) - 1;
         int _snowmanxxxxxxxxxx = (_snowmanxx + 1 << 9) - 1;
         _snowman.append(
            String.format(
               "Region: (%d,%d; contains chunks %d,%d to %d,%d, blocks %d,0,%d to %d,255,%d)",
               _snowmanx,
               _snowmanxx,
               _snowmanxxx,
               _snowmanxxxx,
               _snowmanxxxxx,
               _snowmanxxxxxx,
               _snowmanxxxxxxx,
               _snowmanxxxxxxxx,
               _snowmanxxxxxxxxx,
               _snowmanxxxxxxxxxx
            )
         );
      } catch (Throwable var14) {
         _snowman.append("(Error finding world loc)");
      }

      return _snowman.toString();
   }

   public CrashReportSection add(String _snowman, CrashCallable<String> _snowman) {
      try {
         this.add(_snowman, _snowman.call());
      } catch (Throwable var4) {
         this.add(_snowman, var4);
      }

      return this;
   }

   public CrashReportSection add(String name, Object _snowman) {
      this.elements.add(new CrashReportSection.Element(name, _snowman));
      return this;
   }

   public void add(String name, Throwable _snowman) {
      this.add(name, (Object)_snowman);
   }

   public int initStackTrace(int ignoredCallCount) {
      StackTraceElement[] _snowman = Thread.currentThread().getStackTrace();
      if (_snowman.length <= 0) {
         return 0;
      } else {
         this.stackTrace = new StackTraceElement[_snowman.length - 3 - ignoredCallCount];
         System.arraycopy(_snowman, 3 + ignoredCallCount, this.stackTrace, 0, this.stackTrace.length);
         return this.stackTrace.length;
      }
   }

   public boolean method_584(StackTraceElement _snowman, StackTraceElement _snowman) {
      if (this.stackTrace.length != 0 && _snowman != null) {
         StackTraceElement _snowmanxx = this.stackTrace[0];
         if (_snowmanxx.isNativeMethod() == _snowman.isNativeMethod()
            && _snowmanxx.getClassName().equals(_snowman.getClassName())
            && _snowmanxx.getFileName().equals(_snowman.getFileName())
            && _snowmanxx.getMethodName().equals(_snowman.getMethodName())) {
            if (_snowman != null != this.stackTrace.length > 1) {
               return false;
            } else if (_snowman != null && !this.stackTrace[1].equals(_snowman)) {
               return false;
            } else {
               this.stackTrace[0] = _snowman;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void trimStackTraceEnd(int callCount) {
      StackTraceElement[] _snowman = new StackTraceElement[this.stackTrace.length - callCount];
      System.arraycopy(this.stackTrace, 0, _snowman, 0, _snowman.length);
      this.stackTrace = _snowman;
   }

   public void addStackTrace(StringBuilder _snowman) {
      _snowman.append("-- ").append(this.title).append(" --\n");
      _snowman.append("Details:");

      for (CrashReportSection.Element _snowmanx : this.elements) {
         _snowman.append("\n\t");
         _snowman.append(_snowmanx.getName());
         _snowman.append(": ");
         _snowman.append(_snowmanx.getDetail());
      }

      if (this.stackTrace != null && this.stackTrace.length > 0) {
         _snowman.append("\nStacktrace:");

         for (StackTraceElement _snowmanx : this.stackTrace) {
            _snowman.append("\n\tat ");
            _snowman.append(_snowmanx);
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

      element.add("Block location", () -> createPositionString(pos));
   }

   static class Element {
      private final String name;
      private final String detail;

      public Element(String name, @Nullable Object detail) {
         this.name = name;
         if (detail == null) {
            this.detail = "~~NULL~~";
         } else if (detail instanceof Throwable) {
            Throwable _snowman = (Throwable)detail;
            this.detail = "~~ERROR~~ " + _snowman.getClass().getSimpleName() + ": " + _snowman.getMessage();
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
