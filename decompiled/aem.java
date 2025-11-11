import com.google.common.collect.BiMap;
import com.google.common.collect.ImmutableBiMap;
import com.google.common.collect.ImmutableSet;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.ImmutableSet.Builder;
import java.util.Collection;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import javax.annotation.Nullable;

public interface aem<T> {
   Map<vk, ael<T>> a();

   @Nullable
   default ael<T> a(vk var1) {
      return this.a().get(_snowman);
   }

   ael<T> b(vk var1);

   @Nullable
   vk a(ael<T> var1);

   default vk b(ael<T> var1) {
      vk _snowman = this.a(_snowman);
      if (_snowman == null) {
         throw new IllegalStateException("Unrecognized tag");
      } else {
         return _snowman;
      }
   }

   default Collection<vk> b() {
      return this.a().keySet();
   }

   default Collection<vk> a(T var1) {
      List<vk> _snowman = Lists.newArrayList();

      for (Entry<vk, ael<T>> _snowmanx : this.a().entrySet()) {
         if (_snowmanx.getValue().a(_snowman)) {
            _snowman.add(_snowmanx.getKey());
         }
      }

      return _snowman;
   }

   default void a(nf var1, gb<T> var2) {
      Map<vk, ael<T>> _snowman = this.a();
      _snowman.d(_snowman.size());

      for (Entry<vk, ael<T>> _snowmanx : _snowman.entrySet()) {
         _snowman.a(_snowmanx.getKey());
         _snowman.d(_snowmanx.getValue().b().size());

         for (T _snowmanxx : _snowmanx.getValue().b()) {
            _snowman.d(_snowman.a(_snowmanxx));
         }
      }
   }

   static <T> aem<T> a(nf var0, gm<T> var1) {
      Map<vk, ael<T>> _snowman = Maps.newHashMap();
      int _snowmanx = _snowman.i();

      for (int _snowmanxx = 0; _snowmanxx < _snowmanx; _snowmanxx++) {
         vk _snowmanxxx = _snowman.p();
         int _snowmanxxxx = _snowman.i();
         Builder<T> _snowmanxxxxx = ImmutableSet.builder();

         for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxx; _snowmanxxxxxx++) {
            _snowmanxxxxx.add(_snowman.a(_snowman.i()));
         }

         _snowman.put(_snowmanxxx, ael.b(_snowmanxxxxx.build()));
      }

      return a(_snowman);
   }

   static <T> aem<T> c() {
      return a(ImmutableBiMap.of());
   }

   static <T> aem<T> a(Map<vk, ael<T>> var0) {
      final BiMap<vk, ael<T>> _snowman = ImmutableBiMap.copyOf(_snowman);
      return new aem<T>() {
         private final ael<T> b = aei.a();

         @Override
         public ael<T> b(vk var1x) {
            return (ael<T>)_snowman.getOrDefault(_snowman, this.b);
         }

         @Nullable
         @Override
         public vk a(ael<T> var1x) {
            return _snowman instanceof ael.e ? ((ael.e)_snowman).a() : (vk)_snowman.inverse().get(_snowman);
         }

         @Override
         public Map<vk, ael<T>> a() {
            return _snowman;
         }
      };
   }
}
