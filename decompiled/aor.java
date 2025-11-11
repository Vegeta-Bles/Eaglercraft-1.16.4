import java.util.Arrays;
import java.util.Comparator;
import javax.annotation.Nullable;

public enum aor {
   a(0, "peaceful"),
   b(1, "easy"),
   c(2, "normal"),
   d(3, "hard");

   private static final aor[] e = Arrays.stream(values()).sorted(Comparator.comparingInt(aor::a)).toArray(aor[]::new);
   private final int f;
   private final String g;

   private aor(int var3, String var4) {
      this.f = _snowman;
      this.g = _snowman;
   }

   public int a() {
      return this.f;
   }

   public nr b() {
      return new of("options.difficulty." + this.g);
   }

   public static aor a(int var0) {
      return e[_snowman % e.length];
   }

   @Nullable
   public static aor a(String var0) {
      for (aor _snowman : values()) {
         if (_snowman.g.equals(_snowman)) {
            return _snowman;
         }
      }

      return null;
   }

   public String c() {
      return this.g;
   }

   public aor d() {
      return e[(this.f + 1) % e.length];
   }
}
