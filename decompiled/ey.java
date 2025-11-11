import com.google.common.collect.Maps;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiFunction;
import javax.annotation.Nullable;

public class ey {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.item.tag.disallowed"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.item.id.invalid", var0));
   private static final BiFunction<SuggestionsBuilder, aem<blx>, CompletableFuture<Suggestions>> c = (var0, var1) -> var0.buildFuture();
   private final StringReader d;
   private final boolean e;
   private final Map<cfj<?>, Comparable<?>> f = Maps.newHashMap();
   private blx g;
   @Nullable
   private md h;
   private vk i = new vk("");
   private int j;
   private BiFunction<SuggestionsBuilder, aem<blx>, CompletableFuture<Suggestions>> k = c;

   public ey(StringReader var1, boolean var2) {
      this.d = _snowman;
      this.e = _snowman;
   }

   public blx b() {
      return this.g;
   }

   @Nullable
   public md c() {
      return this.h;
   }

   public vk d() {
      return this.i;
   }

   public void e() throws CommandSyntaxException {
      int _snowman = this.d.getCursor();
      vk _snowmanx = vk.a(this.d);
      this.g = gm.T.b(_snowmanx).orElseThrow(() -> {
         this.d.setCursor(_snowman);
         return b.createWithContext(this.d, _snowman.toString());
      });
   }

   public void f() throws CommandSyntaxException {
      if (!this.e) {
         throw a.create();
      } else {
         this.k = this::c;
         this.d.expect('#');
         this.j = this.d.getCursor();
         this.i = vk.a(this.d);
      }
   }

   public void g() throws CommandSyntaxException {
      this.h = new mu(this.d).f();
   }

   public ey h() throws CommandSyntaxException {
      this.k = this::d;
      if (this.d.canRead() && this.d.peek() == '#') {
         this.f();
      } else {
         this.e();
         this.k = this::b;
      }

      if (this.d.canRead() && this.d.peek() == '{') {
         this.k = c;
         this.g();
      }

      return this;
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder var1, aem<blx> var2) {
      if (_snowman.getRemaining().isEmpty()) {
         _snowman.suggest(String.valueOf('{'));
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder var1, aem<blx> var2) {
      return dd.a(_snowman.b(), _snowman.createOffset(this.j));
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder var1, aem<blx> var2) {
      if (this.e) {
         dd.a(_snowman.b(), _snowman, String.valueOf('#'));
      }

      return dd.a(gm.T.c(), _snowman);
   }

   public CompletableFuture<Suggestions> a(SuggestionsBuilder var1, aem<blx> var2) {
      return this.k.apply(_snowman.createOffset(this.d.getCursor()), _snowman);
   }
}
