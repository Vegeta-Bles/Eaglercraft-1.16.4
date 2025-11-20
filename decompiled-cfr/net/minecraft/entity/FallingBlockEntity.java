/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 */
package net.minecraft.entity;

import com.google.common.collect.Lists;
import java.util.ArrayList;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.AnvilBlock;
import net.minecraft.block.Block;
import net.minecraft.block.BlockEntityProvider;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.ConcretePowderBlock;
import net.minecraft.block.FallingBlock;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.MovementType;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.data.DataTracker;
import net.minecraft.entity.data.TrackedData;
import net.minecraft.entity.data.TrackedDataHandlerRegistry;
import net.minecraft.fluid.Fluids;
import net.minecraft.item.AutomaticItemPlacementContext;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.Tag;
import net.minecraft.network.Packet;
import net.minecraft.network.packet.s2c.play.EntitySpawnS2CPacket;
import net.minecraft.state.property.Properties;
import net.minecraft.tag.BlockTags;
import net.minecraft.tag.FluidTags;
import net.minecraft.util.crash.CrashReportSection;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.hit.HitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.GameRules;
import net.minecraft.world.RaycastContext;
import net.minecraft.world.World;

public class FallingBlockEntity
extends Entity {
    private BlockState block = Blocks.SAND.getDefaultState();
    public int timeFalling;
    public boolean dropItem = true;
    private boolean destroyedOnLanding;
    private boolean hurtEntities;
    private int fallHurtMax = 40;
    private float fallHurtAmount = 2.0f;
    public CompoundTag blockEntityData;
    protected static final TrackedData<BlockPos> BLOCK_POS = DataTracker.registerData(FallingBlockEntity.class, TrackedDataHandlerRegistry.BLOCK_POS);

    public FallingBlockEntity(EntityType<? extends FallingBlockEntity> entityType, World world) {
        super(entityType, world);
    }

    public FallingBlockEntity(World world, double x, double y, double z, BlockState block) {
        this((EntityType<? extends FallingBlockEntity>)EntityType.FALLING_BLOCK, world);
        this.block = block;
        this.inanimate = true;
        this.updatePosition(x, y + (double)((1.0f - this.getHeight()) / 2.0f), z);
        this.setVelocity(Vec3d.ZERO);
        this.prevX = x;
        this.prevY = y;
        this.prevZ = z;
        this.setFallingBlockPos(this.getBlockPos());
    }

    @Override
    public boolean isAttackable() {
        return false;
    }

    public void setFallingBlockPos(BlockPos pos) {
        this.dataTracker.set(BLOCK_POS, pos);
    }

    public BlockPos getFallingBlockPos() {
        return this.dataTracker.get(BLOCK_POS);
    }

    @Override
    protected boolean canClimb() {
        return false;
    }

    @Override
    protected void initDataTracker() {
        this.dataTracker.startTracking(BLOCK_POS, BlockPos.ORIGIN);
    }

    @Override
    public boolean collides() {
        return !this.removed;
    }

    @Override
    public void tick() {
        BlockPos blockPos;
        if (this.block.isAir()) {
            this.remove();
            return;
        }
        Block block = this.block.getBlock();
        if (this.timeFalling++ == 0) {
            blockPos = this.getBlockPos();
            if (this.world.getBlockState(blockPos).isOf(block)) {
                this.world.removeBlock(blockPos, false);
            } else if (!this.world.isClient) {
                this.remove();
                return;
            }
        }
        if (!this.hasNoGravity()) {
            this.setVelocity(this.getVelocity().add(0.0, -0.04, 0.0));
        }
        this.move(MovementType.SELF, this.getVelocity());
        if (!this.world.isClient) {
            blockPos = this.getBlockPos();
            boolean _snowman2 = this.block.getBlock() instanceof ConcretePowderBlock;
            boolean _snowman3 = _snowman2 && this.world.getFluidState(blockPos).isIn(FluidTags.WATER);
            double _snowman4 = this.getVelocity().lengthSquared();
            if (_snowman2 && _snowman4 > 1.0 && ((BlockHitResult)(object = this.world.raycast(new RaycastContext(new Vec3d(this.prevX, this.prevY, this.prevZ), this.getPos(), RaycastContext.ShapeType.COLLIDER, RaycastContext.FluidHandling.SOURCE_ONLY, this)))).getType() != HitResult.Type.MISS && this.world.getFluidState(((BlockHitResult)object).getBlockPos()).isIn(FluidTags.WATER)) {
                blockPos = ((BlockHitResult)object).getBlockPos();
                _snowman3 = true;
            }
            if (this.onGround || _snowman3) {
                Object object = this.world.getBlockState(blockPos);
                this.setVelocity(this.getVelocity().multiply(0.7, -0.5, 0.7));
                if (!((AbstractBlock.AbstractBlockState)object).isOf(Blocks.MOVING_PISTON)) {
                    this.remove();
                    if (!this.destroyedOnLanding) {
                        boolean bl = ((AbstractBlock.AbstractBlockState)object).canReplace(new AutomaticItemPlacementContext(this.world, blockPos, Direction.DOWN, ItemStack.EMPTY, Direction.UP));
                        _snowman = FallingBlock.canFallThrough(this.world.getBlockState(blockPos.down())) && (!_snowman2 || !_snowman3);
                        boolean bl2 = _snowman = this.block.canPlaceAt(this.world, blockPos) && !_snowman;
                        if (bl && _snowman) {
                            if (this.block.contains(Properties.WATERLOGGED) && this.world.getFluidState(blockPos).getFluid() == Fluids.WATER) {
                                this.block = (BlockState)this.block.with(Properties.WATERLOGGED, true);
                            }
                            if (this.world.setBlockState(blockPos, this.block, 3)) {
                                if (block instanceof FallingBlock) {
                                    ((FallingBlock)block).onLanding(this.world, blockPos, this.block, (BlockState)object, this);
                                }
                                if (this.blockEntityData != null && block instanceof BlockEntityProvider && (blockEntity = this.world.getBlockEntity(blockPos)) != null) {
                                    BlockEntity blockEntity;
                                    CompoundTag compoundTag = blockEntity.toTag(new CompoundTag());
                                    for (String string : this.blockEntityData.getKeys()) {
                                        Tag tag = this.blockEntityData.get(string);
                                        if ("x".equals(string) || "y".equals(string) || "z".equals(string)) continue;
                                        compoundTag.put(string, tag.copy());
                                    }
                                    blockEntity.fromTag(this.block, compoundTag);
                                    blockEntity.markDirty();
                                }
                            } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                                this.dropItem(block);
                            }
                        } else if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                            this.dropItem(block);
                        }
                    } else if (block instanceof FallingBlock) {
                        ((FallingBlock)block).onDestroyedOnLanding(this.world, blockPos, this);
                    }
                }
            } else if (!(this.world.isClient || (this.timeFalling <= 100 || blockPos.getY() >= 1 && blockPos.getY() <= 256) && this.timeFalling <= 600)) {
                if (this.dropItem && this.world.getGameRules().getBoolean(GameRules.DO_ENTITY_DROPS)) {
                    this.dropItem(block);
                }
                this.remove();
            }
        }
        this.setVelocity(this.getVelocity().multiply(0.98));
    }

    @Override
    public boolean handleFallDamage(float fallDistance, float damageMultiplier) {
        int n;
        if (this.hurtEntities && (n = MathHelper.ceil(fallDistance - 1.0f)) > 0) {
            ArrayList arrayList = Lists.newArrayList(this.world.getOtherEntities(this, this.getBoundingBox()));
            boolean _snowman2 = this.block.isIn(BlockTags.ANVIL);
            DamageSource _snowman3 = _snowman2 ? DamageSource.ANVIL : DamageSource.FALLING_BLOCK;
            for (Entity entity : arrayList) {
                entity.damage(_snowman3, Math.min(MathHelper.floor((float)n * this.fallHurtAmount), this.fallHurtMax));
            }
            if (_snowman2 && (double)this.random.nextFloat() < (double)0.05f + (double)n * 0.05) {
                BlockState blockState = AnvilBlock.getLandingState(this.block);
                if (blockState == null) {
                    this.destroyedOnLanding = true;
                } else {
                    this.block = blockState;
                }
            }
        }
        return false;
    }

    @Override
    protected void writeCustomDataToTag(CompoundTag tag) {
        tag.put("BlockState", NbtHelper.fromBlockState(this.block));
        tag.putInt("Time", this.timeFalling);
        tag.putBoolean("DropItem", this.dropItem);
        tag.putBoolean("HurtEntities", this.hurtEntities);
        tag.putFloat("FallHurtAmount", this.fallHurtAmount);
        tag.putInt("FallHurtMax", this.fallHurtMax);
        if (this.blockEntityData != null) {
            tag.put("TileEntityData", this.blockEntityData);
        }
    }

    @Override
    protected void readCustomDataFromTag(CompoundTag tag) {
        this.block = NbtHelper.toBlockState(tag.getCompound("BlockState"));
        this.timeFalling = tag.getInt("Time");
        if (tag.contains("HurtEntities", 99)) {
            this.hurtEntities = tag.getBoolean("HurtEntities");
            this.fallHurtAmount = tag.getFloat("FallHurtAmount");
            this.fallHurtMax = tag.getInt("FallHurtMax");
        } else if (this.block.isIn(BlockTags.ANVIL)) {
            this.hurtEntities = true;
        }
        if (tag.contains("DropItem", 99)) {
            this.dropItem = tag.getBoolean("DropItem");
        }
        if (tag.contains("TileEntityData", 10)) {
            this.blockEntityData = tag.getCompound("TileEntityData");
        }
        if (this.block.isAir()) {
            this.block = Blocks.SAND.getDefaultState();
        }
    }

    public World getWorldClient() {
        return this.world;
    }

    public void setHurtEntities(boolean hurtEntities) {
        this.hurtEntities = hurtEntities;
    }

    @Override
    public boolean doesRenderOnFire() {
        return false;
    }

    @Override
    public void populateCrashReport(CrashReportSection section) {
        super.populateCrashReport(section);
        section.add("Immitating BlockState", this.block.toString());
    }

    public BlockState getBlockState() {
        return this.block;
    }

    @Override
    public boolean entityDataRequiresOperator() {
        return true;
    }

    @Override
    public Packet<?> createSpawnPacket() {
        return new EntitySpawnS2CPacket(this, Block.getRawIdFromState(this.getBlockState()));
    }
}

