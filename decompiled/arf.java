import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.UnmodifiableIterator;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.datafixers.util.Pair;
import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.MapLike;
import com.mojang.serialization.RecordBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Supplier;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import org.apache.commons.lang3.mutable.MutableObject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class arf<E extends aqm> {
   private static final Logger a = LogManager.getLogger();
   private final Supplier<Codec<arf<E>>> b;
   private final Map<ayd<?>, Optional<? extends ayc<?>>> c = Maps.newHashMap();
   private final Map<azc<? extends azb<? super E>>, azb<? super E>> d = Maps.newLinkedHashMap();
   private final Map<Integer, Map<bhf, Set<arv<? super E>>>> e = Maps.newTreeMap();
   private bhh f = bhh.a;
   private final Map<bhf, Set<Pair<ayd<?>, aye>>> g = Maps.newHashMap();
   private final Map<bhf, Set<ayd<?>>> h = Maps.newHashMap();
   private Set<bhf> i = Sets.newHashSet();
   private final Set<bhf> j = Sets.newHashSet();
   private bhf k = bhf.b;
   private long l = -9999L;

   public static <E extends aqm> arf.b<E> a(Collection<? extends ayd<?>> var0, Collection<? extends azc<? extends azb<? super E>>> var1) {
      return new arf.b<>(_snowman, _snowman);
   }

   public static <E extends aqm> Codec<arf<E>> b(final Collection<? extends ayd<?>> var0, final Collection<? extends azc<? extends azb<? super E>>> var1) {
      final MutableObject<Codec<arf<E>>> _snowman = new MutableObject();
      _snowman.setValue(
         (new MapCodec<arf<E>>() {
               public <T> Stream<T> keys(DynamicOps<T> var1x) {
                  return _snowman.stream().flatMap(var0x -> x.a(var0x.a().map(var1xx -> gm.ak.b((ayd<?>)var0x)))).map(var1xx -> (T)_snowman.createString(var1xx.toString()));
               }

               public <T> DataResult<arf<E>> decode(DynamicOps<T> var1x, MapLike<T> var2x) {
                  MutableObject<DataResult<Builder<arf.a<?>>>> _snowman = new MutableObject(DataResult.success(ImmutableList.builder()));
                  _snowman.entries().forEach(var3x -> {
                     DataResult<ayd<?>> _snowmanx = gm.ak.parse(_snowman, var3x.getFirst());
                     DataResult<? extends arf.a<?>> _snowmanx = _snowmanx.flatMap(var3xx -> this.a(var3xx, _snowman, (T)var3x.getSecond()));
                     _snowman.setValue(((DataResult)_snowman.getValue()).apply2(Builder::add, _snowmanx));
                  });
                  ImmutableList<arf.a<?>> _snowmanx = ((DataResult)_snowman.getValue())
                     .resultOrPartial(arf.a::error)
                     .<ImmutableList<arf.a<?>>>map(Builder::build)
                     .orElseGet(ImmutableList::of);
                  return DataResult.success(new arf<>(_snowman, _snowman, _snowmanx, _snowman::getValue));
               }

               private <T, U> DataResult<arf.a<U>> a(ayd<U> var1x, DynamicOps<T> var2x, T var3) {
                  return _snowman.a()
                     .<DataResult>map(DataResult::success)
                     .orElseGet(() -> DataResult.error("No codec for memory: " + _snowman))
                     .flatMap(var2xx -> var2xx.parse(_snowman, _snowman))
                     .map(var1xx -> new arf.a(_snowman, Optional.of(var1xx)));
               }

               public <T> RecordBuilder<T> a(arf<E> var1x, DynamicOps<T> var2x, RecordBuilder<T> var3) {
                  _snowman.j().forEach(var2xx -> var2xx.a(_snowman, _snowman));
                  return _snowman;
               }
            })
            .fieldOf("memories")
            .codec()
      );
      return (Codec<arf<E>>)_snowman.getValue();
   }

   public arf(
      Collection<? extends ayd<?>> var1, Collection<? extends azc<? extends azb<? super E>>> var2, ImmutableList<arf.a<?>> var3, Supplier<Codec<arf<E>>> var4
   ) {
      this.b = _snowman;

      for (ayd<?> _snowman : _snowman) {
         this.c.put(_snowman, Optional.empty());
      }

      for (azc<? extends azb<? super E>> _snowman : _snowman) {
         this.d.put(_snowman, (azb<? super E>)_snowman.a());
      }

      for (azb<? super E> _snowman : this.d.values()) {
         for (ayd<?> _snowmanx : _snowman.a()) {
            this.c.put(_snowmanx, Optional.empty());
         }
      }

      UnmodifiableIterator var11 = _snowman.iterator();

      while (var11.hasNext()) {
         arf.a<?> _snowman = (arf.a<?>)var11.next();
         _snowman.a(this);
      }
   }

   public <T> DataResult<T> a(DynamicOps<T> var1) {
      return this.b.get().encodeStart(_snowman, this);
   }

   private Stream<arf.a<?>> j() {
      return this.c.entrySet().stream().map(var0 -> arf.a.b(var0.getKey(), var0.getValue()));
   }

   public boolean a(ayd<?> var1) {
      return this.a(_snowman, aye.a);
   }

   public <U> void b(ayd<U> var1) {
      this.a(_snowman, Optional.empty());
   }

   public <U> void a(ayd<U> var1, @Nullable U var2) {
      this.a(_snowman, Optional.ofNullable(_snowman));
   }

   public <U> void a(ayd<U> var1, U var2, long var3) {
      this.b(_snowman, Optional.of(ayc.a(_snowman, _snowman)));
   }

   public <U> void a(ayd<U> var1, Optional<? extends U> var2) {
      this.b(_snowman, _snowman.map(ayc::a));
   }

   private <U> void b(ayd<U> var1, Optional<? extends ayc<?>> var2) {
      if (this.c.containsKey(_snowman)) {
         if (_snowman.isPresent() && this.a(_snowman.get().c())) {
            this.b(_snowman);
         } else {
            this.c.put(_snowman, _snowman);
         }
      }
   }

   public <U> Optional<U> c(ayd<U> var1) {
      return this.c.get(_snowman).map(ayc::c);
   }

   public <U> boolean b(ayd<U> var1, U var2) {
      return !this.a(_snowman) ? false : this.c(_snowman).filter(var1x -> var1x.equals(_snowman)).isPresent();
   }

   public boolean a(ayd<?> var1, aye var2) {
      Optional<? extends ayc<?>> _snowman = this.c.get(_snowman);
      return _snowman == null ? false : _snowman == aye.c || _snowman == aye.a && _snowman.isPresent() || _snowman == aye.b && !_snowman.isPresent();
   }

   public bhh b() {
      return this.f;
   }

   public void a(bhh var1) {
      this.f = _snowman;
   }

   public void a(Set<bhf> var1) {
      this.i = _snowman;
   }

   @Deprecated
   public List<arv<? super E>> d() {
      List<arv<? super E>> _snowman = new ObjectArrayList();

      for (Map<bhf, Set<arv<? super E>>> _snowmanx : this.e.values()) {
         for (Set<arv<? super E>> _snowmanxx : _snowmanx.values()) {
            for (arv<? super E> _snowmanxxx : _snowmanxx) {
               if (_snowmanxxx.a() == arv.a.b) {
                  _snowman.add(_snowmanxxx);
               }
            }
         }
      }

      return _snowman;
   }

   public void e() {
      this.d(this.k);
   }

   public Optional<bhf> f() {
      for (bhf _snowman : this.j) {
         if (!this.i.contains(_snowman)) {
            return Optional.of(_snowman);
         }
      }

      return Optional.empty();
   }

   public void a(bhf var1) {
      if (this.f(_snowman)) {
         this.d(_snowman);
      } else {
         this.e();
      }
   }

   private void d(bhf var1) {
      if (!this.c(_snowman)) {
         this.e(_snowman);
         this.j.clear();
         this.j.addAll(this.i);
         this.j.add(_snowman);
      }
   }

   private void e(bhf var1) {
      for (bhf _snowman : this.j) {
         if (_snowman != _snowman) {
            Set<ayd<?>> _snowmanx = this.h.get(_snowman);
            if (_snowmanx != null) {
               for (ayd<?> _snowmanxx : _snowmanx) {
                  this.b(_snowmanxx);
               }
            }
         }
      }
   }

   public void a(long var1, long var3) {
      if (_snowman - this.l > 20L) {
         this.l = _snowman;
         bhf _snowman = this.b().a((int)(_snowman % 24000L));
         if (!this.j.contains(_snowman)) {
            this.a(_snowman);
         }
      }
   }

   public void a(List<bhf> var1) {
      for (bhf _snowman : _snowman) {
         if (this.f(_snowman)) {
            this.d(_snowman);
            break;
         }
      }
   }

   public void b(bhf var1) {
      this.k = _snowman;
   }

   public void a(bhf var1, int var2, ImmutableList<? extends arv<? super E>> var3) {
      this.a(_snowman, this.a(_snowman, _snowman));
   }

   public void a(bhf var1, int var2, ImmutableList<? extends arv<? super E>> var3, ayd<?> var4) {
      Set<Pair<ayd<?>, aye>> _snowman = ImmutableSet.of(Pair.of(_snowman, aye.a));
      Set<ayd<?>> _snowmanx = ImmutableSet.of(_snowman);
      this.a(_snowman, this.a(_snowman, _snowman), _snowman, _snowmanx);
   }

   public void a(bhf var1, ImmutableList<? extends Pair<Integer, ? extends arv<? super E>>> var2) {
      this.a(_snowman, _snowman, ImmutableSet.of(), Sets.newHashSet());
   }

   public void a(bhf var1, ImmutableList<? extends Pair<Integer, ? extends arv<? super E>>> var2, Set<Pair<ayd<?>, aye>> var3) {
      this.a(_snowman, _snowman, _snowman, Sets.newHashSet());
   }

   private void a(bhf var1, ImmutableList<? extends Pair<Integer, ? extends arv<? super E>>> var2, Set<Pair<ayd<?>, aye>> var3, Set<ayd<?>> var4) {
      this.g.put(_snowman, _snowman);
      if (!_snowman.isEmpty()) {
         this.h.put(_snowman, _snowman);
      }

      UnmodifiableIterator var5 = _snowman.iterator();

      while (var5.hasNext()) {
         Pair<Integer, ? extends arv<? super E>> _snowman = (Pair<Integer, ? extends arv<? super E>>)var5.next();
         this.e
            .computeIfAbsent((Integer)_snowman.getFirst(), var0 -> Maps.newHashMap())
            .computeIfAbsent(_snowman, var0 -> Sets.newLinkedHashSet())
            .add((arv<? super E>)_snowman.getSecond());
      }
   }

   public boolean c(bhf var1) {
      return this.j.contains(_snowman);
   }

   public arf<E> h() {
      arf<E> _snowman = new arf<>(this.c.keySet(), this.d.keySet(), ImmutableList.of(), this.b);

      for (Entry<ayd<?>, Optional<? extends ayc<?>>> _snowmanx : this.c.entrySet()) {
         ayd<?> _snowmanxx = _snowmanx.getKey();
         if (_snowmanx.getValue().isPresent()) {
            _snowman.c.put(_snowmanxx, _snowmanx.getValue());
         }
      }

      return _snowman;
   }

   public void a(aag var1, E var2) {
      this.k();
      this.c(_snowman, _snowman);
      this.d(_snowman, _snowman);
      this.e(_snowman, _snowman);
   }

   private void c(aag var1, E var2) {
      for (azb<? super E> _snowman : this.d.values()) {
         _snowman.b(_snowman, _snowman);
      }
   }

   private void k() {
      for (Entry<ayd<?>, Optional<? extends ayc<?>>> _snowman : this.c.entrySet()) {
         if (_snowman.getValue().isPresent()) {
            ayc<?> _snowmanx = (ayc<?>)_snowman.getValue().get();
            _snowmanx.a();
            if (_snowmanx.d()) {
               this.b(_snowman.getKey());
            }
         }
      }
   }

   public void b(aag var1, E var2) {
      long _snowman = _snowman.l.T();

      for (arv<? super E> _snowmanx : this.d()) {
         _snowmanx.g(_snowman, _snowman, _snowman);
      }
   }

   private void d(aag var1, E var2) {
      long _snowman = _snowman.T();

      for (Map<bhf, Set<arv<? super E>>> _snowmanx : this.e.values()) {
         for (Entry<bhf, Set<arv<? super E>>> _snowmanxx : _snowmanx.entrySet()) {
            bhf _snowmanxxx = _snowmanxx.getKey();
            if (this.j.contains(_snowmanxxx)) {
               for (arv<? super E> _snowmanxxxx : _snowmanxx.getValue()) {
                  if (_snowmanxxxx.a() == arv.a.a) {
                     _snowmanxxxx.e(_snowman, _snowman, _snowman);
                  }
               }
            }
         }
      }
   }

   private void e(aag var1, E var2) {
      long _snowman = _snowman.T();

      for (arv<? super E> _snowmanx : this.d()) {
         _snowmanx.f(_snowman, _snowman, _snowman);
      }
   }

   private boolean f(bhf var1) {
      if (!this.g.containsKey(_snowman)) {
         return false;
      } else {
         for (Pair<ayd<?>, aye> _snowman : this.g.get(_snowman)) {
            ayd<?> _snowmanx = (ayd<?>)_snowman.getFirst();
            aye _snowmanxx = (aye)_snowman.getSecond();
            if (!this.a(_snowmanx, _snowmanxx)) {
               return false;
            }
         }

         return true;
      }
   }

   private boolean a(Object var1) {
      return _snowman instanceof Collection && ((Collection)_snowman).isEmpty();
   }

   ImmutableList<? extends Pair<Integer, ? extends arv<? super E>>> a(int var1, ImmutableList<? extends arv<? super E>> var2) {
      int _snowman = _snowman;
      Builder<Pair<Integer, ? extends arv<? super E>>> _snowmanx = ImmutableList.builder();
      UnmodifiableIterator var5 = _snowman.iterator();

      while (var5.hasNext()) {
         arv<? super E> _snowmanxx = (arv<? super E>)var5.next();
         _snowmanx.add(Pair.of(_snowman++, _snowmanxx));
      }

      return _snowmanx.build();
   }

   static final class a<U> {
      private final ayd<U> a;
      private final Optional<? extends ayc<U>> b;

      private static <U> arf.a<U> b(ayd<U> var0, Optional<? extends ayc<?>> var1) {
         return new arf.a<>(_snowman, (Optional<? extends ayc<U>>)_snowman);
      }

      private a(ayd<U> var1, Optional<? extends ayc<U>> var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      private void a(arf<?> var1) {
         _snowman.b(this.a, this.b);
      }

      public <T> void a(DynamicOps<T> var1, RecordBuilder<T> var2) {
         this.a.a().ifPresent(var3 -> this.b.ifPresent(var4 -> _snowman.add(gm.ak.encodeStart(_snowman, this.a), var3.encodeStart(_snowman, var4))));
      }
   }

   public static final class b<E extends aqm> {
      private final Collection<? extends ayd<?>> a;
      private final Collection<? extends azc<? extends azb<? super E>>> b;
      private final Codec<arf<E>> c;

      private b(Collection<? extends ayd<?>> var1, Collection<? extends azc<? extends azb<? super E>>> var2) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = arf.b(_snowman, _snowman);
      }

      public arf<E> a(Dynamic<?> var1) {
         return this.c.parse(_snowman).resultOrPartial(arf.a::error).orElseGet(() -> new arf<>(this.a, this.b, ImmutableList.of(), () -> this.c));
      }
   }
}
