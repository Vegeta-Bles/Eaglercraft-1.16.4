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
      String string = null;
      UUID uUID = null;
      if (tag.contains("Name", 8)) {
         string = tag.getString("Name");
      }

      if (tag.containsUuid("Id")) {
         uUID = tag.getUuid("Id");
      }

      try {
         GameProfile gameProfile = new GameProfile(uUID, string);
         if (tag.contains("Properties", 10)) {
            CompoundTag lv = tag.getCompound("Properties");

            for (String string2 : lv.getKeys()) {
               ListTag lv2 = lv.getList(string2, 10);

               for (int i = 0; i < lv2.size(); i++) {
                  CompoundTag lv3 = lv2.getCompound(i);
                  String string3 = lv3.getString("Value");
                  if (lv3.contains("Signature", 8)) {
                     gameProfile.getProperties().put(string2, new com.mojang.authlib.properties.Property(string2, string3, lv3.getString("Signature")));
                  } else {
                     gameProfile.getProperties().put(string2, new com.mojang.authlib.properties.Property(string2, string3));
                  }
               }
            }
         }

         return gameProfile;
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
         CompoundTag lv = new CompoundTag();

         for (String string : profile.getProperties().keySet()) {
            ListTag lv2 = new ListTag();

            for (com.mojang.authlib.properties.Property property : profile.getProperties().get(string)) {
               CompoundTag lv3 = new CompoundTag();
               lv3.putString("Value", property.getValue());
               if (property.hasSignature()) {
                  lv3.putString("Signature", property.getSignature());
               }

               lv2.add(lv3);
            }

            lv.put(string, lv2);
         }

         tag.put("Properties", lv);
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
         CompoundTag lv = (CompoundTag)standard;
         CompoundTag lv2 = (CompoundTag)subject;

         for (String string : lv.getKeys()) {
            Tag lv3 = lv.get(string);
            if (!matches(lv3, lv2.get(string), equalValue)) {
               return false;
            }
         }

         return true;
      } else if (standard instanceof ListTag && equalValue) {
         ListTag lv4 = (ListTag)standard;
         ListTag lv5 = (ListTag)subject;
         if (lv4.isEmpty()) {
            return lv5.isEmpty();
         } else {
            for (int i = 0; i < lv4.size(); i++) {
               Tag lv6 = lv4.get(i);
               boolean bl2 = false;

               for (int j = 0; j < lv5.size(); j++) {
                  if (matches(lv6, lv5.get(j), equalValue)) {
                     bl2 = true;
                     break;
                  }
               }

               if (!bl2) {
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
         int[] is = ((IntArrayTag)tag).getIntArray();
         if (is.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + is.length + ".");
         } else {
            return DynamicSerializableUuid.toUuid(is);
         }
      }
   }

   public static BlockPos toBlockPos(CompoundTag tag) {
      return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
   }

   public static CompoundTag fromBlockPos(BlockPos pos) {
      CompoundTag lv = new CompoundTag();
      lv.putInt("X", pos.getX());
      lv.putInt("Y", pos.getY());
      lv.putInt("Z", pos.getZ());
      return lv;
   }

   public static BlockState toBlockState(CompoundTag tag) {
      if (!tag.contains("Name", 8)) {
         return Blocks.AIR.getDefaultState();
      } else {
         Block lv = Registry.BLOCK.get(new Identifier(tag.getString("Name")));
         BlockState lv2 = lv.getDefaultState();
         if (tag.contains("Properties", 10)) {
            CompoundTag lv3 = tag.getCompound("Properties");
            StateManager<Block, BlockState> lv4 = lv.getStateManager();

            for (String string : lv3.getKeys()) {
               Property<?> lv5 = lv4.getProperty(string);
               if (lv5 != null) {
                  lv2 = withProperty(lv2, lv5, string, lv3, tag);
               }
            }
         }

         return lv2;
      }
   }

   private static <S extends State<?, S>, T extends Comparable<T>> S withProperty(
      S state, Property<T> property, String key, CompoundTag propertiesTag, CompoundTag mainTag
   ) {
      Optional<T> optional = property.parse(propertiesTag.getString(key));
      if (optional.isPresent()) {
         return state.with(property, optional.get());
      } else {
         LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", key, propertiesTag.getString(key), mainTag.toString());
         return state;
      }
   }

   public static CompoundTag fromBlockState(BlockState state) {
      CompoundTag lv = new CompoundTag();
      lv.putString("Name", Registry.BLOCK.getId(state.getBlock()).toString());
      ImmutableMap<Property<?>, Comparable<?>> immutableMap = state.getEntries();
      if (!immutableMap.isEmpty()) {
         CompoundTag lv2 = new CompoundTag();
         UnmodifiableIterator var4 = immutableMap.entrySet().iterator();

         while (var4.hasNext()) {
            Entry<Property<?>, Comparable<?>> entry = (Entry<Property<?>, Comparable<?>>)var4.next();
            Property<?> lv3 = entry.getKey();
            lv2.putString(lv3.getName(), nameValue(lv3, entry.getValue()));
         }

         lv.put("Properties", lv2);
      }

      return lv;
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
