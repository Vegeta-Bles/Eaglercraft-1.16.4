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
import java.util.List;
import java.util.Map;
import java.util.Random;
import java.util.Set;
import java.util.UUID;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import javax.annotation.Nullable;
import net.minecraft.advancement.Advancement;
import net.minecraft.block.Block;
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
import net.minecraft.util.math.Position;
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

public class ClientPlayNetworkHandler implements ClientPlayPacketListener {
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

      ArrayList<RegistryKey<World>> _snowman = Lists.newArrayList(packet.getDimensionIds());
      Collections.shuffle(_snowman);
      this.worldKeys = Sets.newLinkedHashSet(_snowman);
      this.registryManager = packet.getRegistryManager();
      RegistryKey<World> _snowmanx = packet.getDimensionId();
      DimensionType _snowmanxx = packet.getDimensionType();
      this.chunkLoadDistance = packet.getViewDistance();
      boolean _snowmanxxx = packet.isDebugWorld();
      boolean _snowmanxxxx = packet.isFlatWorld();
      ClientWorld.Properties _snowmanxxxxx = new ClientWorld.Properties(Difficulty.NORMAL, packet.isHardcore(), _snowmanxxxx);
      this.worldProperties = _snowmanxxxxx;
      this.world = new ClientWorld(
         this, _snowmanxxxxx, _snowmanx, _snowmanxx, this.chunkLoadDistance, this.client::getProfiler, this.client.worldRenderer, _snowmanxxx, packet.getSha256Seed()
      );
      this.client.joinWorld(this.world);
      if (this.client.player == null) {
         this.client.player = this.client.interactionManager.createPlayer(this.world, new StatHandler(), new ClientRecipeBook());
         this.client.player.yaw = -180.0F;
         if (this.client.getServer() != null) {
            this.client.getServer().setLocalPlayerUuid(this.client.player.getUuid());
         }
      }

