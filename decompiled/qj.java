import java.io.IOException;
import javax.annotation.Nullable;

public class qj implements oj<om> {
   private double a;
   private double b;
   private double c;
   private int d;
   private dj.a e;
   private dj.a f;
   private boolean g;

   public qj() {
   }

   public qj(dj.a var1, double var2, double var4, double var6) {
      this.e = _snowman;
      this.a = _snowman;
      this.b = _snowman;
      this.c = _snowman;
   }

   public qj(dj.a var1, aqa var2, dj.a var3) {
      this.e = _snowman;
      this.d = _snowman.Y();
      this.f = _snowman;
      dcn _snowman = _snowman.a(_snowman);
      this.a = _snowman.b;
      this.b = _snowman.c;
      this.c = _snowman.d;
      this.g = true;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.e = _snowman.a(dj.a.class);
      this.a = _snowman.readDouble();
      this.b = _snowman.readDouble();
      this.c = _snowman.readDouble();
      if (_snowman.readBoolean()) {
         this.g = true;
         this.d = _snowman.i();
         this.f = _snowman.a(dj.a.class);
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.e);
      _snowman.writeDouble(this.a);
      _snowman.writeDouble(this.b);
      _snowman.writeDouble(this.c);
      _snowman.writeBoolean(this.g);
      if (this.g) {
         _snowman.d(this.d);
         _snowman.a(this.f);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public dj.a b() {
      return this.e;
   }

   @Nullable
   public dcn a(brx var1) {
      if (this.g) {
         aqa _snowman = _snowman.a(this.d);
         return _snowman == null ? new dcn(this.a, this.b, this.c) : this.f.a(_snowman);
      } else {
         return new dcn(this.a, this.b, this.c);
      }
   }
}
