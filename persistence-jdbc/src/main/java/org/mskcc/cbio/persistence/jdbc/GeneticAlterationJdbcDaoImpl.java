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
import javax.annotation.Resource;
import org.apache.commons.logging.*;
import org.apache.commons.lang.StringUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of GeneticAlterationJdbcDao
 * @author ochoaa
 */
@Repository
public class GeneticAlterationJdbcDaoImpl implements GeneticAlterationJdbcDao {
    
    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(GeneticAlterationJdbcDaoImpl.class);
    
    @Autowired
    public GeneticAlterationJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code GeneticProfileSamplesJdbcDao}
     */
    @Override
    public void addGeneticAlterations(GeneticProfile geneticProfile, Gene gene, List<String> values) {
        String SQL = "INSERT INTO genetic_alteration " + 
                "(genetic_profile_id, entrez_gene_id, `values`) " +
                "VALUES(:genetic_profile_id, :entrez_gene_id, :values)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("genetic_profile_id", geneticProfile.getGeneticProfileId());
        namedParameters.addValue("entrez_gene_id", gene.getEntrezGeneId());
        namedParameters.addValue("values", StringUtils.join(values, ","));
     
        try{
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }        
        catch (DataAccessException ex) {
            LOG.error("Error importing GENETIC_ALTERATION record with genetic profile stable id: " + geneticProfile.getStableId() + 
                    " and gene symbol: " + gene.getHugoGeneSymbol());
            ex.printStackTrace();
        }
    }

}
