package com.github.hauner.gradle.grails.cucumber

import org.spockframework.mock.IInteractionScope

/**
 *
 */
trait CucumberTrait {
    //    WorldRunner worldRunner

        /**
         * setup Grails test environment
         */
        void runGrailsSetup () {
            $testRuntimeJunitAdapter.setUp (this)
        }

        /**
         * tearDown Grails test environment
         */
        void runGrailsTearDown () {
            $testRuntimeJunitAdapter.tearDown (this)
        }

        /**
         * verify Spock-Mocks.
         */
        void verify () {
            def scopes = getSpecificationContext ().getMockController ().scopes
            scopes.each { IInteractionScope scope ->
                scope.verifyInteractions ()
            }
        }
}
