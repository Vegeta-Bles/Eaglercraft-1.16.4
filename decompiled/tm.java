import java.io.IOException;

public class tm implements oj<sa> {
   private int a;
   private bmb b;

   public tm() {
      this.b = bmb.b;
   }

   public tm(int var1, bmb var2) {
      this.b = bmb.b;
      this.a = _snowman;
      this.b = _snowman.i();
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readShort();
      this.b = _snowman.n();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeShort(this.a);
      _snowman.a(this.b);
   }

   public int b() {
      return this.a;
   }

   public bmb c() {
      return this.b;
   }
}
