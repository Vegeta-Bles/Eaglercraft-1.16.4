package net.minecraft.client.search;

import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;
import net.minecraft.util.Identifier;

public class TextSearchableContainer<T> extends IdentifierSearchableContainer<T> {
   protected SuffixArray<T> byText = new SuffixArray<>();
   private final Function<T, Stream<String>> textFinder;

   public TextSearchableContainer(Function<T, Stream<String>> _snowman, Function<T, Stream<Identifier>> _snowman) {
      super(_snowman);
      this.textFinder = _snowman;
   }

   @Override
   public void reload() {
      this.byText = new SuffixArray<>();
      super.reload();
      this.byText.build();
   }

   @Override
   protected void index(T _snowman) {
      super.index(_snowman);
      this.textFinder.apply(_snowman).forEach(_snowmanx -> this.byText.add(_snowman, _snowmanx.toLowerCase(Locale.ROOT)));
   }

   @Override
   public List<T> findAll(String text) {
      int _snowman = text.indexOf(58);
      if (_snowman < 0) {
         return this.byText.findAll(text);
      } else {
         List<T> _snowmanx = this.byNamespace.findAll(text.substring(0, _snowman).trim());
         String _snowmanxx = text.substring(_snowman + 1).trim();
         List<T> _snowmanxxx = this.byPath.findAll(_snowmanxx);
         List<T> _snowmanxxxx = this.byText.findAll(_snowmanxx);
         return Lists.newArrayList(
            new IdentifierSearchableContainer.Iterator<T>(
               _snowmanx.iterator(), new TextSearchableContainer.Iterator<T>(_snowmanxxx.iterator(), _snowmanxxxx.iterator(), this::compare), this::compare
            )
         );
      }
   }

   static class Iterator<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> field_5499;
      private final PeekingIterator<T> field_5500;
      private final Comparator<T> field_5501;

      public Iterator(java.util.Iterator<T> _snowman, java.util.Iterator<T> _snowman, Comparator<T> _snowman) {
         this.field_5499 = Iterators.peekingIterator(_snowman);
         this.field_5500 = Iterators.peekingIterator(_snowman);
         this.field_5501 = _snowman;
      }

      protected T computeNext() {
         boolean _snowman = !this.field_5499.hasNext();
         boolean _snowmanx = !this.field_5500.hasNext();
         if (_snowman && _snowmanx) {
            return (T)this.endOfData();
         } else if (_snowman) {
            return (T)this.field_5500.next();
         } else if (_snowmanx) {
            return (T)this.field_5499.next();
         } else {
            int _snowmanxx = this.field_5501.compare((T)this.field_5499.peek(), (T)this.field_5500.peek());
            if (_snowmanxx == 0) {
               this.field_5500.next();
            }

            return (T)(_snowmanxx <= 0 ? this.field_5499.next() : this.field_5500.next());
         }
      }
   }
}
