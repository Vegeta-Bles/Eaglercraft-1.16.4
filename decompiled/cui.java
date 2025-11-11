import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import javax.annotation.Nullable;

public abstract class cui<M extends cui<M>> {
   private final long[] b = new long[2];
   private final cgb[] c = new cgb[2];
   private boolean d;
   protected final Long2ObjectOpenHashMap<cgb> a;

   protected cui(Long2ObjectOpenHashMap<cgb> var1) {
      this.a = _snowman;
      this.c();
      this.d = true;
   }

   public abstract M b();

   public void a(long var1) {
      this.a.put(_snowman, ((cgb)this.a.get(_snowman)).b());
      this.c();
   }

   public boolean b(long var1) {
      return this.a.containsKey(_snowman);
   }

   @Nullable
   public cgb c(long var1) {
      if (this.d) {
         for (int _snowman = 0; _snowman < 2; _snowman++) {
            if (_snowman == this.b[_snowman]) {
               return this.c[_snowman];
            }
         }
      }

      cgb _snowmanx = (cgb)this.a.get(_snowman);
      if (_snowmanx == null) {
         return null;
      } else {
         if (this.d) {
            for (int _snowmanxx = 1; _snowmanxx > 0; _snowmanxx--) {
               this.b[_snowmanxx] = this.b[_snowmanxx - 1];
               this.c[_snowmanxx] = this.c[_snowmanxx - 1];
            }

            this.b[0] = _snowman;
            this.c[0] = _snowmanx;
         }

         return _snowmanx;
      }
   }

   @Nullable
   public cgb d(long var1) {
      return (cgb)this.a.remove(_snowman);
   }

   public void a(long var1, cgb var3) {
      this.a.put(_snowman, _snowman);
   }

   public void c() {
      for (int _snowman = 0; _snowman < 2; _snowman++) {
         this.b[_snowman] = Long.MAX_VALUE;
         this.c[_snowman] = null;
      }
   }

   public void d() {
      this.d = false;
   }
}
