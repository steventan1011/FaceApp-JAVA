package TanBinKai_16081119_JAVA;


import java.io.IOException;

import org.opencv.core.Core;
import org.opencv.core.CvType;
import org.opencv.core.Mat;
import org.opencv.ml.RTrees;



public class testing extends readImage{
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	/**
	 * 图片分列、行
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
		detect(urlTest0, urlTest, 500, 500);  //调成测试图片
		Mat testdatamat = new Mat(1, image_rows*image_cols*3, CvType.CV_32FC1);
		
//		try {
//			float[] label = getlabel(urlLabel, trainNum);   //获取标签集
//			labelmat.put(0, 0, label);
//			// System.out.println(labelmat.dump());
//		} catch (IOException e) {
//			// TODO Auto-generated catch block
//			e.printStackTrace();
//		}
		
		try {
//			float[][] traindata = getTrainData(urlTrain, trainNum);   //获取训练集
			float[][] testdata = getTestData1Picture(urlTest, 1);   //获取测试集
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
	
	
	//随机森林
    @SuppressWarnings("resource")
	public static Mat MyRTrees(Mat testData) throws IOException {  
    	RTrees rtrees = RTrees.load("src/FacePointbp_500.xml");
//        RTrees rtrees = RTrees.create();  
//        
//        rtrees.setMaxDepth(13);  // 4  //6 //max_depth  单棵树所可能达到的最大深度
////      rtrees.setMinSampleCount(2);   //min_sample_count  树节点持续分裂的最小样本数量，也就是说，小于这个数节点就不持续分裂，变成叶子了
//      rtrees.setRegressionAccuracy(0.f);  //regression_accuracy  回归树的终止条件，如果所有节点的精度都达到要求就停止
////      rtrees.setUseSurrogates(false);  // false  // use_surrogates 是否使用代理分裂。通常都是false，在有缺损数据或计算变量重要性的场合为true，比如，变量是色彩，而图片中有一部分区域因为光照是全黑的
////      rtrees.setMaxCategories(32);  // 16  //max_categories  将所有可能取值聚类到有限类，以保证计算速度。树会以次优分裂（suboptimal split）的形式生长。只对2种取值以上的树有意义
////      rtrees.setPriors(new Mat());   //priors  优先级设置，设定某些你尤其关心的类或值，使训练过程更关注它们的分类或回归精度。通常不设置
////      rtrees.setCalculateVarImportance(false);   // false  //calc_var_importance  设置是否需要获取变量的重要值，一般设置true
//      rtrees.setActiveVarCount(0);   // 1 //0  //nactive_vars  树的每个节点随机选择变量的数量，根据这些变量寻找最佳分裂。如果设置0值，则自动取变量总和的平方根
////      rtrees.setTermCriteria(new TermCriteria(TermCriteria.MAX_ITER, 5, 0));  
//        TrainData tData = TrainData.create(trainingData, Ml.ROW_SAMPLE, labels);  
//        boolean success = rtrees.train(tData.getSamples(), Ml.ROW_SAMPLE, tData.getResponses());  
//        System.out.println("Rtrees training result: " + success);  
        // rtrees.save("Resourse/Rating_Collection/FacePointbp_500.xml");//存储模型 
        
        Mat responseMat = new Mat();  
        // System.out.println(testData.dump().length());
        rtrees.predict(testData, responseMat, 0);  
        // System.out.println("Rtrees responseMat:\n" + responseMat.dump());  
        int num = 1;
        double[] predict = new double[responseMat.height()];
        for (int i = 0; i < responseMat.height(); i++) {  
        	predict[i] = responseMat.get(i, 0)[0];
        	System.out.println("第" + num + "张照片预测评分：" + responseMat.get(i, 0)[0]);  
        	num++;
        }  
	
        
        return responseMat;  
    }  
	
}
