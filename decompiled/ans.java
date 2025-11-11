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
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.ObjectUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ans implements anv {
   private static final Logger a = LogManager.getLogger();
   private static final anx b = new anx() {
      @Override
      public long a() {
         return 0L;
      }

      @Override
      public long b() {
         return 0L;
      }

      @Override
      public Object2LongMap<String> c() {
         return Object2LongMaps.emptyMap();
      }
   };
   private static final Splitter c = Splitter.on('\u001e');
   private static final Comparator<Entry<String, ans.a>> d = Entry.<String, ans.a>comparingByValue(Comparator.comparingLong(var0 -> var0.b)).reversed();
   private final Map<String, ? extends anx> e;
   private final long f;
   private final int g;
   private final long h;
   private final int i;
   private final int j;

   public ans(Map<String, ? extends anx> var1, long var2, int var4, long var5, int var7) {
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.j = _snowman - _snowman;
   }

   private anx c(String var1) {
      anx _snowman = this.e.get(_snowman);
      return _snowman != null ? _snowman : b;
   }

   @Override
   public List<any> a(String var1) {
      String _snowman = _snowman;
      anx _snowmanx = this.c("root");
      long _snowmanxx = _snowmanx.a();
      anx _snowmanxxx = this.c(_snowman);
      long _snowmanxxxx = _snowmanxxx.a();
      long _snowmanxxxxx = _snowmanxxx.b();
      List<any> _snowmanxxxxxx = Lists.newArrayList();
      if (!_snowman.isEmpty()) {
         _snowman = _snowman + '\u001e';
      }

      long _snowmanxxxxxxx = 0L;

      for (String _snowmanxxxxxxxx : this.e.keySet()) {
         if (a(_snowman, _snowmanxxxxxxxx)) {
            _snowmanxxxxxxx += this.c(_snowmanxxxxxxxx).a();
         }
      }

      float _snowmanxxxxxxxxx = (float)_snowmanxxxxxxx;
      if (_snowmanxxxxxxx < _snowmanxxxx) {
         _snowmanxxxxxxx = _snowmanxxxx;
      }

      if (_snowmanxx < _snowmanxxxxxxx) {
         _snowmanxx = _snowmanxxxxxxx;
      }

      for (String _snowmanxxxxxxxxxx : this.e.keySet()) {
         if (a(_snowman, _snowmanxxxxxxxxxx)) {
            anx _snowmanxxxxxxxxxxx = this.c(_snowmanxxxxxxxxxx);
            long _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a();
            double _snowmanxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxx * 100.0 / (double)_snowmanxxxxxxx;
            double _snowmanxxxxxxxxxxxxxx = (double)_snowmanxxxxxxxxxxxx * 100.0 / (double)_snowmanxx;
            String _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxx.substring(_snowman.length());
            _snowmanxxxxxx.add(new any(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxx.b()));
         }
      }

      if ((float)_snowmanxxxxxxx > _snowmanxxxxxxxxx) {
         _snowmanxxxxxx.add(
            new any(
               "unspecified",
               (double)((float)_snowmanxxxxxxx - _snowmanxxxxxxxxx) * 100.0 / (double)_snowmanxxxxxxx,
               (double)((float)_snowmanxxxxxxx - _snowmanxxxxxxxxx) * 100.0 / (double)_snowmanxx,
               _snowmanxxxxx
            )
         );
      }

      Collections.sort(_snowmanxxxxxx);
      _snowmanxxxxxx.add(0, new any(_snowman, 100.0, (double)_snowmanxxxxxxx * 100.0 / (double)_snowmanxx, _snowmanxxxxx));
      return _snowmanxxxxxx;
   }

   private static boolean a(String var0, String var1) {
      return _snowman.length() > _snowman.length() && _snowman.startsWith(_snowman) && _snowman.indexOf(30, _snowman.length() + 1) < 0;
   }

   private Map<String, ans.a> h() {
      Map<String, ans.a> _snowman = Maps.newTreeMap();
      this.e.forEach((var1x, var2) -> {
         Object2LongMap<String> _snowmanx = var2.c();
         if (!_snowmanx.isEmpty()) {
            List<String> _snowmanx = c.splitToList(var1x);
            _snowmanx.forEach((var2x, var3x) -> _snowman.computeIfAbsent(var2x, var0x -> new ans.a()).a(_snowman.iterator(), var3x));
         }
      });
      return _snowman;
   }

   @Override
   public long a() {
      return this.f;
   }

   @Override
   public int b() {
      return this.g;
   }

   @Override
   public long c() {
      return this.h;
   }

   @Override
   public int d() {
      return this.i;
   }

   @Override
   public boolean a(File var1) {
      _snowman.getParentFile().mkdirs();
      Writer _snowman = null;

      boolean var4;
      try {
         _snowman = new OutputStreamWriter(new FileOutputStream(_snowman), StandardCharsets.UTF_8);
         _snowman.write(this.a(this.g(), this.f()));
         return true;
      } catch (Throwable var8) {
         a.error("Could not save profiler results to {}", _snowman, var8);
         var4 = false;
      } finally {
         IOUtils.closeQuietly(_snowman);
      }

      return var4;
   }

   protected String a(long var1, int var3) {
      StringBuilder _snowman = new StringBuilder();
      _snowman.append("---- Minecraft Profiler Results ----\n");
      _snowman.append("// ");
      _snowman.append(i());
      _snowman.append("\n\n");
      _snowman.append("Version: ").append(w.a().getId()).append('\n');
      _snowman.append("Time span: ").append(_snowman / 1000000L).append(" ms\n");
      _snowman.append("Tick span: ").append(_snowman).append(" ticks\n");
      _snowman.append("// This is approximately ")
         .append(String.format(Locale.ROOT, "%.2f", (float)_snowman / ((float)_snowman / 1.0E9F)))
         .append(" ticks per second. It should be ")
         .append(20)
         .append(" ticks per second\n\n");
      _snowman.append("--- BEGIN PROFILE DUMP ---\n\n");
      this.a(0, "root", _snowman);
      _snowman.append("--- END PROFILE DUMP ---\n\n");
      Map<String, ans.a> _snowmanx = this.h();
      if (!_snowmanx.isEmpty()) {
         _snowman.append("--- BEGIN COUNTER DUMP ---\n\n");
         this.a(_snowmanx, _snowman, _snowman);
         _snowman.append("--- END COUNTER DUMP ---\n\n");
      }

      return _snowman.toString();
   }

   private static StringBuilder a(StringBuilder var0, int var1) {
      _snowman.append(String.format("[%02d] ", _snowman));

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         _snowman.append("|   ");
      }

      return _snowman;
   }

   private void a(int var1, String var2, StringBuilder var3) {
      List<any> _snowman = this.a(_snowman);
      Object2LongMap<String> _snowmanx = ((anx)ObjectUtils.firstNonNull(new anx[]{this.e.get(_snowman), b})).c();
      _snowmanx.forEach((var3x, var4x) -> a(_snowman, _snowman).append('#').append(var3x).append(' ').append(var4x).append('/').append(var4x / (long)this.j).append('\n'));
      if (_snowman.size() >= 3) {
         for (int _snowmanxx = 1; _snowmanxx < _snowman.size(); _snowmanxx++) {
            any _snowmanxxx = _snowman.get(_snowmanxx);
            a(_snowman, _snowman)
               .append(_snowmanxxx.d)
               .append('(')
               .append(_snowmanxxx.c)
               .append('/')
               .append(String.format(Locale.ROOT, "%.0f", (float)_snowmanxxx.c / (float)this.j))
               .append(')')
               .append(" - ")
               .append(String.format(Locale.ROOT, "%.2f", _snowmanxxx.a))
               .append("%/")
               .append(String.format(Locale.ROOT, "%.2f", _snowmanxxx.b))
               .append("%\n");
            if (!"unspecified".equals(_snowmanxxx.d)) {
               try {
                  this.a(_snowman + 1, _snowman + '\u001e' + _snowmanxxx.d, _snowman);
               } catch (Exception var9) {
                  _snowman.append("[[ EXCEPTION ").append(var9).append(" ]]");
               }
            }
         }
      }
   }

   private void a(int var1, String var2, ans.a var3, int var4, StringBuilder var5) {
      a(_snowman, _snowman)
         .append(_snowman)
         .append(" total:")
         .append(_snowman.a)
         .append('/')
         .append(_snowman.b)
         .append(" average: ")
         .append(_snowman.a / (long)_snowman)
         .append('/')
         .append(_snowman.b / (long)_snowman)
         .append('\n');
      _snowman.c.entrySet().stream().sorted(d).forEach(var4x -> this.a(_snowman + 1, var4x.getKey(), var4x.getValue(), _snowman, _snowman));
   }

   private void a(Map<String, ans.a> var1, StringBuilder var2, int var3) {
      _snowman.forEach((var3x, var4) -> {
         _snowman.append("-- Counter: ").append(var3x).append(" --\n");
         this.a(0, "root", var4.c.get("root"), _snowman, _snowman);
         _snowman.append("\n\n");
      });
   }

   private static String i() {
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
         "Maybe if_ you treated it better then it'll have more motivation to work faster! Poor server."
      };

      try {
         return _snowman[(int)(x.c() % (long)_snowman.length)];
      } catch (Throwable var2) {
         return "Witty comment unavailable :(";
      }
   }

   @Override
   public int f() {
      return this.j;
   }

   static class a {
      private long a;
      private long b;
      private final Map<String, ans.a> c = Maps.newHashMap();

      private a() {
      }

      public void a(Iterator<String> var1, long var2) {
         this.b += _snowman;
         if (!_snowman.hasNext()) {
            this.a += _snowman;
         } else {
            this.c.computeIfAbsent(_snowman.next(), var0 -> new ans.a()).a(_snowman, _snowman);
         }
      }
   }
}
