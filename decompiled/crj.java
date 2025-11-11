import com.mojang.serialization.Codec;

public class crj extends cla<cmh> {
   public crj(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   public cla.a<cmh> a() {
      return crj.a::new;
   }

   public static class a extends cqz<cmh> {
      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         brd _snowman = new brd(_snowman, _snowman);
         int _snowmanx = _snowman.d() + this.d.nextInt(16);
         int _snowmanxx = _snowman.e() + this.d.nextInt(16);
         int _snowmanxxx = _snowman.f();
         int _snowmanxxxx = _snowmanxxx + this.d.nextInt(_snowman.e() - 2 - _snowmanxxx);
         brc _snowmanxxxxx = _snowman.a(_snowmanx, _snowmanxx);

         for (fx.a _snowmanxxxxxx = new fx.a(_snowmanx, _snowmanxxxx, _snowmanxx); _snowmanxxxx > _snowmanxxx; _snowmanxxxx--) {
            ceh _snowmanxxxxxxx = _snowmanxxxxx.d_(_snowmanxxxxxx);
            _snowmanxxxxxx.c(gc.a);
            ceh _snowmanxxxxxxxx = _snowmanxxxxx.d_(_snowmanxxxxxx);
            if (_snowmanxxxxxxx.g() && (_snowmanxxxxxxxx.a(bup.cM) || _snowmanxxxxxxxx.d(_snowmanxxxxx, _snowmanxxxxxx, gc.b))) {
               break;
            }
         }

         if (_snowmanxxxx > _snowmanxxx) {
            crk.a(_snowman, this.b, this.d, new fx(_snowmanx, _snowmanxxxx, _snowmanxx));
            this.b();
         }
      }
   }
}
