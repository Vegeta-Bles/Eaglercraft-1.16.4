import java.io.IOException;
import java.util.List;

public class pg implements oj<om> {
   private int a;
   private List<bmb> b;

   public pg() {
   }

   public pg(int var1, gj<bmb> var2) {
      this.a = _snowman;
      this.b = gj.a(_snowman.size(), bmb.b);

      for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
         this.b.set(_snowman, _snowman.get(_snowman).i());
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.readUnsignedByte();
      int _snowman = _snowman.readShort();
      this.b = gj.a(_snowman, bmb.b);

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         this.b.set(_snowmanx, _snowman.n());
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.writeByte(this.a);
      _snowman.writeShort(this.b.size());

      for (bmb _snowman : this.b) {
         _snowman.a(_snowman);
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public List<bmb> c() {
      return this.b;
   }
}
