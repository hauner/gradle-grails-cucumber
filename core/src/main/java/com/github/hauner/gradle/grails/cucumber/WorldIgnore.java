package com.github.hauner.gradle.grails.cucumber;


import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.extension.ExtensionAnnotation;
import org.spockframework.runtime.model.FeatureInfo;
import org.spockframework.runtime.model.SpecInfo;
import spock.lang.Ignore;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention (RetentionPolicy.RUNTIME)
@Target ({ElementType.TYPE, ElementType.METHOD})
@ExtensionAnnotation (WorldIgnoreExtension.class)
public @interface WorldIgnore {
    String value() default "";
}

class WorldIgnoreExtension extends AbstractAnnotationDrivenExtension<WorldIgnore> {
  public void visitSpecAnnotation(WorldIgnore ignore, SpecInfo spec) {
    spec.setSkipped(true);
  }

  public void visitFeatureAnnotation(WorldIgnore ignore, FeatureInfo feature) {
    feature.setSkipped(true);
  }
}
