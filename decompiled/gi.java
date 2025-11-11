import com.google.common.collect.BiMap;
import com.google.common.collect.HashBiMap;
import com.google.common.collect.ImmutableList;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Iterators;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableList.Builder;
import com.mojang.serialization.Codec;
import com.mojang.serialization.Lifecycle;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenCustomHashMap;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import it.unimi.dsi.fastutil.objects.ObjectList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.OptionalInt;
import java.util.Random;
import java.util.Set;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import org.apache.commons.lang3.Validate;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class gi<T> extends gs<T> {
   protected static final Logger a = LogManager.getLogger();
   private final ObjectList<T> bf = new ObjectArrayList(256);
   private final Object2IntMap<T> bg = new Object2IntOpenCustomHashMap(x.k());
   private final BiMap<vk, T> bh;
   private final BiMap<vj<T>, T> bi;
   private final Map<T, Lifecycle> bj;
   private Lifecycle bk;
   protected Object[] b;
   private int bl;

   public gi(vj<? extends gm<T>> var1, Lifecycle var2) {
      super(_snowman, _snowman);
      this.bg.defaultReturnValue(-1);
      this.bh = HashBiMap.create();
      this.bi = HashBiMap.create();
      this.bj = Maps.newIdentityHashMap();
      this.bk = _snowman;
   }

   public static <T> MapCodec<gi.a<T>> a(vj<? extends gm<T>> var0, MapCodec<T> var1) {
      return RecordCodecBuilder.mapCodec(
         var2 -> var2.group(
                  vk.a.xmap(vj.b(_snowman), vj::a).fieldOf("name").forGetter(var0x -> var0x.a),
                  Codec.INT.fieldOf("id").forGetter(var0x -> var0x.b),
                  _snowman.forGetter(var0x -> var0x.c)
               )
               .apply(var2, gi.a::new)
      );
   }

   @Override
   public <V extends T> V a(int var1, vj<T> var2, V var3, Lifecycle var4) {
      return this.a(_snowman, _snowman, _snowman, _snowman, true);
   }

   private <V extends T> V a(int var1, vj<T> var2, V var3, Lifecycle var4, boolean var5) {
      Validate.notNull(_snowman);
      Validate.notNull(_snowman);
      this.bf.size(Math.max(this.bf.size(), _snowman + 1));
      this.bf.set(_snowman, _snowman);
      this.bg.put(_snowman, _snowman);
      this.b = null;
      if (_snowman && this.bi.containsKey(_snowman)) {
         a.debug("Adding duplicate key '{}' to registry", _snowman);
      }

      if (this.bh.containsValue(_snowman)) {
         a.error("Adding duplicate value '{}' to registry", _snowman);
      }

      this.bh.put(_snowman.a(), _snowman);
      this.bi.put(_snowman, _snowman);
      this.bj.put((T)_snowman, _snowman);
      this.bk = this.bk.add(_snowman);
      if (this.bl <= _snowman) {
         this.bl = _snowman + 1;
      }

      return _snowman;
   }

   @Override
   public <V extends T> V a(vj<T> var1, V var2, Lifecycle var3) {
      return this.a(this.bl, _snowman, _snowman, _snowman);
   }

   @Override
   public <V extends T> V a(OptionalInt var1, vj<T> var2, V var3, Lifecycle var4) {
      Validate.notNull(_snowman);
      Validate.notNull(_snowman);
      T _snowman = (T)this.bi.get(_snowman);
      int _snowmanx;
      if (_snowman == null) {
         _snowmanx = _snowman.isPresent() ? _snowman.getAsInt() : this.bl;
      } else {
         _snowmanx = this.bg.getInt(_snowman);
         if (_snowman.isPresent() && _snowman.getAsInt() != _snowmanx) {
            throw new IllegalStateException("ID mismatch");
         }

         this.bg.removeInt(_snowman);
         this.bj.remove(_snowman);
      }

      return this.a(_snowmanx, _snowman, _snowman, _snowman, false);
   }

   @Nullable
   @Override
   public vk b(T var1) {
      return (vk)this.bh.inverse().get(_snowman);
   }

   @Override
   public Optional<vj<T>> c(T var1) {
      return Optional.ofNullable((vj<T>)this.bi.inverse().get(_snowman));
   }

   @Override
   public int a(@Nullable T var1) {
      return this.bg.getInt(_snowman);
   }

   @Nullable
   @Override
   public T a(@Nullable vj<T> var1) {
      return (T)this.bi.get(_snowman);
   }

   @Nullable
   @Override
   public T a(int var1) {
      return (T)(_snowman >= 0 && _snowman < this.bf.size() ? this.bf.get(_snowman) : null);
   }

   @Override
   public Lifecycle d(T var1) {
      return this.bj.get(_snowman);
   }

   @Override
   public Lifecycle b() {
      return this.bk;
   }

   @Override
   public Iterator<T> iterator() {
      return Iterators.filter(this.bf.iterator(), Objects::nonNull);
   }

   @Nullable
   @Override
   public T a(@Nullable vk var1) {
      return (T)this.bh.get(_snowman);
   }

   @Override
   public Set<vk> c() {
      return Collections.unmodifiableSet(this.bh.keySet());
   }

   @Override
   public Set<Entry<vj<T>, T>> d() {
      return Collections.<vj<T>, T>unmodifiableMap(this.bi).entrySet();
   }

   @Nullable
   public T a(Random var1) {
      if (this.b == null) {
         Collection<?> _snowman = this.bh.values();
         if (_snowman.isEmpty()) {
            return null;
         }

         this.b = _snowman.toArray(new Object[_snowman.size()]);
      }

      return x.a((T[])this.b, _snowman);
   }

   @Override
   public boolean c(vk var1) {
      return this.bh.containsKey(_snowman);
   }

   public static <T> Codec<gi<T>> a(vj<? extends gm<T>> var0, Lifecycle var1, Codec<T> var2) {
      return a(_snowman, _snowman.fieldOf("element")).codec().listOf().xmap(var2x -> {
         gi<T> _snowman = new gi<>(_snowman, _snowman);

         for (gi.a<T> _snowmanx : var2x) {
            _snowman.a(_snowmanx.b, _snowmanx.a, _snowmanx.c, _snowman);
         }

         return _snowman;
      }, var0x -> {
         Builder<gi.a<T>> _snowman = ImmutableList.builder();

         for (T _snowmanx : var0x) {
            _snowman.add(new gi.a((vj<T>)var0x.c(_snowmanx).get(), var0x.a(_snowmanx), _snowmanx));
         }

         return _snowman.build();
      });
   }

   public static <T> Codec<gi<T>> b(vj<? extends gm<T>> var0, Lifecycle var1, Codec<T> var2) {
      return ve.a(_snowman, _snowman, _snowman);
   }

   public static <T> Codec<gi<T>> c(vj<? extends gm<T>> var0, Lifecycle var1, Codec<T> var2) {
      return Codec.unboundedMap(vk.a.xmap(vj.b(_snowman), vj::a), _snowman).xmap(var2x -> {
         gi<T> _snowman = new gi<>(_snowman, _snowman);
         var2x.forEach((var2xx, var3x) -> _snowman.a((vj<T>)var2xx, var3x, _snowman));
         return _snowman;
      }, var0x -> ImmutableMap.copyOf(var0x.bi));
   }

   public static class a<T> {
      public final vj<T> a;
      public final int b;
      public final T c;

      public a(vj<T> var1, int var2, T var3) {
         this.a = _snowman;
         this.b = _snowman;
         this.c = _snowman;
      }
   }
}
