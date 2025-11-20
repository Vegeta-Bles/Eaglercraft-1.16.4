package net.minecraft.entity.passive;

import com.google.common.collect.Maps;
import java.util.Arrays;
import java.util.Map;
import java.util.Random;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.entity.EntityData;
import net.minecraft.entity.EntityDimensions;
import net.minecraft.entity.EntityPose;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.Shearable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.ai.goal.AnimalMateGoal;
import net.minecraft.entity.ai.goal.EatGrassGoal;
import net.minecraft.entity.ai.goal.EscapeDangerGoal;
import net.minecraft.entity.ai.goal.FollowParentGoal;
import net.minecraft.entity.ai.goal.LookAroundGoal;
import net.minecraft.entity.ai.goal.LookAtEntityGoal;
import net.minecraft.entity.ai.goal.SwimGoal;
import net.minecraft.entity.ai.goal.TemptGoal;
import net.minecraft.entity.ai.goal.WanderAroundFarGoal;
import net.minecraft.entity.attribute.DefaultAttributeContainer;
import net.minecraft.entity.attribute.EntityAttributes;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.entity.mob.MobEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.DyeItem;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.loot.LootTables;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Hand;
import net.minecraft.util.Identifier;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.LocalDifficulty;
import net.minecraft.world.ServerWorldAccess;
import net.minecraft.world.World;

public class SheepEntity extends AnimalEntity implements Shearable {
   private static final TrackedData<Byte> COLOR = DataTracker.registerData(SheepEntity.class, TrackedDataHandlerRegistry.BYTE);
   private static final Map<DyeColor, ItemConvertible> DROPS = Util.make(Maps.newEnumMap(DyeColor.class), _snowman -> {
      _snowman.put(DyeColor.WHITE, Blocks.WHITE_WOOL);
      _snowman.put(DyeColor.ORANGE, Blocks.ORANGE_WOOL);
      _snowman.put(DyeColor.MAGENTA, Blocks.MAGENTA_WOOL);
      _snowman.put(DyeColor.LIGHT_BLUE, Blocks.LIGHT_BLUE_WOOL);
      _snowman.put(DyeColor.YELLOW, Blocks.YELLOW_WOOL);
      _snowman.put(DyeColor.LIME, Blocks.LIME_WOOL);
      _snowman.put(DyeColor.PINK, Blocks.PINK_WOOL);
      _snowman.put(DyeColor.GRAY, Blocks.GRAY_WOOL);
      _snowman.put(DyeColor.LIGHT_GRAY, Blocks.LIGHT_GRAY_WOOL);
      _snowman.put(DyeColor.CYAN, Blocks.CYAN_WOOL);
      _snowman.put(DyeColor.PURPLE, Blocks.PURPLE_WOOL);
      _snowman.put(DyeColor.BLUE, Blocks.BLUE_WOOL);
      _snowman.put(DyeColor.BROWN, Blocks.BROWN_WOOL);
      _snowman.put(DyeColor.GREEN, Blocks.GREEN_WOOL);
      _snowman.put(DyeColor.RED, Blocks.RED_WOOL);
      _snowman.put(DyeColor.BLACK, Blocks.BLACK_WOOL);
   });
   private static final Map<DyeColor, float[]> COLORS = Maps.newEnumMap(
      Arrays.stream(DyeColor.values()).collect(Collectors.toMap(_snowman -> (DyeColor)_snowman, SheepEntity::getDyedColor))
   );
   private int eatGrassTimer;
   private EatGrassGoal eatGrassGoal;

   private static float[] getDyedColor(DyeColor color) {
      if (color == DyeColor.WHITE) {
         return new float[]{0.9019608F, 0.9019608F, 0.9019608F};
      } else {
         float[] _snowman = color.getColorComponents();
         float _snowmanx = 0.75F;
         return new float[]{_snowman[0] * 0.75F, _snowman[1] * 0.75F, _snowman[2] * 0.75F};
      }
   }

   public static float[] getRgbColor(DyeColor dyeColor) {
      return COLORS.get(dyeColor);
   }

   public SheepEntity(EntityType<? extends SheepEntity> _snowman, World _snowman) {
      super(_snowman, _snowman);
   }

   @Override
   protected void initGoals() {
      this.eatGrassGoal = new EatGrassGoal(this);
      this.goalSelector.add(0, new SwimGoal(this));
      this.goalSelector.add(1, new EscapeDangerGoal(this, 1.25));
      this.goalSelector.add(2, new AnimalMateGoal(this, 1.0));
      this.goalSelector.add(3, new TemptGoal(this, 1.1, Ingredient.ofItems(Items.WHEAT), false));
      this.goalSelector.add(4, new FollowParentGoal(this, 1.1));
      this.goalSelector.add(5, this.eatGrassGoal);
      this.goalSelector.add(6, new WanderAroundFarGoal(this, 1.0));
      this.goalSelector.add(7, new LookAtEntityGoal(this, PlayerEntity.class, 6.0F));
      this.goalSelector.add(8, new LookAroundGoal(this));
   }

