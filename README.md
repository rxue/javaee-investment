What 
----
This project is making use of Hadoop MapReduce 2 to extract useful financial items from online financial reports with `pdf` formats. The main idea is to read many financial reports line by line in parallel in a Hadoop cluster. It is able to find the issued years' total assets, equity etc. from a list of links to some company's financial reports.

An interesting trick in this project is the application of secondary sort and, the current code is extensible due to the use of interface.

How to create a maven project
-----------------------------
1. `cd` to the directory, where you would like to generate your maven project 
2. `mvn archetype:generate` to generate the new maven project
3. Create a new Repository from your github.com home page with the same name as the generated maven project, i.e. the `artifactId` 
4. `cd` to the generated maven project, then `git init`
5. `git remote add origin git@github.com:rxue/java-hadoop2-practices.git`
   
