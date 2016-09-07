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

import org.mskcc.cbio.model.StructuralVariant;

import java.util.*;
import javax.annotation.Resource;
import org.apache.commons.logging.*;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of StructuralVariantJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class StructuralVariantJdbcDaoImpl implements StructuralVariantJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;

    private static final Log LOG = LogFactory.getLog(StructuralVariantJdbcDaoImpl.class);
    
    @Autowired
    public StructuralVariantJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }
    
    @Override
    public Integer addStructuralVariantBatch(List<StructuralVariant> structuralVariantList) {
        String SQL = "INSERT INTO structural_variant " + 
                "(sample_id, breakpoint_type, annotation, comments, confidence_class, connection_type, event_info, " + 
                "mapq, normal_read_count, normal_variant_count, paired_end_read_support, site1_chrom, site1_desc, " + 
                "site1_gene, site1_pos, site2_chrom, site2_desc, site2_gene, site2_pos, split_read_support, sv_class_name, " + 
                "sv_desc, sv_length, tumor_read_count, tumor_variant_count, variant_status_name, genetic_profile_id) " +  
                "VALUES(:sampleId, :breakpointType, :annotation, :comments, :confidenceClass, :connectionType, :eventInfo, " + 
                ":mapQ, :normalReadCount, :normalVariantCount, :pairedEndReadSupport, :site1Chrom, :site1Desc, " + 
                ":site1Gene, :site1Pos, :site2Chrom, :site2Desc, :site2Gene, :site2Pos, :splitReadSupport, :svClassName, " + 
                ":svDesc, :svLength, :tumorReadCount, :tumorVariantCount, :variantStatusName, :geneticProfileId)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
            SqlParameterSourceUtils.createBatch(structuralVariantList.toArray(new StructuralVariant[structuralVariantList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing structural variant batch into STRUCTURAL_VARIANT");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }
    
}
