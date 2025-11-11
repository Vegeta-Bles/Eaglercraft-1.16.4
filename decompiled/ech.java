import java.util.Calendar;

public class ech<T extends ccj & cdc> extends ece<T> {
   private final dwn a;
   private final dwn c;
   private final dwn d;
   private final dwn e;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private boolean k;

   public ech(ecd var1) {
      super(_snowman);
      Calendar _snowman = Calendar.getInstance();
      if (_snowman.get(2) + 1 == 12 && _snowman.get(5) >= 24 && _snowman.get(5) <= 26) {
         this.k = true;
      }

      this.c = new dwn(64, 64, 0, 19);
      this.c.a(1.0F, 0.0F, 1.0F, 14.0F, 10.0F, 14.0F, 0.0F);
      this.a = new dwn(64, 64, 0, 0);
      this.a.a(1.0F, 0.0F, 0.0F, 14.0F, 5.0F, 14.0F, 0.0F);
      this.a.b = 9.0F;
      this.a.c = 1.0F;
      this.d = new dwn(64, 64, 0, 0);
      this.d.a(7.0F, -1.0F, 15.0F, 2.0F, 4.0F, 1.0F, 0.0F);
      this.d.b = 8.0F;
      this.f = new dwn(64, 64, 0, 19);
      this.f.a(1.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.e = new dwn(64, 64, 0, 0);
      this.e.a(1.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.e.b = 9.0F;
      this.e.c = 1.0F;
      this.g = new dwn(64, 64, 0, 0);
      this.g.a(15.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.g.b = 8.0F;
      this.i = new dwn(64, 64, 0, 19);
      this.i.a(0.0F, 0.0F, 1.0F, 15.0F, 10.0F, 14.0F, 0.0F);
      this.h = new dwn(64, 64, 0, 0);
      this.h.a(0.0F, 0.0F, 0.0F, 15.0F, 5.0F, 14.0F, 0.0F);
      this.h.b = 9.0F;
      this.h.c = 1.0F;
      this.j = new dwn(64, 64, 0, 0);
      this.j.a(0.0F, -1.0F, 15.0F, 1.0F, 4.0F, 1.0F, 0.0F);
      this.j.b = 8.0F;
   }

   @Override
   public void a(T var1, float var2, dfm var3, eag var4, int var5, int var6) {
      brx _snowman = _snowman.v();
      boolean _snowmanx = _snowman != null;
      ceh _snowmanxx = _snowmanx ? _snowman.p() : bup.bR.n().a(bve.b, gc.d);
      cez _snowmanxxx = _snowmanxx.b(bve.c) ? _snowmanxx.c(bve.c) : cez.a;
      buo _snowmanxxxx = _snowmanxx.b();
      if (_snowmanxxxx instanceof btn) {
         btn<?> _snowmanxxxxx = (btn<?>)_snowmanxxxx;
         boolean _snowmanxxxxxx = _snowmanxxx != cez.a;
         _snowman.a();
         float _snowmanxxxxxxx = _snowmanxx.c(bve.b).o();
         _snowman.a(0.5, 0.5, 0.5);
         _snowman.a(g.d.a(-_snowmanxxxxxxx));
         _snowman.a(-0.5, -0.5, -0.5);
         bwc.c<? extends ccn> _snowmanxxxxxxxx;
         if (_snowmanx) {
            _snowmanxxxxxxxx = _snowmanxxxxx.a(_snowmanxx, _snowman, _snowman.o(), true);
         } else {
            _snowmanxxxxxxxx = bwc.b::b;
         }

         float _snowmanxxxxxxxxx = _snowmanxxxxxxxx.apply(bve.a(_snowman)).get(_snowman);
         _snowmanxxxxxxxxx = 1.0F - _snowmanxxxxxxxxx;
         _snowmanxxxxxxxxx = 1.0F - _snowmanxxxxxxxxx * _snowmanxxxxxxxxx * _snowmanxxxxxxxxx;
         int _snowmanxxxxxxxxxx = _snowmanxxxxxxxx.apply(new ecf<>()).applyAsInt(_snowman);
         elr _snowmanxxxxxxxxxxx = ear.a(_snowman, _snowmanxxx, this.k);
         dfq _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.a(_snowman, eao::c);
         if (_snowmanxxxxxx) {
            if (_snowmanxxx == cez.b) {
               this.a(_snowman, _snowmanxxxxxxxxxxxx, this.h, this.j, this.i, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman);
            } else {
               this.a(_snowman, _snowmanxxxxxxxxxxxx, this.e, this.g, this.f, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman);
            }
         } else {
            this.a(_snowman, _snowmanxxxxxxxxxxxx, this.a, this.d, this.c, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowman);
         }

         _snowman.b();
      }
   }

   private void a(dfm var1, dfq var2, dwn var3, dwn var4, dwn var5, float var6, int var7, int var8) {
      _snowman.d = -(_snowman * (float) (Math.PI / 2));
      _snowman.d = _snowman.d;
      _snowman.a(_snowman, _snowman, _snowman, _snowman);
      _snowman.a(_snowman, _snowman, _snowman, _snowman);
      _snowman.a(_snowman, _snowman, _snowman, _snowman);
   }
}
