package TanBinKai_16081119_JAVA;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.GridLayout;
import java.awt.Toolkit;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.HashSet;

import javax.swing.ImageIcon;
import javax.swing.JButton;
import javax.swing.JDialog;
import javax.swing.JFileChooser;
import javax.swing.JFrame;
import javax.swing.JLabel;
import javax.swing.JOptionPane;
import javax.swing.JPanel;
import javax.swing.JTextField;
import javax.swing.UIManager;

import org.opencv.core.CvType;
import org.opencv.core.Mat;

import TanBinKai_16081119_JAVA.cameraStart;

/**
 * <p>Title: FaceGUI </p>
 * <p>Description: </p>
 * <p>Company: BUAA </p>
 * @author Steven_Tan
 * @date 2018年6月19日 上午2:27:12
 */
public class FaceGUI extends testing implements ActionListener {
   private static JButton upload, confirm, camera, exit;
   private static JLabel label1;
   private static JLabel label2;
   private static JLabel label3;
   private static JLabel label4;
   private static JLabel label5;
   private static JLabel labelImage;
   private static JLabel labelFace;
   private static JPanel p;
   private static JDialog quit;
   private static int numCircle = 0; 
   private static String pathImage = "";  
   private static String pathFaceDetect = "Face_Detected/Face_Detected-";
   private static String pathCamera = "Camera_Photo/CameraPhoto-";
   private static int image_cols =500;
   private static int image_rows = 500;    
   private static Dimension screensize = Toolkit.getDefaultToolkit().getScreenSize();
   private static final float screenWidthRatio = (float) ((float)screensize.getWidth()/3000.0);
   private static final float screenHeightRatio = (float) ((float)screensize.getHeight()/2000.0);

