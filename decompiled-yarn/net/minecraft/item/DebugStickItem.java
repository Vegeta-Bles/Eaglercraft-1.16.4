package net.minecraft.item;

import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.MessageType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.Util;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import net.minecraft.world.WorldAccess;

public class DebugStickItem extends Item {
   public DebugStickItem(Item.Settings _snowman) {
      super(_snowman);
   }

   @Override
   public boolean hasGlint(ItemStack stack) {
      return true;
   }

   @Override
   public boolean canMine(BlockState state, World world, BlockPos pos, PlayerEntity miner) {
      if (!world.isClient) {
         this.use(miner, state, world, pos, false, miner.getStackInHand(Hand.MAIN_HAND));
      }

      return false;
   }

   @Override
   public ActionResult useOnBlock(ItemUsageContext context) {
      PlayerEntity _snowman = context.getPlayer();
      World _snowmanx = context.getWorld();
      if (!_snowmanx.isClient && _snowman != null) {
         BlockPos _snowmanxx = context.getBlockPos();
         this.use(_snowman, _snowmanx.getBlockState(_snowmanxx), _snowmanx, _snowmanxx, true, context.getStack());
      }

      return ActionResult.success(_snowmanx.isClient);
   }

   private void use(PlayerEntity player, BlockState state, WorldAccess world, BlockPos pos, boolean update, ItemStack stack) {
      if (player.isCreativeLevelTwoOp()) {
         Block _snowman = state.getBlock();
         StateManager<Block, BlockState> _snowmanx = _snowman.getStateManager();
         Collection<Property<?>> _snowmanxx = _snowmanx.getProperties();
         String _snowmanxxx = Registry.BLOCK.getId(_snowman).toString();
         if (_snowmanxx.isEmpty()) {
            sendMessage(player, new TranslatableText(this.getTranslationKey() + ".empty", _snowmanxxx));
         } else {
            CompoundTag _snowmanxxxx = stack.getOrCreateSubTag("DebugProperty");
            String _snowmanxxxxx = _snowmanxxxx.getString(_snowmanxxx);
            Property<?> _snowmanxxxxxx = _snowmanx.getProperty(_snowmanxxxxx);
            if (update) {
               if (_snowmanxxxxxx == null) {
                  _snowmanxxxxxx = _snowmanxx.iterator().next();
               }

               BlockState _snowmanxxxxxxx = cycle(state, _snowmanxxxxxx, player.shouldCancelInteraction());
               world.setBlockState(pos, _snowmanxxxxxxx, 18);
               sendMessage(player, new TranslatableText(this.getTranslationKey() + ".update", _snowmanxxxxxx.getName(), getValueString(_snowmanxxxxxxx, _snowmanxxxxxx)));
            } else {
               _snowmanxxxxxx = cycle(_snowmanxx, _snowmanxxxxxx, player.shouldCancelInteraction());
               String _snowmanxxxxxxx = _snowmanxxxxxx.getName();
               _snowmanxxxx.putString(_snowmanxxx, _snowmanxxxxxxx);
               sendMessage(player, new TranslatableText(this.getTranslationKey() + ".select", _snowmanxxxxxxx, getValueString(state, _snowmanxxxxxx)));
            }
         }
      }
   }

   private static <T extends Comparable<T>> BlockState cycle(BlockState state, Property<T> property, boolean inverse) {
      return state.with(property, cycle(property.getValues(), state.get(property), inverse));
   }

   private static <T> T cycle(Iterable<T> elements, @Nullable T current, boolean inverse) {
      return inverse ? Util.previous(elements, current) : Util.next(elements, current);
   }

   private static void sendMessage(PlayerEntity player, Text message) {
      ((ServerPlayerEntity)player).sendMessage(message, MessageType.GAME_INFO, Util.NIL_UUID);
   }

   private static <T extends Comparable<T>> String getValueString(BlockState state, Property<T> property) {
      return property.name(state.get(property));
   }
}
