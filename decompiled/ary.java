import com.google.common.collect.ImmutableMap;
import com.google.common.collect.Lists;
import java.util.List;
import java.util.Random;
import javax.annotation.Nullable;

public class ary extends arv<bfj> {
   @Nullable
   private bhb b;

   public ary(int var1, int var2) {
      super(ImmutableMap.of(), _snowman, _snowman);
   }

   protected boolean a(aag var1, bfj var2) {
      fx _snowman = _snowman.cB();
      this.b = _snowman.b_(_snowman);
      return this.b != null && this.b.e() && asx.a(_snowman, _snowman, _snowman);
   }

   protected boolean a(aag var1, bfj var2, long var3) {
      return this.b != null && !this.b.d();
   }

   protected void b(aag var1, bfj var2, long var3) {
      this.b = null;
      _snowman.cJ().a(_snowman.U(), _snowman.T());
   }

   protected void c(aag var1, bfj var2, long var3) {
      Random _snowman = _snowman.cY();
      if (_snowman.nextInt(100) == 0) {
         _snowman.eR();
      }

      if (_snowman.nextInt(200) == 0 && asx.a(_snowman, _snowman, _snowman.cB())) {
         bkx _snowmanx = x.a(bkx.values(), _snowman);
         int _snowmanxx = _snowman.nextInt(3);
         bmb _snowmanxxx = this.a(_snowmanx, _snowmanxx);
         bgh _snowmanxxxx = new bgh(_snowman.l, _snowman, _snowman.cD(), _snowman.cG(), _snowman.cH(), _snowmanxxx);
         _snowman.l.c(_snowmanxxxx);
      }
   }

   private bmb a(bkx var1, int var2) {
      bmb _snowman = new bmb(bmd.po, 1);
      bmb _snowmanx = new bmb(bmd.pp);
      md _snowmanxx = _snowmanx.a("Explosion");
      List<Integer> _snowmanxxx = Lists.newArrayList();
      _snowmanxxx.add(_snowman.g());
      _snowmanxx.b("Colors", _snowmanxxx);
      _snowmanxx.a("Type", (byte)blm.a.e.a());
      md _snowmanxxxx = _snowman.a("Fireworks");
      mj _snowmanxxxxx = new mj();
      md _snowmanxxxxxx = _snowmanx.b("Explosion");
      if (_snowmanxxxxxx != null) {
         _snowmanxxxxx.add(_snowmanxxxxxx);
      }

      _snowmanxxxx.a("Flight", (byte)_snowman);
      if (!_snowmanxxxxx.isEmpty()) {
         _snowmanxxxx.a("Explosions", _snowmanxxxxx);
      }

      return _snowman;
   }
}
