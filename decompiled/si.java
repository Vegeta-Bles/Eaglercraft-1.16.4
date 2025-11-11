import java.io.IOException;

public class si implements oj<sa> {
   private int a;
   private short b;
   private boolean c;

   public si() {
   }

   public si(int var1, short var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readByte();
      this.b = _snowman.readShort();
      this.c = _snowman.readByte() != 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeShort(this.b);
      _snowman.writeByte(this.c ? 1 : 0);
   }

   public int b() {
      return this.a;
   }

   public short c() {
      return this.b;
   }
}
