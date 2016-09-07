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
import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.*;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of CancerStudyJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class CancerStudyJdbcDaoImpl implements CancerStudyJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(CancerStudyJdbcDaoImpl.class);
    
    @Autowired
    public CancerStudyJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Implementation of {@code CancerStudyJdbcDao}
     */
    @Override
    public CancerStudy addCancerStudy(CancerStudy cancerStudy) {
        String SQL = "INSERT INTO cancer_study " + 
                "(cancer_study_identifier, type_of_cancer_id, name, short_name, description, public, pmid, citation, groups, status, import_date) " + 
                "VALUES (:cancer_study_identifier, :type_of_cancer_id, :name, :short_name, :description, :public, :pmid, :citation, :groups, :status, :import_date)"; 
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cancer_study_identifier", cancerStudy.getCancerStudyIdentifier());
        namedParameters.addValue("type_of_cancer_id", cancerStudy.getTypeOfCancerId());
        namedParameters.addValue("name", cancerStudy.getName());
        namedParameters.addValue("short_name", cancerStudy.getShortName());
        namedParameters.addValue("description", cancerStudy.getDescription());
        namedParameters.addValue("public", cancerStudy.getPublicStudy());
        namedParameters.addValue("pmid", cancerStudy.getPmid());
        namedParameters.addValue("citation", cancerStudy.getCitation());
        namedParameters.addValue("groups", cancerStudy.getGroups());
        namedParameters.addValue("status", cancerStudy.getStatus());
        namedParameters.addValue("import_date", cancerStudy.getImportDate());
        
        try{
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }        
        catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
        CancerStudy newCancerStudy = getCancerStudy(cancerStudy.getCancerStudyIdentifier());
        if (newCancerStudy == null || newCancerStudy.getCancerStudyId() == -1) {
            LOG.error("Error importing CANCER_STUDY record with CANCER_STUDY_IDENTIFIER: " + 
                    cancerStudy.getCancerStudyIdentifier());
            return cancerStudy;
        }
        else {
            return newCancerStudy;
        }
    }

    /**
     * Implementation of {@code CancerStudyJdbcDao}
     */
    @Override
    public CancerStudy getCancerStudy(String cancerStudyIdentifier) {
        String SQL = "SELECT " +
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
                    "cancer_study.status AS 'cancer_study.status', " +
                    "cancer_study.import_date AS 'cancer_study.importDate', " +
                    "type_of_cancer.type_of_cancer_id AS 'type_of_cancer.typeOfCancerId', " +
                    "type_of_cancer.name AS 'type_of_cancer.name', " +
                    "type_of_cancer.clinical_trial_keywords AS 'type_of_cancer.clinicalTrialKeywords', " +
                    "type_of_cancer.dedicated_color AS 'type_of_cancer.dedicatedColor', " +
                    "type_of_cancer.short_name AS 'type_of_cancer.shortName', " +
                    "type_of_cancer.parent AS 'type_of_cancer.parent' " +
                    "FROM cancer_study " + 
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = cancer_study.type_of_cancer_id " +
                    "WHERE cancer_study.cancer_study_identifier = :cancer_study_identifier";
        SqlParameterSource namedParameters = new MapSqlParameterSource("cancer_study_identifier", cancerStudyIdentifier);
        
        CancerStudy cancerStudy = null;
        try {
            cancerStudy = (CancerStudy) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, cancerStudyRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return cancerStudy;
    }

    /**
     * Implementation of {@code CancerStudyJdbcDao}
     */
    @Override
    public CancerStudy getCancerStudy(Integer cancerStudyId) {
        String SQL = "SELECT " +
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
                    "cancer_study.status AS 'cancer_study.status', " +
                    "cancer_study.import_date AS 'cancer_study.importDate', " +
                    "type_of_cancer.type_of_cancer_id AS 'type_of_cancer.typeOfCancerId', " +
                    "type_of_cancer.name AS 'type_of_cancer.name', " +
                    "type_of_cancer.clinical_trial_keywords AS 'type_of_cancer.clinicalTrialKeywords', " +
                    "type_of_cancer.dedicated_color AS 'type_of_cancer.dedicatedColor', " +
                    "type_of_cancer.short_name AS 'type_of_cancer.shortName', " +
                    "type_of_cancer.parent AS 'type_of_cancer.parent' " +
                    "FROM cancer_study " + 
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = cancer_study.type_of_cancer_id " +
                    "WHERE cancer_study.cancer_study_id = :cancer_study_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("cancer_study_id", cancerStudyId);
        
        CancerStudy cancerStudy = null;
        try {
            cancerStudy = (CancerStudy) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, cancerStudyRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return cancerStudy;
    }

    /**
     * Implementation of {@code CancerStudyJdbcDao}
     */
    @Override
    public List<CancerStudy> listAllCancerStudies(){
        String SQL = "SELECT " +
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
                    "cancer_study.status AS 'cancer_study.status', " +
                    "cancer_study.import_date AS 'cancer_study.importDate', " +
                    "type_of_cancer.type_of_cancer_id AS 'type_of_cancer.typeOfCancerId', " +
                    "type_of_cancer.name AS 'type_of_cancer.name', " +
                    "type_of_cancer.clinical_trial_keywords AS 'type_of_cancer.clinicalTrialKeywords', " +
                    "type_of_cancer.dedicated_color AS 'type_of_cancer.dedicatedColor', " +
                    "type_of_cancer.short_name AS 'type_of_cancer.shortName', " +
                    "type_of_cancer.parent AS 'type_of_cancer.parent' " +
                    "FROM cancer_study " + 
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = cancer_study.type_of_cancer_id";
        
        List<CancerStudy> cancerStudies = new ArrayList();
        try {
            cancerStudies = (List<CancerStudy>) namedParameterJdbcTemplate.query(SQL, cancerStudyRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return cancerStudies;
    }
    
    /**
     * Implementation of {@code CancerStudyJdbcDao}
     */
    @Override
    public void deleteCancerStudy(Integer cancerStudyId) {
        // SQL statements for deleting a cancer study
        String[] SQLs = {
            "DELETE FROM sample_cna_event WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM genetic_alteration WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM genetic_profile_samples WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM sample_profile WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM mutation WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM mutation_event WHERE mutation_event_id NOT IN (SELECT mutation_event_id FROM mutation);",
            "DELETE FROM mutation_count WHERE genetic_profile_id IN (SELECT genetic_profile_id FROM genetic_profile WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM clinical_attribute_meta WHERE cancer_study_id = :cancer_study_id",
            "DELETE FROM clinical_event_data WHERE clinical_event_id IN (SELECT clinical_event_id FROM clinical_event WHERE patient_id in (SELECT internal_id FROM patient WHERE cancer_study_id = :cancer_study_id))",
            "DELETE FROM clinical_event WHERE patient_id in (SELECT internal_id FROM patient WHERE cancer_study_id = :cancer_study_id)",
            "DELETE FROM sample_list_list WHERE list_id IN (SELECT list_id FROM sample_list WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM clinical_sample WHERE internal_id IN (SELECT internal_id FROM sample WHERE patient_id in (SELECT internal_id FROM patient WHERE cancer_study_id = :cancer_study_id));",
            "DELETE FROM sample WHERE patient_id IN (SELECT internal_id FROM patient WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM clinical_patient WHERE internal_id IN (SELECT internal_id FROM patient WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM patient WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM copy_number_seg WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM sample_list WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM genetic_profile WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM gistic_to_gene WHERE gistic_roi_id IN (SELECT gistic_roi_id FROM gistic WHERE cancer_study_id = :cancer_study_id);",
            "DELETE FROM gistic WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM mut_sig WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM protein_array_data WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM protein_array_cancer_study WHERE cancer_study_id = :cancer_study_id;",
            "DELETE FROM cancer_study WHERE cancer_study_id = :cancer_study_id;"
        };
        
        SqlParameterSource namedParameters = new MapSqlParameterSource("cancer_study_id", cancerStudyId);
        for (String SQL : SQLs) {
            try {
                namedParameterJdbcTemplate.update(SQL, namedParameters);
            }
            catch (DataAccessException ex) {
                LOG.error(ex.getMessage());
            }
        }
    }
    
    /**
     * CancerStudy row mapper.
     * 
     * @return RowMapper
     */
    private RowMapper cancerStudyRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("cancer_study", BeanMap.create(new CancerStudy()));
            beans_by_name.put("type_of_cancer", BeanMap.create(new TypeOfCancer()));
            
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
            CancerStudy cancerStudy = (CancerStudy) beans_by_name.get("cancer_study").getBean();
            cancerStudy.setTypeOfCancer((TypeOfCancer) beans_by_name.get("type_of_cancer").getBean());
            
            return cancerStudy;
        };
    }
    
}
