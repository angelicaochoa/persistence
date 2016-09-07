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
 * JDBC implementation of GeneJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class GeneJdbcDaoImpl implements GeneJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(GeneJdbcDaoImpl.class);
    
    @Autowired
    public GeneJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public Gene addGene(Gene gene) {
        // set entrez gene id to next generated entrez gene id if null or <= 0
        if (gene.getEntrezGeneId() == null || gene.getEntrezGeneId() <= 0) {
            gene.setEntrezGeneId(getNextGeneratedEntrezGeneId());
        }
        
        String SQL = "INSERT INTO gene " +
                "(entrez_gene_id, hugo_gene_symbol, type, cytoband, length) " + 
                "VALUES(:entrez_gene_id, :hugo_gene_symbol, :type, :cytoband, :length)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", gene.getEntrezGeneId());
        namedParameters.addValue("hugo_gene_symbol", gene.getHugoGeneSymbol().toUpperCase());
        namedParameters.addValue("type", gene.getType());
        namedParameters.addValue("cytoband", gene.getCytoband());
        namedParameters.addValue("length", gene.getLength());

        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (DataAccessException ex) {}
        
        // also add gene aliases if not empty
        if (!gene.getAliases().isEmpty()) {
            addGeneAliases(gene);
        }
        
        Gene newGene = getGene(gene.getHugoGeneSymbol().toUpperCase());
        if (newGene == null) {
            LOG.error("Error importing new gene record with hugo symbol: " + gene.getHugoGeneSymbol());
        }
        
        return newGene;
    }
    
    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public void addGeneAliases(Gene gene) {
        String SQL = "INSERT INTO gene_alias " +
                "(entrez_gene_id, gene_alias) " +
                "VALUES(:entrez_gene_id, :gene_alias)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", gene.getEntrezGeneId());
        
        for (String alias : gene.getAliases()) {
            namedParameters.addValue("gene_alias", alias);
            try {
                namedParameterJdbcTemplate.update(SQL, namedParameters);
            }
            catch (DataAccessException ex) {}
        }
    }
    
    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public Gene getGene(Integer entrezGeneId) {
        String SQL = "SELECT " +
                    "gene.entrez_gene_id AS 'gene.entrezGeneId', " +
                    "gene.hugo_gene_symbol AS 'gene.hugoGeneSymbol', " +
                    "gene.type AS 'gene.type', " +
                    "gene.cytoband AS 'gene.cytoband', " +
                    "gene.length AS 'gene.length', " +
                    "GROUP_CONCAT(gene_alias.gene_alias) AS 'gene.geneAliases' " +
                    "FROM gene " +
                    "INNER JOIN gene_alias ON gene_alias.entrez_gene_id = gene.entrez_gene_id " +
                    "WHERE gene.entrez_gene_id = :entrez_gene_id " +
                    "GROUP BY gene.entrez_gene_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("entrez_gene_id", entrezGeneId);
        
        Gene gene = null;
        try {
            gene = (Gene) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, geneRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return gene;
    }

    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public Gene getGene(String hugoGeneSymbol) {
        String SQL = "SELECT " +
                    "gene.entrez_gene_id AS 'gene.entrezGeneId', " +
                    "gene.hugo_gene_symbol AS 'gene.hugoGeneSymbol', " +
                    "gene.type AS 'gene.type', " +
                    "gene.cytoband AS 'gene.cytoband', " +
                    "gene.length AS 'gene.length', " +
                    "GROUP_CONCAT(gene_alias.gene_alias) AS 'gene.geneAliases' " +
                    "FROM gene " +
                    "INNER JOIN gene_alias ON gene_alias.entrez_gene_id = gene.entrez_gene_id " +
                    "WHERE gene.hugo_gene_symbol = :hugo_gene_symbol " +
                    "GROUP BY gene.entrez_gene_id";
        SqlParameterSource namedParameters = new MapSqlParameterSource("hugo_gene_symbol", hugoGeneSymbol);
        
        Gene gene = null;
        try {
            gene = (Gene) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, geneRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return gene;
    }
    
    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public Gene getGene(Integer entrezGeneId, String geneAlias) {
        String SQL = "SELECT " +
                    "gene.entrez_gene_id AS 'gene.entrezGeneId', " +
                    "gene.hugo_gene_symbol AS 'gene.hugoGeneSymbol', " +
                    "gene.type AS 'gene.type', " +
                    "gene.cytoband AS 'gene.cytoband', " +
                    "gene.length AS 'gene.length', " +
                    "GROUP_CONCAT(gene_alias.gene_alias) AS 'gene.geneAliases' " +
                    "FROM gene " +
                    "INNER JOIN gene_alias ON gene_alias.entrez_gene_id = gene.entrez_gene_id " +
                    "WHERE gene.entrez_gene_id = :entrez_gene_id " +
                    "AND :alias IN (SELECT gene_alias.gene_alias FROM gene_alias WHERE gene_alias.entrez_gene_id = :entrez_gene_id) " +
                    "GROUP BY gene.entrez_gene_id";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", entrezGeneId);
        namedParameters.addValue("alias", geneAlias);
        
        Gene gene = null;
        try {
            gene = (Gene) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, geneRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return gene;
    }

    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public List<Gene> listAllGenes() {
        String SQL = "SELECT " +
                    "gene.entrez_gene_id AS 'gene.entrezGeneId', " +
                    "gene.hugo_gene_symbol AS 'gene.hugoGeneSymbol', " +
                    "gene.type AS 'gene.type', " +
                    "gene.cytoband AS 'gene.cytoband', " +
                    "gene.length AS 'gene.length', " +
                    "(SELECT GROUP_CONCAT(gene_alias.gene_alias) FROM gene_alias WHERE gene_alias.entrez_gene_id = gene.entrez_gene_id) AS 'gene.geneAliases' " +
                    "FROM gene " +
                    "GROUP BY gene.entrez_gene_id";
        
        List<Gene> genes = new ArrayList();
        try {
            genes = (List<Gene>) namedParameterJdbcTemplate.query(SQL, geneRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}

        return genes;
    }

    /**
     * Implementation of {@code GeneJdbcDao}
     */
    @Override
    public Integer getNextGeneratedEntrezGeneId() {
        String SQL = "SELECT MIN(entrez_gene_id) FROM gene";
        
        Integer nextGeneratedEntrezGeneId = null;
        try {
            nextGeneratedEntrezGeneId = namedParameterJdbcTemplate.query(SQL, (ResultSet rs, int i) -> rs.getInt(1)).get(0);
        }
        catch (DataAccessException ex) {}
        
        return --nextGeneratedEntrezGeneId;
    }
    
    /**
     * Gene row mapper.
     * @return 
     */
    private RowMapper geneRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("gene", BeanMap.create(new Gene()));
          
            // go through each column from sql query and set respective bean properties accordingly
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int index=1; index<=resultSetMetaData.getColumnCount(); index++) {
                // get the table name and the field name for the bean
                String table = resultSetMetaData.getColumnLabel(index).split("\\.")[0];
                String field = resultSetMetaData.getColumnLabel(index).split("\\.")[1];
                
                BeanMap beanMap = beans_by_name.get(table);
                if (rs.getObject(index) != null) {
                    if (field.equals("geneAliases")) {
                        beanMap.put(field, Arrays.asList(rs.getString(index).split(",")));
                    }
                    else {
                        beanMap.put(field, rs.getObject(index));
                    }
                }
            }
            Gene gene = (Gene) beans_by_name.get("gene").getBean();

            return gene;
        };
    }
    
}
