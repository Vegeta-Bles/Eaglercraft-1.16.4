package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.text.TranslatableText;

public class SaveOnCommand {
   private static final SimpleCommandExceptionType ALREADY_ON_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.save.alreadyOn"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("save-on").requires(_snowman -> _snowman.hasPermissionLevel(4))).executes(_snowman -> {
            ServerCommandSource _snowmanx = (ServerCommandSource)_snowman.getSource();
            boolean _snowmanxx = false;

            for (ServerWorld _snowmanxxx : _snowmanx.getMinecraftServer().getWorlds()) {
               if (_snowmanxxx != null && _snowmanxxx.savingDisabled) {
                  _snowmanxxx.savingDisabled = false;
                  _snowmanxx = true;
               }
            }

            if (!_snowmanxx) {
               throw ALREADY_ON_EXCEPTION.create();
            } else {
               _snowmanx.sendFeedback(new TranslatableText("commands.save.enabled"), true);
               return 1;
            }
         })
      );
   }
}
