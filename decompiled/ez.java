import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.function.Predicate;
import javax.annotation.Nullable;

public class ez implements ArgumentType<ez.b> {
   private static final Collection<String> a = Arrays.asList("stick", "minecraft:stick", "#stick", "#stick{foo=bar}");
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("arguments.item.tag.unknown", var0));

   public ez() {
   }

   public static ez a() {
      return new ez();
   }

   public ez.b a(StringReader var1) throws CommandSyntaxException {
      ey _snowman = new ey(_snowman, true).h();
      if (_snowman.b() != null) {
         ez.a _snowmanx = new ez.a(_snowman.b(), _snowman.c());
         return var1x -> _snowman;
      } else {
         vk _snowmanx = _snowman.d();
         return var2x -> {
            ael<blx> _snowmanxx = ((db)var2x.getSource()).j().aG().b().a(_snowman);
            if (_snowmanxx == null) {
               throw b.create(_snowman.toString());
            } else {
               return new ez.c(_snowmanxx, _snowman.c());
            }
         };
      }
   }

   public static Predicate<bmb> a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((ez.b)_snowman.getArgument(_snowman, ez.b.class)).create(_snowman);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader _snowman = new StringReader(_snowman.getInput());
      _snowman.setCursor(_snowman.getStart());
      ey _snowmanx = new ey(_snowman, true);

      try {
         _snowmanx.h();
      } catch (CommandSyntaxException var6) {
      }

      return _snowmanx.a(_snowman, aeg.a());
   }

   public Collection<String> getExamples() {
      return a;
   }

   static class a implements Predicate<bmb> {
      private final blx a;
      @Nullable
      private final md b;

      public a(blx var1, @Nullable md var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public boolean a(bmb var1) {
         return _snowman.b() == this.a && mp.a(this.b, _snowman.o(), true);
      }
   }

   public interface b {
      Predicate<bmb> create(CommandContext<db> var1) throws CommandSyntaxException;
   }

   static class c implements Predicate<bmb> {
      private final ael<blx> a;
      @Nullable
      private final md b;

      public c(ael<blx> var1, @Nullable md var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      public boolean a(bmb var1) {
         return this.a.a(_snowman.b()) && mp.a(this.b, _snowman.o(), true);
      }
   }
}
