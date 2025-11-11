public class ecj extends ece<ccu> {
   public static final elr a = new elr(ekb.d, new vk("entity/enchanting_table_book"));
   private final dto c = new dto();

   public ecj(ecd var1) {
      super(_snowman);
   }

   public void a(ccu var1, float var2, dfm var3, eag var4, int var5, int var6) {
      _snowman.a();
      _snowman.a(0.5, 0.75, 0.5);
      float _snowman = (float)_snowman.a + _snowman;
      _snowman.a(0.0, (double)(0.1F + afm.a(_snowman * 0.1F) * 0.01F), 0.0);
      float _snowmanx = _snowman.k - _snowman.l;

      while (_snowmanx >= (float) Math.PI) {
         _snowmanx -= (float) (Math.PI * 2);
      }

      while (_snowmanx < (float) -Math.PI) {
         _snowmanx += (float) (Math.PI * 2);
      }

      float _snowmanxx = _snowman.l + _snowmanx * _snowman;
      _snowman.a(g.d.c(-_snowmanxx));
      _snowman.a(g.f.a(80.0F));
      float _snowmanxxx = afm.g(_snowman, _snowman.c, _snowman.b);
      float _snowmanxxxx = afm.h(_snowmanxxx + 0.25F) * 1.6F - 0.3F;
      float _snowmanxxxxx = afm.h(_snowmanxxx + 0.75F) * 1.6F - 0.3F;
      float _snowmanxxxxxx = afm.g(_snowman, _snowman.j, _snowman.i);
      this.c.a(_snowman, afm.a(_snowmanxxxx, 0.0F, 1.0F), afm.a(_snowmanxxxxx, 0.0F, 1.0F), _snowmanxxxxxx);
      dfq _snowmanxxxxxxx = a.a(_snowman, eao::b);
      this.c.b(_snowman, _snowmanxxxxxxx, _snowman, _snowman, 1.0F, 1.0F, 1.0F, 1.0F);
      _snowman.b();
   }
}
