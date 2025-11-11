import java.io.IOException;

public class qh implements oj<om> {
   public qh.a a;
   public int b;
   public int c;
   public int d;
   public nr e;

   public qh() {
   }

   public qh(apj var1, qh.a var2) {
      this(_snowman, _snowman, oe.d);
   }

   public qh(apj var1, qh.a var2, nr var3) {
      this.a = _snowman;
      aqm _snowman = _snowman.c();
      switch (_snowman) {
         case b:
            this.d = _snowman.f();
            this.c = _snowman == null ? -1 : _snowman.Y();
            break;
         case c:
            this.b = _snowman.h().Y();
            this.c = _snowman == null ? -1 : _snowman.Y();
            this.e = _snowman;
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(qh.a.class);
      if (this.a == qh.a.b) {
         this.d = _snowman.i();
         this.c = _snowman.readInt();
      } else if (this.a == qh.a.c) {
         this.b = _snowman.i();
         this.c = _snowman.readInt();
         this.e = _snowman.h();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      if (this.a == qh.a.b) {
         _snowman.d(this.d);
         _snowman.writeInt(this.c);
      } else if (this.a == qh.a.c) {
         _snowman.d(this.b);
         _snowman.writeInt(this.c);
         _snowman.a(this.e);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public boolean a() {
      return this.a == qh.a.c;
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
