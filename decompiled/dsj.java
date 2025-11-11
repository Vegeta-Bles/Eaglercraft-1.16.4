import java.util.List;

public class dsj extends dot {
   protected final dot a;
   private List<afa> c;
   private dlj p;
   private dlj q;
   private dlj r;
   private dlj s;
   protected dlq b;
   private dsm t;

   public dsj(dot var1) {
      super(new of("selectWorld.title"));
      this.a = _snowman;
   }

   @Override
   public boolean a(double var1, double var3, double var5) {
      return super.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void d() {
      this.b.a();
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      this.b = new dlq(this.o, this.k / 2 - 100, 22, 200, 20, this.b, new of("selectWorld.search"));
      this.b.a(var1 -> this.t.a(() -> var1, false));
      this.t = new dsm(this, this.i, this.k, this.l, 48, this.l - 64, 36, () -> this.b.b(), this.t);
      this.e.add(this.b);
      this.e.add(this.t);
      this.q = this.a(new dlj(this.k / 2 - 154, this.l - 52, 150, 20, new of("selectWorld.select"), var1 -> this.t.f().ifPresent(dsm.a::a)));
      this.a(new dlj(this.k / 2 + 4, this.l - 52, 150, 20, new of("selectWorld.create"), var1 -> this.i.a(dsf.a(this))));
      this.r = this.a(new dlj(this.k / 2 - 154, this.l - 28, 72, 20, new of("selectWorld.edit"), var1 -> this.t.f().ifPresent(dsm.a::c)));
      this.p = this.a(new dlj(this.k / 2 - 76, this.l - 28, 72, 20, new of("selectWorld.delete"), var1 -> this.t.f().ifPresent(dsm.a::b)));
      this.s = this.a(new dlj(this.k / 2 + 4, this.l - 28, 72, 20, new of("selectWorld.recreate"), var1 -> this.t.f().ifPresent(dsm.a::d)));
      this.a(new dlj(this.k / 2 + 82, this.l - 28, 72, 20, nq.d, var1 -> this.i.a(this.a)));
      this.c(false);
      this.b(this.b);
   }

   @Override
   public boolean a(int var1, int var2, int var3) {
      return super.a(_snowman, _snowman, _snowman) ? true : this.b.a(_snowman, _snowman, _snowman);
   }

   @Override
   public void at_() {
      this.i.a(this.a);
   }

   @Override
   public boolean a(char var1, int var2) {
      return this.b.a(_snowman, _snowman);
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.c = null;
      this.t.a(_snowman, _snowman, _snowman, _snowman);
      this.b.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 8, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.c != null) {
         this.c(_snowman, this.c, _snowman, _snowman);
      }
   }

   public void b(List<afa> var1) {
      this.c = _snowman;
   }

   @Override
   public void c(boolean var1) {
      this.q.o = _snowman;
      this.p.o = _snowman;
      this.r.o = _snowman;
      this.s.o = _snowman;
   }

   @Override
   public void e() {
      if (this.t != null) {
         this.t.au_().forEach(dsm.a::close);
      }
   }
}
