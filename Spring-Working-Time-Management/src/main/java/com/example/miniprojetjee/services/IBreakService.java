package com.example.miniprojetjee.services;

import com.example.miniprojetjee.entity.Breaks;
import com.example.miniprojetjee.entity.Work;

import java.util.List;

public interface IBreakService {
    List<Breaks> getBreaks(Work work);

    void addBreak(Breaks breaks);

    void updateBreak(Breaks breaks);
}
