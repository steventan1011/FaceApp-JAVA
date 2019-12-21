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
	//����OPENCV3.4���ؿ�,�����ȼ���
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	public static void detect(String inImg,String outImg, int rows, int cols){
		//copy opencv_java340.dll under the jdk1.8/bin jdk1.8/jre/bin dir.
		//��opencv_java340.dll������jdk��/jre/binĿ¼����
        // TODO Auto-generated method stub
		File f = new File(inImg);
		
		//ԭͼƬ������ֱ���˳�
		if(!f.exists()){
			System.out.println("\n Image File Not Found!");
			return;
		}
		//��ȡԭ�ļ�·��,������ļ���ԭ�ļ�������ͬһ·��
		String filePath = f.getAbsolutePath().substring(0, f.getAbsolutePath().indexOf(File.separator)+1);
        
        System.out.println("\nRunning FaceDetector");
        CascadeClassifier faceDetector = new CascadeClassifier();
        /**
         * ��������·������ʵ���������
         * haarcascade_frontalface_default  Ĭ��
         * haarcascade_frontalface_alt ʶ������Ҫ��Щ
         */
//        String xmlString = "/haarcascade_frontalface_alt.xml";
        faceDetector.load(
                "src/haarcascade_frontalface_alt.xml");
//        faceDetector.load(getClass().getResource(xmlString).getPath().substring(1));
        //��ȡͼ��
        Mat image = Imgcodecs.imread(inImg);
        //���ڱ����⵽������
        MatOfRect faceDetections = new MatOfRect();
        //��ʼ���
        faceDetector.detectMultiScale(image, faceDetections);
        
        System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));
        int i=0;
        
//        for (Rect rect : faceDetections.toArray())
//        {
//        	//ѭ�����м�⵽������
//        	Point x = new Point(rect.x, rect.y);
//        	Point y = new Point(rect.x + rect.width, rect.y + rect.height);
//        	
//        	Rect r = new Rect(x, y);
//        	System.out.println(r.height+":"+r.width);
//        	Mat newImage = new Mat(image, r);
//        	
//        	Mat resizeImage=new Mat();
//			Mat grayImage=new Mat();
//			Imgproc.resize(newImage, resizeImage, new Size(rows, cols));  //��С
//			//Imgproc.cvtColor(resizeImage, grayImage, Imgproc.COLOR_BGR2GRAY);   //�Ҷ�
//			
//			Imgcodecs.imwrite(outImg, resizeImage);  
//        	            
//        }
        
        // ��⵽������
        Rect rect = faceDetections.toArray()[0];
    	Point x = new Point(rect.x, rect.y);
    	Point y = new Point(rect.x + rect.width, rect.y + rect.height);
    	
    	Rect r = new Rect(x, y);
    	System.out.println(r.height+":"+r.width);
    	Mat newImage = new Mat(image, r);
    	
    	Mat resizeImage=new Mat();
		Mat grayImage=new Mat();
		Imgproc.resize(newImage, resizeImage, new Size(rows, cols));  //��С
		//Imgproc.cvtColor(resizeImage, grayImage, Imgproc.COLOR_BGR2GRAY);   //�Ҷ�
		
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
