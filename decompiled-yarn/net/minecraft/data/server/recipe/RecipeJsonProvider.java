package net.minecraft.data.server.recipe;

import com.google.gson.JsonObject;
import javax.annotation.Nullable;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public interface RecipeJsonProvider {
   void serialize(JsonObject json);

   default JsonObject toJson() {
      JsonObject _snowman = new JsonObject();
      _snowman.addProperty("type", Registry.RECIPE_SERIALIZER.getId(this.getSerializer()).toString());
      this.serialize(_snowman);
      return _snowman;
   }

   Identifier getRecipeId();

   RecipeSerializer<?> getSerializer();

   @Nullable
   JsonObject toAdvancementJson();

   @Nullable
   Identifier getAdvancementId();
}
