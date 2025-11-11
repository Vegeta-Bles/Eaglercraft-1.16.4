import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Collectors;
import java.util.stream.Stream;

public class di implements ArgumentType<vk> {
   private static final Collection<String> a = Stream.of(brx.g, brx.h).map(var0 -> var0.a().toString()).collect(Collectors.toList());
   private static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.dimension.invalid", var0));

   public di() {
   }

   public vk a(StringReader var1) throws CommandSyntaxException {
      return vk.a(_snowman);
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return _snowman.getSource() instanceof dd ? dd.a(((dd)_snowman.getSource()).p().stream().map(vj::a), _snowman) : Suggestions.empty();
   }

   public Collection<String> getExamples() {
      return a;
   }

   public static di a() {
      return new di();
   }

   public static aag a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      vk _snowman = (vk)_snowman.getArgument(_snowman, vk.class);
      vj<brx> _snowmanx = vj.a(gm.L, _snowman);
      aag _snowmanxx = ((db)_snowman.getSource()).j().a(_snowmanx);
      if (_snowmanxx == null) {
         throw b.create(_snowman);
      } else {
         return _snowmanxx;
      }
   }
}
