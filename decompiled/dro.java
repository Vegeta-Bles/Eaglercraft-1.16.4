import com.google.common.collect.Lists;
import com.mojang.blaze3d.systems.RenderSystem;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;

public class dro extends dkw implements dmf, dmi {
   private static final vk a = new vk("textures/gui/recipe_book.png");
   private final List<dro.a> b = Lists.newArrayList();
   private boolean c;
   private int d;
   private int e;
   private djz i;
   private drt j;
   private boq<?> k;
   private float l;
   private boolean m;

   public dro() {
   }

   public void a(djz var1, drt var2, int var3, int var4, int var5, int var6, float var7) {
      this.i = _snowman;
      this.j = _snowman;
      if (_snowman.s.bp instanceof bid) {
         this.m = true;
      }

      boolean _snowman = _snowman.s.F().a((bjj<?>)_snowman.s.bp);
      List<boq<?>> _snowmanx = _snowman.b(true);
      List<boq<?>> _snowmanxx = _snowman ? Collections.emptyList() : _snowman.b(false);
      int _snowmanxxx = _snowmanx.size();
      int _snowmanxxxx = _snowmanxxx + _snowmanxx.size();
      int _snowmanxxxxx = _snowmanxxxx <= 16 ? 4 : 5;
      int _snowmanxxxxxx = (int)Math.ceil((double)((float)_snowmanxxxx / (float)_snowmanxxxxx));
      this.d = _snowman;
      this.e = _snowman;
      int _snowmanxxxxxxx = 25;
      float _snowmanxxxxxxxx = (float)(this.d + Math.min(_snowmanxxxx, _snowmanxxxxx) * 25);
      float _snowmanxxxxxxxxx = (float)(_snowman + 50);
      if (_snowmanxxxxxxxx > _snowmanxxxxxxxxx) {
         this.d = (int)((float)this.d - _snowman * (float)((int)((_snowmanxxxxxxxx - _snowmanxxxxxxxxx) / _snowman)));
      }

      float _snowmanxxxxxxxxxx = (float)(this.e + _snowmanxxxxxx * 25);
      float _snowmanxxxxxxxxxxx = (float)(_snowman + 50);
      if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxx) {
         this.e = (int)((float)this.e - _snowman * (float)afm.f((_snowmanxxxxxxxxxx - _snowmanxxxxxxxxxxx) / _snowman));
      }

      float _snowmanxxxxxxxxxxxx = (float)this.e;
      float _snowmanxxxxxxxxxxxxx = (float)(_snowman - 100);
      if (_snowmanxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxx) {
         this.e = (int)((float)this.e - _snowman * (float)afm.f((_snowmanxxxxxxxxxxxx - _snowmanxxxxxxxxxxxxx) / _snowman));
      }

      this.c = true;
      this.b.clear();

