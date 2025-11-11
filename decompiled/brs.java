import it.unimi.dsi.fastutil.longs.LongOpenHashSet;
import it.unimi.dsi.fastutil.longs.LongSet;

public class brs extends cxs {
   private LongSet a = new LongOpenHashSet();

   public brs() {
      super("chunks");
   }

   @Override
   public void a(md var1) {
      this.a = new LongOpenHashSet(_snowman.o("Forced"));
   }

   @Override
   public md b(md var1) {
      _snowman.a("Forced", this.a.toLongArray());
      return _snowman;
   }

   public LongSet a() {
      return this.a;
   }
}
