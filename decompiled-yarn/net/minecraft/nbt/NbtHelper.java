package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import java.util.Optional;
import java.util.UUID;
import java.util.Map.Entry;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.state.State;
import net.minecraft.state.StateManager;
import net.minecraft.state.property.Property;
import net.minecraft.util.ChatUtil;
import net.minecraft.util.Identifier;
import net.minecraft.util.dynamic.DynamicSerializableUuid;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.registry.Registry;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public final class NbtHelper {
   private static final Logger LOGGER = LogManager.getLogger();

   @Nullable
   public static GameProfile toGameProfile(CompoundTag tag) {
      String _snowman = null;
      UUID _snowmanx = null;
      if (tag.contains("Name", 8)) {
         _snowman = tag.getString("Name");
      }

      if (tag.containsUuid("Id")) {
         _snowmanx = tag.getUuid("Id");
      }

      try {
         GameProfile _snowmanxx = new GameProfile(_snowmanx, _snowman);
         if (tag.contains("Properties", 10)) {
            CompoundTag _snowmanxxx = tag.getCompound("Properties");

            for (String _snowmanxxxx : _snowmanxxx.getKeys()) {
               ListTag _snowmanxxxxx = _snowmanxxx.getList(_snowmanxxxx, 10);

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanxxxxx.size(); _snowmanxxxxxx++) {
                  CompoundTag _snowmanxxxxxxx = _snowmanxxxxx.getCompound(_snowmanxxxxxx);
                  String _snowmanxxxxxxxx = _snowmanxxxxxxx.getString("Value");
                  if (_snowmanxxxxxxx.contains("Signature", 8)) {
                     _snowmanxx.getProperties().put(_snowmanxxxx, new com.mojang.authlib.properties.Property(_snowmanxxxx, _snowmanxxxxxxxx, _snowmanxxxxxxx.getString("Signature")));
                  } else {
                     _snowmanxx.getProperties().put(_snowmanxxxx, new com.mojang.authlib.properties.Property(_snowmanxxxx, _snowmanxxxxxxxx));
                  }
               }
            }
         }

         return _snowmanxx;
      } catch (Throwable var11) {
         return null;
      }
   }

   public static CompoundTag fromGameProfile(CompoundTag tag, GameProfile profile) {
      if (!ChatUtil.isEmpty(profile.getName())) {
         tag.putString("Name", profile.getName());
      }

      if (profile.getId() != null) {
         tag.putUuid("Id", profile.getId());
      }

      if (!profile.getProperties().isEmpty()) {
         CompoundTag _snowman = new CompoundTag();

         for (String _snowmanx : profile.getProperties().keySet()) {
            ListTag _snowmanxx = new ListTag();

            for (com.mojang.authlib.properties.Property _snowmanxxx : profile.getProperties().get(_snowmanx)) {
               CompoundTag _snowmanxxxx = new CompoundTag();
               _snowmanxxxx.putString("Value", _snowmanxxx.getValue());
               if (_snowmanxxx.hasSignature()) {
                  _snowmanxxxx.putString("Signature", _snowmanxxx.getSignature());
               }

               _snowmanxx.add(_snowmanxxxx);
            }

            _snowman.put(_snowmanx, _snowmanxx);
         }

         tag.put("Properties", _snowman);
      }

      return tag;
   }

   @VisibleForTesting
   public static boolean matches(@Nullable Tag standard, @Nullable Tag subject, boolean equalValue) {
      if (standard == subject) {
         return true;
      } else if (standard == null) {
         return true;
      } else if (subject == null) {
         return false;
      } else if (!standard.getClass().equals(subject.getClass())) {
         return false;
      } else if (standard instanceof CompoundTag) {
         CompoundTag _snowman = (CompoundTag)standard;
         CompoundTag _snowmanx = (CompoundTag)subject;

         for (String _snowmanxx : _snowman.getKeys()) {
            Tag _snowmanxxx = _snowman.get(_snowmanxx);
            if (!matches(_snowmanxxx, _snowmanx.get(_snowmanxx), equalValue)) {
               return false;
            }
         }

         return true;
      } else if (standard instanceof ListTag && equalValue) {
         ListTag _snowman = (ListTag)standard;
         ListTag _snowmanx = (ListTag)subject;
         if (_snowman.isEmpty()) {
            return _snowmanx.isEmpty();
         } else {
            for (int _snowmanxxx = 0; _snowmanxxx < _snowman.size(); _snowmanxxx++) {
               Tag _snowmanxxxx = _snowman.get(_snowmanxxx);
               boolean _snowmanxxxxx = false;

               for (int _snowmanxxxxxx = 0; _snowmanxxxxxx < _snowmanx.size(); _snowmanxxxxxx++) {
                  if (matches(_snowmanxxxx, _snowmanx.get(_snowmanxxxxxx), equalValue)) {
                     _snowmanxxxxx = true;
                     break;
                  }
               }

               if (!_snowmanxxxxx) {
                  return false;
               }
            }

            return true;
         }
      } else {
         return standard.equals(subject);
      }
   }

   public static IntArrayTag fromUuid(UUID uuid) {
      return new IntArrayTag(DynamicSerializableUuid.toIntArray(uuid));
   }

   public static UUID toUuid(Tag tag) {
      if (tag.getReader() != IntArrayTag.READER) {
         throw new IllegalArgumentException(
            "Expected UUID-Tag to be of type " + IntArrayTag.READER.getCrashReportName() + ", but found " + tag.getReader().getCrashReportName() + "."
         );
      } else {
         int[] _snowman = ((IntArrayTag)tag).getIntArray();
         if (_snowman.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + _snowman.length + ".");
         } else {
            return DynamicSerializableUuid.toUuid(_snowman);
         }
      }
   }

   public static BlockPos toBlockPos(CompoundTag tag) {
      return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
   }

   public static CompoundTag fromBlockPos(BlockPos pos) {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putInt("X", pos.getX());
      _snowman.putInt("Y", pos.getY());
      _snowman.putInt("Z", pos.getZ());
      return _snowman;
   }

   public static BlockState toBlockState(CompoundTag tag) {
      if (!tag.contains("Name", 8)) {
         return Blocks.AIR.getDefaultState();
      } else {
         Block _snowman = Registry.BLOCK.get(new Identifier(tag.getString("Name")));
         BlockState _snowmanx = _snowman.getDefaultState();
         if (tag.contains("Properties", 10)) {
            CompoundTag _snowmanxx = tag.getCompound("Properties");
            StateManager<Block, BlockState> _snowmanxxx = _snowman.getStateManager();

            for (String _snowmanxxxx : _snowmanxx.getKeys()) {
               Property<?> _snowmanxxxxx = _snowmanxxx.getProperty(_snowmanxxxx);
               if (_snowmanxxxxx != null) {
                  _snowmanx = withProperty(_snowmanx, _snowmanxxxxx, _snowmanxxxx, _snowmanxx, tag);
               }
            }
         }

         return _snowmanx;
      }
   }

   private static <S extends State<?, S>, T extends Comparable<T>> S withProperty(
      S state, Property<T> property, String key, CompoundTag propertiesTag, CompoundTag mainTag
   ) {
      Optional<T> _snowman = property.parse(propertiesTag.getString(key));
      if (_snowman.isPresent()) {
         return state.with(property, _snowman.get());
      } else {
         LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", key, propertiesTag.getString(key), mainTag.toString());
         return state;
      }
   }

   public static CompoundTag fromBlockState(BlockState state) {
      CompoundTag _snowman = new CompoundTag();
      _snowman.putString("Name", Registry.BLOCK.getId(state.getBlock()).toString());
      ImmutableMap<Property<?>, Comparable<?>> _snowmanx = state.getEntries();
      if (!_snowmanx.isEmpty()) {
         CompoundTag _snowmanxx = new CompoundTag();
         UnmodifiableIterator var4 = _snowmanx.entrySet().iterator();

         while (var4.hasNext()) {
            Entry<Property<?>, Comparable<?>> _snowmanxxx = (Entry<Property<?>, Comparable<?>>)var4.next();
            Property<?> _snowmanxxxx = _snowmanxxx.getKey();
            _snowmanxx.putString(_snowmanxxxx.getName(), nameValue(_snowmanxxxx, _snowmanxxx.getValue()));
         }

         _snowman.put("Properties", _snowmanxx);
      }

      return _snowman;
   }

   private static <T extends Comparable<T>> String nameValue(Property<T> property, Comparable<?> value) {
      return property.name((T)value);
   }

   public static CompoundTag update(DataFixer fixer, DataFixTypes fixTypes, CompoundTag tag, int oldVersion) {
      return update(fixer, fixTypes, tag, oldVersion, SharedConstants.getGameVersion().getWorldVersion());
   }

   public static CompoundTag update(DataFixer fixer, DataFixTypes fixTypes, CompoundTag tag, int oldVersion, int targetVersion) {
      return (CompoundTag)fixer.update(fixTypes.getTypeReference(), new Dynamic(NbtOps.INSTANCE, tag), oldVersion, targetVersion).getValue();
   }
}
