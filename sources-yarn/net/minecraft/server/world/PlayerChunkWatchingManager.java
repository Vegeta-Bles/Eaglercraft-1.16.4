package net.minecraft.server.world;

import it.unimi.dsi.fastutil.objects.Object2BooleanMap;
import it.unimi.dsi.fastutil.objects.Object2BooleanOpenHashMap;
import java.util.stream.Stream;
import net.minecraft.server.network.ServerPlayerEntity;

public final class PlayerChunkWatchingManager {
   private final Object2BooleanMap<ServerPlayerEntity> watchingPlayers = new Object2BooleanOpenHashMap();

   public PlayerChunkWatchingManager() {
   }

   public Stream<ServerPlayerEntity> getPlayersWatchingChunk(long l) {
      return this.watchingPlayers.keySet().stream();
   }

   public void add(long l, ServerPlayerEntity arg, boolean watchDisabled) {
      this.watchingPlayers.put(arg, watchDisabled);
   }

   public void remove(long l, ServerPlayerEntity arg) {
      this.watchingPlayers.removeBoolean(arg);
   }

   public void disableWatch(ServerPlayerEntity arg) {
      this.watchingPlayers.replace(arg, true);
   }

   public void enableWatch(ServerPlayerEntity arg) {
      this.watchingPlayers.replace(arg, false);
   }

   public boolean method_21715(ServerPlayerEntity arg) {
      return this.watchingPlayers.getOrDefault(arg, true);
   }

   public boolean isWatchDisabled(ServerPlayerEntity arg) {
      return this.watchingPlayers.getBoolean(arg);
   }

   public void movePlayer(long prevPos, long currentPos, ServerPlayerEntity arg) {
   }
}
