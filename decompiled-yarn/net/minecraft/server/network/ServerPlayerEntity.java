package net.minecraft.server.network;

import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.util.Either;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.class_5459;
import net.minecraft.advancement.PlayerAdvancementTracker;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.HorizontalFacingBlock;
import net.minecraft.block.NetherPortalBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.CommandBlockBlockEntity;
import net.minecraft.block.entity.SignBlockEntity;
import net.minecraft.client.options.ChatVisibility;
import net.minecraft.command.argument.EntityAnchorArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.EntityDamageSource;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.mob.Angerable;
import net.minecraft.entity.mob.HostileEntity;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.player.ItemCooldownManager;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.NetworkSyncedItem;
import net.minecraft.item.WrittenBookItem;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.network.MessageType;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.c2s.play.ClientSettingsC2SPacket;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.CloseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.CombatEventS2CPacket;
import net.minecraft.network.packet.s2c.play.DifficultyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntitiesDestroyS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityAnimationS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.EntityStatusS2CPacket;
import net.minecraft.network.packet.s2c.play.ExperienceBarUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.GameMessageS2CPacket;
import net.minecraft.network.packet.s2c.play.GameStateChangeS2CPacket;
import net.minecraft.network.packet.s2c.play.HealthUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.InventoryS2CPacket;
import net.minecraft.network.packet.s2c.play.LookAtS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenHorseScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenScreenS2CPacket;
import net.minecraft.network.packet.s2c.play.OpenWrittenBookS2CPacket;
import net.minecraft.network.packet.s2c.play.PlaySoundS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerAbilitiesS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerRespawnS2CPacket;
import net.minecraft.network.packet.s2c.play.PlayerSpawnS2CPacket;
import net.minecraft.network.packet.s2c.play.RemoveEntityStatusEffectS2CPacket;
import net.minecraft.network.packet.s2c.play.ResourcePackSendS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerPropertyUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.network.packet.s2c.play.SetCameraEntityS2CPacket;
import net.minecraft.network.packet.s2c.play.SetTradeOffersS2CPacket;
import net.minecraft.network.packet.s2c.play.SignEditorOpenS2CPacket;
import net.minecraft.network.packet.s2c.play.UnloadChunkS2CPacket;
import net.minecraft.network.packet.s2c.play.WorldEventS2CPacket;
import net.minecraft.recipe.Recipe;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.ScoreboardCriterion;
import net.minecraft.scoreboard.ScoreboardPlayerScore;
import net.minecraft.scoreboard.Team;
import net.minecraft.screen.HorseScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerListener;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.ServerStatHandler;
import net.minecraft.stat.Stat;
import net.minecraft.stat.Stats;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Arm;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Unit;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.crash.CrashException;
import net.minecraft.util.crash.CrashReport;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.ChunkSectionPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.village.TradeOfferList;
import net.minecraft.world.GameMode;
import net.minecraft.world.GameRules;
import net.minecraft.world.TeleportTarget;
import net.minecraft.world.World;
import net.minecraft.world.WorldProperties;
import net.minecraft.world.biome.source.BiomeAccess;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ServerPlayerEntity extends PlayerEntity implements ScreenHandlerListener {
   private static final Logger LOGGER = LogManager.getLogger();
   public ServerPlayNetworkHandler networkHandler;
   public final MinecraftServer server;
   public final ServerPlayerInteractionManager interactionManager;
   private final List<Integer> removedEntities = Lists.newLinkedList();
   private final PlayerAdvancementTracker advancementTracker;
   private final ServerStatHandler statHandler;
   private float lastHealthScore = Float.MIN_VALUE;
   private int lastFoodScore = Integer.MIN_VALUE;
   private int lastAirScore = Integer.MIN_VALUE;
   private int lastArmorScore = Integer.MIN_VALUE;
   private int lastLevelScore = Integer.MIN_VALUE;
   private int lastExperienceScore = Integer.MIN_VALUE;
   private float syncedHealth = -1.0E8F;
   private int syncedFoodLevel = -99999999;
   private boolean syncedSaturationIsZero = true;
   private int syncedExperience = -99999999;
   private int joinInvulnerabilityTicks = 60;
   private ChatVisibility clientChatVisibility;
   private boolean clientChatColorsEnabled = true;
   private long lastActionTime = Util.getMeasuringTimeMs();
   private Entity cameraEntity;
   private boolean inTeleportationState;
   private boolean seenCredits;
   private final ServerRecipeBook recipeBook = new ServerRecipeBook();
   private Vec3d levitationStartPos;
   private int levitationStartTick;
   private boolean disconnected;
   @Nullable
   private Vec3d enteredNetherPos;
   private ChunkSectionPos cameraPosition = ChunkSectionPos.from(0, 0, 0);
   private RegistryKey<World> spawnPointDimension = World.OVERWORLD;
   @Nullable
   private BlockPos spawnPointPosition;
   private boolean spawnPointSet;
   private float spawnAngle;
   @Nullable
   private final TextStream textStream;
   private int screenHandlerSyncId;
   public boolean skipPacketSlotUpdates;
   public int pingMilliseconds;
   public boolean notInAnyWorld;

   public ServerPlayerEntity(MinecraftServer server, ServerWorld world, GameProfile profile, ServerPlayerInteractionManager interactionManager) {
      super(world, world.getSpawnPos(), world.getSpawnAngle(), profile);
      interactionManager.player = this;
      this.interactionManager = interactionManager;
      this.server = server;
      this.statHandler = server.getPlayerManager().createStatHandler(this);
      this.advancementTracker = server.getPlayerManager().getAdvancementTracker(this);
      this.stepHeight = 1.0F;
      this.moveToSpawn(world);
      this.textStream = server.createFilterer(this);
   }

   private void moveToSpawn(ServerWorld world) {
      BlockPos _snowman = world.getSpawnPos();
      if (world.getDimension().hasSkyLight() && world.getServer().getSaveProperties().getGameMode() != GameMode.ADVENTURE) {
         int _snowmanx = Math.max(0, this.server.getSpawnRadius(world));
         int _snowmanxx = MathHelper.floor(world.getWorldBorder().getDistanceInsideBorder((double)_snowman.getX(), (double)_snowman.getZ()));
         if (_snowmanxx < _snowmanx) {
            _snowmanx = _snowmanxx;
         }

         if (_snowmanxx <= 1) {
            _snowmanx = 1;
         }

         long _snowmanxxx = (long)(_snowmanx * 2 + 1);
         long _snowmanxxxx = _snowmanxxx * _snowmanxxx;
         int _snowmanxxxxx = _snowmanxxxx > 2147483647L ? Integer.MAX_VALUE : (int)_snowmanxxxx;
         int _snowmanxxxxxx = this.calculateSpawnOffsetMultiplier(_snowmanxxxxx);
         int _snowmanxxxxxxx = new Random().nextInt(_snowmanxxxxx);

         for (int _snowmanxxxxxxxx = 0; _snowmanxxxxxxxx < _snowmanxxxxx; _snowmanxxxxxxxx++) {
            int _snowmanxxxxxxxxx = (_snowmanxxxxxxx + _snowmanxxxxxx * _snowmanxxxxxxxx) % _snowmanxxxxx;
            int _snowmanxxxxxxxxxx = _snowmanxxxxxxxxx % (_snowmanx * 2 + 1);
            int _snowmanxxxxxxxxxxx = _snowmanxxxxxxxxx / (_snowmanx * 2 + 1);
            BlockPos _snowmanxxxxxxxxxxxx = SpawnLocating.findOverworldSpawn(world, _snowman.getX() + _snowmanxxxxxxxxxx - _snowmanx, _snowman.getZ() + _snowmanxxxxxxxxxxx - _snowmanx, false);
            if (_snowmanxxxxxxxxxxxx != null) {
               this.refreshPositionAndAngles(_snowmanxxxxxxxxxxxx, 0.0F, 0.0F);
               if (world.isSpaceEmpty(this)) {
                  break;
               }
            }
         }
      } else {
         this.refreshPositionAndAngles(_snowman, 0.0F, 0.0F);

         while (!world.isSpaceEmpty(this) && this.getY() < 255.0) {
            this.updatePosition(this.getX(), this.getY() + 1.0, this.getZ());
         }
      }
   }

   private int calculateSpawnOffsetMultiplier(int horizontalSpawnArea) {
      return horizontalSpawnArea <= 16 ? horizontalSpawnArea - 1 : 17;
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      if (tag.contains("playerGameType", 99)) {
         if (this.getServer().shouldForceGameMode()) {
            this.interactionManager.setGameMode(this.getServer().getDefaultGameMode(), GameMode.NOT_SET);
         } else {
            this.interactionManager
               .setGameMode(
                  GameMode.byId(tag.getInt("playerGameType")),
                  tag.contains("previousPlayerGameType", 3) ? GameMode.byId(tag.getInt("previousPlayerGameType")) : GameMode.NOT_SET
               );
         }
      }

      if (tag.contains("enteredNetherPosition", 10)) {
         CompoundTag _snowman = tag.getCompound("enteredNetherPosition");
         this.enteredNetherPos = new Vec3d(_snowman.getDouble("x"), _snowman.getDouble("y"), _snowman.getDouble("z"));
      }

      this.seenCredits = tag.getBoolean("seenCredits");
      if (tag.contains("recipeBook", 10)) {
         this.recipeBook.fromTag(tag.getCompound("recipeBook"), this.server.getRecipeManager());
      }

      if (this.isSleeping()) {
         this.wakeUp();
      }

      if (tag.contains("SpawnX", 99) && tag.contains("SpawnY", 99) && tag.contains("SpawnZ", 99)) {
         this.spawnPointPosition = new BlockPos(tag.getInt("SpawnX"), tag.getInt("SpawnY"), tag.getInt("SpawnZ"));
         this.spawnPointSet = tag.getBoolean("SpawnForced");
         this.spawnAngle = tag.getFloat("SpawnAngle");
         if (tag.contains("SpawnDimension")) {
            this.spawnPointDimension = World.CODEC.parse(NbtOps.INSTANCE, tag.get("SpawnDimension")).resultOrPartial(LOGGER::error).orElse(World.OVERWORLD);
         }
      }
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putInt("playerGameType", this.interactionManager.getGameMode().getId());
      tag.putInt("previousPlayerGameType", this.interactionManager.getPreviousGameMode().getId());
      tag.putBoolean("seenCredits", this.seenCredits);
      if (this.enteredNetherPos != null) {
         CompoundTag _snowman = new CompoundTag();
         _snowman.putDouble("x", this.enteredNetherPos.x);
         _snowman.putDouble("y", this.enteredNetherPos.y);
         _snowman.putDouble("z", this.enteredNetherPos.z);
         tag.put("enteredNetherPosition", _snowman);
      }

      Entity _snowman = this.getRootVehicle();
      Entity _snowmanx = this.getVehicle();
      if (_snowmanx != null && _snowman != this && _snowman.hasPlayerRider()) {
         CompoundTag _snowmanxx = new CompoundTag();
         CompoundTag _snowmanxxx = new CompoundTag();
         _snowman.saveToTag(_snowmanxxx);
         _snowmanxx.putUuid("Attach", _snowmanx.getUuid());
         _snowmanxx.put("Entity", _snowmanxxx);
         tag.put("RootVehicle", _snowmanxx);
      }

      tag.put("recipeBook", this.recipeBook.toTag());
      tag.putString("Dimension", this.world.getRegistryKey().getValue().toString());
      if (this.spawnPointPosition != null) {
         tag.putInt("SpawnX", this.spawnPointPosition.getX());
         tag.putInt("SpawnY", this.spawnPointPosition.getY());
         tag.putInt("SpawnZ", this.spawnPointPosition.getZ());
         tag.putBoolean("SpawnForced", this.spawnPointSet);
         tag.putFloat("SpawnAngle", this.spawnAngle);
         Identifier.CODEC
            .encodeStart(NbtOps.INSTANCE, this.spawnPointDimension.getValue())
            .resultOrPartial(LOGGER::error)
            .ifPresent(_snowmanxx -> tag.put("SpawnDimension", _snowmanxx));
      }
   }

   public void setExperiencePoints(int _snowman) {
      float _snowmanx = (float)this.getNextLevelExperience();
      float _snowmanxx = (_snowmanx - 1.0F) / _snowmanx;
      this.experienceProgress = MathHelper.clamp((float)_snowman / _snowmanx, 0.0F, _snowmanxx);
      this.syncedExperience = -1;
   }

   public void setExperienceLevel(int level) {
      this.experienceLevel = level;
      this.syncedExperience = -1;
   }

   @Override
   public void addExperienceLevels(int levels) {
      super.addExperienceLevels(levels);
      this.syncedExperience = -1;
   }

   @Override
   public void applyEnchantmentCosts(ItemStack enchantedItem, int experienceLevels) {
      super.applyEnchantmentCosts(enchantedItem, experienceLevels);
      this.syncedExperience = -1;
   }

   public void onSpawn() {
      this.currentScreenHandler.addListener(this);
   }

   @Override
   public void enterCombat() {
      super.enterCombat();
      this.networkHandler.sendPacket(new CombatEventS2CPacket(this.getDamageTracker(), CombatEventS2CPacket.Type.ENTER_COMBAT));
   }

   @Override
   public void endCombat() {
      super.endCombat();
      this.networkHandler.sendPacket(new CombatEventS2CPacket(this.getDamageTracker(), CombatEventS2CPacket.Type.END_COMBAT));
   }

   @Override
   protected void onBlockCollision(BlockState state) {
      Criteria.ENTER_BLOCK.trigger(this, state);
   }

   @Override
   protected ItemCooldownManager createCooldownManager() {
      return new ServerItemCooldownManager(this);
   }

   @Override
   public void tick() {
      this.interactionManager.update();
      this.joinInvulnerabilityTicks--;
      if (this.timeUntilRegen > 0) {
         this.timeUntilRegen--;
      }

      this.currentScreenHandler.sendContentUpdates();
      if (!this.world.isClient && !this.currentScreenHandler.canUse(this)) {
         this.closeHandledScreen();
         this.currentScreenHandler = this.playerScreenHandler;
      }

      while (!this.removedEntities.isEmpty()) {
         int _snowman = Math.min(this.removedEntities.size(), Integer.MAX_VALUE);
         int[] _snowmanx = new int[_snowman];
         Iterator<Integer> _snowmanxx = this.removedEntities.iterator();
         int _snowmanxxx = 0;

         while (_snowmanxx.hasNext() && _snowmanxxx < _snowman) {
            _snowmanx[_snowmanxxx++] = _snowmanxx.next();
            _snowmanxx.remove();
         }

         this.networkHandler.sendPacket(new EntitiesDestroyS2CPacket(_snowmanx));
      }

      Entity _snowman = this.getCameraEntity();
      if (_snowman != this) {
         if (_snowman.isAlive()) {
            this.updatePositionAndAngles(_snowman.getX(), _snowman.getY(), _snowman.getZ(), _snowman.yaw, _snowman.pitch);
            this.getServerWorld().getChunkManager().updateCameraPosition(this);
            if (this.shouldDismount()) {
               this.setCameraEntity(this);
            }
         } else {
            this.setCameraEntity(this);
         }
      }

      Criteria.TICK.trigger(this);
      if (this.levitationStartPos != null) {
         Criteria.LEVITATION.trigger(this, this.levitationStartPos, this.age - this.levitationStartTick);
      }

      this.advancementTracker.sendUpdate(this);
   }

   public void playerTick() {
      try {
         if (!this.isSpectator() || this.world.isChunkLoaded(this.getBlockPos())) {
            super.tick();
         }

         for (int _snowman = 0; _snowman < this.inventory.size(); _snowman++) {
            ItemStack _snowmanx = this.inventory.getStack(_snowman);
            if (_snowmanx.getItem().isNetworkSynced()) {
               Packet<?> _snowmanxx = ((NetworkSyncedItem)_snowmanx.getItem()).createSyncPacket(_snowmanx, this.world, this);
               if (_snowmanxx != null) {
                  this.networkHandler.sendPacket(_snowmanxx);
               }
            }
         }

         if (this.getHealth() != this.syncedHealth
            || this.syncedFoodLevel != this.hungerManager.getFoodLevel()
            || this.hungerManager.getSaturationLevel() == 0.0F != this.syncedSaturationIsZero) {
            this.networkHandler
               .sendPacket(new HealthUpdateS2CPacket(this.getHealth(), this.hungerManager.getFoodLevel(), this.hungerManager.getSaturationLevel()));
            this.syncedHealth = this.getHealth();
            this.syncedFoodLevel = this.hungerManager.getFoodLevel();
            this.syncedSaturationIsZero = this.hungerManager.getSaturationLevel() == 0.0F;
         }

         if (this.getHealth() + this.getAbsorptionAmount() != this.lastHealthScore) {
            this.lastHealthScore = this.getHealth() + this.getAbsorptionAmount();
            this.updateScores(ScoreboardCriterion.HEALTH, MathHelper.ceil(this.lastHealthScore));
         }

         if (this.hungerManager.getFoodLevel() != this.lastFoodScore) {
            this.lastFoodScore = this.hungerManager.getFoodLevel();
            this.updateScores(ScoreboardCriterion.FOOD, MathHelper.ceil((float)this.lastFoodScore));
         }

         if (this.getAir() != this.lastAirScore) {
            this.lastAirScore = this.getAir();
            this.updateScores(ScoreboardCriterion.AIR, MathHelper.ceil((float)this.lastAirScore));
         }

         if (this.getArmor() != this.lastArmorScore) {
            this.lastArmorScore = this.getArmor();
            this.updateScores(ScoreboardCriterion.ARMOR, MathHelper.ceil((float)this.lastArmorScore));
         }

         if (this.totalExperience != this.lastExperienceScore) {
            this.lastExperienceScore = this.totalExperience;
            this.updateScores(ScoreboardCriterion.XP, MathHelper.ceil((float)this.lastExperienceScore));
         }

         if (this.experienceLevel != this.lastLevelScore) {
            this.lastLevelScore = this.experienceLevel;
            this.updateScores(ScoreboardCriterion.LEVEL, MathHelper.ceil((float)this.lastLevelScore));
         }

         if (this.totalExperience != this.syncedExperience) {
            this.syncedExperience = this.totalExperience;
            this.networkHandler.sendPacket(new ExperienceBarUpdateS2CPacket(this.experienceProgress, this.totalExperience, this.experienceLevel));
         }

         if (this.age % 20 == 0) {
            Criteria.LOCATION.trigger(this);
         }
      } catch (Throwable var4) {
         CrashReport _snowmanx = CrashReport.create(var4, "Ticking player");
         CrashReportSection _snowmanxx = _snowmanx.addElement("Player being ticked");
         this.populateCrashReport(_snowmanxx);
         throw new CrashException(_snowmanx);
      }
   }

   private void updateScores(ScoreboardCriterion criterion, int score) {
      this.getScoreboard().forEachScore(criterion, this.getEntityName(), _snowmanx -> _snowmanx.setScore(score));
   }

   @Override
   public void onDeath(DamageSource source) {
      boolean _snowman = this.world.getGameRules().getBoolean(GameRules.SHOW_DEATH_MESSAGES);
      if (_snowman) {
         Text _snowmanx = this.getDamageTracker().getDeathMessage();
         this.networkHandler
            .sendPacket(
               new CombatEventS2CPacket(this.getDamageTracker(), CombatEventS2CPacket.Type.ENTITY_DIED, _snowmanx),
               _snowmanxx -> {
                  if (!_snowmanxx.isSuccess()) {
                     int _snowmanxxx = 256;
                     String _snowmanxx = _snowman.asTruncatedString(256);
                     Text _snowmanxxx = new TranslatableText("death.attack.message_too_long", new LiteralText(_snowmanxx).formatted(Formatting.YELLOW));
                     Text _snowmanxxxx = new TranslatableText("death.attack.even_more_magic", this.getDisplayName())
                        .styled(_snowmanxxxxx -> _snowmanxxxxx.withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, _snowman)));
                     this.networkHandler.sendPacket(new CombatEventS2CPacket(this.getDamageTracker(), CombatEventS2CPacket.Type.ENTITY_DIED, _snowmanxxxx));
                  }
               }
            );
         AbstractTeam _snowmanxx = this.getScoreboardTeam();
         if (_snowmanxx == null || _snowmanxx.getDeathMessageVisibilityRule() == AbstractTeam.VisibilityRule.ALWAYS) {
            this.server.getPlayerManager().broadcastChatMessage(_snowmanx, MessageType.SYSTEM, Util.NIL_UUID);
         } else if (_snowmanxx.getDeathMessageVisibilityRule() == AbstractTeam.VisibilityRule.HIDE_FOR_OTHER_TEAMS) {
            this.server.getPlayerManager().sendToTeam(this, _snowmanx);
         } else if (_snowmanxx.getDeathMessageVisibilityRule() == AbstractTeam.VisibilityRule.HIDE_FOR_OWN_TEAM) {
            this.server.getPlayerManager().sendToOtherTeams(this, _snowmanx);
         }
      } else {
         this.networkHandler.sendPacket(new CombatEventS2CPacket(this.getDamageTracker(), CombatEventS2CPacket.Type.ENTITY_DIED));
      }

      this.dropShoulderEntities();
      if (this.world.getGameRules().getBoolean(GameRules.FORGIVE_DEAD_PLAYERS)) {
         this.forgiveMobAnger();
      }

      if (!this.isSpectator()) {
         this.drop(source);
      }

      this.getScoreboard().forEachScore(ScoreboardCriterion.DEATH_COUNT, this.getEntityName(), ScoreboardPlayerScore::incrementScore);
      LivingEntity _snowmanx = this.getPrimeAdversary();
      if (_snowmanx != null) {
         this.incrementStat(Stats.KILLED_BY.getOrCreateStat(_snowmanx.getType()));
         _snowmanx.updateKilledAdvancementCriterion(this, this.scoreAmount, source);
         this.onKilledBy(_snowmanx);
      }

      this.world.sendEntityStatus(this, (byte)3);
      this.incrementStat(Stats.DEATHS);
      this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_DEATH));
      this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
      this.extinguish();
      this.setFlag(0, false);
      this.getDamageTracker().update();
   }

   private void forgiveMobAnger() {
      Box _snowman = new Box(this.getBlockPos()).expand(32.0, 10.0, 32.0);
      this.world
         .getEntitiesIncludingUngeneratedChunks(MobEntity.class, _snowman)
         .stream()
         .filter(_snowmanx -> _snowmanx instanceof Angerable)
         .forEach(_snowmanx -> ((Angerable)_snowmanx).forgive(this));
   }

   @Override
   public void updateKilledAdvancementCriterion(Entity killer, int score, DamageSource damageSource) {
      if (killer != this) {
         super.updateKilledAdvancementCriterion(killer, score, damageSource);
         this.addScore(score);
         String _snowman = this.getEntityName();
         String _snowmanx = killer.getEntityName();
         this.getScoreboard().forEachScore(ScoreboardCriterion.TOTAL_KILL_COUNT, _snowman, ScoreboardPlayerScore::incrementScore);
         if (killer instanceof PlayerEntity) {
            this.incrementStat(Stats.PLAYER_KILLS);
            this.getScoreboard().forEachScore(ScoreboardCriterion.PLAYER_KILL_COUNT, _snowman, ScoreboardPlayerScore::incrementScore);
         } else {
            this.incrementStat(Stats.MOB_KILLS);
         }

         this.updateScoreboardScore(_snowman, _snowmanx, ScoreboardCriterion.TEAM_KILLS);
         this.updateScoreboardScore(_snowmanx, _snowman, ScoreboardCriterion.KILLED_BY_TEAMS);
         Criteria.PLAYER_KILLED_ENTITY.trigger(this, killer, damageSource);
      }
   }

   private void updateScoreboardScore(String playerName, String team, ScoreboardCriterion[] _snowman) {
      Team _snowmanx = this.getScoreboard().getPlayerTeam(team);
      if (_snowmanx != null) {
         int _snowmanxx = _snowmanx.getColor().getColorIndex();
         if (_snowmanxx >= 0 && _snowmanxx < _snowman.length) {
            this.getScoreboard().forEachScore(_snowman[_snowmanxx], playerName, ScoreboardPlayerScore::incrementScore);
         }
      }
   }

   @Override
   public boolean damage(DamageSource source, float amount) {
      if (this.isInvulnerableTo(source)) {
         return false;
      } else {
         boolean _snowman = this.server.isDedicated() && this.isPvpEnabled() && "fall".equals(source.name);
         if (!_snowman && this.joinInvulnerabilityTicks > 0 && source != DamageSource.OUT_OF_WORLD) {
            return false;
         } else {
            if (source instanceof EntityDamageSource) {
               Entity _snowmanx = source.getAttacker();
               if (_snowmanx instanceof PlayerEntity && !this.shouldDamagePlayer((PlayerEntity)_snowmanx)) {
                  return false;
               }

               if (_snowmanx instanceof PersistentProjectileEntity) {
                  PersistentProjectileEntity _snowmanxx = (PersistentProjectileEntity)_snowmanx;
                  Entity _snowmanxxx = _snowmanxx.getOwner();
                  if (_snowmanxxx instanceof PlayerEntity && !this.shouldDamagePlayer((PlayerEntity)_snowmanxxx)) {
                     return false;
                  }
               }
            }

            return super.damage(source, amount);
         }
      }
   }

   @Override
   public boolean shouldDamagePlayer(PlayerEntity player) {
      return !this.isPvpEnabled() ? false : super.shouldDamagePlayer(player);
   }

   private boolean isPvpEnabled() {
      return this.server.isPvpEnabled();
   }

   @Nullable
   @Override
   protected TeleportTarget getTeleportTarget(ServerWorld destination) {
      TeleportTarget _snowman = super.getTeleportTarget(destination);
      if (_snowman != null && this.world.getRegistryKey() == World.OVERWORLD && destination.getRegistryKey() == World.END) {
         Vec3d _snowmanx = _snowman.position.add(0.0, -1.0, 0.0);
         return new TeleportTarget(_snowmanx, Vec3d.ZERO, 90.0F, 0.0F);
      } else {
         return _snowman;
      }
   }

   @Nullable
   @Override
   public Entity moveToWorld(ServerWorld destination) {
      this.inTeleportationState = true;
      ServerWorld _snowman = this.getServerWorld();
      RegistryKey<World> _snowmanx = _snowman.getRegistryKey();
      if (_snowmanx == World.END && destination.getRegistryKey() == World.OVERWORLD) {
         this.detach();
         this.getServerWorld().removePlayer(this);
         if (!this.notInAnyWorld) {
            this.notInAnyWorld = true;
            this.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_WON, this.seenCredits ? 0.0F : 1.0F));
            this.seenCredits = true;
         }

         return this;
      } else {
         WorldProperties _snowmanxx = destination.getLevelProperties();
         this.networkHandler
            .sendPacket(
               new PlayerRespawnS2CPacket(
                  destination.getDimension(),
                  destination.getRegistryKey(),
                  BiomeAccess.hashSeed(destination.getSeed()),
                  this.interactionManager.getGameMode(),
                  this.interactionManager.getPreviousGameMode(),
                  destination.isDebugWorld(),
                  destination.isFlat(),
                  true
               )
            );
         this.networkHandler.sendPacket(new DifficultyS2CPacket(_snowmanxx.getDifficulty(), _snowmanxx.isDifficultyLocked()));
         PlayerManager _snowmanxxx = this.server.getPlayerManager();
         _snowmanxxx.sendCommandTree(this);
         _snowman.removePlayer(this);
         this.removed = false;
         TeleportTarget _snowmanxxxx = this.getTeleportTarget(destination);
         if (_snowmanxxxx != null) {
            _snowman.getProfiler().push("moving");
            if (_snowmanx == World.OVERWORLD && destination.getRegistryKey() == World.NETHER) {
               this.enteredNetherPos = this.getPos();
            } else if (destination.getRegistryKey() == World.END) {
               this.createEndSpawnPlatform(destination, new BlockPos(_snowmanxxxx.position));
            }

            _snowman.getProfiler().pop();
            _snowman.getProfiler().push("placing");
            this.setWorld(destination);
            destination.onPlayerChangeDimension(this);
            this.setRotation(_snowmanxxxx.yaw, _snowmanxxxx.pitch);
            this.refreshPositionAfterTeleport(_snowmanxxxx.position.x, _snowmanxxxx.position.y, _snowmanxxxx.position.z);
            _snowman.getProfiler().pop();
            this.worldChanged(_snowman);
            this.interactionManager.setWorld(destination);
            this.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(this.abilities));
            _snowmanxxx.sendWorldInfo(this, destination);
            _snowmanxxx.sendPlayerStatus(this);

            for (StatusEffectInstance _snowmanxxxxx : this.getStatusEffects()) {
               this.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(this.getEntityId(), _snowmanxxxxx));
            }

            this.networkHandler.sendPacket(new WorldEventS2CPacket(1032, BlockPos.ORIGIN, 0, false));
            this.syncedExperience = -1;
            this.syncedHealth = -1.0F;
            this.syncedFoodLevel = -1;
         }

         return this;
      }
   }

   private void createEndSpawnPlatform(ServerWorld world, BlockPos centerPos) {
      BlockPos.Mutable _snowman = centerPos.mutableCopy();

      for (int _snowmanx = -2; _snowmanx <= 2; _snowmanx++) {
         for (int _snowmanxx = -2; _snowmanxx <= 2; _snowmanxx++) {
            for (int _snowmanxxx = -1; _snowmanxxx < 3; _snowmanxxx++) {
               BlockState _snowmanxxxx = _snowmanxxx == -1 ? Blocks.OBSIDIAN.getDefaultState() : Blocks.AIR.getDefaultState();
               world.setBlockState(_snowman.set(centerPos).move(_snowmanxx, _snowmanxxx, _snowmanx), _snowmanxxxx);
            }
         }
      }
   }

   @Override
   protected Optional<class_5459.class_5460> method_30330(ServerWorld _snowman, BlockPos _snowman, boolean _snowman) {
      Optional<class_5459.class_5460> _snowmanxxx = super.method_30330(_snowman, _snowman, _snowman);
      if (_snowmanxxx.isPresent()) {
         return _snowmanxxx;
      } else {
         Direction.Axis _snowmanxxxx = this.world.getBlockState(this.lastNetherPortalPosition).method_28500(NetherPortalBlock.AXIS).orElse(Direction.Axis.X);
         Optional<class_5459.class_5460> _snowmanxxxxx = _snowman.getPortalForcer().method_30482(_snowman, _snowmanxxxx);
         if (!_snowmanxxxxx.isPresent()) {
            LOGGER.error("Unable to create a portal, likely target out of worldborder");
         }

         return _snowmanxxxxx;
      }
   }

   private void worldChanged(ServerWorld origin) {
      RegistryKey<World> _snowman = origin.getRegistryKey();
      RegistryKey<World> _snowmanx = this.world.getRegistryKey();
      Criteria.CHANGED_DIMENSION.trigger(this, _snowman, _snowmanx);
      if (_snowman == World.NETHER && _snowmanx == World.OVERWORLD && this.enteredNetherPos != null) {
         Criteria.NETHER_TRAVEL.trigger(this, this.enteredNetherPos);
      }

      if (_snowmanx != World.NETHER) {
         this.enteredNetherPos = null;
      }
   }

   @Override
   public boolean canBeSpectated(ServerPlayerEntity spectator) {
      if (spectator.isSpectator()) {
         return this.getCameraEntity() == this;
      } else {
         return this.isSpectator() ? false : super.canBeSpectated(spectator);
      }
   }

   private void sendBlockEntityUpdate(BlockEntity blockEntity) {
      if (blockEntity != null) {
         BlockEntityUpdateS2CPacket _snowman = blockEntity.toUpdatePacket();
         if (_snowman != null) {
            this.networkHandler.sendPacket(_snowman);
         }
      }
   }

   @Override
   public void sendPickup(Entity item, int count) {
      super.sendPickup(item, count);
      this.currentScreenHandler.sendContentUpdates();
   }

   @Override
   public Either<PlayerEntity.SleepFailureReason, Unit> trySleep(BlockPos pos) {
      Direction _snowman = this.world.getBlockState(pos).get(HorizontalFacingBlock.FACING);
      if (this.isSleeping() || !this.isAlive()) {
         return Either.left(PlayerEntity.SleepFailureReason.OTHER_PROBLEM);
      } else if (!this.world.getDimension().isNatural()) {
         return Either.left(PlayerEntity.SleepFailureReason.NOT_POSSIBLE_HERE);
      } else if (!this.isBedTooFarAway(pos, _snowman)) {
         return Either.left(PlayerEntity.SleepFailureReason.TOO_FAR_AWAY);
      } else if (this.isBedObstructed(pos, _snowman)) {
         return Either.left(PlayerEntity.SleepFailureReason.OBSTRUCTED);
      } else {
         this.setSpawnPoint(this.world.getRegistryKey(), pos, this.yaw, false, true);
         if (this.world.isDay()) {
            return Either.left(PlayerEntity.SleepFailureReason.NOT_POSSIBLE_NOW);
         } else {
            if (!this.isCreative()) {
               double _snowmanx = 8.0;
               double _snowmanxx = 5.0;
               Vec3d _snowmanxxx = Vec3d.ofBottomCenter(pos);
               List<HostileEntity> _snowmanxxxx = this.world
                  .getEntitiesByClass(
                     HostileEntity.class,
                     new Box(_snowmanxxx.getX() - 8.0, _snowmanxxx.getY() - 5.0, _snowmanxxx.getZ() - 8.0, _snowmanxxx.getX() + 8.0, _snowmanxxx.getY() + 5.0, _snowmanxxx.getZ() + 8.0),
                     _snowmanxxxxx -> _snowmanxxxxx.isAngryAt(this)
                  );
               if (!_snowmanxxxx.isEmpty()) {
                  return Either.left(PlayerEntity.SleepFailureReason.NOT_SAFE);
               }
            }

            Either<PlayerEntity.SleepFailureReason, Unit> _snowmanx = super.trySleep(pos).ifRight(_snowmanxx -> {
               this.incrementStat(Stats.SLEEP_IN_BED);
               Criteria.SLEPT_IN_BED.trigger(this);
            });
            ((ServerWorld)this.world).updateSleepingPlayers();
            return _snowmanx;
         }
      }
   }

   @Override
   public void sleep(BlockPos pos) {
      this.resetStat(Stats.CUSTOM.getOrCreateStat(Stats.TIME_SINCE_REST));
      super.sleep(pos);
   }

   private boolean isBedTooFarAway(BlockPos pos, Direction direction) {
      return this.isBedTooFarAway(pos) || this.isBedTooFarAway(pos.offset(direction.getOpposite()));
   }

   private boolean isBedTooFarAway(BlockPos pos) {
      Vec3d _snowman = Vec3d.ofBottomCenter(pos);
      return Math.abs(this.getX() - _snowman.getX()) <= 3.0 && Math.abs(this.getY() - _snowman.getY()) <= 2.0 && Math.abs(this.getZ() - _snowman.getZ()) <= 3.0;
   }

   private boolean isBedObstructed(BlockPos pos, Direction direction) {
      BlockPos _snowman = pos.up();
      return !this.doesNotSuffocate(_snowman) || !this.doesNotSuffocate(_snowman.offset(direction.getOpposite()));
   }

   @Override
   public void wakeUp(boolean _snowman, boolean updateSleepingPlayers) {
      if (this.isSleeping()) {
         this.getServerWorld().getChunkManager().sendToNearbyPlayers(this, new EntityAnimationS2CPacket(this, 2));
      }

      super.wakeUp(_snowman, updateSleepingPlayers);
      if (this.networkHandler != null) {
         this.networkHandler.requestTeleport(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
      }
   }

   @Override
   public boolean startRiding(Entity entity, boolean force) {
      Entity _snowman = this.getVehicle();
      if (!super.startRiding(entity, force)) {
         return false;
      } else {
         Entity _snowmanx = this.getVehicle();
         if (_snowmanx != _snowman && this.networkHandler != null) {
            this.networkHandler.requestTeleport(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
         }

         return true;
      }
   }

   @Override
   public void stopRiding() {
      Entity _snowman = this.getVehicle();
      super.stopRiding();
      Entity _snowmanx = this.getVehicle();
      if (_snowmanx != _snowman && this.networkHandler != null) {
         this.networkHandler.requestTeleport(this.getX(), this.getY(), this.getZ(), this.yaw, this.pitch);
      }
   }

   @Override
   public boolean isInvulnerableTo(DamageSource damageSource) {
      return super.isInvulnerableTo(damageSource) || this.isInTeleportationState() || this.abilities.invulnerable && damageSource == DamageSource.WITHER;
   }

   @Override
   protected void fall(double heightDifference, boolean onGround, BlockState landedState, BlockPos landedPosition) {
   }

   @Override
   protected void applyMovementEffects(BlockPos pos) {
      if (!this.isSpectator()) {
         super.applyMovementEffects(pos);
      }
   }

   public void handleFall(double heightDifference, boolean onGround) {
      BlockPos _snowman = this.getLandingPos();
      if (this.world.isChunkLoaded(_snowman)) {
         super.fall(heightDifference, onGround, this.world.getBlockState(_snowman), _snowman);
      }
   }

   @Override
   public void openEditSignScreen(SignBlockEntity sign) {
      sign.setEditor(this);
      this.networkHandler.sendPacket(new SignEditorOpenS2CPacket(sign.getPos()));
   }

   private void incrementScreenHandlerSyncId() {
      this.screenHandlerSyncId = this.screenHandlerSyncId % 100 + 1;
   }

   @Override
   public OptionalInt openHandledScreen(@Nullable NamedScreenHandlerFactory factory) {
      if (factory == null) {
         return OptionalInt.empty();
      } else {
         if (this.currentScreenHandler != this.playerScreenHandler) {
            this.closeHandledScreen();
         }

         this.incrementScreenHandlerSyncId();
         ScreenHandler _snowman = factory.createMenu(this.screenHandlerSyncId, this.inventory, this);
         if (_snowman == null) {
            if (this.isSpectator()) {
               this.sendMessage(new TranslatableText("container.spectatorCantOpen").formatted(Formatting.RED), true);
            }

            return OptionalInt.empty();
         } else {
            this.networkHandler.sendPacket(new OpenScreenS2CPacket(_snowman.syncId, _snowman.getType(), factory.getDisplayName()));
            _snowman.addListener(this);
            this.currentScreenHandler = _snowman;
            return OptionalInt.of(this.screenHandlerSyncId);
         }
      }
   }

   @Override
   public void sendTradeOffers(int syncId, TradeOfferList offers, int levelProgress, int experience, boolean leveled, boolean refreshable) {
      this.networkHandler.sendPacket(new SetTradeOffersS2CPacket(syncId, offers, levelProgress, experience, leveled, refreshable));
   }

   @Override
   public void openHorseInventory(HorseBaseEntity horse, Inventory inventory) {
      if (this.currentScreenHandler != this.playerScreenHandler) {
         this.closeHandledScreen();
      }

      this.incrementScreenHandlerSyncId();
      this.networkHandler.sendPacket(new OpenHorseScreenS2CPacket(this.screenHandlerSyncId, inventory.size(), horse.getEntityId()));
      this.currentScreenHandler = new HorseScreenHandler(this.screenHandlerSyncId, this.inventory, inventory, horse);
      this.currentScreenHandler.addListener(this);
   }

   @Override
   public void openEditBookScreen(ItemStack book, Hand hand) {
      Item _snowman = book.getItem();
      if (_snowman == Items.WRITTEN_BOOK) {
         if (WrittenBookItem.resolve(book, this.getCommandSource(), this)) {
            this.currentScreenHandler.sendContentUpdates();
         }

         this.networkHandler.sendPacket(new OpenWrittenBookS2CPacket(hand));
      }
   }

   @Override
   public void openCommandBlockScreen(CommandBlockBlockEntity commandBlock) {
      commandBlock.setNeedsUpdatePacket(true);
      this.sendBlockEntityUpdate(commandBlock);
   }

   @Override
   public void onSlotUpdate(ScreenHandler handler, int slotId, ItemStack stack) {
      if (!(handler.getSlot(slotId) instanceof CraftingResultSlot)) {
         if (handler == this.playerScreenHandler) {
            Criteria.INVENTORY_CHANGED.trigger(this, this.inventory, stack);
         }

         if (!this.skipPacketSlotUpdates) {
            this.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, slotId, stack));
         }
      }
   }

   public void refreshScreenHandler(ScreenHandler handler) {
      this.onHandlerRegistered(handler, handler.getStacks());
   }

   @Override
   public void onHandlerRegistered(ScreenHandler handler, DefaultedList<ItemStack> stacks) {
      this.networkHandler.sendPacket(new InventoryS2CPacket(handler.syncId, stacks));
      this.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-1, -1, this.inventory.getCursorStack()));
   }

   @Override
   public void onPropertyUpdate(ScreenHandler handler, int property, int value) {
      this.networkHandler.sendPacket(new ScreenHandlerPropertyUpdateS2CPacket(handler.syncId, property, value));
   }

   @Override
   public void closeHandledScreen() {
      this.networkHandler.sendPacket(new CloseScreenS2CPacket(this.currentScreenHandler.syncId));
      this.closeScreenHandler();
   }

   public void updateCursorStack() {
      if (!this.skipPacketSlotUpdates) {
         this.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(-1, -1, this.inventory.getCursorStack()));
      }
   }

   public void closeScreenHandler() {
      this.currentScreenHandler.close(this);
      this.currentScreenHandler = this.playerScreenHandler;
   }

   public void method_14218(float _snowman, float _snowman, boolean _snowman, boolean _snowman) {
      if (this.hasVehicle()) {
         if (_snowman >= -1.0F && _snowman <= 1.0F) {
            this.sidewaysSpeed = _snowman;
         }

         if (_snowman >= -1.0F && _snowman <= 1.0F) {
            this.forwardSpeed = _snowman;
         }

         this.jumping = _snowman;
         this.setSneaking(_snowman);
      }
   }

   @Override
   public void increaseStat(Stat<?> stat, int amount) {
      this.statHandler.increaseStat(this, stat, amount);
      this.getScoreboard().forEachScore(stat, this.getEntityName(), _snowmanx -> _snowmanx.incrementScore(amount));
   }

   @Override
   public void resetStat(Stat<?> stat) {
      this.statHandler.setStat(this, stat, 0);
      this.getScoreboard().forEachScore(stat, this.getEntityName(), ScoreboardPlayerScore::clearScore);
   }

   @Override
   public int unlockRecipes(Collection<Recipe<?>> recipes) {
      return this.recipeBook.unlockRecipes(recipes, this);
   }

   @Override
   public void unlockRecipes(Identifier[] ids) {
      List<Recipe<?>> _snowman = Lists.newArrayList();

      for (Identifier _snowmanx : ids) {
         this.server.getRecipeManager().get(_snowmanx).ifPresent(_snowman::add);
      }

      this.unlockRecipes(_snowman);
   }

   @Override
   public int lockRecipes(Collection<Recipe<?>> recipes) {
      return this.recipeBook.lockRecipes(recipes, this);
   }

   @Override
   public void addExperience(int experience) {
      super.addExperience(experience);
      this.syncedExperience = -1;
   }

   public void onDisconnect() {
      this.disconnected = true;
      this.removeAllPassengers();
      if (this.isSleeping()) {
         this.wakeUp(true, false);
      }
   }

   public boolean isDisconnected() {
      return this.disconnected;
   }

   public void markHealthDirty() {
      this.syncedHealth = -1.0E8F;
   }

   @Override
   public void sendMessage(Text message, boolean actionBar) {
      this.networkHandler.sendPacket(new GameMessageS2CPacket(message, actionBar ? MessageType.GAME_INFO : MessageType.CHAT, Util.NIL_UUID));
   }

   @Override
   protected void consumeItem() {
      if (!this.activeItemStack.isEmpty() && this.isUsingItem()) {
         this.networkHandler.sendPacket(new EntityStatusS2CPacket(this, (byte)9));
         super.consumeItem();
      }
   }

   @Override
   public void lookAt(EntityAnchorArgumentType.EntityAnchor anchorPoint, Vec3d target) {
      super.lookAt(anchorPoint, target);
      this.networkHandler.sendPacket(new LookAtS2CPacket(anchorPoint, target.x, target.y, target.z));
   }

   public void method_14222(EntityAnchorArgumentType.EntityAnchor _snowman, Entity _snowman, EntityAnchorArgumentType.EntityAnchor _snowman) {
      Vec3d _snowmanxxx = _snowman.positionAt(_snowman);
      super.lookAt(_snowman, _snowmanxxx);
      this.networkHandler.sendPacket(new LookAtS2CPacket(_snowman, _snowman, _snowman));
   }

   public void copyFrom(ServerPlayerEntity oldPlayer, boolean alive) {
      if (alive) {
         this.inventory.clone(oldPlayer.inventory);
         this.setHealth(oldPlayer.getHealth());
         this.hungerManager = oldPlayer.hungerManager;
         this.experienceLevel = oldPlayer.experienceLevel;
         this.totalExperience = oldPlayer.totalExperience;
         this.experienceProgress = oldPlayer.experienceProgress;
         this.setScore(oldPlayer.getScore());
         this.lastNetherPortalPosition = oldPlayer.lastNetherPortalPosition;
      } else if (this.world.getGameRules().getBoolean(GameRules.KEEP_INVENTORY) || oldPlayer.isSpectator()) {
         this.inventory.clone(oldPlayer.inventory);
         this.experienceLevel = oldPlayer.experienceLevel;
         this.totalExperience = oldPlayer.totalExperience;
         this.experienceProgress = oldPlayer.experienceProgress;
         this.setScore(oldPlayer.getScore());
      }

      this.enchantmentTableSeed = oldPlayer.enchantmentTableSeed;
      this.enderChestInventory = oldPlayer.enderChestInventory;
      this.getDataTracker().set(PLAYER_MODEL_PARTS, oldPlayer.getDataTracker().get(PLAYER_MODEL_PARTS));
      this.syncedExperience = -1;
      this.syncedHealth = -1.0F;
      this.syncedFoodLevel = -1;
      this.recipeBook.copyFrom(oldPlayer.recipeBook);
      this.removedEntities.addAll(oldPlayer.removedEntities);
      this.seenCredits = oldPlayer.seenCredits;
      this.enteredNetherPos = oldPlayer.enteredNetherPos;
      this.setShoulderEntityLeft(oldPlayer.getShoulderEntityLeft());
      this.setShoulderEntityRight(oldPlayer.getShoulderEntityRight());
   }

   @Override
   protected void onStatusEffectApplied(StatusEffectInstance effect) {
      super.onStatusEffectApplied(effect);
      this.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(this.getEntityId(), effect));
      if (effect.getEffectType() == StatusEffects.LEVITATION) {
         this.levitationStartTick = this.age;
         this.levitationStartPos = this.getPos();
      }

      Criteria.EFFECTS_CHANGED.trigger(this);
   }

   @Override
   protected void onStatusEffectUpgraded(StatusEffectInstance effect, boolean reapplyEffect) {
      super.onStatusEffectUpgraded(effect, reapplyEffect);
      this.networkHandler.sendPacket(new EntityStatusEffectS2CPacket(this.getEntityId(), effect));
      Criteria.EFFECTS_CHANGED.trigger(this);
   }

   @Override
   protected void onStatusEffectRemoved(StatusEffectInstance effect) {
      super.onStatusEffectRemoved(effect);
      this.networkHandler.sendPacket(new RemoveEntityStatusEffectS2CPacket(this.getEntityId(), effect.getEffectType()));
      if (effect.getEffectType() == StatusEffects.LEVITATION) {
         this.levitationStartPos = null;
      }

      Criteria.EFFECTS_CHANGED.trigger(this);
   }

   @Override
   public void requestTeleport(double destX, double destY, double destZ) {
      this.networkHandler.requestTeleport(destX, destY, destZ, this.yaw, this.pitch);
   }

   @Override
   public void refreshPositionAfterTeleport(double x, double y, double z) {
      this.requestTeleport(x, y, z);
      this.networkHandler.syncWithPlayerPosition();
   }

   @Override
   public void addCritParticles(Entity target) {
      this.getServerWorld().getChunkManager().sendToNearbyPlayers(this, new EntityAnimationS2CPacket(target, 4));
   }

   @Override
   public void addEnchantedHitParticles(Entity target) {
      this.getServerWorld().getChunkManager().sendToNearbyPlayers(this, new EntityAnimationS2CPacket(target, 5));
   }

   @Override
   public void sendAbilitiesUpdate() {
      if (this.networkHandler != null) {
         this.networkHandler.sendPacket(new PlayerAbilitiesS2CPacket(this.abilities));
         this.updatePotionVisibility();
      }
   }

   public ServerWorld getServerWorld() {
      return (ServerWorld)this.world;
   }

   @Override
   public void setGameMode(GameMode gameMode) {
      this.interactionManager.setGameMode(gameMode);
      this.networkHandler.sendPacket(new GameStateChangeS2CPacket(GameStateChangeS2CPacket.GAME_MODE_CHANGED, (float)gameMode.getId()));
      if (gameMode == GameMode.SPECTATOR) {
         this.dropShoulderEntities();
         this.stopRiding();
      } else {
         this.setCameraEntity(this);
      }

      this.sendAbilitiesUpdate();
      this.markEffectsDirty();
   }

   @Override
   public boolean isSpectator() {
      return this.interactionManager.getGameMode() == GameMode.SPECTATOR;
   }

   @Override
   public boolean isCreative() {
      return this.interactionManager.getGameMode() == GameMode.CREATIVE;
   }

   @Override
   public void sendSystemMessage(Text message, UUID senderUuid) {
      this.sendMessage(message, MessageType.SYSTEM, senderUuid);
   }

   public void sendMessage(Text message, MessageType type, UUID senderUuid) {
      this.networkHandler
         .sendPacket(
            new GameMessageS2CPacket(message, type, senderUuid),
            _snowmanxxx -> {
               if (!_snowmanxxx.isSuccess() && (type == MessageType.GAME_INFO || type == MessageType.SYSTEM)) {
                  int _snowmanxxxx = 256;
                  String _snowmanx = message.asTruncatedString(256);
                  Text _snowmanxx = new LiteralText(_snowmanx).formatted(Formatting.YELLOW);
                  this.networkHandler
                     .sendPacket(
                        new GameMessageS2CPacket(
                           new TranslatableText("multiplayer.message_not_delivered", _snowmanxx).formatted(Formatting.RED), MessageType.SYSTEM, senderUuid
                        )
                     );
               }
            }
         );
   }

   public String getIp() {
      String _snowman = this.networkHandler.connection.getAddress().toString();
      _snowman = _snowman.substring(_snowman.indexOf("/") + 1);
      return _snowman.substring(0, _snowman.indexOf(":"));
   }

   public void setClientSettings(ClientSettingsC2SPacket packet) {
      this.clientChatVisibility = packet.getChatVisibility();
      this.clientChatColorsEnabled = packet.hasChatColors();
      this.getDataTracker().set(PLAYER_MODEL_PARTS, (byte)packet.getPlayerModelBitMask());
      this.getDataTracker().set(MAIN_ARM, (byte)(packet.getMainArm() == Arm.LEFT ? 0 : 1));
   }

   public ChatVisibility getClientChatVisibility() {
      return this.clientChatVisibility;
   }

   public void sendResourcePackUrl(String url, String hash) {
      this.networkHandler.sendPacket(new ResourcePackSendS2CPacket(url, hash));
   }

   @Override
   protected int getPermissionLevel() {
      return this.server.getPermissionLevel(this.getGameProfile());
   }

   public void updateLastActionTime() {
      this.lastActionTime = Util.getMeasuringTimeMs();
   }

   public ServerStatHandler getStatHandler() {
      return this.statHandler;
   }

   public ServerRecipeBook getRecipeBook() {
      return this.recipeBook;
   }

   public void onStoppedTracking(Entity entity) {
      if (entity instanceof PlayerEntity) {
         this.networkHandler.sendPacket(new EntitiesDestroyS2CPacket(entity.getEntityId()));
      } else {
         this.removedEntities.add(entity.getEntityId());
      }
   }

   public void onStartedTracking(Entity entity) {
      this.removedEntities.remove(Integer.valueOf(entity.getEntityId()));
   }

   @Override
   protected void updatePotionVisibility() {
      if (this.isSpectator()) {
         this.clearPotionSwirls();
         this.setInvisible(true);
      } else {
         super.updatePotionVisibility();
      }
   }

   public Entity getCameraEntity() {
      return (Entity)(this.cameraEntity == null ? this : this.cameraEntity);
   }

   public void setCameraEntity(Entity entity) {
      Entity _snowman = this.getCameraEntity();
      this.cameraEntity = (Entity)(entity == null ? this : entity);
      if (_snowman != this.cameraEntity) {
         this.networkHandler.sendPacket(new SetCameraEntityS2CPacket(this.cameraEntity));
         this.requestTeleport(this.cameraEntity.getX(), this.cameraEntity.getY(), this.cameraEntity.getZ());
      }
   }

   @Override
   protected void tickNetherPortalCooldown() {
      if (!this.inTeleportationState) {
         super.tickNetherPortalCooldown();
      }
   }

   @Override
   public void attack(Entity target) {
      if (this.interactionManager.getGameMode() == GameMode.SPECTATOR) {
         this.setCameraEntity(target);
      } else {
         super.attack(target);
      }
   }

   public long getLastActionTime() {
      return this.lastActionTime;
   }

   @Nullable
   public Text getPlayerListName() {
      return null;
   }

   @Override
   public void swingHand(Hand hand) {
      super.swingHand(hand);
      this.resetLastAttackedTicks();
   }

   public boolean isInTeleportationState() {
      return this.inTeleportationState;
   }

   public void onTeleportationDone() {
      this.inTeleportationState = false;
   }

   public PlayerAdvancementTracker getAdvancementTracker() {
      return this.advancementTracker;
   }

   public void teleport(ServerWorld targetWorld, double x, double y, double z, float yaw, float pitch) {
      this.setCameraEntity(this);
      this.stopRiding();
      if (targetWorld == this.world) {
         this.networkHandler.requestTeleport(x, y, z, yaw, pitch);
      } else {
         ServerWorld _snowman = this.getServerWorld();
         WorldProperties _snowmanx = targetWorld.getLevelProperties();
         this.networkHandler
            .sendPacket(
               new PlayerRespawnS2CPacket(
                  targetWorld.getDimension(),
                  targetWorld.getRegistryKey(),
                  BiomeAccess.hashSeed(targetWorld.getSeed()),
                  this.interactionManager.getGameMode(),
                  this.interactionManager.getPreviousGameMode(),
                  targetWorld.isDebugWorld(),
                  targetWorld.isFlat(),
                  true
               )
            );
         this.networkHandler.sendPacket(new DifficultyS2CPacket(_snowmanx.getDifficulty(), _snowmanx.isDifficultyLocked()));
         this.server.getPlayerManager().sendCommandTree(this);
         _snowman.removePlayer(this);
         this.removed = false;
         this.refreshPositionAndAngles(x, y, z, yaw, pitch);
         this.setWorld(targetWorld);
         targetWorld.onPlayerTeleport(this);
         this.worldChanged(_snowman);
         this.networkHandler.requestTeleport(x, y, z, yaw, pitch);
         this.interactionManager.setWorld(targetWorld);
         this.server.getPlayerManager().sendWorldInfo(this, targetWorld);
         this.server.getPlayerManager().sendPlayerStatus(this);
      }
   }

   @Nullable
   public BlockPos getSpawnPointPosition() {
      return this.spawnPointPosition;
   }

   public float getSpawnAngle() {
      return this.spawnAngle;
   }

   public RegistryKey<World> getSpawnPointDimension() {
      return this.spawnPointDimension;
   }

   public boolean isSpawnPointSet() {
      return this.spawnPointSet;
   }

   public void setSpawnPoint(RegistryKey<World> dimension, @Nullable BlockPos pos, float angle, boolean spawnPointSet, boolean _snowman) {
      if (pos != null) {
         boolean _snowmanx = pos.equals(this.spawnPointPosition) && dimension.equals(this.spawnPointDimension);
         if (_snowman && !_snowmanx) {
            this.sendSystemMessage(new TranslatableText("block.minecraft.set_spawn"), Util.NIL_UUID);
         }

         this.spawnPointPosition = pos;
         this.spawnPointDimension = dimension;
         this.spawnAngle = angle;
         this.spawnPointSet = spawnPointSet;
      } else {
         this.spawnPointPosition = null;
         this.spawnPointDimension = World.OVERWORLD;
         this.spawnAngle = 0.0F;
         this.spawnPointSet = false;
      }
   }

   public void sendInitialChunkPackets(ChunkPos _snowman, Packet<?> _snowman, Packet<?> _snowman) {
      this.networkHandler.sendPacket(_snowman);
      this.networkHandler.sendPacket(_snowman);
   }

   public void sendUnloadChunkPacket(ChunkPos _snowman) {
      if (this.isAlive()) {
         this.networkHandler.sendPacket(new UnloadChunkS2CPacket(_snowman.x, _snowman.z));
      }
   }

   public ChunkSectionPos getCameraPosition() {
      return this.cameraPosition;
   }

   public void setCameraPosition(ChunkSectionPos cameraPosition) {
      this.cameraPosition = cameraPosition;
   }

   @Override
   public void playSound(SoundEvent event, SoundCategory category, float volume, float pitch) {
      this.networkHandler.sendPacket(new PlaySoundS2CPacket(event, category, this.getX(), this.getY(), this.getZ(), volume, pitch));
   }

   @Override
   public Packet<?> createSpawnPacket() {
      return new PlayerSpawnS2CPacket(this);
   }

   @Override
   public ItemEntity dropItem(ItemStack stack, boolean throwRandomly, boolean retainOwnership) {
      ItemEntity _snowman = super.dropItem(stack, throwRandomly, retainOwnership);
      if (_snowman == null) {
         return null;
      } else {
         this.world.spawnEntity(_snowman);
         ItemStack _snowmanx = _snowman.getStack();
         if (retainOwnership) {
            if (!_snowmanx.isEmpty()) {
               this.increaseStat(Stats.DROPPED.getOrCreateStat(_snowmanx.getItem()), stack.getCount());
            }

            this.incrementStat(Stats.DROP);
         }

         return _snowman;
      }
   }

   @Nullable
   public TextStream getTextStream() {
      return this.textStream;
   }
}
