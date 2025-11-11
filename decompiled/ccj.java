import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public abstract class ccj {
   private static final Logger a = LogManager.getLogger();
   private final cck<?> b;
   @Nullable
   protected brx d;
   protected fx e = fx.b;
   protected boolean f;
   @Nullable
   private ceh c;
   private boolean g;

   public ccj(cck<?> var1) {
      this.b = _snowman;
   }

   @Nullable
   public brx v() {
      return this.d;
   }

   public void a(brx var1, fx var2) {
      this.d = _snowman;
      this.e = _snowman.h();
   }

   public boolean n() {
      return this.d != null;
   }

   public void a(ceh var1, md var2) {
      this.e = new fx(_snowman.h("x"), _snowman.h("y"), _snowman.h("z"));
   }

   public md a(md var1) {
      return this.b(_snowman);
   }

   private md b(md var1) {
      vk _snowman = cck.a(this.u());
      if (_snowman == null) {
         throw new RuntimeException(this.getClass() + " is missing a mapping! This is a bug!");
      } else {
         _snowman.a("id", _snowman.toString());
         _snowman.b("x", this.e.u());
         _snowman.b("y", this.e.v());
         _snowman.b("z", this.e.w());
         return _snowman;
      }
   }

   @Nullable
   public static ccj b(ceh var0, md var1) {
      String _snowman = _snowman.l("id");
      return gm.W.b(new vk(_snowman)).map(var1x -> {
         try {
            return var1x.a();
         } catch (Throwable var3) {
            a.error("Failed to create block entity {}", _snowman, var3);
            return null;
         }
      }).map(var3 -> {
         try {
            var3.a(_snowman, _snowman);
            return (ccj)var3;
         } catch (Throwable var5) {
            a.error("Failed to load data for block entity {}", _snowman, var5);
            return null;
         }
      }).orElseGet(() -> {
         a.warn("Skipping BlockEntity with id {}", _snowman);
         return null;
      });
   }

   public void X_() {
      if (this.d != null) {
         this.c = this.d.d_(this.e);
         this.d.b(this.e, this);
         if (!this.c.g()) {
            this.d.c(this.e, this.c.b());
         }
      }
   }

   public double i() {
      return 64.0;
   }

   public fx o() {
      return this.e;
   }

   public ceh p() {
      if (this.c == null) {
         this.c = this.d.d_(this.e);
      }

      return this.c;
   }

   @Nullable
   public ow a() {
      return null;
   }

   public md b() {
      return this.b(new md());
   }

   public boolean q() {
      return this.f;
   }

   public void al_() {
      this.f = true;
   }

   public void r() {
      this.f = false;
   }

   public boolean a_(int var1, int var2) {
      return false;
   }

   public void s() {
      this.c = null;
   }

   public void a(m var1) {
      _snowman.a("Name", () -> gm.W.b(this.u()) + " // " + this.getClass().getCanonicalName());
      if (this.d != null) {
         m.a(_snowman, this.e, this.p());
         m.a(_snowman, this.e, this.d.d_(this.e));
      }
   }

   public void a(fx var1) {
      this.e = _snowman.h();
   }

   public boolean t() {
      return false;
   }

   public void a(bzm var1) {
   }

   public void a(byg var1) {
   }

   public cck<?> u() {
      return this.b;
   }

   public void w() {
      if (!this.g) {
         this.g = true;
         a.warn("Block entity invalid: {} @ {}", new Supplier[]{() -> gm.W.b(this.u()), this::o});
      }
   }
}
