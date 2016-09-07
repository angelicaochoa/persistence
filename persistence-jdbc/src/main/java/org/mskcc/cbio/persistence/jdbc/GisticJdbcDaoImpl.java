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
import java.sql.ResultSet;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of GisticJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class GisticJdbcDaoImpl implements GisticJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(GisticJdbcDaoImpl.class);
    
    @Autowired
    public GisticJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code GisticJdbcDao}
     */
    @Override
    public Integer addGisticBatch(List<Gistic> gisticList) {
        String SQL = "INSERT INTO gistic " + 
                "(gistic_roi_id, cancer_study_id, chromosome, cytoband, wide_peak_start, wide_peak_end, q_value, amp) " +
                "VALUES(:gisticRoiId, :cancerStudyId, :chromosome, :cytoband, :widePeakStart, :widePeakEnd, :qValue, :amp)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(gisticList.toArray(new Gistic[gisticList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing gistic batch into GISTIC");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }

    /**
     * Implementation of {@code GisticJdbcDao}
     */
    @Override
    public Integer addGisticGenesBatch(List<GisticToGene> gisticGeneList) {
        String SQL = "INSERT INTO gistic_to_gene " + 
                "(gistic_roi_id, entrez_gene_id) " + 
                "VALUES(:gisticRoiId, :entrezGeneId)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(gisticGeneList.toArray(new GisticToGene[gisticGeneList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing gistic to gene batch into GISTIC_TO_GENE");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }

    /**
     * Implementation of {@code GisticJdbcDao}
     */
    @Override
    public Integer getLargestGisticRoiId() {
        String SQL = "SELECT MAX(gistic_roi_id) FROM gistic";
        
        Integer gisticRoiId = -1;
        try {
            gisticRoiId = namedParameterJdbcTemplate.query(SQL, (ResultSet rs, int i) -> rs.getInt(1)).get(0);
        }
        catch (DataAccessException ex) {}
        
        return gisticRoiId;
    }
    
}
