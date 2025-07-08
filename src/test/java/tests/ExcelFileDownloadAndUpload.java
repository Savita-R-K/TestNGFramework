package tests;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.Iterator;

public class ExcelFileDownloadAndUpload {
    DataFormatter formatter=new DataFormatter();

    @Test
    public void downloadAndUpload() throws InterruptedException, IOException {

        WebDriver driver=new ChromeDriver();
        driver.get("https://rahulshettyacademy.com/upload-download-test/");
        driver.manage().window().maximize();
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(5));

        //download
        WebElement downloadBtn = driver.findElement(By.id("downloadButton"));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", downloadBtn);
        downloadBtn.click();
        Thread.sleep(3000);

        //data to be changed
        String fruitName="Apple";
        String priceValue="400";
        String filePath="C:/Users/280679/Downloads/download.xlsx";

        //EDIT DATA
        int priceColNum=getColNum(filePath,"price");
        int fruitRowNum=getRowNum(filePath,"Apple");
        updateCell(filePath,fruitRowNum,priceColNum,priceValue);


        //upload
        driver.findElement(By.cssSelector("input[type='file']")).sendKeys(filePath);
        WebDriverWait wait=new WebDriverWait(driver,Duration.ofSeconds(5));
        WebElement alertMsg=driver.findElement(By.cssSelector("div[role='alert'] div:nth-child(2)"));
        Thread.sleep(3000);
        Assert.assertEquals(alertMsg.getText(),"Updated Excel Data Successfully.");
        wait.until(ExpectedConditions.invisibilityOf(alertMsg));

        //get new uploaded price value
        WebElement priceEle=driver.findElement(By.xpath("//div[text()='"+fruitName+"']/../../div[4]/div"));
        String priceAfter= priceEle.getText();

        //or
        Assert.assertEquals(priceAfter,priceValue);

    }

    public int getRowNum(String filePath,String cellValue) throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
        XSSFSheet sheet=workbook.getSheetAt(0);
        Iterator<Row> rows=sheet.iterator();
        int k=0;
        int rowNum=-1;
        while (rows.hasNext() && rowNum==-1){
            Row row=rows.next();
            Iterator<Cell> cells=row.cellIterator();
            while(cells.hasNext()){
                Cell cell= cells.next();
                if(formatter.formatCellValue(cell).equals(cellValue)){
                    rowNum=k;
                }
            }
            k++;
        }
        return rowNum;
    }

    public int getColNum(String filePath,String colName) throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook(Files.newInputStream(Paths.get(filePath)));
        XSSFSheet sheet=workbook.getSheetAt(0);
        Iterator<Row> rows=sheet.iterator();
        Row firstRow=rows.next();
        Iterator<Cell> firstRowCells=firstRow.cellIterator();
        int k=0;
        int colNum=0;
        while(firstRowCells.hasNext()){
            Cell cell=firstRowCells.next();
            if(formatter.formatCellValue(cell).equals(colName)){
                colNum=k;
            }
            k++;
        }
        return colNum;
    }

    public void updateCell(String filePath, int rowNum,int colNum,String value) throws IOException {
        XSSFWorkbook workbook=new XSSFWorkbook(new FileInputStream(filePath));
        XSSFSheet sheet=workbook.getSheetAt(0);
        Row row = sheet.getRow(rowNum);
        if (row == null) {
            row = sheet.createRow(rowNum);
        }

        Cell cell = row.getCell(colNum);
        if (cell == null) {
            cell = row.createCell(colNum);
        }
        cell.setCellValue(value);

        // Write changes back to the file
        FileOutputStream fos = new FileOutputStream(filePath);
        workbook.write(fos);

        fos.close();
        workbook.close();
    }
}
