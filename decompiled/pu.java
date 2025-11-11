import java.io.IOException;

public class pu implements oj<om> {
   private int a;
   private fx b;
   private int c;
   private boolean d;

   public pu() {
   }

   public pu(int var1, fx var2, int var3, boolean var4) {
      this.a = _snowman;
      this.b = _snowman.h();
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.b = _snowman.e();
      this.c = _snowman.readInt();
      this.d = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.a(this.b);
      _snowman.writeInt(this.c);
      _snowman.writeBoolean(this.d);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public boolean b() {
      return this.d;
   }

   public int c() {
      return this.a;
   }

   public int d() {
      return this.c;
   }

   public fx e() {
      return this.b;
   }
}
