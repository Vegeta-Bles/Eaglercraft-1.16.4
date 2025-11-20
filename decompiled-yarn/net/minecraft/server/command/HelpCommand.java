package net.minecraft.server.command;

import com.google.common.collect.Iterables;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.ParseResults;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.context.ParsedCommandNode;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.CommandNode;
import java.util.Map;
import net.minecraft.text.LiteralText;
import net.minecraft.text.TranslatableText;

public class HelpCommand {
   private static final SimpleCommandExceptionType FAILED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.help.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("help").executes(_snowmanx -> {
               Map<CommandNode<ServerCommandSource>, String> _snowmanxx = dispatcher.getSmartUsage(dispatcher.getRoot(), _snowmanx.getSource());

               for (String _snowmanxx : _snowmanxx.values()) {
                  ((ServerCommandSource)_snowmanx.getSource()).sendFeedback(new LiteralText("/" + _snowmanxx), false);
               }

               return _snowmanxx.size();
            }))
            .then(
               CommandManager.argument("command", StringArgumentType.greedyString())
                  .executes(
                     _snowmanx -> {
                        ParseResults<ServerCommandSource> _snowmanxx = dispatcher.parse(StringArgumentType.getString(_snowmanx, "command"), _snowmanx.getSource());
                        if (_snowmanxx.getContext().getNodes().isEmpty()) {
                           throw FAILED_EXCEPTION.create();
                        } else {
                           Map<CommandNode<ServerCommandSource>, String> _snowmanxx = dispatcher.getSmartUsage(
                              ((ParsedCommandNode)Iterables.getLast(_snowmanxx.getContext().getNodes())).getNode(), _snowmanx.getSource()
                           );

                           for (String _snowmanxxx : _snowmanxx.values()) {
                              ((ServerCommandSource)_snowmanx.getSource()).sendFeedback(new LiteralText("/" + _snowmanxx.getReader().getString() + " " + _snowmanxxx), false);
                           }

                           return _snowmanxx.size();
                        }
                     }
                  )
            )
      );
   }
}
