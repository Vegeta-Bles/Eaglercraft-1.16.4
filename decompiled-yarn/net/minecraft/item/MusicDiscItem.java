package net.minecraft.item;

import com.google.common.collect.Maps;
import java.util.List;
import java.util.Map;
import javax.annotation.Nullable;
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
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (_snowmanxx.isOf(Blocks.JUKEBOX) && !_snowmanxx.get(JukeboxBlock.HAS_RECORD)) {
         ItemStack _snowmanxxx = context.getStack();
         if (!_snowman.isClient) {
            ((JukeboxBlock)Blocks.JUKEBOX).setRecord(_snowman, _snowmanx, _snowmanxx, _snowmanxxx);
            _snowman.syncWorldEvent(null, 1010, _snowmanx, Item.getRawId(this));
            _snowmanxxx.decrement(1);
            PlayerEntity _snowmanxxxx = context.getPlayer();
            if (_snowmanxxxx != null) {
               _snowmanxxxx.incrementStat(Stats.PLAY_RECORD);
            }
         }

         return ActionResult.success(_snowman.isClient);
      } else {
         return ActionResult.PASS;
      }
   }

   public int getComparatorOutput() {
      return this.comparatorOutput;
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      tooltip.add(this.getDescription().formatted(Formatting.GRAY));
   }

   public MutableText getDescription() {
      return new TranslatableText(this.getTranslationKey() + ".desc");
   }

   @Nullable
   public static MusicDiscItem bySound(SoundEvent sound) {
      return MUSIC_DISCS.get(sound);
   }

   public SoundEvent getSound() {
      return this.sound;
   }
}
