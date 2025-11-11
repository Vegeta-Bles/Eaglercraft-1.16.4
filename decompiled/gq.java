import com.mojang.serialization.Codec;
import java.util.Arrays;
import java.util.UUID;

public final class gq {
   public static final Codec<UUID> a = Codec.INT_STREAM.comapFlatMap(var0 -> x.a(var0, 4).map(gq::a), var0 -> Arrays.stream(a(var0)));

   public static UUID a(int[] var0) {
      return new UUID((long)_snowman[0] << 32 | (long)_snowman[1] & 4294967295L, (long)_snowman[2] << 32 | (long)_snowman[3] & 4294967295L);
   }

   public static int[] a(UUID var0) {
      long _snowman = _snowman.getMostSignificantBits();
      long _snowmanx = _snowman.getLeastSignificantBits();
      return a(_snowman, _snowmanx);
   }

   private static int[] a(long var0, long var2) {
      return new int[]{(int)(_snowman >> 32), (int)_snowman, (int)(_snowman >> 32), (int)_snowman};
   }
}
