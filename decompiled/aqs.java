import java.util.Objects;
import java.util.UUID;
import javax.annotation.Nullable;

public interface aqs {
   int E_();

   void a_(int var1);

   @Nullable
   UUID F_();

   void a(@Nullable UUID var1);

   void G_();

   default void c(md var1) {
      _snowman.b("AngerTime", this.E_());
      if (this.F_() != null) {
         _snowman.a("AngryAt", this.F_());
      }
   }

   default void a(aag var1, md var2) {
      this.a_(_snowman.h("AngerTime"));
      if (!_snowman.b("AngryAt")) {
         this.a(null);
      } else {
         UUID _snowman = _snowman.a("AngryAt");
         this.a(_snowman);
         aqa _snowmanx = _snowman.a(_snowman);
         if (_snowmanx != null) {
            if (_snowmanx instanceof aqn) {
               this.a((aqn)_snowmanx);
            }

            if (_snowmanx.X() == aqe.bc) {
               this.e((bfw)_snowmanx);
            }
         }
      }
   }

   default void a(aag var1, boolean var2) {
      aqm _snowman = this.A();
      UUID _snowmanx = this.F_();
      if ((_snowman == null || _snowman.dl()) && _snowmanx != null && _snowman.a(_snowmanx) instanceof aqn) {
         this.J_();
      } else {
         if (_snowman != null && !Objects.equals(_snowmanx, _snowman.bS())) {
            this.a(_snowman.bS());
            this.G_();
         }

         if (this.E_() > 0 && (_snowman == null || _snowman.X() != aqe.bc || !_snowman)) {
            this.a_(this.E_() - 1);
            if (this.E_() == 0) {
               this.J_();
            }
         }
      }
   }

   default boolean a_(aqm var1) {
      if (!aqd.f.test(_snowman)) {
         return false;
      } else {
         return _snowman.X() == aqe.bc && this.a(_snowman.l) ? true : _snowman.bS().equals(this.F_());
      }
   }

   default boolean a(brx var1) {
      return _snowman.V().b(brt.G) && this.H_() && this.F_() == null;
   }

   default boolean H_() {
      return this.E_() > 0;
   }

   default void b(bfw var1) {
      if (_snowman.l.V().b(brt.F)) {
         if (_snowman.bS().equals(this.F_())) {
            this.J_();
         }
      }
   }

   default void I_() {
      this.J_();
      this.G_();
   }

   default void J_() {
      this.a(null);
      this.a(null);
      this.h(null);
      this.a_(0);
   }

   void a(@Nullable aqm var1);

   void e(@Nullable bfw var1);

   void h(@Nullable aqm var1);

   @Nullable
   aqm A();
}
