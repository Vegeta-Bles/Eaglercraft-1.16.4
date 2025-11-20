package net.minecraft.world;

public enum TickPriority {
   EXTREMELY_HIGH(-3),
   VERY_HIGH(-2),
   HIGH(-1),
   NORMAL(0),
   LOW(1),
   VERY_LOW(2),
   EXTREMELY_LOW(3);

   private final int index;

   private TickPriority(int index) {
      this.index = index;
   }

   public static TickPriority byIndex(int index) {
      for (TickPriority lv : values()) {
         if (lv.index == index) {
            return lv;
         }
      }

      return index < EXTREMELY_HIGH.index ? EXTREMELY_HIGH : EXTREMELY_LOW;
   }

   public int getIndex() {
      return this.index;
   }
}
