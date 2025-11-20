package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.command.argument.TimeArgumentType;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;

public class TimeCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("time")
                     .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                  .then(
                     ((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("set")
                                    .then(CommandManager.literal("day").executes(_snowman -> executeSet((ServerCommandSource)_snowman.getSource(), 1000))))
                                 .then(CommandManager.literal("noon").executes(_snowman -> executeSet((ServerCommandSource)_snowman.getSource(), 6000))))
                              .then(CommandManager.literal("night").executes(_snowman -> executeSet((ServerCommandSource)_snowman.getSource(), 13000))))
                           .then(CommandManager.literal("midnight").executes(_snowman -> executeSet((ServerCommandSource)_snowman.getSource(), 18000))))
                        .then(
                           CommandManager.argument("time", TimeArgumentType.time())
                              .executes(_snowman -> executeSet((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "time")))
                        )
                  ))
               .then(
                  CommandManager.literal("add")
                     .then(
                        CommandManager.argument("time", TimeArgumentType.time())
                           .executes(_snowman -> executeAdd((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "time")))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("query")
                        .then(
                           CommandManager.literal("daytime")
                              .executes(_snowman -> executeQuery((ServerCommandSource)_snowman.getSource(), getDayTime(((ServerCommandSource)_snowman.getSource()).getWorld())))
                        ))
                     .then(
                        CommandManager.literal("gametime")
                           .executes(
                              _snowman -> executeQuery(
                                    (ServerCommandSource)_snowman.getSource(), (int)(((ServerCommandSource)_snowman.getSource()).getWorld().getTime() % 2147483647L)
                                 )
                           )
                     ))
                  .then(
                     CommandManager.literal("day")
                        .executes(
                           _snowman -> executeQuery(
                                 (ServerCommandSource)_snowman.getSource(),
                                 (int)(((ServerCommandSource)_snowman.getSource()).getWorld().getTimeOfDay() / 24000L % 2147483647L)
                              )
                        )
                  )
            )
      );
   }

   private static int getDayTime(ServerWorld world) {
      return (int)(world.getTimeOfDay() % 24000L);
   }

   private static int executeQuery(ServerCommandSource source, int time) {
      source.sendFeedback(new TranslatableText("commands.time.query", time), false);
      return time;
   }

   public static int executeSet(ServerCommandSource source, int time) {
      for (ServerWorld _snowman : source.getMinecraftServer().getWorlds()) {
         _snowman.setTimeOfDay((long)time);
      }

      source.sendFeedback(new TranslatableText("commands.time.set", time), true);
      return getDayTime(source.getWorld());
   }

   public static int executeAdd(ServerCommandSource source, int time) {
      for (ServerWorld _snowman : source.getMinecraftServer().getWorlds()) {
         _snowman.setTimeOfDay(_snowman.getTimeOfDay() + (long)time);
      }

      int _snowman = getDayTime(source.getWorld());
      source.sendFeedback(new TranslatableText("commands.time.set", _snowman), true);
      return _snowman;
   }
}
