import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;

public class rj implements oj<om> {
   private String a = "";
   @Nullable
   private String b;
   private int c;
   private wa.a d;

   public rj() {
   }

   public rj(wa.a var1, @Nullable String var2, String var3, int var4) {
      if (_snowman != wa.a.b && _snowman == null) {
         throw new IllegalArgumentException("Need an objective name");
      } else {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.d = _snowman;
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.e(40);
      this.d = _snowman.a(wa.a.class);
      String _snowman = _snowman.e(16);
      this.b = Objects.equals(_snowman, "") ? null : _snowman;
      if (this.d != wa.a.b) {
         this.c = _snowman.i();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.a(this.d);
      _snowman.a(this.b == null ? "" : this.b);
      if (this.d != wa.a.b) {
         _snowman.d(this.c);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public String b() {
      return this.a;
   }

   @Nullable
   public String c() {
      return this.b;
   }

   public int d() {
      return this.c;
   }

   public wa.a e() {
      return this.d;
   }
}
