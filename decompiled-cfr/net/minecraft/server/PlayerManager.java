/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.server;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.net.SocketAddress;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Collection;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
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
import net.minecraft.server.BannedIpEntry;
import net.minecraft.server.BannedIpList;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.OperatorEntry;
import net.minecraft.server.OperatorList;
import net.minecraft.server.Whitelist;
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
        Object object;
        ServerWorld serverWorld;
        GameProfile gameProfile = player.getGameProfile();
        UserCache _snowman2 = this.server.getUserCache();
        _snowman = _snowman2.getByUuid(gameProfile.getId());
        String _snowman3 = _snowman == null ? gameProfile.getName() : _snowman.getName();
        _snowman2.add(gameProfile);
        CompoundTag _snowman4 = this.loadPlayerData(player);
        RegistryKey<World> _snowman5 = _snowman4 != null ? DimensionType.method_28521(new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)_snowman4.get("Dimension"))).resultOrPartial(arg_0 -> ((Logger)LOGGER).error(arg_0)).orElse(World.OVERWORLD) : World.OVERWORLD;
        ServerWorld _snowman6 = this.server.getWorld(_snowman5);
        if (_snowman6 == null) {
            LOGGER.warn("Unknown respawn dimension {}, defaulting to overworld", _snowman5);
            serverWorld = this.server.getOverworld();
        } else {
            serverWorld = _snowman6;
        }
        player.setWorld(serverWorld);
        player.interactionManager.setWorld((ServerWorld)player.world);
        String _snowman7 = "local";
        if (connection.getAddress() != null) {
            _snowman7 = connection.getAddress().toString();
        }
        LOGGER.info("{}[{}] logged in with entity id {} at ({}, {}, {})", (Object)player.getName().getString(), (Object)_snowman7, (Object)player.getEntityId(), (Object)player.getX(), (Object)player.getY(), (Object)player.getZ());
        WorldProperties _snowman8 = serverWorld.getLevelProperties();
        this.setGameMode(player, null, serverWorld);
        ServerPlayNetworkHandler _snowman9 = new ServerPlayNetworkHandler(this.server, connection, player);
        GameRules _snowman10 = serverWorld.getGameRules();
        boolean _snowman11 = _snowman10.getBoolean(GameRules.DO_IMMEDIATE_RESPAWN);
        boolean _snowman12 = _snowman10.getBoolean(GameRules.REDUCED_DEBUG_INFO);
        _snowman9.sendPacket(new GameJoinS2CPacket(player.getEntityId(), player.interactionManager.getGameMode(), player.interactionManager.getPreviousGameMode(), BiomeAccess.hashSeed(serverWorld.getSeed()), _snowman8.isHardcore(), this.server.getWorldRegistryKeys(), this.registryManager, serverWorld.getDimension(), serverWorld.getRegistryKey(), this.getMaxPlayerCount(), this.viewDistance, _snowman12, !_snowman11, serverWorld.isDebugWorld(), serverWorld.isFlat()));
        _snowman9.sendPacket(new CustomPayloadS2CPacket(CustomPayloadS2CPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(this.getServer().getServerModName())));
        _snowman9.sendPacket(new DifficultyS2CPacket(_snowman8.getDifficulty(), _snowman8.isDifficultyLocked()));
        _snowman9.sendPacket(new PlayerAbilitiesS2CPacket(player.abilities));
        _snowman9.sendPacket(new HeldItemChangeS2CPacket(player.inventory.selectedSlot));
        _snowman9.sendPacket(new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values()));
        _snowman9.sendPacket(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
        this.sendCommandTree(player);
        player.getStatHandler().updateStatSet();
        player.getRecipeBook().sendInitRecipesPacket(player);
        this.sendScoreboard(serverWorld.getScoreboard(), player);
        this.server.forcePlayerSampleUpdate();
        TranslatableText _snowman13 = player.getGameProfile().getName().equalsIgnoreCase(_snowman3) ? new TranslatableText("multiplayer.player.joined", player.getDisplayName()) : new TranslatableText("multiplayer.player.joined.renamed", player.getDisplayName(), _snowman3);
        this.broadcastChatMessage(_snowman13.formatted(Formatting.YELLOW), MessageType.SYSTEM, Util.NIL_UUID);
        _snowman9.requestTeleport(player.getX(), player.getY(), player.getZ(), player.yaw, player.pitch);
        this.players.add(player);
        this.playerMap.put(player.getUuid(), player);
        this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, player));
        for (int _snowman14 = 0; _snowman14 < this.players.size(); ++_snowman14) {
            player.networkHandler.sendPacket(new PlayerListS2CPacket(PlayerListS2CPacket.Action.ADD_PLAYER, this.players.get(_snowman14)));
        }
        serverWorld.onPlayerConnected(player);
        this.server.getBossBarManager().onPlayerConnect(player);
        this.sendWorldInfo(player, serverWorld);
        if (!this.server.getResourcePackUrl().isEmpty()) {
            player.sendResourcePackUrl(this.server.getResourcePackUrl(), this.server.getResourcePackHash());
        }
        Object _snowman14 = player.getStatusEffects().iterator();
        while (_snowman14.hasNext()) {
            object = _snowman14.next();
            _snowman9.sendPacket(new EntityStatusEffectS2CPacket(player.getEntityId(), (StatusEffectInstance)object));
        }
        if (_snowman4 != null && _snowman4.contains("RootVehicle", 10) && (object = EntityType.loadEntityWithPassengers(((CompoundTag)(_snowman14 = _snowman4.getCompound("RootVehicle"))).getCompound("Entity"), serverWorld, vehicle -> {
            if (!serverWorld.tryLoadEntity((Entity)vehicle)) {
                return null;
            }
            return vehicle;
        })) != null) {
            UUID uUID = ((CompoundTag)_snowman14).containsUuid("Attach") ? ((CompoundTag)_snowman14).getUuid("Attach") : null;
            if (((Entity)object).getUuid().equals(uUID)) {
                player.startRiding((Entity)object, true);
            } else {
                for (Entity entity : ((Entity)object).getPassengersDeep()) {
                    if (!entity.getUuid().equals(uUID)) continue;
                    player.startRiding(entity, true);
                    break;
                }
            }
            if (!player.hasVehicle()) {
                LOGGER.warn("Couldn't reattach entity to player");
                serverWorld.removeEntity((Entity)object);
                for (Entity entity : ((Entity)object).getPassengersDeep()) {
                    serverWorld.removeEntity(entity);
                }
            }
        }
        player.onSpawn();
    }

    protected void sendScoreboard(ServerScoreboard scoreboard, ServerPlayerEntity player) {
        HashSet hashSet = Sets.newHashSet();
        for (Team team : scoreboard.getTeams()) {
            player.networkHandler.sendPacket(new TeamS2CPacket(team, 0));
        }
        for (int i = 0; i < 19; ++i) {
            ScoreboardObjective scoreboardObjective = scoreboard.getObjectiveForSlot(i);
            if (scoreboardObjective == null || hashSet.contains(scoreboardObjective)) continue;
            List<Packet<?>> _snowman2 = scoreboard.createChangePackets(scoreboardObjective);
            for (Packet<?> packet : _snowman2) {
                player.networkHandler.sendPacket(packet);
            }
            hashSet.add(scoreboardObjective);
        }
    }

    public void setMainWorld(ServerWorld world) {
        world.getWorldBorder().addListener(new WorldBorderListener(this){
            final /* synthetic */ PlayerManager field_14365;
            {
                this.field_14365 = playerManager;
            }

            public void onSizeChange(WorldBorder border, double size) {
                this.field_14365.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_SIZE));
            }

            public void onInterpolateSize(WorldBorder border, double fromSize, double toSize, long time) {
                this.field_14365.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.LERP_SIZE));
            }

            public void onCenterChanged(WorldBorder border, double centerX, double centerZ) {
                this.field_14365.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_CENTER));
            }

            public void onWarningTimeChanged(WorldBorder border, int warningTime) {
                this.field_14365.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_WARNING_TIME));
            }

            public void onWarningBlocksChanged(WorldBorder border, int warningBlockDistance) {
                this.field_14365.sendToAll(new WorldBorderS2CPacket(border, WorldBorderS2CPacket.Type.SET_WARNING_BLOCKS));
            }

            public void onDamagePerBlockChanged(WorldBorder border, double damagePerBlock) {
            }

            public void onSafeZoneChanged(WorldBorder border, double safeZoneRadius) {
            }
        });
    }

    @Nullable
    public CompoundTag loadPlayerData(ServerPlayerEntity player) {
        CompoundTag compoundTag = this.server.getSaveProperties().getPlayerData();
        if (player.getName().getString().equals(this.server.getUserName()) && compoundTag != null) {
            _snowman = compoundTag;
            player.fromTag(_snowman);
            LOGGER.debug("loading single player");
        } else {
            _snowman = this.saveHandler.loadPlayerData(player);
        }
        return _snowman;
    }

    protected void savePlayerData(ServerPlayerEntity player) {
        this.saveHandler.savePlayerData(player);
        ServerStatHandler serverStatHandler = this.statisticsMap.get(player.getUuid());
        if (serverStatHandler != null) {
            serverStatHandler.save();
        }
        if ((_snowman = this.advancementTrackers.get(player.getUuid())) != null) {
            _snowman.save();
        }
    }

    public void remove(ServerPlayerEntity player) {
        ServerWorld serverWorld = player.getServerWorld();
        player.incrementStat(Stats.LEAVE_GAME);
        this.savePlayerData(player);
        if (player.hasVehicle() && ((Entity)(_snowman2 = player.getRootVehicle())).hasPlayerRider()) {
            LOGGER.debug("Removing player mount");
            player.stopRiding();
            serverWorld.removeEntity((Entity)_snowman2);
            ((Entity)_snowman2).removed = true;
            for (Entity entity : ((Entity)_snowman2).getPassengersDeep()) {
                serverWorld.removeEntity(entity);
                entity.removed = true;
            }
            serverWorld.getChunk(player.chunkX, player.chunkZ).markDirty();
        }
        player.detach();
        serverWorld.removePlayer(player);
        player.getAdvancementTracker().clearCriteria();
        this.players.remove(player);
        this.server.getBossBarManager().onPlayerDisconnect(player);
        Object _snowman2 = player.getUuid();
        ServerPlayerEntity _snowman3 = this.playerMap.get(_snowman2);
        if (_snowman3 == player) {
            this.playerMap.remove(_snowman2);
            this.statisticsMap.remove(_snowman2);
            this.advancementTrackers.remove(_snowman2);
        }
        this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.REMOVE_PLAYER, player));
    }

    @Nullable
    public Text checkCanJoin(SocketAddress address, GameProfile profile) {
        if (this.bannedProfiles.contains(profile)) {
            BannedPlayerEntry bannedPlayerEntry = (BannedPlayerEntry)this.bannedProfiles.get(profile);
            TranslatableText _snowman2 = new TranslatableText("multiplayer.disconnect.banned.reason", bannedPlayerEntry.getReason());
            if (bannedPlayerEntry.getExpiryDate() != null) {
                _snowman2.append(new TranslatableText("multiplayer.disconnect.banned.expiration", DATE_FORMATTER.format(bannedPlayerEntry.getExpiryDate())));
            }
            return _snowman2;
        }
        if (!this.isWhitelisted(profile)) {
            return new TranslatableText("multiplayer.disconnect.not_whitelisted");
        }
        if (this.bannedIps.isBanned(address)) {
            BannedIpEntry _snowman3 = this.bannedIps.get(address);
            TranslatableText _snowman4 = new TranslatableText("multiplayer.disconnect.banned_ip.reason", _snowman3.getReason());
            if (_snowman3.getExpiryDate() != null) {
                _snowman4.append(new TranslatableText("multiplayer.disconnect.banned_ip.expiration", DATE_FORMATTER.format(_snowman3.getExpiryDate())));
            }
            return _snowman4;
        }
        if (this.players.size() >= this.maxPlayers && !this.canBypassPlayerLimit(profile)) {
            return new TranslatableText("multiplayer.disconnect.server_full");
        }
        return null;
    }

    public ServerPlayerEntity createPlayer(GameProfile profile) {
        Object object3;
        UUID uUID = PlayerEntity.getUuidFromProfile(profile);
        ArrayList _snowman2 = Lists.newArrayList();
        for (int i = 0; i < this.players.size(); ++i) {
            Object object2 = this.players.get(i);
            if (!((Entity)object2).getUuid().equals(uUID)) continue;
            _snowman2.add(object2);
        }
        ServerPlayerEntity serverPlayerEntity = this.playerMap.get(profile.getId());
        if (serverPlayerEntity != null && !_snowman2.contains(serverPlayerEntity)) {
            _snowman2.add(serverPlayerEntity);
        }
        for (Object object3 : _snowman2) {
            ((ServerPlayerEntity)object3).networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.duplicate_login"));
        }
        object3 = this.server.getOverworld();
        object2 = this.server.isDemo() ? new DemoServerPlayerInteractionManager((ServerWorld)object3) : new ServerPlayerInteractionManager((ServerWorld)object3);
        return new ServerPlayerEntity(this.server, (ServerWorld)object3, profile, (ServerPlayerInteractionManager)object2);
    }

    public ServerPlayerEntity respawnPlayer(ServerPlayerEntity player, boolean alive) {
        ServerPlayerInteractionManager serverPlayerInteractionManager;
        ServerWorld _snowman6;
        Optional _snowman5;
        this.players.remove(player);
        player.getServerWorld().removePlayer(player);
        BlockPos blockPos = player.getSpawnPointPosition();
        float _snowman2 = player.getSpawnAngle();
        boolean _snowman3 = player.isSpawnPointSet();
        ServerWorld _snowman4 = this.server.getWorld(player.getSpawnPointDimension());
        if (_snowman4 != null && blockPos != null) {
            Optional<Vec3d> optional = PlayerEntity.findRespawnPosition(_snowman4, blockPos, _snowman2, _snowman3, alive);
        } else {
            _snowman5 = Optional.empty();
        }
        ServerWorld serverWorld = _snowman6 = _snowman4 != null && _snowman5.isPresent() ? _snowman4 : this.server.getOverworld();
        if (this.server.isDemo()) {
            DemoServerPlayerInteractionManager _snowman7 = new DemoServerPlayerInteractionManager(_snowman6);
        } else {
            serverPlayerInteractionManager = new ServerPlayerInteractionManager(_snowman6);
        }
        ServerPlayerEntity _snowman8 = new ServerPlayerEntity(this.server, _snowman6, player.getGameProfile(), serverPlayerInteractionManager);
        _snowman8.networkHandler = player.networkHandler;
        _snowman8.copyFrom(player, alive);
        _snowman8.setEntityId(player.getEntityId());
        _snowman8.setMainArm(player.getMainArm());
        for (String string : player.getScoreboardTags()) {
            _snowman8.addScoreboardTag(string);
        }
        this.setGameMode(_snowman8, player, _snowman6);
        boolean bl = false;
        if (_snowman5.isPresent()) {
            float _snowman11;
            BlockState blockState = _snowman6.getBlockState(blockPos);
            boolean _snowman9 = blockState.isOf(Blocks.RESPAWN_ANCHOR);
            Vec3d _snowman10 = (Vec3d)_snowman5.get();
            if (blockState.isIn(BlockTags.BEDS) || _snowman9) {
                Vec3d vec3d = Vec3d.ofBottomCenter(blockPos).subtract(_snowman10).normalize();
                float f = (float)MathHelper.wrapDegrees(MathHelper.atan2(vec3d.z, vec3d.x) * 57.2957763671875 - 90.0);
            } else {
                _snowman11 = _snowman2;
            }
            _snowman8.refreshPositionAndAngles(_snowman10.x, _snowman10.y, _snowman10.z, _snowman11, 0.0f);
            _snowman8.setSpawnPoint(_snowman6.getRegistryKey(), blockPos, _snowman2, _snowman3, false);
            bl = !alive && _snowman9;
        } else if (blockPos != null) {
            _snowman8.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.NO_RESPAWN_BLOCK, 0.0f));
        }
        while (!_snowman6.isSpaceEmpty(_snowman8) && _snowman8.getY() < 256.0) {
            _snowman8.updatePosition(_snowman8.getX(), _snowman8.getY() + 1.0, _snowman8.getZ());
        }
        WorldProperties worldProperties = _snowman8.world.getLevelProperties();
        _snowman8.networkHandler.sendPacket(new PlayerRespawnS2CPacket(_snowman8.world.getDimension(), _snowman8.world.getRegistryKey(), BiomeAccess.hashSeed(_snowman8.getServerWorld().getSeed()), _snowman8.interactionManager.getGameMode(), _snowman8.interactionManager.getPreviousGameMode(), _snowman8.getServerWorld().isDebugWorld(), _snowman8.getServerWorld().isFlat(), alive));
        _snowman8.networkHandler.requestTeleport(_snowman8.getX(), _snowman8.getY(), _snowman8.getZ(), _snowman8.yaw, _snowman8.pitch);
        _snowman8.networkHandler.sendPacket(new PlayerSpawnPositionS2CPacket(_snowman6.getSpawnPos(), _snowman6.getSpawnAngle()));
        _snowman8.networkHandler.sendPacket(new DifficultyS2CPacket(worldProperties.getDifficulty(), worldProperties.isDifficultyLocked()));
        _snowman8.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(_snowman8.experienceProgress, _snowman8.totalExperience, _snowman8.experienceLevel));
        this.sendWorldInfo(_snowman8, _snowman6);
        this.sendCommandTree(_snowman8);
        _snowman6.onPlayerRespawned(_snowman8);
        this.players.add(_snowman8);
        this.playerMap.put(_snowman8.getUuid(), _snowman8);
        _snowman8.onSpawn();
        _snowman8.setHealth(_snowman8.getHealth());
        if (bl) {
            _snowman8.networkHandler.sendPacket(new PlaySoundS2CPacket(SoundEvents.BLOCK_RESPAWN_ANCHOR_DEPLETE, SoundCategory.BLOCKS, blockPos.getX(), blockPos.getY(), blockPos.getZ(), 1.0f, 1.0f));
        }
        return _snowman8;
    }

    public void sendCommandTree(ServerPlayerEntity player) {
        GameProfile gameProfile = player.getGameProfile();
        int _snowman2 = this.server.getPermissionLevel(gameProfile);
        this.sendCommandTree(player, _snowman2);
    }

    public void updatePlayerLatency() {
        if (++this.latencyUpdateTimer > 600) {
            this.sendToAll(new PlayerListS2CPacket(PlayerListS2CPacket.Action.UPDATE_LATENCY, this.players));
            this.latencyUpdateTimer = 0;
        }
    }

    public void sendToAll(Packet<?> packet) {
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get((int)i).networkHandler.sendPacket(packet);
        }
    }

    public void sendToDimension(Packet<?> packet, RegistryKey<World> dimension) {
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (serverPlayerEntity.world.getRegistryKey() != dimension) continue;
            serverPlayerEntity.networkHandler.sendPacket(packet);
        }
    }

    public void sendToTeam(PlayerEntity source, Text message) {
        AbstractTeam abstractTeam = source.getScoreboardTeam();
        if (abstractTeam == null) {
            return;
        }
        Collection<String> _snowman2 = abstractTeam.getPlayerList();
        for (String string : _snowman2) {
            ServerPlayerEntity serverPlayerEntity = this.getPlayer(string);
            if (serverPlayerEntity == null || serverPlayerEntity == source) continue;
            serverPlayerEntity.sendSystemMessage(message, source.getUuid());
        }
    }

    public void sendToOtherTeams(PlayerEntity source, Text message) {
        AbstractTeam abstractTeam = source.getScoreboardTeam();
        if (abstractTeam == null) {
            this.broadcastChatMessage(message, MessageType.SYSTEM, source.getUuid());
            return;
        }
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (serverPlayerEntity.getScoreboardTeam() == abstractTeam) continue;
            serverPlayerEntity.sendSystemMessage(message, source.getUuid());
        }
    }

    public String[] getPlayerNames() {
        String[] stringArray = new String[this.players.size()];
        for (int i = 0; i < this.players.size(); ++i) {
            stringArray[i] = this.players.get(i).getGameProfile().getName();
        }
        return stringArray;
    }

    public BannedPlayerList getUserBanList() {
        return this.bannedProfiles;
    }

    public BannedIpList getIpBanList() {
        return this.bannedIps;
    }

    public void addToOperators(GameProfile profile) {
        this.ops.add(new OperatorEntry(profile, this.server.getOpPermissionLevel(), this.ops.isOp(profile)));
        ServerPlayerEntity serverPlayerEntity = this.getPlayer(profile.getId());
        if (serverPlayerEntity != null) {
            this.sendCommandTree(serverPlayerEntity);
        }
    }

    public void removeFromOperators(GameProfile profile) {
        this.ops.remove(profile);
        ServerPlayerEntity serverPlayerEntity = this.getPlayer(profile.getId());
        if (serverPlayerEntity != null) {
            this.sendCommandTree(serverPlayerEntity);
        }
    }

    private void sendCommandTree(ServerPlayerEntity player, int permissionLevel) {
        if (player.networkHandler != null) {
            byte by = permissionLevel <= 0 ? (byte)24 : (permissionLevel >= 4 ? (byte)28 : (byte)((byte)(24 + permissionLevel)));
            player.networkHandler.sendPacket(new EntityStatusS2CPacket(player, by));
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
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!serverPlayerEntity.getGameProfile().getName().equalsIgnoreCase(name)) continue;
            return serverPlayerEntity;
        }
        return null;
    }

    public void sendToAround(@Nullable PlayerEntity player, double x, double y, double z, double distance, RegistryKey<World> worldKey, Packet<?> packet) {
        for (int i = 0; i < this.players.size(); ++i) {
            ServerPlayerEntity serverPlayerEntity = this.players.get(i);
            if (serverPlayerEntity == player || serverPlayerEntity.world.getRegistryKey() != worldKey || !((_snowman = x - serverPlayerEntity.getX()) * _snowman + (_snowman = y - serverPlayerEntity.getY()) * _snowman + (_snowman = z - serverPlayerEntity.getZ()) * _snowman < distance * distance)) continue;
            serverPlayerEntity.networkHandler.sendPacket(packet);
        }
    }

    public void saveAllPlayerData() {
        for (int i = 0; i < this.players.size(); ++i) {
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
        WorldBorder worldBorder = this.server.getOverworld().getWorldBorder();
        player.networkHandler.sendPacket(new WorldBorderS2CPacket(worldBorder, WorldBorderS2CPacket.Type.INITIALIZE));
        player.networkHandler.sendPacket(new WorldTimeUpdateS2CPacket(world.getTime(), world.getTimeOfDay(), world.getGameRules().getBoolean(GameRules.DO_DAYLIGHT_CYCLE)));
        player.networkHandler.sendPacket(new PlayerSpawnPositionS2CPacket(world.getSpawnPos(), world.getSpawnAngle()));
        if (world.isRaining()) {
            player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_STARTED, 0.0f));
            player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED, world.getRainGradient(1.0f)));
            player.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED, world.getThunderGradient(1.0f)));
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
        ArrayList arrayList = Lists.newArrayList();
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            if (!serverPlayerEntity.getIp().equals(ip)) continue;
            arrayList.add(serverPlayerEntity);
        }
        return arrayList;
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
        for (int i = 0; i < this.players.size(); ++i) {
            this.players.get((int)i).networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.server_shutdown"));
        }
    }

    public void broadcastChatMessage(Text message, MessageType type, UUID senderUuid) {
        this.server.sendSystemMessage(message, senderUuid);
        this.sendToAll(new GameMessageS2CPacket(message, type, senderUuid));
    }

    public ServerStatHandler createStatHandler(PlayerEntity player) {
        ServerStatHandler _snowman2;
        UUID uUID = player.getUuid();
        ServerStatHandler serverStatHandler = _snowman2 = uUID == null ? null : this.statisticsMap.get(uUID);
        if (_snowman2 == null) {
            File file = this.server.getSavePath(WorldSavePath.STATS).toFile();
            _snowman = new File(file, uUID + ".json");
            if (!_snowman.exists() && (_snowman = new File(file, player.getName().getString() + ".json")).exists() && _snowman.isFile()) {
                _snowman.renameTo(_snowman);
            }
            _snowman2 = new ServerStatHandler(this.server, _snowman);
            this.statisticsMap.put(uUID, _snowman2);
        }
        return _snowman2;
    }

    public PlayerAdvancementTracker getAdvancementTracker(ServerPlayerEntity player) {
        UUID uUID = player.getUuid();
        PlayerAdvancementTracker _snowman2 = this.advancementTrackers.get(uUID);
        if (_snowman2 == null) {
            File file = this.server.getSavePath(WorldSavePath.ADVANCEMENTS).toFile();
            _snowman = new File(file, uUID + ".json");
            _snowman2 = new PlayerAdvancementTracker(this.server.getDataFixer(), this, this.server.getAdvancementLoader(), _snowman, player);
            this.advancementTrackers.put(uUID, _snowman2);
        }
        _snowman2.setOwner(player);
        return _snowman2;
    }

    public void setViewDistance(int viewDistance) {
        this.viewDistance = viewDistance;
        this.sendToAll(new ChunkLoadDistanceS2CPacket(viewDistance));
        for (ServerWorld serverWorld : this.server.getWorlds()) {
            if (serverWorld == null) continue;
            serverWorld.getChunkManager().applyViewDistance(viewDistance);
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
        for (PlayerAdvancementTracker playerAdvancementTracker : this.advancementTrackers.values()) {
            playerAdvancementTracker.reload(this.server.getAdvancementLoader());
        }
        this.sendToAll(new SynchronizeTagsS2CPacket(this.server.getTagManager()));
        SynchronizeRecipesS2CPacket synchronizeRecipesS2CPacket = new SynchronizeRecipesS2CPacket(this.server.getRecipeManager().values());
        for (ServerPlayerEntity serverPlayerEntity : this.players) {
            serverPlayerEntity.networkHandler.sendPacket(synchronizeRecipesS2CPacket);
            serverPlayerEntity.getRecipeBook().sendInitRecipesPacket(serverPlayerEntity);
        }
    }

    public boolean areCheatsAllowed() {
        return this.cheatsAllowed;
    }
}

