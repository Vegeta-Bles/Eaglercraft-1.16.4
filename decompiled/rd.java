import com.google.common.collect.Lists;
import com.mojang.datafixers.util.Pair;
import java.io.IOException;
import java.util.List;

public class rd implements oj<om> {
   private int a;
   private final List<Pair<aqf, bmb>> b;

   public rd() {
      this.b = Lists.newArrayList();
   }

   public rd(int var1, List<Pair<aqf, bmb>> var2) {
      this.a = _snowman;
      this.b = _snowman;
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.i();
      aqf[] _snowman = aqf.values();

      int _snowmanx;
      do {
         _snowmanx = _snowman.readByte();
         aqf _snowmanxx = _snowman[_snowmanx & 127];
         bmb _snowmanxxx = _snowman.n();
         this.b.add(Pair.of(_snowmanxx, _snowmanxxx));
      } while ((_snowmanx & -128) != 0);
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.d(this.a);
      int _snowman = this.b.size();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         Pair<aqf, bmb> _snowmanxx = this.b.get(_snowmanx);
         aqf _snowmanxxx = (aqf)_snowmanxx.getFirst();
         boolean _snowmanxxxx = _snowmanx != _snowman - 1;
         int _snowmanxxxxx = _snowmanxxx.ordinal();
         _snowman.writeByte(_snowmanxxxx ? _snowmanxxxxx | -128 : _snowmanxxxxx);
         _snowman.a((bmb)_snowmanxx.getSecond());
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public int b() {
      return this.a;
   }

   public List<Pair<aqf, bmb>> c() {
      return this.b;
   }
}
