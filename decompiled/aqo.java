import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum aqo implements afs {
   a("monster", 70, false, false, 128),
   b("creature", 10, true, true, 128),
   c("ambient", 15, true, false, 128),
   d("water_creature", 5, true, false, 128),
   e("water_ambient", 20, true, false, 64),
   f("misc", -1, true, true, 128);

   public static final Codec<aqo> g = afs.a(aqo::values, aqo::a);
   private static final Map<String, aqo> h = Arrays.stream(values()).collect(Collectors.toMap(aqo::b, var0 -> (aqo)var0));
   private final int i;
   private final boolean j;
   private final boolean k;
   private final String l;
   private final int m = 32;
   private final int n;

   private aqo(String var3, int var4, boolean var5, boolean var6, int var7) {
      this.l = _snowman;
      this.i = _snowman;
      this.j = _snowman;
      this.k = _snowman;
      this.n = _snowman;
   }

   public String b() {
      return this.l;
   }

   @Override
   public String a() {
      return this.l;
   }

   public static aqo a(String var0) {
      return h.get(_snowman);
   }

   public int c() {
      return this.i;
   }

   public boolean d() {
      return this.j;
   }

   public boolean e() {
      return this.k;
   }

   public int f() {
      return this.n;
   }

   public int g() {
      return 32;
   }
}
