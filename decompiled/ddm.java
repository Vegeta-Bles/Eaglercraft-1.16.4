import java.util.Comparator;
import javax.annotation.Nullable;

public class ddm {
   public static final Comparator<ddm> a = (var0, var1) -> {
      if (var0.b() > var1.b()) {
         return 1;
      } else {
         return var0.b() < var1.b() ? -1 : var1.e().compareToIgnoreCase(var0.e());
      }
   };
   private final ddn b;
   @Nullable
   private final ddk c;
   private final String d;
   private int e;
   private boolean f;
   private boolean g;

   public ddm(ddn var1, ddk var2, String var3) {
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
      this.f = true;
      this.g = true;
   }

   public void a(int var1) {
      if (this.c.c().d()) {
         throw new IllegalStateException("Cannot modify read-only score");
      } else {
         this.c(this.b() + _snowman);
      }
   }

   public void a() {
      this.a(1);
   }

   public int b() {
      return this.e;
   }

   public void c() {
      this.c(0);
   }

   public void c(int var1) {
      int _snowman = this.e;
      this.e = _snowman;
      if (_snowman != _snowman || this.g) {
         this.g = false;
         this.f().a(this);
      }
   }

   @Nullable
   public ddk d() {
      return this.c;
   }

   public String e() {
      return this.d;
   }

   public ddn f() {
      return this.b;
   }

   public boolean g() {
      return this.f;
   }

   public void a(boolean var1) {
      this.f = _snowman;
   }
}
