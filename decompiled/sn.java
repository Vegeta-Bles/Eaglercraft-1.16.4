import java.io.IOException;

public class sn implements oj<sa> {
   private bmb a;
   private boolean b;
   private int c;

   public sn() {
   }

   public sn(bmb var1, boolean var2, int var3) {
      this.a = _snowman.i();
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.n();
      this.b = _snowman.readBoolean();
      this.c = _snowman.i();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeBoolean(this.b);
      _snowman.d(this.c);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public bmb b() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }
}
