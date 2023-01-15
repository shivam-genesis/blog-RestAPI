package com.blog.configUser;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.blog.entities.User;

public class UsersDownload {
	public UsersDownload(List<User> users) {
		this.users = users;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	List<User> users;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		Cell cell = row.createCell(0);
		cell.setCellValue("UserId");

		cell = row.createCell(1);
		cell.setCellValue("UserName");

		cell = row.createCell(2);
		cell.setCellValue("UserEmail");

		cell = row.createCell(3);
		cell.setCellValue("UserAbout");

		cell = row.createCell(4);
		cell.setCellValue("UserRole");
		
		cell = row.createCell(5);
		cell.setCellValue("UserImageId");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (User user : users) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(String.valueOf(user.getUserId()));

			cell = row.createCell(1);
			cell.setCellValue(user.getName());

			cell = row.createCell(2);
			cell.setCellValue(user.getEmail());

			cell = row.createCell(3);
			cell.setCellValue(user.getAbout());

			cell = row.createCell(4);
			cell.setCellValue(user.getRole());
			
			cell = row.createCell(5);
			if(user.getUserImage() == null) {
				cell.setCellValue("Not Available");
			}else {
				cell.setCellValue(String.valueOf(user.getUserImage().getUserImageId()));
			}
		}
	}
	
	public void export(HttpServletResponse response) throws IOException {
		writeHeaderRow();
		writeDataRow();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}