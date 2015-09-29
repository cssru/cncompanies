package com.cssru.cncompanies.service.impl;

import com.cssru.cncompanies.dao.AccountDao;
import com.cssru.cncompanies.dao.CompanyDao;
import com.cssru.cncompanies.dao.HumanDao;
import com.cssru.cncompanies.dao.UnitDao;
import com.cssru.cncompanies.domain.*;
import com.cssru.cncompanies.dto.HumanDto;
import com.cssru.cncompanies.dto.HumanMetadataElementDto;
import com.cssru.cncompanies.exception.AccessDeniedException;
import com.cssru.cncompanies.service.HumanService;
import com.cssru.cncompanies.service.TaskService;
import com.cssru.cncompanies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;

@Service
public class HumanServiceImpl implements HumanService {

    @Autowired
    private HumanDao humanDao;

    @Autowired
    private TaskService taskService;

    @Autowired
    private UnitDao unitDao;

    @Autowired
    private CompanyDao companyDao;

    @Autowired
    private AccountDao accountDao;

    @Transactional
    @Override
    public void add(HumanDto humanDto) throws AccessDeniedException {

        Unit unit = unitDao.get(humanDto.getUnitId());

        if (unit == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to add human client must be the Unit Manager, the Company Manager or the Account Holder
        if (!(unit.getManager().equals(clientAccount.getHuman()) ||
                unit.getCompany().getManager().equals(clientAccount.getHuman()) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        Human human = new Human();
        humanDto.mapTo(human);
        // human's unit and metadata was not mapped
        human.setUnit(unit);
        // setup human's metadata
        List<HumanMetadataElement> metadata = new ArrayList<HumanMetadataElement>(humanDto.getMetadata().size());
        for (HumanMetadataElementDto elementDto : humanDto.getMetadata()) {
            HumanMetadataElement newMetadataElement = new HumanMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }
        human.setMetadata(metadata);
        humanDao.addHuman(human);
    }

    @Transactional (readOnly = true)
    @Override
    public List<HumanDto> listByUnit(Long unitId) throws AccessDeniedException {

        Unit unit = unitDao.get(unitId);

        if (unit == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's humans client must be the Unit Manager, the Company Manager or the Account Holder
        if (!(unit.getManager().equals(clientAccount.getHuman()) ||
                unit.getCompany().getManager().equals(clientAccount.getHuman()) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<HumanDto> result = new ArrayList<HumanDto>(unit.getHumans().size());
        for (Human human : unit.getHumans()) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(human);
            humanDto.setUnitId(unitId);
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<HumanMetadataElementDto>(human.getMetadata().size());
            for (HumanMetadataElement element : human.getMetadata()) {
                HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
                elementDto.mapFrom(element);
                metadataDto.add(elementDto);
            }
            humanDto.setMetadata(metadataDto);
        }

        return result;

    }

    @Transactional (readOnly = true)
    @Override
    public List<HumanDto> listByCompany(Long companyId) throws AccessDeniedException {
        Company company = companyDao.get(companyId);

        if (company == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's humans client must be the Company Manager or the Account Holder
        if (!(company.getManager().equals(clientAccount.getHuman()) ||
                company.getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<Human> allHumans = humanDao.list(company);

        List<HumanDto> result = new ArrayList<HumanDto>(allHumans.size());
        for (Human human : allHumans) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(human);
            humanDto.setUnitId(human.getUnit().getId());
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<HumanMetadataElementDto>(human.getMetadata().size());
            for (HumanMetadataElement element : human.getMetadata()) {
                HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
                elementDto.mapFrom(element);
                metadataDto.add(elementDto);
            }
            humanDto.setMetadata(metadataDto);
        }

        return result;

    }

    @Transactional
    @Override
    public void delete(Long id) throws AccessDeniedException {
        Human human = humanDao.get(id);

        if (human == null) {
            throw new AccessDeniedException();
        }

        // delete all tasks linked to the human
        List<Task> tasksForHuman = taskService.listAllTask(human, managerLogin);
        for (Task t:tasksForHuman)
            taskService.removeTask(t.getId(), managerLogin);
        List<Task> tasksWithAuthor = taskService.listTaskWithAuthor(human, managerLogin);
        for (Task t:tasksWithAuthor) {
            // delete human's own tasks for himself
            if (t.getAuthor().equals(human)) {
                taskService.removeTask(t.getId(), managerLogin);
            } else {
                // else set manager as task author (do not delete tasks for another users)
                t.setAuthor(managerLogin.getHuman());
                taskService.updateTask(t, managerLogin);
            }
        }

        List<Unit> units = unitService.listUnitsWithOwner(human, managerLogin);
        for (Unit u:units) {
            u.setOwner(null);
            unitService.updateUnit(u, managerLogin);
        }

        humanDAO.removeHuman(human);
    }

    @Transactional
    @Override
    public void updateHuman(Human human, Login managerLogin) throws AccessDeniedException {
        Human existingHuman = humanDAO.getHuman(human.getId());
        if (existingHuman == null ||
                !(existingHuman.getUnit().getOwner().equals(managerLogin.getHuman()) ||
                        existingHuman.getUnit().getCompany().getOwner().equals(managerLogin.getHuman()))
                ) {
            throw new AccessDeniedException();
        }

        humanDAO.updateHuman(human);
    }

    @Transactional (readOnly = true)
    @Override
    public Human getHuman(Long id, Login managerLogin) throws AccessDeniedException {
        Human human = humanDAO.getHuman(id);
        if (human == null ||
                !(human.getId().equals(id) ||
                        human.getUnit().getOwner().equals(managerLogin.getHuman()) ||
                        human.getUnit().getCompany().getOwner().equals(managerLogin.getHuman()))
                ) {
            throw new AccessDeniedException();
        }

        return human;
    }

}
