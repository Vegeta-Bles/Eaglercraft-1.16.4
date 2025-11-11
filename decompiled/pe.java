import java.io.IOException;

public class pe implements oj<om> {
   private int a;
   private short b;
   private boolean c;

   public pe() {
   }

   public pe(int var1, short var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readUnsignedByte();
      this.b = _snowman.readShort();
      this.c = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeShort(this.b);
      _snowman.writeBoolean(this.c);
   }

   public int b() {
      return this.a;
   }

   public short c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
