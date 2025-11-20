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
      LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("msg")
            .then(
               CommandManager.argument("targets", EntityArgumentType.players())
                  .then(
                     CommandManager.argument("message", MessageArgumentType.message())
                        .executes(
                           commandContext -> execute(
                                 (ServerCommandSource)commandContext.getSource(),
                                 EntityArgumentType.getPlayers(commandContext, "targets"),
                                 MessageArgumentType.getMessage(commandContext, "message")
                              )
                        )
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("tell").redirect(literalCommandNode));
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("w").redirect(literalCommandNode));
   }

   private static int execute(ServerCommandSource source, Collection<ServerPlayerEntity> targets, Text message) {
      UUID uUID = source.getEntity() == null ? Util.NIL_UUID : source.getEntity().getUuid();
      Entity lv = source.getEntity();
      Consumer<Text> consumer;
      if (lv instanceof ServerPlayerEntity) {
         ServerPlayerEntity lv2 = (ServerPlayerEntity)lv;
         consumer = arg3 -> lv2.sendSystemMessage(
               new TranslatableText("commands.message.display.outgoing", arg3, message).formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}),
               lv2.getUuid()
            );
      } else {
         consumer = arg3 -> source.sendFeedback(
               new TranslatableText("commands.message.display.outgoing", arg3, message).formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}), false
            );
      }

      for (ServerPlayerEntity lv3 : targets) {
         consumer.accept(lv3.getDisplayName());
         lv3.sendSystemMessage(
            new TranslatableText("commands.message.display.incoming", source.getDisplayName(), message)
               .formatted(new Formatting[]{Formatting.GRAY, Formatting.ITALIC}),
            uUID
         );
      }

      return targets.size();
   }
}
