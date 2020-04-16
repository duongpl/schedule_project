package com.fpt.edu.schedule.ai.data;


import com.fpt.edu.schedule.ai.lib.Slot;
import com.fpt.edu.schedule.ai.lib.SlotGroup;
import com.fpt.edu.schedule.ai.model.Chromosome;
import com.fpt.edu.schedule.ai.model.Model;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.PrintWriter;
import java.util.Vector;

public class DataWriter {
    public static void writeToCsv(Model model, Chromosome c, String filename) {
        try {
            String path = "src\\main\\java\\com\\fpt\\edu\\schedule\\ai\\data\\" + filename;
            PrintWriter pr = new PrintWriter(new FileOutputStream(path));
            pr.print("teaacher_email");
            Vector<Slot> slots = SlotGroup.getSlotList(model.getSlots());
            for(Slot slot:slots) {
                pr.print("," + slot);
            }
            pr.println();
            for(int i = 0 ; i < model.getTeachers().size(); i++) {
                pr.print(model.getTeachers().get(i).getEmail());
                for(int j = 0; j < slots.size(); j ++) {
                    String s = c.getGenes().get(j).get(i) == -1 ? "###" : model.getClasses().get(c.getGenes().get(j).get(i)).toString();
                    pr.print("," + s);
                }
                pr.println();
            }
            pr.close();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
    }
}
