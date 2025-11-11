import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.Collection;
import java.util.Collections;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.Executor;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.util.Supplier;

public class acm implements acf {
   private static final Logger a = LogManager.getLogger();
   private final Map<String, acb> b = Maps.newHashMap();
   private final List<acc> c = Lists.newArrayList();
   private final List<acc> d = Lists.newArrayList();
   private final Set<String> e = Sets.newLinkedHashSet();
   private final List<abj> f = Lists.newArrayList();
   private final abk g;

   public acm(abk var1) {
      this.g = _snowman;
   }

   public void a(abj var1) {
      this.f.add(_snowman);

      for (String _snowman : _snowman.a(this.g)) {
         this.e.add(_snowman);
         acb _snowmanx = this.b.get(_snowman);
         if (_snowmanx == null) {
            _snowmanx = new acb(this.g, _snowman);
            this.b.put(_snowman, _snowmanx);
         }

         _snowmanx.a(_snowman);
      }
   }

   @Override
   public Set<String> a() {
      return this.e;
   }

   @Override
   public acg a(vk var1) throws IOException {
      ach _snowman = this.b.get(_snowman.b());
      if (_snowman != null) {
         return _snowman.a(_snowman);
      } else {
         throw new FileNotFoundException(_snowman.toString());
      }
   }

   @Override
   public boolean b(vk var1) {
      ach _snowman = this.b.get(_snowman.b());
      return _snowman != null ? _snowman.b(_snowman) : false;
   }

   @Override
   public List<acg> c(vk var1) throws IOException {
      ach _snowman = this.b.get(_snowman.b());
      if (_snowman != null) {
         return _snowman.c(_snowman);
      } else {
         throw new FileNotFoundException(_snowman.toString());
      }
   }

   @Override
   public Collection<vk> a(String var1, Predicate<String> var2) {
      Set<vk> _snowman = Sets.newHashSet();

      for (acb _snowmanx : this.b.values()) {
         _snowman.addAll(_snowmanx.a(_snowman, _snowman));
      }

      List<vk> _snowmanx = Lists.newArrayList(_snowman);
      Collections.sort(_snowmanx);
      return _snowmanx;
   }

   private void c() {
      this.b.clear();
      this.e.clear();
      this.f.forEach(abj::close);
      this.f.clear();
   }

   @Override
   public void close() {
      this.c();
   }

   @Override
   public void a(acc var1) {
      this.c.add(_snowman);
      this.d.add(_snowman);
   }

   protected ace b(Executor var1, Executor var2, List<acc> var3, CompletableFuture<afx> var4) {
      ace _snowman;
      if (a.isDebugEnabled()) {
         _snowman = new acd(this, Lists.newArrayList(_snowman), _snowman, _snowman, _snowman);
      } else {
         _snowman = acl.a(this, Lists.newArrayList(_snowman), _snowman, _snowman, _snowman);
      }

      this.d.clear();
      return _snowman;
   }

   @Override
   public ace a(Executor var1, Executor var2, CompletableFuture<afx> var3, List<abj> var4) {
      this.c();
      a.info("Reloading ResourceManager: {}", new Supplier[]{() -> _snowman.stream().map(abj::a).collect(Collectors.joining(", "))});

      for (abj _snowman : _snowman) {
         try {
            this.a(_snowman);
         } catch (Exception var8) {
            a.error("Failed to add resource pack {}", _snowman.a(), var8);
            return new acm.a(new acm.b(_snowman, var8));
         }
      }

      return this.b(_snowman, _snowman, this.c, _snowman);
   }

   @Override
   public Stream<abj> b() {
      return this.f.stream();
   }

   static class a implements ace {
      private final acm.b a;
      private final CompletableFuture<afx> b;

      public a(acm.b var1) {
         this.a = _snowman;
         this.b = new CompletableFuture<>();
         this.b.completeExceptionally(_snowman);
      }

      @Override
      public CompletableFuture<afx> a() {
         return this.b;
      }

      @Override
      public float b() {
         return 0.0F;
      }

      @Override
      public boolean c() {
         return false;
      }

      @Override
      public boolean d() {
         return true;
      }

      @Override
      public void e() {
         throw this.a;
      }
   }

   public static class b extends RuntimeException {
      private final abj a;

      public b(abj var1, Throwable var2) {
         super(_snowman.a(), _snowman);
         this.a = _snowman;
      }

      public abj a() {
         return this.a;
      }
   }
}
