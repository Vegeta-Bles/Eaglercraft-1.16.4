import com.google.common.primitives.Doubles;
import com.mojang.brigadier.StringReader;
import com.mojang.brigadier.exceptions.CommandSyntaxException;
import com.mojang.brigadier.exceptions.DynamicCommandExceptionType;
import com.mojang.brigadier.exceptions.SimpleCommandExceptionType;
import com.mojang.brigadier.suggestion.Suggestions;
import com.mojang.brigadier.suggestion.SuggestionsBuilder;
import java.util.Collections;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;
import java.util.function.BiFunction;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.ToDoubleFunction;
import javax.annotation.Nullable;

public class fd {
   public static final SimpleCommandExceptionType a = new SimpleCommandExceptionType(new of("argument.entity.invalid"));
   public static final DynamicCommandExceptionType b = new DynamicCommandExceptionType(var0 -> new of("argument.entity.selector.unknown", var0));
   public static final SimpleCommandExceptionType c = new SimpleCommandExceptionType(new of("argument.entity.selector.not_allowed"));
   public static final SimpleCommandExceptionType d = new SimpleCommandExceptionType(new of("argument.entity.selector.missing"));
   public static final SimpleCommandExceptionType e = new SimpleCommandExceptionType(new of("argument.entity.options.unterminated"));
   public static final DynamicCommandExceptionType f = new DynamicCommandExceptionType(var0 -> new of("argument.entity.options.valueless", var0));
   public static final BiConsumer<dcn, List<? extends aqa>> g = (var0, var1) -> {
   };
   public static final BiConsumer<dcn, List<? extends aqa>> h = (var0, var1) -> var1.sort((var1x, var2) -> Doubles.compare(var1x.e(var0), var2.e(var0)));
   public static final BiConsumer<dcn, List<? extends aqa>> i = (var0, var1) -> var1.sort((var1x, var2) -> Doubles.compare(var2.e(var0), var1x.e(var0)));
   public static final BiConsumer<dcn, List<? extends aqa>> j = (var0, var1) -> Collections.shuffle(var1);
   public static final BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> k = (var0, var1) -> var0.buildFuture();
   private final StringReader l;
   private final boolean m;
   private int n;
   private boolean o;
   private boolean p;
   private bz.c q = bz.c.e;
   private bz.d r = bz.d.e;
   @Nullable
   private Double s;
   @Nullable
   private Double t;
   @Nullable
   private Double u;
   @Nullable
   private Double v;
   @Nullable
   private Double w;
   @Nullable
   private Double x;
   private cu y = cu.a;
   private cu z = cu.a;
   private Predicate<aqa> A = var0 -> true;
   private BiConsumer<dcn, List<? extends aqa>> B = g;
   private boolean C;
   @Nullable
   private String D;
   private int E;
   @Nullable
   private UUID F;
   private BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> G = k;
   private boolean H;
   private boolean I;
   private boolean J;
   private boolean K;
   private boolean L;
   private boolean M;
   private boolean N;
   private boolean O;
   @Nullable
   private aqe<?> P;
   private boolean Q;
   private boolean R;
   private boolean S;
   private boolean T;

   public fd(StringReader var1) {
      this(_snowman, true);
   }

   public fd(StringReader var1, boolean var2) {
      this.l = _snowman;
      this.m = _snowman;
   }

   public fc a() {
      dci _snowman;
      if (this.v == null && this.w == null && this.x == null) {
         if (this.q.b() != null) {
            float _snowmanx = (Float)this.q.b();
            _snowman = new dci((double)(-_snowmanx), (double)(-_snowmanx), (double)(-_snowmanx), (double)(_snowmanx + 1.0F), (double)(_snowmanx + 1.0F), (double)(_snowmanx + 1.0F));
         } else {
            _snowman = null;
         }
      } else {
         _snowman = this.a(this.v == null ? 0.0 : this.v, this.w == null ? 0.0 : this.w, this.x == null ? 0.0 : this.x);
      }

      Function<dcn, dcn> _snowmanx;
      if (this.s == null && this.t == null && this.u == null) {
         _snowmanx = var0 -> var0;
      } else {
         _snowmanx = var1x -> new dcn(this.s == null ? var1x.b : this.s, this.t == null ? var1x.c : this.t, this.u == null ? var1x.d : this.u);
      }

      return new fc(this.n, this.o, this.p, this.A, this.q, _snowmanx, _snowman, this.B, this.C, this.D, this.F, this.P, this.T);
   }

   private dci a(double var1, double var3, double var5) {
      boolean _snowman = _snowman < 0.0;
      boolean _snowmanx = _snowman < 0.0;
      boolean _snowmanxx = _snowman < 0.0;
      double _snowmanxxx = _snowman ? _snowman : 0.0;
      double _snowmanxxxx = _snowmanx ? _snowman : 0.0;
      double _snowmanxxxxx = _snowmanxx ? _snowman : 0.0;
      double _snowmanxxxxxx = (_snowman ? 0.0 : _snowman) + 1.0;
      double _snowmanxxxxxxx = (_snowmanx ? 0.0 : _snowman) + 1.0;
      double _snowmanxxxxxxxx = (_snowmanxx ? 0.0 : _snowman) + 1.0;
      return new dci(_snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxxxxxxxx);
   }

