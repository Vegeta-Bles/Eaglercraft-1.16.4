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
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
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

   protected void apply(Map<Identifier, JsonElement> map, ResourceManager arg, Profiler arg2) {
      this.errored = false;
      Map<RecipeType<?>, Builder<Identifier, Recipe<?>>> map2 = Maps.newHashMap();

      for (Entry<Identifier, JsonElement> entry : map.entrySet()) {
         Identifier lv = entry.getKey();

         try {
            Recipe<?> lv2 = deserialize(lv, JsonHelper.asObject(entry.getValue(), "top element"));
            map2.computeIfAbsent(lv2.getType(), argx -> ImmutableMap.builder()).put(lv, lv2);
         } catch (IllegalArgumentException | JsonParseException var9) {
            LOGGER.error("Parsing error loading recipe {}", lv, var9);
         }
      }

      this.recipes = map2.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, entryx -> ((Builder)entryx.getValue()).build()));
      LOGGER.info("Loaded {} recipes", map2.size());
   }

   public <C extends Inventory, T extends Recipe<C>> Optional<T> getFirstMatch(RecipeType<T> type, C inventory, World world) {
      return this.getAllOfType(type).values().stream().flatMap(arg4 -> Util.stream(type.get((Recipe<C>)arg4, world, inventory))).findFirst();
   }

   public <C extends Inventory, T extends Recipe<C>> List<T> listAllOfType(RecipeType<T> arg) {
      return this.getAllOfType(arg).values().stream().collect(Collectors.toList());
   }

   public <C extends Inventory, T extends Recipe<C>> List<T> getAllMatches(RecipeType<T> type, C inventory, World world) {
      return this.getAllOfType(type)
         .values()
         .stream()
         .flatMap(arg4 -> Util.stream(type.get((Recipe<C>)arg4, world, inventory)))
         .sorted(Comparator.comparing(arg -> arg.getOutput().getTranslationKey()))
         .collect(Collectors.toList());
   }

   @SuppressWarnings("unchecked")
   private <C extends Inventory, T extends Recipe<C>> Map<Identifier, T> getAllOfType(RecipeType<T> type) {
      return (Map<Identifier, T>)this.recipes.getOrDefault(type, Collections.emptyMap());
   }

   public <C extends Inventory, T extends Recipe<C>> DefaultedList<ItemStack> getRemainingStacks(RecipeType<T> arg, C arg2, World arg3) {
      Optional<T> optional = this.getFirstMatch(arg, arg2, arg3);
      if (optional.isPresent()) {
         return optional.get().getRemainingStacks(arg2);
      } else {
         DefaultedList<ItemStack> lv = DefaultedList.ofSize(arg2.size(), ItemStack.EMPTY);

         for (int i = 0; i < lv.size(); i++) {
            lv.set(i, arg2.getStack(i));
         }

         return lv;
      }
   }

   public Optional<? extends Recipe<?>> get(Identifier id) {
      return this.recipes.values().stream().map(map -> map.get(id)).filter(Objects::nonNull).findFirst();
   }

   public Collection<Recipe<?>> values() {
      return this.recipes.values().stream().flatMap(map -> map.values().stream()).collect(Collectors.toSet());
   }

   public Stream<Identifier> keys() {
      return this.recipes.values().stream().flatMap(map -> map.keySet().stream());
   }

   public static Recipe<?> deserialize(Identifier id, JsonObject json) {
      String string = JsonHelper.getString(json, "type");
      return Registry.RECIPE_SERIALIZER
         .getOrEmpty(new Identifier(string))
         .orElseThrow(() -> new JsonSyntaxException("Invalid or unsupported recipe type '" + string + "'"))
         .read(id, json);
   }

   @Environment(EnvType.CLIENT)
   public void setRecipes(Iterable<Recipe<?>> recipes) {
      this.errored = false;
      Map<RecipeType<?>, Map<Identifier, Recipe<?>>> map = Maps.newHashMap();
      recipes.forEach(arg -> {
         Map<Identifier, Recipe<?>> map2 = map.computeIfAbsent(arg.getType(), argx -> Maps.newHashMap());
         Recipe<?> lv = map2.put(arg.getId(), (Recipe<?>)arg);
         if (lv != null) {
            throw new IllegalStateException("Duplicate recipe ignored with ID " + arg.getId());
         }
      });
      this.recipes = ImmutableMap.copyOf(map);
   }
}
