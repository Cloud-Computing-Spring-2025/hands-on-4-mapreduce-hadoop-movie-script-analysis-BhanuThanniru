
# Analyze the movie script using Hadoop MapReduce

The **Movie Script Analysis** project implements a Hadoop MapReduce program to analyze a movie script dataset. The program processes a movie script where each line represents a character's dialogue.

# Approach and Implementation
The key tasks are:
1. **Most Frequent Words by Character**
   - Mapper (CharacterWordMapper): Extracts each word from the character's dialogue and emits the word with a count of 1.
   - Reducer (CharacterWordReducer): Aggregates the word counts for each character and outputs the total frequency for each word.
2. **Dialogue Length Analysis**
   - Mapper (DialogueLengthMapper): Emits the character's name and the length of their dialogue.
   - Reducer (DialogueLengthReducer): Aggregates the total length of the dialogues spoken by each character and outputs the total length.
3. **Unique Words by Character**
   - Mapper (UniqueWordsMapper): Extracts the unique words from each character's dialogue and emits them.
   - Reducer (UniqueWordsReducer): Aggregates the unique words used by each character and outputs the result.

## Pre-requisites
- Hadoop 3.2.1 installed and configured
- Java 8 or later
- Maven for building the JAR

## Setup and Execution

### 1. **Start the Hadoop Cluster**

Run the following command to start the Hadoop cluster:

```bash
docker compose up -d
```

### 2. **Build the Code**

Build the code using Maven:

```bash
mvn install
```

### 3. **Move JAR File to Shared Folder**

Move the generated JAR file to a shared folder for easy access:

```bash
mv target/*.jar jar_file/
```

### 4. **Copy JAR to Docker Container**

Copy the JAR file to the Hadoop ResourceManager container:

```bash
docker cp /workspaces/hands-on-4-mapreduce-hadoop-movie-script-analysis-BhanuThanniru/jar_file/hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 5. **Move Dataset to Docker Container**

Copy the dataset to the Hadoop ResourceManager container:

```bash
docker cp input/movie_dialogues.txt resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 6. **Connect to Docker Container**

Access the Hadoop ResourceManager container:

```bash
docker exec -it resourcemanager /bin/bash
```

Navigate to the Hadoop directory:

```bash
cd /opt/hadoop-3.2.1/share/hadoop/mapreduce/
```

### 7. **Set Up HDFS**

Create a folder in HDFS for the input dataset:

```bash
hadoop fs -mkdir -p /input/dataset
```

Copy the input dataset to the HDFS folder:

```bash
hadoop fs -put ./movie_dialogues.txt /input/dataset
```

### 8. **Execute the MapReduce Job**

Run your MapReduce job using the following command:

```bash
hadoop jar hands-on2-movie-script-analysis-1.0-SNAPSHOT.jar com.movie.script.analysis /input/dataset/movie_dialogues.txt /output
```

### 9. **View the Output**

To view the output of your MapReduce job, use:

```bash
hadoop fs -cat /output/*
```

### 10. **Copy Output from HDFS to Local OS**

To copy the output from HDFS to your local machine:

1. Use the following command to copy from HDFS:
    ```bash
    hdfs dfs -get /output /opt/hadoop-3.2.1/share/hadoop/mapreduce/
    ```

2. use Docker to copy from the container to your local machine:
   ```bash
   exit 
   ```
    ```bash
    docker cp resourcemanager:/opt/hadoop-3.2.1/share/hadoop/mapreduce/output/ output/
    ```
# Challenge faced
- The path to the jar file mismatch: Resolved this by checking the path exactly where the jar file has been created.


# Sample input:
   ```
   Sirius: I did my waiting! Twelve years of it! In Azkaban!
   Ron: That's good brilliant!
   Voldemort: We could all have been killed - or worse, expelled.
   ```
# Sample output:
# Task 1:
```
- 1
I 1
did 1
my 1
waiting! 1
Twelve 1
years 1
of 1
it! 1
In 1
Azkaban! 1
That's 1
good 1
brilliant! 1
We 1
could 1
all 1
have 1
been 1
killed 1
or 1
worse 1
expelled 1
```
# Task 2:
```
Sirius 49
Ron 21
Voldemort 51
```
# Task 3:
```
Sirius [I, did, my, waiting!, Twelve, years, of, it!, In, Azkaban!]
Ron [That's, good, brilliant!]
Voldemort [We, could, all, have, been, killed, or, -, worse, expelled]
```
