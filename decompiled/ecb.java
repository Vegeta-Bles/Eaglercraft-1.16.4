public class ecb extends ece<ccf> {
   private final dwn a;
   private final dwn c;
   private final dwn[] d = new dwn[4];

   public ecb(ecd var1) {
      super(_snowman);
      this.a = new dwn(64, 64, 0, 0);
      this.a.a(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
      this.c = new dwn(64, 64, 0, 22);
      this.c.a(0.0F, 0.0F, 0.0F, 16.0F, 16.0F, 6.0F, 0.0F);
      this.d[0] = new dwn(64, 64, 50, 0);
      this.d[1] = new dwn(64, 64, 50, 6);
      this.d[2] = new dwn(64, 64, 50, 12);
      this.d[3] = new dwn(64, 64, 50, 18);
      this.d[0].a(0.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
      this.d[1].a(0.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
      this.d[2].a(-16.0F, 6.0F, -16.0F, 3.0F, 3.0F, 3.0F);
      this.d[3].a(-16.0F, 6.0F, 0.0F, 3.0F, 3.0F, 3.0F);
      this.d[0].d = (float) (Math.PI / 2);
      this.d[1].d = (float) (Math.PI / 2);
      this.d[2].d = (float) (Math.PI / 2);
      this.d[3].d = (float) (Math.PI / 2);
      this.d[0].f = 0.0F;
      this.d[1].f = (float) (Math.PI / 2);
      this.d[2].f = (float) (Math.PI * 3.0 / 2.0);
      this.d[3].f = (float) Math.PI;
   }

   public void a(ccf var1, float var2, dfm var3, eag var4, int var5, int var6) {
      elr _snowman = ear.j[_snowman.d().b()];
      brx _snowmanx = _snowman.v();
      if (_snowmanx != null) {
         ceh _snowmanxx = _snowman.p();
         bwc.c<? extends ccf> _snowmanxxx = bwc.a(cck.x, buj::h, buj::g, bve.b, _snowmanxx, _snowmanx, _snowman.o(), (var0, var1x) -> false);
         int _snowmanxxxx = _snowmanxxx.apply(new ecf<>()).get(_snowman);
         this.a(_snowman, _snowman, _snowmanxx.c(buj.a) == cev.a, _snowmanxx.c(buj.aq), _snowman, _snowmanxxxx, _snowman, false);
      } else {
         this.a(_snowman, _snowman, true, gc.d, _snowman, _snowman, _snowman, false);
         this.a(_snowman, _snowman, false, gc.d, _snowman, _snowman, _snowman, true);
      }
   }

   private void a(dfm var1, eag var2, boolean var3, gc var4, elr var5, int var6, int var7, boolean var8) {
      this.a.h = _snowman;
      this.c.h = !_snowman;
      this.d[0].h = !_snowman;
      this.d[1].h = _snowman;
      this.d[2].h = !_snowman;
      this.d[3].h = _snowman;
      _snowman.a();
      _snowman.a(0.0, 0.5625, _snowman ? -1.0 : 0.0);
      _snowman.a(g.b.a(90.0F));
      _snowman.a(0.5, 0.5, 0.5);
      _snowman.a(g.f.a(180.0F + _snowman.o()));
      _snowman.a(-0.5, -0.5, -0.5);
      dfq _snowman = _snowman.a(_snowman, eao::b);
      this.a.a(_snowman, _snowman, _snowman, _snowman);
      this.c.a(_snowman, _snowman, _snowman, _snowman);
      this.d[0].a(_snowman, _snowman, _snowman, _snowman);
      this.d[1].a(_snowman, _snowman, _snowman, _snowman);
      this.d[2].a(_snowman, _snowman, _snowman, _snowman);
      this.d[3].a(_snowman, _snowman, _snowman, _snowman);
      _snowman.b();
   }
}