   public FaceGUI() throws FileNotFoundException, IOException {
	  loadIndyFont();   //字体
      final JFrame frame = new JFrame("16081119_谭滨锴_JAVA大作业_颜值打分");
      upload = new JButton("上传图片");
      upload.setFont(new Font("黑体", Font.PLAIN, 35));
      confirm = new JButton("本地上传图片的人脸识别+随机森林颜值打分");
      camera = new JButton("前置摄像头拍照+人脸识别+颜值打分");
      camera.setFont(new Font("黑体", Font.PLAIN, 35));
      confirm.setFont(new Font("黑体", Font.PLAIN, 35));
      exit = new JButton("退出");
      exit.setFont(new Font("黑体", Font.PLAIN, 35));
      upload.addActionListener(this);
      exit.addActionListener(this);
      p = new JPanel();
      p.setLayout(new GridLayout(4, 20));
      p.add(upload);
      p.add(confirm);
      p.add(camera);
      p.add(exit);
      frame.add(p, "North");
      frame.pack();
      frame.setBounds(800, 250, 1600, 1200);
      frame.setVisible(true);
      
      label1 = new JLabel("请上传图片：");
      label1.setFont(new Font("黑体", Font.PLAIN, 30));
      label1.setBounds(25, 750, 300, 50);
      frame.add(label1);
      frame.setLayout(null);//设置布局管理器为空
      label1.setVisible(true);
      
      label2 = new JLabel("颜值打分(5分制)：");
      label2.setFont(new Font("黑体", Font.PLAIN, 30));
      label2.setBounds(25, 300, 300, 50);
      frame.add(label2);  // y=1225
      frame.setLayout(null);//设置布局管理器为空
      label2.setVisible(true);
	  
	  label4 = new JLabel("人脸识别：");
      label4.setFont(new Font("黑体", Font.PLAIN, 30));
      label4.setBounds(825, 750, 225, 50); //指定插入的位置
      frame.add(label4);
      frame.setLayout(null);//设置布局管理器为空
      label4.setVisible(true);
      
      label5 = new JLabel("By_16081119_谭滨锴");
      label5.setFont(new Font("黑体", Font.PLAIN, 30));
      label5.setBounds(1000, 300, 300, 50); //指定插入的位置
      frame.add(label5);
      frame.setLayout(null);//设置布局管理器为空
      label5.setVisible(true);
       	  
	  JTextField text = new JTextField();
	  text.setBounds(350, 300, 300, 50);
	  frame.setLayout(null);//设置布局管理器为空
	  frame.add(text);
	  frame.pack();
	  frame.setBounds(800, 250, 1600, 1200);
 	  frame.setVisible(true);
 	  
//      frame.setBounds(800, 250, 1600, 1500);
//      frame.setVisible(true);

	  upload.addMouseListener(new MouseAdapter() { // 添加鼠标点击事件
		  public void mouseClicked(MouseEvent event) {
			  pathImage = eventOnImport(new JButton());
			  if (numCircle>0)
	   			  frame.remove(labelImage);
			  ImageIcon icon = new ImageIcon(pathImage);
	   		  labelImage = new JLabel(icon);
	   		  labelImage.setVisible(false);	   		  
		 	  labelImage.setBounds(250, 400, 500, 700); //指定插入的位置
		 	  frame.setLayout(null);//设置布局管理器为空
		 	  frame.add(labelImage);
		 	  	   		  
//		 	  frame.pack();
//		 	  frame.setBounds(800, 250, 1600, 1500);
		 	  labelImage.setVisible(true);
		 	  frame.setVisible(true);
		  }
	  });
	  
	  confirm.addMouseListener(new MouseAdapter() { // 添加鼠标点击事件
		  public void mouseClicked(MouseEvent event) {
			  String urlTest0 = pathImage;
			  String compute = String.valueOf(eventOnCompute(new JButton(), urlTest0));	   			  
			  
			  if (numCircle > 0)
				  frame.remove(labelFace);	
			  System.out.println("路径" + pathFaceDetect + numCircle + ".jpg");
			  ImageIcon icon1 = new ImageIcon(pathFaceDetect + numCircle + ".jpg");
	   		  labelFace = new JLabel(icon1);	
	   		  labelFace.setVisible(false);
		 	  labelFace.setBounds(1000, 500, 500, 500); //指定插入的位置
		 	  frame.setLayout(null);//设置布局管理器为空
		 	  frame.add(labelFace);
		 	  labelFace.setVisible(true);
		 	  
		 	  JTextField text = new JTextField();
			  text.setBounds(350, 300, 300, 50);
			  frame.setLayout(null);//设置布局管理器为空 y=1225
			  text.setText(compute);
			  frame.add(text);
			  text.setVisible(true);
//			  frame.pack();
//			  frame.setBounds(800, 250, 1600, 1500);
		 	  frame.setVisible(true);
//		 	  frame.pack();
//			  frame.setBounds(800, 250, 1600, 1500);
//		 	  frame.setVisible(true);
					 	  
		 	  numCircle++;
		  }
	  });
	  
	  camera.addMouseListener(new MouseAdapter() { // 添加鼠标点击事件
		  public void mouseClicked(MouseEvent event) {
			  String urlTest0 = pathCamera;			  
			  // 获取文件名
			  String pathcam = "Camera_Photo";  
			  int fileCount = 0;    
			  File d = new File(pathcam);  
			  File list[] = d.listFiles();  
			  for(int i = 0; i < list.length; i++){  
			      if(list[i].isFile())
			          fileCount++;
			  }  
			  System.out.println("文件个数" + fileCount);

			  String compute = String.valueOf(eventOnCompute(new JButton(), pathCamera + (fileCount-1) + ".jpg"));	
			  
			  if (numCircle>0)
	   			  frame.remove(labelImage);
			  ImageIcon icon = new ImageIcon(pathCamera + (fileCount-1) + ".jpg");
	   		  labelImage = new JLabel(icon);
	   		  labelImage.setVisible(false);	   		  
		 	  labelImage.setBounds(250, 400, 500, 700); //指定插入的位置
		 	  frame.setLayout(null);//设置布局管理器为空
		 	  frame.add(labelImage);
		 	  	   		  
//		 	  frame.pack();
//		 	  frame.setBounds(800, 250, 1600, 1500);
		 	  labelImage.setVisible(true);
//		 	  frame.setVisible(true);
		 	  
		 	 if (numCircle > 0)
				  frame.remove(labelFace);	
			  System.out.println("路径" + pathFaceDetect + numCircle + ".jpg");
			  ImageIcon icon1 = new ImageIcon(pathFaceDetect + numCircle + ".jpg");
	   		  labelFace = new JLabel(icon1);	
	   		  labelFace.setVisible(false);
		 	  labelFace.setBounds(1000, 500, 500, 500); //指定插入的位置
		 	  frame.setLayout(null);//设置布局管理器为空
		 	  frame.add(labelFace);
		 	  labelFace.setVisible(true);
		 	  
		 	  JTextField text = new JTextField();
			  text.setBounds(350, 300, 300, 50);
			  frame.setLayout(null);//设置布局管理器为空
			  text.setText(compute);
			  frame.add(text);
//			  frame.pack();
//			  frame.setBounds(800, 250, 1600, 1500);
		 	  frame.setVisible(true);
//		 	  frame.pack();
//			  frame.setBounds(800, 250, 1600, 1500);
//		 	  frame.setVisible(true);
					 	  
		 	  numCircle++;
			  
		 	 if (fileCount == 0)
			  {
		 		  JFrame frameError = new JFrame("Error");
				  frameError.setBounds(1000, 500, 800, 300);
				  frameError.setVisible(true);
				  
				  JLabel Error1 = new JLabel("没有检测到拍照的照片!!");
				  JLabel Error2 = new JLabel("请在左侧摄像头窗体内点击拍照按钮并拍照...");
				  Error1.setFont(new Font("黑体", Font.PLAIN, 30));
				  Error1.setForeground(Color.RED);
				  Error1.setBounds(50, 25, 600, 50);
				  Error2.setFont(new Font("黑体", Font.PLAIN, 30)); 
				  Error2.setForeground(Color.RED);
			      Error2.setBounds(50, 125, 700, 50);
			      frameError.add(Error1);
			      frameError.add(Error2);
			      frameError.setLayout(null);//设置布局管理器为空
			  }
			  
			  
			  }
		  });
	  
      quit = this.creatQuitDialog(frame);  
   }

