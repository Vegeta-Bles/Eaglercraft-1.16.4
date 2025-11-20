package net.minecraft.util.math;

import com.google.common.collect.Iterators;
import com.mojang.serialization.Codec;
import it.unimi.dsi.fastutil.longs.Long2ObjectMap;
import it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Iterator;
import java.util.Locale;
import java.util.Map;
import java.util.Random;
import java.util.function.Predicate;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.annotation.Nullable;
import net.minecraft.client.util.math.Vector3f;
import net.minecraft.client.util.math.Vector4f;
import net.minecraft.entity.Entity;
import net.minecraft.util.StringIdentifiable;
import net.minecraft.util.Util;

public enum Direction implements StringIdentifiable {
   DOWN(0, 1, -1, "down", Direction.AxisDirection.NEGATIVE, Direction.Axis.Y, new Vec3i(0, -1, 0)),
   UP(1, 0, -1, "up", Direction.AxisDirection.POSITIVE, Direction.Axis.Y, new Vec3i(0, 1, 0)),
   NORTH(2, 3, 2, "north", Direction.AxisDirection.NEGATIVE, Direction.Axis.Z, new Vec3i(0, 0, -1)),
   SOUTH(3, 2, 0, "south", Direction.AxisDirection.POSITIVE, Direction.Axis.Z, new Vec3i(0, 0, 1)),
   WEST(4, 5, 1, "west", Direction.AxisDirection.NEGATIVE, Direction.Axis.X, new Vec3i(-1, 0, 0)),
   EAST(5, 4, 3, "east", Direction.AxisDirection.POSITIVE, Direction.Axis.X, new Vec3i(1, 0, 0));

   private final int id;
   private final int idOpposite;
   private final int idHorizontal;
   private final String name;
   private final Direction.Axis axis;
   private final Direction.AxisDirection direction;
   private final Vec3i vector;
   private static final Direction[] ALL = values();
   private static final Map<String, Direction> NAME_MAP = Arrays.stream(ALL).collect(Collectors.toMap(Direction::getName, _snowman -> (Direction)_snowman));
   private static final Direction[] VALUES = Arrays.stream(ALL).sorted(Comparator.comparingInt(_snowman -> _snowman.id)).toArray(Direction[]::new);
   private static final Direction[] HORIZONTAL = Arrays.stream(ALL)
      .filter(_snowman -> _snowman.getAxis().isHorizontal())
      .sorted(Comparator.comparingInt(_snowman -> _snowman.idHorizontal))
      .toArray(Direction[]::new);
   private static final Long2ObjectMap<Direction> VECTOR_TO_DIRECTION = Arrays.stream(ALL)
      .collect(Collectors.toMap(_snowman -> new BlockPos(_snowman.getVector()).asLong(), _snowman -> (Direction)_snowman, (_snowman, _snowmanx) -> {
         throw new IllegalArgumentException("Duplicate keys");
      }, Long2ObjectOpenHashMap::new));

   private Direction(int id, int idOpposite, int idHorizontal, String name, Direction.AxisDirection direction, Direction.Axis axis, Vec3i vector) {
      this.id = id;
      this.idHorizontal = idHorizontal;
      this.idOpposite = idOpposite;
      this.name = name;
      this.axis = axis;
      this.direction = direction;
      this.vector = vector;
   }

