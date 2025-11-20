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
      public void write(PacketByteBuf _snowman, Byte _snowman) {
         _snowman.writeByte(_snowman);
      }

      public Byte read(PacketByteBuf _snowman) {
         return _snowman.readByte();
      }

      public Byte copy(Byte _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Integer> INTEGER = new TrackedDataHandler<Integer>() {
      public void write(PacketByteBuf _snowman, Integer _snowman) {
         _snowman.writeVarInt(_snowman);
      }

      public Integer read(PacketByteBuf _snowman) {
         return _snowman.readVarInt();
      }

      public Integer copy(Integer _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Float> FLOAT = new TrackedDataHandler<Float>() {
      public void write(PacketByteBuf _snowman, Float _snowman) {
         _snowman.writeFloat(_snowman);
      }

      public Float read(PacketByteBuf _snowman) {
         return _snowman.readFloat();
      }

      public Float copy(Float _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<String> STRING = new TrackedDataHandler<String>() {
      public void write(PacketByteBuf _snowman, String _snowman) {
         _snowman.writeString(_snowman);
      }

      public String read(PacketByteBuf _snowman) {
         return _snowman.readString(32767);
      }

      public String copy(String _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Text> TEXT_COMPONENT = new TrackedDataHandler<Text>() {
      public void write(PacketByteBuf _snowman, Text _snowman) {
         _snowman.writeText(_snowman);
      }

      public Text read(PacketByteBuf _snowman) {
         return _snowman.readText();
      }

      public Text copy(Text _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Optional<Text>> OPTIONAL_TEXT_COMPONENT = new TrackedDataHandler<Optional<Text>>() {
      public void write(PacketByteBuf _snowman, Optional<Text> _snowman) {
         if (_snowman.isPresent()) {
            _snowman.writeBoolean(true);
            _snowman.writeText(_snowman.get());
         } else {
            _snowman.writeBoolean(false);
         }
      }

      public Optional<Text> read(PacketByteBuf _snowman) {
         return _snowman.readBoolean() ? Optional.of(_snowman.readText()) : Optional.empty();
      }

      public Optional<Text> copy(Optional<Text> _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<ItemStack> ITEM_STACK = new TrackedDataHandler<ItemStack>() {
      public void write(PacketByteBuf _snowman, ItemStack _snowman) {
         _snowman.writeItemStack(_snowman);
      }

      public ItemStack read(PacketByteBuf _snowman) {
         return _snowman.readItemStack();
      }

      public ItemStack copy(ItemStack _snowman) {
         return _snowman.copy();
      }
   };
   public static final TrackedDataHandler<Optional<BlockState>> OPTIONAL_BLOCK_STATE = new TrackedDataHandler<Optional<BlockState>>() {
      public void write(PacketByteBuf _snowman, Optional<BlockState> _snowman) {
         if (_snowman.isPresent()) {
            _snowman.writeVarInt(Block.getRawIdFromState(_snowman.get()));
         } else {
            _snowman.writeVarInt(0);
         }
      }

      public Optional<BlockState> read(PacketByteBuf _snowman) {
         int _snowmanx = _snowman.readVarInt();
         return _snowmanx == 0 ? Optional.empty() : Optional.of(Block.getStateFromRawId(_snowmanx));
      }

      public Optional<BlockState> copy(Optional<BlockState> _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Boolean> BOOLEAN = new TrackedDataHandler<Boolean>() {
      public void write(PacketByteBuf _snowman, Boolean _snowman) {
         _snowman.writeBoolean(_snowman);
      }

      public Boolean read(PacketByteBuf _snowman) {
         return _snowman.readBoolean();
      }

      public Boolean copy(Boolean _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<ParticleEffect> PARTICLE = new TrackedDataHandler<ParticleEffect>() {
      public void write(PacketByteBuf _snowman, ParticleEffect _snowman) {
         _snowman.writeVarInt(Registry.PARTICLE_TYPE.getRawId(_snowman.getType()));
         _snowman.write(_snowman);
      }

      public ParticleEffect read(PacketByteBuf _snowman) {
         return this.method_12744(_snowman, (ParticleType<ParticleEffect>)Registry.PARTICLE_TYPE.get(_snowman.readVarInt()));
      }

      private <T extends ParticleEffect> T method_12744(PacketByteBuf _snowman, ParticleType<T> _snowman) {
         return _snowman.getParametersFactory().read(_snowman, _snowman);
      }

      public ParticleEffect copy(ParticleEffect _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<EulerAngle> ROTATION = new TrackedDataHandler<EulerAngle>() {
      public void write(PacketByteBuf _snowman, EulerAngle _snowman) {
         _snowman.writeFloat(_snowman.getPitch());
         _snowman.writeFloat(_snowman.getYaw());
         _snowman.writeFloat(_snowman.getRoll());
      }

      public EulerAngle read(PacketByteBuf _snowman) {
         return new EulerAngle(_snowman.readFloat(), _snowman.readFloat(), _snowman.readFloat());
      }

      public EulerAngle copy(EulerAngle _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<BlockPos> BLOCK_POS = new TrackedDataHandler<BlockPos>() {
      public void write(PacketByteBuf _snowman, BlockPos _snowman) {
         _snowman.writeBlockPos(_snowman);
      }

      public BlockPos read(PacketByteBuf _snowman) {
         return _snowman.readBlockPos();
      }

      public BlockPos copy(BlockPos _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Optional<BlockPos>> OPTIONAL_BLOCK_POS = new TrackedDataHandler<Optional<BlockPos>>() {
      public void write(PacketByteBuf _snowman, Optional<BlockPos> _snowman) {
         _snowman.writeBoolean(_snowman.isPresent());
         if (_snowman.isPresent()) {
            _snowman.writeBlockPos(_snowman.get());
         }
      }

      public Optional<BlockPos> read(PacketByteBuf _snowman) {
         return !_snowman.readBoolean() ? Optional.empty() : Optional.of(_snowman.readBlockPos());
      }

      public Optional<BlockPos> copy(Optional<BlockPos> _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Direction> FACING = new TrackedDataHandler<Direction>() {
      public void write(PacketByteBuf _snowman, Direction _snowman) {
         _snowman.writeEnumConstant(_snowman);
      }

      public Direction read(PacketByteBuf _snowman) {
         return _snowman.readEnumConstant(Direction.class);
      }

      public Direction copy(Direction _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<Optional<UUID>> OPTIONAL_UUID = new TrackedDataHandler<Optional<UUID>>() {
      public void write(PacketByteBuf _snowman, Optional<UUID> _snowman) {
         _snowman.writeBoolean(_snowman.isPresent());
         if (_snowman.isPresent()) {
            _snowman.writeUuid(_snowman.get());
         }
      }

      public Optional<UUID> read(PacketByteBuf _snowman) {
         return !_snowman.readBoolean() ? Optional.empty() : Optional.of(_snowman.readUuid());
      }

      public Optional<UUID> copy(Optional<UUID> _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<CompoundTag> TAG_COMPOUND = new TrackedDataHandler<CompoundTag>() {
      public void write(PacketByteBuf _snowman, CompoundTag _snowman) {
         _snowman.writeCompoundTag(_snowman);
      }

      public CompoundTag read(PacketByteBuf _snowman) {
         return _snowman.readCompoundTag();
      }

      public CompoundTag copy(CompoundTag _snowman) {
         return _snowman.copy();
      }
   };
   public static final TrackedDataHandler<VillagerData> VILLAGER_DATA = new TrackedDataHandler<VillagerData>() {
      public void write(PacketByteBuf _snowman, VillagerData _snowman) {
         _snowman.writeVarInt(Registry.VILLAGER_TYPE.getRawId(_snowman.getType()));
         _snowman.writeVarInt(Registry.VILLAGER_PROFESSION.getRawId(_snowman.getProfession()));
         _snowman.writeVarInt(_snowman.getLevel());
      }

      public VillagerData read(PacketByteBuf _snowman) {
         return new VillagerData(Registry.VILLAGER_TYPE.get(_snowman.readVarInt()), Registry.VILLAGER_PROFESSION.get(_snowman.readVarInt()), _snowman.readVarInt());
      }

      public VillagerData copy(VillagerData _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<OptionalInt> FIREWORK_DATA = new TrackedDataHandler<OptionalInt>() {
      public void write(PacketByteBuf _snowman, OptionalInt _snowman) {
         _snowman.writeVarInt(_snowman.orElse(-1) + 1);
      }

      public OptionalInt read(PacketByteBuf _snowman) {
         int _snowmanx = _snowman.readVarInt();
         return _snowmanx == 0 ? OptionalInt.empty() : OptionalInt.of(_snowmanx - 1);
      }

      public OptionalInt copy(OptionalInt _snowman) {
         return _snowman;
      }
   };
   public static final TrackedDataHandler<EntityPose> ENTITY_POSE = new TrackedDataHandler<EntityPose>() {
      public void write(PacketByteBuf _snowman, EntityPose _snowman) {
         _snowman.writeEnumConstant(_snowman);
      }

      public EntityPose read(PacketByteBuf _snowman) {
         return _snowman.readEnumConstant(EntityPose.class);
      }

      public EntityPose copy(EntityPose _snowman) {
         return _snowman;
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
