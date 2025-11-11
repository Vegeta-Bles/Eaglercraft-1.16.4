import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.mojang.datafixers.DataFixUtils;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Map.Entry;
import java.util.function.Supplier;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class bsw {
   public static final Logger a = LogManager.getLogger();
   public static final bsw b = new bsw(() -> kp.p, ImmutableMap.of(), ImmutableList.of(), ImmutableList.of());
   public static final MapCodec<bsw> c = RecordCodecBuilder.mapCodec(
      var0 -> var0.group(
               ctg.b.fieldOf("surface_builder").forGetter(var0x -> var0x.d),
               Codec.simpleMap(chm.a.c, cib.c.promotePartial(x.a("Carver: ", a::error)), afs.a(chm.a.values())).fieldOf("carvers").forGetter(var0x -> var0x.e),
               civ.c.promotePartial(x.a("Feature: ", a::error)).listOf().fieldOf("features").forGetter(var0x -> var0x.f),
               ciw.c.promotePartial(x.a("Structure start: ", a::error)).fieldOf("starts").forGetter(var0x -> var0x.g)
            )
            .apply(var0, bsw::new)
   );
   private final Supplier<ctg<?>> d;
   private final Map<chm.a, List<Supplier<cib<?>>>> e;
   private final List<List<Supplier<civ<?, ?>>>> f;
   private final List<Supplier<ciw<?, ?>>> g;
   private final List<civ<?, ?>> h;

   private bsw(Supplier<ctg<?>> var1, Map<chm.a, List<Supplier<cib<?>>>> var2, List<List<Supplier<civ<?, ?>>>> var3, List<Supplier<ciw<?, ?>>> var4) {
      this.d = _snowman;
      this.e = _snowman;
      this.f = _snowman;
      this.g = _snowman;
      this.h = _snowman.stream()
         .flatMap(Collection::stream)
         .map(Supplier::get)
         .flatMap(civ::d)
         .filter(var0 -> var0.e == cjl.d)
         .collect(ImmutableList.toImmutableList());
   }

   public List<Supplier<cib<?>>> a(chm.a var1) {
      return this.e.getOrDefault(_snowman, ImmutableList.of());
   }

   public boolean a(cla<?> var1) {
      return this.g.stream().anyMatch(var1x -> var1x.get().d == _snowman);
   }

   public Collection<Supplier<ciw<?, ?>>> a() {
      return this.g;
   }

   public ciw<?, ?> a(ciw<?, ?> var1) {
      return (ciw<?, ?>)DataFixUtils.orElse(this.g.stream().map(Supplier::get).filter(var1x -> var1x.d == _snowman.d).findAny(), _snowman);
   }

   public List<civ<?, ?>> b() {
      return this.h;
   }

   public List<List<Supplier<civ<?, ?>>>> c() {
      return this.f;
   }

   public Supplier<ctg<?>> d() {
      return this.d;
   }

   public ctv e() {
      return this.d.get().a();
   }

   public static class a {
      private Optional<Supplier<ctg<?>>> a = Optional.empty();
      private final Map<chm.a, List<Supplier<cib<?>>>> b = Maps.newLinkedHashMap();
      private final List<List<Supplier<civ<?, ?>>>> c = Lists.newArrayList();
      private final List<Supplier<ciw<?, ?>>> d = Lists.newArrayList();

      public a() {
      }

      public bsw.a a(ctg<?> var1) {
         return this.a(() -> _snowman);
      }

      public bsw.a a(Supplier<ctg<?>> var1) {
         this.a = Optional.of(_snowman);
         return this;
      }

      public bsw.a a(chm.b var1, civ<?, ?> var2) {
         return this.a(_snowman.ordinal(), () -> _snowman);
      }

      public bsw.a a(int var1, Supplier<civ<?, ?>> var2) {
         this.a(_snowman);
         this.c.get(_snowman).add(_snowman);
         return this;
      }

      public <C extends chz> bsw.a a(chm.a var1, cib<C> var2) {
         this.b.computeIfAbsent(_snowman, var0 -> Lists.newArrayList()).add(() -> _snowman);
         return this;
      }

      public bsw.a a(ciw<?, ?> var1) {
         this.d.add(() -> _snowman);
         return this;
      }

      private void a(int var1) {
         while (this.c.size() <= _snowman) {
            this.c.add(Lists.newArrayList());
         }
      }

      public bsw a() {
         return new bsw(
            this.a.orElseThrow(() -> new IllegalStateException("Missing surface builder")),
            this.b.entrySet().stream().collect(ImmutableMap.toImmutableMap(Entry::getKey, var0 -> ImmutableList.copyOf((Collection)var0.getValue()))),
            this.c.stream().map(ImmutableList::copyOf).collect(ImmutableList.toImmutableList()),
            ImmutableList.copyOf(this.d)
         );
      }
   }
}
