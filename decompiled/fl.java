import com.google.gson.JsonObject;
import com.mojang.brigadier.arguments.ArgumentType;
import java.util.function.Supplier;

public class fl<T extends ArgumentType<?>> implements fj<T> {
   private final Supplier<T> a;

   public fl(Supplier<T> var1) {
      this.a = _snowman;
   }

   @Override
   public void a(T var1, nf var2) {
   }

   @Override
   public T b(nf var1) {
      return this.a.get();
   }

   @Override
   public void a(T var1, JsonObject var2) {
   }
}
