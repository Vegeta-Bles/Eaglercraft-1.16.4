import com.google.common.collect.Lists;
import com.ibm.icu.lang.UCharacter;
import com.ibm.icu.text.ArabicShaping;
import com.ibm.icu.text.Bidi;
import com.ibm.icu.text.BidiRun;
import java.util.List;

public class ekw {
   public static afa a(nu var0, boolean var1) {
      oc _snowman = oc.a(_snowman, UCharacter::getMirror, ekw::a);
      Bidi _snowmanx = new Bidi(_snowman.a(), _snowman ? 127 : 126);
      _snowmanx.setReorderingMode(0);
      List<afa> _snowmanxx = Lists.newArrayList();
      int _snowmanxxx = _snowmanx.countRuns();

      for (int _snowmanxxxx = 0; _snowmanxxxx < _snowmanxxx; _snowmanxxxx++) {
         BidiRun _snowmanxxxxx = _snowmanx.getVisualRun(_snowmanxxxx);
         _snowmanxx.addAll(_snowman.a(_snowmanxxxxx.getStart(), _snowmanxxxxx.getLength(), _snowmanxxxxx.isOddRun()));
      }

      return afa.a(_snowmanxx);
   }

   private static String a(String var0) {
      try {
         return new ArabicShaping(8).shape(_snowman);
      } catch (Exception var2) {
         return _snowman;
      }
   }
}
