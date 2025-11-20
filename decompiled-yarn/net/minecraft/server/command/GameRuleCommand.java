package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameRules;

public class GameRuleCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      final LiteralArgumentBuilder<ServerCommandSource> _snowman = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("gamerule")
         .requires(source -> source.hasPermissionLevel(2));
      GameRules.accept(
         new GameRules.Visitor() {
            @Override
            public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
               _snowman.then(
                  ((LiteralArgumentBuilder)CommandManager.literal(key.getName())
                        .executes(context -> GameRuleCommand.executeQuery((ServerCommandSource)context.getSource(), key)))
                     .then(type.argument("value").executes(context -> GameRuleCommand.executeSet(context, key)))
               );
            }
         }
      );
      dispatcher.register(_snowman);
   }

   private static <T extends GameRules.Rule<T>> int executeSet(CommandContext<ServerCommandSource> context, GameRules.Key<T> key) {
      ServerCommandSource _snowman = (ServerCommandSource)context.getSource();
      T _snowmanx = _snowman.getMinecraftServer().getGameRules().get(key);
      _snowmanx.set(context, "value");
      _snowman.sendFeedback(new TranslatableText("commands.gamerule.set", key.getName(), _snowmanx.toString()), true);
      return _snowmanx.getCommandResult();
   }

   private static <T extends GameRules.Rule<T>> int executeQuery(ServerCommandSource source, GameRules.Key<T> key) {
      T _snowman = source.getMinecraftServer().getGameRules().get(key);
      source.sendFeedback(new TranslatableText("commands.gamerule.query", key.getName(), _snowman.toString()), false);
      return _snowman.getCommandResult();
   }
}
