import java.io.IOException;

public class qg implements oj<om> {
   private boolean a;
   private boolean b;
   private boolean c;
   private boolean d;
   private float e;
   private float f;

   public qg() {
   }

   public qg(bft var1) {
      this.a = _snowman.a;
      this.b = _snowman.b;
      this.c = _snowman.c;
      this.d = _snowman.d;
      this.e = _snowman.a();
      this.f = _snowman.b();
   }

   @Override
   public void a(nf var1) throws IOException {
      byte _snowman = _snowman.readByte();
      this.a = (_snowman & 1) != 0;
      this.b = (_snowman & 2) != 0;
      this.c = (_snowman & 4) != 0;
      this.d = (_snowman & 8) != 0;
      this.e = _snowman.readFloat();
      this.f = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      byte _snowman = 0;
      if (this.a) {
         _snowman = (byte)(_snowman | 1);
      }

      if (this.b) {
         _snowman = (byte)(_snowman | 2);
      }

      if (this.c) {
         _snowman = (byte)(_snowman | 4);
      }

      if (this.d) {
         _snowman = (byte)(_snowman | 8);
      }

      _snowman.writeByte(_snowman);
      _snowman.writeFloat(this.e);
      _snowman.writeFloat(this.f);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public boolean b() {
      return this.a;
   }

   public boolean c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }

   public boolean e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public float g() {
      return this.f;
   }
}
