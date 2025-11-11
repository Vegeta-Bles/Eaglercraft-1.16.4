import it.unimi.dsi.fastutil.longs.Long2IntLinkedOpenHashMap;
import java.util.Random;

public class cvl implements cvj<cvh> {
   private final Long2IntLinkedOpenHashMap a;
   private final int b;
   private final ctz c;
   private final long d;
   private long e;

   public cvl(int var1, long var2, long var4) {
      this.d = b(_snowman, _snowman);
      this.c = new ctz(new Random(_snowman));
      this.a = new Long2IntLinkedOpenHashMap(16, 0.25F);
      this.a.defaultReturnValue(Integer.MIN_VALUE);
      this.b = _snowman;
   }

   public cvh b(cwv var1) {
      return new cvh(this.a, this.b, _snowman);
   }

   public cvh a(cwv var1, cvh var2) {
      return new cvh(this.a, Math.min(1024, _snowman.a() * 4), _snowman);
   }

   public cvh a(cwv var1, cvh var2, cvh var3) {
      return new cvh(this.a, Math.min(1024, Math.max(_snowman.a(), _snowman.a()) * 4), _snowman);
   }

   @Override
   public void a(long var1, long var3) {
      long _snowman = this.d;
      _snowman = afk.a(_snowman, _snowman);
      _snowman = afk.a(_snowman, _snowman);
      _snowman = afk.a(_snowman, _snowman);
      _snowman = afk.a(_snowman, _snowman);
      this.e = _snowman;
   }

   @Override
   public int a(int var1) {
      int _snowman = (int)Math.floorMod(this.e >> 24, (long)_snowman);
      this.e = afk.a(this.e, this.d);
      return _snowman;
   }

   @Override
   public ctz b() {
      return this.c;
   }

   private static long b(long var0, long var2) {
      long var4 = afk.a(_snowman, _snowman);
      var4 = afk.a(var4, _snowman);
      var4 = afk.a(var4, _snowman);
      long var6 = afk.a(_snowman, var4);
      var6 = afk.a(var6, var4);
      return afk.a(var6, var4);
   }
}
