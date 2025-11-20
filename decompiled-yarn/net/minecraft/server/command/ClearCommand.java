package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import java.util.function.Predicate;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.ItemPredicateArgumentType;
import net.minecraft.item.ItemStack;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class ClearCommand {
   private static final DynamicCommandExceptionType FAILED_SINGLE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("clear.failed.single", _snowman)
   );
   private static final DynamicCommandExceptionType FAILED_MULTIPLE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("clear.failed.multiple", _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("clear").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .executes(
                  _snowman -> execute((ServerCommandSource)_snowman.getSource(), Collections.singleton(((ServerCommandSource)_snowman.getSource()).getPlayer()), _snowmanx -> true, -1)
               ))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.players())
                     .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), EntityArgumentType.getPlayers(_snowman, "targets"), _snowmanx -> true, -1)))
                  .then(
                     ((RequiredArgumentBuilder)CommandManager.argument("item", ItemPredicateArgumentType.itemPredicate())
                           .executes(
                              _snowman -> execute(
                                    (ServerCommandSource)_snowman.getSource(),
                                    EntityArgumentType.getPlayers(_snowman, "targets"),
                                    ItemPredicateArgumentType.getItemPredicate(_snowman, "item"),
                                    -1
                                 )
                           ))
                        .then(
                           CommandManager.argument("maxCount", IntegerArgumentType.integer(0))
                              .executes(
                                 _snowman -> execute(
                                       (ServerCommandSource)_snowman.getSource(),
                                       EntityArgumentType.getPlayers(_snowman, "targets"),
                                       ItemPredicateArgumentType.getItemPredicate(_snowman, "item"),
                                       IntegerArgumentType.getInteger(_snowman, "maxCount")
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Predicate<ItemStack> item, int maxCount) throws CommandSyntaxException {
      int _snowman = 0;

      for (ServerPlayerEntity _snowmanx : targets) {
         _snowman += _snowmanx.inventory.remove(item, maxCount, _snowmanx.playerScreenHandler.method_29281());
         _snowmanx.currentScreenHandler.sendContentUpdates();
         _snowmanx.playerScreenHandler.onContentChanged(_snowmanx.inventory);
         _snowmanx.updateCursorStack();
      }

      if (_snowman == 0) {
         if (targets.size() == 1) {
            throw FAILED_SINGLE_EXCEPTION.create(targets.iterator().next().getName());
         } else {
            throw FAILED_MULTIPLE_EXCEPTION.create(targets.size());
         }
      } else {
         if (maxCount == 0) {
            if (targets.size() == 1) {
               source.sendFeedback(new TranslatableText("commands.clear.test.single", _snowman, targets.iterator().next().getDisplayName()), true);
            } else {
               source.sendFeedback(new TranslatableText("commands.clear.test.multiple", _snowman, targets.size()), true);
            }
         } else if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.clear.success.single", _snowman, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.clear.success.multiple", _snowman, targets.size()), true);
         }

         return _snowman;
      }
   }
}
