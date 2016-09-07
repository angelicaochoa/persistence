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

/**
 * Interface for Mutation, MutationEvent, and MutationCount inserts and retrieval
 * @author ochoaa
 */
public interface MutationJdbcDao {
    
    /**
     * Insert mutation record into MUTATION.
     * 
     * @param mutation
     */
    void addMutation(Mutation mutation);
    
    /**
     * Insert mutation event record into MUTATION_EVENT.
     * 
     * @param mutationEvent
     */
    void addMutationEvent(MutationEvent mutationEvent);
    
    /**
     * Update mutation record in MUTATION.
     * 
     * @param mutation
     */
    void updateMutation(Mutation mutation);    
    
    /**
     * Given a mutation, return a Mutation instance if exists.
     * Existing mutation is determined by mutation event, genetic profile id, and sample id
     * Returns null if not found
     * 
     * @param mutation
     * @return Mutation
     */
    Mutation getMutation(Mutation mutation);
    
    /**
     * Given a mutation event, return a MutationEvent instance if exists.
     * Existing mutation event is determined by gene, chromosome, start position,
     * end position, protein change, tumor seq allele, and mutation type
     * Returns null if not found
     * 
     * @param mutationEvent
     * @return MutationEvent
     */
    MutationEvent getMutationEvent(MutationEvent mutationEvent);
    
    /**
     * Insert calculated mutation counts for a given genetic profile id.
     * Returns the number of rows affected by insertion
     * 
     * @param geneticProfileId 
     * @return Integer
     */
    Integer calculateMutationCount(Integer geneticProfileId);
    
    /**
     * Returns the largest mutation event ID from MUTATION_EVENT.
     * 
     * @return Integer
     */
    Integer getLargestMutationEventId();

}
