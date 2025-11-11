import com.google.common.collect.Lists;
import java.io.IOException;
import java.util.List;

public class po implements oj<om> {
   private double a;
   private double b;
   private double c;
   private float d;
   private List<fx> e;
   private float f;
   private float g;
   private float h;

   public po() {
   }

   public po(double var1, double var3, double var5, float var7, List<fx> var8, dcn var9) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = Lists.newArrayList(_snowman);
      if (_snowman != null) {
         this.f = (float)_snowman.b;
         this.g = (float)_snowman.c;
         this.h = (float)_snowman.d;
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = (double)_snowman.readFloat();
      this.b = (double)_snowman.readFloat();
      this.c = (double)_snowman.readFloat();
      this.d = _snowman.readFloat();
      int _snowman = _snowman.readInt();
      this.e = Lists.newArrayListWithCapacity(_snowman);
      int _snowmanx = afm.c(this.a);
      int _snowmanxx = afm.c(this.b);
      int _snowmanxxx = afm.c(this.c);

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowman; _snowmanxxxx++) {
         int _snowmanxxxxx = _snowman.readByte() + _snowmanx;
         int _snowmanxxxxxx = _snowman.readByte() + _snowmanxx;
         int _snowmanxxxxxxx = _snowman.readByte() + _snowmanxxx;
         this.e.add(new fx(_snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx));
      }

      this.f = _snowman.readFloat();
      this.g = _snowman.readFloat();
      this.h = _snowman.readFloat();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeFloat((float)this.a);
      _snowman.writeFloat((float)this.b);
      _snowman.writeFloat((float)this.c);
      _snowman.writeFloat(this.d);
      _snowman.writeInt(this.e.size());
      int _snowman = afm.c(this.a);
      int _snowmanx = afm.c(this.b);
      int _snowmanxx = afm.c(this.c);

      for (fx _snowmanxxx : this.e) {
         int _snowmanxxxx = _snowmanxxx.u() - _snowman;
         int _snowmanxxxxx = _snowmanxxx.v() - _snowmanx;
         int _snowmanxxxxxx = _snowmanxxx.w() - _snowmanxx;
         _snowman.writeByte(_snowmanxxxx);
         _snowman.writeByte(_snowmanxxxxx);
         _snowman.writeByte(_snowmanxxxxxx);
      }

      _snowman.writeFloat(this.f);
      _snowman.writeFloat(this.g);
      _snowman.writeFloat(this.h);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public float b() {
      return this.f;
   }

   public float c() {
      return this.g;
   }

   public float d() {
      return this.h;
   }

   public double e() {
      return this.a;
   }

   public double f() {
      return this.b;
   }

   public double g() {
      return this.c;
   }

   public float h() {
      return this.d;
   }

   public List<fx> i() {
      return this.e;
   }
}
