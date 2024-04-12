package com.kurko67.controlflotas.models.service;

import com.kurko67.controlflotas.models.dao.ICheckListDao;
import com.kurko67.controlflotas.models.entity.CheckList;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class ICheckListServiceImpl implements ICheckListService {

    @Autowired
    ICheckListDao checkListDao;

    @Override
    @Transactional(readOnly = true)
    public Page<CheckList> findAll(Pageable pageable) {
        return (Page<CheckList>) checkListDao.findAll(pageable);
    }

    @Override
    @Transactional
    public void save(CheckList checkList) {
        checkListDao.save(checkList);
    }

    @Override
    @Transactional(readOnly = true)
    public Optional<CheckList> findOne(Long id) {
        return checkListDao.findById(id);
    }

    @Override
    @Transactional(readOnly = true)
    public CheckList findCheckListById(Long id_checklist) {
        return checkListDao.findCheckListById(id_checklist);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        checkListDao.deleteById(id);
    }
}
