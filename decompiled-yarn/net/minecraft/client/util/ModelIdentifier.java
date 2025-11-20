package net.minecraft.client.util;

import java.util.Locale;
import net.minecraft.util.Identifier;

public class ModelIdentifier extends Identifier {
   private final String variant;

   protected ModelIdentifier(String[] _snowman) {
      super(_snowman);
      this.variant = _snowman[2].toLowerCase(Locale.ROOT);
   }

   public ModelIdentifier(String _snowman) {
      this(split(_snowman));
   }

   public ModelIdentifier(Identifier id, String variant) {
      this(id.toString(), variant);
   }

   public ModelIdentifier(String _snowman, String _snowman) {
      this(split(_snowman + '#' + _snowman));
   }

   protected static String[] split(String id) {
      String[] _snowman = new String[]{null, id, ""};
      int _snowmanx = id.indexOf(35);
      String _snowmanxx = id;
      if (_snowmanx >= 0) {
         _snowman[2] = id.substring(_snowmanx + 1, id.length());
         if (_snowmanx > 1) {
            _snowmanxx = id.substring(0, _snowmanx);
         }
      }

      System.arraycopy(Identifier.split(_snowmanxx, ':'), 0, _snowman, 0, 2);
      return _snowman;
   }

   public String getVariant() {
      return this.variant;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else if (_snowman instanceof ModelIdentifier && super.equals(_snowman)) {
         ModelIdentifier _snowmanx = (ModelIdentifier)_snowman;
         return this.variant.equals(_snowmanx.variant);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return 31 * super.hashCode() + this.variant.hashCode();
   }

   @Override
   public String toString() {
      return super.toString() + '#' + this.variant;
   }
}
