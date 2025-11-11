import java.io.IOException;

public class qt implements oj<om> {
   private qt.a a;
   private int b;
   private double c;
   private double d;
   private double e;
   private double f;
   private long g;
   private int h;
   private int i;

   public qt() {
   }

   public qt(cfu var1, qt.a var2) {
      this.a = _snowman;
      this.c = _snowman.a();
      this.d = _snowman.b();
      this.f = _snowman.i();
      this.e = _snowman.k();
      this.g = _snowman.j();
      this.b = _snowman.m();
      this.i = _snowman.r();
      this.h = _snowman.q();
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(qt.a.class);
      switch (this.a) {
         case a:
            this.e = _snowman.readDouble();
            break;
         case b:
            this.f = _snowman.readDouble();
            this.e = _snowman.readDouble();
            this.g = _snowman.j();
            break;
         case c:
            this.c = _snowman.readDouble();
            this.d = _snowman.readDouble();
            break;
         case f:
            this.i = _snowman.i();
            break;
         case e:
            this.h = _snowman.i();
            break;
         case d:
            this.c = _snowman.readDouble();
            this.d = _snowman.readDouble();
            this.f = _snowman.readDouble();
            this.e = _snowman.readDouble();
            this.g = _snowman.j();
            this.b = _snowman.i();
            this.i = _snowman.i();
            this.h = _snowman.i();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      switch (this.a) {
         case a:
            _snowman.writeDouble(this.e);
            break;
         case b:
            _snowman.writeDouble(this.f);
            _snowman.writeDouble(this.e);
            _snowman.b(this.g);
            break;
         case c:
            _snowman.writeDouble(this.c);
            _snowman.writeDouble(this.d);
            break;
         case f:
            _snowman.d(this.i);
            break;
         case e:
            _snowman.d(this.h);
            break;
         case d:
            _snowman.writeDouble(this.c);
            _snowman.writeDouble(this.d);
            _snowman.writeDouble(this.f);
            _snowman.writeDouble(this.e);
            _snowman.b(this.g);
            _snowman.d(this.b);
            _snowman.d(this.i);
            _snowman.d(this.h);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public void a(cfu var1) {
      switch (this.a) {
         case a:
            _snowman.a(this.e);
            break;
         case b:
            _snowman.a(this.f, this.e, this.g);
            break;
         case c:
            _snowman.c(this.c, this.d);
            break;
         case f:
            _snowman.c(this.i);
            break;
         case e:
            _snowman.b(this.h);
            break;
         case d:
            _snowman.c(this.c, this.d);
            if (this.g > 0L) {
               _snowman.a(this.f, this.e, this.g);
            } else {
               _snowman.a(this.e);
            }

            _snowman.a(this.b);
            _snowman.c(this.i);
            _snowman.b(this.h);
      }
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
