import com.mojang.authlib.GameProfile;
import java.net.SocketAddress;

public class enf extends acu {
   private md a;

   public enf(eng var1, gn.b var2, cyk var3) {
      super(_snowman, _snowman, _snowman, 8);
      this.a(10);
   }

   @Override
   protected void b(aah var1) {
      if (_snowman.R().getString().equals(this.b().N())) {
         this.a = _snowman.e(new md());
      }

      super.b(_snowman);
   }

   @Override
   public nr a(SocketAddress var1, GameProfile var2) {
      return (nr)(_snowman.getName().equalsIgnoreCase(this.b().N()) && this.a(_snowman.getName()) != null ? new of("multiplayer.disconnect.name_taken") : super.a(_snowman, _snowman));
   }

   public eng b() {
      return (eng)super.c();
   }

   @Override
   public md q() {
      return this.a;
   }
}
