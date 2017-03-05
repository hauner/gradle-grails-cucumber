# grails3-cucumber
Experimental code for a Cucumber (Gradle) Plugin for Grails 3

> Note: this repository does NOT contain anything you could use today to run cucumber code with Grails 3.

As Grails 3 is quite different in comparision to Grails 2 there is no way to move the grails-cucumber plugin from Grails 2
to Grails 3. Grails 3 support requires a completely different approach.

This repository just contains some proof of concept code to run cucumber steps with grails 3 support. 

I consider Grails 3 support as being able to

- use grails specific code in the cucumber step code
- use cucumber on each testing level, i.e. for unit, integration and functional tests 

If you just want to run cucumber on the functional level without grails specific code than this is not what you are looking
for. I haven't tried but the gradle-cucumber plugin may work for you.

## Approach

Grails 3 heavily pimps a test class to make its magic available when running test. The challenge with cucumber is, that
we do not have a test class.

But cucumber has a *World*!
 
What I tried to to do is to use a(n empty) Spock `Specification` as a *World* class (let's call this a world spec).
A world spec is annotated with `@CucumberWorld` as documentation and as an alias for `@Ignore` so spock does not run
the world spec as a test.  By adding grails test annotations to it grails will add the testing support. 

We just have to run the setup and tear down when we execute a cucumber feature.

This can be done via the `Before` and `After` hooks.

Now that we annotate the World as if it is a test class for a specific scenario we need multiple Worlds. Each class under
test would want its own World with a class specific `@TestFor(..)` annotation.

Cucumber does not provide much support for multiple Worlds. To allow any number of worlds the setup code in `Before` does
detect an annotation like `@World(name of a Word class)` tag on a feature, loads the named class and uses it as the world. 

Surprisingly it does need much code.

Unfortunately reporting is an issue. It is somehow messed up.. Sorry, I can't remember exactly what the problem was. 


## What does work, what does not

### works

- it is possible to call a controller added by `@TestFor(..)` from a cucumber step
- it is possible to run the test from an IDE
- it is possible to create and use spock stubs from step code

### works, with manual help 

- it is possible to create, use & verify spock mocks from step code. verify needs to be triggered
manually though.

### does not work

- initializing stub or mock at member declaration
- no proper test reporting 
    

## Code

- `buildSrc`
  
  initial code of a gradle plugin, unused

- `core`

   i started to clean up and extract some code from the test grails application
   
- `theapp`

   the grails test app. the code here is a bit messy. I started to clean up and extract some code. It now
   has a lot of duplicate code under different names, some commented.
   
- `theapp/src/test/groovy/learn/Steps.groovy`, `theapp/src/test/groovy/learn/World.groovy`
 
   this two files are probably the most interesting. 

Most of this code is from end of 2015 - beginning of 2016. If you have any question about the code I will try to answer
it if I can remember... :-)


## last words, todo

To make the interesting code recognizable I plan to cleanup the mess in the code mentioned below, remove duplication
and add some comments/explanations to the interesting code.
