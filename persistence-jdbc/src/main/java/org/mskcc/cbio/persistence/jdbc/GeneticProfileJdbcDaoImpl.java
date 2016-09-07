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
 * JDBC implementation of GeneticProfileJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class GeneticProfileJdbcDaoImpl implements GeneticProfileJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(GeneticProfileJdbcDaoImpl.class);
    
    @Autowired
    public GeneticProfileJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }    
    
    /**
     * Implementation of {@code GeneticProfileJdbcDao}
     */
    @Override
    public GeneticProfile addGeneticProfile(GeneticProfile geneticProfile) {
        String SQL = "INSERT INTO genetic_profile " +
                "(stable_id, cancer_study_id, genetic_alteration_type, datatype, name, description, show_profile_in_analysis_tab) " +
                "VALUES (:stable_id, :cancer_study_id, :genetic_alteration_type, :datatype, :name, :description, :show_profile_in_analysis_tab)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", geneticProfile.getStableId());
        namedParameters.addValue("cancer_study_id", geneticProfile.getCancerStudyId());
        namedParameters.addValue("genetic_alteration_type", geneticProfile.getGeneticAlterationType());
        namedParameters.addValue("datatype", geneticProfile.getDatatype());
        namedParameters.addValue("name", geneticProfile.getName());
        namedParameters.addValue("description", geneticProfile.getDescription());
        namedParameters.addValue("show_profile_in_analysis_tab", geneticProfile.getShowProfileInAnalysisTab());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {}
        
        GeneticProfile newGeneticProfile = getGeneticProfile(geneticProfile.getStableId());
        if (newGeneticProfile == null) {
            LOG.error("Error importing GENETIC_PROFILE record with stable id: " + geneticProfile.getStableId());
            return geneticProfile;
        }
        else {
            return newGeneticProfile;
        }
    }

    /**
     * Implementation of {@code GeneticProfileJdbcDao}
     */    
    @Override
    public GeneticProfile getGeneticProfile(String stableId) {
        String SQL  = "SELECT " + 
                    "genetic_profile.genetic_profile_id AS 'genetic_profile.geneticProfileId', " +
                    "genetic_profile.stable_id AS 'genetic_profile.stableId', " +
                    "genetic_profile.cancer_study_id AS 'genetic_profile.cancerStudyId', " +
                    "genetic_profile.genetic_alteration_type AS 'genetic_profile.geneticAlterationType', " +
                    "genetic_profile.datatype AS 'genetic_profile.datatype', " +
                    "genetic_profile.name AS 'genetic_profile.name', " +
                    "genetic_profile.description AS 'genetic_profile.description', " +
                    "genetic_profile.show_profile_in_analysis_tab AS 'genetic_profile.showProfileInAnalysisTab', " +
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
                    "type_of_cancer.type_of_cancer_id AS 'type_of_cancer.typeOfCancerId', " +
                    "type_of_cancer.name AS 'type_of_cancer.name', " +
                    "type_of_cancer.clinical_trial_keywords AS 'type_of_cancer.clinicalTrialKeywords', " +
                    "type_of_cancer.dedicated_color AS 'type_of_cancer.dedicatedColor', " +
                    "type_of_cancer.short_name AS 'type_of_cancer.shortName', " +
                    "type_of_cancer.parent AS 'type_of_cancer.parent' " +
                    "FROM genetic_profile " +
                    "INNER JOIN cancer_study ON cancer_study.cancer_study_id = genetic_profile.cancer_study_id " +
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = cancer_study.type_of_cancer_id " +
                    "WHERE genetic_profile.stable_id = :stable_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("stable_id", stableId);
        
        GeneticProfile geneticProfile = null;
        try {
            geneticProfile = (GeneticProfile) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, geneticProfileRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
                    
        return geneticProfile;
    }

    /**
     * Implementation of {@code GeneticProfileJdbcDao}
     */
    @Override
    public GeneticProfile getGeneticProfile(int geneticProfileId) {
        String SQL  = "SELECT " + 
                    "genetic_profile.genetic_profile_id AS 'genetic_profile.geneticProfileId', " +
                    "genetic_profile.stable_id AS 'genetic_profile.stableId', " +
                    "genetic_profile.cancer_study_id AS 'genetic_profile.cancerStudyId', " +
                    "genetic_profile.genetic_alteration_type AS 'genetic_profile.geneticAlterationType', " +
                    "genetic_profile.datatype AS 'genetic_profile.datatype', " +
                    "genetic_profile.name AS 'genetic_profile.name', " +
                    "genetic_profile.description AS 'genetic_profile.description', " +
                    "genetic_profile.show_profile_in_analysis_tab AS 'genetic_profile.showProfileInAnalysisTab', " +
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
                    "type_of_cancer.type_of_cancer_id AS 'type_of_cancer.typeOfCancerId', " +
                    "type_of_cancer.name AS 'type_of_cancer.name', " +
                    "type_of_cancer.clinical_trial_keywords AS 'type_of_cancer.clinicalTrialKeywords', " +
                    "type_of_cancer.dedicated_color AS 'type_of_cancer.dedicatedColor', " +
                    "type_of_cancer.short_name AS 'type_of_cancer.shortName', " +
                    "type_of_cancer.parent AS 'type_of_cancer.parent' " +
                    "FROM genetic_profile " +
                    "INNER JOIN cancer_study ON cancer_study.cancer_study_id = genetic_profile.cancer_study_id " +
                    "INNER JOIN type_of_cancer ON type_of_cancer.type_of_cancer_id = cancer_study.type_of_cancer_id " +
                    "WHERE genetic_profile.genetic_profile_id = :genetic_profile_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("genetic_profile_id", geneticProfileId);
        
        GeneticProfile geneticProfile = null;
        try {
            geneticProfile = (GeneticProfile) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, geneticProfileRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
                    
        return geneticProfile;        
    }
    
    private RowMapper geneticProfileRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("genetic_profile", BeanMap.create(new GeneticProfile()));
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
                    if (field.equals("showProfileInAnalysisTab")) {
                        beanMap.put(field, rs.getBoolean(index));
                    }
                    else {
                        beanMap.put(field, rs.getObject(index));
                    }
                }
            }
            GeneticProfile geneticProfile = (GeneticProfile) beans_by_name.get("genetic_profile").getBean();
            geneticProfile.setCancerStudy((CancerStudy) beans_by_name.get("cancer_study").getBean());
            geneticProfile.getCancerStudy().setTypeOfCancer((TypeOfCancer) beans_by_name.get("type_of_cancer").getBean());
            
            return geneticProfile;
        };
    }
    
}
