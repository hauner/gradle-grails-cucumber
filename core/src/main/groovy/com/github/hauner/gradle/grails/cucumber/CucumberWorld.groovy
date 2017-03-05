package com.github.hauner.gradle.grails.cucumber

import org.spockframework.runtime.extension.AbstractAnnotationDrivenExtension;
import org.spockframework.runtime.extension.ExtensionAnnotation
import org.spockframework.runtime.extension.builtin.IgnoreExtension
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.SpecInfo
import spock.lang.IgnoreIf

import java.lang.annotation.ElementType
import java.lang.annotation.Retention
import java.lang.annotation.RetentionPolicy
import java.lang.annotation.Target

/**
 * used to provide a test environment to run cucumber specs. behaves like an @Ignore
 */
@Retention(RetentionPolicy.RUNTIME)
@Target([ElementType.TYPE, ElementType.METHOD])
@ExtensionAnnotation(CucumberWorldExtension)
public @interface CucumberWorld {
}

public class CucumberWorldExtension extends AbstractAnnotationDrivenExtension<CucumberWorld> {
  public void visitSpecAnnotation(CucumberWorld ignore, SpecInfo spec) {
    spec.setSkipped(true);
  }
}
