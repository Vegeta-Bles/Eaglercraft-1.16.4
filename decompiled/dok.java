import com.google.common.collect.ImmutableList;
import java.util.List;

public class dok extends dot {
   private static final dkc[] a = new dkc[]{dkc.i};
   private final dot b;
   private final dkd c;
   private dlj p;
   private dlt q;
   private aor r;

   public dok(dot var1, dkd var2) {
      super(new of("options.title"));
      this.b = _snowman;
      this.c = _snowman;
   }

   @Override
   protected void b() {
      int _snowman = 0;

      for (dkc _snowmanx : a) {
         int _snowmanxx = this.k / 2 - 155 + _snowman % 2 * 160;
         int _snowmanxxx = this.l / 6 - 12 + 24 * (_snowman >> 1);
         this.a(_snowmanx.a(this.i.k, _snowmanxx, _snowmanxxx, 150));
         _snowman++;
      }

      if (this.i.r != null) {
         this.r = this.i.r.ad();
         this.p = this.a((dlj)(new dlj(this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 - 12 + 24 * (_snowman >> 1), 150, 20, this.a(this.r), var1x -> {
            this.r = aor.a(this.r.a() + 1);
            this.i.w().a(new sd(this.r));
            this.p.a(this.a(this.r));
         })));
         if (this.i.G() && !this.i.r.w().n()) {
            this.p.b(this.p.h() - 20);
            this.q = this.a(
               new dlt(
                  this.p.l + this.p.h(),
                  this.p.m,
                  var1x -> this.i
                        .a(
                           new dns(
                              this::c,
                              new of("difficulty.lock.title"),
                              new of("difficulty.lock.question", new of("options.difficulty." + this.i.r.w().s().c()))
                           )
                        )
               )
            );
            this.q.e(this.i.r.w().t());
            this.q.o = !this.q.a();
            this.p.o = !this.q.a();
         } else {
            this.p.o = false;
         }
      } else {
         this.a(new dlw(this.k / 2 - 155 + _snowman % 2 * 160, this.l / 6 - 12 + 24 * (_snowman >> 1), 150, 20, dkc.P, dkc.P.c(this.c), var1x -> {
            dkc.P.a(this.c);
            this.c.b();
            var1x.a(dkc.P.c(this.c));
         }));
      }

      this.a((dlj)(new dlj(this.k / 2 - 155, this.l / 6 + 48 - 6, 150, 20, new of("options.skinCustomisation"), var1x -> this.i.a(new dow(this, this.c)))));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l / 6 + 48 - 6, 150, 20, new of("options.sounds"), var1x -> this.i.a(new dox(this, this.c)))));
      this.a((dlj)(new dlj(this.k / 2 - 155, this.l / 6 + 72 - 6, 150, 20, new of("options.video"), var1x -> this.i.a(new doz(this, this.c)))));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l / 6 + 72 - 6, 150, 20, new of("options.controls"), var1x -> this.i.a(new dpl(this, this.c)))));
      this.a((dlj)(new dlj(this.k / 2 - 155, this.l / 6 + 96 - 6, 150, 20, new of("options.language"), var1x -> this.i.a(new dof(this, this.c, this.i.R())))));
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l / 6 + 96 - 6, 150, 20, new of("options.chat.title"), var1x -> this.i.a(new dnp(this, this.c)))));
      this.a(
         (dlj)(new dlj(
            this.k / 2 - 155,
            this.l / 6 + 120 - 6,
            150,
            20,
            new of("options.resourcepack"),
            var1x -> this.i.a(new dri(this, this.i.O(), this::a, this.i.Q(), new of("resourcePack.title")))
         ))
      );
      this.a((dlj)(new dlj(this.k / 2 + 5, this.l / 6 + 120 - 6, 150, 20, new of("options.accessibility.title"), var1x -> this.i.a(new dnm(this, this.c)))));
      this.a((dlj)(new dlj(this.k / 2 - 100, this.l / 6 + 168, 200, 20, nq.c, var1x -> this.i.a(this.b))));
   }

   private void a(abw var1) {
      List<String> _snowman = ImmutableList.copyOf(this.c.h);
      this.c.h.clear();
      this.c.i.clear();

      for (abu _snowmanx : _snowman.e()) {
         if (!_snowmanx.g()) {
            this.c.h.add(_snowmanx.e());
            if (!_snowmanx.c().a()) {
               this.c.i.add(_snowmanx.e());
            }
         }
      }

      this.c.b();
      List<String> _snowmanxx = ImmutableList.copyOf(this.c.h);
      if (!_snowmanxx.equals(_snowman)) {
         this.i.j();
      }
   }

   private nr a(aor var1) {
      return new of("options.difficulty").c(": ").a(_snowman.b());
   }

   private void c(boolean var1) {
      this.i.a(this);
      if (_snowman && this.i.r != null) {
         this.i.w().a(new ss(true));
         this.q.e(true);
         this.q.o = false;
         this.p.o = false;
      }
   }

   @Override
   public void e() {
      this.c.b();
   }

   @Override
   public void a(dfm var1, int var2, int var3, float var4) {
      this.a(_snowman);
      a(_snowman, this.o, this.d, this.k / 2, 15, 16777215);
      super.a(_snowman, _snowman, _snowman, _snowman);
   }
}
