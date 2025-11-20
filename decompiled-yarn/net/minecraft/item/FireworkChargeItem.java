package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.text.LiteralText;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.DyeColor;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;

public class FireworkChargeItem extends Item {
   public FireworkChargeItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      CompoundTag _snowman = stack.getSubTag("Explosion");
      if (_snowman != null) {
         appendFireworkTooltip(_snowman, tooltip);
      }
   }

   public static void appendFireworkTooltip(CompoundTag tag, List<Text> tooltip) {
      FireworkItem.Type _snowman = FireworkItem.Type.byId(tag.getByte("Type"));
      tooltip.add(new TranslatableText("item.minecraft.firework_star.shape." + _snowman.getName()).formatted(Formatting.GRAY));
      int[] _snowmanx = tag.getIntArray("Colors");
      if (_snowmanx.length > 0) {
         tooltip.add(appendColors(new LiteralText("").formatted(Formatting.GRAY), _snowmanx));
      }

      int[] _snowmanxx = tag.getIntArray("FadeColors");
      if (_snowmanxx.length > 0) {
         tooltip.add(appendColors(new TranslatableText("item.minecraft.firework_star.fade_to").append(" ").formatted(Formatting.GRAY), _snowmanxx));
      }

      if (tag.getBoolean("Trail")) {
         tooltip.add(new TranslatableText("item.minecraft.firework_star.trail").formatted(Formatting.GRAY));
      }

      if (tag.getBoolean("Flicker")) {
         tooltip.add(new TranslatableText("item.minecraft.firework_star.flicker").formatted(Formatting.GRAY));
      }
   }

   private static Text appendColors(MutableText line, int[] colors) {
      for (int _snowman = 0; _snowman < colors.length; _snowman++) {
         if (_snowman > 0) {
            line.append(", ");
         }

         line.append(getColorText(colors[_snowman]));
      }

      return line;
   }

   private static Text getColorText(int color) {
      DyeColor _snowman = DyeColor.byFireworkColor(color);
      return _snowman == null
         ? new TranslatableText("item.minecraft.firework_star.custom_color")
         : new TranslatableText("item.minecraft.firework_star." + _snowman.getName());
   }
}
