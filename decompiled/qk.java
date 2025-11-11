import java.io.IOException;
import java.util.EnumSet;
import java.util.Set;

public class qk implements oj<om> {
   private double a;
   private double b;
   private double c;
   private float d;
   private float e;
   private Set<qk.a> f;
   private int g;

   public qk() {
   }

   public qk(double var1, double var3, double var5, float var7, float var8, Set<qk.a> var9, int var10) {
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readDouble();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      this.d = _snowman.readFloat();
      this.e = _snowman.readFloat();
      this.f = qk.a.a(_snowman.readUnsignedByte());
      this.g = _snowman.i();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeDouble(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeFloat(this.d);
      _snowman.writeFloat(this.e);
      _snowman.writeByte(qk.a.a(this.f));
      _snowman.d(this.g);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public double b() {
      return this.a;
   }

   public double c() {
      return this.b;
   }

   public double d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public float f() {
      return this.e;
   }

   public int g() {
      return this.g;
   }

   public Set<qk.a> h() {
      return this.f;
   }

   public static enum a {
      a(0),
      b(1),
      c(2),
      d(3),
      e(4);

      private final int f;

      private a(int var3) {
         this.f = _snowman;
      }

      private int a() {
         return 1 << this.f;
      }

      private boolean b(int var1) {
         return (_snowman & this.a()) == this.a();
      }

      public static Set<qk.a> a(int var0) {
         Set<qk.a> _snowman = EnumSet.noneOf(qk.a.class);

         for (qk.a _snowmanx : values()) {
            if (_snowmanx.b(_snowman)) {
               _snowman.add(_snowmanx);
            }
         }

         return _snowman;
      }

      public static int a(Set<qk.a> var0) {
         int _snowman = 0;

         for (qk.a _snowmanx : _snowman) {
            _snowman |= _snowmanx.a();
         }

         return _snowman;
      }
   }
}
