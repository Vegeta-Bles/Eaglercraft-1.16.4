import java.io.IOException;

public class sj implements oj<sa> {
   private int a;
   private int b;

   public sj() {
   }

   public sj(int var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readByte();
      this.b = _snowman.readByte();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeByte(this.b);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
