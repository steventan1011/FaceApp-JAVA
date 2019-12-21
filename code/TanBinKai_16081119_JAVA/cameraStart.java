package TanBinKai_16081119_JAVA;

import java.awt.Color;
import java.awt.Toolkit;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.GridLayout;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.awt.image.BufferedImage;

import javafx.scene.Camera;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JPanel;
import javax.swing.WindowConstants;

import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.core.MatOfDouble;
import org.opencv.core.MatOfRect;
import org.opencv.core.Point;
import org.opencv.core.Rect;
import org.opencv.core.Scalar;
import org.opencv.imgcodecs.Imgcodecs;
import org.opencv.imgproc.Imgproc;
import org.opencv.ml.SVM;
import org.opencv.objdetect.CascadeClassifier;
import org.opencv.objdetect.HOGDescriptor;
import org.opencv.videoio.VideoCapture;
import org.opencv.videoio.Videoio;

import sun.tools.jar.resources.jar;
  
@SuppressWarnings("serial")
public class cameraStart extends JPanel {  
      
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
    
	private static BufferedImage mimg;
    private static JButton photo;
    private static String pathImageCamera = "Camera_Photo/CameraPhoto-";
    private static Mat capImg=new Mat();  
    private static VideoCapture capture=new VideoCapture(0); 
    private static int num = 0;
    private static int numCam = 0;
    private static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
	private static final float screenWidthRatio = (float)screensize.getWidth()/3000;
	private static final float screenHeightRatio = (float)screensize.getHeight()/2000;
    
    public static BufferedImage getMimg() {
		return mimg;
	}

	public void setMimg(BufferedImage mimg) {
		cameraStart.mimg = mimg;
	}
    
    public BufferedImage mat2BI(Mat mat){  
        int dataSize =mat.cols()*mat.rows()*(int)mat.elemSize();  
        byte[] data=new byte[dataSize];  
        mat.get(0, 0,data);  
        int type=mat.channels()==1?  
                BufferedImage.TYPE_BYTE_GRAY:BufferedImage.TYPE_3BYTE_BGR;  
          
        if(type==BufferedImage.TYPE_3BYTE_BGR){  
            for(int i=0;i<dataSize;i+=3){  
                byte blue=data[i+0];  
                data[i+0]=data[i+2];  
                data[i+2]=blue;  
            }  
        }  
        BufferedImage image=new BufferedImage(mat.cols(),mat.rows(),type);  
        image.getRaster().setDataElements(0, 0, mat.cols(), mat.rows(), data);  
          
        return image;  
    }  
  
    public void paintComponent(Graphics g){  
        if(cameraStart.getMimg()!=null){  
            g.drawImage(cameraStart.getMimg(), 0, 0, cameraStart.getMimg().getWidth(),cameraStart.getMimg().getHeight(),this);  
        }  
    }  
      
