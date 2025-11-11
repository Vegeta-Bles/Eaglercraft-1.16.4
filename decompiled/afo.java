import com.google.common.collect.Lists;
import java.util.List;
import java.util.Spliterator;
import java.util.Spliterators.AbstractSpliterator;
import java.util.function.Consumer;
import java.util.stream.Stream;
import java.util.stream.StreamSupport;

public class afo<T> {
   private final List<T> a = Lists.newArrayList();
   private final Spliterator<T> b;

   public afo(Stream<T> var1) {
      this.b = _snowman.spliterator();
   }

   public Stream<T> a() {
      return StreamSupport.stream(new AbstractSpliterator<T>(Long.MAX_VALUE, 0) {
         private int b;

         @Override
         public boolean tryAdvance(Consumer<? super T> var1) {
            while (this.b >= afo.this.a.size()) {
               if (!afo.this.b.tryAdvance(afo.this.a::add)) {
                  return false;
               }
            }

            _snowman.accept(afo.this.a.get(this.b++));
            return true;
         }
      }, false);
   }
}
