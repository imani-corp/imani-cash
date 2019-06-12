package com.imani.cash.domain.service.geographical;

import com.imani.cash.domain.geographical.GeographicalRegion;
import org.springframework.stereotype.Repository;

/**
 * @author manyce400
 */
@Repository
public interface IGeographicalRegionService {

    public GeographicalRegion findGeographicalRegionByCode(String regionCode);

}
