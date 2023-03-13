# Personnummer

[![GitHub Workflow Status](https://img.shields.io/github/actions/workflow/status/personnummer/java/test.yml?branch=master)](https://github.com/personnummer/java/actions)

Validate Swedish personal identity numbers.

## Installation

Add the package to your maven or gradle configuration.  
If you prefer to use the package from github rather than maven-central,
add the repository as well.

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
    // If using maven central
    mavenCentral()
    // If you wish to use github
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
    Personnummer.valid("121212+1212");     // => True
    Personnummer.valid("20121212-1212");   // => True
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

### Get date

```java
(new Personnummer("1212121212")).getDate();
//=> 2012-12-12T00:00
(new Personnummer("9001010017")).getDate();
//=> 1990-01-01T00:00
```

See `src/test//PersonnummerTest.java` for more examples.

## License

[MIT](https://github.com/personnummer/java/blob/master/LICENSE)
