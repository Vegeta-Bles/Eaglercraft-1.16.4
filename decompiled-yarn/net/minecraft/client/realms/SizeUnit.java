package net.minecraft.client.realms;

import java.util.Locale;

public enum SizeUnit {
   B,
   KB,
   MB,
   GB;

   private SizeUnit() {
   }

   public static SizeUnit getLargestUnit(long bytes) {
      if (bytes < 1024L) {
         return B;
      } else {
         try {
            int _snowman = (int)(Math.log((double)bytes) / Math.log(1024.0));
            String _snowmanx = String.valueOf("KMGTPE".charAt(_snowman - 1));
            return valueOf(_snowmanx + "B");
         } catch (Exception var4) {
            return GB;
         }
      }
   }

   public static double convertToUnit(long bytes, SizeUnit unit) {
      return unit == B ? (double)bytes : (double)bytes / Math.pow(1024.0, (double)unit.ordinal());
   }

   public static String getUserFriendlyString(long bytes) {
      int _snowman = 1024;
      if (bytes < 1024L) {
         return bytes + " B";
      } else {
         int _snowmanx = (int)(Math.log((double)bytes) / Math.log(1024.0));
         String _snowmanxx = "KMGTPE".charAt(_snowmanx - 1) + "";
         return String.format(Locale.ROOT, "%.1f %sB", (double)bytes / Math.pow(1024.0, (double)_snowmanx), _snowmanxx);
      }
   }

   public static String humanReadableSize(long bytes, SizeUnit unit) {
      return String.format("%." + (unit == GB ? "1" : "0") + "f %s", convertToUnit(bytes, unit), unit.name());
   }
}
