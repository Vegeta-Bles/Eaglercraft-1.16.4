/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.mojang.serialization.DynamicOps
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.item;

import com.mojang.serialization.DynamicOps;
import java.util.Optional;
import net.minecraft.block.Blocks;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.item.Items;
import net.minecraft.item.Vanishable;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.NbtHelper;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.RegistryKey;
import net.minecraft.world.World;
import net.minecraft.world.poi.PointOfInterestType;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class CompassItem
extends Item
implements Vanishable {
    private static final Logger field_24670 = LogManager.getLogger();

    public CompassItem(Item.Settings settings) {
        super(settings);
    }

    public static boolean hasLodestone(ItemStack stack) {
        CompoundTag compoundTag = stack.getTag();
        return compoundTag != null && (compoundTag.contains("LodestoneDimension") || compoundTag.contains("LodestonePos"));
    }

    @Override
    public boolean hasGlint(ItemStack stack) {
        return CompassItem.hasLodestone(stack) || super.hasGlint(stack);
    }

    public static Optional<RegistryKey<World>> getLodestoneDimension(CompoundTag tag) {
        return World.CODEC.parse((DynamicOps)NbtOps.INSTANCE, (Object)tag.get("LodestoneDimension")).result();
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (world.isClient) {
            return;
        }
        if (CompassItem.hasLodestone(stack)) {
            CompoundTag compoundTag = stack.getOrCreateTag();
            if (compoundTag.contains("LodestoneTracked") && !compoundTag.getBoolean("LodestoneTracked")) {
                return;
            }
            Optional<RegistryKey<World>> _snowman2 = CompassItem.getLodestoneDimension(compoundTag);
            if (_snowman2.isPresent() && _snowman2.get() == world.getRegistryKey() && compoundTag.contains("LodestonePos") && !((ServerWorld)world).getPointOfInterestStorage().hasTypeAt(PointOfInterestType.LODESTONE, NbtHelper.toBlockPos(compoundTag.getCompound("LodestonePos")))) {
                compoundTag.remove("LodestonePos");
            }
        }
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        BlockPos blockPos = context.getBlockPos();
        World _snowman2 = context.getWorld();
        if (_snowman2.getBlockState(blockPos).isOf(Blocks.LODESTONE)) {
            _snowman2.playSound(null, blockPos, SoundEvents.ITEM_LODESTONE_COMPASS_LOCK, SoundCategory.PLAYERS, 1.0f, 1.0f);
            PlayerEntity playerEntity = context.getPlayer();
            ItemStack _snowman3 = context.getStack();
            boolean bl = _snowman = !playerEntity.abilities.creativeMode && _snowman3.getCount() == 1;
            if (_snowman) {
                this.method_27315(_snowman2.getRegistryKey(), blockPos, _snowman3.getOrCreateTag());
            } else {
                ItemStack itemStack = new ItemStack(Items.COMPASS, 1);
                CompoundTag _snowman4 = _snowman3.hasTag() ? _snowman3.getTag().copy() : new CompoundTag();
                itemStack.setTag(_snowman4);
                if (!playerEntity.abilities.creativeMode) {
                    _snowman3.decrement(1);
                }
                this.method_27315(_snowman2.getRegistryKey(), blockPos, _snowman4);
                if (!playerEntity.inventory.insertStack(itemStack)) {
                    playerEntity.dropItem(itemStack, false);
                }
            }
            return ActionResult.success(_snowman2.isClient);
        }
        return super.useOnBlock(context);
    }

    private void method_27315(RegistryKey<World> registryKey, BlockPos blockPos, CompoundTag compoundTag) {
        compoundTag.put("LodestonePos", NbtHelper.fromBlockPos(blockPos));
        World.CODEC.encodeStart((DynamicOps)NbtOps.INSTANCE, registryKey).resultOrPartial(arg_0 -> ((Logger)field_24670).error(arg_0)).ifPresent(tag -> compoundTag.put("LodestoneDimension", (Tag)tag));
        compoundTag.putBoolean("LodestoneTracked", true);
    }

    @Override
    public String getTranslationKey(ItemStack stack) {
        return CompassItem.hasLodestone(stack) ? "item.minecraft.lodestone_compass" : super.getTranslationKey(stack);
    }
}

