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

import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of SampleProfileJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class SampleProfileJdbcDaoImpl implements SampleProfileJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(CancerStudyJdbcDaoImpl.class);
    
    @Autowired
    public SampleProfileJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code SampleProfileJdbcDao}
     */
    @Override
    public void addSampleProfile(Integer sampleId, Integer geneticProfileId, Integer genePanelId) {
        String SQL = "INSERT INTO sample_profile " + 
                    "(sample_id, genetic_profile_id, panel_id) " + 
                    "VALUES (:sample_id, :genetic_profile_id, :panel_id)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("sample_id", sampleId);
        namedParameters.addValue("genetic_profile_id", geneticProfileId);
        namedParameters.addValue("panel_id", genePanelId);
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {}
    }

    /**
     * Implementation of {@code SampleProfileJdbcDao}
     */
    @Override
    public boolean existsInGeneticProfile(Integer sampleId, Integer geneticProfileId) {
        String SQL = "SELECT 1 FROM sample_profile " + 
                "WHERE sample_id = :sample_id " + 
                "AND genetic_profile_id = :genetic_profile_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("sample_id", sampleId);
        namedParameters.addValue("genetic_profile_id", geneticProfileId);
        
        boolean exists = false;
        try {
            exists = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, Boolean.class);
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return exists;
    }
    
}
