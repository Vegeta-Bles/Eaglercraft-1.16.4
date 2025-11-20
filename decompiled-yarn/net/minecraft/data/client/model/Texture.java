package net.minecraft.data.client.model;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;
import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraft.util.Identifier;
import net.minecraft.util.registry.Registry;

public class Texture {
   private final Map<TextureKey, Identifier> entries = Maps.newHashMap();
   private final Set<TextureKey> inherited = Sets.newHashSet();

   public Texture() {
   }

   public Texture put(TextureKey key, Identifier id) {
      this.entries.put(key, id);
      return this;
   }

   public Stream<TextureKey> getInherited() {
      return this.inherited.stream();
   }

   public Texture inherit(TextureKey parent, TextureKey child) {
      this.entries.put(child, this.entries.get(parent));
      this.inherited.add(child);
      return this;
   }

   public Identifier getTexture(TextureKey key) {
      for (TextureKey _snowman = key; _snowman != null; _snowman = _snowman.getParent()) {
         Identifier _snowmanx = this.entries.get(_snowman);
         if (_snowmanx != null) {
            return _snowmanx;
         }
      }

      throw new IllegalStateException("Can't find texture for slot " + key);
   }

   public Texture copyAndAdd(TextureKey key, Identifier id) {
      Texture _snowman = new Texture();
      _snowman.entries.putAll(this.entries);
      _snowman.inherited.addAll(this.inherited);
      _snowman.put(key, id);
      return _snowman;
   }

   public static Texture all(Block block) {
      Identifier _snowman = getId(block);
      return all(_snowman);
   }

   public static Texture texture(Block block) {
      Identifier _snowman = getId(block);
      return texture(_snowman);
   }

   public static Texture texture(Identifier id) {
      return new Texture().put(TextureKey.TEXTURE, id);
   }

   public static Texture all(Identifier id) {
      return new Texture().put(TextureKey.ALL, id);
   }

   public static Texture cross(Block block) {
      return of(TextureKey.CROSS, getId(block));
   }

   public static Texture cross(Identifier id) {
      return of(TextureKey.CROSS, id);
   }

   public static Texture plant(Block block) {
      return of(TextureKey.PLANT, getId(block));
   }

   public static Texture plant(Identifier id) {
      return of(TextureKey.PLANT, id);
   }

   public static Texture rail(Block block) {
      return of(TextureKey.RAIL, getId(block));
   }

   public static Texture rail(Identifier id) {
      return of(TextureKey.RAIL, id);
   }

   public static Texture wool(Block block) {
      return of(TextureKey.WOOL, getId(block));
   }

   public static Texture stem(Block block) {
      return of(TextureKey.STEM, getId(block));
   }

   public static Texture stemAndUpper(Block stem, Block upper) {
      return new Texture().put(TextureKey.STEM, getId(stem)).put(TextureKey.UPPERSTEM, getId(upper));
   }

   public static Texture pattern(Block block) {
      return of(TextureKey.PATTERN, getId(block));
   }

   public static Texture fan(Block block) {
      return of(TextureKey.FAN, getId(block));
   }

   public static Texture crop(Identifier id) {
      return of(TextureKey.CROP, id);
   }

   public static Texture paneAndTopForEdge(Block block, Block top) {
      return new Texture().put(TextureKey.PANE, getId(block)).put(TextureKey.EDGE, getSubId(top, "_top"));
   }

   public static Texture of(TextureKey key, Identifier id) {
      return new Texture().put(key, id);
   }

   public static Texture sideEnd(Block block) {
      return new Texture().put(TextureKey.SIDE, getSubId(block, "_side")).put(TextureKey.END, getSubId(block, "_top"));
   }

   public static Texture sideAndTop(Block block) {
      return new Texture().put(TextureKey.SIDE, getSubId(block, "_side")).put(TextureKey.TOP, getSubId(block, "_top"));
   }

   public static Texture sideAndEndForTop(Block block) {
      return new Texture().put(TextureKey.SIDE, getId(block)).put(TextureKey.END, getSubId(block, "_top"));
   }

   public static Texture sideEnd(Identifier side, Identifier end) {
      return new Texture().put(TextureKey.SIDE, side).put(TextureKey.END, end);
   }

   public static Texture sideTopBottom(Block block) {
      return new Texture()
         .put(TextureKey.SIDE, getSubId(block, "_side"))
         .put(TextureKey.TOP, getSubId(block, "_top"))
         .put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
   }

   public static Texture wallSideTopBottom(Block block) {
      Identifier _snowman = getId(block);
      return new Texture()
         .put(TextureKey.WALL, _snowman)
         .put(TextureKey.SIDE, _snowman)
         .put(TextureKey.TOP, getSubId(block, "_top"))
         .put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
   }

   public static Texture method_27168(Block _snowman) {
      Identifier _snowmanx = getId(_snowman);
      return new Texture().put(TextureKey.WALL, _snowmanx).put(TextureKey.SIDE, _snowmanx).put(TextureKey.END, getSubId(_snowman, "_top"));
   }

