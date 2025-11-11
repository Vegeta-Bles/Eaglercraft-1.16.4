import java.io.IOException;

public class tc implements oj<sa> {
   private bjk a;
   private boolean b;
   private boolean c;

   public tc() {
   }

   public tc(bjk var1, boolean var2, boolean var3) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(bjk.class);
      this.b = _snowman.readBoolean();
      this.c = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeBoolean(this.b);
      _snowman.writeBoolean(this.c);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public bjk b() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }
}
