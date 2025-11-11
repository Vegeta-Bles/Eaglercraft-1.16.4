public class aov<T> {
   private final aou a;
   private final T b;

   public aov(aou var1, T var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public aou a() {
      return this.a;
   }

   public T b() {
      return this.b;
   }

   public static <T> aov<T> a(T var0) {
      return new aov<>(aou.a, _snowman);
   }

   public static <T> aov<T> b(T var0) {
      return new aov<>(aou.b, _snowman);
   }

   public static <T> aov<T> c(T var0) {
      return new aov<>(aou.c, _snowman);
   }

   public static <T> aov<T> d(T var0) {
      return new aov<>(aou.d, _snowman);
   }

   public static <T> aov<T> a(T var0, boolean var1) {
      return _snowman ? a(_snowman) : b(_snowman);
   }
}
