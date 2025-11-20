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
      GameProfile _snowman = player.getGameProfile();
      UserCache _snowmanx = this.server.getUserCache();
      GameProfile _snowmanxx = _snowmanx.getByUuid(_snowman.getId());
      String _snowmanxxx = _snowmanxx == null ? _snowman.getName() : _snowmanxx.getName();
      _snowmanx.add(_snowman);
      CompoundTag _snowmanxxxx = this.loadPlayerData(player);
      RegistryKey<World> _snowmanxxxxx = _snowmanxxxx != null
         ? DimensionType.method_28521(new Dynamic(NbtOps.INSTANCE, _snowmanxxxx.get("Dimension"))).resultOrPartial(LOGGER::error).orElse(World.OVERWORLD)
         : World.OVERWORLD;
      ServerWorld _snowmanxxxxxx = this.server.getWorld(_snowmanxxxxx);
      ServerWorld _snowmanxxxxxxx;
      if (_snowmanxxxxxx == null) {
         LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", _snowmanxxxxx);
         _snowmanxxxxxxx = this.server.getOverworld();
      } else {
         _snowmanxxxxxxx = _snowmanxxxxxx;
      }

      player.setWorld(_snowmanxxxxxxx);
      player.interactionManager.setWorld((ServerWorld)player.world);
      String _snowmanxxxxxxxx = "local";
      if (connection.getAddress() != null) {
         _snowmanxxxxxxxx = connection.getAddress().toString();
      }

      LOGGER.info(
         "{}[{}] logged in with entity id {} at ({}, {}, {})",
         player.getName().getString(),
         _snowmanxxxxxxxx,
         player.getEntityId(),
         player.getX(),
         player.getY(),
         player.getZ()
      );
      WorldProperties _snowmanxxxxxxxxx = _snowmanxxxxxxx.getLevelProperties();
      this.setGameMode(player, null, _snowmanxxxxxxx);
      ServerPlayNetworkHandler _snowmanxxxxxxxxxx = new ServerPlayNetworkHandler(this.server, connection, player);
      GameRules _snowmanxxxxxxxxxxx = _snowmanxxxxxxx.getGameRules();
      boolean _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
      boolean _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx.getBoolean(GameRules.REDUCED_DEBUG_INFO);
      _snowmanxxxxxxxxxx.sendPacket(
         new GameJoinS2CPacket(
            player.getEntityId(),
            player.interactionManager.getGameMode(),
            player.interactionManager.getPreviousGameMode(),
            BiomeAccess.hashSeed(_snowmanxxxxxxx.getSeed()),
            _snowmanxxxxxxxxx.isHardcore(),
            this.server.getWorldRegistryKeys(),
            this.registryManager,
            _snowmanxxxxxxx.getDimension(),
            _snowmanxxxxxxx.getRegistryKey(),
            this.getMaxPlayerCount(),
            this.viewDistance,
            _snowmanxxxxxxxxxxxxx,
            !_snowmanxxxxxxxxxxxx,
            _snowmanxxxxxxx.isDebugWorld(),
            _snowmanxxxxxxx.isFlat()
         )
      );
      _snowmanxxxxxxxxxx.sendPacket(
         new CustomPayloadS2CPacket(CustomPayloadS2CPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(this.getServer().getServerModName()))
      );
      _snowmanxxxxxxxxxx.sendPacket(new DifficultyS2CPacket(_snowmanxxxxxxxxx.getDifficulty(), _snowmanxxxxxxxxx.isDifficultyLocked()));
      _snowmanxxxxxxxxxx.sendPacket(new PlayerAbilitiesS2CPacket(player.abilities));
      _snowmanxxxxxxxxxx.sendPacket(new HeldItemChangeS2CPacket(player.inventory.selectedSlot));
      _snowmanxxxxxxxxxx.sendPacket(new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values()));
      _snowmanxxxxxxxxxx.sendPacket(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
      this.sendCommandTree(player);
      player.getStatHandler().updateStatSet();
      player.getRecipeBook().sendInitRecipesPacket(player);
      this.sendScoreboard(_snowmanxxxxxxx.getScoreboard(), player);
      this.server.forcePlayerSampleUpdate();
      MutableText _snowmanxxxxxxxxxxxxxx;
      if (player.getGameProfile().getName().equalsIgnoreCase(_snowmanxxx)) {
         _snowmanxxxxxxxxxxxxxx = new TranslatableText("multiplayer.player.joined", player.getDisplayName());
      } else {
         _snowmanxxxxxxxxxxxxxx = new TranslatableText("multiplayer.player.joined.renamed", player.getDisplayName(), _snowmanxxx);
      }

      this.broadcastChatMessage(_snowmanxxxxxxxxxxxxxx.formatted(Formatting.YELLOW), MessageType.SYSTEM, Util.NIL_UUID);
      _snowmanxxxxxxxxxx.requestTeleport(player.getX(), player.getY(), player.getZ(), player.yaw, player.pitch);
      this.players.add(player);
      this.playerMap.put(player.getUuid(), player);
      this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));

      for (int _snowmanxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxx < this.players.size(); _snowmanxxxxxxxxxxxxxxx++) {
         player.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, this.players.get(_snowmanxxxxxxxxxxxxxxx)));
      }

      _snowmanxxxxxxx.onPlayerConnected(player);
      this.server.getBossBarManager().onPlayerConnect(player);
      this.sendWorldInfo(player, _snowmanxxxxxxx);
      if (!this.server.getResourcePackUrl().isEmpty()) {
         player.sendResourcePackUrl(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
      }

      for (StatusEffectInstance _snowmanxxxxxxxxxxxxxxx : player.getStatusEffects()) {
         _snowmanxxxxxxxxxx.sendPacket(new EntityStatusEffectS2CPacket(player.getEntityId(), _snowmanxxxxxxxxxxxxxxx));
      }

      if (_snowmanxxxx != null && _snowmanxxxx.contains("RootVehicle", 10)) {
         CompoundTag _snowmanxxxxxxxxxxxxxxx = _snowmanxxxx.getCompound("RootVehicle");
         Entity _snowmanxxxxxxxxxxxxxxxx = EntityType.loadEntityWithPassengers(
            _snowmanxxxxxxxxxxxxxxx.getCompound("Entity"), _snowmanxxxxxxx, vehicle -> !_snowman.tryLoadEntity(vehicle) ? null : vehicle
         );
         if (_snowmanxxxxxxxxxxxxxxxx != null) {
            UUID _snowmanxxxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxxx.containsUuid("Attach")) {
               _snowmanxxxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxxxx.getUuid("Attach");
            } else {
               _snowmanxxxxxxxxxxxxxxxxx = null;
            }

            if (_snowmanxxxxxxxxxxxxxxxx.getUuid().equals(_snowmanxxxxxxxxxxxxxxxxx)) {
               player.startRiding(_snowmanxxxxxxxxxxxxxxxx, true);
            } else {
               for (Entity _snowmanxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx.getPassengersDeep()) {
                  if (_snowmanxxxxxxxxxxxxxxxxxx.getUuid().equals(_snowmanxxxxxxxxxxxxxxxxx)) {
                     player.startRiding(_snowmanxxxxxxxxxxxxxxxxxx, true);
                     break;
                  }
               }
            }

            if (!player.hasVehicle()) {
               LOGGER.warn("Couldn't reattach entity to player");
               _snowmanxxxxxxx.removeEntity(_snowmanxxxxxxxxxxxxxxxx);

               for (Entity _snowmanxxxxxxxxxxxxxxxxxxx : _snowmanxxxxxxxxxxxxxxxx.getPassengersDeep()) {
                  _snowmanxxxxxxx.removeEntity(_snowmanxxxxxxxxxxxxxxxxxxx);
               }
            }
         }
      }

      player.onSpawn();
   }

   protected void sendScoreboard(ServerScoreboard scoreboard, ServerPlayerEntity player) {
      Set<ScoreboardObjective> _snowman = Sets.newHashSet();

      for (Team _snowmanx : scoreboard.getTeams()) {
         player.networkHandler.sendPacket(new TeamS2CPacket(_snowmanx, 0));
      }

      for (int _snowmanx = 0; _snowmanx < 19; _snowmanx++) {
         ScoreboardObjective _snowmanxx = scoreboard.getObjectiveForSlot(_snowmanx);
         if (_snowmanxx != null && !_snowman.contains(_snowmanxx)) {
            for (Packet<?> _snowmanxxx : scoreboard.createChangePackets(_snowmanxx)) {
               player.networkHandler.sendPacket(_snowmanxxx);
            }

            _snowman.add(_snowmanxx);
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
      CompoundTag _snowman = this.server.getSaveProperties().getPlayerData();
      CompoundTag _snowmanx;
      if (player.getName().getString().equals(this.server.getUserName()) && _snowman != null) {
         _snowmanx = _snowman;
         player.fromTag(_snowman);
         LOGGER.debug("loading single player");
      } else {
         _snowmanx = this.saveHandler.loadPlayerData(player);
      }

      return _snowmanx;
   }

   protected void savePlayerData(ServerPlayerEntity player) {
      this.saveHandler.savePlayerData(player);
      ServerStatHandler _snowman = this.statisticsMap.get(player.getUuid());
      if (_snowman != null) {
         _snowman.save();
      }

      PlayerAdvancementTracker _snowmanx = this.advancementTrackers.get(player.getUuid());
      if (_snowmanx != null) {
         _snowmanx.save();
      }
   }

   public void remove(ServerPlayerEntity player) {
      ServerWorld _snowman = player.getServerWorld();
      player.incrementStat(Stats.LEAVE_GAME);
      this.savePlayerData(player);
      if (player.hasVehicle()) {
         Entity _snowmanx = player.getRootVehicle();
         if (_snowmanx.hasPlayerRider()) {
            LOGGER.debug("Removing player mount");
            player.stopRiding();
            _snowman.removeEntity(_snowmanx);
            _snowmanx.removed = true;

            for (Entity _snowmanxx : _snowmanx.getPassengersDeep()) {
               _snowman.removeEntity(_snowmanxx);
               _snowmanxx.removed = true;
            }

            _snowman.getChunk(player.chunkX, player.chunkZ).markDirty();
         }
      }

      player.detach();
      _snowman.removePlayer(player);
      player.getAdvancementTracker().clearCriteria();
      this.players.remove(player);
      this.server.getBossBarManager().onPlayerDisconnect(player);
      UUID _snowmanx = player.getUuid();
      ServerPlayerEntity _snowmanxx = this.playerMap.get(_snowmanx);
      if (_snowmanxx == player) {
         this.playerMap.remove(_snowmanx);
         this.statisticsMap.remove(_snowmanx);
         this.advancementTrackers.remove(_snowmanx);
      }

      this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
   }

   @Nullable
   public Text checkCanJoin(SocketAddress address, GameProfile profile) {
      if (this.bannedProfiles.contains(profile)) {
         BannedPlayerEntry _snowman = this.bannedProfiles.get(profile);
         MutableText _snowmanx = new TranslatableText("multiplayer.disconnect.banned.reason", _snowman.getReason());
         if (_snowman.getExpiryDate() != null) {
            _snowmanx.append(new TranslatableText("multiplayer.disconnect.banned.expiration", DATE_FORMATTER.format(_snowman.getExpiryDate())));
         }

         return _snowmanx;
      } else if (!this.isWhitelisted(profile)) {
         return new TranslatableText("multiplayer.disconnect.not_whitelisted");
      } else if (this.bannedIps.isBanned(address)) {
         BannedIpEntry _snowman = this.bannedIps.get(address);
         MutableText _snowmanx = new TranslatableText("multiplayer.disconnect.banned_ip.reason", _snowman.getReason());
         if (_snowman.getExpiryDate() != null) {
            _snowmanx.append(new TranslatableText("multiplayer.disconnect.banned_ip.expiration", DATE_FORMATTER.format(_snowman.getExpiryDate())));
         }

         return _snowmanx;
      } else {
         return this.players.size() >= this.maxPlayers && !this.canBypassPlayerLimit(profile)
            ? new TranslatableText("multiplayer.disconnect.server_full")
            : null;
      }
   }

   public ServerPlayerEntity createPlayer(GameProfile profile) {
      UUID _snowman = PlayerEntity.getUuidFromProfile(profile);
      List<ServerPlayerEntity> _snowmanx = Lists.newArrayList();

      for (int _snowmanxx = 0; _snowmanxx < this.players.size(); _snowmanxx++) {
         ServerPlayerEntity _snowmanxxx = this.players.get(_snowmanxx);
         if (_snowmanxxx.getUuid().equals(_snowman)) {
            _snowmanx.add(_snowmanxxx);
         }
      }

      ServerPlayerEntity _snowmanxxx = this.playerMap.get(profile.getId());
      if (_snowmanxxx != null && !_snowmanx.contains(_snowmanxxx)) {
         _snowmanx.add(_snowmanxxx);
      }

      for (ServerPlayerEntity _snowmanxxxx : _snowmanx) {
         _snowmanxxxx.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.duplicate_login"));
      }

      ServerWorld _snowmanxxxx = this.server.getOverworld();
      ServerPlayerInteractionManager _snowmanxxxxx;
      if (this.server.isDemo()) {
         _snowmanxxxxx = new DemoServerPlayerInteractionManager(_snowmanxxxx);
      } else {
         _snowmanxxxxx = new ServerPlayerInteractionManager(_snowmanxxxx);
      }

      return new ServerPlayerEntity(this.server, _snowmanxxxx, profile, _snowmanxxxxx);
   }

   public ServerPlayerEntity respawnPlayer(ServerPlayerEntity player, boolean alive) {
      this.players.remove(player);
      player.getServerWorld().removePlayer(player);
      BlockPos _snowman = player.getSpawnPointPosition();
      float _snowmanx = player.getSpawnAngle();
      boolean _snowmanxx = player.isSpawnPointSet();
      ServerWorld _snowmanxxx = this.server.getWorld(player.getSpawnPointDimension());
      Optional<Vec3d> _snowmanxxxx;
      if (_snowmanxxx != null && _snowman != null) {
         _snowmanxxxx = PlayerEntity.findRespawnPosition(_snowmanxxx, _snowman, _snowmanx, _snowmanxx, alive);
      } else {
         _snowmanxxxx = Optional.empty();
      }

      ServerWorld _snowmanxxxxx = _snowmanxxx != null && _snowmanxxxx.isPresent() ? _snowmanxxx : this.server.getOverworld();
      ServerPlayerInteractionManager _snowmanxxxxxx;
      if (this.server.isDemo()) {
         _snowmanxxxxxx = new DemoServerPlayerInteractionManager(_snowmanxxxxx);
      } else {
         _snowmanxxxxxx = new ServerPlayerInteractionManager(_snowmanxxxxx);
      }

      ServerPlayerEntity _snowmanxxxxxxx = new ServerPlayerEntity(this.server, _snowmanxxxxx, player.getGameProfile(), _snowmanxxxxxx);
      _snowmanxxxxxxx.networkHandler = player.networkHandler;
      _snowmanxxxxxxx.copyFrom(player, alive);
      _snowmanxxxxxxx.setEntityId(player.getEntityId());
      _snowmanxxxxxxx.setMainArm(player.getMainArm());

      for (String _snowmanxxxxxxxx : player.getScoreboardTags()) {
         _snowmanxxxxxxx.addScoreboardTag(_snowmanxxxxxxxx);
      }

      this.setGameMode(_snowmanxxxxxxx, player, _snowmanxxxxx);
      boolean _snowmanxxxxxxxx = false;
      if (_snowmanxxxx.isPresent()) {
         BlockState _snowmanxxxxxxxxx = _snowmanxxxxx.getBlockState(_snowman);
         boolean _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx.isOf(Blocks.RESPAWN_ANCHOR);
         Vec3d _snowmanxxxxxxxxxxx = _snowmanxxxx.get();
         float _snowmanxxxxxxxxxxxx;
         if (!_snowmanxxxxxxxxx.isIn(BlockTags.BEDS) && !_snowmanxxxxxxxxxx) {
            _snowmanxxxxxxxxxxxx = _snowmanx;
         } else {
            Vec3d _snowmanxxxxxxxxxxxxx = Vec3d.ofBottomCenter(_snowman).subtract(_snowmanxxxxxxxxxxx).normalize();
            _snowmanxxxxxxxxxxxx = (float)MathHelper.wrapDegrees(MathHelper.atan2(_snowmanxxxxxxxxxxxxx.z, _snowmanxxxxxxxxxxxxx.x) * 180.0F / (float)Math.PI - 90.0);
         }

         _snowmanxxxxxxx.refreshPositionAndAngles(_snowmanxxxxxxxxxxx.x, _snowmanxxxxxxxxxxx.y, _snowmanxxxxxxxxxxx.z, _snowmanxxxxxxxxxxxx, 0.0F);
         _snowmanxxxxxxx.setSpawnPoint(_snowmanxxxxx.getRegistryKey(), _snowman, _snowmanx, _snowmanxx, false);
         _snowmanxxxxxxxx = !alive && _snowmanxxxxxxxxxx;
      } else if (_snowman != null) {
         _snowmanxxxxxxx.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.NO_RESPAWN_BLOCK, 0.0F));
      }

      while (!_snowmanxxxxx.isSpaceEmpty(_snowmanxxxxxxx) && _snowmanxxxxxxx.getY() < 256.0) {
         _snowmanxxxxxxx.updatePosition(_snowmanxxxxxxx.getX(), _snowmanxxxxxxx.getY() + 1.0, _snowmanxxxxxxx.getZ());
      }

      WorldProperties _snowmanxxxxxxxxx = _snowmanxxxxxxx.world.getLevelProperties();
      _snowmanxxxxxxx.networkHandler
         .sendPacket(
            new PlayerRespawnS2CPacket(
               _snowmanxxxxxxx.world.getDimension(),
               _snowmanxxxxxxx.world.getRegistryKey(),
               BiomeAccess.hashSeed(_snowmanxxxxxxx.getServerWorld().getSeed()),
               _snowmanxxxxxxx.interactionManager.getGameMode(),
               _snowmanxxxxxxx.interactionManager.getPreviousGameMode(),
               _snowmanxxxxxxx.getServerWorld().isDebugWorld(),
               _snowmanxxxxxxx.getServerWorld().isFlat(),
               alive
            )
         );
      _snowmanxxxxxxx.networkHandler.requestTeleport(_snowmanxxxxxxx.getX(), _snowmanxxxxxxx.getY(), _snowmanxxxxxxx.getZ(), _snowmanxxxxxxx.yaw, _snowmanxxxxxxx.pitch);
      _snowmanxxxxxxx.networkHandler.sendPacket(new PlayerSpawnPositionS2CPacket(_snowmanxxxxx.getSpawnPos(), _snowmanxxxxx.getSpawnAngle()));
      _snowmanxxxxxxx.networkHandler.sendPacket(new DifficultyS2CPacket(_snowmanxxxxxxxxx.getDifficulty(), _snowmanxxxxxxxxx.isDifficultyLocked()));
      _snowmanxxxxxxx.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(_snowmanxxxxxxx.experienceProgress, _snowmanxxxxxxx.totalExperience, _snowmanxxxxxxx.experienceLevel));
      this.sendWorldInfo(_snowmanxxxxxxx, _snowmanxxxxx);
      this.sendCommandTree(_snowmanxxxxxxx);
      _snowmanxxxxx.onPlayerRespawned(_snowmanxxxxxxx);
      this.players.add(_snowmanxxxxxxx);
      this.playerMap.put(_snowmanxxxxxxx.getUuid(), _snowmanxxxxxxx);
      _snowmanxxxxxxx.onSpawn();
      _snowmanxxxxxxx.setHealth(_snowmanxxxxxxx.getHealth());
      if (_snowmanxxxxxxxx) {
         _snowmanxxxxxxx.networkHandler
            .sendPacket(
               new PlaySoundS2CPacket(
                  SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, (double)_snowman.getX(), (double)_snowman.getY(), (double)_snowman.getZ(), 1.0F, 1.0F
               )
            );
      }

      return _snowmanxxxxxxx;
   }

   public void sendCommandTree(ServerPlayerEntity player) {
      GameProfile _snowman = player.getGameProfile();
      int _snowmanx = this.server.getPermissionLevel(_snowman);
      this.sendCommandTree(player, _snowmanx);
   }

   public void updatePlayerLatency() {
      if (++this.latencyUpdateTimer > 600) {
         this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_LATENCY, this.players));
         this.latencyUpdateTimer = 0;
      }
   }

   public void sendToAll(Packet<?> packet) {
      for (int _snowman = 0; _snowman < this.players.size(); _snowman++) {
         this.players.get(_snowman).networkHandler.sendPacket(packet);
      }
   }

   public void sendToDimension(Packet<?> packet, RegistryKey<World> dimension) {
      for (int _snowman = 0; _snowman < this.players.size(); _snowman++) {
         ServerPlayerEntity _snowmanx = this.players.get(_snowman);
         if (_snowmanx.world.getRegistryKey() == dimension) {
            _snowmanx.networkHandler.sendPacket(packet);
         }
      }
   }

   public void sendToTeam(PlayerEntity source, Text message) {
      AbstractTeam _snowman = source.getScoreboardTeam();
      if (_snowman != null) {
         for (String _snowmanx : _snowman.getPlayerList()) {
            ServerPlayerEntity _snowmanxx = this.getPlayer(_snowmanx);
            if (_snowmanxx != null && _snowmanxx != source) {
               _snowmanxx.sendSystemMessage(message, source.getUuid());
            }
         }
      }
   }

   public void sendToOtherTeams(PlayerEntity source, Text message) {
      AbstractTeam _snowman = source.getScoreboardTeam();
      if (_snowman == null) {
         this.broadcastChatMessage(message, MessageType.SYSTEM, source.getUuid());
      } else {
         for (int _snowmanx = 0; _snowmanx < this.players.size(); _snowmanx++) {
            ServerPlayerEntity _snowmanxx = this.players.get(_snowmanx);
            if (_snowmanxx.getScoreboardTeam() != _snowman) {
               _snowmanxx.sendSystemMessage(message, source.getUuid());
            }
         }
      }
   }

   public String[] getPlayerNames() {
      String[] _snowman = new String[this.players.size()];

      for (int _snowmanx = 0; _snowmanx < this.players.size(); _snowmanx++) {
         _snowman[_snowmanx] = this.players.get(_snowmanx).getGameProfile().getName();
      }

      return _snowman;
   }

   public BannedPlayerList getUserBanList() {
      return this.bannedProfiles;
   }

   public BannedIpList getIpBanList() {
      return this.bannedIps;
   }

   public void addToOperators(GameProfile profile) {
      this.ops.add(new OperatorEntry(profile, this.server.getOpPermissionLevel(), this.ops.isOp(profile)));
      ServerPlayerEntity _snowman = this.getPlayer(profile.getId());
      if (_snowman != null) {
         this.sendCommandTree(_snowman);
      }
   }

   public void removeFromOperators(GameProfile profile) {
      this.ops.remove(profile);
      ServerPlayerEntity _snowman = this.getPlayer(profile.getId());
      if (_snowman != null) {
         this.sendCommandTree(_snowman);
      }
   }

   private void sendCommandTree(ServerPlayerEntity player, int permissionLevel) {
      if (player.networkHandler != null) {
         byte _snowman;
         if (permissionLevel <= 0) {
            _snowman = 24;
         } else if (permissionLevel >= 4) {
            _snowman = 28;
         } else {
            _snowman = (byte)(24 + permissionLevel);
         }

         player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, _snowman));
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
      for (ServerPlayerEntity _snowman : this.players) {
         if (_snowman.getGameProfile().getName().equalsIgnoreCase(name)) {
            return _snowman;
         }
      }

      return null;
   }

   public void sendToAround(@Nullable PlayerEntity player, double x, double y, double z, double distance, RegistryKey<World> worldKey, Packet<?> packet) {
      for (int _snowman = 0; _snowman < this.players.size(); _snowman++) {
         ServerPlayerEntity _snowmanx = this.players.get(_snowman);
         if (_snowmanx != player && _snowmanx.world.getRegistryKey() == worldKey) {
            double _snowmanxx = x - _snowmanx.getX();
            double _snowmanxxx = y - _snowmanx.getY();
            double _snowmanxxxx = z - _snowmanx.getZ();
            if (_snowmanxx * _snowmanxx + _snowmanxxx * _snowmanxxx + _snowmanxxxx * _snowmanxxxx < distance * distance) {
               _snowmanx.networkHandler.sendPacket(packet);
            }
         }
      }
   }

   public void saveAllPlayerData() {
      for (int _snowman = 0; _snowman < this.players.size(); _snowman++) {
         this.savePlayerData(this.players.get(_snowman));
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
      WorldBorder _snowman = this.server.getOverworld().getWorldBorder();
      player.networkHandler.sendPacket(new WorldBorderS2CPacket(_snowman, WorldBorderS2CPacket.Type.INITIALIZE));
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
      List<ServerPlayerEntity> _snowman = Lists.newArrayList();

      for (ServerPlayerEntity _snowmanx : this.players) {
         if (_snowmanx.getIp().equals(ip)) {
            _snowman.add(_snowmanx);
         }
      }

      return _snowman;
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

   public void setCheatsAllowed(boolean cheatsAllowed) {
      this.cheatsAllowed = cheatsAllowed;
   }

   public void disconnectAllPlayers() {
      for (int _snowman = 0; _snowman < this.players.size(); _snowman++) {
         this.players.get(_snowman).networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.server_shutdown"));
      }
   }

   public void broadcastChatMessage(Text message, MessageType type, UUID senderUuid) {
      this.server.sendSystemMessage(message, senderUuid);
      this.sendToAll(new GameMessageS2CPacket(message, type, senderUuid));
   }

   public ServerStatHandler createStatHandler(PlayerEntity player) {
      UUID _snowman = player.getUuid();
      ServerStatHandler _snowmanx = _snowman == null ? null : this.statisticsMap.get(_snowman);
      if (_snowmanx == null) {
         File _snowmanxx = this.server.getSavePath(WorldSavePath.STATS).toFile();
         File _snowmanxxx = new File(_snowmanxx, _snowman + ".json");
         if (!_snowmanxxx.exists()) {
            File _snowmanxxxx = new File(_snowmanxx, player.getName().getString() + ".json");
            if (_snowmanxxxx.exists() && _snowmanxxxx.isFile()) {
               _snowmanxxxx.renameTo(_snowmanxxx);
            }
         }

         _snowmanx = new ServerStatHandler(this.server, _snowmanxxx);
         this.statisticsMap.put(_snowman, _snowmanx);
      }

      return _snowmanx;
   }

   public PlayerAdvancementTracker getAdvancementTracker(ServerPlayerEntity player) {
      UUID _snowman = player.getUuid();
      PlayerAdvancementTracker _snowmanx = this.advancementTrackers.get(_snowman);
      if (_snowmanx == null) {
         File _snowmanxx = this.server.getSavePath(WorldSavePath.ADVANCEMENTS).toFile();
         File _snowmanxxx = new File(_snowmanxx, _snowman + ".json");
         _snowmanx = new PlayerAdvancementTracker(this.server.getDataFixer(), this, this.server.getAdvancementLoader(), _snowmanxxx, player);
         this.advancementTrackers.put(_snowman, _snowmanx);
      }

      _snowmanx.setOwner(player);
      return _snowmanx;
   }

   public void setViewDistance(int viewDistance) {
      this.viewDistance = viewDistance;
      this.sendToAll(new ChunkLoadDistanceS2CPacket(viewDistance));

      for (ServerWorld _snowman : this.server.getWorlds()) {
         if (_snowman != null) {
            _snowman.getChunkManager().applyViewDistance(viewDistance);
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
      for (PlayerAdvancementTracker _snowman : this.advancementTrackers.values()) {
         _snowman.reload(this.server.getAdvancementLoader());
      }

      this.sendToAll(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
      SynchronizeRecipesS2CPacket _snowman = new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values());

      for (ServerPlayerEntity _snowmanx : this.players) {
         _snowmanx.networkHandler.sendPacket(_snowman);
         _snowmanx.getRecipeBook().sendInitRecipesPacket(_snowmanx);
      }
   }

   public boolean areCheatsAllowed() {
      return this.cheatsAllowed;
   }
}
