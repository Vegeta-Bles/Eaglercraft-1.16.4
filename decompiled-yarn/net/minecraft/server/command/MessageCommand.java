package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.Collection;
import java.util.UUID;
import java.util.function.Consumer;
import net.minecraft.command.argument.EntityArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;
import net.minecraft.util.Util;

public class MessageCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> _snowman = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("msg")
            .then(
               CommandManager.argument("targets", EntityArgumentType.players())
                  .then(
                     CommandManager.argument("message", MessageArgumentType.message())
                        .executes(
                           _snowmanx -> execute(
                                 (ServerCommandSource)_snowmanx.getSource(),
                                 EntityArgumentType.getPlayers(_snowmanx, "targets"),
                                 MessageArgumentType.getMessage(_snowmanx, "message")
                              )
                        )
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("tell").redirect(_snowman));
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("w").redirect(_snowman));
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Text message) {
      UUID _snowman = source.getEntity() == null ? Util.NIL_UUID : source.getEntity().getUuid();
      Entity _snowmanx = source.getEntity();
      Consumer<Text> _snowmanxx;
      if (_snowmanx instanceof ServerPlayerEntity) {
         ServerPlayerEntity _snowmanxxx = (ServerPlayerEntity)_snowmanx;
         _snowmanxx = _snowmanxxxx -> _snowman.sendSystemMessage(
               new TranslatableText("commands.message.display.outgoing", _snowmanxxxx, message).formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}),
               _snowman.getUuid()
            );
      } else {
         _snowmanxx = _snowmanxxx -> source.sendFeedback(
               new TranslatableText("commands.message.display.outgoing", _snowmanxxx, message).formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}), false
            );
      }

      for (ServerPlayerEntity _snowmanxxx : targets) {
         _snowmanxx.accept(_snowmanxxx.getDisplayName());
         _snowmanxxx.sendSystemMessage(
            new TranslatableText("commands.message.display.incoming", source.getDisplayName(), message)
               .formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}),
            _snowman
         );
      }

      return targets.size();
   }
}
