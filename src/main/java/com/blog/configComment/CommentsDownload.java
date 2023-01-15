package com.blog.configComment;

import java.util.List;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.blog.entities.Comment;

public class CommentsDownload {
	public CommentsDownload(List<Comment> comments) {
		this.comments = comments;
		workbook = new XSSFWorkbook();
		sheet = workbook.createSheet("Books");
	}
	
	private List<Comment> comments;
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	private void writeHeaderRow() {
		Row row = sheet.createRow(0);
		
		Cell cell = row.createCell(0);
		cell.setCellValue("ID");
		
		cell = row.createCell(1);
		cell.setCellValue("Content");

		cell = row.createCell(2);
		cell.setCellValue("UserName");
		
		cell = row.createCell(3);
		cell.setCellValue("PostId");
	}

	private void writeDataRow() {
		int rowCount = 1;
		for (Comment comment : comments) {
			Row row = sheet.createRow(rowCount++);

			Cell cell = row.createCell(0);
			cell.setCellValue(String.valueOf(comment.getCommentId()));

			cell = row.createCell(1);
			cell.setCellValue(comment.getCommentContent());

			cell = row.createCell(2);
			cell.setCellValue(comment.getUser().getName());
			
			cell = row.createCell(3);
			cell.setCellValue(String.valueOf(comment.getPost().getPostId()));
		}
	}
	
	public void export(HttpServletResponse response) throws Exception{
		writeHeaderRow();
		writeDataRow();
		ServletOutputStream outputStream = response.getOutputStream();
		workbook.write(outputStream);
		workbook.close();
		outputStream.close();
	}
}
