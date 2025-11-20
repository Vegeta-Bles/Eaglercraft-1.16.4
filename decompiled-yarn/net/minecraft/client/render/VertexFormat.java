package net.minecraft.client.render;

import com.google.common.collect.ImmutableList;
import com.google.common.collect.UnmodifiableIterator;
import com.mojang.blaze3d.systems.RenderSystem;
import it.unimi.dsi.fastutil.ints.IntArrayList;
import it.unimi.dsi.fastutil.ints.IntList;
import java.util.List;
import java.util.stream.Collectors;

public class VertexFormat {
   private final ImmutableList<VertexFormatElement> elements;
   private final IntList offsets = new IntArrayList();
   private final int size;

   public VertexFormat(ImmutableList<VertexFormatElement> _snowman) {
      this.elements = _snowman;
      int _snowmanx = 0;
      UnmodifiableIterator var3 = _snowman.iterator();

      while (var3.hasNext()) {
         VertexFormatElement _snowmanxx = (VertexFormatElement)var3.next();
         this.offsets.add(_snowmanx);
         _snowmanx += _snowmanxx.getSize();
      }

      this.size = _snowmanx;
   }

   @Override
   public String toString() {
      return "format: " + this.elements.size() + " elements: " + this.elements.stream().map(Object::toString).collect(Collectors.joining(" "));
   }

   public int getVertexSizeInteger() {
      return this.getVertexSize() / 4;
   }

   public int getVertexSize() {
      return this.size;
   }

   public ImmutableList<VertexFormatElement> getElements() {
      return this.elements;
   }

   @Override
   public boolean equals(Object o) {
      if (this == o) {
         return true;
      } else if (o != null && this.getClass() == o.getClass()) {
         VertexFormat _snowman = (VertexFormat)o;
         return this.size != _snowman.size ? false : this.elements.equals(_snowman.elements);
      } else {
         return false;
      }
   }

   @Override
   public int hashCode() {
      return this.elements.hashCode();
   }

   public void startDrawing(long pointer) {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(() -> this.startDrawing(pointer));
      } else {
         int _snowman = this.getVertexSize();
         List<VertexFormatElement> _snowmanx = this.getElements();

         for (int _snowmanxx = 0; _snowmanxx < _snowmanx.size(); _snowmanxx++) {
            _snowmanx.get(_snowmanxx).startDrawing(pointer + (long)this.offsets.getInt(_snowmanxx), _snowman);
         }
      }
   }

   public void endDrawing() {
      if (!RenderSystem.isOnRenderThread()) {
         RenderSystem.recordRenderCall(this::endDrawing);
      } else {
         UnmodifiableIterator var1 = this.getElements().iterator();

         while (var1.hasNext()) {
            VertexFormatElement _snowman = (VertexFormatElement)var1.next();
            _snowman.endDrawing();
         }
      }
   }
}
