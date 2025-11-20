package net.minecraft.client.color.world;

public class FoliageColors {
   private static int[] colorMap = new int[65536];

   public static void setColorMap(int[] pixels) {
      colorMap = pixels;
   }

   public static int getColor(double temperature, double humidity) {
      humidity *= temperature;
      int _snowman = (int)((1.0 - temperature) * 255.0);
      int _snowmanx = (int)((1.0 - humidity) * 255.0);
      return colorMap[_snowmanx << 8 | _snowman];
   }

   public static int getSpruceColor() {
      return 6396257;
   }

   public static int getBirchColor() {
      return 8431445;
   }

   public static int getDefaultColor() {
      return 4764952;
   }
}
