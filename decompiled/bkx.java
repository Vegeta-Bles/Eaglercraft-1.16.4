import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;
import javax.annotation.Nullable;

public enum bkx implements afs {
   a(0, "white", 16383998, cvb.j, 15790320, 16777215),
   b(1, "orange", 16351261, cvb.q, 15435844, 16738335),
   c(2, "magenta", 13061821, cvb.r, 12801229, 16711935),
   d(3, "light_blue", 3847130, cvb.s, 6719955, 10141901),
   e(4, "yellow", 16701501, cvb.t, 14602026, 16776960),
   f(5, "lime", 8439583, cvb.u, 4312372, 12582656),
   g(6, "pink", 15961002, cvb.v, 14188952, 16738740),
   h(7, "gray", 4673362, cvb.w, 4408131, 8421504),
   i(8, "light_gray", 10329495, cvb.x, 11250603, 13882323),
   j(9, "cyan", 1481884, cvb.y, 2651799, 65535),
   k(10, "purple", 8991416, cvb.z, 8073150, 10494192),
   l(11, "blue", 3949738, cvb.A, 2437522, 255),
   m(12, "brown", 8606770, cvb.B, 5320730, 9127187),
   n(13, "green", 6192150, cvb.C, 3887386, 65280),
   o(14, "red", 11546150, cvb.D, 11743532, 16711680),
   p(15, "black", 1908001, cvb.E, 1973019, 0);

   private static final bkx[] q = Arrays.stream(values()).sorted(Comparator.comparingInt(bkx::b)).toArray(bkx[]::new);
   private static final Int2ObjectOpenHashMap<bkx> r = new Int2ObjectOpenHashMap(
      Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.y, var0 -> (bkx)var0))
   );
   private final int s;
   private final String t;
   private final cvb u;
   private final int v;
   private final int w;
   private final float[] x;
   private final int y;
   private final int z;

   private bkx(int var3, String var4, int var5, cvb var6, int var7, int var8) {
      this.s = _snowman;
      this.t = _snowman;
      this.v = _snowman;
      this.u = _snowman;
      this.z = _snowman;
      int _snowman = (_snowman & 0xFF0000) >> 16;
      int _snowmanx = (_snowman & 0xFF00) >> 8;
      int _snowmanxx = (_snowman & 0xFF) >> 0;
      this.w = _snowmanxx << 16 | _snowmanx << 8 | _snowman << 0;
      this.x = new float[]{(float)_snowman / 255.0F, (float)_snowmanx / 255.0F, (float)_snowmanxx / 255.0F};
      this.y = _snowman;
   }

   public int b() {
      return this.s;
   }

   public String c() {
      return this.t;
   }

   public float[] e() {
      return this.x;
   }

   public cvb f() {
      return this.u;
   }

   public int g() {
      return this.y;
   }

   public int h() {
      return this.z;
   }

   public static bkx a(int var0) {
      if (_snowman < 0 || _snowman >= q.length) {
         _snowman = 0;
      }

      return q[_snowman];
   }

   public static bkx a(String var0, bkx var1) {
      for (bkx _snowman : values()) {
         if (_snowman.t.equals(_snowman)) {
            return _snowman;
         }
      }

      return _snowman;
   }

   @Nullable
   public static bkx b(int var0) {
      return (bkx)r.get(_snowman);
   }

   @Override
   public String toString() {
      return this.t;
   }

   @Override
   public String a() {
      return this.t;
   }
}
