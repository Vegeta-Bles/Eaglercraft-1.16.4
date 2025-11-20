package net.minecraft.util.collection;

import com.google.common.base.Predicates;
import com.google.common.collect.Iterators;
import java.util.Arrays;
import java.util.Iterator;
import javax.annotation.Nullable;
import net.minecraft.util.math.MathHelper;

public class Int2ObjectBiMap<K> implements IndexedIterable<K> {
   private static final Object EMPTY = null;
   private K[] values;
   private int[] ids;
   private K[] idToValues;
   private int nextId;
   private int size;

   public Int2ObjectBiMap(int size) {
      size = (int)((float)size / 0.8F);
      this.values = (K[])(new Object[size]);
      this.ids = new int[size];
      this.idToValues = (K[])(new Object[size]);
   }

   @Override
   public int getRawId(@Nullable K entry) {
      return this.getIdFromIndex(this.findIndex(entry, this.getIdealIndex(entry)));
   }

   @Nullable
   @Override
   public K get(int index) {
      return index >= 0 && index < this.idToValues.length ? this.idToValues[index] : null;
   }

   private int getIdFromIndex(int index) {
      return index == -1 ? -1 : this.ids[index];
   }

   public int add(K value) {
      int _snowman = this.nextId();
      this.put(value, _snowman);
      return _snowman;
   }

   private int nextId() {
      while (this.nextId < this.idToValues.length && this.idToValues[this.nextId] != null) {
         this.nextId++;
      }

      return this.nextId;
   }

   private void resize(int newSize) {
      K[] _snowman = this.values;
      int[] _snowmanx = this.ids;
      this.values = (K[])(new Object[newSize]);
      this.ids = new int[newSize];
      this.idToValues = (K[])(new Object[newSize]);
      this.nextId = 0;
      this.size = 0;

      for (int _snowmanxx = 0; _snowmanxx < _snowman.length; _snowmanxx++) {
         if (_snowman[_snowmanxx] != null) {
            this.put(_snowman[_snowmanxx], _snowmanx[_snowmanxx]);
         }
      }
   }

   public void put(K value, int id) {
      int _snowman = Math.max(id, this.size + 1);
      if ((float)_snowman >= (float)this.values.length * 0.8F) {
         int _snowmanx = this.values.length << 1;

         while (_snowmanx < id) {
            _snowmanx <<= 1;
         }

         this.resize(_snowmanx);
      }

      int _snowmanx = this.findFree(this.getIdealIndex(value));
      this.values[_snowmanx] = value;
      this.ids[_snowmanx] = id;
      this.idToValues[id] = value;
      this.size++;
      if (id == this.nextId) {
         this.nextId++;
      }
   }

   private int getIdealIndex(@Nullable K value) {
      return (MathHelper.idealHash(System.identityHashCode(value)) & 2147483647) % this.values.length;
   }

   private int findIndex(@Nullable K value, int id) {
      for (int _snowman = id; _snowman < this.values.length; _snowman++) {
         if (this.values[_snowman] == value) {
            return _snowman;
         }

         if (this.values[_snowman] == EMPTY) {
            return -1;
         }
      }

      for (int _snowman = 0; _snowman < id; _snowman++) {
         if (this.values[_snowman] == value) {
            return _snowman;
         }

         if (this.values[_snowman] == EMPTY) {
            return -1;
         }
      }

      return -1;
   }

   private int findFree(int size) {
      for (int _snowman = size; _snowman < this.values.length; _snowman++) {
         if (this.values[_snowman] == EMPTY) {
            return _snowman;
         }
      }

      for (int _snowmanx = 0; _snowmanx < size; _snowmanx++) {
         if (this.values[_snowmanx] == EMPTY) {
            return _snowmanx;
         }
      }

      throw new RuntimeException("Overflowed :(");
   }

   @Override
   public Iterator<K> iterator() {
      return Iterators.filter(Iterators.forArray(this.idToValues), Predicates.notNull());
   }

   public void clear() {
      Arrays.fill(this.values, null);
      Arrays.fill(this.idToValues, null);
      this.nextId = 0;
      this.size = 0;
   }

   public int size() {
      return this.size;
   }
}
