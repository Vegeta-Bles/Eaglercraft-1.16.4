import java.io.IOException;
import javax.annotation.Nullable;

public class qn implements oj<om> {
   private int a;
   private aps b;

   public qn() {
   }

   public qn(int var1, aps var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      this.b = aps.a(_snowman.readUnsignedByte());
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      _snowman.writeByte(aps.a(this.b));
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   @Nullable
   public aqa a(brx var1) {
      return _snowman.a(this.a);
   }

   @Nullable
   public aps b() {
      return this.b;
   }
}
