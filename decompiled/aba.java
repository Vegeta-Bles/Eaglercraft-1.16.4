import com.mojang.authlib.GameProfile;
import com.mojang.authlib.exceptions.AuthenticationUnavailableException;
import io.netty.channel.ChannelFutureListener;
import java.math.BigInteger;
import java.net.InetAddress;
import java.net.InetSocketAddress;
import java.net.SocketAddress;
import java.security.PrivateKey;
import java.util.Arrays;
import java.util.Random;
import java.util.UUID;
import java.util.concurrent.atomic.AtomicInteger;
import javax.annotation.Nullable;
import javax.crypto.Cipher;
import javax.crypto.SecretKey;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class aba implements ue {
   private static final AtomicInteger b = new AtomicInteger(0);
   private static final Logger c = LogManager.getLogger();
   private static final Random d = new Random();
   private final byte[] e = new byte[4];
   private final MinecraftServer f;
   public final nd a;
   private aba.a g = aba.a.a;
   private int h;
   private GameProfile i;
   private final String j = "";
   private SecretKey k;
   private aah l;

   public aba(MinecraftServer var1, nd var2) {
      this.f = _snowman;
      this.a = _snowman;
      d.nextBytes(this.e);
   }

   public void b() {
      if (this.g == aba.a.e) {
         this.c();
      } else if (this.g == aba.a.f) {
         aah _snowman = this.f.ae().a(this.i.getId());
         if (_snowman == null) {
            this.g = aba.a.e;
            this.f.ae().a(this.a, this.l);
            this.l = null;
         }
      }

      if (this.h++ == 600) {
         this.b(new of("multiplayer.disconnect.slow_login"));
      }
   }

   @Override
   public nd a() {
      return this.a;
   }

   public void b(nr var1) {
      try {
         c.info("Disconnecting {}: {}", this.d(), _snowman.getString());
         this.a.a(new ud(_snowman));
         this.a.a(_snowman);
      } catch (Exception var3) {
         c.error("Error whilst disconnecting player", var3);
      }
   }

   public void c() {
      if (!this.i.isComplete()) {
         this.i = this.a(this.i);
      }

      nr _snowman = this.f.ae().a(this.a.c(), this.i);
      if (_snowman != null) {
         this.b(_snowman);
      } else {
         this.g = aba.a.g;
         if (this.f.ax() >= 0 && !this.a.d()) {
            this.a.a(new uc(this.f.ax()), (ChannelFutureListener)var1x -> this.a.a(this.f.ax()));
         }

         this.a.a(new ua(this.i));
         aah _snowmanx = this.f.ae().a(this.i.getId());
         if (_snowmanx != null) {
            this.g = aba.a.f;
            this.l = this.f.ae().g(this.i);
         } else {
            this.f.ae().a(this.a, this.f.ae().g(this.i));
         }
      }
   }

   @Override
   public void a(nr var1) {
      c.info("{} lost connection: {}", this.d(), _snowman.getString());
   }

   public String d() {
      return this.i != null ? this.i + " (" + this.a.c() + ")" : String.valueOf(this.a.c());
   }

   @Override
   public void a(ug var1) {
      Validate.validState(this.g == aba.a.a, "Unexpected hello packet", new Object[0]);
      this.i = _snowman.b();
      if (this.f.V() && !this.a.d()) {
         this.g = aba.a.b;
         this.a.a(new ub("", this.f.L().getPublic().getEncoded(), this.e));
      } else {
         this.g = aba.a.e;
      }
   }

   @Override
   public void a(uh var1) {
      Validate.validState(this.g == aba.a.b, "Unexpected key packet", new Object[0]);
      PrivateKey _snowman = this.f.L().getPrivate();

      final String _snowmanx;
      try {
         if (!Arrays.equals(this.e, _snowman.b(_snowman))) {
            throw new IllegalStateException("Protocol error");
         }

         this.k = _snowman.a(_snowman);
         Cipher _snowmanxx = aeu.a(2, this.k);
         Cipher _snowmanxxx = aeu.a(1, this.k);
         _snowmanx = new BigInteger(aeu.a("", this.f.L().getPublic(), this.k)).toString(16);
         this.g = aba.a.c;
         this.a.a(_snowmanxx, _snowmanxxx);
      } catch (aev var6) {
         throw new IllegalStateException("Protocol error", var6);
      }

      Thread _snowmanxx = new Thread("User Authenticator #" + b.incrementAndGet()) {
         @Override
         public void run() {
            GameProfile _snowman = aba.this.i;

            try {
               aba.this.i = aba.this.f.ap().hasJoinedServer(new GameProfile(null, _snowman.getName()), _snowman, this.a());
               if (aba.this.i != null) {
                  aba.c.info("UUID of player {} is {}", aba.this.i.getName(), aba.this.i.getId());
                  aba.this.g = aba.a.e;
               } else if (aba.this.f.O()) {
                  aba.c.warn("Failed to verify username but will let them in anyway!");
                  aba.this.i = aba.this.a(_snowman);
                  aba.this.g = aba.a.e;
               } else {
                  aba.this.b(new of("multiplayer.disconnect.unverified_username"));
                  aba.c.error("Username '{}' tried to join with an invalid session", _snowman.getName());
               }
            } catch (AuthenticationUnavailableException var3x) {
               if (aba.this.f.O()) {
                  aba.c.warn("Authentication servers are down but will let them in anyway!");
                  aba.this.i = aba.this.a(_snowman);
                  aba.this.g = aba.a.e;
               } else {
                  aba.this.b(new of("multiplayer.disconnect.authservers_down"));
                  aba.c.error("Couldn't verify username because servers are unavailable");
               }
            }
         }

         @Nullable
         private InetAddress a() {
            SocketAddress _snowman = aba.this.a.c();
            return aba.this.f.W() && _snowman instanceof InetSocketAddress ? ((InetSocketAddress)_snowman).getAddress() : null;
         }
      };
      _snowmanxx.setUncaughtExceptionHandler(new o(c));
      _snowmanxx.start();
   }

   @Override
   public void a(uf var1) {
      this.b(new of("multiplayer.disconnect.unexpected_query_response"));
   }

   protected GameProfile a(GameProfile var1) {
      UUID _snowman = bfw.c(_snowman.getName());
      return new GameProfile(_snowman, _snowman.getName());
   }

   static enum a {
      a,
      b,
      c,
      d,
      e,
      f,
      g;

      private a() {
      }
   }
}
