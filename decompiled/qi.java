import com.google.common.base.MoreObjects;
import com.google.common.collect.Lists;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.properties.Property;
import java.io.IOException;
import java.util.List;
import javax.annotation.Nullable;

public class qi implements oj<om> {
   private qi.a a;
   private final List<qi.b> b = Lists.newArrayList();

   public qi() {
   }

   public qi(qi.a var1, aah... var2) {
      this.a = _snowman;

      for (aah _snowman : _snowman) {
         this.b.add(new qi.b(_snowman.eA(), _snowman.f, _snowman.d.b(), _snowman.G()));
      }
   }

   public qi(qi.a var1, Iterable<aah> var2) {
      this.a = _snowman;

      for (aah _snowman : _snowman) {
         this.b.add(new qi.b(_snowman.eA(), _snowman.f, _snowman.d.b(), _snowman.G()));
      }
   }

   @Override
   public void a(nf var1) throws IOException {
      this.a = _snowman.a(qi.a.class);
      int _snowman = _snowman.i();

      for (int _snowmanx = 0; _snowmanx < _snowman; _snowmanx++) {
         GameProfile _snowmanxx = null;
         int _snowmanxxx = 0;
         bru _snowmanxxxx = null;
         nr _snowmanxxxxx = null;
         switch (this.a) {
            case a:
               _snowmanxx = new GameProfile(_snowman.k(), _snowman.e(16));
               int _snowmanxxxxxx = _snowman.i();
               int _snowmanxxxxxxx = 0;

               for (; _snowmanxxxxxxx < _snowmanxxxxxx; _snowmanxxxxxxx++) {
                  String _snowmanxxxxxxxx = _snowman.e(32767);
                  String _snowmanxxxxxxxxx = _snowman.e(32767);
                  if (_snowman.readBoolean()) {
                     _snowmanxx.getProperties().put(_snowmanxxxxxxxx, new Property(_snowmanxxxxxxxx, _snowmanxxxxxxxxx, _snowman.e(32767)));
                  } else {
                     _snowmanxx.getProperties().put(_snowmanxxxxxxxx, new Property(_snowmanxxxxxxxx, _snowmanxxxxxxxxx));
                  }
               }

               _snowmanxxxx = bru.a(_snowman.i());
               _snowmanxxx = _snowman.i();
               if (_snowman.readBoolean()) {
                  _snowmanxxxxx = _snowman.h();
               }
               break;
            case b:
               _snowmanxx = new GameProfile(_snowman.k(), null);
               _snowmanxxxx = bru.a(_snowman.i());
               break;
            case c:
               _snowmanxx = new GameProfile(_snowman.k(), null);
               _snowmanxxx = _snowman.i();
               break;
            case d:
               _snowmanxx = new GameProfile(_snowman.k(), null);
               if (_snowman.readBoolean()) {
                  _snowmanxxxxx = _snowman.h();
               }
               break;
            case e:
               _snowmanxx = new GameProfile(_snowman.k(), null);
         }

         this.b.add(new qi.b(_snowmanxx, _snowmanxxx, _snowmanxxxx, _snowmanxxxxx));
      }
   }

   @Override
   public void b(nf var1) throws IOException {
      _snowman.a(this.a);
      _snowman.d(this.b.size());

      for (qi.b _snowman : this.b) {
         switch (this.a) {
            case a:
               _snowman.a(_snowman.a().getId());
               _snowman.a(_snowman.a().getName());
               _snowman.d(_snowman.a().getProperties().size());

               for (Property _snowmanx : _snowman.a().getProperties().values()) {
                  _snowman.a(_snowmanx.getName());
                  _snowman.a(_snowmanx.getValue());
                  if (_snowmanx.hasSignature()) {
                     _snowman.writeBoolean(true);
                     _snowman.a(_snowmanx.getSignature());
                  } else {
                     _snowman.writeBoolean(false);
                  }
               }

               _snowman.d(_snowman.c().a());
               _snowman.d(_snowman.b());
               if (_snowman.d() == null) {
                  _snowman.writeBoolean(false);
               } else {
                  _snowman.writeBoolean(true);
                  _snowman.a(_snowman.d());
               }
               break;
            case b:
               _snowman.a(_snowman.a().getId());
               _snowman.d(_snowman.c().a());
               break;
            case c:
               _snowman.a(_snowman.a().getId());
               _snowman.d(_snowman.b());
               break;
            case d:
               _snowman.a(_snowman.a().getId());
               if (_snowman.d() == null) {
                  _snowman.writeBoolean(false);
               } else {
                  _snowman.writeBoolean(true);
                  _snowman.a(_snowman.d());
               }
               break;
            case e:
               _snowman.a(_snowman.a().getId());
         }
      }
   }

   public void a(om var1) {
      _snowman.a(this);
   }

   public List<qi.b> b() {
      return this.b;
   }

   public qi.a c() {
      return this.a;
   }

   @Override
   public String toString() {
      return MoreObjects.toStringHelper(this).add("action", this.a).add("entries", this.b).toString();
   }

   public static enum a {
      a,
      b,
      c,
      d,
      e;

      private a() {
      }
   }

   public class b {
      private final int b;
      private final bru c;
      private final GameProfile d;
      private final nr e;

      public b(GameProfile var2, int var3, bru var4, @Nullable nr var5) {
         this.d = _snowman;
         this.b = _snowman;
         this.c = _snowman;
         this.e = _snowman;
      }

      public GameProfile a() {
         return this.d;
      }

      public int b() {
         return this.b;
      }

      public bru c() {
         return this.c;
      }

      @Nullable
      public nr d() {
         return this.e;
      }

      @Override
      public String toString() {
         return MoreObjects.toStringHelper(this)
            .add("latency", this.b)
            .add("gameMode", this.c)
            .add("profile", this.d)
            .add("displayName", this.e == null ? null : nr.a.a(this.e))
            .toString();
      }
   }
}
