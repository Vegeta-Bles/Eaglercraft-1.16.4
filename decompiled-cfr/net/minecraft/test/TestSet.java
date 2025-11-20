/*
 * Decompiled with CFR 0.152.
 * 
 * Could not load the following classes:
 *  com.google.common.collect.Lists
 *  javax.annotation.Nullable
 */
package net.minecraft.test;

import com.google.common.collect.Lists;
import java.util.Collection;
import java.util.function.Consumer;
import javax.annotation.Nullable;
import net.minecraft.test.GameTest;
import net.minecraft.test.TestListener;

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
        this.tests.forEach(gameTest -> gameTest.addListener(listener));
    }

    public void method_29407(Consumer<GameTest> consumer) {
        this.addListener(new TestListener(this, consumer){
            final /* synthetic */ Consumer field_25304;
            final /* synthetic */ TestSet field_25305;
            {
                this.field_25305 = testSet;
                this.field_25304 = consumer;
            }

            public void onStarted(GameTest test) {
            }

            public void onFailed(GameTest test) {
                this.field_25304.accept(test);
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
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append('[');
        this.tests.forEach(gameTest -> {
            if (!gameTest.isStarted()) {
                stringBuffer.append(' ');
            } else if (gameTest.isPassed()) {
                stringBuffer.append('+');
            } else if (gameTest.isFailed()) {
                stringBuffer.append(gameTest.isRequired() ? (char)'X' : (char)'x');
            } else {
                stringBuffer.append('_');
            }
        });
        stringBuffer.append(']');
        return stringBuffer.toString();
    }

    public String toString() {
        return this.getResultString();
    }
}

