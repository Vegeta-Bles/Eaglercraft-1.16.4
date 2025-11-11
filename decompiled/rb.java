import java.io.IOException;
import javax.annotation.Nullable;

public class rb implements oj<om> {
   private int a;
   private int b;

   public rb() {
   }

   public rb(aqa var1, @Nullable aqa var2) {
      this.a = _snowman.Y();
      this.b = _snowman != null ? _snowman.Y() : 0;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readInt();
      this.b = _snowman.readInt();
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeInt(this.a);
      _snowman.writeInt(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public int c() {
      return this.b;
   }
}
