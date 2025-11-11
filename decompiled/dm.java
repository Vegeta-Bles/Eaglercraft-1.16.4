import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.arguments.ArgumentType;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.CompletableFuture;

public class dm implements ArgumentType<dm.a> {
   private static final Collection<String> b = Arrays.asList("Player", "0123", "dd12be42-52a9-4a91-a8a1-11c01849e498", "@e");
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.player.unknown"));

   public dm() {
   }

   public static Collection<GameProfile> a(CommandContext<db> var0, String var1) throws CommandSyntaxException {
      return ((dm.a)_snowman.getArgument(_snowman, dm.a.class)).getNames((db)_snowman.getSource());
   }

   public static dm a() {
      return new dm();
   }

   public dm.a a(StringReader var1) throws CommandSyntaxException {
      if (_snowman.canRead() && _snowman.peek() == '@') {
         fd _snowman = new fd(_snowman);
         fc _snowmanx = _snowman.t();
         if (_snowmanx.b()) {
            throw dk.c.create();
         } else {
            return new dm.b(_snowmanx);
         }
      } else {
         int _snowman = _snowman.getCursor();

         while (_snowman.canRead() && _snowman.peek() != ' ') {
            _snowman.skip();
         }

         String _snowmanx = _snowman.getString().substring(_snowman, _snowman.getCursor());
         return var1x -> {
            GameProfile _snowmanxx = var1x.j().ar().a(_snowman);
            if (_snowmanxx == null) {
               throw a.create();
            } else {
               return Collections.singleton(_snowmanxx);
            }
         };
      }
   }

   public <S> CompletableFuture<Suggestions> listSuggestions(CommandContext<S> var1, SuggestionsBuilder var2) {
      if (_snowman.getSource() instanceof dd) {
         StringReader _snowman = new StringReader(_snowman.getInput());
         _snowman.setCursor(_snowman.getStart());
         fd _snowmanx = new fd(_snowman);

         try {
            _snowmanx.t();
         } catch (CommandSyntaxException var6) {
         }

         return _snowmanx.a(_snowman, var1x -> dd.b(((dd)_snowman.getSource()).l(), var1x));
      } else {
         return Suggestions.empty();
      }
   }

   public Collection<String> getExamples() {
      return b;
   }

   @FunctionalInterface
   public interface a {
      Collection<GameProfile> getNames(db var1) throws CommandSyntaxException;
   }

   public static class b implements dm.a {
      private final fc a;

      public b(fc var1) {
         this.a = _snowman;
      }

      @Override
      public Collection<GameProfile> getNames(db var1) throws CommandSyntaxException {
         List<aah> _snowman = this.a.d(_snowman);
         if (_snowman.isEmpty()) {
            throw dk.e.create();
         } else {
            List<GameProfile> _snowmanx = Lists.newArrayList();

            for (aah _snowmanxx : _snowman) {
               _snowmanx.add(_snowmanxx.eA());
            }

            return _snowmanx;
         }
      }
   }
}
