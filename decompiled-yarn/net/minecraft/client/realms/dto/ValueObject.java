package net.minecraft.client.realms.dto;

import com.google.gson.annotations.SerializedName;
import java.lang.reflect.Field;
import java.lang.reflect.Modifier;

public abstract class ValueObject {
   public ValueObject() {
   }

   @Override
   public String toString() {
      StringBuilder _snowman = new StringBuilder("{");

      for (Field _snowmanx : this.getClass().getFields()) {
         if (!isStatic(_snowmanx)) {
            try {
               _snowman.append(getName(_snowmanx)).append("=").append(_snowmanx.get(this)).append(" ");
            } catch (IllegalAccessException var7) {
            }
         }
      }

      _snowman.deleteCharAt(_snowman.length() - 1);
      _snowman.append('}');
      return _snowman.toString();
   }

   private static String getName(Field f) {
      SerializedName _snowman = f.getAnnotation(SerializedName.class);
      return _snowman != null ? _snowman.value() : f.getName();
   }

   private static boolean isStatic(Field f) {
      return Modifier.isStatic(f.getModifiers());
   }
}
