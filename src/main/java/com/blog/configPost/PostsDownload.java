package com.blog.configPost;

import java.util.ArrayList;
import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.blog.entities.Post;

public class PostsDownload {

	public PostsDownload(List<Post> posts) {
		this.posts = posts;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}

	private List<Post> posts = new ArrayList<>();
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;

	private void writeHeaderRow() {
		Row row = sheet.createRow(0);

		Cell cell = row.createCell(0);
		cell.setCellValue("PostId");

		cell = row.createCell(1);
		cell.setCellValue("Title");

		cell = row.createCell(2);
		cell.setCellValue("Content");

		cell = row.createCell(3);
		cell.setCellValue("PostImageId");

		cell = row.createCell(4);
		cell.setCellValue("Date");

		cell = row.createCell(5);
		cell.setCellValue("CategoryName");

		cell = row.createCell(6);
		cell.setCellValue("UserName");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (Post post : posts) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(String.valueOf(post.getPostId()));

			cell = row.createCell(1);
			cell.setCellValue(post.getPostTitle());

			cell = row.createCell(2);
			cell.setCellValue(post.getContent());

			cell = row.createCell(3);
			if(post.getPostImage() == null) {
				cell.setCellValue("Not Provided");
			} else {
				cell.setCellValue(post.getPostImage().getPostImageId().toString());
			}
			
			cell = row.createCell(4);
			cell.setCellValue(String.valueOf(post.getPostDate()));

			cell = row.createCell(5);
			cell.setCellValue(post.getPostCategory().getCategoryTitle());

			cell = row.createCell(6);
			cell.setCellValue(post.getPostUser().getName());
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