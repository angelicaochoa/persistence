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
import org.apache.commons.lang.StringUtils;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of SampleJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class SampleJdbcDaoImpl implements SampleJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(SampleJdbcDaoImpl.class);

    @Autowired
    public SampleJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code SampleJdbcDao}
     */
    @Override
    public Sample addSample(Sample sample) {
        String SQL = "INSERT INTO sample " + 
                "(stable_id, sample_type, patient_id, type_of_cancer_id) " + 
                "VALUES (:stable_id, :sample_type, :patient_id, :type_of_cancer_id)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", sample.getStableId());
        namedParameters.addValue("sample_type", sample.getSampleType().getName());
        namedParameters.addValue("patient_id", sample.getPatientId());
        namedParameters.addValue("type_of_cancer_id", sample.getTypeOfCancerId());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
        Sample newSample = getSampleByPatient(sample.getStableId(), sample.getPatientId());
        if (newSample == null || newSample.getInternalId() == -1) {
            LOG.error("Error importing new sample record: " + sample.getStableId());
            return sample;
        }
        else {
            return newSample;
        }
    }

    /**
     * Implementation of {@code SampleJdbcDao}
     */
    @Override
    public Sample getSample(int internalId) {
        String SQL = "SELECT " +
                    "sample.internal_id AS 'sample.internalId', " +
                    "sample.stable_id AS 'sample.stableId', " +
                    "sample.sample_type AS 'sample.sampleType', " +
                    "sample.patient_id AS 'sample.patientId', " +
                    "sample.type_of_cancer_id AS 'sample.typeOfCancerId', " +
                    "patient.internal_id AS 'patient.internalId', " +
                    "patient.stable_id AS 'patient.stableId', " +
                    "patient.cancer_study_id AS 'patient.cancerStudyId' " +
                    "FROM sample " +
                    "INNER JOIN patient ON patient.internal_id = sample.patient_id " +
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = sample.type_of_cancer_id " +
                    "WHERE sample.internal_id = :internal_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource("internal_id", internalId);
        
        Sample sample = null;
        try {
            sample = (Sample) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, sampleRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return sample;
    }

    /**
     * Implementation of {@code SampleJdbcDao}
     */
    @Override
    public Sample getSampleByPatient(String stableId, int patientId) {
        String SQL = "SELECT " +
                    "sample.internal_id AS 'sample.internalId', " +
                    "sample.stable_id AS 'sample.stableId', " +
                    "sample.sample_type AS 'sample.sampleType', " +
                    "sample.patient_id AS 'sample.patientId', " +
                    "sample.type_of_cancer_id AS 'sample.typeOfCancerId', " +
                    "patient.internal_id AS 'patient.internalId', " +
                    "patient.stable_id AS 'patient.stableId', " +
                    "patient.cancer_study_id AS 'patient.cancerStudyId' " +
                    "FROM sample " +
                    "JOIN patient ON patient.internal_id = sample.patient_id " +
                    "JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = sample.type_of_cancer_id " +
                    "WHERE sample.stable_id = :stable_id " +
                    "AND sample.patient_id = :patient_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", stableId);        
        namedParameters.addValue("patient_id", patientId);
        
        Sample sample = null;
        try {
            sample = (Sample) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, sampleRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return sample;
    }

    /**
     * Implementation of {@code SampleJdbcDao}
     */
    @Override
    public Sample getSampleByStudy(String stableId, int cancerStudyId) {
        String SQL = "SELECT " +
                    "sample.internal_id AS 'sample.internalId', " +
                    "sample.stable_id AS 'sample.stableId', " +
                    "sample.sample_type AS 'sample.sampleType', " +
                    "sample.patient_id AS 'sample.patientId', " +
                    "sample.type_of_cancer_id AS 'sample.typeOfCancerId', " +
                    "patient.internal_id AS 'patient.internalId', " +
                    "patient.stable_id AS 'patient.stableId', " +
                    "patient.cancer_study_id AS 'patient.cancerStudyId' " +
                    "FROM sample " +
                    "INNER JOIN patient ON patient.internal_id = sample.patient_id " +
                    "WHERE sample.stable_id = :stable_id " +
                    "AND patient.cancer_study_id = :cancer_study_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", stableId);
        namedParameters.addValue("cancer_study_id", cancerStudyId);
        
        Sample sample = null;
        try {
            sample = (Sample) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, sampleRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return sample;
    }
    
    /**
     * Sample row mapper.
     * @return 
     */
    private RowMapper sampleRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("sample", BeanMap.create(new Sample()));
            beans_by_name.put("patient", BeanMap.create(new Patient()));
            
            // go through each column from sql query and set respective bean properties accordingly
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int index=1; index<=resultSetMetaData.getColumnCount(); index++) {
                // get the table name and the field name for the bean
                String table = resultSetMetaData.getColumnLabel(index).split("\\.")[0];
                String field = resultSetMetaData.getColumnLabel(index).split("\\.")[1];
                
                BeanMap beanMap = beans_by_name.get(table);
                if (rs.getObject(index) != null) {
                    // if sampleType then set SampleType enum
                    if (field.equals("sampleType")) {
                        String sampleType = StringUtils.join(rs.getString(index).split(" "), "_").toUpperCase();
                        beanMap.put(field, Sample.SampleType.valueOf(sampleType));
                    }
                    else {
                        beanMap.put(field, rs.getObject(index));
                    }
                }
            }
            Sample sample = (Sample) beans_by_name.get("sample").getBean();
            sample.setPatient((Patient) beans_by_name.get("patient").getBean());
            
            return sample;
        };
    }

}
