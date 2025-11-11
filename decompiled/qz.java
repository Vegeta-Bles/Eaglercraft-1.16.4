import java.io.IOException;
import java.util.Objects;
import javax.annotation.Nullable;

public class qz implements oj<om> {
   private int a;
   private String b;

   public qz() {
   }

   public qz(int var1, @Nullable ddk var2) {
      this.a = _snowman;
      if (_snowman == null) {
         this.b = "";
      } else {
         this.b = _snowman.b();
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readByte();
      this.b = _snowman.e(16);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.a(this.b);
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   @Nullable
   public String c() {
      return Objects.equals(this.b, "") ? null : this.b;
   }
}
