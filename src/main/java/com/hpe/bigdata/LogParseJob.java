package com.hpe.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.FilterFileSystem;
import org.apache.hadoop.fs.Path;
import org.apache.hadoop.io.IntWritable;
import org.apache.hadoop.io.Text;
import org.apache.hadoop.mapreduce.Job;
import org.apache.hadoop.mapreduce.Mapper;
import org.apache.hadoop.mapreduce.Reducer;
import org.apache.hadoop.mapreduce.lib.input.FileInputFormat;
import org.apache.hadoop.mapreduce.lib.output.FileOutputFormat;
import org.apache.hadoop.util.GenericOptionsParser;

import java.io.IOException;
import java.util.StringTokenizer;

public class LogParseJob {
    public static class TokenizerMapper
            extends Mapper<Object, Text, Text, Text> {

        private Text logType = new Text();
        private Text logContent = new Text();

        private JobLogParser jp = new JobLogParser();

        public void map(Object key, Text value, Context context
        ) throws IOException, InterruptedException {
            //StringTokenizer itr = new StringTokenizer(value.toString());
            //while (itr.hasMoreTokens()) {
            //    word.set(itr.nextToken());
            String[] result = jp.parse(value.toString());
            logType.set(result[0]);
            logContent.set(result[1]);

            context.write(logType, logContent);
        }
    }

    public static class IntSumReducer
            extends Reducer<Text, Text, Text, Text> {

        private Text result = new Text();

        public void reduce(Text key, Iterable<Text> values,
                           Context context
        ) throws IOException, InterruptedException {

            for (Text val : values) {
                result.set(val);
                context.write(null, result);
            }

        }
    }

    public static void main(String[] args) throws Exception {
        Configuration conf = new Configuration();

        FileSystem fs = FileSystem.get(conf);

        if (fs.exists(new Path("hdfs://mycluster/user/hpe/out")))
            fs.deleteOnExit(new Path("hdfs://mycluster/user/hpe/out"));
        fs.close();

        String[] otherArgs = new GenericOptionsParser(conf, args).getRemainingArgs();
        if (otherArgs.length < 2) {
            System.err.println("Usage: wordcount <in> [<in>...] <out>");
            System.exit(2);
        }
        Job job = Job.getInstance(conf, "logParse");
        //job.setJar("/Users/zhouyunfeng/IdeaProjects/jobanalysis/target/jobanalysis-1.0.jar");
        job.setJarByClass(LogParseJob.class);
        job.setMapperClass(LogParseJob.TokenizerMapper.class);
        //job.setCombinerClass(WordCount.IntSumReducer.class);
        job.setReducerClass(LogParseJob.IntSumReducer.class);
        // 对于MapOnly的作业，必须显式设置reducer的个数为0
        job.setNumReduceTasks(2);

        job.setOutputKeyClass(Text.class);
        job.setOutputValueClass(Text.class);

        for (int i = 0; i < otherArgs.length - 1; ++i) {
            FileInputFormat.addInputPath(job, new Path(otherArgs[i]));
        }
        FileOutputFormat.setOutputPath(job,
                new Path(otherArgs[otherArgs.length - 1]));
        System.exit(job.waitForCompletion(true) ? 0 : 1);
    }
}
