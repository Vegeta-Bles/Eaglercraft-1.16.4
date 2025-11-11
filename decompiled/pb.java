import java.io.IOException;
import java.util.UUID;

public class pb implements oj<om> {
   private nr a;
   private no b;
   private UUID c;

   public pb() {
   }

   public pb(nr var1, no var2, UUID var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.h();
      this.b = no.a(_snowman.readByte());
      this.c = _snowman.k();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte(this.b.a());
      _snowman.a(this.c);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public nr b() {
      return this.a;
   }

   public boolean c() {
      return this.b == no.b || this.b == no.c;
   }

   public no d() {
      return this.b;
   }

   public UUID e() {
      return this.c;
   }

   @Override
   public boolean a() {
      return true;
   }
}
