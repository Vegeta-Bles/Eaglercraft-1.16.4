package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.AbstractBannerBlock;
import net.minecraft.block.Block;
import net.minecraft.block.entity.BannerPattern;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.apache.commons.lang3.Validate;

public class BannerItem extends WallStandingBlockItem {
   public BannerItem(Block _snowman, Block _snowman, Item.Settings _snowman) {
      super(_snowman, _snowman, _snowman);
      Validate.isInstanceOf(AbstractBannerBlock.class, _snowman);
      Validate.isInstanceOf(AbstractBannerBlock.class, _snowman);
   }

   public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
      CompoundTag _snowman = stack.getSubTag("BlockEntityTag");
      if (_snowman != null && _snowman.contains("Patterns")) {
         ListTag _snowmanx = _snowman.getList("Patterns", 10);

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size() && _snowmanxx < 6; _snowmanxx++) {
            CompoundTag _snowmanxxx = _snowmanx.getCompound(_snowmanxx);
            DyeColor _snowmanxxxx = DyeColor.byId(_snowmanxxx.getInt("Color"));
            BannerPattern _snowmanxxxxx = BannerPattern.byId(_snowmanxxx.getString("Pattern"));
            if (_snowmanxxxxx != null) {
               tooltip.add(new TranslatableText("block.minecraft.banner." + _snowmanxxxxx.getName() + '.' + _snowmanxxxx.getName()).formatted(Formatting.GRAY));
            }
         }
      }
   }

   public DyeColor getColor() {
      return ((AbstractBannerBlock)this.getBlock()).getColor();
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      appendBannerTooltip(stack, tooltip);
   }
}
