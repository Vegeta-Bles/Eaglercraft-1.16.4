import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import com.mojang.authlib.GameProfile;
import com.mojang.authlib.minecraft.SocialInteractionsService;
import java.util.Map;
import java.util.Set;
import java.util.UUID;

public class dsa {
   private final djz a;
   private final Set<UUID> b = Sets.newHashSet();
   private final SocialInteractionsService c;
   private final Map<String, UUID> d = Maps.newHashMap();

   public dsa(djz var1, SocialInteractionsService var2) {
      this.a = _snowman;
      this.c = _snowman;
   }

   public void a(UUID var1) {
      this.b.add(_snowman);
   }

   public void b(UUID var1) {
      this.b.remove(_snowman);
   }

   public boolean c(UUID var1) {
      return this.d(_snowman) || this.e(_snowman);
   }

   public boolean d(UUID var1) {
      return this.b.contains(_snowman);
   }

   public boolean e(UUID var1) {
      return this.c.isBlockedPlayer(_snowman);
   }

   public Set<UUID> a() {
      return this.b;
   }

   public UUID a(String var1) {
      return this.d.getOrDefault(_snowman, x.b);
   }

   public void a(dwx var1) {
      GameProfile _snowman = _snowman.a();
      if (_snowman.isComplete()) {
         this.d.put(_snowman.getName(), _snowman.getId());
      }

      dot _snowmanx = this.a.y;
      if (_snowmanx instanceof dsc) {
         dsc _snowmanxx = (dsc)_snowmanx;
         _snowmanxx.a(_snowman);
      }
   }

   public void f(UUID var1) {
      dot _snowman = this.a.y;
      if (_snowman instanceof dsc) {
         dsc _snowmanx = (dsc)_snowman;
         _snowmanx.a(_snowman);
      }
   }
}
