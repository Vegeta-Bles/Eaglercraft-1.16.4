import it.unimi.dsi.fastutil.ints.IntSet;
import java.io.Closeable;
import javax.annotation.Nullable;

public interface deb extends Closeable {
   @Override
   default void close() {
   }

   @Nullable
   default dec a(int var1) {
      return null;
   }

   IntSet a();
}
