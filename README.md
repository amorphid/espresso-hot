espresso-hot
============

Clone of Android instrumentation testing library [Espresso][1], with dependencies updated to newer versions

```
Dagger is at 1.2.1
Hamcrest is at 1.3
Guava is at 16.0
```

onData has had its signature slightly changed to accept whatever Matcher you throw into it, 
and will wrap it with something that checks and converts the type, using TypeSafeMatcher from
hamcrest. This was just to get around a bunch of nasty generics-related issues that occurred
upon moving up to hamcrest 1.3, but does make things slightly easier in that you no longer
need to write things like:

```java
onData(allOf(is(instanceOf(Map.class)), hasValue("item: 50"))
```

... (or subclass BoundedMatcher to tidy it up), and can instead simply write:

```java
onData(hasValue("item: 50"))
```

I've noticed that some of the tests fail when building this, which they did when I cloned the original.
Haven't looked at why yet; it builds the jars I need, and I have enough confidence it's working OK.

[1]: https://code.google.com/p/android-test-kit/
