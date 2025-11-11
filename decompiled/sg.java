import java.io.IOException;

public class sg implements oj<sa> {
   private String a;
   private int b;
   private bfu c;
   private boolean d;
   private int e;
   private aqi f;

   public sg() {
   }

   public sg(String var1, int var2, bfu var3, boolean var4, int var5, aqi var6) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e(16);
      this.b = _snowman.readByte();
      this.c = _snowman.a(bfu.class);
      this.d = _snowman.readBoolean();
      this.e = _snowman.readUnsignedByte();
      this.f = _snowman.a(aqi.class);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.writeByte(this.b);
      _snowman.a(this.c);
      _snowman.writeBoolean(this.d);
      _snowman.writeByte(this.e);
      _snowman.a(this.f);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public bfu d() {
      return this.c;
   }

   public boolean e() {
      return this.d;
   }

   public int f() {
      return this.e;
   }

   public aqi g() {
      return this.f;
   }
}
