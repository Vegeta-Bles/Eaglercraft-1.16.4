package net.minecraft.nbt;

import java.util.AbstractList;

public abstract class AbstractListTag<T extends Tag> extends AbstractList<T> implements Tag {
   public AbstractListTag() {
   }

   public abstract T set(int var1, T var2);

   public abstract void add(int var1, T var2);

   public abstract T remove(int var1);

   public abstract boolean setTag(int index, Tag tag);

   public abstract boolean addTag(int index, Tag tag);

   public abstract byte getElementType();
}
