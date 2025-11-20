package net.minecraft.server.dedicated.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.arguments.StringArgumentType;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.builder.RequiredArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import javax.annotation.Nullable;
import net.minecraft.command.EntitySelector;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.server.BannedIpEntry;
import net.minecraft.server.BannedIpList;
import net.minecraft.server.command.CommandManager;
import net.minecraft.server.command.ServerCommandSource;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class BanIpCommand {
   public static final Pattern PATTERN = Pattern.compile(
      "^([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])\\.([01]?\\d\\d?|2[0-4]\\d|25[0-5])$"
   );
   private static final SimpleCommandExceptionType INVALID_IP_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.banip.invalid"));
   private static final SimpleCommandExceptionType ALREADY_BANNED_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.banip.failed"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("ban-ip").requires(_snowman -> _snowman.hasPermissionLevel(3)))
            .then(
               ((RequiredArgumentBuilder)CommandManager.argument("target", StringArgumentType.word())
                     .executes(_snowman -> checkIp((ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "target"), null)))
                  .then(
                     CommandManager.argument("reason", MessageArgumentType.message())
                        .executes(
                           _snowman -> checkIp(
                                 (ServerCommandSource)_snowman.getSource(), StringArgumentType.getString(_snowman, "target"), MessageArgumentType.getMessage(_snowman, "reason")
                              )
                        )
                  )
            )
      );
   }

   private static int checkIp(ServerCommandSource source, String target, @Nullable Text reason) throws CommandSyntaxException {
      Matcher _snowman = PATTERN.matcher(target);
      if (_snowman.matches()) {
         return banIp(source, target, reason);
      } else {
         ServerPlayerEntity _snowmanx = source.getMinecraftServer().getPlayerManager().getPlayer(target);
         if (_snowmanx != null) {
            return banIp(source, _snowmanx.getIp(), reason);
         } else {
            throw INVALID_IP_EXCEPTION.create();
         }
      }
   }

   private static int banIp(ServerCommandSource source, String targetIp, @Nullable Text reason) throws CommandSyntaxException {
      BannedIpList _snowman = source.getMinecraftServer().getPlayerManager().getIpBanList();
      if (_snowman.isBanned(targetIp)) {
         throw ALREADY_BANNED_EXCEPTION.create();
      } else {
         List<ServerPlayerEntity> _snowmanx = source.getMinecraftServer().getPlayerManager().getPlayersByIp(targetIp);
         BannedIpEntry _snowmanxx = new BannedIpEntry(targetIp, null, source.getName(), null, reason == null ? null : reason.getString());
         _snowman.add(_snowmanxx);
         source.sendFeedback(new TranslatableText("commands.banip.success", targetIp, _snowmanxx.getReason()), true);
         if (!_snowmanx.isEmpty()) {
            source.sendFeedback(new TranslatableText("commands.banip.info", _snowmanx.size(), EntitySelector.getNames(_snowmanx)), true);
         }

         for (ServerPlayerEntity _snowmanxxx : _snowmanx) {
            _snowmanxxx.networkHandler.disconnect(new TranslatableText("multiplayer.disconnect.ip_banned"));
         }

         return _snowmanx.size();
      }
   }
}
