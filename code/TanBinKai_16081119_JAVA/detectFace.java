package TanBinKai_16081119_JAVA;

import java.io.File;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Size;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.objdetect.CascadeClassifier;

public class detectFace{
	//加载OPENCV3.4本地库,必须先加载
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	public static void detect(String inImg,String outImg, int rows, int cols){
		//copy opencv_java340.dll under the jdk1.8/bin jdk1.8/jre/bin dir.
		//将opencv_java340.dll拷贝到jdk的/jre/bin目录下面
        // TODO Auto-generated method stub
		File f = new File(inImg);
		
		//原图片不存在直接退出
		if(!f.exists()){
			System.out.println("\n Image File Not Found!");
			return;
		}
		//获取原文件路径,让输出文件与原文件保存在同一路径
		String filePath = f.getAbsolutePath().substring(0, f.getAbsolutePath().indexOf(File.separator)+1);
        
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier();
        /**
         * 分类器，路径根据实际情况调整
         * haarcascade_frontalface_default  默认
         * haarcascade_frontalface_alt 识别性能要好些
         */
//        String xmlString = "/haarcascade_frontalface_alt.xml";
        faceDetector.load(
                "src/haarcascade_frontalface_alt.xml");
//        faceDetector.load(getClass().getResource(xmlString).getPath().substring(1));
        //读取图像
        Mat image = Imgcodecs.imread(inImg);
        //用于保存监测到的人脸
        MatOfRect faceDetections = new MatOfRect();
        //开始监测
        faceDetector.detectMultiScale(image, faceDetections);
        
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        int i=0;
        
//        for (Rect rect : faceDetections.toArray())
//        {
//        	//循环所有监测到的人脸
//        	Point x = new Point(rect.x, rect.y);
//        	Point y = new Point(rect.x + rect.width, rect.y + rect.height);
//        	
//        	Rect r = new Rect(x, y);
//        	System.out.println(r.height+":"+r.width);
//        	Mat newImage = new Mat(image, r);
//        	
//        	Mat resizeImage=new Mat();
//			Mat grayImage=new Mat();
//			Imgproc.resize(newImage, resizeImage, new Size(rows, cols));  //大小
//			//Imgproc.cvtColor(resizeImage, grayImage, Imgproc.COLOR_BGR2GRAY);   //灰度
//			
//			Imgcodecs.imwrite(outImg, resizeImage);  
//        	            
//        }
        
        // 监测到的人脸
        Rect rect = faceDetections.toArray()[0];
    	Point x = new Point(rect.x, rect.y);
    	Point y = new Point(rect.x + rect.width, rect.y + rect.height);
    	
    	Rect r = new Rect(x, y);
    	System.out.println(r.height+":"+r.width);
    	Mat newImage = new Mat(image, r);
    	
    	Mat resizeImage=new Mat();
		Mat grayImage=new Mat();
		Imgproc.resize(newImage, resizeImage, new Size(rows, cols));  //大小
		//Imgproc.cvtColor(resizeImage, grayImage, Imgproc.COLOR_BGR2GRAY);   //灰度
		
		Imgcodecs.imwrite(outImg, resizeImage);  
	}

	
		
    public static void main(String[] args)
    {
    	for (int i = 1; i <= 500; i++)
    	{
    		detect("Resourse/Data_Collection/SCUT-FBP-" + i + ".jpg", "Resourse/New_Data/FaceImage-" + i + ".jpg", 500, 500);
    		
    		System.out.println("The FaceImage-" + i + ".jpg has been generated.");
    	}
    	detect("C:/Users/steve/Desktop/1.jpg", "C:/Users/steve/Desktop/1.jpg", 500, 500);
    	
    }
}
