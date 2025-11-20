package net.minecraft.client.search;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;

public class IdentifierSearchableContainer<T> implements SearchableContainer<T> {
   protected SuffixArray<T> byNamespace = new SuffixArray<>();
   protected SuffixArray<T> byPath = new SuffixArray<>();
   private final Function<T, Stream<Identifier>> identifierFinder;
   private final List<T> entries = Lists.newArrayList();
   private final Object2IntMap<T> entryIds = new Object2IntOpenHashMap();

   public IdentifierSearchableContainer(Function<T, Stream<Identifier>> _snowman) {
      this.identifierFinder = _snowman;
   }

   @Override
   public void reload() {
      this.byNamespace = new SuffixArray<>();
      this.byPath = new SuffixArray<>();

      for (T _snowman : this.entries) {
         this.index(_snowman);
      }

      this.byNamespace.build();
      this.byPath.build();
   }

   @Override
   public void add(T _snowman) {
      this.entryIds.put(_snowman, this.entries.size());
      this.entries.add(_snowman);
      this.index(_snowman);
   }

   @Override
   public void clear() {
      this.entries.clear();
      this.entryIds.clear();
   }

   protected void index(T _snowman) {
      this.identifierFinder.apply(_snowman).forEach(_snowmanx -> {
         this.byNamespace.add(_snowman, _snowmanx.getNamespace().toLowerCase(Locale.ROOT));
         this.byPath.add(_snowman, _snowmanx.getPath().toLowerCase(Locale.ROOT));
      });
   }

   protected int compare(T object1, T object2) {
      return Integer.compare(this.entryIds.getInt(object1), this.entryIds.getInt(object2));
   }

   @Override
   public List<T> findAll(String text) {
      int _snowman = text.indexOf(58);
      if (_snowman == -1) {
         return this.byPath.findAll(text);
      } else {
         List<T> _snowmanx = this.byNamespace.findAll(text.substring(0, _snowman).trim());
         String _snowmanxx = text.substring(_snowman + 1).trim();
         List<T> _snowmanxxx = this.byPath.findAll(_snowmanxx);
         return Lists.newArrayList(new IdentifierSearchableContainer.Iterator<T>(_snowmanx.iterator(), _snowmanxxx.iterator(), this::compare));
      }
   }

   public static class Iterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> field_5490;
      private final PeekingIterator<T> field_5491;
      private final Comparator<T> field_5492;

      public Iterator(java.util.Iterator<T> _snowman, java.util.Iterator<T> _snowman, Comparator<T> _snowman) {
         this.field_5490 = Iterators.peekingIterator(_snowman);
         this.field_5491 = Iterators.peekingIterator(_snowman);
         this.field_5492 = _snowman;
      }

      protected T computeNext() {
         while (this.field_5490.hasNext() && this.field_5491.hasNext()) {
            int _snowman = this.field_5492.compare((T)this.field_5490.peek(), (T)this.field_5491.peek());
            if (_snowman == 0) {
               this.field_5491.next();
               return (T)this.field_5490.next();
            }

            if (_snowman < 0) {
               this.field_5490.next();
            } else {
               this.field_5491.next();
            }
         }

         return (T)this.endOfData();
      }
   }
}
