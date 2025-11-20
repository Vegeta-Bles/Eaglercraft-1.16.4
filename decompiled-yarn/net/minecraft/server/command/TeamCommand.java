package net.minecraft.server.command;

import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.argument.ColorArgumentType;
import net.minecraft.command.argument.ScoreHolderArgumentType;
import net.minecraft.command.argument.TeamArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.scoreboard.AbstractTeam;
import net.minecraft.scoreboard.Scoreboard;
import net.minecraft.scoreboard.Team;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class TeamCommand {
   private static final SimpleCommandExceptionType ADD_DUPLICATE_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.team.add.duplicate"));
   private static final DynamicCommandExceptionType ADD_LONG_NAME_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.team.add.longName", _snowman)
   );
   private static final SimpleCommandExceptionType EMPTY_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.empty.unchanged")
   );
   private static final SimpleCommandExceptionType OPTION_NAME_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.name.unchanged")
   );
   private static final SimpleCommandExceptionType OPTION_COLOR_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.color.unchanged")
   );
   private static final SimpleCommandExceptionType OPTION_FRIENDLY_FIRE_ALREADY_ENABLED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.friendlyfire.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType OPTION_FRIENDLY_FIRE_ALREADY_DISABLED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.friendlyfire.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType OPTION_SEE_FRIENDLY_INVISIBLES_ALREADY_ENABLED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.seeFriendlyInvisibles.alreadyEnabled")
   );
   private static final SimpleCommandExceptionType OPTION_SEE_FRIENDLY_INVISIBLES_ALREADY_DISABLED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.seeFriendlyInvisibles.alreadyDisabled")
   );
   private static final SimpleCommandExceptionType OPTION_NAMETAG_VISIBILITY_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.nametagVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType OPTION_DEATH_MESSAGE_VISIBILITY_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.deathMessageVisibility.unchanged")
   );
   private static final SimpleCommandExceptionType OPTION_COLLISION_RULE_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.team.option.collisionRule.unchanged")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                    "team"
                                 )
                                 .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                              .then(
                                 ((LiteralArgumentBuilder)CommandManager.literal("list").executes(_snowman -> executeListTeams((ServerCommandSource)_snowman.getSource())))
                                    .then(
                                       CommandManager.argument("team", TeamArgumentType.team())
                                          .executes(_snowman -> executeListMembers((ServerCommandSource)_snowman.getSource(), TeamArgumentType.getTeam(_snowman, "team")))
                                    )
                              ))
                           .then(
                              CommandManager.literal("add")
                                 .then(
                                    ((RequiredArgumentBuilder)CommandManager.argument("team", StringArgumentType.word())
                                          .executes(_snowman -> executeAdd((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "team"))))
                                       .then(
                                          CommandManager.argument("displayName", TextArgumentType.text())
                                             .executes(
                                                _snowman -> executeAdd(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      StringArgumentType.getString(_snowman, "team"),
                                                      TextArgumentType.getTextArgument(_snowman, "displayName")
                                                   )
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("remove")
                              .then(
                                 CommandManager.argument("team", TeamArgumentType.team())
                                    .executes(_snowman -> executeRemove((ServerCommandSource)_snowman.getSource(), TeamArgumentType.getTeam(_snowman, "team")))
                              )
                        ))
                     .then(
                        CommandManager.literal("empty")
                           .then(
                              CommandManager.argument("team", TeamArgumentType.team())
                                 .executes(_snowman -> executeEmpty((ServerCommandSource)_snowman.getSource(), TeamArgumentType.getTeam(_snowman, "team")))
                           )
                     ))
                  .then(
                     CommandManager.literal("join")
                        .then(
                           ((RequiredArgumentBuilder)CommandManager.argument("team", TeamArgumentType.team())
                                 .executes(
                                    _snowman -> executeJoin(
                                          (ServerCommandSource)_snowman.getSource(),
                                          TeamArgumentType.getTeam(_snowman, "team"),
                                          Collections.singleton(((ServerCommandSource)_snowman.getSource()).getEntityOrThrow().getEntityName())
                                       )
                                 ))
                              .then(
                                 CommandManager.argument("members", ScoreHolderArgumentType.scoreHolders())
                                    .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                                    .executes(
                                       _snowman -> executeJoin(
                                             (ServerCommandSource)_snowman.getSource(),
                                             TeamArgumentType.getTeam(_snowman, "team"),
                                             ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "members")
                                          )
                                    )
                              )
                        )
                  ))
               .then(
                  CommandManager.literal("leave")
                     .then(
                        CommandManager.argument("members", ScoreHolderArgumentType.scoreHolders())
                           .suggests(ScoreHolderArgumentType.SUGGESTION_PROVIDER)
                           .executes(_snowman -> executeLeave((ServerCommandSource)_snowman.getSource(), ScoreHolderArgumentType.getScoreboardScoreHolders(_snowman, "members")))
                     )
               ))
            .then(
               CommandManager.literal("modify")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                   "team", TeamArgumentType.team()
                                                )
                                                .then(
                                                   CommandManager.literal("displayName")
                                                      .then(
                                                         CommandManager.argument("displayName", TextArgumentType.text())
                                                            .executes(
                                                               _snowman -> executeModifyDisplayName(
                                                                     (ServerCommandSource)_snowman.getSource(),
                                                                     TeamArgumentType.getTeam(_snowman, "team"),
                                                                     TextArgumentType.getTextArgument(_snowman, "displayName")
                                                                  )
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("color")
                                                   .then(
                                                      CommandManager.argument("value", ColorArgumentType.color())
                                                         .executes(
                                                            _snowman -> executeModifyColor(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  TeamArgumentType.getTeam(_snowman, "team"),
                                                                  ColorArgumentType.getColor(_snowman, "value")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("friendlyFire")
                                                .then(
                                                   CommandManager.argument("allowed", BoolArgumentType.bool())
                                                      .executes(
                                                         _snowman -> executeModifyFriendlyFire(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               TeamArgumentType.getTeam(_snowman, "team"),
                                                               BoolArgumentType.getBool(_snowman, "allowed")
                                                            )
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("seeFriendlyInvisibles")
                                             .then(
                                                CommandManager.argument("allowed", BoolArgumentType.bool())
                                                   .executes(
                                                      _snowman -> executeModifySeeFriendlyInvisibles(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            TeamArgumentType.getTeam(_snowman, "team"),
                                                            BoolArgumentType.getBool(_snowman, "allowed")
                                                         )
                                                   )
                                             )
                                       ))
                                    .then(
                                       ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("nametagVisibility")
                                                   .then(
                                                      CommandManager.literal("never")
                                                         .executes(
                                                            _snowman -> executeModifyNametagVisibility(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  TeamArgumentType.getTeam(_snowman, "team"),
                                                                  AbstractTeam.VisibilityRule.NEVER
                                                               )
                                                         )
                                                   ))
                                                .then(
                                                   CommandManager.literal("hideForOtherTeams")
                                                      .executes(
                                                         _snowman -> executeModifyNametagVisibility(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               TeamArgumentType.getTeam(_snowman, "team"),
                                                               AbstractTeam.VisibilityRule.HIDE_FOR_OTHER_TEAMS
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("hideForOwnTeam")
                                                   .executes(
                                                      _snowman -> executeModifyNametagVisibility(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            TeamArgumentType.getTeam(_snowman, "team"),
                                                            AbstractTeam.VisibilityRule.HIDE_FOR_OWN_TEAM
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("always")
                                                .executes(
                                                   _snowman -> executeModifyNametagVisibility(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         TeamArgumentType.getTeam(_snowman, "team"),
                                                         AbstractTeam.VisibilityRule.ALWAYS
                                                      )
                                                )
                                          )
                                    ))
                                 .then(
                                    ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("deathMessageVisibility")
                                                .then(
                                                   CommandManager.literal("never")
                                                      .executes(
                                                         _snowman -> executeModifyDeathMessageVisibility(
                                                               (ServerCommandSource)_snowman.getSource(),
                                                               TeamArgumentType.getTeam(_snowman, "team"),
                                                               AbstractTeam.VisibilityRule.NEVER
                                                            )
                                                      )
                                                ))
                                             .then(
                                                CommandManager.literal("hideForOtherTeams")
                                                   .executes(
                                                      _snowman -> executeModifyDeathMessageVisibility(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            TeamArgumentType.getTeam(_snowman, "team"),
                                                            AbstractTeam.VisibilityRule.HIDE_FOR_OTHER_TEAMS
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("hideForOwnTeam")
                                                .executes(
                                                   _snowman -> executeModifyDeathMessageVisibility(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         TeamArgumentType.getTeam(_snowman, "team"),
                                                         AbstractTeam.VisibilityRule.HIDE_FOR_OWN_TEAM
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("always")
                                             .executes(
                                                _snowman -> executeModifyDeathMessageVisibility(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      TeamArgumentType.getTeam(_snowman, "team"),
                                                      AbstractTeam.VisibilityRule.ALWAYS
                                                   )
                                             )
                                       )
                                 ))
                              .then(
                                 ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("collisionRule")
                                             .then(
                                                CommandManager.literal("never")
                                                   .executes(
                                                      _snowman -> executeModifyCollisionRule(
                                                            (ServerCommandSource)_snowman.getSource(),
                                                            TeamArgumentType.getTeam(_snowman, "team"),
                                                            AbstractTeam.CollisionRule.NEVER
                                                         )
                                                   )
                                             ))
                                          .then(
                                             CommandManager.literal("pushOwnTeam")
                                                .executes(
                                                   _snowman -> executeModifyCollisionRule(
                                                         (ServerCommandSource)_snowman.getSource(),
                                                         TeamArgumentType.getTeam(_snowman, "team"),
                                                         AbstractTeam.CollisionRule.PUSH_OWN_TEAM
                                                      )
                                                )
                                          ))
                                       .then(
                                          CommandManager.literal("pushOtherTeams")
                                             .executes(
                                                _snowman -> executeModifyCollisionRule(
                                                      (ServerCommandSource)_snowman.getSource(),
                                                      TeamArgumentType.getTeam(_snowman, "team"),
                                                      AbstractTeam.CollisionRule.PUSH_OTHER_TEAMS
                                                   )
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("always")
                                          .executes(
                                             _snowman -> executeModifyCollisionRule(
                                                   (ServerCommandSource)_snowman.getSource(), TeamArgumentType.getTeam(_snowman, "team"), AbstractTeam.CollisionRule.ALWAYS
                                                )
                                          )
                                    )
                              ))
                           .then(
                              CommandManager.literal("prefix")
                                 .then(
                                    CommandManager.argument("prefix", TextArgumentType.text())
                                       .executes(
                                          _snowman -> executeModifyPrefix(
                                                (ServerCommandSource)_snowman.getSource(),
                                                TeamArgumentType.getTeam(_snowman, "team"),
                                                TextArgumentType.getTextArgument(_snowman, "prefix")
                                             )
                                       )
                                 )
                           ))
                        .then(
                           CommandManager.literal("suffix")
                              .then(
                                 CommandManager.argument("suffix", TextArgumentType.text())
                                    .executes(
                                       _snowman -> executeModifySuffix(
                                             (ServerCommandSource)_snowman.getSource(),
                                             TeamArgumentType.getTeam(_snowman, "team"),
                                             TextArgumentType.getTextArgument(_snowman, "suffix")
                                          )
                                    )
                              )
                        )
                  )
            )
      );
   }

   private static int executeLeave(ServerCommandSource source, Collection<String> members) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();

      for (String _snowmanx : members) {
         _snowman.clearPlayerTeam(_snowmanx);
      }

      if (members.size() == 1) {
         source.sendFeedback(new TranslatableText("commands.team.leave.success.single", members.iterator().next()), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.team.leave.success.multiple", members.size()), true);
      }

      return members.size();
   }

   private static int executeJoin(ServerCommandSource source, Team team, Collection<String> members) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();

      for (String _snowmanx : members) {
         _snowman.addPlayerToTeam(_snowmanx, team);
      }

      if (members.size() == 1) {
         source.sendFeedback(new TranslatableText("commands.team.join.success.single", members.iterator().next(), team.getFormattedName()), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.team.join.success.multiple", members.size(), team.getFormattedName()), true);
      }

      return members.size();
   }

   private static int executeModifyNametagVisibility(ServerCommandSource source, Team team, AbstractTeam.VisibilityRule visibility) throws CommandSyntaxException {
      if (team.getNameTagVisibilityRule() == visibility) {
         throw OPTION_NAMETAG_VISIBILITY_UNCHANGED_EXCEPTION.create();
      } else {
         team.setNameTagVisibilityRule(visibility);
         source.sendFeedback(
            new TranslatableText("commands.team.option.nametagVisibility.success", team.getFormattedName(), visibility.getTranslationKey()), true
         );
         return 0;
      }
   }

   private static int executeModifyDeathMessageVisibility(ServerCommandSource source, Team team, AbstractTeam.VisibilityRule visibility) throws CommandSyntaxException {
      if (team.getDeathMessageVisibilityRule() == visibility) {
         throw OPTION_DEATH_MESSAGE_VISIBILITY_UNCHANGED_EXCEPTION.create();
      } else {
         team.setDeathMessageVisibilityRule(visibility);
         source.sendFeedback(
            new TranslatableText("commands.team.option.deathMessageVisibility.success", team.getFormattedName(), visibility.getTranslationKey()), true
         );
         return 0;
      }
   }

   private static int executeModifyCollisionRule(ServerCommandSource source, Team team, AbstractTeam.CollisionRule collisionRule) throws CommandSyntaxException {
      if (team.getCollisionRule() == collisionRule) {
         throw OPTION_COLLISION_RULE_UNCHANGED_EXCEPTION.create();
      } else {
         team.setCollisionRule(collisionRule);
         source.sendFeedback(
            new TranslatableText("commands.team.option.collisionRule.success", team.getFormattedName(), collisionRule.getTranslationKey()), true
         );
         return 0;
      }
   }

   private static int executeModifySeeFriendlyInvisibles(ServerCommandSource source, Team team, boolean allowed) throws CommandSyntaxException {
      if (team.shouldShowFriendlyInvisibles() == allowed) {
         if (allowed) {
            throw OPTION_SEE_FRIENDLY_INVISIBLES_ALREADY_ENABLED_EXCEPTION.create();
         } else {
            throw OPTION_SEE_FRIENDLY_INVISIBLES_ALREADY_DISABLED_EXCEPTION.create();
         }
      } else {
         team.setShowFriendlyInvisibles(allowed);
         source.sendFeedback(
            new TranslatableText("commands.team.option.seeFriendlyInvisibles." + (allowed ? "enabled" : "disabled"), team.getFormattedName()), true
         );
         return 0;
      }
   }

   private static int executeModifyFriendlyFire(ServerCommandSource source, Team team, boolean allowed) throws CommandSyntaxException {
      if (team.isFriendlyFireAllowed() == allowed) {
         if (allowed) {
            throw OPTION_FRIENDLY_FIRE_ALREADY_ENABLED_EXCEPTION.create();
         } else {
            throw OPTION_FRIENDLY_FIRE_ALREADY_DISABLED_EXCEPTION.create();
         }
      } else {
         team.setFriendlyFireAllowed(allowed);
         source.sendFeedback(new TranslatableText("commands.team.option.friendlyfire." + (allowed ? "enabled" : "disabled"), team.getFormattedName()), true);
         return 0;
      }
   }

   private static int executeModifyDisplayName(ServerCommandSource source, Team team, Text displayName) throws CommandSyntaxException {
      if (team.getDisplayName().equals(displayName)) {
         throw OPTION_NAME_UNCHANGED_EXCEPTION.create();
      } else {
         team.setDisplayName(displayName);
         source.sendFeedback(new TranslatableText("commands.team.option.name.success", team.getFormattedName()), true);
         return 0;
      }
   }

   private static int executeModifyColor(ServerCommandSource source, Team team, Formatting color) throws CommandSyntaxException {
      if (team.getColor() == color) {
         throw OPTION_COLOR_UNCHANGED_EXCEPTION.create();
      } else {
         team.setColor(color);
         source.sendFeedback(new TranslatableText("commands.team.option.color.success", team.getFormattedName(), color.getName()), true);
         return 0;
      }
   }

   private static int executeEmpty(ServerCommandSource source, Team team) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      Collection<String> _snowmanx = Lists.newArrayList(team.getPlayerList());
      if (_snowmanx.isEmpty()) {
         throw EMPTY_UNCHANGED_EXCEPTION.create();
      } else {
         for (String _snowmanxx : _snowmanx) {
            _snowman.removePlayerFromTeam(_snowmanxx, team);
         }

         source.sendFeedback(new TranslatableText("commands.team.empty.success", _snowmanx.size(), team.getFormattedName()), true);
         return _snowmanx.size();
      }
   }

   private static int executeRemove(ServerCommandSource source, Team team) {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      _snowman.removeTeam(team);
      source.sendFeedback(new TranslatableText("commands.team.remove.success", team.getFormattedName()), true);
      return _snowman.getTeams().size();
   }

   private static int executeAdd(ServerCommandSource source, String team) throws CommandSyntaxException {
      return executeAdd(source, team, new LiteralText(team));
   }

   private static int executeAdd(ServerCommandSource source, String team, Text displayName) throws CommandSyntaxException {
      Scoreboard _snowman = source.getMinecraftServer().getScoreboard();
      if (_snowman.getTeam(team) != null) {
         throw ADD_DUPLICATE_EXCEPTION.create();
      } else if (team.length() > 16) {
         throw ADD_LONG_NAME_EXCEPTION.create(16);
      } else {
         Team _snowmanx = _snowman.addTeam(team);
         _snowmanx.setDisplayName(displayName);
         source.sendFeedback(new TranslatableText("commands.team.add.success", _snowmanx.getFormattedName()), true);
         return _snowman.getTeams().size();
      }
   }

   private static int executeListMembers(ServerCommandSource source, Team team) {
      Collection<String> _snowman = team.getPlayerList();
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.team.list.members.empty", team.getFormattedName()), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.team.list.members.success", team.getFormattedName(), _snowman.size(), Texts.joinOrdered(_snowman)), false);
      }

      return _snowman.size();
   }

   private static int executeListTeams(ServerCommandSource source) {
      Collection<Team> _snowman = source.getMinecraftServer().getScoreboard().getTeams();
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.team.list.teams.empty"), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.team.list.teams.success", _snowman.size(), Texts.join(_snowman, Team::getFormattedName)), false);
      }

      return _snowman.size();
   }

   private static int executeModifyPrefix(ServerCommandSource source, Team team, Text prefix) {
      team.setPrefix(prefix);
      source.sendFeedback(new TranslatableText("commands.team.option.prefix.success", prefix), false);
      return 1;
   }

   private static int executeModifySuffix(ServerCommandSource source, Team team, Text suffix) {
      team.setSuffix(suffix);
      source.sendFeedback(new TranslatableText("commands.team.option.suffix.success", suffix), false);
      return 1;
   }
}
