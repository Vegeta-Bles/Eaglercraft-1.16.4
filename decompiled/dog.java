import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;

public class dog extends dot {
   private final aat a;
   private long b = -1L;
   private static final Object2IntMap<cga> c = x.a(new Object2IntOpenHashMap(), var0 -> {
      var0.defaultReturnValue(0);
      var0.put(cga.a, 5526612);
      var0.put(cga.b, 10066329);
      var0.put(cga.c, 6250897);
      var0.put(cga.d, 8434258);
      var0.put(cga.e, 13750737);
      var0.put(cga.f, 7497737);
      var0.put(cga.g, 7169628);
      var0.put(cga.h, 3159410);
      var0.put(cga.i, 2213376);
      var0.put(cga.j, 13421772);
      var0.put(cga.k, 15884384);
      var0.put(cga.l, 15658734);
      var0.put(cga.m, 16777215);
   });

   public dog(aat var1) {
      super(dkz.a);
      this.a = _snowman;
   }

   @Override
   public boolean as_() {
      return false;
   }

   @Override
   public void e() {
      dkz.b.a(new of("narrator.loading.done").getString());
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      String _snowman = afm.a(this.a.e(), 0, 100) + "%";
      long _snowmanx = x.b();
      if (_snowmanx - this.b > 2000L) {
         this.b = _snowmanx;
         dkz.b.a(new of("narrator.loading", _snowman).getString());
      }

      int _snowmanxx = this.k / 2;
      int _snowmanxxx = this.l / 2;
      int _snowmanxxxx = 30;
      a(_snowman, this.a, _snowmanxx, _snowmanxxx + 30, 2, 0);
      a(_snowman, this.o, _snowman, _snowmanxx, _snowmanxxx - 9 / 2 - 30, 16777215);
   }

   public static void a(dfm var0, aat var1, int var2, int var3, int var4, int var5) {
      int _snowman = _snowman + _snowman;
      int _snowmanx = _snowman.c();
      int _snowmanxx = _snowmanx * _snowman - _snowman;
      int _snowmanxxx = _snowman.d();
      int _snowmanxxxx = _snowmanxxx * _snowman - _snowman;
      int _snowmanxxxxx = _snowman - _snowmanxxxx / 2;
      int _snowmanxxxxxx = _snowman - _snowmanxxxx / 2;
      int _snowmanxxxxxxx = _snowmanxx / 2 + 1;
      int _snowmanxxxxxxxx = -16772609;
      if (_snowman != 0) {
         a(_snowman, _snowman - _snowmanxxxxxxx, _snowman - _snowmanxxxxxxx, _snowman - _snowmanxxxxxxx + 1, _snowman + _snowmanxxxxxxx, -16772609);
         a(_snowman, _snowman + _snowmanxxxxxxx - 1, _snowman - _snowmanxxxxxxx, _snowman + _snowmanxxxxxxx, _snowman + _snowmanxxxxxxx, -16772609);
         a(_snowman, _snowman - _snowmanxxxxxxx, _snowman - _snowmanxxxxxxx, _snowman + _snowmanxxxxxxx, _snowman - _snowmanxxxxxxx + 1, -16772609);
         a(_snowman, _snowman - _snowmanxxxxxxx, _snowman + _snowmanxxxxxxx - 1, _snowman + _snowmanxxxxxxx, _snowman + _snowmanxxxxxxx, -16772609);
      }

      for (int _snowmanxxxxxxxxx = 0; _snowmanxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxx++) {
         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < _snowmanxxx; _snowmanxxxxxxxxxx++) {
            cga _snowmanxxxxxxxxxxx = _snowman.a(_snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            int _snowmanxxxxxxxxxxxx = _snowmanxxxxx + _snowmanxxxxxxxxx * _snowman;
            int _snowmanxxxxxxxxxxxxx = _snowmanxxxxxx + _snowmanxxxxxxxxxx * _snowman;
            a(_snowman, _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxx + _snowman, _snowmanxxxxxxxxxxxxx + _snowman, c.getInt(_snowmanxxxxxxxxxxx) | 0xFF000000);
         }
      }
   }
}
