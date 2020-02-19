package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.service.base.ReportService;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.util.Iterator;

public class ReportServiceImpl implements ReportService {
    @Override
    public void generateExcelFile(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));

            //Create Workbook instance holding reference to .xlsx file
            XSSFWorkbook workbook = new XSSFWorkbook(file);

            //Get first/desired sheet from the workbook
            XSSFSheet sheet = workbook.getSheetAt(0);

            //Iterate through each rows one by one
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                //For each row, iterate through all the columns
                Iterator<Cell> cellIterator = row.cellIterator();
                int column = 0;
                 Schedule schedule = new Schedule();
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();
                    if (column == 6) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            scheduleData.setGroupClass(cell.getStringCellValue());
                            break;
                        case 2:
                            scheduleData.setSubject(cell.getStringCellValue());
                            break;
                        case 3:
                            scheduleData.setSlot(cell.getStringCellValue());
                            break;
                        case 4:
                            scheduleData.setDepartment(cell.getStringCellValue());
                            break;
                        case 5:
                            scheduleData.setRoom(cell.getStringCellValue());
                            break;
                    }
                }
                scheduleDataRepository.save(scheduleData);
            }
            subjectRepository.saveDistinct();
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
