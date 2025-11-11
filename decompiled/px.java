import com.google.common.collect.Sets;
import java.io.IOException;
import java.util.Set;
import java.util.function.Supplier;

public class px implements oj<om> {
   private int a;
   private long b;
   private boolean c;
   private bru d;
   private bru e;
   private Set<vj<brx>> f;
   private gn.b g;
   private chd h;
   private vj<brx> i;
   private int j;
   private int k;
   private boolean l;
   private boolean m;
   private boolean n;
   private boolean o;

   public px() {
   }

   public px(
      int var1,
      bru var2,
      bru var3,
      long var4,
      boolean var6,
      Set<vj<brx>> var7,
      gn.b var8,
      chd var9,
      vj<brx> var10,
      int var11,
      int var12,
      boolean var13,
      boolean var14,
      boolean var15,
      boolean var16
   ) {
      this.a = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
      this.i = _snowman;
      this.b = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.j = _snowman;
      this.c = _snowman;
      this.k = _snowman;
      this.l = _snowman;
      this.m = _snowman;
      this.n = _snowman;
      this.o = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.c = _snowman.readBoolean();
      this.d = bru.a(_snowman.readByte());
      this.e = bru.a(_snowman.readByte());
      int _snowman = _snowman.i();
      this.f = Sets.newHashSet();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.f.add(vj.a(gm.L, _snowman.p()));
      }

      this.g = _snowman.a(gn.b.a);
      this.h = _snowman.<Supplier<chd>>a(chd.n).get();
      this.i = vj.a(gm.L, _snowman.p());
      this.b = _snowman.readLong();
      this.j = _snowman.i();
      this.k = _snowman.i();
      this.l = _snowman.readBoolean();
      this.m = _snowman.readBoolean();
      this.n = _snowman.readBoolean();
      this.o = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.writeBoolean(this.c);
      _snowman.writeByte(this.d.a());
      _snowman.writeByte(this.e.a());
      _snowman.d(this.f.size());

      for (vj<brx> _snowman : this.f) {
         _snowman.a(_snowman.a());
      }

      _snowman.a(gn.b.a, this.g);
      _snowman.a(chd.n, () -> this.h);
      _snowman.a(this.i.a());
      _snowman.writeLong(this.b);
      _snowman.d(this.j);
      _snowman.d(this.k);
      _snowman.writeBoolean(this.l);
      _snowman.writeBoolean(this.m);
      _snowman.writeBoolean(this.n);
      _snowman.writeBoolean(this.o);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public long c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }

   public bru e() {
      return this.d;
   }

   public bru f() {
      return this.e;
   }

   public Set<vj<brx>> g() {
      return this.f;
   }

   public gn h() {
      return this.g;
   }

   public chd i() {
      return this.h;
   }

   public vj<brx> j() {
      return this.i;
   }

   public int l() {
      return this.k;
   }

   public boolean m() {
      return this.l;
   }

   public boolean n() {
      return this.m;
   }

   public boolean o() {
      return this.n;
   }

   public boolean p() {
      return this.o;
   }
}
