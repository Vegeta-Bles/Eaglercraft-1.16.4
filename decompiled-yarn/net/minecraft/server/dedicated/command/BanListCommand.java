package net.minecraft.server.dedicated.command;

import com.google.common.collect.Iterables;
import com.google.common.collect.Lists;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.Collection;
import net.minecraft.server.BanEntry;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class BanListCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("banlist")
                     .requires(_snowman -> _snowman.hasPermissionLevel(3)))
                  .executes(_snowman -> {
                     PlayerManager _snowmanx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager();
                     return execute(
                        (ServerCommandSource)_snowman.getSource(), Lists.newArrayList(Iterables.concat(_snowmanx.getUserBanList().values(), _snowmanx.getIpBanList().values()))
                     );
                  }))
               .then(
                  CommandManager.literal("ips")
                     .executes(
                        _snowman -> execute(
                              (ServerCommandSource)_snowman.getSource(),
                              ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getIpBanList().values()
                           )
                     )
               ))
            .then(
               CommandManager.literal("players")
                  .executes(
                     _snowman -> execute(
                           (ServerCommandSource)_snowman.getSource(),
                           ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().getUserBanList().values()
                        )
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<? extends BanEntry<?>> targets) {
      if (targets.isEmpty()) {
         source.sendFeedback(new TranslatableText("commands.banlist.none"), false);
      } else {
         source.sendFeedback(new TranslatableText("commands.banlist.list", targets.size()), false);

         for (BanEntry<?> _snowman : targets) {
            source.sendFeedback(new TranslatableText("commands.banlist.entry", _snowman.toText(), _snowman.getSource(), _snowman.getReason()), false);
         }
      }

      return targets.size();
   }
}
