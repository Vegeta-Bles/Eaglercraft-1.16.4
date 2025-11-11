import javax.annotation.Nullable;

public class apl extends apk {
   @Nullable
   protected final aqa w;
   private boolean x;

   public apl(String var1, @Nullable aqa var2) {
      super(_snowman);
      this.w = _snowman;
   }

   public apl x() {
      this.x = true;
      return this;
   }

   public boolean y() {
      return this.x;
   }

   @Nullable
   @Override
   public aqa k() {
      return this.w;
   }

   @Override
   public nr a(aqm var1) {
      bmb _snowman = this.w instanceof aqm ? ((aqm)this.w).dD() : bmb.b;
      String _snowmanx = "death.attack." + this.v;
      return !_snowman.a() && _snowman.t() ? new of(_snowmanx + ".item", _snowman.d(), this.w.d(), _snowman.C()) : new of(_snowmanx, _snowman.d(), this.w.d());
   }

   @Override
   public boolean s() {
      return this.w != null && this.w instanceof aqm && !(this.w instanceof bfw);
   }

   @Nullable
   @Override
   public dcn w() {
      return this.w != null ? this.w.cA() : null;
   }

   @Override
   public String toString() {
      return "EntityDamageSource (" + this.w + ")";
   }
}
