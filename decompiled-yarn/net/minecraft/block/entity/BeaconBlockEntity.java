package net.minecraft.block.entity;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import net.minecraft.advancement.criterion.Criteria;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.Stainable;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.ContainerLock;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.packet.s2c.play.BlockEntityUpdateS2CPacket;
import net.minecraft.screen.BeaconScreenHandler;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.tag.BlockTags;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Tickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.world.Heightmap;

public class BeaconBlockEntity extends BlockEntity implements NamedScreenHandlerFactory, Tickable {
   public static final StatusEffect[][] EFFECTS_BY_LEVEL = new StatusEffect[][]{
      {StatusEffects.SPEED, StatusEffects.HASTE}, {StatusEffects.RESISTANCE, StatusEffects.JUMP_BOOST}, {StatusEffects.STRENGTH}, {StatusEffects.REGENERATION}
   };
   private static final Set<StatusEffect> EFFECTS = Arrays.stream(EFFECTS_BY_LEVEL).flatMap(Arrays::stream).collect(Collectors.toSet());
   private List<BeaconBlockEntity.BeamSegment> beamSegments = Lists.newArrayList();
   private List<BeaconBlockEntity.BeamSegment> field_19178 = Lists.newArrayList();
   private int level;
   private int field_19179 = -1;
   @Nullable
   private StatusEffect primary;
   @Nullable
   private StatusEffect secondary;
   @Nullable
   private Text customName;
   private ContainerLock lock = ContainerLock.EMPTY;
   private final PropertyDelegate propertyDelegate = new PropertyDelegate() {
      @Override
      public int get(int index) {
         switch (index) {
            case 0:
               return BeaconBlockEntity.this.level;
            case 1:
               return StatusEffect.getRawId(BeaconBlockEntity.this.primary);
            case 2:
               return StatusEffect.getRawId(BeaconBlockEntity.this.secondary);
            default:
               return 0;
         }
      }

      @Override
      public void set(int index, int value) {
         switch (index) {
            case 0:
               BeaconBlockEntity.this.level = value;
               break;
            case 1:
               if (!BeaconBlockEntity.this.world.isClient && !BeaconBlockEntity.this.beamSegments.isEmpty()) {
                  BeaconBlockEntity.this.playSound(SoundEvents.BLOCK_BEACON_POWER_SELECT);
               }

               BeaconBlockEntity.this.primary = BeaconBlockEntity.getPotionEffectById(value);
               break;
            case 2:
               BeaconBlockEntity.this.secondary = BeaconBlockEntity.getPotionEffectById(value);
         }
      }

      @Override
      public int size() {
         return 3;
      }
   };

   public BeaconBlockEntity() {
      super(BlockEntityType.BEACON);
   }

   @Override
   public void tick() {
      int _snowman = this.pos.getX();
      int _snowmanx = this.pos.getY();
      int _snowmanxx = this.pos.getZ();
      BlockPos _snowmanxxx;
      if (this.field_19179 < _snowmanx) {
         _snowmanxxx = this.pos;
         this.field_19178 = Lists.newArrayList();
         this.field_19179 = _snowmanxxx.getY() - 1;
      } else {
         _snowmanxxx = new BlockPos(_snowman, this.field_19179 + 1, _snowmanxx);
      }

      BeaconBlockEntity.BeamSegment _snowmanxxxx = this.field_19178.isEmpty() ? null : this.field_19178.get(this.field_19178.size() - 1);
      int _snowmanxxxxx = this.world.getTopY(Heightmap.Type.WORLD_SURFACE, _snowman, _snowmanxx);

      for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < 10 && _snowmanxxx.getY() <= _snowmanxxxxx; _snowmanxxxxxx++) {
         BlockState _snowmanxxxxxxx = this.world.getBlockState(_snowmanxxx);
         Block _snowmanxxxxxxxx = _snowmanxxxxxxx.getBlock();
         if (_snowmanxxxxxxxx instanceof Stainable) {
            float[] _snowmanxxxxxxxxx = ((Stainable)_snowmanxxxxxxxx).getColor().getColorComponents();
            if (this.field_19178.size() <= 1) {
               _snowmanxxxx = new BeaconBlockEntity.BeamSegment(_snowmanxxxxxxxxx);
               this.field_19178.add(_snowmanxxxx);
            } else if (_snowmanxxxx != null) {
               if (Arrays.equals(_snowmanxxxxxxxxx, _snowmanxxxx.color)) {
                  _snowmanxxxx.increaseHeight();
               } else {
                  _snowmanxxxx = new BeaconBlockEntity.BeamSegment(
                     new float[]{(_snowmanxxxx.color[0] + _snowmanxxxxxxxxx[0]) / 2.0F, (_snowmanxxxx.color[1] + _snowmanxxxxxxxxx[1]) / 2.0F, (_snowmanxxxx.color[2] + _snowmanxxxxxxxxx[2]) / 2.0F}
                  );
                  this.field_19178.add(_snowmanxxxx);
               }
            }
         } else {
            if (_snowmanxxxx == null || _snowmanxxxxxxx.getOpacity(this.world, _snowmanxxx) >= 15 && _snowmanxxxxxxxx != Blocks.BEDROCK) {
               this.field_19178.clear();
               this.field_19179 = _snowmanxxxxx;
               break;
            }

            _snowmanxxxx.increaseHeight();
         }

         _snowmanxxx = _snowmanxxx.up();
         this.field_19179++;
      }

