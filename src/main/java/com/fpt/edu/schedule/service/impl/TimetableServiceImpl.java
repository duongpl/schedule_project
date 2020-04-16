package com.fpt.edu.schedule.service.impl;

import com.fpt.edu.schedule.ai.data.DataReader;
import com.fpt.edu.schedule.ai.model.GeneticAlgorithm;
import com.fpt.edu.schedule.ai.model.Model;
import com.fpt.edu.schedule.ai.model.Train;
import com.fpt.edu.schedule.common.exception.InvalidRequestException;
import com.fpt.edu.schedule.model.Expected;
import com.fpt.edu.schedule.model.Semester;
import com.fpt.edu.schedule.model.Timetable;
import com.fpt.edu.schedule.repository.base.ExpectedRepository;
import com.fpt.edu.schedule.repository.base.TimetableRepository;
import com.fpt.edu.schedule.service.base.SemesterService;
import com.fpt.edu.schedule.service.base.TimetableService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class TimetableServiceImpl implements TimetableService {
    SemesterService semesterService;
    TimetableRepository timetableRepository;
    ExpectedRepository expectedRepository;



    @Override
    public Timetable save(Timetable timeTable) {
        return null;
    }

    @Override
    public Timetable findBySemester(Semester semester) {
        Timetable timetable =timetableRepository.findBySemester(semester);
        if(timetable == null){
            throw new InvalidRequestException("Don't have data for this timetable");
        }
        return timetable;
    }

    @Override
    public void autoArrange(int semesterId,String hodGoogleId) {

        Train train = new Train();
        Model model = DataReader.getData();

        GeneticAlgorithm ga = new GeneticAlgorithm(model, train);
        List<Expected> expecteds = expectedRepository.findAllBySemester(semesterService.findById(semesterId));
        double[][] expectedSubject = model.getRegisteredSubjects();

//  ga.start();
    }
    public Model convertDataModel(){
        Model model = new Model();
     return null;
    }

}
