import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationException;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import com.mojang.authlib.exceptions.InsufficientPrivilegesException;
import com.mojang.authlib.exceptions.InvalidCredentialsException;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.math.BigInteger;
import java.security.PublicKey;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class dws implements ty {
   private static final Logger a = LogManager.getLogger();
   private final djz b;
   @Nullable
   private final dot c;
   private final Consumer<nr> d;
   private final nd e;
   private GameProfile f;

   public dws(nd var1, djz var2, @Nullable dot var3, Consumer<nr> var4) {
      this.e = _snowman;
      this.b = _snowman;
      this.c = _snowman;
      this.d = _snowman;
   }

   @Override
   public void a(ub var1) {
      Cipher _snowman;
      Cipher _snowmanx;
      String _snowmanxx;
      uh _snowmanxxx;
      try {
         SecretKey _snowmanxxxx = aeu.a();
         PublicKey _snowmanxxxxx = _snowman.c();
         _snowmanxx = new BigInteger(aeu.a(_snowman.b(), _snowmanxxxxx, _snowmanxxxx)).toString(16);
         _snowman = aeu.a(2, _snowmanxxxx);
         _snowmanx = aeu.a(1, _snowmanxxxx);
         _snowmanxxx = new uh(_snowmanxxxx, _snowmanxxxxx, _snowman.d());
      } catch (aev var8) {
         throw new IllegalStateException("Protocol error", var8);
      }

      this.d.accept(new of("connect.authorizing"));
      aff.a.submit(() -> {
         nr _snowmanxxxx = this.a(_snowman);
         if (_snowmanxxxx != null) {
            if (this.b.E() == null || !this.b.E().d()) {
               this.e.a(_snowmanxxxx);
               return;
            }

            a.warn(_snowmanxxxx.getString());
         }

         this.d.accept(new of("connect.encrypting"));
         this.e.a(_snowman, var3x -> this.e.a(_snowman, _snowman));
      });
   }

   @Nullable
   private nr a(String var1) {
      try {
         this.b().joinServer(this.b.J().e(), this.b.J().d(), _snowman);
         return null;
      } catch (AuthenticationUnavailableException var3) {
         return new of("disconnect.loginFailedInfo", new of("disconnect.loginFailedInfo.serversUnavailable"));
      } catch (InvalidCredentialsException var4) {
         return new of("disconnect.loginFailedInfo", new of("disconnect.loginFailedInfo.invalidSession"));
      } catch (InsufficientPrivilegesException var5) {
         return new of("disconnect.loginFailedInfo", new of("disconnect.loginFailedInfo.insufficientPrivileges"));
      } catch (AuthenticationException var6) {
         return new of("disconnect.loginFailedInfo", var6.getMessage());
      }
   }

   private MinecraftSessionService b() {
      return this.b.Y();
   }

   @Override
   public void a(ua var1) {
      this.d.accept(new of("connect.joining"));
      this.f = _snowman.b();
      this.e.a(ne.b);
      this.e.a(new dwu(this.b, this.c, this.e, this.f));
   }

   @Override
   public void a(nr var1) {
      if (this.c != null && this.c instanceof eoo) {
         this.b.a(new eoi(this.c, nq.i, _snowman));
      } else {
         this.b.a(new doa(this.c, nq.i, _snowman));
      }
   }

   @Override
   public nd a() {
      return this.e;
   }

   @Override
   public void a(ud var1) {
      this.e.a(_snowman.b());
   }

   @Override
   public void a(uc var1) {
      if (!this.e.d()) {
         this.e.a(_snowman.b());
      }
   }

   @Override
   public void a(tz var1) {
      this.d.accept(new of("connect.negotiating"));
      this.e.a(new uf(_snowman.b(), null));
   }
}
