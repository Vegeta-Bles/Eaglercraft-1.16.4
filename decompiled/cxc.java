import it.unimi.dsi.fastutil.ints.Int2ObjectMap;
import it.unimi.dsi.fastutil.ints.Int2ObjectOpenHashMap;

public abstract class cxc {
   protected bsi a;
   protected aqn b;
   protected final Int2ObjectMap<cxb> c = new Int2ObjectOpenHashMap();
   protected int d;
   protected int e;
   protected int f;
   protected boolean g;
   protected boolean h;
   protected boolean i;

   public cxc() {
   }

   public void a(bsi var1, aqn var2) {
      this.a = _snowman;
      this.b = _snowman;
      this.c.clear();
      this.d = afm.d(_snowman.cy() + 1.0F);
      this.e = afm.d(_snowman.cz() + 1.0F);
      this.f = afm.d(_snowman.cy() + 1.0F);
   }

   public void a() {
      this.a = null;
      this.b = null;
   }

   protected cxb a(fx var1) {
      return this.a(_snowman.u(), _snowman.v(), _snowman.w());
   }

   protected cxb a(int var1, int var2, int var3) {
      return (cxb)this.c.computeIfAbsent(cxb.b(_snowman, _snowman, _snowman), var3x -> new cxb(_snowman, _snowman, _snowman));
   }

   public abstract cxb b();

   public abstract cxh a(double var1, double var3, double var5);

   public abstract int a(cxb[] var1, cxb var2);

   public abstract cwz a(brc var1, int var2, int var3, int var4, aqn var5, int var6, int var7, int var8, boolean var9, boolean var10);

   public abstract cwz a(brc var1, int var2, int var3, int var4);

   public void a(boolean var1) {
      this.g = _snowman;
   }

   public void b(boolean var1) {
      this.h = _snowman;
   }

   public void c(boolean var1) {
      this.i = _snowman;
   }

   public boolean c() {
      return this.g;
   }

   public boolean d() {
      return this.h;
   }

   public boolean e() {
      return this.i;
   }
}