    public static JFrame cameraStartFunction() {
    	 try{  
             System.loadLibrary(Core.NATIVE_LIBRARY_NAME);  
               
//             Mat capImg=new Mat();  
//             VideoCapture capture=new VideoCapture(0);  
             int height = (int)capture.get(Videoio.CAP_PROP_FRAME_HEIGHT);  
             int width = (int)capture.get(Videoio.CAP_PROP_FRAME_WIDTH);  
             if(height==0||width==0){  
                 throw new Exception("camera not found!");  
             }  
               
             JFrame frcamera=new JFrame("16081119_̷����_JAVA����ҵ_ǰ������ͷ");  
             photo = new JButton("���գ�����ʶ����������к�����ʱ�����");
             photo.setFont(new Font("����", Font.PLAIN, 30));
             
             frcamera.setDefaultCloseOperation(WindowConstants.DISPOSE_ON_CLOSE);  
             cameraStart panel=new cameraStart();
             panel.addMouseListener(new MouseAdapter() {
                 @Override
                 public void mouseClicked(MouseEvent arg0) {  
                      System.out.println("click");
                 }
                 @Override
                 public void mouseMoved(MouseEvent arg0) { 
                     System.out.println("move");
                     
                 }
                 @Override
                 public void mouseReleased(MouseEvent arg0) { 
                     System.out.println("mouseReleased");
                 }
                 @Override
                 public void mousePressed(MouseEvent arg0) { 
                     System.out.println("mousePressed");
                 }
                 @Override
                 public void mouseExited(MouseEvent arg0) { 
                     System.out.println("mouseExited");
                     //System.out.println(arg0.toString());
                 }
                 @Override
                 public void mouseDragged(MouseEvent arg0) { 
                     System.out.println("mouseDragged");
                     //System.out.println(arg0.toString());
                 }
                 
     		});
             
             frcamera.setContentPane(panel); 
             frcamera.add(photo);
             photo.setVisible(true);
             frcamera.pack();
//             frcamera.setBounds(800, 250, 1600, 1500);
              
             frcamera.setVisible(true);  
             frcamera.setSize(width+frcamera.getInsets().left+frcamera.getInsets().right,  
                     height+frcamera.getInsets().top+frcamera.getInsets().bottom);  
             
             photo.addMouseListener(new MouseAdapter() { // ���������¼�
         		  public void mouseClicked(MouseEvent event) {
         			  try {
		   					eventOnPhoto(new JButton());
		   					
		   					// ���������ճɹ����桱
		   					JFrame frameSuccess = new JFrame("���ճɹ�");
		   					frameSuccess.setVisible(true);   	
		   					
		   					JLabel Success1 = new JLabel("���ճɹ�!!������ҳ���������ť������ֵ���");
		   					Success1.setFont(new Font("����", Font.PLAIN, 30));
		   					// Success1.setForeground(Color.RED);
		   					frameSuccess.add(Success1);
		   					JLabel Success2 = new JLabel("�����ͼƬ�޷���ʾ��ť�޷����˵��ͼ����������Ϣ��");
		   					Success2.setFont(new Font("����", Font.PLAIN, 23));
		   					// Success2.setForeground(Color.RED);
		   					frameSuccess.add(Success2);
		   					
		   					
		   					JLabel labelCamera = new JLabel();
		   					if (numCam>0)
		   		  			  	frameSuccess.remove(labelCamera);
		   				 	ImageIcon icon = new ImageIcon("Camera_Photo/CameraPhoto-" + numCam + ".jpg");
		   		  		  	labelCamera = new JLabel(icon);
		   		  		  	labelCamera.setVisible(true);	   		  
		   		  		  	
		   		  		  	frameSuccess.setLayout(null);//���ò��ֹ�����Ϊ��
		   		  		  	frameSuccess.add(labelCamera);
		   		  		  	
		   		  		    numCam++;
		   					
		   		  		    Success1.setBounds(75, 25, 800, 50);
		   		  		    Success2.setBounds(90, 80, 800, 50);
		   		  		    labelCamera.setBounds(75, 150, 640, 480); //ָ�������λ��
		   					frameSuccess.setLayout(null);//���ò��ֹ�����Ϊ��
		   					
		   					frameSuccess.pack();
		   					frameSuccess.setBounds((int)(1000*screenWidthRatio), (int)(500*screenHeightRatio), 800, 800);
		   				 	frameSuccess.setVisible(true);
   					
   				} catch (Exception e) {
   					// TODO Auto-generated catch block
   					e.printStackTrace();
   				}
         			  
         		  }
         	  });
             
             
             int n=0;
//             Mat temp=new Mat();
             while(frcamera.isShowing()&& n<500){
                 //System.out.println("��"+n+"��");
                 capture.read(capImg);
//                 Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
                 //Imgcodecs.imwrite("G:/opencv/lw/neg/back"+n+".png", temp);
                 panel.setMimg(panel.mat2BI(detectFace(capImg)));
                 panel.repaint();
                 //n++;
                 //break;
             }             

             capture.release();  
             return frcamera; 
         }catch(Exception e){  
             System.out.println("���⣺" + e);  
         }finally{  
             System.out.println("--done--");  
         }  
    	 return null;
     
	} 
       
    
    // ����
    public static void eventOnPhoto(JButton confirm) throws Exception {
    	Mat temp=new Mat();
        //System.out.println("��"+n+"��");
        capture.read(capImg);
//        Imgproc.cvtColor(capImg, temp, Imgproc.COLOR_RGB2GRAY);
        Imgcodecs.imwrite(pathImageCamera + num + ".jpg", capImg);
//        panel.mImg=panel.mat2BI(detectFace(capImg));
//        panel.repaint();
        num++;
    }
    
    
    /**
     * opencvʵ������ʶ��
     * @param img
     */
    public static Mat detectFace(Mat img) throws Exception
    {

//        System.out.println("Running DetectFace ... ");
        // �������ļ�lbpcascade_frontalface.xml�д���һ������ʶ���������ļ�λ��opencv��װĿ¼��
        CascadeClassifier faceDetector = new CascadeClassifier("src/haarcascade_frontalface_alt.xml");


        // ��ͼƬ�м������
        MatOfRect faceDetections = new MatOfRect();

        faceDetector.detectMultiScale(img, faceDetections);

        //System.out.println(String.format("Detected %s faces", faceDetections.toArray().length));

        Rect[] rects = faceDetections.toArray();
        if(rects != null && rects.length >= 1){          
            for (Rect rect : rects) {  
                Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),  
                        new Scalar(0, 0, 255), 2);  
            } 
        }
        return img;
    }
    
    
    /**
     * opencvʵ������ʶ��hogĬ�ϵķ�����������Ч�����á�
     * @param img
     */
    public static Mat detectPeople(Mat img) {
        //System.out.println("detectPeople...");
        if (img.empty()) {  
            System.out.println("image is exist");  
        }  
        HOGDescriptor hog = new HOGDescriptor();
        hog.setSVMDetector(HOGDescriptor.getDefaultPeopleDetector());
        System.out.println(HOGDescriptor.getDefaultPeopleDetector());
        //hog.setSVMDetector(HOGDescriptor.getDaimlerPeopleDetector());  
        MatOfRect regions = new MatOfRect();  
        MatOfDouble foundWeights = new MatOfDouble(); 
        //System.out.println(foundWeights.toString());
        hog.detectMultiScale(img, regions, foundWeights);        
        for (Rect rect : regions.toArray()) {             
            Imgproc.rectangle(img, new Point(rect.x, rect.y), new Point(rect.x + rect.width, rect.y + rect.height),new Scalar(0, 0, 255), 2);  
        }  
        return img;  
    } 
    
    public static void main(String[] args) {
    	cameraStartFunction();
	}
}