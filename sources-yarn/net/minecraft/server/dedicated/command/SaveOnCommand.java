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
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("save-on").requires(arg -> arg.hasPermissionLevel(4)))
            .executes(commandContext -> {
               ServerCommandSource lv = (ServerCommandSource)commandContext.getSource();
               boolean bl = false;

               for (ServerWorld lv2 : lv.getMinecraftServer().getWorlds()) {
                  if (lv2 != null && lv2.savingDisabled) {
                     lv2.savingDisabled = false;
                     bl = true;
                  }
               }

               if (!bl) {
                  throw ALREADY_ON_EXCEPTION.create();
               } else {
                  lv.sendFeedback(new TranslatableText("commands.save.enabled"), true);
                  return 1;
               }
            })
      );
   }
}
