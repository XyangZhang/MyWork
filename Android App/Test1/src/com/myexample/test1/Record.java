package com.myexample.test1;

import java.io.File;
import java.io.RandomAccessFile;

import android.os.Environment;

public class Record {
	
    final String FILE_NAME="/record.txt";
	
	public void write(String content)
	{
		try
		{
			if(Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED))
			{
				File sdCardDir=Environment.getExternalStorageDirectory();
				File targetFile=new File(sdCardDir.getCanonicalPath()+FILE_NAME);
				
				RandomAccessFile raf=new RandomAccessFile(targetFile,"rw");
				raf.seek(targetFile.length());
				raf.write(content.getBytes());
				raf.close();
			}
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}
	}

}
