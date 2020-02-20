package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.ClassName;
import com.fpt.edu.schedule.model.Room;
import com.fpt.edu.schedule.model.Schedule;
import com.fpt.edu.schedule.model.Subject;
import com.fpt.edu.schedule.service.base.ClassNameService;
import com.fpt.edu.schedule.service.base.ReportService;
import com.fpt.edu.schedule.service.base.RoomService;
import com.fpt.edu.schedule.service.base.SubjectService;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.stereotype.Service;

import java.io.File;
import java.io.FileInputStream;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;

@Service
@AllArgsConstructor
public class ReportServiceImpl implements ReportService {
    RoomService roomService;
    ClassNameService classNameService;
    SubjectService subjectService;

    @Override
    public void generateExcelFile(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            Set<String> subjectNameList = new HashSet<>();
            Set<String> roomNameList = new HashSet<>();
            Set<String> classNameList = new HashSet<>();
            while (rowIterator.hasNext()) {
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
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
                            classNameList.add(cell.getStringCellValue());
                            break;
                        case 2:
                            subjectNameList.add(cell.getStringCellValue());
                            break;
                        case 3:
//                          slot
                            break;
                        case 5:
                            roomNameList.add(cell.getStringCellValue());
                            break;
                    }
                }
//                scheduleDataRepository.save(scheduleData);
            }
            saveInformation(roomNameList, classNameList, subjectNameList);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveInformation(Set<String> roomSet, Set<String> classNameSet, Set<String> subjectSet) {
        roomSet.forEach(i -> {
            roomService.addRoom(new Room(i));
        });
        classNameSet.forEach(i -> {
            classNameService.addClassName(new ClassName(i));
        });
        subjectSet.forEach(i -> {
            subjectService.addSubject(new Subject(i, "CF"));
        });


    }
}
