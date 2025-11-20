package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import java.util.Collection;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.FunctionArgumentType;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.server.function.CommandFunctionManager;
import net.minecraft.text.TranslatableText;

public class FunctionCommand {
   public static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> {
      CommandFunctionManager _snowmanxx = ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getCommandFunctionManager();
      CommandSource.suggestIdentifiers(_snowmanxx.method_29464(), _snowmanx, "#");
      return CommandSource.suggestIdentifiers(_snowmanxx.method_29463(), _snowmanx);
   };

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("function").requires(_snowman -> _snowman.hasPermissionLevel(2)))
            .then(
               CommandManager.argument("name", FunctionArgumentType.function())
                  .suggests(SUGGESTION_PROVIDER)
                  .executes(_snowman -> execute((ServerCommandSource)_snowman.getSource(), FunctionArgumentType.getFunctions(_snowman, "name")))
            )
      );
   }

   private static int execute(ServerCommandSource source, Collection<CommandFunction> functions) {
      int _snowman = 0;

      for (CommandFunction _snowmanx : functions) {
         _snowman += source.getMinecraftServer().getCommandFunctionManager().execute(_snowmanx, source.withSilent().withMaxLevel(2));
      }

      if (functions.size() == 1) {
         source.sendFeedback(new TranslatableText("commands.function.success.single", _snowman, functions.iterator().next().getId()), true);
      } else {
         source.sendFeedback(new TranslatableText("commands.function.success.multiple", _snowman, functions.size()), true);
      }

      return _snowman;
   }
}
