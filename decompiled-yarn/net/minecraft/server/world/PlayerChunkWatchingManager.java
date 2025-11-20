package net.minecraft.server.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerChunkWatchingManager {
   private final Object2BooleanMap<ServerPlayerEntity> watchingPlayers = new Object2BooleanOpenHashMap();

   public PlayerChunkWatchingManager() {
   }

   public Stream<ServerPlayerEntity> getPlayersWatchingChunk(long _snowman) {
      return this.watchingPlayers.keySet().stream();
   }

   public void add(long _snowman, ServerPlayerEntity _snowman, boolean watchDisabled) {
      this.watchingPlayers.put(_snowman, watchDisabled);
   }

   public void remove(long _snowman, ServerPlayerEntity _snowman) {
      this.watchingPlayers.removeBoolean(_snowman);
   }

   public void disableWatch(ServerPlayerEntity _snowman) {
      this.watchingPlayers.replace(_snowman, true);
   }

   public void enableWatch(ServerPlayerEntity _snowman) {
      this.watchingPlayers.replace(_snowman, false);
   }

   public boolean method_21715(ServerPlayerEntity _snowman) {
      return this.watchingPlayers.getOrDefault(_snowman, true);
   }

   public boolean isWatchDisabled(ServerPlayerEntity _snowman) {
      return this.watchingPlayers.getBoolean(_snowman);
   }

   public void movePlayer(long prevPos, long currentPos, ServerPlayerEntity _snowman) {
   }
}
