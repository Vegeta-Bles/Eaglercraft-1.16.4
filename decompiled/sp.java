import java.io.IOException;
import javax.annotation.Nullable;

public class sp implements oj<sa> {
   private int a;
   private sp.a b;
   private dcn c;
   private aot d;
   private boolean e;

   public sp() {
   }

   public sp(aqa var1, boolean var2) {
      this.a = _snowman.Y();
      this.b = sp.a.b;
      this.e = _snowman;
   }

   public sp(aqa var1, aot var2, boolean var3) {
      this.a = _snowman.Y();
      this.b = sp.a.a;
      this.d = _snowman;
      this.e = _snowman;
   }

   public sp(aqa var1, aot var2, dcn var3, boolean var4) {
      this.a = _snowman.Y();
      this.b = sp.a.c;
      this.d = _snowman;
      this.c = _snowman;
      this.e = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = _snowman.a(sp.a.class);
      if (this.b == sp.a.c) {
         this.c = new dcn((double)_snowman.readFloat(), (double)_snowman.readFloat(), (double)_snowman.readFloat());
      }

      if (this.b == sp.a.a || this.b == sp.a.c) {
         this.d = _snowman.a(aot.class);
      }

      this.e = _snowman.readBoolean();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.a(this.b);
      if (this.b == sp.a.c) {
         _snowman.writeFloat((float)this.c.b);
         _snowman.writeFloat((float)this.c.c);
         _snowman.writeFloat((float)this.c.d);
      }

      if (this.b == sp.a.a || this.b == sp.a.c) {
         _snowman.a(this.d);
      }

      _snowman.writeBoolean(this.e);
   }

   public void a(sa var1) {
      _snowman.a(this);
   }

   @Nullable
   public aqa a(brx var1) {
      return _snowman.a(this.a);
   }

   public sp.a b() {
      return this.b;
   }

   @Nullable
   public aot c() {
      return this.d;
   }

   public dcn d() {
      return this.c;
   }

   public boolean e() {
      return this.e;
   }

   public static enum a {
      a,
      b,
      c;

      private a() {
      }
   }
}
