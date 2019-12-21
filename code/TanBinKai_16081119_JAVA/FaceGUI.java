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
 * @date 2018��6��19�� ����2:27:12
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
	  loadIndyFont();   //����
      final JFrame frame = new JFrame("16081119_̷����_JAVA����ҵ_��ֵ���");
      upload = new JButton("�ϴ�ͼƬ");
      upload.setFont(new Font("����", Font.PLAIN, 35));
      confirm = new JButton("�����ϴ�ͼƬ������ʶ��+���ɭ����ֵ���");
      camera = new JButton("ǰ������ͷ����+����ʶ��+��ֵ���");
      camera.setFont(new Font("����", Font.PLAIN, 35));
      confirm.setFont(new Font("����", Font.PLAIN, 35));
      exit = new JButton("�˳�");
      exit.setFont(new Font("����", Font.PLAIN, 35));
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
      
      label1 = new JLabel("���ϴ�ͼƬ��");
      label1.setFont(new Font("����", Font.PLAIN, 30));
      label1.setBounds(25, 750, 300, 50);
      frame.add(label1);
      frame.setLayout(null);//���ò��ֹ�����Ϊ��
      label1.setVisible(true);
      
      label2 = new JLabel("��ֵ���(5����)��");
      label2.setFont(new Font("����", Font.PLAIN, 30));
      label2.setBounds(25, 300, 300, 50);
      frame.add(label2);  // y=1225
      frame.setLayout(null);//���ò��ֹ�����Ϊ��
      label2.setVisible(true);
	  
	  label4 = new JLabel("����ʶ��");
      label4.setFont(new Font("����", Font.PLAIN, 30));
      label4.setBounds(825, 750, 225, 50); //ָ�������λ��
      frame.add(label4);
      frame.setLayout(null);//���ò��ֹ�����Ϊ��
      label4.setVisible(true);
      
      label5 = new JLabel("By_16081119_̷����");
      label5.setFont(new Font("����", Font.PLAIN, 30));
      label5.setBounds(1000, 300, 300, 50); //ָ�������λ��
      frame.add(label5);
      frame.setLayout(null);//���ò��ֹ�����Ϊ��
      label5.setVisible(true);
       	  
	  JTextField text = new JTextField();
	  text.setBounds(350, 300, 300, 50);
	  frame.setLayout(null);//���ò��ֹ�����Ϊ��
	  frame.add(text);
	  frame.pack();
	  frame.setBounds(800, 250, 1600, 1200);
 	  frame.setVisible(true);
 	  
