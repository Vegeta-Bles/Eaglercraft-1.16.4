import java.io.IOException;
import org.apache.commons.lang3.Validate;

public class rn implements oj<om> {
   private adp a;
   private adr b;
   private int c;
   private int d;
   private int e;
   private float f;
   private float g;

   public rn() {
   }

   public rn(adp var1, adr var2, double var3, double var5, double var7, float var9, float var10) {
      Validate.notNull(_snowman, "sound", new Object[0]);
      this.a = _snowman;
      this.b = _snowman;
      this.c = (int)(_snowman * 8.0);
      this.d = (int)(_snowman * 8.0);
      this.e = (int)(_snowman * 8.0);
      this.f = _snowman;
      this.g = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = gm.N.a(_snowman.i());
      this.b = _snowman.a(adr.class);
      this.c = _snowman.readInt();
      this.d = _snowman.readInt();
      this.e = _snowman.readInt();
      this.f = _snowman.readFloat();
      this.g = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(gm.N.a(this.a));
      _snowman.a(this.b);
      _snowman.writeInt(this.c);
      _snowman.writeInt(this.d);
      _snowman.writeInt(this.e);
      _snowman.writeFloat(this.f);
      _snowman.writeFloat(this.g);
   }

   public adp b() {
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
