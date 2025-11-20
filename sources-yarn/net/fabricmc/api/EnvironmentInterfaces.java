package net.fabricmc.api;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * Minimal stub for Fabric's {@code EnvironmentInterfaces} annotation.
 */
@Retention(RetentionPolicy.CLASS)
@Target(ElementType.TYPE)
public @interface EnvironmentInterfaces {
    EnvironmentInterface[] value();
}

