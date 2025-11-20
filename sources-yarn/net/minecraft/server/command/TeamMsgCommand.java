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
      LiteralCommandNode<ServerCommandSource> literalCommandNode = dispatcher.register(
         (LiteralArgumentBuilder)CommandManager.literal("teammsg")
            .then(
               CommandManager.argument("message", MessageArgumentType.message())
                  .executes(
                     commandContext -> execute((ServerCommandSource)commandContext.getSource(), MessageArgumentType.getMessage(commandContext, "message"))
                  )
            )
      );
      dispatcher.register((LiteralArgumentBuilder)CommandManager.literal("tm").redirect(literalCommandNode));
   }

   private static int execute(ServerCommandSource source, Text message) throws CommandSyntaxException {
      Entity lv = source.getEntityOrThrow();
      Team lv2 = (Team)lv.getScoreboardTeam();
      if (lv2 == null) {
         throw NO_TEAM_EXCEPTION.create();
      } else {
         Text lv3 = lv2.getFormattedName().fillStyle(STYLE);
         List<ServerPlayerEntity> list = source.getMinecraftServer().getPlayerManager().getPlayerList();

         for (ServerPlayerEntity lv4 : list) {
            if (lv4 == lv) {
               lv4.sendSystemMessage(new TranslatableText("chat.type.team.sent", lv3, source.getDisplayName(), message), lv.getUuid());
            } else if (lv4.getScoreboardTeam() == lv2) {
               lv4.sendSystemMessage(new TranslatableText("chat.type.team.text", lv3, source.getDisplayName(), message), lv.getUuid());
            }
         }

         return list.size();
      }
   }
}
