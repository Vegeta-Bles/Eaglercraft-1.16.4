package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.CommandContext;
import net.minecraft.text.TranslatableText;
import net.minecraft.world.GameRules;

public class GameRuleCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      final LiteralArgumentBuilder<ServerCommandSource> literalArgumentBuilder = (LiteralArgumentBuilder<ServerCommandSource>)CommandManager.literal("gamerule")
         .requires(source -> source.hasPermissionLevel(2));
      GameRules.accept(
         new GameRules.Visitor() {
            @Override
            public <T extends GameRules.Rule<T>> void visit(GameRules.Key<T> key, GameRules.Type<T> type) {
               literalArgumentBuilder.then(
                  ((LiteralArgumentBuilder)CommandManager.literal(key.getName())
                        .executes(context -> GameRuleCommand.executeQuery((ServerCommandSource)context.getSource(), key)))
                     .then(type.argument("value").executes(context -> GameRuleCommand.executeSet(context, key)))
               );
            }
         }
      );
      dispatcher.register(literalArgumentBuilder);
   }

   private static <T extends GameRules.Rule<T>> int executeSet(CommandContext<ServerCommandSource> context, GameRules.Key<T> key) {
      ServerCommandSource lv = (ServerCommandSource)context.getSource();
      T lv2 = lv.getMinecraftServer().getGameRules().get(key);
      lv2.set(context, "value");
      lv.sendFeedback(new TranslatableText("commands.gamerule.set", key.getName(), lv2.toString()), true);
      return lv2.getCommandResult();
   }

   private static <T extends GameRules.Rule<T>> int executeQuery(ServerCommandSource source, GameRules.Key<T> key) {
      T lv = source.getMinecraftServer().getGameRules().get(key);
      source.sendFeedback(new TranslatableText("commands.gamerule.query", key.getName(), lv.toString()), false);
      return lv.getCommandResult();
   }
}
