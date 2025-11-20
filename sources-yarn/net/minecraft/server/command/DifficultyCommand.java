package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.Difficulty;

public class DifficultyCommand {
   private static final DynamicCommandExceptionType FAILURE_EXCEPTION = new DynamicCommandExceptionType(
      object -> new TranslatableText("commands.difficulty.failure", object)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = CommandManager.literal("difficulty");

      for (Difficulty lv : Difficulty.values()) {
         literalArgumentBuilder.then(
            CommandManager.literal(lv.getName()).executes(commandContext -> execute((ServerCommandSource)commandContext.getSource(), lv))
         );
      }

      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)literalArgumentBuilder.requires(arg -> arg.hasPermissionLevel(2))).executes(commandContext -> {
            Difficulty lvx = ((ServerCommandSource)commandContext.getSource()).getWorld().getDifficulty();
            ((ServerCommandSource)commandContext.getSource()).sendFeedback(new TranslatableText("commands.difficulty.query", lvx.getTranslatableName()), false);
            return lvx.getId();
         })
      );
   }

   public static int execute(ServerCommandSource source, Difficulty difficulty) throws CommandSyntaxException {
      MinecraftServer minecraftServer = source.getMinecraftServer();
      if (minecraftServer.getSaveProperties().getDifficulty() == difficulty) {
         throw FAILURE_EXCEPTION.create(difficulty.getName());
      } else {
         minecraftServer.setDifficulty(difficulty, true);
         source.sendFeedback(new TranslatableText("commands.difficulty.success", difficulty.getTranslatableName()), true);
         return 0;
      }
   }
}
