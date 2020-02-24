package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.model.*;
import com.fpt.edu.schedule.service.base.*;
import lombok.AllArgsConstructor;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.CellType;
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
    ScheduleService scheduleService;
    SlotService slotService;

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
            Set<String> slotList = new HashSet<>();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();

                    if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            classNameList.add(cell.getStringCellValue().trim());
                            break;
                        case 2:
                            subjectNameList.add(cell.getStringCellValue().trim());
                            break;
                        case 3:
                            slotList.add(cell.getStringCellValue().trim());
                            break;
                        case 5:
                            roomNameList.add(cell.getStringCellValue().trim());
                            break;
                    }
                }
            }
            saveInformation(roomNameList, classNameList, subjectNameList,slotList);
            saveSchedule(fileName);
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void saveInformation(Set<String> roomSet, Set<String> classNameList, Set<String> subjectList,Set<String> slotList) {
        roomSet.forEach(i -> roomService.addRoom(new Room(i)));
        classNameList.forEach(i -> classNameService.addClassName(new ClassName(i)));
        subjectList.forEach(i -> subjectService.addSubject(new Subject(i, "CF")));
        slotList.forEach(i -> slotService.addSlot(new Slot(i)));
    }

    private void saveSchedule(String fileName) {
        try {
            FileInputStream file = new FileInputStream(new File(fileName));
            XSSFWorkbook workbook = new XSSFWorkbook(file);
            XSSFSheet sheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = sheet.iterator();
            rowIterator.next();
            while (rowIterator.hasNext()) {
                int column = 0;
                Row row = rowIterator.next();
                Iterator<Cell> cellIterator = row.cellIterator();
                if (!row.getCell(3).getStringCellValue().equalsIgnoreCase("CF")) {
                    continue;
                }
                Schedule schedule = new Schedule();
                while (cellIterator.hasNext()) {
                    column++;
                    Cell cell = cellIterator.next();

                    if (cell.getCellTypeEnum().equals(CellType.BLANK)) {
                        break;
                    }
                    switch (column) {
                        case 1:
                            schedule.setClassName(classNameService.getClassNameByName(cell.getStringCellValue().trim()));
                            break;
                        case 2:
                            schedule.setSubject(subjectService.getSubjectByCode(cell.getStringCellValue().trim()));
                            break;
                        case 3:
                       schedule.setSlot(slotService.getSlotByName(cell.getStringCellValue().trim()));
                            break;
                        case 5:
                            schedule.setRoom(roomService.getRoombyName(cell.getStringCellValue().trim()));
                            break;
                    }
                }
                scheduleService.addSchedule(schedule);
            }
            file.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
