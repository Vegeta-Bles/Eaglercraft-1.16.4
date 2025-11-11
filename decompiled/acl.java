import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.concurrent.Executor;
import java.util.concurrent.atomic.AtomicInteger;

public class acl<S> implements ace {
   protected final ach a;
   protected final CompletableFuture<afx> b = new CompletableFuture<>();
   protected final CompletableFuture<List<S>> c;
   private final Set<acc> d;
   private final int e;
   private int f;
   private int g;
   private final AtomicInteger h = new AtomicInteger();
   private final AtomicInteger i = new AtomicInteger();

   public static acl<Void> a(ach var0, List<acc> var1, Executor var2, Executor var3, CompletableFuture<afx> var4) {
      return new acl<>(_snowman, _snowman, _snowman, _snowman, (var1x, var2x, var3x, var4x, var5) -> var3x.a(var1x, var2x, ant.a, ant.a, _snowman, var5), _snowman);
   }

   protected acl(Executor var1, final Executor var2, ach var3, List<acc> var4, acl.a<S> var5, CompletableFuture<afx> var6) {
      this.a = _snowman;
      this.e = _snowman.size();
      this.h.incrementAndGet();
      _snowman.thenRun(this.i::incrementAndGet);
      List<CompletableFuture<S>> _snowman = Lists.newArrayList();
      CompletableFuture<?> _snowmanx = _snowman;
      this.d = Sets.newHashSet(_snowman);

      for (final acc _snowmanxx : _snowman) {
         final CompletableFuture<?> _snowmanxxx = _snowmanx;
         CompletableFuture<S> _snowmanxxxx = _snowman.create(new acc.a() {
            @Override
            public <T> CompletableFuture<T> a(T var1) {
               _snowman.execute(() -> {
                  acl.this.d.remove(_snowman);
                  if (acl.this.d.isEmpty()) {
                     acl.this.b.complete(afx.a);
                  }
               });
               return acl.this.b.thenCombine((CompletionStage<? extends T>)_snowman, (var1x, var2x) -> _snowman);
            }
         }, _snowman, _snowmanxx, var2x -> {
            this.h.incrementAndGet();
            _snowman.execute(() -> {
               var2x.run();
               this.i.incrementAndGet();
            });
         }, var2x -> {
            this.f++;
            _snowman.execute(() -> {
               var2x.run();
               this.g++;
            });
         });
         _snowman.add(_snowmanxxxx);
         _snowmanx = _snowmanxxxx;
      }

      this.c = x.b(_snowman);
   }

   @Override
   public CompletableFuture<afx> a() {
      return this.c.thenApply(var0 -> afx.a);
   }

   @Override
   public float b() {
      int _snowman = this.e - this.d.size();
      float _snowmanx = (float)(this.i.get() * 2 + this.g * 2 + _snowman * 1);
      float _snowmanxx = (float)(this.h.get() * 2 + this.f * 2 + this.e * 1);
      return _snowmanx / _snowmanxx;
   }

   @Override
   public boolean c() {
      return this.b.isDone();
   }

   @Override
   public boolean d() {
      return this.c.isDone();
   }

   @Override
   public void e() {
      if (this.c.isCompletedExceptionally()) {
         this.c.join();
      }
   }

   public interface a<S> {
      CompletableFuture<S> create(acc.a var1, ach var2, acc var3, Executor var4, Executor var5);
   }
}
