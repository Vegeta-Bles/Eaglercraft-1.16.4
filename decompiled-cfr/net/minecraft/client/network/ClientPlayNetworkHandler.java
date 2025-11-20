/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  com.google.common.collect.Maps
 *  com.google.common.collect.Multimap
 *  com.google.common.collect.Sets
 *  com.mojang.authlib.GameProfile
 *  com.mojang.brigadier.CommandDispatcher
 *  io.netty.buffer.Unpooled
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.client.network;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Multimap;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import io.netty.buffer.Unpooled;
import java.io.File;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URISyntaxException;
import java.net.URLDecoder;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BannerBlockEntity;
import net.minecraft.block.entity.BeaconBlockEntity;
import net.minecraft.block.entity.BedBlockEntity;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CampfireBlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.ConduitBlockEntity;
import net.minecraft.block.entity.EndGatewayBlockEntity;
import net.minecraft.block.entity.JigsawBlockEntity;
import net.minecraft.block.entity.MobSpawnerBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.block.entity.StructureBlockBlockEntity;
import net.minecraft.client.ClientBrandRetriever;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.MapRenderer;
import net.minecraft.client.gui.screen.ConfirmScreen;
import net.minecraft.client.gui.screen.CreditsScreen;
import net.minecraft.client.gui.screen.DeathScreen;
import net.minecraft.client.gui.screen.DemoScreen;
import net.minecraft.client.gui.screen.DisconnectedScreen;
import net.minecraft.client.gui.screen.DownloadingTerrainScreen;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.gui.screen.StatsListener;
import net.minecraft.client.gui.screen.TitleScreen;
import net.minecraft.client.gui.screen.ingame.BookScreen;
import net.minecraft.client.gui.screen.ingame.CommandBlockScreen;
import net.minecraft.client.gui.screen.ingame.CreativeInventoryScreen;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.minecraft.client.gui.screen.ingame.HorseScreen;
import net.minecraft.client.gui.screen.multiplayer.MultiplayerScreen;
import net.minecraft.client.gui.screen.recipebook.RecipeBookProvider;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.input.KeyboardInput;
import net.minecraft.client.network.ClientAdvancementManager;
import net.minecraft.client.network.ClientCommandSource;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.client.network.ClientPlayerInteractionManager;
import net.minecraft.client.network.DataQueryHandler;
import net.minecraft.client.network.OtherClientPlayerEntity;
import net.minecraft.client.network.PlayerListEntry;
import net.minecraft.client.network.ServerInfo;
import net.minecraft.client.options.GameOptions;
import net.minecraft.client.options.ServerList;
import net.minecraft.client.particle.ItemPickupParticle;
import net.minecraft.client.realms.gui.screen.DisconnectedRealmsScreen;
import net.minecraft.client.realms.gui.screen.RealmsScreen;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.client.render.debug.BeeDebugRenderer;
import net.minecraft.client.render.debug.GoalSelectorDebugRenderer;
import net.minecraft.client.render.debug.NeighborUpdateDebugRenderer;
import net.minecraft.client.render.debug.VillageDebugRenderer;
import net.minecraft.client.render.debug.WorldGenAttemptDebugRenderer;
import net.minecraft.client.search.SearchManager;
import net.minecraft.client.search.SearchableContainer;
import net.minecraft.client.sound.AbstractBeeSoundInstance;
import net.minecraft.client.sound.AggressiveBeeSoundInstance;
import net.minecraft.client.sound.GuardianAttackSoundInstance;
import net.minecraft.client.sound.MovingMinecartSoundInstance;
import net.minecraft.client.sound.PassiveBeeSoundInstance;
import net.minecraft.client.sound.PositionedSoundInstance;
import net.minecraft.client.sound.SoundInstance;
import net.minecraft.client.toast.RecipeToast;
import net.minecraft.client.world.ClientChunkManager;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.command.CommandSource;
import net.minecraft.entity.AreaEffectCloudEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.ExperienceOrbEntity;
import net.minecraft.entity.EyeOfEnderEntity;
import net.minecraft.entity.FallingBlockEntity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LightningEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.ai.pathing.Path;
import net.minecraft.entity.attribute.AttributeContainer;
import net.minecraft.entity.attribute.EntityAttributeInstance;
import net.minecraft.entity.attribute.EntityAttributeModifier;
import net.minecraft.entity.boss.dragon.EnderDragonEntity;
import net.minecraft.entity.boss.dragon.EnderDragonPart;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.decoration.EndCrystalEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.decoration.LeashKnotEntity;
import net.minecraft.entity.decoration.painting.PaintingEntity;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.mob.EvokerFangsEntity;
import net.minecraft.entity.mob.GuardianEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.BeeEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.DragonFireballEntity;
import net.minecraft.entity.projectile.FireballEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.FishingBobberEntity;
import net.minecraft.entity.projectile.LlamaSpitEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ShulkerBulletEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.TridentEntity;
import net.minecraft.entity.projectile.WitherSkullEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.EnderPearlEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.vehicle.AbstractMinecartEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.entity.vehicle.ChestMinecartEntity;
import net.minecraft.entity.vehicle.CommandBlockMinecartEntity;
import net.minecraft.entity.vehicle.FurnaceMinecartEntity;
import net.minecraft.entity.vehicle.HopperMinecartEntity;
import net.minecraft.entity.vehicle.MinecartEntity;
import net.minecraft.entity.vehicle.SpawnerMinecartEntity;
import net.minecraft.entity.vehicle.TntMinecartEntity;
import net.minecraft.inventory.SimpleInventory;
import net.minecraft.item.FilledMapItem;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.map.MapState;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.ClientConnection;
import net.minecraft.network.NetworkThreadUtils;
import net.minecraft.network.Packet;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.network.listener.ClientPlayPacketListener;
import net.minecraft.network.packet.c2s.play.ClientStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.ConfirmScreenActionC2SPacket;
import net.minecraft.network.packet.c2s.play.CustomPayloadC2SPacket;
import net.minecraft.network.packet.c2s.play.KeepAliveC2SPacket;
import net.minecraft.network.packet.c2s.play.PlayerMoveC2SPacket;
import net.minecraft.network.packet.c2s.play.ResourcePackStatusC2SPacket;
import net.minecraft.network.packet.c2s.play.TeleportConfirmC2SPacket;
import net.minecraft.network.packet.c2s.play.VehicleMoveC2SPacket;
import net.minecraft.network.packet.s2c.play.AdvancementUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockBreakingProgressS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockEventS2CPacket;
import net.minecraft.network.packet.s2c.play.BlockUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.BossBarS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDataS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkDeltaUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkLoadDistanceS2CPacket;
import net.minecraft.network.packet.s2c.play.ChunkRenderDistanceCenterS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandSuggestionsS2CPacket;
import net.minecraft.network.packet.s2c.play.CommandTreeS2CPacket;
import net.minecraft.network.packet.s2c.play.ConfirmScreenActionS2CPacket;
import net.minecraft.network.packet.s2c.play.CooldownUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CraftFailedResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.CustomPayloadS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.DisconnectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttachS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAttributesS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityEquipmentUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPassengersSetS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySetHeadYawS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityTrackerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityVelocityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceOrbSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ExplosionS2CPacket;
import net.minecraft.network.packet.s2c.play.GameJoinS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.HeldItemChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.ItemPickupAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.KeepAliveS2CPacket;
import net.minecraft.network.packet.s2c.play.LightUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.LookAtS2CPacket;
import net.minecraft.network.packet.s2c.play.MapUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.MobSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.network.packet.s2c.play.PaintingSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.ParticleS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundFromEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundIdS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerActionResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListHeaderS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerListS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerPositionLookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnPositionS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardDisplayS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardObjectiveUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScoreboardPlayerUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerPropertyUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SelectAdvancementTabS2CPacket;
import net.minecraft.network.packet.s2c.play.SetCameraEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.network.packet.s2c.play.StatisticsS2CPacket;
import net.minecraft.network.packet.s2c.play.StopSoundS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.SynchronizeTagsS2CPacket;
import net.minecraft.network.packet.s2c.play.TagQueryResponseS2CPacket;
import net.minecraft.network.packet.s2c.play.TeamS2CPacket;
import net.minecraft.network.packet.s2c.play.TitleS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.network.packet.s2c.play.UnlockRecipesS2CPacket;
import net.minecraft.network.packet.s2c.play.VehicleMoveS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldBorderS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldTimeUpdateS2CPacket;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeManager;
import net.minecraft.resource.ResourcePackSource;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardObjective;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.MerchantScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.stat.Stat;
import net.minecraft.stat.StatHandler;
import net.minecraft.tag.RequiredTagListRegistry;
import net.minecraft.tag.TagManager;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockBox;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.PositionImpl;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.DynamicRegistryManager;
import net.minecraft.util.registry.Registry;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.Difficulty;
import net.minecraft.world.GameMode;
import net.minecraft.world.LightType;
import net.minecraft.world.World;
import net.minecraft.world.biome.source.BiomeArray;
import net.minecraft.world.chunk.ChunkNibbleArray;
import net.minecraft.world.chunk.WorldChunk;
import net.minecraft.world.chunk.light.LightingProvider;
import net.minecraft.world.dimension.DimensionType;
import net.minecraft.world.explosion.Explosion;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ClientPlayNetworkHandler
implements ClientPlayPacketListener {
    private static final Logger LOGGER = LogManager.getLogger();
    private static final Text field_26620 = new TranslatableText("disconnect.lost");
    private final ClientConnection connection;
    private final GameProfile profile;
    private final Screen loginScreen;
    private MinecraftClient client;
    private ClientWorld world;
    private ClientWorld.Properties worldProperties;
    private boolean positionLookSetup;
    private final Map<UUID, PlayerListEntry> playerListEntries = Maps.newHashMap();
    private final ClientAdvancementManager advancementHandler;
    private final ClientCommandSource commandSource;
    private TagManager tagManager = TagManager.EMPTY;
    private final DataQueryHandler dataQueryHandler = new DataQueryHandler(this);
    private int chunkLoadDistance = 3;
    private final Random random = new Random();
    private CommandDispatcher<CommandSource> commandDispatcher = new CommandDispatcher();
    private final RecipeManager recipeManager = new RecipeManager();
    private final UUID sessionId = UUID.randomUUID();
    private Set<RegistryKey<World>> worldKeys;
    private DynamicRegistryManager registryManager = DynamicRegistryManager.create();

    public ClientPlayNetworkHandler(MinecraftClient client, Screen screen, ClientConnection connection, GameProfile profile) {
        this.client = client;
        this.loginScreen = screen;
        this.connection = connection;
        this.profile = profile;
        this.advancementHandler = new ClientAdvancementManager(client);
        this.commandSource = new ClientCommandSource(this, client);
    }

    public ClientCommandSource getCommandSource() {
        return this.commandSource;
    }

    public void clearWorld() {
        this.world = null;
    }

    public RecipeManager getRecipeManager() {
        return this.recipeManager;
    }

    @Override
    public void onGameJoin(GameJoinS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.interactionManager = new ClientPlayerInteractionManager(this.client, this);
        if (!this.connection.isLocal()) {
            RequiredTagListRegistry.clearAllTags();
        }
        ArrayList arrayList = Lists.newArrayList(packet.getDimensionIds());
        Collections.shuffle(arrayList);
        this.worldKeys = Sets.newLinkedHashSet((Iterable)arrayList);
        this.registryManager = packet.getRegistryManager();
        RegistryKey<World> _snowman2 = packet.getDimensionId();
        DimensionType _snowman3 = packet.getDimensionType();
        this.chunkLoadDistance = packet.getViewDistance();
        boolean _snowman4 = packet.isDebugWorld();
        boolean _snowman5 = packet.isFlatWorld();
        this.worldProperties = _snowman = new ClientWorld.Properties(Difficulty.NORMAL, packet.isHardcore(), _snowman5);
        this.world = new ClientWorld(this, _snowman, _snowman2, _snowman3, this.chunkLoadDistance, this.client::getProfiler, this.client.worldRenderer, _snowman4, packet.getSha256Seed());
        this.client.joinWorld(this.world);
        if (this.client.player == null) {
            this.client.player = this.client.interactionManager.createPlayer(this.world, new StatHandler(), new ClientRecipeBook());
            this.client.player.yaw = -180.0f;
            if (this.client.getServer() != null) {
                this.client.getServer().setLocalPlayerUuid(this.client.player.getUuid());
            }
        }
        this.client.debugRenderer.reset();
        this.client.player.afterSpawn();
        int _snowman6 = packet.getEntityId();
        this.world.addPlayer(_snowman6, this.client.player);
        this.client.player.input = new KeyboardInput(this.client.options);
        this.client.interactionManager.copyAbilities(this.client.player);
        this.client.cameraEntity = this.client.player;
        this.client.openScreen(new DownloadingTerrainScreen());
        this.client.player.setEntityId(_snowman6);
        this.client.player.setReducedDebugInfo(packet.hasReducedDebugInfo());
        this.client.player.setShowsDeathScreen(packet.showsDeathScreen());
        this.client.interactionManager.setGameMode(packet.getGameMode());
        this.client.interactionManager.setPreviousGameMode(packet.getPreviousGameMode());
        this.client.options.onPlayerModelPartChange();
        this.connection.send(new CustomPayloadC2SPacket(CustomPayloadC2SPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName())));
        this.client.getGame().onStartGameSession();
    }

    @Override
    public void onEntitySpawn(EntitySpawnS2CPacket packet) {
        Entity _snowman4;
        Entity _snowman3;
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        double d = packet.getX();
        _snowman = packet.getY();
        _snowman = packet.getZ();
        EntityType<?> _snowman2 = packet.getEntityTypeId();
        if (_snowman2 == EntityType.CHEST_MINECART) {
            _snowman3 = new ChestMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.FURNACE_MINECART) {
            _snowman3 = new FurnaceMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.TNT_MINECART) {
            _snowman3 = new TntMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.SPAWNER_MINECART) {
            _snowman3 = new SpawnerMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.HOPPER_MINECART) {
            _snowman3 = new HopperMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.COMMAND_BLOCK_MINECART) {
            _snowman3 = new CommandBlockMinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.MINECART) {
            _snowman3 = new MinecartEntity(this.world, d, _snowman, _snowman);
        } else if (_snowman2 == EntityType.FISHING_BOBBER) {
            _snowman4 = this.world.getEntityById(packet.getEntityData());
            _snowman3 = _snowman4 instanceof PlayerEntity ? new FishingBobberEntity(this.world, (PlayerEntity)_snowman4, d, _snowman, _snowman) : null;
        } else if (_snowman2 == EntityType.ARROW) {
            _snowman3 = new ArrowEntity(this.world, d, _snowman, _snowman);
            _snowman4 = this.world.getEntityById(packet.getEntityData());
            if (_snowman4 != null) {
                ((PersistentProjectileEntity)_snowman3).setOwner(_snowman4);
            }
        } else if (_snowman2 == EntityType.SPECTRAL_ARROW) {
            _snowman3 = new SpectralArrowEntity(this.world, d, _snowman, _snowman);
            _snowman4 = this.world.getEntityById(packet.getEntityData());
            if (_snowman4 != null) {
                ((PersistentProjectileEntity)_snowman3).setOwner(_snowman4);
            }
        } else if (_snowman2 == EntityType.TRIDENT) {
            _snowman3 = new TridentEntity(this.world, d, _snowman, _snowman);
            _snowman4 = this.world.getEntityById(packet.getEntityData());
            if (_snowman4 != null) {
                ((PersistentProjectileEntity)_snowman3).setOwner(_snowman4);
            }
        } else {
            _snowman3 = _snowman2 == EntityType.SNOWBALL ? new SnowballEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.LLAMA_SPIT ? new LlamaSpitEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.ITEM_FRAME ? new ItemFrameEntity(this.world, new BlockPos(d, _snowman, _snowman), Direction.byId(packet.getEntityData())) : (_snowman2 == EntityType.LEASH_KNOT ? new LeashKnotEntity(this.world, new BlockPos(d, _snowman, _snowman)) : (_snowman2 == EntityType.ENDER_PEARL ? new EnderPearlEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.EYE_OF_ENDER ? new EyeOfEnderEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.FIREWORK_ROCKET ? new FireworkRocketEntity(this.world, d, _snowman, _snowman, ItemStack.EMPTY) : (_snowman2 == EntityType.FIREBALL ? new FireballEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.DRAGON_FIREBALL ? new DragonFireballEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.SMALL_FIREBALL ? new SmallFireballEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.WITHER_SKULL ? new WitherSkullEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.SHULKER_BULLET ? new ShulkerBulletEntity(this.world, d, _snowman, _snowman, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ()) : (_snowman2 == EntityType.EGG ? new EggEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.EVOKER_FANGS ? new EvokerFangsEntity(this.world, d, _snowman, _snowman, 0.0f, 0, null) : (_snowman2 == EntityType.POTION ? new PotionEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.EXPERIENCE_BOTTLE ? new ExperienceBottleEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.BOAT ? new BoatEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.TNT ? new TntEntity(this.world, d, _snowman, _snowman, null) : (_snowman2 == EntityType.ARMOR_STAND ? new ArmorStandEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.END_CRYSTAL ? new EndCrystalEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.ITEM ? new ItemEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.FALLING_BLOCK ? new FallingBlockEntity(this.world, d, _snowman, _snowman, Block.getStateFromRawId(packet.getEntityData())) : (_snowman2 == EntityType.AREA_EFFECT_CLOUD ? new AreaEffectCloudEntity(this.world, d, _snowman, _snowman) : (_snowman2 == EntityType.LIGHTNING_BOLT ? new LightningEntity((EntityType<? extends LightningEntity>)EntityType.LIGHTNING_BOLT, (World)this.world) : null)))))))))))))))))))))));
        }
        if (_snowman3 != null) {
            int _snowman5 = packet.getId();
            _snowman3.updateTrackedPosition(d, _snowman, _snowman);
            _snowman3.refreshPositionAfterTeleport(d, _snowman, _snowman);
            _snowman3.pitch = (float)(packet.getPitch() * 360) / 256.0f;
            _snowman3.yaw = (float)(packet.getYaw() * 360) / 256.0f;
            _snowman3.setEntityId(_snowman5);
            _snowman3.setUuid(packet.getUuid());
            this.world.addEntity(_snowman5, _snowman3);
            if (_snowman3 instanceof AbstractMinecartEntity) {
                this.client.getSoundManager().play(new MovingMinecartSoundInstance((AbstractMinecartEntity)_snowman3));
            }
        }
    }

    @Override
    public void onExperienceOrbSpawn(ExperienceOrbSpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        double d = packet.getX();
        _snowman = packet.getY();
        _snowman = packet.getZ();
        ExperienceOrbEntity _snowman2 = new ExperienceOrbEntity(this.world, d, _snowman, _snowman, packet.getExperience());
        _snowman2.updateTrackedPosition(d, _snowman, _snowman);
        _snowman2.yaw = 0.0f;
        _snowman2.pitch = 0.0f;
        _snowman2.setEntityId(packet.getId());
        this.world.addEntity(packet.getId(), _snowman2);
    }

    @Override
    public void onPaintingSpawn(PaintingSpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        PaintingEntity paintingEntity = new PaintingEntity(this.world, packet.getPos(), packet.getFacing(), packet.getMotive());
        paintingEntity.setEntityId(packet.getId());
        paintingEntity.setUuid(packet.getPaintingUuid());
        this.world.addEntity(packet.getId(), paintingEntity);
    }

    @Override
    public void onVelocityUpdate(EntityVelocityUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity == null) {
            return;
        }
        entity.setVelocityClient((double)packet.getVelocityX() / 8000.0, (double)packet.getVelocityY() / 8000.0, (double)packet.getVelocityZ() / 8000.0);
    }

    @Override
    public void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.id());
        if (entity != null && packet.getTrackedValues() != null) {
            entity.getDataTracker().writeUpdatedEntries(packet.getTrackedValues());
        }
    }

    @Override
    public void onPlayerSpawn(PlayerSpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        double d = packet.getX();
        _snowman = packet.getY();
        _snowman = packet.getZ();
        float _snowman2 = (float)(packet.getYaw() * 360) / 256.0f;
        float _snowman3 = (float)(packet.getPitch() * 360) / 256.0f;
        int _snowman4 = packet.getId();
        OtherClientPlayerEntity _snowman5 = new OtherClientPlayerEntity(this.client.world, this.getPlayerListEntry(packet.getPlayerUuid()).getProfile());
        _snowman5.setEntityId(_snowman4);
        _snowman5.resetPosition(d, _snowman, _snowman);
        _snowman5.updateTrackedPosition(d, _snowman, _snowman);
        _snowman5.updatePositionAndAngles(d, _snowman, _snowman, _snowman2, _snowman3);
        this.world.addPlayer(_snowman4, _snowman5);
    }

    @Override
    public void onEntityPosition(EntityPositionS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity == null) {
            return;
        }
        double _snowman2 = packet.getX();
        double _snowman3 = packet.getY();
        double _snowman4 = packet.getZ();
        entity.updateTrackedPosition(_snowman2, _snowman3, _snowman4);
        if (!entity.isLogicalSideForUpdatingMovement()) {
            float f = (float)(packet.getYaw() * 360) / 256.0f;
            _snowman = (float)(packet.getPitch() * 360) / 256.0f;
            entity.updateTrackedPositionAndAngles(_snowman2, _snowman3, _snowman4, f, _snowman, 3, true);
            entity.setOnGround(packet.isOnGround());
        }
    }

    @Override
    public void onHeldItemChange(HeldItemChangeS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (PlayerInventory.isValidHotbarIndex(packet.getSlot())) {
            this.client.player.inventory.selectedSlot = packet.getSlot();
        }
    }

    @Override
    public void onEntityUpdate(EntityS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity == null) {
            return;
        }
        if (!entity.isLogicalSideForUpdatingMovement()) {
            if (packet.isPositionChanged()) {
                Vec3d vec3d = packet.calculateDeltaPosition(entity.getTrackedPosition());
                entity.updateTrackedPosition(vec3d);
                float _snowman2 = packet.hasRotation() ? (float)(packet.getYaw() * 360) / 256.0f : entity.yaw;
                float _snowman3 = packet.hasRotation() ? (float)(packet.getPitch() * 360) / 256.0f : entity.pitch;
                entity.updateTrackedPositionAndAngles(vec3d.getX(), vec3d.getY(), vec3d.getZ(), _snowman2, _snowman3, 3, false);
            } else if (packet.hasRotation()) {
                float _snowman4 = (float)(packet.getYaw() * 360) / 256.0f;
                float _snowman5 = (float)(packet.getPitch() * 360) / 256.0f;
                entity.updateTrackedPositionAndAngles(entity.getX(), entity.getY(), entity.getZ(), _snowman4, _snowman5, 3, false);
            }
            entity.setOnGround(packet.isOnGround());
        }
    }

    @Override
    public void onEntitySetHeadYaw(EntitySetHeadYawS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity == null) {
            return;
        }
        float _snowman2 = (float)(packet.getHeadYaw() * 360) / 256.0f;
        entity.updateTrackedHeadRotation(_snowman2, 3);
    }

    @Override
    public void onEntitiesDestroy(EntitiesDestroyS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        for (int i = 0; i < packet.getEntityIds().length; ++i) {
            _snowman = packet.getEntityIds()[i];
            this.world.removeEntity(_snowman);
        }
    }

    @Override
    public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet) {
        double d;
        double d2;
        double d3;
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        Vec3d _snowman2 = clientPlayerEntity.getVelocity();
        boolean _snowman3 = packet.getFlags().contains((Object)PlayerPositionLookS2CPacket.Flag.X);
        boolean _snowman4 = packet.getFlags().contains((Object)PlayerPositionLookS2CPacket.Flag.Y);
        boolean _snowman5 = packet.getFlags().contains((Object)PlayerPositionLookS2CPacket.Flag.Z);
        if (_snowman3) {
            d3 = _snowman2.getX();
            _snowman = clientPlayerEntity.getX() + packet.getX();
            clientPlayerEntity.lastRenderX += packet.getX();
        } else {
            d3 = 0.0;
            clientPlayerEntity.lastRenderX = _snowman = packet.getX();
        }
        if (_snowman4) {
            d2 = _snowman2.getY();
            _snowman = clientPlayerEntity.getY() + packet.getY();
            clientPlayerEntity.lastRenderY += packet.getY();
        } else {
            d2 = 0.0;
            clientPlayerEntity.lastRenderY = _snowman = packet.getY();
        }
        if (_snowman5) {
            d = _snowman2.getZ();
            _snowman = clientPlayerEntity.getZ() + packet.getZ();
            clientPlayerEntity.lastRenderZ += packet.getZ();
        } else {
            d = 0.0;
            clientPlayerEntity.lastRenderZ = _snowman = packet.getZ();
        }
        if (clientPlayerEntity.age > 0 && clientPlayerEntity.getVehicle() != null) {
            ((PlayerEntity)clientPlayerEntity).method_29239();
        }
        clientPlayerEntity.setPos(_snowman, _snowman, _snowman);
        clientPlayerEntity.prevX = _snowman;
        clientPlayerEntity.prevY = _snowman;
        clientPlayerEntity.prevZ = _snowman;
        clientPlayerEntity.setVelocity(d3, d2, d);
        float _snowman6 = packet.getYaw();
        float _snowman7 = packet.getPitch();
        if (packet.getFlags().contains((Object)PlayerPositionLookS2CPacket.Flag.X_ROT)) {
            _snowman7 += clientPlayerEntity.pitch;
        }
        if (packet.getFlags().contains((Object)PlayerPositionLookS2CPacket.Flag.Y_ROT)) {
            _snowman6 += clientPlayerEntity.yaw;
        }
        clientPlayerEntity.updatePositionAndAngles(_snowman, _snowman, _snowman, _snowman6, _snowman7);
        this.connection.send(new TeleportConfirmC2SPacket(packet.getTeleportId()));
        this.connection.send(new PlayerMoveC2SPacket.Both(clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), clientPlayerEntity.yaw, clientPlayerEntity.pitch, false));
        if (!this.positionLookSetup) {
            this.positionLookSetup = true;
            this.client.openScreen(null);
        }
    }

    @Override
    public void onChunkDeltaUpdate(ChunkDeltaUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        int n = 0x13 | (packet.method_31179() ? 128 : 0);
        packet.visitUpdates((blockPos, blockState) -> this.world.setBlockState((BlockPos)blockPos, (BlockState)blockState, n));
    }

    @Override
    public void onChunkData(ChunkDataS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        int n = packet.getX();
        _snowman = packet.getZ();
        BiomeArray _snowman2 = packet.getBiomeArray() == null ? null : new BiomeArray(this.registryManager.get(Registry.BIOME_KEY), packet.getBiomeArray());
        WorldChunk _snowman3 = this.world.getChunkManager().loadChunkFromPacket(n, _snowman, _snowman2, packet.getReadBuffer(), packet.getHeightmaps(), packet.getVerticalStripBitmask(), packet.isFullChunk());
        if (_snowman3 != null && packet.isFullChunk()) {
            this.world.addEntitiesToChunk(_snowman3);
        }
        for (_snowman = 0; _snowman < 16; ++_snowman) {
            this.world.scheduleBlockRenders(n, _snowman, _snowman);
        }
        for (CompoundTag compoundTag : packet.getBlockEntityTagList()) {
            BlockPos blockPos = new BlockPos(compoundTag.getInt("x"), compoundTag.getInt("y"), compoundTag.getInt("z"));
            BlockEntity _snowman4 = this.world.getBlockEntity(blockPos);
            if (_snowman4 == null) continue;
            _snowman4.fromTag(this.world.getBlockState(blockPos), compoundTag);
        }
    }

    @Override
    public void onUnloadChunk(UnloadChunkS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        int n = packet.getX();
        _snowman = packet.getZ();
        ClientChunkManager _snowman2 = this.world.getChunkManager();
        _snowman2.unload(n, _snowman);
        LightingProvider _snowman3 = _snowman2.getLightingProvider();
        for (_snowman = 0; _snowman < 16; ++_snowman) {
            this.world.scheduleBlockRenders(n, _snowman, _snowman);
            _snowman3.setSectionStatus(ChunkSectionPos.from(n, _snowman, _snowman), true);
        }
        _snowman3.setColumnEnabled(new ChunkPos(n, _snowman), false);
    }

    @Override
    public void onBlockUpdate(BlockUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.world.setBlockStateWithoutNeighborUpdates(packet.getPos(), packet.getState());
    }

    @Override
    public void onDisconnect(DisconnectS2CPacket packet) {
        this.connection.disconnect(packet.getReason());
    }

    @Override
    public void onDisconnected(Text reason) {
        this.client.disconnect();
        if (this.loginScreen != null) {
            if (this.loginScreen instanceof RealmsScreen) {
                this.client.openScreen(new DisconnectedRealmsScreen(this.loginScreen, field_26620, reason));
            } else {
                this.client.openScreen(new DisconnectedScreen(this.loginScreen, field_26620, reason));
            }
        } else {
            this.client.openScreen(new DisconnectedScreen(new MultiplayerScreen(new TitleScreen()), field_26620, reason));
        }
    }

    public void sendPacket(Packet<?> packet) {
        this.connection.send(packet);
    }

    @Override
    public void onItemPickupAnimation(ItemPickupAnimationS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        LivingEntity _snowman2 = (LivingEntity)this.world.getEntityById(packet.getCollectorEntityId());
        if (_snowman2 == null) {
            _snowman2 = this.client.player;
        }
        if (entity != null) {
            if (entity instanceof ExperienceOrbEntity) {
                this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP, SoundCategory.PLAYERS, 0.1f, (this.random.nextFloat() - this.random.nextFloat()) * 0.35f + 0.9f, false);
            } else {
                this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ENTITY_ITEM_PICKUP, SoundCategory.PLAYERS, 0.2f, (this.random.nextFloat() - this.random.nextFloat()) * 1.4f + 2.0f, false);
            }
            this.client.particleManager.addParticle(new ItemPickupParticle(this.client.getEntityRenderDispatcher(), this.client.getBufferBuilders(), this.world, entity, _snowman2));
            if (entity instanceof ItemEntity) {
                ItemEntity itemEntity = (ItemEntity)entity;
                ItemStack _snowman3 = itemEntity.getStack();
                _snowman3.decrement(packet.getStackAmount());
                if (_snowman3.isEmpty()) {
                    this.world.removeEntity(packet.getEntityId());
                }
            } else {
                this.world.removeEntity(packet.getEntityId());
            }
        }
    }

    @Override
    public void onGameMessage(GameMessageS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.inGameHud.addChatMessage(packet.getLocation(), packet.getMessage(), packet.getSenderUuid());
    }

    @Override
    public void onEntityAnimation(EntityAnimationS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity == null) {
            return;
        }
        if (packet.getAnimationId() == 0) {
            LivingEntity livingEntity = (LivingEntity)entity;
            livingEntity.swingHand(Hand.MAIN_HAND);
        } else if (packet.getAnimationId() == 3) {
            LivingEntity _snowman2 = (LivingEntity)entity;
            _snowman2.swingHand(Hand.OFF_HAND);
        } else if (packet.getAnimationId() == 1) {
            entity.animateDamage();
        } else if (packet.getAnimationId() == 2) {
            PlayerEntity _snowman3 = (PlayerEntity)entity;
            _snowman3.wakeUp(false, false);
        } else if (packet.getAnimationId() == 4) {
            this.client.particleManager.addEmitter(entity, ParticleTypes.CRIT);
        } else if (packet.getAnimationId() == 5) {
            this.client.particleManager.addEmitter(entity, ParticleTypes.ENCHANTED_HIT);
        }
    }

    @Override
    public void onMobSpawn(MobSpawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        double d = packet.getX();
        _snowman = packet.getY();
        _snowman = packet.getZ();
        float _snowman2 = (float)(packet.getYaw() * 360) / 256.0f;
        float _snowman3 = (float)(packet.getPitch() * 360) / 256.0f;
        LivingEntity _snowman4 = (LivingEntity)EntityType.createInstanceFromId(packet.getEntityTypeId(), this.client.world);
        if (_snowman4 != null) {
            _snowman4.updateTrackedPosition(d, _snowman, _snowman);
            _snowman4.bodyYaw = (float)(packet.getHeadYaw() * 360) / 256.0f;
            _snowman4.headYaw = (float)(packet.getHeadYaw() * 360) / 256.0f;
            if (_snowman4 instanceof EnderDragonEntity) {
                EnderDragonPart[] enderDragonPartArray = ((EnderDragonEntity)_snowman4).getBodyParts();
                for (int i = 0; i < enderDragonPartArray.length; ++i) {
                    enderDragonPartArray[i].setEntityId(i + packet.getId());
                }
            }
            _snowman4.setEntityId(packet.getId());
            _snowman4.setUuid(packet.getUuid());
            _snowman4.updatePositionAndAngles(d, _snowman, _snowman, _snowman2, _snowman3);
            _snowman4.setVelocity((float)packet.getVelocityX() / 8000.0f, (float)packet.getVelocityY() / 8000.0f, (float)packet.getVelocityZ() / 8000.0f);
            this.world.addEntity(packet.getId(), _snowman4);
            if (_snowman4 instanceof BeeEntity) {
                boolean bl = ((BeeEntity)_snowman4).hasAngerTime();
                AbstractBeeSoundInstance _snowman5 = bl ? new AggressiveBeeSoundInstance((BeeEntity)_snowman4) : new PassiveBeeSoundInstance((BeeEntity)_snowman4);
                this.client.getSoundManager().playNextTick(_snowman5);
            }
        } else {
            LOGGER.warn("Skipping Entity with id {}", (Object)packet.getEntityTypeId());
        }
    }

    @Override
    public void onWorldTimeUpdate(WorldTimeUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.world.method_29089(packet.getTime());
        this.client.world.setTimeOfDay(packet.getTimeOfDay());
    }

    @Override
    public void onPlayerSpawnPosition(PlayerSpawnPositionS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.world.setSpawnPos(packet.getPos(), packet.getAngle());
    }

    @Override
    public void onEntityPassengersSet(EntityPassengersSetS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity == null) {
            LOGGER.warn("Received passengers for unknown entity");
            return;
        }
        boolean _snowman2 = entity.hasPassengerDeep(this.client.player);
        entity.removeAllPassengers();
        for (int n : packet.getPassengerIds()) {
            Entity entity2 = this.world.getEntityById(n);
            if (entity2 == null) continue;
            entity2.startRiding(entity, true);
            if (entity2 != this.client.player || _snowman2) continue;
            this.client.inGameHud.setOverlayMessage(new TranslatableText("mount.onboard", this.client.options.keySneak.getBoundKeyLocalizedText()), false);
        }
    }

    @Override
    public void onEntityAttach(EntityAttachS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getAttachedEntityId());
        if (entity instanceof MobEntity) {
            ((MobEntity)entity).setHoldingEntityId(packet.getHoldingEntityId());
        }
    }

    private static ItemStack getActiveTotemOfUndying(PlayerEntity player) {
        for (Hand hand : Hand.values()) {
            ItemStack itemStack = player.getStackInHand(hand);
            if (itemStack.getItem() != Items.TOTEM_OF_UNDYING) continue;
            return itemStack;
        }
        return new ItemStack(Items.TOTEM_OF_UNDYING);
    }

    @Override
    public void onEntityStatus(EntityStatusS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            if (packet.getStatus() == 21) {
                this.client.getSoundManager().play(new GuardianAttackSoundInstance((GuardianEntity)entity));
            } else if (packet.getStatus() == 35) {
                int n = 40;
                this.client.particleManager.addEmitter(entity, ParticleTypes.TOTEM_OF_UNDYING, 30);
                this.world.playSound(entity.getX(), entity.getY(), entity.getZ(), SoundEvents.ITEM_TOTEM_USE, entity.getSoundCategory(), 1.0f, 1.0f, false);
                if (entity == this.client.player) {
                    this.client.gameRenderer.showFloatingItem(ClientPlayNetworkHandler.getActiveTotemOfUndying(this.client.player));
                }
            } else {
                entity.handleStatus(packet.getStatus());
            }
        }
    }

    @Override
    public void onHealthUpdate(HealthUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.player.updateHealth(packet.getHealth());
        this.client.player.getHungerManager().setFoodLevel(packet.getFood());
        this.client.player.getHungerManager().setSaturationLevelClient(packet.getSaturation());
    }

    @Override
    public void onExperienceBarUpdate(ExperienceBarUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.player.setExperience(packet.getBarProgress(), packet.getExperienceLevel(), packet.getExperience());
    }

    @Override
    public void onPlayerRespawn(PlayerRespawnS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        RegistryKey<World> registryKey = packet.getDimension();
        DimensionType _snowman2 = packet.method_29445();
        ClientPlayerEntity _snowman3 = this.client.player;
        int _snowman4 = _snowman3.getEntityId();
        this.positionLookSetup = false;
        if (registryKey != _snowman3.world.getRegistryKey()) {
            Object object = this.world.getScoreboard();
            boolean _snowman5 = packet.isDebugWorld();
            boolean _snowman6 = packet.isFlatWorld();
            this.worldProperties = _snowman = new ClientWorld.Properties(this.worldProperties.getDifficulty(), this.worldProperties.isHardcore(), _snowman6);
            this.world = new ClientWorld(this, _snowman, registryKey, _snowman2, this.chunkLoadDistance, this.client::getProfiler, this.client.worldRenderer, _snowman5, packet.getSha256Seed());
            this.world.setScoreboard((Scoreboard)object);
            this.client.joinWorld(this.world);
            this.client.openScreen(new DownloadingTerrainScreen());
        }
        this.world.finishRemovingEntities();
        object = _snowman3.getServerBrand();
        this.client.cameraEntity = null;
        ClientPlayerEntity clientPlayerEntity = this.client.interactionManager.createPlayer(this.world, _snowman3.getStatHandler(), _snowman3.getRecipeBook(), _snowman3.isSneaking(), _snowman3.isSprinting());
        clientPlayerEntity.setEntityId(_snowman4);
        this.client.player = clientPlayerEntity;
        if (registryKey != _snowman3.world.getRegistryKey()) {
            this.client.getMusicTracker().stop();
        }
        this.client.cameraEntity = clientPlayerEntity;
        clientPlayerEntity.getDataTracker().writeUpdatedEntries(_snowman3.getDataTracker().getAllEntries());
        if (packet.shouldKeepPlayerAttributes()) {
            clientPlayerEntity.getAttributes().setFrom(_snowman3.getAttributes());
        }
        clientPlayerEntity.afterSpawn();
        clientPlayerEntity.setServerBrand((String)object);
        this.world.addPlayer(_snowman4, clientPlayerEntity);
        clientPlayerEntity.yaw = -180.0f;
        clientPlayerEntity.input = new KeyboardInput(this.client.options);
        this.client.interactionManager.copyAbilities(clientPlayerEntity);
        clientPlayerEntity.setReducedDebugInfo(_snowman3.getReducedDebugInfo());
        clientPlayerEntity.setShowsDeathScreen(_snowman3.showsDeathScreen());
        if (this.client.currentScreen instanceof DeathScreen) {
            this.client.openScreen(null);
        }
        this.client.interactionManager.setGameMode(packet.getGameMode());
        this.client.interactionManager.setPreviousGameMode(packet.getPreviousGameMode());
    }

    @Override
    public void onExplosion(ExplosionS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Explosion explosion = new Explosion(this.client.world, null, packet.getX(), packet.getY(), packet.getZ(), packet.getRadius(), packet.getAffectedBlocks());
        explosion.affectWorld(true);
        this.client.player.setVelocity(this.client.player.getVelocity().add(packet.getPlayerVelocityX(), packet.getPlayerVelocityY(), packet.getPlayerVelocityZ()));
    }

    @Override
    public void onOpenHorseScreen(OpenHorseScreenS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getHorseId());
        if (entity instanceof HorseBaseEntity) {
            ClientPlayerEntity clientPlayerEntity = this.client.player;
            HorseBaseEntity _snowman2 = (HorseBaseEntity)entity;
            SimpleInventory _snowman3 = new SimpleInventory(packet.getSlotCount());
            HorseScreenHandler _snowman4 = new HorseScreenHandler(packet.getSyncId(), clientPlayerEntity.inventory, _snowman3, _snowman2);
            clientPlayerEntity.currentScreenHandler = _snowman4;
            this.client.openScreen(new HorseScreen(_snowman4, clientPlayerEntity.inventory, _snowman2));
        }
    }

    @Override
    public void onOpenScreen(OpenScreenS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        HandledScreens.open(packet.getScreenHandlerType(), this.client, packet.getSyncId(), packet.getName());
    }

    @Override
    public void onScreenHandlerSlotUpdate(ScreenHandlerSlotUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        ItemStack _snowman2 = packet.getItemStack();
        int _snowman3 = packet.getSlot();
        this.client.getTutorialManager().onSlotUpdate(_snowman2);
        if (packet.getSyncId() == -1) {
            if (!(this.client.currentScreen instanceof CreativeInventoryScreen)) {
                clientPlayerEntity.inventory.setCursorStack(_snowman2);
            }
        } else if (packet.getSyncId() == -2) {
            clientPlayerEntity.inventory.setStack(_snowman3, _snowman2);
        } else {
            Object object;
            boolean bl = false;
            if (this.client.currentScreen instanceof CreativeInventoryScreen) {
                object = (CreativeInventoryScreen)this.client.currentScreen;
                boolean bl2 = bl = ((CreativeInventoryScreen)object).getSelectedTab() != ItemGroup.INVENTORY.getIndex();
            }
            if (packet.getSyncId() == 0 && packet.getSlot() >= 36 && _snowman3 < 45) {
                if (!_snowman2.isEmpty() && (((ItemStack)(object = clientPlayerEntity.playerScreenHandler.getSlot(_snowman3).getStack())).isEmpty() || ((ItemStack)object).getCount() < _snowman2.getCount())) {
                    _snowman2.setCooldown(5);
                }
                clientPlayerEntity.playerScreenHandler.setStackInSlot(_snowman3, _snowman2);
            } else if (!(packet.getSyncId() != clientPlayerEntity.currentScreenHandler.syncId || packet.getSyncId() == 0 && bl)) {
                clientPlayerEntity.currentScreenHandler.setStackInSlot(_snowman3, _snowman2);
            }
        }
    }

    @Override
    public void onConfirmScreenAction(ConfirmScreenActionS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ScreenHandler screenHandler = null;
        ClientPlayerEntity _snowman2 = this.client.player;
        if (packet.getSyncId() == 0) {
            screenHandler = _snowman2.playerScreenHandler;
        } else if (packet.getSyncId() == _snowman2.currentScreenHandler.syncId) {
            screenHandler = _snowman2.currentScreenHandler;
        }
        if (screenHandler != null && !packet.wasAccepted()) {
            this.sendPacket(new ConfirmScreenActionC2SPacket(packet.getSyncId(), packet.getActionId(), true));
        }
    }

    @Override
    public void onInventory(InventoryS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (packet.getSyncId() == 0) {
            clientPlayerEntity.playerScreenHandler.updateSlotStacks(packet.getContents());
        } else if (packet.getSyncId() == clientPlayerEntity.currentScreenHandler.syncId) {
            clientPlayerEntity.currentScreenHandler.updateSlotStacks(packet.getContents());
        }
    }

    @Override
    public void onSignEditorOpen(SignEditorOpenS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        BlockEntity blockEntity = this.world.getBlockEntity(packet.getPos());
        if (!(blockEntity instanceof SignBlockEntity)) {
            blockEntity = new SignBlockEntity();
            blockEntity.setLocation(this.world, packet.getPos());
        }
        this.client.player.openEditSignScreen((SignBlockEntity)blockEntity);
    }

    @Override
    public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        BlockPos blockPos = packet.getPos();
        BlockEntity _snowman2 = this.client.world.getBlockEntity(blockPos);
        int _snowman3 = packet.getBlockEntityType();
        boolean bl = _snowman = _snowman3 == 2 && _snowman2 instanceof CommandBlockBlockEntity;
        if (_snowman3 == 1 && _snowman2 instanceof MobSpawnerBlockEntity || _snowman || _snowman3 == 3 && _snowman2 instanceof BeaconBlockEntity || _snowman3 == 4 && _snowman2 instanceof SkullBlockEntity || _snowman3 == 6 && _snowman2 instanceof BannerBlockEntity || _snowman3 == 7 && _snowman2 instanceof StructureBlockBlockEntity || _snowman3 == 8 && _snowman2 instanceof EndGatewayBlockEntity || _snowman3 == 9 && _snowman2 instanceof SignBlockEntity || _snowman3 == 11 && _snowman2 instanceof BedBlockEntity || _snowman3 == 5 && _snowman2 instanceof ConduitBlockEntity || _snowman3 == 12 && _snowman2 instanceof JigsawBlockEntity || _snowman3 == 13 && _snowman2 instanceof CampfireBlockEntity || _snowman3 == 14 && _snowman2 instanceof BeehiveBlockEntity) {
            _snowman2.fromTag(this.client.world.getBlockState(blockPos), packet.getCompoundTag());
        }
        if (_snowman && this.client.currentScreen instanceof CommandBlockScreen) {
            ((CommandBlockScreen)this.client.currentScreen).updateCommandBlock();
        }
    }

    @Override
    public void onScreenHandlerPropertyUpdate(ScreenHandlerPropertyUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        if (clientPlayerEntity.currentScreenHandler != null && clientPlayerEntity.currentScreenHandler.syncId == packet.getSyncId()) {
            clientPlayerEntity.currentScreenHandler.setProperty(packet.getPropertyId(), packet.getValue());
        }
    }

    @Override
    public void onEquipmentUpdate(EntityEquipmentUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getId());
        if (entity != null) {
            packet.getEquipmentList().forEach(pair -> entity.equipStack((EquipmentSlot)((Object)((Object)pair.getFirst())), (ItemStack)pair.getSecond()));
        }
    }

    @Override
    public void onCloseScreen(CloseScreenS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.player.closeScreen();
    }

    @Override
    public void onBlockEvent(BlockEventS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.world.addSyncedBlockEvent(packet.getPos(), packet.getBlock(), packet.getType(), packet.getData());
    }

    @Override
    public void onBlockDestroyProgress(BlockBreakingProgressS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.world.setBlockBreakingInfo(packet.getEntityId(), packet.getPos(), packet.getProgress());
    }

    @Override
    public void onGameStateChange(GameStateChangeS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        GameStateChangeS2CPacket.Reason _snowman2 = packet.getReason();
        float _snowman3 = packet.getValue();
        int _snowman4 = MathHelper.floor(_snowman3 + 0.5f);
        if (_snowman2 == GameStateChangeS2CPacket.NO_RESPAWN_BLOCK) {
            ((PlayerEntity)clientPlayerEntity).sendMessage(new TranslatableText("block.minecraft.spawn.not_valid"), false);
        } else if (_snowman2 == GameStateChangeS2CPacket.RAIN_STARTED) {
            this.world.getLevelProperties().setRaining(true);
            this.world.setRainGradient(0.0f);
        } else if (_snowman2 == GameStateChangeS2CPacket.RAIN_STOPPED) {
            this.world.getLevelProperties().setRaining(false);
            this.world.setRainGradient(1.0f);
        } else if (_snowman2 == GameStateChangeS2CPacket.GAME_MODE_CHANGED) {
            this.client.interactionManager.setGameMode(GameMode.byId(_snowman4));
        } else if (_snowman2 == GameStateChangeS2CPacket.GAME_WON) {
            if (_snowman4 == 0) {
                this.client.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
                this.client.openScreen(new DownloadingTerrainScreen());
            } else if (_snowman4 == 1) {
                this.client.openScreen(new CreditsScreen(true, () -> this.client.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN))));
            }
        } else if (_snowman2 == GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN) {
            GameOptions gameOptions = this.client.options;
            if (_snowman3 == 0.0f) {
                this.client.openScreen(new DemoScreen());
            } else if (_snowman3 == 101.0f) {
                this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.help.movement", gameOptions.keyForward.getBoundKeyLocalizedText(), gameOptions.keyLeft.getBoundKeyLocalizedText(), gameOptions.keyBack.getBoundKeyLocalizedText(), gameOptions.keyRight.getBoundKeyLocalizedText()));
            } else if (_snowman3 == 102.0f) {
                this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.help.jump", gameOptions.keyJump.getBoundKeyLocalizedText()));
            } else if (_snowman3 == 103.0f) {
                this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.help.inventory", gameOptions.keyInventory.getBoundKeyLocalizedText()));
            } else if (_snowman3 == 104.0f) {
                this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.day.6", gameOptions.keyScreenshot.getBoundKeyLocalizedText()));
            }
        } else if (_snowman2 == GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER) {
            this.world.playSound(clientPlayerEntity, clientPlayerEntity.getX(), clientPlayerEntity.getEyeY(), clientPlayerEntity.getZ(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18f, 0.45f);
        } else if (_snowman2 == GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED) {
            this.world.setRainGradient(_snowman3);
        } else if (_snowman2 == GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED) {
            this.world.setThunderGradient(_snowman3);
        } else if (_snowman2 == GameStateChangeS2CPacket.PUFFERFISH_STING) {
            this.world.playSound(clientPlayerEntity, clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), SoundEvents.ENTITY_PUFFER_FISH_STING, SoundCategory.NEUTRAL, 1.0f, 1.0f);
        } else if (_snowman2 == GameStateChangeS2CPacket.ELDER_GUARDIAN_EFFECT) {
            this.world.addParticle(ParticleTypes.ELDER_GUARDIAN, clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), 0.0, 0.0, 0.0);
            if (_snowman4 == 1) {
                this.world.playSound(clientPlayerEntity, clientPlayerEntity.getX(), clientPlayerEntity.getY(), clientPlayerEntity.getZ(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1.0f, 1.0f);
            }
        } else if (_snowman2 == GameStateChangeS2CPacket.IMMEDIATE_RESPAWN) {
            this.client.player.setShowsDeathScreen(_snowman3 == 0.0f);
        }
    }

    @Override
    public void onMapUpdate(MapUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        MapRenderer mapRenderer = this.client.gameRenderer.getMapRenderer();
        String _snowman2 = FilledMapItem.getMapName(packet.getId());
        MapState _snowman3 = this.client.world.getMapState(_snowman2);
        if (_snowman3 == null) {
            _snowman3 = new MapState(_snowman2);
            if (mapRenderer.getTexture(_snowman2) != null && (_snowman = mapRenderer.getState(mapRenderer.getTexture(_snowman2))) != null) {
                _snowman3 = _snowman;
            }
            this.client.world.putMapState(_snowman3);
        }
        packet.apply(_snowman3);
        mapRenderer.updateTexture(_snowman3);
    }

    @Override
    public void onWorldEvent(WorldEventS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (packet.isGlobal()) {
            this.client.world.syncGlobalEvent(packet.getEventId(), packet.getPos(), packet.getData());
        } else {
            this.client.world.syncWorldEvent(packet.getEventId(), packet.getPos(), packet.getData());
        }
    }

    @Override
    public void onAdvancements(AdvancementUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.advancementHandler.onAdvancements(packet);
    }

    @Override
    public void onSelectAdvancementTab(SelectAdvancementTabS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Identifier identifier = packet.getTabId();
        if (identifier == null) {
            this.advancementHandler.selectTab(null, false);
        } else {
            Advancement advancement = this.advancementHandler.getManager().get(identifier);
            this.advancementHandler.selectTab(advancement, false);
        }
    }

    @Override
    public void onCommandTree(CommandTreeS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.commandDispatcher = new CommandDispatcher(packet.getCommandTree());
    }

    @Override
    public void onStopSound(StopSoundS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.getSoundManager().stopSounds(packet.getSoundId(), packet.getCategory());
    }

    @Override
    public void onCommandSuggestions(CommandSuggestionsS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.commandSource.onCommandSuggestions(packet.getCompletionId(), packet.getSuggestions());
    }

    @Override
    public void onSynchronizeRecipes(SynchronizeRecipesS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.recipeManager.setRecipes(packet.getRecipes());
        SearchableContainer<RecipeResultCollection> searchableContainer = this.client.getSearchableContainer(SearchManager.RECIPE_OUTPUT);
        searchableContainer.clear();
        ClientRecipeBook _snowman2 = this.client.player.getRecipeBook();
        _snowman2.reload(this.recipeManager.values());
        _snowman2.getOrderedResults().forEach(searchableContainer::add);
        searchableContainer.reload();
    }

    @Override
    public void onLookAt(LookAtS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Vec3d vec3d = packet.getTargetPosition(this.world);
        if (vec3d != null) {
            this.client.player.lookAt(packet.getSelfAnchor(), vec3d);
        }
    }

    @Override
    public void onTagQuery(TagQueryResponseS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (!this.dataQueryHandler.handleQueryResponse(packet.getTransactionId(), packet.getTag())) {
            LOGGER.debug("Got unhandled response to tag query {}", (Object)packet.getTransactionId());
        }
    }

    @Override
    public void onStatistics(StatisticsS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        for (Map.Entry<Stat<?>, Integer> entry : packet.getStatMap().entrySet()) {
            Stat<?> stat = entry.getKey();
            int _snowman2 = entry.getValue();
            this.client.player.getStatHandler().setStat(this.client.player, stat, _snowman2);
        }
        if (this.client.currentScreen instanceof StatsListener) {
            ((StatsListener)((Object)this.client.currentScreen)).onStatsReady();
        }
    }

    @Override
    public void onUnlockRecipes(UnlockRecipesS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientRecipeBook clientRecipeBook = this.client.player.getRecipeBook();
        clientRecipeBook.setOptions(packet.getOptions());
        UnlockRecipesS2CPacket.Action _snowman2 = packet.getAction();
        switch (_snowman2) {
            case REMOVE: {
                for (Identifier identifier : packet.getRecipeIdsToChange()) {
                    this.recipeManager.get(identifier).ifPresent(clientRecipeBook::remove);
                }
                break;
            }
            case INIT: {
                for (Identifier identifier : packet.getRecipeIdsToChange()) {
                    this.recipeManager.get(identifier).ifPresent(clientRecipeBook::add);
                }
                for (Identifier identifier : packet.getRecipeIdsToInit()) {
                    this.recipeManager.get(identifier).ifPresent(clientRecipeBook::display);
                }
                break;
            }
            case ADD: {
                for (Identifier identifier : packet.getRecipeIdsToChange()) {
                    this.recipeManager.get(identifier).ifPresent(recipe -> {
                        clientRecipeBook.add((Recipe<?>)recipe);
                        clientRecipeBook.display((Recipe<?>)recipe);
                        RecipeToast.show(this.client.getToastManager(), recipe);
                    });
                }
                break;
            }
        }
        clientRecipeBook.getOrderedResults().forEach(recipeResultCollection -> recipeResultCollection.initialize(clientRecipeBook));
        if (this.client.currentScreen instanceof RecipeBookProvider) {
            ((RecipeBookProvider)((Object)this.client.currentScreen)).refreshRecipeBook();
        }
    }

    @Override
    public void onEntityPotionEffect(EntityStatusEffectS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (!(entity instanceof LivingEntity)) {
            return;
        }
        StatusEffect _snowman2 = StatusEffect.byRawId(packet.getEffectId());
        if (_snowman2 == null) {
            return;
        }
        StatusEffectInstance _snowman3 = new StatusEffectInstance(_snowman2, packet.getDuration(), packet.getAmplifier(), packet.isAmbient(), packet.shouldShowParticles(), packet.shouldShowIcon());
        _snowman3.setPermanent(packet.isPermanent());
        ((LivingEntity)entity).applyStatusEffect(_snowman3);
    }

    @Override
    public void onSynchronizeTags(SynchronizeTagsS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        TagManager tagManager = packet.getTagManager();
        Multimap<Identifier, Identifier> _snowman2 = RequiredTagListRegistry.getMissingTags(tagManager);
        if (!_snowman2.isEmpty()) {
            LOGGER.warn("Incomplete server tags, disconnecting. Missing: {}", _snowman2);
            this.connection.disconnect(new TranslatableText("multiplayer.disconnect.missing_tags"));
            return;
        }
        this.tagManager = tagManager;
        if (!this.connection.isLocal()) {
            tagManager.apply();
        }
        this.client.getSearchableContainer(SearchManager.ITEM_TAG).reload();
    }

    @Override
    public void onCombatEvent(CombatEventS2CPacket packet) {
        Entity entity;
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (packet.type == CombatEventS2CPacket.Type.ENTITY_DIED && (entity = this.world.getEntityById(packet.entityId)) == this.client.player) {
            if (this.client.player.showsDeathScreen()) {
                this.client.openScreen(new DeathScreen(packet.deathMessage, this.world.getLevelProperties().isHardcore()));
            } else {
                this.client.player.requestRespawn();
            }
        }
    }

    @Override
    public void onDifficulty(DifficultyS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.worldProperties.setDifficulty(packet.getDifficulty());
        this.worldProperties.setDifficultyLocked(packet.isDifficultyLocked());
    }

    @Override
    public void onSetCameraEntity(SetCameraEntityS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity != null) {
            this.client.setCameraEntity(entity);
        }
    }

    @Override
    public void onWorldBorder(WorldBorderS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        packet.apply(this.world.getWorldBorder());
    }

    @Override
    public void onTitle(TitleS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        TitleS2CPacket.Action action = packet.getAction();
        Text _snowman2 = null;
        Text _snowman3 = null;
        Text _snowman4 = packet.getText() != null ? packet.getText() : LiteralText.EMPTY;
        switch (action) {
            case TITLE: {
                _snowman2 = _snowman4;
                break;
            }
            case SUBTITLE: {
                _snowman3 = _snowman4;
                break;
            }
            case ACTIONBAR: {
                this.client.inGameHud.setOverlayMessage(_snowman4, false);
                return;
            }
            case RESET: {
                this.client.inGameHud.setTitles(null, null, -1, -1, -1);
                this.client.inGameHud.setDefaultTitleFade();
                return;
            }
        }
        this.client.inGameHud.setTitles(_snowman2, _snowman3, packet.getFadeInTicks(), packet.getStayTicks(), packet.getFadeOutTicks());
    }

    @Override
    public void onPlayerListHeader(PlayerListHeaderS2CPacket packet) {
        this.client.inGameHud.getPlayerListWidget().setHeader(packet.getHeader().getString().isEmpty() ? null : packet.getHeader());
        this.client.inGameHud.getPlayerListWidget().setFooter(packet.getFooter().getString().isEmpty() ? null : packet.getFooter());
    }

    @Override
    public void onRemoveEntityEffect(RemoveEntityStatusEffectS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = packet.getEntity(this.world);
        if (entity instanceof LivingEntity) {
            ((LivingEntity)entity).removeStatusEffectInternal(packet.getEffectType());
        }
    }

    @Override
    public void onPlayerList(PlayerListS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        for (PlayerListS2CPacket.Entry entry : packet.getEntries()) {
            if (packet.getAction() == PlayerListS2CPacket.Action.REMOVE_PLAYER) {
                this.client.getSocialInteractionsManager().method_31341(entry.getProfile().getId());
                this.playerListEntries.remove(entry.getProfile().getId());
                continue;
            }
            PlayerListEntry playerListEntry = this.playerListEntries.get(entry.getProfile().getId());
            if (packet.getAction() == PlayerListS2CPacket.Action.ADD_PLAYER) {
                playerListEntry = new PlayerListEntry(entry);
                this.playerListEntries.put(playerListEntry.getProfile().getId(), playerListEntry);
                this.client.getSocialInteractionsManager().method_31337(playerListEntry);
            }
            if (playerListEntry == null) continue;
            switch (packet.getAction()) {
                case ADD_PLAYER: {
                    playerListEntry.setGameMode(entry.getGameMode());
                    playerListEntry.setLatency(entry.getLatency());
                    playerListEntry.setDisplayName(entry.getDisplayName());
                    break;
                }
                case UPDATE_GAME_MODE: {
                    playerListEntry.setGameMode(entry.getGameMode());
                    break;
                }
                case UPDATE_LATENCY: {
                    playerListEntry.setLatency(entry.getLatency());
                    break;
                }
                case UPDATE_DISPLAY_NAME: {
                    playerListEntry.setDisplayName(entry.getDisplayName());
                }
            }
        }
    }

    @Override
    public void onKeepAlive(KeepAliveS2CPacket packet) {
        this.sendPacket(new KeepAliveC2SPacket(packet.getId()));
    }

    @Override
    public void onPlayerAbilities(PlayerAbilitiesS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ClientPlayerEntity clientPlayerEntity = this.client.player;
        clientPlayerEntity.abilities.flying = packet.isFlying();
        clientPlayerEntity.abilities.creativeMode = packet.isCreativeMode();
        clientPlayerEntity.abilities.invulnerable = packet.isInvulnerable();
        clientPlayerEntity.abilities.allowFlying = packet.allowFlying();
        clientPlayerEntity.abilities.setFlySpeed(packet.getFlySpeed());
        clientPlayerEntity.abilities.setWalkSpeed(packet.getWalkSpeed());
    }

    @Override
    public void onPlaySound(PlaySoundS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.world.playSound(this.client.player, packet.getX(), packet.getY(), packet.getZ(), packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch());
    }

    @Override
    public void onPlaySoundFromEntity(PlaySoundFromEntityS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity == null) {
            return;
        }
        this.client.world.playSoundFromEntity(this.client.player, entity, packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch());
    }

    @Override
    public void onPlaySoundId(PlaySoundIdS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.getSoundManager().play(new PositionedSoundInstance(packet.getSoundId(), packet.getCategory(), packet.getVolume(), packet.getPitch(), false, 0, SoundInstance.AttenuationType.LINEAR, packet.getX(), packet.getY(), packet.getZ(), false));
    }

    @Override
    public void onResourcePackSend(ResourcePackSendS2CPacket packet) {
        String string = packet.getURL();
        _snowman = packet.getSHA1();
        if (!this.validateResourcePackUrl(string)) {
            return;
        }
        if (string.startsWith("level://")) {
            try {
                unsupportedEncodingException = URLDecoder.decode(string.substring("level://".length()), StandardCharsets.UTF_8.toString());
                File file = new File(this.client.runDirectory, "saves");
                _snowman = new File(file, unsupportedEncodingException);
                if (_snowman.isFile()) {
                    this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
                    CompletableFuture<Void> completableFuture = this.client.getResourcePackDownloader().loadServerPack(_snowman, ResourcePackSource.PACK_SOURCE_WORLD);
                    this.feedbackAfterDownload(completableFuture);
                    return;
                }
            }
            catch (UnsupportedEncodingException unsupportedEncodingException) {
                // empty catch block
            }
            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD);
            return;
        }
        ServerInfo serverInfo = this.client.getCurrentServerEntry();
        if (serverInfo != null && serverInfo.getResourcePack() == ServerInfo.ResourcePackState.ENABLED) {
            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
            this.feedbackAfterDownload(this.client.getResourcePackDownloader().download(string, _snowman));
        } else if (serverInfo == null || serverInfo.getResourcePack() == ServerInfo.ResourcePackState.PROMPT) {
            this.client.execute(() -> this.client.openScreen(new ConfirmScreen(bl -> {
                this.client = MinecraftClient.getInstance();
                ServerInfo serverInfo = this.client.getCurrentServerEntry();
                if (bl) {
                    if (serverInfo != null) {
                        serverInfo.setResourcePackState(ServerInfo.ResourcePackState.ENABLED);
                    }
                    this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
                    this.feedbackAfterDownload(this.client.getResourcePackDownloader().download(string, _snowman));
                } else {
                    if (serverInfo != null) {
                        serverInfo.setResourcePackState(ServerInfo.ResourcePackState.DISABLED);
                    }
                    this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.DECLINED);
                }
                ServerList.updateServerListEntry(serverInfo);
                this.client.openScreen(null);
            }, new TranslatableText("multiplayer.texturePrompt.line1"), new TranslatableText("multiplayer.texturePrompt.line2"))));
        } else {
            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.DECLINED);
        }
    }

    private boolean validateResourcePackUrl(String url) {
        try {
            URI uRI = new URI(url);
            String _snowman2 = uRI.getScheme();
            boolean _snowman3 = "level".equals(_snowman2);
            if (!("http".equals(_snowman2) || "https".equals(_snowman2) || _snowman3)) {
                throw new URISyntaxException(url, "Wrong protocol");
            }
            if (_snowman3 && (url.contains("..") || !url.endsWith("/resources.zip"))) {
                throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
            }
        }
        catch (URISyntaxException uRISyntaxException) {
            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD);
            return false;
        }
        return true;
    }

    private void feedbackAfterDownload(CompletableFuture<?> downloadFuture) {
        ((CompletableFuture)downloadFuture.thenRun(() -> this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED))).exceptionally(throwable -> {
            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD);
            return null;
        });
    }

    private void sendResourcePackStatus(ResourcePackStatusC2SPacket.Status packStatus) {
        this.connection.send(new ResourcePackStatusC2SPacket(packStatus));
    }

    @Override
    public void onBossBar(BossBarS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.inGameHud.getBossBarHud().handlePacket(packet);
    }

    @Override
    public void onCooldownUpdate(CooldownUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (packet.getCooldown() == 0) {
            this.client.player.getItemCooldownManager().remove(packet.getItem());
        } else {
            this.client.player.getItemCooldownManager().set(packet.getItem(), packet.getCooldown());
        }
    }

    @Override
    public void onVehicleMove(VehicleMoveS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.client.player.getRootVehicle();
        if (entity != this.client.player && entity.isLogicalSideForUpdatingMovement()) {
            entity.updatePositionAndAngles(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
            this.connection.send(new VehicleMoveC2SPacket(entity));
        }
    }

    @Override
    public void onOpenWrittenBook(OpenWrittenBookS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ItemStack itemStack = this.client.player.getStackInHand(packet.getHand());
        if (itemStack.getItem() == Items.WRITTEN_BOOK) {
            this.client.openScreen(new BookScreen(new BookScreen.WrittenBookContents(itemStack)));
        }
    }

    /*
     * WARNING - Removed try catching itself - possible behaviour change.
     */
    @Override
    public void onCustomPayload(CustomPayloadS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Identifier identifier = packet.getChannel();
        PacketByteBuf _snowman2 = null;
        try {
            _snowman2 = packet.getData();
            if (CustomPayloadS2CPacket.BRAND.equals(identifier)) {
                this.client.player.setServerBrand(_snowman2.readString(Short.MAX_VALUE));
            } else if (CustomPayloadS2CPacket.DEBUG_PATH.equals(identifier)) {
                int n = _snowman2.readInt();
                float _snowman3 = _snowman2.readFloat();
                Path _snowman4 = Path.fromBuffer(_snowman2);
                this.client.debugRenderer.pathfindingDebugRenderer.addPath(n, _snowman4, _snowman3);
            } else if (CustomPayloadS2CPacket.DEBUG_NEIGHBORS_UPDATE.equals(identifier)) {
                long _snowman5 = _snowman2.readVarLong();
                BlockPos _snowman6 = _snowman2.readBlockPos();
                ((NeighborUpdateDebugRenderer)this.client.debugRenderer.neighborUpdateDebugRenderer).addNeighborUpdate(_snowman5, _snowman6);
            } else if (CustomPayloadS2CPacket.DEBUG_CAVES.equals(identifier)) {
                BlockPos _snowman7 = _snowman2.readBlockPos();
                _snowman = _snowman2.readInt();
                ArrayList _snowman8 = Lists.newArrayList();
                ArrayList _snowman9 = Lists.newArrayList();
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    _snowman8.add(_snowman2.readBlockPos());
                    _snowman9.add(Float.valueOf(_snowman2.readFloat()));
                }
                this.client.debugRenderer.caveDebugRenderer.method_3704(_snowman7, _snowman8, _snowman9);
            } else if (CustomPayloadS2CPacket.DEBUG_STRUCTURES.equals(identifier)) {
                DimensionType _snowman10 = this.registryManager.getDimensionTypes().get(_snowman2.readIdentifier());
                BlockBox _snowman11 = new BlockBox(_snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt());
                _snowman = _snowman2.readInt();
                ArrayList _snowman12 = Lists.newArrayList();
                ArrayList _snowman13 = Lists.newArrayList();
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    _snowman12.add(new BlockBox(_snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt(), _snowman2.readInt()));
                    _snowman13.add(_snowman2.readBoolean());
                }
                this.client.debugRenderer.structureDebugRenderer.method_3871(_snowman11, _snowman12, _snowman13, _snowman10);
            } else if (CustomPayloadS2CPacket.DEBUG_WORLDGEN_ATTEMPT.equals(identifier)) {
                ((WorldGenAttemptDebugRenderer)this.client.debugRenderer.worldGenAttemptDebugRenderer).method_3872(_snowman2.readBlockPos(), _snowman2.readFloat(), _snowman2.readFloat(), _snowman2.readFloat(), _snowman2.readFloat(), _snowman2.readFloat());
            } else if (CustomPayloadS2CPacket.DEBUG_VILLAGE_SECTIONS.equals(identifier)) {
                _snowman = _snowman2.readInt();
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    this.client.debugRenderer.villageSectionsDebugRenderer.addSection(_snowman2.readChunkSectionPos());
                }
                _snowman = _snowman2.readInt();
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    this.client.debugRenderer.villageSectionsDebugRenderer.removeSection(_snowman2.readChunkSectionPos());
                }
            } else if (CustomPayloadS2CPacket.DEBUG_POI_ADDED.equals(identifier)) {
                BlockPos _snowman14 = _snowman2.readBlockPos();
                String _snowman15 = _snowman2.readString();
                _snowman = _snowman2.readInt();
                VillageDebugRenderer.PointOfInterest _snowman16 = new VillageDebugRenderer.PointOfInterest(_snowman14, _snowman15, _snowman);
                this.client.debugRenderer.villageDebugRenderer.addPointOfInterest(_snowman16);
            } else if (CustomPayloadS2CPacket.DEBUG_POI_REMOVED.equals(identifier)) {
                BlockPos _snowman17 = _snowman2.readBlockPos();
                this.client.debugRenderer.villageDebugRenderer.removePointOfInterest(_snowman17);
            } else if (CustomPayloadS2CPacket.DEBUG_POI_TICKET_COUNT.equals(identifier)) {
                BlockPos _snowman18 = _snowman2.readBlockPos();
                _snowman = _snowman2.readInt();
                this.client.debugRenderer.villageDebugRenderer.setFreeTicketCount(_snowman18, _snowman);
            } else if (CustomPayloadS2CPacket.DEBUG_GOAL_SELECTOR.equals(identifier)) {
                BlockPos _snowman19 = _snowman2.readBlockPos();
                _snowman = _snowman2.readInt();
                _snowman = _snowman2.readInt();
                ArrayList _snowman20 = Lists.newArrayList();
                for (_snowman = 0; _snowman < _snowman; ++_snowman) {
                    _snowman = _snowman2.readInt();
                    boolean bl = _snowman2.readBoolean();
                    String _snowman21 = _snowman2.readString(255);
                    _snowman20.add(new GoalSelectorDebugRenderer.GoalSelector(_snowman19, _snowman, _snowman21, bl));
                }
                this.client.debugRenderer.goalSelectorDebugRenderer.setGoalSelectorList(_snowman, _snowman20);
            } else if (CustomPayloadS2CPacket.DEBUG_RAIDS.equals(identifier)) {
                int n = _snowman2.readInt();
                ArrayList _snowman22 = Lists.newArrayList();
                for (_snowman = 0; _snowman < n; ++_snowman) {
                    _snowman22.add(_snowman2.readBlockPos());
                }
                this.client.debugRenderer.raidCenterDebugRenderer.setRaidCenters(_snowman22);
            } else if (CustomPayloadS2CPacket.DEBUG_BRAIN.equals(identifier)) {
                double _snowman23 = _snowman2.readDouble();
                double _snowman24 = _snowman2.readDouble();
                double _snowman25 = _snowman2.readDouble();
                PositionImpl _snowman26 = new PositionImpl(_snowman23, _snowman24, _snowman25);
                UUID _snowman27 = _snowman2.readUuid();
                _snowman = _snowman2.readInt();
                String _snowman28 = _snowman2.readString();
                String _snowman29 = _snowman2.readString();
                _snowman = _snowman2.readInt();
                float _snowman30 = _snowman2.readFloat();
                float _snowman31 = _snowman2.readFloat();
                String _snowman32 = _snowman2.readString();
                boolean _snowman33 = _snowman2.readBoolean();
                Path _snowman34 = _snowman33 ? Path.fromBuffer(_snowman2) : null;
                boolean _snowman35 = _snowman2.readBoolean();
                VillageDebugRenderer.Brain _snowman36 = new VillageDebugRenderer.Brain(_snowman27, _snowman, _snowman28, _snowman29, _snowman, _snowman30, _snowman31, _snowman26, _snowman32, _snowman34, _snowman35);
                _snowman = _snowman2.readInt();
                for (n = 0; n < _snowman; ++n) {
                    String string = _snowman2.readString();
                    _snowman36.field_18927.add(string);
                }
                int n = _snowman2.readInt();
                for (n2 = 0; n2 < n; ++n2) {
                    String string = _snowman2.readString();
                    _snowman36.field_18928.add(string);
                }
                int n2 = _snowman2.readInt();
                for (n3 = 0; n3 < n2; ++n3) {
                    String string = _snowman2.readString();
                    _snowman36.field_19374.add(string);
                }
                int n3 = _snowman2.readInt();
                for (n4 = 0; n4 < n3; ++n4) {
                    BlockPos blockPos = _snowman2.readBlockPos();
                    _snowman36.pointsOfInterest.add(blockPos);
                }
                int n4 = _snowman2.readInt();
                for (n5 = 0; n5 < n4; ++n5) {
                    BlockPos blockPos = _snowman2.readBlockPos();
                    _snowman36.field_25287.add(blockPos);
                }
                int n5 = _snowman2.readInt();
                for (_snowman = 0; _snowman < n5; ++_snowman) {
                    String string = _snowman2.readString();
                    _snowman36.field_19375.add(string);
                }
                this.client.debugRenderer.villageDebugRenderer.addBrain(_snowman36);
            } else if (CustomPayloadS2CPacket.DEBUG_BEE.equals(identifier)) {
                int n;
                double d = _snowman2.readDouble();
                _snowman = _snowman2.readDouble();
                _snowman = _snowman2.readDouble();
                PositionImpl _snowman37 = new PositionImpl(d, _snowman, _snowman);
                UUID _snowman38 = _snowman2.readUuid();
                int _snowman39 = _snowman2.readInt();
                boolean _snowman40 = _snowman2.readBoolean();
                BlockPos _snowman41 = null;
                if (_snowman40) {
                    _snowman41 = _snowman2.readBlockPos();
                }
                boolean _snowman42 = _snowman2.readBoolean();
                BlockPos _snowman43 = null;
                if (_snowman42) {
                    _snowman43 = _snowman2.readBlockPos();
                }
                int _snowman44 = _snowman2.readInt();
                boolean _snowman45 = _snowman2.readBoolean();
                Path _snowman46 = null;
                if (_snowman45) {
                    _snowman46 = Path.fromBuffer(_snowman2);
                }
                BeeDebugRenderer.Bee _snowman47 = new BeeDebugRenderer.Bee(_snowman38, _snowman39, _snowman37, _snowman46, _snowman41, _snowman43, _snowman44);
                int _snowman48 = _snowman2.readInt();
                for (n = 0; n < _snowman48; ++n) {
                    String string = _snowman2.readString();
                    _snowman47.labels.add(string);
                }
                n = _snowman2.readInt();
                for (_snowman = 0; _snowman < n; ++_snowman) {
                    BlockPos blockPos = _snowman2.readBlockPos();
                    _snowman47.blacklist.add(blockPos);
                }
                this.client.debugRenderer.beeDebugRenderer.addBee(_snowman47);
            } else if (CustomPayloadS2CPacket.DEBUG_HIVE.equals(identifier)) {
                BlockPos blockPos = _snowman2.readBlockPos();
                String _snowman49 = _snowman2.readString();
                int _snowman50 = _snowman2.readInt();
                int _snowman51 = _snowman2.readInt();
                boolean _snowman52 = _snowman2.readBoolean();
                BeeDebugRenderer.Hive _snowman53 = new BeeDebugRenderer.Hive(blockPos, _snowman49, _snowman50, _snowman51, _snowman52, this.world.getTime());
                this.client.debugRenderer.beeDebugRenderer.addHive(_snowman53);
            } else if (CustomPayloadS2CPacket.DEBUG_GAME_TEST_CLEAR.equals(identifier)) {
                this.client.debugRenderer.gameTestDebugRenderer.clear();
            } else if (CustomPayloadS2CPacket.DEBUG_GAME_TEST_ADD_MARKER.equals(identifier)) {
                _snowman = _snowman2.readBlockPos();
                int _snowman54 = _snowman2.readInt();
                String _snowman55 = _snowman2.readString();
                int _snowman56 = _snowman2.readInt();
                this.client.debugRenderer.gameTestDebugRenderer.addMarker(_snowman, _snowman54, _snowman55, _snowman56);
            } else {
                LOGGER.warn("Unknown custom packed identifier: {}", (Object)identifier);
            }
        }
        finally {
            if (_snowman2 != null) {
                _snowman2.release();
            }
        }
    }

    @Override
    public void onScoreboardObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String _snowman2 = packet.getName();
        if (packet.getMode() == 0) {
            scoreboard.addObjective(_snowman2, ScoreboardCriterion.DUMMY, packet.getDisplayName(), packet.getType());
        } else if (scoreboard.containsObjective(_snowman2)) {
            ScoreboardObjective scoreboardObjective = scoreboard.getNullableObjective(_snowman2);
            if (packet.getMode() == 1) {
                scoreboard.removeObjective(scoreboardObjective);
            } else if (packet.getMode() == 2) {
                scoreboardObjective.setRenderType(packet.getType());
                scoreboardObjective.setDisplayName(packet.getDisplayName());
            }
        }
    }

    @Override
    public void onScoreboardPlayerUpdate(ScoreboardPlayerUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String _snowman2 = packet.getObjectiveName();
        switch (packet.getUpdateMode()) {
            case CHANGE: {
                ScoreboardObjective scoreboardObjective = scoreboard.getObjective(_snowman2);
                ScoreboardPlayerScore _snowman3 = scoreboard.getPlayerScore(packet.getPlayerName(), scoreboardObjective);
                _snowman3.setScore(packet.getScore());
                break;
            }
            case REMOVE: {
                scoreboard.resetPlayerScore(packet.getPlayerName(), scoreboard.getNullableObjective(_snowman2));
            }
        }
    }

    @Override
    public void onScoreboardDisplay(ScoreboardDisplayS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        String _snowman2 = packet.getName();
        ScoreboardObjective _snowman3 = _snowman2 == null ? null : scoreboard.getObjective(_snowman2);
        scoreboard.setObjectiveSlot(packet.getSlot(), _snowman3);
    }

    @Override
    public void onTeam(TeamS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Scoreboard scoreboard = this.world.getScoreboard();
        Team _snowman2 = packet.getMode() == 0 ? scoreboard.addTeam(packet.getTeamName()) : scoreboard.getTeam(packet.getTeamName());
        if (packet.getMode() == 0 || packet.getMode() == 2) {
            _snowman2.setDisplayName(packet.getDisplayName());
            _snowman2.setColor(packet.getPlayerPrefix());
            _snowman2.setFriendlyFlagsBitwise(packet.getFlags());
            AbstractTeam.VisibilityRule visibilityRule = AbstractTeam.VisibilityRule.getRule(packet.getNameTagVisibilityRule());
            if (visibilityRule != null) {
                _snowman2.setNameTagVisibilityRule(visibilityRule);
            }
            if ((object = AbstractTeam.CollisionRule.getRule(packet.getCollisionRule())) != null) {
                _snowman2.setCollisionRule((AbstractTeam.CollisionRule)((Object)object));
            }
            _snowman2.setPrefix(packet.getPrefix());
            _snowman2.setSuffix(packet.getSuffix());
        }
        if (packet.getMode() == 0 || packet.getMode() == 3) {
            for (Object object : packet.getPlayerList()) {
                scoreboard.addPlayerToTeam((String)object, _snowman2);
            }
        }
        if (packet.getMode() == 4) {
            for (Object object : packet.getPlayerList()) {
                scoreboard.removePlayerFromTeam((String)object, _snowman2);
            }
        }
        if (packet.getMode() == 1) {
            scoreboard.removeTeam(_snowman2);
        }
    }

    @Override
    public void onParticle(ParticleS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        if (packet.getCount() == 0) {
            double d = packet.getSpeed() * packet.getOffsetX();
            _snowman = packet.getSpeed() * packet.getOffsetY();
            _snowman = packet.getSpeed() * packet.getOffsetZ();
            try {
                this.world.addParticle(packet.getParameters(), packet.isLongDistance(), packet.getX(), packet.getY(), packet.getZ(), d, _snowman, _snowman);
            }
            catch (Throwable _snowman2) {
                LOGGER.warn("Could not spawn particle effect {}", (Object)packet.getParameters());
            }
        } else {
            for (int i = 0; i < packet.getCount(); ++i) {
                double d = this.random.nextGaussian() * (double)packet.getOffsetX();
                _snowman = this.random.nextGaussian() * (double)packet.getOffsetY();
                _snowman = this.random.nextGaussian() * (double)packet.getOffsetZ();
                _snowman = this.random.nextGaussian() * (double)packet.getSpeed();
                _snowman = this.random.nextGaussian() * (double)packet.getSpeed();
                _snowman = this.random.nextGaussian() * (double)packet.getSpeed();
                try {
                    this.world.addParticle(packet.getParameters(), packet.isLongDistance(), packet.getX() + d, packet.getY() + _snowman, packet.getZ() + _snowman, _snowman, _snowman, _snowman);
                    continue;
                }
                catch (Throwable _snowman3) {
                    LOGGER.warn("Could not spawn particle effect {}", (Object)packet.getParameters());
                    return;
                }
            }
        }
    }

    @Override
    public void onEntityAttributes(EntityAttributesS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        Entity entity = this.world.getEntityById(packet.getEntityId());
        if (entity == null) {
            return;
        }
        if (!(entity instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + entity + ")");
        }
        AttributeContainer _snowman2 = ((LivingEntity)entity).getAttributes();
        for (EntityAttributesS2CPacket.Entry entry : packet.getEntries()) {
            EntityAttributeInstance entityAttributeInstance = _snowman2.getCustomInstance(entry.getId());
            if (entityAttributeInstance == null) {
                LOGGER.warn("Entity {} does not have attribute {}", (Object)entity, (Object)Registry.ATTRIBUTE.getId(entry.getId()));
                continue;
            }
            entityAttributeInstance.setBaseValue(entry.getBaseValue());
            entityAttributeInstance.clearModifiers();
            for (EntityAttributeModifier entityAttributeModifier : entry.getModifiers()) {
                entityAttributeInstance.addTemporaryModifier(entityAttributeModifier);
            }
        }
    }

    @Override
    public void onCraftFailedResponse(CraftFailedResponseS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ScreenHandler screenHandler = this.client.player.currentScreenHandler;
        if (screenHandler.syncId != packet.getSyncId() || !screenHandler.isNotRestricted(this.client.player)) {
            return;
        }
        this.recipeManager.get(packet.getRecipeId()).ifPresent(recipe -> {
            if (this.client.currentScreen instanceof RecipeBookProvider) {
                RecipeBookWidget recipeBookWidget = ((RecipeBookProvider)((Object)this.client.currentScreen)).getRecipeBookWidget();
                recipeBookWidget.showGhostRecipe((Recipe<?>)recipe, screenHandler.slots);
            }
        });
    }

    @Override
    public void onLightUpdate(LightUpdateS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        int n = packet.getChunkX();
        _snowman = packet.getChunkZ();
        LightingProvider _snowman2 = this.world.getChunkManager().getLightingProvider();
        _snowman = packet.getSkyLightMask();
        _snowman = packet.getFilledSkyLightMask();
        Iterator<byte[]> _snowman3 = packet.getSkyLightUpdates().iterator();
        this.updateLighting(n, _snowman, _snowman2, LightType.SKY, _snowman, _snowman, _snowman3, packet.method_30006());
        _snowman = packet.getBlockLightMask();
        _snowman = packet.getFilledBlockLightMask();
        Iterator<byte[]> _snowman4 = packet.getBlockLightUpdates().iterator();
        this.updateLighting(n, _snowman, _snowman2, LightType.BLOCK, _snowman, _snowman, _snowman4, packet.method_30006());
    }

    @Override
    public void onSetTradeOffers(SetTradeOffersS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        ScreenHandler screenHandler = this.client.player.currentScreenHandler;
        if (packet.getSyncId() == screenHandler.syncId && screenHandler instanceof MerchantScreenHandler) {
            ((MerchantScreenHandler)screenHandler).setOffers(new TradeOfferList(packet.getOffers().toTag()));
            ((MerchantScreenHandler)screenHandler).setExperienceFromServer(packet.getExperience());
            ((MerchantScreenHandler)screenHandler).setLevelProgress(packet.getLevelProgress());
            ((MerchantScreenHandler)screenHandler).setCanLevel(packet.isLeveled());
            ((MerchantScreenHandler)screenHandler).setRefreshTrades(packet.isRefreshable());
        }
    }

    @Override
    public void onChunkLoadDistance(ChunkLoadDistanceS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.chunkLoadDistance = packet.getDistance();
        this.world.getChunkManager().updateLoadDistance(packet.getDistance());
    }

    @Override
    public void onChunkRenderDistanceCenter(ChunkRenderDistanceCenterS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.world.getChunkManager().setChunkMapCenter(packet.getChunkX(), packet.getChunkZ());
    }

    @Override
    public void onPlayerActionResponse(PlayerActionResponseS2CPacket packet) {
        NetworkThreadUtils.forceMainThread(packet, this, this.client);
        this.client.interactionManager.processPlayerActionResponse(this.world, packet.getBlockPos(), packet.getBlockState(), packet.getAction(), packet.isApproved());
    }

    private void updateLighting(int chunkX, int chunkZ, LightingProvider provider, LightType type, int mask, int filledMask, Iterator<byte[]> updates, boolean bl) {
        for (int i = 0; i < 18; ++i) {
            _snowman = -1 + i;
            boolean bl2 = (mask & 1 << i) != 0;
            boolean bl3 = _snowman = (filledMask & 1 << i) != 0;
            if (!bl2 && !_snowman) continue;
            provider.enqueueSectionData(type, ChunkSectionPos.from(chunkX, _snowman, chunkZ), bl2 ? new ChunkNibbleArray((byte[])updates.next().clone()) : new ChunkNibbleArray(), bl);
            this.world.scheduleBlockRenders(chunkX, _snowman, chunkZ);
        }
    }

    @Override
    public ClientConnection getConnection() {
        return this.connection;
    }

    public Collection<PlayerListEntry> getPlayerList() {
        return this.playerListEntries.values();
    }

    public Collection<UUID> getPlayerUuids() {
        return this.playerListEntries.keySet();
    }

    @Nullable
    public PlayerListEntry getPlayerListEntry(UUID uuid) {
        return this.playerListEntries.get(uuid);
    }

    @Nullable
    public PlayerListEntry getPlayerListEntry(String profileName) {
        for (PlayerListEntry playerListEntry : this.playerListEntries.values()) {
            if (!playerListEntry.getProfile().getName().equals(profileName)) continue;
            return playerListEntry;
        }
        return null;
    }

    public GameProfile getProfile() {
        return this.profile;
    }

    public ClientAdvancementManager getAdvancementHandler() {
        return this.advancementHandler;
    }

    public CommandDispatcher<CommandSource> getCommandDispatcher() {
        return this.commandDispatcher;
    }

    public ClientWorld getWorld() {
        return this.world;
    }

    public TagManager getTagManager() {
        return this.tagManager;
    }

    public DataQueryHandler getDataQueryHandler() {
        return this.dataQueryHandler;
    }

    public UUID getSessionId() {
        return this.sessionId;
    }

    public Set<RegistryKey<World>> getWorldKeys() {
        return this.worldKeys;
    }

    public DynamicRegistryManager getRegistryManager() {
        return this.registryManager;
    }
}