   // 上传图片
   public static String eventOnImport(JButton developer) {
	   JFileChooser chooser = new JFileChooser();
	   chooser.setMultiSelectionEnabled(true);
//	   chooser.setSize(1000, 2000);
//	   chooser.setLocation(300, 500);
	   chooser.setDialogTitle("请选择要上传的文件...");
	   /** 过滤文件类型 * */
//	   FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg", ".jpeg");
//	   chooser.setFileFilter(filter);
	   int returnVal = chooser.showOpenDialog(developer);
	   if (returnVal == JFileChooser.APPROVE_OPTION) 
	   {
	    /** 得到选择的文件* */
	 	  File[] arrfiles = chooser.getSelectedFiles();
	 	  if (arrfiles == null || arrfiles.length == 0) 
	 	  {
	 		  return null;
	 	  }
	 	  FileInputStream input = null;
	 	  FileOutputStream out = null;
	 	  String path = "./";
	   	  String pathFile = chooser.getSelectedFile().getPath();
	   	  try {
	   		  for (File f : arrfiles) 
	   		  {
	   			  File dir = new File(path);
	   			  /** 目标文件夹 * */
	   			  File[] fs = dir.listFiles();
	   			  HashSet<String> set = new HashSet<String>();
	   			  for (File file : fs) 
	   			  {
	   				  set.add(file.getName());
	   			  }
	   			  /** 判断是否已有该文件* */
	   			  if (set.contains(f.getName())) 
	   			  {
	   				  JOptionPane.showMessageDialog(new JDialog(), f.getName() + ":该文件已存在！");
	   			  }
	   			  input = new FileInputStream(f);
	   			  byte[] buffer = new byte[1024];
	   			  File des = new File(path+"/Upload_Image", f.getName());
	   			  out = new FileOutputStream(des);
	   			  int len = 0;
	   			  while (-1 != (len = input.read(buffer))) 
	   			  {
	   				  out.write(buffer, 0, len);
	   			  }
	   			  out.close();
	   			  input.close();
	   		  }
	   		  JOptionPane.showMessageDialog(null, "上传成功！", "提示", JOptionPane.INFORMATION_MESSAGE);
	   		  pathFile = pathFile.replaceAll("\\\\", "/");
	   		  System.out.println(pathFile);
	   		  
	   		  
	   		  return pathFile;
	   	  } catch (FileNotFoundException e1) 
	   	  {
	   		  JOptionPane.showMessageDialog(null, "上传失败！", "提示", JOptionPane.ERROR_MESSAGE);
	   		  e1.printStackTrace();
	   	  } catch (IOException e1) 
	   	  {
	   		  JOptionPane.showMessageDialog(null, "上传失败！", "提示",
	   				  JOptionPane.ERROR_MESSAGE);
	   		  e1.printStackTrace();
	   	  }
	   	}
	return null;
   }
   
