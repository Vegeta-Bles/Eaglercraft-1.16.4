import com.google.common.collect.Maps;
import java.util.Map;
import javax.annotation.Nullable;

public class dkx implements AutoCloseable {
   private static final vk a = new vk("textures/map/map_icons.png");
   private static final eao b = eao.p(a);
   private final ekd c;
   private final Map<String, dkx.a> d = Maps.newHashMap();

   public dkx(ekd var1) {
      this.c = _snowman;
   }

   public void a(cxx var1) {
      this.b(_snowman).a();
   }

   public void a(dfm var1, eag var2, cxx var3, boolean var4, int var5) {
      this.b(_snowman).a(_snowman, _snowman, _snowman, _snowman);
   }

   private dkx.a b(cxx var1) {
      dkx.a _snowman = this.d.get(_snowman.d());
      if (_snowman == null) {
         _snowman = new dkx.a(_snowman);
         this.d.put(_snowman.d(), _snowman);
      }

      return _snowman;
   }

   @Nullable
   public dkx.a a(String var1) {
      return this.d.get(_snowman);
   }

   public void a() {
      for (dkx.a _snowman : this.d.values()) {
         _snowman.close();
      }

      this.d.clear();
   }

   @Nullable
   public cxx a(@Nullable dkx.a var1) {
      return _snowman != null ? _snowman.b : null;
   }

   @Override
   public void close() {
      this.a();
   }

   class a implements AutoCloseable {
      private final cxx b;
      private final ejs c;
      private final eao d;

      private a(cxx var2) {
         this.b = _snowman;
         this.c = new ejs(128, 128, true);
         vk _snowman = dkx.this.c.a("map/" + _snowman.d(), this.c);
         this.d = eao.p(_snowman);
      }

      private void a() {
         for (int _snowman = 0; _snowman < 128; _snowman++) {
            for (int _snowmanx = 0; _snowmanx < 128; _snowmanx++) {
               int _snowmanxx = _snowmanx + _snowman * 128;
               int _snowmanxxx = this.b.g[_snowmanxx] & 255;
               if (_snowmanxxx / 4 == 0) {
                  this.c.e().a(_snowmanx, _snowman, 0);
               } else {
                  this.c.e().a(_snowmanx, _snowman, cvb.a[_snowmanxxx / 4].a(_snowmanxxx & 3));
               }
            }
         }

         this.c.a();
      }

      private void a(dfm var1, eag var2, boolean var3, int var4) {
         int _snowman = 0;
         int _snowmanx = 0;
         float _snowmanxx = 0.0F;
         b _snowmanxxx = _snowman.c().a();
         dfq _snowmanxxxx = _snowman.getBuffer(this.d);
         _snowmanxxxx.a(_snowmanxxx, 0.0F, 128.0F, -0.01F).a(255, 255, 255, 255).a(0.0F, 1.0F).a(_snowman).d();
         _snowmanxxxx.a(_snowmanxxx, 128.0F, 128.0F, -0.01F).a(255, 255, 255, 255).a(1.0F, 1.0F).a(_snowman).d();
         _snowmanxxxx.a(_snowmanxxx, 128.0F, 0.0F, -0.01F).a(255, 255, 255, 255).a(1.0F, 0.0F).a(_snowman).d();
         _snowmanxxxx.a(_snowmanxxx, 0.0F, 0.0F, -0.01F).a(255, 255, 255, 255).a(0.0F, 0.0F).a(_snowman).d();
         int _snowmanxxxxx = 0;

         for (cxu _snowmanxxxxxx : this.b.j.values()) {
            if (!_snowman || _snowmanxxxxxx.f()) {
               _snowman.a();
               _snowman.a((double)(0.0F + (float)_snowmanxxxxxx.c() / 2.0F + 64.0F), (double)(0.0F + (float)_snowmanxxxxxx.d() / 2.0F + 64.0F), -0.02F);
               _snowman.a(g.f.a((float)(_snowmanxxxxxx.e() * 360) / 16.0F));
               _snowman.a(4.0F, 4.0F, 3.0F);
               _snowman.a(-0.125, 0.125, 0.0);
               byte _snowmanxxxxxxx = _snowmanxxxxxx.a();
               float _snowmanxxxxxxxx = (float)(_snowmanxxxxxxx % 16 + 0) / 16.0F;
               float _snowmanxxxxxxxxx = (float)(_snowmanxxxxxxx / 16 + 0) / 16.0F;
               float _snowmanxxxxxxxxxx = (float)(_snowmanxxxxxxx % 16 + 1) / 16.0F;
               float _snowmanxxxxxxxxxxx = (float)(_snowmanxxxxxxx / 16 + 1) / 16.0F;
               b _snowmanxxxxxxxxxxxx = _snowman.c().a();
               float _snowmanxxxxxxxxxxxxx = -0.001F;
               dfq _snowmanxxxxxxxxxxxxxx = _snowman.getBuffer(dkx.b);
               _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxx, -1.0F, 1.0F, (float)_snowmanxxxxx * -0.001F).a(255, 255, 255, 255).a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx).a(_snowman).d();
               _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxx, 1.0F, 1.0F, (float)_snowmanxxxxx * -0.001F).a(255, 255, 255, 255).a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxx).a(_snowman).d();
               _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxx, 1.0F, -1.0F, (float)_snowmanxxxxx * -0.001F).a(255, 255, 255, 255).a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx).a(_snowman).d();
               _snowmanxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxx, -1.0F, -1.0F, (float)_snowmanxxxxx * -0.001F).a(255, 255, 255, 255).a(_snowmanxxxxxxxx, _snowmanxxxxxxxxxxx).a(_snowman).d();
               _snowman.b();
               if (_snowmanxxxxxx.g() != null) {
                  dku _snowmanxxxxxxxxxxxxxxx = djz.C().g;
                  nr _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxx.g();
                  float _snowmanxxxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx);
                  float _snowmanxxxxxxxxxxxxxxxxxx = afm.a(25.0F / _snowmanxxxxxxxxxxxxxxxxx, 0.0F, 6.0F / 9.0F);
                  _snowman.a();
                  _snowman.a(
                     (double)(0.0F + (float)_snowmanxxxxxx.c() / 2.0F + 64.0F - _snowmanxxxxxxxxxxxxxxxxx * _snowmanxxxxxxxxxxxxxxxxxx / 2.0F),
                     (double)(0.0F + (float)_snowmanxxxxxx.d() / 2.0F + 64.0F + 4.0F),
                     -0.025F
                  );
                  _snowman.a(_snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, 1.0F);
                  _snowman.a(0.0, 0.0, -0.1F);
                  _snowmanxxxxxxxxxxxxxxx.a(_snowmanxxxxxxxxxxxxxxxx, 0.0F, 0.0F, -1, false, _snowman.c().a(), _snowman, false, Integer.MIN_VALUE, _snowman);
                  _snowman.b();
               }

               _snowmanxxxxx++;
            }
         }
      }

      @Override
      public void close() {
         this.c.close();
      }
   }
}
