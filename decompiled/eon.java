import java.util.Collection;

public abstract class eon<E extends dlv.a<E>> extends dlv<E> {
   protected eon(int var1, int var2, int var3, int var4, int var5) {
      super(djz.C(), _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   public void j(int var1) {
      if (_snowman == -1) {
         this.a(null);
      } else if (super.l() != 0) {
         this.a(this.e(_snowman));
      }
   }

   @Override
   public void a(int var1) {
      this.j(_snowman);
   }

   public void a(int var1, int var2, double var3, double var5, int var7) {
   }

   @Override
   public int c() {
      return 0;
   }

   @Override
   public int e() {
      return this.q() + this.d();
   }

   @Override
   public int d() {
      return (int)((double)this.d * 0.6);
   }

   @Override
   public void a(Collection<E> var1) {
      super.a(_snowman);
   }

   @Override
   public int l() {
      return super.l();
   }

   @Override
   public int h(int var1) {
      return super.h(_snowman);
   }

   @Override
   public int q() {
      return super.q();
   }

   public int a(E var1) {
      return super.b(_snowman);
   }

   public void a() {
      this.k();
   }
}
