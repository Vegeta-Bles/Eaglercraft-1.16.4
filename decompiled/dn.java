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

public class dn implements ArgumentType<bps> {
   private static final Collection<String> b = Arrays.asList("unbreaking", "silk_touch");
   public static final DynamicCommandExceptionType a = new DynamicCommandExceptionType(var0 -> new of("enchantment.unknown", var0));

   public dn() {
   }

   public static dn a() {
      return new dn();
   }

   public static bps a(CommandContext<db> var0, String var1) {
      return (bps)_snowman.getArgument(_snowman, bps.class);
   }

   public bps a(StringReader var1) throws CommandSyntaxException {
      vk _snowman = vk.a(_snowman);
      return gm.R.b(_snowman).orElseThrow(() -> a.create(_snowman));
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      return dd.a(gm.R.c(), _snowman);
   }

   public Collection<String> getExamples() {
      return b;
   }
}
