public abstract class dlv<E extends dlf.a<E>> extends dlf<E> {
   private boolean a;

   public dlv(djz var1, int var2, int var3, int var4, int var5, int var6) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean c_(boolean var1) {
      if (!this.a && this.l() == 0) {
         return false;
      } else {
         this.a = !this.a;
         if (this.a && this.h() == null && this.l() > 0) {
            this.a(dlf.b.b);
         } else if (this.a && this.h() != null) {
            this.p();
         }

         return this.a;
      }
   }

   public abstract static class a<E extends dlv.a<E>> extends dlf.a<E> {
      public a() {
      }

      @Override
      public boolean c_(boolean var1) {
         return false;
      }
   }
}
