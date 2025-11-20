package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.IntegerArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.text.TranslatableText;

public class WeatherCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("weather")
                     .requires(_snowman -> _snowman.hasPermissionLevel(2)))
                  .then(
                     ((LiteralArgumentBuilder)CommandManager.literal("clear").executes(_snowman -> executeClear((ServerCommandSource)_snowman.getSource(), 6000)))
                        .then(
                           CommandManager.argument("duration", IntegerArgumentType.integer(0, 1000000))
                              .executes(_snowman -> executeClear((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "duration") * 20))
                        )
                  ))
               .then(
                  ((LiteralArgumentBuilder)CommandManager.literal("rain").executes(_snowman -> executeRain((ServerCommandSource)_snowman.getSource(), 6000)))
                     .then(
                        CommandManager.argument("duration", IntegerArgumentType.integer(0, 1000000))
                           .executes(_snowman -> executeRain((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "duration") * 20))
                     )
               ))
            .then(
               ((LiteralArgumentBuilder)CommandManager.literal("thunder").executes(_snowman -> executeThunder((ServerCommandSource)_snowman.getSource(), 6000)))
                  .then(
                     CommandManager.argument("duration", IntegerArgumentType.integer(0, 1000000))
                        .executes(_snowman -> executeThunder((ServerCommandSource)_snowman.getSource(), IntegerArgumentType.getInteger(_snowman, "duration") * 20))
                  )
            )
      );
   }

   private static int executeClear(ServerCommandSource source, int duration) {
      source.getWorld().setWeather(duration, 0, false, false);
      source.sendFeedback(new TranslatableText("commands.weather.set.clear"), true);
      return duration;
   }

   private static int executeRain(ServerCommandSource source, int duration) {
      source.getWorld().setWeather(0, duration, true, false);
      source.sendFeedback(new TranslatableText("commands.weather.set.rain"), true);
      return duration;
   }

   private static int executeThunder(ServerCommandSource source, int duration) {
      source.getWorld().setWeather(0, duration, true, true);
      source.sendFeedback(new TranslatableText("commands.weather.set.thunder"), true);
      return duration;
   }
}
