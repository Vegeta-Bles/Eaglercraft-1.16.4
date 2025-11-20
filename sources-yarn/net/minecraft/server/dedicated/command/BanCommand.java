package net.minecraft.server.dedicated.command;

import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.Collection;
import javax.annotation.Nullable;
import net.minecraft.command.argument.GameProfileArgumentType;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.BannedPlayerEntry;
import net.minecraft.server.BannedPlayerList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class BanCommand {
   private static final SimpleCommandExceptionType ALREADY_BANNED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.ban.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("ban").requires(arg -> arg.hasPermissionLevel(3)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                     .executes(
                        commandContext -> ban(
                              (ServerCommandSource)commandContext.getSource(), GameProfileArgumentType.getProfileArgument(commandContext, "targets"), null
                           )
                     ))
                  .then(
                     CommandManager.argument("reason", MessageArgumentType.message())
                        .executes(
                           commandContext -> ban(
                                 (ServerCommandSource)commandContext.getSource(),
                                 GameProfileArgumentType.getProfileArgument(commandContext, "targets"),
                                 MessageArgumentType.getMessage(commandContext, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int ban(ServerCommandSource source, Collection<GameProfile> targets, @Nullable Text reason) throws CommandSyntaxException {
      BannedPlayerList lv = source.getMinecraftServer().getPlayerManager().getUserBanList();
      int i = 0;

      for (GameProfile gameProfile : targets) {
         if (!lv.contains(gameProfile)) {
            BannedPlayerEntry lv2 = new BannedPlayerEntry(gameProfile, null, source.getName(), null, reason == null ? null : reason.getString());
            lv.add(lv2);
            i++;
            source.sendFeedback(new TranslatableText("commands.ban.success", Texts.toText(gameProfile), lv2.getReason()), true);
            ServerPlayerEntity lv3 = source.getMinecraftServer().getPlayerManager().getPlayer(gameProfile.getId());
            if (lv3 != null) {
               lv3.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.banned"));
            }
         }
      }

      if (i == 0) {
         throw ALREADY_BANNED_EXCEPTION.create();
      } else {
         return i;
      }
   }
}
