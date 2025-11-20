package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.client.util.NetworkUtils;
import net.minecraft.text.TranslatableText;

public class PublishCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.publish.failed"));
   private static final DynamicCommandExceptionType ALREADY_PUBLISHED_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.publish.alreadyPublished", _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("publish").requires(_snowman -> _snowman.hasPermissionLevel(4)))
               .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), NetworkUtils.findLocalPort())))
            .then(
               CommandManager.argument("port", IntegerArgumentType.integer(0, 65535))
                  .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "port")))
            )
      );
   }

   private static int execute(ServerCommandSource source, int port) throws CommandSyntaxException {
      if (source.getMinecraftServer().isRemote()) {
         throw ALREADY_PUBLISHED_EXCEPTION.create(source.getMinecraftServer().getServerPort());
      } else if (!source.getMinecraftServer().openToLan(source.getMinecraftServer().getDefaultGameMode(), false, port)) {
         throw FAILED_EXCEPTION.create();
      } else {
         source.sendFeedback(new TranslatableText("commands.publish.success", port), true);
         return port;
      }
   }
}