//      frame.setBounds(800, 250, 1600, 1500);
//      frame.setVisible(true);

	  upload.addMouseListener(new MouseAdapter() { // ���������¼�
		  public void mouseClicked(MouseEvent event) {
			  pathImage = eventOnImport(new JButton());
			  if (numCircle>0)
	   			  frame.remove(labelImage);
			  ImageIcon icon = new ImageIcon(pathImage);
	   		  labelImage = new JLabel(icon);
	   		  labelImage.setVisible(false);	   		  
		 	  labelImage.setBounds(250, 400, 500, 700); //ָ�������λ��
		 	  frame.setLayout(null);//���ò��ֹ�����Ϊ��
		 	  frame.add(labelImage);
		 	  	   		  
//		 	  frame.pack();
//		 	  frame.setBounds(800, 250, 1600, 1500);
		 	  labelImage.setVisible(true);
		 	  frame.setVisible(true);
		  }
	  });
	  
	  confirm.addMouseListener(new MouseAdapter() { // ���������¼�
		  public void mouseClicked(MouseEvent event) {
			  String urlTest0 = pathImage;
			  String compute = String.valueOf(eventOnCompute(new JButton(), urlTest0));	   			  
			  
			  if (numCircle > 0)
				  frame.remove(labelFace);	
			  System.out.println("·��" + pathFaceDetect + numCircle + ".jpg");
			  ImageIcon icon1 = new ImageIcon(pathFaceDetect + numCircle + ".jpg");
	   		  labelFace = new JLabel(icon1);	
	   		  labelFace.setVisible(false);
		 	  labelFace.setBounds(1000, 500, 500, 500); //ָ�������λ��
		 	  frame.setLayout(null);//���ò��ֹ�����Ϊ��
		 	  frame.add(labelFace);
		 	  labelFace.setVisible(true);
		 	  
		 	  JTextField text = new JTextField();
			  text.setBounds(350, 300, 300, 50);
			  frame.setLayout(null);//���ò��ֹ�����Ϊ�� y=1225
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
	  
	  camera.addMouseListener(new MouseAdapter() { // ���������¼�
		  public void mouseClicked(MouseEvent event) {
			  String urlTest0 = pathCamera;			  
			  // ��ȡ�ļ���
			  String pathcam = "Camera_Photo";  
			  int fileCount = 0;    
			  File d = new File(pathcam);  
			  File list[] = d.listFiles();  
			  for(int i = 0; i < list.length; i++){  
			      if(list[i].isFile())
			          fileCount++;
			  }  
			  System.out.println("�ļ�����" + fileCount);

			  String compute = String.valueOf(eventOnCompute(new JButton(), pathCamera + (fileCount-1) + ".jpg"));	
			  
			  if (numCircle>0)
	   			  frame.remove(labelImage);
			  ImageIcon icon = new ImageIcon(pathCamera + (fileCount-1) + ".jpg");
	   		  labelImage = new JLabel(icon);
	   		  labelImage.setVisible(false);	   		  
		 	  labelImage.setBounds(250, 400, 500, 700); //ָ�������λ��
		 	  frame.setLayout(null);//���ò��ֹ�����Ϊ��
		 	  frame.add(labelImage);
		 	  	   		  
//		 	  frame.pack();
//		 	  frame.setBounds(800, 250, 1600, 1500);
		 	  labelImage.setVisible(true);
//		 	  frame.setVisible(true);
		 	  
		 	 if (numCircle > 0)
				  frame.remove(labelFace);	
			  System.out.println("·��" + pathFaceDetect + numCircle + ".jpg");
			  ImageIcon icon1 = new ImageIcon(pathFaceDetect + numCircle + ".jpg");
	   		  labelFace = new JLabel(icon1);	
	   		  labelFace.setVisible(false);
		 	  labelFace.setBounds(1000, 500, 500, 500); //ָ�������λ��
		 	  frame.setLayout(null);//���ò��ֹ�����Ϊ��
		 	  frame.add(labelFace);
		 	  labelFace.setVisible(true);
		 	  
		 	  JTextField text = new JTextField();
			  text.setBounds(350, 300, 300, 50);
			  frame.setLayout(null);//���ò��ֹ�����Ϊ��
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
				  
				  JLabel Error1 = new JLabel("û�м�⵽���յ���Ƭ!!");
				  JLabel Error2 = new JLabel("�����������ͷ�����ڵ�����հ�ť������...");
				  Error1.setFont(new Font("����", Font.PLAIN, 30));
				  Error1.setForeground(Color.RED);
				  Error1.setBounds(50, 25, 600, 50);
				  Error2.setFont(new Font("����", Font.PLAIN, 30)); 
				  Error2.setForeground(Color.RED);
			      Error2.setBounds(50, 125, 700, 50);
			      frameError.add(Error1);
			      frameError.add(Error2);
			      frameError.setLayout(null);//���ò��ֹ�����Ϊ��
			  }
			  
			  
			  }
		  });
	  
      quit = this.creatQuitDialog(frame);  
   }

   // �ϴ�ͼƬ
   public static String eventOnImport(JButton developer) {
	   JFileChooser chooser = new JFileChooser();
	   chooser.setMultiSelectionEnabled(true);
//	   chooser.setSize(1000, 2000);
//	   chooser.setLocation(300, 500);
	   chooser.setDialogTitle("��ѡ��Ҫ�ϴ����ļ�...");
	   /** �����ļ����� * */
//	   FileNameExtensionFilter filter = new FileNameExtensionFilter(".jpg", ".jpeg");
//	   chooser.setFileFilter(filter);
	   int returnVal = chooser.showOpenDialog(developer);
	   if (returnVal == JFileChooser.APPROVE_OPTION) 
	   {
	    /** �õ�ѡ����ļ�* */
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
	   			  /** Ŀ���ļ��� * */
	   			  File[] fs = dir.listFiles();
	   			  HashSet<String> set = new HashSet<String>();
	   			  for (File file : fs) 
	   			  {
	   				  set.add(file.getName());
	   			  }
	   			  /** �ж��Ƿ����и��ļ�* */
	   			  if (set.contains(f.getName())) 
	   			  {
	   				  JOptionPane.showMessageDialog(new JDialog(), f.getName() + ":���ļ��Ѵ��ڣ�");
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
	   		  JOptionPane.showMessageDialog(null, "�ϴ��ɹ���", "��ʾ", JOptionPane.INFORMATION_MESSAGE);
	   		  pathFile = pathFile.replaceAll("\\\\", "/");
	   		  System.out.println(pathFile);
	   		  
	   		  
	   		  return pathFile;
	   	  } catch (FileNotFoundException e1) 
	   	  {
	   		  JOptionPane.showMessageDialog(null, "�ϴ�ʧ�ܣ�", "��ʾ", JOptionPane.ERROR_MESSAGE);
	   		  e1.printStackTrace();
	   	  } catch (IOException e1) 
	   	  {
	   		  JOptionPane.showMessageDialog(null, "�ϴ�ʧ�ܣ�", "��ʾ",
	   				  JOptionPane.ERROR_MESSAGE);
	   		  e1.printStackTrace();
	   	  }
	   	}
	return null;
   }
   
   // �������ɭ����ֵ���
   public static double eventOnCompute(JButton confirm, String urlTest0) {
		String urlTest = pathFaceDetect + numCircle + ".jpg";
		detect(urlTest0, urlTest, 500, 500);  //���ɲ���ͼƬ
		Mat testdatamat = new Mat(1, image_rows*image_cols*3, CvType.CV_32FC1);
		
		try {
			float[][] testdata = getTestData1Picture(urlTest, 1);   //��ȡ���Լ�
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
	      JDialog d = new JDialog(f, "�˳��Ի���", true);
	      JLabel l = new JLabel("��ȷ��Ҫ�˳�������");
	      l.setFont(new Font("����", Font.PLAIN, 30));
	      JPanel p = new JPanel();
	      JButton confirm = new JButton("ȷ��");
	      confirm.setFont(new Font("����", Font.PLAIN, 30));
	      JButton cancel = new JButton("ȡ��");
	      cancel.setFont(new Font("����", Font.PLAIN, 30));
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
      if (s.equals("�˳�")) {
         quit.setVisible(true);
      } else if (s.equals("confirmQuit")) {
         System.exit(0);
      } else if (s.equals("cancelQuit")) {
         quit.setVisible(false);}
   }

   
   // ������
   private static void loadIndyFont()
   {
	   Font f = new Font("����",Font.PLAIN,30);
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