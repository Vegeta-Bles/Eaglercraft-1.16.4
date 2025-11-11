import com.google.common.collect.Lists;
import java.util.List;
import java.util.Locale;
import javax.annotation.Nullable;

public class m {
   private final l a;
   private final String b;
   private final List<m.a> c = Lists.newArrayList();
   private StackTraceElement[] d = new StackTraceElement[0];

   public m(l var1, String var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public static String a(double var0, double var2, double var4) {
      return String.format(Locale.ROOT, "%.2f,%.2f,%.2f - %s", _snowman, _snowman, _snowman, a(new fx(_snowman, _snowman, _snowman)));
   }

   public static String a(fx var0) {
      return a(_snowman.u(), _snowman.v(), _snowman.w());
   }

   public static String a(int var0, int var1, int var2) {
      StringBuilder _snowman = new StringBuilder();

      try {
         _snowman.append(String.format("World: (%d,%d,%d)", _snowman, _snowman, _snowman));
      } catch (Throwable var16) {
         _snowman.append("(Error finding world loc)");
      }

      _snowman.append(", ");

      try {
         int _snowmanx = _snowman >> 4;
         int _snowmanxx = _snowman >> 4;
         int _snowmanxxx = _snowman & 15;
         int _snowmanxxxx = _snowman >> 4;
         int _snowmanxxxxx = _snowman & 15;
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
         int _snowmanx = _snowman >> 9;
         int _snowmanxx = _snowman >> 9;
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

   public m a(String var1, n<String> var2) {
      try {
         this.a(_snowman, _snowman.call());
      } catch (Throwable var4) {
         this.a(_snowman, var4);
      }

      return this;
   }

   public m a(String var1, Object var2) {
      this.c.add(new m.a(_snowman, _snowman));
      return this;
   }

   public void a(String var1, Throwable var2) {
      this.a(_snowman, (Object)_snowman);
   }

   public int a(int var1) {
      StackTraceElement[] _snowman = Thread.currentThread().getStackTrace();
      if (_snowman.length <= 0) {
         return 0;
      } else {
         this.d = new StackTraceElement[_snowman.length - 3 - _snowman];
         System.arraycopy(_snowman, 3 + _snowman, this.d, 0, this.d.length);
         return this.d.length;
      }
   }

   public boolean a(StackTraceElement var1, StackTraceElement var2) {
      if (this.d.length != 0 && _snowman != null) {
         StackTraceElement _snowman = this.d[0];
         if (_snowman.isNativeMethod() == _snowman.isNativeMethod()
            && _snowman.getClassName().equals(_snowman.getClassName())
            && _snowman.getFileName().equals(_snowman.getFileName())
            && _snowman.getMethodName().equals(_snowman.getMethodName())) {
            if (_snowman != null != this.d.length > 1) {
               return false;
            } else if (_snowman != null && !this.d[1].equals(_snowman)) {
               return false;
            } else {
               this.d[0] = _snowman;
               return true;
            }
         } else {
            return false;
         }
      } else {
         return false;
      }
   }

   public void b(int var1) {
      StackTraceElement[] _snowman = new StackTraceElement[this.d.length - _snowman];
      System.arraycopy(this.d, 0, _snowman, 0, _snowman.length);
      this.d = _snowman;
   }

   public void a(StringBuilder var1) {
      _snowman.append("-- ").append(this.b).append(" --\n");
      _snowman.append("Details:");

      for (m.a _snowman : this.c) {
         _snowman.append("\n\t");
         _snowman.append(_snowman.a());
         _snowman.append(": ");
         _snowman.append(_snowman.b());
      }

      if (this.d != null && this.d.length > 0) {
         _snowman.append("\nStacktrace:");

         for (StackTraceElement _snowman : this.d) {
            _snowman.append("\n\tat ");
            _snowman.append(_snowman);
         }
      }
   }

   public StackTraceElement[] a() {
      return this.d;
   }

   public static void a(m var0, fx var1, @Nullable ceh var2) {
      if (_snowman != null) {
         _snowman.a("Block", _snowman::toString);
      }

      _snowman.a("Block location", () -> a(_snowman));
   }

   static class a {
      private final String a;
      private final String b;

      public a(String var1, @Nullable Object var2) {
         this.a = _snowman;
         if (_snowman == null) {
            this.b = "~~NULL~~";
         } else if (_snowman instanceof Throwable) {
            Throwable _snowman = (Throwable)_snowman;
            this.b = "~~ERROR~~ " + _snowman.getClass().getSimpleName() + ": " + _snowman.getMessage();
         } else {
            this.b = _snowman.toString();
         }
      }

      public String a() {
         return this.a;
      }

      public String b() {
         return this.b;
      }
   }
}
