import java.io.IOException;

public class pz implements oj<om> {
   private int a;
   private bqw b;
   private int c;
   private int d;
   private boolean e;
   private boolean f;

   public pz() {
   }

   public pz(int var1, bqw var2, int var3, int var4, boolean var5, boolean var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = bqw.b(_snowman);
      this.c = _snowman.i();
      this.d = _snowman.i();
      this.e = _snowman.readBoolean();
      this.f = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      this.b.a(_snowman);
      _snowman.d(this.c);
      _snowman.d(this.d);
      _snowman.writeBoolean(this.e);
      _snowman.writeBoolean(this.f);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public bqw c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public int e() {
      return this.d;
   }

   public boolean f() {
      return this.e;
   }

   public boolean g() {
      return this.f;
   }
}
