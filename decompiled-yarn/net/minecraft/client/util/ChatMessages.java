package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;

public class ChatMessages {
   private static final OrderedText field_25263 = OrderedText.styled(32, Style.EMPTY);

   private static String getRenderedChatMessage(String message) {
      return MinecraftClient.getInstance().options.chatColors ? message : Formatting.strip(message);
   }

   public static List<OrderedText> breakRenderedChatMessageLines(StringVisitable _snowman, int width, TextRenderer textRenderer) {
      TextCollector _snowmanx = new TextCollector();
      _snowman.visit((_snowmanxxx, _snowmanxx) -> {
         _snowman.add(StringVisitable.styled(getRenderedChatMessage(_snowmanxx), _snowmanxxx));
         return Optional.empty();
      }, Style.EMPTY);
      List<OrderedText> _snowmanxx = Lists.newArrayList();
      textRenderer.getTextHandler().method_29971(_snowmanx.getCombined(), width, Style.EMPTY, (_snowmanxxxx, _snowmanxxx) -> {
         OrderedText _snowmanxxx = Language.getInstance().reorder(_snowmanxxxx);
         _snowman.add(_snowmanxxx ? OrderedText.concat(field_25263, _snowmanxxx) : _snowmanxxx);
      });
      return (List<OrderedText>)(_snowmanxx.isEmpty() ? Lists.newArrayList(new OrderedText[]{OrderedText.EMPTY}) : _snowmanxx);
   }
}
