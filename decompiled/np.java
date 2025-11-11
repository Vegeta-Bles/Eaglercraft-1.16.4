import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public class np {
   private final np.a a;
   private final String b;

   public np(np.a var1, String var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public np.a a() {
      return this.a;
   }

   public String b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         np _snowman = (np)_snowman;
         if (this.a != _snowman.a) {
            return false;
         } else {
            return this.b != null ? this.b.equals(_snowman.b) : _snowman.b == null;
         }
      } else {
         return false;
      }
   }

   @Override
   public String toString() {
      return "ClickEvent{action=" + this.a + ", value='" + this.b + '\'' + '}';
   }

   @Override
   public int hashCode() {
      int _snowman = this.a.hashCode();
      return 31 * _snowman + (this.b != null ? this.b.hashCode() : 0);
   }

   public static enum a {
      a("open_url", true),
      b("open_file", false),
      c("run_command", true),
      d("suggest_command", true),
      e("change_page", true),
      f("copy_to_clipboard", true);

      private static final Map<String, np.a> g = Arrays.stream(values()).collect(Collectors.toMap(np.a::b, var0 -> (np.a)var0));
      private final boolean h;
      private final String i;

      private a(String var3, boolean var4) {
         this.i = _snowman;
         this.h = _snowman;
      }

      public boolean a() {
         return this.h;
      }

      public String b() {
         return this.i;
      }

      public static np.a a(String var0) {
         return g.get(_snowman);
      }
   }
}
