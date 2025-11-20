package net.minecraft.test;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;

public class TestSet {
   private final Collection<GameTest> tests = Lists.newArrayList();
   @Nullable
   private Collection<TestListener> field_25303 = Lists.newArrayList();

   public TestSet() {
   }

   public TestSet(Collection<GameTest> tests) {
      this.tests.addAll(tests);
   }

   public void add(GameTest test) {
      this.tests.add(test);
      this.field_25303.forEach(test::addListener);
   }

   public void addListener(TestListener listener) {
      this.field_25303.add(listener);
      this.tests.forEach(_snowmanx -> _snowmanx.addListener(listener));
   }

   public void method_29407(Consumer<GameTest> _snowman) {
      this.addListener(new TestListener() {
         @Override
         public void onStarted(GameTest test) {
         }

         @Override
         public void onFailed(GameTest test) {
            _snowman.accept(test);
         }
      });
   }

   public int getFailedRequiredTestCount() {
      return (int)this.tests.stream().filter(GameTest::isFailed).filter(GameTest::isRequired).count();
   }

   public int getFailedOptionalTestCount() {
      return (int)this.tests.stream().filter(GameTest::isFailed).filter(GameTest::isOptional).count();
   }

   public int getCompletedTestCount() {
      return (int)this.tests.stream().filter(GameTest::isCompleted).count();
   }

   public boolean failed() {
      return this.getFailedRequiredTestCount() > 0;
   }

   public boolean hasFailedOptionalTests() {
      return this.getFailedOptionalTestCount() > 0;
   }

   public int getTestCount() {
      return this.tests.size();
   }

   public boolean isDone() {
      return this.getCompletedTestCount() == this.getTestCount();
   }

   public String getResultString() {
      StringBuffer _snowman = new StringBuffer();
      _snowman.append('[');
      this.tests.forEach(_snowmanx -> {
         if (!_snowmanx.isStarted()) {
            _snowman.append(' ');
         } else if (_snowmanx.isPassed()) {
            _snowman.append('+');
         } else if (_snowmanx.isFailed()) {
            _snowman.append((char)(_snowmanx.isRequired() ? 'X' : 'x'));
         } else {
            _snowman.append('_');
         }
      });
      _snowman.append(']');
      return _snowman.toString();
   }

   @Override
   public String toString() {
      return this.getResultString();
   }
}
