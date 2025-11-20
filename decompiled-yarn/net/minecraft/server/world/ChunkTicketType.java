package net.minecraft.server.world;

import java.util.Comparator;
import net.minecraft.util.Unit;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.Vec3i;

public class ChunkTicketType<T> {
   private final String name;
   private final Comparator<T> argumentComparator;
   private final long expiryTicks;
   public static final ChunkTicketType<Unit> START = create("start", (_snowman, _snowmanx) -> 0);
   public static final ChunkTicketType<Unit> DRAGON = create("dragon", (_snowman, _snowmanx) -> 0);
   public static final ChunkTicketType<ChunkPos> PLAYER = create("player", Comparator.comparingLong(ChunkPos::toLong));
   public static final ChunkTicketType<ChunkPos> FORCED = create("forced", Comparator.comparingLong(ChunkPos::toLong));
   public static final ChunkTicketType<ChunkPos> LIGHT = create("light", Comparator.comparingLong(ChunkPos::toLong));
   public static final ChunkTicketType<BlockPos> PORTAL = create("portal", Vec3i::compareTo, 300);
   public static final ChunkTicketType<Integer> POST_TELEPORT = create("post_teleport", Integer::compareTo, 5);
   public static final ChunkTicketType<ChunkPos> UNKNOWN = create("unknown", Comparator.comparingLong(ChunkPos::toLong), 1);

   public static <T> ChunkTicketType<T> create(String name, Comparator<T> _snowman) {
      return new ChunkTicketType<>(name, _snowman, 0L);
   }

   public static <T> ChunkTicketType<T> create(String name, Comparator<T> _snowman, int expiryTicks) {
      return new ChunkTicketType<>(name, _snowman, (long)expiryTicks);
   }

   protected ChunkTicketType(String name, Comparator<T> _snowman, long expiryTicks) {
      this.name = name;
      this.argumentComparator = _snowman;
      this.expiryTicks = expiryTicks;
   }

   @Override
   public String toString() {
      return this.name;
   }

   public Comparator<T> getArgumentComparator() {
      return this.argumentComparator;
   }

   public long getExpiryTicks() {
      return this.expiryTicks;
   }
}
