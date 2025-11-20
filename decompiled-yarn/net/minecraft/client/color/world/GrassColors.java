package net.minecraft.client.color.world;

public class GrassColors {
   private static int[] colorMap = new int[65536];

   public static void setColorMap(int[] map) {
      colorMap = map;
   }

   public static int getColor(double temperature, double humidity) {
      humidity *= temperature;
      int _snowman = (int)((1.0 - temperature) * 255.0);
      int _snowmanx = (int)((1.0 - humidity) * 255.0);
      int _snowmanxx = _snowmanx << 8 | _snowman;
      return _snowmanxx > colorMap.length ? -65281 : colorMap[_snowmanxx];
   }
}
