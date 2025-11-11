import com.google.common.collect.Maps;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.Dynamic2CommandExceptionType;
import com.mojang.brigadier.exceptions.Dynamic3CommandExceptionType;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class ei {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.block.tag.disallowed"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.block.id.invalid", var0));
   public static final Dynamic2CommandExceptionType c = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.block.property.unknown", var0, var1));
   public static final Dynamic2CommandExceptionType d = new Dynamic2CommandExceptionType(
      (var0, var1) -> new of("argument.block.property.duplicate", var1, var0)
   );
   public static final Dynamic3CommandExceptionType e = new Dynamic3CommandExceptionType(
      (var0, var1, var2) -> new of("argument.block.property.invalid", var0, var2, var1)
   );
   public static final Dynamic2CommandExceptionType f = new Dynamic2CommandExceptionType((var0, var1) -> new of("argument.block.property.novalue", var0, var1));
   public static final SimpleCommandExceptionType g = new SimpleCommandExceptionType(new of("argument.block.property.unclosed"));
   private static final BiFunction<SuggestionsBuilder, aem<buo>, CompletableFuture<Suggestions>> h = (var0, var1) -> var0.buildFuture();
   private final StringReader i;
   private final boolean j;
   private final Map<cfj<?>, Comparable<?>> k = Maps.newHashMap();
   private final Map<String, String> l = Maps.newHashMap();
   private vk m = new vk("");
   private cei<buo, ceh> n;
   private ceh o;
   @Nullable
   private md p;
   private vk q = new vk("");
   private int r;
   private BiFunction<SuggestionsBuilder, aem<buo>, CompletableFuture<Suggestions>> s = h;

   public ei(StringReader var1, boolean var2) {
      this.i = _snowman;
      this.j = _snowman;
   }

   public Map<cfj<?>, Comparable<?>> a() {
      return this.k;
   }

   @Nullable
   public ceh b() {
      return this.o;
   }

   @Nullable
   public md c() {
      return this.p;
   }

   @Nullable
   public vk d() {
      return this.q;
   }

   public ei a(boolean var1) throws CommandSyntaxException {
      this.s = this::l;
      if (this.i.canRead() && this.i.peek() == '#') {
         this.f();
         this.s = this::i;
         if (this.i.canRead() && this.i.peek() == '[') {
            this.h();
            this.s = this::f;
         }
      } else {
         this.e();
         this.s = this::j;
         if (this.i.canRead() && this.i.peek() == '[') {
            this.g();
            this.s = this::f;
         }
      }

      if (_snowman && this.i.canRead() && this.i.peek() == '{') {
         this.s = h;
         this.i();
      }

      return this;
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      return this.d(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      return this.e(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder var1, aem<buo> var2) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);

      for (cfj<?> _snowmanx : this.o.r()) {
         if (!this.k.containsKey(_snowmanx) && _snowmanx.f().startsWith(_snowman)) {
            _snowman.suggest(_snowmanx.f() + '=');
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> e(SuggestionsBuilder var1, aem<buo> var2) {
      String _snowman = _snowman.getRemaining().toLowerCase(Locale.ROOT);
      if (this.q != null && !this.q.a().isEmpty()) {
         ael<buo> _snowmanx = _snowman.a(this.q);
         if (_snowmanx != null) {
            for (buo _snowmanxx : _snowmanx.b()) {
               for (cfj<?> _snowmanxxx : _snowmanxx.m().d()) {
                  if (!this.l.containsKey(_snowmanxxx.f()) && _snowmanxxx.f().startsWith(_snowman)) {
                     _snowman.suggest(_snowmanxxx.f() + '=');
                  }
               }
            }
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> f(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty() && this.a(_snowman)) {
         _snowman.suggest(String.valueOf('{'));
      }

      return _snowman.buildFuture();
   }

   private boolean a(aem<buo> var1) {
      if (this.o != null) {
         return this.o.b().q();
      } else {
         if (this.q != null) {
            ael<buo> _snowman = _snowman.a(this.q);
            if (_snowman != null) {
               for (buo _snowmanx : _snowman.b()) {
                  if (_snowmanx.q()) {
                     return true;
                  }
               }
            }
         }

         return false;
      }
   }

   private CompletableFuture<Suggestions> g(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf('='));
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> h(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf(']'));
      }

      if (_snowman.getRemaining().isEmpty() && this.k.size() < this.o.r().size()) {
         _snowman.suggest(String.valueOf(','));
      }

      return _snowman.buildFuture();
   }

   private static <T extends Comparable<T>> SuggestionsBuilder a(SuggestionsBuilder var0, cfj<T> var1) {
      for (T _snowman : _snowman.a()) {
         if (_snowman instanceof Integer) {
            _snowman.suggest((Integer)_snowman);
         } else {
            _snowman.suggest(_snowman.a(_snowman));
         }
      }

      return _snowman;
   }

   private CompletableFuture<Suggestions> a(SuggestionsBuilder var1, aem<buo> var2, String var3) {
      boolean _snowman = false;
      if (this.q != null && !this.q.a().isEmpty()) {
         ael<buo> _snowmanx = _snowman.a(this.q);
         if (_snowmanx != null) {
            for (buo _snowmanxx : _snowmanx.b()) {
               cfj<?> _snowmanxxx = _snowmanxx.m().a(_snowman);
               if (_snowmanxxx != null) {
                  a(_snowman, _snowmanxxx);
               }

               if (!_snowman) {
                  for (cfj<?> _snowmanxxxx : _snowmanxx.m().d()) {
                     if (!this.l.containsKey(_snowmanxxxx.f())) {
                        _snowman = true;
                        break;
                     }
                  }
               }
            }
         }
      }

      if (_snowman) {
         _snowman.suggest(String.valueOf(','));
      }

      _snowman.suggest(String.valueOf(']'));
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> i(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         ael<buo> _snowman = _snowman.a(this.q);
         if (_snowman != null) {
            boolean _snowmanx = false;
            boolean _snowmanxx = false;

            for (buo _snowmanxxx : _snowman.b()) {
               _snowmanx |= !_snowmanxxx.m().d().isEmpty();
               _snowmanxx |= _snowmanxxx.q();
               if (_snowmanx && _snowmanxx) {
                  break;
               }
            }

            if (_snowmanx) {
               _snowman.suggest(String.valueOf('['));
            }

            if (_snowmanxx) {
               _snowman.suggest(String.valueOf('{'));
            }
         }
      }

      return this.k(_snowman, _snowman);
   }

   private CompletableFuture<Suggestions> j(SuggestionsBuilder var1, aem<buo> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         if (!this.o.b().m().d().isEmpty()) {
            _snowman.suggest(String.valueOf('['));
         }

         if (this.o.b().q()) {
            _snowman.suggest(String.valueOf('{'));
         }
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> k(SuggestionsBuilder var1, aem<buo> var2) {
      return dd.a(_snowman.b(), _snowman.createOffset(this.r).add(_snowman));
   }

   private CompletableFuture<Suggestions> l(SuggestionsBuilder var1, aem<buo> var2) {
      if (this.j) {
         dd.a(_snowman.b(), _snowman, String.valueOf('#'));
      }

      dd.a(gm.Q.c(), _snowman);
      return _snowman.buildFuture();
   }

   public void e() throws CommandSyntaxException {
      int _snowman = this.i.getCursor();
      this.m = vk.a(this.i);
      buo _snowmanx = gm.Q.b(this.m).orElseThrow(() -> {
         this.i.setCursor(_snowman);
         return b.createWithContext(this.i, this.m.toString());
      });
      this.n = _snowmanx.m();
      this.o = _snowmanx.n();
   }

   public void f() throws CommandSyntaxException {
      if (!this.j) {
         throw a.create();
      } else {
         this.s = this::k;
         this.i.expect('#');
         this.r = this.i.getCursor();
         this.q = vk.a(this.i);
      }
   }

   public void g() throws CommandSyntaxException {
      this.i.skip();
      this.s = this::b;
      this.i.skipWhitespace();

      while (this.i.canRead() && this.i.peek() != ']') {
         this.i.skipWhitespace();
         int _snowman = this.i.getCursor();
         String _snowmanx = this.i.readString();
         cfj<?> _snowmanxx = this.n.a(_snowmanx);
         if (_snowmanxx == null) {
            this.i.setCursor(_snowman);
            throw c.createWithContext(this.i, this.m.toString(), _snowmanx);
         }

         if (this.k.containsKey(_snowmanxx)) {
            this.i.setCursor(_snowman);
            throw d.createWithContext(this.i, this.m.toString(), _snowmanx);
         }

         this.i.skipWhitespace();
         this.s = this::g;
         if (!this.i.canRead() || this.i.peek() != '=') {
            throw f.createWithContext(this.i, this.m.toString(), _snowmanx);
         }

         this.i.skip();
         this.i.skipWhitespace();
         this.s = (var1x, var2x) -> a(var1x, _snowman).buildFuture();
         int _snowmanxxx = this.i.getCursor();
         this.a(_snowmanxx, this.i.readString(), _snowmanxxx);
         this.s = this::h;
         this.i.skipWhitespace();
         if (this.i.canRead()) {
            if (this.i.peek() != ',') {
               if (this.i.peek() != ']') {
                  throw g.createWithContext(this.i);
               }
               break;
            }

            this.i.skip();
            this.s = this::d;
         }
      }

      if (this.i.canRead()) {
         this.i.skip();
      } else {
         throw g.createWithContext(this.i);
      }
   }

   public void h() throws CommandSyntaxException {
      this.i.skip();
      this.s = this::c;
      int _snowman = -1;
      this.i.skipWhitespace();

      while (this.i.canRead() && this.i.peek() != ']') {
         this.i.skipWhitespace();
         int _snowmanx = this.i.getCursor();
         String _snowmanxx = this.i.readString();
         if (this.l.containsKey(_snowmanxx)) {
            this.i.setCursor(_snowmanx);
            throw d.createWithContext(this.i, this.m.toString(), _snowmanxx);
         }

         this.i.skipWhitespace();
         if (!this.i.canRead() || this.i.peek() != '=') {
            this.i.setCursor(_snowmanx);
            throw f.createWithContext(this.i, this.m.toString(), _snowmanxx);
         }

         this.i.skip();
         this.i.skipWhitespace();
         this.s = (var2x, var3x) -> this.a(var2x, var3x, _snowman);
         _snowman = this.i.getCursor();
         String _snowmanxxx = this.i.readString();
         this.l.put(_snowmanxx, _snowmanxxx);
         this.i.skipWhitespace();
         if (this.i.canRead()) {
            _snowman = -1;
            if (this.i.peek() != ',') {
               if (this.i.peek() != ']') {
                  throw g.createWithContext(this.i);
               }
               break;
            }

            this.i.skip();
            this.s = this::e;
         }
      }

      if (this.i.canRead()) {
         this.i.skip();
      } else {
         if (_snowman >= 0) {
            this.i.setCursor(_snowman);
         }

         throw g.createWithContext(this.i);
      }
   }

   public void i() throws CommandSyntaxException {
      this.p = new mu(this.i).f();
   }

   private <T extends Comparable<T>> void a(cfj<T> var1, String var2, int var3) throws CommandSyntaxException {
      Optional<T> _snowman = _snowman.b(_snowman);
      if (_snowman.isPresent()) {
         this.o = this.o.a(_snowman, _snowman.get());
         this.k.put(_snowman, _snowman.get());
      } else {
         this.i.setCursor(_snowman);
         throw e.createWithContext(this.i, this.m.toString(), _snowman.f(), _snowman);
      }
   }

   public static String a(ceh var0) {
      StringBuilder _snowman = new StringBuilder(gm.Q.b(_snowman.b()).toString());
      if (!_snowman.r().isEmpty()) {
         _snowman.append('[');
         boolean _snowmanx = false;

         for (UnmodifiableIterator var3 = _snowman.s().entrySet().iterator(); var3.hasNext(); _snowmanx = true) {
            Entry<cfj<?>, Comparable<?>> _snowmanxx = (Entry<cfj<?>, Comparable<?>>)var3.next();
            if (_snowmanx) {
               _snowman.append(',');
            }

            a(_snowman, _snowmanxx.getKey(), _snowmanxx.getValue());
         }

         _snowman.append(']');
      }

      return _snowman.toString();
   }

   private static <T extends Comparable<T>> void a(StringBuilder var0, cfj<T> var1, Comparable<?> var2) {
      _snowman.append(_snowman.f());
      _snowman.append('=');
      _snowman.append(_snowman.a((T)_snowman));
   }

   public CompletableFuture<Suggestions> a(SuggestionsBuilder var1, aem<buo> var2) {
      return this.s.apply(_snowman.createOffset(this.i.getCursor()), _snowman);
   }

   public Map<String, String> j() {
      return this.l;
   }
}
