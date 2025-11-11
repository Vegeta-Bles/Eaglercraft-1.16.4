import java.io.IOException;
import javax.annotation.Nullable;

public class qs implements oj<om> {
   @Nullable
   private vk a;

   public qs() {
   }

   public qs(@Nullable vk var1) {
      this.a = _snowman;
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Override
   public void a(nf var1) throws IOException {
      if (_snowman.readBoolean()) {
         this.a = _snowman.p();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeBoolean(this.a != null);
      if (this.a != null) {
         _snowman.a(this.a);
      }
   }

   @Nullable
   public vk b() {
      return this.a;
   }
}
