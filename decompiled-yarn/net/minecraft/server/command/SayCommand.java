package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class SayCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("say").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(CommandManager.argument("message", MessageArgumentType.message()).executes(_snowman -> {
               Text _snowmanx = MessageArgumentType.getMessage(_snowman, "message");
               TranslatableText _snowmanxx = new TranslatableText("chat.type.announcement", ((ServerCommandSource)_snowman.getSource()).getDisplayName(), _snowmanx);
               Entity _snowmanxxx = ((ServerCommandSource)_snowman.getSource()).getEntity();
               if (_snowmanxxx != null) {
                  ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().broadcastChatMessage(_snowmanxx, MessageType.CHAT, _snowmanxxx.getUuid());
               } else {
                  ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getPlayerManager().broadcastChatMessage(_snowmanxx, MessageType.SYSTEM, Util.NIL_UUID);
               }

               return 1;
            }))
      );
   }
}
