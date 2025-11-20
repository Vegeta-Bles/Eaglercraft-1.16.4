package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.List;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.command.argument.BlockPosArgumentType;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemSlotArgumentType;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.math.BlockPos;

public class ReplaceItemCommand {
   public static final SimpleCommandExceptionType BLOCK_FAILED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.replaceitem.block.failed")
   );
   public static final DynamicCommandExceptionType SLOT_INAPPLICABLE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.replaceitem.slot.inapplicable", _snowman)
   );
   public static final Dynamic2CommandExceptionType ENTITY_FAILED_EXCEPTION = new Dynamic2CommandExceptionType(
      (_snowman, _snowmanx) -> new TranslatableText("commands.replaceitem.entity.failed", _snowman, _snowmanx)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("replaceitem").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .then(
                  CommandManager.literal("block")
                     .then(
                        CommandManager.argument("pos", BlockPosArgumentType.blockPos())
                           .then(
                              CommandManager.argument("slot", ItemSlotArgumentType.itemSlot())
                                 .then(
                                    ((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack())
                                          .executes(
                                             _snowman -> executeBlock(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                                   ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                                   ItemStackArgumentType.getItemStackArgument(_snowman, "item").createStack(1, false)
                                                )
                                          ))
                                       .then(
                                          CommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                                             .executes(
                                                _snowman -> executeBlock(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      BlockPosArgumentType.getLoadedBlockPos(_snowman, "pos"),
                                                      ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                                      ItemStackArgumentType.getItemStackArgument(_snowman, "item")
                                                         .createStack(IntegerArgumentType.getInteger(_snowman, "count"), true)
                                                   )
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("entity")
                  .then(
                     CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(
                           CommandManager.argument("slot", ItemSlotArgumentType.itemSlot())
                              .then(
                                 ((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack())
                                       .executes(
                                          _snowman -> executeEntity(
                                                (ServerCommandSource)_snowman.getSource(),
                                                EntityArgumentType.getEntities(_snowman, "targets"),
                                                ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                                ItemStackArgumentType.getItemStackArgument(_snowman, "item").createStack(1, false)
                                             )
                                       ))
                                    .then(
                                       CommandManager.argument("count", IntegerArgumentType.integer(1, 64))
                                          .executes(
                                             _snowman -> executeEntity(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   EntityArgumentType.getEntities(_snowman, "targets"),
                                                   ItemSlotArgumentType.getItemSlot(_snowman, "slot"),
                                                   ItemStackArgumentType.getItemStackArgument(_snowman, "item")
                                                      .createStack(IntegerArgumentType.getInteger(_snowman, "count"), true)
                                                )
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int executeBlock(ServerCommandSource source, BlockPos pos, int slot, ItemStack item) throws CommandSyntaxException {
      BlockEntity _snowman = source.getWorld().getBlockEntity(pos);
      if (!(_snowman instanceof Inventory)) {
         throw BLOCK_FAILED_EXCEPTION.create();
      } else {
         Inventory _snowmanx = (Inventory)_snowman;
         if (slot >= 0 && slot < _snowmanx.size()) {
            _snowmanx.setStack(slot, item);
            source.sendFeedback(new TranslatableText("commands.replaceitem.block.success", pos.getX(), pos.getY(), pos.getZ(), item.toHoverableText()), true);
            return 1;
         } else {
            throw SLOT_INAPPLICABLE_EXCEPTION.create(slot);
         }
      }
   }

   private static int executeEntity(ServerCommandSource source, Collection<? extends Entity> targets, int slot, ItemStack item) throws CommandSyntaxException {
      List<Entity> _snowman = Lists.newArrayListWithCapacity(targets.size());

      for (Entity _snowmanx : targets) {
         if (_snowmanx instanceof ServerPlayerEntity) {
            ((ServerPlayerEntity)_snowmanx).playerScreenHandler.sendContentUpdates();
         }

         if (_snowmanx.equip(slot, item.copy())) {
            _snowman.add(_snowmanx);
            if (_snowmanx instanceof ServerPlayerEntity) {
               ((ServerPlayerEntity)_snowmanx).playerScreenHandler.sendContentUpdates();
            }
         }
      }

      if (_snowman.isEmpty()) {
         throw ENTITY_FAILED_EXCEPTION.create(item.toHoverableText(), slot);
      } else {
         if (_snowman.size() == 1) {
            source.sendFeedback(
               new TranslatableText("commands.replaceitem.entity.success.single", _snowman.iterator().next().getDisplayName(), item.toHoverableText()), true
            );
         } else {
            source.sendFeedback(new TranslatableText("commands.replaceitem.entity.success.multiple", _snowman.size(), item.toHoverableText()), true);
         }

         return _snowman.size();
      }
   }
}
