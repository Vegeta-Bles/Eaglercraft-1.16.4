import java.util.Random;

public class chx extends Random {
   private int a;

   public chx() {
   }

   public chx(long var1) {
      super(_snowman);
   }

   public void a(int var1) {
      for (int _snowman = 0; _snowman < _snowman; _snowman++) {
         this.next(1);
      }
   }

   @Override
   protected int next(int var1) {
      this.a++;
      return super.next(_snowman);
   }

   public long a(int var1, int var2) {
      long _snowman = (long)_snowman * 341873128712L + (long)_snowman * 132897987541L;
      this.setSeed(_snowman);
      return _snowman;
   }

   public long a(long var1, int var3, int var4) {
      this.setSeed(_snowman);
      long _snowman = this.nextLong() | 1L;
      long _snowmanx = this.nextLong() | 1L;
      long _snowmanxx = (long)_snowman * _snowman + (long)_snowman * _snowmanx ^ _snowman;
      this.setSeed(_snowmanxx);
      return _snowmanxx;
   }

   public long b(long var1, int var3, int var4) {
      long _snowman = _snowman + (long)_snowman + (long)(10000 * _snowman);
      this.setSeed(_snowman);
      return _snowman;
   }

   public long c(long var1, int var3, int var4) {
      this.setSeed(_snowman);
      long _snowman = this.nextLong();
      long _snowmanx = this.nextLong();
      long _snowmanxx = (long)_snowman * _snowman ^ (long)_snowman * _snowmanx ^ _snowman;
      this.setSeed(_snowmanxx);
      return _snowmanxx;
   }

   public long a(long var1, int var3, int var4, int var5) {
      long _snowman = (long)_snowman * 341873128712L + (long)_snowman * 132897987541L + _snowman + (long)_snowman;
      this.setSeed(_snowman);
      return _snowman;
   }

   public static Random a(int var0, int var1, long var2, long var4) {
      return new Random(_snowman + (long)(_snowman * _snowman * 4987142) + (long)(_snowman * 5947611) + (long)(_snowman * _snowman) * 4392871L + (long)(_snowman * 389711) ^ _snowman);
   }
}
