package net.minecraft.command;

import com.google.common.collect.Maps;
import java.util.Map;
import java.util.stream.Stream;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.util.Identifier;
import net.minecraft.world.PersistentStateManager;

public class DataCommandStorage {
   private final Map<String, DataCommandStorage.PersistentState> storages = Maps.newHashMap();
   private final PersistentStateManager stateManager;

   public DataCommandStorage(PersistentStateManager stateManager) {
      this.stateManager = stateManager;
   }

   private DataCommandStorage.PersistentState createStorage(String namespace, String saveKey) {
      DataCommandStorage.PersistentState _snowman = new DataCommandStorage.PersistentState(saveKey);
      this.storages.put(namespace, _snowman);
      return _snowman;
   }

   public CompoundTag get(Identifier id) {
      String _snowman = id.getNamespace();
      String _snowmanx = getSaveKey(_snowman);
      DataCommandStorage.PersistentState _snowmanxx = this.stateManager.get(() -> this.createStorage(_snowman, _snowman), _snowmanx);
      return _snowmanxx != null ? _snowmanxx.get(id.getPath()) : new CompoundTag();
   }

   public void set(Identifier id, CompoundTag tag) {
      String _snowman = id.getNamespace();
      String _snowmanx = getSaveKey(_snowman);
      this.stateManager.getOrCreate(() -> this.createStorage(_snowman, _snowman), _snowmanx).set(id.getPath(), tag);
   }

   public Stream<Identifier> getIds() {
      return this.storages.entrySet().stream().flatMap(_snowman -> _snowman.getValue().getIds(_snowman.getKey()));
   }

   private static String getSaveKey(String namespace) {
      return "command_storage_" + namespace;
   }

   static class PersistentState extends net.minecraft.world.PersistentState {
      private final Map<String, CompoundTag> map = Maps.newHashMap();

      public PersistentState(String _snowman) {
         super(_snowman);
      }

      @Override
      public void fromTag(CompoundTag tag) {
         CompoundTag _snowman = tag.getCompound("contents");

         for (String _snowmanx : _snowman.getKeys()) {
            this.map.put(_snowmanx, _snowman.getCompound(_snowmanx));
         }
      }

      @Override
      public CompoundTag toTag(CompoundTag tag) {
         CompoundTag _snowman = new CompoundTag();
         this.map.forEach((_snowmanx, _snowmanxx) -> _snowman.put(_snowmanx, _snowmanxx.copy()));
         tag.put("contents", _snowman);
         return tag;
      }

      public CompoundTag get(String name) {
         CompoundTag _snowman = this.map.get(name);
         return _snowman != null ? _snowman : new CompoundTag();
      }

      public void set(String name, CompoundTag tag) {
         if (tag.isEmpty()) {
            this.map.remove(name);
         } else {
            this.map.put(name, tag);
         }

         this.markDirty();
      }

      public Stream<Identifier> getIds(String namespace) {
         return this.map.keySet().stream().map(_snowmanx -> new Identifier(namespace, _snowmanx));
      }
   }
}
