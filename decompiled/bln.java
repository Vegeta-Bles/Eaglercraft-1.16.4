import java.util.List;
import javax.annotation.Nullable;

public class bln extends blx {
   public bln(blx.a var1) {
      super(_snowman);
   }

   @Override
   public void a(bmb var1, @Nullable brx var2, List<nr> var3, bnl var4) {
      md _snowman = _snowman.b("Explosion");
      if (_snowman != null) {
         a(_snowman, _snowman);
      }
   }

   public static void a(md var0, List<nr> var1) {
      blm.a _snowman = blm.a.a(_snowman.f("Type"));
      _snowman.add(new of("item.minecraft.firework_star.shape." + _snowman.b()).a(k.h));
      int[] _snowmanx = _snowman.n("Colors");
      if (_snowmanx.length > 0) {
         _snowman.add(a(new oe("").a(k.h), _snowmanx));
      }

      int[] _snowmanxx = _snowman.n("FadeColors");
      if (_snowmanxx.length > 0) {
         _snowman.add(a(new of("item.minecraft.firework_star.fade_to").c(" ").a(k.h), _snowmanxx));
      }

      if (_snowman.q("Trail")) {
         _snowman.add(new of("item.minecraft.firework_star.trail").a(k.h));
      }

      if (_snowman.q("Flicker")) {
         _snowman.add(new of("item.minecraft.firework_star.flicker").a(k.h));
      }
   }

   private static nr a(nx var0, int[] var1) {
      for (int _snowman = 0; _snowman < _snowman.length; _snowman++) {
         if (_snowman > 0) {
            _snowman.c(", ");
         }

         _snowman.a(a(_snowman[_snowman]));
      }

      return _snowman;
   }

   private static nr a(int var0) {
      bkx _snowman = bkx.b(_snowman);
      return _snowman == null ? new of("item.minecraft.firework_star.custom_color") : new of("item.minecraft.firework_star." + _snowman.c());
   }
}
