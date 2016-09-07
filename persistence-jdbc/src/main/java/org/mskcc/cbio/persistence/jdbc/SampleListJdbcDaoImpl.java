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

import org.mskcc.cbio.model.SampleList;

import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.BeanPropertyRowMapper;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;
/**
 *
 * @author ochoaa
 */
@Repository
public class SampleListJdbcDaoImpl implements SampleListJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
    
    private static final Log LOG = LogFactory.getLog(SampleListJdbcDaoImpl.class);
    
    @Autowired
    public SampleListJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code SampleListJdbcDao}
     */
    @Override
    public SampleList addSampleList(SampleList sampleList) {
        String SQL = "INSERT INTO sample_list " +
                "(stable_id, category, cancer_study_id, name, description) " + 
                "VALUES(:stable_id, :category, :cancer_study_id, :name, :description)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", sampleList.getStableId());
        namedParameters.addValue("category", sampleList.getCategory());
        namedParameters.addValue("cancer_study_id", sampleList.getCancerStudyId());
        namedParameters.addValue("name", sampleList.getName());
        namedParameters.addValue("description", sampleList.getDescription());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {
            ex.printStackTrace();
        }
        
        SampleList newSampleList = getSampleList(sampleList.getStableId());
        if (newSampleList == null) {
            LOG.error("Error importing new sample list: " + sampleList.getStableId());
            return sampleList;
        }
        else {
            return newSampleList;
        }
    }

    /**
     * Implementation of {@code SampleListJdbcDao}
     */
    @Override
    public void addSampleListList(SampleList sampleList, Integer sampleId) {
        String SQL = "INSERT INTO sample_list_list " +
                "(list_id, sample_id) " + 
                "VALUES(:list_id, :sample_id)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("list_id", sampleList.getListId());
        namedParameters.addValue("sample_id", sampleId);
                
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {
            LOG.error("Error importing new sample list list record for sample list: " + sampleList.getStableId());
            ex.printStackTrace();
        }    
    }

    /**
     * Implementation of {@code SampleListJdbcDao}
     */
    @Override
    public SampleList getSampleList(String stableId) {
        String SQL = "SELECT * FROM sample_list WHERE stable_id = :stable_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("stable_id", stableId);
        
        SampleList sampleList = null;
        try {
            sampleList = (SampleList) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, new BeanPropertyRowMapper(SampleList.class));
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return sampleList;
    }
    
}
