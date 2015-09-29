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
import com.cssru.cncompanies.utils.Utils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class HumanServiceImpl implements HumanService {

    @Autowired
    private HumanDao humanDao;

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
        if (!((unit.getManager() != null && unit.getManager().equals(clientAccount.getHuman())) ||
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getHuman())) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        Human human = new Human();
        humanDto.mapTo(human);
        // human's unit and metadata was not mapped
        human.setUnit(unit);
        // setup human's metadata
        List<HumanMetadataElement> metadata = new ArrayList<>(humanDto.getMetadata().size());
        for (HumanMetadataElementDto elementDto : humanDto.getMetadata()) {
            HumanMetadataElement newMetadataElement = new HumanMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }
        human.setMetadata(metadata);
        humanDao.add(human);
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
        if (!((unit.getManager() != null && unit.getManager().equals(clientAccount.getHuman())) ||
                (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getHuman())) ||
                unit.getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<HumanDto> result = new ArrayList<>(unit.getHumans().size());
        for (Human human : unit.getHumans()) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(human);
            humanDto.setUnitId(unitId);
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(human.getMetadata().size());
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
        if (!((company.getManager() != null && company.getManager().equals(clientAccount.getHuman())) ||
                company.getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        List<Human> allHumans = humanDao.list(company);

        List<HumanDto> result = new ArrayList<>(allHumans.size());
        for (Human human : allHumans) {
            HumanDto humanDto = new HumanDto();
            humanDto.mapFrom(human);
            humanDto.setUnitId(human.getUnit().getId());
            ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(human.getMetadata().size());
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

        Account clientAccount = Utils.clientAccount(accountDao);
        // to list unit's humans client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((human.getUnit().getManager() != null && human.getUnit().getManager().equals(clientAccount.getHuman())) ||
                (human.getUnit().getCompany().getManager() != null && human.getUnit().getCompany().getManager().equals(clientAccount.getHuman())) ||
                human.getUnit().getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        humanDao.delete(human);
    }

    @Transactional
    @Override
    public void update(HumanDto humanDto) throws AccessDeniedException {
        Human human = humanDao.get(humanDto.getId());

        if (human == null) {
            throw new AccessDeniedException();
        }

        if (!human.getUnit().getId().equals(humanDto.getUnitId())) {
            // it happens when someone try to change Human's unit (move human to another unit)
            // this person must be Company Manager or Account Holder
            Unit unit = unitDao.get(humanDto.getUnitId());

            if (unit == null) {
                throw new AccessDeniedException();
            }

            Account clientAccount = Utils.clientAccount(accountDao);
            // to change human's unit client must be the Company Manager or the Account Holder
            if (!((human.getUnit().getCompany().getManager() != null && human.getUnit().getCompany().getManager().equals(clientAccount.getHuman())) ||
                    human.getUnit().getCompany().getHolder().equals(clientAccount) ||
                    (unit.getCompany().getManager() != null && unit.getCompany().getManager().equals(clientAccount.getHuman())) ||
                    unit.getCompany().getHolder().equals(clientAccount)
            )) {
                throw new AccessDeniedException();
            }
            human.setUnit(unit);
        }

        humanDto.mapTo(human);

        // setup human's metadata, because it was not mapped
        List<HumanMetadataElement> metadata = new ArrayList<>(humanDto.getMetadata().size());

        for (HumanMetadataElementDto elementDto : humanDto.getMetadata()) {
            HumanMetadataElement newMetadataElement = new HumanMetadataElement();
            elementDto.mapTo(newMetadataElement);
            metadata.add(newMetadataElement);
        }

        human.setMetadata(metadata);

        humanDao.update(human);
    }

    @Transactional (readOnly = true)
    @Override
    public HumanDto get(Long id) throws AccessDeniedException {
        Human human = humanDao.get(id);

        if (human == null) {
            throw new AccessDeniedException();
        }

        Account clientAccount = Utils.clientAccount(accountDao);
        // to get human client must be the Unit Manager, the Company Manager or the Account Holder
        if (!((human.getUnit().getManager() != null && human.getUnit().getManager().equals(clientAccount.getHuman())) ||
                (human.getUnit().getCompany().getManager() != null && human.getUnit().getCompany().getManager().equals(clientAccount.getHuman())) ||
                human.getUnit().getCompany().getHolder().equals(clientAccount)
        )) {
            throw new AccessDeniedException();
        }

        HumanDto result = new HumanDto();
        result.mapFrom(human);
        result.setUnitId(human.getUnit().getId());

        ArrayList<HumanMetadataElementDto> metadataDto = new ArrayList<>(human.getMetadata().size());
        for (HumanMetadataElement element : human.getMetadata()) {
            HumanMetadataElementDto elementDto = new HumanMetadataElementDto();
            elementDto.mapFrom(element);
            metadataDto.add(elementDto);
        }
        result.setMetadata(metadataDto);
        return result;
    }

}
