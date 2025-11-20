package net.minecraft.client.resource.language;

import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.List;
import net.minecraft.text.OrderedText;
import net.minecraft.text.StringVisitable;

public class ReorderingUtil {
   public static OrderedText reorder(StringVisitable text, boolean rightToLeft) {
      TextReorderingProcessor _snowman = TextReorderingProcessor.create(text, UCharacter::getMirror, ReorderingUtil::shapeArabic);
      Bidi _snowmanx = new Bidi(_snowman.getString(), rightToLeft ? 127 : 126);
      _snowmanx.setReorderingMode(0);
      List<OrderedText> _snowmanxx = Lists.newArrayList();
      int _snowmanxxx = _snowmanx.countRuns();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         BidiRun _snowmanxxxxx = _snowmanx.getVisualRun(_snowmanxxxx);
         _snowmanxx.addAll(_snowman.process(_snowmanxxxxx.getStart(), _snowmanxxxxx.getLength(), _snowmanxxxxx.isOddRun()));
      }

      return OrderedText.concat(_snowmanxx);
   }

   private static String shapeArabic(String string) {
      try {
         return new ArabicShaping(8).shape(string);
      } catch (Exception var2) {
         return string;
      }
   }
}
