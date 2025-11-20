package net.minecraft.stat;

import java.text.DecimalFormat;
import java.text.DecimalFormatSymbols;
import java.text.NumberFormat;
import java.util.Locale;
import net.minecraft.util.Util;

public interface StatFormatter {
   DecimalFormat DECIMAL_FORMAT = Util.make(new DecimalFormat("########0.00"), _snowman -> _snowman.setDecimalFormatSymbols(DecimalFormatSymbols.getInstance(Locale.ROOT)));
   StatFormatter DEFAULT = NumberFormat.getIntegerInstance(Locale.US)::format;
   StatFormatter DIVIDE_BY_TEN = _snowman -> DECIMAL_FORMAT.format((double)_snowman * 0.1);
   StatFormatter DISTANCE = _snowman -> {
      double _snowmanx = (double)_snowman / 100.0;
      double _snowmanxx = _snowmanx / 1000.0;
      if (_snowmanxx > 0.5) {
         return DECIMAL_FORMAT.format(_snowmanxx) + " km";
      } else {
         return _snowmanx > 0.5 ? DECIMAL_FORMAT.format(_snowmanx) + " m" : _snowman + " cm";
      }
   };
   StatFormatter TIME = _snowman -> {
      double _snowmanx = (double)_snowman / 20.0;
      double _snowmanxx = _snowmanx / 60.0;
      double _snowmanxxx = _snowmanxx / 60.0;
      double _snowmanxxxx = _snowmanxxx / 24.0;
      double _snowmanxxxxx = _snowmanxxxx / 365.0;
      if (_snowmanxxxxx > 0.5) {
         return DECIMAL_FORMAT.format(_snowmanxxxxx) + " y";
      } else if (_snowmanxxxx > 0.5) {
         return DECIMAL_FORMAT.format(_snowmanxxxx) + " d";
      } else if (_snowmanxxx > 0.5) {
         return DECIMAL_FORMAT.format(_snowmanxxx) + " h";
      } else {
         return _snowmanxx > 0.5 ? DECIMAL_FORMAT.format(_snowmanxx) + " m" : _snowmanx + " s";
      }
   };

   String format(int var1);
}
