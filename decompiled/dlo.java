import javax.annotation.Nullable;

public abstract class dlo<E extends dlo.a<E>> extends dlf<E> {
   public dlo(djz var1, int var2, int var3, int var4, int var5, int var6) {
      super(_snowman, _snowman, _snowman, _snowman, _snowman, _snowman);
   }

   @Override
   public boolean c_(boolean var1) {
      boolean _snowman = super.c_(_snowman);
      if (_snowman) {
         this.d(this.i());
      }

      return _snowman;
   }

   @Override
   protected boolean f(int var1) {
      return false;
   }

   public abstract static class a<E extends dlo.a<E>> extends dlf.a<E> implements dmh {
      @Nullable
      private dmi a;
      private boolean b;

      public a() {
      }

      @Override
      public boolean av_() {
         return this.b;
      }

      @Override
      public void b_(boolean var1) {
         this.b = _snowman;
      }

      @Override
      public void a(@Nullable dmi var1) {
         this.a = _snowman;
      }

      @Nullable
      @Override
      public dmi aw_() {
         return this.a;
      }
   }
}
