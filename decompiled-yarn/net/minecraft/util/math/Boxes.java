package net.minecraft.util.math;

public class Boxes {
   public static Box stretch(Box box, Direction direction, double length) {
      double _snowman = length * (double)direction.getDirection().offset();
      double _snowmanx = Math.min(_snowman, 0.0);
      double _snowmanxx = Math.max(_snowman, 0.0);
      switch (direction) {
         case WEST:
            return new Box(box.minX + _snowmanx, box.minY, box.minZ, box.minX + _snowmanxx, box.maxY, box.maxZ);
         case EAST:
            return new Box(box.maxX + _snowmanx, box.minY, box.minZ, box.maxX + _snowmanxx, box.maxY, box.maxZ);
         case DOWN:
            return new Box(box.minX, box.minY + _snowmanx, box.minZ, box.maxX, box.minY + _snowmanxx, box.maxZ);
         case UP:
         default:
            return new Box(box.minX, box.maxY + _snowmanx, box.minZ, box.maxX, box.maxY + _snowmanxx, box.maxZ);
         case NORTH:
            return new Box(box.minX, box.minY, box.minZ + _snowmanx, box.maxX, box.maxY, box.minZ + _snowmanxx);
         case SOUTH:
            return new Box(box.minX, box.minY, box.maxZ + _snowmanx, box.maxX, box.maxY, box.maxZ + _snowmanxx);
      }
   }
}
