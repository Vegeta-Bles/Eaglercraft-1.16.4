import java.util.Random;

public class ccs extends cdd {
   private static final Random a = new Random();
   private gj<bmb> b = gj.a(9, bmb.b);

   protected ccs(cck<?> var1) {
      super(_snowman);
   }

   public ccs() {
      this(cck.f);
   }

   @Override
   public int Z_() {
      return 9;
   }

   public int h() {
      this.d(null);
      int _snowman = -1;
      int _snowmanx = 1;

      for (int _snowmanxx = 0; _snowmanxx < this.b.size(); _snowmanxx++) {
         if (!this.b.get(_snowmanxx).a() && a.nextInt(_snowmanx++) == 0) {
            _snowman = _snowmanxx;
         }
      }

      return _snowman;
   }

   public int a(bmb var1) {
      for (int _snowman = 0; _snowman < this.b.size(); _snowman++) {
         if (this.b.get(_snowman).a()) {
            this.a(_snowman, _snowman);
            return _snowman;
         }
      }

      return -1;
   }

   @Override
   protected nr g() {
      return new of("container.dispenser");
   }

   @Override
   public void a(ceh var1, md var2) {
      super.a(_snowman, _snowman);
      this.b = gj.a(this.Z_(), bmb.b);
      if (!this.b(_snowman)) {
         aoo.b(_snowman, this.b);
      }
   }

   @Override
   public md a(md var1) {
      super.a(_snowman);
      if (!this.c(_snowman)) {
         aoo.a(_snowman, this.b);
      }

      return _snowman;
   }

   @Override
   protected gj<bmb> f() {
      return this.b;
   }

   @Override
   protected void a(gj<bmb> var1) {
      this.b = _snowman;
   }

   @Override
   protected bic a(int var1, bfv var2) {
      return new bir(_snowman, _snowman, this);
   }
}
