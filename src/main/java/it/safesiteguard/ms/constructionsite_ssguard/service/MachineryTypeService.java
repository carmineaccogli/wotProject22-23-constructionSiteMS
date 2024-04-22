package it.safesiteguard.ms.constructionsite_ssguard.service;

import com.mongodb.DuplicateKeyException;
import it.safesiteguard.ms.constructionsite_ssguard.domain.ConstructionMachineryType;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryNotFoundException;
import it.safesiteguard.ms.constructionsite_ssguard.exceptions.MachineryTypeNotFoundException;

import java.util.List;

public interface MachineryTypeService {

    List<ConstructionMachineryType> getAll();

    List<ConstructionMachineryType> getMachineryTypesBySpecificLicence(boolean specificLicence);

    String saveNewType(ConstructionMachineryType machineryType) throws DuplicateKeyException;

    ConstructionMachineryType getTypeByName(String typeName) throws MachineryTypeNotFoundException;

    ConstructionMachineryType getTypeByID(String typeID) throws MachineryTypeNotFoundException;
}
