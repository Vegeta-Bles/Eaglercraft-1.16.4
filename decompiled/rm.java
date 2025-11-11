import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class rm implements oj<om> {
   private adp a;
   private adr b;
   private int c;
   private float d;
   private float e;

   public rm() {
   }

   public rm(adp var1, adr var2, aqa var3, float var4, float var5) {
      Validate.notNull(_snowman, "sound", new Object[0]);
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman.Y();
      this.d = _snowman;
      this.e = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = gm.N.a(_snowman.i());
      this.b = _snowman.a(adr.class);
      this.c = _snowman.i();
      this.d = _snowman.readFloat();
      this.e = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(gm.N.a(this.a));
      _snowman.a(this.b);
      _snowman.d(this.c);
      _snowman.writeFloat(this.d);
      _snowman.writeFloat(this.e);
   }

   public adp b() {
      return this.a;
   }

   public adr c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public void a(om var1) {
      _snowman.a(this);
   }
}
