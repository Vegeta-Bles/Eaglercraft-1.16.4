package net.minecraft.recipe;

import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableMap.Builder;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.google.gson.JsonSyntaxException;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.resource.JsonDataLoader;
import net.minecraft.resource.ResourceManager;
import net.minecraft.util.Identifier;
import net.minecraft.util.JsonHelper;
import net.minecraft.util.Util;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.profiler.Profiler;
import net.minecraft.util.registry.Registry;
import net.minecraft.world.World;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class RecipeManager extends JsonDataLoader {
   private static final Gson GSON = new GsonBuilder().setPrettyPrinting().disableHtmlEscaping().create();
   private static final Logger LOGGER = LogManager.getLogger();
   private Map<RecipeType<?>, Map<Identifier, Recipe<?>>> recipes = ImmutableMap.of();
   private boolean errored;

   public RecipeManager() {
      super(GSON, "recipes");
   }

   protected void apply(Map<Identifier, JsonElement> _snowman, ResourceManager _snowman, Profiler _snowman) {
      this.errored = false;
      Map<RecipeType<?>, Builder<Identifier, Recipe<?>>> _snowmanxxx = Maps.newHashMap();

      for (Entry<Identifier, JsonElement> _snowmanxxxx : _snowman.entrySet()) {
         Identifier _snowmanxxxxx = _snowmanxxxx.getKey();

         try {
            Recipe<?> _snowmanxxxxxx = deserialize(_snowmanxxxxx, JsonHelper.asObject(_snowmanxxxx.getValue(), "top element"));
            _snowmanxxx.computeIfAbsent(_snowmanxxxxxx.getType(), _snowmanxxxxxxx -> ImmutableMap.builder()).put(_snowmanxxxxx, _snowmanxxxxxx);
         } catch (IllegalArgumentException | JsonParseException var9) {
            LOGGER.error("Parsing error loading recipe {}", _snowmanxxxxx, var9);
         }
      }

      this.recipes = _snowmanxxx.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, _snowmanxxxx -> ((Builder)_snowmanxxxx.getValue()).build()));
      LOGGER.info("Loaded {} recipes", _snowmanxxx.size());
   }

   public <C extends Inventory, T extends Recipe<C>> Optional<T> getFirstMatch(RecipeType<T> type, C inventory, World world) {
      return this.getAllOfType(type).values().stream().flatMap(_snowmanxxx -> Util.stream(type.get((Recipe<C>)_snowmanxxx, world, inventory))).findFirst();
   }

   public <C extends Inventory, T extends Recipe<C>> List<T> listAllOfType(RecipeType<T> _snowman) {
      return this.getAllOfType(_snowman).values().stream().map(_snowmanx -> _snowmanx).collect(Collectors.toList());
   }

   public <C extends Inventory, T extends Recipe<C>> List<T> getAllMatches(RecipeType<T> type, C inventory, World world) {
      return this.getAllOfType(type)
         .values()
         .stream()
         .flatMap(_snowmanxxx -> Util.stream(type.get((Recipe<C>)_snowmanxxx, world, inventory)))
         .sorted(Comparator.comparing(_snowman -> _snowman.getOutput().getTranslationKey()))
         .collect(Collectors.toList());
   }

   private <C extends Inventory, T extends Recipe<C>> Map<Identifier, Recipe<C>> getAllOfType(RecipeType<T> type) {
      return (Map<Identifier, Recipe<C>>)this.recipes.getOrDefault(type, Collections.emptyMap());
   }

   public <C extends Inventory, T extends Recipe<C>> DefaultedList<ItemStack> getRemainingStacks(RecipeType<T> _snowman, C _snowman, World _snowman) {
      Optional<T> _snowmanxxx = this.getFirstMatch(_snowman, _snowman, _snowman);
      if (_snowmanxxx.isPresent()) {
         return _snowmanxxx.get().getRemainingStacks(_snowman);
      } else {
         DefaultedList<ItemStack> _snowmanxxxx = DefaultedList.ofSize(_snowman.size(), ItemStack.EMPTY);

         for (int _snowmanxxxxx = 0; _snowmanxxxxx < _snowmanxxxx.size(); _snowmanxxxxx++) {
            _snowmanxxxx.set(_snowmanxxxxx, _snowman.getStack(_snowmanxxxxx));
         }

         return _snowmanxxxx;
      }
   }

   public Optional<? extends Recipe<?>> get(Identifier id) {
      return this.recipes.values().stream().map(_snowmanx -> _snowmanx.get(id)).filter(Objects::nonNull).findFirst();
   }

   public Collection<Recipe<?>> values() {
      return this.recipes.values().stream().flatMap(_snowman -> _snowman.values().stream()).collect(Collectors.toSet());
   }

   public Stream<Identifier> keys() {
      return this.recipes.values().stream().flatMap(_snowman -> _snowman.keySet().stream());
   }

   public static Recipe<?> deserialize(Identifier id, JsonObject json) {
      String _snowman = JsonHelper.getString(json, "type");
      return Registry.RECIPE_SERIALIZER
         .getOrEmpty(new Identifier(_snowman))
         .orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + _snowman + "'"))
         .read(id, json);
   }

   public void setRecipes(Iterable<Recipe<?>> recipes) {
      this.errored = false;
      Map<RecipeType<?>, Map<Identifier, Recipe<?>>> _snowman = Maps.newHashMap();
      recipes.forEach(_snowmanx -> {
         Map<Identifier, Recipe<?>> _snowmanx = _snowman.computeIfAbsent(_snowmanx.getType(), _snowmanxx -> Maps.newHashMap());
         Recipe<?> _snowmanxx = _snowmanx.put(_snowmanx.getId(), (Recipe<?>)_snowmanx);
         if (_snowmanxx != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + _snowmanx.getId());
         }
      });
      this.recipes = ImmutableMap.copyOf(_snowman);
   }
}
