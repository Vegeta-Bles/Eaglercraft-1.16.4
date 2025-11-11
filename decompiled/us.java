public class us<T> {
   private final int a;
   private final ut<T> b;

   public us(int var1, ut<T> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public int a() {
      return this.a;
   }

   public ut<T> b() {
      return this.b;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         us<?> _snowman = (us<?>)_snowman;
         return this.a == _snowman.a;
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a;
   }

   @Override
   public String toString() {
      return "<entity data: " + this.a + ">";
   }
}
