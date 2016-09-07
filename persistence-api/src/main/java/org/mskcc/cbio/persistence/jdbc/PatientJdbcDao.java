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

import org.mskcc.cbio.model.Patient;

/**
 * Interface for Patient inserts
 * and Patient retrieval
 * @author ochoaa
 */
public interface PatientJdbcDao {
    
    /**
     * Insert patient record into PATIENT. 
     * Returns patient instance with updated internal id
     * 
     * @param patient
     * @return Patient
     */
    Patient addPatient(Patient patient);
    
    /**
     * Given a stable id and cancer study id, returns a patient instance. 
     * If patient does not exist, returns null.
     * 
     * @param stableId
     * @param cancerStudyId
     * @return Patient
     */
    Patient getPatient(String stableId, int cancerStudyId);
    
    /**
     * Given an internal id, returns a patient instance. 
     * If patient does not exist, returns null.
     * 
     * @param internalId
     * @return Patient
     */
    Patient getPatient(int internalId);
    
}
