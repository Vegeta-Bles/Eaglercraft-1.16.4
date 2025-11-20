/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.annotations.VisibleForTesting
 *  com.google.common.collect.ImmutableMap
 *  com.mojang.authlib.GameProfile
 *  com.mojang.authlib.properties.Property
 *  com.mojang.datafixers.DataFixer
 *  com.mojang.serialization.Dynamic
 *  com.mojang.serialization.DynamicOps
 *  javax.annotation.Nullable
 *  org.apache.logging.log4j.LogManager
 *  org.apache.logging.log4j.Logger
 */
package net.minecraft.nbt;

import com.google.common.annotations.VisibleForTesting;
import com.google.common.collect.ImmutableMap;
import com.mojang.authlib.GameProfile;
import com.mojang.datafixers.DataFixer;
import com.mojang.serialization.Dynamic;
import com.mojang.serialization.DynamicOps;
import java.util.Map;
import java.util.Optional;
import java.util.UUID;
import javax.annotation.Nullable;
import net.minecraft.SharedConstants;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.Blocks;
import net.minecraft.datafixer.DataFixTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.nbt.IntArrayTag;
import net.minecraft.nbt.ListTag;
import net.minecraft.nbt.NbtOps;
import net.minecraft.nbt.Tag;
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
        UUID _snowman2 = null;
        if (tag.contains("Name", 8)) {
            string = tag.getString("Name");
        }
        if (tag.containsUuid("Id")) {
            _snowman2 = tag.getUuid("Id");
        }
        try {
            GameProfile gameProfile = new GameProfile(_snowman2, string);
            if (tag.contains("Properties", 10)) {
                CompoundTag compoundTag = tag.getCompound("Properties");
                for (String string2 : compoundTag.getKeys()) {
                    ListTag listTag = compoundTag.getList(string2, 10);
                    for (int i = 0; i < listTag.size(); ++i) {
                        CompoundTag compoundTag2 = listTag.getCompound(i);
                        String _snowman3 = compoundTag2.getString("Value");
                        if (compoundTag2.contains("Signature", 8)) {
                            gameProfile.getProperties().put((Object)string2, (Object)new com.mojang.authlib.properties.Property(string2, _snowman3, compoundTag2.getString("Signature")));
                            continue;
                        }
                        gameProfile.getProperties().put((Object)string2, (Object)new com.mojang.authlib.properties.Property(string2, _snowman3));
                    }
                }
            }
            return gameProfile;
        }
        catch (Throwable throwable) {
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
            CompoundTag compoundTag = new CompoundTag();
            for (String string : profile.getProperties().keySet()) {
                ListTag listTag = new ListTag();
                for (com.mojang.authlib.properties.Property property : profile.getProperties().get((Object)string)) {
                    CompoundTag compoundTag2 = new CompoundTag();
                    compoundTag2.putString("Value", property.getValue());
                    if (property.hasSignature()) {
                        compoundTag2.putString("Signature", property.getSignature());
                    }
                    listTag.add(compoundTag2);
                }
                compoundTag.put(string, listTag);
            }
            tag.put("Properties", compoundTag);
        }
        return tag;
    }

    @VisibleForTesting
    public static boolean matches(@Nullable Tag standard, @Nullable Tag subject, boolean equalValue) {
        if (standard == subject) {
            return true;
        }
        if (standard == null) {
            return true;
        }
        if (subject == null) {
            return false;
        }
        if (!standard.getClass().equals(subject.getClass())) {
            return false;
        }
        if (standard instanceof CompoundTag) {
            CompoundTag compoundTag = (CompoundTag)standard;
            _snowman = (CompoundTag)subject;
            for (String string : compoundTag.getKeys()) {
                Tag tag = compoundTag.get(string);
                if (NbtHelper.matches(tag, _snowman.get(string), equalValue)) continue;
                return false;
            }
            return true;
        }
        if (standard instanceof ListTag && equalValue) {
            ListTag listTag = (ListTag)standard;
            _snowman = (ListTag)subject;
            if (listTag.isEmpty()) {
                return _snowman.isEmpty();
            }
            for (int i = 0; i < listTag.size(); ++i) {
                Tag tag = listTag.get(i);
                boolean _snowman2 = false;
                for (int j = 0; j < _snowman.size(); ++j) {
                    if (!NbtHelper.matches(tag, _snowman.get(j), equalValue)) continue;
                    _snowman2 = true;
                    break;
                }
                if (_snowman2) continue;
                return false;
            }
            return true;
        }
        return standard.equals(subject);
    }

    public static IntArrayTag fromUuid(UUID uuid) {
        return new IntArrayTag(DynamicSerializableUuid.toIntArray(uuid));
    }

    public static UUID toUuid(Tag tag) {
        if (tag.getReader() != IntArrayTag.READER) {
            throw new IllegalArgumentException("Expected UUID-Tag to be of type " + IntArrayTag.READER.getCrashReportName() + ", but found " + tag.getReader().getCrashReportName() + ".");
        }
        int[] nArray = ((IntArrayTag)tag).getIntArray();
        if (nArray.length != 4) {
            throw new IllegalArgumentException("Expected UUID-Array to be of length 4, but found " + nArray.length + ".");
        }
        return DynamicSerializableUuid.toUuid(nArray);
    }

    public static BlockPos toBlockPos(CompoundTag tag) {
        return new BlockPos(tag.getInt("X"), tag.getInt("Y"), tag.getInt("Z"));
    }

    public static CompoundTag fromBlockPos(BlockPos pos) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putInt("X", pos.getX());
        compoundTag.putInt("Y", pos.getY());
        compoundTag.putInt("Z", pos.getZ());
        return compoundTag;
    }

    public static BlockState toBlockState(CompoundTag tag) {
        if (!tag.contains("Name", 8)) {
            return Blocks.AIR.getDefaultState();
        }
        Block block = Registry.BLOCK.get(new Identifier(tag.getString("Name")));
        BlockState _snowman2 = block.getDefaultState();
        if (tag.contains("Properties", 10)) {
            CompoundTag compoundTag = tag.getCompound("Properties");
            StateManager<Block, BlockState> _snowman3 = block.getStateManager();
            for (String string : compoundTag.getKeys()) {
                Property<?> property = _snowman3.getProperty(string);
                if (property == null) continue;
                _snowman2 = NbtHelper.withProperty(_snowman2, property, string, compoundTag, tag);
            }
        }
        return _snowman2;
    }

    private static <S extends State<?, S>, T extends Comparable<T>> S withProperty(S state, Property<T> property, String key, CompoundTag propertiesTag, CompoundTag mainTag) {
        Optional<T> optional = property.parse(propertiesTag.getString(key));
        if (optional.isPresent()) {
            return (S)((State)state.with(property, (Comparable)((Comparable)optional.get())));
        }
        LOGGER.warn("Unable to read property: {} with value: {} for blockstate: {}", (Object)key, (Object)propertiesTag.getString(key), (Object)mainTag.toString());
        return state;
    }

    public static CompoundTag fromBlockState(BlockState state) {
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putString("Name", Registry.BLOCK.getId(state.getBlock()).toString());
        ImmutableMap<Property<?>, Comparable<?>> _snowman2 = state.getEntries();
        if (!_snowman2.isEmpty()) {
            _snowman = new CompoundTag();
            for (Map.Entry entry : _snowman2.entrySet()) {
                Property property = (Property)entry.getKey();
                _snowman.putString(property.getName(), NbtHelper.nameValue(property, (Comparable)entry.getValue()));
            }
            compoundTag.put("Properties", _snowman);
        }
        return compoundTag;
    }

    private static <T extends Comparable<T>> String nameValue(Property<T> property, Comparable<?> value) {
        return property.name(value);
    }

    public static CompoundTag update(DataFixer fixer, DataFixTypes fixTypes, CompoundTag tag, int oldVersion) {
        return NbtHelper.update(fixer, fixTypes, tag, oldVersion, SharedConstants.getGameVersion().getWorldVersion());
    }

    public static CompoundTag update(DataFixer fixer, DataFixTypes fixTypes, CompoundTag tag, int oldVersion, int targetVersion) {
        return (CompoundTag)fixer.update(fixTypes.getTypeReference(), new Dynamic((DynamicOps)NbtOps.INSTANCE, (Object)tag), oldVersion, targetVersion).getValue();
    }
}