   public static Direction[] getEntityFacingOrder(Entity entity) {
      float _snowman = entity.getPitch(1.0F) * (float) (Math.PI / 180.0);
      float _snowmanx = -entity.getYaw(1.0F) * (float) (Math.PI / 180.0);
      float _snowmanxx = MathHelper.sin(_snowman);
      float _snowmanxxx = MathHelper.cos(_snowman);
      float _snowmanxxxx = MathHelper.sin(_snowmanx);
      float _snowmanxxxxx = MathHelper.cos(_snowmanx);
      boolean _snowmanxxxxxx = _snowmanxxxx > 0.0F;
      boolean _snowmanxxxxxxx = _snowmanxx < 0.0F;
      boolean _snowmanxxxxxxxx = _snowmanxxxxx > 0.0F;
      float _snowmanxxxxxxxxx = _snowmanxxxxxx ? _snowmanxxxx : -_snowmanxxxx;
      float _snowmanxxxxxxxxxx = _snowmanxxxxxxx ? -_snowmanxx : _snowmanxx;
      float _snowmanxxxxxxxxxxx = _snowmanxxxxxxxx ? _snowmanxxxxx : -_snowmanxxxxx;
      float _snowmanxxxxxxxxxxxx = _snowmanxxxxxxxxx * _snowmanxxx;
      float _snowmanxxxxxxxxxxxxx = _snowmanxxxxxxxxxxx * _snowmanxxx;
      Direction _snowmanxxxxxxxxxxxxxx = _snowmanxxxxxx ? EAST : WEST;
      Direction _snowmanxxxxxxxxxxxxxxx = _snowmanxxxxxxx ? UP : DOWN;
      Direction _snowmanxxxxxxxxxxxxxxxx = _snowmanxxxxxxxx ? SOUTH : NORTH;
      if (_snowmanxxxxxxxxx > _snowmanxxxxxxxxxxx) {
         if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxxx) {
            return listClosest(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
         } else {
            return _snowmanxxxxxxxxxxxxx > _snowmanxxxxxxxxxx
               ? listClosest(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
               : listClosest(_snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx);
         }
      } else if (_snowmanxxxxxxxxxx > _snowmanxxxxxxxxxxxxx) {
         return listClosest(_snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      } else {
         return _snowmanxxxxxxxxxxxx > _snowmanxxxxxxxxxx
            ? listClosest(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx)
            : listClosest(_snowmanxxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxxx, _snowmanxxxxxxxxxxxxxx);
      }
   }

   private static Direction[] listClosest(Direction first, Direction second, Direction third) {
      return new Direction[]{first, second, third, third.getOpposite(), second.getOpposite(), first.getOpposite()};
   }

   public static Direction transform(Matrix4f matrix, Direction direction) {
      Vec3i _snowman = direction.getVector();
      Vector4f _snowmanx = new Vector4f((float)_snowman.getX(), (float)_snowman.getY(), (float)_snowman.getZ(), 0.0F);
      _snowmanx.transform(matrix);
      return getFacing(_snowmanx.getX(), _snowmanx.getY(), _snowmanx.getZ());
   }

   public Quaternion getRotationQuaternion() {
      Quaternion _snowman = Vector3f.POSITIVE_X.getDegreesQuaternion(90.0F);
      switch (this) {
         case DOWN:
            return Vector3f.POSITIVE_X.getDegreesQuaternion(180.0F);
         case UP:
            return Quaternion.IDENTITY.copy();
         case NORTH:
            _snowman.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0F));
            return _snowman;
         case SOUTH:
            return _snowman;
         case WEST:
            _snowman.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0F));
            return _snowman;
         case EAST:
         default:
            _snowman.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0F));
            return _snowman;
      }
   }

   public int getId() {
      return this.id;
   }

   public int getHorizontal() {
      return this.idHorizontal;
   }

   public Direction.AxisDirection getDirection() {
      return this.direction;
   }

   public Direction getOpposite() {
      return byId(this.idOpposite);
   }

   public Direction rotateYClockwise() {
      switch (this) {
         case NORTH:
            return EAST;
         case SOUTH:
            return WEST;
         case WEST:
            return NORTH;
         case EAST:
            return SOUTH;
         default:
            throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
      }
   }

   public Direction rotateYCounterclockwise() {
      switch (this) {
         case NORTH:
            return WEST;
         case SOUTH:
            return EAST;
         case WEST:
            return SOUTH;
         case EAST:
            return NORTH;
         default:
            throw new IllegalStateException("Unable to get CCW facing of " + this);
      }
   }

   public int getOffsetX() {
      return this.vector.getX();
   }

   public int getOffsetY() {
      return this.vector.getY();
   }

   public int getOffsetZ() {
      return this.vector.getZ();
   }

   public Vector3f getUnitVector() {
      return new Vector3f((float)this.getOffsetX(), (float)this.getOffsetY(), (float)this.getOffsetZ());
   }

   public String getName() {
      return this.name;
   }

   public Direction.Axis getAxis() {
      return this.axis;
   }

   @Nullable
   public static Direction byName(@Nullable String name) {
      return name == null ? null : NAME_MAP.get(name.toLowerCase(Locale.ROOT));
   }

   public static Direction byId(int id) {
      return VALUES[MathHelper.abs(id % VALUES.length)];
   }

   public static Direction fromHorizontal(int value) {
      return HORIZONTAL[MathHelper.abs(value % HORIZONTAL.length)];
   }

   @Nullable
   public static Direction fromVector(int x, int y, int z) {
      return (Direction)VECTOR_TO_DIRECTION.get(BlockPos.asLong(x, y, z));
   }

   public static Direction fromRotation(double rotation) {
      return fromHorizontal(MathHelper.floor(rotation / 90.0 + 0.5) & 3);
   }

   public static Direction from(Direction.Axis axis, Direction.AxisDirection direction) {
      switch (axis) {
         case X:
            return direction == Direction.AxisDirection.POSITIVE ? EAST : WEST;
         case Y:
            return direction == Direction.AxisDirection.POSITIVE ? UP : DOWN;
         case Z:
         default:
            return direction == Direction.AxisDirection.POSITIVE ? SOUTH : NORTH;
      }
   }

   public float asRotation() {
      return (float)((this.idHorizontal & 3) * 90);
   }

   public static Direction random(Random random) {
      return Util.getRandom(ALL, random);
   }

   public static Direction getFacing(double x, double y, double z) {
      return getFacing((float)x, (float)y, (float)z);
   }

   public static Direction getFacing(float x, float y, float z) {
      Direction _snowman = NORTH;
      float _snowmanx = Float.MIN_VALUE;

      for (Direction _snowmanxx : ALL) {
         float _snowmanxxx = x * (float)_snowmanxx.vector.getX() + y * (float)_snowmanxx.vector.getY() + z * (float)_snowmanxx.vector.getZ();
         if (_snowmanxxx > _snowmanx) {
            _snowmanx = _snowmanxxx;
            _snowman = _snowmanxx;
         }
      }

      return _snowman;
   }

   @Override
   public String toString() {
      return this.name;
   }

   @Override
   public String asString() {
      return this.name;
   }

   public static Direction get(Direction.AxisDirection direction, Direction.Axis axis) {
      for (Direction _snowman : ALL) {
         if (_snowman.getDirection() == direction && _snowman.getAxis() == axis) {
            return _snowman;
         }
      }

      throw new IllegalArgumentException("No such direction: " + direction + " " + axis);
   }

   public Vec3i getVector() {
      return this.vector;
   }

   public boolean method_30928(float _snowman) {
      float _snowmanx = _snowman * (float) (Math.PI / 180.0);
      float _snowmanxx = -MathHelper.sin(_snowmanx);
      float _snowmanxxx = MathHelper.cos(_snowmanx);
      return (float)this.vector.getX() * _snowmanxx + (float)this.vector.getZ() * _snowmanxxx > 0.0F;
   }

   public static enum Axis implements StringIdentifiable, Predicate<Direction> {
      X("x") {
         @Override
         public int choose(int x, int y, int z) {
            return x;
         }

         @Override
         public double choose(double x, double y, double z) {
            return x;
         }
      },
      Y("y") {
         @Override
         public int choose(int x, int y, int z) {
            return y;
         }

         @Override
         public double choose(double x, double y, double z) {
            return y;
         }
      },
      Z("z") {
         @Override
         public int choose(int x, int y, int z) {
            return z;
         }

         @Override
         public double choose(double x, double y, double z) {
            return z;
         }
      };

      private static final Direction.Axis[] VALUES = values();
      public static final Codec<Direction.Axis> CODEC = StringIdentifiable.createCodec(Direction.Axis::values, Direction.Axis::fromName);
      private static final Map<String, Direction.Axis> BY_NAME = Arrays.stream(VALUES)
         .collect(Collectors.toMap(Direction.Axis::getName, _snowman -> (Direction.Axis)_snowman));
      private final String name;

      private Axis(String name) {
         this.name = name;
      }

      @Nullable
      public static Direction.Axis fromName(String name) {
         return BY_NAME.get(name.toLowerCase(Locale.ROOT));
      }

      public String getName() {
         return this.name;
      }

      public boolean isVertical() {
         return this == Y;
      }

      public boolean isHorizontal() {
         return this == X || this == Z;
      }

      @Override
      public String toString() {
         return this.name;
      }

      public static Direction.Axis pickRandomAxis(Random random) {
         return Util.getRandom(VALUES, random);
      }

      public boolean test(@Nullable Direction _snowman) {
         return _snowman != null && _snowman.getAxis() == this;
      }

      public Direction.Type getType() {
         switch (this) {
            case X:
            case Z:
               return Direction.Type.HORIZONTAL;
            case Y:
               return Direction.Type.VERTICAL;
            default:
               throw new Error("Someone's been tampering with the universe!");
         }
      }

      @Override
      public String asString() {
         return this.name;
      }

      public abstract int choose(int x, int y, int z);

      public abstract double choose(double x, double y, double z);
   }

   public static enum AxisDirection {
      POSITIVE(1, "Towards positive"),
      NEGATIVE(-1, "Towards negative");

      private final int offset;
      private final String description;

      private AxisDirection(int offset, String description) {
         this.offset = offset;
         this.description = description;
      }

      public int offset() {
         return this.offset;
      }

      @Override
      public String toString() {
         return this.description;
      }

      public Direction.AxisDirection getOpposite() {
         return this == POSITIVE ? NEGATIVE : POSITIVE;
      }
   }

   public static enum Type implements Iterable<Direction>, Predicate<Direction> {
      HORIZONTAL(new Direction[]{Direction.NORTH, Direction.EAST, Direction.SOUTH, Direction.WEST}, new Direction.Axis[]{Direction.Axis.X, Direction.Axis.Z}),
      VERTICAL(new Direction[]{Direction.UP, Direction.DOWN}, new Direction.Axis[]{Direction.Axis.Y});

      private final Direction[] facingArray;
      private final Direction.Axis[] axisArray;

      private Type(Direction[] facingArray, Direction.Axis[] axisArray) {
         this.facingArray = facingArray;
         this.axisArray = axisArray;
      }

      public Direction random(Random random) {
         return Util.getRandom(this.facingArray, random);
      }

      public boolean test(@Nullable Direction _snowman) {
         return _snowman != null && _snowman.getAxis().getType() == this;
      }

      @Override
      public Iterator<Direction> iterator() {
         return Iterators.forArray(this.facingArray);
      }

      public Stream<Direction> stream() {
         return Arrays.stream(this.facingArray);
      }
   }
}
