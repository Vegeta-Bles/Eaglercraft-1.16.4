import com.google.common.collect.ImmutableList;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.google.common.collect.ImmutableList.Builder;
import java.util.Comparator;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.Map.Entry;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class dsg extends dot {
   private final Consumer<Optional<brt>> a;
   private dsg.g b;
   private final Set<dsg.f> c = Sets.newHashSet();
   private dlj p;
   @Nullable
   private List<afa> q;
   private final brt r;

   public dsg(brt var1, Consumer<Optional<brt>> var2) {
      super(new of("editGamerule.title"));
      this.r = _snowman;
      this.a = _snowman;
   }

   @Override
   protected void b() {
      this.i.m.a(true);
      super.b();
      this.b = new dsg.g(this.r);
      this.e.add(this.b);
      this.a((dlj)(new dlj(this.k / 2 - 155 + 160, this.l - 29, 150, 20, nq.d, var1 -> this.a.accept(Optional.empty()))));
      this.p = this.a((dlj)(new dlj(this.k / 2 - 155, this.l - 29, 150, 20, nq.c, var1 -> this.a.accept(Optional.of(this.r)))));
   }

   @Override
   public void e() {
      this.i.m.a(false);
   }

   @Override
   public void at_() {
      this.a.accept(Optional.empty());
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.q = null;
      this.b.a(_snowman, _snowman, _snowman, _snowman);
      a(_snowman, this.o, this.d, this.k / 2, 20, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
      if (this.q != null) {
         this.c(_snowman, this.q, _snowman, _snowman);
      }
   }

   private void b(@Nullable List<afa> var1) {
      this.q = _snowman;
   }

   private void h() {
      this.p.o = this.c.isEmpty();
   }

   private void a(dsg.f var1) {
      this.c.add(_snowman);
      this.h();
   }

   private void b(dsg.f var1) {
      this.c.remove(_snowman);
      this.h();
   }

   public class a extends dsg.d {
      private final dlj e;

      public a(final nr var2, List<afa> var3, final String var4, final brt.a var5) {
         super(_snowman, _snowman);
         this.e = new dlj(10, 5, 44, 20, nq.a(_snowman.a()), var1x -> {
            boolean _snowman = !_snowman.a();
            _snowman.a(_snowman, null);
            var1x.a(nq.a(_snowman.a()));
         }) {
            @Override
            protected nx c() {
               return nq.a(_snowman, _snowman.a()).c("\n").c(_snowman);
            }
         };
         this.b.add(this.e);
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, _snowman, _snowman);
         this.e.l = _snowman + _snowman - 45;
         this.e.m = _snowman;
         this.e.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public class b extends dsg.f {
      private final nr b;

      public b(nr var2) {
         super(null);
         this.b = _snowman;
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         dkw.a(_snowman, dsg.this.i.g, this.b, _snowman + _snowman / 2, _snowman + 5, 16777215);
      }

      @Override
      public List<? extends dmi> au_() {
         return ImmutableList.of();
      }
   }

   @FunctionalInterface
   interface c<T extends brt.g<T>> {
      dsg.f create(nr var1, List<afa> var2, String var3, T var4);
   }

   public abstract class d extends dsg.f {
      private final List<afa> a;
      protected final List<dmi> b = Lists.newArrayList();

      public d(List<afa> var2, @Nullable nr var3) {
         super(_snowman);
         this.a = dsg.this.i.g.b(_snowman, 175);
      }

      @Override
      public List<? extends dmi> au_() {
         return this.b;
      }

      protected void a(dfm var1, int var2, int var3) {
         if (this.a.size() == 1) {
            dsg.this.i.g.b(_snowman, this.a.get(0), (float)_snowman, (float)(_snowman + 5), 16777215);
         } else if (this.a.size() >= 2) {
            dsg.this.i.g.b(_snowman, this.a.get(0), (float)_snowman, (float)_snowman, 16777215);
            dsg.this.i.g.b(_snowman, this.a.get(1), (float)_snowman, (float)(_snowman + 10), 16777215);
         }
      }
   }

   public class e extends dsg.d {
      private final dlq e;

      public e(nr var2, List<afa> var3, String var4, brt.d var5) {
         super(_snowman, _snowman);
         this.e = new dlq(dsg.this.i.g, 10, 5, 42, 20, _snowman.e().c("\n").c(_snowman).c("\n"));
         this.e.a(Integer.toString(_snowman.a()));
         this.e.a(var2x -> {
            if (_snowman.b(var2x)) {
               this.e.l(14737632);
               dsg.this.b(this);
            } else {
               this.e.l(16711680);
               dsg.this.a(this);
            }
         });
         this.b.add(this.e);
      }

      @Override
      public void a(dfm var1, int var2, int var3, int var4, int var5, int var6, int var7, int var8, boolean var9, float var10) {
         this.a(_snowman, _snowman, _snowman);
         this.e.l = _snowman + _snowman - 44;
         this.e.m = _snowman;
         this.e.a(_snowman, _snowman, _snowman, _snowman);
      }
   }

   public abstract class f extends dlo.a<dsg.f> {
      @Nullable
      private final List<afa> a;

      public f(List<afa> var2) {
         this.a = _snowman;
      }
   }

   public class g extends dlo<dsg.f> {
      public g(final brt var2) {
         super(dsg.this.i, dsg.this.k, dsg.this.l, 43, dsg.this.l - 32, 24);
         final Map<brt.b, Map<brt.e<?>, dsg.f>> _snowman = Maps.newHashMap();
         brt.a(new brt.c() {
            @Override
            public void b(brt.e<brt.a> var1, brt.f<brt.a> var2x) {
               this.a(_snowman, (var1x, var2xx, var3x, var4) -> dsg.this.new a(var1x, var2xx, var3x, var4));
            }

            @Override
            public void c(brt.e<brt.d> var1, brt.f<brt.d> var2x) {
               this.a(_snowman, (var1x, var2xx, var3x, var4) -> dsg.this.new e(var1x, var2xx, var3x, var4));
            }

            private <T extends brt.g<T>> void a(brt.e<T> var1, dsg.c<T> var2x) {
               nr _snowman = new of(_snowman.b());
               nr _snowmanx = new oe(_snowman.a()).a(k.o);
               T _snowmanxx = _snowman.a(_snowman);
               String _snowmanxxx = _snowmanxx.b();
               nr _snowmanxxxx = new of("editGamerule.default", new oe(_snowmanxxx)).a(k.h);
               String _snowmanxxxxx = _snowman.b() + ".description";
               List<afa> _snowmanxxxxxx;
               String _snowmanxxxxxxx;
               if (ekx.a(_snowmanxxxxx)) {
                  Builder<afa> _snowmanxxxxxxxx = ImmutableList.builder().add(_snowmanx.f());
                  nr _snowmanxxxxxxxxx = new of(_snowmanxxxxx);
                  dsg.this.o.b(_snowmanxxxxxxxxx, 150).forEach(_snowmanxxxxxxxx::add);
                  _snowmanxxxxxx = _snowmanxxxxxxxx.add(_snowmanxxxx.f()).build();
                  _snowmanxxxxxxx = _snowmanxxxxxxxxx.getString() + "\n" + _snowmanxxxx.getString();
               } else {
                  _snowmanxxxxxx = ImmutableList.of(_snowmanx.f(), _snowmanxxxx.f());
                  _snowmanxxxxxxx = _snowmanxxxx.getString();
               }

               _snowman.computeIfAbsent(_snowman.c(), var0 -> Maps.newHashMap()).put(_snowman, _snowman.create(_snowman, _snowmanxxxxxx, _snowmanxxxxxxx, _snowmanxx));
            }
         });
         _snowman.entrySet().stream().sorted(Entry.comparingByKey()).forEach(var1x -> {
            this.b(dsg.this.new b(new of(var1x.getKey().a()).a(new k[]{k.r, k.o})));
            var1x.getValue().entrySet().stream().sorted(Entry.comparingByKey(Comparator.comparing(brt.e::a))).forEach(var1xx -> this.b(var1xx.getValue()));
         });
      }

      @Override
      public void a(dfm var1, int var2, int var3, float var4) {
         super.a(_snowman, _snowman, _snowman, _snowman);
         if (this.b((double)_snowman, (double)_snowman)) {
            dsg.f _snowman = this.a((double)_snowman, (double)_snowman);
            if (_snowman != null) {
               dsg.this.b(_snowman.a);
            }
         }
      }
   }
}
