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

import java.sql.*;
import java.util.HashMap;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of PatientJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class PatientJdbcDaoImpl implements PatientJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(PatientJdbcDaoImpl.class);
    
    @Autowired
    public PatientJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code PatientJdbcDao}
     */
    @Override
    public Patient addPatient(Patient patient) {
        String SQL = "INSERT INTO patient (stable_id, cancer_study_id) " + 
                "VALUES (:stable_id, :cancer_study_id)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", patient.getStableId());
        namedParameters.addValue("cancer_study_id", patient.getCancerStudyId());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);            
        }
        catch (DataAccessException ex) {}
        
        Patient newPatient = getPatient(patient.getStableId(), patient.getCancerStudyId());
        if (newPatient == null || newPatient.getInternalId() == -1) {
            LOG.error("Error importing new patient record: " + patient.getStableId());
            return patient;
        }
        else {
            return newPatient;
        }
    }

    /**
     * Implementation of {@code PatientJdbcDao}
     */
    @Override
    public Patient getPatient(String stableId, int cancerStudyId) {
        String SQL = "SELECT " + 
                "patient.internal_id AS 'patient.internalId', " + 
                "patient.stable_id AS 'patient.stableId', " + 
                "patient.cancer_study_id AS 'patient.cancerStudyId', " +
                "cancer_study.cancer_study_id AS 'cancer_study.cancerStudyId', " +
                "cancer_study.cancer_study_identifier AS 'cancer_study.cancerStudyIdentifier', " +
                "cancer_study.type_of_cancer_id AS 'cancer_study.typeOfCancerId', " +
                "cancer_study.name AS 'cancer_study.name', " +
                "cancer_study.short_name AS 'cancer_study.shortName', " +
                "cancer_study.description AS 'cancer_study.description', " +
                "cancer_study.public AS 'cancer_study.publicStudy', " +
                "cancer_study.pmid AS 'cancer_study.pmid', " +
                "cancer_study.citation AS 'cancer_study.citation', " +
                "cancer_study.groups AS 'cancer_study.groups', " +
                "cancer_study.status AS 'cancer_study.status' " +
                "FROM patient " + 
                "INNER JOIN cancer_study ON cancer_study.cancer_study_id = patient.cancer_study_id " + 
                "WHERE patient.stable_id = :stable_id AND patient.cancer_study_id = :cancer_study_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", stableId);
        namedParameters.addValue("cancer_study_id", cancerStudyId);
        
        Patient patient = null;
        try {
            patient = (Patient) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, patientRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return patient;
    }

    /**
     * Implementation of {@code PatientJdbcDao}
     */
    @Override
    public Patient getPatient(int internalId) {
        String SQL = "SELECT * FROM patient " + 
                "WHERE internal_id = :internal_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("internal_id", internalId);
        
        Patient patient = null;
        try {
            patient = (Patient) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, patientRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return patient;
    }
    
    /**
     * Patient row mapper for extracting result sets.
     * @return 
     */
    private RowMapper patientRowMapper() {
        return (RowMapper) new RowMapper() {
            @Override
            public Object mapRow(ResultSet rs, int i) throws SQLException {
                HashMap<String, BeanMap> beans_by_name = new HashMap();
                beans_by_name.put("patient", BeanMap.create(new Patient()));
                beans_by_name.put("cancer_study", BeanMap.create(new CancerStudy()));
                
                // go through each column from sql query and set respective bean properties accordingly
                ResultSetMetaData resultSetMetaData = rs.getMetaData();
                for (int index=1; index<=resultSetMetaData.getColumnCount(); index++) {
                    // get the table name and the field name for the bean
                    String table = resultSetMetaData.getColumnLabel(index).split("\\.")[0];
                    String field = resultSetMetaData.getColumnLabel(index).split("\\.")[1];
                    
                    BeanMap beanMap = beans_by_name.get(table);
                    if (rs.getObject(index) != null) {
                        beanMap.put(field, rs.getObject(index));
                    }
                }
                Patient patient = (Patient) beans_by_name.get("patient").getBean();
                patient.setCancerStudy((CancerStudy) beans_by_name.get("cancer_study").getBean());
                
                return patient;
            }
        };
    }
    
}
