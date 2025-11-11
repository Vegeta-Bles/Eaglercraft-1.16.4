import com.google.common.collect.ImmutableList;

public class duz extends dur<bam> {
   private final dwn a;
   private final dwn b;
   private final dwn f;
   private final dwn g;
   private final dwn h;
   private final dwn i;
   private final dwn j;
   private final dwn k;
   private final dwn l;
   private final dwn m;
   private final dwn n;

   public duz() {
      this.r = 32;
      this.s = 32;
      this.a = new dwn(this, 2, 8);
      this.a.a(-1.5F, 0.0F, -1.5F, 3.0F, 6.0F, 3.0F);
      this.a.a(0.0F, 16.5F, -3.0F);
      this.b = new dwn(this, 22, 1);
      this.b.a(-1.5F, -1.0F, -1.0F, 3.0F, 4.0F, 1.0F);
      this.b.a(0.0F, 21.07F, 1.16F);
      this.f = new dwn(this, 19, 8);
      this.f.a(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F);
      this.f.a(1.5F, 16.94F, -2.76F);
      this.g = new dwn(this, 19, 8);
      this.g.a(-0.5F, 0.0F, -1.5F, 1.0F, 5.0F, 3.0F);
      this.g.a(-1.5F, 16.94F, -2.76F);
      this.h = new dwn(this, 2, 2);
      this.h.a(-1.0F, -1.5F, -1.0F, 2.0F, 3.0F, 2.0F);
      this.h.a(0.0F, 15.69F, -2.76F);
      this.i = new dwn(this, 10, 0);
      this.i.a(-1.0F, -0.5F, -2.0F, 2.0F, 1.0F, 4.0F);
      this.i.a(0.0F, -2.0F, -1.0F);
      this.h.b(this.i);
      this.j = new dwn(this, 11, 7);
      this.j.a(-0.5F, -1.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      this.j.a(0.0F, -0.5F, -1.5F);
      this.h.b(this.j);
      this.k = new dwn(this, 16, 7);
      this.k.a(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      this.k.a(0.0F, -1.75F, -2.45F);
      this.h.b(this.k);
      this.l = new dwn(this, 2, 18);
      this.l.a(0.0F, -4.0F, -2.0F, 0.0F, 5.0F, 4.0F);
      this.l.a(0.0F, -2.15F, 0.15F);
      this.h.b(this.l);
      this.m = new dwn(this, 14, 18);
      this.m.a(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      this.m.a(1.0F, 22.0F, -1.05F);
      this.n = new dwn(this, 14, 18);
      this.n.a(-0.5F, 0.0F, -0.5F, 1.0F, 2.0F, 1.0F);
      this.n.a(-1.0F, 22.0F, -1.05F);
   }

   @Override
   public Iterable<dwn> a() {
      return ImmutableList.of(this.a, this.f, this.g, this.b, this.h, this.m, this.n);
   }

   public void a(bam var1, float var2, float var3, float var4, float var5, float var6) {
      this.a(a(_snowman), _snowman.K, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void a(bam var1, float var2, float var3, float var4) {
      this.a(a(_snowman));
   }

   public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8, int var9) {
      this.a(duz.a.e);
      this.a(duz.a.e, _snowman, _snowman, _snowman, 0.0F, _snowman, _snowman);
      this.a().forEach(var4x -> var4x.a(_snowman, _snowman, _snowman, _snowman));
   }

   private void a(duz.a var1, int var2, float var3, float var4, float var5, float var6, float var7) {
      this.h.d = _snowman * (float) (Math.PI / 180.0);
      this.h.e = _snowman * (float) (Math.PI / 180.0);
      this.h.f = 0.0F;
      this.h.a = 0.0F;
      this.a.a = 0.0F;
      this.b.a = 0.0F;
      this.g.a = -1.5F;
      this.f.a = 1.5F;
      switch (_snowman) {
         case c:
            break;
         case d: {
            float _snowman = afm.b((float)_snowman);
            float _snowmanx = afm.a((float)_snowman);
            this.h.a = _snowman;
            this.h.b = 15.69F + _snowmanx;
            this.h.d = 0.0F;
            this.h.e = 0.0F;
            this.h.f = afm.a((float)_snowman) * 0.4F;
            this.a.a = _snowman;
            this.a.b = 16.5F + _snowmanx;
            this.f.f = -0.0873F - _snowman;
            this.f.a = 1.5F + _snowman;
            this.f.b = 16.94F + _snowmanx;
            this.g.f = 0.0873F + _snowman;
            this.g.a = -1.5F + _snowman;
            this.g.b = 16.94F + _snowmanx;
            this.b.a = _snowman;
            this.b.b = 21.07F + _snowmanx;
            break;
         }
         case b:
            this.m.d = this.m.d + afm.b(_snowman * 0.6662F) * 1.4F * _snowman;
            this.n.d = this.n.d + afm.b(_snowman * 0.6662F + (float) Math.PI) * 1.4F * _snowman;
         case a:
         case e:
         default: {
            float _snowman = _snowman * 0.3F;
            this.h.b = 15.69F + _snowman;
            this.b.d = 1.015F + afm.b(_snowman * 0.6662F) * 0.3F * _snowman;
            this.b.b = 21.07F + _snowman;
            this.a.b = 16.5F + _snowman;
            this.f.f = -0.0873F - _snowman;
            this.f.b = 16.94F + _snowman;
            this.g.f = 0.0873F + _snowman;
            this.g.b = 16.94F + _snowman;
            this.m.b = 22.0F + _snowman;
            this.n.b = 22.0F + _snowman;
         }
      }
   }

   private void a(duz.a var1) {
      this.l.d = -0.2214F;
      this.a.d = 0.4937F;
      this.f.d = -0.6981F;
      this.f.e = (float) -Math.PI;
      this.g.d = -0.6981F;
      this.g.e = (float) -Math.PI;
      this.m.d = -0.0299F;
      this.n.d = -0.0299F;
      this.m.b = 22.0F;
      this.n.b = 22.0F;
      this.m.f = 0.0F;
      this.n.f = 0.0F;
      switch (_snowman) {
         case c:
            float _snowman = 1.9F;
            this.h.b = 17.59F;
            this.b.d = 1.5388988F;
            this.b.b = 22.97F;
            this.a.b = 18.4F;
            this.f.f = -0.0873F;
            this.f.b = 18.84F;
            this.g.f = 0.0873F;
            this.g.b = 18.84F;
            this.m.b++;
            this.n.b++;
            this.m.d++;
            this.n.d++;
            break;
         case d:
            this.m.f = (float) (-Math.PI / 9);
            this.n.f = (float) (Math.PI / 9);
         case b:
         case e:
         default:
            break;
         case a:
            this.m.d += (float) (Math.PI * 2.0 / 9.0);
            this.n.d += (float) (Math.PI * 2.0 / 9.0);
      }
   }

   private static duz.a a(bam var0) {
      if (_snowman.eV()) {
         return duz.a.d;
      } else if (_snowman.eM()) {
         return duz.a.c;
      } else {
         return _snowman.fa() ? duz.a.a : duz.a.b;
      }
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e;

      private a() {
      }
   }
}
