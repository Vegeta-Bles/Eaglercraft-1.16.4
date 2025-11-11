import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class eb implements ArgumentType<Integer> {
   private static final Collection<String> a = Arrays.asList("container.5", "12", "weapon");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("slot.unknown", var0));
   private static final Map<String, Integer> c = x.a(Maps.newHashMap(), var0 -> {
      for (int _snowman = 0; _snowman < 54; _snowman++) {
         var0.put("container." + _snowman, _snowman);
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         var0.put("hotbar." + _snowman, _snowman);
      }

      for (int _snowman = 0; _snowman < 27; _snowman++) {
         var0.put("inventory." + _snowman, 9 + _snowman);
      }

      for (int _snowman = 0; _snowman < 27; _snowman++) {
         var0.put("enderchest." + _snowman, 200 + _snowman);
      }

      for (int _snowman = 0; _snowman < 8; _snowman++) {
         var0.put("villager." + _snowman, 300 + _snowman);
      }

      for (int _snowman = 0; _snowman < 15; _snowman++) {
         var0.put("horse." + _snowman, 500 + _snowman);
      }

      var0.put("weapon", 98);
      var0.put("weapon.mainhand", 98);
      var0.put("weapon.offhand", 99);
      var0.put("armor.head", 100 + aqf.f.b());
      var0.put("armor.chest", 100 + aqf.e.b());
      var0.put("armor.legs", 100 + aqf.d.b());
      var0.put("armor.feet", 100 + aqf.c.b());
      var0.put("horse.saddle", 400);
      var0.put("horse.armor", 401);
      var0.put("horse.chest", 499);
   });

   public eb() {
   }

   public static eb a() {
      return new eb();
   }

   public static int a(CommandContext<db> var0, String var1) {
      return (Integer)_snowman.getArgument(_snowman, Integer.class);
   }

   public Integer a(StringReader var1) throws CommandSyntaxException {
      String _snowman = _snowman.readUnquotedString();
      if (!c.containsKey(_snowman)) {
         throw b.create(_snowman);
      } else {
         return c.get(_snowman);
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.b(c.keySet(), _snowman);
   }

   public Collection<String> getExamples() {
      return a;
   }
}
