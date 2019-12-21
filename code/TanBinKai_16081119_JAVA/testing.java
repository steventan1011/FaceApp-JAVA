package TanBinKai_16081119_JAVA;


import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.RTrees;



public class testing extends readImage{
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	/**
	 * ͼƬ���С���
	 * */
	private static int image_cols =500;
	private static int image_rows = 500;
	
	public static void main(String[] args) {
		int trainNum = 500;
//		String urlTrain = "Resourse/New_Data/FaceImage-";
		String urlTest0 = "C:/Users/steve/Desktop/1.jpg";
		String urlTest = "C:/Users/steve/Desktop/3.jpg";
//		String urlLabel = "Resourse/Rating_Collection/Attractiveness label.xlsx";
//		Mat labelmat = new Mat(trainNum, 1, CvType.CV_32FC1);
//		Mat traindatamat = new Mat(trainNum, image_rows*image_cols*3, CvType.CV_32FC1);
		detect(urlTest0, urlTest, 500, 500);  //���ɲ���ͼƬ
		Mat testdatamat = new Mat(1, image_rows*image_cols*3, CvType.CV_32FC1);
		
//		try {
//			float[] label = getlabel(urlLabel, trainNum);   //��ȡ��ǩ��
//			labelmat.put(0, 0, label);
//			// System.out.println(labelmat.dump());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
//			float[][] traindata = getTrainData(urlTrain, trainNum);   //��ȡѵ����
			float[][] testdata = getTestData1Picture(urlTest, 1);   //��ȡ���Լ�
//			for (int i = 0; i < trainNum; i++) {
//				traindatamat.put(i, 0, traindata[i]);
//			}		
			for (int j = 0; j < 1; j++) {
				testdatamat.put(j, 0, testdata[j]);
			}	
		}catch(Exception exception) {
			exception.printStackTrace();
		}
		
		// System.out.println(traindatamat.dump());
		// System.out.println(testdatamat.dump());
		// System.out.println(labelmat.dump());
		try {
			double grade = MyRTrees(testdatamat).get(0, 0)[0];
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	
	//���ɭ��
    @SuppressWarnings("resource")
	public static Mat MyRTrees(Mat testData) throws IOException {  
    	RTrees rtrees = RTrees.load("src/FacePointbp_500.xml");
//        RTrees rtrees = RTrees.create();  
//        
//        rtrees.setMaxDepth(13);  // 4  //6 //max_depth  �����������ܴﵽ��������
////      rtrees.setMinSampleCount(2);   //min_sample_count  ���ڵ�������ѵ���С����������Ҳ����˵��С��������ڵ�Ͳ��������ѣ����Ҷ����
//      rtrees.setRegressionAccuracy(0.f);  //regression_accuracy  �ع�������ֹ������������нڵ�ľ��ȶ��ﵽҪ���ֹͣ
////      rtrees.setUseSurrogates(false);  // false  // use_surrogates �Ƿ�ʹ�ô�����ѡ�ͨ������false������ȱ�����ݻ���������Ҫ�Եĳ���Ϊtrue�����磬������ɫ�ʣ���ͼƬ����һ����������Ϊ������ȫ�ڵ�
////      rtrees.setMaxCategories(32);  // 16  //max_categories  �����п���ȡֵ���ൽ�����࣬�Ա�֤�����ٶȡ������Դ��ŷ��ѣ�suboptimal split������ʽ������ֻ��2��ȡֵ���ϵ���������
////      rtrees.setPriors(new Mat());   //priors  ���ȼ����ã��趨ĳЩ��������ĵ����ֵ��ʹѵ�����̸���ע���ǵķ����ع龫�ȡ�ͨ��������
////      rtrees.setCalculateVarImportance(false);   // false  //calc_var_importance  �����Ƿ���Ҫ��ȡ��������Ҫֵ��һ������true
//      rtrees.setActiveVarCount(0);   // 1 //0  //nactive_vars  ����ÿ���ڵ����ѡ�������������������Щ����Ѱ����ѷ��ѡ��������0ֵ�����Զ�ȡ�����ܺ͵�ƽ����
////      rtrees.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 5, 0));  
//        TrainData tData = TrainData.create(trainingData, Ml.ROW_SAMPLE, labels);  
//        boolean success = rtrees.train(tData.getSamples(), Ml.ROW_SAMPLE, tData.getResponses());  
//        System.out.println("Rtrees training result: " + success);  
        // rtrees.save("Resourse/Rating_Collection/FacePointbp_500.xml");//�洢ģ�� 
        
        Mat responseMat = new Mat();  
        // System.out.println(testData.dump().length());
        rtrees.predict(testData, responseMat, 0);  
        // System.out.println("Rtrees responseMat:\n" + responseMat.dump());  
        int num = 1;
        double[] predict = new double[responseMat.height()];
        for (int i = 0; i < responseMat.height(); i++) {  
        	predict[i] = responseMat.get(i, 0)[0];
        	System.out.println("��" + num + "����ƬԤ�����֣�" + responseMat.get(i, 0)[0]);  
        	num++;
        }  
	
        
        return responseMat;  
    }  
	
}
