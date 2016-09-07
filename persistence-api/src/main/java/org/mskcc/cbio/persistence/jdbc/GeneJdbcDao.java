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

import org.mskcc.cbio.model.Gene;

import java.util.List;

/**
 * Interface for Gene retrieval
 * @author ochoaa
 */
public interface GeneJdbcDao {
    
    /**
     * Inserts a gene record into GENE. 
     * If the entrez gene id is <= 0 then a new gene record is created with 
     * a generated entrez gene id
     * Returns a Gene instance with an updated entrez gene id if necessary 
     * 
     * @param gene
     * @return Gene
     */
    Gene addGene(Gene gene);
    
    /**
     * Insert gene aliases into GENE_ALIAS for a given gene.
     * 
     * @param gene
     */
    void addGeneAliases(Gene gene);
    
    /**
     * Given an entrez gene id, returns a gene instance.
     * If gene does not exist, returns null
     * 
     * @param entrezGeneId
     * @return Gene
     */
    Gene getGene(Integer entrezGeneId);
    
    /**
     * Given a hugo gene symbol, returns a gene instance.
     * If gene does not exist, returns null
     * 
     * @param hugoGeneSymbol
     * @return 
     */
    Gene getGene(String hugoGeneSymbol);
    
    /**
     * Given an entrez gene id and gene alias, returns a gene instance.
     * If gene alias does not exist, returns null
     * 
     * @param entrezGeneId
     * @param geneAlias
     * @return 
     */
    Gene getGene(Integer entrezGeneId, String geneAlias);
    
    /**
     * List all genes in db.
     * 
     * @return 
     */
    List<Gene> listAllGenes();
    
    /**
     * Returns the next generated entrez gene id for adding gene entries without
     * an entrez gene id. 
     * 
     * @return Integer
     */
    Integer getNextGeneratedEntrezGeneId();
    
}
