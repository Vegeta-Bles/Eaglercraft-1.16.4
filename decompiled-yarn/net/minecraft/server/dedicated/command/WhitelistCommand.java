package net.minecraft.server.dedicated.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.Whitelist;
import net.minecraft.server.WhitelistEntry;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class WhitelistCommand {
   private static final SimpleCommandExceptionType ALREADY_ON_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.whitelist.alreadyOn"));
   private static final SimpleCommandExceptionType ALREADY_OFF_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.whitelist.alreadyOff"));
   private static final SimpleCommandExceptionType ADD_FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.whitelist.add.failed"));
   private static final SimpleCommandExceptionType REMOVE_FAILED_EXCEPTION = new SimpleCommandExceptionType(
      new TranslatableText("commands.whitelist.remove.failed")
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal(
                                 "whitelist"
                              )
                              .requires(_snowman -> _snowman.hasPermissionLevel(3)))
                           .then(CommandManager.literal("on").executes(_snowman -> executeOn((ServerCommandSource)_snowman.getSource()))))
                        .then(CommandManager.literal("off").executes(_snowman -> executeOff((ServerCommandSource)_snowman.getSource()))))
                     .then(CommandManager.literal("list").executes(_snowman -> executeList((ServerCommandSource)_snowman.getSource()))))
                  .then(
                     CommandManager.literal("add")
                        .then(
                           CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                              .suggests(
                                 (_snowman, _snowmanx) -> {
                                    PlayerManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager();
                                    return CommandSource.suggestMatching(
                                       _snowmanxx.getPlayerList()
                                          .stream()
                                          .filter(_snowmanxxx -> !_snowman.getWhitelist().isAllowed(_snowmanxxx.getGameProfile()))
                                          .map(_snowmanxxx -> _snowmanxxx.getGameProfile().getName()),
                                       _snowmanx
                                    );
                                 }
                              )
                              .executes(_snowman -> executeAdd((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets")))
                        )
                  ))
               .then(
                  CommandManager.literal("remove")
                     .then(
                        CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                           .suggests(
                              (_snowman, _snowmanx) -> CommandSource.suggestMatching(
                                    ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getWhitelistedNames(), _snowmanx
                                 )
                           )
                           .executes(_snowman -> executeRemove((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets")))
                     )
               ))
            .then(CommandManager.literal("reload").executes(_snowman -> executeReload((ServerCommandSource)_snowman.getSource())))
      );
   }

   private static int executeReload(ServerCommandSource source) {
      source.getMinecraftServer().getPlayerManager().reloadWhitelist();
      source.sendFeedback(new TranslatableText("commands.whitelist.reloaded"), true);
      source.getMinecraftServer().kickNonWhitelistedPlayers(source);
      return 1;
   }

   private static int executeAdd(ServerCommandSource source, Collection<GameProfile> targets) throws CommandSyntaxException {
      Whitelist _snowman = source.getMinecraftServer().getPlayerManager().getWhitelist();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (!_snowman.isAllowed(_snowmanxx)) {
            WhitelistEntry _snowmanxxx = new WhitelistEntry(_snowmanxx);
            _snowman.add(_snowmanxxx);
            source.sendFeedback(new TranslatableText("commands.whitelist.add.success", Texts.toText(_snowmanxx)), true);
            _snowmanx++;
         }
      }

      if (_snowmanx == 0) {
         throw ADD_FAILED_EXCEPTION.create();
      } else {
         return _snowmanx;
      }
   }

   private static int executeRemove(ServerCommandSource source, Collection<GameProfile> targets) throws CommandSyntaxException {
      Whitelist _snowman = source.getMinecraftServer().getPlayerManager().getWhitelist();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (_snowman.isAllowed(_snowmanxx)) {
            WhitelistEntry _snowmanxxx = new WhitelistEntry(_snowmanxx);
            _snowman.remove(_snowmanxxx);
            source.sendFeedback(new TranslatableText("commands.whitelist.remove.success", Texts.toText(_snowmanxx)), true);
            _snowmanx++;
         }
      }

      if (_snowmanx == 0) {
         throw REMOVE_FAILED_EXCEPTION.create();
      } else {
         source.getMinecraftServer().kickNonWhitelistedPlayers(source);
         return _snowmanx;
      }
   }

   private static int executeOn(ServerCommandSource source) throws CommandSyntaxException {
      PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
      if (_snowman.isWhitelistEnabled()) {
         throw ALREADY_ON_EXCEPTION.create();
      } else {
         _snowman.setWhitelistEnabled(true);
         source.sendFeedback(new TranslatableText("commands.whitelist.enabled"), true);
         source.getMinecraftServer().kickNonWhitelistedPlayers(source);
         return 1;
      }
   }

   private static int executeOff(ServerCommandSource source) throws CommandSyntaxException {
      PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
      if (!_snowman.isWhitelistEnabled()) {
         throw ALREADY_OFF_EXCEPTION.create();
      } else {
         _snowman.setWhitelistEnabled(false);
         source.sendFeedback(new TranslatableText("commands.whitelist.disabled"), true);
         return 1;
      }
   }

   private static int executeList(ServerCommandSource source) {
      String[] _snowman = source.getMinecraftServer().getPlayerManager().getWhitelistedNames();
      if (_snowman.length == 0) {
         source.sendFeedback(new TranslatableText("commands.whitelist.none"), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.whitelist.list", _snowman.length, String.join(", ", _snowman)), false);
      }

      return _snowman.length;
   }
}
