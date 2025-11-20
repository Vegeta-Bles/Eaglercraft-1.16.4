package net.minecraft.client.item;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.Optional;
import javax.annotation.Nullable;
import net.minecraft.client.world.ClientWorld;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.decoration.ItemFrameEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.CompassItem;
import net.minecraft.item.CrossbowItem;
import net.minecraft.item.ElytraItem;
import net.minecraft.item.FishingRodItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.util.Arm;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;

public class ModelPredicateProviderRegistry {
   private static final Map<Identifier, ModelPredicateProvider> GLOBAL = Maps.newHashMap();
   private static final Identifier DAMAGED_ID = new Identifier("damaged");
   private static final Identifier DAMAGE_ID = new Identifier("damage");
   private static final ModelPredicateProvider DAMAGED_PROVIDER = (_snowman, _snowmanx, _snowmanxx) -> _snowman.isDamaged() ? 1.0F : 0.0F;
   private static final ModelPredicateProvider DAMAGE_PROVIDER = (_snowman, _snowmanx, _snowmanxx) -> MathHelper.clamp((float)_snowman.getDamage() / (float)_snowman.getMaxDamage(), 0.0F, 1.0F);
   private static final Map<Item, Map<Identifier, ModelPredicateProvider>> ITEM_SPECIFIC = Maps.newHashMap();

   private static ModelPredicateProvider register(Identifier id, ModelPredicateProvider provider) {
      GLOBAL.put(id, provider);
      return provider;
   }

   private static void register(Item item, Identifier id, ModelPredicateProvider provider) {
      ITEM_SPECIFIC.computeIfAbsent(item, _snowman -> Maps.newHashMap()).put(id, provider);
   }

   @Nullable
   public static ModelPredicateProvider get(Item item, Identifier id) {
      if (item.getMaxDamage() > 0) {
         if (DAMAGE_ID.equals(id)) {
            return DAMAGE_PROVIDER;
         }

         if (DAMAGED_ID.equals(id)) {
            return DAMAGED_PROVIDER;
         }
      }

      ModelPredicateProvider _snowman = GLOBAL.get(id);
      if (_snowman != null) {
         return _snowman;
      } else {
         Map<Identifier, ModelPredicateProvider> _snowmanx = ITEM_SPECIFIC.get(item);
         return _snowmanx == null ? null : _snowmanx.get(id);
      }
   }

