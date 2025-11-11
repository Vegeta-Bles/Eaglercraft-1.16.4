import com.google.common.collect.Lists;
import com.mojang.brigadier.context.CommandContext;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Locale;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.stream.Stream;

public class dwv implements dd {
   private final dwu a;
   private final djz b;
   private int c = -1;
   private CompletableFuture<Suggestions> d;

   public dwv(dwu var1, djz var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public Collection<String> l() {
      List<String> _snowman = Lists.newArrayList();

      for (dwx _snowmanx : this.a.e()) {
         _snowman.add(_snowmanx.a().getName());
      }

      return _snowman;
   }

   @Override
   public Collection<String> r() {
      return (Collection<String>)(this.b.v != null && this.b.v.c() == dcl.a.c ? Collections.singleton(((dck)this.b.v).a().bT()) : Collections.emptyList());
   }

   @Override
   public Collection<String> m() {
      return this.a.j().G().f();
   }

   @Override
   public Collection<vk> n() {
      return this.b.W().a();
   }

   @Override
   public Stream<vk> o() {
      return this.a.d().d();
   }

   @Override
   public boolean c(int var1) {
      dzm _snowman = this.b.s;
      return _snowman != null ? _snowman.k(_snowman) : _snowman == 0;
   }

   @Override
   public CompletableFuture<Suggestions> a(CommandContext<dd> var1, SuggestionsBuilder var2) {
      if (this.d != null) {
         this.d.cancel(false);
      }

      this.d = new CompletableFuture<>();
      int _snowman = ++this.c;
      this.a.a(new sh(_snowman, _snowman.getInput()));
      return this.d;
   }

   private static String a(double var0) {
      return String.format(Locale.ROOT, "%.2f", _snowman);
   }

   private static String a(int var0) {
      return Integer.toString(_snowman);
   }

   @Override
   public Collection<dd.a> s() {
      dcl _snowman = this.b.v;
      if (_snowman != null && _snowman.c() == dcl.a.b) {
         fx _snowmanx = ((dcj)_snowman).a();
         return Collections.singleton(new dd.a(a(_snowmanx.u()), a(_snowmanx.v()), a(_snowmanx.w())));
      } else {
         return dd.super.s();
      }
   }

   @Override
   public Collection<dd.a> t() {
      dcl _snowman = this.b.v;
      if (_snowman != null && _snowman.c() == dcl.a.b) {
         dcn _snowmanx = _snowman.e();
         return Collections.singleton(new dd.a(a(_snowmanx.b), a(_snowmanx.c), a(_snowmanx.d)));
      } else {
         return dd.super.t();
      }
   }

   @Override
   public Set<vj<brx>> p() {
      return this.a.n();
   }

   @Override
   public gn q() {
      return this.a.o();
   }

   public void a(int var1, Suggestions var2) {
      if (_snowman == this.c) {
         this.d.complete(_snowman);
         this.d = null;
         this.c = -1;
      }
   }
}
