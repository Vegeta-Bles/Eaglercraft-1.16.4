import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

public class ena<T> extends emz<T> {
   protected end<T> c = new end<>();
   private final Function<T, Stream<String>> d;

   public ena(Function<T, Stream<String>> var1, Function<T, Stream<vk>> var2) {
      super(_snowman);
      this.d = _snowman;
   }

   @Override
   public void b() {
      this.c = new end<>();
      super.b();
      this.c.a();
   }

   @Override
   protected void b(T var1) {
      super.b(_snowman);
      this.d.apply(_snowman).forEach(var2 -> this.c.a(_snowman, var2.toLowerCase(Locale.ROOT)));
   }

   @Override
   public List<T> a(String var1) {
      int _snowman = _snowman.indexOf(58);
      if (_snowman < 0) {
         return this.c.a(_snowman);
      } else {
         List<T> _snowmanx = this.a.a(_snowman.substring(0, _snowman).trim());
         String _snowmanxx = _snowman.substring(_snowman + 1).trim();
         List<T> _snowmanxxx = this.b.a(_snowmanxx);
         List<T> _snowmanxxxx = this.c.a(_snowmanxx);
         return Lists.newArrayList(new emz.a<T>(_snowmanx.iterator(), new ena.a<T>(_snowmanxxx.iterator(), _snowmanxxxx.iterator(), this::a), this::a));
      }
   }

   static class a<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> a;
      private final PeekingIterator<T> b;
      private final Comparator<T> c;

      public a(Iterator<T> var1, Iterator<T> var2, Comparator<T> var3) {
         this.a = Iterators.peekingIterator(_snowman);
         this.b = Iterators.peekingIterator(_snowman);
         this.c = _snowman;
      }

      protected T computeNext() {
         boolean _snowman = !this.a.hasNext();
         boolean _snowmanx = !this.b.hasNext();
         if (_snowman && _snowmanx) {
            return (T)this.endOfData();
         } else if (_snowman) {
            return (T)this.b.next();
         } else if (_snowmanx) {
            return (T)this.a.next();
         } else {
            int _snowmanxx = this.c.compare((T)this.a.peek(), (T)this.b.peek());
            if (_snowmanxx == 0) {
               this.b.next();
            }

            return (T)(_snowmanxx <= 0 ? this.a.next() : this.b.next());
         }
      }
   }
}
