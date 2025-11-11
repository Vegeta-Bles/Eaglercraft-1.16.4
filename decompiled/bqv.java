public class bqv {
   private final bmb a;
   private final bmb b;
   private final bmb c;
   private int d;
   private final int e;
   private boolean f = true;
   private int g;
   private int h;
   private float i;
   private int j = 1;

   public bqv(md var1) {
      this.a = bmb.a(_snowman.p("buy"));
      this.b = bmb.a(_snowman.p("buyB"));
      this.c = bmb.a(_snowman.p("sell"));
      this.d = _snowman.h("uses");
      if (_snowman.c("maxUses", 99)) {
         this.e = _snowman.h("maxUses");
      } else {
         this.e = 4;
      }

      if (_snowman.c("rewardExp", 1)) {
         this.f = _snowman.q("rewardExp");
      }

      if (_snowman.c("xp", 3)) {
         this.j = _snowman.h("xp");
      }

      if (_snowman.c("priceMultiplier", 5)) {
         this.i = _snowman.j("priceMultiplier");
      }

      this.g = _snowman.h("specialPrice");
      this.h = _snowman.h("demand");
   }

   public bqv(bmb var1, bmb var2, int var3, int var4, float var5) {
      this(_snowman, bmb.b, _snowman, _snowman, _snowman, _snowman);
   }

   public bqv(bmb var1, bmb var2, bmb var3, int var4, int var5, float var6) {
      this(_snowman, _snowman, _snowman, 0, _snowman, _snowman, _snowman);
   }

   public bqv(bmb var1, bmb var2, bmb var3, int var4, int var5, int var6, float var7) {
      this(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman, 0);
   }

   public bqv(bmb var1, bmb var2, bmb var3, int var4, int var5, int var6, float var7, int var8) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.j = _snowman;
      this.i = _snowman;
      this.h = _snowman;
   }

   public bmb a() {
      return this.a;
   }

   public bmb b() {
      int _snowman = this.a.E();
      bmb _snowmanx = this.a.i();
      int _snowmanxx = Math.max(0, afm.d((float)(_snowman * this.h) * this.i));
      _snowmanx.e(afm.a(_snowman + _snowmanxx + this.g, 1, this.a.b().i()));
      return _snowmanx;
   }

   public bmb c() {
      return this.b;
   }

   public bmb d() {
      return this.c;
   }

   public void e() {
      this.h = this.h + this.d - (this.e - this.d);
   }

   public bmb f() {
      return this.c.i();
   }

   public int g() {
      return this.d;
   }

   public void h() {
      this.d = 0;
   }

   public int i() {
      return this.e;
   }

   public void j() {
      this.d++;
   }

   public int k() {
      return this.h;
   }

   public void a(int var1) {
      this.g += _snowman;
   }

   public void l() {
      this.g = 0;
   }

   public int m() {
      return this.g;
   }

   public void b(int var1) {
      this.g = _snowman;
   }

   public float n() {
      return this.i;
   }

   public int o() {
      return this.j;
   }

   public boolean p() {
      return this.d >= this.e;
   }

   public void q() {
      this.d = this.e;
   }

   public boolean r() {
      return this.d > 0;
   }

   public boolean s() {
      return this.f;
   }

   public md t() {
      md _snowman = new md();
      _snowman.a("buy", this.a.b(new md()));
      _snowman.a("sell", this.c.b(new md()));
      _snowman.a("buyB", this.b.b(new md()));
      _snowman.b("uses", this.d);
      _snowman.b("maxUses", this.e);
      _snowman.a("rewardExp", this.f);
      _snowman.b("xp", this.j);
      _snowman.a("priceMultiplier", this.i);
      _snowman.b("specialPrice", this.g);
      _snowman.b("demand", this.h);
      return _snowman;
   }

   public boolean a(bmb var1, bmb var2) {
      return this.c(_snowman, this.b()) && _snowman.E() >= this.b().E() && this.c(_snowman, this.b) && _snowman.E() >= this.b.E();
   }

   private boolean c(bmb var1, bmb var2) {
      if (_snowman.a() && _snowman.a()) {
         return true;
      } else {
         bmb _snowman = _snowman.i();
         if (_snowman.b().k()) {
            _snowman.b(_snowman.g());
         }

         return bmb.c(_snowman, _snowman) && (!_snowman.n() || _snowman.n() && mp.a(_snowman.o(), _snowman.o(), false));
      }
   }

   public boolean b(bmb var1, bmb var2) {
      if (!this.a(_snowman, _snowman)) {
         return false;
      } else {
         _snowman.g(this.b().E());
         if (!this.c().a()) {
            _snowman.g(this.c().E());
         }

         return true;
      }
   }
}
