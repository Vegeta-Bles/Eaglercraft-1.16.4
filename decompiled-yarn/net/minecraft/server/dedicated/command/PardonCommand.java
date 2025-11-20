package net.minecraft.server.dedicated.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class PardonCommand {
   private static final SimpleCommandExceptionType ALREADY_UNBANNED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.pardon.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("pardon").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                  .suggests(
                     (_snowman, _snowmanx) -> CommandSource.suggestMatching(
                           ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getUserBanList().getNames(), _snowmanx
                        )
                  )
                  .executes(_snowman -> pardon((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets")))
            )
      );
   }

   private static int pardon(ServerCommandSource source, Collection<GameProfile> targets) throws CommandSyntaxException {
      BannedPlayerList _snowman = source.getMinecraftServer().getPlayerManager().getUserBanList();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (_snowman.contains(_snowmanxx)) {
            _snowman.remove(_snowmanxx);
            _snowmanx++;
            source.sendFeedback(new TranslatableText("commands.pardon.success", Texts.toText(_snowmanxx)), true);
         }
      }

      if (_snowmanx == 0) {
         throw ALREADY_UNBANNED_EXCEPTION.create();
      } else {
         return _snowmanx;
      }
   }
}
