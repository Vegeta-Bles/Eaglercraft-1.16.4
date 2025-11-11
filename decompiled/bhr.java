public class bhr extends bhl {
   private static final us<String> b = uv.a(bhr.class, uu.d);
   private static final us<nr> c = uv.a(bhr.class, uu.e);
   private final bqy d = new bhr.a();
   private int e;

   public bhr(aqe<? extends bhr> var1, brx var2) {
      super(_snowman, _snowman);
   }

   public bhr(brx var1, double var2, double var4, double var6) {
      super(aqe.V, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   protected void e() {
      super.e();
      this.ab().a(b, "");
      this.ab().a(c, oe.d);
   }

   @Override
   protected void a(md var1) {
      super.a(_snowman);
      this.d.b(_snowman);
      this.ab().b(b, this.u().k());
      this.ab().b(c, this.u().j());
   }

   @Override
   protected void b(md var1) {
      super.b(_snowman);
      this.d.a(_snowman);
   }

   @Override
   public bhl.a o() {
      return bhl.a.g;
   }

   @Override
   public ceh q() {
      return bup.er.n();
   }

   public bqy u() {
      return this.d;
   }

   @Override
   public void a(int var1, int var2, int var3, boolean var4) {
      if (_snowman && this.K - this.e >= 4) {
         this.u().a(this.l);
         this.e = this.K;
      }
   }

   @Override
   public aou a(bfw var1, aot var2) {
      return this.d.a(_snowman);
   }

   @Override
   public void a(us<?> var1) {
      super.a(_snowman);
      if (c.equals(_snowman)) {
         try {
            this.d.b(this.ab().a(c));
         } catch (Throwable var3) {
         }
      } else if (b.equals(_snowman)) {
         this.d.a(this.ab().a(b));
      }
   }

   @Override
   public boolean cj() {
      return true;
   }

   public class a extends bqy {
      public a() {
      }

      @Override
      public aag d() {
         return (aag)bhr.this.l;
      }

      @Override
      public void e() {
         bhr.this.ab().b(bhr.b, this.k());
         bhr.this.ab().b(bhr.c, this.j());
      }

      @Override
      public dcn f() {
         return bhr.this.cA();
      }

      public bhr g() {
         return bhr.this;
      }

      @Override
      public db h() {
         return new db(this, bhr.this.cA(), bhr.this.bi(), this.d(), 2, this.l().getString(), bhr.this.d(), this.d().l(), bhr.this);
      }
   }
}
