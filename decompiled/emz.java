import com.google.common.collect.AbstractIterator;
import com.google.common.collect.Iterators;
import com.google.common.collect.Lists;
import com.google.common.collect.PeekingIterator;
import it.unimi.dsi.fastutil.objects.Object2IntMap;
import it.unimi.dsi.fastutil.objects.Object2IntOpenHashMap;
import java.util.Comparator;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.function.Function;
import java.util.stream.Stream;

public class emz<T> implements emy<T> {
   protected end<T> a = new end<>();
   protected end<T> b = new end<>();
   private final Function<T, Stream<vk>> c;
   private final List<T> d = Lists.newArrayList();
   private final Object2IntMap<T> e = new Object2IntOpenHashMap();

   public emz(Function<T, Stream<vk>> var1) {
      this.c = _snowman;
   }

   @Override
   public void b() {
      this.a = new end<>();
      this.b = new end<>();

      for (T _snowman : this.d) {
         this.b(_snowman);
      }

      this.a.a();
      this.b.a();
   }

   @Override
   public void a(T var1) {
      this.e.put(_snowman, this.d.size());
      this.d.add(_snowman);
      this.b(_snowman);
   }

   @Override
   public void a() {
      this.d.clear();
      this.e.clear();
   }

   protected void b(T var1) {
      this.c.apply(_snowman).forEach(var2 -> {
         this.a.a(_snowman, var2.b().toLowerCase(Locale.ROOT));
         this.b.a(_snowman, var2.a().toLowerCase(Locale.ROOT));
      });
   }

   protected int a(T var1, T var2) {
      return Integer.compare(this.e.getInt(_snowman), this.e.getInt(_snowman));
   }

   @Override
   public List<T> a(String var1) {
      int _snowman = _snowman.indexOf(58);
      if (_snowman == -1) {
         return this.b.a(_snowman);
      } else {
         List<T> _snowmanx = this.a.a(_snowman.substring(0, _snowman).trim());
         String _snowmanxx = _snowman.substring(_snowman + 1).trim();
         List<T> _snowmanxxx = this.b.a(_snowmanxx);
         return Lists.newArrayList(new emz.a<T>(_snowmanx.iterator(), _snowmanxxx.iterator(), this::a));
      }
   }

   public static class a<T> extends AbstractIterator<T> {
      private final PeekingIterator<T> a;
      private final PeekingIterator<T> b;
      private final Comparator<T> c;

      public a(Iterator<T> var1, Iterator<T> var2, Comparator<T> var3) {
         this.a = Iterators.peekingIterator(_snowman);
         this.b = Iterators.peekingIterator(_snowman);
         this.c = _snowman;
      }

      protected T computeNext() {
         while (this.a.hasNext() && this.b.hasNext()) {
            int _snowman = this.c.compare((T)this.a.peek(), (T)this.b.peek());
            if (_snowman == 0) {
               this.b.next();
               return (T)this.a.next();
            }

            if (_snowman < 0) {
               this.a.next();
            } else {
               this.b.next();
            }
         }

         return (T)this.endOfData();
      }
   }
}
