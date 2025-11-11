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

public class dq implements ArgumentType<aps> {
   private static final Collection<String> b = Arrays.asList("spooky", "effect");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("effect.effectNotFound", var0));

   public dq() {
   }

   public static dq a() {
      return new dq();
   }

   public static aps a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return (aps)_snowman.getArgument(_snowman, aps.class);
   }

   public aps a(StringReader var1) throws CommandSyntaxException {
      vk _snowman = vk.a(_snowman);
      return gm.P.b(_snowman).orElseThrow(() -> a.create(_snowman));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.a(gm.P.c(), _snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
