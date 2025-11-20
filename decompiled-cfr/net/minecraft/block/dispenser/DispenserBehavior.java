/*
 * Decompiled with CFR 0.152.
 */
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
import net.minecraft.block.dispenser.BlockPlacementDispenserBehavior;
import net.minecraft.block.dispenser.BoatDispenserBehavior;
import net.minecraft.block.dispenser.FallibleItemDispenserBehavior;
import net.minecraft.block.dispenser.ItemDispenserBehavior;
import net.minecraft.block.dispenser.ProjectileDispenserBehavior;
import net.minecraft.block.dispenser.ShearsDispenserBehavior;
import net.minecraft.block.entity.BeehiveBlockEntity;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.block.entity.DispenserBlockEntity;
import net.minecraft.block.entity.SkullBlockEntity;
import net.minecraft.class_2357;
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

public interface DispenserBehavior {
    public static final DispenserBehavior NOOP = (blockPointer, itemStack) -> itemStack;

    public ItemStack dispense(BlockPointer var1, ItemStack var2);

    public static void registerDefaults() {
        DispenserBlock.registerBehavior(Items.ARROW, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                ArrowEntity arrowEntity = new ArrowEntity(world, position.getX(), position.getY(), position.getZ());
                arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return arrowEntity;
            }
        });
        DispenserBlock.registerBehavior(Items.TIPPED_ARROW, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                ArrowEntity arrowEntity = new ArrowEntity(world, position.getX(), position.getY(), position.getZ());
                arrowEntity.initFromStack(stack);
                arrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return arrowEntity;
            }
        });
        DispenserBlock.registerBehavior(Items.SPECTRAL_ARROW, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                SpectralArrowEntity spectralArrowEntity = new SpectralArrowEntity(world, position.getX(), position.getY(), position.getZ());
                spectralArrowEntity.pickupType = PersistentProjectileEntity.PickupPermission.ALLOWED;
                return spectralArrowEntity;
            }
        });
        DispenserBlock.registerBehavior(Items.EGG, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new EggEntity(world, position.getX(), position.getY(), position.getZ()), eggEntity -> eggEntity.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(Items.SNOWBALL, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new SnowballEntity(world, position.getX(), position.getY(), position.getZ()), snowballEntity -> snowballEntity.setItem(stack));
            }
        });
        DispenserBlock.registerBehavior(Items.EXPERIENCE_BOTTLE, new ProjectileDispenserBehavior(){

            protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                return Util.make(new ExperienceBottleEntity(world, position.getX(), position.getY(), position.getZ()), experienceBottleEntity -> experienceBottleEntity.setItem(stack));
            }

            protected float getVariation() {
                return super.getVariation() * 0.5f;
            }

            protected float getForce() {
                return super.getForce() * 1.25f;
            }
        });
        DispenserBlock.registerBehavior(Items.SPLASH_POTION, new DispenserBehavior(){

            public ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
                return new ProjectileDispenserBehavior(this){
                    final /* synthetic */ class_2357.23 field_13369;
                    {
                        this.field_13369 = _snowman;
                    }

                    protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                        return Util.make(new PotionEntity(world, position.getX(), position.getY(), position.getZ()), potionEntity -> potionEntity.setItem(stack));
                    }

                    protected float getVariation() {
                        return super.getVariation() * 0.5f;
                    }

                    protected float getForce() {
                        return super.getForce() * 1.25f;
                    }
                }.dispense(blockPointer, itemStack);
            }
        });
        DispenserBlock.registerBehavior(Items.LINGERING_POTION, new DispenserBehavior(){

            public ItemStack dispense(BlockPointer blockPointer, ItemStack itemStack) {
                return new ProjectileDispenserBehavior(this){
                    final /* synthetic */ class_2357.24 field_13366;
                    {
                        this.field_13366 = _snowman;
                    }

                    protected ProjectileEntity createProjectile(World world, Position position, ItemStack stack) {
                        return Util.make(new PotionEntity(world, position.getX(), position.getY(), position.getZ()), potionEntity -> potionEntity.setItem(stack));
                    }

                    protected float getVariation() {
                        return super.getVariation() * 0.5f;
                    }

                    protected float getForce() {
                        return super.getForce() * 1.25f;
                    }
                }.dispense(blockPointer, itemStack);
            }
        });
        ItemDispenserBehavior itemDispenserBehavior = new ItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                EntityType<?> _snowman2 = ((SpawnEggItem)stack.getItem()).getEntityType(stack.getTag());
                _snowman2.spawnFromItemStack(pointer.getWorld(), stack, null, pointer.getBlockPos().offset(direction), SpawnReason.DISPENSER, direction != Direction.UP, false);
                stack.decrement(1);
                return stack;
            }
        };
        for (SpawnEggItem spawnEggItem : SpawnEggItem.getAll()) {
            DispenserBlock.registerBehavior(spawnEggItem, itemDispenserBehavior);
        }
        DispenserBlock.registerBehavior(Items.ARMOR_STAND, new ItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos _snowman2 = pointer.getBlockPos().offset(direction);
                ServerWorld _snowman3 = pointer.getWorld();
                ArmorStandEntity _snowman4 = new ArmorStandEntity(_snowman3, (double)_snowman2.getX() + 0.5, _snowman2.getY(), (double)_snowman2.getZ() + 0.5);
                EntityType.loadFromEntityTag(_snowman3, null, _snowman4, stack.getTag());
                _snowman4.yaw = direction.asRotation();
                _snowman3.spawnEntity(_snowman4);
                stack.decrement(1);
                return stack;
            }
        });
        DispenserBlock.registerBehavior(Items.SADDLE, new FallibleItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<LivingEntity> _snowman2 = pointer.getWorld().getEntitiesByClass(LivingEntity.class, new Box(blockPos), livingEntity -> {
                    if (livingEntity instanceof Saddleable) {
                        Saddleable saddleable = (Saddleable)((Object)livingEntity);
                        return !saddleable.isSaddled() && saddleable.canBeSaddled();
                    }
                    return false;
                });
                if (!_snowman2.isEmpty()) {
                    ((Saddleable)((Object)_snowman2.get(0))).saddle(SoundCategory.BLOCKS);
                    stack.decrement(1);
                    this.setSuccess(true);
                    return stack;
                }
                return super.dispenseSilently(pointer, stack);
            }
        });
        FallibleItemDispenserBehavior fallibleItemDispenserBehavior = new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<HorseBaseEntity> _snowman2 = pointer.getWorld().getEntitiesByClass(HorseBaseEntity.class, new Box(blockPos), horseBaseEntity -> horseBaseEntity.isAlive() && horseBaseEntity.hasArmorSlot());
                for (HorseBaseEntity horseBaseEntity2 : _snowman2) {
                    if (!horseBaseEntity2.isHorseArmor(stack) || horseBaseEntity2.hasArmorInSlot() || !horseBaseEntity2.isTame()) continue;
                    horseBaseEntity2.equip(401, stack.split(1));
                    this.setSuccess(true);
                    return stack;
                }
                return super.dispenseSilently(pointer, stack);
            }
        };
        DispenserBlock.registerBehavior(Items.LEATHER_HORSE_ARMOR, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.IRON_HORSE_ARMOR, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.GOLDEN_HORSE_ARMOR, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.DIAMOND_HORSE_ARMOR, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.WHITE_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.ORANGE_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.CYAN_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.BLUE_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.BROWN_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.BLACK_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.GRAY_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.GREEN_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.LIGHT_BLUE_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.LIGHT_GRAY_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.LIME_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.MAGENTA_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.PINK_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.PURPLE_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.RED_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.YELLOW_CARPET, fallibleItemDispenserBehavior);
        DispenserBlock.registerBehavior(Items.CHEST, new FallibleItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BlockPos blockPos = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                List<AbstractDonkeyEntity> _snowman2 = pointer.getWorld().getEntitiesByClass(AbstractDonkeyEntity.class, new Box(blockPos), abstractDonkeyEntity -> abstractDonkeyEntity.isAlive() && !abstractDonkeyEntity.hasChest());
                for (AbstractDonkeyEntity abstractDonkeyEntity2 : _snowman2) {
                    if (!abstractDonkeyEntity2.isTame() || !abstractDonkeyEntity2.equip(499, stack)) continue;
                    stack.decrement(1);
                    this.setSuccess(true);
                    return stack;
                }
                return super.dispenseSilently(pointer, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.FIREWORK_ROCKET, new ItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                FireworkRocketEntity _snowman2 = new FireworkRocketEntity((World)pointer.getWorld(), stack, pointer.getX(), pointer.getY(), pointer.getX(), true);
                DispenserBehavior.method_27042(pointer, _snowman2, direction);
                _snowman2.setVelocity(direction.getOffsetX(), direction.getOffsetY(), direction.getOffsetZ(), 0.5f, 1.0f);
                pointer.getWorld().spawnEntity(_snowman2);
                stack.decrement(1);
                return stack;
            }

            protected void playSound(BlockPointer pointer) {
                pointer.getWorld().syncWorldEvent(1004, pointer.getBlockPos(), 0);
            }
        });
        DispenserBlock.registerBehavior(Items.FIRE_CHARGE, new ItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                Position _snowman2 = DispenserBlock.getOutputLocation(pointer);
                double _snowman3 = _snowman2.getX() + (double)((float)direction.getOffsetX() * 0.3f);
                double _snowman4 = _snowman2.getY() + (double)((float)direction.getOffsetY() * 0.3f);
                double _snowman5 = _snowman2.getZ() + (double)((float)direction.getOffsetZ() * 0.3f);
                ServerWorld _snowman6 = pointer.getWorld();
                Random _snowman7 = _snowman6.random;
                double _snowman8 = _snowman7.nextGaussian() * 0.05 + (double)direction.getOffsetX();
                double _snowman9 = _snowman7.nextGaussian() * 0.05 + (double)direction.getOffsetY();
                double _snowman10 = _snowman7.nextGaussian() * 0.05 + (double)direction.getOffsetZ();
                _snowman6.spawnEntity(Util.make(new SmallFireballEntity(_snowman6, _snowman3, _snowman4, _snowman5, _snowman8, _snowman9, _snowman10), smallFireballEntity -> smallFireballEntity.setItem(stack)));
                stack.decrement(1);
                return stack;
            }

            protected void playSound(BlockPointer pointer) {
                pointer.getWorld().syncWorldEvent(1018, pointer.getBlockPos(), 0);
            }
        });
        DispenserBlock.registerBehavior(Items.OAK_BOAT, new BoatDispenserBehavior(BoatEntity.Type.OAK));
        DispenserBlock.registerBehavior(Items.SPRUCE_BOAT, new BoatDispenserBehavior(BoatEntity.Type.SPRUCE));
        DispenserBlock.registerBehavior(Items.BIRCH_BOAT, new BoatDispenserBehavior(BoatEntity.Type.BIRCH));
        DispenserBlock.registerBehavior(Items.JUNGLE_BOAT, new BoatDispenserBehavior(BoatEntity.Type.JUNGLE));
        DispenserBlock.registerBehavior(Items.DARK_OAK_BOAT, new BoatDispenserBehavior(BoatEntity.Type.DARK_OAK));
        DispenserBlock.registerBehavior(Items.ACACIA_BOAT, new BoatDispenserBehavior(BoatEntity.Type.ACACIA));
        ItemDispenserBehavior itemDispenserBehavior2 = new ItemDispenserBehavior(){
            private final ItemDispenserBehavior field_13367;
            {
                this.field_13367 = new ItemDispenserBehavior();
            }

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                BucketItem bucketItem = (BucketItem)stack.getItem();
                BlockPos _snowman2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                ServerWorld _snowman3 = pointer.getWorld();
                if (bucketItem.placeFluid(null, _snowman3, _snowman2, null)) {
                    bucketItem.onEmptied(_snowman3, stack, _snowman2);
                    return new ItemStack(Items.BUCKET);
                }
                return this.field_13367.dispense(pointer, stack);
            }
        };
        DispenserBlock.registerBehavior(Items.LAVA_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.WATER_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.SALMON_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.COD_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.PUFFERFISH_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.TROPICAL_FISH_BUCKET, itemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.BUCKET, new ItemDispenserBehavior(){
            private final ItemDispenserBehavior field_13368;
            {
                this.field_13368 = new ItemDispenserBehavior();
            }

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Fluid fluid;
                ServerWorld serverWorld = pointer.getWorld();
                BlockState _snowman2 = serverWorld.getBlockState(_snowman = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING)));
                Block _snowman3 = _snowman2.getBlock();
                if (_snowman3 instanceof FluidDrainable) {
                    fluid = ((FluidDrainable)((Object)_snowman3)).tryDrainFluid(serverWorld, _snowman, _snowman2);
                    if (!(fluid instanceof FlowableFluid)) {
                        return super.dispenseSilently(pointer, stack);
                    }
                } else {
                    return super.dispenseSilently(pointer, stack);
                }
                Item _snowman4 = fluid.getBucketItem();
                stack.decrement(1);
                if (stack.isEmpty()) {
                    return new ItemStack(_snowman4);
                }
                if (((DispenserBlockEntity)pointer.getBlockEntity()).addToFirstFreeSlot(new ItemStack(_snowman4)) < 0) {
                    this.field_13368.dispense(pointer, new ItemStack(_snowman4));
                }
                return stack;
            }
        });
        DispenserBlock.registerBehavior(Items.FLINT_AND_STEEL, new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld serverWorld = pointer.getWorld();
                this.setSuccess(true);
                Direction _snowman2 = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos _snowman3 = pointer.getBlockPos().offset(_snowman2);
                BlockState _snowman4 = serverWorld.getBlockState(_snowman3);
                if (AbstractFireBlock.method_30032(serverWorld, _snowman3, _snowman2)) {
                    serverWorld.setBlockState(_snowman3, AbstractFireBlock.getState(serverWorld, _snowman3));
                } else if (CampfireBlock.method_30035(_snowman4)) {
                    serverWorld.setBlockState(_snowman3, (BlockState)_snowman4.with(Properties.LIT, true));
                } else if (_snowman4.getBlock() instanceof TntBlock) {
                    TntBlock.primeTnt(serverWorld, _snowman3);
                    serverWorld.removeBlock(_snowman3, false);
                } else {
                    this.setSuccess(false);
                }
                if (this.isSuccess() && stack.damage(1, serverWorld.random, null)) {
                    stack.setCount(0);
                }
                return stack;
            }
        });
        DispenserBlock.registerBehavior(Items.BONE_MEAL, new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                this.setSuccess(true);
                ServerWorld serverWorld = pointer.getWorld();
                BlockPos _snowman2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                if (BoneMealItem.useOnFertilizable(stack, serverWorld, _snowman2) || BoneMealItem.useOnGround(stack, serverWorld, _snowman2, null)) {
                    if (!serverWorld.isClient) {
                        serverWorld.syncWorldEvent(2005, _snowman2, 0);
                    }
                } else {
                    this.setSuccess(false);
                }
                return stack;
            }
        });
        DispenserBlock.registerBehavior(Blocks.TNT, new ItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld serverWorld = pointer.getWorld();
                BlockPos _snowman2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                TntEntity _snowman3 = new TntEntity(serverWorld, (double)_snowman2.getX() + 0.5, _snowman2.getY(), (double)_snowman2.getZ() + 0.5, null);
                serverWorld.spawnEntity(_snowman3);
                ((World)serverWorld).playSound(null, _snowman3.getX(), _snowman3.getY(), _snowman3.getZ(), SoundEvents.ENTITY_TNT_PRIMED, SoundCategory.BLOCKS, 1.0f, 1.0f);
                stack.decrement(1);
                return stack;
            }
        });
        FallibleItemDispenserBehavior fallibleItemDispenserBehavior2 = new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
                return stack;
            }
        };
        DispenserBlock.registerBehavior(Items.CREEPER_HEAD, fallibleItemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.ZOMBIE_HEAD, fallibleItemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.DRAGON_HEAD, fallibleItemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.SKELETON_SKULL, fallibleItemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.PLAYER_HEAD, fallibleItemDispenserBehavior2);
        DispenserBlock.registerBehavior(Items.WITHER_SKELETON_SKULL, new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld serverWorld = pointer.getWorld();
                Direction _snowman2 = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos _snowman3 = pointer.getBlockPos().offset(_snowman2);
                if (serverWorld.isAir(_snowman3) && WitherSkullBlock.canDispense(serverWorld, _snowman3, stack)) {
                    serverWorld.setBlockState(_snowman3, (BlockState)Blocks.WITHER_SKELETON_SKULL.getDefaultState().with(SkullBlock.ROTATION, _snowman2.getAxis() == Direction.Axis.Y ? 0 : _snowman2.getOpposite().getHorizontal() * 4), 3);
                    BlockEntity blockEntity = serverWorld.getBlockEntity(_snowman3);
                    if (blockEntity instanceof SkullBlockEntity) {
                        WitherSkullBlock.onPlaced(serverWorld, _snowman3, (SkullBlockEntity)blockEntity);
                    }
                    stack.decrement(1);
                    this.setSuccess(true);
                } else {
                    this.setSuccess(ArmorItem.dispenseArmor(pointer, stack));
                }
                return stack;
            }
        });
        DispenserBlock.registerBehavior(Blocks.CARVED_PUMPKIN, new FallibleItemDispenserBehavior(){

            protected ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                ServerWorld serverWorld = pointer.getWorld();
                BlockPos _snowman2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                CarvedPumpkinBlock _snowman3 = (CarvedPumpkinBlock)Blocks.CARVED_PUMPKIN;
                if (serverWorld.isAir(_snowman2) && _snowman3.canDispense(serverWorld, _snowman2)) {
                    if (!serverWorld.isClient) {
                        serverWorld.setBlockState(_snowman2, _snowman3.getDefaultState(), 3);
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
        for (DyeColor dyeColor : DyeColor.values()) {
            DispenserBlock.registerBehavior(ShulkerBoxBlock.get(dyeColor).asItem(), new BlockPlacementDispenserBehavior());
        }
        DispenserBlock.registerBehavior(Items.GLASS_BOTTLE.asItem(), new FallibleItemDispenserBehavior(){
            private final ItemDispenserBehavior field_20533 = new ItemDispenserBehavior();

            private ItemStack method_22141(BlockPointer blockPointer, ItemStack emptyBottleStack, ItemStack filledBottleStack) {
                emptyBottleStack.decrement(1);
                if (emptyBottleStack.isEmpty()) {
                    return filledBottleStack.copy();
                }
                if (((DispenserBlockEntity)blockPointer.getBlockEntity()).addToFirstFreeSlot(filledBottleStack.copy()) < 0) {
                    this.field_20533.dispense(blockPointer, filledBottleStack.copy());
                }
                return emptyBottleStack;
            }

            @Override
            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                this.setSuccess(false);
                ServerWorld serverWorld = pointer.getWorld();
                BlockPos _snowman2 = pointer.getBlockPos().offset(pointer.getBlockState().get(DispenserBlock.FACING));
                BlockState _snowman3 = serverWorld.getBlockState(_snowman2);
                if (_snowman3.method_27851(BlockTags.BEEHIVES, abstractBlockState -> abstractBlockState.contains(BeehiveBlock.HONEY_LEVEL)) && _snowman3.get(BeehiveBlock.HONEY_LEVEL) >= 5) {
                    ((BeehiveBlock)_snowman3.getBlock()).takeHoney(serverWorld, _snowman3, _snowman2, null, BeehiveBlockEntity.BeeState.BEE_RELEASED);
                    this.setSuccess(true);
                    return this.method_22141(pointer, stack, new ItemStack(Items.HONEY_BOTTLE));
                }
                if (serverWorld.getFluidState(_snowman2).isIn(FluidTags.WATER)) {
                    this.setSuccess(true);
                    return this.method_22141(pointer, stack, PotionUtil.setPotion(new ItemStack(Items.POTION), Potions.WATER));
                }
                return super.dispenseSilently(pointer, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.GLOWSTONE, new FallibleItemDispenserBehavior(){

            public ItemStack dispenseSilently(BlockPointer pointer, ItemStack stack) {
                Direction direction = pointer.getBlockState().get(DispenserBlock.FACING);
                BlockPos _snowman2 = pointer.getBlockPos().offset(direction);
                ServerWorld _snowman3 = pointer.getWorld();
                BlockState _snowman4 = _snowman3.getBlockState(_snowman2);
                this.setSuccess(true);
                if (_snowman4.isOf(Blocks.RESPAWN_ANCHOR)) {
                    if (_snowman4.get(RespawnAnchorBlock.CHARGES) != 4) {
                        RespawnAnchorBlock.charge(_snowman3, _snowman2, _snowman4);
                        stack.decrement(1);
                    } else {
                        this.setSuccess(false);
                    }
                    return stack;
                }
                return super.dispenseSilently(pointer, stack);
            }
        });
        DispenserBlock.registerBehavior(Items.SHEARS.asItem(), new ShearsDispenserBehavior());
    }

    public static void method_27042(BlockPointer blockPointer, Entity entity, Direction direction) {
        entity.updatePosition(blockPointer.getX() + (double)direction.getOffsetX() * (0.5000099999997474 - (double)entity.getWidth() / 2.0), blockPointer.getY() + (double)direction.getOffsetY() * (0.5000099999997474 - (double)entity.getHeight() / 2.0) - (double)entity.getHeight() / 2.0, blockPointer.getZ() + (double)direction.getOffsetZ() * (0.5000099999997474 - (double)entity.getWidth() / 2.0));
    }
}

