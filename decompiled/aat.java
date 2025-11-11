import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;

public class aat implements aap {
   private final aar a;
   private final Long2ObjectOpenHashMap<cga> b;
   private brd c = new brd(0, 0);
   private final int d;
   private final int e;
   private final int f;
   private boolean g;

   public aat(int var1) {
      this.a = new aar(_snowman);
      this.d = _snowman * 2 + 1;
      this.e = _snowman + cga.b();
      this.f = this.e * 2 + 1;
      this.b = new Long2ObjectOpenHashMap();
   }

   @Override
   public void a(brd var1) {
      if (this.g) {
         this.a.a(_snowman);
         this.c = _snowman;
      }
   }

   @Override
   public void a(brd var1, @Nullable cga var2) {
      if (this.g) {
         this.a.a(_snowman, _snowman);
         if (_snowman == null) {
            this.b.remove(_snowman.a());
         } else {
            this.b.put(_snowman.a(), _snowman);
         }
      }
   }

   public void a() {
      this.g = true;
      this.b.clear();
   }

   @Override
   public void b() {
      this.g = false;
      this.a.b();
   }

   public int c() {
      return this.d;
   }

   public int d() {
      return this.f;
   }

   public int e() {
      return this.a.c();
   }

   @Nullable
   public cga a(int var1, int var2) {
      return (cga)this.b.get(brd.a(_snowman + this.c.b - this.e, _snowman + this.c.c - this.e));
   }
}
