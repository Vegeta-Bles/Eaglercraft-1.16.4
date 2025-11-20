package net.minecraft.client.resource.metadata;

import com.google.common.collect.Lists;
import com.google.common.collect.Sets;
import com.mojang.datafixers.util.Pair;
import java.util.List;
import java.util.Set;

public class AnimationResourceMetadata {
   public static final AnimationResourceMetadataReader READER = new AnimationResourceMetadataReader();
   public static final AnimationResourceMetadata EMPTY = new AnimationResourceMetadata(Lists.newArrayList(), -1, -1, 1, false) {
      @Override
      public Pair<Integer, Integer> method_24141(int _snowman, int _snowman) {
         return Pair.of(_snowman, _snowman);
      }
   };
   private final List<AnimationFrameResourceMetadata> frames;
   private final int width;
   private final int height;
   private final int defaultFrameTime;
   private final boolean interpolate;

   public AnimationResourceMetadata(List<AnimationFrameResourceMetadata> frames, int width, int height, int defaultFrameTime, boolean interpolate) {
      this.frames = frames;
      this.width = width;
      this.height = height;
      this.defaultFrameTime = defaultFrameTime;
      this.interpolate = interpolate;
   }

   private static boolean method_24142(int _snowman, int _snowman) {
      return _snowman / _snowman * _snowman == _snowman;
   }

   public Pair<Integer, Integer> method_24141(int _snowman, int _snowman) {
      Pair<Integer, Integer> _snowmanxx = this.method_24143(_snowman, _snowman);
      int _snowmanxxx = (Integer)_snowmanxx.getFirst();
      int _snowmanxxxx = (Integer)_snowmanxx.getSecond();
      if (method_24142(_snowman, _snowmanxxx) && method_24142(_snowman, _snowmanxxxx)) {
         return _snowmanxx;
      } else {
         throw new IllegalArgumentException(String.format("Image size %s,%s is not multiply of frame size %s,%s", _snowman, _snowman, _snowmanxxx, _snowmanxxxx));
      }
   }

   private Pair<Integer, Integer> method_24143(int _snowman, int _snowman) {
      if (this.width != -1) {
         return this.height != -1 ? Pair.of(this.width, this.height) : Pair.of(this.width, _snowman);
      } else if (this.height != -1) {
         return Pair.of(_snowman, this.height);
      } else {
         int _snowmanxx = Math.min(_snowman, _snowman);
         return Pair.of(_snowmanxx, _snowmanxx);
      }
   }

   public int getHeight(int _snowman) {
      return this.height == -1 ? _snowman : this.height;
   }

   public int getWidth(int _snowman) {
      return this.width == -1 ? _snowman : this.width;
   }

   public int getFrameCount() {
      return this.frames.size();
   }

   public int getDefaultFrameTime() {
      return this.defaultFrameTime;
   }

   public boolean shouldInterpolate() {
      return this.interpolate;
   }

   private AnimationFrameResourceMetadata getFrame(int frameIndex) {
      return this.frames.get(frameIndex);
   }

   public int getFrameTime(int frameIndex) {
      AnimationFrameResourceMetadata _snowman = this.getFrame(frameIndex);
      return _snowman.usesDefaultFrameTime() ? this.defaultFrameTime : _snowman.getTime();
   }

   public int getFrameIndex(int frameIndex) {
      return this.frames.get(frameIndex).getIndex();
   }

   public Set<Integer> getFrameIndexSet() {
      Set<Integer> _snowman = Sets.newHashSet();

      for (AnimationFrameResourceMetadata _snowmanx : this.frames) {
         _snowman.add(_snowmanx.getIndex());
      }

      return _snowman;
   }
}
