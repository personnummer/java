# Personnummer

[![Build Status](https://travis-ci.org/personnummer/java.svg?branch=master)](https://travis-ci.org/personnummer/java)

Validate Swedish social security numbers 

## Example

```java
class Test {
    public void main(String[] args){
        Personnummer.valid(6403273813L);     // => True
        Personnummer.valid("19130401+2931"); // => True 
    }
}
```

See [`src/test/java/PersonnummerTest.java`](src/test/java/PersonnummerTest.java) for more examples.

## License

[MIT](LICENSE)
