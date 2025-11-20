package net.minecraft.client.resource.language;

import com.mojang.bridge.game.Language;

public class LanguageDefinition implements Language, Comparable<LanguageDefinition> {
   private final String code;
   private final String name;
   private final String region;
   private final boolean rightToLeft;

   public LanguageDefinition(String code, String name, String region, boolean rightToLeft) {
      this.code = code;
      this.name = name;
      this.region = region;
      this.rightToLeft = rightToLeft;
   }

   public String getCode() {
      return this.code;
   }

   public String getName() {
      return this.region;
   }

   public String getRegion() {
      return this.name;
   }

   public boolean isRightToLeft() {
      return this.rightToLeft;
   }

   @Override
   public String toString() {
      return String.format("%s (%s)", this.region, this.name);
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else {
         return !(o instanceof LanguageDefinition) ? false : this.code.equals(((LanguageDefinition)o).code);
      }
   }

   @Override
   public int hashCode() {
      return this.code.hashCode();
   }

   public int compareTo(LanguageDefinition _snowman) {
      return this.code.compareTo(_snowman.code);
   }
}
