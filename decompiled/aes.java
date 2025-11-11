import com.google.common.collect.ImmutableList;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import java.util.AbstractCollection;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.stream.Collectors;

public class aes<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> a = Maps.newHashMap();
   private final Class<T> b;
   private final List<T> c = Lists.newArrayList();

   public aes(Class<T> var1) {
      this.b = _snowman;
      this.a.put(_snowman, this.c);
   }

   @Override
   public boolean add(T var1) {
      boolean _snowman = false;

      for (Entry<Class<?>, List<T>> _snowmanx : this.a.entrySet()) {
         if (_snowmanx.getKey().isInstance(_snowman)) {
            _snowman |= _snowmanx.getValue().add(_snowman);
         }
      }

      return _snowman;
   }

   @Override
   public boolean remove(Object var1) {
      boolean _snowman = false;

      for (Entry<Class<?>, List<T>> _snowmanx : this.a.entrySet()) {
         if (_snowmanx.getKey().isInstance(_snowman)) {
            List<T> _snowmanxx = _snowmanx.getValue();
            _snowman |= _snowmanxx.remove(_snowman);
         }
      }

      return _snowman;
   }

   @Override
   public boolean contains(Object var1) {
      return this.a(_snowman.getClass()).contains(_snowman);
   }

   public <S> Collection<S> a(Class<S> var1) {
      if (!this.b.isAssignableFrom(_snowman)) {
         throw new IllegalArgumentException("Don't know how to search for " + _snowman);
      } else {
         List<T> _snowman = this.a.computeIfAbsent(_snowman, var1x -> this.c.stream().filter(var1x::isInstance).collect(Collectors.toList()));
         return (Collection<S>)Collections.unmodifiableCollection(_snowman);
      }
   }

   @Override
   public Iterator<T> iterator() {
      return (Iterator<T>)(this.c.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.c.iterator()));
   }

   public List<T> a() {
      return ImmutableList.copyOf(this.c);
   }

   @Override
   public int size() {
      return this.c.size();
   }
}
