import com.google.common.collect.ImmutableList;
import com.mojang.serialization.Codec;
import java.util.List;
import java.util.Random;

public class ckh extends cla<cmh> {
   private static final List<btg.c> u = ImmutableList.of(new btg.c(aqe.F, 1, 2, 4));

   public ckh(Codec<cmh> var1) {
      super(_snowman);
   }

   @Override
   protected boolean b() {
      return false;
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmh var10) {
      for (bsv _snowman : _snowman.a(_snowman * 16 + 9, _snowman.f(), _snowman * 16 + 9, 16)) {
         if (!_snowman.e().a(this)) {
            return false;
         }
      }

      for (bsv _snowmanx : _snowman.a(_snowman * 16 + 9, _snowman.f(), _snowman * 16 + 9, 29)) {
         if (_snowmanx.t() != bsv.b.l && _snowmanx.t() != bsv.b.n) {
            return false;
         }
      }

      return true;
   }

   @Override
   public cla.a<cmh> a() {
      return ckh.a::new;
   }

   @Override
   public List<btg.c> c() {
      return u;
   }

   public static class a extends crv<cmh> {
      private boolean e;

      public a(cla<cmh> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmh var7) {
         this.b(_snowman, _snowman);
      }

      private void b(int var1, int var2) {
         int _snowman = _snowman * 16 - 29;
         int _snowmanx = _snowman * 16 - 29;
         gc _snowmanxx = gc.c.a.a(this.d);
         this.b.add(new crl.h(this.d, _snowman, _snowmanx, _snowmanxx));
         this.b();
         this.e = true;
      }

      @Override
      public void a(bsr var1, bsn var2, cfy var3, Random var4, cra var5, brd var6) {
         if (!this.e) {
            this.b.clear();
            this.b(this.f(), this.g());
         }

         super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
