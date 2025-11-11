import java.io.IOException;

public class pl implements oj<om> {
   private vk a;
   private adr b;
   private int c;
   private int d = Integer.MAX_VALUE;
   private int e;
   private float f;
   private float g;

   public pl() {
   }

   public pl(vk var1, adr var2, dcn var3, float var4, float var5) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = (int)(_snowman.b * 8.0);
      this.d = (int)(_snowman.c * 8.0);
      this.e = (int)(_snowman.d * 8.0);
      this.f = _snowman;
      this.g = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.p();
      this.b = _snowman.a(adr.class);
      this.c = _snowman.readInt();
      this.d = _snowman.readInt();
      this.e = _snowman.readInt();
      this.f = _snowman.readFloat();
      this.g = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
      _snowman.writeInt(this.c);
      _snowman.writeInt(this.d);
      _snowman.writeInt(this.e);
      _snowman.writeFloat(this.f);
      _snowman.writeFloat(this.g);
   }

   public vk b() {
      return this.a;
   }

   public adr c() {
      return this.b;
   }

   public double d() {
      return (double)((float)this.c / 8.0F);
   }

   public double e() {
      return (double)((float)this.d / 8.0F);
   }

   public double f() {
      return (double)((float)this.e / 8.0F);
   }

   public float g() {
      return this.f;
   }

   public float h() {
      return this.g;
   }

   public void a(om var1) {
      _snowman.a(this);
   }
}
