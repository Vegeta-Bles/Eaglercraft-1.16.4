import com.google.common.collect.Sets;
import java.util.Iterator;
import java.util.Objects;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Consumer;
import java.util.stream.Stream;
import javax.annotation.Nullable;

public class enn {
   private final Set<enn.a> a = Sets.newIdentityHashSet();
   private final ddv b;
   private final Executor c;

   public enn(ddv var1, Executor var2) {
      this.b = _snowman;
      this.c = _snowman;
   }

   public CompletableFuture<enn.a> a(ddv.c var1) {
      CompletableFuture<enn.a> _snowman = new CompletableFuture<>();
      this.c.execute(() -> {
         ddu _snowmanx = this.b.a(_snowman);
         if (_snowmanx != null) {
            enn.a _snowmanx = new enn.a(_snowmanx);
            this.a.add(_snowmanx);
            _snowman.complete(_snowmanx);
         } else {
            _snowman.complete(null);
         }
      });
      return _snowman;
   }

   public void a(Consumer<Stream<ddu>> var1) {
      this.c.execute(() -> _snowman.accept(this.a.stream().map(var0 -> var0.b).filter(Objects::nonNull)));
   }

   public void a() {
      this.c.execute(() -> {
         Iterator<enn.a> _snowman = this.a.iterator();

         while (_snowman.hasNext()) {
            enn.a _snowmanx = _snowman.next();
            _snowmanx.b.i();
            if (_snowmanx.b.g()) {
               _snowmanx.b();
               _snowman.remove();
            }
         }
      });
   }

   public void b() {
      this.a.forEach(enn.a::b);
      this.a.clear();
   }

   public class a {
      @Nullable
      private ddu b;
      private boolean c;

      public boolean a() {
         return this.c;
      }

      public a(ddu var2) {
         this.b = _snowman;
      }

      public void a(Consumer<ddu> var1) {
         enn.this.c.execute(() -> {
            if (this.b != null) {
               _snowman.accept(this.b);
            }
         });
      }

      public void b() {
         this.c = true;
         enn.this.b.a(this.b);
         this.b = null;
      }
   }
}
