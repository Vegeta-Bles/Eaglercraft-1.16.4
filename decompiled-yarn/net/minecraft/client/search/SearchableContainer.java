package net.minecraft.client.search;

public interface SearchableContainer<T> extends Searchable<T> {
   void add(T var1);

   void clear();

   void reload();
}
