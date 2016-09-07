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

package org.mskcc.cbio.model;

import java.util.*;

/**
 *
 * @author abeshoua
 */
public class MutationSignatureFactory {
	
    private static final List<String> NUCLEOTIDES = Arrays.asList(new String[]{"A", "C", "T", "G"});

    private static final String[] CANONICAL_SNP_TYPES = new String[]{"C>A", "C>G", "C>T", "T>A", "T>C", "T>G"};

    private static final String[] NO_CONTEXT_MUTATION_TYPES;
    private static final String[] ONE_BP_CONTEXT_MUTATION_TYPES;

    static {
            NO_CONTEXT_MUTATION_TYPES = new String[CANONICAL_SNP_TYPES.length];
            System.arraycopy(CANONICAL_SNP_TYPES, 0, NO_CONTEXT_MUTATION_TYPES, 0, CANONICAL_SNP_TYPES.length);

            ONE_BP_CONTEXT_MUTATION_TYPES = new String[NUCLEOTIDES.size() * CANONICAL_SNP_TYPES.length * NUCLEOTIDES.size()];
            int i = 0;
            for (String snp : CANONICAL_SNP_TYPES) {
                    for (String before : NUCLEOTIDES) {
                            for (String after : NUCLEOTIDES) {
                                    ONE_BP_CONTEXT_MUTATION_TYPES[i] = before + snp + after;
                                    i++;
                            }
                    }
            }
    }

    private static String complementaryNucleotide(String nucleotide) {
            String comp = null;
            switch(nucleotide) {
                    case "A":
                            comp = "T";
                            break;
                    case "T":
                            comp = "A";
                            break;
                    case "C":
                            comp = "G";
                            break;
                    case "G":
                            comp = "C";
                            break;
            }
            return comp;
    }

    private static String getNoContextMutationTypeString(Mutation mutation) {
            String ref = mutation.getMutationEvent().getReferenceAllele();
            if (ref == null || ref.length() != 1) {
                    // Not a SNP
                    return null;
            }
            String tum = mutation.getMutationEvent().getTumorSeqAllele();
            if (tum == null || tum.length() != 1) {
                    // Not a SNP
                    return null;
            }
            if (NUCLEOTIDES.indexOf(ref) == -1 || NUCLEOTIDES.indexOf(tum) == -1) {
                    // Somethings wrong
                    return null;
            }
            // If everythings good, then we have the SNP
            // Put it into canonical form: starting with C or T
            if (!ref.equals("C") && !ref.equals("T")) {
                    ref = complementaryNucleotide(ref);
                    tum = complementaryNucleotide(tum);
            }
            String snp = ref + ">" + tum;
            return snp;
    }

    private static Map<String, Integer> makeNoContextSignature(List<Mutation> mutations) {
            Map<String, Integer> noContextSignature = new HashMap<>();

            for (Mutation mutation: mutations) {
                    String mutationType = getNoContextMutationTypeString(mutation);
                    if (mutationType != null) {
                            Integer currentCount = noContextSignature.get(mutationType);
                            if (currentCount == null) {
                                    currentCount = 0;
                            }
                            currentCount += 1;
                            noContextSignature.put(mutationType, currentCount);
                    }
            }
            for (String mutationType: NO_CONTEXT_MUTATION_TYPES) {
                    if (!noContextSignature.containsKey(mutationType)) {
                            noContextSignature.put(mutationType, 0);
                    }
            }
            return noContextSignature;
    }
    
    public static MutationSignature NoContextMutationSignature(String id, List<Mutation> mutations) {
            return new MutationSignature(id, makeNoContextSignature(mutations));
    }
    
}