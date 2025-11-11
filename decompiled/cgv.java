import com.google.common.collect.Maps;
import com.mojang.datafixers.util.Either;
import java.io.File;
import java.io.IOException;
import java.util.Iterator;
import java.util.Map;
import java.util.Map.Entry;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionException;
import java.util.concurrent.atomic.AtomicBoolean;
import java.util.function.Function;
import java.util.function.Supplier;
import javax.annotation.Nullable;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class cgv implements AutoCloseable {
   private static final Logger a = LogManager.getLogger();
   private final AtomicBoolean b = new AtomicBoolean();
   private final aoe<aog.b> c;
   private final cgz d;
   private final Map<brd, cgv.a> e = Maps.newLinkedHashMap();

   protected cgv(File var1, boolean var2, String var3) {
      this.d = new cgz(_snowman, _snowman);
      this.c = new aoe<>(new aog.a(cgv.b.values().length), x.g(), "IOWorker-" + _snowman);
   }

   public CompletableFuture<Void> a(brd var1, md var2) {
      return this.a(() -> {
         cgv.a _snowman = this.e.computeIfAbsent(_snowman, var1x -> new cgv.a(_snowman));
         _snowman.a = _snowman;
         return Either.left(_snowman.b);
      }).thenCompose(Function.identity());
   }

   @Nullable
   public md a(brd var1) throws IOException {
      CompletableFuture<md> _snowman = this.a(() -> {
         cgv.a _snowmanx = this.e.get(_snowman);
         if (_snowmanx != null) {
            return Either.left(_snowmanx.a);
         } else {
            try {
               md _snowmanx = this.d.a(_snowman);
               return Either.left(_snowmanx);
            } catch (Exception var4x) {
               a.warn("Failed to read chunk {}", _snowman, var4x);
               return Either.right(var4x);
            }
         }
      });

      try {
         return _snowman.join();
      } catch (CompletionException var4) {
         if (var4.getCause() instanceof IOException) {
            throw (IOException)var4.getCause();
         } else {
            throw var4;
         }
      }
   }

   public CompletableFuture<Void> a() {
      CompletableFuture<Void> _snowman = this.a(
            () -> Either.left(CompletableFuture.allOf(this.e.values().stream().map(var0 -> var0.b).toArray(CompletableFuture[]::new)))
         )
         .thenCompose(Function.identity());
      return _snowman.thenCompose(var1x -> this.a(() -> {
            try {
               this.d.a();
               return Either.left(null);
            } catch (Exception var2) {
               a.warn("Failed to synchronized chunks", var2);
               return Either.right(var2);
            }
         }));
   }

   private <T> CompletableFuture<T> a(Supplier<Either<T, Exception>> var1) {
      return this.c.c(var2 -> new aog.b(cgv.b.a.ordinal(), () -> {
            if (!this.b.get()) {
               var2.a(_snowman.get());
            }

            this.c();
         }));
   }

   private void b() {
      Iterator<Entry<brd, cgv.a>> _snowman = this.e.entrySet().iterator();
      if (_snowman.hasNext()) {
         Entry<brd, cgv.a> _snowmanx = _snowman.next();
         _snowman.remove();
         this.a(_snowmanx.getKey(), _snowmanx.getValue());
         this.c();
      }
   }

   private void c() {
      this.c.a(new aog.b(cgv.b.b.ordinal(), this::b));
   }

   private void a(brd var1, cgv.a var2) {
      try {
         this.d.a(_snowman, _snowman.a);
         _snowman.b.complete(null);
      } catch (Exception var4) {
         a.error("Failed to store chunk {}", _snowman, var4);
         _snowman.b.completeExceptionally(var4);
      }
   }

   @Override
   public void close() throws IOException {
      if (this.b.compareAndSet(false, true)) {
         CompletableFuture<afx> _snowman = this.c.b(var0 -> new aog.b(cgv.b.a.ordinal(), () -> var0.a(afx.a)));

         try {
            _snowman.join();
         } catch (CompletionException var4) {
            if (var4.getCause() instanceof IOException) {
               throw (IOException)var4.getCause();
            }

            throw var4;
         }

         this.c.close();
         this.e.forEach(this::a);
         this.e.clear();

         try {
            this.d.close();
         } catch (Exception var3) {
            a.error("Failed to close storage", var3);
         }
      }
   }

   static class a {
      private md a;
      private final CompletableFuture<Void> b = new CompletableFuture<>();

      public a(md var1) {
         this.a = _snowman;
      }
   }

   static enum b {
      a,
      b;

      private b() {
      }
   }
}
