import com.google.common.collect.Lists;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.ArabicShapingException;
import com.ibm.icu.text.Bidi;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.List;
import java.util.Random;
import java.util.function.Function;
import javax.annotation.Nullable;

public class dku {
   private static final g c = new g(0.0F, 0.0F, 0.03F);
   public final int a = 9;
   public final Random b = new Random();
   private final Function<vk, dmw> d;
   private final dkj e;

   public dku(Function<vk, dmw> var1) {
      this.d = _snowman;
      this.e = new dkj((var1x, var2) -> this.a(var2.k()).a(var1x).a(var2.b()));
   }

   private dmw a(vk var1) {
      return this.d.apply(_snowman);
   }

   public int a(dfm var1, String var2, float var3, float var4, int var5) {
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c().a(), true, this.a());
   }

   public int a(dfm var1, String var2, float var3, float var4, int var5, boolean var6) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c().a(), true, _snowman);
   }

   public int b(dfm var1, String var2, float var3, float var4, int var5) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c().a(), false, this.a());
   }

   public int a(dfm var1, afa var2, float var3, float var4, int var5) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c().a(), true);
   }

   public int a(dfm var1, nr var2, float var3, float var4, int var5) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman.f(), _snowman, _snowman, _snowman, _snowman.c().a(), true);
   }

   public int b(dfm var1, afa var2, float var3, float var4, int var5) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman.c().a(), false);
   }

   public int b(dfm var1, nr var2, float var3, float var4, int var5) {
      RenderSystem.enableAlphaTest();
      return this.a(_snowman.f(), _snowman, _snowman, _snowman, _snowman.c().a(), false);
   }

   public String a(String var1) {
      try {
         Bidi _snowman = new Bidi(new ArabicShaping(8).shape(_snowman), 127);
         _snowman.setReorderingMode(0);
         return _snowman.writeReordered(2);
      } catch (ArabicShapingException var3) {
         return _snowman;
      }
   }

   private int a(String var1, float var2, float var3, int var4, b var5, boolean var6, boolean var7) {
      if (_snowman == null) {
         return 0;
      } else {
         eag.a _snowman = eag.a(dfo.a().c());
         int _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, 0, 15728880, _snowman);
         _snowman.a();
         return _snowmanx;
      }
   }

   private int a(afa var1, float var2, float var3, int var4, b var5, boolean var6) {
      eag.a _snowman = eag.a(dfo.a().c());
      int _snowmanx = this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, false, 0, 15728880);
      _snowman.a();
      return _snowmanx;
   }

   public int a(String var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      return this.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, this.a());
   }

   public int a(String var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10, boolean var11) {
      return this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public int a(nr var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      return this.a(_snowman.f(), _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public int a(afa var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      return this.b(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static int a(int var0) {
      return (_snowman & -67108864) == 0 ? _snowman | 0xFF000000 : _snowman;
   }

   private int b(String var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10, boolean var11) {
      if (_snowman) {
         _snowman = this.a(_snowman);
      }

      _snowman = a(_snowman);
      b _snowman = _snowman.d();
      if (_snowman) {
         this.b(_snowman, _snowman, _snowman, _snowman, true, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(c);
      }

      _snowman = this.b(_snowman, _snowman, _snowman, _snowman, false, _snowman, _snowman, _snowman, _snowman, _snowman);
      return (int)_snowman + (_snowman ? 1 : 0);
   }

   private int b(afa var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      _snowman = a(_snowman);
      b _snowman = _snowman.d();
      if (_snowman) {
         this.c(_snowman, _snowman, _snowman, _snowman, true, _snowman, _snowman, _snowman, _snowman, _snowman);
         _snowman.a(c);
      }

      _snowman = this.c(_snowman, _snowman, _snowman, _snowman, false, _snowman, _snowman, _snowman, _snowman, _snowman);
      return (int)_snowman + (_snowman ? 1 : 0);
   }

   private float b(String var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      dku.a _snowman = new dku.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      afr.c(_snowman, ob.a, _snowman);
      return _snowman.a(_snowman, _snowman);
   }

   private float c(afa var1, float var2, float var3, int var4, boolean var5, b var6, eag var7, boolean var8, int var9, int var10) {
      dku.a _snowman = new dku.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      _snowman.accept(_snowman);
      return _snowman.a(_snowman, _snowman);
   }

   private void a(
      dmz var1, boolean var2, boolean var3, float var4, float var5, float var6, b var7, dfq var8, float var9, float var10, float var11, float var12, int var13
   ) {
      _snowman.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman) {
         _snowman.a(_snowman, _snowman + _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }
   }

   public int b(String var1) {
      return afm.f(this.e.a(_snowman));
   }

   public int a(nu var1) {
      return afm.f(this.e.a(_snowman));
   }

   public int a(afa var1) {
      return afm.f(this.e.a(_snowman));
   }

   public String a(String var1, int var2, boolean var3) {
      return _snowman ? this.e.c(_snowman, _snowman, ob.a) : this.e.b(_snowman, _snowman, ob.a);
   }

   public String a(String var1, int var2) {
      return this.e.b(_snowman, _snowman, ob.a);
   }

   public nu a(nu var1, int var2) {
      return this.e.a(_snowman, _snowman, ob.a);
   }

   public void a(nu var1, int var2, int var3, int var4, int var5) {
      b _snowman = f.a().c();

      for (afa _snowmanx : this.b(_snowman, _snowman)) {
         this.a(_snowmanx, (float)_snowman, (float)_snowman, _snowman, _snowman, false);
         _snowman += 9;
      }
   }

   public int b(String var1, int var2) {
      return 9 * this.e.g(_snowman, _snowman, ob.a).size();
   }

   public List<afa> b(nu var1, int var2) {
      return ly.a().a(this.e.b(_snowman, _snowman, ob.a));
   }

   public boolean a() {
      return ly.a().b();
   }

   public dkj b() {
      return this.e;
   }

   class a implements afb {
      final eag a;
      private final boolean c;
      private final float d;
      private final float e;
      private final float f;
      private final float g;
      private final float h;
      private final b i;
      private final boolean j;
      private final int k;
      private float l;
      private float m;
      @Nullable
      private List<dmz.a> n;

      private void a(dmz.a var1) {
         if (this.n == null) {
            this.n = Lists.newArrayList();
         }

         this.n.add(_snowman);
      }

      public a(eag var2, float var3, float var4, int var5, boolean var6, b var7, boolean var8, int var9) {
         this.a = _snowman;
         this.l = _snowman;
         this.m = _snowman;
         this.c = _snowman;
         this.d = _snowman ? 0.25F : 1.0F;
         this.e = (float)(_snowman >> 16 & 0xFF) / 255.0F * this.d;
         this.f = (float)(_snowman >> 8 & 0xFF) / 255.0F * this.d;
         this.g = (float)(_snowman & 0xFF) / 255.0F * this.d;
         this.h = (float)(_snowman >> 24 & 0xFF) / 255.0F;
         this.i = _snowman;
         this.j = _snowman;
         this.k = _snowman;
      }

      @Override
      public boolean accept(int var1, ob var2, int var3) {
         dmw _snowman = dku.this.a(_snowman.k());
         dea _snowmanx = _snowman.a(_snowman);
         dmz _snowmanxx = _snowman.f() && _snowman != 32 ? _snowman.a(_snowmanx) : _snowman.b(_snowman);
         boolean _snowmanxxx = _snowman.b();
         float _snowmanxxxx = this.h;
         od _snowmanxxxxx = _snowman.a();
         float _snowmanxxxxxx;
         float _snowmanxxxxxxx;
         float _snowmanxxxxxxxx;
         if (_snowmanxxxxx != null) {
            int _snowmanxxxxxxxxx = _snowmanxxxxx.a();
            _snowmanxxxxxx = (float)(_snowmanxxxxxxxxx >> 16 & 0xFF) / 255.0F * this.d;
            _snowmanxxxxxxx = (float)(_snowmanxxxxxxxxx >> 8 & 0xFF) / 255.0F * this.d;
            _snowmanxxxxxxxx = (float)(_snowmanxxxxxxxxx & 0xFF) / 255.0F * this.d;
         } else {
            _snowmanxxxxxx = this.e;
            _snowmanxxxxxxx = this.f;
            _snowmanxxxxxxxx = this.g;
         }

         if (!(_snowmanxx instanceof dna)) {
            float _snowmanxxxxxxxxx = _snowmanxxx ? _snowmanx.b() : 0.0F;
            float _snowmanxxxxxxxxxx = this.c ? _snowmanx.c() : 0.0F;
            dfq _snowmanxxxxxxxxxxx = this.a.getBuffer(_snowmanxx.a(this.j));
            dku.this.a(
               _snowmanxx, _snowmanxxx, _snowman.c(), _snowmanxxxxxxxxx, this.l + _snowmanxxxxxxxxxx, this.m + _snowmanxxxxxxxxxx, this.i, _snowmanxxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxx, this.k
            );
         }

         float _snowmanxxxxxxxxx = _snowmanx.a(_snowmanxxx);
         float _snowmanxxxxxxxxxx = this.c ? 1.0F : 0.0F;
         if (_snowman.d()) {
            this.a(
               new dmz.a(
                  this.l + _snowmanxxxxxxxxxx - 1.0F,
                  this.m + _snowmanxxxxxxxxxx + 4.5F,
                  this.l + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx,
                  this.m + _snowmanxxxxxxxxxx + 4.5F - 1.0F,
                  0.01F,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxx,
                  _snowmanxxxxxxxx,
                  _snowmanxxxx
               )
            );
         }

         if (_snowman.e()) {
            this.a(
               new dmz.a(
                  this.l + _snowmanxxxxxxxxxx - 1.0F,
                  this.m + _snowmanxxxxxxxxxx + 9.0F,
                  this.l + _snowmanxxxxxxxxxx + _snowmanxxxxxxxxx,
                  this.m + _snowmanxxxxxxxxxx + 9.0F - 1.0F,
                  0.01F,
                  _snowmanxxxxxx,
                  _snowmanxxxxxxx,
                  _snowmanxxxxxxxx,
                  _snowmanxxxx
               )
            );
         }

         this.l += _snowmanxxxxxxxxx;
         return true;
      }

      public float a(int var1, float var2) {
         if (_snowman != 0) {
            float _snowman = (float)(_snowman >> 24 & 0xFF) / 255.0F;
            float _snowmanx = (float)(_snowman >> 16 & 0xFF) / 255.0F;
            float _snowmanxx = (float)(_snowman >> 8 & 0xFF) / 255.0F;
            float _snowmanxxx = (float)(_snowman & 0xFF) / 255.0F;
            this.a(new dmz.a(_snowman - 1.0F, this.m + 9.0F, this.l + 1.0F, this.m - 1.0F, 0.01F, _snowmanx, _snowmanxx, _snowmanxxx, _snowman));
         }

         if (this.n != null) {
            dmz _snowman = dku.this.a(ob.b).a();
            dfq _snowmanx = this.a.getBuffer(_snowman.a(this.j));

            for (dmz.a _snowmanxx : this.n) {
               _snowman.a(_snowmanxx, this.i, _snowmanx, this.k);
            }
         }

         return this.l;
      }
   }
}
