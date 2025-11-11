import com.mojang.serialization.Lifecycle;
import java.util.OptionalInt;

public abstract class gs<T> extends gm<T> {
   public gs(vj<? extends gm<T>> var1, Lifecycle var2) {
      super(_snowman, _snowman);
   }

   public abstract <V extends T> V a(int var1, vj<T> var2, V var3, Lifecycle var4);

   public abstract <V extends T> V a(vj<T> var1, V var2, Lifecycle var3);

   public abstract <V extends T> V a(OptionalInt var1, vj<T> var2, V var3, Lifecycle var4);
}
