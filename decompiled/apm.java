import javax.annotation.Nullable;

public class apm extends apl {
   private final aqa x;

   public apm(String var1, aqa var2, @Nullable aqa var3) {
      super(_snowman, _snowman);
      this.x = _snowman;
   }

   @Nullable
   @Override
   public aqa j() {
      return this.w;
   }

   @Nullable
   @Override
   public aqa k() {
      return this.x;
   }

   @Override
   public nr a(aqm var1) {
      nr _snowman = this.x == null ? this.w.d() : this.x.d();
      bmb _snowmanx = this.x instanceof aqm ? ((aqm)this.x).dD() : bmb.b;
      String _snowmanxx = "death.attack." + this.v;
      String _snowmanxxx = _snowmanxx + ".item";
      return !_snowmanx.a() && _snowmanx.t() ? new of(_snowmanxxx, _snowman.d(), _snowman, _snowmanx.C()) : new of(_snowmanxx, _snowman.d(), _snowman);
   }
}