   @Override
   protected void mobTick() {
      this.eatGrassTimer = this.eatGrassGoal.getTimer();
      super.mobTick();
   }

   @Override
   public void tickMovement() {
      if (this.world.isClient) {
         this.eatGrassTimer = Math.max(0, this.eatGrassTimer - 1);
      }

      super.tickMovement();
   }

   public static DefaultAttributeContainer.Builder createSheepAttributes() {
      return MobEntity.createMobAttributes().add(EntityAttributes.GENERIC_MAX_HEALTH, 8.0).add(EntityAttributes.GENERIC_MOVEMENT_SPEED, 0.23F);
   }

   @Override
   protected void initDataTracker() {
      super.initDataTracker();
      this.dataTracker.startTracking(COLOR, (byte)0);
   }

   @Override
   public Identifier getLootTableId() {
      if (this.isSheared()) {
         return this.getType().getLootTableId();
      } else {
         switch (this.getColor()) {
            case WHITE:
            default:
               return LootTables.WHITE_SHEEP_ENTITY;
            case ORANGE:
               return LootTables.ORANGE_SHEEP_ENTITY;
            case MAGENTA:
               return LootTables.MAGENTA_SHEEP_ENTITY;
            case LIGHT_BLUE:
               return LootTables.LIGHT_BLUE_SHEEP_ENTITY;
            case YELLOW:
               return LootTables.YELLOW_SHEEP_ENTITY;
            case LIME:
               return LootTables.LIME_SHEEP_ENTITY;
            case PINK:
               return LootTables.PINK_SHEEP_ENTITY;
            case GRAY:
               return LootTables.GRAY_SHEEP_ENTITY;
            case LIGHT_GRAY:
               return LootTables.LIGHT_GRAY_SHEEP_ENTITY;
            case CYAN:
               return LootTables.CYAN_SHEEP_ENTITY;
            case PURPLE:
               return LootTables.PURPLE_SHEEP_ENTITY;
            case BLUE:
               return LootTables.BLUE_SHEEP_ENTITY;
            case BROWN:
               return LootTables.BROWN_SHEEP_ENTITY;
            case GREEN:
               return LootTables.GREEN_SHEEP_ENTITY;
            case RED:
               return LootTables.RED_SHEEP_ENTITY;
            case BLACK:
               return LootTables.BLACK_SHEEP_ENTITY;
         }
      }
   }

   @Override
   public void handleStatus(byte status) {
      if (status == 10) {
         this.eatGrassTimer = 40;
      } else {
         super.handleStatus(status);
      }
   }

   public float getNeckAngle(float delta) {
      if (this.eatGrassTimer <= 0) {
         return 0.0F;
      } else if (this.eatGrassTimer >= 4 && this.eatGrassTimer <= 36) {
         return 1.0F;
      } else {
         return this.eatGrassTimer < 4 ? ((float)this.eatGrassTimer - delta) / 4.0F : -((float)(this.eatGrassTimer - 40) - delta) / 4.0F;
      }
   }

   public float getHeadAngle(float delta) {
      if (this.eatGrassTimer > 4 && this.eatGrassTimer <= 36) {
         float _snowman = ((float)(this.eatGrassTimer - 4) - delta) / 32.0F;
         return (float) (Math.PI / 5) + 0.21991149F * MathHelper.sin(_snowman * 28.7F);
      } else {
         return this.eatGrassTimer > 0 ? (float) (Math.PI / 5) : this.pitch * (float) (Math.PI / 180.0);
      }
   }

   @Override
   public ActionResult interactMob(PlayerEntity player, Hand hand) {
      ItemStack _snowman = player.getStackInHand(hand);
      if (_snowman.getItem() == Items.SHEARS) {
         if (!this.world.isClient && this.isShearable()) {
            this.sheared(SoundCategory.PLAYERS);
            _snowman.damage(1, player, _snowmanx -> _snowmanx.sendToolBreakStatus(hand));
            return ActionResult.SUCCESS;
         } else {
            return ActionResult.CONSUME;
         }
      } else {
         return super.interactMob(player, hand);
      }
   }

