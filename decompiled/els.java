import com.google.common.annotations.VisibleForTesting;
import com.google.common.base.Splitter;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.Reader;
import java.io.StringReader;
import java.nio.charset.StandardCharsets;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Predicate;
import java.util.function.Supplier;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.tuple.Triple;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class els {
   public static final elr a = new elr(ekb.d, new vk("block/fire_0"));
   public static final elr b = new elr(ekb.d, new vk("block/fire_1"));
   public static final elr c = new elr(ekb.d, new vk("block/lava_flow"));
   public static final elr d = new elr(ekb.d, new vk("block/water_flow"));
   public static final elr e = new elr(ekb.d, new vk("block/water_overlay"));
   public static final elr f = new elr(ekb.d, new vk("entity/banner_base"));
   public static final elr g = new elr(ekb.d, new vk("entity/shield_base"));
   public static final elr h = new elr(ekb.d, new vk("entity/shield_base_nopattern"));
   public static final List<vk> i = IntStream.range(0, 10).mapToObj(var0 -> new vk("block/destroy_stage_" + var0)).collect(Collectors.toList());
   public static final List<vk> j = i.stream().map(var0 -> new vk("textures/" + var0.a() + ".png")).collect(Collectors.toList());
   public static final List<eao> k = j.stream().map(eao::o).collect(Collectors.toList());
   private static final Set<elr> p = x.a(Sets.newHashSet(), var0 -> {
      var0.add(d);
      var0.add(c);
      var0.add(e);
      var0.add(a);
      var0.add(b);
      var0.add(ecc.a);
      var0.add(eci.a);
      var0.add(eci.c);
      var0.add(eci.d);
      var0.add(eci.e);
      var0.add(eci.f);
      var0.add(eci.g);
      var0.add(ecj.a);
      var0.add(f);
      var0.add(g);
      var0.add(h);

      for (vk _snowman : i) {
         var0.add(new elr(ekb.d, _snowman));
      }

      var0.add(new elr(ekb.d, biz.d));
      var0.add(new elr(ekb.d, biz.e));
      var0.add(new elr(ekb.d, biz.f));
      var0.add(new elr(ekb.d, biz.g));
      var0.add(new elr(ekb.d, biz.h));
      ear.a(var0::add);
   });
   private static final Logger q = LogManager.getLogger();
   public static final elu l = new elu("builtin/missing", "missing");
   private static final String r = l.toString();
   @VisibleForTesting
   public static final String m = ("{    'textures': {       'particle': '"
         + ejv.a().a()
         + "',       'missingno': '"
         + ejv.a().a()
         + "'    },    'elements': [         {  'from': [ 0, 0, 0 ],            'to': [ 16, 16, 16 ],            'faces': {                'down':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'down',  'texture': '#missingno' },                'up':    { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'up',    'texture': '#missingno' },                'north': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'north', 'texture': '#missingno' },                'south': { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'south', 'texture': '#missingno' },                'west':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'west',  'texture': '#missingno' },                'east':  { 'uv': [ 0, 0, 16, 16 ], 'cullface': 'east',  'texture': '#missingno' }            }        }    ]}")
      .replace('\'', '"');
   private static final Map<String, String> s = Maps.newHashMap(ImmutableMap.of("missing", m));
   private static final Splitter t = Splitter.on(',');
   private static final Splitter u = Splitter.on('=').limit(2);
   public static final ebf n = x.a(ebf.a("{\"gui_light\": \"front\"}"), var0 -> var0.b = "generation marker");
   public static final ebf o = x.a(ebf.a("{\"gui_light\": \"side\"}"), var0 -> var0.b = "block entity marker");
   private static final cei<buo, ceh> v = new cei.a<buo, ceh>(bup.a).a(cey.a("map")).a(buo::n, ceh::new);
   private static final ebi w = new ebi();
   private static final Map<vk, cei<buo, ceh>> x = ImmutableMap.of(new vk("item_frame"), v);
   private final ach y;
   @Nullable
   private ejr z;
   private final dko A;
   private final Set<vk> B = Sets.newHashSet();
   private final ebg.a C = new ebg.a();
   private final Map<vk, ely> D = Maps.newHashMap();
   private final Map<Triple<vk, f, Boolean>, elo> E = Maps.newHashMap();
   private final Map<vk, ely> F = Maps.newHashMap();
   private final Map<vk, elo> G = Maps.newHashMap();
   private final Map<vk, Pair<ekb, ekb.a>> H;
   private int I = 1;
   private final Object2IntMap<ceh> J = x.a(new Object2IntOpenHashMap(), var0 -> var0.defaultReturnValue(-1));

   public els(ach var1, dko var2, anw var3, int var4) {
      this.y = _snowman;
      this.A = _snowman;
      _snowman.a("missing_model");

      try {
         this.D.put(l, this.c(l));
         this.a(l);
      } catch (IOException var12) {
         q.error("Error loading missing model, should never happen :(", var12);
         throw new RuntimeException(var12);
      }

      _snowman.b("static_definitions");
      x.forEach((var1x, var2x) -> var2x.a().forEach(var2xx -> this.a(eaw.a(var1x, var2xx))));
      _snowman.b("blocks");

      for (buo _snowman : gm.Q) {
         _snowman.m().a().forEach(var1x -> this.a(eaw.c(var1x)));
      }

      _snowman.b("items");

      for (vk _snowman : gm.T.c()) {
         this.a(new elu(_snowman, "inventory"));
      }

      _snowman.b("special");
      this.a(new elu("minecraft:trident_in_hand#inventory"));
      _snowman.b("textures");
      Set<Pair<String, String>> _snowman = Sets.newLinkedHashSet();
      Set<elr> _snowmanx = this.F.values().stream().flatMap(var2x -> var2x.a(this::a, _snowman).stream()).collect(Collectors.toSet());
      _snowmanx.addAll(p);
      _snowman.stream()
         .filter(var0 -> !((String)var0.getSecond()).equals(r))
         .forEach(var0 -> q.warn("Unable to resolve texture reference: {} in {}", var0.getFirst(), var0.getSecond()));
      Map<vk, List<elr>> _snowmanxx = _snowmanx.stream().collect(Collectors.groupingBy(elr::a));
      _snowman.b("stitching");
      this.H = Maps.newHashMap();

      for (Entry<vk, List<elr>> _snowmanxxx : _snowmanxx.entrySet()) {
         ekb _snowmanxxxx = new ekb(_snowmanxxx.getKey());
         ekb.a _snowmanxxxxx = _snowmanxxxx.a(this.y, _snowmanxxx.getValue().stream().map(elr::b), _snowman, _snowman);
         this.H.put(_snowmanxxx.getKey(), Pair.of(_snowmanxxxx, _snowmanxxxxx));
      }

      _snowman.c();
   }

   public ejr a(ekd var1, anw var2) {
      _snowman.a("atlas");

      for (Pair<ekb, ekb.a> _snowman : this.H.values()) {
         ekb _snowmanx = (ekb)_snowman.getFirst();
         ekb.a _snowmanxx = (ekb.a)_snowman.getSecond();
         _snowmanx.a(_snowmanxx);
         _snowman.a(_snowmanx.g(), _snowmanx);
         _snowman.a(_snowmanx.g());
         _snowmanx.b(_snowmanxx);
      }

      this.z = new ejr(this.H.values().stream().<ekb>map(Pair::getFirst).collect(Collectors.toList()));
      _snowman.b("baking");
      this.F.keySet().forEach(var1x -> {
         elo _snowman = null;

         try {
            _snowman = this.a(var1x, elp.a);
         } catch (Exception var4x) {
            q.warn("Unable to bake model: '{}': {}", var1x, var4x);
         }

         if (_snowman != null) {
            this.G.put(var1x, _snowman);
         }
      });
      _snowman.c();
      return this.z;
   }

   private static Predicate<ceh> a(cei<buo, ceh> var0, String var1) {
      Map<cfj<?>, Comparable<?>> _snowman = Maps.newHashMap();

      for (String _snowmanx : t.split(_snowman)) {
         Iterator<String> _snowmanxx = u.split(_snowmanx).iterator();
         if (_snowmanxx.hasNext()) {
            String _snowmanxxx = _snowmanxx.next();
            cfj<?> _snowmanxxxx = _snowman.a(_snowmanxxx);
            if (_snowmanxxxx != null && _snowmanxx.hasNext()) {
               String _snowmanxxxxx = _snowmanxx.next();
               Comparable<?> _snowmanxxxxxx = a((cfj<Comparable<?>>)_snowmanxxxx, _snowmanxxxxx);
               if (_snowmanxxxxxx == null) {
                  throw new RuntimeException("Unknown value: '" + _snowmanxxxxx + "' for blockstate property: '" + _snowmanxxx + "' " + _snowmanxxxx.a());
               }

               _snowman.put(_snowmanxxxx, _snowmanxxxxxx);
            } else if (!_snowmanxxx.isEmpty()) {
               throw new RuntimeException("Unknown blockstate property: '" + _snowmanxxx + "'");
            }
         }
      }

      buo _snowmanxx = _snowman.c();
      return var2x -> {
         if (var2x != null && _snowman == var2x.b()) {
            for (Entry<cfj<?>, Comparable<?>> _snowmanxxx : _snowman.entrySet()) {
               if (!Objects.equals(var2x.c(_snowmanxxx.getKey()), _snowmanxxx.getValue())) {
                  return false;
               }
            }

            return true;
         } else {
            return false;
         }
      };
   }

   @Nullable
   static <T extends Comparable<T>> T a(cfj<T> var0, String var1) {
      return _snowman.b(_snowman).orElse(null);
   }

   public ely a(vk var1) {
      if (this.D.containsKey(_snowman)) {
         return this.D.get(_snowman);
      } else if (this.B.contains(_snowman)) {
         throw new IllegalStateException("Circular reference while loading " + _snowman);
      } else {
         this.B.add(_snowman);
         ely _snowman = this.D.get(l);

         while (!this.B.isEmpty()) {
            vk _snowmanx = this.B.iterator().next();

            try {
               if (!this.D.containsKey(_snowmanx)) {
                  this.b(_snowmanx);
               }
            } catch (els.a var9) {
               q.warn(var9.getMessage());
               this.D.put(_snowmanx, _snowman);
            } catch (Exception var10) {
               q.warn("Unable to load model: '{}' referenced from: {}: {}", _snowmanx, _snowman, var10);
               this.D.put(_snowmanx, _snowman);
            } finally {
               this.B.remove(_snowmanx);
            }
         }

         return this.D.getOrDefault(_snowman, _snowman);
      }
   }

   private void b(vk var1) throws Exception {
      if (!(_snowman instanceof elu)) {
         this.a(_snowman, this.c(_snowman));
      } else {
         elu _snowman = (elu)_snowman;
         if (Objects.equals(_snowman.d(), "inventory")) {
            vk _snowmanx = new vk(_snowman.b(), "item/" + _snowman.a());
            ebf _snowmanxx = this.c(_snowmanx);
            this.a(_snowman, _snowmanxx);
            this.D.put(_snowmanx, _snowmanxx);
         } else {
            vk _snowmanx = new vk(_snowman.b(), _snowman.a());
            cei<buo, ceh> _snowmanxx = Optional.ofNullable(x.get(_snowmanx)).orElseGet(() -> gm.Q.a(_snowman).m());
            this.C.a(_snowmanxx);
            List<cfj<?>> _snowmanxxx = ImmutableList.copyOf(this.A.a(_snowmanxx.c()));
            ImmutableList<ceh> _snowmanxxxx = _snowmanxx.a();
            Map<elu, ceh> _snowmanxxxxx = Maps.newHashMap();
            _snowmanxxxx.forEach(var2x -> {
               ceh var10000 = _snowman.put(eaw.a(_snowman, var2x), var2x);
            });
            Map<ceh, Pair<ely, Supplier<els.b>>> _snowmanxxxxxx = Maps.newHashMap();
            vk _snowmanxxxxxxx = new vk(_snowman.b(), "blockstates/" + _snowman.a() + ".json");
            ely _snowmanxxxxxxxx = this.D.get(l);
            els.b _snowmanxxxxxxxxx = new els.b(ImmutableList.of(_snowmanxxxxxxxx), ImmutableList.of());
            Pair<ely, Supplier<els.b>> _snowmanxxxxxxxxxx = Pair.of(_snowmanxxxxxxxx, (Supplier<els.b>)() -> _snowman);

            try {
               List<Pair<String, ebg>> _snowmanxxxxxxxxxxx;
               try {
                  _snowmanxxxxxxxxxxx = this.y
                     .c(_snowmanxxxxxxx)
                     .stream()
                     .map(
                        var1x -> {
                           try (InputStream _snowmanxxxxxxxxxxxx = var1x.b()) {
                              return Pair.of(var1x.d(), ebg.a(this.C, new InputStreamReader(_snowmanxxxxxxxxxxxx, StandardCharsets.UTF_8)));
                           } catch (Exception var16x) {
                              throw new els.a(
                                 String.format(
                                    "Exception loading blockstate definition: '%s' in resourcepack: '%s': %s", var1x.a(), var1x.d(), var16x.getMessage()
                                 )
                              );
                           }
                        }
                     )
                     .collect(Collectors.toList());
               } catch (IOException var25) {
                  q.warn("Exception loading blockstate definition: {}: {}", _snowmanxxxxxxx, var25);
                  return;
               }

               for (Pair<String, ebg> _snowmanxxxxxxxxxxxx : _snowmanxxxxxxxxxxx) {
                  ebg _snowmanxxxxxxxxxxxxx = (ebg)_snowmanxxxxxxxxxxxx.getSecond();
                  Map<ceh, Pair<ely, Supplier<els.b>>> _snowmanxxxxxxxxxxxxxx = Maps.newIdentityHashMap();
                  ebs _snowmanxxxxxxxxxxxxxxx;
                  if (_snowmanxxxxxxxxxxxxx.c()) {
                     _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxxxxxxxx.d();
                     _snowmanxxxx.forEach(var3x -> {
                        Pair var10000 = _snowman.put(var3x, Pair.of(_snowman, (Supplier<els.b>)() -> els.b.a(var3x, _snowman, _snowman)));
                     });
                  } else {
                     _snowmanxxxxxxxxxxxxxxx = null;
                  }

                  _snowmanxxxxxxxxxxxxx.a()
                     .forEach(
                        (var9x, var10x) -> {
                           try {
                              _snowman.stream()
                                 .filter(a(_snowman, var9x))
                                 .forEach(
                                    var6x -> {
                                       Pair<ely, Supplier<els.b>> _snowmanxxxxxxxxxxxxxxxx = _snowman.put(
                                          var6x, Pair.of(var10x, (Supplier<els.b>)() -> els.b.a(var6x, var10x, _snowman))
                                       );
                                       if (_snowmanxxxxxxxxxxxxxxxx != null && _snowmanxxxxxxxxxxxxxxxx.getFirst() != _snowman) {
                                          _snowman.put(var6x, _snowman);
                                          throw new RuntimeException(
                                             "Overlapping definition with: "
                                                + _snowman.a().entrySet().stream().filter(var1x -> var1x.getValue() == _snowman.getFirst()).findFirst().get().getKey()
                                          );
                                       }
                                    }
                                 );
                           } catch (Exception var12x) {
                              q.warn(
                                 "Exception loading blockstate definition: '{}' in resourcepack: '{}' for variant: '{}': {}",
                                 _snowman,
                                 _snowman.getFirst(),
                                 var9x,
                                 var12x.getMessage()
                              );
                           }
                        }
                     );
                  _snowmanxxxxxx.putAll(_snowmanxxxxxxxxxxxxxx);
               }
            } catch (els.a var26) {
               throw var26;
            } catch (Exception var27) {
               throw new els.a(String.format("Exception loading blockstate definition: '%s': %s", _snowmanxxxxxxx, var27));
            } finally {
               Map<els.b, Set<ceh>> _snowmanxxxxxxxxxxx = Maps.newHashMap();
               _snowmanxxxxx.forEach((var5x, var6x) -> {
                  Pair<ely, Supplier<els.b>> _snowmanxxxxxxxxxxxx = _snowman.get(var6x);
                  if (_snowmanxxxxxxxxxxxx == null) {
                     q.warn("Exception loading blockstate definition: '{}' missing model for variant: '{}'", _snowman, var5x);
                     _snowmanxxxxxxxxxxxx = _snowman;
                  }

                  this.a(var5x, (ely)_snowmanxxxxxxxxxxxx.getFirst());

                  try {
                     els.b _snowmanx = (els.b)((Supplier)_snowmanxxxxxxxxxxxx.getSecond()).get();
                     _snowman.computeIfAbsent(_snowmanx, var0 -> Sets.newIdentityHashSet()).add(var6x);
                  } catch (Exception var9x) {
                     q.warn("Exception evaluating model definition: '{}'", var5x, var9x);
                  }
               });
               _snowmanxxxxxxxxxxx.forEach((var1x, var2x) -> {
                  Iterator<ceh> _snowmanxxxxxxxxxxxx = var2x.iterator();

                  while (_snowmanxxxxxxxxxxxx.hasNext()) {
                     ceh _snowmanx = _snowmanxxxxxxxxxxxx.next();
                     if (_snowmanx.h() != bzh.c) {
                        _snowmanxxxxxxxxxxxx.remove();
                        this.J.put(_snowmanx, 0);
                     }
                  }

                  if (var2x.size() > 1) {
                     this.a(var2x);
                  }
               });
            }
         }
      }
   }

   private void a(vk var1, ely var2) {
      this.D.put(_snowman, _snowman);
      this.B.addAll(_snowman.f());
   }

   private void a(elu var1) {
      ely _snowman = this.a((vk)_snowman);
      this.D.put(_snowman, _snowman);
      this.F.put(_snowman, _snowman);
   }

   private void a(Iterable<ceh> var1) {
      int _snowman = this.I++;
      _snowman.forEach(var2x -> this.J.put(var2x, _snowman));
   }

   @Nullable
   public elo a(vk var1, elv var2) {
      Triple<vk, f, Boolean> _snowman = Triple.of(_snowman, _snowman.b(), _snowman.c());
      if (this.E.containsKey(_snowman)) {
         return this.E.get(_snowman);
      } else if (this.z == null) {
         throw new IllegalStateException("bake called too early");
      } else {
         ely _snowmanx = this.a(_snowman);
         if (_snowmanx instanceof ebf) {
            ebf _snowmanxx = (ebf)_snowmanx;
            if (_snowmanxx.g() == n) {
               return w.a(this.z::a, _snowmanxx).a(this, _snowmanxx, this.z::a, _snowman, _snowman, false);
            }
         }

         elo _snowmanxx = _snowmanx.a(this, this.z::a, _snowman, _snowman);
         this.E.put(_snowman, _snowmanxx);
         return _snowmanxx;
      }
   }

   private ebf c(vk var1) throws IOException {
      Reader _snowman = null;
      acg _snowmanx = null;

      ebf _snowmanxx;
      try {
         String _snowmanxxx = _snowman.a();
         if ("builtin/generated".equals(_snowmanxxx)) {
            return n;
         }

         if (!"builtin/entity".equals(_snowmanxxx)) {
            if (_snowmanxxx.startsWith("builtin/")) {
               String _snowmanxxxx = _snowmanxxx.substring("builtin/".length());
               String _snowmanxxxxx = s.get(_snowmanxxxx);
               if (_snowmanxxxxx == null) {
                  throw new FileNotFoundException(_snowman.toString());
               }

               _snowman = new StringReader(_snowmanxxxxx);
            } else {
               _snowmanx = this.y.a(new vk(_snowman.b(), "models/" + _snowman.a() + ".json"));
               _snowman = new InputStreamReader(_snowmanx.b(), StandardCharsets.UTF_8);
            }

            _snowmanxx = ebf.a(_snowman);
            _snowmanxx.b = _snowman.toString();
            return _snowmanxx;
         }

         _snowmanxx = o;
      } finally {
         IOUtils.closeQuietly(_snowman);
         IOUtils.closeQuietly(_snowmanx);
      }

      return _snowmanxx;
   }

   public Map<vk, elo> a() {
      return this.G;
   }

   public Object2IntMap<ceh> b() {
      return this.J;
   }

   static class a extends RuntimeException {
      public a(String var1) {
         super(_snowman);
      }
   }

   static class b {
      private final List<ely> a;
      private final List<Object> b;

      public b(List<ely> var1, List<Object> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public boolean equals(Object var1) {
         if (this == _snowman) {
            return true;
         } else if (!(_snowman instanceof els.b)) {
            return false;
         } else {
            els.b _snowman = (els.b)_snowman;
            return Objects.equals(this.a, _snowman.a) && Objects.equals(this.b, _snowman.b);
         }
      }

      @Override
      public int hashCode() {
         return 31 * this.a.hashCode() + this.b.hashCode();
      }

      public static els.b a(ceh var0, ebs var1, Collection<cfj<?>> var2) {
         cei<buo, ceh> _snowman = _snowman.b().m();
         List<ely> _snowmanx = _snowman.a().stream().filter(var2x -> var2x.a(_snowman).test(_snowman)).map(ebu::a).collect(ImmutableList.toImmutableList());
         List<Object> _snowmanxx = a(_snowman, _snowman);
         return new els.b(_snowmanx, _snowmanxx);
      }

      public static els.b a(ceh var0, ely var1, Collection<cfj<?>> var2) {
         List<Object> _snowman = a(_snowman, _snowman);
         return new els.b(ImmutableList.of(_snowman), _snowman);
      }

      private static List<Object> a(ceh var0, Collection<cfj<?>> var1) {
         return _snowman.stream().map(_snowman::c).collect(ImmutableList.toImmutableList());
      }
   }
}
