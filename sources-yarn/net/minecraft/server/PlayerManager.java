package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.UUID;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HeldItemChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeTagsS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ServerScoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.DemoServerPlayerInteractionManager;
import net.minecraft.server.network.ServerPlayNetworkHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.network.ServerPlayerInteractionManager;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stats;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.UserCache;
import net.minecraft.util.Util;
import net.minecraft.util.WorldSavePath;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.WorldSaveHandler;
import net.minecraft.world.biome.source.BiomeAccess;
import net.minecraft.world.border.WorldBorder;
import net.minecraft.world.border.WorldBorderListener;
import net.minecraft.world.dimension.DimensionType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class PlayerManager {
   public static final File BANNED_PLAYERS_FILE = new File("banned-players.json");
   public static final File BANNED_IPS_FILE = new File("banned-ips.json");
   public static final File OPERATORS_FILE = new File("ops.json");
   public static final File WHITELIST_FILE = new File("whitelist.json");
   private static final Logger LOGGER = LogManager.getLogger();
   private static final SimpleDateFormat DATE_FORMATTER = new SimpleDateFormat("yyyy-MM-dd 'at' HH:mm:ss z");
   private final MinecraftServer server;
   private final List<ServerPlayerEntity> players = Lists.newArrayList();
   private final Map<UUID, ServerPlayerEntity> playerMap = Maps.newHashMap();
   private final BannedPlayerList bannedProfiles = new BannedPlayerList(BANNED_PLAYERS_FILE);
   private final BannedIpList bannedIps = new BannedIpList(BANNED_IPS_FILE);
   private final OperatorList ops = new OperatorList(OPERATORS_FILE);
   private final Whitelist whitelist = new Whitelist(WHITELIST_FILE);
   private final Map<UUID, ServerStatHandler> statisticsMap = Maps.newHashMap();
   private final Map<UUID, PlayerAdvancementTracker> advancementTrackers = Maps.newHashMap();
   private final WorldSaveHandler saveHandler;
   private boolean whitelistEnabled;
   private final DynamicRegistryManager.Impl registryManager;
   protected final int maxPlayers;
   private int viewDistance;
   private GameMode gameMode;
   private boolean cheatsAllowed;
   private int latencyUpdateTimer;

   public PlayerManager(MinecraftServer server, DynamicRegistryManager.Impl registryManager, WorldSaveHandler saveHandler, int maxPlayers) {
      this.server = server;
      this.registryManager = registryManager;
      this.maxPlayers = maxPlayers;
      this.saveHandler = saveHandler;
   }

   public void onPlayerConnect(ClientConnection connection, ServerPlayerEntity player) {
      GameProfile gameProfile = player.getGameProfile();
      UserCache lv = this.server.getUserCache();
      GameProfile gameProfile2 = lv.getByUuid(gameProfile.getId());
      String string = gameProfile2 == null ? gameProfile.getName() : gameProfile2.getName();
      lv.add(gameProfile);
      CompoundTag lv2 = this.loadPlayerData(player);
      RegistryKey<World> lv3 = lv2 != null
         ? DimensionType.method_28521(new Dynamic(NbtOps.INSTANCE, lv2.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(World.OVERWORLD)
         : World.OVERWORLD;
      ServerWorld lv4 = this.server.getWorld(lv3);
      ServerWorld lv5;
      if (lv4 == null) {
         LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", lv3);
         lv5 = this.server.getOverworld();
      } else {
         lv5 = lv4;
      }

      player.setWorld(lv5);
      player.interactionManager.setWorld((ServerWorld)player.world);
      String string2 = "local";
      if (connection.getAddress() != null) {
         string2 = connection.getAddress().toString();
      }

      LOGGER.info(
         "{}[{}] logged in with entity id {} at ({}, {}, {})",
         player.getName().getString(),
         string2,
         player.getEntityId(),
         player.getX(),
         player.getY(),
         player.getZ()
      );
      WorldProperties lv7 = lv5.getLevelProperties();
      this.setGameMode(player, null, lv5);
      ServerPlayNetworkHandler lv8 = new ServerPlayNetworkHandler(this.server, connection, player);
      GameRules lv9 = lv5.getGameRules();
      boolean bl = lv9.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
      boolean bl2 = lv9.getBoolean(GameRules.REDUCED_DEBUG_INFO);
      lv8.sendPacket(
         new GameJoinS2CPacket(
            player.getEntityId(),
            player.interactionManager.getGameMode(),
            player.interactionManager.getPreviousGameMode(),
            BiomeAccess.hashSeed(lv5.getSeed()),
            lv7.isHardcore(),
            this.server.getWorldRegistryKeys(),
            this.registryManager,
            lv5.getDimension(),
            lv5.getRegistryKey(),
            this.getMaxPlayerCount(),
            this.viewDistance,
            bl2,
            !bl,
            lv5.isDebugWorld(),
            lv5.isFlat()
         )
      );
      lv8.sendPacket(
         new CustomPayloadS2CPacket(CustomPayloadS2CPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(this.getServer().getServerModName()))
      );
      lv8.sendPacket(new DifficultyS2CPacket(lv7.getDifficulty(), lv7.isDifficultyLocked()));
      lv8.sendPacket(new PlayerAbilitiesS2CPacket(player.abilities));
      lv8.sendPacket(new HeldItemChangeS2CPacket(player.inventory.selectedSlot));
      lv8.sendPacket(new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values()));
      lv8.sendPacket(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
      this.sendCommandTree(player);
      player.getStatHandler().updateStatSet();
      player.getRecipeBook().sendInitRecipesPacket(player);
      this.sendScoreboard(lv5.getScoreboard(), player);
      this.server.forcePlayerSampleUpdate();
      MutableText lv10;
      if (player.getGameProfile().getName().equalsIgnoreCase(string)) {
         lv10 = new TranslatableText("multiplayer.player.joined", player.getDisplayName());
      } else {
         lv10 = new TranslatableText("multiplayer.player.joined.renamed", player.getDisplayName(), string);
      }

      this.broadcastChatMessage(lv10.formatted(Formatting.YELLOW), MessageType.SYSTEM, Util.NIL_UUID);
      lv8.requestTeleport(player.getX(), player.getY(), player.getZ(), player.yaw, player.pitch);
      this.players.add(player);
      this.playerMap.put(player.getUuid(), player);
      this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));

      for (int i = 0; i < this.players.size(); i++) {
         player.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, this.players.get(i)));
      }

      lv5.onPlayerConnected(player);
      this.server.getBossBarManager().onPlayerConnect(player);
      this.sendWorldInfo(player, lv5);
      if (!this.server.getResourcePackUrl().isEmpty()) {
         player.sendResourcePackUrl(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
      }

      for (StatusEffectInstance lv12 : player.getStatusEffects()) {
         lv8.sendPacket(new EntityStatusEffectS2CPacket(player.getEntityId(), lv12));
      }

      if (lv2 != null && lv2.contains("RootVehicle", 10)) {
         CompoundTag lv13 = lv2.getCompound("RootVehicle");
         Entity lv14 = EntityType.loadEntityWithPassengers(lv13.getCompound("Entity"), lv5, vehicle -> !lv5.tryLoadEntity(vehicle) ? null : vehicle);
         if (lv14 != null) {
            UUID uUID;
            if (lv13.containsUuid("Attach")) {
               uUID = lv13.getUuid("Attach");
            } else {
               uUID = null;
            }

            if (lv14.getUuid().equals(uUID)) {
               player.startRiding(lv14, true);
            } else {
               for (Entity lv15 : lv14.getPassengersDeep()) {
                  if (lv15.getUuid().equals(uUID)) {
                     player.startRiding(lv15, true);
                     break;
                  }
               }
            }

            if (!player.hasVehicle()) {
               LOGGER.warn("Couldn't reattach entity to player");
               lv5.removeEntity(lv14);

               for (Entity lv16 : lv14.getPassengersDeep()) {
                  lv5.removeEntity(lv16);
               }
            }
         }
      }

      player.onSpawn();
   }

   protected void sendScoreboard(ServerScoreboard scoreboard, ServerPlayerEntity player) {
      Set<ScoreboardObjective> set = Sets.newHashSet();

      for (Team lv : scoreboard.getTeams()) {
         player.networkHandler.sendPacket(new TeamS2CPacket(lv, 0));
      }

      for (int i = 0; i < 19; i++) {
         ScoreboardObjective lv2 = scoreboard.getObjectiveForSlot(i);
         if (lv2 != null && !set.contains(lv2)) {
            for (Packet<?> lv3 : scoreboard.createChangePackets(lv2)) {
               player.networkHandler.sendPacket(lv3);
            }

            set.add(lv2);
         }
      }
   }

   public void setMainWorld(ServerWorld world) {
      world.getWorldBorder().addListener(new WorldBorderListener() {
         @Override
         public void onSizeChange(WorldBorder border, double size) {
            PlayerManager.this.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_SIZE));
         }

         @Override
         public void onInterpolateSize(WorldBorder border, double fromSize, double toSize, long time) {
            PlayerManager.this.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.LERP_SIZE));
         }

         @Override
         public void onCenterChanged(WorldBorder border, double centerX, double centerZ) {
            PlayerManager.this.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_CENTER));
         }

         @Override
         public void onWarningTimeChanged(WorldBorder border, int warningTime) {
            PlayerManager.this.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_WARNING_TIME));
         }

         @Override
         public void onWarningBlocksChanged(WorldBorder border, int warningBlockDistance) {
            PlayerManager.this.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_WARNING_BLOCKS));
         }

         @Override
         public void onDamagePerBlockChanged(WorldBorder border, double damagePerBlock) {
         }

         @Override
         public void onSafeZoneChanged(WorldBorder border, double safeZoneRadius) {
         }
      });
   }

   @Nullable
   public CompoundTag loadPlayerData(ServerPlayerEntity player) {
      CompoundTag lv = this.server.getSaveProperties().getPlayerData();
      CompoundTag lv2;
      if (player.getName().getString().equals(this.server.getUserName()) && lv != null) {
         lv2 = lv;
         player.fromTag(lv);
         LOGGER.debug("loading single player");
      } else {
         lv2 = this.saveHandler.loadPlayerData(player);
      }

      return lv2;
   }

   protected void savePlayerData(ServerPlayerEntity player) {
      this.saveHandler.savePlayerData(player);
      ServerStatHandler lv = this.statisticsMap.get(player.getUuid());
      if (lv != null) {
         lv.save();
      }

      PlayerAdvancementTracker lv2 = this.advancementTrackers.get(player.getUuid());
      if (lv2 != null) {
         lv2.save();
      }
   }

   public void remove(ServerPlayerEntity player) {
      ServerWorld lv = player.getServerWorld();
      player.incrementStat(Stats.LEAVE_GAME);
      this.savePlayerData(player);
      if (player.hasVehicle()) {
         Entity lv2 = player.getRootVehicle();
         if (lv2.hasPlayerRider()) {
            LOGGER.debug("Removing player mount");
            player.stopRiding();
            lv.removeEntity(lv2);
            lv2.removed = true;

            for (Entity lv3 : lv2.getPassengersDeep()) {
               lv.removeEntity(lv3);
               lv3.removed = true;
            }

            lv.getChunk(player.chunkX, player.chunkZ).markDirty();
         }
      }

      player.detach();
      lv.removePlayer(player);
      player.getAdvancementTracker().clearCriteria();
      this.players.remove(player);
      this.server.getBossBarManager().onPlayerDisconnect(player);
      UUID uUID = player.getUuid();
      ServerPlayerEntity lv4 = this.playerMap.get(uUID);
      if (lv4 == player) {
         this.playerMap.remove(uUID);
         this.statisticsMap.remove(uUID);
         this.advancementTrackers.remove(uUID);
      }

      this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
   }

   @Nullable
   public Text checkCanJoin(SocketAddress address, GameProfile profile) {
      if (this.bannedProfiles.contains(profile)) {
         BannedPlayerEntry lv = this.bannedProfiles.get(profile);
         MutableText lv2 = new TranslatableText("multiplayer.disconnect.banned.reason", lv.getReason());
         if (lv.getExpiryDate() != null) {
            lv2.append(new TranslatableText("multiplayer.disconnect.banned.expiration", DATE_FORMATTER.format(lv.getExpiryDate())));
         }

         return lv2;
      } else if (!this.isWhitelisted(profile)) {
         return new TranslatableText("multiplayer.disconnect.not_whitelisted");
      } else if (this.bannedIps.isBanned(address)) {
         BannedIpEntry lv3 = this.bannedIps.get(address);
         MutableText lv4 = new TranslatableText("multiplayer.disconnect.banned_ip.reason", lv3.getReason());
         if (lv3.getExpiryDate() != null) {
            lv4.append(new TranslatableText("multiplayer.disconnect.banned_ip.expiration", DATE_FORMATTER.format(lv3.getExpiryDate())));
         }

         return lv4;
      } else {
         return this.players.size() >= this.maxPlayers && !this.canBypassPlayerLimit(profile)
            ? new TranslatableText("multiplayer.disconnect.server_full")
            : null;
      }
   }

   public ServerPlayerEntity createPlayer(GameProfile profile) {
      UUID uUID = PlayerEntity.getUuidFromProfile(profile);
      List<ServerPlayerEntity> list = Lists.newArrayList();

      for (int i = 0; i < this.players.size(); i++) {
         ServerPlayerEntity lv = this.players.get(i);
         if (lv.getUuid().equals(uUID)) {
            list.add(lv);
         }
      }

      ServerPlayerEntity lv2 = this.playerMap.get(profile.getId());
      if (lv2 != null && !list.contains(lv2)) {
         list.add(lv2);
      }

      for (ServerPlayerEntity lv3 : list) {
         lv3.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.duplicate_login"));
      }

      ServerWorld lv4 = this.server.getOverworld();
      ServerPlayerInteractionManager lv5;
      if (this.server.isDemo()) {
         lv5 = new DemoServerPlayerInteractionManager(lv4);
      } else {
         lv5 = new ServerPlayerInteractionManager(lv4);
      }

      return new ServerPlayerEntity(this.server, lv4, profile, lv5);
   }

   public ServerPlayerEntity respawnPlayer(ServerPlayerEntity player, boolean alive) {
      this.players.remove(player);
      player.getServerWorld().removePlayer(player);
      BlockPos lv = player.getSpawnPointPosition();
      float f = player.getSpawnAngle();
      boolean bl2 = player.isSpawnPointSet();
      ServerWorld lv2 = this.server.getWorld(player.getSpawnPointDimension());
      Optional<Vec3d> optional;
      if (lv2 != null && lv != null) {
         optional = PlayerEntity.findRespawnPosition(lv2, lv, f, bl2, alive);
      } else {
         optional = Optional.empty();
      }

      ServerWorld lv3 = lv2 != null && optional.isPresent() ? lv2 : this.server.getOverworld();
      ServerPlayerInteractionManager lv4;
      if (this.server.isDemo()) {
         lv4 = new DemoServerPlayerInteractionManager(lv3);
      } else {
         lv4 = new ServerPlayerInteractionManager(lv3);
      }

      ServerPlayerEntity lv6 = new ServerPlayerEntity(this.server, lv3, player.getGameProfile(), lv4);
      lv6.networkHandler = player.networkHandler;
      lv6.copyFrom(player, alive);
      lv6.setEntityId(player.getEntityId());
      lv6.setMainArm(player.getMainArm());

      for (String string : player.getScoreboardTags()) {
         lv6.addScoreboardTag(string);
      }

      this.setGameMode(lv6, player, lv3);
      boolean bl3 = false;
      if (optional.isPresent()) {
         BlockState lv7 = lv3.getBlockState(lv);
         boolean bl4 = lv7.isOf(Blocks.RESPAWN_ANCHOR);
         Vec3d lv8 = optional.get();
         float h;
         if (!lv7.isIn(BlockTags.BEDS) && !bl4) {
            h = f;
         } else {
            Vec3d lv9 = Vec3d.ofBottomCenter(lv).subtract(lv8).normalize();
            h = (float)MathHelper.wrapDegrees(MathHelper.atan2(lv9.z, lv9.x) * 180.0F / (float)Math.PI - 90.0);
         }

         lv6.refreshPositionAndAngles(lv8.x, lv8.y, lv8.z, h, 0.0F);
         lv6.setSpawnPoint(lv3.getRegistryKey(), lv, f, bl2, false);
         bl3 = !alive && bl4;
      } else if (lv != null) {
         lv6.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.NO_RESPAWN_BLOCK, 0.0F));
      }

      while (!lv3.isSpaceEmpty(lv6) && lv6.getY() < 256.0) {
         lv6.updatePosition(lv6.getX(), lv6.getY() + 1.0, lv6.getZ());
      }

      WorldProperties lv10 = lv6.world.getLevelProperties();
      lv6.networkHandler
         .sendPacket(
            new PlayerRespawnS2CPacket(
               lv6.world.getDimension(),
               lv6.world.getRegistryKey(),
               BiomeAccess.hashSeed(lv6.getServerWorld().getSeed()),
               lv6.interactionManager.getGameMode(),
               lv6.interactionManager.getPreviousGameMode(),
               lv6.getServerWorld().isDebugWorld(),
               lv6.getServerWorld().isFlat(),
               alive
            )
         );
      lv6.networkHandler.requestTeleport(lv6.getX(), lv6.getY(), lv6.getZ(), lv6.yaw, lv6.pitch);
      lv6.networkHandler.sendPacket(new PlayerSpawnPositionS2CPacket(lv3.getSpawnPos(), lv3.getSpawnAngle()));
      lv6.networkHandler.sendPacket(new DifficultyS2CPacket(lv10.getDifficulty(), lv10.isDifficultyLocked()));
      lv6.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(lv6.experienceProgress, lv6.totalExperience, lv6.experienceLevel));
      this.sendWorldInfo(lv6, lv3);
      this.sendCommandTree(lv6);
      lv3.onPlayerRespawned(lv6);
      this.players.add(lv6);
      this.playerMap.put(lv6.getUuid(), lv6);
      lv6.onSpawn();
      lv6.setHealth(lv6.getHealth());
      if (bl3) {
         lv6.networkHandler
            .sendPacket(
               new PlaySoundS2CPacket(
                  SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, (double)lv.getX(), (double)lv.getY(), (double)lv.getZ(), 1.0F, 1.0F
               )
            );
      }

      return lv6;
   }

   public void sendCommandTree(ServerPlayerEntity player) {
      GameProfile gameProfile = player.getGameProfile();
      int i = this.server.getPermissionLevel(gameProfile);
      this.sendCommandTree(player, i);
   }

   public void updatePlayerLatency() {
      if (++this.latencyUpdateTimer > 600) {
         this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_LATENCY, this.players));
         this.latencyUpdateTimer = 0;
      }
   }

   public void sendToAll(Packet<?> packet) {
      for (int i = 0; i < this.players.size(); i++) {
         this.players.get(i).networkHandler.sendPacket(packet);
      }
   }

   public void sendToDimension(Packet<?> packet, RegistryKey<World> dimension) {
      for (int i = 0; i < this.players.size(); i++) {
         ServerPlayerEntity lv = this.players.get(i);
         if (lv.world.getRegistryKey() == dimension) {
            lv.networkHandler.sendPacket(packet);
         }
      }
   }

   public void sendToTeam(PlayerEntity source, Text message) {
      AbstractTeam lv = source.getScoreboardTeam();
      if (lv != null) {
         for (String string : lv.getPlayerList()) {
            ServerPlayerEntity lv2 = this.getPlayer(string);
            if (lv2 != null && lv2 != source) {
               lv2.sendSystemMessage(message, source.getUuid());
            }
         }
      }
   }

   public void sendToOtherTeams(PlayerEntity source, Text message) {
      AbstractTeam lv = source.getScoreboardTeam();
      if (lv == null) {
         this.broadcastChatMessage(message, MessageType.SYSTEM, source.getUuid());
      } else {
         for (int i = 0; i < this.players.size(); i++) {
            ServerPlayerEntity lv2 = this.players.get(i);
            if (lv2.getScoreboardTeam() != lv) {
               lv2.sendSystemMessage(message, source.getUuid());
            }
         }
      }
   }

   public String[] getPlayerNames() {
      String[] strings = new String[this.players.size()];

      for (int i = 0; i < this.players.size(); i++) {
         strings[i] = this.players.get(i).getGameProfile().getName();
      }

      return strings;
   }

   public BannedPlayerList getUserBanList() {
      return this.bannedProfiles;
   }

   public BannedIpList getIpBanList() {
      return this.bannedIps;
   }

   public void addToOperators(GameProfile profile) {
      this.ops.add(new OperatorEntry(profile, this.server.getOpPermissionLevel(), this.ops.isOp(profile)));
      ServerPlayerEntity lv = this.getPlayer(profile.getId());
      if (lv != null) {
         this.sendCommandTree(lv);
      }
   }

   public void removeFromOperators(GameProfile profile) {
      this.ops.remove(profile);
      ServerPlayerEntity lv = this.getPlayer(profile.getId());
      if (lv != null) {
         this.sendCommandTree(lv);
      }
   }

   private void sendCommandTree(ServerPlayerEntity player, int permissionLevel) {
      if (player.networkHandler != null) {
         byte b;
         if (permissionLevel <= 0) {
            b = 24;
         } else if (permissionLevel >= 4) {
            b = 28;
         } else {
            b = (byte)(24 + permissionLevel);
         }

         player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, b));
      }

      this.server.getCommandManager().sendCommandTree(player);
   }

   public boolean isWhitelisted(GameProfile profile) {
      return !this.whitelistEnabled || this.ops.contains(profile) || this.whitelist.contains(profile);
   }

   public boolean isOperator(GameProfile profile) {
      return this.ops.contains(profile) || this.server.isHost(profile) && this.server.getSaveProperties().areCommandsAllowed() || this.cheatsAllowed;
   }

   @Nullable
   public ServerPlayerEntity getPlayer(String name) {
      for (ServerPlayerEntity lv : this.players) {
         if (lv.getGameProfile().getName().equalsIgnoreCase(name)) {
            return lv;
         }
      }

      return null;
   }

   public void sendToAround(@Nullable PlayerEntity player, double x, double y, double z, double distance, RegistryKey<World> worldKey, Packet<?> packet) {
      for (int i = 0; i < this.players.size(); i++) {
         ServerPlayerEntity lv = this.players.get(i);
         if (lv != player && lv.world.getRegistryKey() == worldKey) {
            double h = x - lv.getX();
            double j = y - lv.getY();
            double k = z - lv.getZ();
            if (h * h + j * j + k * k < distance * distance) {
               lv.networkHandler.sendPacket(packet);
            }
         }
      }
   }

   public void saveAllPlayerData() {
      for (int i = 0; i < this.players.size(); i++) {
         this.savePlayerData(this.players.get(i));
      }
   }

   public Whitelist getWhitelist() {
      return this.whitelist;
   }

   public String[] getWhitelistedNames() {
      return this.whitelist.getNames();
   }

   public OperatorList getOpList() {
      return this.ops;
   }

   public String[] getOpNames() {
      return this.ops.getNames();
   }

   public void reloadWhitelist() {
   }

   public void sendWorldInfo(ServerPlayerEntity player, ServerWorld world) {
      WorldBorder lv = this.server.getOverworld().getWorldBorder();
      player.networkHandler.sendPacket(new WorldBorderS2CPacket(lv, WorldBorderS2CPacket.Type.INITIALIZE));
      player.networkHandler
         .sendPacket(new WorldTimeUpdateS2CPacket(world.getTime(), world.getTimeOfDay(), world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)));
      player.networkHandler.sendPacket(new PlayerSpawnPositionS2CPacket(world.getSpawnPos(), world.getSpawnAngle()));
      if (world.isRaining()) {
         player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STARTED, 0.0F));
         player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, world.getRainGradient(1.0F)));
         player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, world.getThunderGradient(1.0F)));
      }
   }

   public void sendPlayerStatus(ServerPlayerEntity player) {
      player.refreshScreenHandler(player.playerScreenHandler);
      player.markHealthDirty();
      player.networkHandler.sendPacket(new HeldItemChangeS2CPacket(player.inventory.selectedSlot));
   }

   public int getCurrentPlayerCount() {
      return this.players.size();
   }

   public int getMaxPlayerCount() {
      return this.maxPlayers;
   }

   public boolean isWhitelistEnabled() {
      return this.whitelistEnabled;
   }

   public void setWhitelistEnabled(boolean whitelistEnabled) {
      this.whitelistEnabled = whitelistEnabled;
   }

   public List<ServerPlayerEntity> getPlayersByIp(String ip) {
      List<ServerPlayerEntity> list = Lists.newArrayList();

      for (ServerPlayerEntity lv : this.players) {
         if (lv.getIp().equals(ip)) {
            list.add(lv);
         }
      }

      return list;
   }

   public int getViewDistance() {
      return this.viewDistance;
   }

   public MinecraftServer getServer() {
      return this.server;
   }

   public CompoundTag getUserData() {
      return null;
   }

   @Environment(EnvType.CLIENT)
   public void setGameMode(GameMode gameMode) {
      this.gameMode = gameMode;
   }

   private void setGameMode(ServerPlayerEntity player, @Nullable ServerPlayerEntity oldPlayer, ServerWorld world) {
      if (oldPlayer != null) {
         player.interactionManager.setGameMode(oldPlayer.interactionManager.getGameMode(), oldPlayer.interactionManager.getPreviousGameMode());
      } else if (this.gameMode != null) {
         player.interactionManager.setGameMode(this.gameMode, GameMode.NOT_SET);
      }

      player.interactionManager.setGameModeIfNotPresent(world.getServer().getSaveProperties().getGameMode());
   }

   @Environment(EnvType.CLIENT)
   public void setCheatsAllowed(boolean cheatsAllowed) {
      this.cheatsAllowed = cheatsAllowed;
   }

   public void disconnectAllPlayers() {
      for (int i = 0; i < this.players.size(); i++) {
         this.players.get(i).networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.server_shutdown"));
      }
   }

   public void broadcastChatMessage(Text message, MessageType type, UUID senderUuid) {
      this.server.sendSystemMessage(message, senderUuid);
      this.sendToAll(new GameMessageS2CPacket(message, type, senderUuid));
   }

   public ServerStatHandler createStatHandler(PlayerEntity player) {
      UUID uUID = player.getUuid();
      ServerStatHandler lv = uUID == null ? null : this.statisticsMap.get(uUID);
      if (lv == null) {
         File file = this.server.getSavePath(WorldSavePath.STATS).toFile();
         File file2 = new File(file, uUID + ".json");
         if (!file2.exists()) {
            File file3 = new File(file, player.getName().getString() + ".json");
            if (file3.exists() && file3.isFile()) {
               file3.renameTo(file2);
            }
         }

         lv = new ServerStatHandler(this.server, file2);
         this.statisticsMap.put(uUID, lv);
      }

      return lv;
   }

   public PlayerAdvancementTracker getAdvancementTracker(ServerPlayerEntity player) {
      UUID uUID = player.getUuid();
      PlayerAdvancementTracker lv = this.advancementTrackers.get(uUID);
      if (lv == null) {
         File file = this.server.getSavePath(WorldSavePath.ADVANCEMENTS).toFile();
         File file2 = new File(file, uUID + ".json");
         lv = new PlayerAdvancementTracker(this.server.getDataFixer(), this, this.server.getAdvancementLoader(), file2, player);
         this.advancementTrackers.put(uUID, lv);
      }

      lv.setOwner(player);
      return lv;
   }

   public void setViewDistance(int viewDistance) {
      this.viewDistance = viewDistance;
      this.sendToAll(new ChunkLoadDistanceS2CPacket(viewDistance));

      for (ServerWorld lv : this.server.getWorlds()) {
         if (lv != null) {
            lv.getChunkManager().applyViewDistance(viewDistance);
         }
      }
   }

   public List<ServerPlayerEntity> getPlayerList() {
      return this.players;
   }

   @Nullable
   public ServerPlayerEntity getPlayer(UUID uuid) {
      return this.playerMap.get(uuid);
   }

   public boolean canBypassPlayerLimit(GameProfile profile) {
      return false;
   }

   public void onDataPacksReloaded() {
      for (PlayerAdvancementTracker lv : this.advancementTrackers.values()) {
         lv.reload(this.server.getAdvancementLoader());
      }

      this.sendToAll(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
      SynchronizeRecipesS2CPacket lv2 = new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values());

      for (ServerPlayerEntity lv3 : this.players) {
         lv3.networkHandler.sendPacket(lv2);
         lv3.getRecipeBook().sendInitRecipesPacket(lv3);
      }
   }

   public boolean areCheatsAllowed() {
      return this.cheatsAllowed;
   }
}
