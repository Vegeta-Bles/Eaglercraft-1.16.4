/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  javax.annotation.Nullable
 */
package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityType;
import net.minecraft.entity.SpawnReason;
import net.minecraft.entity.passive.FishEntity;
import net.minecraft.entity.passive.TropicalFishEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.fluid.Fluid;
import net.minecraft.item.BucketItem;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FishBucketItem
extends BucketItem {
    private final EntityType<?> fishType;

    public FishBucketItem(EntityType<?> type, Fluid fluid, Item.Settings settings) {
        super(fluid, settings);
        this.fishType = type;
    }

    @Override
    public void onEmptied(World world, ItemStack stack, BlockPos pos) {
        if (world instanceof ServerWorld) {
            this.spawnFish((ServerWorld)world, stack, pos);
        }
    }

    @Override
    protected void playEmptyingSound(@Nullable PlayerEntity player, WorldAccess world, BlockPos pos) {
        world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0f, 1.0f);
    }

    private void spawnFish(ServerWorld serverWorld, ItemStack stack, BlockPos pos) {
        Entity entity = this.fishType.spawnFromItemStack(serverWorld, stack, null, pos, SpawnReason.BUCKET, true, false);
        if (entity != null) {
            ((FishEntity)entity).setFromBucket(true);
        }
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        CompoundTag compoundTag;
        if (this.fishType == EntityType.TROPICAL_FISH && (compoundTag = stack.getTag()) != null && compoundTag.contains("BucketVariantTag", 3)) {
            int n = compoundTag.getInt("BucketVariantTag");
            Formatting[] _snowman2 = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
            String _snowman3 = "color.minecraft." + TropicalFishEntity.getBaseDyeColor(n);
            String _snowman4 = "color.minecraft." + TropicalFishEntity.getPatternDyeColor(n);
            for (_snowman = 0; _snowman < TropicalFishEntity.COMMON_VARIANTS.length; ++_snowman) {
                if (n != TropicalFishEntity.COMMON_VARIANTS[_snowman]) continue;
                tooltip.add(new TranslatableText(TropicalFishEntity.getToolTipForVariant(_snowman)).formatted(_snowman2));
                return;
            }
            tooltip.add(new TranslatableText(TropicalFishEntity.getTranslationKey(n)).formatted(_snowman2));
            TranslatableText _snowman5 = new TranslatableText(_snowman3);
            if (!_snowman3.equals(_snowman4)) {
                _snowman5.append(", ").append(new TranslatableText(_snowman4));
            }
            _snowman5.formatted(_snowman2);
            tooltip.add(_snowman5);
        }
    }
}

