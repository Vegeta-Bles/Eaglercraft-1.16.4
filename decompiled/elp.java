import java.util.Arrays;
import java.util.Map;
import java.util.stream.Collectors;

public enum elp implements elv {
   a(0, 0),
   b(0, 90),
   c(0, 180),
   d(0, 270),
   e(90, 0),
   f(90, 90),
   g(90, 180),
   h(90, 270),
   i(180, 0),
   j(180, 90),
   k(180, 180),
   l(180, 270),
   m(270, 0),
   n(270, 90),
   o(270, 180),
   p(270, 270);

   private static final Map<Integer, elp> q = Arrays.stream(values()).collect(Collectors.toMap(var0 -> var0.t, var0 -> (elp)var0));
   private final f r;
   private final c s;
   private final int t;

   private static int b(int var0, int var1) {
      return _snowman * 360 + _snowman;
   }

   private elp(int var3, int var4) {
      this.t = b(_snowman, _snowman);
      d _snowman = new d(new g(0.0F, 1.0F, 0.0F), (float)(-_snowman), true);
      _snowman.a(new d(new g(1.0F, 0.0F, 0.0F), (float)(-_snowman), true));
      c _snowmanx = c.a;

      for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx += 90) {
         _snowmanx = _snowmanx.a(c.u);
      }

      for (int _snowmanxx = 0; _snowmanxx < _snowman; _snowmanxx += 90) {
         _snowmanx = _snowmanx.a(c.s);
      }

      this.r = new f(null, _snowman, null, null);
      this.s = _snowmanx;
   }

   @Override
   public f b() {
      return this.r;
   }

   public static elp a(int var0, int var1) {
      return q.get(b(afm.b(_snowman, 360), afm.b(_snowman, 360)));
   }
}
