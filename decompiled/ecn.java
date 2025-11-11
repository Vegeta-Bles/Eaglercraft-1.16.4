import java.util.List;

public class ecn extends ece<cdf> {
   private final ecn.a a = new ecn.a();

   public ecn(ecd var1) {
      super(_snowman);
   }

   public void a(cdf var1, float var2, dfm var3, eag var4, int var5, int var6) {
      ceh _snowman = _snowman.p();
      _snowman.a();
      float _snowmanx = 0.6666667F;
      if (_snowman.b() instanceof cal) {
         _snowman.a(0.5, 0.5, 0.5);
         float _snowmanxx = -((float)(_snowman.c(cal.c) * 360) / 16.0F);
         _snowman.a(g.d.a(_snowmanxx));
         this.a.b.h = true;
      } else {
         _snowman.a(0.5, 0.5, 0.5);
         float _snowmanxx = -_snowman.c(cbl.c).o();
         _snowman.a(g.d.a(_snowmanxx));
         _snowman.a(0.0, -0.3125, -0.4375);
         this.a.b.h = false;
      }

      _snowman.a();
      _snowman.a(0.6666667F, -0.6666667F, -0.6666667F);
      elr _snowmanxx = a(_snowman.b());
      dfq _snowmanxxx = _snowmanxx.a(_snowman, this.a::a);
      this.a.a.a(_snowman, _snowmanxxx, _snowman, _snowman);
      this.a.b.a(_snowman, _snowmanxxx, _snowman, _snowman);
      _snowman.b();
      dku _snowmanxxxx = this.b.a();
      float _snowmanxxxxx = 0.010416667F;
      _snowman.a(0.0, 0.33333334F, 0.046666667F);
      _snowman.a(0.010416667F, -0.010416667F, 0.010416667F);
      int _snowmanxxxxxx = _snowman.g().h();
      double _snowmanxxxxxxx = 0.4;
      int _snowmanxxxxxxxx = (int)((double)det.b(_snowmanxxxxxx) * 0.4);
      int _snowmanxxxxxxxxx = (int)((double)det.c(_snowmanxxxxxx) * 0.4);
      int _snowmanxxxxxxxxxx = (int)((double)det.d(_snowmanxxxxxx) * 0.4);
      int _snowmanxxxxxxxxxxx = det.a(0, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx);
      int _snowmanxxxxxxxxxxxx = 20;

      for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 4; _snowmanxxxxxxxxxxxxx++) {
         afa _snowmanxxxxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxxxxxx, var1x -> {
            List<afa> _snowmanxxxxxxxxxxxxxxx = _snowman.b(var1x, 90);
            return _snowmanxxxxxxxxxxxxxxx.isEmpty() ? afa.a : _snowmanxxxxxxxxxxxxxxx.get(0);
         });
         if (_snowmanxxxxxxxxxxxxxx != null) {
            float _snowmanxxxxxxxxxxxxxxx = (float)(-_snowmanxxxx.a(_snowmanxxxxxxxxxxxxxx) / 2);
            _snowmanxxxx.a(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, (float)(_snowmanxxxxxxxxxxxxx * 10 - 20), _snowmanxxxxxxxxxxx, false, _snowman.c().a(), _snowman, false, 0, _snowman);
         }
      }

      _snowman.b();
   }

   public static elr a(buo var0) {
      cfq _snowman;
      if (_snowman instanceof bzt) {
         _snowman = ((bzt)_snowman).c();
      } else {
         _snowman = cfq.a;
      }

      return ear.a(_snowman);
   }

   public static final class a extends duv {
      public final dwn a = new dwn(64, 32, 0, 0);
      public final dwn b;

      public a() {
         super(eao::d);
         this.a.a(-12.0F, -14.0F, -1.0F, 24.0F, 12.0F, 2.0F, 0.0F);
         this.b = new dwn(64, 32, 0, 14);
         this.b.a(-1.0F, -2.0F, -1.0F, 2.0F, 14.0F, 2.0F, 0.0F);
      }

      @Override
      public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         this.a.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
         this.b.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }
}
