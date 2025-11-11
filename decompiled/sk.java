import java.io.IOException;

public class sk implements oj<sa> {
   private int a;
   private int b;
   private int c;
   private short d;
   private bmb e = bmb.b;
   private bik f;

   public sk() {
   }

   public sk(int var1, int var2, int var3, bik var4, bmb var5, short var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.e = _snowman.i();
      this.d = _snowman;
      this.f = _snowman;
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readByte();
      this.b = _snowman.readShort();
      this.c = _snowman.readByte();
      this.d = _snowman.readShort();
      this.f = _snowman.a(bik.class);
      this.e = _snowman.n();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeShort(this.b);
      _snowman.writeByte(this.c);
      _snowman.writeShort(this.d);
      _snowman.a(this.f);
      _snowman.a(this.e);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public short e() {
      return this.d;
   }

   public bmb f() {
      return this.e;
   }

   public bik g() {
      return this.f;
   }
}
