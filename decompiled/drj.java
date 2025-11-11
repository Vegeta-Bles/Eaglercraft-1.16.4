import com.mojang.blaze3d.systems.RenderSystem;

public class drj extends dlv<drj.a> {
   private static final vk a = new vk("textures/gui/resource_packs.png");
   private static final nr o = new of("pack.incompatible");
   private static final nr p = new of("pack.incompatible.confirm.title");
   private final nr q;

   public drj(djz var1, int var2, int var3, nr var4) {
      super(_snowman, _snowman, _snowman, 32, _snowman - 55 + 4, 36);
      this.q = _snowman;
      this.m = false;
      this.a(true, (int)(9.0F * 1.5F));
   }

   @Override
   protected void a(dfm var1, int var2, int var3, dfo var4) {
      nr _snowman = new oe("").a(this.q).a(k.t, k.r);
      this.b.g.b(_snowman, _snowman, (float)(_snowman + this.d / 2 - this.b.g.a(_snowman) / 2), (float)Math.min(this.i + 3, _snowman), 16777215);
   }

   @Override
   public int d() {
      return this.d;
   }

   @Override
   protected int e() {
      return this.k - 6;
   }

   public static class a extends dlv.a<drj.a> {
      private drj c;
      protected final djz a;
      protected final dot b;
      private final drh.a d;
      private final afa e;
      private final dlu f;
      private final afa g;
      private final dlu h;

      public a(djz var1, drj var2, dot var3, drh.a var4) {
         this.a = _snowman;
         this.b = _snowman;
         this.d = _snowman;
         this.c = _snowman;
         this.e = a(_snowman, _snowman.c());
         this.f = b(_snowman, _snowman.f());
         this.g = a(_snowman, drj.o);
         this.h = b(_snowman, _snowman.b().b());
      }

      private static afa a(djz var0, nr var1) {
         int _snowman = _snowman.g.a(_snowman);
         if (_snowman > 157) {
            nu _snowmanx = nu.a(_snowman.g.a(_snowman, 157 - _snowman.g.b("...")), nu.b("..."));
            return ly.a().a(_snowmanx);
         } else {
            return _snowman.f();
         }
      }

      private static dlu b(djz var0, nr var1) {
         return dlu.a(_snowman.g, _snowman, 157, 2);
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         abv _snowman = this.d.b();
         if (!_snowman.a()) {
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            dkw.a(_snowman, _snowman - 1, _snowman - 1, _snowman + _snowman - 9, _snowman + _snowman + 1, -8978432);
         }

         this.a.M().a(this.d.a());
         RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
         dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 32, 32);
         afa _snowmanx = this.e;
         dlu _snowmanxx = this.f;
         if (this.a() && (this.a.k.Y || _snowman)) {
            this.a.M().a(drj.a);
            dkw.a(_snowman, _snowman, _snowman, _snowman + 32, _snowman + 32, -1601138544);
            RenderSystem.color4f(1.0F, 1.0F, 1.0F, 1.0F);
            int _snowmanxxx = _snowman - _snowman;
            int _snowmanxxxx = _snowman - _snowman;
            if (!this.d.b().a()) {
               _snowmanx = this.g;
               _snowmanxx = this.h;
            }

            if (this.d.n()) {
               if (_snowmanxxx < 32) {
                  dkw.a(_snowman, _snowman, _snowman, 0.0F, 32.0F, 32, 32, 256, 256);
               } else {
                  dkw.a(_snowman, _snowman, _snowman, 0.0F, 0.0F, 32, 32, 256, 256);
               }
            } else {
               if (this.d.o()) {
                  if (_snowmanxxx < 16) {
                     dkw.a(_snowman, _snowman, _snowman, 32.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     dkw.a(_snowman, _snowman, _snowman, 32.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.d.p()) {
                  if (_snowmanxxx < 32 && _snowmanxxx > 16 && _snowmanxxxx < 16) {
                     dkw.a(_snowman, _snowman, _snowman, 96.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     dkw.a(_snowman, _snowman, _snowman, 96.0F, 0.0F, 32, 32, 256, 256);
                  }
               }

               if (this.d.q()) {
                  if (_snowmanxxx < 32 && _snowmanxxx > 16 && _snowmanxxxx > 16) {
                     dkw.a(_snowman, _snowman, _snowman, 64.0F, 32.0F, 32, 32, 256, 256);
                  } else {
                     dkw.a(_snowman, _snowman, _snowman, 64.0F, 0.0F, 32, 32, 256, 256);
                  }
               }
            }
         }

         this.a.g.a(_snowman, _snowmanx, (float)(_snowman + 32 + 2), (float)(_snowman + 1), 16777215);
         _snowmanxx.b(_snowman, _snowman + 32 + 2, _snowman + 12, 10, 8421504);
      }

      private boolean a() {
         return !this.d.g() || !this.d.h();
      }

      @Override
      public boolean a(double var1, double var3, int var5) {
         double _snowman = _snowman - (double)this.c.q();
         double _snowmanx = _snowman - (double)this.c.h(this.c.au_().indexOf(this));
         if (this.a() && _snowman <= 32.0) {
            if (this.d.n()) {
               abv _snowmanxx = this.d.b();
               if (_snowmanxx.a()) {
                  this.d.i();
               } else {
                  nr _snowmanxxx = _snowmanxx.c();
                  this.a.a(new dns(var1x -> {
                     this.a.a(this.b);
                     if (var1x) {
                        this.d.i();
                     }
                  }, drj.p, _snowmanxxx));
               }

               return true;
            }

            if (_snowman < 16.0 && this.d.o()) {
               this.d.j();
               return true;
            }

            if (_snowman > 16.0 && _snowmanx < 16.0 && this.d.p()) {
               this.d.k();
               return true;
            }

            if (_snowman > 16.0 && _snowmanx > 16.0 && this.d.q()) {
               this.d.l();
               return true;
            }
         }

         return false;
      }
   }
}
