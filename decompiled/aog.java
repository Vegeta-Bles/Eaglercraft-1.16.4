import com.google.common.collect.Queues;
import java.util.Collection;
import java.util.List;
import java.util.Queue;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import javax.annotation.Nullable;

public interface aog<T, F> {
   @Nullable
   F a();

   boolean a(T var1);

   boolean b();

   public static final class a implements aog<aog.b, Runnable> {
      private final List<Queue<Runnable>> a;

      public a(int var1) {
         this.a = IntStream.range(0, _snowman).mapToObj(var0 -> Queues.newConcurrentLinkedQueue()).collect(Collectors.toList());
      }

      @Nullable
      public Runnable c() {
         for (Queue<Runnable> _snowman : this.a) {
            Runnable _snowmanx = _snowman.poll();
            if (_snowmanx != null) {
               return _snowmanx;
            }
         }

         return null;
      }

      public boolean a(aog.b var1) {
         int _snowman = _snowman.a();
         this.a.get(_snowman).add(_snowman);
         return true;
      }

      @Override
      public boolean b() {
         return this.a.stream().allMatch(Collection::isEmpty);
      }
   }

   public static final class b implements Runnable {
      private final int a;
      private final Runnable b;

      public b(int var1, Runnable var2) {
         this.a = _snowman;
         this.b = _snowman;
      }

      @Override
      public void run() {
         this.b.run();
      }

      public int a() {
         return this.a;
      }
   }

   public static final class c<T> implements aog<T, T> {
      private final Queue<T> a;

      public c(Queue<T> var1) {
         this.a = _snowman;
      }

      @Nullable
      @Override
      public T a() {
         return this.a.poll();
      }

      @Override
      public boolean a(T var1) {
         return this.a.add(_snowman);
      }

      @Override
      public boolean b() {
         return this.a.isEmpty();
      }
   }
}
