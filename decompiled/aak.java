import java.util.Objects;

public final class aak<T> implements Comparable<aak<?>> {
   private final aal<T> a;
   private final int b;
   private final T c;
   private long d;

   protected aak(aal<T> var1, int var2, T var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public int a(aak<?> var1) {
      int _snowman = Integer.compare(this.b, _snowman.b);
      if (_snowman != 0) {
         return _snowman;
      } else {
         int _snowmanx = Integer.compare(System.identityHashCode(this.a), System.identityHashCode(_snowman.a));
         return _snowmanx != 0 ? _snowmanx : this.a.a().compare(this.c, (T)_snowman.c);
      }
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (!(_snowman instanceof aak)) {
         return false;
      } else {
         aak<?> _snowman = (aak<?>)_snowman;
         return this.b == _snowman.b && Objects.equals(this.a, _snowman.a) && Objects.equals(this.c, _snowman.c);
      }
   }

   @Override
   public int hashCode() {
      return Objects.hash(this.a, this.b, this.c);
   }

   @Override
   public String toString() {
      return "Ticket[" + this.a + " " + this.b + " (" + this.c + ")] at " + this.d;
   }

   public aal<T> a() {
      return this.a;
   }

   public int b() {
      return this.b;
   }

   protected void a(long var1) {
      this.d = _snowman;
   }

   protected boolean b(long var1) {
      long _snowman = this.a.b();
      return _snowman != 0L && _snowman - this.d > _snowman;
   }
}