   @Override
   public void sheared(SoundCategory shearedSoundCategory) {
      this.world.playSoundFromEntity(null, this, SoundEvents.ENTITY_SHEEP_SHEAR, shearedSoundCategory, 1.0F, 1.0F);
      this.setSheared(true);
      int _snowman = 1 + this.random.nextInt(3);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         ItemEntity _snowmanxx = this.dropItem(DROPS.get(this.getColor()), 1);
         if (_snowmanxx != null) {
            _snowmanxx.setVelocity(
               _snowmanxx.getVelocity()
                  .add(
                     (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F),
                     (double)(this.random.nextFloat() * 0.05F),
                     (double)((this.random.nextFloat() - this.random.nextFloat()) * 0.1F)
                  )
            );
         }
      }
   }

   @Override
   public boolean isShearable() {
      return this.isAlive() && !this.isSheared() && !this.isBaby();
   }

   @Override
   public void writeCustomDataToTag(CompoundTag tag) {
      super.writeCustomDataToTag(tag);
      tag.putBoolean("Sheared", this.isSheared());
      tag.putByte("Color", (byte)this.getColor().getId());
   }

   @Override
   public void readCustomDataFromTag(CompoundTag tag) {
      super.readCustomDataFromTag(tag);
      this.setSheared(tag.getBoolean("Sheared"));
      this.setColor(DyeColor.byId(tag.getByte("Color")));
   }

   @Override
   protected SoundEvent getAmbientSound() {
      return SoundEvents.ENTITY_SHEEP_AMBIENT;
   }

   @Override
   protected SoundEvent getHurtSound(DamageSource source) {
      return SoundEvents.ENTITY_SHEEP_HURT;
   }

   @Override
   protected SoundEvent getDeathSound() {
      return SoundEvents.ENTITY_SHEEP_DEATH;
   }

   @Override
   protected void playStepSound(BlockPos pos, BlockState state) {
      this.playSound(SoundEvents.ENTITY_SHEEP_STEP, 0.15F, 1.0F);
   }

   public DyeColor getColor() {
      return DyeColor.byId(this.dataTracker.get(COLOR) & 15);
   }

   public void setColor(DyeColor color) {
      byte _snowman = this.dataTracker.get(COLOR);
      this.dataTracker.set(COLOR, (byte)(_snowman & 240 | color.getId() & 15));
   }

   public boolean isSheared() {
      return (this.dataTracker.get(COLOR) & 16) != 0;
   }

   public void setSheared(boolean sheared) {
      byte _snowman = this.dataTracker.get(COLOR);
      if (sheared) {
         this.dataTracker.set(COLOR, (byte)(_snowman | 16));
      } else {
         this.dataTracker.set(COLOR, (byte)(_snowman & -17));
      }
   }

   public static DyeColor generateDefaultColor(Random _snowman) {
      int _snowmanx = _snowman.nextInt(100);
      if (_snowmanx < 5) {
         return DyeColor.BLACK;
      } else if (_snowmanx < 10) {
         return DyeColor.GRAY;
      } else if (_snowmanx < 15) {
         return DyeColor.LIGHT_GRAY;
      } else if (_snowmanx < 18) {
         return DyeColor.BROWN;
      } else {
         return _snowman.nextInt(500) == 0 ? DyeColor.PINK : DyeColor.WHITE;
      }
   }

   public SheepEntity createChild(ServerWorld _snowman, PassiveEntity _snowman) {
      SheepEntity _snowmanxx = (SheepEntity)_snowman;
      SheepEntity _snowmanxxx = EntityType.SHEEP.create(_snowman);
      _snowmanxxx.setColor(this.getChildColor(this, _snowmanxx));
      return _snowmanxxx;
   }

   @Override
   public void onEatingGrass() {
      this.setSheared(false);
      if (this.isBaby()) {
         this.growUp(60);
      }
   }

   @Nullable
   @Override
   public EntityData initialize(
      ServerWorldAccess world, LocalDifficulty difficulty, SpawnReason spawnReason, @Nullable EntityData entityData, @Nullable CompoundTag entityTag
   ) {
      this.setColor(generateDefaultColor(world.getRandom()));
      return super.initialize(world, difficulty, spawnReason, entityData, entityTag);
   }

   private DyeColor getChildColor(AnimalEntity firstParent, AnimalEntity secondParent) {
      DyeColor _snowman = ((SheepEntity)firstParent).getColor();
      DyeColor _snowmanx = ((SheepEntity)secondParent).getColor();
      CraftingInventory _snowmanxx = createDyeMixingCraftingInventory(_snowman, _snowmanx);
      return this.world
         .getRecipeManager()
         .getFirstMatch(RecipeType.CRAFTING, _snowmanxx, this.world)
         .map(_snowmanxxx -> _snowmanxxx.craft(_snowman))
         .map(ItemStack::getItem)
         .filter(DyeItem.class::isInstance)
         .map(DyeItem.class::cast)
         .map(DyeItem::getColor)
         .orElseGet(() -> this.world.random.nextBoolean() ? _snowman : _snowman);
   }

   private static CraftingInventory createDyeMixingCraftingInventory(DyeColor firstColor, DyeColor secondColor) {
      CraftingInventory _snowman = new CraftingInventory(new ScreenHandler(null, -1) {
         @Override
         public boolean canUse(PlayerEntity player) {
            return false;
         }
      }, 2, 1);
      _snowman.setStack(0, new ItemStack(DyeItem.byColor(firstColor)));
      _snowman.setStack(1, new ItemStack(DyeItem.byColor(secondColor)));
      return _snowman;
   }

   @Override
   protected float getActiveEyeHeight(EntityPose pose, EntityDimensions dimensions) {
      return 0.95F * dimensions.height;
   }
}
