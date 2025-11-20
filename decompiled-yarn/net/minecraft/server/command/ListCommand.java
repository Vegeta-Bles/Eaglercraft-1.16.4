package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import java.util.List;
import java.util.function.Function;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.server.PlayerManager;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;

public class ListCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("list").executes(_snowman -> executeNames((ServerCommandSource)_snowman.getSource())))
            .then(CommandManager.literal("uuids").executes(_snowman -> executeUuids((ServerCommandSource)_snowman.getSource())))
      );
   }

   private static int executeNames(ServerCommandSource source) {
      return execute(source, PlayerEntity::getDisplayName);
   }

   private static int executeUuids(ServerCommandSource source) {
      return execute(source, _snowman -> new TranslatableText("commands.list.nameAndId", _snowman.getName(), _snowman.getGameProfile().getId()));
   }

   private static int execute(ServerCommandSource source, Function<ServerPlayerEntity, Text> nameProvider) {
      PlayerManager _snowman = source.getMinecraftServer().getPlayerManager();
      List<ServerPlayerEntity> _snowmanx = _snowman.getPlayerList();
      Text _snowmanxx = Texts.join(_snowmanx, nameProvider);
      source.sendFeedback(new TranslatableText("commands.list.players", _snowmanx.size(), _snowman.getMaxPlayerCount(), _snowmanxx), false);
      return _snowmanx.size();
   }
}
