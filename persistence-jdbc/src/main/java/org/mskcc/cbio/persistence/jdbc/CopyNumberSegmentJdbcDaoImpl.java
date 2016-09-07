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

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataAccessException;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of CopyNumberSegmentJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class CopyNumberSegmentJdbcDaoImpl implements CopyNumberSegmentJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(CopyNumberSegmentJdbcDaoImpl.class);
    
    @Autowired
    public CopyNumberSegmentJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    /**
     * Implementation of {@code CopyNumberSegmentJdbcDao}
     */
    @Override
    public Integer addCopyNumberSegmentBatch(List<CopyNumberSegment> copyNumberSegmentList) {
        String SQL = "INSERT INTO copy_number_seg " +
                "(cancer_study_id, sample_id, chr, start, end, num_probes, segment_mean) " + 
                "VALUES (:cancerStudyId, :sampleId, :chr, :start, :end, :numProbes, :segmentMean)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(copyNumberSegmentList.toArray(new CopyNumberSegment[copyNumberSegmentList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing clinical data batch into COPY_NUMBER_SEG");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }

    /**
     * Implementation of {@code CopyNumberSegmentJdbcDao}
     */
    @Override
    public void addCopyNumberSegmentFile(CopyNumberSegmentFile copyNumberSegmentFile) {
        String SQL = "INSERT INTO copy_number_seg_file " + 
                "(cancer_study_id, reference_genome_id, description, filename) " + 
                "VALUES(:cancer_study_id, :reference_genome_id, :description, :filename)";
        MapSqlParameterSource namedParameters = new MapSqlParameterSource();
        namedParameters.addValue("cancer_study_id", copyNumberSegmentFile.getCancerStudyId());
        namedParameters.addValue("reference_genome_id", copyNumberSegmentFile.getReferenceGenomeId().getName());
        namedParameters.addValue("description", copyNumberSegmentFile.getDescription());
        namedParameters.addValue("filename", copyNumberSegmentFile.getFilename());
        
        try{
            namedParameterJdbcTemplate.update(SQL, namedParameters);
        }        
        catch (DataAccessException ex) {
            LOG.error("Error importing COPY_NUMBER_SEGMENT_FILE record with data filename: " + copyNumberSegmentFile.getFilename());
            ex.printStackTrace();
        }        
    }
    
}
