package net.minecraft.client;

import com.mojang.bridge.game.GameSession;
import java.util.UUID;
import net.minecraft.client.network.ClientPlayNetworkHandler;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.world.ClientWorld;

public class ClientGameSession implements GameSession {
   private final int playerCount;
   private final boolean remoteServer;
   private final String difficulty;
   private final String gameMode;
   private final UUID sessionId;

   public ClientGameSession(ClientWorld _snowman, ClientPlayerEntity _snowman, ClientPlayNetworkHandler _snowman) {
      this.playerCount = _snowman.getPlayerList().size();
      this.remoteServer = !_snowman.getConnection().isLocal();
      this.difficulty = _snowman.getDifficulty().getName();
      PlayerListEntry _snowmanxxx = _snowman.getPlayerListEntry(_snowman.getUuid());
      if (_snowmanxxx != null) {
         this.gameMode = _snowmanxxx.getGameMode().getName();
      } else {
         this.gameMode = "unknown";
      }

      this.sessionId = _snowman.getSessionId();
   }

   public int getPlayerCount() {
      return this.playerCount;
   }

   public boolean isRemoteServer() {
      return this.remoteServer;
   }

   public String getDifficulty() {
      return this.difficulty;
   }

   public String getGameMode() {
      return this.gameMode;
   }

   public UUID getSessionId() {
      return this.sessionId;
   }
}
