/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Iterators
 *  com.mojang.serialization.Codec
 *  it.unimi.dsi.fastutil.longs.Long2ObjectMap
 *  it.unimi.dsi.fastutil.longs.Long2ObjectOpenHashMap
 *  javax.annotation.Nullable
 */
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
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Matrix4f;
import net.minecraft.util.math.Quaternion;
import net.minecraft.util.math.Vec3i;

public enum Direction implements StringIdentifiable
{
    DOWN(0, 1, -1, "down", AxisDirection.NEGATIVE, Axis.Y, new Vec3i(0, -1, 0)),
    UP(1, 0, -1, "up", AxisDirection.POSITIVE, Axis.Y, new Vec3i(0, 1, 0)),
    NORTH(2, 3, 2, "north", AxisDirection.NEGATIVE, Axis.Z, new Vec3i(0, 0, -1)),
    SOUTH(3, 2, 0, "south", AxisDirection.POSITIVE, Axis.Z, new Vec3i(0, 0, 1)),
    WEST(4, 5, 1, "west", AxisDirection.NEGATIVE, Axis.X, new Vec3i(-1, 0, 0)),
    EAST(5, 4, 3, "east", AxisDirection.POSITIVE, Axis.X, new Vec3i(1, 0, 0));

    private final int id;
    private final int idOpposite;
    private final int idHorizontal;
    private final String name;
    private final Axis axis;
    private final AxisDirection direction;
    private final Vec3i vector;
    private static final Direction[] ALL;
    private static final Map<String, Direction> NAME_MAP;
    private static final Direction[] VALUES;
    private static final Direction[] HORIZONTAL;
    private static final Long2ObjectMap<Direction> VECTOR_TO_DIRECTION;

    private Direction(int id, int idOpposite, int idHorizontal, String name, AxisDirection direction, Axis axis, Vec3i vector) {
        this.id = id;
        this.idHorizontal = idHorizontal;
        this.idOpposite = idOpposite;
        this.name = name;
        this.axis = axis;
        this.direction = direction;
        this.vector = vector;
    }

    public static Direction[] getEntityFacingOrder(Entity entity) {
        float f = entity.getPitch(1.0f) * ((float)Math.PI / 180);
        _snowman = -entity.getYaw(1.0f) * ((float)Math.PI / 180);
        _snowman = MathHelper.sin(f);
        _snowman = MathHelper.cos(f);
        _snowman = MathHelper.sin(_snowman);
        _snowman = MathHelper.cos(_snowman);
        boolean _snowman2 = _snowman > 0.0f;
        boolean _snowman3 = _snowman < 0.0f;
        boolean _snowman4 = _snowman > 0.0f;
        _snowman = _snowman2 ? _snowman : -_snowman;
        _snowman = _snowman3 ? -_snowman : _snowman;
        _snowman = _snowman4 ? _snowman : -_snowman;
        _snowman = _snowman * _snowman;
        _snowman = _snowman * _snowman;
        Direction _snowman5 = _snowman2 ? EAST : WEST;
        Direction _snowman6 = _snowman3 ? UP : DOWN;
        Direction direction = _snowman = _snowman4 ? SOUTH : NORTH;
        if (_snowman > _snowman) {
            if (_snowman > _snowman) {
                return Direction.listClosest(_snowman6, _snowman5, _snowman);
            }
            if (_snowman > _snowman) {
                return Direction.listClosest(_snowman5, _snowman, _snowman6);
            }
            return Direction.listClosest(_snowman5, _snowman6, _snowman);
        }
        if (_snowman > _snowman) {
            return Direction.listClosest(_snowman6, _snowman, _snowman5);
        }
        if (_snowman > _snowman) {
            return Direction.listClosest(_snowman, _snowman5, _snowman6);
        }
        return Direction.listClosest(_snowman, _snowman6, _snowman5);
    }

    private static Direction[] listClosest(Direction first, Direction second, Direction third) {
        return new Direction[]{first, second, third, third.getOpposite(), second.getOpposite(), first.getOpposite()};
    }

    public static Direction transform(Matrix4f matrix, Direction direction) {
        Vec3i vec3i = direction.getVector();
        Vector4f _snowman2 = new Vector4f(vec3i.getX(), vec3i.getY(), vec3i.getZ(), 0.0f);
        _snowman2.transform(matrix);
        return Direction.getFacing(_snowman2.getX(), _snowman2.getY(), _snowman2.getZ());
    }

