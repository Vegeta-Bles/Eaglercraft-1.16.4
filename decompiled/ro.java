import java.io.IOException;
import javax.annotation.Nullable;

public class ro implements oj<om> {
   private vk a;
   private adr b;

   public ro() {
   }

   public ro(@Nullable vk var1, @Nullable adr var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      int _snowman = _snowman.readByte();
      if ((_snowman & 1) > 0) {
         this.b = _snowman.a(adr.class);
      }

      if ((_snowman & 2) > 0) {
         this.a = _snowman.p();
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      if (this.b != null) {
         if (this.a != null) {
            _snowman.writeByte(3);
            _snowman.a(this.b);
            _snowman.a(this.a);
         } else {
            _snowman.writeByte(1);
            _snowman.a(this.b);
         }
      } else if (this.a != null) {
         _snowman.writeByte(2);
         _snowman.a(this.a);
      } else {
         _snowman.writeByte(0);
      }
   }

   @Nullable
   public vk b() {
      return this.a;
   }

   @Nullable
   public adr c() {
      return this.b;
   }

   public void a(om var1) {
      _snowman.a(this);
   }
}
