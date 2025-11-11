import com.mojang.serialization.Lifecycle;
import java.util.Optional;
import java.util.Random;
import javax.annotation.Nonnull;
import javax.annotation.Nullable;

public class gb<T> extends gi<T> {
   private final vk bf;
   private T bg;

   public gb(String var1, vj<? extends gm<T>> var2, Lifecycle var3) {
      super(_snowman, _snowman);
      this.bf = new vk(_snowman);
   }

   @Override
   public <V extends T> V a(int var1, vj<T> var2, V var3, Lifecycle var4) {
      if (this.bf.equals(_snowman.a())) {
         this.bg = (T)_snowman;
      }

      return super.a(_snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public int a(@Nullable T var1) {
      int _snowman = super.a(_snowman);
      return _snowman == -1 ? super.a(this.bg) : _snowman;
   }

   @Nonnull
   @Override
   public vk b(T var1) {
      vk _snowman = super.b(_snowman);
      return _snowman == null ? this.bf : _snowman;
   }

   @Nonnull
   @Override
   public T a(@Nullable vk var1) {
      T _snowman = super.a(_snowman);
      return _snowman == null ? this.bg : _snowman;
   }

   @Override
   public Optional<T> b(@Nullable vk var1) {
      return Optional.ofNullable(super.a(_snowman));
   }

   @Nonnull
   @Override
   public T a(int var1) {
      T _snowman = super.a(_snowman);
      return _snowman == null ? this.bg : _snowman;
   }

   @Nonnull
   @Override
   public T a(Random var1) {
      T _snowman = super.a(_snowman);
      return _snowman == null ? this.bg : _snowman;
   }

   public vk a() {
      return this.bf;
   }
}
