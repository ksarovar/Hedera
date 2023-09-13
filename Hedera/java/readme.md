To set up a Gradle project, add a dependency, and run a single file, follow these steps:

Install Gradle (if not already installed):
If you don't have Gradle installed on your system, you can download and install it from the official Gradle website: Gradle Installation Guide.

Create a New Gradle Project:
You can either create a new Gradle project using the gradle init command or manually create the project structure. For simplicity, we'll use the gradle init command:

bash
Copy code
mkdir MyGradleProject
cd MyGradleProject
gradle init --type java-application
This will generate a basic Gradle Java application project structure.

Add a Dependency:
To add a dependency to your Gradle project, open the build.gradle file in your project directory. Add the dependency in the dependencies block. For example, let's add a dependency on the Apache Commons Lang library:

gradle
Copy code
dependencies {
    implementation group: 'org.apache.commons', name: 'commons-lang3', version: '3.12.0'
}
Save the build.gradle file.

Sync Your Project:
Run the following Gradle command to sync your project and download the added dependencies:

bash
Copy code
gradle build
Create a Java File:
Create a Java source file (e.g., MyClass.java) in the src/main/java directory of your project. Add your code to this file.

java
Copy code
public class MyClass {
    public static void main(String[] args) {
        System.out.println("Hello, Gradle!");
    }
}
Run the Single File:
You can run your Java class using the Gradle run task. In your terminal, run:

bash
Copy code
gradle run
Gradle will compile your Java code and execute the main method of your MyClass class. You should see the output on the console.

That's it! You've set up a Gradle project, added a dependency, and executed a single Java file within the project. You can further customize your Gradle build to suit your specific requirements.




