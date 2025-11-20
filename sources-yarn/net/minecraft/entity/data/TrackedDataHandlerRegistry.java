package net.minecraft.entity.data;

import java.util.Optional;
import java.util.OptionalInt;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.EntityPose;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.particle.ParticleEffect;
import net.minecraft.particle.ParticleType;
import net.minecraft.text.Text;
import net.minecraft.util.collection.Int2ObjectBiMap;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Direction;
import net.minecraft.util.math.EulerAngle;
import net.minecraft.util.registry.Registry;
import net.minecraft.village.VillagerData;

public class TrackedDataHandlerRegistry {
   private static final Int2ObjectBiMap<TrackedDataHandler<?>> field_13328 = new Int2ObjectBiMap<>(16);
   public static final TrackedDataHandler<Byte> BYTE = new TrackedDataHandler<Byte>() {
      public void write(PacketByteBuf arg, Byte byte_) {
         arg.writeByte(byte_);
      }

      public Byte read(PacketByteBuf arg) {
         return arg.readByte();
      }

      public Byte copy(Byte byte_) {
         return byte_;
      }
   };
   public static final TrackedDataHandler<Integer> INTEGER = new TrackedDataHandler<Integer>() {
      public void write(PacketByteBuf arg, Integer integer) {
         arg.writeVarInt(integer);
      }

      public Integer read(PacketByteBuf arg) {
         return arg.readVarInt();
      }

      public Integer copy(Integer integer) {
         return integer;
      }
   };
   public static final TrackedDataHandler<Float> FLOAT = new TrackedDataHandler<Float>() {
      public void write(PacketByteBuf arg, Float float_) {
         arg.writeFloat(float_);
      }

      public Float read(PacketByteBuf arg) {
         return arg.readFloat();
      }

      public Float copy(Float float_) {
         return float_;
      }
   };
   public static final TrackedDataHandler<String> STRING = new TrackedDataHandler<String>() {
      public void write(PacketByteBuf arg, String string) {
         arg.writeString(string);
      }

      public String read(PacketByteBuf arg) {
         return arg.readString(32767);
      }

      public String copy(String string) {
         return string;
      }
   };
   public static final TrackedDataHandler<Text> TEXT_COMPONENT = new TrackedDataHandler<Text>() {
      public void write(PacketByteBuf arg, Text arg2) {
         arg.writeText(arg2);
      }

      public Text read(PacketByteBuf arg) {
         return arg.readText();
      }

      public Text copy(Text arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<Optional<Text>> OPTIONAL_TEXT_COMPONENT = new TrackedDataHandler<Optional<Text>>() {
      public void write(PacketByteBuf arg, Optional<Text> optional) {
         if (optional.isPresent()) {
            arg.writeBoolean(true);
            arg.writeText(optional.get());
         } else {
            arg.writeBoolean(false);
         }
      }

      public Optional<Text> read(PacketByteBuf arg) {
         return arg.readBoolean() ? Optional.of(arg.readText()) : Optional.empty();
      }

      public Optional<Text> copy(Optional<Text> optional) {
         return optional;
      }
   };
   public static final TrackedDataHandler<ItemStack> ITEM_STACK = new TrackedDataHandler<ItemStack>() {
      public void write(PacketByteBuf arg, ItemStack arg2) {
         arg.writeItemStack(arg2);
      }

      public ItemStack read(PacketByteBuf arg) {
         return arg.readItemStack();
      }

      public ItemStack copy(ItemStack arg) {
         return arg.copy();
      }
   };
   public static final TrackedDataHandler<Optional<BlockState>> OPTIONAL_BLOCK_STATE = new TrackedDataHandler<Optional<BlockState>>() {
      public void write(PacketByteBuf arg, Optional<BlockState> optional) {
         if (optional.isPresent()) {
            arg.writeVarInt(Block.getRawIdFromState(optional.get()));
         } else {
            arg.writeVarInt(0);
         }
      }

      public Optional<BlockState> read(PacketByteBuf arg) {
         int i = arg.readVarInt();
         return i == 0 ? Optional.empty() : Optional.of(Block.getStateFromRawId(i));
      }

      public Optional<BlockState> copy(Optional<BlockState> optional) {
         return optional;
      }
   };
   public static final TrackedDataHandler<Boolean> BOOLEAN = new TrackedDataHandler<Boolean>() {
      public void write(PacketByteBuf arg, Boolean boolean_) {
         arg.writeBoolean(boolean_);
      }

      public Boolean read(PacketByteBuf arg) {
         return arg.readBoolean();
      }

      public Boolean copy(Boolean boolean_) {
         return boolean_;
      }
   };
   public static final TrackedDataHandler<ParticleEffect> PARTICLE = new TrackedDataHandler<ParticleEffect>() {
      public void write(PacketByteBuf arg, ParticleEffect arg2) {
         arg.writeVarInt(Registry.PARTICLE_TYPE.getRawId(arg2.getType()));
         arg2.write(arg);
      }

      public ParticleEffect read(PacketByteBuf arg) {
         return this.method_12744(arg, (ParticleType<ParticleEffect>)Registry.PARTICLE_TYPE.get(arg.readVarInt()));
      }

      private <T extends ParticleEffect> T method_12744(PacketByteBuf arg, ParticleType<T> arg2) {
         return arg2.getParametersFactory().read(arg2, arg);
      }

      public ParticleEffect copy(ParticleEffect arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<EulerAngle> ROTATION = new TrackedDataHandler<EulerAngle>() {
      public void write(PacketByteBuf arg, EulerAngle arg2) {
         arg.writeFloat(arg2.getPitch());
         arg.writeFloat(arg2.getYaw());
         arg.writeFloat(arg2.getRoll());
      }

      public EulerAngle read(PacketByteBuf arg) {
         return new EulerAngle(arg.readFloat(), arg.readFloat(), arg.readFloat());
      }

      public EulerAngle copy(EulerAngle arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<BlockPos> BLOCK_POS = new TrackedDataHandler<BlockPos>() {
      public void write(PacketByteBuf arg, BlockPos arg2) {
         arg.writeBlockPos(arg2);
      }

      public BlockPos read(PacketByteBuf arg) {
         return arg.readBlockPos();
      }

      public BlockPos copy(BlockPos arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new TrackedDataHandler<Optional<BlockPos>>() {
      public void write(PacketByteBuf arg, Optional<BlockPos> optional) {
         arg.writeBoolean(optional.isPresent());
         if (optional.isPresent()) {
            arg.writeBlockPos(optional.get());
         }
      }

      public Optional<BlockPos> read(PacketByteBuf arg) {
         return !arg.readBoolean() ? Optional.empty() : Optional.of(arg.readBlockPos());
      }

      public Optional<BlockPos> copy(Optional<BlockPos> optional) {
         return optional;
      }
   };
   public static final TrackedDataHandler<Direction> FACING = new TrackedDataHandler<Direction>() {
      public void write(PacketByteBuf arg, Direction arg2) {
         arg.writeEnumConstant(arg2);
      }

      public Direction read(PacketByteBuf arg) {
         return arg.readEnumConstant(Direction.class);
      }

      public Direction copy(Direction arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<Optional<UUID>> OPTIONAL_UUID = new TrackedDataHandler<Optional<UUID>>() {
      public void write(PacketByteBuf arg, Optional<UUID> optional) {
         arg.writeBoolean(optional.isPresent());
         if (optional.isPresent()) {
            arg.writeUuid(optional.get());
         }
      }

      public Optional<UUID> read(PacketByteBuf arg) {
         return !arg.readBoolean() ? Optional.empty() : Optional.of(arg.readUuid());
      }

      public Optional<UUID> copy(Optional<UUID> optional) {
         return optional;
      }
   };
   public static final TrackedDataHandler<CompoundTag> TAG_COMPOUND = new TrackedDataHandler<CompoundTag>() {
      public void write(PacketByteBuf arg, CompoundTag arg2) {
         arg.writeCompoundTag(arg2);
      }

      public CompoundTag read(PacketByteBuf arg) {
         return arg.readCompoundTag();
      }

      public CompoundTag copy(CompoundTag arg) {
         return arg.copy();
      }
   };
   public static final TrackedDataHandler<VillagerData> VILLAGER_DATA = new TrackedDataHandler<VillagerData>() {
      public void write(PacketByteBuf arg, VillagerData arg2) {
         arg.writeVarInt(Registry.VILLAGER_TYPE.getRawId(arg2.getType()));
         arg.writeVarInt(Registry.VILLAGER_PROFESSION.getRawId(arg2.getProfession()));
         arg.writeVarInt(arg2.getLevel());
      }

      public VillagerData read(PacketByteBuf arg) {
         return new VillagerData(Registry.VILLAGER_TYPE.get(arg.readVarInt()), Registry.VILLAGER_PROFESSION.get(arg.readVarInt()), arg.readVarInt());
      }

      public VillagerData copy(VillagerData arg) {
         return arg;
      }
   };
   public static final TrackedDataHandler<OptionalInt> FIREWORK_DATA = new TrackedDataHandler<OptionalInt>() {
      public void write(PacketByteBuf arg, OptionalInt optionalInt) {
         arg.writeVarInt(optionalInt.orElse(-1) + 1);
      }

      public OptionalInt read(PacketByteBuf arg) {
         int i = arg.readVarInt();
         return i == 0 ? OptionalInt.empty() : OptionalInt.of(i - 1);
      }

      public OptionalInt copy(OptionalInt optionalInt) {
         return optionalInt;
      }
   };
   public static final TrackedDataHandler<EntityPose> ENTITY_POSE = new TrackedDataHandler<EntityPose>() {
      public void write(PacketByteBuf arg, EntityPose arg2) {
         arg.writeEnumConstant(arg2);
      }

      public EntityPose read(PacketByteBuf arg) {
         return arg.readEnumConstant(EntityPose.class);
      }

      public EntityPose copy(EntityPose arg) {
         return arg;
      }
   };

   public static void register(TrackedDataHandler<?> handler) {
      field_13328.add(handler);
   }

   @Nullable
   public static TrackedDataHandler<?> get(int id) {
      return field_13328.get(id);
   }

   public static int getId(TrackedDataHandler<?> handler) {
      return field_13328.getRawId(handler);
   }

   static {
      register(BYTE);
      register(INTEGER);
      register(FLOAT);
      register(STRING);
      register(TEXT_COMPONENT);
      register(OPTIONAL_TEXT_COMPONENT);
      register(ITEM_STACK);
      register(BOOLEAN);
      register(ROTATION);
      register(BLOCK_POS);
      register(OPTIONAL_BLOCK_POS);
      register(FACING);
      register(OPTIONAL_UUID);
      register(OPTIONAL_BLOCK_STATE);
      register(TAG_COMPOUND);
      register(PARTICLE);
      register(VILLAGER_DATA);
      register(FIREWORK_DATA);
      register(ENTITY_POSE);
   }
}
