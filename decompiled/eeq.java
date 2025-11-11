import java.util.Random;
import javax.annotation.Nullable;

public class eeq extends eeu<bbr> {
   public static final vk a = new vk("textures/entity/end_crystal/end_crystal_beam.png");
   private static final vk e = new vk("textures/entity/enderdragon/dragon_exploding.png");
   private static final vk f = new vk("textures/entity/enderdragon/dragon.png");
   private static final vk g = new vk("textures/entity/enderdragon/dragon_eyes.png");
   private static final eao h = eao.d(f);
   private static final eao i = eao.j(f);
   private static final eao j = eao.m(g);
   private static final eao k = eao.i(a);
   private static final float l = (float)(Math.sqrt(3.0) / 2.0);
   private final eeq.a m = new eeq.a();

   public eeq(eet var1) {
      super(_snowman);
      this.c = 0.5F;
   }

   public void a(bbr var1, float var2, float var3, dfm var4, eag var5, int var6) {
      _snowman.a();
      float _snowman = (float)_snowman.a(7, _snowman)[0];
      float _snowmanx = (float)(_snowman.a(5, _snowman)[1] - _snowman.a(10, _snowman)[1]);
      _snowman.a(g.d.a(-_snowman));
      _snowman.a(g.b.a(_snowmanx * 10.0F));
      _snowman.a(0.0, 0.0, 1.0);
      _snowman.a(-1.0F, -1.0F, 1.0F);
      _snowman.a(0.0, -1.501F, 0.0);
      boolean _snowmanxx = _snowman.an > 0;
      this.m.a(_snowman, 0.0F, 0.0F, _snowman);
      if (_snowman.bs > 0) {
         float _snowmanxxx = (float)_snowman.bs / 200.0F;
         dfq _snowmanxxxx = _snowman.getBuffer(eao.a(e, _snowmanxxx));
         this.m.a(_snowman, _snowmanxxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
         dfq _snowmanxxxxx = _snowman.getBuffer(i);
         this.m.a(_snowman, _snowmanxxxxx, _snowman, ejw.a(0.0F, _snowmanxx), 1.0F, 1.0F, 1.0F, 1.0F);
      } else {
         dfq _snowmanxxx = _snowman.getBuffer(h);
         this.m.a(_snowman, _snowmanxxx, _snowman, ejw.a(0.0F, _snowmanxx), 1.0F, 1.0F, 1.0F, 1.0F);
      }

      dfq _snowmanxxx = _snowman.getBuffer(j);
      this.m.a(_snowman, _snowmanxxx, _snowman, ejw.a, 1.0F, 1.0F, 1.0F, 1.0F);
      if (_snowman.bs > 0) {
         float _snowmanxxxx = ((float)_snowman.bs + _snowman) / 200.0F;
         float _snowmanxxxxx = Math.min(_snowmanxxxx > 0.8F ? (_snowmanxxxx - 0.8F) / 0.2F : 0.0F, 1.0F);
         Random _snowmanxxxxxx = new Random(432L);
         dfq _snowmanxxxxxxx = _snowman.getBuffer(eao.r());
         _snowman.a();
         _snowman.a(0.0, -1.0, -2.0);

         for (int _snowmanxxxxxxxx = 0; (float)_snowmanxxxxxxxx < (_snowmanxxxx + _snowmanxxxx * _snowmanxxxx) / 2.0F * 60.0F; _snowmanxxxxxxxx++) {
            _snowman.a(g.b.a(_snowmanxxxxxx.nextFloat() * 360.0F));
            _snowman.a(g.d.a(_snowmanxxxxxx.nextFloat() * 360.0F));
            _snowman.a(g.f.a(_snowmanxxxxxx.nextFloat() * 360.0F));
            _snowman.a(g.b.a(_snowmanxxxxxx.nextFloat() * 360.0F));
            _snowman.a(g.d.a(_snowmanxxxxxx.nextFloat() * 360.0F));
            _snowman.a(g.f.a(_snowmanxxxxxx.nextFloat() * 360.0F + _snowmanxxxx * 90.0F));
            float _snowmanxxxxxxxxx = _snowmanxxxxxx.nextFloat() * 20.0F + 5.0F + _snowmanxxxxx * 10.0F;
            float _snowmanxxxxxxxxxx = _snowmanxxxxxx.nextFloat() * 2.0F + 1.0F + _snowmanxxxxx * 2.0F;
            b _snowmanxxxxxxxxxxx = _snowman.c().a();
            int _snowmanxxxxxxxxxxxx = (int)(255.0F * (1.0F - _snowmanxxxxx));
            a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            b(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            b(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            c(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
            c(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
            a(_snowmanxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx);
         }

         _snowman.b();
      }

      _snowman.b();
      if (_snowman.bu != null) {
         _snowman.a();
         float _snowmanxxxx = (float)(_snowman.bu.cD() - afm.d((double)_snowman, _snowman.m, _snowman.cD()));
         float _snowmanxxxxx = (float)(_snowman.bu.cE() - afm.d((double)_snowman, _snowman.n, _snowman.cE()));
         float _snowmanxxxxxx = (float)(_snowman.bu.cH() - afm.d((double)_snowman, _snowman.o, _snowman.cH()));
         a(_snowmanxxxx, _snowmanxxxxx + eep.a(_snowman.bu, _snowman), _snowmanxxxxxx, _snowman, _snowman.K, _snowman, _snowman, _snowman);
         _snowman.b();
      }

      super.a(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   private static void a(dfq var0, b var1, int var2) {
      _snowman.a(_snowman, 0.0F, 0.0F, 0.0F).a(255, 255, 255, _snowman).d();
      _snowman.a(_snowman, 0.0F, 0.0F, 0.0F).a(255, 255, 255, _snowman).d();
   }

   private static void a(dfq var0, b var1, float var2, float var3) {
      _snowman.a(_snowman, -l * _snowman, _snowman, -0.5F * _snowman).a(255, 0, 255, 0).d();
   }

   private static void b(dfq var0, b var1, float var2, float var3) {
      _snowman.a(_snowman, l * _snowman, _snowman, -0.5F * _snowman).a(255, 0, 255, 0).d();
   }

   private static void c(dfq var0, b var1, float var2, float var3) {
      _snowman.a(_snowman, 0.0F, _snowman, 1.0F * _snowman).a(255, 0, 255, 0).d();
   }

   public static void a(float var0, float var1, float var2, float var3, int var4, dfm var5, eag var6, int var7) {
      float _snowman = afm.c(_snowman * _snowman + _snowman * _snowman);
      float _snowmanx = afm.c(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman);
      _snowman.a();
      _snowman.a(0.0, 2.0, 0.0);
      _snowman.a(g.d.c((float)(-Math.atan2((double)_snowman, (double)_snowman)) - (float) (Math.PI / 2)));
      _snowman.a(g.b.c((float)(-Math.atan2((double)_snowman, (double)_snowman)) - (float) (Math.PI / 2)));
      dfq _snowmanxx = _snowman.getBuffer(k);
      float _snowmanxxx = 0.0F - ((float)_snowman + _snowman) * 0.01F;
      float _snowmanxxxx = afm.c(_snowman * _snowman + _snowman * _snowman + _snowman * _snowman) / 32.0F - ((float)_snowman + _snowman) * 0.01F;
      int _snowmanxxxxx = 8;
      float _snowmanxxxxxx = 0.0F;
      float _snowmanxxxxxxx = 0.75F;
      float _snowmanxxxxxxxx = 0.0F;
      dfm.a _snowmanxxxxxxxxx = _snowman.c();
      b _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.a();
      a _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx.b();

      for (int _snowmanxxxxxxxxxxxx = 1; _snowmanxxxxxxxxxxxx <= 8; _snowmanxxxxxxxxxxxx++) {
         float _snowmanxxxxxxxxxxxxx = afm.a((float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float _snowmanxxxxxxxxxxxxxx = afm.b((float)_snowmanxxxxxxxxxxxx * (float) (Math.PI * 2) / 8.0F) * 0.75F;
         float _snowmanxxxxxxxxxxxxxxx = (float)_snowmanxxxxxxxxxxxx / 8.0F;
         _snowmanxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxx * 0.2F, _snowmanxxxxxxx * 0.2F, 0.0F).a(0, 0, 0, 255).a(_snowmanxxxxxxxx, _snowmanxxx).b(ejw.a).a(_snowman).a(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanx).a(255, 255, 255, 255).a(_snowmanxxxxxxxx, _snowmanxxxx).b(ejw.a).a(_snowman).a(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F).d();
         _snowmanxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanx)
            .a(255, 255, 255, 255)
            .a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxx)
            .b(ejw.a)
            .a(_snowman)
            .a(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .d();
         _snowmanxx.a(_snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxxxx * 0.2F, _snowmanxxxxxxxxxxxxxx * 0.2F, 0.0F)
            .a(0, 0, 0, 255)
            .a(_snowmanxxxxxxxxxxxxxxx, _snowmanxxx)
            .b(ejw.a)
            .a(_snowman)
            .a(_snowmanxxxxxxxxxxx, 0.0F, -1.0F, 0.0F)
            .d();
         _snowmanxxxxxx = _snowmanxxxxxxxxxxxxx;
         _snowmanxxxxxxx = _snowmanxxxxxxxxxxxxxx;
         _snowmanxxxxxxxx = _snowmanxxxxxxxxxxxxxxx;
      }

      _snowman.b();
   }

   public vk a(bbr var1) {
      return f;
   }

   public static class a extends duc<bbr> {
      private final dwn a;
      private final dwn b;
      private final dwn f;
      private final dwn g;
      private dwn h;
      private dwn i;
      private dwn j;
      private dwn k;
      private dwn l;
      private dwn m;
      private dwn n;
      private dwn o;
      private dwn p;
      private dwn t;
      private dwn u;
      private dwn v;
      private dwn w;
      private dwn x;
      private dwn y;
      private dwn z;
      @Nullable
      private bbr A;
      private float B;

      public a() {
         this.r = 256;
         this.s = 256;
         float _snowman = -16.0F;
         this.a = new dwn(this);
         this.a.a("upperlip", -6.0F, -1.0F, -24.0F, 12, 5, 16, 0.0F, 176, 44);
         this.a.a("upperhead", -8.0F, -8.0F, -10.0F, 16, 16, 16, 0.0F, 112, 30);
         this.a.g = true;
         this.a.a("scale", -5.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.a.a("nostril", -5.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.a.g = false;
         this.a.a("scale", 3.0F, -12.0F, -4.0F, 2, 4, 6, 0.0F, 0, 0);
         this.a.a("nostril", 3.0F, -3.0F, -22.0F, 2, 2, 4, 0.0F, 112, 0);
         this.f = new dwn(this);
         this.f.a(0.0F, 4.0F, -8.0F);
         this.f.a("jaw", -6.0F, 0.0F, -16.0F, 12, 4, 16, 0.0F, 176, 65);
         this.a.b(this.f);
         this.b = new dwn(this);
         this.b.a("box", -5.0F, -5.0F, -5.0F, 10, 10, 10, 0.0F, 192, 104);
         this.b.a("scale", -1.0F, -9.0F, -3.0F, 2, 4, 6, 0.0F, 48, 0);
         this.g = new dwn(this);
         this.g.a(0.0F, 4.0F, 8.0F);
         this.g.a("body", -12.0F, 0.0F, -16.0F, 24, 24, 64, 0.0F, 0, 0);
         this.g.a("scale", -1.0F, -6.0F, -10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.g.a("scale", -1.0F, -6.0F, 10.0F, 2, 6, 12, 0.0F, 220, 53);
         this.g.a("scale", -1.0F, -6.0F, 30.0F, 2, 6, 12, 0.0F, 220, 53);
         this.h = new dwn(this);
         this.h.g = true;
         this.h.a(12.0F, 5.0F, 2.0F);
         this.h.a("bone", 0.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.h.a("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.i = new dwn(this);
         this.i.g = true;
         this.i.a(56.0F, 0.0F, 0.0F);
         this.i.a("bone", 0.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.i.a("skin", 0.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.h.b(this.i);
         this.j = new dwn(this);
         this.j.a(12.0F, 20.0F, 2.0F);
         this.j.a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.k = new dwn(this);
         this.k.a(0.0F, 20.0F, -1.0F);
         this.k.a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.j.b(this.k);
         this.l = new dwn(this);
         this.l.a(0.0F, 23.0F, 0.0F);
         this.l.a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.k.b(this.l);
         this.m = new dwn(this);
         this.m.a(16.0F, 16.0F, 42.0F);
         this.m.a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.n = new dwn(this);
         this.n.a(0.0F, 32.0F, -4.0F);
         this.n.a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.m.b(this.n);
         this.o = new dwn(this);
         this.o.a(0.0F, 31.0F, 4.0F);
         this.o.a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.n.b(this.o);
         this.p = new dwn(this);
         this.p.a(-12.0F, 5.0F, 2.0F);
         this.p.a("bone", -56.0F, -4.0F, -4.0F, 56, 8, 8, 0.0F, 112, 88);
         this.p.a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 88);
         this.t = new dwn(this);
         this.t.a(-56.0F, 0.0F, 0.0F);
         this.t.a("bone", -56.0F, -2.0F, -2.0F, 56, 4, 4, 0.0F, 112, 136);
         this.t.a("skin", -56.0F, 0.0F, 2.0F, 56, 0, 56, 0.0F, -56, 144);
         this.p.b(this.t);
         this.u = new dwn(this);
         this.u.a(-12.0F, 20.0F, 2.0F);
         this.u.a("main", -4.0F, -4.0F, -4.0F, 8, 24, 8, 0.0F, 112, 104);
         this.v = new dwn(this);
         this.v.a(0.0F, 20.0F, -1.0F);
         this.v.a("main", -3.0F, -1.0F, -3.0F, 6, 24, 6, 0.0F, 226, 138);
         this.u.b(this.v);
         this.w = new dwn(this);
         this.w.a(0.0F, 23.0F, 0.0F);
         this.w.a("main", -4.0F, 0.0F, -12.0F, 8, 4, 16, 0.0F, 144, 104);
         this.v.b(this.w);
         this.x = new dwn(this);
         this.x.a(-16.0F, 16.0F, 42.0F);
         this.x.a("main", -8.0F, -4.0F, -8.0F, 16, 32, 16, 0.0F, 0, 0);
         this.y = new dwn(this);
         this.y.a(0.0F, 32.0F, -4.0F);
         this.y.a("main", -6.0F, -2.0F, 0.0F, 12, 32, 12, 0.0F, 196, 0);
         this.x.b(this.y);
         this.z = new dwn(this);
         this.z.a(0.0F, 31.0F, 4.0F);
         this.z.a("main", -9.0F, 0.0F, -20.0F, 18, 6, 24, 0.0F, 112, 0);
         this.y.b(this.z);
      }

      public void a(bbr var1, float var2, float var3, float var4) {
         this.A = _snowman;
         this.B = _snowman;
      }

      public void a(bbr var1, float var2, float var3, float var4, float var5, float var6) {
      }

      @Override
      public void a(dfm var1, dfq var2, int var3, int var4, float var5, float var6, float var7, float var8) {
         _snowman.a();
         float _snowman = afm.g(this.B, this.A.bp, this.A.bq);
         this.f.d = (float)(Math.sin((double)(_snowman * (float) (Math.PI * 2))) + 1.0) * 0.2F;
         float _snowmanx = (float)(Math.sin((double)(_snowman * (float) (Math.PI * 2) - 1.0F)) + 1.0);
         _snowmanx = (_snowmanx * _snowmanx + _snowmanx * 2.0F) * 0.05F;
         _snowman.a(0.0, (double)(_snowmanx - 2.0F), -3.0);
         _snowman.a(g.b.a(_snowmanx * 2.0F));
         float _snowmanxx = 0.0F;
         float _snowmanxxx = 20.0F;
         float _snowmanxxxx = -12.0F;
         float _snowmanxxxxx = 1.5F;
         double[] _snowmanxxxxxx = this.A.a(6, this.B);
         float _snowmanxxxxxxx = afm.l(this.A.a(5, this.B)[0] - this.A.a(10, this.B)[0]);
         float _snowmanxxxxxxxx = afm.l(this.A.a(5, this.B)[0] + (double)(_snowmanxxxxxxx / 2.0F));
         float _snowmanxxxxxxxxx = _snowman * (float) (Math.PI * 2);

         for (int _snowmanxxxxxxxxxx = 0; _snowmanxxxxxxxxxx < 5; _snowmanxxxxxxxxxx++) {
            double[] _snowmanxxxxxxxxxxx = this.A.a(5 - _snowmanxxxxxxxxxx, this.B);
            float _snowmanxxxxxxxxxxxx = (float)Math.cos((double)((float)_snowmanxxxxxxxxxx * 0.45F + _snowmanxxxxxxxxx)) * 0.15F;
            this.b.e = afm.l(_snowmanxxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.d = _snowmanxxxxxxxxxxxx + this.A.a(_snowmanxxxxxxxxxx, _snowmanxxxxxx, _snowmanxxxxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.b.f = -afm.l(_snowmanxxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.b = _snowmanxxx;
            this.b.c = _snowmanxxxx;
            this.b.a = _snowmanxx;
            _snowmanxxx = (float)((double)_snowmanxxx + Math.sin((double)this.b.d) * 10.0);
            _snowmanxxxx = (float)((double)_snowmanxxxx - Math.cos((double)this.b.e) * Math.cos((double)this.b.d) * 10.0);
            _snowmanxx = (float)((double)_snowmanxx - Math.sin((double)this.b.e) * Math.cos((double)this.b.d) * 10.0);
            this.b.a(_snowman, _snowman, _snowman, _snowman);
         }

         this.a.b = _snowmanxxx;
         this.a.c = _snowmanxxxx;
         this.a.a = _snowmanxx;
         double[] _snowmanxxxxxxxxxx = this.A.a(0, this.B);
         this.a.e = afm.l(_snowmanxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * (float) (Math.PI / 180.0);
         this.a.d = afm.l((double)this.A.a(6, _snowmanxxxxxx, _snowmanxxxxxxxxxx)) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
         this.a.f = -afm.l(_snowmanxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0);
         this.a.a(_snowman, _snowman, _snowman, _snowman);
         _snowman.a();
         _snowman.a(0.0, 1.0, 0.0);
         _snowman.a(g.f.a(-_snowmanxxxxxxx * 1.5F));
         _snowman.a(0.0, -1.0, 0.0);
         this.g.f = 0.0F;
         this.g.a(_snowman, _snowman, _snowman, _snowman);
         float _snowmanxxxxxxxxxxx = _snowman * (float) (Math.PI * 2);
         this.h.d = 0.125F - (float)Math.cos((double)_snowmanxxxxxxxxxxx) * 0.2F;
         this.h.e = -0.25F;
         this.h.f = -((float)(Math.sin((double)_snowmanxxxxxxxxxxx) + 0.125)) * 0.8F;
         this.i.f = (float)(Math.sin((double)(_snowmanxxxxxxxxxxx + 2.0F)) + 0.5) * 0.75F;
         this.p.d = this.h.d;
         this.p.e = -this.h.e;
         this.p.f = -this.h.f;
         this.t.f = -this.i.f;
         this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, this.h, this.j, this.k, this.l, this.m, this.n, this.o);
         this.a(_snowman, _snowman, _snowman, _snowman, _snowmanx, this.p, this.u, this.v, this.w, this.x, this.y, this.z);
         _snowman.b();
         float _snowmanxxxxxxxxxxxx = -((float)Math.sin((double)(_snowman * (float) (Math.PI * 2)))) * 0.0F;
         _snowmanxxxxxxxxx = _snowman * (float) (Math.PI * 2);
         _snowmanxxx = 10.0F;
         _snowmanxxxx = 60.0F;
         _snowmanxx = 0.0F;
         _snowmanxxxxxx = this.A.a(11, this.B);

         for (int _snowmanxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxx < 12; _snowmanxxxxxxxxxxxxx++) {
            _snowmanxxxxxxxxxx = this.A.a(12 + _snowmanxxxxxxxxxxxxx, this.B);
            _snowmanxxxxxxxxxxxx = (float)((double)_snowmanxxxxxxxxxxxx + Math.sin((double)((float)_snowmanxxxxxxxxxxxxx * 0.45F + _snowmanxxxxxxxxx)) * 0.05F);
            this.b.e = (afm.l(_snowmanxxxxxxxxxx[0] - _snowmanxxxxxx[0]) * 1.5F + 180.0F) * (float) (Math.PI / 180.0);
            this.b.d = _snowmanxxxxxxxxxxxx + (float)(_snowmanxxxxxxxxxx[1] - _snowmanxxxxxx[1]) * (float) (Math.PI / 180.0) * 1.5F * 5.0F;
            this.b.f = afm.l(_snowmanxxxxxxxxxx[0] - (double)_snowmanxxxxxxxx) * (float) (Math.PI / 180.0) * 1.5F;
            this.b.b = _snowmanxxx;
            this.b.c = _snowmanxxxx;
            this.b.a = _snowmanxx;
            _snowmanxxx = (float)((double)_snowmanxxx + Math.sin((double)this.b.d) * 10.0);
            _snowmanxxxx = (float)((double)_snowmanxxxx - Math.cos((double)this.b.e) * Math.cos((double)this.b.d) * 10.0);
            _snowmanxx = (float)((double)_snowmanxx - Math.sin((double)this.b.e) * Math.cos((double)this.b.d) * 10.0);
            this.b.a(_snowman, _snowman, _snowman, _snowman);
         }

         _snowman.b();
      }

      private void a(dfm var1, dfq var2, int var3, int var4, float var5, dwn var6, dwn var7, dwn var8, dwn var9, dwn var10, dwn var11, dwn var12) {
         _snowman.d = 1.0F + _snowman * 0.1F;
         _snowman.d = 0.5F + _snowman * 0.1F;
         _snowman.d = 0.75F + _snowman * 0.1F;
         _snowman.d = 1.3F + _snowman * 0.1F;
         _snowman.d = -0.5F - _snowman * 0.1F;
         _snowman.d = 0.75F + _snowman * 0.1F;
         _snowman.a(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(_snowman, _snowman, _snowman, _snowman);
         _snowman.a(_snowman, _snowman, _snowman, _snowman);
      }
   }
}
