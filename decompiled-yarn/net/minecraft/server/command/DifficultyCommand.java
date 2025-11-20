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
      _snowman -> new TranslatableText("commands.difficulty.failure", _snowman)
   );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralArgumentBuilder<ServerCommandSource> _snowman = CommandManager.literal("difficulty");

      for (Difficulty _snowmanx : Difficulty.values()) {
         _snowman.then(CommandManager.literal(_snowmanx.getName()).executes(_snowmanxx -> execute((ServerCommandSource)_snowmanxx.getSource(), _snowman)));
      }

      dispatcher.register((LiteralArgumentBuilder)((LiteralArgumentBuilder)_snowman.requires(_snowmanx -> _snowmanx.hasPermissionLevel(2))).executes(_snowmanx -> {
         Difficulty _snowmanx = ((ServerCommandSource)_snowmanx.getSource()).getWorld().getDifficulty();
         ((ServerCommandSource)_snowmanx.getSource()).sendFeedback(new TranslatableText("commands.difficulty.query", _snowmanx.getTranslatableName()), false);
         return _snowmanx.getId();
      }));
   }

   public static int execute(ServerCommandSource source, Difficulty difficulty) throws CommandSyntaxException {
      MinecraftServer _snowman = source.getMinecraftServer();
      if (_snowman.getSaveProperties().getDifficulty() == difficulty) {
         throw FAILURE_EXCEPTION.create(difficulty.getName());
      } else {
         _snowman.setDifficulty(difficulty, true);
         source.sendFeedback(new TranslatableText("commands.difficulty.success", difficulty.getTranslatableName()), true);
         return 0;
      }
   }
}
