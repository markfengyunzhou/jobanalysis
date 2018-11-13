package com.hpe.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.FileSystem;
import org.apache.hadoop.fs.Path;

import java.io.IOException;

public class HdfsFileOperator {

    private Configuration conf = null;
    private FileSystem fs = null;

    public HdfsFileOperator() throws IOException {
        conf = new Configuration();
        fs = FileSystem.get(conf);
    }

    public void addFile(String fromPath, String toPath) throws IOException {

        fs.copyFromLocalFile(new Path(fromPath), new Path(toPath));

    }

}
