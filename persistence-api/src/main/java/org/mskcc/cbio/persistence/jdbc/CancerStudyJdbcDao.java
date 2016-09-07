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

import org.mskcc.cbio.model.CancerStudy;

import java.util.List;

/**
 * Interface for CancerStudy inserts
 * and CancerStudy retrieval 
 * @author ochoaa
 */
public interface CancerStudyJdbcDao {

    /**
     * Insert cancer study record into CANCER_STUDY. 
     * Returns cancer study instance with updated internal id
     * 
     * @param cancerStudy
     * @return CancerStudy
     */
    CancerStudy addCancerStudy(CancerStudy cancerStudy);
    
    /**
     * Given a cancer study identifier, returns a cancer study instance.
     * If cancer study does not exist, returns null
     * 
     * @param cancerStudyIdentifier
     * @return CancerStudy 
     */
    CancerStudy getCancerStudy(String cancerStudyIdentifier);
    
    /**
     * Given a cancer study id, returns a cancer study instance.
     * If cancer study does not exist, returns null
     * 
     * @param cancerStudyId
     * @return CancerStudy 
     */
    CancerStudy getCancerStudy(Integer cancerStudyId);
    
    /**
     * List all cancer studies in db.
     * 
     * @return List<CancerStudy>
     */
    List<CancerStudy> listAllCancerStudies();
    
    /**
     * Given a cancer study id, deletes all records associated with study.
     * 
     * @param cancerStudyId 
     */
    void deleteCancerStudy(Integer cancerStudyId);
    
}
