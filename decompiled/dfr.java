import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.stream.Collectors;

public class dfr {
   private final ImmutableList<dfs> a;
   private final IntList b = new IntArrayList();
   private final int c;

   public dfr(ImmutableList<dfs> var1) {
      this.a = _snowman;
      int _snowman = 0;
      UnmodifiableIterator var3 = _snowman.iterator();

      while (var3.hasNext()) {
         dfs _snowmanx = (dfs)var3.next();
         this.b.add(_snowman);
         _snowman += _snowmanx.d();
      }

      this.c = _snowman;
   }

   @Override
   public String toString() {
      return "format: " + this.a.size() + " elements: " + this.a.stream().map(Object::toString).collect(Collectors.joining(" "));
   }

   public int a() {
      return this.b() / 4;
   }

   public int b() {
      return this.c;
   }

   public ImmutableList<dfs> c() {
      return this.a;
   }

   @Override
   public boolean equals(Object var1) {
      if (this == _snowman) {
         return true;
      } else if (_snowman != null && this.getClass() == _snowman.getClass()) {
         dfr _snowman = (dfr)_snowman;
         return this.c != _snowman.c ? false : this.a.equals(_snowman.a);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.a.hashCode();
   }

   public void a(long var1) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.a(_snowman));
      } else {
         int _snowman = this.b();
         List<dfs> _snowmanx = this.c();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            _snowmanx.get(_snowmanxx).a(_snowman + (long)this.b.getInt(_snowmanxx), _snowman);
         }
      }
   }

   public void d() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::d);
      } else {
         UnmodifiableIterator var1 = this.c().iterator();

         while (var1.hasNext()) {
            dfs _snowman = (dfs)var1.next();
            _snowman.e();
         }
      }
   }
}
