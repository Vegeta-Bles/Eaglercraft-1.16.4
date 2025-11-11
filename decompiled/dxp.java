public class dxp extends dzb {
   private final cuw b;
   protected boolean a;

   private dxp(dwt var1, double var2, double var4, double var6, cuw var8) {
      super(_snowman, _snowman, _snowman, _snowman);
      this.a(0.01F, 0.01F);
      this.u = 0.06F;
      this.b = _snowman;
   }

   @Override
   public dyk b() {
      return dyk.b;
   }

   @Override
   public int a(float var1) {
      return this.a ? 240 : super.a(_snowman);
   }

   @Override
   public void a() {
      this.d = this.g;
      this.e = this.h;
      this.f = this.i;
      this.g();
      if (!this.o) {
         this.k = this.k - (double)this.u;
         this.a(this.j, this.k, this.l);
         this.h();
         if (!this.o) {
            this.j *= 0.98F;
            this.k *= 0.98F;
            this.l *= 0.98F;
            fx _snowman = new fx(this.g, this.h, this.i);
            cux _snowmanx = this.c.b(_snowman);
            if (_snowmanx.a() == this.b && this.h < (double)((float)_snowman.v() + _snowmanx.a((brc)this.c, _snowman))) {
               this.j();
            }
         }
      }
   }

   protected void g() {
      if (this.t-- <= 0) {
         this.j();
      }
   }

   protected void h() {
   }

   static class a extends dxp.b {
      private a(dwt var1, double var2, double var4, double var6, cuw var8, hf var9) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      protected void g() {
         this.v = 1.0F;
         this.w = 16.0F / (float)(40 - this.t + 16);
         this.x = 4.0F / (float)(40 - this.t + 8);
         super.g();
      }
   }

   static class b extends dxp {
      private final hf b;

      private b(dwt var1, double var2, double var4, double var6, cuw var8, hf var9) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.b = _snowman;
         this.u *= 0.02F;
         this.t = 40;
      }

      @Override
      protected void g() {
         if (this.t-- <= 0) {
            this.j();
            this.c.a(this.b, this.g, this.h, this.i, this.j, this.k, this.l);
         }
      }

      @Override
      protected void h() {
         this.j *= 0.02;
         this.k *= 0.02;
         this.l *= 0.02;
      }
   }

   static class c extends dxp {
      private c(dwt var1, double var2, double var4, double var6, cuw var8) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.t = (int)(16.0 / (Math.random() * 0.8 + 0.2));
      }
   }

   static class d extends dxp.e {
      protected final hf b;

      private d(dwt var1, double var2, double var4, double var6, cuw var8, hf var9) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.b = _snowman;
      }

      @Override
      protected void h() {
         if (this.m) {
            this.j();
            this.c.a(this.b, this.g, this.h, this.i, 0.0, 0.0, 0.0);
         }
      }
   }

   static class e extends dxp {
      private e(dwt var1, double var2, double var4, double var6, cuw var8) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman);
         this.t = (int)(64.0 / (Math.random() * 0.8 + 0.2));
      }

      @Override
      protected void h() {
         if (this.m) {
            this.j();
         }
      }
   }

   static class f extends dxp.d {
      private f(dwt var1, double var2, double var4, double var6, cuw var8, hf var9) {
         super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      }

      @Override
      protected void h() {
         if (this.m) {
            this.j();
            this.c.a(this.b, this.g, this.h, this.i, 0.0, 0.0, 0.0);
            this.c.a(this.g + 0.5, this.h, this.i + 0.5, adq.aE, adr.e, 0.3F + this.c.t.nextFloat() * 2.0F / 3.0F, 1.0F, false);
         }
      }
   }

   public static class g implements dyj<hi> {
      protected final dyw a;

      public g(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.f(_snowman, _snowman, _snowman, _snowman, cuy.a, hh.ak);
         _snowman.u = 0.01F;
         _snowman.a(0.582F, 0.448F, 0.082F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class h implements dyj<hi> {
      protected final dyw a;

      public h(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp.b _snowman = new dxp.b(_snowman, _snowman, _snowman, _snowman, cuy.a, hh.aj);
         _snowman.u *= 0.01F;
         _snowman.t = 100;
         _snowman.a(0.622F, 0.508F, 0.082F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class i implements dyj<hi> {
      protected final dyw a;

      public i(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.c(_snowman, _snowman, _snowman, _snowman, cuy.a);
         _snowman.t = (int)(128.0 / (Math.random() * 0.8 + 0.2));
         _snowman.a(0.522F, 0.408F, 0.082F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class j implements dyj<hi> {
      protected final dyw a;

      public j(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.d(_snowman, _snowman, _snowman, _snowman, cuy.e, hh.l);
         _snowman.a(1.0F, 0.2857143F, 0.083333336F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class k implements dyj<hi> {
      protected final dyw a;

      public k(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp.a _snowman = new dxp.a(_snowman, _snowman, _snowman, _snowman, cuy.e, hh.k);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class l implements dyj<hi> {
      protected final dyw a;

      public l(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.c(_snowman, _snowman, _snowman, _snowman, cuy.e);
         _snowman.a(1.0F, 0.2857143F, 0.083333336F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class m implements dyj<hi> {
      protected final dyw a;

      public m(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.e(_snowman, _snowman, _snowman, _snowman, cuy.a);
         _snowman.t = (int)(16.0 / (Math.random() * 0.8 + 0.2));
         _snowman.u = 0.007F;
         _snowman.a(0.92F, 0.782F, 0.72F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class n implements dyj<hi> {
      protected final dyw a;

      public n(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.d(_snowman, _snowman, _snowman, _snowman, cuy.a, hh.ar);
         _snowman.a = true;
         _snowman.u = 0.01F;
         _snowman.a(0.51171875F, 0.03125F, 0.890625F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class o implements dyj<hi> {
      protected final dyw a;

      public o(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp.b _snowman = new dxp.b(_snowman, _snowman, _snowman, _snowman, cuy.a, hh.aq);
         _snowman.a = true;
         _snowman.u *= 0.01F;
         _snowman.t = 100;
         _snowman.a(0.51171875F, 0.03125F, 0.890625F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class p implements dyj<hi> {
      protected final dyw a;

      public p(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.c(_snowman, _snowman, _snowman, _snowman, cuy.a);
         _snowman.a = true;
         _snowman.t = (int)(28.0 / (Math.random() * 0.8 + 0.2));
         _snowman.a(0.51171875F, 0.03125F, 0.890625F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class q implements dyj<hi> {
      protected final dyw a;

      public q(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.d(_snowman, _snowman, _snowman, _snowman, cuy.c, hh.Z);
         _snowman.a(0.2F, 0.3F, 1.0F);
         _snowman.a(this.a);
         return _snowman;
      }
   }

   public static class r implements dyj<hi> {
      protected final dyw a;

      public r(dyw var1) {
         this.a = _snowman;
      }

      public dyg a(hi var1, dwt var2, double var3, double var5, double var7, double var9, double var11, double var13) {
         dxp _snowman = new dxp.b(_snowman, _snowman, _snowman, _snowman, cuy.c, hh.n);
         _snowman.a(0.2F, 0.3F, 1.0F);
         _snowman.a(this.a);
         return _snowman;
      }
   }
}
