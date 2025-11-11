import java.io.IOException;

public class rk implements oj<om> {
   private long a;
   private long b;

   public rk() {
   }

   public rk(long var1, long var3, boolean var5) {
      this.a = _snowman;
      this.b = _snowman;
      if (!_snowman) {
         this.b = -this.b;
         if (this.b == 0L) {
            this.b = -1L;
         }
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readLong();
      this.b = _snowman.readLong();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeLong(this.a);
      _snowman.writeLong(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public long b() {
      return this.a;
   }

   public long c() {
      return this.b;
   }
}
