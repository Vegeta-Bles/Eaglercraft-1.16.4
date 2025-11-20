package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.List;
import net.minecraft.advancement.Advancement;
import net.minecraft.advancement.AdvancementProgress;
import net.minecraft.command.CommandException;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.TranslatableText;

public class AdvancementCommand {
   private static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> {
      Collection<Advancement> _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getAdvancementLoader().getAdvancements();
      return CommandSource.suggestIdentifiers(_snowmanxx.stream().map(Advancement::getId), _snowmanx);
   };

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("advancement").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .then(
                  CommandManager.literal("grant")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                          "targets", EntityArgumentType.players()
                                       )
                                       .then(
                                          CommandManager.literal("only")
                                             .then(
                                                ((RequiredArgumentBuilder)CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                                      .suggests(SUGGESTION_PROVIDER)
                                                      .executes(
                                                         _snowman -> executeAdvancement(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               EntityArgumentType.getPlayers(_snowman, "targets"),
                                                               AdvancementCommand.Operation.GRANT,
                                                               select(
                                                                  IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"),
                                                                  AdvancementCommand.Selection.ONLY
                                                               )
                                                            )
                                                      ))
                                                   .then(
                                                      CommandManager.argument("criterion", StringArgumentType.greedyString())
                                                         .suggests(
                                                            (_snowman, _snowmanx) -> CommandSource.suggestMatching(
                                                                  IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement").getCriteria().keySet(), _snowmanx
                                                               )
                                                         )
                                                         .executes(
                                                            _snowman -> executeCriterion(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  EntityArgumentType.getPlayers(_snowman, "targets"),
                                                                  AdvancementCommand.Operation.GRANT,
                                                                  IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"),
                                                                  StringArgumentType.getString(_snowman, "criterion")
                                                               )
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("from")
                                          .then(
                                             CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                                .suggests(SUGGESTION_PROVIDER)
                                                .executes(
                                                   _snowman -> executeAdvancement(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         EntityArgumentType.getPlayers(_snowman, "targets"),
                                                         AdvancementCommand.Operation.GRANT,
                                                         select(
                                                            IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.FROM
                                                         )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("until")
                                       .then(
                                          CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                             .suggests(SUGGESTION_PROVIDER)
                                             .executes(
                                                _snowman -> executeAdvancement(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      EntityArgumentType.getPlayers(_snowman, "targets"),
                                                      AdvancementCommand.Operation.GRANT,
                                                      select(
                                                         IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.UNTIL
                                                      )
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("through")
                                    .then(
                                       CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                          .suggests(SUGGESTION_PROVIDER)
                                          .executes(
                                             _snowman -> executeAdvancement(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   EntityArgumentType.getPlayers(_snowman, "targets"),
                                                   AdvancementCommand.Operation.GRANT,
                                                   select(IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.THROUGH)
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("everything")
                                 .executes(
                                    _snowman -> executeAdvancement(
                                          (ServerCommandSource)_snowman.getSource(),
                                          EntityArgumentType.getPlayers(_snowman, "targets"),
                                          AdvancementCommand.Operation.GRANT,
                                          ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getAdvancementLoader().getAdvancements()
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("revoke")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                       "targets", EntityArgumentType.players()
                                    )
                                    .then(
                                       CommandManager.literal("only")
                                          .then(
                                             ((RequiredArgumentBuilder)CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                                   .suggests(SUGGESTION_PROVIDER)
                                                   .executes(
                                                      _snowman -> executeAdvancement(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            EntityArgumentType.getPlayers(_snowman, "targets"),
                                                            AdvancementCommand.Operation.REVOKE,
                                                            select(
                                                               IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"),
                                                               AdvancementCommand.Selection.ONLY
                                                            )
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.argument("criterion", StringArgumentType.greedyString())
                                                      .suggests(
                                                         (_snowman, _snowmanx) -> CommandSource.suggestMatching(
                                                               IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement").getCriteria().keySet(), _snowmanx
                                                            )
                                                      )
                                                      .executes(
                                                         _snowman -> executeCriterion(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               EntityArgumentType.getPlayers(_snowman, "targets"),
                                                               AdvancementCommand.Operation.REVOKE,
                                                               IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"),
                                                               StringArgumentType.getString(_snowman, "criterion")
                                                            )
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("from")
                                       .then(
                                          CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                             .suggests(SUGGESTION_PROVIDER)
                                             .executes(
                                                _snowman -> executeAdvancement(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      EntityArgumentType.getPlayers(_snowman, "targets"),
                                                      AdvancementCommand.Operation.REVOKE,
                                                      select(IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.FROM)
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("until")
                                    .then(
                                       CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                          .suggests(SUGGESTION_PROVIDER)
                                          .executes(
                                             _snowman -> executeAdvancement(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   EntityArgumentType.getPlayers(_snowman, "targets"),
                                                   AdvancementCommand.Operation.REVOKE,
                                                   select(IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.UNTIL)
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("through")
                                 .then(
                                    CommandManager.argument("advancement", IdentifierArgumentType.identifier())
                                       .suggests(SUGGESTION_PROVIDER)
                                       .executes(
                                          _snowman -> executeAdvancement(
                                                (ServerCommandSource)_snowman.getSource(),
                                                EntityArgumentType.getPlayers(_snowman, "targets"),
                                                AdvancementCommand.Operation.REVOKE,
                                                select(IdentifierArgumentType.getAdvancementArgument(_snowman, "advancement"), AdvancementCommand.Selection.THROUGH)
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("everything")
                              .executes(
                                 _snowman -> executeAdvancement(
                                       (ServerCommandSource)_snowman.getSource(),
                                       EntityArgumentType.getPlayers(_snowman, "targets"),
                                       AdvancementCommand.Operation.REVOKE,
                                       ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getAdvancementLoader().getAdvancements()
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int executeAdvancement(
      ServerCommandSource source, Collection<ServerPlayerEntity> targets, AdvancementCommand.Operation operation, Collection<Advancement> selection
   ) {
      int _snowman = 0;

      for (ServerPlayerEntity _snowmanx : targets) {
         _snowman += operation.processAll(_snowmanx, selection);
      }

      if (_snowman == 0) {
         if (selection.size() == 1) {
            if (targets.size() == 1) {
               throw new CommandException(
                  new TranslatableText(
                     operation.getCommandPrefix() + ".one.to.one.failure",
                     selection.iterator().next().toHoverableText(),
                     targets.iterator().next().getDisplayName()
                  )
               );
            } else {
               throw new CommandException(
                  new TranslatableText(operation.getCommandPrefix() + ".one.to.many.failure", selection.iterator().next().toHoverableText(), targets.size())
               );
            }
         } else if (targets.size() == 1) {
            throw new CommandException(
               new TranslatableText(operation.getCommandPrefix() + ".many.to.one.failure", selection.size(), targets.iterator().next().getDisplayName())
            );
         } else {
            throw new CommandException(new TranslatableText(operation.getCommandPrefix() + ".many.to.many.failure", selection.size(), targets.size()));
         }
      } else {
         if (selection.size() == 1) {
            if (targets.size() == 1) {
               source.sendFeedback(
                  new TranslatableText(
                     operation.getCommandPrefix() + ".one.to.one.success",
                     selection.iterator().next().toHoverableText(),
                     targets.iterator().next().getDisplayName()
                  ),
                  true
               );
            } else {
               source.sendFeedback(
                  new TranslatableText(operation.getCommandPrefix() + ".one.to.many.success", selection.iterator().next().toHoverableText(), targets.size()),
                  true
               );
            }
         } else if (targets.size() == 1) {
            source.sendFeedback(
               new TranslatableText(operation.getCommandPrefix() + ".many.to.one.success", selection.size(), targets.iterator().next().getDisplayName()), true
            );
         } else {
            source.sendFeedback(new TranslatableText(operation.getCommandPrefix() + ".many.to.many.success", selection.size(), targets.size()), true);
         }

         return _snowman;
      }
   }

   private static int executeCriterion(
      ServerCommandSource source, Collection<ServerPlayerEntity> targets, AdvancementCommand.Operation operation, Advancement advancement, String criterion
   ) {
      int _snowman = 0;
      if (!advancement.getCriteria().containsKey(criterion)) {
         throw new CommandException(new TranslatableText("commands.advancement.criterionNotFound", advancement.toHoverableText(), criterion));
      } else {
         for (ServerPlayerEntity _snowmanx : targets) {
            if (operation.processEachCriterion(_snowmanx, advancement, criterion)) {
               _snowman++;
            }
         }

         if (_snowman == 0) {
            if (targets.size() == 1) {
               throw new CommandException(
                  new TranslatableText(
                     operation.getCommandPrefix() + ".criterion.to.one.failure",
                     criterion,
                     advancement.toHoverableText(),
                     targets.iterator().next().getDisplayName()
                  )
               );
            } else {
               throw new CommandException(
                  new TranslatableText(operation.getCommandPrefix() + ".criterion.to.many.failure", criterion, advancement.toHoverableText(), targets.size())
               );
            }
         } else {
            if (targets.size() == 1) {
               source.sendFeedback(
                  new TranslatableText(
                     operation.getCommandPrefix() + ".criterion.to.one.success",
                     criterion,
                     advancement.toHoverableText(),
                     targets.iterator().next().getDisplayName()
                  ),
                  true
               );
            } else {
               source.sendFeedback(
                  new TranslatableText(operation.getCommandPrefix() + ".criterion.to.many.success", criterion, advancement.toHoverableText(), targets.size()),
                  true
               );
            }

            return _snowman;
         }
      }
   }

   private static List<Advancement> select(Advancement advancement, AdvancementCommand.Selection selection) {
      List<Advancement> _snowman = Lists.newArrayList();
      if (selection.before) {
         for (Advancement _snowmanx = advancement.getParent(); _snowmanx != null; _snowmanx = _snowmanx.getParent()) {
            _snowman.add(_snowmanx);
         }
      }

      _snowman.add(advancement);
      if (selection.after) {
         addChildrenRecursivelyToList(advancement, _snowman);
      }

      return _snowman;
   }

   private static void addChildrenRecursivelyToList(Advancement parent, List<Advancement> childList) {
      for (Advancement _snowman : parent.getChildren()) {
         childList.add(_snowman);
         addChildrenRecursivelyToList(_snowman, childList);
      }
   }

   static enum Operation {
      GRANT("grant") {
         @Override
         protected boolean processEach(ServerPlayerEntity player, Advancement advancement) {
            AdvancementProgress _snowman = player.getAdvancementTracker().getProgress(advancement);
            if (_snowman.isDone()) {
               return false;
            } else {
               for (String _snowmanx : _snowman.getUnobtainedCriteria()) {
                  player.getAdvancementTracker().grantCriterion(advancement, _snowmanx);
               }

               return true;
            }
         }

         @Override
         protected boolean processEachCriterion(ServerPlayerEntity player, Advancement advancement, String criterion) {
            return player.getAdvancementTracker().grantCriterion(advancement, criterion);
         }
      },
      REVOKE("revoke") {
         @Override
         protected boolean processEach(ServerPlayerEntity player, Advancement advancement) {
            AdvancementProgress _snowman = player.getAdvancementTracker().getProgress(advancement);
            if (!_snowman.isAnyObtained()) {
               return false;
            } else {
               for (String _snowmanx : _snowman.getObtainedCriteria()) {
                  player.getAdvancementTracker().revokeCriterion(advancement, _snowmanx);
               }

               return true;
            }
         }

         @Override
         protected boolean processEachCriterion(ServerPlayerEntity player, Advancement advancement, String criterion) {
            return player.getAdvancementTracker().revokeCriterion(advancement, criterion);
         }
      };

      private final String commandPrefix;

      private Operation(String name) {
         this.commandPrefix = "commands.advancement." + name;
      }

      public int processAll(ServerPlayerEntity player, Iterable<Advancement> advancements) {
         int _snowman = 0;

         for (Advancement _snowmanx : advancements) {
            if (this.processEach(player, _snowmanx)) {
               _snowman++;
            }
         }

         return _snowman;
      }

      protected abstract boolean processEach(ServerPlayerEntity player, Advancement advancement);

      protected abstract boolean processEachCriterion(ServerPlayerEntity player, Advancement advancement, String criterion);

      protected String getCommandPrefix() {
         return this.commandPrefix;
      }
   }

   static enum Selection {
      ONLY(false, false),
      THROUGH(true, true),
      FROM(false, true),
      UNTIL(true, false),
      EVERYTHING(true, true);

      private final boolean before;
      private final boolean after;

      private Selection(boolean before, boolean after) {
         this.before = before;
         this.after = after;
      }
   }
}
