import java.io.IOException;
import java.util.function.Supplier;

public class qp implements oj<om> {
   private chd a;
   private vj<brx> b;
   private long c;
   private bru d;
   private bru e;
   private boolean f;
   private boolean g;
   private boolean h;

   public qp() {
   }

   public qp(chd var1, vj<brx> var2, long var3, bru var5, bru var6, boolean var7, boolean var8, boolean var9) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.<Supplier<chd>>a(chd.n).get();
      this.b = vj.a(gm.L, _snowman.p());
      this.c = _snowman.readLong();
      this.d = bru.a(_snowman.readUnsignedByte());
      this.e = bru.a(_snowman.readUnsignedByte());
      this.f = _snowman.readBoolean();
      this.g = _snowman.readBoolean();
      this.h = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(chd.n, () -> this.a);
      _snowman.a(this.b.a());
      _snowman.writeLong(this.c);
      _snowman.writeByte(this.d.a());
      _snowman.writeByte(this.e.a());
      _snowman.writeBoolean(this.f);
      _snowman.writeBoolean(this.g);
      _snowman.writeBoolean(this.h);
   }

   public chd b() {
      return this.a;
   }

   public vj<brx> c() {
      return this.b;
   }

   public long d() {
      return this.c;
   }

   public bru e() {
      return this.d;
   }

   public bru f() {
      return this.e;
   }

   public boolean g() {
      return this.f;
   }

   public boolean h() {
      return this.g;
   }

   public boolean i() {
      return this.h;
   }
}
