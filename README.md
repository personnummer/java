# Personnummer

[![GitHub Workflow Status](https://img.shields.io/github/workflow/status/personnummer/java/Test)](https://github.com/personnummer/java/actions)

Validate Swedish social security numbers.

## Installation

Add the github repository as a Maven or Gradle repository:  

```xml
<dependency>
  <groupId>dev.personnummer</groupId>
  <artifactId>personnummer</artifactId>
  <version>3.*.*</version>
</dependency> 
```

```groovy
plugins {
    id 'maven'
}

repositories {
    maven {
      url "https://github.com/personnummer/java:personnummer"
    }
}

dependencies {
    configuration("dev.personnummer:personnummer")
}
```

For more information on how to install and authenticate with github packages, check [this link](https://help.github.com/en/packages/using-github-packages-with-your-projects-ecosystem/configuring-apache-maven-for-use-with-github-packages).

## Examples

### Validation

```java
import dev.personnummer.*;

class Test 
{
  public void TestValidation() 
  {
    Personnummer.valid("191212121212");    // => True
    Personnummer.valid("12121+21212");     // => True
    Personnummer.valid("2012121-21212");   // => True
  }
}
```

### Format

```java
// Short format (YYMMDD-XXXX)
(new Personnummer("1212121212")).format();
// => 121212-1212

// Short format for 100+ years old
(new Personnummer("191212121212")).format();
//=> 121212+1212

// Long format (YYYYMMDDXXXX)
Personnummer.parse("1212121212").format(true);
//=> 201212121212
```

### Age

```java
(new Personnummer("1212121212")).getAge();
//=> 7
```

### Get sex

```java
(new Personnummer("1212121212")).isMale();
//=> true
Personnummer.parse("1212121212").isFemale();
//=> false
```

See `src/test//PersonnummerTest.java` for more examples.

## License

```
MIT License

Copyright (c) 2017-2020 - Personnummer and Contributors

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.

```
