import com.mojang.bridge.game.GameSession;
import java.util.UUID;

public class dki implements GameSession {
   private final int a;
   private final boolean b;
   private final String c;
   private final String d;
   private final UUID e;

   public dki(dwt var1, dzm var2, dwu var3) {
      this.a = _snowman.e().size();
      this.b = !_snowman.a().d();
      this.c = _snowman.ad().c();
      dwx _snowman = _snowman.a(_snowman.bS());
      if (_snowman != null) {
         this.d = _snowman.b().b();
      } else {
         this.d = "unknown";
      }

      this.e = _snowman.m();
   }

   public int getPlayerCount() {
      return this.a;
   }

   public boolean isRemoteServer() {
      return this.b;
   }

   public String getDifficulty() {
      return this.c;
   }

   public String getGameMode() {
      return this.d;
   }

   public UUID getSessionId() {
      return this.e;
   }
}