   public static Texture topBottom(Block block) {
      return new Texture().put(TextureKey.TOP, getSubId(block, "_top")).put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
   }

   public static Texture particle(Block block) {
      return new Texture().put(TextureKey.PARTICLE, getId(block));
   }

   public static Texture particle(Identifier id) {
      return new Texture().put(TextureKey.PARTICLE, id);
   }

   public static Texture fire0(Block block) {
      return new Texture().put(TextureKey.FIRE, getSubId(block, "_0"));
   }

   public static Texture fire1(Block block) {
      return new Texture().put(TextureKey.FIRE, getSubId(block, "_1"));
   }

   public static Texture lantern(Block block) {
      return new Texture().put(TextureKey.LANTERN, getId(block));
   }

   public static Texture torch(Block block) {
      return new Texture().put(TextureKey.TORCH, getId(block));
   }

   public static Texture torch(Identifier id) {
      return new Texture().put(TextureKey.TORCH, id);
   }

   public static Texture particle(Item item) {
      return new Texture().put(TextureKey.PARTICLE, getId(item));
   }

   public static Texture sideFrontBack(Block block) {
      return new Texture()
         .put(TextureKey.SIDE, getSubId(block, "_side"))
         .put(TextureKey.FRONT, getSubId(block, "_front"))
         .put(TextureKey.BACK, getSubId(block, "_back"));
   }

   public static Texture sideFrontTopBottom(Block block) {
      return new Texture()
         .put(TextureKey.SIDE, getSubId(block, "_side"))
         .put(TextureKey.FRONT, getSubId(block, "_front"))
         .put(TextureKey.TOP, getSubId(block, "_top"))
         .put(TextureKey.BOTTOM, getSubId(block, "_bottom"));
   }

   public static Texture sideFrontTop(Block block) {
      return new Texture()
         .put(TextureKey.SIDE, getSubId(block, "_side"))
         .put(TextureKey.FRONT, getSubId(block, "_front"))
         .put(TextureKey.TOP, getSubId(block, "_top"));
   }

   public static Texture sideFrontEnd(Block block) {
      return new Texture()
         .put(TextureKey.SIDE, getSubId(block, "_side"))
         .put(TextureKey.FRONT, getSubId(block, "_front"))
         .put(TextureKey.END, getSubId(block, "_end"));
   }

   public static Texture top(Block top) {
      return new Texture().put(TextureKey.TOP, getSubId(top, "_top"));
   }

   public static Texture frontSideWithCustomBottom(Block block, Block bottom) {
      return new Texture()
         .put(TextureKey.PARTICLE, getSubId(block, "_front"))
         .put(TextureKey.DOWN, getId(bottom))
         .put(TextureKey.UP, getSubId(block, "_top"))
         .put(TextureKey.NORTH, getSubId(block, "_front"))
         .put(TextureKey.EAST, getSubId(block, "_side"))
         .put(TextureKey.SOUTH, getSubId(block, "_side"))
         .put(TextureKey.WEST, getSubId(block, "_front"));
   }

   public static Texture frontTopSide(Block frontTopSideBlock, Block downBlock) {
      return new Texture()
         .put(TextureKey.PARTICLE, getSubId(frontTopSideBlock, "_front"))
         .put(TextureKey.DOWN, getId(downBlock))
         .put(TextureKey.UP, getSubId(frontTopSideBlock, "_top"))
         .put(TextureKey.NORTH, getSubId(frontTopSideBlock, "_front"))
         .put(TextureKey.SOUTH, getSubId(frontTopSideBlock, "_front"))
         .put(TextureKey.EAST, getSubId(frontTopSideBlock, "_side"))
         .put(TextureKey.WEST, getSubId(frontTopSideBlock, "_side"));
   }

   public static Texture method_27167(Block _snowman) {
      return new Texture().put(TextureKey.LIT_LOG, getSubId(_snowman, "_log_lit")).put(TextureKey.FIRE, getSubId(_snowman, "_fire"));
   }

   public static Texture layer0(Item item) {
      return new Texture().put(TextureKey.LAYER0, getId(item));
   }

   public static Texture layer0(Block block) {
      return new Texture().put(TextureKey.LAYER0, getId(block));
   }

   public static Texture layer0(Identifier id) {
      return new Texture().put(TextureKey.LAYER0, id);
   }

   public static Identifier getId(Block block) {
      Identifier _snowman = Registry.BLOCK.getId(block);
      return new Identifier(_snowman.getNamespace(), "block/" + _snowman.getPath());
   }

   public static Identifier getSubId(Block block, String suffix) {
      Identifier _snowman = Registry.BLOCK.getId(block);
      return new Identifier(_snowman.getNamespace(), "block/" + _snowman.getPath() + suffix);
   }

   public static Identifier getId(Item item) {
      Identifier _snowman = Registry.ITEM.getId(item);
      return new Identifier(_snowman.getNamespace(), "item/" + _snowman.getPath());
   }

   public static Identifier getSubId(Item item, String suffix) {
      Identifier _snowman = Registry.ITEM.getId(item);
      return new Identifier(_snowman.getNamespace(), "item/" + _snowman.getPath() + suffix);
   }
}
