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
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("tag").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("targets", EntityArgumentType.entities())
                        .then(
                           CommandManager.literal("add")
                              .then(
                                 CommandManager.argument("name", StringArgumentType.word())
                                    .executes(
                                       _snowman -> executeAdd(
                                             (ServerCommandSource)_snowman.getSource(),
                                             EntityArgumentType.getEntities(_snowman, "targets"),
                                             StringArgumentType.getString(_snowman, "name")
                                          )
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("remove")
                           .then(
                              CommandManager.argument("name", StringArgumentType.word())
                                 .suggests((_snowman, _snowmanx) -> CommandSource.suggestMatching(getTags(EntityArgumentType.getEntities(_snowman, "targets")), _snowmanx))
                                 .executes(
                                    _snowman -> executeRemove(
                                          (ServerCommandSource)_snowman.getSource(),
                                          EntityArgumentType.getEntities(_snowman, "targets"),
                                          StringArgumentType.getString(_snowman, "name")
                                       )
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("list")
                        .executes(_snowman -> executeList((ServerCommandSource)_snowman.getSource(), EntityArgumentType.getEntities(_snowman, "targets")))
                  )
            )
      );
   }

   private static Collection<String> getTags(Collection<? extends Entity> entities) {
      Set<String> _snowman = Sets.newHashSet();

      for (Entity _snowmanx : entities) {
         _snowman.addAll(_snowmanx.getScoreboardTags());
      }

      return _snowman;
   }

   private static int executeAdd(ServerCommandSource source, Collection<? extends Entity> targets, String tag) throws CommandSyntaxException {
      int _snowman = 0;

      for (Entity _snowmanx : targets) {
         if (_snowmanx.addScoreboardTag(tag)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw ADD_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.tag.add.success.single", tag, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.add.success.multiple", tag, targets.size()), true);
         }

         return _snowman;
      }
   }

   private static int executeRemove(ServerCommandSource source, Collection<? extends Entity> targets, String tag) throws CommandSyntaxException {
      int _snowman = 0;

      for (Entity _snowmanx : targets) {
         if (_snowmanx.removeScoreboardTag(tag)) {
            _snowman++;
         }
      }

      if (_snowman == 0) {
         throw REMOVE_FAILED_EXCEPTION.create();
      } else {
         if (targets.size() == 1) {
            source.sendFeedback(new TranslatableText("commands.tag.remove.success.single", tag, targets.iterator().next().getDisplayName()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.remove.success.multiple", tag, targets.size()), true);
         }

         return _snowman;
      }
   }

   private static int executeList(ServerCommandSource source, Collection<? extends Entity> targets) {
      Set<String> _snowman = Sets.newHashSet();

      for (Entity _snowmanx : targets) {
         _snowman.addAll(_snowmanx.getScoreboardTags());
      }

      if (targets.size() == 1) {
         Entity _snowmanx = targets.iterator().next();
         if (_snowman.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.tag.list.single.empty", _snowmanx.getDisplayName()), false);
         } else {
            source.sendFeedback(new TranslatableText("commands.tag.list.single.success", _snowmanx.getDisplayName(), _snowman.size(), Texts.joinOrdered(_snowman)), false);
         }
      } else if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.tag.list.multiple.empty", targets.size()), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.tag.list.multiple.success", targets.size(), _snowman.size(), Texts.joinOrdered(_snowman)), false);
      }

      return _snowman.size();
   }
}