   static {
      register(new Identifier("lefthanded"), (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && _snowmanxx.getMainArm() != Arm.RIGHT ? 1.0F : 0.0F);
      register(
         new Identifier("cooldown"),
         (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx instanceof PlayerEntity ? ((PlayerEntity)_snowmanxx).getItemCooldownManager().getCooldownProgress(_snowman.getItem(), 0.0F) : 0.0F
      );
      register(new Identifier("custom_model_data"), (_snowman, _snowmanx, _snowmanxx) -> _snowman.hasTag() ? (float)_snowman.getTag().getInt("CustomModelData") : 0.0F);
      register(Items.BOW, new Identifier("pull"), (_snowman, _snowmanx, _snowmanxx) -> {
         if (_snowmanxx == null) {
            return 0.0F;
         } else {
            return _snowmanxx.getActiveItem() != _snowman ? 0.0F : (float)(_snowman.getMaxUseTime() - _snowmanxx.getItemUseTimeLeft()) / 20.0F;
         }
      });
      register(Items.BOW, new Identifier("pulling"), (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && _snowmanxx.isUsingItem() && _snowmanxx.getActiveItem() == _snowman ? 1.0F : 0.0F);
      register(Items.CLOCK, new Identifier("time"), new ModelPredicateProvider() {
         private double time;
         private double step;
         private long lastTick;

         @Override
         public float call(ItemStack _snowman, @Nullable ClientWorld _snowman, @Nullable LivingEntity _snowman) {
            Entity _snowmanxxx = (Entity)(_snowman != null ? _snowman : _snowman.getHolder());
            if (_snowmanxxx == null) {
               return 0.0F;
            } else {
               if (_snowman == null && _snowmanxxx.world instanceof ClientWorld) {
                  _snowman = (ClientWorld)_snowmanxxx.world;
               }

               if (_snowman == null) {
                  return 0.0F;
               } else {
                  double _snowmanxxxx;
                  if (_snowman.getDimension().isNatural()) {
                     _snowmanxxxx = (double)_snowman.getSkyAngle(1.0F);
                  } else {
                     _snowmanxxxx = Math.random();
                  }

                  _snowmanxxxx = this.getTime(_snowman, _snowmanxxxx);
                  return (float)_snowmanxxxx;
               }
            }
         }

         private double getTime(World world, double skyAngle) {
            if (world.getTime() != this.lastTick) {
               this.lastTick = world.getTime();
               double _snowman = skyAngle - this.time;
               _snowman = MathHelper.floorMod(_snowman + 0.5, 1.0) - 0.5;
               this.step += _snowman * 0.1;
               this.step *= 0.9;
               this.time = MathHelper.floorMod(this.time + this.step, 1.0);
            }

            return this.time;
         }
      });
      register(
         Items.COMPASS,
         new Identifier("angle"),
         new ModelPredicateProvider() {
            private final ModelPredicateProviderRegistry.AngleInterpolator value = new ModelPredicateProviderRegistry.AngleInterpolator();
            private final ModelPredicateProviderRegistry.AngleInterpolator speed = new ModelPredicateProviderRegistry.AngleInterpolator();

            @Override
            public float call(ItemStack _snowman, @Nullable ClientWorld _snowman, @Nullable LivingEntity _snowman) {
               Entity _snowmanxxx = (Entity)(_snowman != null ? _snowman : _snowman.getHolder());
               if (_snowmanxxx == null) {
                  return 0.0F;
               } else {
                  if (_snowman == null && _snowmanxxx.world instanceof ClientWorld) {
                     _snowman = (ClientWorld)_snowmanxxx.world;
                  }

                  BlockPos _snowmanxxxx = CompassItem.hasLodestone(_snowman) ? this.getLodestonePos(_snowman, _snowman.getOrCreateTag()) : this.getSpawnPos(_snowman);
                  long _snowmanxxxxx = _snowman.getTime();
                  if (_snowmanxxxx != null
                     && !(_snowmanxxx.getPos().squaredDistanceTo((double)_snowmanxxxx.getX() + 0.5, _snowmanxxx.getPos().getY(), (double)_snowmanxxxx.getZ() + 0.5) < 1.0E-5F)) {
                     boolean _snowmanxxxxxx = _snowman instanceof PlayerEntity && ((PlayerEntity)_snowman).isMainPlayer();
                     double _snowmanxxxxxxx = 0.0;
                     if (_snowmanxxxxxx) {
                        _snowmanxxxxxxx = (double)_snowman.yaw;
                     } else if (_snowmanxxx instanceof ItemFrameEntity) {
                        _snowmanxxxxxxx = this.getItemFrameAngleOffset((ItemFrameEntity)_snowmanxxx);
                     } else if (_snowmanxxx instanceof ItemEntity) {
                        _snowmanxxxxxxx = (double)(180.0F - ((ItemEntity)_snowmanxxx).method_27314(0.5F) / (float) (Math.PI * 2) * 360.0F);
                     } else if (_snowman != null) {
                        _snowmanxxxxxxx = (double)_snowman.bodyYaw;
                     }

                     _snowmanxxxxxxx = MathHelper.floorMod(_snowmanxxxxxxx / 360.0, 1.0);
                     double _snowmanxxxxxxxx = this.getAngleToPos(Vec3d.ofCenter(_snowmanxxxx), _snowmanxxx) / (float) (Math.PI * 2);
                     double _snowmanxxxxxxxxx;
                     if (_snowmanxxxxxx) {
                        if (this.value.shouldUpdate(_snowmanxxxxx)) {
                           this.value.update(_snowmanxxxxx, 0.5 - (_snowmanxxxxxxx - 0.25));
                        }

                        _snowmanxxxxxxxxx = _snowmanxxxxxxxx + this.value.value;
                     } else {
                        _snowmanxxxxxxxxx = 0.5 - (_snowmanxxxxxxx - 0.25 - _snowmanxxxxxxxx);
                     }

                     return MathHelper.floorMod((float)_snowmanxxxxxxxxx, 1.0F);
                  } else {
                     if (this.speed.shouldUpdate(_snowmanxxxxx)) {
                        this.speed.update(_snowmanxxxxx, Math.random());
                     }

                     double _snowmanxxxxxxxx = this.speed.value + (double)((float)_snowman.hashCode() / 2.1474836E9F);
                     return MathHelper.floorMod((float)_snowmanxxxxxxxx, 1.0F);
                  }
               }
            }

            @Nullable
            private BlockPos getSpawnPos(ClientWorld world) {
               return world.getDimension().isNatural() ? world.getSpawnPos() : null;
            }

            @Nullable
            private BlockPos getLodestonePos(World world, CompoundTag tag) {
               boolean _snowman = tag.contains("LodestonePos");
               boolean _snowmanx = tag.contains("LodestoneDimension");
               if (_snowman && _snowmanx) {
                  Optional<RegistryKey<World>> _snowmanxx = CompassItem.getLodestoneDimension(tag);
                  if (_snowmanxx.isPresent() && world.getRegistryKey() == _snowmanxx.get()) {
                     return NbtHelper.toBlockPos(tag.getCompound("LodestonePos"));
                  }
               }

               return null;
            }

            private double getItemFrameAngleOffset(ItemFrameEntity itemFrame) {
               Direction _snowman = itemFrame.getHorizontalFacing();
               int _snowmanx = _snowman.getAxis().isVertical() ? 90 * _snowman.getDirection().offset() : 0;
               return (double)MathHelper.wrapDegrees(180 + _snowman.getHorizontal() * 90 + itemFrame.getRotation() * 45 + _snowmanx);
            }

            private double getAngleToPos(Vec3d pos, Entity entity) {
               return Math.atan2(pos.getZ() - entity.getZ(), pos.getX() - entity.getX());
            }
         }
      );
      register(Items.CROSSBOW, new Identifier("pull"), (_snowman, _snowmanx, _snowmanxx) -> {
         if (_snowmanxx == null) {
            return 0.0F;
         } else {
            return CrossbowItem.isCharged(_snowman) ? 0.0F : (float)(_snowman.getMaxUseTime() - _snowmanxx.getItemUseTimeLeft()) / (float)CrossbowItem.getPullTime(_snowman);
         }
      });
      register(
         Items.CROSSBOW,
         new Identifier("pulling"),
         (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && _snowmanxx.isUsingItem() && _snowmanxx.getActiveItem() == _snowman && !CrossbowItem.isCharged(_snowman) ? 1.0F : 0.0F
      );
      register(Items.CROSSBOW, new Identifier("charged"), (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && CrossbowItem.isCharged(_snowman) ? 1.0F : 0.0F);
      register(
         Items.CROSSBOW,
         new Identifier("firework"),
         (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && CrossbowItem.isCharged(_snowman) && CrossbowItem.hasProjectile(_snowman, Items.FIREWORK_ROCKET) ? 1.0F : 0.0F
      );
      register(Items.ELYTRA, new Identifier("broken"), (_snowman, _snowmanx, _snowmanxx) -> ElytraItem.isUsable(_snowman) ? 0.0F : 1.0F);
      register(Items.FISHING_ROD, new Identifier("cast"), (_snowman, _snowmanx, _snowmanxx) -> {
         if (_snowmanxx == null) {
            return 0.0F;
         } else {
            boolean _snowmanxxx = _snowmanxx.getMainHandStack() == _snowman;
            boolean _snowmanx = _snowmanxx.getOffHandStack() == _snowman;
            if (_snowmanxx.getMainHandStack().getItem() instanceof FishingRodItem) {
               _snowmanx = false;
            }

            return (_snowmanxxx || _snowmanx) && _snowmanxx instanceof PlayerEntity && ((PlayerEntity)_snowmanxx).fishHook != null ? 1.0F : 0.0F;
         }
      });
      register(Items.SHIELD, new Identifier("blocking"), (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && _snowmanxx.isUsingItem() && _snowmanxx.getActiveItem() == _snowman ? 1.0F : 0.0F);
      register(Items.TRIDENT, new Identifier("throwing"), (_snowman, _snowmanx, _snowmanxx) -> _snowmanxx != null && _snowmanxx.isUsingItem() && _snowmanxx.getActiveItem() == _snowman ? 1.0F : 0.0F);
   }

   static class AngleInterpolator {
      private double value;
      private double speed;
      private long lastUpdateTime;

      private AngleInterpolator() {
      }

      private boolean shouldUpdate(long time) {
         return this.lastUpdateTime != time;
      }

      private void update(long time, double _snowman) {
         this.lastUpdateTime = time;
         double _snowmanx = _snowman - this.value;
         _snowmanx = MathHelper.floorMod(_snowmanx + 0.5, 1.0) - 0.5;
         this.speed += _snowmanx * 0.1;
         this.speed *= 0.8;
         this.value = MathHelper.floorMod(this.value + this.speed, 1.0);
      }
   }
}
