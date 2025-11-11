import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Arrays;
import java.util.Collection;
import java.util.concurrent.CompletableFuture;

public class ed implements ArgumentType<Integer> {
   private static final Collection<String> a = Arrays.asList("0d", "0s", "0t", "0");
   private static final SimpleCommandExceptionType b = new SimpleCommandExceptionType(new of("argument.time.invalid_unit"));
   private static final DynamicCommandExceptionType c = new DynamicCommandExceptionType(var0 -> new of("argument.time.invalid_tick_count", var0));
   private static final Object2IntMap<String> d = new Object2IntOpenHashMap();

   public ed() {
   }

   public static ed a() {
      return new ed();
   }

   public Integer a(StringReader var1) throws CommandSyntaxException {
      float _snowman = _snowman.readFloat();
      String _snowmanx = _snowman.readUnquotedString();
      int _snowmanxx = d.getOrDefault(_snowmanx, 0);
      if (_snowmanxx == 0) {
         throw b.create();
      } else {
         int _snowmanxxx = Math.round(_snowman * (float)_snowmanxx);
         if (_snowmanxxx < 0) {
            throw c.create(_snowmanxxx);
         } else {
            return _snowmanxxx;
         }
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      StringReader _snowman = new StringReader(_snowman.getRemaining());

      try {
         _snowman.readFloat();
      } catch (CommandSyntaxException var5) {
         return _snowman.buildFuture();
      }

      return dd.b(d.keySet(), _snowman.createOffset(_snowman.getStart() + _snowman.getCursor()));
   }

   public Collection<String> getExamples() {
      return a;
   }

   static {
      d.put("d", 24000);
      d.put("s", 20);
      d.put("t", 1);
      d.put("", 1);
   }
}
