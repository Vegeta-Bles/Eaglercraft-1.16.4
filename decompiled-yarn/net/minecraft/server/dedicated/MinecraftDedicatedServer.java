package net.minecraft.server.dedicated;

import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.GameProfileRepository;
import com.mojang.authlib.minecraft.MinecraftSessionService;
import com.mojang.datafixers.DataFixer;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.InetAddress;
import java.net.Proxy;
import java.nio.charset.StandardCharsets;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Optional;
import java.util.function.BooleanSupplier;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.Items;
import net.minecraft.resource.ResourcePackManager;
import net.minecraft.resource.ServerResourceManager;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.ServerConfigHandler;
import net.minecraft.server.WorldGenerationProgressListenerFactory;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.dedicated.gui.DedicatedServerGui;
import net.minecraft.server.filter.TextFilterer;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.rcon.QueryResponseHandler;
import net.minecraft.server.rcon.RconCommandOutput;
import net.minecraft.server.rcon.RconListener;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.logging.UncaughtExceptionHandler;
import net.minecraft.util.logging.UncaughtExceptionLogger;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.snooper.Snooper;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.SaveProperties;
import net.minecraft.world.World;
import net.minecraft.world.level.storage.LevelStorage;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class MinecraftDedicatedServer extends MinecraftServer implements DedicatedServer {
   private static final Logger LOGGER = LogManager.getLogger();
   private static final Pattern SHA1_PATTERN = Pattern.compile("^[a-fA-F0-9]{40}$");
   private final List<PendingServerCommand> commandQueue = Collections.synchronizedList(Lists.newArrayList());
   private QueryResponseHandler queryResponseHandler;
   private final RconCommandOutput rconCommandOutput;
   private RconListener rconServer;
   private final ServerPropertiesLoader propertiesLoader;
   @Nullable
   private DedicatedServerGui gui;
   @Nullable
   private final TextFilterer filterer;

   public MinecraftDedicatedServer(
      Thread _snowman,
      DynamicRegistryManager.Impl _snowman,
      LevelStorage.Session _snowman,
      ResourcePackManager _snowman,
      ServerResourceManager _snowman,
      SaveProperties _snowman,
      ServerPropertiesLoader _snowman,
      DataFixer _snowman,
      MinecraftSessionService _snowman,
      GameProfileRepository _snowman,
      UserCache _snowman,
      WorldGenerationProgressListenerFactory _snowman
   ) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, Proxy.NO_PROXY, _snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
      this.propertiesLoader = _snowman;
      this.rconCommandOutput = new RconCommandOutput(this);
      this.filterer = null;
   }

   @Override
   public boolean setupServer() throws IOException {
      Thread _snowman = new Thread("Server console handler") {
         @Override
         public void run() {
            BufferedReader _snowman = new BufferedReader(new InputStreamReader(System.in, StandardCharsets.UTF_8));

            String _snowmanx;
            try {
               while (!MinecraftDedicatedServer.this.isStopped() && MinecraftDedicatedServer.this.isRunning() && (_snowmanx = _snowman.readLine()) != null) {
                  MinecraftDedicatedServer.this.enqueueCommand(_snowmanx, MinecraftDedicatedServer.this.getCommandSource());
               }
            } catch (IOException var4) {
               MinecraftDedicatedServer.LOGGER.error("Exception handling console input", var4);
            }
         }
      };
      _snowman.setDaemon(true);
      _snowman.setUncaughtExceptionHandler(new UncaughtExceptionLogger(LOGGER));
      _snowman.start();
      LOGGER.info("Starting minecraft server version " + SharedConstants.getGameVersion().getName());
      if (Runtime.getRuntime().maxMemory() / 1024L / 1024L < 512L) {
         LOGGER.warn("To start the server with more ram, launch it as \"java -Xmx1024M -Xms1024M -jar minecraft_server.jar\"");
      }

      LOGGER.info("Loading properties");
      ServerPropertiesHandler _snowmanx = this.propertiesLoader.getPropertiesHandler();
      if (this.isSinglePlayer()) {
         this.setServerIp("127.0.0.1");
      } else {
         this.setOnlineMode(_snowmanx.onlineMode);
         this.setPreventProxyConnections(_snowmanx.preventProxyConnections);
         this.setServerIp(_snowmanx.serverIp);
      }

      this.setPvpEnabled(_snowmanx.pvp);
      this.setFlightEnabled(_snowmanx.allowFlight);
      this.setResourcePack(_snowmanx.resourcePack, this.createResourcePackHash());
      this.setMotd(_snowmanx.motd);
      this.setForceGameMode(_snowmanx.forceGameMode);
      super.setPlayerIdleTimeout(_snowmanx.playerIdleTimeout.get());
      this.setEnforceWhitelist(_snowmanx.enforceWhitelist);
      this.saveProperties.setGameMode(_snowmanx.gameMode);
      LOGGER.info("Default game type: {}", _snowmanx.gameMode);
      InetAddress _snowmanxx = null;
      if (!this.getServerIp().isEmpty()) {
         _snowmanxx = InetAddress.getByName(this.getServerIp());
      }

      if (this.getServerPort() < 0) {
         this.setServerPort(_snowmanx.serverPort);
      }

      this.method_31400();
      LOGGER.info("Starting Minecraft server on {}:{}", this.getServerIp().isEmpty() ? "*" : this.getServerIp(), this.getServerPort());

      try {
         this.getNetworkIo().bind(_snowmanxx, this.getServerPort());
      } catch (IOException var10) {
         LOGGER.warn("**** FAILED TO BIND TO PORT!");
         LOGGER.warn("The exception was: {}", var10.toString());
         LOGGER.warn("Perhaps a server is already running on that port?");
         return false;
      }

      if (!this.isOnlineMode()) {
         LOGGER.warn("**** SERVER IS RUNNING IN OFFLINE/INSECURE MODE!");
         LOGGER.warn("The server will make no attempt to authenticate usernames. Beware.");
         LOGGER.warn(
            "While this makes the game possible to play without internet access, it also opens up the ability for hackers to connect with any username they choose."
         );
         LOGGER.warn("To change this, set \"online-mode\" to \"true\" in the server.properties file.");
      }

      if (this.convertData()) {
         this.getUserCache().save();
      }

      if (!ServerConfigHandler.checkSuccess(this)) {
         return false;
      } else {
         this.setPlayerManager(new DedicatedPlayerManager(this, this.registryManager, this.saveHandler));
         long _snowmanxxx = Util.getMeasuringTimeNano();
         this.setWorldHeight(_snowmanx.maxBuildHeight);
         SkullBlockEntity.setUserCache(this.getUserCache());
         SkullBlockEntity.setSessionService(this.getSessionService());
         UserCache.setUseRemote(this.isOnlineMode());
         LOGGER.info("Preparing level \"{}\"", this.getLevelName());
         this.loadWorld();
         long _snowmanxxxx = Util.getMeasuringTimeNano() - _snowmanxxx;
         String _snowmanxxxxx = String.format(Locale.ROOT, "%.3fs", (double)_snowmanxxxx / 1.0E9);
         LOGGER.info("Done ({})! For help, type \"help\"", _snowmanxxxxx);
         if (_snowmanx.announcePlayerAchievements != null) {
            this.getGameRules().get(GameRules.ANNOUNCE_ADVANCEMENTS).set(_snowmanx.announcePlayerAchievements, this);
         }

         if (_snowmanx.enableQuery) {
            LOGGER.info("Starting GS4 status listener");
            this.queryResponseHandler = QueryResponseHandler.create(this);
         }

         if (_snowmanx.enableRcon) {
            LOGGER.info("Starting remote control listener");
            this.rconServer = RconListener.create(this);
         }

         if (this.getMaxTickTime() > 0L) {
            Thread _snowmanxxxxxx = new Thread(new DedicatedServerWatchdog(this));
            _snowmanxxxxxx.setUncaughtExceptionHandler(new UncaughtExceptionHandler(LOGGER));
            _snowmanxxxxxx.setName("Server Watchdog");
            _snowmanxxxxxx.setDaemon(true);
            _snowmanxxxxxx.start();
         }

         Items.AIR.appendStacks(ItemGroup.SEARCH, DefaultedList.of());
         if (_snowmanx.enableJmxMonitoring) {
            ServerMBean.register(this);
         }

         return true;
      }
   }

   @Override
   public boolean shouldSpawnAnimals() {
      return this.getProperties().spawnAnimals && super.shouldSpawnAnimals();
   }

   @Override
   public boolean isMonsterSpawningEnabled() {
      return this.propertiesLoader.getPropertiesHandler().spawnMonsters && super.isMonsterSpawningEnabled();
   }

   @Override
   public boolean shouldSpawnNpcs() {
      return this.propertiesLoader.getPropertiesHandler().spawnNpcs && super.shouldSpawnNpcs();
   }

   public String createResourcePackHash() {
      ServerPropertiesHandler _snowman = this.propertiesLoader.getPropertiesHandler();
      String _snowmanx;
      if (!_snowman.resourcePackSha1.isEmpty()) {
         _snowmanx = _snowman.resourcePackSha1;
         if (!Strings.isNullOrEmpty(_snowman.resourcePackHash)) {
            LOGGER.warn("resource-pack-hash is deprecated and found along side resource-pack-sha1. resource-pack-hash will be ignored.");
         }
      } else if (!Strings.isNullOrEmpty(_snowman.resourcePackHash)) {
         LOGGER.warn("resource-pack-hash is deprecated. Please use resource-pack-sha1 instead.");
         _snowmanx = _snowman.resourcePackHash;
      } else {
         _snowmanx = "";
      }

      if (!_snowmanx.isEmpty() && !SHA1_PATTERN.matcher(_snowmanx).matches()) {
         LOGGER.warn("Invalid sha1 for ressource-pack-sha1");
      }

      if (!_snowman.resourcePack.isEmpty() && _snowmanx.isEmpty()) {
         LOGGER.warn("You specified a resource pack without providing a sha1 hash. Pack will be updated on the client only if you change the name of the pack.");
      }

      return _snowmanx;
   }

   @Override
   public ServerPropertiesHandler getProperties() {
      return this.propertiesLoader.getPropertiesHandler();
   }

   @Override
   public void method_27731() {
      this.setDifficulty(this.getProperties().difficulty, true);
   }

   @Override
   public boolean isHardcore() {
      return this.getProperties().hardcore;
   }

   @Override
   public CrashReport populateCrashReport(CrashReport report) {
      report = super.populateCrashReport(report);
      report.getSystemDetailsSection().add("Is Modded", () -> this.getModdedStatusMessage().orElse("Unknown (can't tell)"));
      report.getSystemDetailsSection().add("Type", () -> "Dedicated Server (map_server.txt)");
      return report;
   }

   @Override
   public Optional<String> getModdedStatusMessage() {
      String _snowman = this.getServerModName();
      return !"vanilla".equals(_snowman) ? Optional.of("Definitely; Server brand changed to '" + _snowman + "'") : Optional.empty();
   }

   @Override
   public void exit() {
      if (this.filterer != null) {
         this.filterer.close();
      }

      if (this.gui != null) {
         this.gui.stop();
      }

      if (this.rconServer != null) {
         this.rconServer.stop();
      }

      if (this.queryResponseHandler != null) {
         this.queryResponseHandler.stop();
      }
   }

   @Override
   public void tickWorlds(BooleanSupplier shouldKeepTicking) {
      super.tickWorlds(shouldKeepTicking);
      this.executeQueuedCommands();
   }

   @Override
   public boolean isNetherAllowed() {
      return this.getProperties().allowNether;
   }

   @Override
   public void addSnooperInfo(Snooper snooper) {
      snooper.addInfo("whitelist_enabled", this.getPlayerManager().isWhitelistEnabled());
      snooper.addInfo("whitelist_count", this.getPlayerManager().getWhitelistedNames().length);
      super.addSnooperInfo(snooper);
   }

   public void enqueueCommand(String command, ServerCommandSource commandSource) {
      this.commandQueue.add(new PendingServerCommand(command, commandSource));
   }

   public void executeQueuedCommands() {
      while (!this.commandQueue.isEmpty()) {
         PendingServerCommand _snowman = this.commandQueue.remove(0);
         this.getCommandManager().execute(_snowman.source, _snowman.command);
      }
   }

   @Override
   public boolean isDedicated() {
      return true;
   }

   @Override
   public int getRateLimit() {
      return this.getProperties().rateLimit;
   }

   @Override
   public boolean isUsingNativeTransport() {
      return this.getProperties().useNativeTransport;
   }

   public DedicatedPlayerManager getPlayerManager() {
      return (DedicatedPlayerManager)super.getPlayerManager();
   }

   @Override
   public boolean isRemote() {
      return true;
   }

   @Override
   public String getHostname() {
      return this.getServerIp();
   }

   @Override
   public int getPort() {
      return this.getServerPort();
   }

   @Override
   public String getMotd() {
      return this.getServerMotd();
   }

   public void createGui() {
      if (this.gui == null) {
         this.gui = DedicatedServerGui.create(this);
      }
   }

   @Override
   public boolean hasGui() {
      return this.gui != null;
   }

   @Override
   public boolean openToLan(GameMode gameMode, boolean cheatsAllowed, int port) {
      return false;
   }

   @Override
   public boolean areCommandBlocksEnabled() {
      return this.getProperties().enableCommandBlock;
   }

   @Override
   public int getSpawnProtectionRadius() {
      return this.getProperties().spawnProtection;
   }

   @Override
   public boolean isSpawnProtected(ServerWorld world, BlockPos pos, PlayerEntity player) {
      if (world.getRegistryKey() != World.OVERWORLD) {
         return false;
      } else if (this.getPlayerManager().getOpList().isEmpty()) {
         return false;
      } else if (this.getPlayerManager().isOperator(player.getGameProfile())) {
         return false;
      } else if (this.getSpawnProtectionRadius() <= 0) {
         return false;
      } else {
         BlockPos _snowman = world.getSpawnPos();
         int _snowmanx = MathHelper.abs(pos.getX() - _snowman.getX());
         int _snowmanxx = MathHelper.abs(pos.getZ() - _snowman.getZ());
         int _snowmanxxx = Math.max(_snowmanx, _snowmanxx);
         return _snowmanxxx <= this.getSpawnProtectionRadius();
      }
   }

   @Override
   public boolean acceptsStatusQuery() {
      return this.getProperties().enableStatus;
   }

   @Override
   public int getOpPermissionLevel() {
      return this.getProperties().opPermissionLevel;
   }

   @Override
   public int getFunctionPermissionLevel() {
      return this.getProperties().functionPermissionLevel;
   }

   @Override
   public void setPlayerIdleTimeout(int playerIdleTimeout) {
      super.setPlayerIdleTimeout(playerIdleTimeout);
      this.propertiesLoader.apply(_snowmanx -> _snowmanx.playerIdleTimeout.set(this.getRegistryManager(), playerIdleTimeout));
   }

   @Override
   public boolean shouldBroadcastRconToOps() {
      return this.getProperties().broadcastRconToOps;
   }

   @Override
   public boolean shouldBroadcastConsoleToOps() {
      return this.getProperties().broadcastConsoleToOps;
   }

   @Override
   public int getMaxWorldBorderRadius() {
      return this.getProperties().maxWorldSize;
   }

   @Override
   public int getNetworkCompressionThreshold() {
      return this.getProperties().networkCompressionThreshold;
   }

   protected boolean convertData() {
      boolean _snowman = false;

      for (int _snowmanx = 0; !_snowman && _snowmanx <= 2; _snowmanx++) {
         if (_snowmanx > 0) {
            LOGGER.warn("Encountered a problem while converting the user banlist, retrying in a few seconds");
            this.sleepFiveSeconds();
         }

         _snowman = ServerConfigHandler.convertBannedPlayers(this);
      }

      boolean _snowmanx = false;

      for (int var7 = 0; !_snowmanx && var7 <= 2; var7++) {
         if (var7 > 0) {
            LOGGER.warn("Encountered a problem while converting the ip banlist, retrying in a few seconds");
            this.sleepFiveSeconds();
         }

         _snowmanx = ServerConfigHandler.convertBannedIps(this);
      }

      boolean _snowmanxx = false;

      for (int var8 = 0; !_snowmanxx && var8 <= 2; var8++) {
         if (var8 > 0) {
            LOGGER.warn("Encountered a problem while converting the op list, retrying in a few seconds");
            this.sleepFiveSeconds();
         }

         _snowmanxx = ServerConfigHandler.convertOperators(this);
      }

      boolean _snowmanxxx = false;

      for (int var9 = 0; !_snowmanxxx && var9 <= 2; var9++) {
         if (var9 > 0) {
            LOGGER.warn("Encountered a problem while converting the whitelist, retrying in a few seconds");
            this.sleepFiveSeconds();
         }

         _snowmanxxx = ServerConfigHandler.convertWhitelist(this);
      }

      boolean _snowmanxxxx = false;

      for (int var10 = 0; !_snowmanxxxx && var10 <= 2; var10++) {
         if (var10 > 0) {
            LOGGER.warn("Encountered a problem while converting the player save files, retrying in a few seconds");
            this.sleepFiveSeconds();
         }

         _snowmanxxxx = ServerConfigHandler.convertPlayerFiles(this);
      }

      return _snowman || _snowmanx || _snowmanxx || _snowmanxxx || _snowmanxxxx;
   }

   private void sleepFiveSeconds() {
      try {
         Thread.sleep(5000L);
      } catch (InterruptedException var2) {
      }
   }

   public long getMaxTickTime() {
      return this.getProperties().maxTickTime;
   }

   @Override
   public String getPlugins() {
      return "";
   }

   @Override
   public String executeRconCommand(String command) {
      this.rconCommandOutput.clear();
      this.submitAndJoin(() -> this.getCommandManager().execute(this.rconCommandOutput.createRconCommandSource(), command));
      return this.rconCommandOutput.asString();
   }

   public void setUseWhitelist(boolean _snowman) {
      this.propertiesLoader.apply(_snowmanx -> _snowmanx.whiteList.set(this.getRegistryManager(), _snowman));
   }

   @Override
   public void shutdown() {
      super.shutdown();
      Util.shutdownExecutors();
   }

   @Override
   public boolean isHost(GameProfile profile) {
      return false;
   }

   @Override
   public int adjustTrackingDistance(int initialDistance) {
      return this.getProperties().entityBroadcastRangePercentage * initialDistance / 100;
   }

   @Override
   public String getLevelName() {
      return this.session.getDirectoryName();
   }

   @Override
   public boolean syncChunkWrites() {
      return this.propertiesLoader.getPropertiesHandler().syncChunkWrites;
   }

   @Nullable
   @Override
   public TextStream createFilterer(ServerPlayerEntity player) {
      return this.filterer != null ? this.filterer.createFilterer(player.getGameProfile()) : null;
   }
}
