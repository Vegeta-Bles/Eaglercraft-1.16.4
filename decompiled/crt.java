import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class crt extends cxs {
   private LongSet a = new LongOpenHashSet();
   private LongSet b = new LongOpenHashSet();

   public crt(String var1) {
      super(_snowman);
   }

   @Override
   public void a(md var1) {
      this.a = new LongOpenHashSet(_snowman.o("All"));
      this.b = new LongOpenHashSet(_snowman.o("Remaining"));
   }

   @Override
   public md b(md var1) {
      _snowman.a("All", this.a.toLongArray());
      _snowman.a("Remaining", this.b.toLongArray());
      return _snowman;
   }

   public void a(long var1) {
      this.a.add(_snowman);
      this.b.add(_snowman);
   }

   public boolean b(long var1) {
      return this.a.contains(_snowman);
   }

   public boolean c(long var1) {
      return this.b.contains(_snowman);
   }

   public void d(long var1) {
      this.b.remove(_snowman);
   }

   public LongSet a() {
      return this.a;
   }
}
