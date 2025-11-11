import javax.annotation.Nullable;

public class dof extends dol {
   private static final nr c = new oe("(").a(new of("options.languageWarning")).c(")").a(k.h);
   private dof.a p;
   private final ekz q;
   private dlw r;
   private dlj s;

   public dof(dot var1, dkd var2, ekz var3) {
      super(_snowman, _snowman, new of("options.language"));
      this.q = _snowman;
   }

   @Override
   protected void b() {
      this.p = new dof.a(this.i);
      this.e.add(this.p);
      this.r = this.a(new dlw(this.k / 2 - 155, this.l - 38, 150, 20, dkc.N, dkc.N.c(this.b), var1 -> {
         dkc.N.a(this.b);
         this.b.b();
         var1.a(dkc.N.c(this.b));
         this.i.a();
      }));
      this.s = this.a((dlj)(new dlj(this.k / 2 - 155 + 160, this.l - 38, 150, 20, nq.c, var1 -> {
         dof.a.a _snowman = this.p.h();
         if (_snowman != null && !_snowman.b.getCode().equals(this.q.b().getCode())) {
            this.q.a(_snowman.b);
            this.b.aV = _snowman.b.getCode();
            this.i.j();
            this.s.a(nq.c);
            this.r.a(dkc.N.c(this.b));
            this.b.b();
         }

         this.i.a(this.a);
      })));
      super.b();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.p.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 16, 16777215);
      a(_snowman, this.o, c, this.k / 2, this.l - 56, 8421504);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }

   class a extends dlv<dof.a.a> {
      public a(djz var2) {
         super(_snowman, dof.this.k, dof.this.l, 32, dof.this.l - 65 + 4, 18);

         for (eky _snowman : dof.this.q.d()) {
            dof.a.a _snowmanx = new dof.a.a(_snowman);
            this.b(_snowmanx);
            if (dof.this.q.b().getCode().equals(_snowman.getCode())) {
               this.a(_snowmanx);
            }
         }

         if (this.h() != null) {
            this.c(this.h());
         }
      }

      @Override
      protected int e() {
         return super.e() + 20;
      }

      @Override
      public int d() {
         return super.d() + 50;
      }

      public void a(@Nullable dof.a.a var1) {
         super.a(_snowman);
         if (_snowman != null) {
            dkz.b.a(new of("narrator.select", _snowman.b).getString());
         }
      }

      @Override
      protected void a(dfm var1) {
         dof.this.a(_snowman);
      }

      @Override
      protected boolean b() {
         return dof.this.aw_() == this;
      }

      public class a extends dlv.a<dof.a.a> {
         private final eky b;

         public a(eky var2) {
            this.b = _snowman;
         }

         @Override
         public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
            String _snowman = this.b.toString();
            dof.this.o.a(_snowman, _snowman, (float)(a.this.d / 2 - dof.this.o.b(_snowman) / 2), (float)(_snowman + 1), 16777215, true);
         }

         @Override
         public boolean a(double var1, double var3, int var5) {
            if (_snowman == 0) {
               this.a();
               return true;
            } else {
               return false;
            }
         }

         private void a() {
            a.this.a(this);
         }
      }
   }
}
