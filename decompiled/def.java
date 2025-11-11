import com.google.common.collect.ImmutableList;
import java.util.List;
import java.util.concurrent.ConcurrentLinkedQueue;

public class def {
   private final List<ConcurrentLinkedQueue<dee>> a = ImmutableList.of(
      new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue(), new ConcurrentLinkedQueue()
   );
   private volatile int b;
   private volatile int c;
   private volatile int d;

   public def() {
      this.b = this.c = this.d + 1;
   }
}
