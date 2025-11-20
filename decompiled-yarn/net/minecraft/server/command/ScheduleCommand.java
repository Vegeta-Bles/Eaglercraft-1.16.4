package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import net.minecraft.command.CommandSource;
import net.minecraft.command.argument.FunctionArgumentType;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.function.CommandFunction;
import net.minecraft.tag.Tag;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Identifier;
import net.minecraft.world.timer.FunctionTagTimerCallback;
import net.minecraft.world.timer.FunctionTimerCallback;
import net.minecraft.world.timer.Timer;

public class ScheduleCommand {
   private static final SimpleCommandExceptionType SAME_TICK_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.schedule.same_tick"));
   private static final DynamicCommandExceptionType CLEARED_FAILURE_EXCEPTION = new DynamicCommandExceptionType(
      _snowman -> new TranslatableText("commands.schedule.cleared.failure", _snowman)
   );
   private static final SuggestionProvider<ServerCommandSource> SUGGESTION_PROVIDER = (_snowman, _snowmanx) -> CommandSource.suggestMatching(
         ((ServerCommandSource)_snowman.getSource()).getMinecraftServer().getSaveProperties().getMainWorldProperties().getScheduledEvents().method_22592(), _snowmanx
      );

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("schedule").requires(_snowman -> _snowman.hasPermissionLevel(2)))
               .then(
                  CommandManager.literal("function")
                     .then(
                        CommandManager.argument("function", FunctionArgumentType.function())
                           .suggests(FunctionCommand.SUGGESTION_PROVIDER)
                           .then(
                              ((RequiredArgumentBuilder)((RequiredArgumentBuilder)CommandManager.argument("time", TimeArgumentType.time())
                                       .executes(
                                          _snowman -> execute(
                                                (ServerCommandSource)_snowman.getSource(),
                                                FunctionArgumentType.getFunctionOrTag(_snowman, "function"),
                                                IntegerArgumentType.getInteger(_snowman, "time"),
                                                true
                                             )
                                       ))
                                    .then(
                                       CommandManager.literal("append")
                                          .executes(
                                             _snowman -> execute(
                                                   (ServerCommandSource)_snowman.getSource(),
                                                   FunctionArgumentType.getFunctionOrTag(_snowman, "function"),
                                                   IntegerArgumentType.getInteger(_snowman, "time"),
                                                   false
                                                )
                                          )
                                    ))
                                 .then(
                                    CommandManager.literal("replace")
                                       .executes(
                                          _snowman -> execute(
                                                (ServerCommandSource)_snowman.getSource(),
                                                FunctionArgumentType.getFunctionOrTag(_snowman, "function"),
                                                IntegerArgumentType.getInteger(_snowman, "time"),
                                                true
                                             )
                                       )
                                 )
                           )
                     )
               ))
            .then(
               CommandManager.literal("clear")
                  .then(
                     CommandManager.argument("function", StringArgumentType.greedyString())
                        .suggests(SUGGESTION_PROVIDER)
                        .executes(_snowman -> method_22833((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "function")))
                  )
            )
      );
   }

   private static int execute(ServerCommandSource source, Pair<Identifier, Either<CommandFunction, Tag<CommandFunction>>> _snowman, int _snowman, boolean _snowman) throws CommandSyntaxException {
      if (_snowman == 0) {
         throw SAME_TICK_EXCEPTION.create();
      } else {
         long _snowmanxxx = source.getWorld().getTime() + (long)_snowman;
         Identifier _snowmanxxxx = (Identifier)_snowman.getFirst();
         Timer<MinecraftServer> _snowmanxxxxx = source.getMinecraftServer().getSaveProperties().getMainWorldProperties().getScheduledEvents();
         ((Either)_snowman.getSecond()).ifLeft(_snowmanxxxxxx -> {
            String _snowmanxxxxxxx = _snowman.toString();
            if (_snowman) {
               _snowman.method_22593(_snowmanxxxxxxx);
            }

            _snowman.setEvent(_snowmanxxxxxxx, _snowman, new FunctionTimerCallback(_snowman));
            source.sendFeedback(new TranslatableText("commands.schedule.created.function", _snowman, _snowman, _snowman), true);
         }).ifRight(_snowmanxxxxxx -> {
            String _snowmanxxxxxxx = "#" + _snowman.toString();
            if (_snowman) {
               _snowman.method_22593(_snowmanxxxxxxx);
            }

            _snowman.setEvent(_snowmanxxxxxxx, _snowman, new FunctionTagTimerCallback(_snowman));
            source.sendFeedback(new TranslatableText("commands.schedule.created.tag", _snowman, _snowman, _snowman), true);
         });
         return (int)Math.floorMod(_snowmanxxx, 2147483647L);
      }
   }

   private static int method_22833(ServerCommandSource _snowman, String _snowman) throws CommandSyntaxException {
      int _snowmanxx = _snowman.getMinecraftServer().getSaveProperties().getMainWorldProperties().getScheduledEvents().method_22593(_snowman);
      if (_snowmanxx == 0) {
         throw CLEARED_FAILURE_EXCEPTION.create(_snowman);
      } else {
         _snowman.sendFeedback(new TranslatableText("commands.schedule.cleared.success", _snowmanxx, _snowman), true);
         return _snowmanxx;
      }
   }
}
