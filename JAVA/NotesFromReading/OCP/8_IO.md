# IO

## Understanding Files and Directories

A file is record within a file system that stores user and system data.

A directory is a record within a file system that contains files as well as other directories.

the root directory is the topmost directory in the file system, from which all files and directories inherit. In Windows, it is denoted with a drive name such as
c:\, while on Linux it is denoted with a single forward slash /.

A path is a String representation of a file or directory within a file system.

path separator character that is used between directory entries.

The *absolute path* of a file or directory is the full path from the root directory to the file or directory, including all subdirectories that contain the file or directory.

The *relative path* of a file or directory is the path from the current working directory to file or directory.

For convenience, Java offers two options to retrieve the local separator character: *a system property* and *a static variable* defined in the File class.

```java
    System.out.println(System.getProperty("file.separator")); // sys ppt
    System.out.println(java.io.File.separator); // static var in File class
```

## The File Class

most commonly used: java.io.File class, or File class for short.

An instance of a File class represents the pathname of a particular file or directory on the file system.

The File class cannot read or write data within a file, although it can be passed as a reference to many *stream classes* to read or write data.

### Creating a File Object

A File object often is initialized with String containing either an absolute or relative path to the file or directory within the file system.

```java
    File file = new File("abs/rel path");
    // OR by joining paths.
    File parent = new File("/home/smith");
    // If the parent object happened to be null, then it would be skipped and the method would revert to our single String constructor.
    File child = new File(parent,"data/zoo.txt");
```

### Working with a File Object

Few most commonly used methods:

![File object Methods](../img/FileClassMethods.png)

## Introducing Streams

The I/O streams that in this chapter are data streams and completely unrelated to the new Stream API.

### Stream Fundamentals

The contents of a file may be accessed or written via a stream , which is a list of data elements presented sequentially.

Each type of stream segments data into a "wave" or "block" in a particular way.

Some stream classes read or write data as individual byte values.

Other stream classes read or write individual characters or strings of characters.

### Stream Nomenclature

The java.io API provides numerous classes for creating, accessing, and manipulating streams.

#### Byte Streams vs. Character Streams

The java.io API defines two sets of classes for reading and writing streams:
those with Stream in their name and those with Reader/Writer in their name.

- Differences between Streams and Readers/Writers
  1. The stream classes are used for inputting and outputting all types of **binary or byte data**.
  2. The reader and writer classes are used for inputting and outputting only **character and String data**.

#### Input and Output

Most Input stream classes have a corresponding Output class and vice versa.

Example: FileOutputStream class writes data that can be read by a FileInputStream.

It follows, then, that most Reader classes have a corresponding Writer class.

Example: FileWriter class writes data that can be read by a FileReader.

- exceptions to the rules above:
  1. PrintWriter has no accompanying PrintReader class.
  2. PrintStream class has no corresponding InputStream class.

#### Low-Level vs. High-Level Streams

the java.io API also segments streams into *low-level* and *high-level* streams.

A low-level stream connects directly with the source of the data, such as a file, an array, or a String.
Low-level streams process the raw data or resource and are accessed in a direct and unfiltered manner.

A high-level stream is built on top of another stream using wrapping. Wrapping is the process by which an instance is passed to the constructor of another
class and operations on the resulting instance are filtered and applied to the original instance.

```java
try (
    // FileReader is the low-level stream reader
    // BufferedReader is the high-level stream that takes a FileReader as input
    BufferedReader bufferedReader = new BufferedReader(new FileReader("zoo-data.txt"))) {
    System.out.println(bufferedReader.readLine());
}
```

> For the exam, the only low-level stream classes we need to be familiar with are the ones that operate on files. The rest of the non-abstract stream classes are all high-level streams.

#### Stream Base Classes

The java.io library defines four **abstract classes** that are the parents of all stream classes defined within the API:

- InputStream
- OutputStream
- Reader
- Writer

#### Decoding Java I/O Class Names

Table 8.2 describes those java.io streams you should be familiar with for the exam.

![IO classes](../img/IOStreamClasses.png)

#### Common Stream Operations

streams are considered resources, it is imperative that they be closed after they are used lest they lead to resource leaks.

- Closing the Stream

    closing can accomplish by calling the close() method in a finally block or using the try-with-resource syntax.

- Flushing the Stream

    When data is written to an OutputStream, the underlying operating system does not necessarily guarantee that the data will make it to the file immediately.

    If the data is cached in memory and the application terminates unexpectedly, the data would be lost, because it was never written to the file system.

    Java provides a *flush()* method, which requests that all accumulated data be written immediately to disk.

    *close()* method will automatically call *flush()* method.

- Marking the Stream
    The InputStream and Reader classes include mark(int) and reset() methods to move the stream back to an earlier position.
    the markSupported() method, which returns true only if mark() is supported. trying to call mark(int) or reset() on a class that does not support these operations will throw an exception at runtime.
    if you call reset() after you have passed your mark() read limit, an exception may be thrown at runtime since the marked position may become invalidated.

- Skipping over Data
    The InputStream and Reader classes include a skip(long) method,skips over a certain number of bytes. It returns a long value, which indicates
    the number of bytes that were actually skipped. If the return value is zero or negative,then end of the stream was reached.

## Working with Streams

### The FileInputStream and FileOutputStream Classes

The data in a **FileInputStream** object is commonly accessed by successive calls to the *read()* method until a value of *-1* is returned, indicating that the end of the stream—in this case the end of the file has been reached.

A **FileOutputStream** object is accessed by writing successive bytes using the *write(int)* method.

#### The BufferedInputStream and BufferedOutputStream Classes

we can enhance the performance by wrapping the FileInputStream and FileOutputStream classes with the BufferedInputStream and BufferedOutputStream classes.

