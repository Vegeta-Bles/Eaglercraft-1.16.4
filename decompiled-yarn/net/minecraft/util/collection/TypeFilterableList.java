package net.minecraft.util.collection;

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

public class TypeFilterableList<T> extends AbstractCollection<T> {
   private final Map<Class<?>, List<T>> elementsByType = Maps.newHashMap();
   private final Class<T> elementType;
   private final List<T> allElements = Lists.newArrayList();

   public TypeFilterableList(Class<T> elementType) {
      this.elementType = elementType;
      this.elementsByType.put(elementType, this.allElements);
   }

   @Override
   public boolean add(T e) {
      boolean _snowman = false;

      for (Entry<Class<?>, List<T>> _snowmanx : this.elementsByType.entrySet()) {
         if (_snowmanx.getKey().isInstance(e)) {
            _snowman |= _snowmanx.getValue().add(e);
         }
      }

      return _snowman;
   }

   @Override
   public boolean remove(Object o) {
      boolean _snowman = false;

      for (Entry<Class<?>, List<T>> _snowmanx : this.elementsByType.entrySet()) {
         if (_snowmanx.getKey().isInstance(o)) {
            List<T> _snowmanxx = _snowmanx.getValue();
            _snowman |= _snowmanxx.remove(o);
         }
      }

      return _snowman;
   }

   @Override
   public boolean contains(Object o) {
      return this.getAllOfType(o.getClass()).contains(o);
   }

   public <S> Collection<S> getAllOfType(Class<S> type) {
      if (!this.elementType.isAssignableFrom(type)) {
         throw new IllegalArgumentException("Don't know how to search for " + type);
      } else {
         List<T> _snowman = this.elementsByType.computeIfAbsent(type, _snowmanx -> this.allElements.stream().filter(_snowmanx::isInstance).collect(Collectors.toList()));
         return (Collection<S>)Collections.unmodifiableCollection(_snowman);
      }
   }

   @Override
   public Iterator<T> iterator() {
      return (Iterator<T>)(this.allElements.isEmpty() ? Collections.emptyIterator() : Iterators.unmodifiableIterator(this.allElements.iterator()));
   }

   public List<T> method_29903() {
      return ImmutableList.copyOf(this.allElements);
   }

   @Override
   public int size() {
      return this.allElements.size();
   }
}
