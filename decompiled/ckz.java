import com.mojang.serialization.Codec;
import java.util.List;

public class ckz extends cla<cmh> {
   public ckz(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   public cla.a<cmh> a() {
      return ckz.a::new;
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmh var10) {
      return _snowman.a(new brd(_snowman, _snowman));
   }

   public static class a extends crv<cmh> {
      private final long e;

      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         this.e = _snowman;
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         int _snowman = 0;

         crs.m _snowmanx;
         do {
            this.b.clear();
            this.c = cra.a();
            this.d.c(this.e + (long)(_snowman++), _snowman, _snowman);
            crs.a();
            _snowmanx = new crs.m(this.d, (_snowman << 4) + 2, (_snowman << 4) + 2);
            this.b.add(_snowmanx);
            _snowmanx.a(_snowmanx, this.b, this.d);
            List<cru> _snowmanxx = _snowmanx.c;

            while (!_snowmanxx.isEmpty()) {
               int _snowmanxxx = this.d.nextInt(_snowmanxx.size());
               cru _snowmanxxxx = _snowmanxx.remove(_snowmanxxx);
               _snowmanxxxx.a(_snowmanx, this.b, this.d);
            }

            this.b();
            this.a(_snowman.f(), this.d, 10);
         } while (this.b.isEmpty() || _snowmanx.b == null);
      }
   }
}
