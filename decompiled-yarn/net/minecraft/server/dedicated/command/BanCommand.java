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
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("ban").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("targets", GameProfileArgumentType.gameProfile())
                     .executes(_snowman -> ban((ServerCommandSource)_snowman.getSource(), GameProfileArgumentType.getProfileArgument(_snowman, "targets"), null)))
                  .then(
                     CommandManager.argument("reason", MessageArgumentType.message())
                        .executes(
                           _snowman -> ban(
                                 (ServerCommandSource)_snowman.getSource(),
                                 GameProfileArgumentType.getProfileArgument(_snowman, "targets"),
                                 MessageArgumentType.getMessage(_snowman, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int ban(ServerCommandSource source, Collection<GameProfile> targets, @Nullable Text reason) throws CommandSyntaxException {
      BannedPlayerList _snowman = source.getMinecraftServer().getPlayerManager().getUserBanList();
      int _snowmanx = 0;

      for (GameProfile _snowmanxx : targets) {
         if (!_snowman.contains(_snowmanxx)) {
            BannedPlayerEntry _snowmanxxx = new BannedPlayerEntry(_snowmanxx, null, source.getName(), null, reason == null ? null : reason.getString());
            _snowman.add(_snowmanxxx);
            _snowmanx++;
            source.sendFeedback(new TranslatableText("commands.ban.success", Texts.toText(_snowmanxx), _snowmanxxx.getReason()), true);
            ServerPlayerEntity _snowmanxxxx = source.getMinecraftServer().getPlayerManager().getPlayer(_snowmanxx.getId());
            if (_snowmanxxxx != null) {
               _snowmanxxxx.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.banned"));
            }
         }
      }

      if (_snowmanx == 0) {
         throw ALREADY_BANNED_EXCEPTION.create();
      } else {
         return _snowmanx;
      }
   }
}
