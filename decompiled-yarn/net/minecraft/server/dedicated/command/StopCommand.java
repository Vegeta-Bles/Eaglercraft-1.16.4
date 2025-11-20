package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class StopCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("stop").requires(_snowman -> _snowman.hasPermissionLevel(4))).executes(_snowman -> {
            ((ServerCommandSource)_snowman.getSource()).sendFeedback(new TranslatableText("commands.stop.stopping"), true);
            ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().stop(false);
            return 1;
         })
      );
   }
}
