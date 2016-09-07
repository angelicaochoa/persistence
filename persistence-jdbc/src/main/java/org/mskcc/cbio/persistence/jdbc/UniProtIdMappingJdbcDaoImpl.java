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

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of UniProtIdMappingJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class UniProtIdMappingJdbcDaoImpl implements UniProtIdMappingJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(UniProtIdMappingJdbcDaoImpl.class);
    
    @Autowired
    public UniProtIdMappingJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code UniProtIdMappingJdbcDao}
     */
    @Override
    public List<String> mapEntrezGeneIdToUniProtAccession(Integer entrezGeneId) {
        String SQL = "SELECT uniprot_acc FROM uniprot_id_mapping WHERE entrez_gene_id = :entrez_gene_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("entrez_gene_id", entrezGeneId);
        
        List<String> uniProtAccessions = new ArrayList();
        try {
            uniProtAccessions = namedParameterJdbcTemplate.queryForList(SQL, namedParameters, String.class);
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return uniProtAccessions;
    }

    /**
     * Implementation of {@code UniProtIdMappingJdbcDao}
     */
    @Override
    public String mapUniProtAccessionToId(String uniProtAccession) {
        String SQL = "SELECT uniprot_id FROM uniprot_id_mapping WHERE uniprot_acc = :uniprot_acc";
        SqlParameterSource namedParameters = new MapSqlParameterSource("uniprot_acc", uniProtAccession);
        
        String uniProtId = null;
        try {
            uniProtId = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, String.class);
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return uniProtId;
    }

    /**
     * Implementation of {@code UniProtIdMappingJdbcDao}
     */
    @Override
    public String mapUniProtIdToAccession(String uniProtId) {
        String SQL = "SELECT uniprot_acc FROM uniprot_id_mapping WHERE uniprot_id = :uniprot_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("uniprot_id", uniProtId);
        
        String uniProtAccession = null;
        try {
            uniProtAccession = namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, String.class);
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return uniProtAccession;
    }
    
}
