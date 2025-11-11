import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;

public class uu {
   private static final aet<ut<?>> t = new aet<>(16);
   public static final ut<Byte> a = new ut<Byte>() {
      public void a(nf var1, Byte var2) {
         _snowman.writeByte(_snowman);
      }

      public Byte b(nf var1) {
         return _snowman.readByte();
      }

      public Byte a(Byte var1) {
         return _snowman;
      }
   };
   public static final ut<Integer> b = new ut<Integer>() {
      public void a(nf var1, Integer var2) {
         _snowman.d(_snowman);
      }

      public Integer b(nf var1) {
         return _snowman.i();
      }

      public Integer a(Integer var1) {
         return _snowman;
      }
   };
   public static final ut<Float> c = new ut<Float>() {
      public void a(nf var1, Float var2) {
         _snowman.writeFloat(_snowman);
      }

      public Float b(nf var1) {
         return _snowman.readFloat();
      }

      public Float a(Float var1) {
         return _snowman;
      }
   };
   public static final ut<String> d = new ut<String>() {
      public void a(nf var1, String var2) {
         _snowman.a(_snowman);
      }

      public String b(nf var1) {
         return _snowman.e(32767);
      }

      public String a(String var1) {
         return _snowman;
      }
   };
   public static final ut<nr> e = new ut<nr>() {
      public void a(nf var1, nr var2) {
         _snowman.a(_snowman);
      }

      public nr b(nf var1) {
         return _snowman.h();
      }

      public nr a(nr var1) {
         return _snowman;
      }
   };
   public static final ut<Optional<nr>> f = new ut<Optional<nr>>() {
      public void a(nf var1, Optional<nr> var2) {
         if (_snowman.isPresent()) {
            _snowman.writeBoolean(true);
            _snowman.a(_snowman.get());
         } else {
            _snowman.writeBoolean(false);
         }
      }

      public Optional<nr> b(nf var1) {
         return _snowman.readBoolean() ? Optional.of(_snowman.h()) : Optional.empty();
      }

      public Optional<nr> a(Optional<nr> var1) {
         return _snowman;
      }
   };
   public static final ut<bmb> g = new ut<bmb>() {
      public void a(nf var1, bmb var2) {
         _snowman.a(_snowman);
      }

      public bmb b(nf var1) {
         return _snowman.n();
      }

      public bmb a(bmb var1) {
         return _snowman.i();
      }
   };
   public static final ut<Optional<ceh>> h = new ut<Optional<ceh>>() {
      public void a(nf var1, Optional<ceh> var2) {
         if (_snowman.isPresent()) {
            _snowman.d(buo.i(_snowman.get()));
         } else {
            _snowman.d(0);
         }
      }

      public Optional<ceh> b(nf var1) {
         int _snowman = _snowman.i();
         return _snowman == 0 ? Optional.empty() : Optional.of(buo.a(_snowman));
      }

      public Optional<ceh> a(Optional<ceh> var1) {
         return _snowman;
      }
   };
   public static final ut<Boolean> i = new ut<Boolean>() {
      public void a(nf var1, Boolean var2) {
         _snowman.writeBoolean(_snowman);
      }

      public Boolean b(nf var1) {
         return _snowman.readBoolean();
      }

      public Boolean a(Boolean var1) {
         return _snowman;
      }
   };
   public static final ut<hf> j = new ut<hf>() {
      public void a(nf var1, hf var2) {
         _snowman.d(gm.V.a(_snowman.b()));
         _snowman.a(_snowman);
      }

      public hf b(nf var1) {
         return this.a(_snowman, (hg<hf>)gm.V.a(_snowman.i()));
      }

      private <T extends hf> T a(nf var1, hg<T> var2) {
         return _snowman.d().b(_snowman, _snowman);
      }

      public hf a(hf var1) {
         return _snowman;
      }
   };
   public static final ut<go> k = new ut<go>() {
      public void a(nf var1, go var2) {
         _snowman.writeFloat(_snowman.b());
         _snowman.writeFloat(_snowman.c());
         _snowman.writeFloat(_snowman.d());
      }

      public go b(nf var1) {
         return new go(_snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat());
      }

      public go a(go var1) {
         return _snowman;
      }
   };
   public static final ut<fx> l = new ut<fx>() {
      public void a(nf var1, fx var2) {
         _snowman.a(_snowman);
      }

      public fx b(nf var1) {
         return _snowman.e();
      }

      public fx a(fx var1) {
         return _snowman;
      }
   };
   public static final ut<Optional<fx>> m = new ut<Optional<fx>>() {
      public void a(nf var1, Optional<fx> var2) {
         _snowman.writeBoolean(_snowman.isPresent());
         if (_snowman.isPresent()) {
            _snowman.a(_snowman.get());
         }
      }

      public Optional<fx> b(nf var1) {
         return !_snowman.readBoolean() ? Optional.empty() : Optional.of(_snowman.e());
      }

      public Optional<fx> a(Optional<fx> var1) {
         return _snowman;
      }
   };
   public static final ut<gc> n = new ut<gc>() {
      public void a(nf var1, gc var2) {
         _snowman.a(_snowman);
      }

      public gc b(nf var1) {
         return _snowman.a(gc.class);
      }

      public gc a(gc var1) {
         return _snowman;
      }
   };
   public static final ut<Optional<UUID>> o = new ut<Optional<UUID>>() {
      public void a(nf var1, Optional<UUID> var2) {
         _snowman.writeBoolean(_snowman.isPresent());
         if (_snowman.isPresent()) {
            _snowman.a(_snowman.get());
         }
      }

      public Optional<UUID> b(nf var1) {
         return !_snowman.readBoolean() ? Optional.empty() : Optional.of(_snowman.k());
      }

      public Optional<UUID> a(Optional<UUID> var1) {
         return _snowman;
      }
   };
   public static final ut<md> p = new ut<md>() {
      public void a(nf var1, md var2) {
         _snowman.a(_snowman);
      }

      public md b(nf var1) {
         return _snowman.l();
      }

      public md a(md var1) {
         return _snowman.g();
      }
   };
   public static final ut<bfk> q = new ut<bfk>() {
      public void a(nf var1, bfk var2) {
         _snowman.d(gm.ah.a(_snowman.a()));
         _snowman.d(gm.ai.a(_snowman.b()));
         _snowman.d(_snowman.c());
      }

      public bfk b(nf var1) {
         return new bfk(gm.ah.a(_snowman.i()), gm.ai.a(_snowman.i()), _snowman.i());
      }

      public bfk a(bfk var1) {
         return _snowman;
      }
   };
   public static final ut<OptionalInt> r = new ut<OptionalInt>() {
      public void a(nf var1, OptionalInt var2) {
         _snowman.d(_snowman.orElse(-1) + 1);
      }

      public OptionalInt b(nf var1) {
         int _snowman = _snowman.i();
         return _snowman == 0 ? OptionalInt.empty() : OptionalInt.of(_snowman - 1);
      }

      public OptionalInt a(OptionalInt var1) {
         return _snowman;
      }
   };
   public static final ut<aqx> s = new ut<aqx>() {
      public void a(nf var1, aqx var2) {
         _snowman.a(_snowman);
      }

      public aqx b(nf var1) {
         return _snowman.a(aqx.class);
      }

      public aqx a(aqx var1) {
         return _snowman;
      }
   };

   public static void a(ut<?> var0) {
      t.c(_snowman);
   }

   @Nullable
   public static ut<?> a(int var0) {
      return t.a(_snowman);
   }

   public static int b(ut<?> var0) {
      return t.a(_snowman);
   }

   static {
      a(a);
      a(b);
      a(c);
      a(d);
      a(e);
      a(f);
      a(g);
      a(i);
      a(k);
      a(l);
      a(m);
      a(n);
      a(o);
      a(h);
      a(p);
      a(j);
      a(q);
      a(r);
      a(s);
   }
}
