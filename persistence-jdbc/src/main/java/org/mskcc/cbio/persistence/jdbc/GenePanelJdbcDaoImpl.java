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
 * JDBC implementation of GenePanelJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class GenePanelJdbcDaoImpl implements GenePanelJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(GenePanelJdbcDaoImpl.class);
    
    @Autowired
    public GenePanelJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code GenePanelJdbcDao}
     */
    @Override
    public void addGenePanel(String stableId, String description) {
        String SQL = "INSERT INTO gene_panel " +
                "(stable_id, description) " +
                "VALUES (:stable_id, :description)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", stableId);
        namedParameters.addValue("description", description);
        
        try{
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }        
        catch (DataAccessException ex) {
            LOG.error("Error importing GENE_PANEL record with stable id: " + stableId);
        }
    }

    /**
     * Implementation of {@code GenePanelJdbcDao}
     */
    @Override
    public Integer getGenePanelId(String stableId) {
        String SQL = "SELECT internal_id FROM gene_panel " +
                "WHERE stable_id = :stable_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("stable_id", stableId);
        
        Integer internalId = null;
        try {
            internalId = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, Integer.class);
        }
        catch (EmptyResultDataAccessException ex) {}
        
        
        return internalId;        
    }
    
}
