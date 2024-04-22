package it.safesiteguard.ms.constructionsite_ssguard.service;


import com.mongodb.DuplicateKeyException;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.repositories.ConstructionMachineryTypeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.swing.text.html.Option;
import java.util.List;
import java.util.Optional;

@Service
public class MachineryTypeServiceImpl implements MachineryTypeService {


    @Autowired
    private ConstructionMachineryTypeRepository machineryTypeRepository;

    public List<ConstructionMachineryType> getAll() {
        return machineryTypeRepository.findAll();
    }

    public List<ConstructionMachineryType> getMachineryTypesBySpecificLicence(boolean specificLicence) {
        return machineryTypeRepository.findConstructionMachineryTypeByRequiredSpecificLicence(specificLicence);
    }

    public String saveNewType(ConstructionMachineryType machineryType) throws DuplicateKeyException {
        ConstructionMachineryType createdType = machineryTypeRepository.save(machineryType);
        return createdType.getId();
    }

    public ConstructionMachineryType getTypeByName(String typeName) throws MachineryTypeNotFoundException{
        Optional<ConstructionMachineryType> optType = machineryTypeRepository.findConstructionMachineryTypeByName(typeName);

        if(!optType.isPresent())
            throw new MachineryTypeNotFoundException();

        return optType.get();
    }

    public ConstructionMachineryType getTypeByID(String typeID) throws MachineryTypeNotFoundException {
        Optional<ConstructionMachineryType> optType = machineryTypeRepository.findConstructionMachineryTypeById(typeID);

        if(!optType.isPresent())
            throw new MachineryTypeNotFoundException();

        return optType.get();
    }
}
