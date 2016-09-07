/*
 * Copyright (c) 2015 Memorial Sloan-Kettering Cancer Center.
 *
 * This library is distributed in the hope that it will be useful, but WITHOUT
 * ANY WARRANTY, WITHOUT EVEN THE IMPLIED WARRANTY OF MERCHANTABILITY OR FITNESS
 * FOR A PARTICULAR PURPOSE. The software and documentation provided hereunder
 * is on an "as is" basis, and Memorial Sloan-Kettering Cancer Center has no
 * obligations to provide maintenance, support, updates, enhancements or
 * modifications. In no event shall Memorial Sloan-Kettering Cancer Center be
 * liable to any party for direct, indirect, special, incidental or
 * consequential damages, including lost profits, arising out of the use of this
 * software and its documentation, even if Memorial Sloan-Kettering Cancer
 * Center has been advised of the possibility of such damage.
 */

/*
 * This file is part of cBioPortal.
 *
 * cBioPortal is free software: you can redistribute it and/or modify
 * it under the terms of the GNU Affero General Public License as
 * published by the Free Software Foundation, either version 3 of the
 * License.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 *
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
*/

package org.mskcc.cbio.persistence.jdbc;

import org.mskcc.cbio.model.*;

import java.util.*;
import java.sql.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 *
 * @author ochoaa
 */
@Repository
public class ClinicalEventJdbcDaoImpl implements ClinicalEventJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(ClinicalEventJdbcDaoImpl.class);
    
    @Autowired
    public ClinicalEventJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code ClinicalEventJdbcDao}
     */
    @Override
    public Integer addClinicalEventBatch(List<ClinicalEvent> clinicalEventList) {
        String SQL = "INSERT INTO clinical_event " +
                " (clinical_event_id, patient_id, start_date, stop_date, event_type) " + 
                "VALUES(:clinicalEventId, :patientId, :startDate, :stopDate, :eventType)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(clinicalEventList.toArray(new ClinicalEvent[clinicalEventList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing clinical event batch into CLINICAL_EVENT");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }

    /**
     * Implementation of {@code ClinicalEventJdbcDao}
     */
    @Override
    public Integer addClinicalEventDataBatch(List<ClinicalEventData> clinicalEventDataList) {
        String SQL = "INSERT INTO clinical_event_data " +
                " (clinical_event_id, `key`, `value`) " + 
                "VALUES(:clinicalEventId, :key, :value)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(clinicalEventDataList.toArray(new ClinicalEventData[clinicalEventDataList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing clinical event data batch into CLINICAL_EVENT_DATA");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }

    /**
     * Implementation of {@code ClinicalEventJdbcDao}
     */
    @Override
    public Integer getLargestClinicalEventId() {
        String SQL = "SELECT MAX(clinical_event_id) FROM clinical_event";
        
        Integer clinicalEventId = -1;
        try {
            clinicalEventId = namedParameterJdbcTemplate.query(SQL, (ResultSet rs, int i) -> rs.getInt(1)).get(0);
        }
        catch (DataAccessException ex) {}
        
        return clinicalEventId;
    }
    
}
