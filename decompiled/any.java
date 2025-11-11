public final class any implements Comparable<any> {
   public final double a;
   public final double b;
   public final long c;
   public final String d;

   public any(String var1, double var2, double var4, long var6) {
      this.d = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public int a(any var1) {
      if (_snowman.a < this.a) {
         return -1;
      } else {
         return _snowman.a > this.a ? 1 : _snowman.d.compareTo(this.d);
      }
   }

   public int a() {
      return (this.d.hashCode() & 11184810) + 4473924;
   }
}
