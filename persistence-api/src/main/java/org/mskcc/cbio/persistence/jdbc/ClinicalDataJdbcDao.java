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

import org.mskcc.cbio.model.summary.ClinicalDataSummary;

import java.util.*;

/**
 * Interface for ClinicalData inserts
 * @author ochoaa
 */
public interface ClinicalDataJdbcDao {
    
    /**
     * Insert batch of clinical data records into CLINICAL_PATIENT or CLINICAL_SAMPLE.
     * Returns number of rows affected by the batch update
     * 
     * @param tableName
     * @param clinicalDataList
     * @return Integer
     */
    Integer addClinicalDataBatch(String tableName, List<ClinicalDataSummary> clinicalDataList);

    /**
     * Insert clinical datum into CLINICAL_PATIENT or CLINICAL_SAMPLE.
     * Returns number of rows affected by the batch update
     * 
     * @param tableName
     * @param clinicalDatum
     * @return Integer
     */
    Integer addClinicalDatum(String tableName, ClinicalDataSummary clinicalDatum);    
    
    /**
     * Given a patient internal id, returns a set of clinical data attribute ids.
     * Returns empty set if internal id not associated with any clinical data
     * 
     * @param internalId
     * @return Map<String, String>
     */
    Map<String, String> getPatientClinicalDataAttributes(Integer internalId);
    
    /**
     * Given a sample internal id, returns a set of clinical data attribute ids.
     * Returns empty set if internal id not associated with any clinical data
     * 
     * @param internalId
     * @return Map<String, String>
     */
    Map<String, String> getSampleClinicalDataAttributes(Integer internalId);
    
}
