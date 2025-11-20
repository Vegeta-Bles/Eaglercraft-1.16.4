package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.regex.Matcher;
import net.minecraft.command.CommandSource;
import net.minecraft.server.BannedIpList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class PardonIpCommand {
   private static final SimpleCommandExceptionType INVALID_IP_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.pardonip.invalid"));
   private static final SimpleCommandExceptionType ALREADY_UNBANNED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.pardonip.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("pardon-ip").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               CommandManager.argument("target", StringArgumentType.word())
                  .suggests(
                     (_snowman, _snowmanx) -> CommandSource.suggestMatching(
                           ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getIpBanList().getNames(), _snowmanx
                        )
                  )
                  .executes(_snowman -> pardonIp((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "target")))
            )
      );
   }

   private static int pardonIp(ServerCommandSource source, String target) throws CommandSyntaxException {
      Matcher _snowman = BanIpCommand.PATTERN.matcher(target);
      if (!_snowman.matches()) {
         throw INVALID_IP_EXCEPTION.create();
      } else {
         BannedIpList _snowmanx = source.getMinecraftServer().getPlayerManager().getIpBanList();
         if (!_snowmanx.isBanned(target)) {
            throw ALREADY_UNBANNED_EXCEPTION.create();
         } else {
            _snowmanx.remove(target);
            source.sendFeedback(new TranslatableText("commands.pardonip.success", target), true);
            return 1;
         }
      }
   }
}