Instead of reading the data one byte at a time, we use the underlying read(byte[]) method of BufferedInputStream, which returns the number of bytes read into the provided byte array.

- The number of bytes read is important for two reasons.
  1. if the value returned is 0, then we have reached the end of the file so stop reading from the BufferedInputStream.
  2. the last read of the file will likely only partially fill the byte array, since it is unlikely for the file size to be an exact multiple of our buffer array size.

The data is written into the BufferedOutputStream using the write(byte[],int,int) method, which takes as input a byte array, an offset, and a length value, respectively.

### The FileReader and FileWriter classes

Like the FileInputStream and FileOutputStream classes, the FileReader and FileWriter classes contain read() and write() methods, respectively.
These methods read/write char values instead of byte values.

The Writer class, which FileWriter inherits from, offers a *write(String)* method that allows a String object to be written directly to the stream. Using FileReader also allows you to pair it with BufferedReader in order to use the very convenient *readLine()* method.

only the Reader/Writer solution gives us structured access to the text data and also takes care of *character encoding*.

### The ObjectInputStream and ObjectOutputStream Classes

- serialization
  The process of converting an in-memory object to a stored data format

- deserialization
  process of converting stored data into an object

#### The Serializable Interface

The Serializable interface is a tagging or marker interface, which means that it does not have any methods associated with it.

In order to serialize objects using the java.io API, the class they belong to must implement the java.io.Serializable interface.

The purpose of implementing the Serializable interface is to inform any process attempting to serialize the object that you have taken the proper steps to make the object serializable, which involves making sure that the classes of all instance variables within the object are also marked Serializable.

A process attempting to serialize an object will throw a NotSerializableException if the class or one of its contained classes does not properly implement the Serializable interface.

Instance variable marked by transient keyword will be lost during serialization process.

Besides transient instance variables, static class members will also be ignored during the serialization and deserialization process. This should follow logically, as static class variables do not belong to one particular instance.

#### Serializing and Deserializing Objects

The java.io API provides two stream classes for object serialization and deserialization called ObjectInputStream and ObjectOutputStream.

The ObjectOutputStream class includes a method to serialize the object to the stream called *void writeObject(Object)*. If the provided object is not Serializable, or it contains an embedded reference to a class that is not Serializable or not marked *transient*, a *NotSerializableException* will be thrown at runtime.

For the deserialization process, the ObjectInputStream class includes a deserialization method that returns an object called *readObject()*. Notice that the return type of this method is the generic type java.lang.Object, indicating that the object will have to be cast explicitly at runtime to be used.

ObjectOutputStream and ObjectInputStream support reading and writing *null* objects. Therefore, it is important to check for null values whenreading from a serialized data stream.

#### Understanding Object Creation

For the exam, how a deserialized object is created ?

When you deserialize an object, the constructor of the serialized class is not called.

In fact, Java calls the first no-arg constructor for the first nonserializable parent class, skipping the constructors of
any serialized class in between. 

Furthermore, any static variables or default initializations are ignored.

### The PrintStream and PrintWriter Classes

The PrintStream and PrintWriter classes are high-level stream classes that write formatted representation of Java objects to a text-based output stream.

PrintStream -> OutputStream instances -> writes data as bytes.
PrintWriter -> Writer instance -> write data as characters.

For convenience, both of these classes include constructors that can open and write to files directly.

Furthermore, the PrintWriter class even has a constructor that takes an OutputStream as input, allowing you to wrap a PrintWriter class around an OutputStream.

- *System.out* and *System.err* are actually PrintStream objects.

> For the exam, we should be familiar with the print() , println() , format() , and printf() methods.

print-based methods do not throw any checked exceptions.

write() method -> throws IOException.(checked)

*checkError()* , that can be used to detect the presence of a problem after attempting to write data to the stream.

- *print()* method \
    print() is overloaded with all Java primitives as well as String and Object. handles character encoding automatically. uses valueOf() applied to an object calls the object's *toString()* method or returns *null* if the object is not set.

- *println()* method \
    virtually identical to the print() methods, except that they insert a line break after the String value is written. The classes also include a version of println() that takes no arguments, which terminates the current line by writing a line separator. \
    uses System.getProperty("line.separator") for line breaks.

- *format() and printf()* methods \
    format() method in PrintStream and PrintWriter takes a String , an optional locale, and a set of arguments, and it writes a formatted String to the stream based on the input. \
    For convenience, as well as to make C developers feel more at home in Java, the PrintStream and PrintWriter APIs also include a set of printf() methods, which are straight pass-through methods to the format() methods.

### Review of Stream Classes

- abstract parent classes and their concrete implementations
  1. InputStream
    - low-level streams \
      FileInputStream
    - high-level streams \
      FilterInputStream <- BufferedInputStream \
      ObjectInputStream
  2. Reader
    - low-level streams \
      FileReader
    - high-level streams \
      BufferedReader, InputStreamReader
  3. OutputStream
    - low-level streams \
      FileOutputStream
    - high-level streams \
      FilterOutputStream <- BufferedOutputStream, PrintStream \
      ObjectOutputStream
  4. Writer
    - low-level streams \
      FileWriter
    - high-level streams \
      BufferedWriter, OutputStreamWriter, PrintWriter

## Interacting with Users

The *Console* class was introduced in Java 6 as a more evolved form of the *System.in* and *System.out* stream classes.

### The Old Way

System.in returns an InputStream - used to retrieve text input from the user.

It can be chained to a BufferedReader to allow input that terminates with the Enter key.
BufferedReader is a Reader so wrap System.in object using InputStreamReader.

```java
    BufferedReader reader = new BufferedReader(new InputStreamReader(System.in));
```

