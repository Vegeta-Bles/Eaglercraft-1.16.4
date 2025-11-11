import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;

public interface ady {
   DecimalFormat a = x.a(new DecimalFormat("########0.00"), var0 -> var0.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
   ady b = NumberFormat.getIntegerInstance(Locale.US)::format;
   ady c = var0 -> a.format((double)var0 * 0.1);
   ady d = var0 -> {
      double _snowman = (double)var0 / 100.0;
      double _snowmanx = _snowman / 1000.0;
      if (_snowmanx > 0.5) {
         return a.format(_snowmanx) + " km";
      } else {
         return _snowman > 0.5 ? a.format(_snowman) + " m" : var0 + " cm";
      }
   };
   ady e = var0 -> {
      double _snowman = (double)var0 / 20.0;
      double _snowmanx = _snowman / 60.0;
      double _snowmanxx = _snowmanx / 60.0;
      double _snowmanxxx = _snowmanxx / 24.0;
      double _snowmanxxxx = _snowmanxxx / 365.0;
      if (_snowmanxxxx > 0.5) {
         return a.format(_snowmanxxxx) + " y";
      } else if (_snowmanxxx > 0.5) {
         return a.format(_snowmanxxx) + " d";
      } else if (_snowmanxx > 0.5) {
         return a.format(_snowmanxx) + " h";
      } else {
         return _snowmanx > 0.5 ? a.format(_snowmanx) + " m" : _snowman + " s";
      }
   };

   String format(int var1);
}
