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
import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class FishBucketItem extends BucketItem {
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
      world.playSound(player, pos, SoundEvents.ITEM_BUCKET_EMPTY_FISH, SoundCategory.NEUTRAL, 1.0F, 1.0F);
   }

   private void spawnFish(ServerWorld _snowman, ItemStack stack, BlockPos pos) {
      Entity _snowmanx = this.fishType.spawnFromItemStack(_snowman, stack, null, pos, SpawnReason.BUCKET, true, false);
      if (_snowmanx != null) {
         ((FishEntity)_snowmanx).setFromBucket(true);
      }
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      if (this.fishType == EntityType.TROPICAL_FISH) {
         CompoundTag _snowman = stack.getTag();
         if (_snowman != null && _snowman.contains("BucketVariantTag", 3)) {
            int _snowmanx = _snowman.getInt("BucketVariantTag");
            Formatting[] _snowmanxx = new Formatting[]{Formatting.ITALIC, Formatting.GRAY};
            String _snowmanxxx = "color.minecraft." + TropicalFishEntity.getBaseDyeColor(_snowmanx);
            String _snowmanxxxx = "color.minecraft." + TropicalFishEntity.getPatternDyeColor(_snowmanx);

            for (int _snowmanxxxxx = 0; _snowmanxxxxx < TropicalFishEntity.COMMON_VARIANTS.length; _snowmanxxxxx++) {
               if (_snowmanx == TropicalFishEntity.COMMON_VARIANTS[_snowmanxxxxx]) {
                  tooltip.add(new TranslatableText(TropicalFishEntity.getToolTipForVariant(_snowmanxxxxx)).formatted(_snowmanxx));
                  return;
               }
            }

            tooltip.add(new TranslatableText(TropicalFishEntity.getTranslationKey(_snowmanx)).formatted(_snowmanxx));
            MutableText _snowmanxxxxxx = new TranslatableText(_snowmanxxx);
            if (!_snowmanxxx.equals(_snowmanxxxx)) {
               _snowmanxxxxxx.append(", ").append(new TranslatableText(_snowmanxxxx));
            }

            _snowmanxxxxxx.formatted(_snowmanxx);
            tooltip.add(_snowmanxxxxxx);
         }
      }
   }
}
