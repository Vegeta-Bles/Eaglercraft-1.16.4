import com.google.common.base.MoreObjects;
import com.google.common.collect.ImmutableSet;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.JsonOps;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Locale;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalLong;
import java.util.Properties;
import java.util.Random;
import java.util.Map.Entry;
import java.util.function.Function;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class chw {
   public static final Codec<chw> a = RecordCodecBuilder.create(
         var0 -> var0.group(
                  Codec.LONG.fieldOf("seed").stable().forGetter(chw::a),
                  Codec.BOOL.fieldOf("generate_features").orElse(true).stable().forGetter(chw::b),
                  Codec.BOOL.fieldOf("bonus_chest").orElse(false).stable().forGetter(chw::c),
                  gi.b(gm.M, Lifecycle.stable(), che.a).xmap(che::a, Function.identity()).fieldOf("dimensions").forGetter(chw::d),
                  Codec.STRING.optionalFieldOf("legacy_custom_options").stable().forGetter(var0x -> var0x.g)
               )
               .apply(var0, var0.stable(chw::new))
      )
      .comapFlatMap(chw::m, Function.identity());
   private static final Logger b = LogManager.getLogger();
   private final long c;
   private final boolean d;
   private final boolean e;
   private final gi<che> f;
   private final Optional<String> g;

   private DataResult<chw> m() {
      che _snowman = this.f.a(che.b);
      if (_snowman == null) {
         return DataResult.error("Overworld settings missing");
      } else {
         return this.n() ? DataResult.success(this, Lifecycle.stable()) : DataResult.success(this);
      }
   }

   private boolean n() {
      return che.a(this.c, this.f);
   }

   public chw(long var1, boolean var3, boolean var4, gi<che> var5) {
      this(_snowman, _snowman, _snowman, _snowman, Optional.empty());
      che _snowman = _snowman.a(che.b);
      if (_snowman == null) {
         throw new IllegalStateException("Overworld settings missing");
      }
   }

   private chw(long var1, boolean var3, boolean var4, gi<che> var5, Optional<String> var6) {
      this.c = _snowman;
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
   }

   public static chw a(gn var0) {
      gm<bsv> _snowman = _snowman.b(gm.ay);
      int _snowmanx = "North Carolina".hashCode();
      gm<chd> _snowmanxx = _snowman.b(gm.K);
      gm<chp> _snowmanxxx = _snowman.b(gm.ar);
      return new chw((long)_snowmanx, true, true, a(_snowmanxx, chd.a(_snowmanxx, _snowman, _snowmanxxx, (long)_snowmanx), a(_snowman, _snowmanxxx, (long)_snowmanx)));
   }

   public static chw a(gm<chd> var0, gm<bsv> var1, gm<chp> var2) {
      long _snowman = new Random().nextLong();
      return new chw(_snowman, true, false, a(_snowman, chd.a(_snowman, _snowman, _snowman, _snowman), a(_snowman, _snowman, _snowman)));
   }

   public static cho a(gm<bsv> var0, gm<chp> var1, long var2) {
      return new cho(new btj(_snowman, false, false, _snowman), _snowman, () -> _snowman.d(chp.c));
   }

   public long a() {
      return this.c;
   }

   public boolean b() {
      return this.d;
   }

   public boolean c() {
      return this.e;
   }

   public static gi<che> a(gm<chd> var0, gi<che> var1, cfy var2) {
      che _snowman = _snowman.a(che.b);
      Supplier<chd> _snowmanx = () -> _snowman == null ? _snowman.d(chd.f) : _snowman.b();
      return a(_snowman, _snowmanx, _snowman);
   }

   public static gi<che> a(gi<che> var0, Supplier<chd> var1, cfy var2) {
      gi<che> _snowman = new gi<>(gm.M, Lifecycle.experimental());
      _snowman.a(che.b, new che(_snowman, _snowman), Lifecycle.stable());

      for (Entry<vj<che>, che> _snowmanx : _snowman.d()) {
         vj<che> _snowmanxx = _snowmanx.getKey();
         if (_snowmanxx != che.b) {
            _snowman.a(_snowmanxx, _snowmanx.getValue(), _snowman.d(_snowmanx.getValue()));
         }
      }

      return _snowman;
   }

   public gi<che> d() {
      return this.f;
   }

   public cfy e() {
      che _snowman = this.f.a(che.b);
      if (_snowman == null) {
         throw new IllegalStateException("Overworld settings missing");
      } else {
         return _snowman.c();
      }
   }

   public ImmutableSet<vj<brx>> f() {
      return this.d().d().stream().map(var0 -> vj.a(gm.L, var0.getKey().a())).collect(ImmutableSet.toImmutableSet());
   }

   public boolean g() {
      return this.e() instanceof chj;
   }

   public boolean h() {
      return this.e() instanceof chl;
   }

   public boolean i() {
      return this.g.isPresent();
   }

   public chw j() {
      return new chw(this.c, this.d, true, this.f, this.g);
   }

   public chw k() {
      return new chw(this.c, !this.d, this.e, this.f);
   }

   public chw l() {
      return new chw(this.c, this.d, !this.e, this.f);
   }

   public static chw a(gn var0, Properties var1) {
      String _snowman = (String)MoreObjects.firstNonNull((String)_snowman.get("generator-settings"), "");
      _snowman.put("generator-settings", _snowman);
      String _snowmanx = (String)MoreObjects.firstNonNull((String)_snowman.get("level-seed"), "");
      _snowman.put("level-seed", _snowmanx);
      String _snowmanxx = (String)_snowman.get("generate-structures");
      boolean _snowmanxxx = _snowmanxx == null || Boolean.parseBoolean(_snowmanxx);
      _snowman.put("generate-structures", Objects.toString(_snowmanxxx));
      String _snowmanxxxx = (String)_snowman.get("level-type");
      String _snowmanxxxxx = Optional.ofNullable(_snowmanxxxx).map(var0x -> var0x.toLowerCase(Locale.ROOT)).orElse("default");
      _snowman.put("level-type", _snowmanxxxxx);
      long _snowmanxxxxxx = new Random().nextLong();
      if (!_snowmanx.isEmpty()) {
         try {
            long _snowmanxxxxxxx = Long.parseLong(_snowmanx);
            if (_snowmanxxxxxxx != 0L) {
               _snowmanxxxxxx = _snowmanxxxxxxx;
            }
         } catch (NumberFormatException var18) {
            _snowmanxxxxxx = (long)_snowmanx.hashCode();
         }
      }

      gm<chd> _snowmanxxxxxxx = _snowman.b(gm.K);
      gm<bsv> _snowmanxxxxxxxx = _snowman.b(gm.ay);
      gm<chp> _snowmanxxxxxxxxx = _snowman.b(gm.ar);
      gi<che> _snowmanxxxxxxxxxx = chd.a(_snowmanxxxxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx);
      switch (_snowmanxxxxx) {
         case "flat":
            JsonObject _snowmanxxxxxxxxxxx = !_snowman.isEmpty() ? afd.a(_snowman) : new JsonObject();
            Dynamic<JsonElement> _snowmanxxxxxxxxxxxx = new Dynamic(JsonOps.INSTANCE, _snowmanxxxxxxxxxxx);
            return new chw(
               _snowmanxxxxxx, _snowmanxxx, false, a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, new chl(cpf.a.parse(_snowmanxxxxxxxxxxxx).resultOrPartial(b::error).orElseGet(() -> cpf.a(_snowman))))
            );
         case "debug_all_block_states":
            return new chw(_snowmanxxxxxx, _snowmanxxx, false, a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, new chj(_snowmanxxxxxxxx)));
         case "amplified":
            return new chw(_snowmanxxxxxx, _snowmanxxx, false, a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, new cho(new btj(_snowmanxxxxxx, false, false, _snowmanxxxxxxxx), _snowmanxxxxxx, () -> _snowman.d(chp.d))));
         case "largebiomes":
            return new chw(_snowmanxxxxxx, _snowmanxxx, false, a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, new cho(new btj(_snowmanxxxxxx, false, true, _snowmanxxxxxxxx), _snowmanxxxxxx, () -> _snowman.d(chp.c))));
         default:
            return new chw(_snowmanxxxxxx, _snowmanxxx, false, a(_snowmanxxxxxxx, _snowmanxxxxxxxxxx, a(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowmanxxxxxx)));
      }
   }

   public chw a(boolean var1, OptionalLong var2) {
      long _snowman = _snowman.orElse(this.c);
      gi<che> _snowmanx;
      if (_snowman.isPresent()) {
         _snowmanx = new gi<>(gm.M, Lifecycle.experimental());
         long _snowmanxx = _snowman.getAsLong();

         for (Entry<vj<che>, che> _snowmanxxx : this.f.d()) {
            vj<che> _snowmanxxxx = _snowmanxxx.getKey();
            _snowmanx.a(_snowmanxxxx, new che(_snowmanxxx.getValue().a(), _snowmanxxx.getValue().c().a(_snowmanxx)), this.f.d(_snowmanxxx.getValue()));
         }
      } else {
         _snowmanx = this.f;
      }

      chw _snowmanxx;
      if (this.g()) {
         _snowmanxx = new chw(_snowman, false, false, _snowmanx);
      } else {
         _snowmanxx = new chw(_snowman, this.b(), this.c() && !_snowman, _snowmanx);
      }

      return _snowmanxx;
   }
}
