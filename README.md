## Merge sort application

This application allows you to sort text files and write result to another.   
   
**Parameters to run:**
- Sort mode. (-a for ascending sort, -d for descending sort). 
This is not required parameter, application will apply ascending sort by default; 
- Data type (-i for integer, -s for string). This is required parameter;
- Name of output file. This is required parameter;
- Names of input files. At least one exists file is required;

## First App run

Inside the root directory, do a: 

```
mvn clean install
```

Then run merge-sort-app.jar with parameters.


## Examples

Integer ascending sort:
```
Java -jar merge-sort-app.jar -i -a out.txt in.txt
```
String ascending sort:
```
Java -jar merge-sort-app.jar -s out.txt in1.txt in3.txt
```
String descending sort:
```
Java -jar merge-sort-app.jar -d -s out.txt in1.txt in3.txt
```
