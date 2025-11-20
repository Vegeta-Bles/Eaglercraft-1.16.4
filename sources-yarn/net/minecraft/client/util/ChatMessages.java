package net.minecraft.client.util;

import com.google.common.collect.Lists;
import java.util.List;
import java.util.Optional;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;
import net.minecraft.text.Style;
import net.minecraft.util.Formatting;
import net.minecraft.util.Language;

@Environment(EnvType.CLIENT)
public class ChatMessages {
   private static final OrderedText field_25263 = OrderedText.styled(32, Style.EMPTY);

   private static String getRenderedChatMessage(String message) {
      return MinecraftClient.getInstance().options.chatColors ? message : Formatting.strip(message);
   }

   public static List<OrderedText> breakRenderedChatMessageLines(StringVisitable arg, int width, TextRenderer textRenderer) {
      TextCollector lv = new TextCollector();
      arg.visit((arg2, string) -> {
         lv.add(StringVisitable.styled(getRenderedChatMessage(string), arg2));
         return Optional.empty();
      }, Style.EMPTY);
      List<OrderedText> list = Lists.newArrayList();
      textRenderer.getTextHandler().method_29971(lv.getCombined(), width, Style.EMPTY, (argx, boolean_) -> {
         OrderedText lvx = Language.getInstance().reorder(argx);
         list.add(boolean_ ? OrderedText.concat(field_25263, lvx) : lvx);
      });
      return (List<OrderedText>)(list.isEmpty() ? Lists.newArrayList(new OrderedText[]{OrderedText.EMPTY}) : list);
   }
}
