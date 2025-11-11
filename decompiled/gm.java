import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.Keyable;
import com.mojang.serialization.Lifecycle;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public abstract class gm<T> implements Codec<T>, Keyable, gg<T> {
   protected static final Logger c = LogManager.getLogger();
   private static final Map<vk, Supplier<?>> a = Maps.newLinkedHashMap();
   public static final vk d = new vk("root");
   protected static final gs<gs<?>> e = new gi<>(a("root"), Lifecycle.experimental());
   public static final gm<? extends gm<?>> f = e;
   public static final vj<gm<adp>> g = a("sound_event");
   public static final vj<gm<cuw>> h = a("fluid");
   public static final vj<gm<aps>> i = a("mob_effect");
   public static final vj<gm<buo>> j = a("block");
   public static final vj<gm<bps>> k = a("enchantment");
   public static final vj<gm<aqe<?>>> l = a("entity_type");
   public static final vj<gm<blx>> m = a("item");
   public static final vj<gm<bnt>> n = a("potion");
   public static final vj<gm<hg<?>>> o = a("particle_type");
   public static final vj<gm<cck<?>>> p = a("block_entity_type");
   public static final vj<gm<bcr>> q = a("motive");
   public static final vj<gm<vk>> r = a("custom_stat");
   public static final vj<gm<cga>> s = a("chunk_status");
   public static final vj<gm<csv<?>>> t = a("rule_test");
   public static final vj<gm<csp<?>>> u = a("pos_rule_test");
   public static final vj<gm<bje<?>>> v = a("menu");
   public static final vj<gm<bot<?>>> w = a("recipe_type");
   public static final vj<gm<bos<?>>> x = a("recipe_serializer");
   public static final vj<gm<arg>> y = a("attribute");
   public static final vj<gm<adz<?>>> z = a("stat_type");
   public static final vj<gm<bfo>> A = a("villager_type");
   public static final vj<gm<bfm>> B = a("villager_profession");
   public static final vj<gm<azr>> C = a("point_of_interest_type");
   public static final vj<gm<ayd<?>>> D = a("memory_module_type");
   public static final vj<gm<azc<?>>> E = a("sensor_type");
   public static final vj<gm<bhh>> F = a("schedule");
   public static final vj<gm<bhf>> G = a("activity");
   public static final vj<gm<czr>> H = a("loot_pool_entry_type");
   public static final vj<gm<dak>> I = a("loot_function_type");
   public static final vj<gm<dbp>> J = a("loot_condition_type");
   public static final vj<gm<chd>> K = a("dimension_type");
   public static final vj<gm<brx>> L = a("dimension");
   public static final vj<gm<che>> M = a("dimension");
   public static final gm<adp> N = a(g, () -> adq.gL);
   public static final gb<cuw> O = a(h, "empty", () -> cuy.a);
   public static final gm<aps> P = a(i, () -> apw.z);
   public static final gb<buo> Q = a(j, "air", () -> bup.a);
   public static final gm<bps> R = a(k, () -> bpw.w);
   public static final gb<aqe<?>> S = a(l, "pig", () -> aqe.ah);
   public static final gb<blx> T = a(m, "air", () -> bmd.a);
   public static final gb<bnt> U = a(n, "empty", () -> bnw.a);
   public static final gm<hg<?>> V = a(o, () -> hh.d);
   public static final gm<cck<?>> W = a(p, () -> cck.a);
   public static final gb<bcr> X = a(q, "kebab", () -> bcr.a);
   public static final gm<vk> Y = a(r, () -> aea.D);
   public static final gb<cga> Z = a(s, "empty", () -> cga.a);
   public static final gm<csv<?>> aa = a(t, () -> csv.a);
   public static final gm<csp<?>> ab = a(u, () -> csp.a);
   public static final gm<bje<?>> ac = a(v, () -> bje.h);
   public static final gm<bot<?>> ad = a(w, () -> bot.a);
   public static final gm<bos<?>> ae = a(x, () -> bos.b);
   public static final gm<arg> af = a(y, () -> arl.k);
   public static final gm<adz<?>> ag = a(z, () -> aea.c);
   public static final gb<bfo> ah = a(A, "plains", () -> bfo.c);
   public static final gb<bfm> ai = a(B, "none", () -> bfm.a);
   public static final gb<azr> aj = a(C, "unemployed", () -> azr.c);
   public static final gb<ayd<?>> ak = a(D, "dummy", () -> ayd.a);
   public static final gb<azc<?>> al = a(E, "dummy", () -> azc.a);
   public static final gm<bhh> am = a(F, () -> bhh.a);
   public static final gm<bhf> an = a(G, () -> bhf.b);
   public static final gm<czr> ao = a(H, () -> czo.a);
   public static final gm<dak> ap = a(I, () -> dal.b);
   public static final gm<dbp> aq = a(J, () -> dbq.a);
   public static final vj<gm<chp>> ar = a("worldgen/noise_settings");
   public static final vj<gm<ctg<?>>> as = a("worldgen/configured_surface_builder");
   public static final vj<gm<cib<?>>> at = a("worldgen/configured_carver");
   public static final vj<gm<civ<?, ?>>> au = a("worldgen/configured_feature");
   public static final vj<gm<ciw<?, ?>>> av = a("worldgen/configured_structure_feature");
   public static final vj<gm<csz>> aw = a("worldgen/processor_list");
   public static final vj<gm<cok>> ax = a("worldgen/template_pool");
   public static final vj<gm<bsv>> ay = a("worldgen/biome");
   public static final vj<gm<ctt<?>>> az = a("worldgen/surface_builder");
   public static final gm<ctt<?>> aA = a(az, () -> ctt.v);
   public static final vj<gm<cig<?>>> aB = a("worldgen/carver");
   public static final gm<cig<?>> aC = a(aB, () -> cig.a);
   public static final vj<gm<cjl<?>>> aD = a("worldgen/feature");
   public static final gm<cjl<?>> aE = a(aD, () -> cjl.A);
   public static final vj<gm<cla<?>>> aF = a("worldgen/structure_feature");
   public static final gm<cla<?>> aG = a(aF, () -> cla.c);
   public static final vj<gm<clb>> aH = a("worldgen/structure_piece");
   public static final gm<clb> aI = a(aH, () -> clb.c);
   public static final vj<gm<cqc<?>>> aJ = a("worldgen/decorator");
   public static final gm<cqc<?>> aK = a(aJ, () -> cqc.a);
   public static final vj<gm<cnu<?>>> aL = a("worldgen/block_state_provider_type");
   public static final vj<gm<clm<?>>> aM = a("worldgen/block_placer_type");
   public static final vj<gm<cnm<?>>> aN = a("worldgen/foliage_placer_type");
   public static final vj<gm<cpc<?>>> aO = a("worldgen/trunk_placer_type");
   public static final vj<gm<cos<?>>> aP = a("worldgen/tree_decorator_type");
   public static final vj<gm<cnc<?>>> aQ = a("worldgen/feature_size_type");
   public static final vj<gm<Codec<? extends bsy>>> aR = a("worldgen/biome_source");
   public static final vj<gm<Codec<? extends cfy>>> aS = a("worldgen/chunk_generator");
   public static final vj<gm<cta<?>>> aT = a("worldgen/structure_processor");
   public static final vj<gm<coj<?>>> aU = a("worldgen/structure_pool_element");
   public static final gm<cnu<?>> aV = a(aL, () -> cnu.a);
   public static final gm<clm<?>> aW = a(aM, () -> clm.a);
   public static final gm<cnm<?>> aX = a(aN, () -> cnm.a);
   public static final gm<cpc<?>> aY = a(aO, () -> cpc.a);
   public static final gm<cos<?>> aZ = a(aP, () -> cos.b);
   public static final gm<cnc<?>> ba = a(aQ, () -> cnc.a);
   public static final gm<Codec<? extends bsy>> bb = a(aR, Lifecycle.stable(), () -> bsy.a);
   public static final gm<Codec<? extends cfy>> bc = a(aS, Lifecycle.stable(), () -> cfy.a);
   public static final gm<cta<?>> bd = a(aT, () -> cta.a);
   public static final gm<coj<?>> be = a(aU, () -> coj.d);
   private final vj<? extends gm<T>> b;
   private final Lifecycle bf;

   private static <T> vj<gm<T>> a(String var0) {
      return vj.a(new vk(_snowman));
   }

   public static <T extends gs<?>> void a(gs<T> var0) {
      _snowman.forEach(var1 -> {
         if (var1.c().isEmpty()) {
            c.error("Registry '{}' was empty after loading", _snowman.b((T)var1));
            if (w.d) {
               throw new IllegalStateException("Registry: '" + _snowman.b((T)var1) + "' is empty, not allowed, fix me!");
            }
         }

         if (var1 instanceof gb) {
            vk _snowman = ((gb)var1).a();
            Validate.notNull(var1.a(_snowman), "Missing default of DefaultedMappedRegistry: " + _snowman, new Object[0]);
         }
      });
   }

   private static <T> gm<T> a(vj<? extends gm<T>> var0, Supplier<T> var1) {
      return a(_snowman, Lifecycle.experimental(), _snowman);
   }

   private static <T> gb<T> a(vj<? extends gm<T>> var0, String var1, Supplier<T> var2) {
      return a(_snowman, _snowman, Lifecycle.experimental(), _snowman);
   }

   private static <T> gm<T> a(vj<? extends gm<T>> var0, Lifecycle var1, Supplier<T> var2) {
      return a(_snowman, new gi<>(_snowman, _snowman), _snowman, _snowman);
   }

   private static <T> gb<T> a(vj<? extends gm<T>> var0, String var1, Lifecycle var2, Supplier<T> var3) {
      return a(_snowman, new gb<>(_snowman, _snowman, _snowman), _snowman, _snowman);
   }

   private static <T, R extends gs<T>> R a(vj<? extends gm<T>> var0, R var1, Supplier<T> var2, Lifecycle var3) {
      vk _snowman = _snowman.a();
      a.put(_snowman, _snowman);
      gs<R> _snowmanx = e;
      return _snowmanx.a((vj<R>)_snowman, _snowman, _snowman);
   }

   protected gm(vj<? extends gm<T>> var1, Lifecycle var2) {
      this.b = _snowman;
      this.bf = _snowman;
   }

   public vj<? extends gm<T>> f() {
      return this.b;
   }

   @Override
   public String toString() {
      return "Registry[" + this.b + " (" + this.bf + ")]";
   }

   public <U> DataResult<Pair<T, U>> decode(DynamicOps<U> var1, U var2) {
      return _snowman.compressMaps() ? _snowman.getNumberValue(_snowman).flatMap(var1x -> {
         T _snowman = this.a(var1x.intValue());
         return _snowman == null ? DataResult.error("Unknown registry id: " + var1x) : DataResult.success(_snowman, this.d(_snowman));
      }).map(var1x -> Pair.of(var1x, _snowman.empty())) : vk.a.decode(_snowman, _snowman).flatMap(var1x -> {
         T _snowman = this.a((vk)var1x.getFirst());
         return _snowman == null ? DataResult.error("Unknown registry key: " + var1x.getFirst()) : DataResult.success(Pair.of(_snowman, var1x.getSecond()), this.d(_snowman));
      });
   }

   public <U> DataResult<U> encode(T var1, DynamicOps<U> var2, U var3) {
      vk _snowman = this.b(_snowman);
      if (_snowman == null) {
         return DataResult.error("Unknown registry element " + _snowman);
      } else {
         return _snowman.compressMaps()
            ? _snowman.mergeToPrimitive(_snowman, _snowman.createInt(this.a(_snowman))).setLifecycle(this.bf)
            : _snowman.mergeToPrimitive(_snowman, _snowman.createString(_snowman.toString())).setLifecycle(this.bf);
      }
   }

   public <U> Stream<U> keys(DynamicOps<U> var1) {
      return this.c().stream().map(var1x -> (U)_snowman.createString(var1x.toString()));
   }

   @Nullable
   public abstract vk b(T var1);

   public abstract Optional<vj<T>> c(T var1);

   @Override
   public abstract int a(@Nullable T var1);

   @Nullable
   public abstract T a(@Nullable vj<T> var1);

   @Nullable
   public abstract T a(@Nullable vk var1);

   protected abstract Lifecycle d(T var1);

   public abstract Lifecycle b();

   public Optional<T> b(@Nullable vk var1) {
      return Optional.ofNullable(this.a(_snowman));
   }

   public Optional<T> c(@Nullable vj<T> var1) {
      return Optional.ofNullable(this.a(_snowman));
   }

   public T d(vj<T> var1) {
      T _snowman = this.a(_snowman);
      if (_snowman == null) {
         throw new IllegalStateException("Missing: " + _snowman);
      } else {
         return _snowman;
      }
   }

   public abstract Set<vk> c();

   public abstract Set<Entry<vj<T>, T>> d();

   public Stream<T> g() {
      return StreamSupport.stream(this.spliterator(), false);
   }

   public abstract boolean c(vk var1);

   public static <T> T a(gm<? super T> var0, String var1, T var2) {
      return a(_snowman, new vk(_snowman), _snowman);
   }

   public static <V, T extends V> T a(gm<V> var0, vk var1, T var2) {
      return ((gs)_snowman).a(vj.a(_snowman.b, _snowman), _snowman, Lifecycle.stable());
   }

   public static <V, T extends V> T a(gm<V> var0, int var1, String var2, T var3) {
      return ((gs)_snowman).a(_snowman, vj.a(_snowman.b, new vk(_snowman)), _snowman, Lifecycle.stable());
   }

   static {
      hk.a();
      a.forEach((var0, var1) -> {
         if (var1.get() == null) {
            c.error("Unable to bootstrap registry '{}'", var0);
         }
      });
      a(e);
   }
}