   private void I() {
      if (this.y != cu.a) {
         this.A = this.A.and(this.a(this.y, var0 -> (double)var0.q));
      }

      if (this.z != cu.a) {
         this.A = this.A.and(this.a(this.z, var0 -> (double)var0.p));
      }

      if (!this.r.c()) {
         this.A = this.A.and(var1 -> !(var1 instanceof aah) ? false : this.r.d(((aah)var1).bD));
      }
   }

   private Predicate<aqa> a(cu var1, ToDoubleFunction<aqa> var2) {
      double _snowman = (double)afm.g(_snowman.a() == null ? 0.0F : _snowman.a());
      double _snowmanx = (double)afm.g(_snowman.b() == null ? 359.0F : _snowman.b());
      return var5x -> {
         double _snowmanxx = afm.g(_snowman.applyAsDouble(var5x));
         return _snowman > _snowman ? _snowmanxx >= _snowman || _snowmanxx <= _snowman : _snowmanxx >= _snowman && _snowmanxx <= _snowman;
      };
   }

   protected void b() throws CommandSyntaxException {
      this.T = true;
      this.G = this::d;
      if (!this.l.canRead()) {
         throw d.createWithContext(this.l);
      } else {
         int _snowman = this.l.getCursor();
         char _snowmanx = this.l.read();
         if (_snowmanx == 'p') {
            this.n = 1;
            this.o = false;
            this.B = h;
            this.a(aqe.bc);
         } else if (_snowmanx == 'a') {
            this.n = Integer.MAX_VALUE;
            this.o = false;
            this.B = g;
            this.a(aqe.bc);
         } else if (_snowmanx == 'r') {
            this.n = 1;
            this.o = false;
            this.B = j;
            this.a(aqe.bc);
         } else if (_snowmanx == 's') {
            this.n = 1;
            this.o = true;
            this.C = true;
         } else {
            if (_snowmanx != 'e') {
               this.l.setCursor(_snowman);
               throw b.createWithContext(this.l, '@' + String.valueOf(_snowmanx));
            }

            this.n = Integer.MAX_VALUE;
            this.o = true;
            this.B = g;
            this.A = aqa::aX;
         }

         this.G = this::e;
         if (this.l.canRead() && this.l.peek() == '[') {
            this.l.skip();
            this.G = this::f;
            this.d();
         }
      }
   }

   protected void c() throws CommandSyntaxException {
      if (this.l.canRead()) {
         this.G = this::c;
      }

      int _snowman = this.l.getCursor();
      String _snowmanx = this.l.readString();

      try {
         this.F = UUID.fromString(_snowmanx);
         this.o = true;
      } catch (IllegalArgumentException var4) {
         if (_snowmanx.isEmpty() || _snowmanx.length() > 16) {
            this.l.setCursor(_snowman);
            throw a.createWithContext(this.l);
         }

         this.o = false;
         this.D = _snowmanx;
      }

      this.n = 1;
   }

   protected void d() throws CommandSyntaxException {
      this.G = this::g;
      this.l.skipWhitespace();

      while (this.l.canRead() && this.l.peek() != ']') {
         this.l.skipWhitespace();
         int _snowman = this.l.getCursor();
         String _snowmanx = this.l.readString();
         fe.a _snowmanxx = fe.a(this, _snowmanx, _snowman);
         this.l.skipWhitespace();
         if (!this.l.canRead() || this.l.peek() != '=') {
            this.l.setCursor(_snowman);
            throw f.createWithContext(this.l, _snowmanx);
         }

         this.l.skip();
         this.l.skipWhitespace();
         this.G = k;
         _snowmanxx.handle(this);
         this.l.skipWhitespace();
         this.G = this::h;
         if (this.l.canRead()) {
            if (this.l.peek() != ',') {
               if (this.l.peek() != ']') {
                  throw e.createWithContext(this.l);
               }
               break;
            }

            this.l.skip();
            this.G = this::g;
         }
      }

      if (this.l.canRead()) {
         this.l.skip();
         this.G = k;
      } else {
         throw e.createWithContext(this.l);
      }
   }

