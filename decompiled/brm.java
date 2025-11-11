public class brm<T> implements bso<T> {
   private static final brm<Object> a = new brm<>();

   public brm() {
   }

   public static <T> brm<T> b() {
      return (brm<T>)a;
   }

   @Override
   public boolean a(fx var1, T var2) {
      return false;
   }

   @Override
   public void a(fx var1, T var2, int var3) {
   }

   @Override
   public void a(fx var1, T var2, int var3, bsq var4) {
   }

   @Override
   public boolean b(fx var1, T var2) {
      return false;
   }
}
