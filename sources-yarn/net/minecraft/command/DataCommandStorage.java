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
      DataCommandStorage.PersistentState lv = new DataCommandStorage.PersistentState(saveKey);
      this.storages.put(namespace, lv);
      return lv;
   }

   public CompoundTag get(Identifier id) {
      String string = id.getNamespace();
      String string2 = getSaveKey(string);
      DataCommandStorage.PersistentState lv = this.stateManager.get(() -> this.createStorage(string, string2), string2);
      return lv != null ? lv.get(id.getPath()) : new CompoundTag();
   }

   public void set(Identifier id, CompoundTag tag) {
      String string = id.getNamespace();
      String string2 = getSaveKey(string);
      this.stateManager.getOrCreate(() -> this.createStorage(string, string2), string2).set(id.getPath(), tag);
   }

   public Stream<Identifier> getIds() {
      return this.storages.entrySet().stream().flatMap(entry -> entry.getValue().getIds(entry.getKey()));
   }

   private static String getSaveKey(String namespace) {
      return "command_storage_" + namespace;
   }

   static class PersistentState extends net.minecraft.world.PersistentState {
      private final Map<String, CompoundTag> map = Maps.newHashMap();

      public PersistentState(String string) {
         super(string);
      }

      @Override
      public void fromTag(CompoundTag tag) {
         CompoundTag lv = tag.getCompound("contents");

         for (String string : lv.getKeys()) {
            this.map.put(string, lv.getCompound(string));
         }
      }

      @Override
      public CompoundTag toTag(CompoundTag tag) {
         CompoundTag lv = new CompoundTag();
         this.map.forEach((string, arg2) -> lv.put(string, arg2.copy()));
         tag.put("contents", lv);
         return tag;
      }

      public CompoundTag get(String name) {
         CompoundTag lv = this.map.get(name);
         return lv != null ? lv : new CompoundTag();
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
         return this.map.keySet().stream().map(string2 -> new Identifier(namespace, string2));
      }
   }
}
