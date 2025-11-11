import java.io.IOException;

public class sv implements oj<sa> {
   private boolean a;
   private boolean b;

   public sv() {
   }

   public sv(boolean var1, boolean var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readBoolean();
      this.b = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeBoolean(this.a);
      _snowman.writeBoolean(this.b);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public boolean b() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }
}
