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
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("give").requires(arg -> arg.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("targets", EntityArgumentType.players())
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("item", ItemStackArgumentType.itemStack())
                           .executes(
                              commandContext -> execute(
                                    (ServerCommandSource)commandContext.getSource(),
                                    ItemStackArgumentType.getItemStackArgument(commandContext, "item"),
                                    EntityArgumentType.getPlayers(commandContext, "targets"),
                                    1
                                 )
                           ))
                        .then(
                           CommandManager.argument("count", IntegerArgumentType.integer(1))
                              .executes(
                                 commandContext -> execute(
                                       (ServerCommandSource)commandContext.getSource(),
                                       ItemStackArgumentType.getItemStackArgument(commandContext, "item"),
                                       EntityArgumentType.getPlayers(commandContext, "targets"),
                                       IntegerArgumentType.getInteger(commandContext, "count")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, ItemStackArgument item, Collection<ServerPlayerEntity> targets, int count) throws CommandSyntaxException {
      for (ServerPlayerEntity lv : targets) {
         int j = count;

         while (j > 0) {
            int k = Math.min(item.getItem().getMaxCount(), j);
            j -= k;
            ItemStack lv2 = item.createStack(k, false);
            boolean bl = lv.inventory.insertStack(lv2);
            if (bl && lv2.isEmpty()) {
               lv2.setCount(1);
               ItemEntity lv4 = lv.dropItem(lv2, false);
               if (lv4 != null) {
                  lv4.setDespawnImmediately();
               }

               lv.world
                  .playSound(
                     null,
                     lv.getX(),
                     lv.getY(),
                     lv.getZ(),
                     SoundEvents.ENTITY_ITEM_PICKUP,
                     SoundCategory.PLAYERS,
                     0.2F,
                     ((lv.getRandom().nextFloat() - lv.getRandom().nextFloat()) * 0.7F + 1.0F) * 2.0F
                  );
               lv.playerScreenHandler.sendContentUpdates();
            } else {
               ItemEntity lv3 = lv.dropItem(lv2, false);
               if (lv3 != null) {
                  lv3.resetPickupDelay();
                  lv3.setOwner(lv.getUuid());
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
