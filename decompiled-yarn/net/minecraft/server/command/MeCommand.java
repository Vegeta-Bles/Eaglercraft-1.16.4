package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.entity.Entity;
import net.minecraft.network.MessageType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.filter.TextStream;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Util;

public class MeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("me")
            .then(
               CommandManager.argument("action", StringArgumentType.greedyString())
                  .executes(
                     _snowman -> {
                        String _snowmanx = StringArgumentType.getString(_snowman, "action");
                        Entity _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getEntity();
                        MinecraftServer _snowmanxxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer();
                        if (_snowmanxx != null) {
                           if (_snowmanxx instanceof ServerPlayerEntity) {
                              TextStream _snowmanxxxx = ((ServerPlayerEntity)_snowmanxx).getTextStream();
                              if (_snowmanxxxx != null) {
                                 _snowmanxxxx.filterText(_snowmanx)
                                    .thenAcceptAsync(
                                       _snowmanxxxxx -> _snowmanxxxxx.ifPresent(
                                             _snowmanxxxxxx -> _snowman.getPlayerManager().broadcastChatMessage(method_31373(_snowman, _snowmanxxxxxx), MessageType.CHAT, _snowman.getUuid())
                                          ),
                                       _snowmanxxx
                                    );
                                 return 1;
                              }
                           }

                           _snowmanxxx.getPlayerManager().broadcastChatMessage(method_31373(_snowman, _snowmanx), MessageType.CHAT, _snowmanxx.getUuid());
                        } else {
                           _snowmanxxx.getPlayerManager().broadcastChatMessage(method_31373(_snowman, _snowmanx), MessageType.SYSTEM, Util.NIL_UUID);
                        }

                        return 1;
                     }
                  )
            )
      );
   }

   private static Text method_31373(CommandContext<ServerCommandSource> _snowman, String _snowman) {
      return new TranslatableText("chat.type.emote", ((ServerCommandSource)_snowman.getSource()).getDisplayName(), _snowman);
   }
}