      int _snowmanxxxxxx = this.level;
      if (this.world.getTime() % 80L == 0L) {
         if (!this.beamSegments.isEmpty()) {
            this.updateLevel(_snowman, _snowmanx, _snowmanxx);
         }

         if (this.level > 0 && !this.beamSegments.isEmpty()) {
            this.applyPlayerEffects();
            this.playSound(SoundEvents.BLOCK_BEACON_AMBIENT);
         }
      }

      if (this.field_19179 >= _snowmanxxxxx) {
         this.field_19179 = -1;
         boolean _snowmanxxxxxxx = _snowmanxxxxxx > 0;
         this.beamSegments = this.field_19178;
         if (!this.world.isClient) {
            boolean _snowmanxxxxxxxx = this.level > 0;
            if (!_snowmanxxxxxxx && _snowmanxxxxxxxx) {
               this.playSound(SoundEvents.BLOCK_BEACON_ACTIVATE);

               for (ServerPlayerEntity _snowmanxxxxxxxxx : this.world
                  .getNonSpectatingEntities(
                     ServerPlayerEntity.class, new Box((double)_snowman, (double)_snowmanx, (double)_snowmanxx, (double)_snowman, (double)(_snowmanx - 4), (double)_snowmanxx).expand(10.0, 5.0, 10.0)
                  )) {
                  Criteria.CONSTRUCT_BEACON.trigger(_snowmanxxxxxxxxx, this);
               }
            } else if (_snowmanxxxxxxx && !_snowmanxxxxxxxx) {
               this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
            }
         }
      }
   }

   private void updateLevel(int x, int y, int z) {
      this.level = 0;

      for (int _snowman = 1; _snowman <= 4; this.level = _snowman++) {
         int _snowmanx = y - _snowman;
         if (_snowmanx < 0) {
            break;
         }

         boolean _snowmanxx = true;

         for (int _snowmanxxx = x - _snowman; _snowmanxxx <= x + _snowman && _snowmanxx; _snowmanxxx++) {
            for (int _snowmanxxxx = z - _snowman; _snowmanxxxx <= z + _snowman; _snowmanxxxx++) {
               if (!this.world.getBlockState(new BlockPos(_snowmanxxx, _snowmanx, _snowmanxxxx)).isIn(BlockTags.BEACON_BASE_BLOCKS)) {
                  _snowmanxx = false;
                  break;
               }
            }
         }

         if (!_snowmanxx) {
            break;
         }
      }
   }

   @Override
   public void markRemoved() {
      this.playSound(SoundEvents.BLOCK_BEACON_DEACTIVATE);
      super.markRemoved();
   }

   private void applyPlayerEffects() {
      if (!this.world.isClient && this.primary != null) {
         double _snowman = (double)(this.level * 10 + 10);
         int _snowmanx = 0;
         if (this.level >= 4 && this.primary == this.secondary) {
            _snowmanx = 1;
         }

         int _snowmanxx = (9 + this.level * 2) * 20;
         Box _snowmanxxx = new Box(this.pos).expand(_snowman).stretch(0.0, (double)this.world.getHeight(), 0.0);
         List<PlayerEntity> _snowmanxxxx = this.world.getNonSpectatingEntities(PlayerEntity.class, _snowmanxxx);

         for (PlayerEntity _snowmanxxxxx : _snowmanxxxx) {
            _snowmanxxxxx.addStatusEffect(new StatusEffectInstance(this.primary, _snowmanxx, _snowmanx, true, true));
         }

         if (this.level >= 4 && this.primary != this.secondary && this.secondary != null) {
            for (PlayerEntity _snowmanxxxxx : _snowmanxxxx) {
               _snowmanxxxxx.addStatusEffect(new StatusEffectInstance(this.secondary, _snowmanxx, 0, true, true));
            }
         }
      }
   }

   public void playSound(SoundEvent _snowman) {
      this.world.playSound(null, this.pos, _snowman, SoundCategory.BLOCKS, 1.0F, 1.0F);
   }

   public List<BeaconBlockEntity.BeamSegment> getBeamSegments() {
      return (List<BeaconBlockEntity.BeamSegment>)(this.level == 0 ? ImmutableList.of() : this.beamSegments);
   }

   public int getLevel() {
      return this.level;
   }

   @Nullable
   @Override
   public BlockEntityUpdateS2CPacket toUpdatePacket() {
      return new BlockEntityUpdateS2CPacket(this.pos, 3, this.toInitialChunkDataTag());
   }

   @Override
   public CompoundTag toInitialChunkDataTag() {
      return this.toTag(new CompoundTag());
   }

   @Override
   public double getSquaredRenderDistance() {
      return 256.0;
   }

   @Nullable
   private static StatusEffect getPotionEffectById(int id) {
      StatusEffect _snowman = StatusEffect.byRawId(id);
      return EFFECTS.contains(_snowman) ? _snowman : null;
   }

   @Override
   public void fromTag(BlockState state, CompoundTag tag) {
      super.fromTag(state, tag);
      this.primary = getPotionEffectById(tag.getInt("Primary"));
      this.secondary = getPotionEffectById(tag.getInt("Secondary"));
      if (tag.contains("CustomName", 8)) {
         this.customName = Text.Serializer.fromJson(tag.getString("CustomName"));
      }

      this.lock = ContainerLock.fromTag(tag);
   }

   @Override
   public CompoundTag toTag(CompoundTag tag) {
      super.toTag(tag);
      tag.putInt("Primary", StatusEffect.getRawId(this.primary));
      tag.putInt("Secondary", StatusEffect.getRawId(this.secondary));
      tag.putInt("Levels", this.level);
      if (this.customName != null) {
         tag.putString("CustomName", Text.Serializer.toJson(this.customName));
      }

      this.lock.toTag(tag);
      return tag;
   }

   public void setCustomName(@Nullable Text _snowman) {
      this.customName = _snowman;
   }

   @Nullable
   @Override
   public ScreenHandler createMenu(int _snowman, PlayerInventory _snowman, PlayerEntity _snowman) {
      return LockableContainerBlockEntity.checkUnlocked(_snowman, this.lock, this.getDisplayName())
         ? new BeaconScreenHandler(_snowman, _snowman, this.propertyDelegate, ScreenHandlerContext.create(this.world, this.getPos()))
         : null;
   }

   @Override
   public Text getDisplayName() {
      return (Text)(this.customName != null ? this.customName : new TranslatableText("container.beacon"));
   }

   public static class BeamSegment {
      private final float[] color;
      private int height;

      public BeamSegment(float[] color) {
         this.color = color;
         this.height = 1;
      }

      protected void increaseHeight() {
         this.height++;
      }

      public float[] getColor() {
         return this.color;
      }

      public int getHeight() {
         return this.height;
      }
   }
}
