package net.minecraft.recipe;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.JsonArray;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Map;
import java.util.Set;
import java.util.Map.Entry;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;

public class ShapedRecipe implements CraftingRecipe {
   private final int width;
   private final int height;
   private final DefaultedList<Ingredient> inputs;
   private final ItemStack output;
   private final Identifier id;
   private final String group;

   public ShapedRecipe(Identifier id, String group, int width, int height, DefaultedList<Ingredient> ingredients, ItemStack output) {
      this.id = id;
      this.group = group;
      this.width = width;
      this.height = height;
      this.inputs = ingredients;
      this.output = output;
   }

   @Override
   public Identifier getId() {
      return this.id;
   }

   @Override
   public RecipeSerializer<?> getSerializer() {
      return RecipeSerializer.SHAPED;
   }

   @Override
   public String getGroup() {
      return this.group;
   }

   @Override
   public ItemStack getOutput() {
      return this.output;
   }

   @Override
   public DefaultedList<Ingredient> getPreviewInputs() {
      return this.inputs;
   }

   @Override
   public boolean fits(int width, int height) {
      return width >= this.width && height >= this.height;
   }

   public boolean matches(CraftingInventory _snowman, World _snowman) {
      for (int _snowmanxx = 0; _snowmanxx <= _snowman.getWidth() - this.width; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx <= _snowman.getHeight() - this.height; _snowmanxxx++) {
            if (this.matchesSmall(_snowman, _snowmanxx, _snowmanxxx, true)) {
               return true;
            }

            if (this.matchesSmall(_snowman, _snowmanxx, _snowmanxxx, false)) {
               return true;
            }
         }
      }

