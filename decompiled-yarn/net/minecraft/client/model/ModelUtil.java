package net.minecraft.client.model;

public class ModelUtil {
   public static float interpolateAngle(float angle1, float angle2, float progress) {
      float _snowman = angle2 - angle1;

      while (_snowman < (float) -Math.PI) {
         _snowman += (float) (Math.PI * 2);
      }

      while (_snowman >= (float) Math.PI) {
         _snowman -= (float) (Math.PI * 2);
      }

      return angle1 + progress * _snowman;
   }
}
