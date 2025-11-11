import java.util.List;
import javax.annotation.Nullable;

public class bjs extends bja {
   private final brx g;
   @Nullable
   private bph h;
   private final List<bph> i;

   public bjs(int var1, bfv var2) {
      this(_snowman, _snowman, bim.a);
   }

   public bjs(int var1, bfv var2, bim var3) {
      super(bje.u, _snowman, _snowman, _snowman);
      this.g = _snowman.e.l;
      this.i = this.g.o().a(bot.g);
   }

   @Override
   protected boolean a(ceh var1) {
      return _snowman.a(bup.lZ);
   }

   @Override
   protected boolean b(bfw var1, boolean var2) {
      return this.h != null && this.h.a(this.d, this.g);
   }

   @Override
   protected bmb a(bfw var1, bmb var2) {
      _snowman.a(_snowman.l, _snowman, _snowman.E());
      this.c.b(_snowman);
      this.d(0);
      this.d(1);
      this.e.a((var0, var1x) -> var0.c(1044, var1x, 0));
      return _snowman;
   }

   private void d(int var1) {
      bmb _snowman = this.d.a(_snowman);
      _snowman.g(1);
      this.d.a(_snowman, _snowman);
   }

   @Override
   public void e() {
      List<bph> _snowman = this.g.o().b(bot.g, this.d, this.g);
      if (_snowman.isEmpty()) {
         this.c.a(0, bmb.b);
      } else {
         this.h = _snowman.get(0);
         bmb _snowmanx = this.h.a(this.d);
         this.c.a(this.h);
         this.c.a(0, _snowmanx);
      }
   }

   @Override
   protected boolean a(bmb var1) {
      return this.i.stream().anyMatch(var1x -> var1x.a(_snowman));
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return _snowman.c != this.c && super.a(_snowman, _snowman);
   }
}
