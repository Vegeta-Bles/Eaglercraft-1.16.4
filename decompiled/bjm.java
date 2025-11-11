import javax.annotation.Nullable;

public class bjm implements aon, bjl {
   private final gj<bmb> a = gj.a(1, bmb.b);
   @Nullable
   private boq<?> b;

   public bjm() {
   }

   @Override
   public int Z_() {
      return 1;
   }

   @Override
   public boolean c() {
      for (bmb _snowman : this.a) {
         if (!_snowman.a()) {
            return false;
         }
      }

      return true;
   }

   @Override
   public bmb a(int var1) {
      return this.a.get(0);
   }

   @Override
   public bmb a(int var1, int var2) {
      return aoo.a(this.a, 0);
   }

   @Override
   public bmb b(int var1) {
      return aoo.a(this.a, 0);
   }

   @Override
   public void a(int var1, bmb var2) {
      this.a.set(0, _snowman);
   }

   @Override
   public void X_() {
   }

   @Override
   public boolean a(bfw var1) {
      return true;
   }

   @Override
   public void Y_() {
      this.a.clear();
   }

   @Override
   public void a(@Nullable boq<?> var1) {
      this.b = _snowman;
   }

   @Nullable
   @Override
   public boq<?> ak_() {
      return this.b;
   }
}
