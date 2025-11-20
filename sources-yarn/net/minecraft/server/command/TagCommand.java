package net.minecraft.server.command;

import com.google.common.collect.Sets;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Set;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class TagCommand {
   private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.tag.add.failed"));
   private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.tag.remove.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("tag").requires(arg -> arg.hasPermissionLevel(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(
                           CommandManager.literal("add")
                              .then(
                                 CommandManager.argument("name", StringArgumentType.word())
                                    .executes(
                                       commandContext -> executeAdd(
                                             (ServerCommandSource)commandContext.getSource(),
                                             EntityArgumentType.getEntities(commandContext, "targets"),
                                             StringArgumentType.getString(commandContext, "name")
                                          )
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("remove")
                           .then(
                              CommandManager.argument("name", StringArgumentType.word())
                                 .suggests(
                                    (commandContext, suggestionsBuilder) -> CommandSource.suggestMatching(
                                          getTags(EntityArgumentType.getEntities(commandContext, "targets")), suggestionsBuilder
                                       )
                                 )
                                 .executes(
                                    commandContext -> executeRemove(
                                          (ServerCommandSource)commandContext.getSource(),
                                          EntityArgumentType.getEntities(commandContext, "targets"),
                                          StringArgumentType.getString(commandContext, "name")
                                       )
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("list")
                        .executes(
                           commandContext -> executeList(
                                 (ServerCommandSource)commandContext.getSource(), EntityArgumentType.getEntities(commandContext, "targets")
                              )
                        )
                  )
            )
      );
   }

   private static Collection<String> getTags(Collection<? extends Entity> entities) {
      Set<String> set = Sets.newHashSet();

      for (Entity lv : entities) {
         set.addAll(lv.getScoreboardTags());
      }

      return set;
   }

   private static int executeAdd(ServerCommandSource source, Collection<? extends Entity> targets, String tag) throws CommandSyntaxException {
      int i = 0;

      for (Entity lv : targets) {
         if (lv.addScoreboardTag(tag)) {
            i++;
         }
      }

      if (i == 0) {
         throw ADD_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.tag.add.success.single", tag, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.add.success.multiple", tag, targets.size()), true);
         }

         return i;
      }
   }

   private static int executeRemove(ServerCommandSource source, Collection<? extends Entity> targets, String tag) throws CommandSyntaxException {
      int i = 0;

      for (Entity lv : targets) {
         if (lv.removeScoreboardTag(tag)) {
            i++;
         }
      }

      if (i == 0) {
         throw REMOVE_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.tag.remove.success.single", tag, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.remove.success.multiple", tag, targets.size()), true);
         }

         return i;
      }
   }

   private static int executeList(ServerCommandSource source, Collection<? extends Entity> targets) {
      Set<String> set = Sets.newHashSet();

      for (Entity lv : targets) {
         set.addAll(lv.getScoreboardTags());
      }

      if (targets.size() == 1) {
         Entity lv2 = targets.iterator().next();
         if (set.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.tag.list.single.empty", lv2.getDisplayName()), false);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.list.single.success", lv2.getDisplayName(), set.size(), Texts.joinOrdered(set)), false);
         }
      } else if (set.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.tag.list.multiple.empty", targets.size()), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.tag.list.multiple.success", targets.size(), set.size(), Texts.joinOrdered(set)), false);
      }

      return set.size();
   }
}
