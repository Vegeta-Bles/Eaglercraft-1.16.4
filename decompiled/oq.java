import java.io.IOException;
import java.util.UUID;

public class oq implements oj<om> {
   private int a;
   private UUID b;
   private fx c;
   private gc d;
   private int e;

   public oq() {
   }

   public oq(bcs var1) {
      this.a = _snowman.Y();
      this.b = _snowman.bS();
      this.c = _snowman.n();
      this.d = _snowman.bZ();
      this.e = gm.X.a(_snowman.e);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.k();
      this.e = _snowman.i();
      this.c = _snowman.e();
      this.d = gc.b(_snowman.readUnsignedByte());
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      _snowman.d(this.e);
      _snowman.a(this.c);
      _snowman.writeByte(this.d.d());
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public UUID c() {
      return this.b;
   }

   public fx d() {
      return this.c;
   }

   public gc e() {
      return this.d;
   }

   public bcr f() {
      return gm.X.a(this.e);
   }
}
