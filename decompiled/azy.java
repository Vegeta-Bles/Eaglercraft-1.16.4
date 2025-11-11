import java.util.List;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public abstract class azy extends azw {
   private azy b;
   private int c = 1;

   public azy(aqe<? extends azy> var1, brx var2) {
      super(_snowman, _snowman);
   }

   @Override
   protected void o() {
      super.o();
      this.bk.a(5, new avr(this));
   }

   @Override
   public int eq() {
      return this.eN();
   }

   public int eN() {
      return super.eq();
   }

   @Override
   protected boolean eL() {
      return !this.eO();
   }

   public boolean eO() {
      return this.b != null && this.b.aX();
   }

   public azy a(azy var1) {
      this.b = _snowman;
      _snowman.eU();
      return _snowman;
   }

   public void eP() {
      this.b.eV();
      this.b = null;
   }

   private void eU() {
      this.c++;
   }

   private void eV() {
      this.c--;
   }

   public boolean eQ() {
      return this.eR() && this.c < this.eN();
   }

   @Override
   public void j() {
      super.j();
      if (this.eR() && this.l.t.nextInt(200) == 1) {
         List<azw> _snowman = this.l.a((Class<? extends azw>)this.getClass(), this.cc().c(8.0, 8.0, 8.0));
         if (_snowman.size() <= 1) {
            this.c = 1;
         }
      }
   }

   public boolean eR() {
      return this.c > 1;
   }

   public boolean eS() {
      return this.h(this.b) <= 121.0;
   }

   public void eT() {
      if (this.eO()) {
         this.x().a(this.b, 1.0);
      }
   }

   public void a(Stream<azy> var1) {
      _snowman.limit((long)(this.eN() - this.c)).filter(var1x -> var1x != this).forEach(var1x -> var1x.a(this));
   }

   @Nullable
   @Override
   public arc a(bsk var1, aos var2, aqp var3, @Nullable arc var4, @Nullable md var5) {
      super.a(_snowman, _snowman, _snowman, _snowman, _snowman);
      if (_snowman == null) {
         _snowman = new azy.a(this);
      } else {
         this.a(((azy.a)_snowman).a);
      }

      return _snowman;
   }

   public static class a implements arc {
      public final azy a;

      public a(azy var1) {
         this.a = _snowman;
      }
   }
}
