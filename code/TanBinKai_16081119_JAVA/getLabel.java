package TanBinKai_16081119_JAVA;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class getLabel {
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
			XSSFCell fenCell = row.getCell(1); // ±êÇ©ÁÐ
			label[i] = (float) fenCell.getNumericCellValue();
		}
		excelFileInputStream.close();
		return label;
	}
	
	public static void main(String[] args) {
		try {
			float[] label = getlabel("Resourse/Rating_Collection/Attractiveness label.xlsx", 400);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}
}
