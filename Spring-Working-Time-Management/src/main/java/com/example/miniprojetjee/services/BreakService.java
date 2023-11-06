package com.example.miniprojetjee.services;

import com.example.miniprojetjee.entity.Breaks;
import com.example.miniprojetjee.entity.Work;
import com.example.miniprojetjee.repository.BreaksRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
@Service
@Primary
public class BreakService implements IBreakService{
    @Autowired
    private BreaksRepo breaksRepo;

    @Override
    public List<Breaks> getBreaks(Work work) {
        return breaksRepo.findByDateAndWork(LocalDate.now(),work);
    }

    @Override
    public void addBreak(Breaks breaks) {
        breaksRepo.save(breaks);
    }

    @Override
    public void updateBreak(Breaks breaks) {
        breaksRepo.save(breaks);
    }
}
