package com.movie.script.analysis;

import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Mapper;

import java.io.IOException;
import java.util.StringTokenizer;

public class CharacterWordMapper extends Mapper<Object, Text, Text, IntWritable> {

    private final static IntWritable one = new IntWritable(1);
    private Text word = new Text();
    private Text characterWord = new Text();

    @Override
    public void map(Object key, Text value, Context context) throws IOException, InterruptedException {
        String line = value.toString();
        
        if (line.contains(":")) {
            String[] lineParts = line.split(":");
            String character = lineParts[0].trim();
            String dialogue = lineParts[1].trim();
            
            StringTokenizer strTokenizer = new StringTokenizer(dialogue);
            while (strTokenizer.hasMoreTokens()) {
                word.set(strTokenizer.nextToken().toLowerCase());
                context.write(word, one);
            }
        }

    }
}
