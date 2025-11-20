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
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class OpCommand {
   private static final SimpleCommandExceptionType ALREADY_OPPED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.op.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("op").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                  .suggests(
                     (_snowman, _snowmanx) -> {
                        PlayerManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager();
                        return CommandSource.suggestMatching(
                           _snowmanxx.getPlayerList().stream().filter(_snowmanxxx -> !_snowman.isOperator(_snowmanxxx.getGameProfile())).map(_snowmanxxx -> _snowmanxxx.getGameProfile().getName()), _snowmanx
                        );
                     }
                  )
                  .executes(_snowman -> op((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets")))
            )
      );
   }

   private static int op(ServerCommandSource source, Collection<GameProfile> targets) throws CommandSyntaxException {
      PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (!_snowman.isOperator(_snowmanxx)) {
            _snowman.addToOperators(_snowmanxx);
            _snowmanx++;
            source.sendFeedback(new TranslatableText("commands.op.success", targets.iterator().next().getName()), true);
         }
      }

      if (_snowmanx == 0) {
         throw ALREADY_OPPED_EXCEPTION.create();
      } else {
         return _snowmanx;
      }
   }
}
