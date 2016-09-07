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
import org.mskcc.cbio.model.summary.ClinicalDataSummary;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of ClinicalDataJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class ClinicalDataJdbcDaoImpl implements ClinicalDataJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(ClinicalDataJdbcDaoImpl.class);
    
    @Autowired
    public ClinicalDataJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code ClinicalDataJdbcDao}
     */
    @Override
    public Integer addClinicalDataBatch(String tableName, List<ClinicalDataSummary> clinicalDataList) {
        String SQL = "INSERT INTO " + tableName + 
                " (internal_id, attr_id, attr_value) " + 
                "VALUES(:internalId, :attrId, :attrValue)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(clinicalDataList.toArray(new ClinicalDataSummary[clinicalDataList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing clinical data batch into table: " + tableName.toUpperCase());
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }
    
    /**
     * Implementation of {@code ClinicalDataJdbcDao}
     */
    @Override
    public Integer addClinicalDatum(String tableName, ClinicalDataSummary clinicalDatum) {
        String SQL = "INSERT INTO " + tableName + 
                " (internal_id, attr_id, attr_value) " + 
                "VALUES(:internal_id, :attr_id, :attr_value)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("internal_id", clinicalDatum.getInternalId());
        namedParameters.addValue("attr_id", clinicalDatum.getAttrId());
        namedParameters.addValue("attr_value", clinicalDatum.getAttrValue());
        
        int rowsAffected = 0;
        try {
            rowsAffected = namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {}
        
        return rowsAffected;
    }
    
    /**
     * Implementation of {@code ClinicalDataJdbcDao}
     */
    @Override
    public Map<String, String> getPatientClinicalDataAttributes(Integer internalId) {
        String SQL = "SELECT * FROM clinical_patient " +
                "WHERE internal_id = :internal_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("internal_id", internalId);
        
        List<PatientClinicalData> patientClinicalData = new ArrayList();
        try {
            patientClinicalData = (List<PatientClinicalData>) namedParameterJdbcTemplate.query(SQL, namedParameters, new BeanPropertyRowMapper(PatientClinicalData.class));
        }
        catch (EmptyResultDataAccessException ex) {}
        
        // generate map of attr id to attr values
        Map<String, String> patientClinicalDataMap = new HashMap<>();
        for (PatientClinicalData pcd : patientClinicalData) {
            patientClinicalDataMap.put(pcd.getAttrId(), pcd.getAttrValue());
        }
        
        return patientClinicalDataMap;
    }
    
    /**
     * Implementation of {@code ClinicalDataJdbcDao}
     */
    @Override
    public Map<String, String> getSampleClinicalDataAttributes(Integer internalId) {
        String SQL = "SELECT * FROM clinical_sample " +
                "WHERE internal_id = :internal_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("internal_id", internalId);
        
        List<SampleClinicalData> sampleClinicalData = new ArrayList();
        try {
            sampleClinicalData = (List<SampleClinicalData>) namedParameterJdbcTemplate.query(SQL, namedParameters, new BeanPropertyRowMapper(SampleClinicalData.class));
        }
        catch (EmptyResultDataAccessException ex) {}
        
        // generate map of attr id to attr values
        Map<String, String> sampleClinicalDataMap = new HashMap<>();
        for (SampleClinicalData scd : sampleClinicalData) {
            sampleClinicalDataMap.put(scd.getAttrId(), scd.getAttrValue());
        }
        
        return sampleClinicalDataMap;
    }    
    
}
