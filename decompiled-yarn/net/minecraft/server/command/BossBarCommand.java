package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.BoolArgumentType;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import java.util.Collections;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.IdentifierArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.entity.boss.BossBar;
import net.minecraft.entity.boss.BossBarManager;
import net.minecraft.entity.boss.CommandBossBar;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;

public class BossBarCommand {
   private static final DynamicCommandExceptionType CREATE_FAILED_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.bossbar.create.failed", _snowman)
   );
   private static final DynamicCommandExceptionType UNKNOWN_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.bossbar.unknown", _snowman)
   );
   private static final SimpleCommandExceptionType SET_PLAYERS_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.players.unchanged")
   );
   private static final SimpleCommandExceptionType SET_NAME_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.name.unchanged")
   );
   private static final SimpleCommandExceptionType SET_COLOR_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.color.unchanged")
   );
   private static final SimpleCommandExceptionType SET_STYLE_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.style.unchanged")
   );
   private static final SimpleCommandExceptionType SET_VALUE_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.value.unchanged")
   );
   private static final SimpleCommandExceptionType SET_MAX_UNCHANGED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.max.unchanged")
   );
   private static final SimpleCommandExceptionType SET_VISIBILITY_UNCHANGED_HIDDEN_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.visibility.unchanged.hidden")
   );
   private static final SimpleCommandExceptionType SET_VISIBILITY_UNCHANGED_VISIBLE_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.bossbar.set.visibility.unchanged.visible")
   );
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> CommandSource.suggestIdentifiers(
         ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getBossBarManager().getIds(), _snowmanx
      );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                              "bossbar"
                           )
                           .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                        .then(
                           CommandManager.literal("add")
                              .then(
                                 CommandManager.argument("id", IdentifierArgumentType.identifier())
                                    .then(
                                       CommandManager.argument("name", TextArgumentType.text())
                                          .executes(
                                             _snowman -> addBossBar(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   IdentifierArgumentType.getIdentifier(_snowman, "id"),
                                                   TextArgumentType.getTextArgument(_snowman, "name")
                                                )
                                          )
                                    )
                              )
                        ))
                     .then(
                        CommandManager.literal("remove")
                           .then(
                              CommandManager.argument("id", IdentifierArgumentType.identifier())
                                 .suggests(SUGGESTION_PROVIDER)
                                 .executes(_snowman -> removeBossBar((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman)))
                           )
                     ))
                  .then(CommandManager.literal("list").executes(_snowman -> listBossBars((ServerCommandSource)_snowman.getSource()))))
               .then(
                  CommandManager.literal("set")
                     .then(
                        ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                                "id", IdentifierArgumentType.identifier()
                                             )
                                             .suggests(SUGGESTION_PROVIDER)
                                             .then(
                                                CommandManager.literal("name")
                                                   .then(
                                                      CommandManager.argument("name", TextArgumentType.text())
                                                         .executes(
                                                            _snowman -> setName(
                                                                  (ServerCommandSource)_snowman.getSource(),
                                                                  getBossBar(_snowman),
                                                                  TextArgumentType.getTextArgument(_snowman, "name")
                                                               )
                                                         )
                                                   )
                                             ))
                                          .then(
                                             ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                                                     "color"
                                                                  )
                                                                  .then(
                                                                     CommandManager.literal("pink")
                                                                        .executes(
                                                                           _snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.PINK)
                                                                        )
                                                                  ))
                                                               .then(
                                                                  CommandManager.literal("blue")
                                                                     .executes(
                                                                        _snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.BLUE)
                                                                     )
                                                               ))
                                                            .then(
                                                               CommandManager.literal("red")
                                                                  .executes(_snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.RED))
                                                            ))
                                                         .then(
                                                            CommandManager.literal("green")
                                                               .executes(_snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.GREEN))
                                                         ))
                                                      .then(
                                                         CommandManager.literal("yellow")
                                                            .executes(_snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.YELLOW))
                                                      ))
                                                   .then(
                                                      CommandManager.literal("purple")
                                                         .executes(_snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.PURPLE))
                                                   ))
                                                .then(
                                                   CommandManager.literal("white")
                                                      .executes(_snowman -> setColor((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Color.WHITE))
                                                )
                                          ))
                                       .then(
                                          ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                                            "style"
                                                         )
                                                         .then(
                                                            CommandManager.literal("progress")
                                                               .executes(
                                                                  _snowman -> setStyle((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Style.PROGRESS)
                                                               )
                                                         ))
                                                      .then(
                                                         CommandManager.literal("notched_6")
                                                            .executes(_snowman -> setStyle((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Style.NOTCHED_6))
                                                      ))
                                                   .then(
                                                      CommandManager.literal("notched_10")
                                                         .executes(_snowman -> setStyle((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Style.NOTCHED_10))
                                                   ))
                                                .then(
                                                   CommandManager.literal("notched_12")
                                                      .executes(_snowman -> setStyle((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Style.NOTCHED_12))
                                                ))
                                             .then(
                                                CommandManager.literal("notched_20")
                                                   .executes(_snowman -> setStyle((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BossBar.Style.NOTCHED_20))
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("value")
                                          .then(
                                             CommandManager.argument("value", IntegerArgumentType.integer(0))
                                                .executes(
                                                   _snowman -> setValue((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), IntegerArgumentType.getInteger(_snowman, "value"))
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("max")
                                       .then(
                                          CommandManager.argument("max", IntegerArgumentType.integer(1))
                                             .executes(
                                                _snowman -> setMaxValue((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), IntegerArgumentType.getInteger(_snowman, "max"))
                                             )
                                       )
                                 ))
                              .then(
                                 CommandManager.literal("visible")
                                    .then(
                                       CommandManager.argument("visible", BoolArgumentType.bool())
                                          .executes(_snowman -> setVisible((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), BoolArgumentType.getBool(_snowman, "visible")))
                                    )
                              ))
                           .then(
                              ((LiteralArgumentBuilder)CommandManager.literal("players")
                                    .executes(_snowman -> setPlayers((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), Collections.emptyList())))
                                 .then(
                                    CommandManager.argument("targets", EntityArgumentType.players())
                                       .executes(
                                          _snowman -> setPlayers(
                                                (ServerCommandSource)_snowman.getSource(), getBossBar(_snowman), EntityArgumentType.getOptionalPlayers(_snowman, "targets")
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("get")
                  .then(
                     ((RequiredArgumentBuilder)((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument(
                                    "id", IdentifierArgumentType.identifier()
                                 )
                                 .suggests(SUGGESTION_PROVIDER)
                                 .then(CommandManager.literal("value").executes(_snowman -> getValue((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman)))))
                              .then(CommandManager.literal("max").executes(_snowman -> getMaxValue((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman)))))
                           .then(CommandManager.literal("visible").executes(_snowman -> isVisible((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman)))))
                        .then(CommandManager.literal("players").executes(_snowman -> getPlayers((ServerCommandSource)_snowman.getSource(), getBossBar(_snowman))))
                  )
            )
      );
   }

   private static int getValue(ServerCommandSource source, CommandBossBar bossBar) {
      source.sendFeedback(new TranslatableText("commands.bossbar.get.value", bossBar.toHoverableText(), bossBar.getValue()), true);
      return bossBar.getValue();
   }

   private static int getMaxValue(ServerCommandSource source, CommandBossBar bossBar) {
      source.sendFeedback(new TranslatableText("commands.bossbar.get.max", bossBar.toHoverableText(), bossBar.getMaxValue()), true);
      return bossBar.getMaxValue();
   }

   private static int isVisible(ServerCommandSource source, CommandBossBar bossBar) {
      if (bossBar.isVisible()) {
         source.sendFeedback(new TranslatableText("commands.bossbar.get.visible.visible", bossBar.toHoverableText()), true);
         return 1;
      } else {
         source.sendFeedback(new TranslatableText("commands.bossbar.get.visible.hidden", bossBar.toHoverableText()), true);
         return 0;
      }
   }

   private static int getPlayers(ServerCommandSource source, CommandBossBar bossBar) {
      if (bossBar.getPlayers().isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.bossbar.get.players.none", bossBar.toHoverableText()), true);
      } else {
         source.sendFeedback(
            new TranslatableText(
               "commands.bossbar.get.players.some",
               bossBar.toHoverableText(),
               bossBar.getPlayers().size(),
               Texts.join(bossBar.getPlayers(), PlayerEntity::getDisplayName)
            ),
            true
         );
      }

      return bossBar.getPlayers().size();
   }

   private static int setVisible(ServerCommandSource source, CommandBossBar bossBar, boolean visible) throws CommandSyntaxException {
      if (bossBar.isVisible() == visible) {
         if (visible) {
            throw SET_VISIBILITY_UNCHANGED_VISIBLE_EXCEPTION.create();
         } else {
            throw SET_VISIBILITY_UNCHANGED_HIDDEN_EXCEPTION.create();
         }
      } else {
         bossBar.setVisible(visible);
         if (visible) {
            source.sendFeedback(new TranslatableText("commands.bossbar.set.visible.success.visible", bossBar.toHoverableText()), true);
         } else {
            source.sendFeedback(new TranslatableText("commands.bossbar.set.visible.success.hidden", bossBar.toHoverableText()), true);
         }

         return 0;
      }
   }

   private static int setValue(ServerCommandSource source, CommandBossBar bossBar, int value) throws CommandSyntaxException {
      if (bossBar.getValue() == value) {
         throw SET_VALUE_UNCHANGED_EXCEPTION.create();
      } else {
         bossBar.setValue(value);
         source.sendFeedback(new TranslatableText("commands.bossbar.set.value.success", bossBar.toHoverableText(), value), true);
         return value;
      }
   }

   private static int setMaxValue(ServerCommandSource source, CommandBossBar bossBar, int value) throws CommandSyntaxException {
      if (bossBar.getMaxValue() == value) {
         throw SET_MAX_UNCHANGED_EXCEPTION.create();
      } else {
         bossBar.setMaxValue(value);
         source.sendFeedback(new TranslatableText("commands.bossbar.set.max.success", bossBar.toHoverableText(), value), true);
         return value;
      }
   }

   private static int setColor(ServerCommandSource source, CommandBossBar bossBar, BossBar.Color color) throws CommandSyntaxException {
      if (bossBar.getColor().equals(color)) {
         throw SET_COLOR_UNCHANGED_EXCEPTION.create();
      } else {
         bossBar.setColor(color);
         source.sendFeedback(new TranslatableText("commands.bossbar.set.color.success", bossBar.toHoverableText()), true);
         return 0;
      }
   }

   private static int setStyle(ServerCommandSource source, CommandBossBar bossBar, BossBar.Style style) throws CommandSyntaxException {
      if (bossBar.getOverlay().equals(style)) {
         throw SET_STYLE_UNCHANGED_EXCEPTION.create();
      } else {
         bossBar.setOverlay(style);
         source.sendFeedback(new TranslatableText("commands.bossbar.set.style.success", bossBar.toHoverableText()), true);
         return 0;
      }
   }

   private static int setName(ServerCommandSource source, CommandBossBar bossBar, Text name) throws CommandSyntaxException {
      Text _snowman = Texts.parse(source, name, null, 0);
      if (bossBar.getName().equals(_snowman)) {
         throw SET_NAME_UNCHANGED_EXCEPTION.create();
      } else {
         bossBar.setName(_snowman);
         source.sendFeedback(new TranslatableText("commands.bossbar.set.name.success", bossBar.toHoverableText()), true);
         return 0;
      }
   }

   private static int setPlayers(ServerCommandSource source, CommandBossBar bossBar, Collection<ServerPlayerEntity> players) throws CommandSyntaxException {
      boolean _snowman = bossBar.addPlayers(players);
      if (!_snowman) {
         throw SET_PLAYERS_UNCHANGED_EXCEPTION.create();
      } else {
         if (bossBar.getPlayers().isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.bossbar.set.players.success.none", bossBar.toHoverableText()), true);
         } else {
            source.sendFeedback(
               new TranslatableText(
                  "commands.bossbar.set.players.success.some", bossBar.toHoverableText(), players.size(), Texts.join(players, PlayerEntity::getDisplayName)
               ),
               true
            );
         }

         return bossBar.getPlayers().size();
      }
   }

   private static int listBossBars(ServerCommandSource source) {
      Collection<CommandBossBar> _snowman = source.getMinecraftServer().getBossBarManager().getAll();
      if (_snowman.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.bossbar.list.bars.none"), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.bossbar.list.bars.some", _snowman.size(), Texts.join(_snowman, CommandBossBar::toHoverableText)), false);
      }

      return _snowman.size();
   }

   private static int addBossBar(ServerCommandSource source, Identifier name, Text displayName) throws CommandSyntaxException {
      BossBarManager _snowman = source.getMinecraftServer().getBossBarManager();
      if (_snowman.get(name) != null) {
         throw CREATE_FAILED_EXCEPTION.create(name.toString());
      } else {
         CommandBossBar _snowmanx = _snowman.add(name, Texts.parse(source, displayName, null, 0));
         source.sendFeedback(new TranslatableText("commands.bossbar.create.success", _snowmanx.toHoverableText()), true);
         return _snowman.getAll().size();
      }
   }

   private static int removeBossBar(ServerCommandSource source, CommandBossBar bossBar) {
      BossBarManager _snowman = source.getMinecraftServer().getBossBarManager();
      bossBar.clearPlayers();
      _snowman.remove(bossBar);
      source.sendFeedback(new TranslatableText("commands.bossbar.remove.success", bossBar.toHoverableText()), true);
      return _snowman.getAll().size();
   }

   public static CommandBossBar getBossBar(CommandContext<ServerCommandSource> context) throws CommandSyntaxException {
      Identifier _snowman = IdentifierArgumentType.getIdentifier(context, "id");
      CommandBossBar _snowmanx = ((ServerCommandSource)context.getSource()).getMinecraftServer().getBossBarManager().get(_snowman);
      if (_snowmanx == null) {
         throw UNKNOWN_EXCEPTION.create(_snowman.toString());
      } else {
         return _snowmanx;
      }
   }
}
