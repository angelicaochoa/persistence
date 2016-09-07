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
import java.sql.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cglib.beans.BeanMap;
import org.springframework.dao.*;
import org.springframework.jdbc.core.*;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of CnaEventJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class CnaEventJdbcDaoImpl implements CnaEventJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(CnaEventJdbcDaoImpl.class);
    
    @Autowired
    public CnaEventJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code CnaEventJdbcDao}
     */
    @Override
    public void addCnaEvent(CnaEvent cnaEvent) {
        String SQL = "INSERT INTO cna_event " + 
                "(entrez_gene_id, alteration) " +
                "VALUES(:entrez_gene_id, :alteration)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", cnaEvent.getEntrezGeneId());
        namedParameters.addValue("alteration", Integer.valueOf(cnaEvent.getAlterationType().getName()));
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (Exception ex) {
            LOG.error("Error importing CNA_EVENT record");
            ex.printStackTrace();
        }
    }

    /**
     * Implementation of {@code CnaEventJdbcDao}
     */
    @Override
    public void addSampleCnaEvent(SampleCnaEvent sampleCnaEvent) {
        String SQL = "INSERT INTO sample_cna_event " +
                "(cna_event_id, sample_id, genetic_profile_id) " +
                "VALUES(:cna_event_id, :sample_id, :genetic_profile_id)";        
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cna_event_id", sampleCnaEvent.getCnaEventId());
        namedParameters.addValue("sample_id", sampleCnaEvent.getSampleId());
        namedParameters.addValue("genetic_profile_id", sampleCnaEvent.getGeneticProfileId());
        
        try {
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }
        catch (Exception ex) {
            LOG.error("Error importing SAMPLE_CNA_EVENT record");
            ex.printStackTrace();
        }
    }
    
    /**
     * Implementation of {@code CnaEventJdbcDao}
     */
    @Override
    public CnaEvent getCnaEvent(CnaEvent cnaEvent) {
        String SQL = "SELECT " + 
                "cna_event.cna_event_id AS 'cna_event.cnaEventId', " + 
                "cna_event.entrez_gene_id AS 'cna_event.entrezGeneId', " + 
                "cna_event.alteration AS 'cna_event.alteration' " + 
                "FROM cna_event " + 
                "WHERE cna_event.entrez_gene_id = :entrez_gene_id " +
                "AND cna_event.alteration = :alteration";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("entrez_gene_id", cnaEvent.getEntrezGeneId());
        namedParameters.addValue("alteration", Integer.valueOf(cnaEvent.getAlterationType().getName()));
        
        CnaEvent existingCnaEvent = null;
        try {
            existingCnaEvent = (CnaEvent) namedParameterJdbcTemplate.queryForObject(SQL, namedParameters, cnaEventRowMapper());
        }
        catch (EmptyResultDataAccessException ex) {}
        
        return existingCnaEvent;
    }
    
    /**
     * Implementation of {@code CnaEventJdbcDao}
     */
    @Override
    public Integer getLargestCnaEventId() {
        String SQL = "SELECT MAX(cna_event_id) FROM cna_event";
        
        Integer cnaEventId = -1;
        try {
            cnaEventId = namedParameterJdbcTemplate.query(SQL, (ResultSet rs, int i) -> rs.getInt(1)).get(0);
        }
        catch (DataAccessException ex) {}
        
        return cnaEventId;
    }
    
    /**
     * CnaEvent row mapper. 
     * 
     * @return RowMapper
     */
    private RowMapper cnaEventRowMapper() {
        return (RowMapper) (ResultSet rs, int i) -> {
            HashMap<String, BeanMap> beans_by_name = new HashMap();
            beans_by_name.put("cna_event", BeanMap.create(new CnaEvent()));
            
            // go through each column from sql query and set respective bean properties accordingly
            ResultSetMetaData resultSetMetaData = rs.getMetaData();
            for (int index=1; index<=resultSetMetaData.getColumnCount(); index++) {
                // get the table name and the field name for the bean
                String table = resultSetMetaData.getColumnLabel(index).split("\\.")[0];
                String field = resultSetMetaData.getColumnLabel(index).split("\\.")[1];

                BeanMap beanMap = beans_by_name.get(table);
                if (rs.getObject(index) != null) {
                    if (field.equals("alteration")) {
                        // if alteration then set alterationType enum
                        CnaEvent.AlterationType alterationType;
                        if (rs.getInt(index) == -2) {
                            alterationType = CnaEvent.AlterationType.HOMOZYGOUS_DELETION;
                        }
                        else {
                            alterationType = CnaEvent.AlterationType.AMPLIFICATION;
                        }
                        beanMap.put(field, alterationType);
                    }
                    else {
                        beanMap.put(field, rs.getObject(index));
                    }
                }
            }
            CnaEvent cnaEvent = (CnaEvent) beans_by_name.get("cna_event").getBean();
            
            return cnaEvent;
        };
    }
    
}
