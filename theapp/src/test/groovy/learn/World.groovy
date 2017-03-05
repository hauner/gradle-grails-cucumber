package learn

import cucumber.api.Scenario
import cucumber.runtime.groovy.GroovyBackend
import grails.test.mixin.TestFor
import org.junit.Before
import org.spockframework.runtime.BaseSpecRunner
import org.spockframework.runtime.IRunSupervisor
import org.spockframework.runtime.SpecInfoBuilder
import org.spockframework.runtime.model.ErrorInfo
import org.spockframework.runtime.model.FeatureInfo
import org.spockframework.runtime.model.IterationInfo
import org.spockframework.runtime.model.SpecInfo
import spock.lang.Ignore
import spock.lang.Specification

import static cucumber.api.groovy.Hooks.*

/*
@Ignore
@TestFor(SimpleController)
class CukeWorld extends Specification {
    void cukeUp () {
        $testRuntimeJunitAdapter.setUp(this)
    }

    void cukeDown () {
        $testRuntimeJunitAdapter.tearDown(this)
    }
}*/

// $controllerUnitTestMixin @MixinInstance
// $testRuntimeJunitAdapter
// $testRuntimeStaticClassRule
// $testRuntimeSharedClassRule  @ClassRule @Shared @FieldMetadata
// $testRuntimeRule @Rule @FieldMetadata
// $controller
// setupControllerUnderTest

// Before call $testRuntimeJunitAdapter.setUp()
// After call $testRuntimeJunitAdapter.tearDown()

/*
GroovyBackend.getInstance().getGroovyWorld().registerWorld(theWorld)
def result = (articleContent =~ /<!\[CDATA\[(.+)​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​​]]>/​​​​​​​​​​​​​​​​​)[ 0 ]​[ 1 ]
def instance = this.class.classLoader.loadClass( 'Item', true, false )?.newInstance()

    @FeatureMetadata(
        line = 63,
        name = "foo",)
 */

class NullRunSupervisor implements IRunSupervisor {
    @Override
    void beforeSpec (SpecInfo spec) {
        println spec
    }

    @Override
    void beforeFeature (FeatureInfo feature) {
        println feature
    }

    @Override
    void beforeIteration (IterationInfo iteration) {
        println iteration
    }

    @Override
    void afterIteration (IterationInfo iteration) {
        println iteration
    }

    @Override
    void afterFeature (FeatureInfo feature) {
        println feature
    }

    @Override
    void afterSpec (SpecInfo spec) {
        println spec
    }

    @Override
    int error (ErrorInfo error) {
        return 0
    }

    @Override
    void specSkipped (SpecInfo spec) {
        println spec
    }

    @Override
    void featureSkipped (FeatureInfo feature) {
        println feature
    }
}

class WorldRunner {
    Specification world
    BaseSpecRunner runner

    public WorldRunner(Specification world, BaseSpecRunner runner) {
        this.world = world
        this.runner = runner
    }

    void setup () {
//        runner.createSpecInstance (true)
        runner.setupForCucumber ()

        runner.currentInstance = world
        runner.sharedInstance = world
        runner.currentFeature = new FeatureInfo()      // fake it
        runner.currentIteration = new IterationInfo(runner.currentFeature, new Object[0], 0)  // fake it

        runner.getSpecificationContext ().setCurrentSpec (runner.spec);
        runner.getSpecificationContext ().setSharedInstance (world);

        runner.runSharedInitializer ()
        runner.runSetupSpec ()
        runner.runSetup ()
    }

    void cleanup () {
        runner.runCleanup ()
        runner.runCleanupSpec ()
    }
}

def runner


String getWorldClassName (Collection<String> tags) {
    for (String tag : tags) {
        if (tag.matches (/@World\s*\(.+\)/)) {
            String worldClassName = (tag =~ /@World\s*\((.+)\)/) [0] [1]
            println "$tag, $worldClassName"
            return worldClassName
        }
    }
    null
}


Before (0) { Scenario s ->
    String className = getWorldClassName (s.sourceTagNames)
    if (className) {
        def clazz = Class.forName(className)
        def world = clazz.newInstance ()
        world.metaClass.runGrailsSetup = {
            $testRuntimeJunitAdapter.setUp (this)
        }
        world.metaClass.runGrailsTearDown = {
            $testRuntimeJunitAdapter.tearDown (this)
        }


        // todo
        // check class is annotated with @CucumberWorld
        // report error if class does not exist

        GroovyBackend.getInstance ().getGroovyWorld().registerWorld (world)

        // grails first..
//        $testRuntimeJunitAdapter.setUp (world)
        runGrailsSetup ()

        // spock second..
        def specInfo = new SpecInfoBuilder(clazz).build ()
        def baseRunner = new BaseSpecRunner(specInfo, new NullRunSupervisor())
        baseRunner.metaClass.setupForCucumber = {
            println ("setupForCucumber!")
        }
        runner = new WorldRunner((Specification)world, baseRunner)
        runner.setup ()

    } else {
        // use default grails world ???
    }

    /*
    s.sourceTagNames.each { String tag ->

        if (tag.matches (/@World\s*\(.+\)/)) {
            String worldClassName = (tag =~ /@World\s*\((.+)\)/)[0][1]
            println "$tag, $worldClassName"

            def worldClass = Class.forName(worldClassName)
            def world = worldClass.newInstance ()

            // check class is annotated with @CucumberWorld
            // report error if class does not exist
            GroovyBackend.getInstance ().getGroovyWorld().registerWorld (world)

            def specInfo = new SpecInfoBuilder(world.class).build ()
            def baseRunner = new BaseSpecRunner(specInfo, new NullRunSupervisor())
            runner = new WorldRunner(baseRunner)
            runner.setup ()
            return
        }

    }
    */
}

After (0) {
    runner.cleanup ()
//    $testRuntimeJunitAdapter.tearDown (this)
    runGrailsTearDown ()
}



Before (1) {
//    runGrailsSetup ()
}

After (1) {
//    runGrailsTearDown ()
}




class UserWorld {
    String clazz = "UserWorld"
}

World () {
   new UserWorld ()
}

