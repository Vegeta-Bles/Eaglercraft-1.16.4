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

public class DeOpCommand {
   private static final SimpleCommandExceptionType ALREADY_DEOPPED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.deop.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("deop").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                  .suggests(
                     (_snowman, _snowmanx) -> CommandSource.suggestMatching(((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getOpNames(), _snowmanx)
                  )
                  .executes(_snowman -> deop((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets")))
            )
      );
   }

   private static int deop(ServerCommandSource source, Collection<GameProfile> targets) throws CommandSyntaxException {
      PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (_snowman.isOperator(_snowmanxx)) {
            _snowman.removeFromOperators(_snowmanxx);
            _snowmanx++;
            source.sendFeedback(new TranslatableText("commands.deop.success", targets.iterator().next().getName()), true);
         }
      }

      if (_snowmanx == 0) {
         throw ALREADY_DEOPPED_EXCEPTION.create();
      } else {
         source.getMinecraftServer().kickNonWhitelistedPlayers(source);
         return _snowmanx;
      }
   }
}
