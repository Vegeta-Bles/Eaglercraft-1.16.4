import com.google.common.base.Strings;
import com.google.common.collect.Lists;
import com.mojang.brigadier.Message;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Stream;

public interface dd {
   Collection<String> l();

   default Collection<String> r() {
      return Collections.emptyList();
   }

   Collection<String> m();

   Collection<vk> n();

   Stream<vk> o();

   CompletableFuture<Suggestions> a(CommandContext<dd> var1, SuggestionsBuilder var2);

   default Collection<dd.a> s() {
      return Collections.singleton(dd.a.b);
   }

   default Collection<dd.a> t() {
      return Collections.singleton(dd.a.b);
   }

   Set<vj<brx>> p();

   gn q();

   boolean c(int var1);

   static <T> void a(Iterable<T> var0, String var1, Function<T, vk> var2, Consumer<T> var3) {
      boolean _snowman = _snowman.indexOf(58) > -1;

      for (T _snowmanx : _snowman) {
         vk _snowmanxx = _snowman.apply(_snowmanx);
         if (_snowman) {
            String _snowmanxxx = _snowmanxx.toString();
            if (a(_snowman, _snowmanxxx)) {
               _snowman.accept(_snowmanx);
            }
         } else if (a(_snowman, _snowmanxx.b()) || _snowmanxx.b().equals("minecraft") && a(_snowman, _snowmanxx.a())) {
            _snowman.accept(_snowmanx);
         }
      }
   }

   static <T> void a(Iterable<T> var0, String var1, String var2, Function<T, vk> var3, Consumer<T> var4) {
      if (_snowman.isEmpty()) {
         _snowman.forEach(_snowman);
      } else {
         String _snowman = Strings.commonPrefix(_snowman, _snowman);
         if (!_snowman.isEmpty()) {
            String _snowmanx = _snowman.substring(_snowman.length());
            a(_snowman, _snowmanx, _snowman, _snowman);
         }
      }
   }

   static CompletableFuture<Suggestions> a(Iterable<vk> var0, SuggestionsBuilder var1, String var2) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      a(_snowman, _snowman, _snowman, var0x -> var0x, var2x -> _snowman.suggest(_snowman + var2x));
      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> a(Iterable<vk> var0, SuggestionsBuilder var1) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      a(_snowman, _snowman, var0x -> var0x, var1x -> _snowman.suggest(var1x.toString()));
      return _snowman.buildFuture();
   }

   static <T> CompletableFuture<Suggestions> a(Iterable<T> var0, SuggestionsBuilder var1, Function<T, vk> var2, Function<T, Message> var3) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      a(_snowman, _snowman, _snowman, var3x -> _snowman.suggest(_snowman.apply(var3x).toString(), _snowman.apply(var3x)));
      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> a(Stream<vk> var0, SuggestionsBuilder var1) {
      return a(_snowman::iterator, _snowman);
   }

   static <T> CompletableFuture<Suggestions> a(Stream<T> var0, SuggestionsBuilder var1, Function<T, vk> var2, Function<T, Message> var3) {
      return a(_snowman::iterator, _snowman, _snowman, _snowman);
   }

   static CompletableFuture<Suggestions> a(String var0, Collection<dd.a> var1, SuggestionsBuilder var2, Predicate<String> var3) {
      List<String> _snowman = Lists.newArrayList();
      if (Strings.isNullOrEmpty(_snowman)) {
         for (dd.a _snowmanx : _snowman) {
            String _snowmanxx = _snowmanx.c + " " + _snowmanx.d + " " + _snowmanx.e;
            if (_snowman.test(_snowmanxx)) {
               _snowman.add(_snowmanx.c);
               _snowman.add(_snowmanx.c + " " + _snowmanx.d);
               _snowman.add(_snowmanxx);
            }
         }
      } else {
         String[] _snowmanxx = _snowman.split(" ");
         if (_snowmanxx.length == 1) {
            for (dd.a _snowmanxxx : _snowman) {
               String _snowmanxxxx = _snowmanxx[0] + " " + _snowmanxxx.d + " " + _snowmanxxx.e;
               if (_snowman.test(_snowmanxxxx)) {
                  _snowman.add(_snowmanxx[0] + " " + _snowmanxxx.d);
                  _snowman.add(_snowmanxxxx);
               }
            }
         } else if (_snowmanxx.length == 2) {
            for (dd.a _snowmanxxxx : _snowman) {
               String _snowmanxxxxx = _snowmanxx[0] + " " + _snowmanxx[1] + " " + _snowmanxxxx.e;
               if (_snowman.test(_snowmanxxxxx)) {
                  _snowman.add(_snowmanxxxxx);
               }
            }
         }
      }

      return b(_snowman, _snowman);
   }

   static CompletableFuture<Suggestions> b(String var0, Collection<dd.a> var1, SuggestionsBuilder var2, Predicate<String> var3) {
      List<String> _snowman = Lists.newArrayList();
      if (Strings.isNullOrEmpty(_snowman)) {
         for (dd.a _snowmanx : _snowman) {
            String _snowmanxx = _snowmanx.c + " " + _snowmanx.e;
            if (_snowman.test(_snowmanxx)) {
               _snowman.add(_snowmanx.c);
               _snowman.add(_snowmanxx);
            }
         }
      } else {
         String[] _snowmanxx = _snowman.split(" ");
         if (_snowmanxx.length == 1) {
            for (dd.a _snowmanxxx : _snowman) {
               String _snowmanxxxx = _snowmanxx[0] + " " + _snowmanxxx.e;
               if (_snowman.test(_snowmanxxxx)) {
                  _snowman.add(_snowmanxxxx);
               }
            }
         }
      }

      return b(_snowman, _snowman);
   }

   static CompletableFuture<Suggestions> b(Iterable<String> var0, SuggestionsBuilder var1) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (String _snowmanx : _snowman) {
         if (a(_snowman, _snowmanx.toLowerCase(Locale.ROOT))) {
            _snowman.suggest(_snowmanx);
         }
      }

      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> b(Stream<String> var0, SuggestionsBuilder var1) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      _snowman.filter(var1x -> a(_snowman, var1x.toLowerCase(Locale.ROOT))).forEach(_snowman::suggest);
      return _snowman.buildFuture();
   }

   static CompletableFuture<Suggestions> a(String[] var0, SuggestionsBuilder var1) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (String _snowmanx : _snowman) {
         if (a(_snowman, _snowmanx.toLowerCase(Locale.ROOT))) {
            _snowman.suggest(_snowmanx);
         }
      }

      return _snowman.buildFuture();
   }

   static boolean a(String var0, String var1) {
      for (int _snowman = 0; !_snowman.startsWith(_snowman, _snowman); _snowman++) {
         _snowman = _snowman.indexOf(95, _snowman);
         if (_snowman < 0) {
            return false;
         }
      }

      return true;
   }

   public static class a {
      public static final dd.a a = new dd.a("^", "^", "^");
      public static final dd.a b = new dd.a("~", "~", "~");
      public final String c;
      public final String d;
      public final String e;

      public a(String var1, String var2, String var3) {
         this.c = _snowman;
         this.d = _snowman;
         this.e = _snowman;
      }
   }
}
