package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.JukeboxBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundEvent;
import net.minecraft.stat.Stats;
import net.minecraft.text.MutableText;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MusicDiscItem extends Item {
   private static final Map<SoundEvent, MusicDiscItem> MUSIC_DISCS = Maps.newHashMap();
   private final int comparatorOutput;
   private final SoundEvent sound;

   protected MusicDiscItem(int comparatorOutput, SoundEvent sound, Item.Settings settings) {
      super(settings);
      this.comparatorOutput = comparatorOutput;
      this.sound = sound;
      MUSIC_DISCS.put(this.sound, this);
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World lv = context.getWorld();
      BlockPos lv2 = context.getBlockPos();
      BlockState lv3 = lv.getBlockState(lv2);
      if (lv3.isOf(Blocks.JUKEBOX) && !lv3.get(JukeboxBlock.HAS_RECORD)) {
         ItemStack lv4 = context.getStack();
         if (!lv.isClient) {
            ((JukeboxBlock)Blocks.JUKEBOX).setRecord(lv, lv2, lv3, lv4);
            lv.syncWorldEvent(null, 1010, lv2, Item.getRawId(this));
            lv4.decrement(1);
            PlayerEntity lv5 = context.getPlayer();
            if (lv5 != null) {
               lv5.incrementStat(Stats.PLAY_RECORD);
            }
         }

         return ActionResult.success(lv.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   public int getComparatorOutput() {
      return this.comparatorOutput;
   }

   @Environment(EnvType.CLIENT)
   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      tooltip.add(this.getDescription().formatted(Formatting.GRAY));
   }

   @Environment(EnvType.CLIENT)
   public MutableText getDescription() {
      return new TranslatableText(this.getTranslationKey() + ".desc");
   }

   @Nullable
   @Environment(EnvType.CLIENT)
   public static MusicDiscItem bySound(SoundEvent sound) {
      return MUSIC_DISCS.get(sound);
   }

   @Environment(EnvType.CLIENT)
   public SoundEvent getSound() {
      return this.sound;
   }
}
