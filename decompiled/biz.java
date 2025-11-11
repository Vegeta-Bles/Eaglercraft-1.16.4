import com.mojang.datafixers.util.Pair;

public class biz extends bjj<bio> {
   public static final vk c = new vk("textures/atlas/blocks.png");
   public static final vk d = new vk("item/empty_armor_slot_helmet");
   public static final vk e = new vk("item/empty_armor_slot_chestplate");
   public static final vk f = new vk("item/empty_armor_slot_leggings");
   public static final vk g = new vk("item/empty_armor_slot_boots");
   public static final vk h = new vk("item/empty_armor_slot_shield");
   private static final vk[] j = new vk[]{g, f, e, d};
   private static final aqf[] k = new aqf[]{aqf.f, aqf.e, aqf.d, aqf.c};
   private final bio l = new bio(this, 2, 2);
   private final bjm m = new bjm();
   public final boolean i;
   private final bfw n;

   public biz(bfv var1, boolean var2, bfw var3) {
      super(null, 0);
      this.i = _snowman;
      this.n = _snowman;
      this.a(new bjn(_snowman.e, this.l, this.m, 0, 154, 28));

      for (int _snowman = 0; _snowman < 2; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 2; _snowmanx++) {
            this.a(new bjr(this.l, _snowmanx + _snowman * 2, 98 + _snowmanx * 18, 18 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 4; _snowman++) {
         final aqf _snowmanx = k[_snowman];
         this.a(new bjr(_snowman, 39 - _snowman, 8, 8 + _snowman * 18) {
            @Override
            public int a() {
               return 1;
            }

            @Override
            public boolean a(bmb var1) {
               return _snowman == aqn.j(_snowman);
            }

            @Override
            public boolean a(bfw var1) {
               bmb _snowman = this.e();
               return !_snowman.a() && !_snowman.b_() && bpu.d(_snowman) ? false : super.a(_snowman);
            }

            @Override
            public Pair<vk, vk> c() {
               return Pair.of(biz.c, biz.j[_snowman.b()]);
            }
         });
      }

      for (int _snowman = 0; _snowman < 3; _snowman++) {
         for (int _snowmanx = 0; _snowmanx < 9; _snowmanx++) {
            this.a(new bjr(_snowman, _snowmanx + (_snowman + 1) * 9, 8 + _snowmanx * 18, 84 + _snowman * 18));
         }
      }

      for (int _snowman = 0; _snowman < 9; _snowman++) {
         this.a(new bjr(_snowman, _snowman, 8 + _snowman * 18, 142));
      }

      this.a(new bjr(_snowman, 40, 77, 62) {
         @Override
         public Pair<vk, vk> c() {
            return Pair.of(biz.c, biz.h);
         }
      });
   }

   @Override
   public void a(bfy var1) {
      this.l.a(_snowman);
   }

   @Override
   public void e() {
      this.m.Y_();
      this.l.Y_();
   }

   @Override
   public boolean a(boq<? super bio> var1) {
      return _snowman.a(this.l, this.n.l);
   }

   @Override
   public void a(aon var1) {
      bip.a(this.b, this.n.l, this.n, this.l, this.m);
   }

   @Override
   public void b(bfw var1) {
      super.b(_snowman);
      this.m.Y_();
      if (!_snowman.l.v) {
         this.a(_snowman, _snowman.l, this.l);
      }
   }

   @Override
   public boolean a(bfw var1) {
      return true;
   }

   @Override
   public bmb b(bfw var1, int var2) {
      bmb _snowman = bmb.b;
      bjr _snowmanx = this.a.get(_snowman);
      if (_snowmanx != null && _snowmanx.f()) {
         bmb _snowmanxx = _snowmanx.e();
         _snowman = _snowmanxx.i();
         aqf _snowmanxxx = aqn.j(_snowman);
         if (_snowman == 0) {
            if (!this.a(_snowmanxx, 9, 45, true)) {
               return bmb.b;
            }

            _snowmanx.a(_snowmanxx, _snowman);
         } else if (_snowman >= 1 && _snowman < 5) {
            if (!this.a(_snowmanxx, 9, 45, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 5 && _snowman < 9) {
            if (!this.a(_snowmanxx, 9, 45, false)) {
               return bmb.b;
            }
         } else if (_snowmanxxx.a() == aqf.a.b && !this.a.get(8 - _snowmanxxx.b()).f()) {
            int _snowmanxxxx = 8 - _snowmanxxx.b();
            if (!this.a(_snowmanxx, _snowmanxxxx, _snowmanxxxx + 1, false)) {
               return bmb.b;
            }
         } else if (_snowmanxxx == aqf.b && !this.a.get(45).f()) {
            if (!this.a(_snowmanxx, 45, 46, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 9 && _snowman < 36) {
            if (!this.a(_snowmanxx, 36, 45, false)) {
               return bmb.b;
            }
         } else if (_snowman >= 36 && _snowman < 45) {
            if (!this.a(_snowmanxx, 9, 36, false)) {
               return bmb.b;
            }
         } else if (!this.a(_snowmanxx, 9, 45, false)) {
            return bmb.b;
         }

         if (_snowmanxx.a()) {
            _snowmanx.d(bmb.b);
         } else {
            _snowmanx.d();
         }

         if (_snowmanxx.E() == _snowman.E()) {
            return bmb.b;
         }

         bmb _snowmanxxxx = _snowmanx.a(_snowman, _snowmanxx);
         if (_snowman == 0) {
            _snowman.a(_snowmanxxxx, false);
         }
      }

      return _snowman;
   }

   @Override
   public boolean a(bmb var1, bjr var2) {
      return _snowman.c != this.m && super.a(_snowman, _snowman);
   }

   @Override
   public int f() {
      return 0;
   }

   @Override
   public int g() {
      return this.l.g();
   }

   @Override
   public int h() {
      return this.l.f();
   }

   @Override
   public int i() {
      return 5;
   }

   public bio j() {
      return this.l;
   }

   @Override
   public bjk m() {
      return bjk.a;
   }
}
