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
import java.util.Set;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class eg implements ArgumentType<eg.b> {
   private static final Collection<String> a = Arrays.asList("stone", "minecraft:stone", "stone[foo=bar]", "#stone", "#stone[foo=bar]{baz=nbt}");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("arguments.block.tag.unknown", var0));

   public eg() {
   }

   public static eg a() {
      return new eg();
   }

   public eg.b a(StringReader var1) throws CommandSyntaxException {
      ei _snowman = new ei(_snowman, true).a(true);
      if (_snowman.b() != null) {
         eg.a _snowmanx = new eg.a(_snowman.b(), _snowman.a().keySet(), _snowman.c());
         return var1x -> _snowman;
      } else {
         vk _snowmanx = _snowman.d();
         return var2x -> {
            ael<buo> _snowmanxx = var2x.a().a(_snowman);
            if (_snowmanxx == null) {
               throw b.create(_snowman.toString());
            } else {
               return new eg.c(_snowmanxx, _snowman.j(), _snowman.c());
            }
         };
      }
   }

   public static Predicate<cel> a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((eg.b)_snowman.getArgument(_snowman, eg.b.class)).create(((db)_snowman.getSource()).j().aG());
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader _snowman = new StringReader(_snowman.getInput());
      _snowman.setCursor(_snowman.getStart());
      ei _snowmanx = new ei(_snowman, true);

      try {
         _snowmanx.a(true);
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.a(_snowman, aed.a());
   }

   public Collection<String> getExamples() {
      return a;
   }

   static class a implements Predicate<cel> {
      private final ceh a;
      private final Set<cfj<?>> b;
      @Nullable
      private final md c;

      public a(ceh var1, Set<cfj<?>> var2, @Nullable md var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }

      public boolean a(cel var1) {
         ceh _snowman = _snowman.a();
         if (!_snowman.a(this.a.b())) {
            return false;
         } else {
            for (cfj<?> _snowmanx : this.b) {
               if (_snowman.c(_snowmanx) != this.a.c(_snowmanx)) {
                  return false;
               }
            }

            if (this.c == null) {
               return true;
            } else {
               ccj _snowmanxx = _snowman.b();
               return _snowmanxx != null && mp.a(this.c, _snowmanxx.a(new md()), true);
            }
         }
      }
   }

   public interface b {
      Predicate<cel> create(aen var1) throws CommandSyntaxException;
   }

   static class c implements Predicate<cel> {
      private final ael<buo> a;
      @Nullable
      private final md b;
      private final Map<String, String> c;

      private c(ael<buo> var1, Map<String, String> var2, @Nullable md var3) {
         this.a = _snowman;
         this.c = _snowman;
         this.b = _snowman;
      }

      public boolean a(cel var1) {
         ceh _snowman = _snowman.a();
         if (!_snowman.a(this.a)) {
            return false;
         } else {
            for (Entry<String, String> _snowmanx : this.c.entrySet()) {
               cfj<?> _snowmanxx = _snowman.b().m().a(_snowmanx.getKey());
               if (_snowmanxx == null) {
                  return false;
               }

               Comparable<?> _snowmanxxx = (Comparable<?>)_snowmanxx.b(_snowmanx.getValue()).orElse(null);
               if (_snowmanxxx == null) {
                  return false;
               }

               if (_snowman.c(_snowmanxx) != _snowmanxxx) {
                  return false;
               }
            }

            if (this.b == null) {
               return true;
            } else {
               ccj _snowmanx = _snowman.b();
               return _snowmanx != null && mp.a(this.b, _snowmanx.a(new md()), true);
            }
         }
      }
   }
}
