package com.example.helper;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.List;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import com.example.models.BlogPost;

public class ExcelHelper {
  public static String TYPE = "application/vnd.openxmlformats-officedocument.spreadsheetml.sheet";
  static String[] HEADERs = { "Id", "Title", "Author", "Description", "Date", "Content", "Likes" };
  static String SHEET = "Blog Posts";
  public static ByteArrayInputStream blogPostsToExcel(List<BlogPost> posts) {
    try (Workbook workbook = new XSSFWorkbook(); ByteArrayOutputStream out = new ByteArrayOutputStream();) {
      Sheet sheet = workbook.createSheet(SHEET);
      // Header
      Row headerRow = sheet.createRow(0);
      for (int col = 0; col < HEADERs.length; col++) {
        Cell cell = headerRow.createCell(col);
        cell.setCellValue(HEADERs[col]);
      }
      int rowIdx = 1;
      for (BlogPost post : posts) {
        Row row = sheet.createRow(rowIdx++);
        row.createCell(0).setCellValue(post.getId());
        row.createCell(1).setCellValue(post.getTitle());
        row.createCell(2).setCellValue(post.getAuthor());
        row.createCell(3).setCellValue(post.getDescription());
        row.createCell(4).setCellValue(post.getDate());
        row.createCell(5).setCellValue(post.getContent());
        row.createCell(6).setCellValue(post.getLikes());
      }
      workbook.write(out);
      return new ByteArrayInputStream(out.toByteArray());
    } catch (IOException e) {
      throw new RuntimeException("fail to import data to Excel file: " + e.getMessage());
    }
  }
}