      this.client.debugRenderer.reset();
      this.client.player.afterSpawn();
      int _snowmanxxxxxx = packet.getEntityId();
      this.world.addPlayer(_snowmanxxxxxx, this.client.player);
      this.client.player.input = new KeyboardInput(this.client.options);
      this.client.interactionManager.copyAbilities(this.client.player);
      this.client.cameraEntity = this.client.player;
      this.client.openScreen(new DownloadingTerrainScreen());
      this.client.player.setEntityId(_snowmanxxxxxx);
      this.client.player.setReducedDebugInfo(packet.hasReducedDebugInfo());
      this.client.player.setShowsDeathScreen(packet.showsDeathScreen());
      this.client.interactionManager.setGameMode(packet.getGameMode());
      this.client.interactionManager.setPreviousGameMode(packet.getPreviousGameMode());
      this.client.options.onPlayerModelPartChange();
      this.connection
         .send(
            new CustomPayloadC2SPacket(CustomPayloadC2SPacket.BRAND, new PacketByteBuf(Unpooled.buffer()).writeString(ClientBrandRetriever.getClientModName()))
         );
      this.client.getGame().onStartGameSession();
   }

   @Override
   public void onEntitySpawn(EntitySpawnS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      double _snowman = packet.getX();
      double _snowmanx = packet.getY();
      double _snowmanxx = packet.getZ();
      EntityType<?> _snowmanxxx = packet.getEntityTypeId();
      Entity _snowmanxxxx;
      if (_snowmanxxx == EntityType.CHEST_MINECART) {
         _snowmanxxxx = new ChestMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.FURNACE_MINECART) {
         _snowmanxxxx = new FurnaceMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.TNT_MINECART) {
         _snowmanxxxx = new TntMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.SPAWNER_MINECART) {
         _snowmanxxxx = new SpawnerMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.HOPPER_MINECART) {
         _snowmanxxxx = new HopperMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.COMMAND_BLOCK_MINECART) {
         _snowmanxxxx = new CommandBlockMinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.MINECART) {
         _snowmanxxxx = new MinecartEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.FISHING_BOBBER) {
         Entity _snowmanxxxxx = this.world.getEntityById(packet.getEntityData());
         if (_snowmanxxxxx instanceof PlayerEntity) {
            _snowmanxxxx = new FishingBobberEntity(this.world, (PlayerEntity)_snowmanxxxxx, _snowman, _snowmanx, _snowmanxx);
         } else {
            _snowmanxxxx = null;
         }
      } else if (_snowmanxxx == EntityType.ARROW) {
         _snowmanxxxx = new ArrowEntity(this.world, _snowman, _snowmanx, _snowmanxx);
         Entity _snowmanxxxxx = this.world.getEntityById(packet.getEntityData());
         if (_snowmanxxxxx != null) {
            ((PersistentProjectileEntity)_snowmanxxxx).setOwner(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == EntityType.SPECTRAL_ARROW) {
         _snowmanxxxx = new SpectralArrowEntity(this.world, _snowman, _snowmanx, _snowmanxx);
         Entity _snowmanxxxxx = this.world.getEntityById(packet.getEntityData());
         if (_snowmanxxxxx != null) {
            ((PersistentProjectileEntity)_snowmanxxxx).setOwner(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == EntityType.TRIDENT) {
         _snowmanxxxx = new TridentEntity(this.world, _snowman, _snowmanx, _snowmanxx);
         Entity _snowmanxxxxx = this.world.getEntityById(packet.getEntityData());
         if (_snowmanxxxxx != null) {
            ((PersistentProjectileEntity)_snowmanxxxx).setOwner(_snowmanxxxxx);
         }
      } else if (_snowmanxxx == EntityType.SNOWBALL) {
         _snowmanxxxx = new SnowballEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.LLAMA_SPIT) {
         _snowmanxxxx = new LlamaSpitEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.ITEM_FRAME) {
         _snowmanxxxx = new ItemFrameEntity(this.world, new BlockPos(_snowman, _snowmanx, _snowmanxx), Direction.byId(packet.getEntityData()));
      } else if (_snowmanxxx == EntityType.LEASH_KNOT) {
         _snowmanxxxx = new LeashKnotEntity(this.world, new BlockPos(_snowman, _snowmanx, _snowmanxx));
      } else if (_snowmanxxx == EntityType.ENDER_PEARL) {
         _snowmanxxxx = new EnderPearlEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.EYE_OF_ENDER) {
         _snowmanxxxx = new EyeOfEnderEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.FIREWORK_ROCKET) {
         _snowmanxxxx = new FireworkRocketEntity(this.world, _snowman, _snowmanx, _snowmanxx, ItemStack.EMPTY);
      } else if (_snowmanxxx == EntityType.FIREBALL) {
         _snowmanxxxx = new FireballEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.DRAGON_FIREBALL) {
         _snowmanxxxx = new DragonFireballEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.SMALL_FIREBALL) {
         _snowmanxxxx = new SmallFireballEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.WITHER_SKULL) {
         _snowmanxxxx = new WitherSkullEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.SHULKER_BULLET) {
         _snowmanxxxx = new ShulkerBulletEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getVelocityX(), packet.getVelocityY(), packet.getVelocityZ());
      } else if (_snowmanxxx == EntityType.EGG) {
         _snowmanxxxx = new EggEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.EVOKER_FANGS) {
         _snowmanxxxx = new EvokerFangsEntity(this.world, _snowman, _snowmanx, _snowmanxx, 0.0F, 0, null);
      } else if (_snowmanxxx == EntityType.POTION) {
         _snowmanxxxx = new PotionEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.EXPERIENCE_BOTTLE) {
         _snowmanxxxx = new ExperienceBottleEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.BOAT) {
         _snowmanxxxx = new BoatEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.TNT) {
         _snowmanxxxx = new TntEntity(this.world, _snowman, _snowmanx, _snowmanxx, null);
      } else if (_snowmanxxx == EntityType.ARMOR_STAND) {
         _snowmanxxxx = new ArmorStandEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.END_CRYSTAL) {
         _snowmanxxxx = new EndCrystalEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.ITEM) {
         _snowmanxxxx = new ItemEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.FALLING_BLOCK) {
         _snowmanxxxx = new FallingBlockEntity(this.world, _snowman, _snowmanx, _snowmanxx, Block.getStateFromRawId(packet.getEntityData()));
      } else if (_snowmanxxx == EntityType.AREA_EFFECT_CLOUD) {
         _snowmanxxxx = new AreaEffectCloudEntity(this.world, _snowman, _snowmanx, _snowmanxx);
      } else if (_snowmanxxx == EntityType.LIGHTNING_BOLT) {
         _snowmanxxxx = new LightningEntity(EntityType.LIGHTNING_BOLT, this.world);
      } else {
         _snowmanxxxx = null;
      }

      if (_snowmanxxxx != null) {
         int _snowmanxxxxx = packet.getId();
         _snowmanxxxx.updateTrackedPosition(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxx.refreshPositionAfterTeleport(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxx.pitch = (float)(packet.getPitch() * 360) / 256.0F;
         _snowmanxxxx.yaw = (float)(packet.getYaw() * 360) / 256.0F;
         _snowmanxxxx.setEntityId(_snowmanxxxxx);
         _snowmanxxxx.setUuid(packet.getUuid());
         this.world.addEntity(_snowmanxxxxx, _snowmanxxxx);
         if (_snowmanxxxx instanceof AbstractMinecartEntity) {
            this.client.getSoundManager().play(new MovingMinecartSoundInstance((AbstractMinecartEntity)_snowmanxxxx));
         }
      }
   }

   @Override
   public void onExperienceOrbSpawn(ExperienceOrbSpawnS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      double _snowman = packet.getX();
      double _snowmanx = packet.getY();
      double _snowmanxx = packet.getZ();
      Entity _snowmanxxx = new ExperienceOrbEntity(this.world, _snowman, _snowmanx, _snowmanxx, packet.getExperience());
      _snowmanxxx.updateTrackedPosition(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxx.yaw = 0.0F;
      _snowmanxxx.pitch = 0.0F;
      _snowmanxxx.setEntityId(packet.getId());
      this.world.addEntity(packet.getId(), _snowmanxxx);
   }

   @Override
   public void onPaintingSpawn(PaintingSpawnS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      PaintingEntity _snowman = new PaintingEntity(this.world, packet.getPos(), packet.getFacing(), packet.getMotive());
      _snowman.setEntityId(packet.getId());
      _snowman.setUuid(packet.getPaintingUuid());
      this.world.addEntity(packet.getId(), _snowman);
   }

   @Override
   public void onVelocityUpdate(EntityVelocityUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getId());
      if (_snowman != null) {
         _snowman.setVelocityClient((double)packet.getVelocityX() / 8000.0, (double)packet.getVelocityY() / 8000.0, (double)packet.getVelocityZ() / 8000.0);
      }
   }

   @Override
   public void onEntityTrackerUpdate(EntityTrackerUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.id());
      if (_snowman != null && packet.getTrackedValues() != null) {
         _snowman.getDataTracker().writeUpdatedEntries(packet.getTrackedValues());
      }
   }

   @Override
   public void onPlayerSpawn(PlayerSpawnS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      double _snowman = packet.getX();
      double _snowmanx = packet.getY();
      double _snowmanxx = packet.getZ();
      float _snowmanxxx = (float)(packet.getYaw() * 360) / 256.0F;
      float _snowmanxxxx = (float)(packet.getPitch() * 360) / 256.0F;
      int _snowmanxxxxx = packet.getId();
      OtherClientPlayerEntity _snowmanxxxxxx = new OtherClientPlayerEntity(this.client.world, this.getPlayerListEntry(packet.getPlayerUuid()).getProfile());
      _snowmanxxxxxx.setEntityId(_snowmanxxxxx);
      _snowmanxxxxxx.resetPosition(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxxxxx.updateTrackedPosition(_snowman, _snowmanx, _snowmanxx);
      _snowmanxxxxxx.updatePositionAndAngles(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
      this.world.addPlayer(_snowmanxxxxx, _snowmanxxxxxx);
   }

   @Override
   public void onEntityPosition(EntityPositionS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getId());
      if (_snowman != null) {
         double _snowmanx = packet.getX();
         double _snowmanxx = packet.getY();
         double _snowmanxxx = packet.getZ();
         _snowman.updateTrackedPosition(_snowmanx, _snowmanxx, _snowmanxxx);
         if (!_snowman.isLogicalSideForUpdatingMovement()) {
            float _snowmanxxxx = (float)(packet.getYaw() * 360) / 256.0F;
            float _snowmanxxxxx = (float)(packet.getPitch() * 360) / 256.0F;
            _snowman.updateTrackedPositionAndAngles(_snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, 3, true);
            _snowman.setOnGround(packet.isOnGround());
         }
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
      Entity _snowman = packet.getEntity(this.world);
      if (_snowman != null) {
         if (!_snowman.isLogicalSideForUpdatingMovement()) {
            if (packet.isPositionChanged()) {
               Vec3d _snowmanx = packet.calculateDeltaPosition(_snowman.getTrackedPosition());
               _snowman.updateTrackedPosition(_snowmanx);
               float _snowmanxx = packet.hasRotation() ? (float)(packet.getYaw() * 360) / 256.0F : _snowman.yaw;
               float _snowmanxxx = packet.hasRotation() ? (float)(packet.getPitch() * 360) / 256.0F : _snowman.pitch;
               _snowman.updateTrackedPositionAndAngles(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ(), _snowmanxx, _snowmanxxx, 3, false);
            } else if (packet.hasRotation()) {
               float _snowmanx = (float)(packet.getYaw() * 360) / 256.0F;
               float _snowmanxx = (float)(packet.getPitch() * 360) / 256.0F;
               _snowman.updateTrackedPositionAndAngles(_snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowmanx, _snowmanxx, 3, false);
            }

            _snowman.setOnGround(packet.isOnGround());
         }
      }
   }

   @Override
   public void onEntitySetHeadYaw(EntitySetHeadYawS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = packet.getEntity(this.world);
      if (_snowman != null) {
         float _snowmanx = (float)(packet.getHeadYaw() * 360) / 256.0F;
         _snowman.updateTrackedHeadRotation(_snowmanx, 3);
      }
   }

   @Override
   public void onEntitiesDestroy(EntitiesDestroyS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);

      for (int _snowman = 0; _snowman < packet.getEntityIds().length; _snowman++) {
         int _snowmanx = packet.getEntityIds()[_snowman];
         this.world.removeEntity(_snowmanx);
      }
   }

   @Override
   public void onPlayerPositionLook(PlayerPositionLookS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      PlayerEntity _snowman = this.client.player;
      Vec3d _snowmanx = _snowman.getVelocity();
      boolean _snowmanxx = packet.getFlags().contains(PlayerPositionLookS2CPacket.Flag.X);
      boolean _snowmanxxx = packet.getFlags().contains(PlayerPositionLookS2CPacket.Flag.Y);
      boolean _snowmanxxxx = packet.getFlags().contains(PlayerPositionLookS2CPacket.Flag.Z);
      double _snowmanxxxxx;
      double _snowmanxxxxxx;
      if (_snowmanxx) {
         _snowmanxxxxx = _snowmanx.getX();
         _snowmanxxxxxx = _snowman.getX() + packet.getX();
         _snowman.lastRenderX = _snowman.lastRenderX + packet.getX();
      } else {
         _snowmanxxxxx = 0.0;
         _snowmanxxxxxx = packet.getX();
         _snowman.lastRenderX = _snowmanxxxxxx;
      }

      double _snowmanxxxxxxx;
      double _snowmanxxxxxxxx;
      if (_snowmanxxx) {
         _snowmanxxxxxxx = _snowmanx.getY();
         _snowmanxxxxxxxx = _snowman.getY() + packet.getY();
         _snowman.lastRenderY = _snowman.lastRenderY + packet.getY();
      } else {
         _snowmanxxxxxxx = 0.0;
         _snowmanxxxxxxxx = packet.getY();
         _snowman.lastRenderY = _snowmanxxxxxxxx;
      }

      double _snowmanxxxxxxxxx;
      double _snowmanxxxxxxxxxx;
      if (_snowmanxxxx) {
         _snowmanxxxxxxxxx = _snowmanx.getZ();
         _snowmanxxxxxxxxxx = _snowman.getZ() + packet.getZ();
         _snowman.lastRenderZ = _snowman.lastRenderZ + packet.getZ();
      } else {
         _snowmanxxxxxxxxx = 0.0;
         _snowmanxxxxxxxxxx = packet.getZ();
         _snowman.lastRenderZ = _snowmanxxxxxxxxxx;
      }

      if (_snowman.age > 0 && _snowman.getVehicle() != null) {
         _snowman.method_29239();
      }

      _snowman.setPos(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx);
      _snowman.prevX = _snowmanxxxxxx;
      _snowman.prevY = _snowmanxxxxxxxx;
      _snowman.prevZ = _snowmanxxxxxxxxxx;
      _snowman.setVelocity(_snowmanxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx);
      float _snowmanxxxxxxxxxxx = packet.getYaw();
      float _snowmanxxxxxxxxxxxx = packet.getPitch();
      if (packet.getFlags().contains(PlayerPositionLookS2CPacket.Flag.X_ROT)) {
         _snowmanxxxxxxxxxxxx += _snowman.pitch;
      }

      if (packet.getFlags().contains(PlayerPositionLookS2CPacket.Flag.Y_ROT)) {
         _snowmanxxxxxxxxxxx += _snowman.yaw;
      }

      _snowman.updatePositionAndAngles(_snowmanxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx);
      this.connection.send(new TeleportConfirmC2SPacket(packet.getTeleportId()));
      this.connection.send(new PlayerMoveC2SPacket.Both(_snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowman.yaw, _snowman.pitch, false));
      if (!this.positionLookSetup) {
         this.positionLookSetup = true;
         this.client.openScreen(null);
      }
   }

   @Override
   public void onChunkDeltaUpdate(ChunkDeltaUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      int _snowman = 19 | (packet.method_31179() ? 128 : 0);
      packet.visitUpdates((_snowmanx, _snowmanxx) -> this.world.setBlockState(_snowmanx, _snowmanxx, _snowman));
   }

   @Override
   public void onChunkData(ChunkDataS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      int _snowman = packet.getX();
      int _snowmanx = packet.getZ();
      BiomeArray _snowmanxx = packet.getBiomeArray() == null ? null : new BiomeArray(this.registryManager.get(Registry.BIOME_KEY), packet.getBiomeArray());
      WorldChunk _snowmanxxx = this.world
         .getChunkManager()
         .loadChunkFromPacket(_snowman, _snowmanx, _snowmanxx, packet.getReadBuffer(), packet.getHeightmaps(), packet.getVerticalStripBitmask(), packet.isFullChunk());
      if (_snowmanxxx != null && packet.isFullChunk()) {
         this.world.addEntitiesToChunk(_snowmanxxx);
      }

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         this.world.scheduleBlockRenders(_snowman, _snowmanxxxx, _snowmanx);
      }

      for (CompoundTag _snowmanxxxx : packet.getBlockEntityTagList()) {
         BlockPos _snowmanxxxxx = new BlockPos(_snowmanxxxx.getInt("x"), _snowmanxxxx.getInt("y"), _snowmanxxxx.getInt("z"));
         BlockEntity _snowmanxxxxxx = this.world.getBlockEntity(_snowmanxxxxx);
         if (_snowmanxxxxxx != null) {
            _snowmanxxxxxx.fromTag(this.world.getBlockState(_snowmanxxxxx), _snowmanxxxx);
         }
      }
   }

   @Override
   public void onUnloadChunk(UnloadChunkS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      int _snowman = packet.getX();
      int _snowmanx = packet.getZ();
      ClientChunkManager _snowmanxx = this.world.getChunkManager();
      _snowmanxx.unload(_snowman, _snowmanx);
      LightingProvider _snowmanxxx = _snowmanxx.getLightingProvider();

      for (int _snowmanxxxx = 0; _snowmanxxxx < 16; _snowmanxxxx++) {
         this.world.scheduleBlockRenders(_snowman, _snowmanxxxx, _snowmanx);
         _snowmanxxx.setSectionStatus(ChunkSectionPos.from(_snowman, _snowmanxxxx, _snowmanx), true);
      }

      _snowmanxxx.setColumnEnabled(new ChunkPos(_snowman, _snowmanx), false);
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
      Entity _snowman = this.world.getEntityById(packet.getEntityId());
      LivingEntity _snowmanx = (LivingEntity)this.world.getEntityById(packet.getCollectorEntityId());
      if (_snowmanx == null) {
         _snowmanx = this.client.player;
      }

      if (_snowman != null) {
         if (_snowman instanceof ExperienceOrbEntity) {
            this.world
               .playSound(
                  _snowman.getX(),
                  _snowman.getY(),
                  _snowman.getZ(),
                  SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP,
                  SoundCategory.PLAYERS,
                  0.1F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 0.35F + 0.9F,
                  false
               );
         } else {
            this.world
               .playSound(
                  _snowman.getX(),
                  _snowman.getY(),
                  _snowman.getZ(),
                  SoundEvents.ENTITY_ITEM_PICKUP,
                  SoundCategory.PLAYERS,
                  0.2F,
                  (this.random.nextFloat() - this.random.nextFloat()) * 1.4F + 2.0F,
                  false
               );
         }

         this.client
            .particleManager
            .addParticle(new ItemPickupParticle(this.client.getEntityRenderDispatcher(), this.client.getBufferBuilders(), this.world, _snowman, _snowmanx));
         if (_snowman instanceof ItemEntity) {
            ItemEntity _snowmanxx = (ItemEntity)_snowman;
            ItemStack _snowmanxxx = _snowmanxx.getStack();
            _snowmanxxx.decrement(packet.getStackAmount());
            if (_snowmanxxx.isEmpty()) {
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
      Entity _snowman = this.world.getEntityById(packet.getId());
      if (_snowman != null) {
         if (packet.getAnimationId() == 0) {
            LivingEntity _snowmanx = (LivingEntity)_snowman;
            _snowmanx.swingHand(Hand.MAIN_HAND);
         } else if (packet.getAnimationId() == 3) {
            LivingEntity _snowmanx = (LivingEntity)_snowman;
            _snowmanx.swingHand(Hand.OFF_HAND);
         } else if (packet.getAnimationId() == 1) {
            _snowman.animateDamage();
         } else if (packet.getAnimationId() == 2) {
            PlayerEntity _snowmanx = (PlayerEntity)_snowman;
            _snowmanx.wakeUp(false, false);
         } else if (packet.getAnimationId() == 4) {
            this.client.particleManager.addEmitter(_snowman, ParticleTypes.CRIT);
         } else if (packet.getAnimationId() == 5) {
            this.client.particleManager.addEmitter(_snowman, ParticleTypes.ENCHANTED_HIT);
         }
      }
   }

   @Override
   public void onMobSpawn(MobSpawnS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      double _snowman = packet.getX();
      double _snowmanx = packet.getY();
      double _snowmanxx = packet.getZ();
      float _snowmanxxx = (float)(packet.getYaw() * 360) / 256.0F;
      float _snowmanxxxx = (float)(packet.getPitch() * 360) / 256.0F;
      LivingEntity _snowmanxxxxx = (LivingEntity)EntityType.createInstanceFromId(packet.getEntityTypeId(), this.client.world);
      if (_snowmanxxxxx != null) {
         _snowmanxxxxx.updateTrackedPosition(_snowman, _snowmanx, _snowmanxx);
         _snowmanxxxxx.bodyYaw = (float)(packet.getHeadYaw() * 360) / 256.0F;
         _snowmanxxxxx.headYaw = (float)(packet.getHeadYaw() * 360) / 256.0F;
         if (_snowmanxxxxx instanceof EnderDragonEntity) {
            EnderDragonPart[] _snowmanxxxxxx = ((EnderDragonEntity)_snowmanxxxxx).getBodyParts();

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxxxx.length; _snowmanxxxxxxx++) {
               _snowmanxxxxxx[_snowmanxxxxxxx].setEntityId(_snowmanxxxxxxx + packet.getId());
            }
         }

         _snowmanxxxxx.setEntityId(packet.getId());
         _snowmanxxxxx.setUuid(packet.getUuid());
         _snowmanxxxxx.updatePositionAndAngles(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx);
         _snowmanxxxxx.setVelocity(
            (double)((float)packet.getVelocityX() / 8000.0F),
            (double)((float)packet.getVelocityY() / 8000.0F),
            (double)((float)packet.getVelocityZ() / 8000.0F)
         );
         this.world.addEntity(packet.getId(), _snowmanxxxxx);
         if (_snowmanxxxxx instanceof BeeEntity) {
            boolean _snowmanxxxxxx = ((BeeEntity)_snowmanxxxxx).hasAngerTime();
            AbstractBeeSoundInstance _snowmanxxxxxxx;
            if (_snowmanxxxxxx) {
               _snowmanxxxxxxx = new AggressiveBeeSoundInstance((BeeEntity)_snowmanxxxxx);
            } else {
               _snowmanxxxxxxx = new PassiveBeeSoundInstance((BeeEntity)_snowmanxxxxx);
            }

            this.client.getSoundManager().playNextTick(_snowmanxxxxxxx);
         }
      } else {
         LOGGER.warn("Skipping Entity with id {}", packet.getEntityTypeId());
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
      Entity _snowman = this.world.getEntityById(packet.getId());
      if (_snowman == null) {
         LOGGER.warn("Received passengers for unknown entity");
      } else {
         boolean _snowmanx = _snowman.hasPassengerDeep(this.client.player);
         _snowman.removeAllPassengers();

         for (int _snowmanxx : packet.getPassengerIds()) {
            Entity _snowmanxxx = this.world.getEntityById(_snowmanxx);
            if (_snowmanxxx != null) {
               _snowmanxxx.startRiding(_snowman, true);
               if (_snowmanxxx == this.client.player && !_snowmanx) {
                  this.client
                     .inGameHud
                     .setOverlayMessage(new TranslatableText("mount.onboard", this.client.options.keySneak.getBoundKeyLocalizedText()), false);
               }
            }
         }
      }
   }

   @Override
   public void onEntityAttach(EntityAttachS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getAttachedEntityId());
      if (_snowman instanceof MobEntity) {
         ((MobEntity)_snowman).setHoldingEntityId(packet.getHoldingEntityId());
      }
   }

   private static ItemStack getActiveTotemOfUndying(PlayerEntity player) {
      for (Hand _snowman : Hand.values()) {
         ItemStack _snowmanx = player.getStackInHand(_snowman);
         if (_snowmanx.getItem() == Items.TOTEM_OF_UNDYING) {
            return _snowmanx;
         }
      }

      return new ItemStack(Items.TOTEM_OF_UNDYING);
   }

   @Override
   public void onEntityStatus(EntityStatusS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = packet.getEntity(this.world);
      if (_snowman != null) {
         if (packet.getStatus() == 21) {
            this.client.getSoundManager().play(new GuardianAttackSoundInstance((GuardianEntity)_snowman));
         } else if (packet.getStatus() == 35) {
            int _snowmanx = 40;
            this.client.particleManager.addEmitter(_snowman, ParticleTypes.TOTEM_OF_UNDYING, 30);
            this.world.playSound(_snowman.getX(), _snowman.getY(), _snowman.getZ(), SoundEvents.ITEM_TOTEM_USE, _snowman.getSoundCategory(), 1.0F, 1.0F, false);
            if (_snowman == this.client.player) {
               this.client.gameRenderer.showFloatingItem(getActiveTotemOfUndying(this.client.player));
            }
         } else {
            _snowman.handleStatus(packet.getStatus());
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
      RegistryKey<World> _snowman = packet.getDimension();
      DimensionType _snowmanx = packet.method_29445();
      ClientPlayerEntity _snowmanxx = this.client.player;
      int _snowmanxxx = _snowmanxx.getEntityId();
      this.positionLookSetup = false;
      if (_snowman != _snowmanxx.world.getRegistryKey()) {
         Scoreboard _snowmanxxxx = this.world.getScoreboard();
         boolean _snowmanxxxxx = packet.isDebugWorld();
         boolean _snowmanxxxxxx = packet.isFlatWorld();
         ClientWorld.Properties _snowmanxxxxxxx = new ClientWorld.Properties(this.worldProperties.getDifficulty(), this.worldProperties.isHardcore(), _snowmanxxxxxx);
         this.worldProperties = _snowmanxxxxxxx;
         this.world = new ClientWorld(
            this, _snowmanxxxxxxx, _snowman, _snowmanx, this.chunkLoadDistance, this.client::getProfiler, this.client.worldRenderer, _snowmanxxxxx, packet.getSha256Seed()
         );
         this.world.setScoreboard(_snowmanxxxx);
         this.client.joinWorld(this.world);
         this.client.openScreen(new DownloadingTerrainScreen());
      }

      this.world.finishRemovingEntities();
      String _snowmanxxxx = _snowmanxx.getServerBrand();
      this.client.cameraEntity = null;
      ClientPlayerEntity _snowmanxxxxx = this.client
         .interactionManager
         .createPlayer(this.world, _snowmanxx.getStatHandler(), _snowmanxx.getRecipeBook(), _snowmanxx.isSneaking(), _snowmanxx.isSprinting());
      _snowmanxxxxx.setEntityId(_snowmanxxx);
      this.client.player = _snowmanxxxxx;
      if (_snowman != _snowmanxx.world.getRegistryKey()) {
         this.client.getMusicTracker().stop();
      }

      this.client.cameraEntity = _snowmanxxxxx;
      _snowmanxxxxx.getDataTracker().writeUpdatedEntries(_snowmanxx.getDataTracker().getAllEntries());
      if (packet.shouldKeepPlayerAttributes()) {
         _snowmanxxxxx.getAttributes().setFrom(_snowmanxx.getAttributes());
      }

      _snowmanxxxxx.afterSpawn();
      _snowmanxxxxx.setServerBrand(_snowmanxxxx);
      this.world.addPlayer(_snowmanxxx, _snowmanxxxxx);
      _snowmanxxxxx.yaw = -180.0F;
      _snowmanxxxxx.input = new KeyboardInput(this.client.options);
      this.client.interactionManager.copyAbilities(_snowmanxxxxx);
      _snowmanxxxxx.setReducedDebugInfo(_snowmanxx.getReducedDebugInfo());
      _snowmanxxxxx.setShowsDeathScreen(_snowmanxx.showsDeathScreen());
      if (this.client.currentScreen instanceof DeathScreen) {
         this.client.openScreen(null);
      }

      this.client.interactionManager.setGameMode(packet.getGameMode());
      this.client.interactionManager.setPreviousGameMode(packet.getPreviousGameMode());
   }

   @Override
   public void onExplosion(ExplosionS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Explosion _snowman = new Explosion(this.client.world, null, packet.getX(), packet.getY(), packet.getZ(), packet.getRadius(), packet.getAffectedBlocks());
      _snowman.affectWorld(true);
      this.client
         .player
         .setVelocity(
            this.client.player.getVelocity().add((double)packet.getPlayerVelocityX(), (double)packet.getPlayerVelocityY(), (double)packet.getPlayerVelocityZ())
         );
   }

   @Override
   public void onOpenHorseScreen(OpenHorseScreenS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getHorseId());
      if (_snowman instanceof HorseBaseEntity) {
         ClientPlayerEntity _snowmanx = this.client.player;
         HorseBaseEntity _snowmanxx = (HorseBaseEntity)_snowman;
         SimpleInventory _snowmanxxx = new SimpleInventory(packet.getSlotCount());
         HorseScreenHandler _snowmanxxxx = new HorseScreenHandler(packet.getSyncId(), _snowmanx.inventory, _snowmanxxx, _snowmanxx);
         _snowmanx.currentScreenHandler = _snowmanxxxx;
         this.client.openScreen(new HorseScreen(_snowmanxxxx, _snowmanx.inventory, _snowmanxx));
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
      PlayerEntity _snowman = this.client.player;
      ItemStack _snowmanx = packet.getItemStack();
      int _snowmanxx = packet.getSlot();
      this.client.getTutorialManager().onSlotUpdate(_snowmanx);
      if (packet.getSyncId() == -1) {
         if (!(this.client.currentScreen instanceof CreativeInventoryScreen)) {
            _snowman.inventory.setCursorStack(_snowmanx);
         }
      } else if (packet.getSyncId() == -2) {
         _snowman.inventory.setStack(_snowmanxx, _snowmanx);
      } else {
         boolean _snowmanxxx = false;
         if (this.client.currentScreen instanceof CreativeInventoryScreen) {
            CreativeInventoryScreen _snowmanxxxx = (CreativeInventoryScreen)this.client.currentScreen;
            _snowmanxxx = _snowmanxxxx.getSelectedTab() != ItemGroup.INVENTORY.getIndex();
         }

         if (packet.getSyncId() == 0 && packet.getSlot() >= 36 && _snowmanxx < 45) {
            if (!_snowmanx.isEmpty()) {
               ItemStack _snowmanxxxx = _snowman.playerScreenHandler.getSlot(_snowmanxx).getStack();
               if (_snowmanxxxx.isEmpty() || _snowmanxxxx.getCount() < _snowmanx.getCount()) {
                  _snowmanx.setCooldown(5);
               }
            }

            _snowman.playerScreenHandler.setStackInSlot(_snowmanxx, _snowmanx);
         } else if (packet.getSyncId() == _snowman.currentScreenHandler.syncId && (packet.getSyncId() != 0 || !_snowmanxxx)) {
            _snowman.currentScreenHandler.setStackInSlot(_snowmanxx, _snowmanx);
         }
      }
   }

   @Override
   public void onConfirmScreenAction(ConfirmScreenActionS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      ScreenHandler _snowman = null;
      PlayerEntity _snowmanx = this.client.player;
      if (packet.getSyncId() == 0) {
         _snowman = _snowmanx.playerScreenHandler;
      } else if (packet.getSyncId() == _snowmanx.currentScreenHandler.syncId) {
         _snowman = _snowmanx.currentScreenHandler;
      }

      if (_snowman != null && !packet.wasAccepted()) {
         this.sendPacket(new ConfirmScreenActionC2SPacket(packet.getSyncId(), packet.getActionId(), true));
      }
   }

   @Override
   public void onInventory(InventoryS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      PlayerEntity _snowman = this.client.player;
      if (packet.getSyncId() == 0) {
         _snowman.playerScreenHandler.updateSlotStacks(packet.getContents());
      } else if (packet.getSyncId() == _snowman.currentScreenHandler.syncId) {
         _snowman.currentScreenHandler.updateSlotStacks(packet.getContents());
      }
   }

   @Override
   public void onSignEditorOpen(SignEditorOpenS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      BlockEntity _snowman = this.world.getBlockEntity(packet.getPos());
      if (!(_snowman instanceof SignBlockEntity)) {
         _snowman = new SignBlockEntity();
         _snowman.setLocation(this.world, packet.getPos());
      }

      this.client.player.openEditSignScreen((SignBlockEntity)_snowman);
   }

   @Override
   public void onBlockEntityUpdate(BlockEntityUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      BlockPos _snowman = packet.getPos();
      BlockEntity _snowmanx = this.client.world.getBlockEntity(_snowman);
      int _snowmanxx = packet.getBlockEntityType();
      boolean _snowmanxxx = _snowmanxx == 2 && _snowmanx instanceof CommandBlockBlockEntity;
      if (_snowmanxx == 1 && _snowmanx instanceof MobSpawnerBlockEntity
         || _snowmanxxx
         || _snowmanxx == 3 && _snowmanx instanceof BeaconBlockEntity
         || _snowmanxx == 4 && _snowmanx instanceof SkullBlockEntity
         || _snowmanxx == 6 && _snowmanx instanceof BannerBlockEntity
         || _snowmanxx == 7 && _snowmanx instanceof StructureBlockBlockEntity
         || _snowmanxx == 8 && _snowmanx instanceof EndGatewayBlockEntity
         || _snowmanxx == 9 && _snowmanx instanceof SignBlockEntity
         || _snowmanxx == 11 && _snowmanx instanceof BedBlockEntity
         || _snowmanxx == 5 && _snowmanx instanceof ConduitBlockEntity
         || _snowmanxx == 12 && _snowmanx instanceof JigsawBlockEntity
         || _snowmanxx == 13 && _snowmanx instanceof CampfireBlockEntity
         || _snowmanxx == 14 && _snowmanx instanceof BeehiveBlockEntity) {
         _snowmanx.fromTag(this.client.world.getBlockState(_snowman), packet.getCompoundTag());
      }

      if (_snowmanxxx && this.client.currentScreen instanceof CommandBlockScreen) {
         ((CommandBlockScreen)this.client.currentScreen).updateCommandBlock();
      }
   }

   @Override
   public void onScreenHandlerPropertyUpdate(ScreenHandlerPropertyUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      PlayerEntity _snowman = this.client.player;
      if (_snowman.currentScreenHandler != null && _snowman.currentScreenHandler.syncId == packet.getSyncId()) {
         _snowman.currentScreenHandler.setProperty(packet.getPropertyId(), packet.getValue());
      }
   }

   @Override
   public void onEquipmentUpdate(EntityEquipmentUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getId());
      if (_snowman != null) {
         packet.getEquipmentList().forEach(_snowmanx -> _snowman.equipStack((EquipmentSlot)_snowmanx.getFirst(), (ItemStack)_snowmanx.getSecond()));
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
      PlayerEntity _snowman = this.client.player;
      GameStateChangeS2CPacket.Reason _snowmanx = packet.getReason();
      float _snowmanxx = packet.getValue();
      int _snowmanxxx = MathHelper.floor(_snowmanxx + 0.5F);
      if (_snowmanx == GameStateChangeS2CPacket.NO_RESPAWN_BLOCK) {
         _snowman.sendMessage(new TranslatableText("block.minecraft.spawn.not_valid"), false);
      } else if (_snowmanx == GameStateChangeS2CPacket.RAIN_STARTED) {
         this.world.getLevelProperties().setRaining(true);
         this.world.setRainGradient(0.0F);
      } else if (_snowmanx == GameStateChangeS2CPacket.RAIN_STOPPED) {
         this.world.getLevelProperties().setRaining(false);
         this.world.setRainGradient(1.0F);
      } else if (_snowmanx == GameStateChangeS2CPacket.GAME_MODE_CHANGED) {
         this.client.interactionManager.setGameMode(GameMode.byId(_snowmanxxx));
      } else if (_snowmanx == GameStateChangeS2CPacket.GAME_WON) {
         if (_snowmanxxx == 0) {
            this.client.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN));
            this.client.openScreen(new DownloadingTerrainScreen());
         } else if (_snowmanxxx == 1) {
            this.client
               .openScreen(
                  new CreditsScreen(
                     true, () -> this.client.player.networkHandler.sendPacket(new ClientStatusC2SPacket(ClientStatusC2SPacket.Mode.PERFORM_RESPAWN))
                  )
               );
         }
      } else if (_snowmanx == GameStateChangeS2CPacket.DEMO_MESSAGE_SHOWN) {
         GameOptions _snowmanxxxx = this.client.options;
         if (_snowmanxx == 0.0F) {
            this.client.openScreen(new DemoScreen());
         } else if (_snowmanxx == 101.0F) {
            this.client
               .inGameHud
               .getChatHud()
               .addMessage(
                  new TranslatableText(
                     "demo.help.movement",
                     _snowmanxxxx.keyForward.getBoundKeyLocalizedText(),
                     _snowmanxxxx.keyLeft.getBoundKeyLocalizedText(),
                     _snowmanxxxx.keyBack.getBoundKeyLocalizedText(),
                     _snowmanxxxx.keyRight.getBoundKeyLocalizedText()
                  )
               );
         } else if (_snowmanxx == 102.0F) {
            this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.help.jump", _snowmanxxxx.keyJump.getBoundKeyLocalizedText()));
         } else if (_snowmanxx == 103.0F) {
            this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.help.inventory", _snowmanxxxx.keyInventory.getBoundKeyLocalizedText()));
         } else if (_snowmanxx == 104.0F) {
            this.client.inGameHud.getChatHud().addMessage(new TranslatableText("demo.day.6", _snowmanxxxx.keyScreenshot.getBoundKeyLocalizedText()));
         }
      } else if (_snowmanx == GameStateChangeS2CPacket.PROJECTILE_HIT_PLAYER) {
         this.world.playSound(_snowman, _snowman.getX(), _snowman.getEyeY(), _snowman.getZ(), SoundEvents.ENTITY_ARROW_HIT_PLAYER, SoundCategory.PLAYERS, 0.18F, 0.45F);
      } else if (_snowmanx == GameStateChangeS2CPacket.RAIN_GRADIENT_CHANGED) {
         this.world.setRainGradient(_snowmanxx);
      } else if (_snowmanx == GameStateChangeS2CPacket.THUNDER_GRADIENT_CHANGED) {
         this.world.setThunderGradient(_snowmanxx);
      } else if (_snowmanx == GameStateChangeS2CPacket.PUFFERFISH_STING) {
         this.world.playSound(_snowman, _snowman.getX(), _snowman.getY(), _snowman.getZ(), SoundEvents.ENTITY_PUFFER_FISH_STING, SoundCategory.NEUTRAL, 1.0F, 1.0F);
      } else if (_snowmanx == GameStateChangeS2CPacket.ELDER_GUARDIAN_EFFECT) {
         this.world.addParticle(ParticleTypes.ELDER_GUARDIAN, _snowman.getX(), _snowman.getY(), _snowman.getZ(), 0.0, 0.0, 0.0);
         if (_snowmanxxx == 1) {
            this.world.playSound(_snowman, _snowman.getX(), _snowman.getY(), _snowman.getZ(), SoundEvents.ENTITY_ELDER_GUARDIAN_CURSE, SoundCategory.HOSTILE, 1.0F, 1.0F);
         }
      } else if (_snowmanx == GameStateChangeS2CPacket.IMMEDIATE_RESPAWN) {
         this.client.player.setShowsDeathScreen(_snowmanxx == 0.0F);
      }
   }

   @Override
   public void onMapUpdate(MapUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      MapRenderer _snowman = this.client.gameRenderer.getMapRenderer();
      String _snowmanx = FilledMapItem.getMapName(packet.getId());
      MapState _snowmanxx = this.client.world.getMapState(_snowmanx);
      if (_snowmanxx == null) {
         _snowmanxx = new MapState(_snowmanx);
         if (_snowman.getTexture(_snowmanx) != null) {
            MapState _snowmanxxx = _snowman.getState(_snowman.getTexture(_snowmanx));
            if (_snowmanxxx != null) {
               _snowmanxx = _snowmanxxx;
            }
         }

         this.client.world.putMapState(_snowmanxx);
      }

      packet.apply(_snowmanxx);
      _snowman.updateTexture(_snowmanxx);
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
      Identifier _snowman = packet.getTabId();
      if (_snowman == null) {
         this.advancementHandler.selectTab(null, false);
      } else {
         Advancement _snowmanx = this.advancementHandler.getManager().get(_snowman);
         this.advancementHandler.selectTab(_snowmanx, false);
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
      SearchableContainer<RecipeResultCollection> _snowman = this.client.getSearchableContainer(SearchManager.RECIPE_OUTPUT);
      _snowman.clear();
      ClientRecipeBook _snowmanx = this.client.player.getRecipeBook();
      _snowmanx.reload(this.recipeManager.values());
      _snowmanx.getOrderedResults().forEach(_snowman::add);
      _snowman.reload();
   }

   @Override
   public void onLookAt(LookAtS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Vec3d _snowman = packet.getTargetPosition(this.world);
      if (_snowman != null) {
         this.client.player.lookAt(packet.getSelfAnchor(), _snowman);
      }
   }

   @Override
   public void onTagQuery(TagQueryResponseS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      if (!this.dataQueryHandler.handleQueryResponse(packet.getTransactionId(), packet.getTag())) {
         LOGGER.debug("Got unhandled response to tag query {}", packet.getTransactionId());
      }
   }

   @Override
   public void onStatistics(StatisticsS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);

      for (Entry<Stat<?>, Integer> _snowman : packet.getStatMap().entrySet()) {
         Stat<?> _snowmanx = _snowman.getKey();
         int _snowmanxx = _snowman.getValue();
         this.client.player.getStatHandler().setStat(this.client.player, _snowmanx, _snowmanxx);
      }

      if (this.client.currentScreen instanceof StatsListener) {
         ((StatsListener)this.client.currentScreen).onStatsReady();
      }
   }

   @Override
   public void onUnlockRecipes(UnlockRecipesS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      ClientRecipeBook _snowman = this.client.player.getRecipeBook();
      _snowman.setOptions(packet.getOptions());
      UnlockRecipesS2CPacket.Action _snowmanx = packet.getAction();
      switch (_snowmanx) {
         case REMOVE:
            for (Identifier _snowmanxx : packet.getRecipeIdsToChange()) {
               this.recipeManager.get(_snowmanxx).ifPresent(_snowman::remove);
            }
            break;
         case INIT:
            for (Identifier _snowmanxx : packet.getRecipeIdsToChange()) {
               this.recipeManager.get(_snowmanxx).ifPresent(_snowman::add);
            }

            for (Identifier _snowmanxx : packet.getRecipeIdsToInit()) {
               this.recipeManager.get(_snowmanxx).ifPresent(_snowman::display);
            }
            break;
         case ADD:
            for (Identifier _snowmanxx : packet.getRecipeIdsToChange()) {
               this.recipeManager.get(_snowmanxx).ifPresent(_snowmanxxx -> {
                  _snowman.add((Recipe<?>)_snowmanxxx);
                  _snowman.display((Recipe<?>)_snowmanxxx);
                  RecipeToast.show(this.client.getToastManager(), (Recipe<?>)_snowmanxxx);
               });
            }
      }

      _snowman.getOrderedResults().forEach(_snowmanx -> _snowmanx.initialize(_snowman));
      if (this.client.currentScreen instanceof RecipeBookProvider) {
         ((RecipeBookProvider)this.client.currentScreen).refreshRecipeBook();
      }
   }

   @Override
   public void onEntityPotionEffect(EntityStatusEffectS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getEntityId());
      if (_snowman instanceof LivingEntity) {
         StatusEffect _snowmanx = StatusEffect.byRawId(packet.getEffectId());
         if (_snowmanx != null) {
            StatusEffectInstance _snowmanxx = new StatusEffectInstance(
               _snowmanx, packet.getDuration(), packet.getAmplifier(), packet.isAmbient(), packet.shouldShowParticles(), packet.shouldShowIcon()
            );
            _snowmanxx.setPermanent(packet.isPermanent());
            ((LivingEntity)_snowman).applyStatusEffect(_snowmanxx);
         }
      }
   }

   @Override
   public void onSynchronizeTags(SynchronizeTagsS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      TagManager _snowman = packet.getTagManager();
      Multimap<Identifier, Identifier> _snowmanx = RequiredTagListRegistry.getMissingTags(_snowman);
      if (!_snowmanx.isEmpty()) {
         LOGGER.warn("Incomplete server tags, disconnecting. Missing: {}", _snowmanx);
         this.connection.disconnect(new TranslatableText("multiplayer.disconnect.missing_tags"));
      } else {
         this.tagManager = _snowman;
         if (!this.connection.isLocal()) {
            _snowman.apply();
         }

         this.client.getSearchableContainer(SearchManager.ITEM_TAG).reload();
      }
   }

   @Override
   public void onCombatEvent(CombatEventS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      if (packet.type == CombatEventS2CPacket.Type.ENTITY_DIED) {
         Entity _snowman = this.world.getEntityById(packet.entityId);
         if (_snowman == this.client.player) {
            if (this.client.player.showsDeathScreen()) {
               this.client.openScreen(new DeathScreen(packet.deathMessage, this.world.getLevelProperties().isHardcore()));
            } else {
               this.client.player.requestRespawn();
            }
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
      Entity _snowman = packet.getEntity(this.world);
      if (_snowman != null) {
         this.client.setCameraEntity(_snowman);
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
      TitleS2CPacket.Action _snowman = packet.getAction();
      Text _snowmanx = null;
      Text _snowmanxx = null;
      Text _snowmanxxx = packet.getText() != null ? packet.getText() : LiteralText.EMPTY;
      switch (_snowman) {
         case TITLE:
            _snowmanx = _snowmanxxx;
            break;
         case SUBTITLE:
            _snowmanxx = _snowmanxxx;
            break;
         case ACTIONBAR:
            this.client.inGameHud.setOverlayMessage(_snowmanxxx, false);
            return;
         case RESET:
            this.client.inGameHud.setTitles(null, null, -1, -1, -1);
            this.client.inGameHud.setDefaultTitleFade();
            return;
      }

      this.client.inGameHud.setTitles(_snowmanx, _snowmanxx, packet.getFadeInTicks(), packet.getStayTicks(), packet.getFadeOutTicks());
   }

   @Override
   public void onPlayerListHeader(PlayerListHeaderS2CPacket packet) {
      this.client.inGameHud.getPlayerListWidget().setHeader(packet.getHeader().getString().isEmpty() ? null : packet.getHeader());
      this.client.inGameHud.getPlayerListWidget().setFooter(packet.getFooter().getString().isEmpty() ? null : packet.getFooter());
   }

   @Override
   public void onRemoveEntityEffect(RemoveEntityStatusEffectS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = packet.getEntity(this.world);
      if (_snowman instanceof LivingEntity) {
         ((LivingEntity)_snowman).removeStatusEffectInternal(packet.getEffectType());
      }
   }

   @Override
   public void onPlayerList(PlayerListS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);

      for (PlayerListS2CPacket.Entry _snowman : packet.getEntries()) {
         if (packet.getAction() == PlayerListS2CPacket.Action.REMOVE_PLAYER) {
            this.client.getSocialInteractionsManager().method_31341(_snowman.getProfile().getId());
            this.playerListEntries.remove(_snowman.getProfile().getId());
         } else {
            PlayerListEntry _snowmanx = this.playerListEntries.get(_snowman.getProfile().getId());
            if (packet.getAction() == PlayerListS2CPacket.Action.ADD_PLAYER) {
               _snowmanx = new PlayerListEntry(_snowman);
               this.playerListEntries.put(_snowmanx.getProfile().getId(), _snowmanx);
               this.client.getSocialInteractionsManager().method_31337(_snowmanx);
            }

            if (_snowmanx != null) {
               switch (packet.getAction()) {
                  case ADD_PLAYER:
                     _snowmanx.setGameMode(_snowman.getGameMode());
                     _snowmanx.setLatency(_snowman.getLatency());
                     _snowmanx.setDisplayName(_snowman.getDisplayName());
                     break;
                  case UPDATE_GAME_MODE:
                     _snowmanx.setGameMode(_snowman.getGameMode());
                     break;
                  case UPDATE_LATENCY:
                     _snowmanx.setLatency(_snowman.getLatency());
                     break;
                  case UPDATE_DISPLAY_NAME:
                     _snowmanx.setDisplayName(_snowman.getDisplayName());
               }
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
      PlayerEntity _snowman = this.client.player;
      _snowman.abilities.flying = packet.isFlying();
      _snowman.abilities.creativeMode = packet.isCreativeMode();
      _snowman.abilities.invulnerable = packet.isInvulnerable();
      _snowman.abilities.allowFlying = packet.allowFlying();
      _snowman.abilities.setFlySpeed(packet.getFlySpeed());
      _snowman.abilities.setWalkSpeed(packet.getWalkSpeed());
   }

   @Override
   public void onPlaySound(PlaySoundS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      this.client
         .world
         .playSound(
            this.client.player, packet.getX(), packet.getY(), packet.getZ(), packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch()
         );
   }

   @Override
   public void onPlaySoundFromEntity(PlaySoundFromEntityS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getEntityId());
      if (_snowman != null) {
         this.client.world.playSoundFromEntity(this.client.player, _snowman, packet.getSound(), packet.getCategory(), packet.getVolume(), packet.getPitch());
      }
   }

   @Override
   public void onPlaySoundId(PlaySoundIdS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      this.client
         .getSoundManager()
         .play(
            new PositionedSoundInstance(
               packet.getSoundId(),
               packet.getCategory(),
               packet.getVolume(),
               packet.getPitch(),
               false,
               0,
               SoundInstance.AttenuationType.LINEAR,
               packet.getX(),
               packet.getY(),
               packet.getZ(),
               false
            )
         );
   }

   @Override
   public void onResourcePackSend(ResourcePackSendS2CPacket packet) {
      String _snowman = packet.getURL();
      String _snowmanx = packet.getSHA1();
      if (this.validateResourcePackUrl(_snowman)) {
         if (_snowman.startsWith("level://")) {
            try {
               String _snowmanxx = URLDecoder.decode(_snowman.substring("level://".length()), StandardCharsets.UTF_8.toString());
               File _snowmanxxx = new File(this.client.runDirectory, "saves");
               File _snowmanxxxx = new File(_snowmanxxx, _snowmanxx);
               if (_snowmanxxxx.isFile()) {
                  this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
                  CompletableFuture<?> _snowmanxxxxx = this.client.getResourcePackDownloader().loadServerPack(_snowmanxxxx, ResourcePackSource.PACK_SOURCE_WORLD);
                  this.feedbackAfterDownload(_snowmanxxxxx);
                  return;
               }
            } catch (UnsupportedEncodingException var8) {
            }

            this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD);
         } else {
            ServerInfo _snowmanxx = this.client.getCurrentServerEntry();
            if (_snowmanxx != null && _snowmanxx.getResourcePack() == ServerInfo.ResourcePackState.ENABLED) {
               this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
               this.feedbackAfterDownload(this.client.getResourcePackDownloader().download(_snowman, _snowmanx));
            } else if (_snowmanxx != null && _snowmanxx.getResourcePack() != ServerInfo.ResourcePackState.PROMPT) {
               this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.DECLINED);
            } else {
               this.client.execute(() -> this.client.openScreen(new ConfirmScreen(_snowmanxxx -> {
                     this.client = MinecraftClient.getInstance();
                     ServerInfo _snowmanxxx = this.client.getCurrentServerEntry();
                     if (_snowmanxxx) {
                        if (_snowmanxxx != null) {
                           _snowmanxxx.setResourcePackState(ServerInfo.ResourcePackState.ENABLED);
                        }

                        this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.ACCEPTED);
                        this.feedbackAfterDownload(this.client.getResourcePackDownloader().download(_snowman, _snowman));
                     } else {
                        if (_snowmanxxx != null) {
                           _snowmanxxx.setResourcePackState(ServerInfo.ResourcePackState.DISABLED);
                        }

                        this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.DECLINED);
                     }

                     ServerList.updateServerListEntry(_snowmanxxx);
                     this.client.openScreen(null);
                  }, new TranslatableText("multiplayer.texturePrompt.line1"), new TranslatableText("multiplayer.texturePrompt.line2"))));
            }
         }
      }
   }

   private boolean validateResourcePackUrl(String url) {
      try {
         URI _snowman = new URI(url);
         String _snowmanx = _snowman.getScheme();
         boolean _snowmanxx = "level".equals(_snowmanx);
         if (!"http".equals(_snowmanx) && !"https".equals(_snowmanx) && !_snowmanxx) {
            throw new URISyntaxException(url, "Wrong protocol");
         } else if (!_snowmanxx || !url.contains("..") && url.endsWith("/resources.zip")) {
            return true;
         } else {
            throw new URISyntaxException(url, "Invalid levelstorage resourcepack path");
         }
      } catch (URISyntaxException var5) {
         this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.FAILED_DOWNLOAD);
         return false;
      }
   }

   private void feedbackAfterDownload(CompletableFuture<?> downloadFuture) {
      downloadFuture.thenRun(() -> this.sendResourcePackStatus(ResourcePackStatusC2SPacket.Status.SUCCESSFULLY_LOADED)).exceptionally(_snowman -> {
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
      Entity _snowman = this.client.player.getRootVehicle();
      if (_snowman != this.client.player && _snowman.isLogicalSideForUpdatingMovement()) {
         _snowman.updatePositionAndAngles(packet.getX(), packet.getY(), packet.getZ(), packet.getYaw(), packet.getPitch());
         this.connection.send(new VehicleMoveC2SPacket(_snowman));
      }
   }

   @Override
   public void onOpenWrittenBook(OpenWrittenBookS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      ItemStack _snowman = this.client.player.getStackInHand(packet.getHand());
      if (_snowman.getItem() == Items.WRITTEN_BOOK) {
         this.client.openScreen(new BookScreen(new BookScreen.WrittenBookContents(_snowman)));
      }
   }

   @Override
   public void onCustomPayload(CustomPayloadS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Identifier _snowman = packet.getChannel();
      PacketByteBuf _snowmanx = null;

      try {
         _snowmanx = packet.getData();
         if (CustomPayloadS2CPacket.BRAND.equals(_snowman)) {
            this.client.player.setServerBrand(_snowmanx.readString(32767));
         } else if (CustomPayloadS2CPacket.DEBUG_PATH.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();
            float _snowmanxxx = _snowmanx.readFloat();
            Path _snowmanxxxx = Path.fromBuffer(_snowmanx);
            this.client.debugRenderer.pathfindingDebugRenderer.addPath(_snowmanxx, _snowmanxxxx, _snowmanxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_NEIGHBORS_UPDATE.equals(_snowman)) {
            long _snowmanxx = _snowmanx.readVarLong();
            BlockPos _snowmanxxx = _snowmanx.readBlockPos();
            ((NeighborUpdateDebugRenderer)this.client.debugRenderer.neighborUpdateDebugRenderer).addNeighborUpdate(_snowmanxx, _snowmanxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_CAVES.equals(_snowman)) {
            BlockPos _snowmanxx = _snowmanx.readBlockPos();
            int _snowmanxxx = _snowmanx.readInt();
            List<BlockPos> _snowmanxxxx = Lists.newArrayList();
            List<Float> _snowmanxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxx; _snowmanxxxxxx++) {
               _snowmanxxxx.add(_snowmanx.readBlockPos());
               _snowmanxxxxx.add(_snowmanx.readFloat());
            }

            this.client.debugRenderer.caveDebugRenderer.method_3704(_snowmanxx, _snowmanxxxx, _snowmanxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_STRUCTURES.equals(_snowman)) {
            DimensionType _snowmanxx = this.registryManager.getDimensionTypes().get(_snowmanx.readIdentifier());
            BlockBox _snowmanxxx = new BlockBox(_snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt());
            int _snowmanxxxx = _snowmanx.readInt();
            List<BlockBox> _snowmanxxxxx = Lists.newArrayList();
            List<Boolean> _snowmanxxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxxx = 0; _snowmanxxxxxxx < _snowmanxxxx; _snowmanxxxxxxx++) {
               _snowmanxxxxx.add(new BlockBox(_snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt(), _snowmanx.readInt()));
               _snowmanxxxxxx.add(_snowmanx.readBoolean());
            }

            this.client.debugRenderer.structureDebugRenderer.method_3871(_snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxx);
         } else if (CustomPayloadS2CPacket.DEBUG_WORLDGEN_ATTEMPT.equals(_snowman)) {
            ((WorldGenAttemptDebugRenderer)this.client.debugRenderer.worldGenAttemptDebugRenderer)
               .method_3872(_snowmanx.readBlockPos(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat(), _snowmanx.readFloat());
         } else if (CustomPayloadS2CPacket.DEBUG_VILLAGE_SECTIONS.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();

            for (int _snowmanxxx = 0; _snowmanxxx < _snowmanxx; _snowmanxxx++) {
               this.client.debugRenderer.villageSectionsDebugRenderer.addSection(_snowmanx.readChunkSectionPos());
            }

            int _snowmanxxx = _snowmanx.readInt();

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
               this.client.debugRenderer.villageSectionsDebugRenderer.removeSection(_snowmanx.readChunkSectionPos());
            }
         } else if (CustomPayloadS2CPacket.DEBUG_POI_ADDED.equals(_snowman)) {
            BlockPos _snowmanxx = _snowmanx.readBlockPos();
            String _snowmanxxx = _snowmanx.readString();
            int _snowmanxxxx = _snowmanx.readInt();
            VillageDebugRenderer.PointOfInterest _snowmanxxxxx = new VillageDebugRenderer.PointOfInterest(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            this.client.debugRenderer.villageDebugRenderer.addPointOfInterest(_snowmanxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_POI_REMOVED.equals(_snowman)) {
            BlockPos _snowmanxx = _snowmanx.readBlockPos();
            this.client.debugRenderer.villageDebugRenderer.removePointOfInterest(_snowmanxx);
         } else if (CustomPayloadS2CPacket.DEBUG_POI_TICKET_COUNT.equals(_snowman)) {
            BlockPos _snowmanxx = _snowmanx.readBlockPos();
            int _snowmanxxx = _snowmanx.readInt();
            this.client.debugRenderer.villageDebugRenderer.setFreeTicketCount(_snowmanxx, _snowmanxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_GOAL_SELECTOR.equals(_snowman)) {
            BlockPos _snowmanxx = _snowmanx.readBlockPos();
            int _snowmanxxx = _snowmanx.readInt();
            int _snowmanxxxx = _snowmanx.readInt();
            List<GoalSelectorDebugRenderer.GoalSelector> _snowmanxxxxx = Lists.newArrayList();

            for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
               int _snowmanxxxxxxx = _snowmanx.readInt();
               boolean _snowmanxxxxxxxx = _snowmanx.readBoolean();
               String _snowmanxxxxxxxxx = _snowmanx.readString(255);
               _snowmanxxxxx.add(new GoalSelectorDebugRenderer.GoalSelector(_snowmanxx, _snowmanxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxx));
            }

            this.client.debugRenderer.goalSelectorDebugRenderer.setGoalSelectorList(_snowmanxxx, _snowmanxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_RAIDS.equals(_snowman)) {
            int _snowmanxx = _snowmanx.readInt();
            Collection<BlockPos> _snowmanxxx = Lists.newArrayList();

            for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxx; _snowmanxxxx++) {
               _snowmanxxx.add(_snowmanx.readBlockPos());
            }

            this.client.debugRenderer.raidCenterDebugRenderer.setRaidCenters(_snowmanxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_BRAIN.equals(_snowman)) {
            double _snowmanxx = _snowmanx.readDouble();
            double _snowmanxxx = _snowmanx.readDouble();
            double _snowmanxxxx = _snowmanx.readDouble();
            Position _snowmanxxxxx = new PositionImpl(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            UUID _snowmanxxxxxx = _snowmanx.readUuid();
            int _snowmanxxxxxxx = _snowmanx.readInt();
            String _snowmanxxxxxxxx = _snowmanx.readString();
            String _snowmanxxxxxxxxx = _snowmanx.readString();
            int _snowmanxxxxxxxxxx = _snowmanx.readInt();
            float _snowmanxxxxxxxxxxx = _snowmanx.readFloat();
            float _snowmanxxxxxxxxxxxx = _snowmanx.readFloat();
            String _snowmanxxxxxxxxxxxxx = _snowmanx.readString();
            boolean _snowmanxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            Path _snowmanxxxxxxxxxxxxxxx;
            if (_snowmanxxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxxx = Path.fromBuffer(_snowmanx);
            } else {
               _snowmanxxxxxxxxxxxxxxx = null;
            }

            boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            VillageDebugRenderer.Brain _snowmanxxxxxxxxxxxxxxxxx = new VillageDebugRenderer.Brain(
               _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanx.readString();
               _snowmanxxxxxxxxxxxxxxxxx.field_18927.add(_snowmanxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readString();
               _snowmanxxxxxxxxxxxxxxxxx.field_18928.add(_snowmanxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readString();
               _snowmanxxxxxxxxxxxxxxxxx.field_19374.add(_snowmanxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readBlockPos();
               _snowmanxxxxxxxxxxxxxxxxx.pointsOfInterest.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readBlockPos();
               _snowmanxxxxxxxxxxxxxxxxx.field_25287.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxxxxxxxxx = _snowmanx.readString();
               _snowmanxxxxxxxxxxxxxxxxx.field_19375.add(_snowmanxxxxxxxxxxxxxxxxxxxxxxxxx);
            }

            this.client.debugRenderer.villageDebugRenderer.addBrain(_snowmanxxxxxxxxxxxxxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_BEE.equals(_snowman)) {
            double _snowmanxx = _snowmanx.readDouble();
            double _snowmanxxx = _snowmanx.readDouble();
            double _snowmanxxxx = _snowmanx.readDouble();
            Position _snowmanxxxxx = new PositionImpl(_snowmanxx, _snowmanxxx, _snowmanxxxx);
            UUID _snowmanxxxxxx = _snowmanx.readUuid();
            int _snowmanxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxx = _snowmanx.readBoolean();
            BlockPos _snowmanxxxxxxxxx = null;
            if (_snowmanxxxxxxxx) {
               _snowmanxxxxxxxxx = _snowmanx.readBlockPos();
            }

            boolean _snowmanxxxxxxxxxx = _snowmanx.readBoolean();
            BlockPos _snowmanxxxxxxxxxxx = null;
            if (_snowmanxxxxxxxxxx) {
               _snowmanxxxxxxxxxxx = _snowmanx.readBlockPos();
            }

            int _snowmanxxxxxxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxxxxxxx = _snowmanx.readBoolean();
            Path _snowmanxxxxxxxxxxxxxx = null;
            if (_snowmanxxxxxxxxxxxxx) {
               _snowmanxxxxxxxxxxxxxx = Path.fromBuffer(_snowmanx);
            }

            BeeDebugRenderer.Bee _snowmanxxxxxxxxxxxxxxx = new BeeDebugRenderer.Bee(
               _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxxxxxxx, _snowmanxxxxxxxxxxxx
            );
            int _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxx++) {
               String _snowmanxxxxxxxxxxxxxxxxxx = _snowmanx.readString();
               _snowmanxxxxxxxxxxxxxxx.labels.add(_snowmanxxxxxxxxxxxxxxxxxx);
            }

            int _snowmanxxxxxxxxxxxxxxxxx = _snowmanx.readInt();

            for (int _snowmanxxxxxxxxxxxxxxxxxx = 0; _snowmanxxxxxxxxxxxxxxxxxx < _snowmanxxxxxxxxxxxxxxxxx; _snowmanxxxxxxxxxxxxxxxxxx++) {
               BlockPos _snowmanxxxxxxxxxxxxxxxxxxx = _snowmanx.readBlockPos();
               _snowmanxxxxxxxxxxxxxxx.blacklist.add(_snowmanxxxxxxxxxxxxxxxxxxx);
            }

            this.client.debugRenderer.beeDebugRenderer.addBee(_snowmanxxxxxxxxxxxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_HIVE.equals(_snowman)) {
            BlockPos _snowmanxxxxxxxxxxxx = _snowmanx.readBlockPos();
            String _snowmanxxxxxxxxxxxxx = _snowmanx.readString();
            int _snowmanxxxxxxxxxxxxxx = _snowmanx.readInt();
            int _snowmanxxxxxxxxxxxxxxx = _snowmanx.readInt();
            boolean _snowmanxxxxxxxxxxxxxxxx = _snowmanx.readBoolean();
            BeeDebugRenderer.Hive _snowmanxxxxxxxxxxxxxxxxx = new BeeDebugRenderer.Hive(
               _snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, this.world.getTime()
            );
            this.client.debugRenderer.beeDebugRenderer.addHive(_snowmanxxxxxxxxxxxxxxxxx);
         } else if (CustomPayloadS2CPacket.DEBUG_GAME_TEST_CLEAR.equals(_snowman)) {
            this.client.debugRenderer.gameTestDebugRenderer.clear();
         } else if (CustomPayloadS2CPacket.DEBUG_GAME_TEST_ADD_MARKER.equals(_snowman)) {
            BlockPos _snowmanxxxxxxxxxxxx = _snowmanx.readBlockPos();
            int _snowmanxxxxxxxxxxxxx = _snowmanx.readInt();
            String _snowmanxxxxxxxxxxxxxx = _snowmanx.readString();
            int _snowmanxxxxxxxxxxxxxxx = _snowmanx.readInt();
            this.client.debugRenderer.gameTestDebugRenderer.addMarker(_snowmanxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx);
         } else {
            LOGGER.warn("Unknown custom packed identifier: {}", _snowman);
         }
      } finally {
         if (_snowmanx != null) {
            _snowmanx.release();
         }
      }
   }

   @Override
   public void onScoreboardObjectiveUpdate(ScoreboardObjectiveUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Scoreboard _snowman = this.world.getScoreboard();
      String _snowmanx = packet.getName();
      if (packet.getMode() == 0) {
         _snowman.addObjective(_snowmanx, ScoreboardCriterion.DUMMY, packet.getDisplayName(), packet.getType());
      } else if (_snowman.containsObjective(_snowmanx)) {
         ScoreboardObjective _snowmanxx = _snowman.getNullableObjective(_snowmanx);
         if (packet.getMode() == 1) {
            _snowman.removeObjective(_snowmanxx);
         } else if (packet.getMode() == 2) {
            _snowmanxx.setRenderType(packet.getType());
            _snowmanxx.setDisplayName(packet.getDisplayName());
         }
      }
   }

   @Override
   public void onScoreboardPlayerUpdate(ScoreboardPlayerUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Scoreboard _snowman = this.world.getScoreboard();
      String _snowmanx = packet.getObjectiveName();
      switch (packet.getUpdateMode()) {
         case CHANGE:
            ScoreboardObjective _snowmanxx = _snowman.getObjective(_snowmanx);
            ScoreboardPlayerScore _snowmanxxx = _snowman.getPlayerScore(packet.getPlayerName(), _snowmanxx);
            _snowmanxxx.setScore(packet.getScore());
            break;
         case REMOVE:
            _snowman.resetPlayerScore(packet.getPlayerName(), _snowman.getNullableObjective(_snowmanx));
      }
   }

   @Override
   public void onScoreboardDisplay(ScoreboardDisplayS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Scoreboard _snowman = this.world.getScoreboard();
      String _snowmanx = packet.getName();
      ScoreboardObjective _snowmanxx = _snowmanx == null ? null : _snowman.getObjective(_snowmanx);
      _snowman.setObjectiveSlot(packet.getSlot(), _snowmanxx);
   }

   @Override
   public void onTeam(TeamS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Scoreboard _snowman = this.world.getScoreboard();
      Team _snowmanx;
      if (packet.getMode() == 0) {
         _snowmanx = _snowman.addTeam(packet.getTeamName());
      } else {
         _snowmanx = _snowman.getTeam(packet.getTeamName());
      }

      if (packet.getMode() == 0 || packet.getMode() == 2) {
         _snowmanx.setDisplayName(packet.getDisplayName());
         _snowmanx.setColor(packet.getPlayerPrefix());
         _snowmanx.setFriendlyFlagsBitwise(packet.getFlags());
         AbstractTeam.VisibilityRule _snowmanxx = AbstractTeam.VisibilityRule.getRule(packet.getNameTagVisibilityRule());
         if (_snowmanxx != null) {
            _snowmanx.setNameTagVisibilityRule(_snowmanxx);
         }

         AbstractTeam.CollisionRule _snowmanxxx = AbstractTeam.CollisionRule.getRule(packet.getCollisionRule());
         if (_snowmanxxx != null) {
            _snowmanx.setCollisionRule(_snowmanxxx);
         }

         _snowmanx.setPrefix(packet.getPrefix());
         _snowmanx.setSuffix(packet.getSuffix());
      }

      if (packet.getMode() == 0 || packet.getMode() == 3) {
         for (String _snowmanxxx : packet.getPlayerList()) {
            _snowman.addPlayerToTeam(_snowmanxxx, _snowmanx);
         }
      }

      if (packet.getMode() == 4) {
         for (String _snowmanxxx : packet.getPlayerList()) {
            _snowman.removePlayerFromTeam(_snowmanxxx, _snowmanx);
         }
      }

      if (packet.getMode() == 1) {
         _snowman.removeTeam(_snowmanx);
      }
   }

   @Override
   public void onParticle(ParticleS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      if (packet.getCount() == 0) {
         double _snowman = (double)(packet.getSpeed() * packet.getOffsetX());
         double _snowmanx = (double)(packet.getSpeed() * packet.getOffsetY());
         double _snowmanxx = (double)(packet.getSpeed() * packet.getOffsetZ());

         try {
            this.world.addParticle(packet.getParameters(), packet.isLongDistance(), packet.getX(), packet.getY(), packet.getZ(), _snowman, _snowmanx, _snowmanxx);
         } catch (Throwable var17) {
            LOGGER.warn("Could not spawn particle effect {}", packet.getParameters());
         }
      } else {
         for (int _snowman = 0; _snowman < packet.getCount(); _snowman++) {
            double _snowmanx = this.random.nextGaussian() * (double)packet.getOffsetX();
            double _snowmanxx = this.random.nextGaussian() * (double)packet.getOffsetY();
            double _snowmanxxx = this.random.nextGaussian() * (double)packet.getOffsetZ();
            double _snowmanxxxx = this.random.nextGaussian() * (double)packet.getSpeed();
            double _snowmanxxxxx = this.random.nextGaussian() * (double)packet.getSpeed();
            double _snowmanxxxxxx = this.random.nextGaussian() * (double)packet.getSpeed();

            try {
               this.world
                  .addParticle(
                     packet.getParameters(), packet.isLongDistance(), packet.getX() + _snowmanx, packet.getY() + _snowmanxx, packet.getZ() + _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx
                  );
            } catch (Throwable var16) {
               LOGGER.warn("Could not spawn particle effect {}", packet.getParameters());
               return;
            }
         }
      }
   }

   @Override
   public void onEntityAttributes(EntityAttributesS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      Entity _snowman = this.world.getEntityById(packet.getEntityId());
      if (_snowman != null) {
         if (!(_snowman instanceof LivingEntity)) {
            throw new IllegalStateException("Server tried to update attributes of a non-living entity (actually: " + _snowman + ")");
         } else {
            AttributeContainer _snowmanx = ((LivingEntity)_snowman).getAttributes();

            for (EntityAttributesS2CPacket.Entry _snowmanxx : packet.getEntries()) {
               EntityAttributeInstance _snowmanxxx = _snowmanx.getCustomInstance(_snowmanxx.getId());
               if (_snowmanxxx == null) {
                  LOGGER.warn("Entity {} does not have attribute {}", _snowman, Registry.ATTRIBUTE.getId(_snowmanxx.getId()));
               } else {
                  _snowmanxxx.setBaseValue(_snowmanxx.getBaseValue());
                  _snowmanxxx.clearModifiers();

                  for (EntityAttributeModifier _snowmanxxxx : _snowmanxx.getModifiers()) {
                     _snowmanxxx.addTemporaryModifier(_snowmanxxxx);
                  }
               }
            }
         }
      }
   }

   @Override
   public void onCraftFailedResponse(CraftFailedResponseS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      ScreenHandler _snowman = this.client.player.currentScreenHandler;
      if (_snowman.syncId == packet.getSyncId() && _snowman.isNotRestricted(this.client.player)) {
         this.recipeManager.get(packet.getRecipeId()).ifPresent(_snowmanx -> {
            if (this.client.currentScreen instanceof RecipeBookProvider) {
               RecipeBookWidget _snowmanx = ((RecipeBookProvider)this.client.currentScreen).getRecipeBookWidget();
               _snowmanx.showGhostRecipe((Recipe<?>)_snowmanx, _snowman.slots);
            }
         });
      }
   }

   @Override
   public void onLightUpdate(LightUpdateS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      int _snowman = packet.getChunkX();
      int _snowmanx = packet.getChunkZ();
      LightingProvider _snowmanxx = this.world.getChunkManager().getLightingProvider();
      int _snowmanxxx = packet.getSkyLightMask();
      int _snowmanxxxx = packet.getFilledSkyLightMask();
      Iterator<byte[]> _snowmanxxxxx = packet.getSkyLightUpdates().iterator();
      this.updateLighting(_snowman, _snowmanx, _snowmanxx, LightType.SKY, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, packet.method_30006());
      int _snowmanxxxxxx = packet.getBlockLightMask();
      int _snowmanxxxxxxx = packet.getFilledBlockLightMask();
      Iterator<byte[]> _snowmanxxxxxxxx = packet.getBlockLightUpdates().iterator();
      this.updateLighting(_snowman, _snowmanx, _snowmanxx, LightType.BLOCK, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, packet.method_30006());
   }

   @Override
   public void onSetTradeOffers(SetTradeOffersS2CPacket packet) {
      NetworkThreadUtils.forceMainThread(packet, this, this.client);
      ScreenHandler _snowman = this.client.player.currentScreenHandler;
      if (packet.getSyncId() == _snowman.syncId && _snowman instanceof MerchantScreenHandler) {
         ((MerchantScreenHandler)_snowman).setOffers(new TradeOfferList(packet.getOffers().toTag()));
         ((MerchantScreenHandler)_snowman).setExperienceFromServer(packet.getExperience());
         ((MerchantScreenHandler)_snowman).setLevelProgress(packet.getLevelProgress());
         ((MerchantScreenHandler)_snowman).setCanLevel(packet.isLeveled());
         ((MerchantScreenHandler)_snowman).setRefreshTrades(packet.isRefreshable());
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
      this.client
         .interactionManager
         .processPlayerActionResponse(this.world, packet.getBlockPos(), packet.getBlockState(), packet.getAction(), packet.isApproved());
   }

   private void updateLighting(int chunkX, int chunkZ, LightingProvider provider, LightType type, int mask, int filledMask, Iterator<byte[]> updates, boolean _snowman) {
      for (int _snowmanx = 0; _snowmanx < 18; _snowmanx++) {
         int _snowmanxx = -1 + _snowmanx;
         boolean _snowmanxxx = (mask & 1 << _snowmanx) != 0;
         boolean _snowmanxxxx = (filledMask & 1 << _snowmanx) != 0;
         if (_snowmanxxx || _snowmanxxxx) {
            provider.enqueueSectionData(
               type, ChunkSectionPos.from(chunkX, _snowmanxx, chunkZ), _snowmanxxx ? new ChunkNibbleArray((byte[])updates.next().clone()) : new ChunkNibbleArray(), _snowman
            );
            this.world.scheduleBlockRenders(chunkX, _snowmanxx, chunkZ);
         }
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
      for (PlayerListEntry _snowman : this.playerListEntries.values()) {
         if (_snowman.getProfile().getName().equals(profileName)) {
            return _snowman;
         }
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
