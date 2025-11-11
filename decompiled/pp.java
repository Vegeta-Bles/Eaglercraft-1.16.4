import java.io.IOException;

public class pp implements oj<om> {
   private int a;
   private int b;

   public pp() {
   }

   public pp(int var1, int var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.b = _snowman.readInt();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.writeInt(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
