package learn

import com.github.hauner.gradle.grails.cucumber.CucumberTrait
import com.github.hauner.gradle.grails.cucumber.CucumberWorld
import com.github.hauner.gradle.grails.cucumber.WorldIgnore
import cucumber.api.PendingException
import grails.test.mixin.Mock
import grails.test.mixin.TestFor
import org.spockframework.mock.IInteractionScope
import org.spockframework.runtime.SpecInfoBuilder
import org.spockframework.runtime.SpockRuntime
import spock.lang.Ignore

//import spock.lang.Ignore
import spock.lang.Specification


//this.metaClass.mixin (cucumber.api.groovy.Hooks)
import static cucumber.api.groovy.EN.*


Given (~/^given$/) { ->
    // nop
    //setup() // not called automatically
}


When (~/^when$/) { ->
    name = controller.index()
    stubbed = simpleStub.doSomething()
    simpleMock.doSomethingElse()
}


Then (~/^then$/) { ->
    assert stubbed == "Stubbed!"
    assert name == "index"
    verify()
}


Given (~/^givenDomain$/) { ->
    // nop
    int i = 0
}

When (~/^whenDomain$/) { ->
    def obj = new Simple (name: 'foo')
    obj.save ()
}

Then (~/^thenDomain$/) { ->
    assert Simple.findAll ().size () == 1
}

/**
 * Spock:
 *
 *  //def specInfo = new SpecInfoBuilder(this.class).build ()
 *  //specInfo
 *
 * @CucumberWorld / Cucumber Traits are used to reconfigure the Specification as a
 * cucumber environment
 */


@CucumberWorld
@TestFor(SimpleController)
@Mock([Simple])
//@Ignore
//@WorldIgnore
class CukeDomainWorld extends Specification implements CucumberOriginal {

}


/*
@CucumberWorld
@TestFor(SimpleController)
//@WorldIgnore
//@Ignore
class CukeWorld extends Specification implements CucumberOriginal {
    def simpleStub
    def simpleMock

    def setup () {
        simpleStub = Stub (SimpleService) {
            doSomething() >> "Stubbed!"
        }

        simpleMock = Mock (SimpleService) {
            1 * doSomethingElse()
        }
    }
}*/


trait CucumberOriginal {
//    WorldRunner worldRunner

    /**
     * setup Grails test environment, grails added this to the Cucumber World class and
     * we simply call it to setup grails test stuff.
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


