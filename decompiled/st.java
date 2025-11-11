import java.io.IOException;

public class st implements oj<sa> {
   protected double a;
   protected double b;
   protected double c;
   protected float d;
   protected float e;
   protected boolean f;
   protected boolean g;
   protected boolean h;

   public st() {
   }

   public st(boolean var1) {
      this.f = _snowman;
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      this.f = _snowman.readUnsignedByte() != 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.f ? 1 : 0);
   }

   public double a(double var1) {
      return this.g ? this.a : _snowman;
   }

   public double b(double var1) {
      return this.g ? this.b : _snowman;
   }

   public double c(double var1) {
      return this.g ? this.c : _snowman;
   }

   public float a(float var1) {
      return this.h ? this.d : _snowman;
   }

   public float b(float var1) {
      return this.h ? this.e : _snowman;
   }

   public boolean b() {
      return this.f;
   }

   public static class a extends st {
      public a() {
         this.g = true;
      }

      public a(double var1, double var3, double var5, boolean var7) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.f = _snowman;
         this.g = true;
      }

      @Override
      public void a(nf var1) throws IOException {
         this.a = _snowman.readDouble();
         this.b = _snowman.readDouble();
         this.c = _snowman.readDouble();
         super.a(_snowman);
      }

      @Override
      public void b(nf var1) throws IOException {
         _snowman.writeDouble(this.a);
         _snowman.writeDouble(this.b);
         _snowman.writeDouble(this.c);
         super.b(_snowman);
      }
   }

   public static class b extends st {
      public b() {
         this.g = true;
         this.h = true;
      }

      public b(double var1, double var3, double var5, float var7, float var8, boolean var9) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.h = true;
         this.g = true;
      }

      @Override
      public void a(nf var1) throws IOException {
         this.a = _snowman.readDouble();
         this.b = _snowman.readDouble();
         this.c = _snowman.readDouble();
         this.d = _snowman.readFloat();
         this.e = _snowman.readFloat();
         super.a(_snowman);
      }

      @Override
      public void b(nf var1) throws IOException {
         _snowman.writeDouble(this.a);
         _snowman.writeDouble(this.b);
         _snowman.writeDouble(this.c);
         _snowman.writeFloat(this.d);
         _snowman.writeFloat(this.e);
         super.b(_snowman);
      }
   }

   public static class c extends st {
      public c() {
         this.h = true;
      }

      public c(float var1, float var2, boolean var3) {
         this.d = _snowman;
         this.e = _snowman;
         this.f = _snowman;
         this.h = true;
      }

      @Override
      public void a(nf var1) throws IOException {
         this.d = _snowman.readFloat();
         this.e = _snowman.readFloat();
         super.a(_snowman);
      }

      @Override
      public void b(nf var1) throws IOException {
         _snowman.writeFloat(this.d);
         _snowman.writeFloat(this.e);
         super.b(_snowman);
      }
   }
}
