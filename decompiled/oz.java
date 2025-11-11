import java.io.IOException;
import java.util.UUID;

public class oz implements oj<om> {
   private UUID a;
   private oz.a b;
   private nr c;
   private float d;
   private aok.a e;
   private aok.b f;
   private boolean g;
   private boolean h;
   private boolean i;

   public oz() {
   }

   public oz(oz.a var1, aok var2) {
      this.b = _snowman;
      this.a = _snowman.i();
      this.c = _snowman.j();
      this.d = _snowman.k();
      this.e = _snowman.l();
      this.f = _snowman.m();
      this.g = _snowman.n();
      this.h = _snowman.o();
      this.i = _snowman.p();
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.k();
      this.b = _snowman.a(oz.a.class);
      switch (this.b) {
         case a:
            this.c = _snowman.h();
            this.d = _snowman.readFloat();
            this.e = _snowman.a(aok.a.class);
            this.f = _snowman.a(aok.b.class);
            this.a(_snowman.readUnsignedByte());
         case b:
         default:
            break;
         case c:
            this.d = _snowman.readFloat();
            break;
         case d:
            this.c = _snowman.h();
            break;
         case e:
            this.e = _snowman.a(aok.a.class);
            this.f = _snowman.a(aok.b.class);
            break;
         case f:
            this.a(_snowman.readUnsignedByte());
      }
   }

   private void a(int var1) {
      this.g = (_snowman & 1) > 0;
      this.h = (_snowman & 2) > 0;
      this.i = (_snowman & 4) > 0;
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.b);
      switch (this.b) {
         case a:
            _snowman.a(this.c);
            _snowman.writeFloat(this.d);
            _snowman.a(this.e);
            _snowman.a(this.f);
            _snowman.writeByte(this.k());
         case b:
         default:
            break;
         case c:
            _snowman.writeFloat(this.d);
            break;
         case d:
            _snowman.a(this.c);
            break;
         case e:
            _snowman.a(this.e);
            _snowman.a(this.f);
            break;
         case f:
            _snowman.writeByte(this.k());
      }
   }

   private int k() {
      int _snowman = 0;
      if (this.g) {
         _snowman |= 1;
      }

      if (this.h) {
         _snowman |= 2;
      }

      if (this.i) {
         _snowman |= 4;
      }

      return _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public UUID b() {
      return this.a;
   }

   public oz.a c() {
      return this.b;
   }

   public nr d() {
      return this.c;
   }

   public float e() {
      return this.d;
   }

   public aok.a f() {
      return this.e;
   }

   public aok.b g() {
      return this.f;
   }

   public boolean h() {
      return this.g;
   }

   public boolean i() {
      return this.h;
   }

   public boolean j() {
      return this.i;
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e,
      f;

      private a() {
      }
   }
}
