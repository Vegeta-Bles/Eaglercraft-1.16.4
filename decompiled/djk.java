public class djk {
   private boolean a;
   private brc b;
   private aqa c;
   private dcn d = dcn.a;
   private final fx.a e = new fx.a();
   private final g f = new g(0.0F, 0.0F, 1.0F);
   private final g g = new g(0.0F, 1.0F, 0.0F);
   private final g h = new g(1.0F, 0.0F, 0.0F);
   private float i;
   private float j;
   private final d k = new d(0.0F, 0.0F, 0.0F, 1.0F);
   private boolean l;
   private boolean m;
   private float n;
   private float o;

   public djk() {
   }

   public void a(brc var1, aqa var2, boolean var3, boolean var4, float var5) {
      this.a = true;
      this.b = _snowman;
      this.c = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.a(_snowman.h(_snowman), _snowman.g(_snowman));
      this.b(afm.d((double)_snowman, _snowman.m, _snowman.cD()), afm.d((double)_snowman, _snowman.n, _snowman.cE()) + (double)afm.g(_snowman, this.o, this.n), afm.d((double)_snowman, _snowman.o, _snowman.cH()));
      if (_snowman) {
         if (_snowman) {
            this.a(this.j + 180.0F, -this.i);
         }

         this.a(-this.a(4.0), 0.0, 0.0);
      } else if (_snowman instanceof aqm && ((aqm)_snowman).em()) {
         gc _snowman = ((aqm)_snowman).eo();
         this.a(_snowman != null ? _snowman.o() - 180.0F : 0.0F, 0.0F);
         this.a(0.0, 0.3, 0.0);
      }
   }

   public void a() {
      if (this.c != null) {
         this.o = this.n;
         this.n = this.n + (this.c.ce() - this.n) * 0.5F;
      }
   }

   private double a(double var1) {
      for (int _snowman = 0; _snowman < 8; _snowman++) {
         float _snowmanx = (float)((_snowman & 1) * 2 - 1);
         float _snowmanxx = (float)((_snowman >> 1 & 1) * 2 - 1);
         float _snowmanxxx = (float)((_snowman >> 2 & 1) * 2 - 1);
         _snowmanx *= 0.1F;
         _snowmanxx *= 0.1F;
         _snowmanxxx *= 0.1F;
         dcn _snowmanxxxx = this.d.b((double)_snowmanx, (double)_snowmanxx, (double)_snowmanxxx);
         dcn _snowmanxxxxx = new dcn(
            this.d.b - (double)this.f.a() * _snowman + (double)_snowmanx + (double)_snowmanxxx,
            this.d.c - (double)this.f.b() * _snowman + (double)_snowmanxx,
            this.d.d - (double)this.f.c() * _snowman + (double)_snowmanxxx
         );
         dcl _snowmanxxxxxx = this.b.a(new brf(_snowmanxxxx, _snowmanxxxxx, brf.a.c, brf.b.a, this.c));
         if (_snowmanxxxxxx.c() != dcl.a.a) {
            double _snowmanxxxxxxx = _snowmanxxxxxx.e().f(this.d);
            if (_snowmanxxxxxxx < _snowman) {
               _snowman = _snowmanxxxxxxx;
            }
         }
      }

      return _snowman;
   }

   protected void a(double var1, double var3, double var5) {
      double _snowman = (double)this.f.a() * _snowman + (double)this.g.a() * _snowman + (double)this.h.a() * _snowman;
      double _snowmanx = (double)this.f.b() * _snowman + (double)this.g.b() * _snowman + (double)this.h.b() * _snowman;
      double _snowmanxx = (double)this.f.c() * _snowman + (double)this.g.c() * _snowman + (double)this.h.c() * _snowman;
      this.a(new dcn(this.d.b + _snowman, this.d.c + _snowmanx, this.d.d + _snowmanxx));
   }

   protected void a(float var1, float var2) {
      this.i = _snowman;
      this.j = _snowman;
      this.k.a(0.0F, 0.0F, 0.0F, 1.0F);
      this.k.a(g.d.a(-_snowman));
      this.k.a(g.b.a(_snowman));
      this.f.a(0.0F, 0.0F, 1.0F);
      this.f.a(this.k);
      this.g.a(0.0F, 1.0F, 0.0F);
      this.g.a(this.k);
      this.h.a(1.0F, 0.0F, 0.0F);
      this.h.a(this.k);
   }

   protected void b(double var1, double var3, double var5) {
      this.a(new dcn(_snowman, _snowman, _snowman));
   }

   protected void a(dcn var1) {
      this.d = _snowman;
      this.e.c(_snowman.b, _snowman.c, _snowman.d);
   }

   public dcn b() {
      return this.d;
   }

   public fx c() {
      return this.e;
   }

   public float d() {
      return this.i;
   }

   public float e() {
      return this.j;
   }

   public d f() {
      return this.k;
   }

   public aqa g() {
      return this.c;
   }

   public boolean h() {
      return this.a;
   }

   public boolean i() {
      return this.l;
   }

   public cux k() {
      if (!this.a) {
         return cuy.a.h();
      } else {
         cux _snowman = this.b.b(this.e);
         return !_snowman.c() && this.d.c >= (double)((float)this.e.v() + _snowman.a(this.b, this.e)) ? cuy.a.h() : _snowman;
      }
   }

   public final g l() {
      return this.f;
   }

   public final g m() {
      return this.g;
   }

   public void o() {
      this.b = null;
      this.c = null;
      this.a = false;
   }
}
