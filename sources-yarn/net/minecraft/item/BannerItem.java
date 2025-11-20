package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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
   public BannerItem(Block arg, Block arg2, Item.Settings arg3) {
      super(arg, arg2, arg3);
      Validate.isInstanceOf(AbstractBannerBlock.class, arg);
      Validate.isInstanceOf(AbstractBannerBlock.class, arg2);
   }

   @Environment(EnvType.CLIENT)
   public static void appendBannerTooltip(ItemStack stack, List<Text> tooltip) {
      CompoundTag lv = stack.getSubTag("BlockEntityTag");
      if (lv != null && lv.contains("Patterns")) {
         ListTag lv2 = lv.getList("Patterns", 10);

         for (int i = 0; i < lv2.size() && i < 6; i++) {
            CompoundTag lv3 = lv2.getCompound(i);
            DyeColor lv4 = DyeColor.byId(lv3.getInt("Color"));
            BannerPattern lv5 = BannerPattern.byId(lv3.getString("Pattern"));
            if (lv5 != null) {
               tooltip.add(new TranslatableText("block.minecraft.banner." + lv5.getName() + '.' + lv4.getName()).formatted(Formatting.GRAY));
            }
         }
      }
   }

   public DyeColor getColor() {
      return ((AbstractBannerBlock)this.getBlock()).getColor();
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      appendBannerTooltip(stack, tooltip);
   }
}
