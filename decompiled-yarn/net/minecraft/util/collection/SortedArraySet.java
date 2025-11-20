package net.minecraft.util.collection;

import it.unimi.dsi.fastutil.objects.ObjectArrays;
import java.util.AbstractSet;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.NoSuchElementException;

public class SortedArraySet<T> extends AbstractSet<T> {
   private final Comparator<T> comparator;
   private T[] elements;
   private int size;

   private SortedArraySet(int initialCapacity, Comparator<T> comparator) {
      this.comparator = comparator;
      if (initialCapacity < 0) {
         throw new IllegalArgumentException("Initial capacity (" + initialCapacity + ") is negative");
      } else {
         this.elements = (T[])cast(new Object[initialCapacity]);
      }
   }

   public static <T extends Comparable<T>> SortedArraySet<T> create(int initialCapacity) {
      return new SortedArraySet<>(initialCapacity, Comparator.naturalOrder());
   }

   private static <T> T[] cast(Object[] array) {
      return (T[])array;
   }

   private int binarySearch(T object) {
      return Arrays.binarySearch(this.elements, 0, this.size, object, this.comparator);
   }

   private static int insertionPoint(int binarySearchResult) {
      return -binarySearchResult - 1;
   }

   @Override
   public boolean add(T _snowman) {
      int _snowmanx = this.binarySearch(_snowman);
      if (_snowmanx >= 0) {
         return false;
      } else {
         int _snowmanxx = insertionPoint(_snowmanx);
         this.add(_snowman, _snowmanxx);
         return true;
      }
   }

   private void ensureCapacity(int minCapacity) {
      if (minCapacity > this.elements.length) {
         if (this.elements != ObjectArrays.DEFAULT_EMPTY_ARRAY) {
            minCapacity = (int)Math.max(Math.min((long)this.elements.length + (long)(this.elements.length >> 1), 2147483639L), (long)minCapacity);
         } else if (minCapacity < 10) {
            minCapacity = 10;
         }

         Object[] _snowman = new Object[minCapacity];
         System.arraycopy(this.elements, 0, _snowman, 0, this.size);
         this.elements = (T[])cast(_snowman);
      }
   }

   private void add(T object, int index) {
      this.ensureCapacity(this.size + 1);
      if (index != this.size) {
         System.arraycopy(this.elements, index, this.elements, index + 1, this.size - index);
      }

      this.elements[index] = object;
      this.size++;
   }

   private void remove(int index) {
      this.size--;
      if (index != this.size) {
         System.arraycopy(this.elements, index + 1, this.elements, index, this.size - index);
      }

      this.elements[this.size] = null;
   }

   private T get(int index) {
      return this.elements[index];
   }

   public T addAndGet(T object) {
      int _snowman = this.binarySearch(object);
      if (_snowman >= 0) {
         return this.get(_snowman);
      } else {
         this.add(object, insertionPoint(_snowman));
         return object;
      }
   }

   @Override
   public boolean remove(Object _snowman) {
      int _snowmanx = this.binarySearch((T)_snowman);
      if (_snowmanx >= 0) {
         this.remove(_snowmanx);
         return true;
      } else {
         return false;
      }
   }

   public T first() {
      return this.get(0);
   }

   @Override
   public boolean contains(Object _snowman) {
      int _snowmanx = this.binarySearch((T)_snowman);
      return _snowmanx >= 0;
   }

   @Override
   public Iterator<T> iterator() {
      return new SortedArraySet.SetIterator();
   }

   @Override
   public int size() {
      return this.size;
   }

   @Override
   public Object[] toArray() {
      return (Object[])this.elements.clone();
   }

   @Override
   public <U> U[] toArray(U[] _snowman) {
      if (_snowman.length < this.size) {
         return (U[])Arrays.copyOf(this.elements, this.size, (Class<? extends T[]>)_snowman.getClass());
      } else {
         System.arraycopy(this.elements, 0, _snowman, 0, this.size);
         if (_snowman.length > this.size) {
            _snowman[this.size] = null;
         }

         return _snowman;
      }
   }

   @Override
   public void clear() {
      Arrays.fill(this.elements, 0, this.size, null);
      this.size = 0;
   }

   @Override
   public boolean equals(Object _snowman) {
      if (this == _snowman) {
         return true;
      } else {
         if (_snowman instanceof SortedArraySet) {
            SortedArraySet<?> _snowmanx = (SortedArraySet<?>)_snowman;
            if (this.comparator.equals(_snowmanx.comparator)) {
               return this.size == _snowmanx.size && Arrays.equals(this.elements, _snowmanx.elements);
            }
         }

         return super.equals(_snowman);
      }
   }

   class SetIterator implements Iterator<T> {
      private int nextIndex;
      private int lastIndex = -1;

      private SetIterator() {
      }

      @Override
      public boolean hasNext() {
         return this.nextIndex < SortedArraySet.this.size;
      }

      @Override
      public T next() {
         if (this.nextIndex >= SortedArraySet.this.size) {
            throw new NoSuchElementException();
         } else {
            this.lastIndex = this.nextIndex++;
            return SortedArraySet.this.elements[this.lastIndex];
         }
      }

      @Override
      public void remove() {
         if (this.lastIndex == -1) {
            throw new IllegalStateException();
         } else {
            SortedArraySet.this.remove(this.lastIndex);
            this.nextIndex--;
            this.lastIndex = -1;
         }
      }
   }
}
