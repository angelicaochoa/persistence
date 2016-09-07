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

import org.mskcc.cbio.model.GeneticProfile;

/**
 * Interface for GeneticProfile inserts and retrieval
 * @author ochoaa
 */
public interface GeneticProfileJdbcDao {
    
    /**
     * Insert genetic profile record into GENETIC_PROFILE.
     * Returns genetic profile instance with updated geneticProfileId
     * 
     * @param geneticProfile
     * @return GeneticProfile
     */
    GeneticProfile addGeneticProfile(GeneticProfile geneticProfile);
    
    /**
     * Given a genetic profile stable id, returns a genetic profile instance.
     * If genetic profile does not exist, returns null
     * 
     * @param stableId
     * @return GeneticProfile
     */
    GeneticProfile getGeneticProfile(String stableId);
    
    /**
     * Given a genetic profile id, returns a genetic profile instance.
     * If genetic profile does not exist, returns null
     * 
     * @param geneticProfileId
     * @return geneticProfileId
     */
    GeneticProfile getGeneticProfile(int geneticProfileId);
    
}
