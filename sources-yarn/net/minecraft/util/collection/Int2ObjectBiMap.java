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
      int i = this.nextId();
      this.put(value, i);
      return i;
   }

   private int nextId() {
      while (this.nextId < this.idToValues.length && this.idToValues[this.nextId] != null) {
         this.nextId++;
      }

      return this.nextId;
   }

   private void resize(int newSize) {
      K[] objects = this.values;
      int[] is = this.ids;
      this.values = (K[])(new Object[newSize]);
      this.ids = new int[newSize];
      this.idToValues = (K[])(new Object[newSize]);
      this.nextId = 0;
      this.size = 0;

      for (int j = 0; j < objects.length; j++) {
         if (objects[j] != null) {
            this.put(objects[j], is[j]);
         }
      }
   }

   public void put(K value, int id) {
      int j = Math.max(id, this.size + 1);
      if ((float)j >= (float)this.values.length * 0.8F) {
         int k = this.values.length << 1;

         while (k < id) {
            k <<= 1;
         }

         this.resize(k);
      }

      int l = this.findFree(this.getIdealIndex(value));
      this.values[l] = value;
      this.ids[l] = id;
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
      for (int j = id; j < this.values.length; j++) {
         if (this.values[j] == value) {
            return j;
         }

         if (this.values[j] == EMPTY) {
            return -1;
         }
      }

      for (int k = 0; k < id; k++) {
         if (this.values[k] == value) {
            return k;
         }

         if (this.values[k] == EMPTY) {
            return -1;
         }
      }

      return -1;
   }

   private int findFree(int size) {
      for (int j = size; j < this.values.length; j++) {
         if (this.values[j] == EMPTY) {
            return j;
         }
      }

      for (int k = 0; k < size; k++) {
         if (this.values[k] == EMPTY) {
            return k;
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
