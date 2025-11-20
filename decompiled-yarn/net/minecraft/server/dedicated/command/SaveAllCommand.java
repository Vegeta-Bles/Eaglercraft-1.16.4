package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.text.TranslatableText;

public class SaveAllCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.save.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("save-all").requires(_snowman -> _snowman.hasPermissionLevel(4)))
               .executes(_snowman -> saveAll((ServerCommandSource)_snowman.getSource(), false)))
            .then(CommandManager.literal("flush").executes(_snowman -> saveAll((ServerCommandSource)_snowman.getSource(), true)))
      );
   }

   private static int saveAll(ServerCommandSource source, boolean flush) throws CommandSyntaxException {
      source.sendFeedback(new TranslatableText("commands.save.saving"), false);
      MinecraftServer _snowman = source.getMinecraftServer();
      _snowman.getPlayerManager().saveAllPlayerData();
      boolean _snowmanx = _snowman.save(true, flush, true);
      if (!_snowmanx) {
         throw FAILED_EXCEPTION.create();
      } else {
         source.sendFeedback(new TranslatableText("commands.save.success"), true);
         return 1;
      }
   }
}
