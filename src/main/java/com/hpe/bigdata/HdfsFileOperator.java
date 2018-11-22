package com.hpe.bigdata;

import org.apache.hadoop.conf.Configuration;
import org.apache.hadoop.fs.*;
import java.io.IOException;

public class HdfsFileOperator {

    private Configuration conf = null;
    private FileSystem fs = null;

    public HdfsFileOperator() throws IOException {
        conf = new Configuration();
        fs = FileSystem.get(conf);
    }

    public void listFile(String path) throws IOException {
        RemoteIterator<LocatedFileStatus> ls = fs.listFiles(new Path("/"), false);
        while (ls.hasNext()) {
            System.out.println(ls.next().getPath().toString());
        }
    }

    public void addFile(String fromPath, String toPath) throws IOException {

        fs.copyFromLocalFile(new Path(fromPath), new Path(toPath));
    }

    public void delFile(String path) throws IOException {
        if (fs.exists(new Path(path))) {
            fs.delete(new Path(path), true);
        }
    }

    public void mkDir(String path) throws IOException {
        fs.mkdirs(new Path(path));
    }

    public void getFile(String rPath, String lPath) throws IOException {
        fs.copyToLocalFile(false, new Path(rPath), new Path(lPath), true);
    }

    public void appendFile(String content, String path) throws IOException {
        FSDataOutputStream outpustream = fs.append(new Path(path));
        outpustream.writeBytes(content);
        outpustream.close();
    }
}
