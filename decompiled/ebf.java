import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Joiner;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializationContext;
import com.google.gson.JsonDeserializer;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParseException;
import com.mojang.datafixers.util.Either;
import com.mojang.datafixers.util.Pair;
import java.io.Reader;
import java.io.StringReader;
import java.lang.reflect.Type;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.stream.Collectors;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ebf implements ely {
   private static final Logger f = LogManager.getLogger();
   private static final ebh g = new ebh();
   @VisibleForTesting
   static final Gson a = new GsonBuilder()
      .registerTypeAdapter(ebf.class, new ebf.a())
      .registerTypeAdapter(ebb.class, new ebb.a())
      .registerTypeAdapter(ebc.class, new ebc.a())
      .registerTypeAdapter(ebe.class, new ebe.a())
      .registerTypeAdapter(ebl.class, new ebl.a())
      .registerTypeAdapter(ebm.class, new ebm.a())
      .registerTypeAdapter(ebj.class, new ebj.a())
      .create();
   private final List<ebb> h;
   @Nullable
   private final ebf.b i;
   private final boolean j;
   private final ebm k;
   private final List<ebj> l;
   public String b = "";
   @VisibleForTesting
   protected final Map<String, Either<elr, String>> c;
   @Nullable
   protected ebf d;
   @Nullable
   protected vk e;

   public static ebf a(Reader var0) {
      return afd.a(a, _snowman, ebf.class);
   }

   public static ebf a(String var0) {
      return a(new StringReader(_snowman));
   }

   public ebf(@Nullable vk var1, List<ebb> var2, Map<String, Either<elr, String>> var3, boolean var4, @Nullable ebf.b var5, ebm var6, List<ebj> var7) {
      this.h = _snowman;
      this.j = _snowman;
      this.i = _snowman;
      this.c = _snowman;
      this.e = _snowman;
      this.k = _snowman;
      this.l = _snowman;
   }

   public List<ebb> a() {
      return this.h.isEmpty() && this.d != null ? this.d.a() : this.h;
   }

   public boolean b() {
      return this.d != null ? this.d.b() : this.j;
   }

   public ebf.b c() {
      if (this.i != null) {
         return this.i;
      } else {
         return this.d != null ? this.d.c() : ebf.b.b;
      }
   }

   public List<ebj> e() {
      return this.l;
   }

   private ebk a(els var1, ebf var2) {
      return this.l.isEmpty() ? ebk.a : new ebk(_snowman, _snowman, _snowman::a, this.l);
   }

   @Override
   public Collection<vk> f() {
      Set<vk> _snowman = Sets.newHashSet();

      for (ebj _snowmanx : this.l) {
         _snowman.add(_snowmanx.a());
      }

      if (this.e != null) {
         _snowman.add(this.e);
      }

      return _snowman;
   }

   @Override
   public Collection<elr> a(Function<vk, ely> var1, Set<Pair<String, String>> var2) {
      Set<ely> _snowman = Sets.newLinkedHashSet();

      for (ebf _snowmanx = this; _snowmanx.e != null && _snowmanx.d == null; _snowmanx = _snowmanx.d) {
         _snowman.add(_snowmanx);
         ely _snowmanxx = _snowman.apply(_snowmanx.e);
         if (_snowmanxx == null) {
            f.warn("No parent '{}' while loading model '{}'", this.e, _snowmanx);
         }

         if (_snowman.contains(_snowmanxx)) {
            f.warn(
               "Found 'parent' loop while loading model '{}' in chain: {} -> {}",
               _snowmanx,
               _snowman.stream().map(Object::toString).collect(Collectors.joining(" -> ")),
               this.e
            );
            _snowmanxx = null;
         }

         if (_snowmanxx == null) {
            _snowmanx.e = els.l;
            _snowmanxx = _snowman.apply(_snowmanx.e);
         }

         if (!(_snowmanxx instanceof ebf)) {
            throw new IllegalStateException("BlockModel parent has to be a block model.");
         }

         _snowmanx.d = (ebf)_snowmanxx;
      }

      Set<elr> _snowmanx = Sets.newHashSet(new elr[]{this.c("particle")});

      for (ebb _snowmanxxx : this.a()) {
         for (ebc _snowmanxxxx : _snowmanxxx.c.values()) {
            elr _snowmanxxxxx = this.c(_snowmanxxxx.c);
            if (Objects.equals(_snowmanxxxxx.b(), ejv.a())) {
               _snowman.add(Pair.of(_snowmanxxxx.c, this.b));
            }

            _snowmanx.add(_snowmanxxxxx);
         }
      }

      this.l.forEach(var4x -> {
         ely _snowmanxxx = _snowman.apply(var4x.a());
         if (!Objects.equals(_snowmanxxx, this)) {
            _snowman.addAll(_snowmanxxx.a(_snowman, _snowman));
         }
      });
      if (this.g() == els.n) {
         ebi.a.forEach(var2x -> _snowman.add(this.c(var2x)));
      }

      return _snowmanx;
   }

   @Override
   public elo a(els var1, Function<elr, ekc> var2, elv var3, vk var4) {
      return this.a(_snowman, this, _snowman, _snowman, _snowman, true);
   }

   public elo a(els var1, ebf var2, Function<elr, ekc> var3, elv var4, vk var5, boolean var6) {
      ekc _snowman = _snowman.apply(this.c("particle"));
      if (this.g() == els.o) {
         return new elq(this.h(), this.a(_snowman, _snowman), _snowman, this.c().a());
      } else {
         elx.a _snowmanx = new elx.a(this, this.a(_snowman, _snowman), _snowman).a(_snowman);

         for (ebb _snowmanxx : this.a()) {
            for (gc _snowmanxxx : _snowmanxx.c.keySet()) {
               ebc _snowmanxxxx = _snowmanxx.c.get(_snowmanxxx);
               ekc _snowmanxxxxx = _snowman.apply(this.c(_snowmanxxxx.c));
               if (_snowmanxxxx.a == null) {
                  _snowmanx.a(a(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx, _snowman, _snowman));
               } else {
                  _snowmanx.a(gc.a(_snowman.b().c(), _snowmanxxxx.a), a(_snowmanxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxx, _snowman, _snowman));
               }
            }
         }

         return _snowmanx.b();
      }
   }

   private static eba a(ebb var0, ebc var1, ekc var2, gc var3, elv var4, vk var5) {
      return g.a(_snowman.a, _snowman.b, _snowman, _snowman, _snowman, _snowman, _snowman.d, _snowman.e, _snowman);
   }

   public boolean b(String var1) {
      return !ejv.a().equals(this.c(_snowman).b());
   }

   public elr c(String var1) {
      if (f(_snowman)) {
         _snowman = _snowman.substring(1);
      }

      List<String> _snowman = Lists.newArrayList();

      while (true) {
         Either<elr, String> _snowmanx = this.e(_snowman);
         Optional<elr> _snowmanxx = _snowmanx.left();
         if (_snowmanxx.isPresent()) {
            return _snowmanxx.get();
         }

         _snowman = (String)_snowmanx.right().get();
         if (_snowman.contains(_snowman)) {
            f.warn("Unable to resolve texture due to reference chain {}->{} in {}", Joiner.on("->").join(_snowman), _snowman, this.b);
            return new elr(ekb.d, ejv.a());
         }

         _snowman.add(_snowman);
      }
   }

   private Either<elr, String> e(String var1) {
      for (ebf _snowman = this; _snowman != null; _snowman = _snowman.d) {
         Either<elr, String> _snowmanx = _snowman.c.get(_snowman);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      return Either.left(new elr(ekb.d, ejv.a()));
   }

   private static boolean f(String var0) {
      return _snowman.charAt(0) == '#';
   }

   public ebf g() {
      return this.d == null ? this : this.d.g();
   }

   public ebm h() {
      ebl _snowman = this.a(ebm.b.b);
      ebl _snowmanx = this.a(ebm.b.c);
      ebl _snowmanxx = this.a(ebm.b.d);
      ebl _snowmanxxx = this.a(ebm.b.e);
      ebl _snowmanxxxx = this.a(ebm.b.f);
      ebl _snowmanxxxxx = this.a(ebm.b.g);
      ebl _snowmanxxxxxx = this.a(ebm.b.h);
      ebl _snowmanxxxxxxx = this.a(ebm.b.i);
      return new ebm(_snowman, _snowmanx, _snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx, _snowmanxxxxxx, _snowmanxxxxxxx);
   }

   private ebl a(ebm.b var1) {
      return this.d != null && !this.k.b(_snowman) ? this.d.a(_snowman) : this.k.a(_snowman);
   }

   @Override
   public String toString() {
      return this.b;
   }

   public static class a implements JsonDeserializer<ebf> {
      public a() {
      }

      public ebf a(JsonElement var1, Type var2, JsonDeserializationContext var3) throws JsonParseException {
         JsonObject _snowman = _snowman.getAsJsonObject();
         List<ebb> _snowmanx = this.b(_snowman, _snowman);
         String _snowmanxx = this.c(_snowman);
         Map<String, Either<elr, String>> _snowmanxxx = this.b(_snowman);
         boolean _snowmanxxxx = this.a(_snowman);
         ebm _snowmanxxxxx = ebm.a;
         if (_snowman.has("display")) {
            JsonObject _snowmanxxxxxx = afd.t(_snowman, "display");
            _snowmanxxxxx = (ebm)_snowman.deserialize(_snowmanxxxxxx, ebm.class);
         }

         List<ebj> _snowmanxxxxxx = this.a(_snowman, _snowman);
         ebf.b _snowmanxxxxxxx = null;
         if (_snowman.has("gui_light")) {
            _snowmanxxxxxxx = ebf.b.a(afd.h(_snowman, "gui_light"));
         }

         vk _snowmanxxxxxxxx = _snowmanxx.isEmpty() ? null : new vk(_snowmanxx);
         return new ebf(_snowmanxxxxxxxx, _snowmanx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxxxx, _snowmanxxxxx, _snowmanxxxxxx);
      }

      protected List<ebj> a(JsonDeserializationContext var1, JsonObject var2) {
         List<ebj> _snowman = Lists.newArrayList();
         if (_snowman.has("overrides")) {
            for (JsonElement _snowmanx : afd.u(_snowman, "overrides")) {
               _snowman.add((ebj)_snowman.deserialize(_snowmanx, ebj.class));
            }
         }

         return _snowman;
      }

      private Map<String, Either<elr, String>> b(JsonObject var1) {
         vk _snowman = ekb.d;
         Map<String, Either<elr, String>> _snowmanx = Maps.newHashMap();
         if (_snowman.has("textures")) {
            JsonObject _snowmanxx = afd.t(_snowman, "textures");

            for (Entry<String, JsonElement> _snowmanxxx : _snowmanxx.entrySet()) {
               _snowmanx.put(_snowmanxxx.getKey(), a(_snowman, _snowmanxxx.getValue().getAsString()));
            }
         }

         return _snowmanx;
      }

      private static Either<elr, String> a(vk var0, String var1) {
         if (ebf.f(_snowman)) {
            return Either.right(_snowman.substring(1));
         } else {
            vk _snowman = vk.a(_snowman);
            if (_snowman == null) {
               throw new JsonParseException(_snowman + " is not valid resource location");
            } else {
               return Either.left(new elr(_snowman, _snowman));
            }
         }
      }

      private String c(JsonObject var1) {
         return afd.a(_snowman, "parent", "");
      }

      protected boolean a(JsonObject var1) {
         return afd.a(_snowman, "ambientocclusion", true);
      }

      protected List<ebb> b(JsonDeserializationContext var1, JsonObject var2) {
         List<ebb> _snowman = Lists.newArrayList();
         if (_snowman.has("elements")) {
            for (JsonElement _snowmanx : afd.u(_snowman, "elements")) {
               _snowman.add((ebb)_snowman.deserialize(_snowmanx, ebb.class));
            }
         }

         return _snowman;
      }
   }

   public static enum b {
      a("front"),
      b("side");

      private final String c;

      private b(String var3) {
         this.c = _snowman;
      }

      public static ebf.b a(String var0) {
         for (ebf.b _snowman : values()) {
            if (_snowman.c.equals(_snowman)) {
               return _snowman;
            }
         }

         throw new IllegalArgumentException("Invalid gui light: " + _snowman);
      }

      public boolean a() {
         return this == b;
      }
   }
}
