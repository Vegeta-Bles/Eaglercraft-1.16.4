package net.minecraft.item;

import java.util.List;
import javax.annotation.Nullable;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.block.LecternBlock;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.StringTag;
import net.minecraft.nbt.Tag;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.stat.Stats;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class WrittenBookItem extends Item {
   public WrittenBookItem(Item.Settings _snowman) {
      super(_snowman);
   }

   public static boolean isValid(@Nullable CompoundTag tag) {
      if (!WritableBookItem.isValid(tag)) {
         return false;
      } else if (!tag.contains("title", 8)) {
         return false;
      } else {
         String _snowman = tag.getString("title");
         return _snowman.length() > 32 ? false : tag.contains("author", 8);
      }
   }

   public static int getGeneration(ItemStack stack) {
      return stack.getTag().getInt("generation");
   }

   public static int getPageCount(ItemStack stack) {
      CompoundTag _snowman = stack.getTag();
      return _snowman != null ? _snowman.getList("pages", 8).size() : 0;
   }

   @Override
   public Text getName(ItemStack stack) {
      if (stack.hasTag()) {
         CompoundTag _snowman = stack.getTag();
         String _snowmanx = _snowman.getString("title");
         if (!ChatUtil.isEmpty(_snowmanx)) {
            return new LiteralText(_snowmanx);
         }
      }

      return super.getName(stack);
   }

   @Override
   public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
      if (stack.hasTag()) {
         CompoundTag _snowman = stack.getTag();
         String _snowmanx = _snowman.getString("author");
         if (!ChatUtil.isEmpty(_snowmanx)) {
            tooltip.add(new TranslatableText("book.byAuthor", _snowmanx).formatted(Formatting.GRAY));
         }

         tooltip.add(new TranslatableText("book.generation." + _snowman.getInt("generation")).formatted(Formatting.GRAY));
      }
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      World _snowman = context.getWorld();
      BlockPos _snowmanx = context.getBlockPos();
      BlockState _snowmanxx = _snowman.getBlockState(_snowmanx);
      if (_snowmanxx.isOf(Blocks.LECTERN)) {
         return LecternBlock.putBookIfAbsent(_snowman, _snowmanx, _snowmanxx, context.getStack()) ? ActionResult.success(_snowman.isClient) : ActionResult.PASS;
      } else {
         return ActionResult.PASS;
      }
   }

   @Override
   public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
      ItemStack _snowman = user.getStackInHand(hand);
      user.openEditBookScreen(_snowman, hand);
      user.incrementStat(Stats.USED.getOrCreateStat(this));
      return TypedActionResult.success(_snowman, world.isClient());
   }

   public static boolean resolve(ItemStack book, @Nullable ServerCommandSource commandSource, @Nullable PlayerEntity player) {
      CompoundTag _snowman = book.getTag();
      if (_snowman != null && !_snowman.getBoolean("resolved")) {
         _snowman.putBoolean("resolved", true);
         if (!isValid(_snowman)) {
            return false;
         } else {
            ListTag _snowmanx = _snowman.getList("pages", 8);

            for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
               String _snowmanxxx = _snowmanx.getString(_snowmanxx);

               Text _snowmanxxxx;
               try {
                  _snowmanxxxx = Text.Serializer.fromLenientJson(_snowmanxxx);
                  _snowmanxxxx = Texts.parse(commandSource, _snowmanxxxx, player, 0);
               } catch (Exception var9) {
                  _snowmanxxxx = new LiteralText(_snowmanxxx);
               }

               _snowmanx.set(_snowmanxx, (Tag)StringTag.of(Text.Serializer.toJson(_snowmanxxxx)));
            }

            _snowman.put("pages", _snowmanx);
            return true;
         }
      } else {
         return false;
      }
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }
}
