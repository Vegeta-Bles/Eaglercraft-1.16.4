import java.io.IOException;

public class pa implements oj<om> {
   private aor a;
   private boolean b;

   public pa() {
   }

   public pa(aor var1, boolean var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = aor.a(_snowman.readUnsignedByte());
      this.b = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a.a());
      _snowman.writeBoolean(this.b);
   }

   public boolean b() {
      return this.b;
   }

   public aor c() {
      return this.a;
   }
}
