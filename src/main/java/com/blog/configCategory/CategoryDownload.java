package com.blog.configCategory;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.blog.entities.Category;

public class CategoryDownload {

	public CategoryDownload(List<Category> categories) {
		this.categories = categories;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private List<Category> categories = new ArrayList<>();
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		Cell cell = row.createCell(0);
		cell.setCellValue("CategoryId");

		cell = row.createCell(1);
		cell.setCellValue("Title");

		cell = row.createCell(2);
		cell.setCellValue("Description");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (Category category : categories) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(String.valueOf(category.getCategoryId()));

			cell = row.createCell(1);
			cell.setCellValue(category.getCategoryTitle());

			cell = row.createCell(2);
			cell.setCellValue(category.getCategoryDescription());
		}
	}

	public void export(HttpServletResponse response) throws Exception {
		writeHeaderRow();
		writeDataRow();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
