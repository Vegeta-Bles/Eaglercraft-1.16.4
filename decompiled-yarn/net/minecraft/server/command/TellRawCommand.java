package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.TextArgumentType;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Texts;
import net.minecraft.util.Util;

public class TellRawCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("tellraw").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("targets", EntityArgumentType.players())
                  .then(CommandManager.argument("message", TextArgumentType.text()).executes(_snowman -> {
                     int _snowmanx = 0;

                     for (ServerPlayerEntity _snowmanxx : EntityArgumentType.getPlayers(_snowman, "targets")) {
                        _snowmanxx.sendSystemMessage(
                           Texts.parse((ServerCommandSource)_snowman.getSource(), TextArgumentType.getTextArgument(_snowman, "message"), _snowmanxx, 0), Util.NIL_UUID
                        );
                        _snowmanx++;
                     }

                     return _snowmanx;
                  }))
            )
      );
   }
}
