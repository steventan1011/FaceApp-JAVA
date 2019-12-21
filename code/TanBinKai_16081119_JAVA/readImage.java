package TanBinKai_16081119_JAVA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.opencv.core.Core;
import org.opencv.core.Mat;
import org.opencv.imgcodecs.Imgcodecs;


public class readImage extends detectFace{
	//����OPENCV3.4���ؿ�,�����ȼ���
	static{System.loadLibrary(Core.NATIVE_LIBRARY_NAME);}
	
	private static int image_cols = 500;
	private static int image_rows = 500;
	
	public static float[][] getTrainData(String src, int len){
		float[][] traindata = new float[len][image_rows*image_cols*3];
		
		for (int i = 1; i <= len; i++)
		{
			Mat srcImage = Imgcodecs.imread(src + i + ".jpg");
			// ת��������ʽ
			for (int j = 0; j < image_rows; j++)
			{
				for (int k = 0; k < image_cols; k++)
				{
					traindata[i-1][3*j*image_cols+3*k] = (float) srcImage.get(j, k)[0];
					traindata[i-1][3*j*image_cols+3*k+1] = (float) srcImage.get(j, k)[1];
					traindata[i-1][3*j*image_cols+3*k+2] = (float) srcImage.get(j, k)[2];
				}				
			}
			// System.out.println("��" + i + " TrainingData");
		}
		return traindata;
	}
	
	public static float[][] getTestData(String src, int len){
		float[][] testdata = new float[len][image_rows*image_cols*3];
		for (int i = 501-len; i <= 500; i++)
		{
			Mat srcImage = Imgcodecs.imread(src + i + ".jpg");
			// ת��������ʽ
			for (int j = 0; j < image_rows; j++)
			{
				for (int k = 0; k < image_cols; k++)
				{
					testdata[i+len-501][3*j*image_cols+3*k] = (float) srcImage.get(j, k)[0];
					testdata[i+len-501][3*j*image_cols+3*k+1] = (float) srcImage.get(j, k)[1];
					testdata[i+len-501][3*j*image_cols+3*k+2] = (float) srcImage.get(j, k)[2];
				}
			}
		}
		return testdata;
	}
	
	public static float[][] getTestData1Picture(String src, int len){ //��1��ͼ
		float[][] testdata = new float[len][image_rows*image_cols*3];
		
		Mat srcImage = Imgcodecs.imread(src);
		// ת��������ʽ
		for (int j = 0; j < image_rows; j++)
		{
			for (int k = 0; k < image_cols; k++)
			{
				testdata[0][3*j*image_cols+3*k] = (float) srcImage.get(j, k)[0];
				testdata[0][3*j*image_cols+3*k+1] = (float) srcImage.get(j, k)[1];
				testdata[0][3*j*image_cols+3*k+2] = (float) srcImage.get(j, k)[2];
			}
		}
		
		return testdata;
	}
	
	public static float[] getlabel(String file,int len) throws IOException{
		File a = new File(file);
		float[] label = new float[len];
		FileInputStream excelFileInputStream = new FileInputStream(a);
		@SuppressWarnings("resource")
		XSSFWorkbook workbook = new XSSFWorkbook(excelFileInputStream);
		XSSFSheet sheet = workbook.getSheetAt(0);
		for(int i=0;i<len;i++) {
			XSSFRow row = sheet.getRow(i+1);
			if(row == null)
				continue;
			XSSFCell fenCell = row.getCell(1); // ��ǩ��
			label[i] = (float) fenCell.getNumericCellValue();
		}
		excelFileInputStream.close();
		return label;
	}
	
    public static void main(String[] args)
    {
    	float[][] traindata = getTrainData("Resourse/New_Data/FaceImage-", 400);  //ѵ����
    	float[][] testdata = getTestData("Resourse/New_Data/FaceImage-", 100);    //���Լ�
		try {
			float[] label = getlabel("Resourse/Rating_Collection/Attractiveness label.xlsx", 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}   //��ǩ

    	for (int i = 0; i < 75; i++)
    		System.out.println(testdata[83][i]);

    }
}
