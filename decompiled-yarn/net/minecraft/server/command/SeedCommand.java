package net.minecraft.server.command;

import com.mojang.brigadier.CommandDispatcher;
import com.mojang.brigadier.builder.LiteralArgumentBuilder;
import net.minecraft.text.ClickEvent;
import net.minecraft.text.HoverEvent;
import net.minecraft.text.LiteralText;
import net.minecraft.text.Text;
import net.minecraft.text.Texts;
import net.minecraft.text.TranslatableText;
import net.minecraft.util.Formatting;

public class SeedCommand {
   public static void register(CommandDispatcher<ServerCommandSource> dispatcher, boolean dedicated) {
      dispatcher.register(
         (LiteralArgumentBuilder)((LiteralArgumentBuilder)CommandManager.literal("seed").requires(_snowmanx -> !dedicated || _snowmanx.hasPermissionLevel(2)))
            .executes(
               _snowman -> {
                  long _snowmanx = ((ServerCommandSource)_snowman.getSource()).getWorld().getSeed();
                  Text _snowmanxx = Texts.bracketed(
                     new LiteralText(String.valueOf(_snowmanx))
                        .styled(
                           _snowmanxxx -> _snowmanxxx.withColor(Formatting.GREEN)
                                 .withClickEvent(new ClickEvent(ClickEvent.Action.COPY_TO_CLIPBOARD, String.valueOf(_snowman)))
                                 .withHoverEvent(new HoverEvent(HoverEvent.Action.SHOW_TEXT, new TranslatableText("chat.copy.click")))
                                 .withInsertion(String.valueOf(_snowman))
                        )
                  );
                  ((ServerCommandSource)_snowman.getSource()).sendFeedback(new TranslatableText("commands.seed.success", _snowmanxx), false);
                  return (int)_snowmanx;
               }
            )
      );
   }
}
