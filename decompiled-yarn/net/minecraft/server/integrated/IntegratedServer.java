package net.minecraft.server.integrated;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import java.io.File;
import java.io.IOException;
import java.util.Optional;
import java.util.UUID;
import java.util.function.BooleanSupplier;
import net.minecraft.SharedConstants;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.LanServerPinger;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.util.UserCache;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.snooper.Snooper;
import net.minecraft.world.GameMode;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class IntegratedServer extends MinecraftServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private final MinecraftClient client;
   private boolean paused;
   private int lanPort = -1;
   private LanServerPinger lanPinger;
   private UUID localPlayerUuid;

   public IntegratedServer(
      Thread serverThread,
      MinecraftClient client,
      DynamicRegistryManager.Impl registryManager,
      LevelStorage.Session session,
      ResourcePackManager _snowman,
      ServerResourceManager _snowman,
      SaveProperties _snowman,
      MinecraftSessionService _snowman,
      GameProfileRepository _snowman,
      UserCache _snowman,
      WorldGenerationProgressListenerFactory _snowman
   ) {
      super(serverThread, registryManager, session, _snowman, _snowman, client.getNetworkProxy(), client.getDataFixer(), _snowman, _snowman, _snowman, _snowman, _snowman);
      this.setServerName(client.getSession().getUsername());
      this.setDemo(client.isDemo());
      this.setWorldHeight(256);
      this.setPlayerManager(new IntegratedPlayerManager(this, this.registryManager, this.saveHandler));
      this.client = client;
   }

   @Override
   public boolean setupServer() {
      LOGGER.info("Starting integrated minecraft server version " + SharedConstants.getGameVersion().getName());
      this.setOnlineMode(true);
      this.setPvpEnabled(true);
      this.setFlightEnabled(true);
      this.method_31400();
      this.loadWorld();
      this.setMotd(this.getUserName() + " - " + this.getSaveProperties().getLevelName());
      return true;
   }

   @Override
   public void tick(BooleanSupplier shouldKeepTicking) {
      boolean _snowman = this.paused;
      this.paused = MinecraftClient.getInstance().getNetworkHandler() != null && MinecraftClient.getInstance().isPaused();
      Profiler _snowmanx = this.getProfiler();
      if (!_snowman && this.paused) {
         _snowmanx.push("autoSave");
         LOGGER.info("Saving and pausing game...");
         this.getPlayerManager().saveAllPlayerData();
         this.save(false, false, false);
         _snowmanx.pop();
      }

      if (!this.paused) {
         super.tick(shouldKeepTicking);
         int _snowmanxx = Math.max(2, this.client.options.viewDistance + -1);
         if (_snowmanxx != this.getPlayerManager().getViewDistance()) {
            LOGGER.info("Changing view distance to {}, from {}", _snowmanxx, this.getPlayerManager().getViewDistance());
            this.getPlayerManager().setViewDistance(_snowmanxx);
         }
      }
   }

   @Override
   public boolean shouldBroadcastRconToOps() {
      return true;
   }

   @Override
   public boolean shouldBroadcastConsoleToOps() {
      return true;
   }

   @Override
   public File getRunDirectory() {
      return this.client.runDirectory;
   }

   @Override
   public boolean isDedicated() {
      return false;
   }

   @Override
   public int getRateLimit() {
      return 0;
   }

   @Override
   public boolean isUsingNativeTransport() {
      return false;
   }

   @Override
   public void setCrashReport(CrashReport report) {
      this.client.setCrashReport(report);
   }

   @Override
   public CrashReport populateCrashReport(CrashReport report) {
      report = super.populateCrashReport(report);
      report.getSystemDetailsSection().add("Type", "Integrated Server (map_client.txt)");
      report.getSystemDetailsSection()
         .add("Is Modded", () -> this.getModdedStatusMessage().orElse("Probably not. Jar signature remains and both client + server brands are untouched."));
      return report;
   }

   @Override
   public Optional<String> getModdedStatusMessage() {
      String _snowman = ClientBrandRetriever.getClientModName();
      if (!_snowman.equals("vanilla")) {
         return Optional.of("Definitely; Client brand changed to '" + _snowman + "'");
      } else {
         _snowman = this.getServerModName();
         if (!"vanilla".equals(_snowman)) {
            return Optional.of("Definitely; Server brand changed to '" + _snowman + "'");
         } else {
            return MinecraftClient.class.getSigners() == null ? Optional.of("Very likely; Jar signature invalidated") : Optional.empty();
         }
      }
   }

   @Override
   public void addSnooperInfo(Snooper snooper) {
      super.addSnooperInfo(snooper);
      snooper.addInfo("snooper_partner", this.client.getSnooper().getToken());
   }

   @Override
   public boolean openToLan(GameMode gameMode, boolean cheatsAllowed, int port) {
      try {
         this.getNetworkIo().bind(null, port);
         LOGGER.info("Started serving on {}", port);
         this.lanPort = port;
         this.lanPinger = new LanServerPinger(this.getServerMotd(), port + "");
         this.lanPinger.start();
         this.getPlayerManager().setGameMode(gameMode);
         this.getPlayerManager().setCheatsAllowed(cheatsAllowed);
         int _snowman = this.getPermissionLevel(this.client.player.getGameProfile());
         this.client.player.setClientPermissionLevel(_snowman);

         for (ServerPlayerEntity _snowmanx : this.getPlayerManager().getPlayerList()) {
            this.getCommandManager().sendCommandTree(_snowmanx);
         }

         return true;
      } catch (IOException var7) {
         return false;
      }
   }

   @Override
   public void shutdown() {
      super.shutdown();
      if (this.lanPinger != null) {
         this.lanPinger.interrupt();
         this.lanPinger = null;
      }
   }

   @Override
   public void stop(boolean _snowman) {
      this.submitAndJoin(() -> {
         for (ServerPlayerEntity _snowmanx : Lists.newArrayList(this.getPlayerManager().getPlayerList())) {
            if (!_snowmanx.getUuid().equals(this.localPlayerUuid)) {
               this.getPlayerManager().remove(_snowmanx);
            }
         }
      });
      super.stop(_snowman);
      if (this.lanPinger != null) {
         this.lanPinger.interrupt();
         this.lanPinger = null;
      }
   }

   @Override
   public boolean isRemote() {
      return this.lanPort > -1;
   }

   @Override
   public int getServerPort() {
      return this.lanPort;
   }

   @Override
   public void setDefaultGameMode(GameMode gameMode) {
      super.setDefaultGameMode(gameMode);
      this.getPlayerManager().setGameMode(gameMode);
   }

   @Override
   public boolean areCommandBlocksEnabled() {
      return true;
   }

   @Override
   public int getOpPermissionLevel() {
      return 2;
   }

   @Override
   public int getFunctionPermissionLevel() {
      return 2;
   }

   public void setLocalPlayerUuid(UUID localPlayerUuid) {
      this.localPlayerUuid = localPlayerUuid;
   }

   @Override
   public boolean isHost(GameProfile profile) {
      return profile.getName().equalsIgnoreCase(this.getUserName());
   }

   @Override
   public int adjustTrackingDistance(int initialDistance) {
      return (int)(this.client.options.entityDistanceScaling * (float)initialDistance);
   }

   @Override
   public boolean syncChunkWrites() {
      return this.client.options.syncChunkWrites;
   }
}
