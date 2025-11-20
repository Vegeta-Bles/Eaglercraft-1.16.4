package net.minecraft.util.profiler;

import java.util.function.Supplier;

public interface Profiler {
   void startTick();

   void endTick();

   void push(String location);

   void push(Supplier<String> locationGetter);

   void pop();

   void swap(String location);

   void swap(Supplier<String> locationGetter);

   void visit(String marker);

   void visit(Supplier<String> markerGetter);

   static Profiler union(Profiler _snowman, Profiler _snowman) {
      if (_snowman == DummyProfiler.INSTANCE) {
         return _snowman;
      } else {
         return _snowman == DummyProfiler.INSTANCE ? _snowman : new Profiler() {
            @Override
            public void startTick() {
               _snowman.startTick();
               _snowman.startTick();
            }

            @Override
            public void endTick() {
               _snowman.endTick();
               _snowman.endTick();
            }

            @Override
            public void push(String location) {
               _snowman.push(location);
               _snowman.push(location);
            }

            @Override
            public void push(Supplier<String> locationGetter) {
               _snowman.push(locationGetter);
               _snowman.push(locationGetter);
            }

            @Override
            public void pop() {
               _snowman.pop();
               _snowman.pop();
            }

            @Override
            public void swap(String location) {
               _snowman.swap(location);
               _snowman.swap(location);
            }

            @Override
            public void swap(Supplier<String> locationGetter) {
               _snowman.swap(locationGetter);
               _snowman.swap(locationGetter);
            }

            @Override
            public void visit(String marker) {
               _snowman.visit(marker);
               _snowman.visit(marker);
            }

            @Override
            public void visit(Supplier<String> markerGetter) {
               _snowman.visit(markerGetter);
               _snowman.visit(markerGetter);
            }
         };
      }
   }
}
