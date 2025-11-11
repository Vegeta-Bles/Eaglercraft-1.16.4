import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.server.MinecraftServer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class eng extends MinecraftServer {
   private static final Logger a = LogManager.getLogger();
   private final djz j;
   private boolean k;
   private int l = -1;
   private enj m;
   private UUID n;

   public eng(
      Thread var1, djz var2, gn.b var3, cyg.a var4, abw var5, vz var6, cyn var7, MinecraftSessionService var8, GameProfileRepository var9, acq var10, aaq var11
   ) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman.L(), _snowman.ai(), _snowman, _snowman, _snowman, _snowman, _snowman);
      this.d(_snowman.J().c());
      this.c(_snowman.v());
      this.c(256);
      this.a(new enf(this, this.f, this.e));
      this.j = _snowman;
   }

   @Override
   public boolean d() {
      a.info("Starting integrated minecraft server version " + w.a().getName());
      this.d(true);
      this.f(true);
      this.g(true);
      this.P();
      this.l_();
      this.e(this.N() + " - " + this.aX().g());
      return true;
   }

   @Override
   public void a(BooleanSupplier var1) {
      boolean _snowman = this.k;
      this.k = djz.C().w() != null && djz.C().T();
      anw _snowmanx = this.aQ();
      if (!_snowman && this.k) {
         _snowmanx.a("autoSave");
         a.info("Saving and pausing game...");
         this.ae().h();
         this.a(false, false, false);
         _snowmanx.c();
      }

      if (!this.k) {
         super.a(_snowman);
         int _snowmanxx = Math.max(2, this.j.k.b + -1);
         if (_snowmanxx != this.ae().p()) {
            a.info("Changing view distance to {}, from {}", _snowmanxx, this.ae().p());
            this.ae().a(_snowmanxx);
         }
      }
   }

   @Override
   public boolean i() {
      return true;
   }

   @Override
   public boolean R_() {
      return true;
   }

   @Override
   public File B() {
      return this.j.n;
   }

   @Override
   public boolean j() {
      return false;
   }

   @Override
   public int k() {
      return 0;
   }

   @Override
   public boolean l() {
      return false;
   }

   @Override
   public void a(l var1) {
      this.j.a(_snowman);
   }

   @Override
   public l b(l var1) {
      _snowman = super.b(_snowman);
      _snowman.g().a("Type", "Integrated Server (map_client.txt)");
      _snowman.g().a("Is Modded", () -> this.o().orElse("Probably not. Jar signature remains and both client + server brands are untouched."));
      return _snowman;
   }

   @Override
   public Optional<String> o() {
      String _snowman = ClientBrandRetriever.getClientModName();
      if (!_snowman.equals("vanilla")) {
         return Optional.of("Definitely; Client brand changed to '" + _snowman + "'");
      } else {
         _snowman = this.getServerModName();
         if (!"vanilla".equals(_snowman)) {
            return Optional.of("Definitely; Server brand changed to '" + _snowman + "'");
         } else {
            return djz.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
         }
      }
   }

   @Override
   public void a(apc var1) {
      super.a(_snowman);
      _snowman.a("snooper_partner", this.j.I().f());
   }

   @Override
   public boolean a(bru var1, boolean var2, int var3) {
      try {
         this.af().a(null, _snowman);
         a.info("Started serving on {}", _snowman);
         this.l = _snowman;
         this.m = new enj(this.ab(), _snowman + "");
         this.m.start();
         this.ae().a(_snowman);
         this.ae().b(_snowman);
         int _snowman = this.b(this.j.s.eA());
         this.j.s.a(_snowman);

         for (aah _snowmanx : this.ae().s()) {
            this.aD().a(_snowmanx);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   @Override
   public void t() {
      super.t();
      if (this.m != null) {
         this.m.interrupt();
         this.m = null;
      }
   }

   @Override
   public void a(boolean var1) {
      this.g(() -> {
         for (aah _snowman : Lists.newArrayList(this.ae().s())) {
            if (!_snowman.bS().equals(this.n)) {
               this.ae().c(_snowman);
            }
         }
      });
      super.a(_snowman);
      if (this.m != null) {
         this.m.interrupt();
         this.m = null;
      }
   }

   @Override
   public boolean n() {
      return this.l > -1;
   }

   @Override
   public int M() {
      return this.l;
   }

   @Override
   public void a(bru var1) {
      super.a(_snowman);
      this.ae().a(_snowman);
   }

   @Override
   public boolean m() {
      return true;
   }

   @Override
   public int g() {
      return 2;
   }

   @Override
   public int h() {
      return 2;
   }

   public void a(UUID var1) {
      this.n = _snowman;
   }

   @Override
   public boolean a(GameProfile var1) {
      return _snowman.getName().equalsIgnoreCase(this.N());
   }

   @Override
   public int b(int var1) {
      return (int)(this.j.k.c * (float)_snowman);
   }

   @Override
   public boolean aV() {
      return this.j.k.aW;
   }
}
