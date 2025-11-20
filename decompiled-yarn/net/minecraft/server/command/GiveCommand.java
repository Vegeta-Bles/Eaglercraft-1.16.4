package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import java.util.Collection;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemStackArgument;
import net.minecraft.command.argument.ItemStackArgumentType;
import net.minecraft.entity.ItemEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.text.TranslatableText;

public class GiveCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("give").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("targets", EntityArgumentType.players())
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    ItemStackArgumentType.getItemStackArgument(_snowman, "item"),
                                    EntityArgumentType.getPlayers(_snowman, "targets"),
                                    1
                                 )
                           ))
                        .then(
                           CommandManager.argument("count", IntegerArgumentType.integer(1))
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       ItemStackArgumentType.getItemStackArgument(_snowman, "item"),
                                       EntityArgumentType.getPlayers(_snowman, "targets"),
                                       IntegerArgumentType.getInteger(_snowman, "count")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, ItemStackArgument item, Collection<ServerPlayerEntity> targets, int count) throws CommandSyntaxException {
      for (ServerPlayerEntity _snowman : targets) {
         int _snowmanx = count;

         while (_snowmanx > 0) {
            int _snowmanxx = Math.min(item.getItem().getMaxCount(), _snowmanx);
            _snowmanx -= _snowmanxx;
            ItemStack _snowmanxxx = item.createStack(_snowmanxx, false);
            boolean _snowmanxxxx = _snowman.inventory.insertStack(_snowmanxxx);
            if (_snowmanxxxx && _snowmanxxx.isEmpty()) {
               _snowmanxxx.setCount(1);
               ItemEntity _snowmanxxxxx = _snowman.dropItem(_snowmanxxx, false);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxxx.setDespawnImmediately();
               }

               _snowman.world
                  .playSound(
                     null,
                     _snowman.getX(),
                     _snowman.getY(),
                     _snowman.getZ(),
                     SoundEvents.ENTITY_ITEM_PICKUP,
                     SoundCategory.PLAYERS,
                     0.2F,
                     ((_snowman.getRandom().nextFloat() - _snowman.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               _snowman.playerScreenHandler.sendContentUpdates();
            } else {
               ItemEntity _snowmanxxxxx = _snowman.dropItem(_snowmanxxx, false);
               if (_snowmanxxxxx != null) {
                  _snowmanxxxxx.resetPickupDelay();
                  _snowmanxxxxx.setOwner(_snowman.getUuid());
               }
            }
         }
      }

      if (targets.size() == 1) {
         source.sendFeedback(
            new TranslatableText(
               "commands.give.success.single", count, item.createStack(count, false).toHoverableText(), targets.iterator().next().getDisplayName()
            ),
            true
         );
      } else {
         source.sendFeedback(
            new TranslatableText("commands.give.success.single", count, item.createStack(count, false).toHoverableText(), targets.size()), true
         );
      }

      return targets.size();
   }
}
