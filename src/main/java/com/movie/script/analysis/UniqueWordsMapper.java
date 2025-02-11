package com.movie.script.analysis;

import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.HashSet;
import java.util.StringTokenizer;

public class UniqueWordsMapper extends Mapper<Object, Text, Text, Text> {

    private Text character = new Text();
    private Text word = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {

        String line = value.toString();
        if (line.contains(":")) {
            String[] lineParts = line.split(":");
            character.set(lineParts[0].trim());
            String dialogue = lineParts[1].trim();
            HashSet<String> wordsSet = new HashSet<>();
            StringTokenizer strTokenizer = new StringTokenizer(dialogue);
            while (strTokenizer.hasMoreTokens()) {
                wordsSet.add(strTokenizer.nextToken().toLowerCase());
            }
            word.set(wordsSet.toString());
            context.write(character, word);
        }
    }
}