      for (int _snowmanxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxx < _snowmanxxxx; _snowmanxxxxxxxxxxxxxx++) {
         boolean _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxx < _snowmanxxx;
         boq<?> _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx ? _snowmanx.get(_snowmanxxxxxxxxxxxxxx) : _snowmanxx.get(_snowmanxxxxxxxxxxxxxx - _snowmanxxx);
         int _snowmanxxxxxxxxxxxxxxxxx = this.d + 4 + 25 * (_snowmanxxxxxxxxxxxxxx % _snowmanxxxxx);
         int _snowmanxxxxxxxxxxxxxxxxxx = this.e + 5 + 25 * (_snowmanxxxxxxxxxxxxxx / _snowmanxxxxx);
         if (this.m) {
            this.b.add(new dro.b(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         } else {
            this.b.add(new dro.a(_snowmanxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx));
         }
      }

      this.k = null;
   }

   @Override
   public boolean c_(boolean var1) {
      return false;
   }

   public drt a() {
      return this.j;
   }

   public boq<?> b() {
      return this.k;
   }

   @Override
   public boolean a(double var1, double var3, int var5) {
      if (_snowman != 0) {
         return false;
      } else {
         for (dro.a _snowman : this.b) {
            if (_snowman.a(_snowman, _snowman, _snowman)) {
               this.k = _snowman.c;
               return true;
            }
         }

         return false;
      }
   }

   @Override
   public boolean b(double var1, double var3) {
      return false;
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      if (this.c) {
         this.l += _snowman;
         RenderSystem.enableBlend();
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         this.i.M().a(a);
         RenderSystem.pushMatrix();
         RenderSystem.translatef(0.0F, 0.0F, 170.0F);
         int _snowman = this.b.size() <= 16 ? 4 : 5;
         int _snowmanx = Math.min(this.b.size(), _snowman);
         int _snowmanxx = afm.f((float)this.b.size() / (float)_snowman);
         int _snowmanxxx = 24;
         int _snowmanxxxx = 4;
         int _snowmanxxxxx = 82;
         int _snowmanxxxxxx = 208;
         this.c(_snowman, _snowmanx, _snowmanxx, 24, 4, 82, 208);
         RenderSystem.disableBlend();

         for (dro.a _snowmanxxxxxxx : this.b) {
            _snowmanxxxxxxx.a(_snowman, _snowman, _snowman, _snowman);
         }

         RenderSystem.popMatrix();
      }
   }

   private void c(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7) {
      this.b(_snowman, this.d, this.e, _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman, this.d + _snowman * 2 + _snowman * _snowman, this.e, _snowman + _snowman + _snowman, _snowman, _snowman, _snowman);
      this.b(_snowman, this.d, this.e + _snowman * 2 + _snowman * _snowman, _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
      this.b(_snowman, this.d + _snowman * 2 + _snowman * _snowman, this.e + _snowman * 2 + _snowman * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);

      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         this.b(_snowman, this.d + _snowman + _snowman * _snowman, this.e, _snowman + _snowman, _snowman, _snowman, _snowman);
         this.b(_snowman, this.d + _snowman + (_snowman + 1) * _snowman, this.e, _snowman + _snowman, _snowman, _snowman, _snowman);

         for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
            if (_snowman == 0) {
               this.b(_snowman, this.d, this.e + _snowman + _snowmanx * _snowman, _snowman, _snowman + _snowman, _snowman, _snowman);
               this.b(_snowman, this.d, this.e + _snowman + (_snowmanx + 1) * _snowman, _snowman, _snowman + _snowman, _snowman, _snowman);
            }

            this.b(_snowman, this.d + _snowman + _snowman * _snowman, this.e + _snowman + _snowmanx * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.b(_snowman, this.d + _snowman + (_snowman + 1) * _snowman, this.e + _snowman + _snowmanx * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.b(_snowman, this.d + _snowman + _snowman * _snowman, this.e + _snowman + (_snowmanx + 1) * _snowman, _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            this.b(_snowman, this.d + _snowman + (_snowman + 1) * _snowman - 1, this.e + _snowman + (_snowmanx + 1) * _snowman - 1, _snowman + _snowman, _snowman + _snowman, _snowman + 1, _snowman + 1);
            if (_snowman == _snowman - 1) {
               this.b(_snowman, this.d + _snowman * 2 + _snowman * _snowman, this.e + _snowman + _snowmanx * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
               this.b(_snowman, this.d + _snowman * 2 + _snowman * _snowman, this.e + _snowman + (_snowmanx + 1) * _snowman, _snowman + _snowman + _snowman, _snowman + _snowman, _snowman, _snowman);
            }
         }

         this.b(_snowman, this.d + _snowman + _snowman * _snowman, this.e + _snowman * 2 + _snowman * _snowman, _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
         this.b(_snowman, this.d + _snowman + (_snowman + 1) * _snowman, this.e + _snowman * 2 + _snowman * _snowman, _snowman + _snowman, _snowman + _snowman + _snowman, _snowman, _snowman);
      }
   }

   public void a(boolean var1) {
      this.c = _snowman;
   }

   public boolean c() {
      return this.c;
   }

   class a extends dlh implements uz<bon> {
      private final boq<?> c;
      private final boolean d;
      protected final List<dro.a.a> a = Lists.newArrayList();

      public a(int var2, int var3, boq<?> var4, boolean var5) {
         super(_snowman, _snowman, 200, 20, oe.d);
         this.j = 24;
         this.k = 24;
         this.c = _snowman;
         this.d = _snowman;
         this.a(_snowman);
      }

      protected void a(boq<?> var1) {
         this.a(3, 3, -1, _snowman, _snowman.a().iterator(), 0);
      }

      @Override
      public void a(Iterator<bon> var1, int var2, int var3, int var4, int var5) {
         bmb[] _snowman = _snowman.next().a();
         if (_snowman.length != 0) {
            this.a.add(new dro.a.a(3 + _snowman * 7, 3 + _snowman * 7, _snowman));
         }
      }

      @Override
      public void b(dfm var1, int var2, int var3, float var4) {
         RenderSystem.enableAlphaTest();
         dro.this.i.M().a(dro.a);
         int _snowman = 152;
         if (!this.d) {
            _snowman += 26;
         }

         int _snowmanx = dro.this.m ? 130 : 78;
         if (this.g()) {
            _snowmanx += 26;
         }

         this.b(_snowman, this.l, this.m, _snowman, _snowmanx, this.j, this.k);

         for (dro.a.a _snowmanxx : this.a) {
            RenderSystem.pushMatrix();
            float _snowmanxxx = 0.42F;
            int _snowmanxxxx = (int)((float)(this.l + _snowmanxx.b) / 0.42F - 3.0F);
            int _snowmanxxxxx = (int)((float)(this.m + _snowmanxx.c) / 0.42F - 3.0F);
            RenderSystem.scalef(0.42F, 0.42F, 1.0F);
            dro.this.i.ad().b(_snowmanxx.a[afm.d(dro.this.l / 30.0F) % _snowmanxx.a.length], _snowmanxxxx, _snowmanxxxxx);
            RenderSystem.popMatrix();
         }

         RenderSystem.disableAlphaTest();
      }

      public class a {
         public final bmb[] a;
         public final int b;
         public final int c;

         public a(int var2, int var3, bmb[] var4) {
            this.b = _snowman;
            this.c = _snowman;
            this.a = _snowman;
         }
      }
   }

   class b extends dro.a {
      public b(int var2, int var3, boq<?> var4, boolean var5) {
         super(_snowman, _snowman, _snowman, _snowman);
      }

      @Override
      protected void a(boq<?> var1) {
         bmb[] _snowman = _snowman.a().get(0).a();
         this.a.add(new dro.a.a(10, 10, _snowman));
      }
   }
}