      return false;
   }

   private boolean matchesSmall(CraftingInventory inv, int offsetX, int offsetY, boolean _snowman) {
      for (int _snowmanx = 0; _snowmanx < inv.getWidth(); _snowmanx++) {
         for (int _snowmanxx = 0; _snowmanxx < inv.getHeight(); _snowmanxx++) {
            int _snowmanxxx = _snowmanx - offsetX;
            int _snowmanxxxx = _snowmanxx - offsetY;
            Ingredient _snowmanxxxxx = Ingredient.EMPTY;
            if (_snowmanxxx >= 0 && _snowmanxxxx >= 0 && _snowmanxxx < this.width && _snowmanxxxx < this.height) {
               if (_snowman) {
                  _snowmanxxxxx = this.inputs.get(this.width - _snowmanxxx - 1 + _snowmanxxxx * this.width);
               } else {
                  _snowmanxxxxx = this.inputs.get(_snowmanxxx + _snowmanxxxx * this.width);
               }
            }

            if (!_snowmanxxxxx.test(inv.getStack(_snowmanx + _snowmanxx * inv.getWidth()))) {
               return false;
            }
         }
      }

      return true;
   }

   public ItemStack craft(CraftingInventory _snowman) {
      return this.getOutput().copy();
   }

   public int getWidth() {
      return this.width;
   }

   public int getHeight() {
      return this.height;
   }

   private static DefaultedList<Ingredient> getIngredients(String[] pattern, Map<String, Ingredient> key, int width, int height) {
      DefaultedList<Ingredient> _snowman = DefaultedList.ofSize(width * height, Ingredient.EMPTY);
      Set<String> _snowmanx = Sets.newHashSet(key.keySet());
      _snowmanx.remove(" ");

      for (int _snowmanxx = 0; _snowmanxx < pattern.length; _snowmanxx++) {
         for (int _snowmanxxx = 0; _snowmanxxx < pattern[_snowmanxx].length(); _snowmanxxx++) {
            String _snowmanxxxx = pattern[_snowmanxx].substring(_snowmanxxx, _snowmanxxx + 1);
            Ingredient _snowmanxxxxx = key.get(_snowmanxxxx);
            if (_snowmanxxxxx == null) {
               throw new JsonSyntaxException("Pattern references symbol '" + _snowmanxxxx + "' but it's not defined in the key");
            }

            _snowmanx.remove(_snowmanxxxx);
            _snowman.set(_snowmanxxx + width * _snowmanxx, _snowmanxxxxx);
         }
      }

      if (!_snowmanx.isEmpty()) {
         throw new JsonSyntaxException("Key defines symbols that aren't used in pattern: " + _snowmanx);
      } else {
         return _snowman;
      }
   }

   @VisibleForTesting
   static String[] combinePattern(String... lines) {
      int _snowman = Integer.MAX_VALUE;
      int _snowmanx = 0;
      int _snowmanxx = 0;
      int _snowmanxxx = 0;

      for (int _snowmanxxxx = 0; _snowmanxxxx < lines.length; _snowmanxxxx++) {
         String _snowmanxxxxx = lines[_snowmanxxxx];
         _snowman = Math.min(_snowman, findNextIngredient(_snowmanxxxxx));
         int _snowmanxxxxxx = findNextIngredientReverse(_snowmanxxxxx);
         _snowmanx = Math.max(_snowmanx, _snowmanxxxxxx);
         if (_snowmanxxxxxx < 0) {
            if (_snowmanxx == _snowmanxxxx) {
               _snowmanxx++;
            }

            _snowmanxxx++;
         } else {
            _snowmanxxx = 0;
         }
      }

      if (lines.length == _snowmanxxx) {
         return new String[0];
      } else {
         String[] _snowmanxxxxx = new String[lines.length - _snowmanxxx - _snowmanxx];

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.length; _snowmanxxxxxx++) {
            _snowmanxxxxx[_snowmanxxxxxx] = lines[_snowmanxxxxxx + _snowmanxx].substring(_snowman, _snowmanx + 1);
         }

         return _snowmanxxxxx;
      }
   }

   private static int findNextIngredient(String pattern) {
      int _snowman = 0;

      while (_snowman < pattern.length() && pattern.charAt(_snowman) == ' ') {
         _snowman++;
      }

      return _snowman;
   }

   private static int findNextIngredientReverse(String pattern) {
      int _snowman = pattern.length() - 1;

      while (_snowman >= 0 && pattern.charAt(_snowman) == ' ') {
         _snowman--;
      }

      return _snowman;
   }

   private static String[] getPattern(JsonArray json) {
      String[] _snowman = new String[json.size()];
      if (_snowman.length > 3) {
         throw new JsonSyntaxException("Invalid pattern: too many rows, 3 is maximum");
      } else if (_snowman.length == 0) {
         throw new JsonSyntaxException("Invalid pattern: empty pattern not allowed");
      } else {
         for (int _snowmanx = 0; _snowmanx < _snowman.length; _snowmanx++) {
            String _snowmanxx = JsonHelper.asString(json.get(_snowmanx), "pattern[" + _snowmanx + "]");
            if (_snowmanxx.length() > 3) {
               throw new JsonSyntaxException("Invalid pattern: too many columns, 3 is maximum");
            }

            if (_snowmanx > 0 && _snowman[0].length() != _snowmanxx.length()) {
               throw new JsonSyntaxException("Invalid pattern: each row must be the same width");
            }

            _snowman[_snowmanx] = _snowmanxx;
         }

         return _snowman;
      }
   }

   private static Map<String, Ingredient> getComponents(JsonObject json) {
      Map<String, Ingredient> _snowman = Maps.newHashMap();

      for (Entry<String, JsonElement> _snowmanx : json.entrySet()) {
         if (_snowmanx.getKey().length() != 1) {
            throw new JsonSyntaxException("Invalid key entry: '" + _snowmanx.getKey() + "' is an invalid symbol (must be 1 character only).");
         }

         if (" ".equals(_snowmanx.getKey())) {
            throw new JsonSyntaxException("Invalid key entry: ' ' is a reserved symbol.");
         }

         _snowman.put(_snowmanx.getKey(), Ingredient.fromJson(_snowmanx.getValue()));
      }

      _snowman.put(" ", Ingredient.EMPTY);
      return _snowman;
   }

   public static ItemStack getItemStack(JsonObject json) {
      String _snowman = JsonHelper.getString(json, "item");
      Item _snowmanx = Registry.ITEM.getOrEmpty(new Identifier(_snowman)).orElseThrow(() -> new JsonSyntaxException("Unknown item '" + _snowman + "'"));
      if (json.has("data")) {
         throw new JsonParseException("Disallowed data tag found");
      } else {
         int _snowmanxx = JsonHelper.getInt(json, "count", 1);
         return new ItemStack(_snowmanx, _snowmanxx);
      }
   }

   public static class Serializer implements RecipeSerializer<ShapedRecipe> {
      public Serializer() {
      }

      public ShapedRecipe read(Identifier _snowman, JsonObject _snowman) {
         String _snowmanxx = JsonHelper.getString(_snowman, "group", "");
         Map<String, Ingredient> _snowmanxxx = ShapedRecipe.getComponents(JsonHelper.getObject(_snowman, "key"));
         String[] _snowmanxxxx = ShapedRecipe.combinePattern(ShapedRecipe.getPattern(JsonHelper.getArray(_snowman, "pattern")));
         int _snowmanxxxxx = _snowmanxxxx[0].length();
         int _snowmanxxxxxx = _snowmanxxxx.length;
         DefaultedList<Ingredient> _snowmanxxxxxxx = ShapedRecipe.getIngredients(_snowmanxxxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
         ItemStack _snowmanxxxxxxxx = ShapedRecipe.getItemStack(JsonHelper.getObject(_snowman, "result"));
         return new ShapedRecipe(_snowman, _snowmanxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
      }

      public ShapedRecipe read(Identifier _snowman, PacketByteBuf _snowman) {
         int _snowmanxx = _snowman.readVarInt();
         int _snowmanxxx = _snowman.readVarInt();
         String _snowmanxxxx = _snowman.readString(32767);
         DefaultedList<Ingredient> _snowmanxxxxx = DefaultedList.ofSize(_snowmanxx * _snowmanxxx, Ingredient.EMPTY);

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
            _snowmanxxxxx.set(_snowmanxxxxxx, Ingredient.fromPacket(_snowman));
         }

         ItemStack _snowmanxxxxxx = _snowman.readItemStack();
         return new ShapedRecipe(_snowman, _snowmanxxxx, _snowmanxx, _snowmanxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      public void write(PacketByteBuf _snowman, ShapedRecipe _snowman) {
         _snowman.writeVarInt(_snowman.width);
         _snowman.writeVarInt(_snowman.height);
         _snowman.writeString(_snowman.group);

         for (Ingredient _snowmanxx : _snowman.inputs) {
            _snowmanxx.write(_snowman);
         }

         _snowman.writeItemStack(_snowman.output);
      }
   }
}
