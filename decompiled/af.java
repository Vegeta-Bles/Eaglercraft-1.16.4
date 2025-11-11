import com.google.gson.JsonObject;

public interface af<T extends ag> {
   vk a();

   void a(vt var1, af.a<T> var2);

   void b(vt var1, af.a<T> var2);

   void a(vt var1);

   T a(JsonObject var1, ax var2);

   public static class a<T extends ag> {
      private final T a;
      private final y b;
      private final String c;

      public a(T var1, y var2, String var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public T a() {
         return this.a;
      }

      public void a(vt var1) {
         _snowman.a(this.b, this.c);
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
            af.a<?> _snowman = (af.a<?>)_snowman;
            if (!this.a.equals(_snowman.a)) {
               return false;
            } else {
               return !this.b.equals(_snowman.b) ? false : this.c.equals(_snowman.c);
            }
         } else {
            return false;
         }
      }

      @Override
      public int hashCode() {
         int _snowman = this.a.hashCode();
         _snowman = 31 * _snowman + this.b.hashCode();
         return 31 * _snowman + this.c.hashCode();
      }
   }
}
