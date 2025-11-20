package net.minecraft.util.math;

import java.util.Arrays;
import net.minecraft.util.Util;

public enum AxisTransformation {
   P123(0, 1, 2),
   P213(1, 0, 2),
   P132(0, 2, 1),
   P231(1, 2, 0),
   P312(2, 0, 1),
   P321(2, 1, 0);

   private final int[] mappings;
   private final Matrix3f matrix;
   private static final AxisTransformation[][] COMBINATIONS = Util.make(new AxisTransformation[values().length][values().length], _snowman -> {
      for (AxisTransformation _snowmanx : values()) {
         for (AxisTransformation _snowmanxx : values()) {
            int[] _snowmanxxx = new int[3];

            for (int _snowmanxxxx = 0; _snowmanxxxx < 3; _snowmanxxxx++) {
               _snowmanxxx[_snowmanxxxx] = _snowmanx.mappings[_snowmanxx.mappings[_snowmanxxxx]];
            }

            AxisTransformation _snowmanxxxx = Arrays.stream(values()).filter(_snowmanxxxxx -> Arrays.equals(_snowmanxxxxx.mappings, _snowman)).findFirst().get();
            _snowman[_snowmanx.ordinal()][_snowmanxx.ordinal()] = _snowmanxxxx;
         }
      }
   });

   private AxisTransformation(int xMapping, int yMapping, int zMapping) {
      this.mappings = new int[]{xMapping, yMapping, zMapping};
      this.matrix = new Matrix3f();
      this.matrix.set(0, this.map(0), 1.0F);
      this.matrix.set(1, this.map(1), 1.0F);
      this.matrix.set(2, this.map(2), 1.0F);
   }

   public AxisTransformation prepend(AxisTransformation transformation) {
      return COMBINATIONS[this.ordinal()][transformation.ordinal()];
   }

   public int map(int oldAxis) {
      return this.mappings[oldAxis];
   }

   public Matrix3f getMatrix() {
      return this.matrix;
   }
}
