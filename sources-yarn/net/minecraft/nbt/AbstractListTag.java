package net.minecraft.nbt;

import java.util.AbstractList;

public abstract class AbstractListTag<T extends Tag> extends AbstractList<T> implements Tag {
   public AbstractListTag() {
   }

   public abstract T set(int i, T arg);

   public abstract void add(int i, T arg);

   public abstract T remove(int i);

   public abstract boolean setTag(int index, Tag tag);

   public abstract boolean addTag(int index, Tag tag);

   public abstract byte getElementType();
}
