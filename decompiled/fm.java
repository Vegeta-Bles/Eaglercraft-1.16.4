import com.google.common.collect.Maps;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.suggestion.SuggestionProvider;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;

public class fm {
   private static final Map<vk, SuggestionProvider<dd>> f = Maps.newHashMap();
   private static final vk g = new vk("ask_server");
   public static final SuggestionProvider<dd> a = a(g, (var0, var1) -> ((dd)var0.getSource()).a(var0, var1));
   public static final SuggestionProvider<db> b = a(new vk("all_recipes"), (var0, var1) -> dd.a(((dd)var0.getSource()).o(), var1));
   public static final SuggestionProvider<db> c = a(new vk("available_sounds"), (var0, var1) -> dd.a(((dd)var0.getSource()).n(), var1));
   public static final SuggestionProvider<db> d = a(new vk("available_biomes"), (var0, var1) -> dd.a(((dd)var0.getSource()).q().b(gm.ay).c(), var1));
   public static final SuggestionProvider<db> e = a(
      new vk("summonable_entities"), (var0, var1) -> dd.a(gm.S.g().filter(aqe::b), var1, aqe::a, var0x -> new of(x.a("entity", aqe.a(var0x))))
   );

   public static <S extends dd> SuggestionProvider<S> a(vk var0, SuggestionProvider<dd> var1) {
      if (f.containsKey(_snowman)) {
         throw new IllegalArgumentException("A command suggestion provider is already registered with the name " + _snowman);
      } else {
         f.put(_snowman, _snowman);
         return new fm.a(_snowman, _snowman);
      }
   }

   public static SuggestionProvider<dd> a(vk var0) {
      return f.getOrDefault(_snowman, a);
   }

   public static vk a(SuggestionProvider<dd> var0) {
      return _snowman instanceof fm.a ? ((fm.a)_snowman).b : g;
   }

   public static SuggestionProvider<dd> b(SuggestionProvider<dd> var0) {
      return _snowman instanceof fm.a ? _snowman : a;
   }

   public static class a implements SuggestionProvider<dd> {
      private final SuggestionProvider<dd> a;
      private final vk b;

      public a(vk var1, SuggestionProvider<dd> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public CompletableFuture<Suggestions> getSuggestions(CommandContext<dd> var1, SuggestionsBuilder var2) throws CommandSyntaxException {
         return this.a.getSuggestions(_snowman, _snowman);
      }
   }
}
