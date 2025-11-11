import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Objects;

public class diu {
   @VisibleForTesting
   protected static List<String> a(String var0) {
      return Arrays.asList(_snowman.split("\\n"));
   }

   public static List<diu.a> a(String var0, diu.b... var1) {
      return a(_snowman, Arrays.asList(_snowman));
   }

   private static List<diu.a> a(String var0, List<diu.b> var1) {
      List<String> _snowman = a(_snowman);
      return a(_snowman, _snowman);
   }

   private static List<diu.a> a(List<String> var0, List<diu.b> var1) {
      int _snowman = 0;
      List<diu.a> _snowmanx = Lists.newArrayList();

      for (String _snowmanxx : _snowman) {
         List<diu.b> _snowmanxxx = Lists.newArrayList();

         for (String _snowmanxxxx : a(_snowmanxx, "%link")) {
            if ("%link".equals(_snowmanxxxx)) {
               _snowmanxxx.add(_snowman.get(_snowman++));
            } else {
               _snowmanxxx.add(diu.b.a(_snowmanxxxx));
            }
         }

         _snowmanx.add(new diu.a(_snowmanxxx));
      }

      return _snowmanx;
   }

   public static List<String> a(String var0, String var1) {
      if (_snowman.isEmpty()) {
         throw new IllegalArgumentException("Delimiter cannot be the empty string");
      } else {
         List<String> _snowman = Lists.newArrayList();
         int _snowmanx = 0;

         int _snowmanxx;
         while ((_snowmanxx = _snowman.indexOf(_snowman, _snowmanx)) != -1) {
            if (_snowmanxx > _snowmanx) {
               _snowman.add(_snowman.substring(_snowmanx, _snowmanxx));
            }

            _snowman.add(_snowman);
            _snowmanx = _snowmanxx + _snowman.length();
         }

         if (_snowmanx < _snowman.length()) {
            _snowman.add(_snowman.substring(_snowmanx));
         }

         return _snowman;
      }
   }

   public static class a {
      public final List<diu.b> a;

      a(List<diu.b> var1) {
         this.a = _snowman;
      }

      @Override
      public String toString() {
         return "Line{segments=" + this.a + '}';
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            diu.a _snowman = (diu.a)_snowman;
            return Objects.equals(this.a, _snowman.a);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.a);
      }
   }

   public static class b {
      private final String a;
      private final String b;
      private final String c;

      private b(String var1) {
         this.a = _snowman;
         this.b = null;
         this.c = null;
      }

      private b(String var1, String var2, String var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            diu.b _snowman = (diu.b)_snowman;
            return Objects.equals(this.a, _snowman.a) && Objects.equals(this.b, _snowman.b) && Objects.equals(this.c, _snowman.c);
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         return Objects.hash(this.a, this.b, this.c);
      }

      @Override
      public String toString() {
         return "Segment{fullText='" + this.a + '\'' + ", linkTitle='" + this.b + '\'' + ", linkUrl='" + this.c + '\'' + '}';
      }

      public String a() {
         return this.b() ? this.b : this.a;
      }

      public boolean b() {
         return this.b != null;
      }

      public String c() {
         if (!this.b()) {
            throw new IllegalStateException("Not a link: " + this);
         } else {
            return this.c;
         }
      }

      public static diu.b a(String var0, String var1) {
         return new diu.b(null, _snowman, _snowman);
      }

      @VisibleForTesting
      protected static diu.b a(String var0) {
         return new diu.b(_snowman);
      }
   }
}
