import java.util.List;
import javax.annotation.Nullable;

public abstract class dov extends dol {
   private final dkc[] c;
   @Nullable
   private dlh p;
   private dlx q;

   public dov(dot var1, dkd var2, nr var3, dkc[] var4) {
      super(_snowman, _snowman, _snowman);
      this.c = _snowman;
   }

   @Override
   protected void b() {
      this.q = new dlx(this.i, this.k, this.l, 32, this.l - 32, 25);
      this.q.a(this.c);
      this.e.add(this.q);
      this.c();
      this.p = this.q.b(dkc.A);
      if (this.p != null) {
         this.p.o = dkz.b.a();
      }
   }

   protected void c() {
      this.a(new dlj(this.k / 2 - 100, this.l - 27, 200, 20, nq.c, var1 -> this.i.a(this.a)));
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      this.q.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      List<afa> _snowman = a(this.q, _snowman, _snowman);
      if (_snowman != null) {
         this.c(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public void h() {
      if (this.p != null) {
         this.p.a(dkc.A.c(this.b));
      }
   }
}
