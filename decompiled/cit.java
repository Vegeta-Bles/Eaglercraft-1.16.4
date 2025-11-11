import com.mojang.serialization.Codec;

public class cit extends cla<cmk> {
   public cit(Codec<cmk> var1) {
      super(_snowman);
   }

   protected boolean a(cfy var1, bsy var2, long var3, chx var5, int var6, int var7, bsv var8, brd var9, cmk var10) {
      _snowman.a(_snowman, _snowman, _snowman, 10387320);
      return _snowman.nextFloat() < _snowman.c;
   }

   @Override
   public cla.a<cmk> a() {
      return cit.a::new;
   }

   public static class a extends crv<cmk> {
      public a(cla<cmk> var1, int var2, int var3, cra var4, int var5, long var6) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      public void a(gn var1, cfy var2, csw var3, int var4, int var5, bsv var6, cmk var7) {
         int _snowman = _snowman * 16;
         int _snowmanx = _snowman * 16;
         fx _snowmanxx = new fx(_snowman + 9, 90, _snowmanx + 9);
         this.b.add(new crb.a(_snowmanxx));
         this.b();
      }

      @Override
      public fx a() {
         return new fx((this.f() << 4) + 9, 0, (this.g() << 4) + 9);
      }
   }
}