    public Quaternion getRotationQuaternion() {
        Quaternion quaternion = Vector3f.POSITIVE_X.getDegreesQuaternion(90.0f);
        switch (this) {
            case DOWN: {
                return Vector3f.POSITIVE_X.getDegreesQuaternion(180.0f);
            }
            case UP: {
                return Quaternion.IDENTITY.copy();
            }
            case NORTH: {
                quaternion.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(180.0f));
                return quaternion;
            }
            case SOUTH: {
                return quaternion;
            }
            case WEST: {
                quaternion.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(90.0f));
                return quaternion;
            }
        }
        quaternion.hamiltonProduct(Vector3f.POSITIVE_Z.getDegreesQuaternion(-90.0f));
        return quaternion;
    }

    public int getId() {
        return this.id;
    }

    public int getHorizontal() {
        return this.idHorizontal;
    }

    public AxisDirection getDirection() {
        return this.direction;
    }

    public Direction getOpposite() {
        return Direction.byId(this.idOpposite);
    }

    public Direction rotateYClockwise() {
        switch (this) {
            case NORTH: {
                return EAST;
            }
            case EAST: {
                return SOUTH;
            }
            case SOUTH: {
                return WEST;
            }
            case WEST: {
                return NORTH;
            }
        }
        throw new IllegalStateException("Unable to get Y-rotated facing of " + this);
    }

    public Direction rotateYCounterclockwise() {
        switch (this) {
            case NORTH: {
                return WEST;
            }
            case EAST: {
                return NORTH;
            }
            case SOUTH: {
                return EAST;
            }
            case WEST: {
                return SOUTH;
            }
        }
        throw new IllegalStateException("Unable to get CCW facing of " + this);
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
        return new Vector3f(this.getOffsetX(), this.getOffsetY(), this.getOffsetZ());
    }

    public String getName() {
        return this.name;
    }

    public Axis getAxis() {
        return this.axis;
    }

    @Nullable
    public static Direction byName(@Nullable String name) {
        if (name == null) {
            return null;
        }
        return NAME_MAP.get(name.toLowerCase(Locale.ROOT));
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
        return Direction.fromHorizontal(MathHelper.floor(rotation / 90.0 + 0.5) & 3);
    }

    public static Direction from(Axis axis, AxisDirection direction) {
        switch (axis) {
            case X: {
                return direction == AxisDirection.POSITIVE ? EAST : WEST;
            }
            case Y: {
                return direction == AxisDirection.POSITIVE ? UP : DOWN;
            }
        }
        return direction == AxisDirection.POSITIVE ? SOUTH : NORTH;
    }

    public float asRotation() {
        return (this.idHorizontal & 3) * 90;
    }

    public static Direction random(Random random) {
        return Util.getRandom(ALL, random);
    }

    public static Direction getFacing(double x, double y, double z) {
        return Direction.getFacing((float)x, (float)y, (float)z);
    }

    public static Direction getFacing(float x, float y, float z) {
        Direction _snowman3 = NORTH;
        float _snowman2 = Float.MIN_VALUE;
        for (Direction direction : ALL) {
            float f = x * (float)direction.vector.getX() + y * (float)direction.vector.getY() + z * (float)direction.vector.getZ();
            if (!(f > _snowman2)) continue;
            _snowman2 = f;
            _snowman3 = direction;
        }
        return _snowman3;
    }

    public String toString() {
        return this.name;
    }

    @Override
    public String asString() {
        return this.name;
    }

    public static Direction get(AxisDirection direction, Axis axis) {
        for (Direction direction2 : ALL) {
            if (direction2.getDirection() != direction || direction2.getAxis() != axis) continue;
            return direction2;
        }
        throw new IllegalArgumentException("No such direction: " + (Object)((Object)direction) + " " + axis);
    }

    public Vec3i getVector() {
        return this.vector;
    }

    public boolean method_30928(float f) {
        _snowman = f * ((float)Math.PI / 180);
        _snowman = -MathHelper.sin(_snowman);
        _snowman = MathHelper.cos(_snowman);
        return (float)this.vector.getX() * _snowman + (float)this.vector.getZ() * _snowman > 0.0f;
    }

    static {
        ALL = Direction.values();
        NAME_MAP = Arrays.stream(ALL).collect(Collectors.toMap(Direction::getName, direction -> direction));
        VALUES = (Direction[])Arrays.stream(ALL).sorted(Comparator.comparingInt(direction -> direction.id)).toArray(Direction[]::new);
        HORIZONTAL = (Direction[])Arrays.stream(ALL).filter(direction -> direction.getAxis().isHorizontal()).sorted(Comparator.comparingInt(direction -> direction.idHorizontal)).toArray(Direction[]::new);
        VECTOR_TO_DIRECTION = (Long2ObjectMap)Arrays.stream(ALL).collect(Collectors.toMap(direction -> new BlockPos(direction.getVector()).asLong(), direction -> direction, (direction, direction2) -> {
            throw new IllegalArgumentException("Duplicate keys");
        }, Long2ObjectOpenHashMap::new));
    }

    public static enum Type implements Iterable<Direction>,
    Predicate<Direction>
    {
        HORIZONTAL(new Direction[]{NORTH, EAST, SOUTH, WEST}, new Axis[]{Axis.X, Axis.Z}),
        VERTICAL(new Direction[]{UP, DOWN}, new Axis[]{Axis.Y});

        private final Direction[] facingArray;
        private final Axis[] axisArray;

        private Type(Direction[] facingArray, Axis[] axisArray) {
            this.facingArray = facingArray;
            this.axisArray = axisArray;
        }

        public Direction random(Random random) {
            return Util.getRandom(this.facingArray, random);
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis().getType() == this;
        }

        @Override
        public Iterator<Direction> iterator() {
            return Iterators.forArray((Object[])this.facingArray);
        }

        public Stream<Direction> stream() {
            return Arrays.stream(this.facingArray);
        }

        @Override
        public /* synthetic */ boolean test(@Nullable Object direction) {
            return this.test((Direction)direction);
        }
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

        public String toString() {
            return this.description;
        }

        public AxisDirection getOpposite() {
            return this == POSITIVE ? NEGATIVE : POSITIVE;
        }
    }

    public static enum Axis implements StringIdentifiable,
    Predicate<Direction>
    {
        X("x"){

            public int choose(int x, int y, int z) {
                return x;
            }

            public double choose(double x, double y, double z) {
                return x;
            }

            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        }
        ,
        Y("y"){

            public int choose(int x, int y, int z) {
                return y;
            }

            public double choose(double x, double y, double z) {
                return y;
            }

            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        }
        ,
        Z("z"){

            public int choose(int x, int y, int z) {
                return z;
            }

            public double choose(double x, double y, double z) {
                return z;
            }

            public /* synthetic */ boolean test(@Nullable Object object) {
                return super.test((Direction)object);
            }
        };

        private static final Axis[] VALUES;
        public static final Codec<Axis> CODEC;
        private static final Map<String, Axis> BY_NAME;
        private final String name;

        private Axis(String name) {
            this.name = name;
        }

        @Nullable
        public static Axis fromName(String name) {
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

        public String toString() {
            return this.name;
        }

        public static Axis pickRandomAxis(Random random) {
            return Util.getRandom(VALUES, random);
        }

        @Override
        public boolean test(@Nullable Direction direction) {
            return direction != null && direction.getAxis() == this;
        }

        public Type getType() {
            switch (this) {
                case X: 
                case Z: {
                    return Type.HORIZONTAL;
                }
                case Y: {
                    return Type.VERTICAL;
                }
            }
            throw new Error("Someone's been tampering with the universe!");
        }

        @Override
        public String asString() {
            return this.name;
        }

        public abstract int choose(int var1, int var2, int var3);

        public abstract double choose(double var1, double var3, double var5);

        @Override
        public /* synthetic */ boolean test(@Nullable Object object) {
            return this.test((Direction)object);
        }

        static {
            VALUES = Axis.values();
            CODEC = StringIdentifiable.createCodec(Axis::values, Axis::fromName);
            BY_NAME = Arrays.stream(VALUES).collect(Collectors.toMap(Axis::getName, axis -> axis));
        }
    }
}

