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
import org.springframework.jdbc.core.namedparam.*;
import org.springframework.stereotype.Repository;

/**
 * JDBC implementation of MutSigJdbcDao.
 * 
 * @author ochoaa
 */
@Repository
public class MutSigJdbcDaoImpl implements MutSigJdbcDao {

    @Resource(name="namedParameterJdbcTemplate")
    private final NamedParameterJdbcTemplate namedParameterJdbcTemplate;
        
    private static final Log LOG = LogFactory.getLog(MutSigJdbcDaoImpl.class);
    
    @Autowired
    public MutSigJdbcDaoImpl(NamedParameterJdbcTemplate namedParameterJdbcTemplate) {
        this.namedParameterJdbcTemplate = namedParameterJdbcTemplate;
    }

    /**
     * Implementation of {@code MutSigJdbcDao}
     */
    @Override
    public Integer addMutSigBatch(List<MutSig> mutSigList) {
        String SQL = "INSERT INTO mut_sig " + 
                "(cancer_study_id, entrez_gene_id, rank, NumBasesCovered, NumMutations, p_value, q_value) " +
                "VALUES(:cancerStudyId, :entrezGeneId, :rank, :numBasesCovered, :numMutations, :pValue, :qValue)";
        
        int[] rows = null;
        try {
            rows = namedParameterJdbcTemplate.batchUpdate(SQL, 
                    SqlParameterSourceUtils.createBatch(mutSigList.toArray(new MutSig[mutSigList.size()])));
        }
        catch (Exception ex) {
            LOG.error("Error importing mutsig batch into MUT_SIG");
            ex.printStackTrace();
        }
        
        // return the number of rows affected
        return rows!=null?Arrays.stream(rows).sum():0;
    }
    
}