   // 调用随机森林颜值打分
   public static double eventOnCompute(JButton confirm, String urlTest0) {
		String urlTest = pathFaceDetect + numCircle + ".jpg";
		detect(urlTest0, urlTest, 500, 500);  //调成测试图片
		Mat testdatamat = new Mat(1, image_rows*image_cols*3, CvType.CV_32FC1);
		
		try {
			float[][] testdata = getTestData1Picture(urlTest, 1);   //获取测试集
			for (int j = 0; j < 1; j++) {
				testdatamat.put(j, 0, testdata[j]);
			}	
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		
		try {
			double grade = MyRTrees(testdatamat).get(0, 0)[0];
			return grade;
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   
		return 0;
   }

	public JDialog creatQuitDialog(JFrame f) {
	      JDialog d = new JDialog(f, "退出对话框", true);
	      JLabel l = new JLabel("你确定要退出程序吗？");
	      l.setFont(new Font("黑体", Font.PLAIN, 30));
	      JPanel p = new JPanel();
	      JButton confirm = new JButton("确定");
	      confirm.setFont(new Font("黑体", Font.PLAIN, 30));
	      JButton cancel = new JButton("取消");
	      cancel.setFont(new Font("黑体", Font.PLAIN, 30));
	      confirm.setActionCommand("confirmQuit");
	      cancel.setActionCommand("cancelQuit");
	      confirm.addActionListener(this);
	      cancel.addActionListener(this);
	      p.add(confirm);
	      p.add(cancel);
	      d.setSize(400, 300);
	      d.setLocation(400, 200);
	      d.add(l, "Center");
	      d.add(p, "South");
	      return d;
	   }
   
   @Override
   public void actionPerformed(ActionEvent e) {
      String s = e.getActionCommand();
      if (s.equals("退出")) {
         quit.setVisible(true);
      } else if (s.equals("confirmQuit")) {
         System.exit(0);
      } else if (s.equals("cancelQuit")) {
         quit.setVisible(false);}
   }

   
   // 调字体
   private static void loadIndyFont()
   {
	   Font f = new Font("黑体",Font.PLAIN,30);
		String   names[]={ "Label", "CheckBox", "PopupMenu","MenuItem", "CheckBoxMenuItem",
				"JRadioButtonMenuItem","ComboBox", "Button", "Tree", "ScrollPane",
				"TabbedPane", "EditorPane", "TitledBorder", "Menu", "TextArea",
				"OptionPane", "MenuBar", "ToolBar", "ToggleButton", "ToolTip",
				"ProgressBar", "TableHeader", "Panel", "List", "ColorChooser",
				"PasswordField","TextField", "Table", "Label", "Viewport",
				"RadioButtonMenuItem","RadioButton", "DesktopPane", "InternalFrame"
		}; 
		for (String item : names) {
			 UIManager.put(item+ ".font",f); 
		}
   }

   public static void main(String[] args) {
      try {
		new FaceGUI();
		cameraStart.cameraStartFunction();
	} catch (FileNotFoundException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	} catch (IOException e) {
		// TODO Auto-generated catch block
		e.printStackTrace();
	}
    
   }
   
}