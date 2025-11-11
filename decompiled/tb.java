import java.io.IOException;

public class tb implements oj<sa> {
   private float a;
   private float b;
   private boolean c;
   private boolean d;

   public tb() {
   }

   public tb(float var1, float var2, boolean var3, boolean var4) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readFloat();
      this.b = _snowman.readFloat();
      byte _snowman = _snowman.readByte();
      this.c = (_snowman & 1) > 0;
      this.d = (_snowman & 2) > 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeFloat(this.a);
      _snowman.writeFloat(this.b);
      byte _snowman = 0;
      if (this.c) {
         _snowman = (byte)(_snowman | 1);
      }

      if (this.d) {
         _snowman = (byte)(_snowman | 2);
      }

      _snowman.writeByte(_snowman);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   public float b() {
      return this.a;
   }

   public float c() {
      return this.b;
   }

   public boolean d() {
      return this.c;
   }

   public boolean e() {
      return this.d;
   }
}
