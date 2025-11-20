package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.tree.LiteralCommandNode;
import java.util.List;
import net.minecraft.command.argument.MessageArgumentType;
import net.minecraft.entity.Entity;
import net.minecraft.scoreboard.Team;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.Style;
import net.minecraft.text.Text;
import net.minecraft.text.TranslatableText;

public class TeamMsgCommand {
   private static final Style STYLE = Style.EMPTY
      .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.type.team.hover")))
      .withClickEvent(new ClickEvent(ClickEvent.Action.SUGGEST_COMMAND, "/teammsg "));
   private static final SimpleCommandExceptionType NO_TEAM_EXCEPTION = new SimpleCommandExceptionType(new TranslatableText("commands.teammsg.failed.noteam"));

   public static void register(CommandDispatcher<ServerCommandSource> dispatcher) {
      LiteralCommandNode<ServerCommandSource> _snowman = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("teammsg")
            .then(
               CommandManager.argument("message", MessageArgumentType.message())
                  .executes(_snowmanx -> execute((ServerCommandSource)_snowmanx.getSource(), MessageArgumentType.getMessage(_snowmanx, "message")))
            )
      );
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("tm").redirect(_snowman));
   }

   private static int execute(ServerCommandSource source, Text message) throws CommandSyntaxException {
      Entity _snowman = source.getEntityOrThrow();
      Team _snowmanx = (Team)_snowman.getScoreboardTeam();
      if (_snowmanx == null) {
         throw NO_TEAM_EXCEPTION.create();
      } else {
         Text _snowmanxx = _snowmanx.getFormattedName().fillStyle(STYLE);
         List<ServerPlayerEntity> _snowmanxxx = source.getMinecraftServer().getPlayerManager().getPlayerList();

         for (ServerPlayerEntity _snowmanxxxx : _snowmanxxx) {
            if (_snowmanxxxx == _snowman) {
               _snowmanxxxx.sendSystemMessage(new TranslatableText("chat.type.team.sent", _snowmanxx, source.getDisplayName(), message), _snowman.getUuid());
            } else if (_snowmanxxxx.getScoreboardTeam() == _snowmanx) {
               _snowmanxxxx.sendSystemMessage(new TranslatableText("chat.type.team.text", _snowmanxx, source.getDisplayName(), message), _snowman.getUuid());
            }
         }

         return _snowmanxxx.size();
      }
   }
}
