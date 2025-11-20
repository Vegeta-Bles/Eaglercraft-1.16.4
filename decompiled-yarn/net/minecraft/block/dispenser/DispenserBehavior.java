package net.minecraft.block.dispenser;

import java.util.List;
import java.util.Random;
import net.minecraft.block.AbstractFireBlock;
import net.minecraft.block.BeehiveBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.CampfireBlock;
import net.minecraft.block.CarvedPumpkinBlock;
import net.minecraft.block.DispenserBlock;
import net.minecraft.block.FluidDrainable;
import net.minecraft.block.RespawnAnchorBlock;
import net.minecraft.block.ShulkerBoxBlock;
import net.minecraft.block.SkullBlock;
import net.minecraft.block.TntBlock;
import net.minecraft.block.WitherSkullBlock;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.Saddleable;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.TntEntity;
import net.minecraft.entity.decoration.ArmorStandEntity;
import net.minecraft.entity.passive.AbstractDonkeyEntity;
import net.minecraft.entity.passive.HorseBaseEntity;
import net.minecraft.entity.projectile.ArrowEntity;
import net.minecraft.entity.projectile.FireworkRocketEntity;
import net.minecraft.entity.projectile.PersistentProjectileEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.entity.projectile.SmallFireballEntity;
import net.minecraft.entity.projectile.SpectralArrowEntity;
import net.minecraft.entity.projectile.thrown.EggEntity;
import net.minecraft.entity.projectile.thrown.ExperienceBottleEntity;
import net.minecraft.entity.projectile.thrown.PotionEntity;
import net.minecraft.entity.projectile.thrown.SnowballEntity;
import net.minecraft.entity.vehicle.BoatEntity;
import net.minecraft.fluid.FlowableFluid;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.BoneMealItem;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.item.SpawnEggItem;
import net.minecraft.potion.PotionUtil;
import net.minecraft.potion.Potions;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPointer;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Box;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.Position;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public interface DispenserBehavior {
   DispenserBehavior NOOP = (_snowman, _snowmanx) -> _snowmanx;

   ItemStack dispense(BlockPointer pointer, ItemStack stack);

   static void registerDefaults() {
      DispenserBlock.registerBehavior(Items.ARROW, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            ArrowEntity _snowman = new ArrowEntity(world, position.getX(), position.getY(), position.getZ());
            _snowman.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
            return _snowman;
         }
      });
      DispenserBlock.registerBehavior(Items.TIPPED_ARROW, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            ArrowEntity _snowman = new ArrowEntity(world, position.getX(), position.getY(), position.getZ());
            _snowman.initFromStack(stack);
            _snowman.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
            return _snowman;
         }
      });
      DispenserBlock.registerBehavior(Items.SPECTRAL_ARROW, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            PersistentProjectileEntity _snowman = new SpectralArrowEntity(world, position.getX(), position.getY(), position.getZ());
            _snowman.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
            return _snowman;
         }
      });
      DispenserBlock.registerBehavior(Items.EGG, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return Util.make(new EggEntity(world, position.getX(), position.getY(), position.getZ()), _snowmanx -> _snowmanx.setItem(stack));
         }
      });
      DispenserBlock.registerBehavior(Items.SNOWBALL, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return Util.make(new SnowballEntity(world, position.getX(), position.getY(), position.getZ()), _snowmanx -> _snowmanx.setItem(stack));
         }
      });
      DispenserBlock.registerBehavior(Items.EXPERIENCE_BOTTLE, new ProjectileDispenserBehavior() {
         @Override
         protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
            return Util.make(new ExperienceBottleEntity(world, position.getX(), position.getY(), position.getZ()), _snowmanx -> _snowmanx.setItem(stack));
         }

         @Override
         protected float getVariation() {
            return super.getVariation() * 0.5F;
         }

         @Override
         protected float getForce() {
            return super.getForce() * 1.25F;
         }
      });
      DispenserBlock.registerBehavior(Items.SPLASH_POTION, new DispenserBehavior() {
         @Override
         public ItemStack dispense(BlockPointer _snowman, ItemStack _snowman) {
            return (new ProjectileDispenserBehavior() {
               @Override
               protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                  return Util.make(new PotionEntity(world, position.getX(), position.getY(), position.getZ()), _snowmanx -> _snowmanx.setItem(stack));
               }

               @Override
               protected float getVariation() {
                  return super.getVariation() * 0.5F;
               }

               @Override
               protected float getForce() {
                  return super.getForce() * 1.25F;
               }
            }).dispense(_snowman, _snowman);
         }
      });
      DispenserBlock.registerBehavior(Items.LINGERING_POTION, new DispenserBehavior() {
         @Override
         public ItemStack dispense(BlockPointer _snowman, ItemStack _snowman) {
            return (new ProjectileDispenserBehavior() {
               @Override
               protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                  return Util.make(new PotionEntity(world, position.getX(), position.getY(), position.getZ()), _snowmanx -> _snowmanx.setItem(stack));
               }

               @Override
               protected float getVariation() {
                  return super.getVariation() * 0.5F;
               }

               @Override
               protected float getForce() {
                  return super.getForce() * 1.25F;
               }
            }).dispense(_snowman, _snowman);
         }
      });
      ItemDispenserBehavior _snowman = new ItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
            EntityType<?> _snowmanx = ((SpawnEggItem)stack.getItem()).getEntityType(stack.getTag());
            _snowmanx.spawnFromItemStack(pointer.getWorld(), stack, null, pointer.getBlockPos().offset(_snowman), SpawnReason.DISPENSER, _snowman != Direction.UP, false);
            stack.decrement(1);
            return stack;
         }
      };

      for (SpawnEggItem _snowmanx : SpawnEggItem.getAll()) {
         DispenserBlock.registerBehavior(_snowmanx, _snowman);
      }

      DispenserBlock.registerBehavior(Items.ARMOR_STAND, new ItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos _snowmanx = pointer.getBlockPos().offset(_snowman);
            World _snowmanxx = pointer.getWorld();
            ArmorStandEntity _snowmanxxx = new ArmorStandEntity(_snowmanxx, (double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY(), (double)_snowmanx.getZ() + 0.5);
            EntityType.loadFromEntityTag(_snowmanxx, null, _snowmanxxx, stack.getTag());
            _snowmanxxx.yaw = _snowman.asRotation();
            _snowmanxx.spawnEntity(_snowmanxxx);
            stack.decrement(1);
            return stack;
         }
      });
      DispenserBlock.registerBehavior(Items.SADDLE, new FallibleItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            BlockPos _snowman = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            List<LivingEntity> _snowmanx = pointer.getWorld().getEntitiesByClass(LivingEntity.class, new Box(_snowman), _snowmanxx -> {
               if (!(_snowmanxx instanceof Saddleable)) {
                  return false;
               } else {
                  Saddleable _snowmanx = (Saddleable)_snowmanxx;
                  return !_snowmanx.isSaddled() && _snowmanx.canBeSaddled();
               }
            });
            if (!_snowmanx.isEmpty()) {
               ((Saddleable)_snowmanx.get(0)).saddle(SoundCategory.BLOCKS);
               stack.decrement(1);
               this.setSuccess(true);
               return stack;
            } else {
               return super.dispenseSilently(pointer, stack);
            }
         }
      });
      ItemDispenserBehavior _snowmanx = new FallibleItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            BlockPos _snowman = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

            for (HorseBaseEntity _snowmanx : pointer.getWorld().getEntitiesByClass(HorseBaseEntity.class, new Box(_snowman), _snowmanxx -> _snowmanxx.isAlive() && _snowmanxx.hasArmorSlot())) {
               if (_snowmanx.isHorseArmor(stack) && !_snowmanx.hasArmorInSlot() && _snowmanx.isTame()) {
                  _snowmanx.equip(401, stack.split(1));
                  this.setSuccess(true);
                  return stack;
               }
            }

            return super.dispenseSilently(pointer, stack);
         }
      };
      DispenserBlock.registerBehavior(Items.LEATHER_HORSE_ARMOR, _snowmanx);
      DispenserBlock.registerBehavior(Items.IRON_HORSE_ARMOR, _snowmanx);
      DispenserBlock.registerBehavior(Items.GOLDEN_HORSE_ARMOR, _snowmanx);
      DispenserBlock.registerBehavior(Items.DIAMOND_HORSE_ARMOR, _snowmanx);
      DispenserBlock.registerBehavior(Items.WHITE_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.ORANGE_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.CYAN_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.BLUE_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.BROWN_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.BLACK_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.GRAY_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.GREEN_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.LIGHT_BLUE_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.LIGHT_GRAY_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.LIME_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.MAGENTA_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.PINK_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.PURPLE_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.RED_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(Items.YELLOW_CARPET, _snowmanx);
      DispenserBlock.registerBehavior(
         Items.CHEST,
         new FallibleItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
               BlockPos _snowman = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));

               for (AbstractDonkeyEntity _snowmanx : pointer.getWorld()
                  .getEntitiesByClass(AbstractDonkeyEntity.class, new Box(_snowman), _snowmanxx -> _snowmanxx.isAlive() && !_snowmanxx.hasChest())) {
                  if (_snowmanx.isTame() && _snowmanx.equip(499, stack)) {
                     stack.decrement(1);
                     this.setSuccess(true);
                     return stack;
                  }
               }

               return super.dispenseSilently(pointer, stack);
            }
         }
      );
      DispenserBlock.registerBehavior(Items.FIREWORK_ROCKET, new ItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
            FireworkRocketEntity _snowmanx = new FireworkRocketEntity(pointer.getWorld(), stack, pointer.getX(), pointer.getY(), pointer.getX(), true);
            DispenserBehavior.method_27042(pointer, _snowmanx, _snowman);
            _snowmanx.setVelocity((double)_snowman.getOffsetX(), (double)_snowman.getOffsetY(), (double)_snowman.getOffsetZ(), 0.5F, 1.0F);
            pointer.getWorld().spawnEntity(_snowmanx);
            stack.decrement(1);
            return stack;
         }

         @Override
         protected void playSound(BlockPointer pointer) {
            pointer.getWorld().syncWorldEvent(1004, pointer.getBlockPos(), 0);
         }
      });
      DispenserBlock.registerBehavior(
         Items.FIRE_CHARGE,
         new ItemDispenserBehavior() {
            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
               Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
               Position _snowmanx = DispenserBlock.getOutputLocation(pointer);
               double _snowmanxx = _snowmanx.getX() + (double)((float)_snowman.getOffsetX() * 0.3F);
               double _snowmanxxx = _snowmanx.getY() + (double)((float)_snowman.getOffsetY() * 0.3F);
               double _snowmanxxxx = _snowmanx.getZ() + (double)((float)_snowman.getOffsetZ() * 0.3F);
               World _snowmanxxxxx = pointer.getWorld();
               Random _snowmanxxxxxx = _snowmanxxxxx.random;
               double _snowmanxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.getOffsetX();
               double _snowmanxxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.getOffsetY();
               double _snowmanxxxxxxxxx = _snowmanxxxxxx.nextGaussian() * 0.05 + (double)_snowman.getOffsetZ();
               _snowmanxxxxx.spawnEntity(
                  Util.make(new SmallFireballEntity(_snowmanxxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx), _snowmanxxxxxxxxxx -> _snowmanxxxxxxxxxx.setItem(stack))
               );
               stack.decrement(1);
               return stack;
            }

            @Override
            protected void playSound(BlockPointer pointer) {
               pointer.getWorld().syncWorldEvent(1018, pointer.getBlockPos(), 0);
            }
         }
      );
      DispenserBlock.registerBehavior(Items.OAK_BOAT, new BoatDispenserBehavior(BoatEntity.Type.OAK));
      DispenserBlock.registerBehavior(Items.SPRUCE_BOAT, new BoatDispenserBehavior(BoatEntity.Type.SPRUCE));
      DispenserBlock.registerBehavior(Items.BIRCH_BOAT, new BoatDispenserBehavior(BoatEntity.Type.BIRCH));
      DispenserBlock.registerBehavior(Items.JUNGLE_BOAT, new BoatDispenserBehavior(BoatEntity.Type.JUNGLE));
      DispenserBlock.registerBehavior(Items.DARK_OAK_BOAT, new BoatDispenserBehavior(BoatEntity.Type.DARK_OAK));
      DispenserBlock.registerBehavior(Items.ACACIA_BOAT, new BoatDispenserBehavior(BoatEntity.Type.ACACIA));
      DispenserBehavior _snowmanxx = new ItemDispenserBehavior() {
         private final ItemDispenserBehavior field_13367 = new ItemDispenserBehavior();

         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            BucketItem _snowman = (BucketItem)stack.getItem();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            World _snowmanxx = pointer.getWorld();
            if (_snowman.placeFluid(null, _snowmanxx, _snowmanx, null)) {
               _snowman.onEmptied(_snowmanxx, stack, _snowmanx);
               return new ItemStack(Items.BUCKET);
            } else {
               return this.field_13367.dispense(pointer, stack);
            }
         }
      };
      DispenserBlock.registerBehavior(Items.LAVA_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.WATER_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.SALMON_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.COD_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.PUFFERFISH_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.TROPICAL_FISH_BUCKET, _snowmanxx);
      DispenserBlock.registerBehavior(Items.BUCKET, new ItemDispenserBehavior() {
         private final ItemDispenserBehavior field_13368 = new ItemDispenserBehavior();

         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            WorldAccess _snowman = pointer.getWorld();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
            Block _snowmanxxx = _snowmanxx.getBlock();
            if (_snowmanxxx instanceof FluidDrainable) {
               Fluid _snowmanxxxx = ((FluidDrainable)_snowmanxxx).tryDrainFluid(_snowman, _snowmanx, _snowmanxx);
               if (!(_snowmanxxxx instanceof FlowableFluid)) {
                  return super.dispenseSilently(pointer, stack);
               } else {
                  Item _snowmanxxxxx = _snowmanxxxx.getBucketItem();
                  stack.decrement(1);
                  if (stack.isEmpty()) {
                     return new ItemStack(_snowmanxxxxx);
                  } else {
                     if (pointer.<DispenserBlockEntity>getBlockEntity().addToFirstFreeSlot(new ItemStack(_snowmanxxxxx)) < 0) {
                        this.field_13368.dispense(pointer, new ItemStack(_snowmanxxxxx));
                     }

                     return stack;
                  }
               }
            } else {
               return super.dispenseSilently(pointer, stack);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, new FallibleItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            World _snowman = pointer.getWorld();
            this.setSuccess(true);
            Direction _snowmanx = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos _snowmanxx = pointer.getBlockPos().offset(_snowmanx);
            BlockState _snowmanxxx = _snowman.getBlockState(_snowmanxx);
            if (AbstractFireBlock.method_30032(_snowman, _snowmanxx, _snowmanx)) {
               _snowman.setBlockState(_snowmanxx, AbstractFireBlock.getState(_snowman, _snowmanxx));
            } else if (CampfireBlock.method_30035(_snowmanxxx)) {
               _snowman.setBlockState(_snowmanxx, _snowmanxxx.with(Properties.LIT, Boolean.valueOf(true)));
            } else if (_snowmanxxx.getBlock() instanceof TntBlock) {
               TntBlock.primeTnt(_snowman, _snowmanxx);
               _snowman.removeBlock(_snowmanxx, false);
            } else {
               this.setSuccess(false);
            }

            if (this.isSuccess() && stack.damage(1, _snowman.random, null)) {
               stack.setCount(0);
            }

            return stack;
         }
      });
      DispenserBlock.registerBehavior(Items.BONE_MEAL, new FallibleItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            this.setSuccess(true);
            World _snowman = pointer.getWorld();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            if (!BoneMealItem.useOnFertilizable(stack, _snowman, _snowmanx) && !BoneMealItem.useOnGround(stack, _snowman, _snowmanx, null)) {
               this.setSuccess(false);
            } else if (!_snowman.isClient) {
               _snowman.syncWorldEvent(2005, _snowmanx, 0);
            }

            return stack;
         }
      });
      DispenserBlock.registerBehavior(Blocks.TNT, new ItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            World _snowman = pointer.getWorld();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            TntEntity _snowmanxx = new TntEntity(_snowman, (double)_snowmanx.getX() + 0.5, (double)_snowmanx.getY(), (double)_snowmanx.getZ() + 0.5, null);
            _snowman.spawnEntity(_snowmanxx);
            _snowman.playSound(null, _snowmanxx.getX(), _snowmanxx.getY(), _snowmanxx.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0F, 1.0F);
            stack.decrement(1);
            return stack;
         }
      });
      DispenserBehavior _snowmanxxx = new FallibleItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
            return stack;
         }
      };
      DispenserBlock.registerBehavior(Items.CREEPER_HEAD, _snowmanxxx);
      DispenserBlock.registerBehavior(Items.ZOMBIE_HEAD, _snowmanxxx);
      DispenserBlock.registerBehavior(Items.DRAGON_HEAD, _snowmanxxx);
      DispenserBlock.registerBehavior(Items.SKELETON_SKULL, _snowmanxxx);
      DispenserBlock.registerBehavior(Items.PLAYER_HEAD, _snowmanxxx);
      DispenserBlock.registerBehavior(
         Items.WITHER_SKELETON_SKULL,
         new FallibleItemDispenserBehavior() {
            @Override
            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
               World _snowman = pointer.getWorld();
               Direction _snowmanx = pointer.getBlockState().get(DispenserBlock.FACING);
               BlockPos _snowmanxx = pointer.getBlockPos().offset(_snowmanx);
               if (_snowman.isAir(_snowmanxx) && WitherSkullBlock.canDispense(_snowman, _snowmanxx, stack)) {
                  _snowman.setBlockState(
                     _snowmanxx,
                     Blocks.WITHER_SKELETON_SKULL
                        .getDefaultState()
                        .with(SkullBlock.ROTATION, Integer.valueOf(_snowmanx.getAxis() == Direction.Axis.Y ? 0 : _snowmanx.getOpposite().getHorizontal() * 4)),
                     3
                  );
                  BlockEntity _snowmanxxx = _snowman.getBlockEntity(_snowmanxx);
                  if (_snowmanxxx instanceof SkullBlockEntity) {
                     WitherSkullBlock.onPlaced(_snowman, _snowmanxx, (SkullBlockEntity)_snowmanxxx);
                  }

                  stack.decrement(1);
                  this.setSuccess(true);
               } else {
                  this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
               }

               return stack;
            }
         }
      );
      DispenserBlock.registerBehavior(Blocks.CARVED_PUMPKIN, new FallibleItemDispenserBehavior() {
         @Override
         protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            World _snowman = pointer.getWorld();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            CarvedPumpkinBlock _snowmanxx = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
            if (_snowman.isAir(_snowmanx) && _snowmanxx.canDispense(_snowman, _snowmanx)) {
               if (!_snowman.isClient) {
                  _snowman.setBlockState(_snowmanx, _snowmanxx.getDefaultState(), 3);
               }

               stack.decrement(1);
               this.setSuccess(true);
            } else {
               this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
            }

            return stack;
         }
      });
      DispenserBlock.registerBehavior(Blocks.SHULKER_BOX.asItem(), new BlockPlacementDispenserBehavior());

      for (DyeColor _snowmanxxxx : DyeColor.values()) {
         DispenserBlock.registerBehavior(ShulkerBoxBlock.get(_snowmanxxxx).asItem(), new BlockPlacementDispenserBehavior());
      }

      DispenserBlock.registerBehavior(Items.GLASS_BOTTLE.asItem(), new FallibleItemDispenserBehavior() {
         private final ItemDispenserBehavior field_20533 = new ItemDispenserBehavior();

         private ItemStack method_22141(BlockPointer _snowman, ItemStack emptyBottleStack, ItemStack filledBottleStack) {
            emptyBottleStack.decrement(1);
            if (emptyBottleStack.isEmpty()) {
               return filledBottleStack.copy();
            } else {
               if (_snowman.<DispenserBlockEntity>getBlockEntity().addToFirstFreeSlot(filledBottleStack.copy()) < 0) {
                  this.field_20533.dispense(_snowman, filledBottleStack.copy());
               }

               return emptyBottleStack;
            }
         }

         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            this.setSuccess(false);
            ServerWorld _snowman = pointer.getWorld();
            BlockPos _snowmanx = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
            BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
            if (_snowmanxx.method_27851(BlockTags.BEEHIVES, _snowmanxxx -> _snowmanxxx.contains(BeehiveBlock.HONEY_LEVEL)) && _snowmanxx.get(BeehiveBlock.HONEY_LEVEL) >= 5) {
               ((BeehiveBlock)_snowmanxx.getBlock()).takeHoney(_snowman, _snowmanxx, _snowmanx, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
               this.setSuccess(true);
               return this.method_22141(pointer, stack, new ItemStack(Items.HONEY_BOTTLE));
            } else if (_snowman.getFluidState(_snowmanx).isIn(FluidTags.WATER)) {
               this.setSuccess(true);
               return this.method_22141(pointer, stack, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
            } else {
               return super.dispenseSilently(pointer, stack);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.GLOWSTONE, new FallibleItemDispenserBehavior() {
         @Override
         public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
            Direction _snowman = pointer.getBlockState().get(DispenserBlock.FACING);
            BlockPos _snowmanx = pointer.getBlockPos().offset(_snowman);
            World _snowmanxx = pointer.getWorld();
            BlockState _snowmanxxx = _snowmanxx.getBlockState(_snowmanx);
            this.setSuccess(true);
            if (_snowmanxxx.isOf(Blocks.RESPAWN_ANCHOR)) {
               if (_snowmanxxx.get(RespawnAnchorBlock.CHARGES) != 4) {
                  RespawnAnchorBlock.charge(_snowmanxx, _snowmanx, _snowmanxxx);
                  stack.decrement(1);
               } else {
                  this.setSuccess(false);
               }

               return stack;
            } else {
               return super.dispenseSilently(pointer, stack);
            }
         }
      });
      DispenserBlock.registerBehavior(Items.SHEARS.asItem(), new ShearsDispenserBehavior());
   }

   static void method_27042(BlockPointer _snowman, Entity _snowman, Direction _snowman) {
      _snowman.updatePosition(
         _snowman.getX() + (double)_snowman.getOffsetX() * (0.5000099999997474 - (double)_snowman.getWidth() / 2.0),
         _snowman.getY() + (double)_snowman.getOffsetY() * (0.5000099999997474 - (double)_snowman.getHeight() / 2.0) - (double)_snowman.getHeight() / 2.0,
         _snowman.getZ() + (double)_snowman.getOffsetZ() * (0.5000099999997474 - (double)_snowman.getWidth() / 2.0)
      );
   }
}
