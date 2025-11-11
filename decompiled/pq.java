import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;
import java.io.IOException;

public class pq implements oj<om> {
   public static final pq.a a = new pq.a(0);
   public static final pq.a b = new pq.a(1);
   public static final pq.a c = new pq.a(2);
   public static final pq.a d = new pq.a(3);
   public static final pq.a e = new pq.a(4);
   public static final pq.a f = new pq.a(5);
   public static final pq.a g = new pq.a(6);
   public static final pq.a h = new pq.a(7);
   public static final pq.a i = new pq.a(8);
   public static final pq.a j = new pq.a(9);
   public static final pq.a k = new pq.a(10);
   public static final pq.a l = new pq.a(11);
   private pq.a m;
   private float n;

   public pq() {
   }

   public pq(pq.a var1, float var2) {
      this.m = _snowman;
      this.n = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.m = (pq.a)pq.a.a.get(_snowman.readUnsignedByte());
      this.n = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.m.b);
      _snowman.writeFloat(this.n);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public pq.a b() {
      return this.m;
   }

   public float c() {
      return this.n;
   }

   public static class a {
      private static final Int2ObjectMap<pq.a> a = new Int2ObjectOpenHashMap();
      private final int b;

      public a(int var1) {
         this.b = _snowman;
         a.put(_snowman, this);
      }
   }
}
