import com.mojang.authlib.GameProfile;
import java.io.IOException;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class zf extends acu {
   private static final Logger a = LogManager.getLogger();

   public zf(zg var1, gn.b var2, cyk var3) {
      super(_snowman, _snowman, _snowman, _snowman.g_().K);
      zh _snowman = _snowman.g_();
      this.a(_snowman.J);
      super.a(_snowman.V.get());
      this.y();
      this.w();
      this.x();
      this.v();
      this.z();
      this.B();
      this.A();
      if (!this.i().b().exists()) {
         this.C();
      }
   }

   @Override
   public void a(boolean var1) {
      super.a(_snowman);
      this.b().j(_snowman);
   }

   @Override
   public void a(GameProfile var1) {
      super.a(_snowman);
      this.A();
   }

   @Override
   public void b(GameProfile var1) {
      super.b(_snowman);
      this.A();
   }

   @Override
   public void a() {
      this.B();
   }

   private void v() {
      try {
         this.g().e();
      } catch (IOException var2) {
         a.warn("Failed to save ip banlist: ", var2);
      }
   }

   private void w() {
      try {
         this.f().e();
      } catch (IOException var2) {
         a.warn("Failed to save user banlist: ", var2);
      }
   }

   private void x() {
      try {
         this.g().f();
      } catch (IOException var2) {
         a.warn("Failed to load ip banlist: ", var2);
      }
   }

   private void y() {
      try {
         this.f().f();
      } catch (IOException var2) {
         a.warn("Failed to load user banlist: ", var2);
      }
   }

   private void z() {
      try {
         this.k().f();
      } catch (Exception var2) {
         a.warn("Failed to load operators list: ", var2);
      }
   }

   private void A() {
      try {
         this.k().e();
      } catch (Exception var2) {
         a.warn("Failed to save operators list: ", var2);
      }
   }

   private void B() {
      try {
         this.i().f();
      } catch (Exception var2) {
         a.warn("Failed to load white-list: ", var2);
      }
   }

   private void C() {
      try {
         this.i().e();
      } catch (Exception var2) {
         a.warn("Failed to save white-list: ", var2);
      }
   }

   @Override
   public boolean e(GameProfile var1) {
      return !this.o() || this.h(_snowman) || this.i().a(_snowman);
   }

   public zg b() {
      return (zg)super.c();
   }

   @Override
   public boolean f(GameProfile var1) {
      return this.k().b(_snowman);
   }
}