   public boolean e() {
      this.l.skipWhitespace();
      if (this.l.canRead() && this.l.peek() == '!') {
         this.l.skip();
         this.l.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public boolean f() {
      this.l.skipWhitespace();
      if (this.l.canRead() && this.l.peek() == '#') {
         this.l.skip();
         this.l.skipWhitespace();
         return true;
      } else {
         return false;
      }
   }

   public StringReader g() {
      return this.l;
   }

   public void a(Predicate<aqa> var1) {
      this.A = this.A.and(_snowman);
   }

   public void h() {
      this.p = true;
   }

   public bz.c i() {
      return this.q;
   }

   public void a(bz.c var1) {
      this.q = _snowman;
   }

   public bz.d j() {
      return this.r;
   }

   public void a(bz.d var1) {
      this.r = _snowman;
   }

   public cu k() {
      return this.y;
   }

   public void a(cu var1) {
      this.y = _snowman;
   }

   public cu l() {
      return this.z;
   }

   public void b(cu var1) {
      this.z = _snowman;
   }

   @Nullable
   public Double m() {
      return this.s;
   }

   @Nullable
   public Double n() {
      return this.t;
   }

   @Nullable
   public Double o() {
      return this.u;
   }

   public void a(double var1) {
      this.s = _snowman;
   }

   public void b(double var1) {
      this.t = _snowman;
   }

   public void c(double var1) {
      this.u = _snowman;
   }

   public void d(double var1) {
      this.v = _snowman;
   }

   public void e(double var1) {
      this.w = _snowman;
   }

   public void f(double var1) {
      this.x = _snowman;
   }

   @Nullable
   public Double p() {
      return this.v;
   }

   @Nullable
   public Double q() {
      return this.w;
   }

   @Nullable
   public Double r() {
      return this.x;
   }

   public void a(int var1) {
      this.n = _snowman;
   }

   public void a(boolean var1) {
      this.o = _snowman;
   }

   public void a(BiConsumer<dcn, List<? extends aqa>> var1) {
      this.B = _snowman;
   }

   public fc t() throws CommandSyntaxException {
      this.E = this.l.getCursor();
      this.G = this::b;
      if (this.l.canRead() && this.l.peek() == '@') {
         if (!this.m) {
            throw c.createWithContext(this.l);
         }

         this.l.skip();
         this.b();
      } else {
         this.c();
      }

      this.I();
      return this.a();
   }

   private static void a(SuggestionsBuilder var0) {
      _snowman.suggest("@p", new of("argument.entity.selector.nearestPlayer"));
      _snowman.suggest("@a", new of("argument.entity.selector.allPlayers"));
      _snowman.suggest("@r", new of("argument.entity.selector.randomPlayer"));
      _snowman.suggest("@s", new of("argument.entity.selector.self"));
      _snowman.suggest("@e", new of("argument.entity.selector.allEntities"));
   }

   private CompletableFuture<Suggestions> b(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      _snowman.accept(_snowman);
      if (this.m) {
         a(_snowman);
      }

      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> c(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      SuggestionsBuilder _snowman = _snowman.createOffset(this.E);
      _snowman.accept(_snowman);
      return _snowman.add(_snowman).buildFuture();
   }

   private CompletableFuture<Suggestions> d(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      SuggestionsBuilder _snowman = _snowman.createOffset(_snowman.getStart() - 1);
      a(_snowman);
      _snowman.add(_snowman);
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> e(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      _snowman.suggest(String.valueOf('['));
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> f(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      _snowman.suggest(String.valueOf(']'));
      fe.a(this, _snowman);
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> g(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      fe.a(this, _snowman);
      return _snowman.buildFuture();
   }

   private CompletableFuture<Suggestions> h(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      _snowman.suggest(String.valueOf(','));
      _snowman.suggest(String.valueOf(']'));
      return _snowman.buildFuture();
   }

   public boolean u() {
      return this.C;
   }

   public void a(BiFunction<SuggestionsBuilder, Consumer<SuggestionsBuilder>, CompletableFuture<Suggestions>> var1) {
      this.G = _snowman;
   }

   public CompletableFuture<Suggestions> a(SuggestionsBuilder var1, Consumer<SuggestionsBuilder> var2) {
      return this.G.apply(_snowman.createOffset(this.l.getCursor()), _snowman);
   }

   public boolean v() {
      return this.H;
   }

   public void b(boolean var1) {
      this.H = _snowman;
   }

   public boolean w() {
      return this.I;
   }

   public void c(boolean var1) {
      this.I = _snowman;
   }

   public boolean x() {
      return this.J;
   }

   public void d(boolean var1) {
      this.J = _snowman;
   }

   public boolean y() {
      return this.K;
   }

   public void e(boolean var1) {
      this.K = _snowman;
   }

   public boolean z() {
      return this.L;
   }

   public void f(boolean var1) {
      this.L = _snowman;
   }

   public boolean A() {
      return this.M;
   }

   public void g(boolean var1) {
      this.M = _snowman;
   }

   public boolean B() {
      return this.N;
   }

   public void h(boolean var1) {
      this.N = _snowman;
   }

   public void i(boolean var1) {
      this.O = _snowman;
   }

   public void a(aqe<?> var1) {
      this.P = _snowman;
   }

   public void D() {
      this.Q = true;
   }

   public boolean E() {
      return this.P != null;
   }

   public boolean F() {
      return this.Q;
   }

   public boolean G() {
      return this.R;
   }

   public void j(boolean var1) {
      this.R = _snowman;
   }

   public boolean H() {
      return this.S;
   }

   public void k(boolean var1) {
      this.S = _snowman;
   }
}
