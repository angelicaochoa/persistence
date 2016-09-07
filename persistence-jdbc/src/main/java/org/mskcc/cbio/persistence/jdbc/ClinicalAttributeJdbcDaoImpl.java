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

import org.mskcc.cbio.model.ClinicalAttribute;

import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of ClinicalAttributeJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class ClinicalAttributeJdbcDaoImpl implements ClinicalAttributeJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final Log LOG = LogFactory.getLog(ClinicalAttributeJdbcDaoImpl.class);
    
    @Autowired
    public ClinicalAttributeJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code ClinicalAttributeJdbcDao}
     */
    @Override
    public void addClinicalAttribute(ClinicalAttribute clinicalAttribute) {
        String SQL = "INSERT INTO clinical_attribute_meta " + 
                "(attr_id, display_name, description, datatype, patient_attribute, priority, cancer_study_id) " +
                "VALUES (:attr_id, :display_name, :description, :datatype, :patient_attribute, :priority, :cancer_study_id)";        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("attr_id", clinicalAttribute.getAttrId());
        namedParameters.addValue("display_name", clinicalAttribute.getDisplayName());
        namedParameters.addValue("description", clinicalAttribute.getDescription());
        namedParameters.addValue("datatype", clinicalAttribute.getDatatype());
        namedParameters.addValue("patient_attribute", clinicalAttribute.getPatientAttribute());
        namedParameters.addValue("priority", clinicalAttribute.getPriority());
        namedParameters.addValue("cancer_study_id", clinicalAttribute.getCancerStudyId());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {
            LOG.error("Error importing new clinical attribute: " + clinicalAttribute.getAttrId());
            ex.printStackTrace();
        }
    }

    /**
     * Implementation of {@code ClinicalAttributeJdbcDao}
     */
    @Override
    public ClinicalAttribute getClinicalAttribute(String attrId, Integer cancerStudyId) {
        String SQL = "SELECT * FROM clinical_attribute_meta WHERE attr_id = :attr_id AND cancer_study_id = :cancer_study_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("attr_id", attrId);
        namedParameters.addValue("cancer_study_id", cancerStudyId);
        
        ClinicalAttribute clinicalAttribute = null;
        try {
            clinicalAttribute = (ClinicalAttribute) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, new BeanPropertyRowMapper(ClinicalAttribute.class));
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return clinicalAttribute;
    }
    
}
