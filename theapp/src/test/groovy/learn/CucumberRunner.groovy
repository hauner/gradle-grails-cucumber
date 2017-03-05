package learn

import cucumber.api.junit.Cucumber
import grails.test.mixin.TestFor
import org.junit.runner.RunWith
import spock.lang.Specification


/**
 * a gradle plugin would want to automatically use @RunWith(Cucumber) on the features and
 * provide cucumber configuration via build.gradle.
 *
 * not sure why this extends from Specification, hmmmm....
 */

@RunWith(Cucumber)
//@CucumberOptions(features = "src/test/resources")
//@TestFor(SimpleController)
class CucumberRunner extends Specification {
}
